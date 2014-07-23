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

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportCategoryImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategorySet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 * Import Category Services
 *
 * @since 0.1
 */
@Path("importcategories")
public class ImportCategorySet
{

	private final String resourcePath = "oracle/sysman/emSDK/emaas/platform/savedsearch/ws/rest/importsearch/category.xsd";

	/**
	 * Import the category with defined XML file<br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/importcategories</font><br>
	 * The string "importcategories" in the URL signifies import operation on category.<br>
	 *
	 * @since 0.1
	 * @param xml
	 *            "xml" is the XML definition used to import category<br>
	 *            Input Sample:<br>
	 *            <font color="DarkCyan">&lt;?xml version="1.0" encoding="UTF-8" standalone="yes"?&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;CategorySet&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Category&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;name&gt;Category123&lt;/name&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;defaultFolderId&gt;1&lt;/
	 *            defaultFolderId&gt;&nbsp;&nbsp;&nbsp;&nbsp;&lt;!-- optional, if we don't specify it will take '1' as default
	 *            else it will take the one which is as input --&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/Category&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;/CategorySet&gt;</font><br>
	 * @return The category with id and name<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">[<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 1121,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "Category123"<br>
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
	 *         <td>import category successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>400</td>
	 *         <td>Bad Request</td>
	 *         <td>could be the following errors:<br>
	 *         1. If missing some necessary element in XML, it would return 400 error<br>
	 *         For example, if there is no "name" in described in XML, it would return the error message<br>
	 *         Error at line 4 , column 22 Invalid content was found starting with element 'defaultFolderId'. One of '{id, name}'
	 *         is expected.<br>
	 *         2. If the XML format is wrong, it would return the error message "Please specify input with valid format"</td>
	 *         </tr>
	 *         <tr>
	 *         <td>500</td>
	 *         <td>Internal Server Error</td>
	 *         <td>An internal error has occurred</td>
	 *         </tr>
	 *         </table>
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/xml")
	public Response importsCategories(String xml)
	{
		Response res = null;
		if (xml != null && xml.length() == 0) {
			return res = Response.status(Status.BAD_REQUEST).entity("Please specify input with valid format").build();
		}
		res = Response.ok().build();
		String msg = "";
		try {
			InputStream stream = ImportFolderSet.class.getClassLoader().getResourceAsStream(resourcePath);
			StringBuffer xmlStr = new StringBuffer(xml);
			StringReader sReader = new StringReader(xmlStr.toString());
			CategorySet categories = (CategorySet) JAXBUtil
					.unmarshal(sReader, stream, JAXBUtil.getJAXBContext(CategorySet.class));
			List<ImportCategoryImpl> list = categories.getCategorySet();
			if (list.size() == 0) {
				return res = Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
			}
			if (validateData(list)) {
				return res = Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
			}
			CategoryManagerImpl mgr = (CategoryManagerImpl) CategoryManagerImpl.getInstance();
			List<ImportCategoryImpl> addedList = mgr.saveMultipleCategories(list);
			JSONArray jsonArray = new JSONArray();
			for (ImportCategoryImpl categoryImpl : addedList) {
				JSONObject jObj = new JSONObject();
				jObj.put("id", categoryImpl.getId());
				jObj.put("name", categoryImpl.getName());
				jsonArray.put(jObj);
			}
			res = Response.status(Status.OK).entity(jsonArray).build();
		}
		catch (ImportException e) {
			msg = e.getMessage();
			e.printStackTrace();
			res = Response.status(Status.BAD_REQUEST).entity(msg).build();
		}
		catch (Exception e) {
			msg = "An internal error has occurred";
			res = Response.status(Status.INTERNAL_SERVER_ERROR).entity(msg).build();
		}
		return res;
	}

	private boolean validateData(List<ImportCategoryImpl> list)
	{
		for (ImportCategoryImpl obj : list) {
			if (!(obj.getName() != null && obj.getName().trim().length() > 0)) {
				return true;
			}

			if (obj.getFolder() != null) {
				if (obj.getFolder() instanceof FolderImpl) {
					FolderImpl objFolder = (FolderImpl) obj.getFolder();
					if (!(objFolder.getName() != null && objFolder.getName().trim().length() > 0)) {
						return true;
					}
				}
			}
		}
		return false;

	}

}
