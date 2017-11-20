package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTm;
import vos.FuncionamientoVos;
import vos.PersonaVos;

@Path("gerentes")
public class GerenteServices {

	
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
	@Path("{id: \\d+}/funcionamiento")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFuncionamiento(@PathParam("id") Long id) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			FuncionamientoVos f = tm.funcionamiento();
			return Response.status(200).entity(f).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Path("{id: \\d+}/buenosClientes")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response buenosClientes(@PathParam("id") Long id) {
		RotondAndesTm tm = new RotondAndesTm(getPath());

		List<PersonaVos> personas;
		try {
			personas = tm.buenosClientes(id);
			return Response.status(200).entity(personas).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
}
