package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTm;
import vos.Ingredientes;

@Path("ingredientes")
public class IngredientesServices {

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

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getiNGREDIENTE() {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<Ingredientes> ingre;
		try {
			ingre = tm.darIngredientes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingre).build();
	}

	/**
	 * path http://localhost:8080/RotondAndes/rest/ingredientes/nombre?nombre=leche
	 * 
	 * @param nombre
	 * @return
	 */
	@GET
	@Path("{nombre}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getIngrediente(@QueryParam("nombre") String nombre) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		Ingredientes ingre;
		try {
			ingre = tm.darIngrediente(nombre);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingre).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addIgrediente(Ingredientes ingre) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.addIngrediente(ingre);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingre).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response UpdateIngrediente(Ingredientes ingre) {
		RotondAndesTm tm = new RotondAndesTm(getPath());

		try {
			tm.UpdateIngrediente(ingre);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingre).build();
	}

}
