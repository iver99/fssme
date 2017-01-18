package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * The primary key class for the EMS_ANALYTICS_CATEGORY_PARAMS database table.
 */

public class EmAnalyticsCategoryParamPK implements Serializable
{
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private BigInteger categoryId;

	private String name;

	private Long tenantId;

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
		return categoryId == castOther.categoryId && name.equals(castOther.name) && tenantId == castOther.tenantId;

	}

	public BigInteger getCategoryId()
	{
		return categoryId;
	}

	public String getName()
	{
		return name;
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
		hash = hash * prime + (int) (categoryId.intValue() ^ categoryId.intValue() >>> 32);
		hash = hash * prime + (name == null ? 0 : name.hashCode());
		hash = hash * prime + (int) (tenantId ^ tenantId >>> 32);
		return hash;

	}

	public void setCategoryId(BigInteger categoryId)
	{
		this.categoryId = categoryId;
	}

	public void setName(String name)
	{
		this.name = name;
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