package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Items {
	@JsonProperty(value = "id")
	private Long id;
	@JsonProperty(value = "rid")
	private Long rid;
	@JsonProperty(value = "nombre")
	private String nombre;
	@JsonProperty(value = "tipo")
	private String tipo;
	@JsonProperty(value = "precio")
	private Long precio;
	@JsonProperty(value = "nombreEN")
	private String nombreEN;
	@JsonProperty(value = "tiempopreparacion")
	private Long tiempopreparacion;
	@JsonProperty(value = "costoproducion")
	private Long costoproducion;
	@JsonProperty(value = "cantidad")
	private int cantidad;
	private List<Ingredientes> ingredientes;

	public Items(@JsonProperty(value = "id") Long id, @JsonProperty(value = "rid") Long rid,
			@JsonProperty(value = "nombre") String nombre, @JsonProperty(value = "tipo") String tipo,
			@JsonProperty(value = "precio") Long precio, @JsonProperty(value = "nombreEN") String nombreEN,
			@JsonProperty(value = "tiempopreparacion") Long tiempopreparacion,
			@JsonProperty(value = "costoproducion") Long costoproducion,
			@JsonProperty(value = "cantidad") int cantidad) {
		this.id = id;
		this.rid = rid;
		this.nombre = nombre;
		this.tipo = tipo;
		this.precio = precio;
		this.nombreEN = nombreEN;
		this.tiempopreparacion = tiempopreparacion;
		this.costoproducion = costoproducion;
		this.cantidad = cantidad;

	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 *            the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the nombreEN
	 */
	public String getNombreEN() {
		return nombreEN;
	}

	/**
	 * @param nombreEN
	 *            the nombreEN to set
	 */
	public void setNombreEN(String nombreEN) {
		this.nombreEN = nombreEN;
	}

	/**
	 * @return the tiempopreparacion
	 */
	public Long getTiempopreparacion() {
		return tiempopreparacion;
	}

	/**
	 * @param tiempopreparacion
	 *            the tiempopreparacion to set
	 */
	public void setTiempopreparacion(Long tiempopreparacion) {
		this.tiempopreparacion = tiempopreparacion;
	}

	/**
	 * @return the costoproducion
	 */
	public Long getCostoproducion() {
		return costoproducion;
	}

	/**
	 * @param costoproducion
	 *            the costoproducion to set
	 */
	public void setCostoproducion(Long costoproducion) {
		this.costoproducion = costoproducion;
	}

	/**
	 * @return the ingredientes
	 */
	public List<Ingredientes> getIngredientes() {
		return ingredientes;
	}

	/**
	 * @param ingredientes
	 *            the ingredientes to set
	 */
	public void setIngredientes(List<Ingredientes> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getPrecio() {
		return precio;
	}

	public void setPrecio(Long precio) {
		this.precio = precio;
	}

}
