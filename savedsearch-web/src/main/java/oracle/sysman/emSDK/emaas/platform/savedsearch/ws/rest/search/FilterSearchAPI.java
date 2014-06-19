package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

@Path("findsearch")
public class FilterSearchAPI {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/category")
	public Response getAllSearchByCategory(@QueryParam("id") String catId,@QueryParam("name") String name){
		SearchManager searchMan=SearchManager.getInstance();
		CategoryManager catMan=CategoryManager.getInstance();
		Search search;
		int id=0;
		Category category;
		String message="";
		
			if(catId==null && name== null){
				return Response.status(400).entity("please give either category id or name").build();
			}
			else if(catId !=null && catId.equals("") && name !=null && name.equals("") )
				return Response.status(400).entity("please give either category id or name").build();
			else if(catId ==null && name !=null && name.equals("") )
				return Response.status(400).entity("please give either category id or name").build();
			else if(name ==null && catId !=null && catId.equals(""))
				return Response.status(400).entity("please give either category id or name").build();
		
		
		try{	
			
			if(catId !=null && !catId.equals(""))
			id=Integer.parseInt(catId);
		}
		catch(NumberFormatException e){
			return Response.status(404).entity("Category with id: "+catId +" does not exist").build();
		}
		int statusCode=200;
		JSONArray jsonArray=new JSONArray();
		List<Search> searchList=new ArrayList<Search>();
		
		if(id==0 && name !=null){

			try {
				category=catMan.getCategory(name);
				id=category.getId();
			} catch (EMAnalyticsFwkException e) {
				message=e.getMessage();
				statusCode=e.getStatusCode();
				return Response.status(statusCode).entity(message).build();
			}
		}
		try {
			//just for checking whether category with given Id exist or not
			category=catMan.getCategory(id);
			searchList=searchMan.getSearchListByCategoryId(id);
		} catch (EMAnalyticsFwkException e) {
			message=e.getMessage();
			statusCode=e.getStatusCode();
			return Response.status(statusCode).entity(message).build();
		}


		try {
			for(int i=0;i<searchList.size();i++)
			{
				search=searchList.get(i);
				try {
					jsonArray.put(JSONUtil.ObjectToJSONObject(search));
				} catch (JSONException e) {
					message=e.getMessage();
					statusCode=500;
					return Response.status(statusCode).entity(message).build();
				}
			}
			message=jsonArray.toString();
		} catch (EMAnalyticsWSException e) {
			message=e.getMessage();
			statusCode=e.getStatusCode();
			return Response.status(statusCode).entity(message).build();
		}
		return Response.status(statusCode).entity(message).build();
	}

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/folder")
	public Response getAllSearchByFolder(@QueryParam("id") String foldId){
		SearchManager searchMan=SearchManager.getInstance();
		FolderManager foldMan=FolderManager.getInstance();
		Search search;
		int id=0;
	
		String message="";
		if(foldId == null)
			return Response.status(400).entity("please give folder id").build();
		else if(foldId !=null &&foldId.equals(""))
			return Response.status(400).entity("please give folder id").build();
		
		
		try{	
			
			if(foldId !=null && !foldId.equals(""))
			id=Integer.parseInt(foldId);
		}
		catch(NumberFormatException e){
			return Response.status(404).entity("folder with id: "+foldId +" does not exist").build();
		}
		int statusCode=200;
		JSONArray jsonArray=new JSONArray();
		List<Search> searchList=new ArrayList<Search>();
		
		/*if(id==0 && name !=null){

			try {
				folder=foldMan.getCategory(name);
				id=category.getId();
			} catch (EMAnalyticsFwkException e) {
				message=e.getMessage();
				statusCode=e.getStatusCode();
				return Response.status(statusCode).entity(message).build();
			}
		}*/
		try {
			//just for checking whether category with given Id exist or not
			foldMan.getFolder(id);
			searchList=searchMan.getSearchListByFolderId(id);
		} catch (EMAnalyticsFwkException e) {
			message=e.getMessage();
			statusCode=e.getStatusCode();
			return Response.status(statusCode).entity(message).build();
		}


		try {
			for(int i=0;i<searchList.size();i++)
			{
				search=searchList.get(i);
				try {
					jsonArray.put(JSONUtil.ObjectToJSONObject(search));
				} catch (JSONException e) {
					message=e.getMessage();
					statusCode=500;
					return Response.status(statusCode).entity(message).build();
				}
			}
			message=jsonArray.toString();
		} catch (EMAnalyticsWSException e) {
			message=e.getMessage();
			statusCode=e.getStatusCode();
			return Response.status(statusCode).entity(message).build();
		}
		return Response.status(statusCode).entity(message).build();
	}

}
