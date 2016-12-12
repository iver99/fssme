package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.navigation;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsDatabaseUnavailException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jettison.json.JSONException;

/**
 * Saved Search Service
 *
 * @since 0.1
 */
@Path("")
public class SavedSearchAPI
{
	private static final Logger LOGGER = LogManager.getLogger(SearchManagerImpl.class);
	@Context
	UriInfo uri;

	/**
	 * Get list of all existed categories<br>
	 * <br>
	 * URL: <font color="blue">http://&lthost-name&gt:&lt;port number&gt;/savedsearch/v1/categories</font><br>
	 * The string "categories" in the URL signifies read operation on category.
	 *
	 * @since 0.1
	 * @return Lists all the existed categories<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">[<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 1,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "Log Analytics",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "description": "Search Category for Log Analytics",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "providerName": "MyProvider1",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "providerVersion": "1.0",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "providerAssetRoot": "assetRoot",
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "createdOn": "2014-07-22T14:48:53.048Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/category/1"<br>
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
	 *         <td>List all exists categories successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>500</td>
	 *         <td>Internal Server Error</td>
	 *         <td>&nbsp;</td>
	 *         </tr>
	 *         </table>
	 */
	@GET
	@Path("/categories")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCategory()
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/categories");
		String message = null;
		int statusCode = 200;
		ArrayNode jsonArray = new ObjectMapper().createArrayNode();
		List<Category> catList = new ArrayList<Category>();

		CategoryManager catMan = CategoryManager.getInstance();
		try {
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			catList = catMan.getAllCategories();
			for (Category category : catList) {
				ObjectNode jsonCat = EntityJsonUtil.getSimpleCategoryJsonObj(uri.getBaseUri(), category);
				jsonArray.add(jsonCat);
			}
			message = jsonArray.toString();

		}
		catch (EMAnalyticsFwkException e) {
			statusCode = e.getStatusCode();
			message = e.getMessage();
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Failed to get all categories, statusCode:" + e.getStatusCode() + " ,err:" + e.getMessage(), e);
		}
		catch (Exception e) {
			statusCode = 500;
			message = e.getMessage();
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Unknow error when retrieving all categories, statusCode:" + "500" + " ,err:" + e.getMessage(), e);
		}
		return Response.status(statusCode).entity(message).build();
	}

	/**
	 * List all entities (search or folder or both) which have the given folder Id<br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/entities?folderId=&lt;folder
	 * Id&gt;</font><br>
	 * The string "entities?folderId=&lt;folder Id&gt;" in the URL signifies read operation on search with given folder id<br>
	 *
	 * @since 0.1
	 * @param id
	 *            The folder Id by which user wants to get the entity details
	 * @return Lists all the entities with the given folder Id<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">[<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 9998,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"name": "Demo Search 1",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"description": "Search for Demo",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "category":<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 999,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/category/999"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "folder":<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 999,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/999"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "createdOn": "2014-07-03T11:07:24.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastModiedOn": "2014-07-03T11:07:24.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "systemSearch": false,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/search/9998",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "search"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 1011,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"name": "Demo Folder",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"description": "Folder for Demo",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "createdOn": "2014-07-03T11:07:24.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastModiedOn": "2014-07-03T11:07:24.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "systemFolder": false,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/1011",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "folder"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
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
	 *         <td>List all entities successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>404</td>
	 *         <td>Not Found</td>
	 *         <td>Folder with the Id &lt;folder Id&gt; does not exist</td>
	 *         </tr>
	 *         <tr>
	 *         <td>400</td>
	 *         <td>Bad Request</td>
	 *         <td>could be the following errors:<br>
	 *         1.Please give folderId<br>
	 *         2.Empty folderId<br>
	 *         3.Invalid Folder Id: xx<br>
	 *         4.Folder Id should be a numeric and not alphanumeric</td>
	 *         </tr>
	 *         <tr>
	 *         <td>500</td>
	 *         <td>Internal Server Error</td>
	 *         <td>&nbsp;</td>
	 *         </tr>
	 *         </table>
	 */
	@GET
	@Path("/entities")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDetails(@QueryParam("folderId") BigInteger id)
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/entities?folderId={}", id);
		String message = null;
		BigInteger folderId = BigInteger.ZERO;
		/**
		 * /entities will make id null
		 */
		if (id == null) {
			return Response.status(400).entity("Please give folderId").build();
		} 
		else if ("".equals(id.toString().trim())) {
			return Response.status(400).entity("Empty folderId").build();
		}
		else {
			folderId = id;
		}
		if (BigInteger.ZERO.compareTo(folderId) < 0) {
			try {
				if (!DependencyStatus.getInstance().isDatabaseUp()) {
					throw new EMAnalyticsDatabaseUnavailException();
				}
				return Response.status(200).entity(getFolderDetails(folderId)).build();
			}
			catch (JSONException e) {
				LOGGER.error(e.getLocalizedMessage());
				return Response.status(500).entity(e.getMessage()).build();

			}
			catch (EMAnalyticsFwkException e) {
				LOGGER.error(e.getLocalizedMessage());
				return Response.status(e.getStatusCode()).entity(e.getMessage()).build();

			}
			catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getLocalizedMessage());
			}
			return Response.status(200).entity(message).build();
		}
		else {
			return Response.status(400).entity("Invalid folderId: " + folderId).build();
		}

	}

	/**
	 * List all root folders<br>
	 * <br>
	 * URL: <font color="blue">http://&lthost-name&gt:&lt;port number&gt;/savedsearch/v1</font><br>
	 *
	 * @since 0.1
	 * @return Lists all the root folders<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">[<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 1,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "All Searches",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "createdOn": "2014-07-03T11:07:21.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "description": "Search Console Root Folder",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastModifiedOn": "2014-07-03T11:07:21.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "systemFolder": true,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/1", <br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "folder"<br>
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
	 *         <td>List all the root folders successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>500</td>
	 *         <td>Internal Server Error</td>
	 *         <td>&nbsp;</td>
	 *         </tr>
	 *         </table>
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRootFolders()
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1");
		String message = "";
		try {
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			message = getFolderDetails(BigInteger.ZERO);
		}
		catch (EMAnalyticsFwkException e) {
			LOGGER.error(e.getLocalizedMessage());
			return Response.status(e.getStatusCode()).entity(e.getMessage()).build();

		}
		catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(message).build();

	}

	private String getFolderDetails(BigInteger id) throws EMAnalyticsFwkException, UnsupportedEncodingException, JSONException,
			EMAnalyticsFwkException
	{

		String message = new String();
		FolderManager fman = FolderManager.getInstance();
		SearchManager sman = SearchManager.getInstance();
		ArrayNode jsonArray = new ObjectMapper().createArrayNode();
		if (!BigInteger.ZERO.equals(id)) {
			fman.getFolder(id);
		}
		ObjectNode jsonObj;
		List<Folder> folderList = fman.getSubFolders(id);
		for (Folder folderObj : folderList) {
			jsonObj = EntityJsonUtil.getSimpleFolderJsonObj(uri.getBaseUri(), folderObj, true);
			jsonArray.add(jsonObj);
		}

		// Get Searches too !!
		List<Search> searchList;
		searchList = sman.getSearchListByFolderId(id);
		for (Search searchObj : searchList) {
			jsonObj = EntityJsonUtil.getSimpleSearchJsonObj(uri.getBaseUri(), searchObj, null, true);
			jsonArray.add(jsonObj);
		}
		message = jsonArray.toString();

		return message;

	}

}
