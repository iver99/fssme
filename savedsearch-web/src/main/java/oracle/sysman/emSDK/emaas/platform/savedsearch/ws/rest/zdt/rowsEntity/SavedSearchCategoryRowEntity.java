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
public class SavedSearchCategoryRowEntity implements RowEntity
{
	@JsonProperty("CATEGORY_ID")
	private String categoryId;

	@JsonProperty("DESCRIPTION")
	private String description;

	@JsonProperty("DESCRIPTION_NLSID")
	private String descriptionNlsid;

	@JsonProperty("DESCRIPTION_SUBSYSTEM")
	private String descriptionSubsystem;

	@JsonProperty("EM_PLUGIN_ID")
	private String emPluginId;

	@JsonProperty("NAME")
	private String name;

	@JsonProperty("NAME_NLSID")
	private String nameNlsid;

	@JsonProperty("NAME_SUBSYSTEM")
	private String nameSubsystem;

	@JsonProperty("OWNER")
	private String owner;

	@JsonProperty("DELETED")
	private String deleted;

	@JsonProperty("PROVIDER_NAME")
	private String providerName;

	@JsonProperty("PROVIDER_VERSION")
	private String providerVersion;

	@JsonProperty("PROVIDER_DISCOVERY")
	private String providerDiscovery;

	@JsonProperty("PROVIDER_ASSET_ROOT")
	private String providerAssetRoot;

	@JsonProperty("LAST_MODIFICATION_DATE")
	private String lastModificationDate;

	@JsonProperty("TENANT_ID")
	private Long tenantId;

	@JsonProperty("CREATION_DATE")
	private String creationDate;

	@JsonProperty("DEFAULT_FOLDER_ID")
	private String defaultFolderId;

	@JsonProperty("DASHBOARD_INELIGIBLE")
	private String dashboardIneligible;
	
	

	public SavedSearchCategoryRowEntity() {
		super();
	}

	public SavedSearchCategoryRowEntity(String categoryId,
			String description, String descriptionNlsid,
			String descriptionSubsystem, String emPluginId, String name,
			String nameNlsid, String nameSubsystem, String owner,
			String deleted, String providerName, String providerVersion,
			String providerDiscovery, String providerAssetRoot,
			String lastModificationDate, Long tenantId, String creationDate,
			String defaultFolderId, String dashboardIneligible) {
		super();
		this.categoryId = categoryId;
		this.description = description;
		this.descriptionNlsid = descriptionNlsid;
		this.descriptionSubsystem = descriptionSubsystem;
		this.emPluginId = emPluginId;
		this.name = name;
		this.nameNlsid = nameNlsid;
		this.nameSubsystem = nameSubsystem;
		this.owner = owner;
		this.deleted = deleted;
		this.providerName = providerName;
		this.providerVersion = providerVersion;
		this.providerDiscovery = providerDiscovery;
		this.providerAssetRoot = providerAssetRoot;
		this.lastModificationDate = lastModificationDate;
		this.tenantId = tenantId;
		this.creationDate = creationDate;
		this.defaultFolderId = defaultFolderId;
		this.dashboardIneligible = dashboardIneligible;
	}

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
		SavedSearchCategoryRowEntity other = (SavedSearchCategoryRowEntity) obj;
		if (categoryId == null) {
			if (other.categoryId != null) {
				return false;
			}
		}
		else if (!categoryId.equals(other.categoryId)) {
			return false;
		}
		if (deleted == null) {
			if (other.deleted != null) {
				return false;
			}
		}
		else if (!deleted.equals(other.deleted)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		}
		else if (!description.equals(other.description)) {
			return false;
		}
		if (descriptionNlsid == null) {
			if (other.descriptionNlsid != null) {
				return false;
			}
		}
		else if (!descriptionNlsid.equals(other.descriptionNlsid)) {
			return false;
		}
		if (descriptionSubsystem == null) {
			if (other.descriptionSubsystem != null) {
				return false;
			}
		}
		else if (!descriptionSubsystem.equals(other.descriptionSubsystem)) {
			return false;
		}
		if (emPluginId == null) {
			if (other.emPluginId != null) {
				return false;
			}
		}
		else if (!emPluginId.equals(other.emPluginId)) {
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
		if (nameNlsid == null) {
			if (other.nameNlsid != null) {
				return false;
			}
		}
		else if (!nameNlsid.equals(other.nameNlsid)) {
			return false;
		}
		if (nameSubsystem == null) {
			if (other.nameSubsystem != null) {
				return false;
			}
		}
		else if (!nameSubsystem.equals(other.nameSubsystem)) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		}
		else if (!owner.equals(other.owner)) {
			return false;
		}
		if (providerAssetRoot == null) {
			if (other.providerAssetRoot != null) {
				return false;
			}
		}
		else if (!providerAssetRoot.equals(other.providerAssetRoot)) {
			return false;
		}
		if (providerDiscovery == null) {
			if (other.providerDiscovery != null) {
				return false;
			}
		}
		else if (!providerDiscovery.equals(other.providerDiscovery)) {
			return false;
		}
		if (providerName == null) {
			if (other.providerName != null) {
				return false;
			}
		}
		else if (!providerName.equals(other.providerName)) {
			return false;
		}
		if (providerVersion == null) {
			if (other.providerVersion != null) {
				return false;
			}
		}
		else if (!providerVersion.equals(other.providerVersion)) {
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
		return true;
	}

	/**
	 * @return the categoryId
	 */
	public String getCategoryId()
	{
		return categoryId;
	}

	/**
	 * @return the creationDate
	 */
	public String getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @return the dashboardIneligible
	 */
	public String getDashboardIneligible()
	{
		return dashboardIneligible;
	}

	/**
	 * @return the defaultFolderId
	 */
	public String getDefaultFolderId()
	{
		return defaultFolderId;
	}

	/**
	 * @return the deleted
	 */
	public String getDeleted()
	{
		return deleted;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return the descriptionNlsid
	 */
	public String getDescriptionNlsid()
	{
		return descriptionNlsid;
	}

	/**
	 * @return the descriptionSubsystem
	 */
	public String getDescriptionSubsystem()
	{
		return descriptionSubsystem;
	}

	/**
	 * @return the emPluginId
	 */
	public String getEmPluginId()
	{
		return emPluginId;
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
	 * @return the nameNlsid
	 */
	public String getNameNlsid()
	{
		return nameNlsid;
	}

	/**
	 * @return the nameSubsystem
	 */
	public String getNameSubsystem()
	{
		return nameSubsystem;
	}

	/**
	 * @return the owner
	 */
	public String getOwner()
	{
		return owner;
	}

	/**
	 * @return the providerAssetRoot
	 */
	public String getProviderAssetRoot()
	{
		return providerAssetRoot;
	}

	/**
	 * @return the providerDiscovery
	 */
	public String getProviderDiscovery()
	{
		return providerDiscovery;
	}

	/**
	 * @return the providerName
	 */
	public String getProviderName()
	{
		return providerName;
	}

	/**
	 * @return the providerVersion
	 */
	public String getProviderVersion()
	{
		return providerVersion;
	}

	/**
	 * @return the tenantId
	 */
	public Long getTenantId()
	{
		return tenantId;
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
		result = prime * result + (deleted == null ? 0 : deleted.hashCode());
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (descriptionNlsid == null ? 0 : descriptionNlsid.hashCode());
		result = prime * result + (descriptionSubsystem == null ? 0 : descriptionSubsystem.hashCode());
		result = prime * result + (emPluginId == null ? 0 : emPluginId.hashCode());
		result = prime * result + (lastModificationDate == null ? 0 : lastModificationDate.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (nameNlsid == null ? 0 : nameNlsid.hashCode());
		result = prime * result + (nameSubsystem == null ? 0 : nameSubsystem.hashCode());
		result = prime * result + (owner == null ? 0 : owner.hashCode());
		result = prime * result + (providerAssetRoot == null ? 0 : providerAssetRoot.hashCode());
		result = prime * result + (providerDiscovery == null ? 0 : providerDiscovery.hashCode());
		result = prime * result + (providerName == null ? 0 : providerName.hashCode());
		result = prime * result + (providerVersion == null ? 0 : providerVersion.hashCode());
		result = prime * result + (tenantId == null ? 0 : tenantId.hashCode());
		return result;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(String categoryId)
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
	 * @param dashboardIneligible
	 *            the dashboardIneligible to set
	 */
	public void setDashboardIneligible(String dashboardIneligible)
	{
		this.dashboardIneligible = dashboardIneligible;
	}

	/**
	 * @param defaultFolderId
	 *            the defaultFolderId to set
	 */
	public void setDefaultFolderId(String defaultFolderId)
	{
		this.defaultFolderId = defaultFolderId;
	}

	/**
	 * @param deleted
	 *            the deleted to set
	 */
	public void setDeleted(String deleted)
	{
		this.deleted = deleted;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @param descriptionNlsid
	 *            the descriptionNlsid to set
	 */
	public void setDescriptionNlsid(String descriptionNlsid)
	{
		this.descriptionNlsid = descriptionNlsid;
	}

	/**
	 * @param descriptionSubsystem
	 *            the descriptionSubsystem to set
	 */
	public void setDescriptionSubsystem(String descriptionSubsystem)
	{
		this.descriptionSubsystem = descriptionSubsystem;
	}

	/**
	 * @param emPluginId
	 *            the emPluginId to set
	 */
	public void setEmPluginId(String emPluginId)
	{
		this.emPluginId = emPluginId;
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
	 * @param nameNlsid
	 *            the nameNlsid to set
	 */
	public void setNameNlsid(String nameNlsid)
	{
		this.nameNlsid = nameNlsid;
	}

	/**
	 * @param nameSubsystem
	 *            the nameSubsystem to set
	 */
	public void setNameSubsystem(String nameSubsystem)
	{
		this.nameSubsystem = nameSubsystem;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	/**
	 * @param providerAssetRoot
	 *            the providerAssetRoot to set
	 */
	public void setProviderAssetRoot(String providerAssetRoot)
	{
		this.providerAssetRoot = providerAssetRoot;
	}

	/**
	 * @param providerDiscovery
	 *            the providerDiscovery to set
	 */
	public void setProviderDiscovery(String providerDiscovery)
	{
		this.providerDiscovery = providerDiscovery;
	}

	/**
	 * @param providerName
	 *            the providerName to set
	 */
	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	/**
	 * @param providerVersion
	 *            the providerVersion to set
	 */
	public void setProviderVersion(String providerVersion)
	{
		this.providerVersion = providerVersion;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(Long tenantId)
	{
		this.tenantId = tenantId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "SavedSearchCategoryRowEntity [categoryId=" + categoryId + ", description=" + description + ", descriptionNlsid="
				+ descriptionNlsid + ", descriptionSubsystem=" + descriptionSubsystem + ", emPluginId=" + emPluginId + ", name="
				+ name + ", nameNlsid=" + nameNlsid + ", nameSubsystem=" + nameSubsystem + ", owner=" + owner + ", deleted="
				+ deleted + ", providerName=" + providerName + ", providerVersion=" + providerVersion + ", providerDiscovery="
				+ providerDiscovery + ", providerAssetRoot=" + providerAssetRoot + ", lastModificationDate="
				+ lastModificationDate + ", tenantId=" + tenantId + ", creationDate=" + creationDate + ", defaultFolderId="
				+ defaultFolderId + ", dashboardIneligible=" + dashboardIneligible + "]";
	}

}
