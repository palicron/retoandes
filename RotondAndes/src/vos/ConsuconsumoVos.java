package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsuconsumoVos {
	@JsonProperty(value = "ID")
	private Long id;
	@JsonProperty(value = "ROL")
	private String rol;
	@JsonProperty(value = "NOMBRE")
	private String nombre;
	@JsonProperty(value = "FI")
	private Date incio;
	@JsonProperty(value = "FF")
	private Date fin;
	
	public ConsuconsumoVos(@JsonProperty(value = "ID") Long id,@JsonProperty(value = "ROL") String rol,@JsonProperty(value = "NOMBRE") String nombre
			,@JsonProperty(value = "FI")  Date inicio,@JsonProperty(value = "FF") Date fin)
	{
		this.id=id;
		this.rol=rol;
		this.nombre = nombre;
		this.incio = inicio;
		this.fin= fin;
		
	}

	public Date getIncio() {
		return incio;
	}

	public void setIncio(Date incio) {
		this.incio = incio;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
