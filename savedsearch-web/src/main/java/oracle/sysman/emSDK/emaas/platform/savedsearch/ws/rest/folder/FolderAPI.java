package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.folder;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JSONUtil;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("folder")
public class FolderAPI {
	@Context
	private UriInfo uri;
	

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createFolder(JSONObject folderObj) {
		Folder objFld = null;
		String msg = "";
		JSONObject jsonObj;
		int statusCode=201;
		try {
			objFld = getFolderFromJsonForCreate(folderObj);
			FolderManager mgrFolder = FolderManager.getInstance();
			objFld = mgrFolder.saveFolder(objFld);
			jsonObj=JSONUtil.ObjectToJSONObject(objFld);
			jsonObj = modifyFolder(jsonObj);
			msg = jsonObj.toString();
		} catch (EMAnalyticsFwkException e) {
			msg=e.getMessage();
			statusCode=e.getStatusCode();
		} 
		catch(EMAnalyticsWSException e){
			msg=e.getMessage();
			statusCode=e.getStatusCode();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			statusCode=404;
			msg=e.getMessage();
		}
		return Response.status(statusCode).entity(msg).build();
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("path")
	public Response createFolderPath(JSONObject folderObj) {
		Folder objFld = null;
		String msg = "";
		int statusCode=201;
		try {
			FolderManager mgrFolder = FolderManager.getInstance();
			objFld = getFolderFromJsonForCreate(folderObj);
			// Here the call is made to savePath to distinguish the path save mkdir-p option !!
			objFld = mgrFolder.savePath(objFld);
			folderObj.put("parentId",objFld.getId());
			
			msg = JSONUtil.ObjectToJSONString(objFld);
		} catch (EMAnalyticsFwkException e) {
			msg=e.getMessage();
			statusCode=e.getStatusCode();
		} 
		catch(EMAnalyticsWSException e){
			msg=e.getMessage();
			statusCode=e.getStatusCode();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			msg=e.getMessage();
			e.printStackTrace();
		}
		return Response.status(statusCode).entity(msg).build();
	}

	@DELETE
	@Path("{id: [0-9]*}")
	public Response delete(@PathParam("id") long id) {
		int statusCode=204;
		try {
			
			FolderManager mgrFolder = FolderManager.getInstance();
			mgrFolder.deleteFolder(id);
			
		} catch (EMAnalyticsFwkException e) {
			return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
		}
		return Response.status(statusCode).build();
	}

	@GET
	@Path("{id: [0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFolder(@PathParam("id") long id) {
		String msg ="";
		int statusCode=200;
		JSONObject jsonObj;
		try {
			FolderManager mgrFolder = FolderManager.getInstance();
			Folder tmp = mgrFolder.getFolder(id);
			jsonObj = JSONUtil.ObjectToJSONObject(tmp);
			jsonObj = modifyFolder(jsonObj);
			msg = jsonObj.toString();
		} catch (EMAnalyticsFwkException  e) {
			msg = e.getMessage();
			statusCode=e.getStatusCode();
		} 
		catch(EMAnalyticsWSException e){
			msg=e.getMessage();
			statusCode=e.getStatusCode();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			statusCode=404;
		}
		return Response.status(statusCode).entity(msg).build();
	}
	
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{id : [0-9]*}")
	public Response editFolder(JSONObject folderObj, @PathParam("id") long id) {
		Folder objFld = null;
		String msg = "";
		JSONObject jsonObj;
		int statusCode=200;
		try {
			FolderManager mgrFolder = FolderManager.getInstance();
			objFld = getFolderFromJsonForEdit(folderObj,
					mgrFolder.getFolder(id));
			objFld = mgrFolder.updateFolder(objFld);
			jsonObj = JSONUtil.ObjectToJSONObject(objFld);
			jsonObj = modifyFolder(jsonObj);
			msg = jsonObj.toString();
			statusCode=200;
		}catch (EMAnalyticsFwkException e) {
				msg=e.getMessage();
				statusCode=e.getStatusCode();
			
		}catch(EMAnalyticsWSException e){
			msg=e.getMessage();
			statusCode=e.getStatusCode();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			statusCode=404;
		}
		return Response.status(statusCode).entity(msg).build();
	}

	private Folder getFolderFromJsonForEdit(JSONObject folderObj, Folder folder) throws EMAnalyticsWSException {
		
		if(folderObj.has("name")){
			String name=folderObj.optString("name");
			if(name == null)
				throw new EMAnalyticsWSException("The name key for folder can not be null in the input JSON Object",EMAnalyticsWSException.JSON_FOLDER_NAME_MISSING);
			if(name !=null && name.trim().equals(""))
				throw new EMAnalyticsWSException("The name key for folder can not be empty in the input JSON Object",EMAnalyticsWSException.JSON_FOLDER_NAME_MISSING);
			folder.setName(name);
		}else 
			folder.setName(folderObj.optString("name", folder.getName()));
		folder.setDescription(folderObj.optString("description",
				folder.getDescription()));
		folder.setUiHidden(folderObj.optBoolean("uiHidden", folder.isUiHidden()));
		if(folderObj.has("parentFolder") ){
			JSONObject jsonFol=folderObj.optJSONObject("parentFolder");
			int parentId=jsonFol.optInt("id");
			if( parentId ==0 )
				folder.setParentId(1);
			else
				folder.setParentId(parentId);
		}else
			folder.setParentId(1);
	
		return folder;
	}
	

	private Folder getFolderFromJsonForCreate(JSONObject folderObj)
			throws  EMAnalyticsWSException {
		Folder objFld = FolderManager.getInstance().createNewFolder();		
		try{
			String name=folderObj.getString("name");
			if(name == null)
				throw new EMAnalyticsWSException("The name key for folder can not be null in the input JSON Object",EMAnalyticsWSException.JSON_FOLDER_NAME_MISSING);
			if(name !=null && name.trim().equals(""))
				throw new EMAnalyticsWSException("The name key for folder can not be empty in the input JSON Object",EMAnalyticsWSException.JSON_FOLDER_NAME_MISSING);
			
			objFld.setName(name);
		}catch(JSONException e){
			throw new EMAnalyticsWSException("The name key for folder is missing in the input JSON Object", EMAnalyticsWSException.JSON_FOLDER_NAME_MISSING, e);
		}
		// nullables !!
		objFld.setDescription(folderObj.optString("description",
				objFld.getDescription()));
		objFld.setUiHidden(folderObj.optBoolean("uiHidden", objFld.isUiHidden()));
		if(folderObj.has("parentFolder") ){
			JSONObject jsonFol=folderObj.optJSONObject("parentFolder");
			int parentId=jsonFol.optInt("id");
			if( parentId ==0 )
				objFld.setParentId(1);
			else
				objFld.setParentId(parentId);
		}else
			objFld.setParentId(1);
		return objFld;
	}

private JSONObject modifyFolder(JSONObject jsonObj) throws JSONException{
	JSONObject rtnObj=new JSONObject();
	jsonObj.put("createdOn", JSONUtil.getDate(Long.parseLong(jsonObj.getString("createdOn"))));
	jsonObj.put("lastModifiedOn", JSONUtil.getDate(Long.parseLong(jsonObj.getString("lastModifiedOn"))));
	rtnObj.put("id", jsonObj.getInt("id"));
	rtnObj.put("name", jsonObj.optString("name"));
	if(jsonObj.has("parentId"))
	{
		JSONObject jsonFold = new JSONObject();
		jsonFold.put("id", jsonObj.getInt("parentId"));
		jsonFold.put("href",uri.getBaseUri() +"folder/" +jsonObj.getInt("parentId"));
		rtnObj.put("parentFolder", jsonFold);
	}
	if(jsonObj.has("description"))
		rtnObj.put("description",jsonObj.getString("description"));
	if(jsonObj.has("owner"))
		rtnObj.put("owner", jsonObj.getString("owner"));
	rtnObj.put("createdOn",jsonObj.optString("createdOn"));
	if(jsonObj.has("lastModifiedBy"))
	rtnObj.put("lastModifiedBy",jsonObj.optString("lastModifiedBy"));
	rtnObj.put("lastModifiedOn", jsonObj.optString("lastModifiedOn"));
	rtnObj.put("systemFolder", jsonObj.optBoolean("systemFolder"));
	rtnObj.put("href",uri.getBaseUri() +"folder/" +jsonObj.getInt("id"));
	return rtnObj;
	
}
}
