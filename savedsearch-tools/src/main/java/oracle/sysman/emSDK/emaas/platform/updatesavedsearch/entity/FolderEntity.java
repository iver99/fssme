package oracle.sysman.emSDK.emaas.platform.updatesavedsearch.entity;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.Category;

public class FolderEntity extends Entity
{
	// IMPORTANT: keep the type constants, fields in this class the same with what's defined in 
	// saved search restful specification for folder: https://confluence.oraclecorp.com/confluence/display/EMS/Saved+Search+Framework+Web+Service+Specifications
	private static final String TYPE_FOLDER = "folder";
	private static final String TYPE_SEARCH = "search";

	private String name;
	private String type;
	private String description;
	private String createdOn;
	private String lastModifiedOn;
	private Category category;
	private FolderEntity parent;
	private boolean isFolderOnly;

	private final List<FolderEntity> m_childFolders = new ArrayList<FolderEntity>();

	public FolderEntity()
	{
		super();
	}

	public Category getCategory()
	{
		return category;
	}

	public List<FolderEntity> getChildFolders()
	{
		return m_childFolders;
	}

	public String getCreatedOn()
	{
		return createdOn;
	}

	public String getDescription()
	{
		return description;
	}

	public String getLastModifiedOn()
	{
		return lastModifiedOn;
	}

	public String getName()
	{
		return name;
	}

	public FolderEntity getParent()
	{
		return parent;
	}

	public String getType()
	{
		return type;
	}

	public boolean isFolder()
	{
		return TYPE_FOLDER.equals(type);
	}

	public boolean isIsFolderOnly()
	{
		return isFolderOnly;
	}

	public void setCategory(Category cat)
	{
		category = cat;
	}

	public void setCreatdOn(String createdOn)
	{
		this.createdOn = createdOn;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setIsFolder(boolean isFolder)
	{
		if (isFolder) {
			type = TYPE_FOLDER;
		}
		else {
			type = TYPE_SEARCH;
		}
	}

	public void setIsFolderOnly(boolean isFolderOnly)
	{
		this.isFolderOnly = isFolderOnly;
	}

	public void setLastModifiedOn(String lastModifiedOn)
	{
		this.lastModifiedOn = lastModifiedOn;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setParent(FolderEntity parent)
	{
		this.parent = parent;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
