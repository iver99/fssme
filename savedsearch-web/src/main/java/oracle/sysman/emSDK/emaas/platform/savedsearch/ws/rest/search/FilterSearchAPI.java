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

@Path("searches")
public class FilterSearchAPI {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSearchByCategory(@QueryParam("categoryId") String catId,@QueryParam("categoryName") String name,@QueryParam("folderId") String foldId){
		SearchManager searchMan=SearchManager.getInstance();
		CategoryManager catMan=CategoryManager.getInstance();
		Search search;
		int categId=0;
		int folId=0;
		Category category;
		String message="";
		
			if(catId==null && name== null && foldId == null){
				return Response.status(400).entity("please give either categoryId,categoryName or folderId").build();
			}
			else if(catId !=null && catId.equals("") && name !=null && name.equals("") && foldId !=null && foldId.equals(""))
				return Response.status(400).entity("please give either categoryId,categoryName or folderId").build();
			else if(catId ==null && foldId ==null && name !=null && name.equals("") )
				return Response.status(400).entity("please give either categoryId,categoryName or folderId").build();
			else if(name ==null && foldId == null && catId !=null && catId.equals(""))
				return Response.status(400).entity("please give either categoryId,categoryName or folderId").build();
			else if(name ==null && catId == null && foldId !=null && foldId.equals(""))
				return Response.status(400).entity("please give either categoryId,categoryName or folderId").build();
		
		
		try{	
			
			if(catId !=null && !catId.equals(""))
			categId=Integer.parseInt(catId);
			if(foldId !=null && !foldId.equals(""))
				folId=Integer.parseInt(foldId);
		}
		catch(NumberFormatException e){
			return Response.status(404).entity("Id should be a numeric and not alphanumeric.").build();
		}
		int statusCode=200;
		JSONArray jsonArray=new JSONArray();
		List<Search> searchList=new ArrayList<Search>();
		if(folId !=0){
			return getAllSearchByFolder(folId);
		}
		if(categId==0 && name !=null){

			try {
				category=catMan.getCategory(name);
				categId=category.getId();
			} catch (EMAnalyticsFwkException e) {
				message=e.getMessage();
				statusCode=e.getStatusCode();
				return Response.status(statusCode).entity(message).build();
			}
		}
		try {
			//just for checking whether category with given Id exist or not
			category=catMan.getCategory(categId);
			searchList=searchMan.getSearchListByCategoryId(categId);
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

	
	
	private Response getAllSearchByFolder(int foldId){
		SearchManager searchMan=SearchManager.getInstance();
		FolderManager foldMan=FolderManager.getInstance();
		Search search;
		
	
		String message="";
		/*if(foldId == null)
			return Response.status(400).entity("Please give folder Id").build();
		else if(foldId !=null &&foldId.equals(""))
			return Response.status(400).entity("Please give folder Id").build();
		
		
		try{	
			
			if(foldId !=null && !foldId.equals(""))
			id=Integer.parseInt(foldId);
		}
		catch(NumberFormatException e){
			return Response.status(400).entity("Folder Id should be a numeric and not alphanumeric").build();
		}*/
		int statusCode=200;
		JSONArray jsonArray=new JSONArray();
		List<Search> searchList=new ArrayList<Search>();
		
				try {
			//just for checking whether folder with given Id exist or not
			foldMan.getFolder(foldId);
			searchList=searchMan.getSearchListByFolderId(foldId);
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
