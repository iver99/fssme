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

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
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
	private static final String TABLE_SEARCH = "EMS_ANALYTICS_SEARCH";
	private static final String TABLE_SEARCH_PARAMS = "EMS_ANALYTICS_SEARCH_PARAMS";

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
		EntityManager em = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			JSONArray tableData = getCategoryTableData(em);
			obj.put(TABLE_CATEGORY, tableData);
			tableData = getCategoryParamTableData(em);
			obj.put(TABLE_CATEGORY_PARAMS, tableData);
			tableData = getFolderTableData(em);
			obj.put(TABLE_FOLDERS, tableData);
			tableData = getFolderTableData(em);
			obj.put(TABLE_FOLDERS, tableData);
			tableData = getSearchTableData(em);
			obj.put(TABLE_SEARCH, tableData);
			tableData = getSearchParamsTableData(em);
			obj.put(TABLE_SEARCH_PARAMS, tableData);
		}
		catch (JSONException e) {
			logger.error(e.getLocalizedMessage(), e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Errors:" + e.getLocalizedMessage()).build();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return Response.status(Status.OK).entity(obj).build();
	}

	@GET
	@Path("counts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEntitiesCount()
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /v1/zdt/counts");
		EntityManager em = null;
		String message = null;
		int statusCode = 200;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			long categoryCount = DataManager.getInstance().getAllCategoryCount(em);
			long folderCount = DataManager.getInstance().getAllFolderCount(em);
			long searcheCount = DataManager.getInstance().getAllSearchCount(em);
			logger.debug("ZDT counters: category count - {}, folder count - {}, search count - {}", categoryCount, folderCount,
				searcheCount);
			ZDTCountEntity zdte = new ZDTCountEntity(categoryCount, folderCount, searcheCount);
			
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
		} 
		catch (Exception e) {
			logger.error("errors while getting counts for tables -",e.getLocalizedMessage());
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
		return Response.status(statusCode).entity(message).build();
	}

	@PUT
	@Path("sync")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response sync(JSONObject dataToSync)
	{
		LogUtil.getInteractionLogger().info("Service calling to (PUT) /v1/zdt/sync");
		ZDTTableRowEntity data = null;
		try {
			data = JSONUtil.fromJson(new ObjectMapper(), dataToSync.toString(), ZDTTableRowEntity.class);
			String response = new ZDTSynchronizer().sync(data);
			if (response.contains("Errors:")) {
				return Response.status(500).entity(response).build();
			}
			return Response.status(Status.NO_CONTENT).entity(response).build();
		}
		catch (IOException e) {
			logger.error(e.getLocalizedMessage(), e);
			return Response.status(400).entity(e.getLocalizedMessage()).build();
		}
	}

	private JSONArray getCategoryParamTableData(EntityManager em)
	{
		List<Map<String, Object>> list = DataManager.getInstance().getCategoryParamTableData(em);
		return getJSONArrayForListOfObjects(TABLE_CATEGORY_PARAMS, list);
	}

	private JSONArray getCategoryTableData(EntityManager em)
	{
		List<Map<String, Object>> list = DataManager.getInstance().getCategoryTableData(em);
		return getJSONArrayForListOfObjects(TABLE_CATEGORY, list);
	}

	private JSONArray getFolderTableData(EntityManager em)
	{
		List<Map<String, Object>> list = DataManager.getInstance().getFolderTableData(em);
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

	private JSONArray getSearchParamsTableData(EntityManager em)
	{
		List<Map<String, Object>> list = DataManager.getInstance().getSearchParamTableData(em);
		return getJSONArrayForListOfObjects(TABLE_SEARCH_PARAMS, list);
	}

	private JSONArray getSearchTableData(EntityManager em)
	{
		List<Map<String, Object>> list = DataManager.getInstance().getSearchTableData(em);
		return getJSONArrayForListOfObjects(TABLE_SEARCH, list);
	}

}
