package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the EM_ANALYTICS_SEARCH_PARAMS database table.
 */
@Embeddable
public class EmAnalyticsSearchParamPK implements Serializable
{
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "SEARCH_ID", insertable = false, updatable = false, nullable = false)
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
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + (int) (searchId ^ searchId >>> 32);
		hash = hash * prime + name.hashCode();

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
}