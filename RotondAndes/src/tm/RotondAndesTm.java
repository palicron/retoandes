package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dao.DAOIngredientes;
import dao.DAOMenu;
import dao.DAOMesa;
import dao.DAOOrden;
import dao.DAOReservas;
import dao.DAOitems;
import dao.DaoPersonas;
import dao.DaoPreferencia;
import dao.DaoRestaurante;
import dao.DaoZona;
import vos.ConsuVos;
import vos.Ingredientes;
import vos.Items;
import vos.MenuVos;
import vos.OrdenVos;
import vos.PersonaVos;
import vos.Preferencia;
import vos.ReservaVos;
import vos.Restaurante;
import vos.Zona;

public class RotondAndesTm {

	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los
	 * datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los
	 * datos de la conexion
	 */
	private String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String driver;

	/**
	 * conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor de la clase VideoAndesMaster, esta clase modela y contiene
	 * cada una de las transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesTM, se inicializa el path absoluto
	 * del archivo de conexion y se inicializa los atributos que se usan par la
	 * conexion a la base de datos.
	 * 
	 * @param contextPathP
	 *            - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTm(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/**
	 * Metodo que inicializa los atributos que se usan para la conexion a la base de
	 * datos. <b>post: </b> Se han inicializado los atributos que se usan par la
	 * conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			System.out.println(url);
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que retorna la conexion a la base de datos
	 * 
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException
	 *             - Cualquier error que se genere durante la conexion a la base de
	 *             datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	/**
	 * da las personas de todo el sistema
	 * 
	 * @return
	 * @throws Exception
	 */

	public List<PersonaVos> darPersonas() throws Exception {
		List<PersonaVos> personas;
		DaoPersonas daoPersona = new DaoPersonas();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPersona.setConn(conn);
			personas = daoPersona.darPersonas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersona.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return personas;
	}

	/**
	 * 
	 * @param persona
	 * @throws Exception
	 */
	public void addPersona(PersonaVos persona) throws Exception {
		DaoPersonas daoPersonas = new DaoPersonas();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPersonas.setConn(conn);
			daoPersonas.addPersona(persona);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersonas.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addCliente(PersonaVos persona) throws Exception {
		DaoPersonas daoPersonas = new DaoPersonas();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPersonas.setConn(conn);
			daoPersonas.addCliente(persona);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersonas.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * retorna una persona con su ID.
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public PersonaVos BuscarPersonaPorId(Long id) throws SQLException, Exception {
		PersonaVos persona;
		DaoPersonas daoPersona = new DaoPersonas();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPersona.setConn(conn);
			persona = daoPersona.buscarPersonaPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersona.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return persona;
	}

	/**
	 * retorna los admins del sistema.
	 * 
	 * @return
	 * @throws SQLException,Exception
	 */
	public List<PersonaVos> darAdmins() throws SQLException, Exception {
		List<PersonaVos> admins;
		DaoPersonas daoPersona = new DaoPersonas();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPersona.setConn(conn);
			admins = daoPersona.darAdmins();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersona.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return admins;
	}

	public void addRestaurante(Restaurante restaurante) throws SQLException, Exception {
		DaoRestaurante daoRestaurante = new DaoRestaurante();
		try {
			////// transaccion
			this.conn = darConexion();
			daoRestaurante.setConn(conn);
			daoRestaurante.addRestaurante(restaurante);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurante.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	public void addZona(Zona zona) throws SQLException, Exception {
		DaoZona daoZona = new DaoZona();
		try {
			////// transaccion
			this.conn = darConexion();
			daoZona.setConn(conn);
			daoZona.addZona(zona);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZona.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	public void addPreferencia(Preferencia preferencia) throws SQLException, Exception {
		DaoPreferencia daoPreferencia = new DaoPreferencia();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPreferencia.setConn(conn);
			daoPreferencia.addPreferencia(preferencia);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencia.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public Preferencia BuscarPreferenciaPorTipo(Long id, String tipo) throws SQLException, Exception {
		Preferencia preferencia;
		DaoPreferencia daoPreferencia = new DaoPreferencia();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPreferencia.setConn(conn);
			preferencia = daoPreferencia.buscarPreferencias(id, tipo);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencia.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return preferencia;
	}

	//////////////////////////////////////////
	///////////////// ITEMS////////////////////
	/////////////////////////////////////////
	/**
	 * metodo que retorna los items de la base de datos
	 * 
	 * @return lista de items
	 * @throws Exception
	 */
	public List<Items> darItems() throws Exception {
		List<Items> items = null;
		DAOitems it = new DAOitems();
		try {
			this.conn = darConexion();
			it.setConn(conn);
			items = it.darItems();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				it.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return items;
	}

	/**
	 * retorna un idtem con un id
	 * 
	 * @param id
	 *            ide del item
	 * @return el item
	 * @throws Exception
	 */
	public Items darItem(Long id) throws Exception {
		Items item = null;
		DAOitems it = new DAOitems();
		try {
			this.conn = darConexion();
			it.setConn(conn);
			item = it.darItem(id);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				it.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return item;
	}

	/**
	 * crea un nuevo objeto
	 * 
	 * @param items
	 * @throws Exception
	 */
	public void addItem(Items items) throws Exception {
		DAOitems it = new DAOitems();
		try {
			this.conn = darConexion();
			it.setConn(conn);
			it.addItems(items);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				it.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	/**
	 * actuliza un item
	 * 
	 * @param items
	 * @throws Exception
	 */
	public void UpdateItem(Items items) throws Exception {
		DAOitems it = new DAOitems();
		try {
			this.conn = darConexion();
			it.setConn(conn);
			it.updateItem(items);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				it.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	/***
	 * borra un item
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void DeleteItem(Long id) throws Exception {
		DAOitems it = new DAOitems();
		try {
			this.conn = darConexion();
			it.setConn(conn);
			it.deleteItems(id);
			;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				it.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	////////////////////////////////
	//// ingredienteproducto///////
	//////////////////////////////
	public void addItemigrendeinte(Long ids, Ingredientes ingre) throws Exception {
		DAOitems it = new DAOitems();
		try {
			this.conn = darConexion();
			it.setConn(conn);
			it.addIngrediente(ids, ingre);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				it.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<Ingredientes> darItemingredientes(Long id) throws Exception {
		List<String> nom = null;
		ArrayList<Ingredientes> items = new ArrayList<Ingredientes>();
		DAOitems it = new DAOitems();
		DAOIngredientes ingre = new DAOIngredientes();
		try {
			this.conn = darConexion();
			it.setConn(conn);
			ingre.setConn(conn);
			nom = it.darItemsIngrediente(id);
			for (int i = 0; i < nom.size(); i++) {
				String nom1 = nom.get(i);
				items.add(ingre.darIngrediente(nom1));
			}

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				it.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return items;
	}

	//////////////////////////////////////////
	//////////// ingredientes//////////////////
	/////////////////////////////////////////

	public List<Ingredientes> darIngredientes() throws Exception {
		List<Ingredientes> ingre = null;
		DAOIngredientes dao = new DAOIngredientes();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			ingre = dao.darIngredientes();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return ingre;
	}

	public Ingredientes darIngrediente(String id) throws Exception {
		Ingredientes ingre = null;
		DAOIngredientes dao = new DAOIngredientes();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			ingre = dao.darIngrediente(id);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return ingre;
	}

	public void addIngrediente(Ingredientes ingre) throws Exception {
		DAOIngredientes dao = new DAOIngredientes();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			dao.addIngrediente(ingre);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void UpdateIngrediente(Ingredientes ingre) throws Exception {
		DAOIngredientes dao = new DAOIngredientes();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			dao.updateIngredientes(ingre);
			;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}
	//////////////////////////////////////////
	//////////////// Reservas//////////////////
	/////////////////////////////////////////

	public List<ReservaVos> darReservas() throws Exception {
		List<ReservaVos> reservas = null;
		DAOReservas dao = new DAOReservas();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			reservas = dao.darReservas();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return reservas;
	}

	public void addreserva(ReservaVos res) throws Exception {
		List<ReservaVos> reservas = null;
		DAOReservas dao = new DAOReservas();
		DaoZona zdao = new DaoZona();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			zdao.setConn(conn);
			int cap = dao.darconflicto(res);
			int zcap = zdao.darcapacidad(res.getZid());
			if ((zcap / 2) < cap) {
				throw new Exception("se a superado la capacidad de la zona");
			} else {
				dao.addReserva(res);
			}

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	//////////////////////////////////////////
	///////////////// menus///////////////////
	/////////////////////////////////////////
	public List<MenuVos> darMenus() throws Exception {
		List<MenuVos> menus = null;
		DAOMenu dao = new DAOMenu();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			menus = dao.darMenus();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return menus;
	}

	public void addmenu(MenuVos menu) throws Exception {

		DAOMenu dao = new DAOMenu();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			dao.addMenu(menu);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	public void addMenuItem(Items item, Long idmenu) throws Exception {
		if (item == null) {
			throw new Exception("el item que quiere agregar no existe");
		}
		DAOMenu dao = new DAOMenu();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			dao.addMenuItems(idmenu, item.getId());
			dao.Updatevalos(idmenu, item.getPrecio());
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}
	//////////////////////////////////////////
	///////////////// ordenes//////////////////
	/////////////////////////////////////////

	public List<OrdenVos> darOrdenes() throws Exception {
		List<OrdenVos> ordenes = null;
		DAOOrden dao = new DAOOrden();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			ordenes = dao.darOrdenes();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return ordenes;
	}

	public void addorden(Long id) throws Exception {
		OrdenVos ordenes = null;
		DAOOrden dao = new DAOOrden();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			dao.addOrden(id);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	public void addordenitem(Long id, Long Iid) throws Exception {

		DAOOrden dao = new DAOOrden();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			dao.addOrdenItem(id, Iid);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	public void updatePreferencia(Preferencia preferencia, String tipoAct) throws SQLException {
		DaoPreferencia daoPreferencia = new DaoPreferencia();
		try {
			////// Transacción
			this.conn = darConexion();
			daoPreferencia.setConn(conn);
			daoPreferencia.updatePreferencia(preferencia, tipoAct);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencia.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deletePreferencia(Preferencia preferencia) throws SQLException {
		DaoPreferencia daoPreferencia = new DaoPreferencia();
		try {
			////// Transacción
			this.conn = darConexion();
			daoPreferencia.setConn(conn);
			daoPreferencia.deletePreferencia(preferencia);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencia.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public Preferencia BuscarPreferenciaPorId(Long id, String tipo) throws SQLException, Exception {
		Preferencia preferencia;
		DaoPreferencia daoPreferencia = new DaoPreferencia();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPreferencia.setConn(conn);
			preferencia = daoPreferencia.buscarPreferencias(id, tipo);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencia.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return preferencia;
	}

	public List<PersonaVos> darClientes() throws SQLException {
		List<PersonaVos> clientes;
		DaoPersonas daoPersona = new DaoPersonas();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPersona.setConn(conn);
			clientes = daoPersona.darClientes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersona.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes;
	}

	public List<Preferencia> BuscarPreferenciasLista(Long id) throws SQLException {

		List<Preferencia> preferencias;
		DaoPreferencia daoPreferencia = new DaoPreferencia();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPreferencia.setConn(conn);
			preferencias = daoPreferencia.darPreferenciasClienteLista(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencia.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return preferencias;
	}

	public List<Items> darItemsFiltrados(String filter) throws SQLException {

		List<Items> items = null;
		DAOitems it = new DAOitems();
		try {
			this.conn = darConexion();
			it.setConn(conn);
			items = it.darItemsFiltrado(filter);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				it.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return items;

	}

	public void addIngredienteEquivalente(String idIngrediente, String idEquiv) throws SQLException, Exception {

		DAOIngredientes dao = new DAOIngredientes();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			dao.addEquivalenciaIngrediente(idIngrediente, idEquiv);
			;
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addItemEquivalente(Long idItem, Long idEquiv, Long idRestaurante) throws SQLException, Exception {

		DAOitems dao = new DAOitems();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			dao.addEquivalenciaItems(idItem, idEquiv, idRestaurante);
			;
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void surtirRestaurante(Long idRestaurante, Long item, Long cantidad) throws SQLException, Exception {
		DAOitems dao = new DAOitems();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			dao.surtirRestaurante(idRestaurante, item, cantidad);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void cancelarOrden(Long idRestaurante, Long ordenId) throws SQLException, Exception {

		DAOOrden dao = new DAOOrden();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			dao.cancelarOrden(idRestaurante, ordenId);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/////// EQUIVALENCIA EN ORDEN/////////
	public void rempalsarequvi(Long idorden, Long idproduc, Long idequiv) throws SQLException, Exception {
		DAOOrden dao = new DAOOrden();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			dao.equivalencia(idorden, idproduc, idequiv);
			conn.commit();
		
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	////// mesa//////

	public void mesaOrden(Long idp, Long idm) throws SQLException, Exception {
		DAOMesa dao = new DAOMesa();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
			dao.setConn(conn);
			dao.addOrdenMesa(idp, idm);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				conn.rollback();
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<Items> darPersonaConsumo(Long idCliente) throws SQLException, Exception {
		List<Items> items = null;
		DAOitems it = new DAOitems();
		try {
			this.conn = darConexion();
			it.setConn(conn);
			items = it.darItemsConsumo(idCliente);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				it.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return items;
	}

	////// REVICION ///////

	public void comproOrden(Long id) throws SQLException, Exception {
		DAOOrden dao = new DAOOrden();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			dao.complobarentrega(id);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	////// Consultar genreal ///////

	public List<ConsuVos> Congen() throws SQLException, Exception {
		List<ConsuVos> list = null;
		DaoRestaurante dao = new DaoRestaurante();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			list = dao.consutlagenral();
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return list;
	}

	//// conuslta especifica///
	public List<ConsuVos> Conespe(Long id) throws SQLException, Exception {
		List<ConsuVos> list = null;
		DaoRestaurante dao = new DaoRestaurante();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			list = dao.consutlarestaurante(id);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return list;
	}
}
