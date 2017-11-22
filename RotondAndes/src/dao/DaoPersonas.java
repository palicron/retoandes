package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vos.ConsuconsumoVos;
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

		while (rs.next() && personas.size()<2000) {

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
	
	public List<ConsuconsumoVos> darConfe(ConsuconsumoVos con , Long id,long id2) throws Exception {
		ArrayList<ConsuconsumoVos> consu = new ArrayList<ConsuconsumoVos>();
		ArrayList<String> order = new ArrayList<String>();
		Date in = con.getIncio();
		Calendar cal = Calendar.getInstance();
		cal.setTime(in);
		int d = cal.get(Calendar.DAY_OF_MONTH);
		int m = cal.get(Calendar.MONTH)+1;
		int y = cal.get(Calendar.YEAR);
		String fi = d+"-"+m+"-"+y;
		Date fin = con.getFin();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(fin);
		int df = cal1.get(Calendar.DAY_OF_MONTH);
		int mf = cal1.get(Calendar.MONTH)+1;
		int yf = cal1.get(Calendar.YEAR);
		String fif = df+"-"+mf+"-"+yf;
		order.add("ORDER BY FECHA ");
		order.add("ORDER BY ID ");
		order.add("ORDER BY NOMBRE ");
		order.add("ORDER BY NOMBRE_PRODUC ");
		String sql = "SELECT PERSONA.USUARIO_ID AS ID , ROL,NOMBRE,NOMBRE_PRODUC,FECHA  ";
		sql += "FROM(select ORDEN.ID_PERSONA AS ID,ORDEN.FECHA AS FECHA, ITEMS.NOMBRE AS NOMBRE_PRODUC  ";
		sql += "from( ORDEN JOIN ITEMS_ORDEN ";
		sql += "ON ID = ITEMS_ORDEN.ORDEN_ID)JOIN ITEMS ";
		sql += "ON ITEMS_ORDEN.ITEMS_ID = ITEMS.ID ";
				sql += "WHERE ITEMS.ID_RESTAURANTE =" + id + " ";
 						sql += "AND FECHA BETWEEN '"+ fi+ "' AND '"+ fif +"') JOIN  PERSONA ";
 						sql += "ON PERSONA.USUARIO_ID = ID ";
 		if(id2==1)
 		{
 			sql += order.get(0);
 		}
 		else if(id2==2)
 		{
 			sql += order.get(1);
 		}
 		else if(id2==3)
 		{
 			sql += order.get(2);
 		}
 		else if (id2==4)
 		{
 			
 		}
 		else
 		{
 			throw new Exception("opcionde de ordenamiento no valido");
 		}
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			Long idu = rs.getLong("ID");
			String rol = rs.getString("ROL");
			String nombre = rs.getString("NOMBRE");
			Date fecha = rs.getDate("FECHA");

			consu.add(new ConsuconsumoVos(idu,rol,nombre,fecha,fecha));
			
		}
		return consu;
	}
	public List<ConsuconsumoVos> darConfeUS(ConsuconsumoVos con , Long id,long id2,long id3) throws Exception {
		ArrayList<ConsuconsumoVos> consu = new ArrayList<ConsuconsumoVos>();
		ArrayList<String> order = new ArrayList<String>();
		Date in = con.getIncio();
		Calendar cal = Calendar.getInstance();
		cal.setTime(in);
		int d = cal.get(Calendar.DAY_OF_MONTH);
		int m = cal.get(Calendar.MONTH)+1;
		int y = cal.get(Calendar.YEAR);
		String fi = d+"-"+m+"-"+y;
		Date fin = con.getFin();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(fin);
		int df = cal1.get(Calendar.DAY_OF_MONTH);
		int mf = cal1.get(Calendar.MONTH)+1;
		int yf = cal1.get(Calendar.YEAR);
		String fif = df+"-"+mf+"-"+yf;
		order.add("ORDER BY FECHA");
		order.add("ORDER BY ID");
		order.add("ORDER BY NOMBRE");
		String sql = "SELECT PERSONA.USUARIO_ID AS ID , ROL,NOMBRE,NOMBRE_PRODUC,FECHA  ";
		sql += "FROM(select ORDEN.ID_PERSONA AS ID,ORDEN.FECHA AS FECHA, ITEMS.NOMBRE AS NOMBRE_PRODUC  ";
		sql += "from( ORDEN JOIN ITEMS_ORDEN ";
		sql += "ON ID = ITEMS_ORDEN.ORDEN_ID)JOIN ITEMS ";
		sql += "ON ITEMS_ORDEN.ITEMS_ID = ITEMS.ID ";
				sql += "WHERE ITEMS.ID_RESTAURANTE =" + id + " ";
 						sql += "AND FECHA BETWEEN '"+ fi+ "' AND '"+ fif +"') JOIN  PERSONA ";
 						sql += "ON PERSONA.USUARIO_ID = ID ";
 						sql += "WHERE ID = " + id3 +" ";	
 		if(id2==1)
 		{
 			sql += order.get(0);
 		}
 		else if(id2==2)
 		{
 			sql += order.get(1);
 		}
 		else if(id2==3)
 		{
 			sql += order.get(2);
 		}
 		else
 		{
 			throw new Exception("opcionde de ordenamiento no valido");
 		}
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			Long idu = rs.getLong("ID");
			String rol = rs.getString("ROL");
			String nombre = rs.getString("NOMBRE");
			Date fecha = rs.getDate("FECHA");

			consu.add(new ConsuconsumoVos(idu,rol,nombre,fecha,fecha));
			
		}
		return consu;
	}
	
	public List<ConsuconsumoVos> darNoConfe(ConsuconsumoVos con , Long id,long id2) throws Exception {
		ArrayList<ConsuconsumoVos> consu = new ArrayList<ConsuconsumoVos>();
		ArrayList<String> order = new ArrayList<String>();
		Date in = con.getIncio();
		Calendar cal = Calendar.getInstance();
		cal.setTime(in);
		int d = cal.get(Calendar.DAY_OF_MONTH);
		int m = cal.get(Calendar.MONTH)+1;
		int y = cal.get(Calendar.YEAR);
		String fi = d+"-"+m+"-"+y;
		Date fin = con.getFin();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(fin);
		int df = cal1.get(Calendar.DAY_OF_MONTH);
		int mf = cal1.get(Calendar.MONTH)+1;
		int yf = cal1.get(Calendar.YEAR);
		String fif = df+"-"+mf+"-"+yf;
		order.add("ORDER BY FECHA");
		order.add("ORDER BY ID");
		order.add("ORDER BY NOMBRE");
		String sql = "SELECT PERSONA.USUARIO_ID AS ID , ROL,NOMBRE,FECHA  ";
		sql += "FROM(select ORDEN.ID_PERSONA AS ID,ORDEN.FECHA AS FECHA  ";
		sql += "from( ORDEN JOIN ITEMS_ORDEN ";
		sql += "ON ID = ITEMS_ORDEN.ORDEN_ID)JOIN ITEMS  ";
		sql += "ON ITEMS_ORDEN.ITEMS_ID = ITEMS.ID  ";
				sql += "WHERE ITEMS.ID_RESTAURANTE !=" + id + " ";
 						sql += "AND FECHA BETWEEN '"+ fi+ "' AND '"+ fif +"') JOIN  PERSONA ";
 						sql += "ON PERSONA.USUARIO_ID = ID ";
 		if(id2==1)
 		{
 			sql += order.get(0);
 		}
 		else if(id2==2)
 		{
 			sql += order.get(1);
 		}
 		else if(id2==3)
 		{
 			sql += order.get(2);
 		}
 		else
 		{
 			throw new Exception("opcionde de ordenamiento no valido");
 		}
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			Long idu = rs.getLong("ID");
			String rol = rs.getString("ROL");
			String nombre = rs.getString("NOMBRE");
			Date fecha = rs.getDate("FECHA");

			consu.add(new ConsuconsumoVos(idu,rol,nombre,fecha,fecha));
			
		}
		if(consu.size()==0)
			throw new Exception("Nigun usuario consumio en este rango de fecha");
		
		
		return consu;
	}

	public List<ConsuconsumoVos> darNoConfeUS(ConsuconsumoVos con , Long id,long id2,long id3) throws Exception {
		ArrayList<ConsuconsumoVos> consu = new ArrayList<ConsuconsumoVos>();
		ArrayList<String> order = new ArrayList<String>();
		Date in = con.getIncio();
		Calendar cal = Calendar.getInstance();
		cal.setTime(in);
		int d = cal.get(Calendar.DAY_OF_MONTH);
		int m = cal.get(Calendar.MONTH)+1;
		int y = cal.get(Calendar.YEAR);
		String fi = d+"-"+m+"-"+y;
		Date fin = con.getFin();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(fin);
		int df = cal1.get(Calendar.DAY_OF_MONTH);
		int mf = cal1.get(Calendar.MONTH)+1;
		int yf = cal1.get(Calendar.YEAR);
		String fif = df+"-"+mf+"-"+yf;
		order.add("ORDER BY FECHA");
		order.add("ORDER BY ID");
		order.add("ORDER BY NOMBRE");
		String sql = "SELECT PERSONA.USUARIO_ID AS ID , ROL,NOMBRE,NOMBRE_PRODUC,FECHA ";
		sql += "FROM(select ORDEN.ID_PERSONA AS ID,ORDEN.FECHA AS FECHA,ITEMS.NOMBRE AS NOMBRE_PRODUC " ;
		sql += "from( ORDEN JOIN ITEMS_ORDEN ";
		sql += "ON ID = ITEMS_ORDEN.ORDEN_ID)JOIN ITEMS ";
		sql += "ON ITEMS_ORDEN.ITEMS_ID = ITEMS.ID ";
				sql += "WHERE ITEMS.ID_RESTAURANTE !=" + id + " ";
 						sql += "AND FECHA BETWEEN '"+ fi+ "' AND '"+ fif +"') JOIN  PERSONA ";
 						sql += "ON PERSONA.USUARIO_ID = ID ";
 						sql += "WHERE ID = " + id3 +" ";	
 		if(id2==1)
 		{
 			sql += order.get(0);
 		}
 		else if(id2==2)
 		{
 			sql += order.get(1);
 		}
 		else if(id2==3)
 		{
 			sql += order.get(2);
 		}
 		else
 		{
 			throw new Exception("opcionde de ordenamiento no valido");
 		}
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			Long idu = rs.getLong("ID");
			String rol = rs.getString("ROL");
			String nombre = rs.getString("NOMBRE");
			Date fecha = rs.getDate("FECHA");

			consu.add(new ConsuconsumoVos(idu,rol,nombre,fecha,fecha));
			
		}
		if(consu.size()==0)
			throw new Exception("Usted no a consumido en esta fecha en este restaurante");
		
		return consu;
	}
////
	public List<PersonaVos> buenosClientes() throws SQLException {
		
		ArrayList<PersonaVos> personas = new ArrayList<PersonaVos>();

		String sql = "SELECT PERSONA.USUARIO_ID,PERSONA.ROL,PERSONA.CLAVE,PERSONA.Telefono,PERSONA.NOMBRE FROM PERSONA JOIN ORDEN   ON PERSONA.USUARIO_ID = ORDEN.ID_PERSONA JOIN ITEMS_ORDEN ON ORDEN.ID=ITEMS_ORDEN.ORDEN_ID JOIN ITEMS ON ITEMS_ORDEN.ITEMS_ID=ITEMS.ID "
				+ "WHERE ITEMS.PRECIO>=37";

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
