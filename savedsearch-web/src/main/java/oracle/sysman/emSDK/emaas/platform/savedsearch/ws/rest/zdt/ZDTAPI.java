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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.countEntity.ZDTCountEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchFolderRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchParamRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchRowEntity;
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

//	private static final String TABLE_CATEGORY = "EMS_ANALYTICS_CATEGORY";
//	private static final String TABLE_CATEGORY_PARAMS = "EMS_ANALYTICS_CATEGORY_PARAMS";
	private static final String TABLE_FOLDERS = "EMS_ANALYTICS_FOLDERS";
	private static final String TABLE_SEARCH = "EMS_ANALYTICS_SEARCH";
	private static final String TABLE_SEARCH_PARAMS = "EMS_ANALYTICS_SEARCH_PARAMS";

	/**
	 *  This method is return all the tenants that have widgets in EMS_ANALYTICS_SEARCH table
	 *  NOTE:
	 *  #1. check compare table to see if comparison is done before,
	 *  #2. return all tenant(id) from table, no matter record is marked as deleted or not.
	 * @return
	 */
	@GET
	@Path("tenants")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTenants() {
		LogUtil.getInteractionLogger().info("Service calling to (GET) /v1/zdt/tenants");
		EntityManager em = null;
		JSONObject obj = new JSONObject();
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			String lastComparisonDate = DataManager.getInstance().getLatestComparisonDateForCompare(em);
			List<Object> tenants = DataManager.getInstance().getAllTenants(em);
			JSONArray array = new JSONArray();
			if (tenants != null) {
				for (Object tenant:tenants) {
					array.put(tenant.toString());
				}
			}
			boolean isCompared = true;
			if (lastComparisonDate == null) {
				isCompared =  false;
				//reset lastComparisonDate to empty str because if it is a null, it will not be put into json response
				lastComparisonDate = "";
			}
			obj.put("isCompared", isCompared);
			obj.put("tenants", array);
			obj.put("lastComparedDate", lastComparisonDate);
			logger.info("isCompared is {}, tenants is {}, lastComparedDate is {}", isCompared, tenants, lastComparisonDate);
			return Response.status(Status.OK).entity(obj).build();
		} catch (Exception e) {
			logger.error("errors in getting all tenants:"+e.getLocalizedMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Error occurred when retrieve all tenants\"}").build();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**
	 *  this method return all records in each table for each tenant
	 * @param type
	 * @param maxComparedDate
	 * @param tenant tenant id, first compare will retrieve data for specific tenant, if tenant id is null means this is not the first
	 *               time comparision, comparision work has finished before.(Incremental)
	 * @return
	 */
	@GET
	@Path("tablerows")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTableData(@QueryParam("comparisonType") String type, @QueryParam("maxComparedDate") String maxComparedDate,
			@QueryParam("tenant") String tenant)
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /v1/tablerows?comparisonType={}",type);
		JSONObject obj = new JSONObject();
		EntityManager em = null;
		if (type == null) {
			type = "incremental";
		}
		if (maxComparedDate == null) {
			Date date = getCurrentUTCTime();
			maxComparedDate = getTimeString(date);
		}
		logger.info("ComparisonType for get tableRows is {} ",type);
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			String lastComparisonDate = DataManager.getInstance().getLatestComparisonDateForCompare(em);
			JSONArray tableData = getFolderTableData(em,type, lastComparisonDate, maxComparedDate, tenant);
			obj.put(TABLE_FOLDERS, tableData);
			tableData = getSearchTableData(em,type, lastComparisonDate, maxComparedDate, tenant);
			obj.put(TABLE_SEARCH, tableData);
			tableData = getSearchParamsTableData(em,type, lastComparisonDate, maxComparedDate, tenant);
			obj.put(TABLE_SEARCH_PARAMS, tableData);
		}catch (JSONException e) {
			logger.error(e.getLocalizedMessage(), e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"#1.Error occurred when retrieve all tablerows\"}").build();
		}catch (Exception e1) {
			logger.error(e1.getLocalizedMessage(), e1);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"#2.Error occurred when retrieve all tablerows\"}").build();
		}finally {
			if (em != null) {
				em.close();
			}
		}
		return Response.status(Status.OK).entity(obj).build();
	}

	/**
	 * return the table counts of each table in this cloud
	 * @param maxComparedData Current time - 30mins, This parameter is a MUST or will return 0 of all table
	 * @return
	 */
	@GET
	@Path("counts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEntitiesCount(@QueryParam("maxComparedDate") String maxComparedData)
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /v1/zdt/counts");
		EntityManager em = null;
		String message = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			//FIXME need to know this is by design, category, category param folder will not fetched any data
//			long categoryCount = DataManager.getInstance().getAllCategoryCount(em, maxComparedData);
			long folderCount = DataManager.getInstance().getAllFolderCount(em, maxComparedData);
			long searcheCount = DataManager.getInstance().getAllSearchCount(em, maxComparedData);
			long searchPramCount = DataManager.getInstance().getAllSearchParamsCount(em, maxComparedData);
//			long categoryPramCount = DataManager.getInstance().getAllCategoryPramsCount(em, maxComparedData);
			logger.info("ZDT counters: folder count - {}, search count - {}, searchParams count - {},", folderCount,searcheCount, searchPramCount);
			ZDTCountEntity zdte = new ZDTCountEntity(folderCount, searcheCount,searchPramCount);
			message = JSONUtil.objectToJSONString(zdte);
		}catch (EMAnalyticsFwkException e) {
			message = e.getLocalizedMessage();
			logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "An error occurred while retrieving all table counts, statusCode:" + e.getStatusCode()
					+ " ,error:" + e.getMessage(), e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"#1.Error occurred when retrieve table counts in this cloud\"}").build();
		}catch (Exception e) {
			logger.error("errors while getting counts for tables -",e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"#2.Error occurred when retrieve table counts in this cloud\"}").build();
		}finally {
			if (em != null) {
				em.close();
			}
		}
		return Response.status(Status.OK).entity(message).build();
	}
	
	@GET
	@Path("sync")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sync()
	{
		Date currentUtcDate = getCurrentUTCTime();
		String syncDate = getTimeString(currentUtcDate);
		LogUtil.getInteractionLogger().info("Service calling to (GET) /v1/zdt/sync");
		ZDTTableRowEntity data = null;
		String lastCompareDate = null;
		EntityManager em = null;
		String lastComparisonDateForSync = null;
		List<Map<String, Object>> comparedDataToSync = null;
		try {			
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			lastComparisonDateForSync = DataManager.getInstance().getLastComparisonDateForSync(em);
			logger.info("LastComparisonDateForSync is {}", lastComparisonDateForSync);
			//this object contains the divergence data that will be synced, can be more than 1 records. pls NOTE how to retrieve divergence data from compare table
			comparedDataToSync = DataManager.getInstance().getComparedDataToSync(em, lastComparisonDateForSync);
			logger.info("comparedDataToSync size ={}", comparedDataToSync.size());

		} catch (Exception e) {
			logger.error(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"#2.Error occurred when fetch the data to be synced from compare table\"}").build();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		try {
			//I think this comparedDataToSync can never be null, even there is no divergence, it will not be null
			if (comparedDataToSync != null && !comparedDataToSync.isEmpty()) {
				for (Map<String, Object> dataMap : comparedDataToSync) {
					Object compareResult = dataMap.get("COMPARISON_RESULT");
					Object compareDate = dataMap.get("COMPARISON_DATE");
					JsonUtil ju = JsonUtil.buildNormalMapper();
					data = ju.fromJson(compareResult.toString(), ZDTTableRowEntity.class);
					logger.info("Prepare to split table row for sync...");
					//FIXME look into splitTableRowEntity
					List<ZDTTableRowEntity> entities = splitTableRowEntity(data);
					String response = null;
					if (entities != null) {
						logger.info("Start to sync for SSF");
						for (ZDTTableRowEntity entity : entities) {
							response = new ZDTSynchronizer().sync(entity);
						}
					}
					//sync work is done, record the sync status into sync table now...
					if (lastCompareDate != null) {
						if (lastCompareDate.compareTo( (String) compareDate) < 0) {
							lastCompareDate = (String) compareDate;
						}
					} else {
						lastCompareDate = (String) compareDate;
					}
					
					if (response != null && response.contains("Errors:")) {
						logger.error("sync failed... save FAILED status into sync table...");
						saveToSyncTable(syncDate, "full", "FAILED",lastCompareDate);
						return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
					}
				}
				logger.info("sync successful... save SUCCESSFUL status into sync table...");
				int flag = saveToSyncTable(syncDate, "full", "SUCCESSFUL",lastCompareDate);
				if (flag < 0) {
					logger.error("Sync is successful but save SUCCESSFUL status into sync table fail...");
					//FIXME sync work success, but save to sync table fail
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Fail to save sync status data\"}").build();
				}
			} else {
				return Response.ok("{\"msg\": \"Nothing to sync as no compared data\"}").build();
			}
						
			return Response.ok("{\"msg\": \"Sync is successful!\"}").build();
		}
		catch (Exception e) {
			logger.error(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Error occurred when sync\"}").build();
		} 
	}
	
	/**
	 * sync status of last time, empty for the first time.
	 * @return
	 */
	@GET
	@Path("sync/status")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSyncStatus() {

		LogUtil.getInteractionLogger().info("Service calling to (GET) /v1/zdt/sync/status");
		EntityManager em = null;
		String message = null;
		String sync_date = "";
		String sync_type = "";
		String next_schedule_date = "";
		double percentage = 0.0;
		em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
		List<Map<String, Object>> result = DataManager.getInstance().getSyncStatus(em);
		if (result != null && result.size() == 1) {
				Map<String, Object> resultMap = result.get(0);
				sync_date = resultMap.get("SYNC_DATE").toString();
				sync_type = resultMap.get("SYNC_TYPE").toString();
				next_schedule_date =  resultMap.get("NEXT_SCHEDULE_SYNC_DATE").toString();
				percentage = Double.parseDouble(resultMap.get("DIVERGENCE_PERCENTAGE").toString());
		}
		ZDTSyncStatusRowEntity syncStatus = new ZDTSyncStatusRowEntity(sync_date,sync_type, next_schedule_date, percentage);
		try {
			message = JSONUtil.objectToJSONString(syncStatus);
			return Response.status(Status.OK).entity(message).build();
		} catch (EMAnalyticsFwkException e) {
			logger.error("Errors while transfer sync status object to json string {}",e.getLocalizedMessage());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Error occurred when request sync/status\"}").build();
	}

	/**
	 * latest compare status,retrieve from compare table, get the latest comparison record(comparison_date)
	 * @return
	 */
	@GET
	@Path("compare/status")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getComparisonStatus() {
		LogUtil.getInteractionLogger().info("Service calling to (GET) /v1/zdt/compare/status");
		EntityManager em = null;
		String message = null;
		String comparison_date = "";
		String comparison_type = "";
		//String comparison_result = null;
		String next_schedule_date = "";
		String percentage = null;
		em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
		List<Map<String, Object>> result = DataManager.getInstance().getComparatorStatus(em);
		if (result != null && result.size() == 1) {
				Map<String, Object> resultMap = result.get(0);
				comparison_date = resultMap.get("COMPARISON_DATE").toString();
				comparison_type = resultMap.get("COMPARISON_TYPE").toString();
				next_schedule_date =  resultMap.get("NEXT_SCHEDULE_COMPARISON_DATE").toString();
				//	comparison_result = resultMap.get("").toString();
				percentage = resultMap.get("DIVERGENCE_PERCENTAGE").toString();
		}
		ZDTComparatorStatusRowEntity comparatorStatus = new ZDTComparatorStatusRowEntity(comparison_date,comparison_type, next_schedule_date, percentage);
		try {
			message = JSONUtil.objectToJSONString(comparatorStatus);
			return Response.status(Status.OK).entity(message).build();
		} catch (EMAnalyticsFwkException e) {
			logger.error("Errors while transfer comparator status object to json string {}",e.getLocalizedMessage());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Error occurred when request compare/status\"}").build();
	}

	/**
	 *  Save comparison result into compare table
	 * @param jsonObj
	 * @return
	 */
	@PUT
	@Path("compare/result")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveComparatorData(JSONObject jsonObj) {
		LogUtil.getInteractionLogger().info("Service calling to (PUT) /v1/zdt/compare/result");
		EntityManager em = null;
		String comparisonDate = null;
		String nextScheduleDate = null;
		String comparisonType = null;
		String comparisonResult = null;
		double divergencePercentage = 0;
		if (jsonObj != null) {
			try {
				if (jsonObj.getString("lastComparisonDateTime") == null) {
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"comparison date time can not be null\"}").build();
				} else {
					try {
						comparisonDate = jsonObj.getString("lastComparisonDateTime");
						comparisonType = jsonObj.getString("comparisonType");
						comparisonResult = jsonObj.getString("comparisonResult");
						divergencePercentage = Double.valueOf(jsonObj.getString("divergencePercentage"));
						nextScheduleDate = jsonObj.getString("nextScheduledComparisonDateTime");
						em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
						int result = DataManager.getInstance().saveToComparatorTable(em, comparisonDate,nextScheduleDate,
								comparisonType, comparisonResult, divergencePercentage);
						if (result < 0) {
							return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Error: error occurs while saving data to zdt comparator table\"}").build();
						}
					} catch (Exception e) {
						logger.error("could not save data to comparator table, "+e.getLocalizedMessage());
						return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"#1. Could not save data to comparator table\"}").build();
					} finally {
						if (em != null) {
							em.close();
						}
					}
				}
			} catch (JSONException e) {
				logger.error("could not save data to comparator table, "+e.getLocalizedMessage());
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"#2.Could not save data to comparator table\"}").build();
			}
		}
	
		return Response.status(Status.OK).entity("{\"msg\": \"Succeed to save data to zdt comparator table\"}").build();
	}

	private JSONArray getFolderTableData(EntityManager em, String type, String date,String maxComparedData,String tenant)
	{
		List<Map<String, Object>> list = DataManager.getInstance().getFolderTableData(em,type, date, maxComparedData, tenant);
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
			return new JSONArray();
		}
		JSONArray array = new JSONArray();
		for (Map<String, Object> row : list) {
			array.put(row);
		}
		logger.debug("Retrieved table data for {} is \"{}\"", dataName, array.toString());
		return array;
	}

	private JSONArray getSearchParamsTableData(EntityManager em, String type, String date, String maxComparedData, String tenant)
	{
		List<Map<String, Object>> list = DataManager.getInstance().getSearchParamTableData(em,type, date, maxComparedData, tenant);
		return getJSONArrayForListOfObjects(TABLE_SEARCH_PARAMS, list);
	}

	private JSONArray getSearchTableData(EntityManager em, String type, String date, String maxComparedData, String tenant)
	{
		List<Map<String, Object>> list = DataManager.getInstance().getSearchTableData(em,type, date, maxComparedData, tenant);
		return getJSONArrayForListOfObjects(TABLE_SEARCH, list);
	}

	public Date getCurrentUTCTime()
	{
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		long localNow = System.currentTimeMillis();
		long offset = cal.getTimeZone().getOffset(localNow);
		Date utcDate = new Date(localNow - offset);

		return utcDate;
	}

	private  List<List<?>> splitList(List<?> list, int len) {
		if (list == null || list.size() == 0 || len < 1) {
			return null;
		}
		List<List<?>> result = new ArrayList<List<?>>();
		int size = list.size();
		int count = (size + len - 1) / len;
		for (int i = 0; i < count; i++) {
			List<?> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
			result.add(subList);
		}
		return result;
	}

	public List<ZDTTableRowEntity> splitTableRowEntity(ZDTTableRowEntity originalEntity){
		List<ZDTTableRowEntity> entities = new ArrayList<ZDTTableRowEntity>();
		if (originalEntity != null) {
			List<List<?>> splitFolders = null;
			List<List<?>> splitSearch = null;
			List<List<?>> splitParams = null;
			// for each connection, we just sync 1000 rows
			int length = 1000;
			if (originalEntity.getSavedSearchFoldersy() != null) {
				splitFolders = splitList(originalEntity.getSavedSearchFoldersy(), length);
			}
			if (originalEntity.getSavedSearchSearch() != null) {
				splitSearch = splitList(originalEntity.getSavedSearchSearch(), length);
			}

			if (originalEntity.getSavedSearchSearchParams() != null) {
				splitParams = splitList(originalEntity.getSavedSearchSearchParams(),length);
			}

			// sync search table first and then sync parameter table to avoid key constraints
			if (splitFolders != null) {
				for (List<?> folderList : splitFolders) {
					ZDTTableRowEntity rowEntity = new ZDTTableRowEntity();
					rowEntity.setSavedSearchFoldersy((List<SavedSearchFolderRowEntity>) folderList);
					entities.add(rowEntity);
				}
			}
			if (splitSearch != null) {
				for (List<?> searchList : splitSearch) {
					ZDTTableRowEntity rowEntity = new ZDTTableRowEntity();
					rowEntity.setSavedSearchSearch((List<SavedSearchSearchRowEntity>) searchList);
					entities.add(rowEntity);
				}
			}
			if (splitParams != null) {
				for (List<?> paramList : splitParams) {
					ZDTTableRowEntity rowEntity = new ZDTTableRowEntity();
					rowEntity.setSavedSearchSearchParams((List<SavedSearchSearchParamRowEntity>) paramList);
					entities.add(rowEntity);
				}
			}
		}
		return entities;
	}


	private String getTimeString(Date date)
	{
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	private int saveToSyncTable(String syncDateStr, String type, String syncResult, String lastComparisonDate) {
		Date syncDate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			syncDate = sdf.parse(syncDateStr);
		} catch (ParseException e) {
			logger.error(e);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(syncDate);
		cal.add(Calendar.HOUR_OF_DAY, 6);//FIXME: what is this mean>?
		Date nextScheduleDate = cal.getTime();
		String nextScheduleDateStr = getTimeString(nextScheduleDate);
		double divergencePercentage = 0.0;
		return DataManager.getInstance().saveToSyncTable(syncDateStr, nextScheduleDateStr, type, syncResult, divergencePercentage, lastComparisonDate);

	}
}
