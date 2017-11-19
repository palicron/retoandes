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
import vos.ConsuVos;
import vos.Equivalencia;
import vos.EquivalenciaItems;
import vos.Ingredientes;
import vos.Items;
import vos.MenuVos;

@Path("restaurantes")
public class RestaurantesServices {

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
	@Path("menus")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMenus() {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<MenuVos> menu;
		try {
			menu = tm.darMenus();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}



	@POST
	@Path("{id: \\d+}/newMenu")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenu(@PathParam("id") Long id, MenuVos menu) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		menu.setRid(id);
		try {
			tm.addmenu(menu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}

	@POST
	@Path("{id:\\d+}/menu/{id2:\\d+}/item/{id3:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItemmenu(@PathParam("id") Long id, @PathParam("id2") Long id2, @PathParam("id3") Long id3) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		Items item;
		try {

			item = tm.darItem(id3);
			tm.addMenuItem(item, id2);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(item).build();
	}

	@POST
	@Path("{id:\\d+}/additem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItem(@PathParam("id") Long id, Items item) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		item.setRid(id);
		try {
			tm.addItem(item);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(item).build();
	}

	@POST
	@Path("{id:\\d+}/item/{id2:\\d+}/ingrediente")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItemIngrediente(@PathParam("id") Long id, @PathParam("id2") Long id2, Ingredientes ingre) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.addItemigrendeinte(id2, ingre);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingre).build();
	}

	@POST
	@Path("{id:\\d+}/ingredientes/{id2:\\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addIngredienteEquivalente(Equivalencia ingreEquiv) {
		RotondAndesTm tm = new RotondAndesTm(getPath());

		try {
			tm.addIngredienteEquivalente(ingreEquiv.getIdIngrdiente(), ingreEquiv.getIdEquiv());
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingreEquiv).build();
	}

	@POST
	@Path("{id:\\d+}/items/{id2:\\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItemEquivalente(EquivalenciaItems itemEquiv, @PathParam("id") Long idRestaurante) {
		RotondAndesTm tm = new RotondAndesTm(getPath());

		try {
			tm.addItemEquivalente(itemEquiv.getIdItem(), itemEquiv.getIdEquiv(), idRestaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(itemEquiv).build();
	}

	@POST
	@Path("{id:\\d+}/surtir/items/{id2:\\d+}/{cantidad:\\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response surtirRestauante(@PathParam("id") Long idRestaurante, @PathParam("id2") Long item,
			@PathParam("cantidad") Long cantidad) {
		RotondAndesTm tm = new RotondAndesTm(getPath());

		try {
			tm.surtirRestaurante(idRestaurante, item, cantidad);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(item).build();
	}

	@DELETE
	@Path("{id:\\d+}/cancelarPedido/{id2:\\d+}")

	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelarPedido(@PathParam("id") Long idRestaurante, @PathParam("id2") Long ordenId) {
		RotondAndesTm tm = new RotondAndesTm(getPath());

		try {
			tm.cancelarOrden(idRestaurante, ordenId);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity("pedido cancelado").build();
	}

	@GET
	@Path("{id:\\d+}/pedidos")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getconsulta1(@PathParam("id") Long id) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<ConsuVos> con;
		try {
			con = tm.Conespe(id);

		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(con).build();
	}
	
	@DELETE
	@Path("{id:\\d+}/pedidos")

	@Produces(MediaType.APPLICATION_JSON)
	public Response getConsulta(@PathParam("id") Long idRestaurante) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<ConsuVos> con;
		try {
			con = tm.Conespe(idRestaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(con).build();
	}
	
	@GET
	@Path( "{id:\\d+}/orden/{id2:\\d+} ")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOrdencompreta(@PathParam("id") Long id, @PathParam("id2") Long id2) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.comproOrden(id2);

		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(id2).build();
	}

}
