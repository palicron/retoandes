package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class MenuVos {
	@JsonProperty(value = "id")
	private Long id;
	@JsonProperty(value = "rid")
	private Long rid;
	@JsonProperty(value = "precio")
	private Long precio;

	public MenuVos(@JsonProperty(value = "id") Long id, @JsonProperty(value = "rid") Long rid,
			@JsonProperty(value = "precio") Long precio) {
		this.id = id;
		this.rid = rid;
		this.precio = precio;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the rid
	 */
	public Long getRid() {
		return rid;
	}

	/**
	 * @param rid
	 *            the rid to set
	 */
	public void setRid(Long rid) {
		this.rid = rid;
	}

	/**
	 * @return the precio
	 */
	public Long getPrecio() {
		return precio;
	}

	/**
	 * @param precio
	 *            the precio to set
	 */
	public void setPrecio(Long precio) {
		this.precio = precio;
	}
}
