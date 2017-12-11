package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ConsuVos;
import vos.Restaurante;

public class DaoRestaurante {

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
	public DaoRestaurante() {
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

	public void addRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "INSERT INTO RESTAURANTE VALUES (";

		sql += restaurante.getId() + ",'";
		sql += restaurante.getNombre() + "',";
		sql += restaurante.getZona() + ",'";
		sql += restaurante.getDescripcion() + "','";
		sql += restaurante.getWeb() + "','";
		sql += restaurante.getEspecialidad() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	// consutla pedidos general//

	public ArrayList<ConsuVos> consutlagenral() throws SQLException, Exception {

		ArrayList<ConsuVos> list = new ArrayList<>();
		String sql = "alter session set isolation_level=serializable ";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "SELECT ITEMS_ORDEN.ITEMS_ID AS ITEMS, SUM(PRECIO) AS TOTAL, ITEMS.ID_RESTAURANTE AS RESTAURANTE "+
				 " from ITEMS_ORDEN JOIN ITEMS " + "ON ITEMS_ID = ITEMS.ID "
				+ "GROUP BY ITEMS_ID,ITEMS.ID_RESTAURANTE ";
		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			Long it = rs.getLong("ITEMS");
			Long to = rs.getLong("TOTAL");
			Long res = rs.getLong("RESTAURANTE");
			list.add(new ConsuVos(it, to, res));
		}
		return list;

	}

	// busqeuda por restaurante
	public ArrayList<ConsuVos> consutlarestaurante(Long id) throws SQLException, Exception {

		ArrayList<ConsuVos> list = new ArrayList<>();
		String sql = "alter session set isolation_level=serializable ";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "SELECT ITEMS_ORDEN.ITEMS_ID AS ITEMS, SUM(PRECIO) AS TOTAL, ITEMS.ID_RESTAURANTE AS RESTAURANTE"
				+ " from  ITEMS_ORDEN JOIN ITEMS " + "ON ITEMS_ID = ITEMS.ID " + "WHERE ITEMS.ID_RESTAURANTE = " + id
				+ "GROUP BY ITEMS_ID,ITEMS.ID_RESTAURANTE ";
		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			Long it = rs.getLong("ITEMS");
			Long to = rs.getLong("TOTAL");
			Long res = rs.getLong("RESTAURANTE");
			list.add(new ConsuVos(it, to, res));
		}
		return list;

	}

	//ELIMINA UN RESTAURANTE 
	public void eliminarRestaurante(String id) throws SQLException, Exception {
		
		String sql = "alter session set isolation_level=serializable ";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "DELETE FROM REST RESTAURANTE WHERE NOMBRE LIKE " + id;
		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);


	}

}
