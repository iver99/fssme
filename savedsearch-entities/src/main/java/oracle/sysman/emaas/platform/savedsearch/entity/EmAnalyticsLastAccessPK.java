package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the EMS_ANALYTICS_LAST_ACCESS database table.
 */
@Embeddable
public class EmAnalyticsLastAccessPK implements Serializable
{
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "OBJECT_ID")
	private long objectId;

	@Column(name = "ACCESSED_BY")
	private String accessedBy;

	@Column(name = "OBJECT_TYPE")
	private long objectType;

	public EmAnalyticsLastAccessPK()
	{
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmAnalyticsLastAccessPK)) {
			return false;
		}
		EmAnalyticsLastAccessPK castOther = (EmAnalyticsLastAccessPK) other;
		return objectId == castOther.objectId && accessedBy.equals(castOther.accessedBy) && objectType == castOther.objectType;
	}

	public String getAccessedBy()
	{
		return accessedBy;
	}

	public long getObjectId()
	{
		return objectId;
	}

	public long getObjectType()
	{
		return objectType;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + (int) (objectId ^ objectId >>> 32);
		hash = hash * prime + accessedBy.hashCode();
		hash = hash * prime + (int) (objectType ^ objectType >>> 32);

		return hash;
	}

	public void setAccessedBy(String accessedBy)
	{
		this.accessedBy = accessedBy;
	}

	public void setObjectId(long objectId)
	{
		this.objectId = objectId;
	}

	public void setObjectType(long objectType)
	{
		this.objectType = objectType;
	}
}