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
		int statusCode=201;
		try {
			objFld = getFolderFromJsonForCreate(folderObj);
			FolderManager mgrFolder = FolderManager.getInstance();
			objFld = mgrFolder.saveFolder(objFld);
			msg = JSONUtil.ObjectToJSONString(objFld);
		} catch (EMAnalyticsFwkException e) {
			msg=e.getMessage();
			statusCode=e.getStatusCode();
		} 
		catch(EMAnalyticsWSException e){
			msg=e.getMessage();
			statusCode=e.getStatusCode();
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
		try {
			FolderManager mgrFolder = FolderManager.getInstance();
			Folder tmp = mgrFolder.getFolder(id);
			msg = JSONUtil.ObjectToJSONString(tmp);
		} catch (EMAnalyticsFwkException  e) {
			msg = e.getMessage();
			statusCode=e.getStatusCode();
		} 
		catch(EMAnalyticsWSException e){
			msg=e.getMessage();
			statusCode=e.getStatusCode();
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
		int statusCode=200;
		try {
			FolderManager mgrFolder = FolderManager.getInstance();
			objFld = getFolderFromJsonForEdit(folderObj,
					mgrFolder.getFolder(id));
			objFld = mgrFolder.updateFolder(objFld);
			msg = JSONUtil.ObjectToJSONString(objFld);
			statusCode=200;
		}catch (EMAnalyticsFwkException e) {
				msg=e.getMessage();
				statusCode=e.getStatusCode();
			
		}catch(EMAnalyticsWSException e){
			msg=e.getMessage();
			statusCode=e.getStatusCode();
		}
		return Response.status(statusCode).entity(msg).build();
	}

	private Folder getFolderFromJsonForEdit(JSONObject folderObj, Folder folder) {

		folder.setName(folderObj.optString("name", folder.getName()));
		folder.setDescription(folderObj.optString("description",
				folder.getDescription()));
		folder.setUiHidden(folderObj.optBoolean("uiHidden", folder.isUiHidden()));
		if(folderObj.has("parentId") )
			folder.setParentId(folderObj.optInt("parentId"));
		
		return folder;
	}
	

	private Folder getFolderFromJsonForCreate(JSONObject folderObj)
			throws  EMAnalyticsWSException {
		Folder objFld = FolderManager.getInstance().createNewFolder();		
		try{
			objFld.setName(folderObj.getString("name"));
		}catch(JSONException e){
			throw new EMAnalyticsWSException("The name key for folder is missing in the input JSON Object", EMAnalyticsWSException.JSON_FOLDER_NAME_MISSING, e);
		}
		// nullables !!
		objFld.setDescription(folderObj.optString("description",
				objFld.getDescription()));
		objFld.setUiHidden(folderObj.optBoolean("uiHidden", objFld.isUiHidden()));
		if(folderObj.has("parentId") )
			objFld.setParentId(folderObj.optInt("parentId"));
		return objFld;
	}

}
