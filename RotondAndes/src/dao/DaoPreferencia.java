package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Preferencia;

public class DaoPreferencia {

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
	public DaoPreferencia() {
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

	public void addPreferencia(Preferencia preferencia) throws SQLException, Exception {

		String sql = "INSERT INTO PREFERENCIAS_PERSONAS VALUES (";
		sql += preferencia.id_persona() + ",'";
		sql += preferencia.getTipo() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public Preferencia buscarPreferencias(Long id, String tipo) throws SQLException {

		Preferencia preferencia = null;
		String sql = "SELECT * FROM PREFERENCIAS_PERSONAS WHERE ID_PERSONA = " + id + " AND TIPO = '" + tipo + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {

			Long idPreferencia = rs.getLong("ID_PERSONA");

			String tipo2 = rs.getString("TIPO");

			preferencia = new Preferencia(idPreferencia, tipo2);
		}
		return preferencia;

	}

	public void updatePreferencia(Preferencia preferencia, String tipoAct) throws SQLException {

		String sql = "UPDATE PREFERENCIAS_PERSONAS SET ";
		sql += "ID_PERSONA=" + preferencia.id_persona() + ",";
		sql += "TIPO='" + preferencia.getTipo() + "'";
		sql += " WHERE  ID_PERSONA = " + preferencia.id_persona() + " AND TIPO ='" + tipoAct + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void deletePreferencia(Preferencia preferencia) throws SQLException {

		String sql = "DELETE FROM PREFERENCIAS_PERSONAS";
		sql += " WHERE ID_PERSONA = " + preferencia.id_persona();
		sql += " AND TIPO = '" + preferencia.getTipo() + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public List<Preferencia> darPreferenciasClienteLista(Long id2) throws SQLException {
		ArrayList<Preferencia> personas = new ArrayList<Preferencia>();

		String sql = "SELECT * FROM PREFERENCIAS_PERSONAS p inner join Persona s on s.USUARIO_ID=p.ID_PERSONA WHERE ROL ="
				+ " 'CLIENTE' AND " + "s.USUARIO_ID =" + id2 + " AND p.ID_PERSONA = " + id2;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			Long id = rs.getLong("USUARIO_ID");
			String tipo = rs.getString("TIPO");

			personas.add(new Preferencia(id, tipo));
		}
		return personas;
	}

}
