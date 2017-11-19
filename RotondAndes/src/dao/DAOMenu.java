package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.MenuVos;

public class DAOMenu {

	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOMenu() {
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

	public ArrayList<MenuVos> darMenus() throws SQLException, Exception {
		ArrayList<MenuVos> menu = new ArrayList<MenuVos>();

		String sql = "SELECT * FROM MENU";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			Long rid = rs.getLong("RESTAURANTE_ID");
			Long precio = rs.getLong("PRECIO");
			menu.add(new MenuVos(id, rid, precio));
		}
		return menu;

	}

	public MenuVos darMenu(long id) throws SQLException, Exception {
		MenuVos menu = null;

		String sql = "SELECT * FROM MENU WHERE ID=" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long ids = rs.getLong("ID");
			Long rid = rs.getLong("RESTAURANTE_ID");
			Long precio = rs.getLong("PRECIO");
			menu = new MenuVos(ids, rid, precio);
		}
		return menu;
	}

	public void addMenu(MenuVos menu) throws SQLException, Exception {

		String sql = "INSERT INTO MENU (RESTAURANTE_ID) VALUES (";
		sql += menu.getRid() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void addMenuItems(Long mid, Long iid) throws SQLException, Exception {
		String sql = "INSERT INTO ITEMS_MENU (ITEMS_ID,MENU_ID) VALUES (";
		sql += iid + ",";
		sql += mid + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void Updatevalos(Long id, Long valor) throws SQLException, Exception {
		MenuVos menu = darMenu(id);
		Long precio = menu.getPrecio() + valor;
		menu.setPrecio(precio);
		String sql = "UPDATE MENU SET ";
		sql += "ID=" + menu.getId() + ",";
		sql += "PRECIO=" + menu.getPrecio() + ", ";
		sql += "RESTAURANTE_ID=" + menu.getRid();
		sql += " WHERE ID = " + menu.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
