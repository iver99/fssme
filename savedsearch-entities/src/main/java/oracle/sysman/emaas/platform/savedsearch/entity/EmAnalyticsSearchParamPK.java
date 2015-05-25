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
		return searchId == castOther.searchId && name.equals(castOther.name);

	}

	public String getName()
	{
		return name;
	}

	public long getSearchId()
	{
		return searchId;
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setSearchId(long searchId)
	{
		this.searchId = searchId;
	}
}