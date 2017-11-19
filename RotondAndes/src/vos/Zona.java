package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Zona {

	@JsonProperty(value = "id")
	private Long id;

	@JsonProperty(value = "ambiente")
	private String ambiente;

	@JsonProperty(value = "capacidad")
	private Long capacidad;

	@JsonProperty(value = "descripcion_tecnica")
	private String descripcion_tecnica;

	@JsonProperty(value = "especial")
	private char especial;

	public Zona(@JsonProperty(value = "id") Long id, @JsonProperty(value = "ambiente") String ambiente,
			@JsonProperty(value = "capacidad") Long capacidad,
			@JsonProperty(value = "descripcion_tecninca") String descripcion,
			@JsonProperty(value = "especial") char especial) {
		super();
		this.id = id;
		this.ambiente = ambiente;
		this.capacidad = capacidad;
		this.descripcion_tecnica = descripcion;
		this.especial = especial;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public Long getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
	}

	public String getDescripcion() {
		return descripcion_tecnica;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion_tecnica = descripcion;
	}

	public char getEspecial() {
		return especial;
	}

	public void setEspecial(char especial) {
		this.especial = especial;
	}

}
