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
public class SavedSearchFolderRowEntity implements RowEntity
{
	@JsonProperty("FOLDER_ID")
	private BigInteger folderId;

	@JsonProperty("DESCRIPTION")
	private String description;

	@JsonProperty("DESCRIPTION_NLSID")
	private String descriptionNlsid;

	@JsonProperty("DESCRIPTION_SUBSYSTEM")
	private String descriptionSubsystem;

	@JsonProperty("EM_PLUGIN_ID")
	private String emPluginId;

	@JsonProperty("LAST_MODIFIED_BY")
	private String lastModifiedBy;

	@JsonProperty("LAST_MODIFICATION_DATE")
	private String lastModificationDate;

	@JsonProperty("NAME")
	private String name;

	@JsonProperty("NAME_NLSID")
	private String nameNlsid;

	@JsonProperty("NAME_SUBSYSTEM")
	private String nameSubsystem;

	@JsonProperty("OWNER")
	private String owner;

	@JsonProperty("SYSTEM_FOLDER")
	private Integer systemFolder;

	@JsonProperty("UI_HIDDEN")
	private Integer uiHidden;

	@JsonProperty("DELETED")
	private BigInteger deleted;

	@JsonProperty("TENANT_ID")
	private Long tenantId;

	@JsonProperty("PARENT_ID")
	private BigInteger parentId;

	@JsonProperty("CREATION_DATE")
	private String creationDate;

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
		SavedSearchFolderRowEntity other = (SavedSearchFolderRowEntity) obj;
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
		if (folderId == null) {
			if (other.folderId != null) {
				return false;
			}
		}
		else if (!folderId.equals(other.folderId)) {
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
		if (lastModifiedBy == null) {
			if (other.lastModifiedBy != null) {
				return false;
			}
		}
		else if (!lastModifiedBy.equals(other.lastModifiedBy)) {
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
		if (parentId == null) {
			if (other.parentId != null) {
				return false;
			}
		}
		else if (!parentId.equals(other.parentId)) {
			return false;
		}
		if (systemFolder == null) {
			if (other.systemFolder != null) {
				return false;
			}
		}
		else if (!systemFolder.equals(other.systemFolder)) {
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
		if (uiHidden == null) {
			if (other.uiHidden != null) {
				return false;
			}
		}
		else if (!uiHidden.equals(other.uiHidden)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the creationDate
	 */
	public String getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @return the deleted
	 */
	public BigInteger getDeleted()
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
	 * @return the folderId
	 */
	public BigInteger getFolderId()
	{
		return folderId;
	}

	/**
	 * @return the lastModificationDate
	 */
	public String getLastModificationDate()
	{
		return lastModificationDate;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy()
	{
		return lastModifiedBy;
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
	 * @return the parentId
	 */
	public BigInteger getParentId()
	{
		return parentId;
	}

	/**
	 * @return the systemFolder
	 */
	public Integer getSystemFolder()
	{
		return systemFolder;
	}

	/**
	 * @return the tenantId
	 */
	public Long getTenantId()
	{
		return tenantId;
	}

	/**
	 * @return the uiHidden
	 */
	public Integer getUiHidden()
	{
		return uiHidden;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (deleted == null ? 0 : deleted.hashCode());
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (descriptionNlsid == null ? 0 : descriptionNlsid.hashCode());
		result = prime * result + (descriptionSubsystem == null ? 0 : descriptionSubsystem.hashCode());
		result = prime * result + (emPluginId == null ? 0 : emPluginId.hashCode());
		result = prime * result + (folderId == null ? 0 : folderId.hashCode());
		result = prime * result + (lastModificationDate == null ? 0 : lastModificationDate.hashCode());
		result = prime * result + (lastModifiedBy == null ? 0 : lastModifiedBy.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (nameNlsid == null ? 0 : nameNlsid.hashCode());
		result = prime * result + (nameSubsystem == null ? 0 : nameSubsystem.hashCode());
		result = prime * result + (owner == null ? 0 : owner.hashCode());
		result = prime * result + (parentId == null ? 0 : parentId.hashCode());
		result = prime * result + (systemFolder == null ? 0 : systemFolder.hashCode());
		result = prime * result + (tenantId == null ? 0 : tenantId.hashCode());
		result = prime * result + (uiHidden == null ? 0 : uiHidden.hashCode());
		return result;
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
	 * @param deleted
	 *            the deleted to set
	 */
	public void setDeleted(BigInteger deleted)
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
	 * @param folderId
	 *            the folderId to set
	 */
	public void setFolderId(BigInteger folderId)
	{
		this.folderId = folderId;
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
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy)
	{
		this.lastModifiedBy = lastModifiedBy;
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
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(BigInteger parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * @param systemFolder
	 *            the systemFolder to set
	 */
	public void setSystemFolder(Integer systemFolder)
	{
		this.systemFolder = systemFolder;
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
	 * @param uiHidden
	 *            the uiHidden to set
	 */
	public void setUiHidden(Integer uiHidden)
	{
		this.uiHidden = uiHidden;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "SavedSearchFolderRowEntity [folderId=" + folderId + ", description=" + description + ", descriptionNlsid="
				+ descriptionNlsid + ", descriptionSubsystem=" + descriptionSubsystem + ", emPluginId=" + emPluginId
				+ ", lastModifiedBy=" + lastModifiedBy + ", lastModificationDate=" + lastModificationDate + ", name=" + name
				+ ", nameNlsid=" + nameNlsid + ", nameSubsystem=" + nameSubsystem + ", owner=" + owner + ", systemFolder="
				+ systemFolder + ", uiHidden=" + uiHidden + ", deleted=" + deleted + ", tenantId=" + tenantId + ", parentId="
				+ parentId + ", creationDate=" + creationDate + "]";
	}

}
