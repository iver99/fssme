package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search;

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
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JSONUtil;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("searches")
public class FilterSearchAPI {
	
	@Context
	UriInfo uri;
	
	private final String search = "search";
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSearches(@Context UriInfo uri,
			@QueryParam("categoryId") String catId,
			@QueryParam("categoryName") String name,
			@QueryParam("folderId") String foldId) {
		CategoryManager catMan = CategoryManager.getInstance();
		int categId = 0;
		int folId = 0;
		Category category;
		String message = "";
		String key = "";
		String value;
		String query = uri.getRequestUri().getQuery();
		if(query ==null)
		return getLastAccessSearch(0);
		String[] param = query.split("&");
		if (param.length > 0) {
			String firstParam = param[0];
			String[] input = firstParam.split("=");
			if (input.length >= 2) {
				key = input[0];
				value = input[1];
			} else
				return Response.status(400)
						.entity("Please give the value for " + input[0]).build();

			try {
				if (key.equals("categoryId") && value != null) {
					categId = Integer.parseInt(value);
					if(categId <0)
						throw new NumberFormatException();
					return getAllSearchByCategory(categId);
				} else if (key.equals("folderId") && value != null) {
					folId = Integer.parseInt(value);
					if(folId <0)
						throw new NumberFormatException();
					return getAllSearchByFolder(folId);
				}else if(key.equals("lastAccessCount") && value!= null){
					int count=Integer.parseInt(value);
					if(count < 0)
						throw new NumberFormatException();
					if(count == 0)
						return Response.status(200).build();
					return getLastAccessSearch(count);
				}
				else if (key.equals("categoryName") && value != null) {
					try {
						category = catMan.getCategory(value);
						categId = category.getId();
						return getAllSearchByCategory(categId);
					} catch (EMAnalyticsFwkException e) {
						message = e.getMessage();
						return Response.status(e.getStatusCode())
								.entity(message).build();
					}

				} else
					return Response
							.status(400)
							.entity("please give either categoryId,categoryName,folderId or lastAccessCount")
							.build();
			} catch (NumberFormatException e) {
				return Response.status(400)
						.entity("Id/count should be a positive number and not an alphanumeric.")
						.build();
			}
		} else
			return Response
					.status(400)
					.entity("please give either categoryId,categoryName,folderId,lastAccessDate")
					.build();

			}

	private Response getAllSearchByCategory(int catId) {
		SearchManager searchMan = SearchManager.getInstance();
		CategoryManager catMan = CategoryManager.getInstance();
		Search search;
		int statusCode = 200;
		String message = "";
		JSONArray jsonArray = new JSONArray();
		List<Search> searchList = new ArrayList<Search>();

		try {
			// just for checking whether category with given Id exist or not
			catMan.getCategory(catId);
			searchList = searchMan.getSearchListByCategoryId(catId);
		} catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			return Response.status(statusCode).entity(message).build();
		}

		try {
			for (int i = 0; i < searchList.size(); i++) {
				search = searchList.get(i);
				try {
					JSONObject jsonObj=JSONUtil.ObjectToJSONObject(search);
					jsonObj=modifyObject(jsonObj);
					jsonObj.put("href", uri.getBaseUri() + "search/" + search.getId());
					jsonArray.put(jsonObj);
				} catch (JSONException e) {
					message = e.getMessage();
					statusCode = 500;
					return Response.status(statusCode).entity(message).build();
				}
			}
			message = jsonArray.toString();
		} catch (EMAnalyticsWSException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			return Response.status(statusCode).entity(message).build();
		}
		return Response.status(statusCode).entity(message).build();

	}

	private Response getAllSearchByFolder(int foldId) {
		SearchManager searchMan = SearchManager.getInstance();
		FolderManager foldMan = FolderManager.getInstance();
		Search search;

		String message = "";
		int statusCode = 200;
		JSONArray jsonArray = new JSONArray();
		List<Search> searchList = new ArrayList<Search>();

		try {
			// just for checking whether folder with given Id exist or not
			foldMan.getFolder(foldId);
			searchList = searchMan.getSearchListByFolderId(foldId);
		} catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			return Response.status(statusCode).entity(message).build();
		}

		try {
			for (int i = 0; i < searchList.size(); i++) {
				search = searchList.get(i);
				try {
					JSONObject jsonObj=JSONUtil.ObjectToJSONObject(search);
					jsonObj=modifyObject(jsonObj);
					jsonObj.put("href", uri.getBaseUri() + "search/" + search.getId());
					jsonArray.put(jsonObj);
				} catch (JSONException e) {
					message = e.getMessage();
					statusCode = 500;
					return Response.status(statusCode).entity(message).build();
				}
			}
			message = jsonArray.toString();
		} catch (EMAnalyticsWSException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			return Response.status(statusCode).entity(message).build();
		}
		return Response.status(statusCode).entity(message).build();
	}
	private Response getLastAccessSearch(int count){
		String message="";
		JSONArray jsonArray = new JSONArray();
		try {
			List<Search> searchList = SearchManager.getInstance()
					.getSearchListByLastAccessDate(count);
			for (Search searchObj : searchList) {
				JSONObject jsonObj=JSONUtil.ObjectToJSONObject(searchObj);
				jsonObj=modifyObject(jsonObj);
				FolderManager folderMgr = FolderManager.getInstance();	
				 JSONArray jsonPathArray = new JSONArray();
				 String[] pathArray = folderMgr.getPathForFolderId(searchObj.getFolderId());
				 for(String p: pathArray)
					 jsonPathArray.put(p);
				jsonObj.put("flattenedFolderPath", jsonPathArray);	 
				jsonObj.put("href", uri.getBaseUri() + "search/" + searchObj.getId());
				
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
	
	private JSONObject modifyObject(JSONObject jsonObj) throws JSONException{
		JSONObject rtnObj=new JSONObject();
		JSONObject jsonCat= new JSONObject();
		jsonCat.put("id", jsonObj.getInt("categoryId"));
		jsonCat.put("href",uri.getBaseUri() +"category/" +jsonObj.getInt("categoryId"));
		JSONObject jsonFold = new JSONObject();
		jsonFold.put("id", jsonObj.getInt("folderId"));
		jsonFold.put("href",uri.getBaseUri() +"folder/" +jsonObj.getInt("folderId"));
		jsonObj.put("createdOn", JSONUtil.getDate(Long.parseLong(jsonObj.getString("createdOn"))));
		jsonObj.put("lastModifiedOn", JSONUtil.getDate(Long.parseLong(jsonObj.getString("lastModifiedOn"))));
		rtnObj.put("id", jsonObj.optInt("id"));
		rtnObj.put("name", jsonObj.optString("name"));
		if(jsonObj.has("description"))
			rtnObj.put("description",jsonObj.getString("description"));
		rtnObj.put("category",jsonCat);
		rtnObj.put("folder",jsonFold);
		rtnObj.put("owner", jsonObj.getString("owner"));
		rtnObj.put("createdOn",jsonObj.optString("createdOn"));
		rtnObj.put("lastModifiedBy",jsonObj.optString("lastModifiedBy"));
		rtnObj.put("lastModifiedOn", jsonObj.optString("lastModifiedOn"));
		if(jsonObj.has("lastAccessDate"))
			rtnObj.put("lastAccessDate", JSONUtil.getDate(Long.parseLong(jsonObj.getString("lastAccessDate"))));
		if(jsonObj.has("queryStr"))
			rtnObj.put("queryStr", jsonObj.getString("queryStr"));
		if(jsonObj.has("parameters"))
		rtnObj.put("parameters", jsonObj.getJSONArray("parameters"));
		return rtnObj;
	}
}
