
package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportCategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ObjectFactory;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategorySet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

/**
 * Import Category Services
 * 
 * @since 0.1
 */
@Path("importcategories")
public class ImportCategorySet
{
	private static final Logger LOGGER = LogManager.getLogger(ImportCategorySet.class);
	private static final String RESOURCE_PATH = "oracle/sysman/emSDK/emaas/platform/savedsearch/ws/rest/importsearch/category.xsd";

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
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Name&gt;Category123&lt;/Name&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;DefaultFolderId&gt;1&lt;/
	 *            DefaultFolderId&gt; &nbsp;&nbsp;&nbsp;&nbsp;&lt;!-- optional, if we don't specify it will insert null else it
	 *            will take the one which is as input --&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;ProviderName&gt;Log
	 *            Analytics&lt;/ProviderName&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;ProviderVersion&gt;1.0&lt;/
	 *            ProviderVersion&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;ProviderDiscovery&gt;discovery&lt;/
	 *            ProviderDiscovery&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;ProviderAssetRoot&gt;asset&lt;/
	 *            ProviderAssetRoot&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/Category&gt;<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&lt;/CategorySet&gt;</font><br>
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
	 *            <td>DefaultFolderId</td>
	 *            <td>NUMBER(38,0)</td>
	 *            <td>N</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            <tr>
	 *            <td>Provider Name</td>
	 *            <td>VARCHAR2(64 BYTE)</td>
	 *            <td>Y</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            <tr>
	 *            <td>Provider Version</td>
	 *            <td>VARCHAR2(64 BYTE)</td>
	 *            <td>Y</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            <tr>
	 *            <td>Provider Discovery</td>
	 *            <td>VARCHAR2(64 BYTE)</td>
	 *            <td>N</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            <tr>
	 *            <td>Provider Asset Root</td>
	 *            <td>VARCHAR2(64 BYTE)</td>
	 *            <td>Y</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            </table>
	 * @return The category with id and name<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">[<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 1121,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "Category123"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "providerName": "Log Analytics"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "providerVersion": "1.0"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "providerDiscovery": "discovery"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "providerAssetRoot": "asset"<br>
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
	 *         Error at line 4 , column 22 Invalid content was found starting with element 'DefaultFolderId'. One of '{id, name}'
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
		InputStream stream = null;
		if (xml != null && xml.length() == 0) {
			return res = Response.status(Status.BAD_REQUEST).entity("Please specify input with valid format").build();
		}
		res = Response.ok().build();
		String msg = "";
		try {
			stream = ImportCategorySet.class.getClassLoader().getResourceAsStream(RESOURCE_PATH);
			StringBuilder xmlStr = new StringBuilder(xml);
			StringReader sReader = new StringReader(xmlStr.toString());
			CategorySet categories = (CategorySet) JAXBUtil.unmarshal(sReader, stream,
					JAXBUtil.getJAXBContext(ObjectFactory.class));
			List<ImportCategoryImpl> list = categories.getCategorySet();
			if (list.isEmpty()) {
				return res = Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
			}
			if (validateData(list)) {
				return res = Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
			}
			CategoryManagerImpl mgr = (CategoryManagerImpl) CategoryManagerImpl.getInstance();
			List<Category> addedList = mgr.saveMultipleCategories(list);
			JSONArray jsonArray = new JSONArray();
			for (Category categoryImpl : addedList) {
				JSONObject jObj = new JSONObject();
				jObj.put("id", categoryImpl.getId());
				jObj.put("name", categoryImpl.getName());
				jsonArray.put(jObj);
			}
			res = Response.status(Status.OK).entity(jsonArray).build();
		}
		catch (ImportException e) {
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Failed to import categories (1)", e);
			msg = e.getMessage();
			res = Response.status(Status.BAD_REQUEST).entity(msg).build();
		}
		catch (Exception e) {
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Failed to import categories (2)", e);
			msg = "An internal error has occurred";
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

	private boolean validateData(List<ImportCategoryImpl> list)
	{
		for (ImportCategoryImpl obj : list) {
			if (!(obj.getName() != null && obj.getName().trim().length() > 0)) {
				return true;
			}

			if (obj.getFolderDet() != null) {
				if (obj.getFolderDetails() instanceof FolderImpl) {
					FolderImpl objFolder = (FolderImpl) obj.getFolderDetails();
					if (!(objFolder.getName() != null && objFolder.getName().trim().length() > 0)) {
						return true;
					}
				}
			}
		}
		return false;

	}

}
