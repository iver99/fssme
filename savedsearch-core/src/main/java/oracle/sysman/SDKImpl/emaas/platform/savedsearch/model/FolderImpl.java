package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

/* $Header: emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/sdkImpl/core/emanalytics/impl/FolderImpl.java /st_emgc_pt-13.1mstr/2 2014/02/03 02:51:00 saurgarg Exp $ */

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;

@XmlRootElement
@XmlType(propOrder = { "id", "name", "description", "parentId", "creationDate", "lastModificationDate", "owner",
		"lastModifiedBy", "systemFolder", "uiHidden" })
public class FolderImpl implements Folder
{
	protected Integer id;
	protected String name;

	protected Integer parentId;
	protected String description;

	protected Date creationDate;
	protected Date lastModificationDate;
	protected String owner;
	protected String lastModifiedBy;
	protected boolean systemFolder;
	protected boolean uiHidden;

	//protected String emPluginId;

	@Override
	public Date getCreationDate()
	{
		return creationDate;
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
	public Date getLastModificationDate()
	{
		return lastModificationDate;
	}

	@Override
	public String getLastModifiedBy()
	{
		return lastModifiedBy;
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
	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
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
	 * @param lastModificationDate
	 *            the lastModificationDate to set
	 */
	public void setLastModificationDate(Date lastModificationDate)
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
