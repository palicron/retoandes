package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Ingredientes {

	@JsonProperty(value = "nombre")
	private String nombre;
	@JsonProperty(value = "descripcion")
	private String descripcion;
	@JsonProperty(value = "descripcionen")
	private String descripcionen;

	public Ingredientes(@JsonProperty(value = "nombre") String nombre, @JsonProperty(value = "descripcion") String des,
			@JsonProperty(value = "descripcionen") String desen) {
		this.nombre = nombre;
		this.descripcion = des;
		this.descripcionen = desen;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the descripcionen
	 */
	public String getDescripcionen() {
		return descripcionen;
	}

	/**
	 * @param descripcionen
	 *            the descripcionen to set
	 */
	public void setDescripcionen(String descripcionen) {
		this.descripcionen = descripcionen;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
