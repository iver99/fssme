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

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportSearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 * Import Searches Services
 *
 * @since 0.1
 */
@Path("importsearches")
public class ImportSearchSet
{

	private final String resourcePath = "oracle/sysman/emSDK/emaas/platform/savedsearch/ws/rest/importsearch/search.xsd";

	/**
	 * Import the searches with defined XML file<br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/importsearches</font><br>
	 * The string "importsearches" in the URL signifies import operation on search.<br>
	 *
	 * @since 0.1
	 * @param xml
	 *            "xml" is the XML definition used to import search<br>
	 *            Input Sample - Importing the search with category name & folder name objects:<br>
	 *            <font color="DarkCyan">&lt;?xml version="1.0" encoding="UTF-8" standalone="yes"?&gt;<br>
	 *            &lt;SearchSet&gt;<br>
	 *            &nbsp;&nbsp;&lt;search&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;name&gt;Search1234&lt;/name&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;SearchParameters&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;SearchParameter&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;name&gt;Param1&lt;/name&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;STRING&lt;/type&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/SearchParameter&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;/SearchParameters&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;Category&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;name&gt;cat_name&lt;/name&gt;&nbsp;&nbsp;&nbsp;&nbsp;&lt!-- If the
	 *            category name is not existed, it would create a new category with the given name --&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;/Category&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;Folder&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;name&gt;fol_name&lt;/name&gt;&nbsp;&nbsp;&nbsp;&nbsp;&lt!-- If the
	 *            folder name is not existed, it would create a new folder with the given name --&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;uiHidden&gt;false&lt;/uiHidden&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;/Folder&gt;<br>
	 *            &nbsp;&nbsp;&lt;/search&gt;<br>
	 *            &lt;/SearchSet&gt;</font> <br>
	 *            Input Sample - Importing the search with categoryId & folderId objects:<br>
	 *            <font color="DarkCyan">&lt;?xml version="1.0" encoding="UTF-8" standalone="yes"?&gt;<br>
	 *            &lt;SearchSet&gt;<br>
	 *            &nbsp;&nbsp;&lt;search&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;name&gt;Search1&lt;/name&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;SearchParameters&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;SearchParameter&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;name&gt;Param1&lt;/name&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;type&gt;STRING&lt;/type&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/SearchParameter&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;/SearchParameters&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;categoryId&gt;1122&lt;/categoryId&gt;&nbsp;&nbsp;&nbsp;&nbsp;&lt!-- If the
	 *            categoryId is not existed, importing search would failed --&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;folderId&gt;1644&lt;/folderId&gt;&nbsp;&nbsp;&nbsp;&nbsp;&lt!-- If the folderId is
	 *            not existed, importing search would failed --&gt;<br>
	 *            &nbsp;&nbsp;&lt;/search&gt;<br>
	 *            &lt;/SearchSet&gt;</font><br>
	 * @return The search with id and name which means the importing successfully<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">[<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 10665,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "Search1234"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }<br>
	 *         ]</font><br>
	 *         The search with name which means the importing failed<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">[<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "Search1234"<br>
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
	 *         <td>If the response content contains search id and name, it means the search is created.<br>
	 *         If the response content contains only name, it means the search is not created.</td>
	 *         </tr>
	 *         <tr>
	 *         <td>400</td>
	 *         <td>Bad Request</td>
	 *         <td>could be the following errors:<br>
	 *         1. If missing some necessary element in XML, it would return 400 error<br>
	 *         For example, if there is no "category" in described in XML, it would return the error message<br>
	 *         Error at line 12 , column 14 Invalid content was found starting with element 'Folder'. One of '{Category,
	 *         categoryId}' is expected.<br>
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
	public Response importSearches(String xml)
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
			SearchSet searches = (SearchSet) JAXBUtil.unmarshal(sReader, stream, JAXBUtil.getJAXBContext(SearchSet.class));
			List<ImportSearchImpl> list = searches.getSearchSet();
			if (list.size() == 0) {
				return res = Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
			}
			if (validateData(list)) {
				return res = Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
			}
			List<ImportSearchImpl> addedList = SearchManagerImpl.getInstance().saveMultipleSearch(list);
			JSONArray jsonArray = new JSONArray();
			for (ImportSearchImpl impSearch : addedList) {
				JSONObject jObj = new JSONObject();
				jObj.put("id", impSearch.getId());
				jObj.put("name", impSearch.getName());
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

	private boolean validateData(List<ImportSearchImpl> list)
	{
		for (ImportSearchImpl obj : list) {
			if (!(obj.getName() != null && obj.getName().trim().length() > 0)) {
				return true;
			}
			if (obj.getCategoryDetails() != null) {
				if (obj.getCategoryDetails() instanceof CategoryImpl) {
					CategoryImpl objCat = (CategoryImpl) obj.getCategoryDetails();
					if (!(obj.getName() != null && objCat.getName().trim().length() > 0)) {
						return true;
					}
				}
			}
			if (obj.getFolderDetails() != null) {
				if (obj.getFolderDetails() instanceof FolderImpl) {
					FolderImpl objFolder = (FolderImpl) obj.getFolderDetails();
					if (!(obj.getName() != null && objFolder.getName().trim().length() > 0)) {
						return true;
					}
				}
			}
		}
		return false;

	}

}
