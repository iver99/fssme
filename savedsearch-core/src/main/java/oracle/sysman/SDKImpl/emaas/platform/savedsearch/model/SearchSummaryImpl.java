package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchSummary;

@XmlTransient
public class SearchSummaryImpl implements SearchSummary, Serializable
{
	private static final long serialVersionUID = 1578561229141793173L;

	protected String guid;
	protected Integer id;
	protected String name;

	protected String description;

	protected Integer categoryId;
	protected Integer folderId;

	protected String owner;
	protected Date createdOn;
	protected String lastModifiedBy;
	protected Date lastModifiedOn;
	protected Date lastAccessDate;
	protected boolean systemSearch;
	protected String[] tags;

	@Override
	public Integer getCategoryId()
	{
		return categoryId;
	}

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
	public Integer getFolderId()
	{
		return folderId;
	}

	public String getGuid()
	{
		return guid;
	}

	@Override
	public Integer getId()
	{
		return id;
	}

	@Override
	public Date getLastAccessDate()
	{
		return lastAccessDate;
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
	@XmlTransient
	public String[] getTags()
	{
		return tags;
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchSummary#isSystemSearch()
	 */
	@Override
	public boolean isSystemSearch()
	{
		return systemSearch;
	}

	public void setCategoryId(Integer categoryId)
	{
		this.categoryId = categoryId;
	}

	public void setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setFolderId(Integer folderId)
	{
		this.folderId = folderId;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public void setLastAccessDate(Date lastAccessDate)
	{
		this.lastAccessDate = lastAccessDate;
	}

	public void setLastModifiedBy(String lastModifiedBy)
	{
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setLastModifiedOn(Date lastModifiedOn)
	{
		this.lastModifiedOn = lastModifiedOn;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public void setSystemSearch(boolean value)
	{
		systemSearch = value;
	}

	public void setTags(String[] tags)
	{
		this.tags = tags;
	}

}
