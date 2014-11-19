package oracle.sysman.emSDK.emaas.platform.savedsearch.model;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportSearchImpl;


@XmlRootElement(name = "SearchSet")
@XmlAccessorType (XmlAccessType.FIELD)
public class SearchSet
{
    
    private List<ImportSearchImpl> search = null;
 
    @XmlElement(name="Search")
    public List<ImportSearchImpl> getSearchSet() {
    	if(search==null)
    		search = new ArrayList<ImportSearchImpl>();
        return search;
    }
 
    public void setSearchSet(List<ImportSearchImpl> d) {
        this.search = d;
    }
    
       
    
}