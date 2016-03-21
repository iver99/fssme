package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.StringUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.validationUtil;
import oracle.sysman.emaas.platform.savedsearch.targetmodel.services.OdsDataService;
import oracle.sysman.emaas.platform.savedsearch.targetmodel.services.OdsDataServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * The Search Services
 *
 * @since 0.1
 */
@Path("search")
public class SearchAPI
{

	private static final String FOLDER_PATH = "flattenedFolderPath";
	private static final Logger _logger = LogManager.getLogger(SearchAPI.class);
	@Context
	UriInfo uri;

	/**
	 * Create a search<br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/search</font><br>
	 * The string "search" in the URL signifies create operation on search.<br>
	 *
	 * @since 0.1
	 * @param inputJsonObj
	 *            The search details <br>
	 *            Input Sample:<br>
	 *            <font color="DarkCyan"> {<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;"name": "Demo Search", //required<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;"category": {"id":999},//required <br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;"folder": {"id":999},//required <br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;"description": "Search for Demo", //optional<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;"queryStr": "*",//optional <br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;"parameters":[<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name":"time",<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type":"STRING",<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value":"ALL"<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name":"additionalInfo",<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type":"CLOB",<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value":"this is a demo"<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; ]<br>
	 *            } </font><br>
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
	 *            <td>category id</td>
	 *            <td>NUMBER(38,0)</td>
	 *            <td>Y</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            <tr>
	 *            <td>folder id</td>
	 *            <td>NUMBER(38,0)</td>
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
	 *            <td>queryStr</td>
	 *            <td>CLOB</td>
	 *            <td>N</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            <tr>
	 *            <td>parameters name</td>
	 *            <td>VARCHAR2(64 BYTE)</td>
	 *            <td>N</td>
	 *            <td>N/A</td>
	 *            <td>For each parameter, name and type are required, value is optional</td>
	 *            </tr>
	 *            <tr>
	 *            <td>parameters type</td>
	 *            <td>VARCHAR2(16 BYTE)</td>
	 *            <td>N</td>
	 *            <td>N/A</td>
	 *            <td>Valid value: STRING, CLOB.</td>
	 *            </tr>
	 *            <tr>
	 *            <td>parameters value</td>
	 *            <td>VARCHAR2(1024 BYTE)/NCLOB</td>
	 *            <td>N</td>
	 *            <td>N/A</td>
	 *            <td>&nbsp;</td>
	 *            </tr>
	 *            </table>
	 * @return Return Complete details of search with search Id generated automatically.<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan"> {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "id": 10438,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "name": "Demo Search",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"description": "Search for Demo",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "category": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 999,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/category/999"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "folder": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 999,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/999"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "owner": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "createdOn": "2014-07-14T05:19:26.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "lastModifiedBy": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "lastModifiedOn": "2014-07-14T05:19:26.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "lastAccessDate": "2014-07-13T22:19:26.358Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "systemSearch": false,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "queryStr": "*",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "parameters": [<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "additionalInfo",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value": "this is a demo",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "CLOB"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; "name": "time",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value": "ALL",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "STRING"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; ],<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "href": "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/search/10438"<br>
	 *         } </font><br>
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
	 *         <td>Create search successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>404</td>
	 *         <td>Not Found</td>
	 *         <td>&nbsp;</td>
	 *         </tr>
	 *         <tr>
	 *         <td>400</td>
	 *         <td>Bad Request</td>
	 *         <td>could be the following errors:<br>
	 *         1.The name key for search is missing in the input JSON Object<br>
	 *         2.The category key for search is missing in the input JSON Object<br>
	 *         3.The folder key for search is missing in the input JSON Object</td>
	 *         </tr>
	 *         </table>
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createSearch(JSONObject inputJsonObj, @HeaderParam(value = "OAM_REMOTE_USER") String tenantName)
	{
		LogUtil.getInteractionLogger().info("Service calling to (POST) /savedsearch/v1/search");

		String message = "";
		int statusCode = 201;
		JSONObject jsonObj;
		SearchManager sman = SearchManager.getInstance();
		try {
			Search searchObj = createSearchObjectForAdd(inputJsonObj);
			Search savedSearch = sman.saveSearch(searchObj);
			
			// see if an ODS entity needs to be create
			for (SearchParameter param : searchObj.getParameters()) {
				if (OdsDataService.ENTITY_FLAG.equalsIgnoreCase(param.getName())
						&& "TRUE".equalsIgnoreCase(param.getValue())) {
					OdsDataService ods = OdsDataServiceImpl.getInstance();
					String meId = ods.createOdsEntity(savedSearch.getId().toString(), savedSearch.getName(), tenantName);
						
					// add ODS Entity ID as one of search parameters of the saved search
					savedSearch.getParameters().add(generateOdsEntitySearchParam(meId));
					
					// store the whole saved search again
					// TODO provide new API to add a search parameter solely
					savedSearch = sman.editSearch(savedSearch);
					break;
				}
			}
			
			jsonObj = EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), savedSearch);
			message = jsonObj.toString();
		}
		catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
		}
		catch (EMAnalyticsWSException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
		}
		return Response.status(statusCode).entity(message).build();
	}

	private SearchParameter generateOdsEntitySearchParam(String meId) {
		SearchParameter newParam = new SearchParameter();
		newParam.setName(OdsDataService.ENTITY_ID);
		newParam.setValue(meId);
		newParam.setType(ParameterType.STRING);
		return newParam;
	}

	/**
	 * Delete a saved-search with the given id<br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/search/&lt;id&gt;</font><br>
	 * The string "search/&lt;id&gt;" in the URL signifies delete operation on search with given id.
	 *
	 * @since 0.1
	 * @param searchId
	 *            The id of saved-search which user wants to delete
	 * @return Nothing to return in the response body<br>
	 * <br>
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
	 *         <td>Delete search successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>404</td>
	 *         <td>Not Found</td>
	 *         <td>Search with Id: &lt;id&gt; does not exist</td>
	 *         </tr>
	 *         <tr>
	 *         <td>500</td>
	 *         <td>Internal Error</td>
	 *         <td>Search with Id: &lt;id&gt; is system search and NOT allowed to delete</td>
	 *         </tr>
	 *         </table>
	 */
	@DELETE
	@Path("{id: [0-9]*}")
	public Response deleteSearch(@PathParam("id") long searchId, @HeaderParam(value = "OAM_REMOTE_USER") String tenantName)
	{
		LogUtil.getInteractionLogger().info("Service calling to (DELETE) /savedsearch/v1/search/{}", searchId);
		int statusCode = 204;
		
		OdsDataService odsService = OdsDataServiceImpl.getInstance();
		SearchManager sman = SearchManager.getInstance();
		
		try {
			odsService.deleteOdsEntity(searchId, tenantName);
			sman.deleteSearch(searchId, false);
		}
		catch (EMAnalyticsFwkException e) {
			return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
		}

		return Response.status(statusCode).build();
	}

	/**
	 * Edit the search with given search Id <br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/search/&lt;id&gt;</font><br>
	 * The string "search/&lt;id&gt;" in the URL signifies edit operation on search with given id.
	 *
	 * @since 0.1
	 * @param inputJsonObj
	 *            JSON string which contains all key value pairs that the user wants to edit.<br>
	 *            Input Sample:<br>
	 *            <font color="DarkCyan"> {<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; "name": "Demo Search X1",<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;"description": "Search for Demo",<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; "category": {<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 999,<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *            "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/category/999"<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; "folder": {<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 999,<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *            "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/999"<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; "queryStr": "*1",<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; "parameters": [<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "additionalInfo x1",<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value": "this is a demo",<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "CLOB"<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "time X2",<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value": "ALL",<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "STRING"<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; ]<br>
	 *            }</font><br>
	 *            input spec:<br>
	 *            Please see the input spec for <a href="resource_SearchAPI.html#path__search.html">/search, POST</a><br>
	 * @param searchId
	 *            The saved-search id to edit
	 * @param updateCategory
	 *            INTERNAL USE ONLY
	 * @return Return complete details of search with gived search Id <br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan"> {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "id": 10438,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "name": "Demo Search X1",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;"description": "Search for Demo",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "category": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 999,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/category/999"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "folder": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 999,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/999"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "owner": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "createdOn": "2014-07-14T05:19:26.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "lastModifiedBy": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "lastModifiedOn": "2014-07-14T05:19:26.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "lastAccessDate": "2014-07-13T22:19:26.358Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "systemSearch": false,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "queryStr": "*",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "parameters": [<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "additionalInfo x1",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value": "this is a demo",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "CLOB"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; "name": "time X2",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value": "ALL",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "STRING"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; ],<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "href": "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/search/10438"<br>
	 *         } </font><br>
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
	 *         <td>Edit search successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>404</td>
	 *         <td>Not Found</td>
	 *         <td>Search with Id: &lt;id&gt; does not exist</td>
	 *         </tr>
	 *         <tr>
	 *         <td>500</td>
	 *         <td>Internal Error</td>
	 *         <td>Search with Id: &lt;id&gt; is system search and NOT allowed to update</td>
	 *         </tr>
	 *         </table>
	 */
	@PUT
	@Path("{id: [0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editSearch(JSONObject inputJsonObj, @HeaderParam("X_SSF_API_AUTH") String updateCategory,
			@PathParam("id") long searchId)
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/search/{}", searchId);
		String message = null;
		int statusCode = 200;
		JSONObject jsonObj;
		Search searchObj;
		SearchManager sman = SearchManager.getInstance();
		try {
			if (updateCategory != null && updateCategory.equals("ORACLE_INTERNAL")) {
				searchObj = createSearchObjectForEdit(inputJsonObj, sman.getSearch(searchId), true);

			}
			else {
				searchObj = createSearchObjectForEdit(inputJsonObj, sman.getSearch(searchId), false);
			}
			Search savedSearch = sman.editSearch(searchObj);
			jsonObj = EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), savedSearch);
			message = jsonObj.toString();

		}
		catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
		}
		catch (EMAnalyticsWSException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
		}

		return Response.status(statusCode).entity(message).build();

	}
	
	@PUT
	@Path("{id: [0-9]*}/odsentity")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOdsEntity(@PathParam("id") long searchId, @HeaderParam(value = "OAM_REMOTE_USER") String tenantName) {
		String message = null;
		int statusCode = 200;
		SearchManager sman = SearchManager.getInstance();
		Search savedSearch = null;
		
		try {
			savedSearch = sman.getSearch(searchId);
		} catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
		}
		
		if(savedSearch != null) {
			for (SearchParameter param : savedSearch.getParameters()) {
				if(OdsDataService.ENTITY_ID.equalsIgnoreCase(param.getName())) {
					statusCode = 400;
					message = "Exist Entity ID: " + param.getValue();
					_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message);
				}
			}
			
			if(statusCode != 400) {
				OdsDataService ods = OdsDataServiceImpl.getInstance();
				try {
					String meId = ods.createOdsEntity(savedSearch.getId().toString(), savedSearch.getName(), tenantName);
					savedSearch.getParameters().add(generateOdsEntitySearchParam(meId));
					savedSearch = sman.editSearch(savedSearch);
					JSONObject jsonObj = EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), savedSearch);
					message = jsonObj.toString();
				} catch (EMAnalyticsFwkException e) {
					statusCode = e.getStatusCode();
					message = e.getMessage();
					_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
				}
			}
		}
		
		return Response.status(statusCode).entity(message).build();
	}

	/**
	 * Set last access time to the saved search with given search Id<br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port
	 * number&gt;/savedsearch/v1/search/&lt;id&gt;?updateLastAccessTime=&lt;boolean value&gt;</font><br>
	 * The string "search/&lt;id&gt;?updateLastAccessTime=&lt;boolean value&gt;" in the URL signifies set last accesst time
	 * operation on search with given id.
	 *
	 * @since 0.1
	 * @param searchId
	 *            The saved-search id to edit
	 * @param update
	 *            If set the last access time to the saved search. "True" means last access time will be updated
	 * @return Response body will be shown the set access date ie: "lastAccessDate" in LONG format<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">2014-07-11T02:20:32.112Z</font><br>
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
	 *         <td>Update last access time of search successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>404</td>
	 *         <td>Not Found</td>
	 *         <td>Search with Id: &lt;id&gt; does not exist</td>
	 *         </tr>
	 *         <tr>
	 *         <td>400</td>
	 *         <td>Bad Request</td>
	 *         <td>Please specify updateLastAccessTime true or false</td>
	 *         </tr>
	 *         </table>
	 */
	@PUT
	@Path("{id: [0-9]*}")
	public Response editSearchAccessDate(@PathParam("id") long searchId, @QueryParam("updateLastAccessTime") boolean update)
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/search/{}?updateLastAccessTime={}",
				searchId, update);
		String query = uri.getRequestUri().getQuery();
		if (query == null) {
			return Response.status(400).entity("Please specify updateLastAccessTime true or false").build();
		}
		String[] input = query.split("=");
		if (input.length == 2) {
			if (update) {
				return updateLastAccessTime(searchId);
			}
			else {
				return Response.status(200).build();
			}
		}
		else {
			return Response.status(400).entity("please give the value for updateLastAccessTime").build();
		}
	}

	/**
	 * Get details of saved-search with given id<br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port
	 * number&gt;/savedsearch/v1/search/&lt;id&gt;?flattenedFolderPath=&lt;boolean value&gt;</font><br>
	 * The string "search/&lt;id&gt;?flattenedFolderPath=&lt;boolean value&gt;" in the URL signifies get the search operation on
	 * search with given id. <br>
	 * If "flattenedFolderPath = true", the flattened folder hierarchy will be added to any search
	 *
	 * @since 0.1
	 * @param searchid
	 *            The id of saved-search which user wants to read
	 * @param bPath
	 *            The parameter value is true or false. If set true it will return the flattenedFolderPath of this search.
	 * @return Return complete details of search with given search Id <br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "id": 10011,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "name": "sample for creation 2c",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "description": "Search for Demo",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "category": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 999,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/category/999"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "folder": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 999,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/999"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "owner": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "createdOn": "2014-07-04T12:03:03.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "lastModifiedBy": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "lastModifiedOn": "2014-07-04T12:03:03.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "lastAccessDate": "2014-07-08T22:25:52.857Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "systemSearch": false,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "href": "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/search/10011",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "flattenedFolderPath": [<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "Demo Searches",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "All Searches"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; ]<br>
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
	 *         <td>List search successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>404</td>
	 *         <td>Not Found</td>
	 *         <td>Search with Id: &lt;id&gt; does not exist</td>
	 *         </tr>
	 *         <tr>
	 *         <td>400</td>
	 *         <td>Bad Request</td>
	 *         <td>&nbsp;</td>
	 *         </tr>
	 *         </table>
	 */
	@GET
	@Path("{id: [0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearch(@PathParam("id") long searchid, @QueryParam("flattenedFolderPath") boolean bPath)
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/search/{}?flattenedFolderPath={}",
				searchid, bPath);
		String message = null;
		int statusCode = 200;
		JSONObject jsonObj;
		SearchManager sman = SearchManager.getInstance();
		try {

			Search searchObj = sman.getSearch(searchid);
			String[] pathArray = null;
			if (bPath) {
				FolderManager folderMgr = FolderManager.getInstance();
				pathArray = folderMgr.getPathForFolderId(searchObj.getFolderId());
			}
			jsonObj = EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), searchObj, pathArray);
			message = jsonObj.toString();
		}
		catch (EMAnalyticsFwkException e) {
			statusCode = e.getStatusCode();
			message = e.getMessage();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + e.getMessage(),
					e.getStatusCode());
		}

		return Response.status(statusCode).entity(message).build();
	}

	public Response updateLastAccessTime(long searchId)
	{
		String message = null;
		int statusCode = 200;
		try {
			SearchManager sman = SearchManager.getInstance();
			sman.getSearch(searchId);
			java.util.Date date = sman.modifyLastAccessDate(searchId);
			message = String.valueOf(DateUtil.getDateFormatter().format(date));

		}
		catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
		}
		return Response.status(statusCode).entity(message).build();

	}

	private Search createSearchObjectForAdd(JSONObject json) throws EMAnalyticsWSException
	{
		Search searchObj = new SearchImpl();
		// Data population !
		try {
			String name = json.getString("name");

			//			if (name.trim() == null) {
			//				throw new EMAnalyticsWSException("The name key for search can not be null in the input JSON Object",
			//						EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING);
			//			}
			if (name != null && name.trim().equals("")) {
				throw new EMAnalyticsWSException("The name key for search can not be empty in the input JSON Object",
						EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING);
			}

			if (StringUtil.isSpecialCharFound(name)) {
				throw new EMAnalyticsWSException(
						"The search name contains at least one invalid character ('<' or '>'), please correct search name and retry",
						EMAnalyticsWSException.JSON_INVALID_CHAR);
			}

			try {
				validationUtil.validateLength("name", name, 64);
			}
			catch (EMAnalyticsWSException e) {
				throw e;
			}

			searchObj.setName(name);
		}
		catch (JSONException je) {
			throw new EMAnalyticsWSException("The name key for search is missing in the input JSON Object",
					EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING, je);
		}
		String desc = "";
		try {
			desc = json.getString("description");
			if (StringUtil.isSpecialCharFound(desc)) {
				throw new EMAnalyticsWSException(
						"The search description contains at least one invalid character ('<' or '>'), please correct search description and retry",
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

		try {
			JSONObject jsonObj = json.getJSONObject("category");
			searchObj.setCategoryId(Integer.parseInt(jsonObj.getString("id")));
		}
		catch (JSONException je) {
			throw new EMAnalyticsWSException("The category key for search is missing in the input JSON Object",
					EMAnalyticsWSException.JSON_SEARCH_CATEGORY_ID_MISSING, je);
		}
		try {
			JSONObject jsonFold = json.getJSONObject("folder");
			searchObj.setFolderId(Integer.parseInt(jsonFold.getString("id")));
		}
		catch (JSONException je) {
			throw new EMAnalyticsWSException("The folder key for search is missing in the input JSON Object",
					EMAnalyticsWSException.JSON_SEARCH_FOLDER_ID_MISSING, je);
		}

		// Nullable properties !
		searchObj.setMetadata(json.optString("metadata", searchObj.getMetadata()));
		searchObj.setQueryStr(json.optString("queryStr", searchObj.getQueryStr()));
		searchObj.setDescription(json.optString("description", searchObj.getDescription()));
		// non-nullable with db defaults !!
		searchObj.setLocked(Boolean.parseBoolean(json.optString("locked", Boolean.toString(searchObj.isLocked()))));
		searchObj.setUiHidden(Boolean.parseBoolean(json.optString("uiHidden", Boolean.toString(searchObj.isUiHidden()))));
		searchObj.setIsWidget(Boolean.parseBoolean(json.optString("isWidget", Boolean.toString(searchObj.getIsWidget()))));

		// Parameters
		if (json.has("parameters")) {
			JSONArray jsonArr = json.optJSONArray("parameters");
			List<SearchParameter> searchParamList = new ArrayList<SearchParameter>();
			// FIltering values with Map .. not exactly required .. duplicate
			// params if any will be discarded at persistence layer !!
			String type;
			for (int i = 0; i < jsonArr.length(); i++) {
				SearchParameter searchParam = new SearchParameter();
				JSONObject jsonParam = jsonArr.optJSONObject(i);
				if (jsonParam.has("attributes")) {
					searchParam.setAttributes(jsonParam.optString("attributes"));
				}
				try {
					String name = jsonParam.getString("name");
					//					if (name.trim() == null) {
					//						throw new EMAnalyticsWSException(
					//								"The name key for search param can not be null in the input JSON Object",
					//								EMAnalyticsWSException.JSON_SEARCH_PARAM_NAME_MISSING);
					//					}
					if (name != null && name.trim().equals("")) {
						throw new EMAnalyticsWSException(
								"The name key for search param can not be empty in the input JSON Object",
								EMAnalyticsWSException.JSON_SEARCH_PARAM_NAME_MISSING);
					}

					searchParam.setName(name);
				}
				catch (JSONException je) {
					throw new EMAnalyticsWSException("The name key for search parameter is missing in the input JSON Object",
							EMAnalyticsWSException.JSON_SEARCH_PARAM_NAME_MISSING, je);
				}
				try {
					type = jsonParam.getString("type");
				}
				catch (JSONException je) {
					throw new EMAnalyticsWSException("The type key for search param is missing in the input JSON Object",
							EMAnalyticsWSException.JSON_SEARCH_PARAM_TYPE_MISSING, je);
				}
				if (type.equals("STRING") | type.equals("CLOB")) {
					searchParam.setType(ParameterType.valueOf(type));
				}
				else {
					throw new EMAnalyticsWSException("Invalid param type, please specify either STRING or CLOB",
							EMAnalyticsWSException.JSON_SEARCH_PARAM_TYPE_INVALID);
				}

				searchParam.setValue(jsonParam.optString("value"));
				searchParamList.add(searchParam);

			}
			searchObj.setParameters(searchParamList);

		}
		else {
			searchObj.setParameters(null);
		}

		return searchObj;
	}

	private Search createSearchObjectForEdit(JSONObject json, Search searchObj, Boolean update) throws EMAnalyticsWSException
	{
		// Data population !

		/*
		 * Quick Note: json.optString() returns second String if the key is not
		 * found Useful for edit
		 */
		if (json.has("name")) {
			String name = json.optString("name");
			//			if (name.trim() == null) {
			//				throw new EMAnalyticsWSException("The name key for search can not be null in the input JSON Object",
			//						EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING);
			//			}
			if (name != null && name.trim().equals("")) {
				throw new EMAnalyticsWSException("The name key for search can not be empty in the input JSON Object",
						EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING);
			}
			if (StringUtil.isSpecialCharFound(name)) {
				throw new EMAnalyticsWSException(
						"The search name contains at least one invalid character ('<' or '>'), please correct search name and retry",
						EMAnalyticsWSException.JSON_INVALID_CHAR);
			}

			try {
				validationUtil.validateLength("name", name, 64);
			}
			catch (EMAnalyticsWSException e) {
				throw e;
			}
			searchObj.setName(name);
		}
		else {
			searchObj.setName(json.optString("name", searchObj.getName()));
		}
		String desc = json.optString("description", searchObj.getDescription());
		if (StringUtil.isSpecialCharFound(desc)) {
			throw new EMAnalyticsWSException(
					"The search description contains at least one invalid character ('<' or '>'), please correct search description and retry",
					EMAnalyticsWSException.JSON_INVALID_CHAR);
		}
		try {
			validationUtil.validateLength("description", desc, 256);
		}
		catch (EMAnalyticsWSException e) {
			throw e;
		}
		searchObj.setDescription(json.optString("description", searchObj.getDescription()));
		if (update) {
			if (json.has("category")) {
				try {
					JSONObject jsonObj = json.getJSONObject("category");
					searchObj.setCategoryId(Integer.parseInt(jsonObj.getString("id")));
				}
				catch (JSONException je) {
					throw new EMAnalyticsWSException("The category key for search is missing in the input JSON Object",
							EMAnalyticsWSException.JSON_SEARCH_CATEGORY_ID_MISSING, je);
				}
			}
			else {
				searchObj.setCategoryId(Integer.parseInt(searchObj.getCategoryId().toString()));
			}
		}
		if (json.has("folder")) {
			try {
				JSONObject jsonFold = json.getJSONObject("folder");
				searchObj.setFolderId(Integer.parseInt(jsonFold.getString("id")));
			}
			catch (JSONException je) {
				throw new EMAnalyticsWSException("The folder key for search is missing in the input JSON Object",
						EMAnalyticsWSException.JSON_SEARCH_FOLDER_ID_MISSING, je);
			}
		}
		else {
			searchObj.setFolderId(Integer.parseInt(searchObj.getFolderId().toString()));
		}

		searchObj.setLocked(Boolean.parseBoolean(json.optString("locked", Boolean.toString(searchObj.isLocked()))));
		searchObj.setUiHidden(Boolean.parseBoolean(json.optString("uiHidden", Boolean.toString(searchObj.isUiHidden()))));
		searchObj.setIsWidget(Boolean.parseBoolean(json.optString("isWidget", Boolean.toString(searchObj.getIsWidget()))));

		// Nullable properties !
		searchObj.setMetadata(json.optString("metadata", searchObj.getMetadata()));
		searchObj.setQueryStr(json.optString("queryStr", searchObj.getQueryStr()));

		// Parameters
		if (json.has("parameters")) {
			JSONArray jsonArr = json.optJSONArray("parameters");
			List<SearchParameter> searchParamList = new ArrayList<SearchParameter>();
			// FIltering values with Map .. not exactly required .. duplicate
			// params if any will be discarded at persistence layer !!
			String type;
			for (int i = 0; i < jsonArr.length(); i++) {
				SearchParameter searchParam = new SearchParameter();
				JSONObject jsonParam = jsonArr.optJSONObject(i);
				searchParam.setAttributes(jsonParam.optString("attributes"));

				try {
					String name = jsonParam.getString("name");
					//					if (name.trim() == null) {
					//						throw new EMAnalyticsWSException(
					//								"The name key for search param can not be null in the input JSON Object",
					//								EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING);
					//					}
					if (name != null && name.trim().equals("")) {
						throw new EMAnalyticsWSException(
								"The name key for search param can not be empty in the input JSON Object",
								EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING);
					}
					searchParam.setName(name);
				}
				catch (JSONException je) {
					throw new EMAnalyticsWSException("The name key for search param is missing in the input JSON Object",
							EMAnalyticsWSException.JSON_SEARCH_PARAM_NAME_MISSING, je);
				}
				try {
					type = jsonParam.getString("type");
				}
				catch (JSONException je) {
					throw new EMAnalyticsWSException("The type key for search param is missing in the input JSON Object",
							EMAnalyticsWSException.JSON_SEARCH_PARAM_TYPE_MISSING, je);
				}
				if (type.equals("STRING") | type.equals("CLOB")) {
					searchParam.setType(ParameterType.valueOf(type));
				}
				else {
					throw new EMAnalyticsWSException("Invalid param type, please specify either STRING or CLOB",
							EMAnalyticsWSException.JSON_SEARCH_PARAM_TYPE_INVALID);
				}
				searchParam.setValue(jsonParam.optString("value"));
				searchParamList.add(searchParam);

			}
			searchObj.setParameters(searchParamList);

		}
		else {
			searchObj.setParameters(null);
		}

		return searchObj;

	}

}
