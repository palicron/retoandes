package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Equivalencia {

	@JsonProperty(value = "idIngrediente")
	private String idIngrdiente;
	@JsonProperty(value = "idEquiv")
	private String idEquiv;

	/**
	 * @return the idIngrdiente
	 */
	public String getIdIngrdiente() {
		return idIngrdiente;
	}

	/**
	 * @param idIngrdiente
	 *            the idIngrdiente to set
	 */
	public void setIdIngrdiente(String idIngrdiente) {
		this.idIngrdiente = idIngrdiente;
	}

	/**
	 * @return the idEquiv
	 */
	public String getIdEquiv() {
		return idEquiv;
	}

	/**
	 * @param idEquiv
	 *            the idEquiv to set
	 */
	public void setIdEquiv(String idEquiv) {
		this.idEquiv = idEquiv;
	}
}

