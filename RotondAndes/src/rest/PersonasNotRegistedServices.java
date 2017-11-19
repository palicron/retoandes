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
import vos.PersonaVos;

@Path("temp")
public class PersonasNotRegistedServices {

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
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPersonas() {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<PersonaVos> personas;
		try {
			personas = tm.darPersonas();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(personas).build();
	}

	/**
	 * 
	 * @param persona
	 * @return
	 */

	@POST
	@Path("{id: \\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPersona(PersonaVos persona) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.addPersona(persona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(persona).build();
	}

	/**
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

}
