package oracle.sysman.emSDK.emaas.platform.savedsearch.model;


import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
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

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;

@XmlRootElement(name = "FolderSet")
@XmlAccessorType (XmlAccessType.NONE)
@XmlType(propOrder = {})

public class FolderSet
{
    
    private List<FolderImpl> Folder = null;
 
    @XmlElement(name = "Folder")
   // @XmlElementWrapper(name = "FolderSet")    
    public List<FolderImpl> getFolderSet() {
    	if(Folder==null)
    		Folder = new ArrayList<FolderImpl>();
        return Folder;
    }
 
    public void setFolderSet(List<FolderImpl> d) {
        this.Folder = d;
    }
    }