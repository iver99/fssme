package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchSummary;

@XmlTransient
public class SearchSummaryImpl implements SearchSummary
{
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
	@Override
	public Date getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

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
	public Date getLastModifiedOn()
	{
		return lastModifiedOn;
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
	@XmlTransient
	public String[] getTags()
	{
		return tags;
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

	public void setLastModifiedOn(Date lastModifiedOn)
	{
		this.lastModifiedOn = lastModifiedOn;
	}

	public void setLastModifiedBy(String lastModifiedBy)
	{
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public void setTags(String[] tags)
	{
		this.tags = tags;
	}

}
