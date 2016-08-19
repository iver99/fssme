package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;

import org.xml.sax.SAXException;

public class JAXBUtil
{
	public static final String VALID_ERR_MESSAGE = "Please specify input with valid format";
	private static String[] fields = { "id", "name", "description", "parentId", "uiHidden", "value", "type", "folderId",
			"categoryId", "metadata", "defaultFolderId", "queryStr", "locked", "uiHidden", "isWidget", "providerName",
			"providerVersion", "providerDiscovery", "providerAssetRoot" };

	private JAXBUtil() {
	}

	public static JAXBContext getJAXBContext(Class<?> cls) throws Exception
	{
		JAXBContext jaxbcontext = null;

		try {
			jaxbcontext = JAXBContext.newInstance(cls);
		}
		catch (JAXBException ex) {
			throw new Exception(ex);
		}

		return jaxbcontext;
	}

	/*	public static String marshal(JAXBContext jaxbContext, InputStream schemaFile, Object obj) throws Exception
		{
			String xml = null;
			try {
				StreamSource xsdSource = null;
				if (schemaFile != null) {
					SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
					xsdSource = new StreamSource(schemaFile);
					Schema schema = sf.newSchema(xsdSource);
				}
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				//jaxbMarshaller.setEventHandler(new ValidationEventHandler() {
					//@Override
					//public boolean handleEvent(ValidationEvent validationevent) {
						//return true;
					//}
				///});
				//jaxbMarshaller.setSchema(schema);	
				StringWriter output = new StringWriter();
				jaxbMarshaller.marshal(obj, output);
				xml = output.toString();
			}
			catch (JAXBException | SAXException e) {
				e.printStackTrace();
				throw e;
			}
			return xml;
		}*/

	/*public static void marshal(Object object, String fileName, JAXBContext jaxbcontext) throws JAXBException,
			FileNotFoundException, ImportException
	{
		Marshaller m = jaxbcontext.createMarshaller();

		FileOutputStream fo = null;

		try {
			fo = new FileOutputStream(fileName);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(object, fo);
		}
		finally {
			if (fo != null) {
				try {
					fo.close();
				}
				catch (IOException e) {
					throw new ImportException("failed to close file=<" + fileName + ">!", e);
				}
			}
		}
	}*/

	/*public static Object unmarshal(Reader reader, File schemaFile, JAXBContext jaxbcontext) throws JAXBException, SAXException
	{
		Unmarshaller u = jaxbcontext.createUnmarshaller();

		SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sf.newSchema(schemaFile);

		u.setSchema(schema);

		Object object = u.unmarshal(reader);

		return object;
	}*/

	public static Object unmarshal(Reader reader, InputStream schemaFile, JAXBContext jaxbcontext) throws ImportException
	{
		final List<String> errorList = new ArrayList<String>();
		Object object = null;
		try {
			StreamSource xsdSource = null;
			Unmarshaller u = jaxbcontext.createUnmarshaller();
			SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			xsdSource = new StreamSource(schemaFile);
			Schema schema = sf.newSchema(xsdSource);
			u.setSchema(schema);
			u.setEventHandler(new ValidationEventHandler() {
				@Override
				public boolean handleEvent(ValidationEvent validationevent)
				{
					String errLocation = null;
					if (validationevent.getSeverity() != ValidationEvent.WARNING) {
						String msg = validationevent.getMessage();
						Pattern pattern = Pattern.compile("(cvc.*)(:)");
						Matcher matcher = pattern.matcher(msg);
						if ((msg.contains("cvc-complex-type.2.4.a") || msg.contains("cvc-complex-type.2.4.b")) && matcher.find()) {
							errLocation = "Error at line " + validationevent.getLocator().getLineNumber() + " , column "
									+ validationevent.getLocator().getColumnNumber();
							errorList.add(errLocation + "  " + msg.substring(matcher.end()) + " "
									+ System.getProperty("line.separator"));

						}

						if (msg.contains("cvc-complex-type.2.4.d") && matcher.find()) {
							errLocation = "Error at line " + validationevent.getLocator().getLineNumber() + " , column "
									+ validationevent.getLocator().getColumnNumber();
							if (msg.contains("'Folder'")) {

								errorList.add(errLocation + "  "
										+ " Please specify either folder details or folder id not both" + " "
										+ System.getProperty("line.separator"));

							} else if (msg.contains("'Category'")) {
								errorList.add(errLocation + "  "
										+ " Please specify either category details or category id not both" + " "
										+ System.getProperty("line.separator"));

							} else {
								errorList.add(errLocation + "  " + msg.substring(matcher.end()) + " "
										+ System.getProperty("line.separator"));
							}

						}

						if (matcher.find()) {
							boolean bResult = false;
							msg = msg.substring(matcher.end());
							for (String fld : fields) {
								pattern = Pattern.compile(".*\\{.*" + fld + ".*\\}|'" + fld + "'");
								matcher = pattern.matcher(msg);
								if (matcher.find()) {
									errLocation = "Error at line " + validationevent.getLocator().getLineNumber() + " , column "
											+ validationevent.getLocator().getColumnNumber();
									errorList.add(errLocation + "  " + msg + " " + System.getProperty("line.separator"));
									bResult = true;
									break;
								}

							}
							if (!bResult && !errorList.contains(VALID_ERR_MESSAGE) && errorList.isEmpty()) {
								errorList.add(VALID_ERR_MESSAGE + System.getProperty("line.separator"));
							}
						}
						else if (!errorList.contains(VALID_ERR_MESSAGE) && errorList.isEmpty()) {
							errorList.add(VALID_ERR_MESSAGE + System.getProperty("line.separator"));
						}

						return true;
					}
					return true;
				}
			});
			StreamSource br = new StreamSource(reader);
			object = u.unmarshal(br);
		}
		catch (UnmarshalException e) {
			e.printStackTrace();
			if (!errorList.contains(VALID_ERR_MESSAGE) && errorList.isEmpty()) {
				errorList.add(VALID_ERR_MESSAGE);
			}
		}
		catch (JAXBException | SAXException e) {
			e.printStackTrace();
		}
		if (!errorList.isEmpty()) {
			StringBuilder errMsg = new StringBuilder();
			for (String sError : errorList) {
				errMsg.append(sError);
			}
			throw new ImportException(errMsg.toString());
		}
		return object;
	}

	/*public static Object unmarshal(Reader reader, JAXBContext jaxbcontext) throws JAXBException
	{
		Unmarshaller u = jaxbcontext.createUnmarshaller();
		Object object = u.unmarshal(reader);

		return object;
	}*/

	/*public static Object unmarshal(String fileName, File schemaFile, JAXBContext jaxbcontext) throws JAXBException,
			FileNotFoundException, SAXException, ImportException
	{
		Unmarshaller u = jaxbcontext.createUnmarshaller();

		SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sf.newSchema(schemaFile);

		u.setSchema(schema);

		FileInputStream fi = null;

		Object object = null;

		try {
			fi = new FileInputStream(fileName);
			object = u.unmarshal(fi);
		}
		finally {
			if (fi != null) {
				try {
					fi.close();
				}
				catch (IOException e) {
					throw new ImportException("failed to close file=<" + fileName + ">!", e);
				}
			}
		}

		return object;
	}*/

	/*public static Object unmarshal(String fileName, JAXBContext jaxbcontext) throws JAXBException, FileNotFoundException,
			ImportException
	{
		Unmarshaller u = jaxbcontext.createUnmarshaller();

		FileInputStream fi = null;

		Object object = null;

		try {
			fi = new FileInputStream(fileName);
			object = u.unmarshal(fi);
		}
		finally {
			if (fi != null) {
				try {
					fi.close();
				}
				catch (IOException e) {
					throw new ImportException("failed to close file=<" + fileName + ">!", e);
				}
			}
		}

		return object;
	}*/

}
