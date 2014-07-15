package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.navigation;

import java.io.UnsupportedEncodingException;
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

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JSONUtil;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Saved Search Service
 *
 * @since 0.1
 */
@Path("")
public class SavedSearchAPI
{
	private final String search = "search";
	private final String folder = "folder";
	private static final String FOLDER_PATH = "flattenedFolderPath";
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
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "defaultFolder":<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 2,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/2"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "parameters":<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name":
	 *         "CATEGORY_PARAM_VIEW_TASKFLOW",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value":
	 *         "/WEB-INF/core/loganalytics/obssearch/plugins/dashboard-flow-definition.xml#dashboard-flow-definition" ,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "STRING"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ],<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/category/1"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }<br>
	 *         ]</font><br>
	 */
	@GET
	@Path("/categories")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCategory()
	{
		String message = null;
		int statusCode = 200;
		JSONArray jsonArray = new JSONArray();
		Category category;
		List<Category> catList = new ArrayList<Category>();

		CategoryManager catMan = CategoryManager.getInstance();
		try {

			catList = catMan.getAllCategories();
			try {
				for (int i = 0; i < catList.size(); i++) {
					category = catList.get(i);
					try {
						JSONObject jsonCat = JSONUtil.ObjectToJSONObject(category);
						jsonCat = modifyCategoryResponse(jsonCat);
						jsonArray.put(jsonCat);
					}
					catch (JSONException e) {
						message = e.getMessage();
						statusCode = 500;
						return Response.status(statusCode).entity(message).build();
					}
				}
				message = jsonArray.toString();
			}
			catch (EMAnalyticsWSException e) {
				message = e.getMessage();
				statusCode = e.getStatusCode();
				return Response.status(statusCode).entity(message).build();
			}
		}
		catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
		}
		return Response.status(statusCode).entity(message).build();
	}

	/**
	 * List all searches which has the given folder Id<br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port
	 * number&gt;/savedsearch/v1/entities?folderId=&lt;folderId&gt;</font><br>
	 * The string "entities?folderId=&lt;folder Id&gt;" in the URL signifies get the search operation on search with given folder
	 * id and category name, and all the listed searches will order by the given field name <br>
	 *
	 * @since 0.1
	 * @param id
	 *            The folder Id which user wants to get the search details
	 * @return Lists all the searches with the given folder Id<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">[<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 9998,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"name": "Demo Search 1",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "search",<br>
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
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/search/9998"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }<br>
	 *         ]</font><br>
	 */
	@GET
	@Path("/entities")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDetails(@QueryParam("folderId") String id)
	{
		String message = null;
		Long folderId = 0L;
		if (id == null) {
			return Response.status(400).entity("please give folderId").build();
		}
		else if (id != null && id.equals("")) {
			return Response.status(400).entity("please give folderId").build();
		}
		try {

			if (id != null && !id.equals("")) {
				folderId = Long.parseLong(id);
			}
		}
		catch (NumberFormatException e) {
			return Response.status(400).entity("Folder Id should be a numeric and not alphanumeric").build();
		}
		if (folderId != 0L) {
			try {
				return Response.status(200).entity(getFolderDetails(folderId)).build();
			}
			catch (EMAnalyticsFwkException e) {
				e.printStackTrace();
				return Response.status(e.getStatusCode()).entity(e.getMessage()).build();

			}
			catch (JSONException e) {
				e.printStackTrace();
				return Response.status(500).entity(e.getMessage()).build();
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
		return Response.status(200).entity(message).build();
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
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "folder",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "createdOn": "2014-07-03T11:07:21.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "description": "Search Console Root Folder",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastModifiedOn": "2014-07-03T11:07:21.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "href": "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/1" <br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }<br>
	 *         ]</font><br>
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRootFolders()
	{

		String message = "";
		try {
			message = getFolderDetails(0);
		}
		catch (EMAnalyticsFwkException e) {
			e.printStackTrace();
			return Response.status(e.getStatusCode()).entity(e.getMessage()).build();

		}
		catch (JSONException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(message).build();

	}

	private String getFolderDetails(long id) throws EMAnalyticsFwkException, JSONException, UnsupportedEncodingException
	{

		String message = new String();
		FolderManager fman = FolderManager.getInstance();
		SearchManager sman = SearchManager.getInstance();
		JSONArray jsonArray = new JSONArray();
		if (id != 0) {
			fman.getFolder(id);
		}
		JSONObject jsonObj;
		List<Folder> folderList = fman.getSubFolders(id);
		for (Folder folderObj : folderList) {
			jsonObj = new JSONObject();
			jsonObj.put("id", folderObj.getId());
			jsonObj.put("name", folderObj.getName());
			jsonObj.put("type", folder);
			jsonObj.put("createdOn", JSONUtil.getDate(folderObj.getCreatedOn().getTime()));
			jsonObj.put("description", folderObj.getDescription());
			jsonObj.put("lastModifiedOn", JSONUtil.getDate(folderObj.getLastModifiedOn().getTime()));
			StringBuilder linkBuilder = new StringBuilder(uri.getBaseUri() + "folder/" + folderObj.getId());

			jsonObj.put("href", linkBuilder.toString());
			jsonArray.put(jsonObj);
		}

		// Get Searches too !!
		List<Search> searchList;

		searchList = sman.getSearchListByFolderIdCategoryFilter(id);

		for (Search searchObj : searchList) {
			jsonObj = new JSONObject();
			jsonObj.put("id", searchObj.getId());
			jsonObj.put("name", searchObj.getName());
			jsonObj.put("type", search);
			JSONObject categoryObj = new JSONObject();
			categoryObj.put("id", searchObj.getCategoryId());
			categoryObj.put("href", uri.getBaseUri() + "category/" + searchObj.getCategoryId());
			// jsonObj.put("categoryId", searchObj.getCategoryId());
			jsonObj.put("category", categoryObj);
			JSONObject folderObj = new JSONObject();
			folderObj.put("id", searchObj.getFolderId());
			folderObj.put("href", uri.getBaseUri() + "folder/" + searchObj.getCategoryId());
			jsonObj.put("folder", folderObj);
			// jsonObj.put("folderId", searchObj.getFolderId());
			jsonObj.put("createdOn", JSONUtil.getDate(searchObj.getCreatedOn().getTime()));
			jsonObj.put("description", searchObj.getDescription());
			jsonObj.put("lastModiedOn", JSONUtil.getDate(searchObj.getLastModifiedOn().getTime()));
			jsonObj.put("href", uri.getBaseUri() + "search/" + searchObj.getId());
			jsonArray.put(jsonObj);
		}
		message = jsonArray.toString(1);

		return message;

	}

	private JSONObject modifyCategoryResponse(JSONObject jsonObj) throws JSONException
	{
		JSONObject rtnObj = new JSONObject();
		rtnObj.put("id", jsonObj.optInt("id"));
		rtnObj.put("name", jsonObj.getString("name"));
		if (jsonObj.has("description")) {
			rtnObj.put("description", jsonObj.getString("description"));
		}
		if (jsonObj.has("defaultFolderId")) {
			JSONObject fold = new JSONObject();
			fold.put("id", jsonObj.optInt("defaultFolderId"));
			fold.put("href", uri.getBaseUri() + "folder/" + jsonObj.getInt("defaultFolderId"));
			rtnObj.put("defaultFolder", fold);
		}
		if (jsonObj.has("parameters")) {
			rtnObj.put("parameters", jsonObj.getJSONArray("parameters"));
		}
		rtnObj.put("href", uri.getBaseUri() + "category/" + jsonObj.getInt("id"));
		return rtnObj;
	}

}
