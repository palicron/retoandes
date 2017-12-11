package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Productoxxx 
{
	
	
	@JsonProperty(value="nombreProducto")
	private String nombreProducto;
	
	
	@JsonProperty(value="tiempo")
	private double tiempo;
	
	

	
	public Productoxxx( @JsonProperty(value="nombre")String nombreProducto,@JsonProperty(value="tiempo")double tiempo) {
		super();
		
		this.nombreProducto=nombreProducto;
		
		this.tiempo = tiempo;
		
		
	}

	





	public String getNombreProducto() {
		return nombreProducto;
	}



	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}



	public double getTiempo() {
		return tiempo;
	}

	public void setTiempo(double tiempo) {
		this.tiempo = tiempo;
	}

	

	


	


}
