package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.category;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * The Category Services
 * 
 * @since 0.1
 */
@Path("category")
public class CategoryAPI
{

	@Context
	private UriInfo uri;
	private final String resourcePath = "oracle/sysman/emSDK/emaas/platform/savedsearch/ws/rest/importsearch/search.xsd";
	private static final Logger _logger = LogManager.getLogger(CategoryAPI.class);

	/*	@DELETE
		@Path("{id : [0-9]*}")
		public Response deleteCategory(@PathParam("id") int categoryId)
		{
			//Response res=Response.ok().build();
			int statusCode = 204;

			CategoryManager catMan = CategoryManager.getInstance();
			try {
				catMan.deleteCategory(categoryId);

			}
			catch (EMAnalyticsFwkException e) {
				return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
			}
			return Response.status(statusCode).build();
		}

		@DELETE
		public Response deleteCategoryByName(@QueryParam("name") String name)
		{

			int statusCode = 204;
			if (name == null) {
				return Response.status(400).entity("please give category name").build();
			}
			else if (name.equals("")) {
				return Response.status(400).entity("please give category name").build();
			}
			CategoryManager catMan = CategoryManager.getInstance();
			try {
				Category category = catMan.getCategory(name);
				int categoryId = category.getId();
				catMan.deleteCategory(categoryId);

			}
			catch (EMAnalyticsFwkException e) {
				return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
			}
			return Response.status(statusCode).build();
		}

		@PUT
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@Path("{id : [0-9]*}")
		public Response editCategory(JSONObject inputJsonObj, @PathParam("id") int categoryId)
		{
			String sMsg = "";
			int statusCode = 200;
			JSONObject jsonObj;
			CategoryManager catMan = CategoryManager.getInstance();
			try {
				Category objCategory = createCategoryObjectForEdit(inputJsonObj, catMan.getCategory(categoryId));
				objCategory = catMan.editCategory(objCategory);
				sMsg = JSONUtil.ObjectToJSONString(objCategory);
			}
			catch (EMAnalyticsFwkException e) {
				sMsg = e.getMessage();
				statusCode = e.getStatusCode();
			}
			catch (EMAnalyticsWSException e) {
				sMsg = e.getMessage();
				statusCode = e.getStatusCode();
			}
			return Response.status(statusCode).entity(sMsg).build();
		}

		@PUT
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response editCategoryByName(JSONObject inputJsonObj, @QueryParam("name") String name)
		{
			String sMsg = "";
			int statusCode = 200;
			if (name == null) {
				return Response.status(400).entity("please give category name").build();
			}
			else if (name.equals("")) {
				return Response.status(400).entity("please give category name").build();
			}
			CategoryManager catMan = CategoryManager.getInstance();
			try {
				Category objCategory = createCategoryObjectForEdit(inputJsonObj, catMan.getCategory(name));
				objCategory = catMan.editCategory(objCategory);
				sMsg = JSONUtil.ObjectToJSONString(objCategory);
			}
			catch (EMAnalyticsFwkException e) {
				sMsg = e.getMessage();
				statusCode = e.getStatusCode();
			}
			catch (EMAnalyticsWSException e) {
				sMsg = e.getMessage();
				statusCode = e.getStatusCode();
			}
			return Response.status(statusCode).entity(sMsg).build();
		}

		@GET
		@Path("/get/all")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getAllCategory()
		{
			String message = null;
			int statusCode = 200;
			JSONArray jsonArray = new JSONArray();
			Category category;
			List<Category> catList = new ArrayList<Category>();

			CategoryManager catMan = CategoryManager.getInstance();
			try {

				catList = catMan.getAllCategories();
				try {
					for (int i = 0; i < catList.size(); i++) {
						category = catList.get(i);
						try {
							jsonArray.put(JSONUtil.ObjectToJSONObject(category));
						}
						catch (JSONException e) {
							message = e.getMessage();
							statusCode = 500;
							return Response.status(statusCode).entity(message).build();
						}
					}
					message = jsonArray.toString();
				}
				catch (EMAnalyticsWSException e) {
					message = e.getMessage();
					statusCode = e.getStatusCode();
					return Response.status(statusCode).entity(message).build();
				}
			}
			catch (EMAnalyticsFwkException e) {
				message = e.getMessage();
				statusCode = e.getStatusCode();
			}
			return Response.status(statusCode).entity(message).build();
		}
	 */
	/**
	 * Get the details of category with the given category Id<br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/category/&lt;id&gt;</font><br>
	 * The string "/category/&lt;id&gt;" in the URL signifies read operation on category with given category Id.
	 * 
	 * @since 0.1
	 * @param categoryId
	 *            The category Id which user want to read the details
	 * @return Return the complete details of category with given category Id<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "id": 1, <br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "name": "Log Analytics",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "description": "Search Category for Log Analytics",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "owner": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "createdOn": "2014-07-22T14:48:53.048Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "providerName": "Log Analytics",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "providerVersion": "1.0",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "providerAssetRoot": "assetRoot",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "defaultFolder": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 2,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/2"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "parameters": [<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "CATEGORY_PARAM_VIEW_TASKFLOW",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value":
	 *         "/WEB-INF/core/loganalytics/obssearch/plugins/dashboard-flow-definition.xml#dashboard-flow-definition",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "STRING"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; ],<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "href": "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/category/1"<br>
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
	 *         <td>List category details successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>404</td>
	 *         <td>Not Found</td>
	 *         <td>Category object by ID: &lt;id&gt; does not exist</td>
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
	public Response getCategory(@PathParam("id") int categoryId)
	{
		String message = null;
		int statusCode = 200;
		CategoryManager catMan = CategoryManager.getInstance();
		try {
			Category category = catMan.getCategory(categoryId);
			JSONObject jsonObj = EntityJsonUtil.getFullCategoryJsonObj(uri.getBaseUri(), category);
			message = jsonObj.toString();
		}
		catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			statusCode = 400;
			message = e.getMessage();
		}
		return Response.status(statusCode).entity(message).build();
	}

	/**
	 * Get the details of category with given category name<br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/category?name=&lt;name&gt;</font><br>
	 * The string "/category?name=&lt;name&gt;" in the URL signifies read operation on category with given category name.
	 * 
	 * @since 0.1
	 * @param name
	 *            The name of category which users wants to get the details
	 * @return Return the complete details of category with given category Id<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "id": 1, <br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "name": "Log Analytics",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "description": "Search Category for Log Analytics",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "owner": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "createdOn": "2014-07-22T14:48:53.048Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "providerName": "Log Analytics",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "providerVersion": "1.0",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "providerAssetRoot": "assetRoot",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "defaultFolder": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 2,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/2"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "parameters": [<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "CATEGORY_PARAM_VIEW_TASKFLOW",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value":
	 *         "/WEB-INF/core/loganalytics/obssearch/plugins/dashboard-flow-definition.xml#dashboard-flow-definition",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "STRING"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; ],<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "href": "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/category/1"<br>
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
	 *         <td>List category details successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>404</td>
	 *         <td>Not Found</td>
	 *         <td>Category object by name: &lt;name&gt; does not exist</td>
	 *         </tr>
	 *         <tr>
	 *         <td>400</td>
	 *         <td>Bad Request</td>
	 *         <td>please give category name</td>
	 *         </tr>
	 *         </table>
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategoryByName(@QueryParam("name") String name)
	{
		String message = null;
		int statusCode = 200;
		if (name == null) {
			return Response.status(400).entity("please give category name").build();
		}
		else if (name.equals("")) {
			return Response.status(400).entity("please give category name").build();
		}
		CategoryManager catMan = CategoryManager.getInstance();
		try {

			Category category = catMan.getCategory(name);
			JSONObject jsonObj = EntityJsonUtil.getFullCategoryJsonObj(uri.getBaseUri(), category);
			message = jsonObj.toString();
		}
		catch (EMAnalyticsFwkException e) {

			_logger.error("An error occurredh while retrieving all category by name, statusCode:" + e.getStatusCode() + " ,err:"
					+ e.getMessage(), e);

		}
		catch (JSONException e) {
			_logger.error(
					"An error occurred while retrieving all category by name, statusCode:" + "400" + " ,err:" + e.getMessage(), e);
		}
		return Response.status(statusCode).entity(message).build();
	}

	/*
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveCategory(JSONObject inputJsonObj)
	{
		String sMsg = "";
		int statusCode = 201;
		CategoryManager catMan = CategoryManager.getInstance();
		try {
			Category category = createCategoryObjectForAdd(inputJsonObj);
			category = catMan.saveCategory(category);
			sMsg = JSONUtil.ObjectToJSONString(category);

		}
		catch (EMAnalyticsFwkException e) {
			sMsg = e.getMessage();
			statusCode = e.getStatusCode();

		}
		catch (EMAnalyticsWSException e) {
			sMsg = e.getMessage();
			statusCode = e.getStatusCode();
		}
		return Response.status(statusCode).entity(sMsg).build();
	}

	private Category createCategoryObjectForAdd(JSONObject inputJsonObj) throws EMAnalyticsWSException
	{
		Category category = CategoryManager.getInstance().createNewCategory();
		try {
			String name=inputJsonObj.getString("name");
			if(name == null)
				throw new EMAnalyticsWSException("The name key for category can not be null in the input JSON Object",EMAnalyticsWSException.JSON_CATEGORY_NAME_MISSING);
			if(name !=null && name.equals(""))
				throw new EMAnalyticsWSException("The name key for category can not be empty in the input JSON Object",EMAnalyticsWSException.JSON_CATEGORY_NAME_MISSING);
			category.setName(name);

		}
		catch (JSONException je) {
			throw new EMAnalyticsWSException("The name key for category is missing in the input JSON Object",
					EMAnalyticsWSException.JSON_CATEGORY_NAME_MISSING, je);
		}

		category.setDescription(inputJsonObj.optString("description", category.getDescription()));
		if (inputJsonObj.has("defaultFolderId")) {
			category.setDefaultFolderId(inputJsonObj.optInt("defaultFolderId"));
		}
		List<Parameter> categoryParamList = new ArrayList<Parameter>();
		if (inputJsonObj.has("parameters")) {
			JSONArray jsonArr = inputJsonObj.optJSONArray("parameters");

			// FIltering values with Map .. not exactly required .. duplicate params if any will be discarded at persistence layer !!
			for (int index = 0; index < jsonArr.length(); index++) {
				Parameter categoryParam = new Parameter();
				JSONObject jsonParam = jsonArr.optJSONObject(index);

				try {
					String name=jsonParam.getString("name");
					if(name == null)
						throw new EMAnalyticsWSException("The name key for category param can not be null in the input JSON Object",EMAnalyticsWSException.JSON_CATEGORY_PARAM_NAME_MISSING);
					if(name !=null && name.equals(""))
						throw new EMAnalyticsWSException("The name key for category param can not be empty in the input JSON Object",EMAnalyticsWSException.JSON_CATEGORY_PARAM_NAME_MISSING);

					categoryParam.setName("name");
				}
				catch (JSONException je) {
					throw new EMAnalyticsWSException("The name key for category param is missing in the input JSON Object",
							EMAnalyticsWSException.JSON_CATEGORY_PARAM_NAME_MISSING, je);
				}

				categoryParam.setValue(jsonParam.optString("value", categoryParam.getValue()));

				categoryParamList.add(categoryParam);

			}

		}
		category.setParameters(categoryParamList);

		return category;
	}

	private Category createCategoryObjectForEdit(JSONObject inputJsonObj, Category category) throws EMAnalyticsWSException
	{

		category.setName(inputJsonObj.optString("name", category.getName()));
		category.setDescription(inputJsonObj.optString("description", category.getDescription()));
		if (inputJsonObj.has("defaultFolderId")) {
			category.setDefaultFolderId(inputJsonObj.optInt("defaultFolderId"));
		}
		List<Parameter> categoryParamList = new ArrayList<Parameter>();
		if (inputJsonObj.has("parameters")) {
			JSONArray jsonArr = inputJsonObj.optJSONArray("parameters");

			// FIltering values with Map .. not exactly required .. duplicate params if any will be discarded at persistence layer !!
			for (int index = 0; index < jsonArr.length(); index++) {
				Parameter categoryParam = new Parameter();
				JSONObject jsonParam = jsonArr.optJSONObject(index);
				try {
					categoryParam.setName(jsonParam.getString("name"));
				}
				catch (JSONException e) {
					throw new EMAnalyticsWSException("the name key for category param is missing in input JSON Object",
							EMAnalyticsWSException.JSON_CATEGORY_PARAM_NAME_MISSING, e);
				}
				categoryParam.setValue(jsonParam.optString("value", categoryParam.getValue()));
				categoryParamList.add(categoryParam);
			}

		}
		category.setParameters(categoryParamList);
		return category;

	}
	 */

	/**
	 * List all the searches with given category Id <br>
	 * <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/category/&lt;categoryid&gt;/searches
	 * </font><br>
	 * The string "savedsearch/v1/category/&lt;categoryid&gt;/searches" in the URL signifies read operation on search with given
	 * category Id.<br>
	 * <br>
	 * Each search object include following elements :<br>
	 * id,name,description,category,folder,owner,guid,createdOn,lastModifiedOn,lastModifiedBy,
	 * lastAccessDate,systemSearch,parameters,queryStr,locked,uiHidden,isWidget
	 * 
	 * @since 0.1
	 * @param uri
	 * @param catId
	 *            The category Id which user wants to get the details
	 * @return Lists all the searches <br>
	 *         If category Id is given as the parameter, it will list all the searches present in the category whose id is the
	 *         given category Id<br>
	 * <br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">[<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 10003,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "sample for creation1",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "guid": "[B@7b9a67de",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "description": "sample for creation",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "owner": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "category": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 1,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/category/1"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "folder": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 2,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/2"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "createdOn": "2014-07-04T02:20:07.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastModifiedOn": "2014-07-04T02:20:07.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastAccessDate": "2014-07-04T02:20:07.000Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "systemSearch": "false",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastModifiedBy": "ORACLE",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "locked": "false",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "uiHidden": "false",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "isWidget": true,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "queryStr": "*",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "parameters":[<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name":"time",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type":"STRING",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value":"ALL"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name":"additionalInfo",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type":"CLOB",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value":"this is a demo"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;}<br>
	 *         ]</font><br>
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
	 *         <td>List searches successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>404</td>
	 *         <td>Not Found</td>
	 *         <td>could be the following errors:<br>
	 *         Category object by ID: &lt;category Id&gt; does not exist<br>
	 *         </tr>
	 *         <tr>
	 *         <td>400</td>
	 *         <td>Bad Request</td>
	 *         <td>could be the following errors:<br>
	 *         1.please give the value for categoryId<br>
	 *         2.Id/count should be a positive number and not an alphanumeric<br>
	 *         </tr>
	 *         <tr>
	 *         <td>500</td>
	 *         <td>Internal Server Error</td>
	 *         <td>&nbsp;</td>
	 *         </tr>
	 *         </table>
	 */

	@GET
	@Path("{id}/searches")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchesByCategory(@PathParam("id") String categoryId, @HeaderParam("SSF_OOB") String oobSearch)
	{
		SearchManager searchMan = SearchManager.getInstance();
		CategoryManager catMan = CategoryManager.getInstance();
		Search search;
		int statusCode = 200;
		String message = "";
		JSONArray jsonArray = new JSONArray();
		List<Search> searchList = new ArrayList<Search>();
		long tmpCatId = -1;

		if (categoryId == null) {
			return Response.status(400).entity("Please specify vaild category Id").build();
		}
		else if ("".equals(categoryId.trim())) {
			return Response.status(400).entity("Category Id is empty Please specify valid Category Id").build();
		}
		else {
			try {

				if (!"".equals(categoryId.trim())) {
					tmpCatId = Long.parseLong(categoryId);
				}
			}
			catch (NumberFormatException e) {
				return Response.status(400).entity("Id/count should be a positive number and not an alphanumeric").build();
			}
		}

		if (tmpCatId <= 0) {
			return Response.status(400).entity("Id/count should be a positive number and not an alphanumeric").build();
		}

		try {

			boolean bResult = false;
			if (oobSearch == null) {
				bResult = false;
			}
			if (oobSearch != null && oobSearch.equalsIgnoreCase("true")) {
				bResult = true;
			}

			// just for checking whether category with given Id exist or not
			catMan.getCategory(tmpCatId);
			if (bResult) {
				searchList = searchMan.getSystemSearchListByCategoryId(tmpCatId);
			}
			else {
				searchList = searchMan.getSearchListByCategoryId(tmpCatId);
			}
		}
		catch (EMAnalyticsFwkException e) {

			return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
		}

		try {
			for (int i = 0; i < searchList.size(); i++) {
				search = searchList.get(i);
				try {
					JSONObject jsonObj = EntityJsonUtil.getFullSearchJsonObj(uri.getBaseUri(), search, null, false);
					jsonArray.put(jsonObj);
				}
				catch (JSONException e) {

					return Response.status(500).entity(e.getMessage()).build();
				}
			}
			message = jsonArray.toString();
		}
		catch (EMAnalyticsFwkException e) {

			return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
		}
		return Response.status(statusCode).entity(message).build();

	}

}
