package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.transaction.SystemException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTm;
import vos.PersonaVos;
import vos.Preferencia;

@Path("clientesData")
public class ClientesDataServices {

	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la
	 * conexion actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy
	 * actual dentro del servidor.
	 * 
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}

	private String doErrorMessage(Exception e) {
		return "{ \"ERROR\": \"" + e.getMessage() + "\"}";
	}

	// TRANSACCIONES//

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClientesR3() {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<PersonaVos> clientes;
		try {
			clientes = tm.darClientes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(clientes).build();
	}

	@GET
	@Path("{id: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClientePorId(@PathParam("id") Long id) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			PersonaVos v = tm.BuscarPersonaPorId(id);
			return Response.status(200).entity(v).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("{id: \\d+}/preferences/{id2: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPreferenciasPorId(@PathParam("id") Long id, @PathParam("id2") String id2) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			Preferencia v = tm.BuscarPreferenciaPorId(id, id2);
			return Response.status(200).entity(v).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

	}

	@GET
	@Path("{id: \\d+}/preferences")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPreferencias(@PathParam("id") Long id) {

		RotondAndesTm tm = new RotondAndesTm(getPath());

		try {
			if (!tm.BuscarPersonaPorId(id).getRol().equalsIgnoreCase("Cliente")) {
				throw new SystemException("forbidden");
			}
			List<Preferencia> v = tm.BuscarPreferenciasLista(id);
			return Response.status(200).entity(v).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

	}
}
