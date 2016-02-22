package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.io.Serializable;

/* $Header: emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/sdkImpl/core/emanalytics/impl/FolderImpl.java /st_emgc_pt-13.1mstr/2 2014/02/03 02:51:00 saurgarg Exp $ */

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;

@XmlRootElement
@XmlType(propOrder = { "id", "name", "description", "parentId", "createdOn", "owner", "lastModifiedOn", "systemFolder",
		"uiHidden", "lastModifiedBy" })
public class FolderImpl implements Folder, Serializable
{
	private static final long serialVersionUID = 8581284618417733855L;

	protected Integer id;
	protected String name;
	protected String description;
	protected String owner;

	protected Date createdOn;
	protected Date lastModifiedOn;
	protected String lastModifiedBy;
	protected boolean systemFolder;
	protected Integer parentId;

	protected boolean uiHidden;

	@Override
	public Date getCreatedOn()
	{
		return createdOn;
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	@Override
	public Integer getId()
	{
		return id;
	}

	@Override
	public String getLastModifiedBy()
	{
		return lastModifiedBy;
	}

	@Override
	public Date getLastModifiedOn()
	{
		return lastModifiedOn;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public String getOwner()
	{
		return owner;
	}

	@Override
	public Integer getParentId()
	{
		return parentId;
	}

	@Override
	public boolean isSystemFolder()
	{
		return systemFolder;
	}

	@Override
	public boolean isUiHidden()
	{
		return uiHidden;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
	}

	@Override
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
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
	 * @param lastModificationDate
	 *            the lastModificationDate to set
	 */
	public void setLastModifiedOn(Date lastModifiedOn)
	{
		this.lastModifiedOn = lastModifiedOn;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	@Override
	public void setParentId(Integer parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * @param systemFolder
	 *            the systemFolder to set
	 */

	public void setSystemFolder(boolean systemFolder)
	{
		this.systemFolder = systemFolder;
	}

	@Override
	public void setUiHidden(boolean uiHidden)
	{
		this.uiHidden = uiHidden;
	}

}
