package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.navigation;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

@Path("")
public class SavedSearchAPI {
	private final String search = "search";
	private final String folder = "folder";
	private final String ORDERBY_SEPARATOR = ",";
	private static final String FOLDER_PATH = "flattenedFolderPath";
	@Context
	UriInfo uri;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRootFolders(@QueryParam("category") String catName,
			@QueryParam("orderby") String orderBy) {

		String message = "";
		try {
			message = getFolderDetails(0, catName, orderBy);
		} catch (EMAnalyticsFwkException e) {
			e.printStackTrace();
			return Response.status(e.getStatusCode()).entity(e.getMessage())
					.build();

		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(message).build();

	}

	@GET
	@Path("/entities")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDetails(@QueryParam("folderId") String id,
			@QueryParam("category") String catName,
			@QueryParam("orderby") String orderBy) {
			String message = null;
			Long folderId=0L;
		if (id == null)
			return Response.status(400).entity("please give folderId").build();
		else if(id != null && id.equals(""))
			return Response.status(400).entity("please give folderId").build();
		try{	
			
			if(id !=null && !id.equals(""))
			folderId=Long.parseLong(id);
			}
		catch(NumberFormatException e){
			return Response.status(400).entity("Folder Id should be a numeric and not alphanumeric").build();
		}
		if (folderId !=0L) {
			try {
				return Response.status(200)
						.entity(getFolderDetails(folderId, catName, orderBy)).build();
			} catch (EMAnalyticsFwkException e) {
				e.printStackTrace();
				return Response.status(e.getStatusCode())
						.entity(e.getMessage()).build();

			} catch (JSONException e) {
				e.printStackTrace();
				return Response.status(500).entity(e.getMessage()).build();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
				return Response.status(200).entity(message).build();
	}

	private String getFolderDetails(long id, String catName, String orderBy)
			throws EMAnalyticsFwkException, JSONException,
			UnsupportedEncodingException {

		String message = new String();
		FolderManager fman = FolderManager.getInstance();
		SearchManager sman = SearchManager.getInstance();
		JSONArray jsonArray = new JSONArray();
		if (id != 0)
			fman.getFolder(id);
		JSONObject jsonObj;
		List<Folder> folderList = fman.getSubFolders(id);
		for (Folder folderObj : folderList) {
			jsonObj = new JSONObject();
			jsonObj.put("id", folderObj.getId());
			jsonObj.put("name", folderObj.getName());
			jsonObj.put("type", folder);
			StringBuilder linkBuilder = new StringBuilder(uri.getBaseUri() + "entities?folderId=" + folderObj.getId() );
			if (catName != null && catName.trim().length() != 0)
				linkBuilder.append("&category="
						+ URLEncoder.encode(catName, "UTF-8"));
			if (orderBy != null && orderBy.trim().length() != 0)
				linkBuilder.append("&orderby="
						+ URLEncoder.encode(orderBy, "UTF-8"));

			jsonObj.put("href", linkBuilder.toString());

			jsonArray.put(jsonObj);
		}

		// Get Searches too !!
		List<Search> searchList;
		if (orderBy != null)
			searchList = sman.getSearchListByFolderIdCategoryFilter(id,
					catName, orderBy.split(ORDERBY_SEPARATOR));
		else
			searchList = sman.getSearchListByFolderIdCategoryFilter(id,
					catName, null);
		for (Search searchObj : searchList) {
			jsonObj = new JSONObject();
			jsonObj.put("id", searchObj.getId());
			jsonObj.put("name", searchObj.getName());
			jsonObj.put("type", search);
			jsonObj.put("categoryId", searchObj.getCategoryId());
			jsonObj.put("folderId", searchObj.getFolderId());
			jsonObj.put("href", uri.getBaseUri() + "search/" + searchObj.getId());
			FolderManager folderMgr = FolderManager.getInstance();
			JSONArray jsonPathArray = new JSONArray();
			String[] pathArray = folderMgr.getPathForFolderId(searchObj
					.getFolderId());
			for (String p : pathArray) {
				jsonPathArray.put(p);
			}
			jsonObj.put(FOLDER_PATH, jsonPathArray);
			jsonArray.put(jsonObj);
		}
		message = jsonArray.toString(1);

		return message;

	}

	@GET
	@Path("lastaccess/searches/{id: [0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchListByAccessTime(@PathParam("id") int id) {
		String message = "";
		JSONObject jsonObj;
		JSONArray jsonArray = new JSONArray();
		try {
			List<Search> searchList = SearchManager.getInstance()
					.getSearchListByLastAccessDate(id);
			for (Search searchObj : searchList) {
				jsonObj = new JSONObject();
				jsonObj.put("id", searchObj.getId());
				jsonObj.put("name", searchObj.getName());
				jsonObj.put("description", searchObj.getDescription());
				jsonObj.put("type", search);
				jsonObj.put("categoryId", searchObj.getCategoryId());
				jsonObj.put("folderId", searchObj.getFolderId());
				jsonObj.put("lastAccessDate", searchObj.getLastAccessDate()
						.getTime());
				jsonObj.put("href", uri.getBaseUri() + "search/" + searchObj.getId());
				FolderManager folderMgr = FolderManager.getInstance();
				JSONArray jsonPathArray = new JSONArray();
				String[] folderPath = folderMgr.getPathForFolderId(searchObj
						.getFolderId());
				for (String p : folderPath) {
					jsonPathArray.put(p);
				}
				jsonObj.put(FOLDER_PATH, jsonPathArray);

				jsonArray.put(jsonObj);
			}
			message = jsonArray.toString(1);
		} catch (EMAnalyticsFwkException e) {
			e.printStackTrace();
			return Response.status(e.getStatusCode()).entity(e.getMessage())
					.build();

		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(message).build();
	}

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
						jsonArray.put(JSONUtil.ObjectToJSONObject(category));
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

	@PUT
	@Path("lastaccess/search/{id: [0-9]*}")
	public Response editSearchAccessDate(@PathParam("id") long searchId) {
		String message = null;
		int statusCode = 200;
		try {

			SearchManager sman = SearchManager.getInstance();
			java.util.Date date = sman.modifyLastAccessDate(searchId);
			message = String.valueOf(date.getTime());

		} catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
		}
		return Response.status(statusCode).entity(message).build();

	}

}
