package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch;

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
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;

import org.apache.log4j.Logger;
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
	private static final Logger _logger = Logger.getLogger(ImportFolderSet.class);

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
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;name&gt;Folder2&lt;/name&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parentId&gt;1&lt;/parentId&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;uiHidden&gt;false&lt;/uiHidden&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;/Folder&gt;<br>
	 *            &lt;/FolderSet&gt;</font><br>
	 *            Note:<br>
	 *            parentId is optional, if we don't specify it will take '1' as default else it will take the one which is as
	 *            input<br>
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
		_logger.info("import folders: \n" + xml);
		Response res = null;
		if (xml != null && xml.length() == 0) {
			return res = Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
		}
		res = Response.ok().build();
		String msg = "";
		try {
			JAXBContext jaxbContext = JAXBUtil.getJAXBContext(FolderSet.class);
			InputStream stream = ImportFolderSet.class.getClassLoader().getResourceAsStream(resourcePath);
			StringBuffer xmlStr = new StringBuffer(xml);
			StringReader sReader = new StringReader(xmlStr.toString());
			FolderSet folders = (FolderSet) JAXBUtil.unmarshal(sReader, stream, jaxbContext);
			List<FolderImpl> list = folders.getFolderSet();
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
		return res;
	}

	private boolean validateData(List<FolderImpl> list)
	{
		for (FolderImpl obj : list) {
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