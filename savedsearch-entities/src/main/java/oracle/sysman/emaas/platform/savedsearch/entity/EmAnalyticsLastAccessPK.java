package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;

/**
 * The primary key class for the EMS_ANALYTICS_LAST_ACCESS database table.
 */

public class EmAnalyticsLastAccessPK implements Serializable
{
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long objectId;

	private String accessedBy;

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
		return super.hashCode();
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