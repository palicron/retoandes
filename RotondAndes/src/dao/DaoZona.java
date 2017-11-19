package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Zona;

public class DaoZona {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOVideo <b>post: </b> Crea la instancia del DAO
	 * e inicializa el Arraylist de recursos
	 */
	public DaoZona() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
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

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la
	 * conexión que entra como parametro.
	 * 
	 * @param con
	 *            - connection a la base de datos
	 */
	public void setConn(Connection con) {
		this.conn = con;
	}

	public void addZona(Zona zona) throws SQLException, Exception {

		String sql = "INSERT INTO ZONAS VALUES (";

		sql += zona.getId() + ",'";
		sql += zona.getAmbiente() + "',";
		sql += zona.getCapacidad() + ",'";
		sql += zona.getDescripcion() + "','";
		sql += zona.getEspecial() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public int darcapacidad(Long id) throws SQLException, Exception {
		int cap = 0;
		String sql = "SELECT CAPACIDAD FROM ZONAS WHERE ID=" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long in = rs.getLong("CAPACIDAD");
			cap += in;
		}
		return cap;
	}

}
