package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.PersonaVos;

public class DaoPersonas {

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
	public DaoPersonas() {
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

	public List<PersonaVos> darPersonas() throws SQLException, Exception {
		ArrayList<PersonaVos> personas = new ArrayList<PersonaVos>();

		String sql = "SELECT * FROM PERSONA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			Long id = rs.getLong("USUARIO_ID");
			String rol = rs.getString("ROL");
			String clave = rs.getString("CLAVE");
			Long telefono = rs.getLong("Telefono");
			String nombre = rs.getString("NOMBRE");

			personas.add(new PersonaVos(id, rol, clave, telefono, nombre));
		}
		return personas;
	}

	public void addPersona(PersonaVos persona) throws SQLException, Exception {

		String sql = "INSERT INTO PERSONA VALUES (";
		sql += persona.getId() + ",'";

		if (persona.getRol() == "Administrador" | persona.getRol() == "Cliente") {
			throw new Exception("Forbidden");
		}

		sql += persona.getRol() + "',";
		sql += persona.getClave() + ",";
		sql += persona.getTelefono() + ",'";
		sql += persona.getNombre() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void addCliente(PersonaVos persona) throws SQLException, Exception {

		String sql = "INSERT INTO PERSONA VALUES (";
		sql += persona.getId() + ",'";

		if (persona.getRol() != null) {
			throw new Exception("Forbidden");
		}
		if (persona.getRol() == null) {
			persona.setRol("CLIENTE");
		}
		sql += persona.getRol() + "',";
		sql += persona.getClave() + ",";
		sql += persona.getTelefono() + ",'";
		sql += persona.getNombre() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public PersonaVos buscarPersonaPorId(Long id) throws SQLException {
		PersonaVos persona = null;

		String sql = "SELECT * FROM PERSONA WHERE USUARIO_ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			Long id2 = rs.getLong("USUARIO_ID");
			String rol = rs.getString("ROL");
			String clave = rs.getString("CLAVE");
			Long telefono = rs.getLong("Telefono");
			String nombre = rs.getString("NOMBRE");
			persona = new PersonaVos(id2, rol, clave, telefono, nombre);
		}

		return persona;
	}

	public List<PersonaVos> darAdmins() throws SQLException {
		ArrayList<PersonaVos> personas = new ArrayList<PersonaVos>();

		String sql = "SELECT * FROM PERSONA WHERE ROL ='Administrador'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			Long id = rs.getLong("USUARIO_ID");
			String rol = rs.getString("ROL");
			String clave = rs.getString("CLAVE");
			Long telefono = rs.getLong("Telefono");
			String nombre = rs.getString("NOMBRE");

			personas.add(new PersonaVos(id, rol, clave, telefono, nombre));
		}
		return personas;
	}

	public List<PersonaVos> darClientes() throws SQLException {
		ArrayList<PersonaVos> personas = new ArrayList<PersonaVos>();

		String sql = "SELECT * FROM PERSONA WHERE ROL ='CLIENTE'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			Long id = rs.getLong("USUARIO_ID");
			String rol = rs.getString("ROL");
			String clave = rs.getString("CLAVE");
			Long telefono = rs.getLong("Telefono");
			String nombre = rs.getString("NOMBRE");

			personas.add(new PersonaVos(id, rol, clave, telefono, nombre));
		}
		return personas;
	}

}
