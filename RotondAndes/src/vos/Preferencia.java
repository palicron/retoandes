package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Preferencia {
	@JsonProperty(value = "id_persona")
	private Long id_persona;

	@JsonProperty(value = "tipo")
	private String tipo;

	public Preferencia(@JsonProperty(value = "id_persona") Long id_preferencia,
			@JsonProperty(value = "tipo") String tipo) {

		this.id_persona = id_preferencia;
		this.tipo = tipo;
	}

	/**
	 * @return the id_preferencia
	 */
	public Long id_persona() {
		return id_persona;
	}

	/**
	 * @param id_preferencia
	 *            the id_preferencia to set
	 */
	public void setId_persona(Long id_preferencia) {
		this.id_persona = id_preferencia;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
