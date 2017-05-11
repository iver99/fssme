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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.JSONUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.countEntity.ZDTCountEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.ZDTComparatorStatusRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.ZDTSyncStatusRowEntity;
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
	public Response getAllTableData(@QueryParam("comparisonType") String type)
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /v1/tablerows?comparisonType=");
		JSONObject obj = new JSONObject();
		EntityManager em = null;
		if (type == null) {
			type = "full";
		}
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			String lastComparisonDate = DataManager.getInstance().getLatestComparisonDateForCompare(em);
			JSONArray tableData = getCategoryTableData(em,type, lastComparisonDate);
			obj.put(TABLE_CATEGORY, tableData);
			tableData = getCategoryParamTableData(em,type, lastComparisonDate);
			obj.put(TABLE_CATEGORY_PARAMS, tableData);
			tableData = getFolderTableData(em,type, lastComparisonDate);
			obj.put(TABLE_FOLDERS, tableData);
			tableData = getFolderTableData(em,type, lastComparisonDate);
			obj.put(TABLE_FOLDERS, tableData);
			tableData = getSearchTableData(em,type, lastComparisonDate);
			obj.put(TABLE_SEARCH, tableData);
			tableData = getSearchParamsTableData(em,type, lastComparisonDate);
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
/*
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
			return Response.ok(response).build();
		}
		catch (IOException e) {
			logger.error(e.getLocalizedMessage(), e);
			return Response.status(400).entity(e.getLocalizedMessage()).build();
		}
	}
	*/
/*	@GET
	@Path("sync")
	public Response sync(@QueryParam("syncType") String type, @QueryParam("syncDate") String syncDate)
	{
		LogUtil.getInteractionLogger().info("Service calling to (PUT) /v1/zdt/sync?syncType=xx&syncDate=xx");
		ZDTTableRowEntity data = null;
		try {
			data = JSONUtil.fromJson(new ObjectMapper(), dataToSync.toString(), ZDTTableRowEntity.class);
			String response = new ZDTSynchronizer().sync(data);
			if (response.contains("Errors:")) {
				return Response.status(500).entity(response).build();
			}
			return Response.ok(response).build();
		}
		catch (IOException e) {
			logger.error(e.getLocalizedMessage(), e);
			return Response.status(400).entity(e.getLocalizedMessage()).build();
		}
	}
*/
	@GET
	@Path("sync")
	public Response sync(@QueryParam("syncType") String type, @QueryParam("syncDate") String syncDate)
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /v1/zdt/sync?syncType=xx&syncDate=xx");
		ZDTTableRowEntity data = null;
		String lastCompareDate = null;
		if (type == null) {
			type = "full";
		}
		EntityManager em = null;
		String lastComparisonDateForSync = null;
		List<Map<String, Object>> comparedDataToSync = null;
		try {			
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			lastComparisonDateForSync = DataManager.getInstance().getLastComparisonDateForSync(em);
			comparedDataToSync = DataManager.getInstance().getComparedDataToSync(em, lastComparisonDateForSync);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return Response.status(400).entity(e.getLocalizedMessage()).build();				
		} finally {
			if (em != null) {
				em.close();
			}
		}
		try {	
			if (comparedDataToSync != null && !comparedDataToSync.isEmpty()) {
				for (Map<String, Object> dataMap : comparedDataToSync) {
					Object compareResult = dataMap.get("COMPARISON_RESULT");
					Object compareDate = dataMap.get("COMPARISON_DATE");
					data = JSONUtil.fromJson(new ObjectMapper(), compareResult.toString(), ZDTTableRowEntity.class);
					String response = new ZDTSynchronizer().sync(data);
					lastCompareDate = (String) compareDate;
					if (response.contains("Errors:")) {
						saveToSyncTable(syncDate, type, "FAILED",lastCompareDate);
						return Response.status(500).entity(response).build();
					}
				}
				int flag = saveToSyncTable(syncDate, type, "SUCCESSFUL",lastCompareDate);
				if (flag < 0) {
					return Response.status(500).entity("Fail to save sync status data").build();
				}
			} else {
				return Response.ok("Nothing to sync as no compared data").build();
			}
						
			return Response.ok("Sync is successful!").build();
		}
		catch (IOException e) {
			logger.error(e.getLocalizedMessage(), e);
			return Response.status(400).entity("Errors: " + e.getLocalizedMessage()).build();
		} 
	}
	
	private int saveToSyncTable(String syncDateStr, String type, String syncResult, String lastComparisonDate) {
		Date syncDate = null;
		try {  
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		    syncDate = sdf.parse(syncDateStr);  
		} catch (ParseException e) {  
		    logger.error(e);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(syncDate);
		cal.add(Calendar.HOUR_OF_DAY, 6);
		Date nextScheduleDate = cal.getTime();
		String nextScheduleDateStr = getTimeString(nextScheduleDate);
		// TO DO... currently each sync operation will sync all of the existing compared result, 
		// so for the existing compared result the divergence percentage will always be 0.0
		// In the future, it is better to add logic to check the divergence percentage for the rows which are not compared;
		double divergencePercentage = 0.0; 
		return DataManager.getInstance().saveToSyncTable(syncDateStr, nextScheduleDateStr, type, syncResult, divergencePercentage, lastComparisonDate);
		
	}
	
	private String getTimeString(Date date)
	{
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
	
	
	@GET
	@Path("sync/status")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSyncStatus() {

		LogUtil.getInteractionLogger().info("Service calling to (GET) /v1/zdt/sync/status");
		EntityManager em = null;
		String message = null;
		int statusCode = 200;
		String sync_date = "";
		String sync_type = "";
		String next_schedule_date = "";
		double percentage = 0.0;
		em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
		List<Map<String, Object>> result = DataManager.getInstance().getSyncStatus(em);
		if (result != null && result.size() == 1) {
				Map<String, Object> resultMap = result.get(0);
				sync_date = resultMap.get("SYNC_DATE").toString();
				sync_type = resultMap.get("SYNC_DATE").toString();
				next_schedule_date =  resultMap.get("NEXT_SCHEDULE_SYNC_DATE").toString();
				percentage = Double.parseDouble(resultMap.get("DIVERGENCE_PERCENTAGE").toString());
		}
		ZDTSyncStatusRowEntity syncStatus = new ZDTSyncStatusRowEntity(sync_date,sync_type, next_schedule_date, percentage);
		try {
			message = JSONUtil.objectToJSONString(syncStatus);
			return Response.status(statusCode).entity(message).build();
		} catch (EMAnalyticsFwkException e) {
			message = "Errors:" + e.getLocalizedMessage();
			statusCode = 500;
			logger.error("Errors while transfer sync status object to json string {}",e.getLocalizedMessage());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return Response.status(statusCode).entity(message).build();
	}
	
	@GET
	@Path("compare/status")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getComparisonStatus() {
		LogUtil.getInteractionLogger().info("Service calling to (GET) /v1/zdt/compare/status");
		EntityManager em = null;
		String message = null;
		int statusCode = 200;
		String comparison_date = "";
		String comparison_type = "";
		//String comparison_result = null;
		String next_schedule_date = "";
		double percentage = 0.0;
		em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
		List<Map<String, Object>> result = DataManager.getInstance().getComparatorStatus(em);
		if (result != null && result.size() == 1) {
				Map<String, Object> resultMap = result.get(0);
				comparison_date = resultMap.get("COMPARISON_DATE").toString();
				comparison_type = resultMap.get("COMPARISON_TYPE").toString();
				next_schedule_date =  resultMap.get("NEXT_SCHEDULE_COMPARISON_DATE").toString();
				//	comparison_result = resultMap.get("").toString();
				percentage = Double.parseDouble(resultMap.get("DIVERGENCE_PERCENTAGE").toString());
		}
		ZDTComparatorStatusRowEntity comparatorStatus = new ZDTComparatorStatusRowEntity(comparison_date,comparison_type, next_schedule_date, percentage);
		try {
			message = JSONUtil.objectToJSONString(comparatorStatus);
			return Response.status(statusCode).entity(message).build();
		} catch (EMAnalyticsFwkException e) {
			message = "Errors:" + e.getLocalizedMessage();
			statusCode = 500;
			logger.error("Errors while transfer comparator status object to json string {}",e.getLocalizedMessage());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return Response.status(statusCode).entity(message).build();
	}

	@PUT
	@Path("compare/result")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveComparatorData(JSONObject jsonObj) {
		LogUtil.getInteractionLogger().info("Service calling to (PUT) /v1/zdt/compare/result");
		EntityManager em = null;
		String message = null;
		int statusCode = 200;
		String comparisonDate = null;
		String nextScheduleDate = null;
		String comparisonType = null;
		String comparisonResult = null;
		double divergencePercentage = 0;
		if (jsonObj != null) {
			try {
				if (jsonObj.getString("lastComparisonDateTime") == null) {
					message = "comparison date time can not be null";
					statusCode = 500;
				} else {
					try {
						comparisonDate = jsonObj.getString("lastComparisonDateTime");
						comparisonType = jsonObj.getString("comparisonType");
						comparisonResult = jsonObj.getString("comparisonResult");
						divergencePercentage = jsonObj.getDouble("divergencePercentage");
						nextScheduleDate = jsonObj.getString("nextScheduledComparisonDateTime");
						em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
						int result = DataManager.getInstance().saveToComparatorTable(em, comparisonDate,nextScheduleDate,
								comparisonType, comparisonResult, divergencePercentage);
						if (result < 0) {
							message = "Error: error occurs while saving data to zdt comparator table";
							statusCode = 500;
						} else {
							message = "succeed to save data to zdt comparator table";
						}		
					} catch (Exception e) {
						statusCode = 500;
						logger.error("could not save data to comparator table, "+e.getLocalizedMessage());
						return Response.status(statusCode).entity("could not save data to comparator table").build();						
					} finally {
						if (em != null) {
							em.close();
						}
					}
				}
			} catch (JSONException e) {
				statusCode = 500;
				logger.error("could not save data to comparator table, "+e.getLocalizedMessage());
				return Response.status(statusCode).entity("could not save data to comparator table").build();	
			}
		}
	
		return Response.status(statusCode).entity(message).build();
	}

	private JSONArray getCategoryParamTableData(EntityManager em, String type, String date)
	{
		List<Map<String, Object>> list = DataManager.getInstance().getCategoryParamTableData(em,type, date);
		return getJSONArrayForListOfObjects(TABLE_CATEGORY_PARAMS, list);
	}

	private JSONArray getCategoryTableData(EntityManager em, String type, String date)
	{
		List<Map<String, Object>> list = DataManager.getInstance().getCategoryTableData(em,type, date);
		return getJSONArrayForListOfObjects(TABLE_CATEGORY, list);
	}

	private JSONArray getFolderTableData(EntityManager em, String type, String date)
	{
		List<Map<String, Object>> list = DataManager.getInstance().getFolderTableData(em,type, date);
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

	private JSONArray getSearchParamsTableData(EntityManager em, String type, String date)
	{
		List<Map<String, Object>> list = DataManager.getInstance().getSearchParamTableData(em,type, date);
		return getJSONArrayForListOfObjects(TABLE_SEARCH_PARAMS, list);
	}

	private JSONArray getSearchTableData(EntityManager em, String type, String date)
	{
		List<Map<String, Object>> list = DataManager.getInstance().getSearchTableData(em,type, date);
		return getJSONArrayForListOfObjects(TABLE_SEARCH, list);
	}

}
