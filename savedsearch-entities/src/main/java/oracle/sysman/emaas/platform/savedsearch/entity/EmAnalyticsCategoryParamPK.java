package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the EMS_ANALYTICS_CATEGORY_PARAMS database table.
 */
@Embeddable
public class EmAnalyticsCategoryParamPK implements Serializable
{
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "CATEGORY_ID", insertable = false, updatable = false, nullable = false)
	private long categoryId;

	private String name;

	public EmAnalyticsCategoryParamPK()
	{
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmAnalyticsCategoryParamPK)) {
			return false;
		}
		EmAnalyticsCategoryParamPK castOther = (EmAnalyticsCategoryParamPK) other;
		return categoryId == castOther.categoryId && name.equals(castOther.name);

	}

	public long getCategoryId()
	{
		return categoryId;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + (int) (categoryId ^ categoryId >>> 32);
		hash = hash * prime + name.hashCode();

		return hash;
	}

	public void setCategoryId(long categoryId)
	{
		this.categoryId = categoryId;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}