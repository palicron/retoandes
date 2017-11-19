package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOMesa {

	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOMesa() {
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

	// registrop una ordena una mesa
	public void addOrdenMesa(Long idp, Long idm) throws SQLException, Exception {

		String sql = "Alter session set isolation_level=serializable";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "INSERT INTO ORDEN (ID_PERSONA) VALUES (" + idp + ")";
		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "SELECT MAX (ID) AS MAXIMO FROM ORDEN ";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		Long iddd = rs.getLong("MAXIMO");

		sql = "INSERT INTO MESA_ORDEN (MESA_ID,ORDEN_ID) VALUES (1," + iddd + ") ";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
}
