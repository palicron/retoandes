package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PersonaVos {

	/**
	 * id del usuario
	 */
	@JsonProperty(value = "id")
	private Long id;

	/**
	 * rol del usuario
	 */
	@JsonProperty(value = "rol")
	private String rol;

	/**
	 * clave del usuario
	 */
	@JsonProperty(value = "clave")
	private String clave;

	/**
	 * telefono del usuario
	 */
	@JsonProperty(value = "telefono")
	private Long telefono;

	/**
	 * nombre del usuario
	 */
	@JsonProperty(value = "nombre")
	private String nombre;

	/**
	 * Crea una persona
	 * 
	 * @param id
	 * @param rol
	 * @param clave
	 * @param telefono
	 * @param nombre
	 */

	public PersonaVos(@JsonProperty(value = "id") Long id, @JsonProperty(value = "rol") String rol,
			@JsonProperty(value = "clave") String clave, @JsonProperty(value = "telefono") Long telefono,
			@JsonProperty(value = "nombre") String nombre) {
		super();
		this.id = id;
		this.rol = rol;
		this.clave = clave;
		this.telefono = telefono;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Long getTelefono() {
		return telefono;
	}

	public void setTelefono(Long telefono) {
		this.telefono = telefono;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
