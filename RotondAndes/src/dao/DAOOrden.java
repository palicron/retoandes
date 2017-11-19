package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.OrdenVos;

public class DAOOrden {

	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOOrden() {
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

	public ArrayList<OrdenVos> darOrdenes() throws SQLException, Exception {
		ArrayList<OrdenVos> ordenes = new ArrayList<OrdenVos>();

		String sql = "SELECT * FROM ORDEN";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			Long pid = rs.getLong("ID_PERSONA");
			Long total = rs.getLong("TOTAL");
			Date ff = rs.getDate("FECHA");
			ordenes.add(new OrdenVos(id, pid, total, ff));
		}
		return ordenes;

	}

	public OrdenVos darOrden(Long ids) throws SQLException, Exception {
		OrdenVos ordenes = null;

		String sql = "SELECT * FROM ORDEN WHERE ID = " + ids;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			Long pid = rs.getLong("ID_PERSONA");
			Long total = rs.getLong("TOTAL");
			Date ff = rs.getDate("FECHA");
			ordenes = new OrdenVos(id, pid, total, ff);
		}
		return ordenes;

	}

	public void addOrden(Long id) throws SQLException, Exception {

		String sql = "INSERT INTO ORDEN (ID_PERSONA) VALUES (";
		sql += id + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void addOrdenItem(Long id, Long Iid) throws SQLException, Exception {

		String sql = "INSERT INTO ITEMS_ORDEN (ORDEN_ID,ITEMS_ID) VALUES (";
		sql += id + " ," + Iid + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void cancelarOrden(Long idRestaurante, Long ordenId) throws Exception {

		surtirItemsIdOrden(idRestaurante, ordenId);

		String sql = "DELETE FROM ITEMS_ORDEN WHERE ID = " + ordenId;
		String sql2 = "DELETE FROM ORDEN WHERE ID = " + ordenId;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt.executeQuery();
	}

	public ArrayList<Long> surtirItemsIdOrden(Long idRestaurante, Long ordenId) throws SQLException, Exception {
		ArrayList<Long> items = new ArrayList<Long>();

		DAOitems item = new DAOitems();

		String sql = "SELECT * FROM ITEMS_ORDEN WHERE ORDEN_ID = " + ordenId;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			Long id = rs.getLong("ORDEN_ID");
			Long itemsId = rs.getLong("ITEMS_ID");
			item.surtirRestaurante(idRestaurante, itemsId, new Long(1));

		}
		return items;

	}

	// equivalencia orden
	public void equivalencia(Long id, Long idi, Long idq) throws SQLException, Exception {
		OrdenVos ordenes = null;

		String sql = "SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; " + "SELECT * from ITEMS_ORDEN JOIN EQUIV_PRODUCTO "
				+ "ON ITEMS_ORDEN.ITEMS_ID = EQUIV_PRODUCTO.PRODUCTOID " + "WHERE ITEMS_ORDEN.ORDEN_ID = " + id
				+ "AND ITEMS_ORDEN.ITEMS_ID = " + idi + "AND EQUIV_PRODUCTO.EQUIVID = " + idq
				+ " FOR UPDATE OF ITEMS_ID";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			sql = "Update ITEMS_ORDEN SET " + " ORDEN_ID =" + id + ",ITEMS_ID =" + idq + "WHERE ORDEN_ID =" + id
					+ "AND ITEMS_ID =" + idi;
			prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			prepStmt = conn.prepareStatement("COMMIT");
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			ordenes = new OrdenVos(id, idi, idq, null);
		}
		if (ordenes == null) {
			prepStmt = conn.prepareStatement("ROLLBACK");
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			throw new Exception("no se pudo cambiar el equivalente");
		}
	}

	/// comprobar toda el orden

	public void complobarentrega(Long idp) throws SQLException, Exception {
		DAOitems it = new DAOitems();
		ArrayList items = new ArrayList();
		String sql = "alter session set isolation_level=serializable ";
		int cont = 0;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "SELECT * FROM ITEMS_ORDEN WHERE ORDEN_ID =" + idp;
		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String b = rs.getString("ENTREGADO");
			Long id = rs.getLong("ITEMS_ID");
			items.add(id);
			if (b.equalsIgnoreCase("N")) {
				throw new Exception("la ordene esta incompleta");
			} else {
				cont++;
			}

		}
		if (cont == 0) {
			throw new Exception("la orden noe xiste o no tine items");
		} else {
			for (int i = 0; i < items.size(); i++) {
				it.subtraeritem((Long) items.get(i));
			}
			throw new Exception("la ordene esta completa");
		}

	}

	public void desitems(Long id) {

	}
}
