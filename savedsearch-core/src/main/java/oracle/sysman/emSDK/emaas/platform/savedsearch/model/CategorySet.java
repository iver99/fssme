package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportCategoryImpl;

@XmlRootElement(name = "CategorySet")
@XmlAccessorType (XmlAccessType.FIELD)
public class CategorySet 
{   
    private List<ImportCategoryImpl> Category = null;
 
    public List<ImportCategoryImpl> getCategorySet() {
    	if(Category==null)
    		Category = new ArrayList<ImportCategoryImpl>();
        return Category;
    }
 
    public void setCategorySet(List<ImportCategoryImpl> category) {
        this.Category = category;
    }
    
   }
