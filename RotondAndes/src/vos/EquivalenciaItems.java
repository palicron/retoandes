package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class EquivalenciaItems {

	@JsonProperty(value = "idItem")
	private Long idItem;
	@JsonProperty(value = "idEquiv")
	private Long idEquiv;

	/**
	 * @return the idIngrdiente
	 */
	public Long getIdItem() {
		return idItem;
	}

	/**
	 * @param idIngrdiente
	 *            the idIngrdiente to set
	 */
	public void setIdItem(Long idIngrdiente) {
		this.idItem = idIngrdiente;
	}

	/**
	 * @return the idEquiv
	 */
	public Long getIdEquiv() {
		return idEquiv;
	}

	/**
	 * @param idEquiv
	 *            the idEquiv to set
	 */
	public void setIdEquiv(Long idEquiv) {
		this.idEquiv = idEquiv;
	}

}
