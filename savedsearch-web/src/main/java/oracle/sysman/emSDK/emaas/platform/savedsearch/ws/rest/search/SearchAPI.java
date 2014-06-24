package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JSONUtil;

@Path("search")
public class SearchAPI {

	private static final String FOLDER_PATH="flattenedFolderPath";
	
	@GET
	@Path("{id: [0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearch( @PathParam("id") long searchid , @QueryParam("flattenedFolderPath") boolean bPath ) {
		String message=null;
		int statusCode=200;		
		JSONObject jsonObj ;
		SearchManager sman = SearchManager.getInstance();
		try {
			
		 
			Search searchObj = sman.getSearch(searchid);
			jsonObj =JSONUtil.ObjectToJSONObject(searchObj);		
			
			if(bPath)
			{  
				 FolderManager folderMgr = FolderManager.getInstance();	
				 JSONArray jsonPathArray = new JSONArray();
				 String[] pathArray = folderMgr.getPathForFolderId(searchObj.getFolderId());
				 for(String p: pathArray){
					 jsonPathArray.put(p);
				 }
				 jsonObj.put(FOLDER_PATH, jsonPathArray);				 
				
			}
			 message=jsonObj.toString();
		} catch (EMAnalyticsFwkException e) {
			message=e.getMessage();
			statusCode=e.getStatusCode();
		}catch(EMAnalyticsWSException e){
			message=e.getMessage();
			statusCode=e.getStatusCode();
		} catch (JSONException e) {
			statusCode=404;
		}
		
        return Response.status(statusCode).entity(message).build();
    }
		
	@DELETE
	@Path("{id: [0-9]*}")
	public Response deleteSearch(@PathParam("id") long searchId ) {
		int statusCode=204;
		
		SearchManager sman = SearchManager.getInstance();
		try {
			sman.deleteSearch(searchId);
			
		} catch (EMAnalyticsFwkException e) {
			return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
			
		}
		
		return Response.status(statusCode).build();
    }
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createSearch(JSONObject inputJsonObj ) 
	 {
		String message="";
		int statusCode=201;
		SearchManager sman =SearchManager.getInstance() ;
		try {
			Search searchObj = createSearchObjectForAdd(inputJsonObj);
			Search savedSearch = sman.saveSearch(searchObj);
			message = JSONUtil.ObjectToJSONString(savedSearch);			 
		} catch (EMAnalyticsFwkException e) {
			message=e.getMessage();
			statusCode=e.getStatusCode();
		}catch(EMAnalyticsWSException e){
			message=e.getMessage();
			statusCode=e.getStatusCode();
		}
		return Response.status(statusCode).entity(message).build() ;
	}
    
	@PUT
	@Path("{id: [0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editSearch(JSONObject inputJsonObj, @PathParam("id") long searchId)
	{
		String message=null;
		int statusCode=200;
		SearchManager sman =SearchManager.getInstance() ;
		try {
			Search searchObj = createSearchObjectForEdit(inputJsonObj, sman.getSearch(searchId));
		    Search savedSearch = sman.editSearch(searchObj);
			message  = JSONUtil.ObjectToJSONString(savedSearch);
		}
		catch (EMAnalyticsFwkException e) {
				message=e.getMessage();
				statusCode=e.getStatusCode();
		}catch(EMAnalyticsWSException e){
			message=e.getMessage();
			statusCode=e.getStatusCode();
		}
		
		return Response.status(statusCode).entity(message).build() ;
		
		
	}
	
	
	private Search createSearchObjectForAdd(JSONObject json) throws EMAnalyticsWSException {
		Search searchObj = new SearchImpl();
		// Data population !
			try{
				String name=json.getString("name");
				if(name == null)
					throw new EMAnalyticsWSException("The name key for search can not be null in the input JSON Object",EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING);
				if(name !=null && name.equals(""))
					throw new EMAnalyticsWSException("The name key for search can not be empty in the input JSON Object",EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING);
				searchObj.setName(name);
			}
			catch(JSONException je){
				throw new EMAnalyticsWSException("The name key for search is missing in the input JSON Object",EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING,je);
			}
			 
			try{
				searchObj.setCategoryId(Integer.parseInt(json.getString("categoryId")));
				}
				catch(JSONException je){
					throw new EMAnalyticsWSException("The categoryId key for search is missing in the input JSON Object",EMAnalyticsWSException.JSON_SEARCH_CATEGORY_ID_MISSING,je);
				}
			try{
				searchObj.setFolderId(Integer.parseInt(json.getString("folderId" ) ) );
				}
				catch(JSONException je){
					throw new EMAnalyticsWSException("The folderId key for search is missing in the input JSON Object",EMAnalyticsWSException.JSON_SEARCH_FOLDER_ID_MISSING,je);
				}
		    
		    	    
		    
		    // Nullable properties !
		    searchObj.setMetadata(json.optString("metadata", searchObj.getMetadata()));
		    searchObj.setQueryStr(json.optString("queryStr", searchObj.getQueryStr()));
		    searchObj.setDescription(json.optString("description", searchObj.getDescription() ));
		    // non-nullable with db defaults !!
		    searchObj.setLocked(Boolean.parseBoolean(json.optString("locked", Boolean.toString(searchObj.isLocked() ) ) ) );
		    searchObj.setUiHidden( Boolean.parseBoolean(json.optString("uiHidden" , Boolean.toString(searchObj.isUiHidden() ) ) ) );
		    
		    // Parameters
		    if(json.has("parameters"))
		    {
			    JSONArray jsonArr = json.optJSONArray("parameters");
			    List<SearchParameter> searchParamList = new ArrayList<SearchParameter>() ;
			    // FIltering values with Map .. not exactly required .. duplicate params if any will be discarded at persistence layer !!
			  	String type;	    
			    for(int i=0 ; i < jsonArr.length() ; i++)
			    {
			    	SearchParameter searchParam = new SearchParameter();
			    	JSONObject jsonParam = jsonArr.optJSONObject(i);
			    	if(jsonParam.has("attributes"))
			    		searchParam.setAttributes(jsonParam.optString("attributes"));
			    	try{
			    		String name=jsonParam.getString("name");
						if(name == null)
							throw new EMAnalyticsWSException("The name key for search param can not be null in the input JSON Object",EMAnalyticsWSException.JSON_SEARCH_PARAM_NAME_MISSING);
						if(name !=null && name.equals(""))
							throw new EMAnalyticsWSException("The name key for search param can not be empty in the input JSON Object",EMAnalyticsWSException.JSON_SEARCH_PARAM_NAME_MISSING);
					
			    		searchParam.setName(name);
						}
					catch(JSONException je){
						throw new EMAnalyticsWSException("The name key for search parameter is missing in the input JSON Object",EMAnalyticsWSException.JSON_SEARCH_PARAM_NAME_MISSING,je);
					}
			    	try{
			    		type=jsonParam.getString("type");
			    	}
			    	catch(JSONException je){
						throw new EMAnalyticsWSException("The type key for search param is missing in the input JSON Object",EMAnalyticsWSException.JSON_SEARCH_PARAM_TYPE_MISSING,je);
					}
			    	if(type.equals("STRING") | type.equals("CLOB"))
			    		searchParam.setType(ParameterType.valueOf(type));
			    	else
			    		throw new EMAnalyticsWSException("Invalid param type, please specify either STRING or CLOB", EMAnalyticsWSException.JSON_SEARCH_PARAM_TYPE_INVALID);
			    	
			    	searchParam.setValue(jsonParam.optString("value"));
			    	searchParamList.add(searchParam);
			    	
			    }
			    searchObj.setParameters(searchParamList);
			    
		    }
		    else
		    	searchObj.setParameters(null);
		    
		 
		return searchObj;
	}

	private Search createSearchObjectForEdit(JSONObject json, Search searchObj) throws EMAnalyticsWSException
	{
		// Data population !
		 
			 /*
			  * Quick Note:
			  * json.optString() returns second String if the key is not found 
			  * Useful for edit
			  */
			searchObj.setName(json.optString("name", searchObj.getName() ));
		    searchObj.setDescription(json.optString("description", searchObj.getDescription() ));
		    
		    searchObj.setCategoryId(Integer.parseInt(json.optString("categoryId", searchObj.getCategoryId().toString() )));
		    searchObj.setFolderId(Integer.parseInt(json.optString("folderId" , searchObj.getFolderId().toString() ) ) );
		        
		    searchObj.setLocked(Boolean.parseBoolean(json.optString("locked", Boolean.toString(searchObj.isLocked() ) ) ) );
		    searchObj.setUiHidden( Boolean.parseBoolean(json.optString("uiHidden" , Boolean.toString(searchObj.isUiHidden() ) ) ) );
		    
		    // Nullable properties !
		    searchObj.setMetadata(json.optString("metadata", searchObj.getMetadata()));
		    searchObj.setQueryStr(json.optString("queryStr", searchObj.getQueryStr()));
		    
		    // Parameters
		    if(json.has("parameters"))
		    {
			    JSONArray jsonArr = json.optJSONArray("parameters");
			    List<SearchParameter> searchParamList = new ArrayList<SearchParameter>() ;
			    // FIltering values with Map .. not exactly required .. duplicate params if any will be discarded at persistence layer !!
			    String type;	    
			    for(int i=0 ; i < jsonArr.length() ; i++)
			    {
			    	SearchParameter searchParam = new SearchParameter();
			    	JSONObject jsonParam = jsonArr.optJSONObject(i);
			    	searchParam.setAttributes(jsonParam.optString("attributes"));
			    	
			    	try{
			    		searchParam.setName(jsonParam.getString("name"));
						}
					catch(JSONException je){
						throw new EMAnalyticsWSException("The name key for search param is missing in the input JSON Object",EMAnalyticsWSException.JSON_SEARCH_PARAM_NAME_MISSING,je);
					}
			    	try{
			    		type=jsonParam.getString("type");
			    	}
			    	catch(JSONException je){
						throw new EMAnalyticsWSException("The type key for search param is missing in the input JSON Object",EMAnalyticsWSException.JSON_SEARCH_PARAM_TYPE_MISSING,je);
					}
			    	if(type.equals("STRING") | type.equals("CLOB"))
			    		searchParam.setType(ParameterType.valueOf(type));
			    	else
			    		throw new EMAnalyticsWSException(" Invalid param type, please specify either STRING or CLOB.", EMAnalyticsWSException.JSON_SEARCH_PARAM_TYPE_INVALID);
			    	searchParam.setValue(jsonParam.optString("value"));
			    	searchParamList.add(searchParam);
			    	
			    }
			    searchObj.setParameters(searchParamList);
			    
		    }
		    else
		    	searchObj.setParameters(null);
		    
		return searchObj;
		
		
	}
}

