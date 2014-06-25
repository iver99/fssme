package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.category;

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

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JSONUtil;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("category")
public class CategoryAPI
{

	@DELETE
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
	@Path("/categories")
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
			message = JSONUtil.ObjectToJSONString(category);
		}
		catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
		}
		catch (EMAnalyticsWSException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
		}
		return Response.status(statusCode).entity(message).build();
	}

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
			message = JSONUtil.ObjectToJSONString(category);
		}
		catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
		}
		catch (EMAnalyticsWSException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
		}
		return Response.status(statusCode).entity(message).build();
	}

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

}
