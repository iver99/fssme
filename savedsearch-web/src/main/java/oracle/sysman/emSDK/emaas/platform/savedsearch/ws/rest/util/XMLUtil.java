package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLUtil {

	public static Object XMLtoObject(String xmlString, Class classname )
	{
		Object ob=null;
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(classname);
            Unmarshaller un = context.createUnmarshaller();
	        ob = un.unmarshal((Node) new InputSource( new StringReader( xmlString )));
			} catch (JAXBException e) {
				
				e.printStackTrace();
			}
		 return ob ; 
	}
	
	public static String ObjectToXML(Object object)
	{		
		OutputStream os = new ByteArrayOutputStream();
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
           
            //for pretty-print XML in JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(object,new OutputStreamWriter(os, "UTF-8"));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return os.toString() ; 
	}
	
	
	public  static String changeTagName(String data, Map<String,String> map)throws Exception {
		DocumentBuilder documentBuilder= getDocumentBuilder();
		InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
		Document doc =documentBuilder.parse(stream);
		for(String key : map.keySet()){			   
	    NodeList nodes = doc.getElementsByTagName(key);
	    for (int i = 0; i < nodes.getLength(); i++) {
	        if (nodes.item(i) instanceof Element) {
	            Element elem = (Element)nodes.item(i);
	            doc.renameNode(elem, elem.getNamespaceURI(), map.get(key));
	        }
	    }	
		}
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "no");
	    StringWriter writer = new StringWriter();
	    transformer.transform(new DOMSource(doc), new StreamResult(writer));
	    String output = writer.getBuffer().toString();
	    return output;
	}
	
	public static String removeElement(String data , String fromTag,String elementName[]) throws Exception {
		DocumentBuilder documentBuilder= getDocumentBuilder();
		InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
		Document doc =documentBuilder.parse(stream);
		int iCount=doc.getElementsByTagName(fromTag).getLength();
		for (int index = 0; index <iCount ; index++) {
			Node search = doc.getElementsByTagName(fromTag).item(index);
			NodeList nodes = search.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node element = nodes.item(i);
				for (int j = 0; j < elementName.length; j++) {
					if (elementName[j].equalsIgnoreCase(element.getNodeName())) {
						
						search.removeChild(element);
					}

				}
			}
		}
		TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();	    
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    StringWriter writer = new StringWriter();
	    transformer.transform(new DOMSource(doc), new StreamResult(writer));
	    String output = writer.getBuffer().toString();
	    return output ; //;.replaceAll(">\\s*<", ">"  +  System.getProperty("line.separator") +  "<");
	}
	
	private static DocumentBuilder getDocumentBuilder() throws Exception
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		return documentBuilder;
	}

}
