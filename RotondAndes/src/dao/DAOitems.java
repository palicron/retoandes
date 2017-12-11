package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Ingredientes;
import vos.Items;
import vos.ListaProductos;
import vos.Productoxxx;

public class DAOitems {

	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOitems() {
		recursos = new ArrayList<Object>();
	}

	public void cerrarRecursos() {
		for (Object ob : recursos) {
			if (ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	public void setConn(Connection con) {
		this.conn = con;
	}

	public ListaProductos darItems() throws SQLException, Exception {
		ArrayList<Productoxxx> items = new ArrayList<Productoxxx>();
        
		String sql = "SELECT * FROM ITEMS";
        List<Productoxxx> ll = new ArrayList<Productoxxx>();
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			Double tiempop = rs.getDouble("TIEMPO_PREPARACION");

			Productoxxx por = new Productoxxx(name, tiempop);
			ll.add(por);
			
		}
		 ListaProductos sad = new ListaProductos(ll);
		 return sad;

	}
//	public ArrayList<Items> darItems() throws SQLException, Exception {
//		ArrayList<Items> items = new ArrayList<Items>();
//
//		String sql = "SELECT * FROM ITEMS";
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		while (rs.next()) {
//			Long id = rs.getLong("ID");
//			Long Rid = rs.getLong("ID_RESTAURANTE");
//			String name = rs.getString("NOMBRE");
//			String tipo = rs.getString("TIPO");
//			Long precio = rs.getLong("PRECIO");
//			String nombreen = rs.getString("NOMBREINGLES");
//			Long tiempop = rs.getLong("TIEMPO_PREPARACION");
//			Long costop = rs.getLong("COSTO_PRODU");
//			int cant = rs.getInt("CANTIDAD");
//			items.add(new Items(id, Rid, name, tipo, precio, nombreen, tiempop, costop, cant));
//		}
//		return items;
//
//	}

	public Items darItem(Long id) throws SQLException, Exception {

		Items it = null;

		String sql = "SELECT * FROM ITEMS WHERE ID= " + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if (rs.next()) {
			Long ids = rs.getLong("ID");
			Long Rid = rs.getLong("ID_RESTAURANTE");
			String name = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			Long precio = rs.getLong("PRECIO");
			String nombreen = rs.getString("NOMBREINGLES");
			Long tiempop = rs.getLong("TIEMPO_PREPARACION");
			Long costop = rs.getLong("COSTO_PRODU");
			int cant = rs.getInt("CANTIDAD");
			it = new Items(id, Rid, name, tipo, precio, nombreen, tiempop, costop, cant);
		}
		return it;

	}

	public void addItems(Items item) throws SQLException, Exception {

		String sql = "INSERT INTO ITEMS (ID_RESTAURANTE,NOMBRE,TIPO,PRECIO,NOMBREINGLES,TIEMPO_PREPARACION,COSTO_PRODU,CANTIDAD) VALUES (";
		sql += item.getRid() + ",'";
		sql += item.getNombre() + "','";
		sql += item.getTipo() + "',";
		sql += item.getPrecio() + ",'";
		sql += item.getNombreEN() + "',";
		sql += item.getTiempopreparacion() + ",";
		sql += item.getCostoproducion() + ",";
		sql += item.getCantidad() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateItem(Items item) throws SQLException, Exception {

		String sql = "UPDATE ITEMS SET ";
		sql += "ID=" + item.getId() + ",";
		sql += "ID_RESTAURANTE=" + item.getRid() + ",";
		sql += "NOMBRE='" + item.getNombre() + "',";
		sql += "TIPO='" + item.getTipo() + "',";
		sql += "PRECIO=" + item.getPrecio() + ", ";
		sql += "NOMBREINGLES='" + item.getNombreEN() + "',";
		sql += "TIEMPO_PREPARACION=" + item.getTiempopreparacion() + ",";
		sql += "COSTO_PRODU=" + item.getTiempopreparacion() + " ";
		sql += " WHERE ID = " + item.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void deleteItems(Long id) throws SQLException, Exception {

		String sql = "DELETE FROM ITEMS";
		sql += " WHERE ID = " + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	//////////////////////
	/// ingredientes//////
	////////////////////

	public void addIngrediente(Long id, Ingredientes nom) throws SQLException {
		String sql = "INSERT INTO INGREDIENTE_ITEM (ITEMS_ID,INGRE_ID) VALUES (";
		sql += id + ",'";
		sql += nom.getNombre() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public ArrayList<String> darItemsIngrediente(Long id) throws SQLException, Exception {
		ArrayList<String> in = new ArrayList<String>();
		ArrayList<Ingredientes> items = new ArrayList<Ingredientes>();
		DAOIngredientes ingredi = new DAOIngredientes();
		ingredi.setConn(conn);
		String sql = "SELECT INGRE_ID AS NOM FROM INGREDIENTE_ITEM WHERE ITEMS_ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOM");
			in.add(nombre);
		}

		return in;

	}

	public List<Items> darItemsFiltrado(String filter) throws SQLException {

		ArrayList<Items> items = new ArrayList<Items>();

		String sql = "";
		if (filter.equalsIgnoreCase("precio")) {
			sql = "SELECT * FROM ITEMS OREDER BY PRECIO";
		} else if (filter.equalsIgnoreCase("tipo")) {
			sql = "SELECT * FROM ITEMS OREDER BY TIPO";
		}

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			Long Rid = rs.getLong("ID_RESTAURANTE");
			String name = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			Long precio = rs.getLong("PRECIO");
			String nombreen = rs.getString("NOMBREINGLES");
			Long tiempop = rs.getLong("TIEMPO_PREPARACION");
			Long costop = rs.getLong("COSTO_PRODU");
			int cant = rs.getInt("CANTIDAD");
			items.add(new Items(id, Rid, name, tipo, precio, nombreen, tiempop, costop, cant));
		}
		return items;
	}

	public void addEquivalenciaItems(Long id1, Long id2, Long idrestaurante) throws SQLException, Exception {
		Items it1 = darItem(id1);
		Items it2 = darItem(id2);

		if (it1 == null && it2 == null || it1.getRid() != it2.getRid()) {

			throw new SQLException("no existen uno o ninguno de los items propuestos o no son del mismo restaurante");
		}

		String sql = "INSERT INTO EQUIV_PRODUCTO (PRODUCTOID,EQUIVID) VALUES (";
		sql += id1 + ",";
		sql += id2 + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void surtirRestaurante(Long idRestaurante, Long item, Long cantidad) throws SQLException, Exception {

		Items item2 = darItem(item);
		cantidad += item2.getCantidad();

		String sql = "UPDATE ITEMS SET CANTIDAD =";
		sql += cantidad;
		sql += "WHERE ID_RESTAURANTE = " + idRestaurante + " AND ID = " + item;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public List<Items> darItemsConsumo(Long idCliente) throws SQLException {
		ArrayList<Items> items = new ArrayList<Items>();

		String sql = "SELECT * FROM ITEMS_ORDEN INNER JOIN ORDEN ON ITEMS_ORDEN.ORDEN_ID = ORDEN.ID WHERE ID_PERSONA = ";

		sql += idCliente;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			Long Rid = rs.getLong("ID_RESTAURANTE");
			String name = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			Long precio = rs.getLong("PRECIO");
			String nombreen = rs.getString("NOMBREINGLES");
			Long tiempop = rs.getLong("TIEMPO_PREPARACION");
			Long costop = rs.getLong("COSTO_PRODU");
			int cant = rs.getInt("CANTIDAD");
			items.add(new Items(id, Rid, name, tipo, precio, nombreen, tiempop, costop, cant));
		}
		return items;
	}

	// desconta un item
	public void subtraeritem(Long idi) throws SQLException, Exception {
		String sql = "alter session set isolation_level=serializable ";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		sql = "SELECT CANTIDAD FROM ITEMS WHERE ID = " + idi + " FOR UPDATE OF CANTIDAD";
		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();

		Long rr = rs.getLong("CANTIDAD");
		rr = rr - 1;
		sql = "UPDATE ITEMS SET";
		sql += "CANTIDAD =" + rr;
		sql += "WHERE ID =" + idi;
		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

}
