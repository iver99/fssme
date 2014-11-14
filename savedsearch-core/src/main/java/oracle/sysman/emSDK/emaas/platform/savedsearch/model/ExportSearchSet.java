package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;


@XmlRootElement(name = "SearchSet")
@XmlAccessorType (XmlAccessType.FIELD)
public class ExportSearchSet
{
    
    private List<SearchImpl> search = null;
 
        
    /*@XmlElement(name="Search")
    public List<SearchImpl> getSearchSet() {
    	if(search==null)
    		search = new ArrayList<SearchImpl>();
        return search;
    }*/
 
    @XmlElement(name="Search")
    public void setSearchSet(List<SearchImpl> d) {
        this.search = d;
    }
}