package oracle.sysman.emSDK.emaas.platform.savedsearch;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Node;
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
	
	
	
	

}
