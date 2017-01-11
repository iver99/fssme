/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity;

import java.math.BigInteger;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author pingwu
 */
public class SavedSearchCategoryParamRowEntity implements RowEntity
{
	@JsonProperty("CATEGORY_ID")
	private BigInteger categoryId;

	@JsonProperty("NAME")
	private String name;

	@JsonProperty("PARAM_VALUE")
	private String value;

	@JsonProperty("TENANT_ID")
	private Long tenantId;

	@JsonProperty("CREATION_DATE")
	private String creationDate;

	@JsonProperty("LAST_MODIFICATION_DATE")
	private String lastModificationDate;

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SavedSearchCategoryParamRowEntity other = (SavedSearchCategoryParamRowEntity) obj;
		if (categoryId == null) {
			if (other.categoryId != null) {
				return false;
			}
		}
		else if (!categoryId.equals(other.categoryId)) {
			return false;
		}
		if (creationDate == null) {
			if (other.creationDate != null) {
				return false;
			}
		}
		else if (!creationDate.equals(other.creationDate)) {
			return false;
		}
		if (lastModificationDate == null) {
			if (other.lastModificationDate != null) {
				return false;
			}
		}
		else if (!lastModificationDate.equals(other.lastModificationDate)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!name.equals(other.name)) {
			return false;
		}
		if (tenantId == null) {
			if (other.tenantId != null) {
				return false;
			}
		}
		else if (!tenantId.equals(other.tenantId)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		}
		else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the categoryId
	 */
	public BigInteger getCategoryId()
	{
		return categoryId;
	}

	public SavedSearchCategoryParamRowEntity(BigInteger categoryId,
			String name, String value, Long tenantId, String creationDate,
			String lastModificationDate) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.value = value;
		this.tenantId = tenantId;
		this.creationDate = creationDate;
		this.lastModificationDate = lastModificationDate;
	}

	/**
	 * @return the creationDate
	 */
	public String getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @return the lastModificationDate
	 */
	public String getLastModificationDate()
	{
		return lastModificationDate;
	}

	/**
	 * @return the name
	 */
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

	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (categoryId == null ? 0 : categoryId.hashCode());
		result = prime * result + (creationDate == null ? 0 : creationDate.hashCode());
		result = prime * result + (lastModificationDate == null ? 0 : lastModificationDate.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (tenantId == null ? 0 : tenantId.hashCode());
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(BigInteger categoryId)
	{
		this.categoryId = categoryId;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(String creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @param lastModificationDate
	 *            the lastModificationDate to set
	 */
	public void setLastModificationDate(String lastModificationDate)
	{
		this.lastModificationDate = lastModificationDate;
	}

	/**
	 * @param name
	 *            the name to set
	 */
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

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "SavedSearchCategoryParamRowEntity [categoryId=" + categoryId + ", name=" + name + ", value=" + value
				+ ", tenantId=" + tenantId + ", creationDate=" + creationDate + ", lastModificationDate=" + lastModificationDate
				+ "]";
	}

}
