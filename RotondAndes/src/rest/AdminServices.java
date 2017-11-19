package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTm;
import vos.ConsuVos;
import vos.PersonaVos;
import vos.Restaurante;
import vos.Zona;

@Path("admins")
public class AdminServices {

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

	/**
	 * Metodo que expone servicio REST usando GET que da todos los usiarios de la
	 * base de datos. <b>URL: </b> http://"ip o nombre de
	 * host":8080/RotondAndes/rest/videos
	 * 
	 * @return Json con todos los videos de la base de datos o json con el error que
	 *         se produjo
	 */
	

	@GET
	@Path("pedidos")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getconsulta() {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<ConsuVos> con;
		try {
			con = tm.Congen();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(con).build();
	}

	/**
	 * Metodo para agregar empleados al sistema.
	 * 
	 * @param persona
	 * @return
	 */

	@POST
	@Path("{id: \\d+}/newClient")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPersona(PersonaVos persona, @PathParam("id") Long id) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			PersonaVos v = tm.BuscarPersonaPorId(id);
			if (v.getRol().equals("Administrador")) {

				tm.addCliente(persona);
			}

		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(persona).build();
	}

	/**
	 * retorna un admin.
	 * 
	 * @param id
	 * @return
	 */

	@GET
	@Path("{id: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPersona(@PathParam("id") Long id) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			PersonaVos v = tm.BuscarPersonaPorId(id);
			return Response.status(200).entity(v).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	/**
	 * 
	 * @param restaurante
	 * @return
	 */
	@POST
	@Path("{id: \\d+}/newRestaurante")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRestaurante(Restaurante restaurante) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.addRestaurante(restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}

	/**
	 * 
	 * @param Zona
	 * @return
	 */
	@POST
	@Path("{id:\\d+}/newZona")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addZona(Zona zona) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.addZona(zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPersonas() {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		
	
		List<PersonaVos> personas;
		try {
			personas = tm.darAdmins();
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(personas).build();
	}

}
