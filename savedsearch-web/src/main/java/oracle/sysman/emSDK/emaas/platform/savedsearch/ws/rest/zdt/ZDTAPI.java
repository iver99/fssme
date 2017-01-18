/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.JSONUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.countEntity.ZDTCountEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.ZDTTableRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.DataManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author pingwu
 */
@Path("zdt")
public class ZDTAPI
{

	private static final Logger logger = LogManager.getLogger(ZDTAPI.class);

	private static final String TABLE_CATEGORY = "EMS_ANALYTICS_CATEGORY";
	private static final String TABLE_CATEGORY_PARAMS = "EMS_ANALYTICS_CATEGORY_PARAMS";
	private static final String TABLE_FOLDERS = "EMS_ANALYTICS_FOLDERS";
	private static final String TABLE_LAST_ACCESS = "EMS_ANALYTICS_LAST_ACCESS";
	private static final String TABLE_SEARCH = "EMS_ANALYTICS_SEARCH";
	private static final String TABLE_SEARCH_PARAMS = "EMS_ANALYTICS_SEARCH_PARAMS";
	private static final String TABLE_SCHEMA_VERSION = "EMS_ANALYTICS_SCHEMA_VER_SSF";

	public ZDTAPI()
	{
		super();
	}

	@GET
	@Path("tablerows")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTableData()
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /v1/tablerows");

		JSONObject obj = new JSONObject();
		try {
			JSONArray tableData = getCategoryTableData();
			obj.put(TABLE_CATEGORY, tableData);
			tableData = getCategoryParamTableData();
			obj.put(TABLE_CATEGORY_PARAMS, tableData);
			tableData = getFolderTableData();
			obj.put(TABLE_FOLDERS, tableData);
			tableData = getFolderTableData();
			obj.put(TABLE_FOLDERS, tableData);
			tableData = getSearchTableData();
			obj.put(TABLE_SEARCH, tableData);
			tableData = getSearchParamsTableData();
			obj.put(TABLE_SEARCH_PARAMS, tableData);
			tableData = getSchemaVersionTableData();
			obj.put(TABLE_SCHEMA_VERSION, tableData);
		}
		catch (JSONException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return Response.status(Status.OK).entity(obj).build();
	}

	@GET
	@Path("counts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEntitiesCount()
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /v1/zdt/counts");
		long categoryCount = DataManager.getInstance().getAllCategoryCount();
		long folderCount = DataManager.getInstance().getAllFolderCount();
		long searcheCount = DataManager.getInstance().getAllSearchCount();
		logger.debug("ZDT counters: category count - {}, folder count - {}, search count - {}", categoryCount, folderCount,
				searcheCount);
		ZDTCountEntity zdte = new ZDTCountEntity(categoryCount, folderCount, searcheCount);
		String message = null;
		int statusCode = 200;
		try {
			message = JSONUtil.objectToJSONString(zdte);
		}
		catch (EMAnalyticsFwkException e) {
			message = e.getLocalizedMessage();
			statusCode = e.getErrorCode();
			logger.error(
					(TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
							+ "An error occurredh while retrieving all table counts, statusCode:" + e.getStatusCode()
							+ " ,error:" + e.getMessage(), e);
		}
		return Response.status(statusCode).entity(message).build();
	}

	@PUT
	@Path("sync")
	public Response sync(JSONObject dataToSync)
	{
		LogUtil.getInteractionLogger().info("Service calling to (PUT) /v1/zdt/sync");
		ZDTTableRowEntity data = null;
		try {
			data = JSONUtil.fromJson(new ObjectMapper(), dataToSync.toString(), ZDTTableRowEntity.class);
			new ZDTSynchronizer().sync(data);
			return Response.status(Status.NO_CONTENT).build();
		}
		catch (IOException e) {
			logger.error(e.getLocalizedMessage(), e);
			return Response.status(400).entity(e.getLocalizedMessage()).build();
		}
	}

	private JSONArray getCategoryParamTableData()
	{
		List<Map<String, Object>> list = DataManager.getInstance().getCategoryParamTableData();
		return getJSONArrayForListOfObjects(TABLE_CATEGORY_PARAMS, list);
	}

	private JSONArray getCategoryTableData()
	{
		List<Map<String, Object>> list = DataManager.getInstance().getCategoryTableData();
		return getJSONArrayForListOfObjects(TABLE_CATEGORY, list);
	}

	private JSONArray getFolderTableData()
	{
		List<Map<String, Object>> list = DataManager.getInstance().getFolderTableData();
		return getJSONArrayForListOfObjects(TABLE_FOLDERS, list);
	}

	/**
	 * @param list
	 * @return
	 */
	private JSONArray getJSONArrayForListOfObjects(String dataName, List<Map<String, Object>> list)
	{
		if (list == null) {
			logger.warn("Trying to get a JSON object for {} from a null object/list. Returning null JSON object", dataName);
			return null;
		}
		JSONArray array = new JSONArray();
		for (Map<String, Object> row : list) {
			array.put(row);
		}
		logger.debug("Retrieved table data for {} is \"{}\"", dataName, array.toString());
		return array;
	}

	private JSONArray getSchemaVersionTableData()
	{
		List<Map<String, Object>> list = DataManager.getInstance().getSchemaVerTableData();
		return getJSONArrayForListOfObjects(TABLE_SCHEMA_VERSION, list);
	}

	private JSONArray getSearchParamsTableData()
	{
		List<Map<String, Object>> list = DataManager.getInstance().getSearchParamTableData();
		return getJSONArrayForListOfObjects(TABLE_SEARCH_PARAMS, list);
	}

	private JSONArray getSearchTableData()
	{
		List<Map<String, Object>> list = DataManager.getInstance().getSearchTableData();
		return getJSONArrayForListOfObjects(TABLE_SEARCH, list);
	}

}
