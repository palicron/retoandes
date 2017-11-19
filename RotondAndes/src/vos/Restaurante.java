package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Restaurante {

	@JsonProperty(value = "id")
	private Long id;

	@JsonProperty(value = "nombre")
	private String nombre;

	@JsonProperty(value = "zona_id")
	private Long zona_id;

	@JsonProperty(value = "descripcion")
	private String descripcion;

	@JsonProperty(value = "web")
	private String web;

	@JsonProperty(value = "especialidad")
	private String especialidad;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public Restaurante(@JsonProperty(value = "id") Long id, @JsonProperty(value = "zona_id") Long zona,
			@JsonProperty(value = "descripcion") String descripcion, @JsonProperty(value = "web") String web,
			@JsonProperty(value = "especialidad") String especialidad) {
		super();
		this.id = id;
		this.zona_id = zona;
		this.descripcion = descripcion;
		this.web = web;
		this.especialidad = especialidad;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getZona() {
		return zona_id;
	}

	public void setZona(Long zona) {
		this.zona_id = zona;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
