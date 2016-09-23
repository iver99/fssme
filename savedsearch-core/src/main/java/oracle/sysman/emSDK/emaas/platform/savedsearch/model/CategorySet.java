package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportCategoryImpl;

@XmlRootElement(name = "CategorySet")
@XmlAccessorType (XmlAccessType.FIELD)
public class CategorySet 
{   
    private List<ImportCategoryImpl> Category = null;
 
    public List<ImportCategoryImpl> getCategorySet() {
    	if(Category==null) {
            Category = new ArrayList<ImportCategoryImpl>();
        }
        return Category;
    }
 
    public void setCategorySet(List<ImportCategoryImpl> category) {
        this.Category = category;
    }
    
   }
