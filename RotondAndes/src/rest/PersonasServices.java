package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import vos.Preferencia;
import vos.ReservaVos;


@Path("profiles")
public class PersonasServices {

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

	/**
	 * 
	 * @param persona
	 * @return
	 */
	@POST
	@Path("{id: \\d+}/preferences")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPreferencia(Preferencia preferencia) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.addPreferencia(preferencia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencia).build();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id: \\d+}/preferences/{tipo}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPreferencias(@PathParam("id") Long id, @PathParam("tipo") String id2) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			Preferencia v = tm.BuscarPreferenciaPorTipo(id, id2);
			return Response.status(200).entity(v).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

	}

	@POST
	@Path("{id: \\d+}/preferences/{tipo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePreferencia(Preferencia preferencia, @PathParam("tipo") String tipoAct) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.updatePreferencia(preferencia, tipoAct);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencia).build();
	}

	@DELETE
	@Path("{id: \\d+}/preferences/{tipo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePreferencia(Preferencia preferencia) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.deletePreferencia(preferencia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencia).build();
	}

	/**
	 * metdo que expone servicio rest post para agregar una reserva a un usuario
	 * 
	 * @param preferencia
	 * @return
	 */
	@POST
	@Path("{id: \\d+}/reservas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReserva(@PathParam("id") Long id, ReservaVos reserva) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		reserva.setUid(id);
		try {
			tm.addreserva(reserva);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}

	@POST
	@Path("{id: \\d+}/addorden")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addOrden(@PathParam("id") Long id) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.addorden(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(id).build();
	}

	@POST
	@Path("{id:\\d+}/orden/{id2:\\d+}/item/{id3:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addOrdenitem(@PathParam("id") Long id, @PathParam("id2") Long id2, @PathParam("id3") Long id3) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {

			tm.addordenitem(id2, id3);

		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(id).build();
	}

	@POST
	@Path("{id:\\d+}/orden/{id2:\\d+}/item/{id3:\\d+}/equiv/{id4:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addequivalencia(@PathParam("id") Long id, @PathParam("id2") Long id2, @PathParam("id3") Long id3,
			@PathParam("id4") Long id4) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {

			tm.rempalsarequvi(id2, id3, id4);

		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(id).build();
	}

	@POST
	@Path("{id:\\d+}/mesa/{id2:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addequivalencia(@PathParam("id") Long id, @PathParam("id2") Long id2) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {

			tm.mesaOrden(id, id2);

		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(id + " " + id2).build();
	}


}
