package oracle.sysman.emSDK.emaas.platform.updatesavedsearch.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.Category;

public class SearchEntity extends Entity
{

	private Category category;
	private String queryStr;
	private FolderEntity folder;
	private String description;
	private String name;
	private String locked;
	private String uiHidden;
	private String folderId;
	private String categoryId;
	private String metadata;
	private String widget;

	private List<SearchParamEntity> parameters;

	public SearchEntity()
	{
		super();
	}

	@XmlTransient
	public Category getCategory()
	{
		return category;
	}

	@XmlElement(name = "CategoryId")
	public String getCategoryId()
	{
		return categoryId;
	}

	@XmlElement(name = "Description")
	public String getDescription()
	{
		return description;
	}

	@XmlTransient
	public FolderEntity getFolder()
	{
		return folder;
	}

	@XmlElement(name = "FolderId")
	public String getFolderId()
	{
		return folderId;
	}

	@XmlElement(name = "IsWidget")
	public String getIsWidget()

	{

		return widget;

	}

	@XmlElement(name = "Locked")
	public String getLocked()
	{
		return locked;
	}

	@XmlElement(name = "Metadata")
	public String getMetadata()
	{
		return metadata;
	}

	@XmlElement(name = "Name")
	public String getName()
	{
		return name;
	}

	@XmlElement(name = "SearchParameter")
	@XmlElementWrapper(name = "SearchParameters")
	public List<SearchParamEntity> getParameters()
	{
		return parameters;
	}

	@XmlElement(name = "QueryStr")
	public String getQueryStr()
	{
		return queryStr;
	}

	@XmlElement(name = "UiHidden")
	public String getUiHidden()
	{
		return uiHidden;
	}

	public void setCategory(Category category)
	{
		this.category = category;
		setCategoryId(category.getId());
	}

	public void setCategoryId(String categoryId)
	{
		this.categoryId = categoryId;
	}

	/* public void setCreationDate(Date creationDate)
	 {
	     this.creationDate = creationDate;
	 }

	 public Date getCreationDate()
	 {
	     return creationDate;
	 }*/

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setFolder(FolderEntity folder)
	{
		this.folder = folder;
		setFolderId(folder.getId());
	}

	public void setFolderId(String folderId)
	{
		this.folderId = folderId;
	}

	public void setIsWidget(String value)
	{
		widget = value;
	}

	/* public void setLastModifiedBy(String lastModifiedBy)
	 {
	     this.lastModifiedBy = lastModifiedBy;
	 }

	 public String getLastModifiedBy()
	 {
	     return lastModifiedBy;
	 }

	 public void setOwner(String owner)
	 {
	     this.owner = owner;
	 }

	 public String getOwner()
	 {
	     return owner;
	 }*/

	public void setLocked(String locked)
	{
		this.locked = locked;
	}

	public void setMetadata(String metadata)
	{
		this.metadata = metadata;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setParameters(List<SearchParamEntity> parameters)
	{
		this.parameters = parameters;
	}

	public void setQueryStr(String queryStr)
	{
		this.queryStr = queryStr;
	}

	public void setUiHidden(String uiHidden)
	{
		this.uiHidden = uiHidden;
	}
}
