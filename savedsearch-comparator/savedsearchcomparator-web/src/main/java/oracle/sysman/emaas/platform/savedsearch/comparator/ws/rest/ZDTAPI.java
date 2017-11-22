/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.TimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emaas.platform.savedsearch.comparator.exception.ErrorEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.exception.ZDTErrorConstants;
import oracle.sysman.emaas.platform.savedsearch.comparator.exception.ZDTException;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.JsonUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts.CountsEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.SavedsearchRowsComparator;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstanceData;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstancesComparedData;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities.ZDTComparatorStatusRowEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities.ZDTTableRowEntity;

@Path("/v1/comparator")
public class ZDTAPI
{
	private static final Logger logger = LogManager.getLogger(ZDTAPI.class);

	/**
	 * this method is return the comparator status for omc instances.(will request zdt/compare/status API)
	 * @param tenantIdParam
	 * @return
	 */
	@GET
	@Path("compare/status")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompareStatus(@HeaderParam(value = "X-USER-IDENTITY-DOMAIN-NAME") String tenantIdParam) {
		logger.info("incoming call from zdt comparator to get comparator status");
		if (tenantIdParam == null){
			tenantIdParam = "CloudServices";
		}
		SavedsearchRowsComparator dcc = null;
		String response = null;
		try {
			dcc = new SavedsearchRowsComparator();
			response = dcc.retrieveComparatorStatusForOmcInstance(tenantIdParam, null);
		} catch (ZDTException e1) {
			logger.error(e1);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(JsonUtil.buildNormalMapper().toJson(new ErrorEntity(e1))).build();
		} catch (Exception e2) {
			logger.error(e2);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(JsonUtil.buildNormalMapper().toJson(new ErrorEntity(e2))).build();
		}
		
		return Response.status(Status.OK).entity(response).build();
		
	}

	/**
	 * this method is return the sync status for omc instance.(Will request zdt/sync/status)
	 * @param tenantIdParam
	 * @return
	 */
	@GET
	@Path("sync/status")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSyncStatus(@HeaderParam(value = "X-USER-IDENTITY-DOMAIN-NAME") String tenantIdParam) {
		logger.info("incoming call from zdt comparator to get sync status");
		if (tenantIdParam == null){
			tenantIdParam = "CloudServices";
		}
		SavedsearchRowsComparator dcc = null;
		String response = null;
		try {
			dcc = new SavedsearchRowsComparator();
			response = dcc.retrieveSyncStatusForOmcInstance(tenantIdParam, null);
		} catch (ZDTException e1) {
			logger.error(e1);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(JsonUtil.buildNormalMapper().toJson(new ErrorEntity(e1))).build();
		} catch (Exception e2) {
			logger.error(e2);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(JsonUtil.buildNormalMapper().toJson(new ErrorEntity(e2))).build();
		}
		
		return Response.status(Status.OK).entity(response).build();
		
	}

	/**
	 *  will invoke compare action, #1.fetch data, #2.compare data, #3.store the different data into zdt table
	 * @param tenantIdParam
	 * @param skipMinutes
	 * @return
	 */
	@GET
	@Path("compare")
	@Produces(MediaType.APPLICATION_JSON)
	public Response compareRows(@HeaderParam(value = "X-USER-IDENTITY-DOMAIN-NAME") String tenantIdParam,
            @QueryParam("before") int skipMinutes) {
		logger.info("incoming call from zdt comparator to do row comparing");
		if (tenantIdParam == null){
			tenantIdParam = "CloudServices";
		}
		String message = "";
		if (skipMinutes <= 0) {
			skipMinutes = 30;    // to check: what is the accurate default skipping time for comparator?
		}
		String maxComparedDate = null;
		if (skipMinutes > 0) {
			maxComparedDate = TimeUtil.getMaxTimeStampStr(skipMinutes);
		}
		logger.info("the max compared date is "+maxComparedDate);
		try {
			SavedsearchRowsComparator dcc = new SavedsearchRowsComparator();
			String tenants1 = dcc.retrieveTenants(tenantIdParam, null,dcc.getClient1());			
			String tenants2 = dcc.retrieveTenants(tenantIdParam, null,dcc.getClient2());
			//FIXME fix this return laster
			if (tenants1 == null || tenants2 == null) {
				return Response.status(400).entity(new ErrorEntity(ZDTErrorConstants.NULL_TABLE_ROWS_ERROR_CODE, ZDTErrorConstants.NULL_TABLE_ROWS_ERROR_MESSAGE)).build();
			}
			JSONArray tenantArrayForClient1 = null;
			
			JSONObject obj1 = new JSONObject(tenants1);
			boolean isComparedForClient1 = obj1.getBoolean("isCompared");
			tenantArrayForClient1 = obj1.getJSONArray("tenants");
			String lastComparedDateC1 = obj1.getString("lastComparedDate");
			logger.info("C1: isCompared={}, tenant list size = {}, last compared date = {}", isComparedForClient1, tenantArrayForClient1.length(),lastComparedDateC1);

			JSONArray tenantArrayForClient2 = null;
			JSONObject obj2 = new JSONObject(tenants2);
			boolean isComparedForClient2 = obj2.getBoolean("isCompared");
			tenantArrayForClient2 = obj2.getJSONArray("tenants");
			String lastComparedDateC2 = obj2.getString("lastComparedDate");
			logger.info("C2: isCompared={}, tenant list size = {}, last compared date = {}", isComparedForClient2, tenantArrayForClient2.length(),lastComparedDateC2);

			if (tenantArrayForClient1.length() == 0 && tenantArrayForClient2.length() == 0) {
				return Response.status(Status.OK).entity("{\"msg\":\"#1.No user created dashboards, No need to compare\"}").build();
			}
			
			Set<String> tenants = new HashSet<String>();
			for (int i = 0; i < tenantArrayForClient1.length(); i++) {
				String tenant = tenantArrayForClient1.get(i).toString();
				tenants.add(tenant);
			}
			for (int i = 0; i < tenantArrayForClient2.length(); i++) {
				String tenant = tenantArrayForClient2.get(i).toString();
				tenants.add(tenant);
			}
			
			boolean isCompared = true;
			String compareType = "incremental";
			//check compare table, if compared before, it is a incremental, otherwise is a full compare.
			if ((!isComparedForClient1) || (!isComparedForClient2)) {
				isCompared = false;
				compareType = "full";
			}
			logger.info("Is Compared? = {}, compare type = {}", isCompared, compareType);
			InstancesComparedData<ZDTTableRowEntity> result = null;
			int totalRowForClient1 = dcc.getTotalRowForOmcInstance(tenantIdParam, null,dcc.getClient1(), maxComparedDate);
			int totalRowForClient2 = dcc.getTotalRowForOmcInstance(tenantIdParam, null,dcc.getClient2(), maxComparedDate);
			int totalRow = totalRowForClient1 + totalRowForClient2;
			if (totalRow == 0) {
				return Response.status(Status.OK).entity("{\"msg\":\"#2.No user created dashboards, No need to compare\"}").build();
			} 
			logger.info("totalRow={}",totalRow);
			int totalDifferentRows = 0;
			
			// if it is the first time comparison
			if ("full".equals(compareType)) {
				int count = 0;
				JSONObject obj = null;
				JSONObject subObj = new JSONObject();
				StringBuffer sb1 = new StringBuffer();
				StringBuffer sb2 = new StringBuffer();
				//handle tenant one by one and save comparison result for each tenant
				for (String tenantStr : tenants) {					
					count = count + 1;
					//logger.info("tenant count = "+count);
					result = dcc.compare(tenantIdParam, null,compareType,maxComparedDate, isCompared, tenantStr);
					if (result != null) {
						int comparedDataNum = dcc.countForComparedRows(result.getInstance1().getData()) + dcc.countForComparedRows(result.getInstance2().getData());
						logger.info("comparedNum={}",comparedDataNum);
						totalDifferentRows = totalDifferentRows + comparedDataNum;
												
						double percentage = 0;
						Date currentUtcDate = TimeUtil.getCurrentUTCTime();
						String comparisonDate = TimeUtil.getTimeString(currentUtcDate);
						Calendar cal = Calendar.getInstance();
						cal.setTime(currentUtcDate);
						cal.add(Calendar.HOUR_OF_DAY, 12);
						Date nextScheduleDate = cal.getTime();
						String nextScheduleDateStr = TimeUtil.getTimeString(nextScheduleDate);
						
						if (count == tenants.size()) {
							// the last tenant is fetched										
							double percen = (double)totalDifferentRows/(double)totalRow;
							DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
							DecimalFormat df = new DecimalFormat("#.##########", symbols);
							percentage = Double.parseDouble(df.format(percen));
						}
						
						JsonUtil jsonUtil = JsonUtil.buildNormalMapper();
						
						ZDTTableRowEntity data1 = result.getInstance1().getData();
						String result1 = jsonUtil.toJson(data1);
						//logger.info("client1-result="+result1);
						ZDTComparatorStatusRowEntity statusRow1 = new ZDTComparatorStatusRowEntity(comparisonDate,compareType,nextScheduleDateStr,percentage+"", result1);
										
						ZDTTableRowEntity data2 = result.getInstance2().getData();
						String result2 = jsonUtil.toJson(data2);
						//logger.info("client2-result="+result2);
						ZDTComparatorStatusRowEntity statusRow2 = new ZDTComparatorStatusRowEntity(comparisonDate,compareType,nextScheduleDateStr,percentage+"", result2);
						
						logger.info("save status tenant by tenant");
						// save status information for client 1  -- switch data for sync
						LookupClient client1 = result.getInstance1().getClient();
						dcc.saveComparatorStatus(tenantIdParam,null, client1, statusRow2);
						
						// save status information for client 2 -- switch data for sync
						LookupClient client2 = result.getInstance2().getClient();
						dcc.saveComparatorStatus(tenantIdParam,null, client2, statusRow1);
						
						
						sb1.append(result1);
						
						sb2.append(result2);
						
						if (count == tenants.size()) {
							 obj = new JSONObject();
							obj.put("comparisonDateTime", comparisonDate);
							obj.put("comparisonType", compareType);
							obj.put("differentRowNum", totalDifferentRows);
							obj.put("totalRowNum", totalRow);
							obj.put("divergencePercentage", percentage * 100 + "%");
							if(isCompared){
								obj.put("msg","NOTE: This is the comparison result since last compared date [" + lastComparedDateC1 + "], but latest 30 mins modified data will not be compared.");// here we take cloud1's last compared date.
							}else{
								obj.put("msg","NOTE: This is the comparison result of all user created data in 2 clouds, but latest 30 mins modified data will not be compared");
							}
							if (totalDifferentRows > 100) {
								obj.put("divergenceSummary", "The number for different rows is more than 1000; There is too much content to display;");
							} else {
								
								subObj.put(result.getInstance1().getKey(), sb2.toString());
								subObj.put(result.getInstance2().getKey(), sb1.toString());
								obj.put("divergenceSummary", subObj);
							}							
						}						
						result = null;
					} else {
						Response.status(Status.INTERNAL_SERVER_ERROR).entity(JsonUtil.buildNormalMapper().toJson(new ErrorEntity(ZDTErrorConstants.NULL_LINK_ERROR_CODE, ZDTErrorConstants.NULL_LINK_ERROR_MESSAGE))).build();
					}
				}  // end loop for tenants
				message = obj.toString(); 
			} else {
				// not the first time comparison
				result = dcc.compare(tenantIdParam, null,compareType,maxComparedDate, isCompared, null);
				if (result != null) {
					int comparedDataNum = dcc.countForComparedRows(result.getInstance1().getData()) + dcc.countForComparedRows(result.getInstance2().getData());
					logger.info("comparedNum={}",comparedDataNum);
					totalDifferentRows = totalDifferentRows + comparedDataNum;
					double percen = (double)comparedDataNum/(double)totalRow;
					DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
					DecimalFormat df = new DecimalFormat("#.##########", symbols);
					double percentage = Double.parseDouble(df.format(percen));
					Date currentUtcDate = TimeUtil.getCurrentUTCTime();
					String comparisonDate = TimeUtil.getTimeString(currentUtcDate);
					Calendar cal = Calendar.getInstance();
					cal.setTime(currentUtcDate);
					cal.add(Calendar.HOUR_OF_DAY, 12);
					Date nextScheduleDate = cal.getTime();
					String nextScheduleDateStr = TimeUtil.getTimeString(nextScheduleDate);
					
					JsonUtil jsonUtil = JsonUtil.buildNormalMapper();
					
					ZDTTableRowEntity data1 = result.getInstance1().getData();
					String result1 = jsonUtil.toJson(data1);
					//logger.info("client1-result="+result1);
					ZDTComparatorStatusRowEntity statusRow1 = new ZDTComparatorStatusRowEntity(comparisonDate,compareType,nextScheduleDateStr,percentage+"", result1);
									
					ZDTTableRowEntity data2 = result.getInstance2().getData();
					String result2 = jsonUtil.toJson(data2);
					//logger.info("client2-result="+result2);
					ZDTComparatorStatusRowEntity statusRow2 = new ZDTComparatorStatusRowEntity(comparisonDate,compareType,nextScheduleDateStr,percentage+"", result2);
					
					// save status information for client 1  -- switch data for sync
					LookupClient client1 = result.getInstance1().getClient();
					dcc.saveComparatorStatus(tenantIdParam,null, client1, statusRow2);
					
					// save status informantion for client 2 -- switch data for sync
					LookupClient client2 = result.getInstance2().getClient();
					dcc.saveComparatorStatus(tenantIdParam,null, client2, statusRow1);
					
					JSONObject obj = new JSONObject();
					obj.put("comparisonDateTime", comparisonDate);
					obj.put("comparisonType", compareType);
					obj.put("differentRowNum", totalDifferentRows);
					obj.put("totalRowNum", totalRow);
					obj.put("divergencePercentage", percentage * 100 + "%");
					if(isCompared){
						obj.put("msg","NOTE: This is the comparison result since last compared date [" + lastComparedDateC1 + "], but latest 30 mins modified data will not be compared.");// here we take cloud1's last compared date.
					}else{
						obj.put("msg","NOTE: This is the comparison result of all user created data in 2 clouds, but latest 30 mins modified data will not be compared");
					}
					JSONObject subObj = new JSONObject();
					subObj.put(result.getInstance1().getKey(), result2);
					subObj.put(result.getInstance2().getKey(), result1);
					obj.put("divergenceSummary", subObj);
					
					message = obj.toString(); 
				} else {
					Response.status(Status.INTERNAL_SERVER_ERROR).entity(JsonUtil.buildNormalMapper().toJson(new ErrorEntity(ZDTErrorConstants.NULL_LINK_ERROR_CODE, ZDTErrorConstants.NULL_LINK_ERROR_MESSAGE))).build();				
				}
			}			
		}  catch(ZDTException zdtE) {
 			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(JsonUtil.buildNormalMapper().toJson(new ErrorEntity(zdtE))).build();
 		} catch (Exception e) {
 			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(JsonUtil.buildNormalMapper().toJson(new ErrorEntity(e))).build();
 		}
		
		return Response.status(Status.OK).entity(message).build();
	}

	/**
	 * will sync sync action, #1.retrieve data from zdt table, #2. sync action. #3. store sync status into sync table
	 * @param tenantIdParam
	 * @return
	 */
	@GET
	@Path("sync")
	@Produces(MediaType.APPLICATION_JSON)
	public Response syncOnSSF(@HeaderParam(value = "X-USER-IDENTITY-DOMAIN-NAME") String tenantIdParam)
	{
		logger.info("There is an incoming call from ZDT comparator API to sync");
		if (tenantIdParam == null){
			tenantIdParam = "CloudServices";
		}
		SavedsearchRowsComparator dcc;
		try {
			dcc = new SavedsearchRowsComparator();		

			String message1 = dcc.syncForInstance(tenantIdParam,  null,  dcc.getClient1());
			String message2 = dcc.syncForInstance(tenantIdParam,  null,  dcc.getClient2());
			if (message1.contains("Errors") || message2.contains("Errors")) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(JsonUtil.buildNormalMapper().toJson(new ErrorEntity(ZDTErrorConstants.FAIL_TO_SYNC_ERROR_CODE, ZDTErrorConstants.FAIL_TO_SYNC_ERROR_MESSAGE))).build();
			}
			JSONObject object = new JSONObject();
			object.put(dcc.getKey1(), message1);
			object.put(dcc.getKey2(), message2);
			return Response.ok(object).build();
			
	    } catch(ZDTException zdtE) {
			logger.error(zdtE);
 			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(JsonUtil.buildNormalMapper().toJson(new ErrorEntity(zdtE))).build();
 		} catch (Exception e) {
			logger.error(e);
 			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(JsonUtil.buildNormalMapper().toJson(new ErrorEntity(e))).build();
 		}
		
	}
	public static class InstanceCounts
	{
		private String instanceName;
		private CountsEntity counts;

		public InstanceCounts(InstanceData<CountsEntity> data)
		{
			instanceName = data.getKey();
			counts = data.getData();
		}

		/**
		 * @return the counts
		 */
		public CountsEntity getCounts()
		{
			return counts;
		}

		/**
		 * @return the instanceName
		 */
		public String getInstanceName()
		{
			return instanceName;
		}

		/**
		 * @param counts
		 *            the counts to set
		 */
		public void setCounts(CountsEntity counts)
		{
			this.counts = counts;
		}

		/**
		 * @param instanceName
		 *            the instanceName to set
		 */
		public void setInstanceName(String instanceName)
		{
			this.instanceName = instanceName;
		}
	}

	public static class InstancesComapredCounts
	{
		private InstanceCounts instance1;
		private InstanceCounts instance2;

		/**
		 * @param instance1
		 * @param instance2
		 */
		public InstancesComapredCounts(InstanceCounts instance1, InstanceCounts instance2)
		{
			super();
			this.instance1 = instance1;
			this.instance2 = instance2;
		}

		/**
		 * @return the instance1
		 */
		public InstanceCounts getInstance1()
		{
			return instance1;
		}

		/**
		 * @return the instance2
		 */
		public InstanceCounts getInstance2()
		{
			return instance2;
		}

		/**
		 * @param instance1
		 *            the instance1 to set
		 */
		public void setInstance1(InstanceCounts instance1)
		{
			this.instance1 = instance1;
		}

		/**
		 * @param instance2
		 *            the instance2 to set
		 */
		public void setInstance2(InstanceCounts instance2)
		{
			this.instance2 = instance2;
		}
	}
}
