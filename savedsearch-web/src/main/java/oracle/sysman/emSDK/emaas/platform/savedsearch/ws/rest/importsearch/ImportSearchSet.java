package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
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
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ObjectFactory;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private static final Logger LOGGER = LogManager.getLogger(ImportSearchSet.class);
	private static final String RESOURCE_PATH = "oracle/sysman/emSDK/emaas/platform/savedsearch/ws/rest/importsearch/search.xsd";

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
	 *            &nbsp;&nbsp;&lt;Search&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;Name&gt;Search1234&lt;/Name&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;SearchParameters&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;SearchParameter&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Name&gt;Param1&lt;/Name&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Type&gt;STRING&lt;/Type&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Value&gt;ALL&lt;/Value&gt;&lt;br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/SearchParameter&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;/SearchParameters&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;Category&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Name&gt;cat_name&lt;/Name&gt;&nbsp;&nbsp;&nbsp;&nbsp;&lt!-- If the
	 *            category name is not existed, it would create a new category with the given name --&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;ProviderName&gt;MySearchProvider&lt;/ProviderName&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;ProviderVersion&gt;1.0&lt;/ProviderVersion&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;ProviderAssetRoot&gt;assetRoot&lt;/ProviderAssetRoot&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;/Category&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;Folder&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Name&gt;fol_name&lt;/Name&gt;&nbsp;&nbsp;&nbsp;&nbsp;&lt!-- If the
	 *            folder name is not existed, it would create a new folder with the given name --&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;UiHidden&gt;false&lt;/UiHidden&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;/Folder&gt;<br>
	 *            &nbsp;&nbsp;&lt;/Search&gt;<br>
	 *            &lt;/SearchSet&gt;</font> <br>
	 *            Input Sample - Importing the search with categoryId & folderId objects:<br>
	 *            <font color="DarkCyan">&lt;?xml version="1.0" encoding="UTF-8" standalone="yes"?&gt;<br>
	 *            &lt;SearchSet&gt;<br>
	 *            &nbsp;&nbsp;&lt;Search&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;Name&gt;Search1&lt;/Name&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;SearchParameters&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;SearchParameter&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Name&gt;Param1&lt;/Name&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Type&gt;STRING&lt;/Type&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Value&gt;ALL&lt;/Value&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/SearchParameter&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;/SearchParameters&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;CategoryId&gt;1122&lt;/CategoryId&gt;&nbsp;&nbsp;&nbsp;&nbsp;&lt!-- If the
	 *            categoryId is not existed, importing search would failed --&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;FolderId&gt;1644&lt;/FolderId&gt;&nbsp;&nbsp;&nbsp;&nbsp;&lt!-- If the folderId is
	 *            not existed, importing search would failed --&gt;<br>
	 *            &nbsp;&nbsp;&lt;/Search&gt;<br>
	 *            &lt;/SearchSet&gt;</font><br>
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
	 *            <td>CategoryId</td>
	 *            <td>NUMBER(38,0)</td>
	 *            <td>Y</td>
	 *            <td>N/A</td>
	 *            <td>CategoryId and Category Name is alternative, required</td>
	 *            </tr>
	 *            <tr>
	 *            <td>Category Name</td>
	 *            <td>VARCHAR2(64 BYTE)</td>
	 *            <td>Y</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            <tr>
	 *            <td>FolderId</td>
	 *            <td>NUMBER(38,0)</td>
	 *            <td>Y</td>
	 *            <td>N/A</td>
	 *            <td>FolderId and Folder Name is alternative, required</td>
	 *            </tr>
	 *            <tr>
	 *            <td>Folder Name</td>
	 *            <td>VARCHAR2(64 BYTE)</td>
	 *            <td>Y</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            <tr>
	 *            <td>Folder UiHidden</td>
	 *            <td>BOOLEAN</td>
	 *            <td>N</td>
	 *            <td>false</td>
	 *            <td>Valid value: true, false.</td>
	 *            </TR>
	 *            <tr>
	 *            <td>SearchParameter Name</td>
	 *            <td>VARCHAR2(64 BYTE)</td>
	 *            <td>N</td>
	 *            <td>N/A</td>
	 *            <td>For each SearchParameter, Name and Type are required, Value is optional</td>
	 *            </tr>
	 *            <tr>
	 *            <td>SearchParameter Type</td>
	 *            <td>VARCHAR2(16 BYTE)</td>
	 *            <td>N</td>
	 *            <td>N/A</td>
	 *            <td>Valid value: STRING, CLOB.</td>
	 *            </tr>
	 *            <tr>
	 *            <td>SearchParameter Value</td>
	 *            <td>VARCHAR2(1024 BYTE)/NCLOB</td>
	 *            <td>N</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            </table>
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
	 *         CategoryId}' is expected.<br>
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
	public Response importSearches(String xml, @HeaderParam("SSF_OOB") String oobSearch)
	{

		Response res = null;
		InputStream stream = null;
		if (xml != null && xml.length() == 0) {
			return res = Response.status(Status.BAD_REQUEST).entity("Please specify input with valid format").build();
		}
		res = Response.ok().build();
		String msg = "";
		try {
			stream = ImportSearchSet.class.getClassLoader().getResourceAsStream(RESOURCE_PATH);
			StringBuilder xmlStr = new StringBuilder(xml);
			StringReader sReader = new StringReader(xmlStr.toString());
			SearchSet searches = (SearchSet) JAXBUtil.unmarshal(sReader, stream, JAXBUtil.getJAXBContext(ObjectFactory.class));
			List<ImportSearchImpl> list = searches.getSearchSet();
			if (list.isEmpty()) {
				return res = Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
			}
			if (validateData(list)) {
				return res = Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
			}

			boolean bResult = false;
			if (oobSearch == null) {
				bResult = false;
			}
			if (oobSearch != null && "true".equalsIgnoreCase(oobSearch)) {
				bResult = true;
			}

			List<Search> addedList = SearchManagerImpl.getInstance().saveMultipleSearch(list, bResult);
			JSONArray jsonArray = new JSONArray();
			for (Search impSearch : addedList) {
				JSONObject jObj = new JSONObject();
				jObj.put("id", impSearch.getId().toString());
				jObj.put("name", impSearch.getName());
				jsonArray.put(jObj);
			}
			res = Response.status(Status.OK).entity(jsonArray).build();

		}
		catch (ImportException e) {
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Failed to import searches (1)", e);
			msg = e.getMessage();
			e.printStackTrace();
			res = Response.status(Status.BAD_REQUEST).entity(msg).build();
		}
		catch (Exception e) {
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Failed to import searches (2)", e);
			msg = "An internal error has occurred" + e.getMessage();
			res = Response.status(Status.INTERNAL_SERVER_ERROR).entity(msg).build();
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

	private boolean validateData(List<ImportSearchImpl> list)
	{
		for (ImportSearchImpl obj : list) {
			if (!(obj.getName() != null && obj.getName().trim().length() > 0)) {
				return true;
			}
			if (obj.getCategoryDetails() != null && obj.getCategoryDetails() instanceof CategoryImpl) {
				CategoryImpl objCat = (CategoryImpl) obj.getCategoryDetails();
				if (!(objCat.getName() != null && objCat.getName().trim().length() > 0)) {
					return true;
				}
			}
			if (obj.getFolderDetails() != null && obj.getFolderDetails() instanceof FolderImpl) {
				FolderImpl objFolder = (FolderImpl) obj.getFolderDetails();
				if (!(objFolder.getName() != null && objFolder.getName().trim().length() > 0)) {
					return true;
				}
			}
		}
		return false;

	}

}
