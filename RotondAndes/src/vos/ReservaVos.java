package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReservaVos {

	@JsonProperty(value = "id")
	private Long id;

	@JsonProperty(value = "zid")
	private Long zid;

	@JsonProperty(value = "fecha")
	private Date fecha;

	@JsonProperty(value = "invitados")
	private int invitados;

	@JsonProperty(value = "mid")
	private Long mid;

	@JsonProperty(value = "uid")
	private Long uid;

	public ReservaVos(@JsonProperty(value = "id") Long id, @JsonProperty(value = "zid") Long zid,
			@JsonProperty(value = "fecha") Date fecha2, @JsonProperty(value = "invitados") int invitados,
			@JsonProperty(value = "mid") Long mid, @JsonProperty(value = "uid") Long uid) {
		this.id = id;
		this.zid = zid;
		this.fecha = fecha2;
		this.invitados = invitados;
		this.mid = mid;
		this.uid = uid;
	}

	/**
	 * @return the invitados
	 */
	public int getInvitados() {
		return invitados;
	}

	/**
	 * @param invitados
	 *            the invitados to set
	 */
	public void setInvitados(int invitados) {
		this.invitados = invitados;
	}

	/**
	 * @return the mid
	 */
	public Long getMid() {
		return mid;
	}

	/**
	 * @param mid
	 *            the mid to set
	 */
	public void setMid(Long mid) {
		this.mid = mid;
	}

	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	 * @return the zid
	 */
	public Long getZid() {
		return zid;
	}

	/**
	 * @param zid
	 *            the zid to set
	 */
	public void setZid(Long zid) {
		this.zid = zid;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

}
