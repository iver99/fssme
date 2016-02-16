package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;

/**
 * The primary key class for the EMS_ANALYTICS_SEARCH_PARAMS database table.
 */

public class EmAnalyticsSearchParamPK implements Serializable
{
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long searchId;

	private String name;

	private Long tenantId;

	public EmAnalyticsSearchParamPK()
	{
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmAnalyticsSearchParamPK)) {
			return false;
		}
		EmAnalyticsSearchParamPK castOther = (EmAnalyticsSearchParamPK) other;
		return searchId == castOther.searchId && name.equals(castOther.name) && tenantId == castOther.tenantId;

	}

	public String getName()
	{
		return name;
	}

	public long getSearchId()
	{
		return searchId;
	}

	/**
	 * @return the tenantId
	 */
	public Long getTenantId()
	{
		return tenantId;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + (int) (searchId ^ searchId >>> 32);
		hash = hash * prime + (name == null ? 0 : name.hashCode());
		hash = hash * prime + (int) (tenantId ^ tenantId >>> 32);
		return hash;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setSearchId(long searchId)
	{
		this.searchId = searchId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(Long tenantId)
	{
		this.tenantId = tenantId;
	}
}