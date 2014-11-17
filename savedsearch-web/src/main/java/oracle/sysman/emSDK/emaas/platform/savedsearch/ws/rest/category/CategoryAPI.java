package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.category;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkJsonException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ExportSearchSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch.ImportSearchSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.XMLUtil;

import org.apache.log4j.Logger;
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
	private static final Logger _logger = Logger.getLogger(CategoryAPI.class);

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
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "defaultFolder": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 2,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/2"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "parameters": [<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "CATEGORY_PARAM_VIEW_TASKFLOW",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value":
	 *         "/WEB-INF/core/loganalytics/obssearch/plugins/dashboard-flow-definition.xml#dashboard-flow-definition",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "STRING"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }<br>
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
	 *         <td>List catergory details successfully</td>
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
		catch (EMAnalyticsFwkJsonException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
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
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "defaultFolder": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "id": 2,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "href":
	 *         "http://slc04pxi.us.oracle.com:7001/savedsearch/v1/folder/2"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "parameters": [<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "CATEGORY_PARAM_VIEW_TASKFLOW",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "value":
	 *         "/WEB-INF/core/loganalytics/obssearch/plugins/dashboard-flow-definition.xml#dashboard-flow-definition",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "type": "STRING"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }<br>
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
			message = e.getMessage();
			statusCode = e.getStatusCode();
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			statusCode = 400;
			message = e.getMessage();
		}
		catch (EMAnalyticsFwkJsonException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
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
	
	@GET
	@Path("{id: [0-9]*}/searches")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchesByCategory(@PathParam("id") int categoryId)
	{
		
		int statusCode = 200;
		Response res = null;
		String msg="";
		String data ="";
		if (categoryId <=0) {
			return res = Response.status(Status.BAD_REQUEST).entity("Please specify valid category id").build();
		}
		res = Response.ok().build();
		try
		{
			SearchManager mgr = SearchManager.getInstance();
			List<Search> searchList = mgr.getSearchListByCategoryId(categoryId);
			ExportSearchSet exList = new ExportSearchSet();
			Iterator<Search> iterator = searchList.iterator();
			ArrayList<SearchImpl> tmp = new ArrayList<SearchImpl>();	
			while (iterator.hasNext()) {
				 Search tmpSearch=iterator.next();
				 tmp.add((SearchImpl)tmpSearch);				 
			}		
			exList.setSearchSet(tmp);
			JAXBContext context = JAXBContext.newInstance(ExportSearchSet.class);
			InputStream stream = ImportSearchSet.class.getClassLoader().getResourceAsStream(resourcePath);
			data= JAXBUtil.marshal(context, stream, exList);
			data=processData(data);
			res = Response.status(statusCode).entity(data).build();
		}
		catch (Exception e) {
			_logger.error("Failed to retrive searches ", e);
			msg = "An internal error has occurred  while retriving  searches " +e.toString();			
			res = Response.status(Status.INTERNAL_SERVER_ERROR).entity(msg).build();
			e.printStackTrace();
			return res;
		}
		return res;
	}

	private String processData(String data) throws Exception
	{
		String tmpData = "";		
		tmpData= XMLUtil.changeTagName(data,getRenameElementList());		
	 	tmpData = XMLUtil.removeElement(tmpData,"Search" ,getRemovedElements());		
		return tmpData;
	}
	
	
	private Map <String,String> getRenameElementList()
	{
		Map <String,String> map = new HashMap<String,String>();		
		map.put("search" ,"Search");
		map.put("id" ,"Id");
		map.put("folderId" ,"FolderId");
		map.put("categoryId" ,"CategoryId");
		map.put("description" ,"Description");
		map.put("locked" ,"Locked");
		map.put("uiHidden" ,"UiHidden");
		map.put("name" ,"Name");
		map.put("type" ,"Type");
		map.put("value" ,"Value");
		map.put("metadata" ,"Metadata");
		map.put("queryStr" ,"QueryStr");	
		return map;
	}
	
	private String[] getRemovedElements()
	{
		String elementName [] = {"guid" ,"owner", "createdOn",  "lastModifiedOn", "lastAccessDate", "lastModifiedBy", "systemSearch"};
		return elementName;
	}
	

	
}
