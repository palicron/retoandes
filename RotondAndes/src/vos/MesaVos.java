package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class MesaVos {
	@JsonProperty(value = "id")
	private Long id;

	public MesaVos(@JsonProperty(value = "id") Long id) {
		this.id = id;
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

}
