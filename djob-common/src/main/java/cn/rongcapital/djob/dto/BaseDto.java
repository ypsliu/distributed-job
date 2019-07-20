package cn.rongcapital.djob.dto;

import java.util.Date;

/**
 * @author shangchunming
 *
 */
public abstract class BaseDto {
//	@NotNull
	protected Key key;

	protected Long version;

	protected Date createAt;

	protected Date updateAt;

	/**
	 * @return the key
	 */
	public final Key getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public final void setKey(Key key) {
		this.key = key;
	}

	/**
	 * @return the version
	 */
	public final Long getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public final void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * @return the createAt
	 */
	public final Date getCreateAt() {
		return createAt;
	}

	/**
	 * @param createAt
	 *            the createAt to set
	 */
	public final void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	/**
	 * @return the updateAt
	 */
	public final Date getUpdateAt() {
		return updateAt;
	}

	/**
	 * @param updateAt
	 *            the updateAt to set
	 */
	public final void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

}
