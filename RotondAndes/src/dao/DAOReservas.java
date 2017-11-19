package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ReservaVos;

public class DAOReservas {

	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOReservas() {
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

	public ArrayList<ReservaVos> darReservas() throws SQLException, Exception {
		ArrayList<ReservaVos> reservas = new ArrayList<ReservaVos>();

		String sql = "SELECT * FROM RESERVAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID_RESERVA");
			Date fecha = rs.getDate("FECHA");
			Long zid = rs.getLong("ZONA_ID");
			int invitados = rs.getInt("INVITADOS");
			Long mid = rs.getLong("MENU_ID");
			Long uid = rs.getLong("PERSONA_ID");
			reservas.add(new ReservaVos(id, zid, fecha, invitados, mid, uid));
		}
		return reservas;

	}

	public void addReserva(ReservaVos reserva) throws SQLException, Exception {

		String sql = "INSERT INTO RESERVAS (FECHA,ZONA_ID,INVITADOS,MENU_ID,PERSONA_ID) VALUES (TO_DATE('";
		sql += reserva.getFecha() + "','YYYY/MM/DD HH24:MI:SS'),";
		sql += reserva.getZid() + ",";
		sql += reserva.getInvitados() + ",";
		sql += reserva.getMid() + ",";
		sql += reserva.getUid() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public int darconflicto(ReservaVos res) throws SQLException, Exception {

		int ocupacion = 0;
		String sql = "SELECT * FROM RESERVAS WHERE ZONA_ID=" + res.getZid() + "AND FECHA=TO_DATE('" + res.getFecha()
				+ "','YYYY/MM/DD HH24:MI:SS')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		Long i = res.getFecha().getTime();
		while (rs.next()) {
			Long uu = rs.getDate("FECHA").getTime();
			if (res.getFecha().getTime() == rs.getDate("FECHA").getTime()) {
				int invitados = rs.getInt("INVITADOS");
				ocupacion += invitados;
			}

		}
		return ocupacion + res.getInvitados();

	}
}
