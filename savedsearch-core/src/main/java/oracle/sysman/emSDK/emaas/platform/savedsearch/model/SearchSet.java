package oracle.sysman.emSDK.emaas.platform.savedsearch.model;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportSearchImpl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


@XmlRootElement(name = "SearchSet")
@XmlAccessorType (XmlAccessType.FIELD)
public class SearchSet
{
    
    private List<ImportSearchImpl> search = null;
 
    
    public List<ImportSearchImpl> getSearchSet() {
    	if(search==null)
    		search = new ArrayList<ImportSearchImpl>();
        return search;
    }
 
    public void setSearchSet(List<ImportSearchImpl> d) {
        this.search = d;
    }
    
       
    
}