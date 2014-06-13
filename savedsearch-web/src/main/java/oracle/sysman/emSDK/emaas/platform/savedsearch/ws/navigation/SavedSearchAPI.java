package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.navigation;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
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
public class SavedSearchAPI
{
	private final String search = "search";
	private final String folder = "folder";
	private final String ORDERBY_SEPARATOR = ",";
	@Context
	UriInfo uri;

	@GET
	@Path("{id: [0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDetails(@PathParam("id") long id, @QueryParam("type") String type, @QueryParam("category") String catName,
			@QueryParam("orderby") String orderBy)
	{
		String message = null;
		if (type == null) {
			return Response.status(400).entity("please specify Type").build();
		}
		if (type.equals(folder)) {
			try {
				return Response.status(200).entity(getFolderDetails(id, catName, orderBy)).build();
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
		if (type.equals(search)) {
			SearchManager sman = SearchManager.getInstance();
			Search searchObj = null;

			try {
				searchObj = sman.getSearch(id);
			}
			catch (EMAnalyticsFwkException e) {
				e.printStackTrace();
				return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
			}
			try {
				JSONObject jsonObj = JSONUtil.ObjectToJSONObject(searchObj);
				jsonObj.put("type", search);
				jsonObj.put("link", uri.getBaseUri().toString() + searchObj.getId() + "?type=" + search);
				message = jsonObj.toString(1);

			}
			catch (EMAnalyticsWSException e) {
				e.printStackTrace();
				return Response.status(e.getStatusCode()).entity(e.getMessage()).build();

			}
			catch (JSONException e) {
				e.printStackTrace();
				return Response.status(500).entity(e.getMessage()).build();
			}

		}
		else {
			return Response.status(400).entity("Type not supported").build();
		}
		return Response.status(200).entity(message).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRootFolders(@QueryParam("category") String catName, @QueryParam("orderby") String orderBy)
	{

		String message = "";
		try {
			message = getFolderDetails(0, catName, orderBy);
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

	private String getFolderDetails(long id, String catName, String orderBy) throws EMAnalyticsFwkException, JSONException,
			UnsupportedEncodingException
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
			StringBuilder linkBuilder = new StringBuilder(uri.getBaseUri().toString() + folderObj.getId() + "?type=" + folder);
			if (catName != null && catName.trim().length() != 0) {
				linkBuilder.append("&category=" + URLEncoder.encode(catName, "UTF-8"));
			}
			if (orderBy != null && orderBy.trim().length() != 0) {
				linkBuilder.append("&orderby=" + URLEncoder.encode(orderBy, "UTF-8"));
			}

			jsonObj.put("link", linkBuilder.toString());

			jsonArray.put(jsonObj);
		}

		// Get Searches too !!
		List<Search> searchList;
		if (orderBy != null) {
			searchList = sman.getSearchListByFolderIdCategoryFilter(id, catName, orderBy.split(ORDERBY_SEPARATOR));
		}
		else {
			searchList = sman.getSearchListByFolderIdCategoryFilter(id, catName, null);
		}
		for (Search searchObj : searchList) {
			jsonObj = new JSONObject();
			jsonObj.put("id", searchObj.getId());
			jsonObj.put("name", searchObj.getName());
			jsonObj.put("type", search);
			jsonObj.put("categoryId", searchObj.getCategoryId());
			jsonObj.put("link", uri.getBaseUri().toString() + searchObj.getId() + "?type=" + search);
			jsonArray.put(jsonObj);
		}
		message = jsonArray.toString(1);

		return message;

	}

}
