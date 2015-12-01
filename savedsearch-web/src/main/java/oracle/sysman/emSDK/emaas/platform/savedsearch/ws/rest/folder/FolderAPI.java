package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.folder;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.StringUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.validationUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * The Folder Services
 *
 * @since 0.1
 */
@Path("folder")
public class FolderAPI
{
	@Context
	private UriInfo uri;

	@Context
	private HttpHeaders headers;

	private static final Logger _logger = LogManager.getLogger(FolderAPI.class);

	/**
	 * Create a folder<br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/folder</font><br>
	 * The string "folder" in the URL signifies create operation on search.
	 *
	 * @since 0.1
	 * @param folderObj
	 *            "folderObj" is the input JSON string which contains all the information needed to create a new folder.<br>
	 *            Input Sample:<br>
	 *            <font color="DarkCyan">{<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;"name": "Demo Folder", //required<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;"description": "Folder for demo", //optional<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;"parentFolder":{ <br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id":1 //optional<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;}<br>
	 *            }</font><br>
	 *            Input Spec:<br>
	 *            <table border="1">
	 *            <tr>
	 *            <th>Field Name</th>
	 *            <th>Type</th>
	 *            <th>Required
	 *            <th>Default Value</th>
	 *            <th>Comments</th>
	 *            </tr>
	 *            <tr>
	 *            <td>name</td>
	 *            <td>VARCHAR2(64 BYTE)</td>
	 *            <td>Y</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            <tr>
	 *            <td>description</td>
	 *            <td>VARCHAR2(256 BYTE)</td>
	 *            <td>N</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            <tr>
	 *            <td>parentFolder id</td>
	 *            <td>NUMBER(38,0)</td>
	 *            <td>N</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            </table>
	 * @return Return the complete details of folder with generated folder Id <br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"id": 1368, <br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"name": "Demo Folder",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"owner": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"description": "Folder for demo",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"createdOn": "2014-07-10T07:05:14.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"lastModifiedOn": "2014-07-10T07:05:14.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"lastModifiedBy": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"systemFolder": false, <br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"parentFolder": { <br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 1,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"href":
	 *         "http://slc06byz.us.oracle.com:7001/savedsearch/v1/folder/1"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;}, <br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"href": "http://slc06byz.us.oracle.com:7001/savedsearch/v1/folder/1368" <br>
	 *         }</font><br>
	 * <br>
	 *         Response Code:<br>
	 *         <table border="1">
	 *         <tr>
	 *         <th>Status code</th>
	 *         <th>Status</th>
	 *         <th>Description</th>
	 *         </tr>
	 *         <tr>
	 *         <td>201</td>
	 *         <td>Created</td>
	 *         <td>create folder successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>404</td>
	 *         <td>Not Found</td>
	 *         <td>&nbsp;</td>
	 *         </tr>
	 *         <tr>
	 *         <td>400</td>
	 *         <td>Bad Request</td>
	 *         <td>The name key for folder is missing in the input JSON Object</td>
	 *         </tr>
	 *         </table>
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createFolder(JSONObject folderObj)
	{
		LogUtil.getInteractionLogger().info("Service calling to (POST) /savedsearch/v1/folder");
		Folder objFld = null;
		String msg = "";
		JSONObject jsonObj;
		int statusCode = 201;
		try {
			objFld = getFolderFromJsonForCreate(folderObj);
			FolderManager mgrFolder = FolderManager.getInstance();
			objFld = mgrFolder.saveFolder(objFld);
			jsonObj = EntityJsonUtil.getFullFolderJsonObj(uri.getBaseUri(), objFld);
			msg = jsonObj.toString();
		}
		catch (EMAnalyticsFwkException e) {
			msg = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Fail to create folder object", e);

		}
		catch (EMAnalyticsWSException e) {
			msg = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Fail to create folder object", e);
		}
		return Response.status(statusCode).entity(msg).build();
	}

	/**
	 * Create a folder Not for External User
	 */
	/*
	 * @POST
	 *
	 * @Consumes({ MediaType.APPLICATION_JSON })
	 *
	 * @Produces({ MediaType.APPLICATION_JSON })
	 *
	 * @Path("path") public Response createFolderPath(JSONObject folderObj) {
	 * Folder objFld = null; String msg = ""; int statusCode = 201; try {
	 * FolderManager mgrFolder = FolderManager.getInstance(); objFld =
	 * getFolderFromJsonForCreate(folderObj); // Here the call is made to
	 * savePath to distinguish the path save mkdir-p option !! objFld =
	 * mgrFolder.savePath(objFld); folderObj.put("parentId", objFld.getId());
	 *
	 * msg = JSONUtil.ObjectToJSONString(objFld); } catch
	 * (EMAnalyticsFwkException e) { msg = e.getMessage(); statusCode =
	 * e.getStatusCode(); } catch (EMAnalyticsWSException e) { msg =
	 * e.getMessage(); statusCode = e.getStatusCode(); } catch (JSONException e)
	 * { // TODO Auto-generated catch block msg = e.getMessage();
	 * e.printStackTrace(); } return
	 * Response.status(statusCode).entity(msg).build(); }
	 */
	/**
	 * Delete the folder with given folder Id <br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/folder/&lt;id&gt;</font><br>
	 * The string "folder/&lt;id&gt;" in the URL signifies delete operation with given folder Id.
	 *
	 * @since 0.1
	 * @param id
	 *            The folder Id which user wants to delete
	 * @return Nothing will return in response body <br>
	 *         Response Code:<br>
	 *         <table border="1">
	 *         <tr>
	 *         <th>Status code</th>
	 *         <th>Status</th>
	 *         <th>Description</th>
	 *         </tr>
	 *         <tr>
	 *         <td>204</td>
	 *         <td>No Content</td>
	 *         <td>Delete folder successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>404</td>
	 *         <td>Not Found</td>
	 *         <td>Folder with Id &lt;id&gt; does not exist</td>
	 *         </tr>
	 *         <tr>
	 *         <td>500</td>
	 *         <td>Internal Error</td>
	 *         <td>Folder with Id &lt;id&gt; is system folder and NOT allowed to delete</td>
	 *         </tr>
	 *         </table>
	 */
	@DELETE
	@Path("{id: [0-9]*}")
	public Response delete(@PathParam("id") long id)
	{
		LogUtil.getInteractionLogger().info("Service calling to (DELETE) /savedsearch/v1/folder/{}", id);
		int statusCode = 204;
		try {

			FolderManager mgrFolder = FolderManager.getInstance();
			mgrFolder.deleteFolder(id, false);

		}
		catch (EMAnalyticsFwkException e) {
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Fail to delete folder object", e);
			return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
		}
		return Response.status(statusCode).build();
	}

	/**
	 * Edit the folder with given folder Id<br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/folder/&lt;id&gt;</font><br>
	 * The string "folder/&lt;id&gt;" in the URL signifies edit operation with given folder Id.
	 *
	 * @since 0.1
	 * @param id
	 *            The folder Id which the user wants to edit<br>
	 * @param folderObj
	 *            "folderObj" is the JSON string which contains key value pairs which the user wants to edit.<br>
	 *            Input Sample:<br>
	 *            <font color="DarkCyan">{<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;"name": "Demo Folder_Edit",<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;"description": "Folder for demo_Edit",<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;"parentFolder":{ <br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id":1 //optional<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;}<br>
	 *            }</font><br>
	 *            input spec:<br>
	 *            Please see the input spec for <a href="resource_FolderAPI.html#path__folder.html">/folder, POST</a><br>
	 * @return Return the complete details of folder with given folder Id <br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"id": 1368,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"name": "Demo Folder_Edit",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"description": "Folder for demo_Edit",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"owner": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"createdOn": "2014-07-10T07:05:14.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"lastModifiedOn": "2014-07-10T07:05:14.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"lastModifiedBy": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"systemFolder": false,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"parentFolder": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 1,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/1"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "href": "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/1368"<br>
	 *         }</font><br>
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
	 *         <td>Edit folder successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>404</td>
	 *         <td>Not Found</td>
	 *         <td>Folder with Id &lt;id&gt; does not exist</td>
	 *         </tr>
	 *         <tr>
	 *         <td>500</td>
	 *         <td>Internal Error</td>
	 *         <td>Folder with Id &lt;id&gt; is system folder and NOT allowed to edit</td>
	 *         </tr>
	 *         </table>
	 */
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{id: [0-9]*}")
	public Response editFolder(JSONObject folderObj, @PathParam("id") long id)
	{
		LogUtil.getInteractionLogger().info("Service calling to (PUT) /savedsearch/v1/folder/{}", id);
		Folder objFld = null;
		String msg = "";
		JSONObject jsonObj;
		int statusCode = 200;
		try {

			FolderManager mgrFolder = FolderManager.getInstance();
			objFld = getFolderFromJsonForEdit(folderObj, mgrFolder.getFolder(id));
			objFld = mgrFolder.updateFolder(objFld);
			jsonObj = EntityJsonUtil.getFullFolderJsonObj(uri.getBaseUri(), objFld);
			msg = jsonObj.toString();
			statusCode = 200;
		}
		catch (EMAnalyticsFwkException e) {
			msg = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Fail to update folder object", e);

		}
		catch (EMAnalyticsWSException e) {
			msg = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Fail to update folder object", e);
		}

		return Response.status(statusCode).entity(msg).build();
	}

	/**
	 * Read the details of the folder with given folder Id<br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/folder/&lt;id&gt;</font><br>
	 * The string "folder/&lt;id&gt;" in the URL signifies read operation with given folder Id.
	 *
	 * @since 0.1
	 * @param id
	 *            The folder Id which user wants to get the details.
	 * @return Return the complete details of folder with given folder Id.<br>
	 *         <font color="DarkCyan">Response Sample:<br>
	 *         {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"id": 1368,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"name": "Demo Folder_Edit",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"description": "Folder for demo_Edit",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"owner": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"createdOn": "2014-07-10T07:05:14.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"lastModifiedOn": "2014-07-10T07:05:14.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"lastModifiedBy": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"systemFolder": false,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"parentFolder": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id": 1,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/1"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "href": "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/1368"<br>
	 *         }</font><br>
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
	 *         <td>List all searches successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>404</td>
	 *         <td>Not Found</td>
	 *         <td>Folder with the Id &lt;id&gt; does not exist</td>
	 *         </tr>
	 *         </table>
	 */
	@GET
	@Path("{id: [0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFolder(@PathParam("id") long id)
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/folder/{}", id);
		String msg = "";
		int statusCode = 200;
		JSONObject jsonObj;
		try {
			FolderManager mgrFolder = FolderManager.getInstance();
			Folder tmp = mgrFolder.getFolder(id);
			jsonObj = EntityJsonUtil.getFullFolderJsonObj(uri.getBaseUri(), tmp);
			msg = jsonObj.toString();
		}
		catch (EMAnalyticsFwkException e) {
			msg = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Fail to read folder object", e);
		}

		return Response.status(statusCode).entity(msg).build();
	}

	private Folder getFolderFromJsonForCreate(JSONObject folderObj) throws EMAnalyticsWSException
	{
		Folder objFld = FolderManager.getInstance().createNewFolder();
		try {
			String name = folderObj.getString("name");
			//			if (name == null) {
			//				throw new EMAnalyticsWSException("The name key for folder can not be null in the input JSON Object",
			//						EMAnalyticsWSException.JSON_FOLDER_NAME_MISSING);
			//			}
			if (name != null && name.trim().equals("")) {
				throw new EMAnalyticsWSException("The name key for folder can not be empty in the input JSON Object",
						EMAnalyticsWSException.JSON_FOLDER_NAME_MISSING);
			}

			if (StringUtil.isSpecialCharFound(name)) {
				throw new EMAnalyticsWSException(
						"The folder name contains at least one invalid character ('<' or '>'), please correct folder name and retry",
						EMAnalyticsWSException.JSON_INVALID_CHAR);
			}
			try {
				validationUtil.validateLength("name", name, 64);
			}
			catch (EMAnalyticsWSException e) {
				throw e;
			}

			objFld.setName(name);
		}
		catch (JSONException e) {
			throw new EMAnalyticsWSException("The name key for folder is missing in the input JSON Object",
					EMAnalyticsWSException.JSON_FOLDER_NAME_MISSING, e);
		}
		// nullables !!
		String desc = "";
		try {
			desc = folderObj.getString("description");
			if (StringUtil.isSpecialCharFound(desc)) {
				throw new EMAnalyticsWSException(
						"The folder description contains at least one invalid character ('<' or '>'), please correct folder description and retry",
						EMAnalyticsWSException.JSON_INVALID_CHAR);
			}

		}
		catch (JSONException je) {
			//ignore the description if not provided by user
		}

		try {
			validationUtil.validateLength("description", desc, 256);
		}
		catch (EMAnalyticsWSException e) {
			throw e;
		}

		objFld.setDescription(folderObj.optString("description", objFld.getDescription()));
		objFld.setUiHidden(folderObj.optBoolean("uiHidden", objFld.isUiHidden()));
		if (folderObj.has("parentFolder")) {
			JSONObject jsonFol = folderObj.optJSONObject("parentFolder");
			int parentId = jsonFol.optInt("id");
			if (parentId == 0) {
				objFld.setParentId(1);
			}
			else {
				objFld.setParentId(parentId);
			}
		}
		else {
			objFld.setParentId(1);
		}
		return objFld;
	}

	private Folder getFolderFromJsonForEdit(JSONObject folderObj, Folder folder) throws EMAnalyticsWSException
	{

		if (folderObj.has("name")) {
			String name = folderObj.optString("name");
			//			if (name == null) {
			//				throw new EMAnalyticsWSException("The name key for folder can not be null in the input JSON Object",
			//						EMAnalyticsWSException.JSON_FOLDER_NAME_MISSING);
			//			}
			if (name != null && name.trim().equals("")) {
				throw new EMAnalyticsWSException("The name key for folder can not be empty in the input JSON Object",
						EMAnalyticsWSException.JSON_FOLDER_NAME_MISSING);
			}
			if (StringUtil.isSpecialCharFound(name)) {
				throw new EMAnalyticsWSException(
						"The folder name contains at least one invalid character ('<' or '>'), please correct folder name and retry",
						EMAnalyticsWSException.JSON_INVALID_CHAR);
			}
			try {
				validationUtil.validateLength("name", name, 64);
			}
			catch (EMAnalyticsWSException e) {
				throw e;
			}

			folder.setName(name);
		}
		else {
			folder.setName(folderObj.optString("name", folder.getName()));
		}
		String desc = folderObj.optString("description", folder.getDescription());
		if (StringUtil.isSpecialCharFound(desc)) {
			throw new EMAnalyticsWSException(
					"The folder description contains at least one invalid character ('<' or '>'), please correct folder description and retry",
					EMAnalyticsWSException.JSON_INVALID_CHAR);
		}
		try {
			validationUtil.validateLength("description", desc, 256);
		}
		catch (EMAnalyticsWSException e) {
			throw e;
		}

		folder.setDescription(folderObj.optString("description", folder.getDescription()));
		folder.setUiHidden(folderObj.optBoolean("uiHidden", folder.isUiHidden()));
		if (folderObj.has("parentFolder")) {
			JSONObject jsonFol = folderObj.optJSONObject("parentFolder");
			int parentId = jsonFol.optInt("id");
			if (parentId == 0) {
				folder.setParentId(1);
			}
			else {
				folder.setParentId(parentId);
			}
		}
		else {
			folder.setParentId(1);
		}

		return folder;
	}
}
