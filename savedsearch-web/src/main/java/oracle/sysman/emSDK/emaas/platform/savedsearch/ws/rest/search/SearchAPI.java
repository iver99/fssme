package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FederationSupportedType;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.IdGenerator;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.ZDTContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsDatabaseUnavailException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.WidgetDeletionNotification;
import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.WidgetNotificationType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.WidgetNotifyEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ModifySystemDataException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.StringUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.ValidationUtil;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emaas.platform.emcpdf.registry.RegistryLookupUtil;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;
import oracle.sysman.emaas.platform.savedsearch.model.ImportMsgModel;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
import oracle.sysman.emaas.platform.savedsearch.targetmodel.services.OdsDataService;
import oracle.sysman.emaas.platform.savedsearch.targetmodel.services.OdsDataServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.persistence.internal.oxm.schema.model.Import;

/**
 * The Search Services
 *
 * @since 0.1
 */
@Path("search")
public class SearchAPI
{

	//private static final String FOLDER_PATH = "flattenedFolderPath";
	private static final Logger LOGGER = LogManager.getLogger(SearchAPI.class);
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
	 * @throws JSONException 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createSearch(JSONObject inputJsonObj) throws JSONException
	{
		LogUtil.getInteractionLogger().info("Service calling to (POST) /savedsearch/v1/search");

		String message = "";
		int statusCode = 201;
		SearchManager sman = SearchManager.getInstance();
		try {
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			Search searchObj = createSearchObjectForAdd(inputJsonObj);
			Search savedSearch = sman.saveSearch(searchObj);
			
			// see if an ODS entity needs to be create
			if(searchObj != null && searchObj.getParameters() != null) {
				for (SearchParameter param : searchObj.getParameters()) {
					if (param.getName().equalsIgnoreCase(OdsDataService.ENTITY_FLAG)
							&& "TRUE".equalsIgnoreCase(param.getValue())) {
						OdsDataService ods = OdsDataServiceImpl.getInstance();
						String meId = ods.createOdsEntity(savedSearch.getId().toString(), savedSearch.getName());
							
						// add ODS Entity ID as one of search parameters of the saved search
						savedSearch.getParameters().add(generateOdsEntitySearchParam(meId));
						
						// store the whole saved search again
						// TODO provide new API to add a search parameter solely
						savedSearch = sman.editSearch(savedSearch);
						break;
					}
				}
			}
			
			message = EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), savedSearch).toString();
		}
		catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
		}
		catch (EMAnalyticsWSException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
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
	public Response deleteSearch(@PathParam("id") BigInteger searchId)
	{
		LogUtil.getInteractionLogger().info("Service calling to (DELETE) /savedsearch/v1/search/{}", searchId);
		int statusCode = 204;
		
		OdsDataService odsService = OdsDataServiceImpl.getInstance();
		SearchManager sman = SearchManager.getInstance();
		
		try {
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			odsService.deleteOdsEntity(searchId);
			EmAnalyticsSearch eas = sman.deleteSearch(searchId, false);
			// TODO: when merging with ZDT, this deletionTime should be from the APIGW request
			Date deletionTime = DateUtil.getCurrentUTCTime();
			WidgetNotifyEntity wne = new WidgetNotifyEntity(eas, deletionTime, WidgetNotificationType.DELETE);
			if (eas.getIsWidget() == 1L) {
				new WidgetDeletionNotification().notify(wne);
			}
		}
		catch (EMAnalyticsFwkException e) {
			LOGGER.error(e.getLocalizedMessage());
			return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
		}

		return Response.status(statusCode).build();
	}

	/**
	 * This API will delete searches by Ids.
	 * NOTE: 1. Because this api have input parameters, so set the API as HTTP PUT in case DELETE method not support body.
	 * 		 2. If you input multiple ids, if anyone of these ids is not existing in DB, will return err to client and delete Nothing finally.
	 * 		 3. Searches will be soft deleted.
	 * 		 4. Till now I plan to delete search one by one, if hit PSR issue in the future, we can consider delete search in bulk.
	 *
	 * 		 Input ex: ["1000,", "1001", "1002"]
	 */
	@PUT
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSearchesByIds(JSONArray searchIdArray)
	{
		LogUtil.getInteractionLogger().info("Service calling to (PUT) /savedsearch/v1/search/delete");
		LOGGER.info("input search id list is {}", searchIdArray);
		OdsDataService odsService = OdsDataServiceImpl.getInstance();
		SearchManager sman = SearchManager.getInstance();
		EntityManager em = null;

		try {
			//check input integrity Part I
			if(searchIdArray == null || (searchIdArray != null && searchIdArray.length() == 0)){
				LOGGER.error("Input Search id list can't be null or empty");
				return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.buildNormalMapper().toJson(new ImportMsgModel(false, "Input can't be null or empty!"))).build();
			}
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			if(em == null){
				LOGGER.error("Can't get EntityManager instance correctly!");
				throw new Exception("Can't get EntityManager instance correctly!");
			}
			//open a txn
			if(!em.getTransaction().isActive()){
				em.getTransaction().begin();
			}

			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			int searchCount = searchIdArray.length();
			LOGGER.info("Deleting search id list length is {}, ids are {}", searchCount, searchIdArray.toString());
			for(int i =0; i<searchCount; i++){
				BigInteger searchId = new BigInteger(searchIdArray.getString(i));
				LOGGER.info("Prepare to delete search with id {}", searchId);
				odsService.deleteOdsEntity(searchId);
				EmAnalyticsSearch eas = sman.deleteSearchWithEm(searchId, em, false);//Soft delete
				// TODO: when merging with ZDT, this deletionTime should be from the APIGW request
				Date deletionTime = DateUtil.getCurrentUTCTime();
				WidgetNotifyEntity wne = new WidgetNotifyEntity(eas, deletionTime, WidgetNotificationType.DELETE);
				if (eas.getIsWidget() == 1L) {
					new WidgetDeletionNotification().notify(wne);
				}
			}
			//Commit txn
			em.getTransaction().commit();
			LOGGER.info("Delete searches and commit txn successfully!");
		}catch (EMAnalyticsFwkException e) {
			em.getTransaction().rollback();
			LOGGER.warn("Rollback txn due to Exception occurred!");
			LOGGER.error(e.getLocalizedMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.buildNormalMapper()
					.toJson(new ImportMsgModel(false, "EMAnalyticsFwkException occurred when delete searches by id list!"))).build();
		}catch (Exception e){
			em.getTransaction().rollback();
			LOGGER.warn("Rollback txn due to Exception occurred!");
			LOGGER.error(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.buildNormalMapper()
					.toJson(new ImportMsgModel(false, "Exception occurred when delete searches by id list!"))).build();
		}finally {
			if(em !=null && em.isOpen()){
				em.close();
			}
		}

		return Response.status(Response.Status.OK).entity(JsonUtil.buildNormalMapper().
				toJson(new ImportMsgModel(false, "All specified search ids are deleted. " + searchIdArray.toString()))).build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSearchByName(@QueryParam("searchName") String name, @QueryParam("isExactly") String isExactly) {
		LogUtil.getInteractionLogger().info("Service calling to (DELETE) /savedsearch/v1/search?searchName={}&isExactly={}", name, isExactly);
		SearchManager searchManager = SearchManager.getInstance();
		if (isExactly == null) {
			LOGGER.debug("isExactly is null and set the default value true");
			isExactly = "true";
		}
		if (!"true".equalsIgnoreCase(isExactly) && !"false".equalsIgnoreCase(isExactly)) {
			LOGGER.error("The param isExactly is invalid");
			return Response.status(Response.Status.BAD_REQUEST).entity("The param isExactly is invalid").build();
		}
		try {
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			LOGGER.debug("Calling searchManager.deleteSearchByName");
			searchManager.deleteSearchByName(name, Boolean.valueOf(isExactly));
		} catch (EMAnalyticsFwkException e) {
			LOGGER.error(e.getLocalizedMessage());
			return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
		}
		return Response.status(Response.Status.OK).build();
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
			@PathParam("id") BigInteger searchId)
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/search/{}", searchId);
		String message = null;
		int statusCode = 200;
		Search searchObj;
		SearchManager sman = SearchManager.getInstance();
		try {
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			if (updateCategory != null && "ORACLE_INTERNAL".equals(updateCategory)) {
				searchObj = createSearchObjectForEdit(inputJsonObj, sman.getSearch(searchId), true);

			}
			else {
				searchObj = createSearchObjectForEdit(inputJsonObj, sman.getSearch(searchId), false);
			}
			Search savedSearch = sman.editSearch(searchObj);
			message = EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), savedSearch).toString();
		}
		catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
		}
		catch (EMAnalyticsWSException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
		}

		return Response.status(statusCode).entity(message).build();

	}

	/**
	 * Update search in bulk, NOTE: this api have multiple txn, not in one transaction, need to fix later.
	 * @param jsonArray
	 * @return
	 */
	@PUT
	@Path("searches")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editSearches(JSONArray jsonArray)
	{
		LogUtil.getInteractionLogger().info("Service calling to (PUT) /savedsearch/v1/search/searches");
		Search searchObj;
		SearchManager sman = SearchManager.getInstance();
		EntityManager em = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			if(em == null){
				LOGGER.error("Can't get EntityManager instance correctly!");
				throw new Exception("Can't get EntityManager instance correctly!");
			}
			//open a txn
			if(!em.getTransaction().isActive()){
				em.getTransaction().begin();
			}
			if(jsonArray == null || jsonArray.length() == 0){
				LOGGER.error("input json array can not be null or empty!");
				throw new Exception("input json array can not be null or empty!");
			}
			JSONArray result = new JSONArray();
			for(int i = 0 ; i <jsonArray.length(); i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				LOGGER.info("Prepare to update search with id {}", jsonObject.get("id"));
				searchObj = createSearchObjectForEdit(jsonObject, sman.getSearch(new BigInteger(jsonObject.get("id").toString())), false);
				Search savedSearch = sman.editSearchWithEm(searchObj, false, em);
				JSONObject jsonObject1 = new JSONObject(EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), savedSearch).toString());
				result.put(jsonObject1);
			}
			LOGGER.info("Searches are updated and txn is committed successfully!");
			em.getTransaction().commit();
			return Response.status(Response.Status.OK).entity(result.toString()).build();
		}catch (EMAnalyticsFwkException |EMAnalyticsWSException e) {
			em.getTransaction().rollback();
			LOGGER.error("Transaction will be roll back due to exception occurred!");
			LOGGER.error(e);
		}catch (Exception e){
			em.getTransaction().rollback();
			LOGGER.error("Transaction will be roll back due to exception occurred!");
			LOGGER.error(e);
		}finally {
			if(em !=null && em.isOpen()){
				em.close();
			}
		}

		return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.buildNormalMapper().
				toJson(new ImportMsgModel(false, "Can't finish searches edit actions! Please check log!"))).build();

	}
	
	@PUT
	@Path("{id: [0-9]*}/odsentity")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOdsEntity(@PathParam("id") BigInteger searchId) {
		String message = null;
		int statusCode = 200;
		SearchManager sman = SearchManager.getInstance();
		Search savedSearch = null;
		
		try {
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			savedSearch = sman.getSearch(searchId);
		} catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
		}
		
		if(savedSearch != null) {
			if(savedSearch.getParameters() == null) {
				savedSearch.setParameters(new ArrayList<SearchParameter>());
			}
			for (SearchParameter param : savedSearch.getParameters()) {
				if(param.getName().equalsIgnoreCase(OdsDataService.ENTITY_ID)) {
					statusCode = 400;
					message = "Exist Entity ID: " + param.getValue();
					LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message);
				}
			}
			
			if(statusCode != 400) {
				OdsDataService ods = OdsDataServiceImpl.getInstance();
				try {
					String meId = ods.createOdsEntity(savedSearch.getId().toString(), savedSearch.getName());
					savedSearch.getParameters().add(generateOdsEntitySearchParam(meId));
					savedSearch = sman.editSearch(savedSearch, true);
					message = EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), savedSearch).toString();
				} catch (EMAnalyticsFwkException e) {
					statusCode = e.getStatusCode();
					message = e.getMessage();
					LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
				}
			}
		}
		
		return Response.status(statusCode).entity(message).build();
	}
	
	/**
	 * Just return the whole search and will be removed
	 * @param searchId
	 * @return
	 */
	@Deprecated
	@PUT
	@Path("{id: [0-9]*}/updatelastaccess")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editLastAccess(@PathParam("id") BigInteger searchId) {
		LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/search/{}/updatelastaccess", searchId);
		
		String message = null;
		int statusCode = 200;
		ObjectNode jsonObj = null;
		
		// find and return the whole object
		SearchManager sman = SearchManager.getInstance();
		try {
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			Search searchObj = sman.getSearchWithoutOwner(searchId);
			jsonObj = EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), searchObj);
			message = jsonObj.toString();
		}
		catch (EMAnalyticsFwkException e) {
			statusCode = e.getStatusCode();
			message = e.getMessage();
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + e.getMessage(),
					e.getStatusCode());
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
	@Deprecated
	@PUT
	@Path("{id: [0-9]*}")
	public Response editSearchAccessDate(@PathParam("id") BigInteger searchId, @QueryParam("updateLastAccessTime") boolean update)
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
				java.util.Date date = DateUtil.getCurrentUTCTime();
				String message = String.valueOf(DateUtil.getDateFormatter().format(date));
				return Response.status(200).entity(message).build();
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
	 * save imported widget data
     * NOTE: *If you are override a search, you should not modify its PK(name,cat id,folder id....etc) fields,
     * otherwise this search can't be found!!!(Actually will create a new Search)*
	 * @param override
	 * @param importedData
	 * @return Example:
     *  override=false:
	 * 	{"2004":"191134497286884694333701301925787569634",
	 * 	"2026":"303726178767420504723169950985867708641",
	 * 	"2022":"182578934661553784711389571629249005671",
	 * 	"2020":"65426205633609290111501435536176656608"}
     *
     * 	override = true:
     * 	{
     *   "18990242278817038474213646037468377095": "18990242278817038474213646037468377095"
     *   }
	 */
	@PUT
	@Path("/import")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response importData(@QueryParam("override") @DefaultValue("false") boolean override, JSONArray importedData) {

		LogUtil.getInteractionLogger().info("Service calling to (PUT) savedsearch/v1/search/import?override={}", override);
		SearchManager searchManager = SearchManager.getInstance();
		Map<String, String> idMaps = new HashMap<>();
        EntityManager em = null;
		try {
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
            em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			if(em == null){
				LOGGER.error("EntityManager is NULL/Not fetched correctly.");
				throw new Exception("EntityManager is NULL/Not fetched correctly.");
			}
			if(importedData == null || importedData.length() == 0){
				LOGGER.error("Input cannot be null or empty");
				return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.buildNormalMapper().toJson(new ImportMsgModel(false, "Input cannot be null or empty!"))).build();
			}
			int widgetCount = importedData.length();
			LOGGER.info("Import search count is {}", widgetCount);
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}

			for (int i = 0; i < widgetCount; i++) {
				JSONObject inputJsonObj = importedData.getJSONObject(i);
				//original id is DB's id
				String originalId = inputJsonObj.getString("id");
				LOGGER.info("Begin to import search with id {}", originalId);
				List<Map<String, Object>> idAndNameList = getIdAndNameByUniqueKey(inputJsonObj, em);
				LOGGER.info("get Id and Name By UK for search id {} is {}", originalId, idAndNameList);
				Search searchObj = null;
			    if (idAndNameList != null && !idAndNameList.isEmpty()) {
			    	Map<String, Object> idAndName = idAndNameList.get(0);
			    	//NOTE: this id should be same with original id if you are override a search!!!!
			    	BigInteger id  = new BigInteger(idAndName.get("SEARCH_ID").toString());
			    	String name = idAndName.get("NAME").toString();
			    	if (override) {
				    		// override existing row
							LOGGER.info("Begin to edit search with id {}, override is {}", originalId, override);
				    		if (inputJsonObj.getBoolean("editable")) {
				    			searchObj = createSearchObjectForEdit(inputJsonObj, searchManager.getSearch(id), false);
				    			searchObj.setEditable(true);
					    		searchManager.editSearchWithEm(searchObj, false, em);
					    		idMaps.put(originalId, id.toString());
				    		}else {
				    		    //need throw a exception here.
                                LOGGER.error("User try to override a system search!");
                                throw new ModifySystemDataException();
				    		}
				    		
				    	} else {
				    		// insert new row
                            //hard code isWidget = 1
							inputJsonObj.put("isWidget","true");
						//remove owner fields for import api, will set current user later
						inputJsonObj.remove("owner");
						searchObj = createSearchObjectForAdd(inputJsonObj);
						searchObj.setEditable(true);
						//NOTE new name suffix now is a random
						int num = new SecureRandom().nextInt(9999);
						String newName = name + "_" + num;
						LOGGER.info("new search name is {}", newName);
						searchObj.setName(newName);
						Search savedSearch = searchManager.saveSearchWithEm(searchObj, em);
						LOGGER.info("Begin to create search with original id {}, new search id is {}, override is {}", originalId, savedSearch.getId(), override);
						idMaps.put(originalId, savedSearch.getId().toString());
							// see if an ODS entity needs to be create
                            createOdsEntity(searchManager, searchObj, savedSearch);
				    	}
			    	
			    } else {
			        //id and name is not existing. create a new row
                    //hard code isWidget = 1
                    inputJsonObj.put("isWidget","true");
                    //remove owner fields for import api, will set current user later
					inputJsonObj.remove("owner");
					searchObj = createSearchObjectForAdd(inputJsonObj);
					searchObj.setEditable(true);
					Search savedSearch = searchManager.saveSearchWithEm(searchObj, em);
					LOGGER.info("Begin to create search with original id {}, new search id is {}, override is {}", originalId, savedSearch.getId(), override);

					idMaps.put(originalId, savedSearch.getId().toString());
					// see if an ODS entity needs to be create
                    createOdsEntity(searchManager, searchObj, savedSearch);
			    }
				
			}
			LOGGER.info("Save Searches successfully but not commit yet. preparing response...");
            if (!idMaps.isEmpty()) {
                Set<String> keySet = idMaps.keySet();
                JSONObject obj = new JSONObject();
                for (String key : keySet) {
                    obj.put(key, idMaps.get(key));
                }
                //Commit this big transaction.
                LOGGER.info("import API will return response {}", obj.toString());
				em.getTransaction().commit();
				LOGGER.info("Import searches successfully, commit txn successfully!");
				return Response.status(Response.Status.OK).entity(obj.toString()).build();
            } else {
			    LOGGER.error("import API will return no_content response which is unexpected...");
				throw new Exception("import API will return no_content response which is unexpected...");
            }
		}catch(ModifySystemDataException e){
			em.getTransaction().rollback();
			LOGGER.error("Rollback txn due to exception occurred!");
            LOGGER.error(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.buildNormalMapper().toJson(new ImportMsgModel(false, "ModifySystemDataException found in SSF service!"))).build();
        }catch (EMAnalyticsFwkException e) {
			em.getTransaction().rollback();
			LOGGER.error("Rollback txn due to exception occurred!");
			LOGGER.error(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.buildNormalMapper().toJson(new ImportMsgModel(false, "EMAnalyticsFwkException found in SSF service!"))).build();
        }catch (EMAnalyticsWSException e) {
			em.getTransaction().rollback();
			LOGGER.error("Rollback txn due to exception occurred!");
            LOGGER.error(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.buildNormalMapper().toJson(new ImportMsgModel(false, "EMAnalyticsWSException found in SSF service!"))).build();
        } catch (JSONException e) {
			em.getTransaction().rollback();
			LOGGER.error("Rollback txn due to exception occurred!");
		    LOGGER.error(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.buildNormalMapper().toJson(new ImportMsgModel(false, "JSONException found in SSF service!"))).build();
		}catch(Exception e){
			em.getTransaction().rollback();
			LOGGER.error("Rollback txn due to exception occurred!");
            LOGGER.error(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.buildNormalMapper().toJson(new ImportMsgModel(false, "Exception found in SSF service! " + e.getMessage()))).build();
        }finally {
			if(em != null){
				em.close();
			}
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
	public Response getSearch(@PathParam("id") BigInteger searchid, @QueryParam("flattenedFolderPath") boolean bPath)
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/search/{}?flattenedFolderPath={}",
				searchid, bPath);
		String message = null;
		int statusCode = 200;
		SearchManager sman = SearchManager.getInstance();
		try {
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			Search searchObj = sman.getSearchWithoutOwner(searchid);
			String[] pathArray = null;
			if (bPath) {
				FolderManager folderMgr = FolderManager.getInstance();
				pathArray = folderMgr.getPathForFolderId(searchObj.getFolderId());
			}
			message = EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), searchObj, pathArray).toString();
		}
		catch (EMAnalyticsFwkException e) {
			LOGGER.error(e.getLocalizedMessage());
			statusCode = e.getStatusCode();
			message = e.getMessage();
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + e.getMessage(),
					e.getStatusCode());
		}

		return Response.status(statusCode).entity(message).build();
	}

	/**
	 * get searches by id list, set method into a PUT method because there need a body
	 * @param jsonArray
	 * @return
	 */
	@PUT
	@Path("ids")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchesByIds(JSONArray jsonArray){
		LogUtil.getInteractionLogger().info("Service calling to (PUT) /savedsearch/v1/search/ids");

		SearchManager sman = SearchManager.getInstance();
		LOGGER.info("Prepare to get searches by ids: {}", jsonArray);
		try {
			if(jsonArray == null || (jsonArray !=null && jsonArray.length() == 0)){
				LOGGER.error("input id array can't be null or empty!");
				throw new Exception("input id array can't be null or empty!");
			}
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			StringBuilder sb = new StringBuilder("[");
			for(int i =0; i<jsonArray.length(); i++){
				BigInteger id = new BigInteger(jsonArray.get(i).toString());
				LOGGER.info("Prepare to retrieve search with id {}", id);
				Search searchObj = sman.getSearchWithoutOwner(id);
				String[] pathArray = null;
				String searchString = EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), searchObj, pathArray).toString();
				sb.append(searchString);
				if(i < jsonArray.length()-1){
					sb.append(",");
				}
			}
			sb.append("]");
			LOGGER.info("Retrieved searches are: {}", sb.toString());
			return Response.status(Response.Status.OK).entity(sb.toString()).build();
		}catch (EMAnalyticsFwkException e) {
			LOGGER.error(e.getLocalizedMessage());
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + e.getMessage(),
					e.getStatusCode());
		}catch(Exception e){
			LOGGER.error(e);
		}

		return Response.status(Response.Status.BAD_REQUEST).entity(JsonUtil.buildNormalMapper().toJson(new ImportMsgModel(false, "Bad request! check you input!"))).build();
	}
	
	/**
	 * 
	 * @param searchName
	 * @return
	 */
    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSearchByName(@PathParam("name") String searchName) {
        LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/search/{}", searchName);
        String message;
        int statusCode = 200;
        SearchManager sman = SearchManager.getInstance();
        try {
            if (!DependencyStatus.getInstance().isDatabaseUp()) {
                throw new EMAnalyticsDatabaseUnavailException();
            }
            Search searchObj = sman.getSearchWithoutOwnerByName(searchName);
            message = EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), searchObj).toString();
        } catch (EMAnalyticsFwkException e) {
            statusCode = e.getStatusCode();
            message = e.getMessage();
            LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + e.getMessage(),
                    e.getStatusCode());
        } catch(Exception e){
			statusCode = 505;
			message = "Fail to get search by name, "+searchName;
			LOGGER.error(e);
		}
        return Response.status(statusCode).entity(message).build();
    }
    
	/**
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
    @GET
    @Path("/param")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSearchByParam(@QueryParam("name") String name, @QueryParam("value") String value) {
        LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/search/parameter?name={}&value={}", 
                name, value);
        String message = null;
        int statusCode = 200;
        if (name == null || name.isEmpty() || value == null || value.isEmpty()) {
            statusCode = 400;
            message = "Neither search parameter name nor search parameter value can be null or empty!";
        } else {
            ArrayNode outputJsonArray = new ObjectMapper().createArrayNode();
            SearchManager sman = SearchManager.getInstance();
            try {
                if (!DependencyStatus.getInstance().isDatabaseUp()) {
                    throw new EMAnalyticsDatabaseUnavailException();
                }
                List<Search> searchList = sman.getSearchListWithoutOwnerByParam(name, value);
                for (Search searchObj : searchList) {
                    outputJsonArray.add(EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), searchObj));
                }
                message = outputJsonArray.toString();
            } catch (EMAnalyticsFwkException e) {
                LOGGER.error(e.getLocalizedMessage());
                statusCode = e.getStatusCode();
                message = e.getMessage();
                LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + e.getMessage(),
                        e.getStatusCode());
            }
        }
        return Response.status(statusCode).entity(message).build();
    }
	
	@PUT
	@Path("/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchList(String inputIdList)
	{
		LogUtil.getInteractionLogger().info("Service calling to (PUT) /savedsearch/v1/search/list");
		List<BigInteger> searchIdList = new ArrayList<BigInteger>();
		try {
			JsonNode inputJsonArray = new ObjectMapper().readTree(inputIdList);
			for(Iterator<JsonNode> i = inputJsonArray.getElements(); i.hasNext(); ) {
				searchIdList.add(new BigInteger(i.next().asText()));
			}
		} catch (IOException e) {
			return Response.status(404).entity("search id list can be parsed").build();
		} catch(NullPointerException npe) {
			return Response.status(404).entity("search id list is empty").build();
		} catch(NumberFormatException nfe) {
			return Response.status(404).entity("invalid search id in search id list").build();
		}
		
		String message = null;
		int statusCode = 200;
		ArrayNode outputJsonArray = new ObjectMapper().createArrayNode();
		SearchManager sman = SearchManager.getInstance();
		try {
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			
			List<Search> searchList = sman.getSearchListByIds(searchIdList);
			for(Search searchObj : searchList) {
				outputJsonArray.add(EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), searchObj));
			}
			message = outputJsonArray.toString();
		} catch (EMAnalyticsFwkException e) {
			LOGGER.error(e.getLocalizedMessage());
			statusCode = e.getStatusCode();
			message = e.getMessage();
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + e.getMessage(),
					e.getStatusCode());
		}
		return Response.status(statusCode).entity(message).build();
	}

	
	@PUT
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchData(JSONArray inputJsonArray) throws JSONException
	{
		LogUtil.getInteractionLogger().info("Service calling to (PUT) /savedsearch/v1/search/all");
		LOGGER.info("Input is {}", inputJsonArray);
		SearchManager sman = SearchManager.getInstance();
		ArrayNode outputJsonArray = new ObjectMapper().createArrayNode();
		try {
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			List<BigInteger> searchIdList = new ArrayList<BigInteger>();
			for(int i = 0; i < inputJsonArray.length(); i++) {
				searchIdList.add(new BigInteger(inputJsonArray.getString(i)));
			}			
			for (BigInteger id : searchIdList) {
				Search searchObj = sman.getSearchWithoutOwner(id);
				outputJsonArray.add(EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), searchObj));
			}
		}
		catch (EMAnalyticsFwkException e) {
			LOGGER.error(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ImportMsgModel(false, "Error occurred when get Search data!")).build();
		}
		LOGGER.info("Get Search data is {]", outputJsonArray.toString());
		return Response.status(Response.Status.OK).entity(outputJsonArray.toString()).build();
	}


	@GET
	@Path("{id: [0-9]*}/assetroot")
	public Response getAssetRoot(@PathParam("id") BigInteger searchid){
		LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/assetroot/{}",
				searchid);
		SearchManager searchManager = SearchManager.getInstance();
		CategoryManager categoryManager = CategoryManager.getInstance();
		String serviceName;
		String version;
		String rel;
		try{
			if (!DependencyStatus.getInstance().isDatabaseUp()) {
				throw new EMAnalyticsDatabaseUnavailException();
			}
			Search search = searchManager.getSearch(searchid);
			if(search.getCategoryId()==null){
				return Response.status(Response.Status.NOT_FOUND).entity("Search with id "+searchid
						+"not found ").build();
			}
			Category category = categoryManager.getCategory(search.getCategoryId());
			serviceName = category.getProviderName();
			version = category.getProviderVersion();
			rel = category.getProviderAssetRoot();
			if(StringUtil.isEmpty(serviceName)||StringUtil.isEmpty(version)||StringUtil.isEmpty(rel)){
				return Response.status(Response.Status.NOT_FOUND).entity("Category not found").build();
			}
			if(!version.endsWith("+")){
				LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + "The version is wrong",version);
				version +="+";
			}
			RegistryLookupUtil.VersionedLink linkInfo = RegistryLookupUtil.getServiceExternalLink(serviceName, version, rel, TenantContext.getContext().gettenantName());
			if(linkInfo == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Failed:"+serviceName+","+version+","+rel+","+TenantContext.getContext().gettenantName()).build();
			}
			Link link = RegistryLookupUtil.replaceWithVanityUrl(linkInfo, TenantContext.getContext().gettenantName(), serviceName);
			if(link == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("The link is null").build();
			}
			return Response.status(Response.Status.OK).entity(JsonUtil.buildNormalMapper().toJson(link)).build();
		}catch (EMAnalyticsFwkException e) {
			LOGGER.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + e.getMessage(),
					e.getStatusCode());
			return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
		}
	}
	
	private List<Map<String, Object>> getIdAndNameByUniqueKey(JSONObject json, EntityManager em) {
		try {
			String name = json.getString("name");
			if (name != null && "".equals(name.trim())) {
				throw new EMAnalyticsWSException("The name key for search can not be empty in the input JSON Object",
						EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING);
			}
		  String categoryIdStr = json.getJSONObject("category").getString("id");
		  BigInteger categoryId = new BigInteger(categoryIdStr);
		  String folderIdStr = json.getJSONObject("folder").getString("id");
		  BigInteger folderId = new BigInteger(folderIdStr);
		  BigInteger deleted = new BigInteger("0");
		  //EMCPSSF-649 fix
		  String currentUser = TenantContext.getContext().getUsername();
		  LOGGER.info("Current user is {}", currentUser);
//		  String owner = json.getString("owner");
		  SearchManager searchManager = SearchManager.getInstance();
		  return searchManager.getSearchIdAndNameByUniqueKey(name, folderId, categoryId, deleted, currentUser, em);
		} catch(Exception e) {
			LOGGER.error(e);
		}
		return Collections.emptyList();
	}
	
	private Search createSearchObjectForAdd(JSONObject json) throws EMAnalyticsWSException, JSONException
	{
		Search searchObj = new SearchImpl();
		
		// set id provided by gateway
	
		searchObj.setId(IdGenerator.getIntUUID(ZDTContext.getRequestId()));
		
		
		// Data population !
		try {
			String name = json.getString("name");
			if (name != null && "".equals(name.trim())) {
				throw new EMAnalyticsWSException("The name key for search can not be empty in the input JSON Object",
						EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING);
			}

			if (StringUtil.isSpecialCharFound(name)) {
				throw new EMAnalyticsWSException(
						"The search name contains at least one invalid character ('<' or '>'), please correct search name and retry",
						EMAnalyticsWSException.JSON_INVALID_CHAR);
			}

			try {
				ValidationUtil.validateLength("name", name, 64);
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
			if (json.has("description")) {
				desc = json.getString("description");
				if (StringUtil.isSpecialCharFound(desc)) {
					throw new EMAnalyticsWSException(
							"The search description contains at least one invalid character ('<' or '>'), please correct search description and retry",
							EMAnalyticsWSException.JSON_INVALID_CHAR);
				}
			}
			

		}
		catch (JSONException je) {
			LOGGER.error(je.getLocalizedMessage());
		}

		try {
			ValidationUtil.validateLength("description", desc, 256);
		}
		catch (EMAnalyticsWSException e) {
			throw e;
		}

		try {
			JSONObject jsonObj = json.getJSONObject("category");
			searchObj.setCategoryId(new BigInteger(jsonObj.getString("id")));
		}
		catch (JSONException je) {
			throw new EMAnalyticsWSException("The category key for search is missing in the input JSON Object",
					EMAnalyticsWSException.JSON_SEARCH_CATEGORY_ID_MISSING, je);
		}
		try {
			JSONObject jsonFold = json.getJSONObject("folder");
			searchObj.setFolderId(new BigInteger(jsonFold.getString("id")));
		}
		catch (JSONException je) {
			throw new EMAnalyticsWSException("The folder key for search is missing in the input JSON Object",
					EMAnalyticsWSException.JSON_SEARCH_FOLDER_ID_MISSING, je);
		}

		String owner = "";
		try {
			if (json.has("owner")) {
				owner = json.getString("owner");
				if (StringUtil.isSpecialCharFound(owner)) {
					throw new EMAnalyticsWSException(
							"The search description contains at least one invalid character ('<' or '>'), please correct search description and retry",
							EMAnalyticsWSException.JSON_INVALID_CHAR);
				}
				if("ORACLE".equals(owner)){
					throw new EMAnalyticsWSException(
							"The owner of customer search can not be ORACLE. Please correct the owner and retry",
							EMAnalyticsWSException.JSON_INVALID_OWNER);
				}
			}
		}
		catch (JSONException je) {
			LOGGER.error(je.getLocalizedMessage());
		}

		// Nullable properties !
		searchObj.setMetadata(json.optString("metadata", searchObj.getMetadata()));
		searchObj.setQueryStr(json.optString("queryStr", searchObj.getQueryStr()));
		searchObj.setDescription(json.optString("description", searchObj.getDescription()));
		// non-nullable with db defaults !!
		searchObj.setLocked(Boolean.parseBoolean(json.optString("locked", Boolean.toString(searchObj.isLocked()))));
		searchObj.setUiHidden(Boolean.parseBoolean(json.optString("uiHidden", Boolean.toString(searchObj.isUiHidden()))));
		searchObj.setIsWidget(Boolean.parseBoolean(json.optString("isWidget", Boolean.toString(searchObj.getIsWidget()))));
		searchObj.setFederationSupported(json.optString("federationSupported", searchObj.getFederationSupported()));

		boolean isWidget = searchObj.getIsWidget();
		
		boolean hasWidgetTemplate = false;
		boolean hasWidgetViewModel = false;
		boolean hasWidgetKocName = false;
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
					if (name != null && "".equals(name.trim())) {
						throw new EMAnalyticsWSException(
								"The name key for search param can not be empty in the input JSON Object",
								EMAnalyticsWSException.JSON_SEARCH_PARAM_NAME_MISSING);
					}
					if (name.equals("WIDGET_TEMPLATE")) {
						hasWidgetTemplate = true;
					} else if (name.equals("WIDGET_VIEWMODEL")) {
						hasWidgetViewModel = true;
					} else if (name.equals("WIDGET_KOC_NAME")) {
						hasWidgetKocName = true;
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
				if ("STRING".equals(type) || "CLOB".equals(type)) {
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
		if(isWidget){
			if (!hasWidgetTemplate) {
				JSONObject obj = new JSONObject();
				obj.put("errorCode", EMAnalyticsWSException.JSON_MISSING_WIDGET_TEMPLATE);
				obj.put("message", "Widget template is a required field for a widget, please add it");
				throw new EMAnalyticsWSException(obj.toString(),
						EMAnalyticsWSException.JSON_MISSING_WIDGET_TEMPLATE);
			}
			if (!hasWidgetViewModel) {
				JSONObject obj = new JSONObject();
				obj.put("errorCode", EMAnalyticsWSException.JSON_MISSING_WIDGET_VIEWMODEL);
				obj.put("message", "Widget view model is a required field for a widget, please add it");
				throw new EMAnalyticsWSException(obj.toString(),
						EMAnalyticsWSException.JSON_MISSING_WIDGET_VIEWMODEL);
			}
			if (!hasWidgetKocName) {
				JSONObject obj = new JSONObject();
				obj.put("errorCode", EMAnalyticsWSException.JSON_MISSING_WIDGET_KOC_NAME);
				obj.put("message", "Widget koc name is a required field for a widget, please add it");
				throw new EMAnalyticsWSException(obj.toString(),
						EMAnalyticsWSException.JSON_MISSING_WIDGET_KOC_NAME);
			}
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
			if (name != null && "".equals(name.trim())) {
				throw new EMAnalyticsWSException("The name key for search can not be empty in the input JSON Object",
						EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING);
			}
			if (StringUtil.isSpecialCharFound(name)) {
				throw new EMAnalyticsWSException(
						"The search name contains at least one invalid character ('<' or '>'), please correct search name and retry",
						EMAnalyticsWSException.JSON_INVALID_CHAR);
			}

			try {
				ValidationUtil.validateLength("name", name, 64);
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
			ValidationUtil.validateLength("description", desc, 256);
		}
		catch (EMAnalyticsWSException e) {
			throw e;
		}
		searchObj.setDescription(json.optString("description", searchObj.getDescription()));
		if (update) {
			if (json.has("category")) {
				try {
					JSONObject jsonObj = json.getJSONObject("category");
					searchObj.setCategoryId(new BigInteger(jsonObj.getString("id")));
				}
				catch (JSONException je) {
					throw new EMAnalyticsWSException("The category key for search is missing in the input JSON Object",
							EMAnalyticsWSException.JSON_SEARCH_CATEGORY_ID_MISSING, je);
				}
			}
			else {
				searchObj.setCategoryId(searchObj.getCategoryId());
			}
		}
		if (json.has("folder")) {
			try {
				JSONObject jsonFold = json.getJSONObject("folder");
				searchObj.setFolderId(new BigInteger(jsonFold.getString("id")));
			}
			catch (JSONException je) {
				throw new EMAnalyticsWSException("The folder key for search is missing in the input JSON Object",
						EMAnalyticsWSException.JSON_SEARCH_FOLDER_ID_MISSING, je);
			}
		}
		else {
			searchObj.setFolderId(searchObj.getFolderId());
		}

		searchObj.setLocked(Boolean.parseBoolean(json.optString("locked", Boolean.toString(searchObj.isLocked()))));
		searchObj.setUiHidden(Boolean.parseBoolean(json.optString("uiHidden", Boolean.toString(searchObj.isUiHidden()))));
		searchObj.setIsWidget(Boolean.parseBoolean(json.optString("isWidget", Boolean.toString(searchObj.getIsWidget()))));
		searchObj.setFederationSupported(json.optString("federationSupported", searchObj.getFederationSupported()));

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
					if (name != null && "".equals(name.trim())) {
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
				if ("STRING".equals(type) || "CLOB".equals(type)) {
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
    private void createOdsEntity(SearchManager searchManager, Search searchObj, Search savedSearch) throws EMAnalyticsFwkException {
        if(searchObj != null && searchObj.getParameters() != null) {
            for (SearchParameter param : searchObj.getParameters()) {
                if (param.getName().equalsIgnoreCase(OdsDataService.ENTITY_FLAG)
                        && "TRUE".equalsIgnoreCase(param.getValue())) {
                    OdsDataService ods = OdsDataServiceImpl.getInstance();
                    String meId = ods.createOdsEntity(savedSearch.getId().toString(), savedSearch.getName());

                    // add ODS Entity ID as one of search parameters of the saved search
                    savedSearch.getParameters().add(generateOdsEntitySearchParam(meId));

                    // store the whole saved search again
                    // TODO provide new API to add a search parameter solely
                    searchManager.editSearch(savedSearch);
                    break;
                }
            }
        }
    }

}
