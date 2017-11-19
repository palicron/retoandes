package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Ingredientes;

public class DAOIngredientes {

	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOIngredientes() {
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

	public ArrayList<Ingredientes> darIngredientes() throws SQLException, Exception {
		ArrayList<Ingredientes> ingre = new ArrayList<Ingredientes>();

		String sql = "SELECT * FROM INGREDIENTES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String descrip = rs.getString("DESCRIPCION");
			String desen = rs.getString("DES_EN");
			ingre.add(new Ingredientes(nombre, descrip, desen));
		}
		return ingre;

	}

	public Ingredientes darIngrediente(String id) throws SQLException, Exception {

		Ingredientes it = null;
		String sql = "SELECT * FROM INGREDIENTES WHERE NOMBRE LIKE '" + id + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String descrip = rs.getString("DESCRIPCION");
			String desen = rs.getString("DES_EN");
			it = new Ingredientes(nombre, descrip, desen);
		}
		return it;

	}

	public void addIngrediente(Ingredientes ing) throws SQLException, Exception {

		String sql = "INSERT INTO INGREDIENTES (NOMBRE,DESCRIPCION,DES_EN) VALUES (";
		sql += "'" + ing.getNombre() + "',";
		sql += "'" + ing.getDescripcion() + "',";
		sql += "'" + ing.getDescripcionen() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateIngredientes(Ingredientes ing) throws SQLException, Exception {

		String sql = "UPDATE INGREDIENTES SET ";
		sql += "NOMBRE='" + ing.getNombre() + "',";
		sql += "DESCRIPCION='" + ing.getDescripcion() + "', ";
		sql += "DES_EN='" + ing.getDescripcionen() + "' ";
		sql += " WHERE INGREDIENTE_ID like'" + ing.getNombre() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void addEquivalenciaIngrediente(String id1, String id2) throws SQLException, Exception {
		if (darIngrediente(id1) == null && darIngrediente(id2) == null) {

			throw new SQLException("no existen uno o ninguno de los ingredientes propuestos");
		}

		String sql = "INSERT INTO EQUIV_INGREDIENTE (IDINGREDIENTEA,IDEQUIV) VALUES ('";
		sql += id1 + "','";
		sql += id2 + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

}
