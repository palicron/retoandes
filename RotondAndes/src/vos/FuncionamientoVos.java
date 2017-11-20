package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class FuncionamientoVos {
	
	@JsonProperty(value = "restauranteMasFrecuentado")
	private String restauranteMasFrecuentado;
	@JsonProperty(value = "restauranteMenosFrecuentado")
	private String restauranteMenosFrecuentado;
	@JsonProperty(value = "productoMasConsumido")
	private String productoMasConsumido;
	@JsonProperty(value = "productoMenosConsumido")
	private String productoMenosConsumido;
	
	
	/**
	 * @return the restauranteMasFrecuentado
	 */
	public String getRestauranteMasFrecuentado() {
		return restauranteMasFrecuentado;
	}


	/**
	 * @param restauranteMasFrecuentado the restauranteMasFrecuentado to set
	 */
	public void setRestauranteMasFrecuentado(String restauranteMasFrecuentado) {
		this.restauranteMasFrecuentado = restauranteMasFrecuentado;
	}


	/**
	 * @return the restauranteMenosFrecuentado
	 */
	public String getRestauranteMenosFrecuentado() {
		return restauranteMenosFrecuentado;
	}


	/**
	 * @param restauranteMenosFrecuentado the restauranteMenosFrecuentado to set
	 */
	public void setRestauranteMenosFrecuentado(String restauranteMenosFrecuentado) {
		this.restauranteMenosFrecuentado = restauranteMenosFrecuentado;
	}


	/**
	 * @return the productoMasConsumido
	 */
	public String getProductoMasConsumido() {
		return productoMasConsumido;
	}


	/**
	 * @param productoMasConsumido the productoMasConsumido to set
	 */
	public void setProductoMasConsumido(String productoMasConsumido) {
		this.productoMasConsumido = productoMasConsumido;
	}


	/**
	 * @return the productoMenosConsumido
	 */
	public String getProductoMenosConsumido() {
		return productoMenosConsumido;
	}


	/**
	 * @param productoMenosConsumido the productoMenosConsumido to set
	 */
	public void setProductoMenosConsumido(String productoMenosConsumido) {
		this.productoMenosConsumido = productoMenosConsumido;
	}


	public FuncionamientoVos(@JsonProperty(value = "restauranteMasFrecuentado") String restauranteMasFrecuentado, @JsonProperty(value = "restauranteMenosFrecuentado") String restauranteMenosFrecuentado,
			@JsonProperty(value = "productoMasConsumido") String productoMasConsumido, 	@JsonProperty(value = "productoMenosConsumido") String productoMenosConsumido) {
		
		this.restauranteMasFrecuentado = restauranteMasFrecuentado;
		this.restauranteMenosFrecuentado = restauranteMenosFrecuentado;
		this.productoMasConsumido = productoMasConsumido;
		this.productoMenosConsumido = productoMenosConsumido;
	}
	
	
	
}

