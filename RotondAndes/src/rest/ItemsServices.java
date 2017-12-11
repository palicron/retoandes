package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTm;
import vos.Ingredientes;
import vos.Items;
import vos.ListaProductos;

@Path("items")
public class ItemsServices {

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
	public Response getitems() {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		ListaProductos items;
		try {
			items = tm.darItemsR();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(items).build();
	}

	@GET
	@Path("{id: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getitem(@PathParam("id") Long id) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		Items items;
		try {
			items = tm.darItem(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(items).build();
	}

	@GET
	@Path("{id: \\d+}/ingrediente")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getitemingredientes(@PathParam("id") Long id) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<Ingredientes> items;
		try {
			items = tm.darItemingredientes(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(items).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItem(Items item) {
		RotondAndesTm tm = new RotondAndesTm(getPath());

		try {
			tm.addItem(item);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(item).build();
	}

	@POST
	@Path("{id: \\d+}/ingrediente")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItemIngrediente(@PathParam("id") Long id, Ingredientes ingre) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.addItemigrendeinte(id, ingre);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingre).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Updateitem(Items item) {
		RotondAndesTm tm = new RotondAndesTm(getPath());

		try {
			tm.UpdateItem(item);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(item).build();
	}

	@DELETE
	@Path("{id: \\d+}")
	public void DeleteItem(@PathParam("id") Long id) {
		RotondAndesTm tm = new RotondAndesTm(getPath());

		try {
			tm.DeleteItem(id);
		} catch (Exception e) {

		}

	}

	@GET
	@Path("{filter}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getItemsFiltro(@PathParam("filter") String filter) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<Items> items;
		try {
			items = tm.darItemsFiltrados(filter);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(items).build();
	}

}
