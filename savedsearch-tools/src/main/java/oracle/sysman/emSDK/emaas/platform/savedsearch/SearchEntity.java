package oracle.sysman.emSDK.emaas.platform.savedsearch;

import java.net.URLEncoder;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;


public class SearchEntity extends Entity
{
//    private static final ADFLogger LOGGER =  ADFLogger.createADFLogger(SearchEntity.class);
    
    // IMPORTANT: keep the constans, fields in this class the same with what's defined in 
    // saved search restful specification for search: https://confluence.oraclecorp.com/confluence/display/EMS/Saved+Search+Framework+Web+Service+Specifications   
    private static final String PARAM_TYPE_STRING = "STRING";
    private static final String PARAM_TYPE_CLOB   = "CLOB";    
    private  Category category;    
    private String queryStr;
    private   FolderEntity folder;    
    private String description;
    private String name;       
    private String locked;    
    private String uiHidden;
    private String folderId;
    private String categoryId;
    private String metadata;
    
    @XmlElement(name = "Metadata")
    public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	@XmlElement(name = "CategoryId")
    public String getCategoryId() {
		return categoryId;
	}
    
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@XmlElement(name = "FolderId")
    public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}


	private List<SearchParamEntity> parameters;
    private static final String TIME_RANGE = "time_range";
    private static final String HISTOGRAM_GROUPBY = "HISTOGRAM_GROUPBY";
    
    public SearchEntity()
    {
        super();
    }

    public void setCategory(Category category)
    {
        this.category = category;
        setCategoryId(category.getId());
    }

    @XmlTransient
    public Category getCategory()
    {
        return category;
    }

   
    public void setQueryStr(String queryStr)
    {
        this.queryStr = queryStr;
    }

    @XmlElement(name = "QueryStr")
    public String getQueryStr()
    {
        return queryStr;
    }

    public void setFolder(FolderEntity folder)
    {
        this.folder = folder;
        setFolderId(folder.getId());
    }

    @XmlTransient
    public FolderEntity getFolder()
    {
        return this.folder;
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

    @XmlElement(name = "Description")
    public String getDescription()
    {
        return description;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @XmlElement(name = "Name")
    public String getName()
    {
        return name;
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

    @XmlElement(name = "Locked")
    public String getLocked()
    {
        return locked;
    }
   
   
    public void setUiHidden(String uiHidden)
    {
        this.uiHidden = uiHidden;
    }

    @XmlElement(name = "UiHidden")
    public String getUiHidden()
    {
        return uiHidden;
    }

    public void setParameters(List<SearchParamEntity> parameters)
    {
        this.parameters = parameters;
    }

    
    @XmlElement(name = "SearchParameter")
	@XmlElementWrapper(name = "SearchParameters")
    public List<SearchParamEntity> getParameters()
    {
        return parameters;
    }

    public String getHistogramGraphGroupBy() {
        if (parameters != null && parameters.size() > 0) {
            for (SearchParamEntity param : parameters) {
                String paramName = param.getName();
                if (HISTOGRAM_GROUPBY.equals(paramName)) {
                    return param.getValue();
                }
            }
        }
        return null;
    }
    
    public String getEncodedURLParam() {
        if (parameters != null && parameters.size() > 0) {
            for (SearchParamEntity param : parameters) {
                String paramName = param.getName();
                if (TIME_RANGE.equals(paramName)) {
                    String paramValue = param.getValue();
                    
                    String urlParam = "queryStr="  + URLEncoder.encode(queryStr == null? "*" : queryStr) + "&" + 
                                      "timeRange=" + paramValue;
                    
                    return urlParam;
                }
            }
        }
        
        return null;
    }

    
    public String getTimeRangeStr() {
        if (parameters != null && parameters.size() > 0) {
            for (SearchParamEntity param : parameters) {
                String paramName = param.getName();
                if (TIME_RANGE.equals(paramName)) {
                    return param.getValue();
                }
            }
        }
        
        return null;
    }
}
