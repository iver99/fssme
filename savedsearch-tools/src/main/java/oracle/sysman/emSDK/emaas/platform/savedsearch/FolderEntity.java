package oracle.sysman.emSDK.emaas.platform.savedsearch;



import java.util.ArrayList;
import java.util.List;

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

    private List<FolderEntity> m_childFolders = new ArrayList<FolderEntity>();
    //private List<SearchEntity> childSearch;
    
    public FolderEntity()
    {
        super();
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public void setCreatdOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public String getLastModifiedOn() {
        return lastModifiedOn;
    }
    
    public void setCategory(Category cat) {
       this.category = cat;    
    }
    
    public Category getCategory() {
        return this.category;    
    }     

    public void setParent(FolderEntity parent) {
        this.parent = parent;
    }

    public FolderEntity getParent() {
        return parent;
    }

    public void setChildFolders(List<FolderEntity> childFolders)
    {
        if (childFolders != null) {
            m_childFolders.clear();
            for (FolderEntity folder : childFolders) {
                if (this.isFolderOnly) {
                    if (folder.isFolder()) {
                        m_childFolders.add(folder);
                        folder.parent = this;
                    }
                } else {
                    if (folder.isFolder() || "1".equals(folder.getCategory().getId())) {
                        m_childFolders.add(folder);
                        folder.parent = this;
                    }
                }
            }
        }
    }

    public List<FolderEntity> getChildFolders()
    {
        return m_childFolders;
    }    

    public void setIsFolderOnly(boolean isFolderOnly) {
        this.isFolderOnly = isFolderOnly;
    }

    public boolean isIsFolderOnly() {
        return isFolderOnly;
    }

    /*
    public void setChildSearch(List<SearchEntity> childSearch)
    {
        this.childSearch = childSearch;
    }

    public List<SearchEntity> getChildSearch()
    {
        return childSearch;
    }
    */
    public void setIsFolder(boolean isFolder) 
    {
        if (isFolder)
            type = TYPE_FOLDER;
        else
            type = TYPE_SEARCH;
    }

    public boolean isFolder()
    {
        return TYPE_FOLDER.equals(type);
    }
    
}
