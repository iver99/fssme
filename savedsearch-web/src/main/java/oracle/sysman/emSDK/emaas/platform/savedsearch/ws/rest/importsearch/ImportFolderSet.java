package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ObjectFactory;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 * Import Folders Services
 *
 * @since 0.1
 */
@Path("importfolders")
public class ImportFolderSet
{
	private final String resourcePath = "oracle/sysman/emSDK/emaas/platform/savedsearch/ws/rest/importsearch/folder.xsd";
	private static final Logger _logger = LogManager.getLogger(ImportFolderSet.class);

	/**
	 * Import the folders with defined XML file<br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/importfolders</font><br>
	 * The string "importfolders" in the URL signifies import operation on folder.<br>
	 *
	 * @since 0.1
	 * @param xml
	 *            "xml" is the XML definition used to import folder<br>
	 *            input Sample:<br>
	 *            <font color="DarkCyan">&lt;?xml version="1.0" encoding="UTF-8" standalone="yes"?&gt;<br>
	 *            &lt;FolderSet&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;Folder&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Name&gt;Folder2&lt;/Name&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;ParentId&gt;1&lt;/ParentId&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;UiHidden&gt;false&lt;/UiHidden&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;/Folder&gt;<br>
	 *            &lt;/FolderSet&gt;</font><br>
	 *            Note:<br>
	 *            ParentId is optional, if we don't specify it will take '1' as default else it will take the one which is as
	 *            input<br>
	 *            Input Spec:<br>
	 *            <table border="1">
	 *            <tr>
	 *            <th>Field Name</th>
	 *            <th>Type</th>
	 *            <th>Required
	 *            <th>Default Value</th>
	 *            <th>Comments</th>
	 *            </tr>
	 *            <tr>
	 *            <td>Name</td>
	 *            <td>VARCHAR2(64 BYTE)</td>
	 *            <td>Y</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            <tr>
	 *            <td>ParentId</td>
	 *            <td>NUMBER(38,0)</td>
	 *            <td>N</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            <tr>
	 *            <td>UiHidden</td>
	 *            <td>BOOLEAN</td>
	 *            <td>N</td>
	 *            <td>false</td>
	 *            <td>Valid value: true, false.</td>
	 *            </tr>
	 *            </table>
	 * @return The folder with id and name<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">[<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 1643,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "Folder2"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }<br>
	 *         ]</font><br>
	 * <br>
	 *         Response Code:<br>
	 *         <table border="1">
	 *         <tr>
	 *         <th>Status code</th>
	 *         <th>Status</th>
	 *         <th>Description</th>
	 *         </tr>
	 *         <tr>
	 *         <td>200</td>
	 *         <td>OK</td>
	 *         <td>import folder successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>400</td>
	 *         <td>Bad Request</td>
	 *         <td>could be the following errors:<br>
	 *         1. If missing some necessary element in XML, it would return 400 error<br>
	 *         For example, if there is no "name" in described in XML, it would return the error message<br>
	 *         Error at line 4 , column 15 Invalid content was found starting with element 'parentId'. One of '{id, name}' is
	 *         expected. <br>
	 *         2. If the XML format is wrong, it would return the error message "Please specify input with valid format"</td>
	 *         </tr>
	 *         <tr>
	 *         <td>500</td>
	 *         <td>Internal Server Error</td>
	 *         <td>An internal error has occurred while importing folder</td>
	 *         </tr>
	 *         </table>
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/xml")
	public Response importsFolders(String xml)
	{

		Response res = null;
		InputStream stream = null;
		if (xml != null && xml.length() == 0) {
			return res = Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
		}
		res = Response.ok().build();
		String msg = "";
		try {
			JAXBContext jaxbContext = JAXBUtil.getJAXBContext(ObjectFactory.class);
			stream = ImportFolderSet.class.getClassLoader().getResourceAsStream(resourcePath);
			StringBuffer xmlStr = new StringBuffer(xml);
			StringReader sReader = new StringReader(xmlStr.toString());
			FolderSet folders = (FolderSet) JAXBUtil.unmarshal(sReader, stream, jaxbContext);
			List<FolderDetails> list = folders.getFolderSet();
			if (list.size() == 0) {
				return res = Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
			}
			if (validateData(list)) {
				return res = Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
			}
			List<FolderImpl> addedList = FolderManagerImpl.getInstance().saveMultipleFolders(list);
			JSONArray jsonArray = new JSONArray();
			for (FolderImpl folderImpl : addedList) {
				JSONObject jObj = new JSONObject();
				jObj.put("id", folderImpl.getId());
				jObj.put("name", folderImpl.getName());
				jsonArray.put(jObj);
			}
			res = Response.status(Status.OK).entity(jsonArray).build();
		}
		catch (ImportException e) {
			_logger.error("Failed to import folders (1)", e);
			msg = e.getMessage();
			res = Response.status(Status.BAD_REQUEST).entity(msg).build();
		}
		catch (Exception e) {
			_logger.error("Failed to import folders (2)", e);
			msg = "An internal error has occurred  while importing folder ";
			res = Response.status(Status.INTERNAL_SERVER_ERROR).entity(msg).build();
			e.printStackTrace();
		}
		finally {
			if (stream != null) {
				try {
					stream.close();
				}
				catch (IOException e) {

				}
			}
		}
		return res;
	}

	private boolean validateData(List<FolderDetails> list)
	{
		for (FolderDetails obj : list) {
			if (!(obj.getName() != null && obj.getName().trim().length() > 0)) {
				return true;
			}
		}
		return false;

	}

}

/*FolderSet fSet = new FolderSet();
fSet.setFolderSet(rejectedList);
result=result+JAXBUtil.marshal(jaxbContext,ImportFolderSet.class.getClassLoader().getResourceAsStream(resourcePath), fSet);*/