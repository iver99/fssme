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
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.exception.HalfSyncException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.exception.NoComparedResultException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.exception.SyncException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
		EntityManager em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
		String lastComparisonDateForSync = null;
		List<Map<String, Object>> comparedDataToSync = null;
		int position = 0;
		int halfSyncCursor = 0;
		String halfSyncLastCompareDate = null;
		//We need to handle half-sync case first, means last sync work is half finished(commited into db successfully) and half not.
		//if we finish handling half-sync case successful, we only update the type and sync_result in sync table, no need to create a new record in sync table
		try {
			Map<String, Object> halfSyncRecord = DataManager.getInstance().checkHalfSyncRecord(em);
			//handle half sync of last sync work
			if(halfSyncRecord !=null){
				//last time sync work failed position
				position  = (int)halfSyncRecord.get("SYNC_RESULT");
				halfSyncLastCompareDate  = (String)halfSyncRecord.get("LAST_COMPARISON_DATE");
				logger.info("Last time sync work failed at position {} and last comparision date is {}", position, halfSyncLastCompareDate);
				//get half-synced compared data
				Map<String, Object> halfSyncComparedDataToSync = DataManager.getInstance().getHalfSyncedComparedData(em, halfSyncLastCompareDate);
				Object compareResult = halfSyncComparedDataToSync.get("COMPARISON_RESULT");
//				Object compareDate = halfSyncComparedDataToSync.get("COMPARISON_DATE");
				JsonUtil ju = JsonUtil.buildNormalMapper();
				data = ju.fromJson(compareResult.toString(), ZDTTableRowEntity.class);
				logger.info("#1.Prepare to split table row for sync...");
				List<ZDTTableRowEntity> entities = splitTableRowEntity(data);
				if (entities != null) {
					logger.info("#1. Start to sync for SSF");
					for(halfSyncCursor =0 ;halfSyncCursor<entities.size() ; halfSyncCursor++){
						//find the position that last sync failed at
						if(halfSyncCursor < position){
							continue;
						}
						new ZDTSynchronizer().sync(entities.get(halfSyncCursor));
					}
				}
				logger.info("Handle half-sync case successful, update sync status in sync table");
				//finish handling half-sync case, update sync result to successful and type into full
				int flag = DataManager.getInstance().updateHalfSyncStatus("SUCCESSFUL","full");
				if(flag < 0){
					logger.error("updateHalfSyncStatus into sync table fail... ");
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Errors Fail to save sync status data\"}").build();
				}
			}
		}catch(IOException e){
			logger.error(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Errors IOException occurred when sync\"}").build();
		}catch (NoComparedResultException e){
			logger.error(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Errors occurred when sync, NoComparedResultException threw out, no last compared data was found!\"}").build();
		}catch (HalfSyncException e) {
			logger.error(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Errors occurred when sync, HalfSyncException threw out!\"}").build();
		}catch(SyncException e){
			/**
			 *  If handling half-sync case failed at the beginning(no commit generated), then don't need to update the sync table.
			 *  If there were commits after the position, need to update the sync table with the new the failed sync position.
			 */
			if(halfSyncCursor == position){
				//sync failed again, but nothing is needed to do.
				logger.error("Handling half-sync fails, but nothing is needed since there was no commit.");
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Errors SyncException occurred when sync\"}").build();
			}
			//sync failed again, and need to update the failed position.
			if(halfSyncCursor > position){
				logger.error("Handling half-sync fails at position {} will save this position in to sync table.", halfSyncCursor);
				int flag = DataManager.getInstance().updateHalfSyncStatus(Integer.toString(halfSyncCursor),null);
				if(flag < 0){
					logger.error("updateHalfSyncStatus into sync table fail... ");
                    return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Errors Fail to save sync status data\"}").build();
				}
			}
			logger.error(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Errors SyncException occurred when sync\"}").build();
		}
		//handle half sync case end...

		try {
			lastComparisonDateForSync = DataManager.getInstance().getLastComparisonDateForSync(em);
			logger.info("#2.LastComparisonDateForSync is {}", lastComparisonDateForSync);
			//this object contains the divergence data that will be synced, can be more than 1 records. pls NOTE how to retrieve divergence data from compare table
			comparedDataToSync = DataManager.getInstance().getComparedDataForSync(em, lastComparisonDateForSync);
			logger.info("comparedDataToSync size ={}", comparedDataToSync.size());

		}catch (Exception e) {
			//FIXME no exception in this try indeed.
			logger.error(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"#2.Error occurred when fetch the data to be synced from compare table\"}").build();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		//in case sync fail, we need to know what position sync work failed at, in order next time we re-sync.
		int cursor = 0;
		Object compareResult = null;
		Object compareDate = null;
		List<ZDTTableRowEntity> entities = null;
		try {
			//I think this comparedDataToSync can never be null, even there is no divergence, it will not be null
			if (comparedDataToSync != null && !comparedDataToSync.isEmpty()) {
				for (Map<String, Object> dataMap : comparedDataToSync) {
					compareResult = dataMap.get("COMPARISON_RESULT");
					compareDate = dataMap.get("COMPARISON_DATE");
					JsonUtil ju = JsonUtil.buildNormalMapper();
					data = ju.fromJson(compareResult.toString(), ZDTTableRowEntity.class);
					logger.info("Prepare to split table row for sync...");
					//FIXME look into splitTableRowEntity
					entities = splitTableRowEntity(data);
					if (entities != null) {
						logger.info("#2.Start to sync for SSF");
						for( cursor =0 ;cursor<entities.size() ; cursor++){
							new ZDTSynchronizer().sync(entities.get(cursor));
						}
					}
					//sync work is done, record the sync status into sync table now...
					//FIXME below may have some problem
					lastCompareDate = getComparedDateforSync(lastCompareDate, (String) compareDate);
				}
				logger.info("sync successful... save SUCCESSFUL status into sync table...");
				int flag = saveToSyncTable(syncDate, "full", "SUCCESSFUL",lastCompareDate);
				if (flag < 0) {
					logger.error("#1.Sync is successful but save SUCCESSFUL status into sync table fail...");
					//FIXME sync work success, but save to sync table fail
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Errors Fail to save sync status data\"}").build();
				}
			} else {
				return Response.ok("{\"msg\": \"Nothing to sync as no compared data\"}").build();
			}
						
			return Response.ok("{\"msg\": \"Sync is successful!\"}").build();
		}catch (IOException e){//ZDTAPI sync api will check 'Errors' in response
			logger.error(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Errors IOException occurred when sync\"}").build();
		}catch (SyncException e){
			/**
			 * There is 2 kinds of case SyncException threw out:
			 *
			 * #1. If entities size is more than 2(means divergence data is more than 1000), and cursor is more than 1, means sync work is failed into a half-sync situation,
			 * in this case, will store SYNC_TYPE=half and cursor=xx value into sync table.
			 *
			 * #2. Divergence data is less than 1000, or sync work failed WITHIN the first commit. Will create a new sync status 'SYNC_RESULT=FAILED' into sync table
			 */
			lastCompareDate = getComparedDateforSync(lastCompareDate, (String) compareDate);
			if(entities.size() >1 && cursor>0){
				logger.error("Sync work failed at position {} will save this position in to sync table with type = half.", cursor);
				int flag = saveToSyncTable(syncDate, "half", Integer.toString(cursor),lastCompareDate);
				if (flag < 0) {
					//FIXME this case is not handled yet
					logger.error("#2.Save half sync status into sync table fail...");
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Errors Fail to save sync status data\"}").build();
				}
			}else{
				logger.error("sync failed... save FAILED status into sync table...");
				int flag = saveToSyncTable(syncDate, "full", "FAILED",lastCompareDate);
				if (flag < 0) {
					//FIXME this case is not handled yet
					logger.error("#3.Save half sync status into sync table fail...");
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Errors Fail to save sync status data\"}").build();
				}
			}

			logger.error(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Errors SyncException occurred when sync\"}").build();
		}catch (Exception e){
			logger.error(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{\"msg\": \"Errors occurred when sync\"}").build();
		}
	}

	private String getComparedDateforSync(String lastCompareDate, String compareDate) {
		if (lastCompareDate != null) {
            if (lastCompareDate.compareTo(compareDate) < 0) {
                lastCompareDate = compareDate;
            }
        } else {
            lastCompareDate = compareDate;
        }
		return lastCompareDate;
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
		logger.info("[Sync report]: Sync finished with result [{}] at [{}] with sync type [{}]", syncResult, syncDateStr, type);
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
