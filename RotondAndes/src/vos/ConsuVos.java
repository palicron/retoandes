package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsuVos {

	@JsonProperty(value = "items")
	private Long items;
	@JsonProperty(value = "total")
	private Long total;
	@JsonProperty(value = "Restaurante")
	private Long res;

	public ConsuVos(@JsonProperty(value = "items") Long items, @JsonProperty(value = "total") Long total,
			@JsonProperty(value = "Restaurante") Long Restaurante) {
		this.items = items;
		this.total = total;
		this.res = Restaurante;
	}

	/**
	 * @return the items
	 */
	public Long getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(Long items) {
		this.items = items;
	}

	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * @return the res
	 */
	public Long getRes() {
		return res;
	}

	/**
	 * @param res
	 *            the res to set
	 */
	public void setRes(Long res) {
		this.res = res;
	}
}
