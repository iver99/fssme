/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emaas.platform.savedsearch.comparator.exception.ZDTErrorConstants;
import oracle.sysman.emaas.platform.savedsearch.comparator.exception.ZDTException;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.JsonUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.TenantSubscriptionUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.AbstractComparator;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts.CountsEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.RowEntityComparator.CompareListPair;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities.*;


public class SavedsearchRowsComparator extends AbstractComparator
{
	private static final Logger logger = LogManager.getLogger(SavedsearchRowsComparator.class);
	
	private  String key1 = null;
	private String key2 = null;
	private  LookupClient client1 = null;
	private  LookupClient client2 = null;
	
	
	

	public SavedsearchRowsComparator() throws ZDTException {
		super();
		HashMap<String, LookupClient> instances = getOMCInstances();
		if (instances == null) {
			logger.error("Failed to retrieve ZDT OMC instances: null retrieved");
			throw new ZDTException(ZDTErrorConstants.NULL_RETRIEVED_ERROR_CODE,ZDTErrorConstants.NULL_RETRIEVED_ERROR_MESSAGE);
		}
		 
		for (String key : instances.keySet()) {
				if (client1 == null) {
					client1 = instances.get(key);
					key1 = key;
				} else {
					if (client2 == null)
					client2 = instances.get(key);
					key2 = key;
				}
			}
	}
	
	public ZDTTableRowEntity combineRowEntity(List<ZDTTableRowEntity> rowEntityList){
		ZDTTableRowEntity finalEntity = new ZDTTableRowEntity();
		List<SavedSearchCategoryRowEntity> category = new ArrayList<SavedSearchCategoryRowEntity>();
		List<SavedSearchCategoryParamRowEntity> categoryParams = new ArrayList<SavedSearchCategoryParamRowEntity>();
		List<SavedSearchFolderRowEntity> folder = new ArrayList<SavedSearchFolderRowEntity>();
		List<SavedSearchSearchParamRowEntity> searchParams = new ArrayList<SavedSearchSearchParamRowEntity>();
		List<SavedSearchSearchRowEntity> search = new ArrayList<SavedSearchSearchRowEntity>();
		for (ZDTTableRowEntity entity : rowEntityList) {
			if (entity.getSavedSearchCategory() != null && !entity.getSavedSearchCategory().isEmpty()) {
				category.addAll(entity.getSavedSearchCategory());
			}
			if (entity.getSavedSearchCategoryParams()!= null && !entity.getSavedSearchCategoryParams().isEmpty()) {
				categoryParams.addAll(entity.getSavedSearchCategoryParams());
			}
			if (entity.getSavedSearchFoldersy() != null && !entity.getSavedSearchFoldersy().isEmpty()) {
				folder.addAll(entity.getSavedSearchFoldersy());
			}
			if (entity.getSavedSearchSearch() != null && !entity.getSavedSearchSearch().isEmpty()) {
				search.addAll(entity.getSavedSearchSearch());
			}
			if (entity.getSavedSearchSearchParams() != null && !entity.getSavedSearchSearchParams().isEmpty()) {
				searchParams.addAll(entity.getSavedSearchSearchParams());
			}
		}
		finalEntity.setSavedSearchCategory(category);
		finalEntity.setSavedSearchCategoryParams(categoryParams);
		finalEntity.setSavedSearchFoldersy(folder);
		finalEntity.setSavedSearchSearch(search);
		finalEntity.setSavedSearchSearchParams(searchParams);
		return finalEntity;
	}

	public InstancesComparedData<ZDTTableRowEntity> compare(String tenantId, String userTenant, String comparisonType, 
			String maxComparedDate, boolean iscompared, JSONObject tenantObj) throws ZDTException
	{
		try {
			logger.info("Starts to compare the two ssf OMC instances: table by table and row by row");
			ZDTTableRowEntity tre1 = null;
			ZDTTableRowEntity tre2 = null;
			if (!iscompared || comparisonType == "full") {
				// for the first time comparing, fetch all table data tenant by tenant
				// for client1
				List<ZDTTableRowEntity> allRowEntitisForClient1 = new ArrayList<ZDTTableRowEntity>();
				JSONArray array1 = tenantObj.getJSONArray("client1");
				logger.info("****client1="+client1.getServiceUrls().get(0));				
				for (int i = 0; i < array1.length(); i++) {
					String tenant = array1.get(i).toString();
					ZDTTableRowEntity subTre = retrieveRowsForSingleInstance(tenantId, userTenant,client1, comparisonType, maxComparedDate,tenant);
					if (subTre == null) {
						logger.error("Failed to retrieve ZDT table rows entity for instance {}", key1);
						return null;
					}
					logger.info("client1-{}-search={}",i,subTre.getSavedSearchSearch().size());
					logger.info("client1-{}-search params={}",i,subTre.getSavedSearchSearchParams().size());
					
					allRowEntitisForClient1.add(subTre);
				}
				tre1 = combineRowEntity(allRowEntitisForClient1);
				
				logger.info("tre1 search size = "+tre1.getSavedSearchSearch().size());
				logger.info("tre1 search params size = "+tre1.getSavedSearchSearchParams().size());
				// for client2
				List<ZDTTableRowEntity> allRowEntitisForClient2 = new ArrayList<ZDTTableRowEntity>();
				logger.info("****client2="+client2.getServiceUrls().get(0));
				JSONArray array2 = tenantObj.getJSONArray("client2");
				logger.info("array2="+array2.toString());
				for (int i = 0; i < array2.length(); i++) {
					String tenant = array2.get(i).toString();
					ZDTTableRowEntity subTre = retrieveRowsForSingleInstance(tenantId, userTenant,client2, comparisonType, maxComparedDate,tenant);
					logger.info("client2-search="+subTre.getSavedSearchSearch().size());
					logger.info("client2-search params="+subTre.getSavedSearchSearch().size());
					allRowEntitisForClient2.add(subTre);
				}
				tre2 = combineRowEntity(allRowEntitisForClient2);
				logger.info("tre2 search size = "+tre2.getSavedSearchSearch().size());
				logger.info("tre2 search params size = "+tre2.getSavedSearchSearchParams().size());
			} else {
				tre1 = retrieveRowsForSingleInstance(tenantId, userTenant,client1, comparisonType, maxComparedDate, null);
				tre2 = retrieveRowsForSingleInstance(tenantId, userTenant,client2, comparisonType, maxComparedDate, null);
			}
			
 			CountsEntity entity1 = retrieveCountsForSingleInstance(tenantId, userTenant,client1, maxComparedDate);
			if (entity1 == null) {
				return null;
			}
			int rowNum1 = (int)((entity1.getCountOfCategory() == null? 0L:entity1.getCountOfCategory())
					+ (entity1.getCountOfFolders() ==null?0L:entity1.getCountOfFolders())
					+ (entity1.getCountOfCategoryPrams() ==null?0L:entity1.getCountOfCategoryPrams())
					+ (entity1.getCountOfSearchParams() ==null?0L:entity1.getCountOfSearchParams())
					+ (entity1.getCountOfSearch()==null?0L:entity1.getCountOfSearch()));
 			

			CountsEntity entity2 = retrieveCountsForSingleInstance(tenantId, userTenant,client2, maxComparedDate);
			if (entity2 == null) {
				return null;
			}
			int rowNum2 = (int)((entity2.getCountOfCategory() == null? 0L:entity2.getCountOfCategory())
					+ (entity2.getCountOfFolders() ==null?0L:entity2.getCountOfFolders())
					+ (entity2.getCountOfCategoryPrams() ==null?0L:entity2.getCountOfCategoryPrams())
					+ (entity2.getCountOfSearchParams() ==null?0L:entity2.getCountOfSearchParams())
					+ (entity2.getCountOfSearch()==null?0L:entity2.getCountOfSearch()));
 		
			InstancesComparedData<ZDTTableRowEntity> cd = compareInstancesData(new InstanceData<ZDTTableRowEntity>(key1, client1, tre1, rowNum1),
					new InstanceData<ZDTTableRowEntity>(key2, client2, tre2, rowNum2));
			logger.info("Completed to compare the two SSF OMC instances");
			return cd;
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return null;
		}
	}
	
	public int countForComparedRows(ZDTTableRowEntity tableRow) {
 		int count = 0;		
 		if (tableRow != null) {
 			count = count + (tableRow.getSavedSearchCategory()==null?0:tableRow.getSavedSearchCategory().size());
 	 		count = count + (tableRow.getSavedSearchCategoryParams()==null?0:tableRow.getSavedSearchCategoryParams().size());
 	 		count = count + (tableRow.getSavedSearchFoldersy()==null?0:tableRow.getSavedSearchFoldersy().size());
 	 		count = count + (tableRow.getSavedSearchSearchParams()==null?0:tableRow.getSavedSearchSearchParams().size());
 	 		count = count + (tableRow.getSavedSearchSearch()==null?0:tableRow.getSavedSearchSearch().size());
 		}
 		
 		return count;
 	}

	/**
	 * Compares the SSF category rows data for the 2 instances, and put the compare result into <code>ComparedData</code>
	 * object
	 *
	 * @param rows1
	 * @param rows2
	 * @param cd
	 */
	private void compareSSFCategoryRows(List<SavedSearchCategoryRowEntity> rows1, List<SavedSearchCategoryRowEntity> rows2,
			InstancesComparedData<ZDTTableRowEntity> cd)
	{
		if (cd == null) {
			return;
		}
		RowEntityComparator<SavedSearchCategoryRowEntity> rec = new RowEntityComparator<SavedSearchCategoryRowEntity>();
		CompareListPair<SavedSearchCategoryRowEntity> result = rec.compare(rows1, rows2);
		cd.getInstance1().getData().setSavedSearchCategory(result.getList1());
		cd.getInstance2().getData().setSavedSearchCategory(result.getList2());
	}

	
	/**
	 * Compares the saved search rows data for the 2 instances, and put the compare result into <code>ComparedData</code> object
	 *
	 * @param rows1
	 * @param rows2
	 * @param cd
	 */
	private void compareSSFSearchRows(List<SavedSearchSearchRowEntity> rows1, List<SavedSearchSearchRowEntity> rows2,
			InstancesComparedData<ZDTTableRowEntity> cd)
	{
		if (cd == null) {
			return;
		}
		logger.info("rows1="+rows1.size());
		logger.info("rows2="+rows2.size());
		RowEntityComparator<SavedSearchSearchRowEntity> rec = new RowEntityComparator<SavedSearchSearchRowEntity>();
		CompareListPair<SavedSearchSearchRowEntity> result = rec.compare(rows1, rows2);
		cd.getInstance1().getData().setSavedSearchSearch(result.getList1());
		cd.getInstance2().getData().setSavedSearchSearch(result.getList2());
	}

	/**
	 * Compares the saved search category param rows data for the 2 instances, and put the compare result into <code>ComparedData</code> object
	 *
	 * @param rows1
	 * @param rows2
	 * @param cd
	 */
	private void compareSSFCategoryParamRows(List<SavedSearchCategoryParamRowEntity> rows1, List<SavedSearchCategoryParamRowEntity> rows2,
			InstancesComparedData<ZDTTableRowEntity> cd)
	{
		if (cd == null) {
			return;
		}
		RowEntityComparator<SavedSearchCategoryParamRowEntity> rec = new RowEntityComparator<SavedSearchCategoryParamRowEntity>();
		CompareListPair<SavedSearchCategoryParamRowEntity> result = rec.compare(rows1, rows2);
		cd.getInstance1().getData().setSavedSearchCategoryParams(result.getList1());
		cd.getInstance2().getData().setSavedSearchCategoryParams(result.getList2());
	}

	/**
	 * Compares the saved search search param rows data for the 2 instances, and put the compare result into <code>ComparedData</code>
	 * object
	 *
	 * @param rows1
	 * @param rows2
	 * @param cd
	 */
	private void compareSSFSearchParamsRows(List<SavedSearchSearchParamRowEntity> rows1,
			List<SavedSearchSearchParamRowEntity> rows2, InstancesComparedData<ZDTTableRowEntity> cd)
	{
		if (cd == null) {
			return;
		}
		RowEntityComparator<SavedSearchSearchParamRowEntity> rec = new RowEntityComparator<SavedSearchSearchParamRowEntity>();
		CompareListPair<SavedSearchSearchParamRowEntity> result = rec.compare(rows1, rows2);
		cd.getInstance1().getData().setSavedSearchSearchParams(result.getList1());
		cd.getInstance2().getData().setSavedSearchSearchParams(result.getList2());
	}

	/**
	 * Compares the SSF folder rows data for the 2 instances, and put the compare result into <code>ComparedData</code> object
	 *
	 * @param rows1
	 * @param rows2
	 * @param cd
	 */
	private void compareSSFFolderRows(List<SavedSearchFolderRowEntity> rows1, List<SavedSearchFolderRowEntity> rows2,
			InstancesComparedData<ZDTTableRowEntity> cd)
	{
		if (cd == null) {
			return;
		}
		RowEntityComparator<SavedSearchFolderRowEntity> rec = new RowEntityComparator<SavedSearchFolderRowEntity>();
		CompareListPair<SavedSearchFolderRowEntity> result = rec.compare(rows1, rows2);
		cd.getInstance1().getData().setSavedSearchFoldersy(result.getList1());
		cd.getInstance2().getData().setSavedSearchFoldersy(result.getList2());
	}

	

	private InstancesComparedData<ZDTTableRowEntity> compareInstancesData(InstanceData<ZDTTableRowEntity> insData1,
			InstanceData<ZDTTableRowEntity> insData2)
	{
		if (insData1 == null || insData2 == null) {
			logger.error("Input instance data is null, can't compare and return null directly");
			return null;
		}
		// prepare the output compared data
		InstanceData<ZDTTableRowEntity> outData1 = new InstanceData<ZDTTableRowEntity>(insData1.getKey(),insData1.getClient(), new ZDTTableRowEntity(),
				insData1.getTotalRowNum());
		InstanceData<ZDTTableRowEntity> outData2 = new InstanceData<ZDTTableRowEntity>(insData2.getKey(),insData2.getClient(), new ZDTTableRowEntity(),
				insData2.getTotalRowNum());
		InstancesComparedData<ZDTTableRowEntity> cd = new InstancesComparedData<ZDTTableRowEntity>(outData1, outData2);
		compareSSFCategoryRows(insData1.getData().getSavedSearchCategory(), insData2.getData().getSavedSearchCategory(), cd);
		compareSSFCategoryParamRows(insData1.getData().getSavedSearchCategoryParams(), insData2.getData().getSavedSearchCategoryParams(),
				cd);
		compareSSFFolderRows(insData1.getData().getSavedSearchFoldersy(),
				insData2.getData().getSavedSearchFoldersy(), cd);
		compareSSFSearchParamsRows(insData1.getData().getSavedSearchSearchParams(),
				insData2.getData().getSavedSearchSearchParams(), cd);
		compareSSFSearchRows(insData1.getData().getSavedSearchSearch(),
				insData2.getData().getSavedSearchSearch(), cd);
		
		return cd;
	}

	private CountsEntity retrieveCountsForSingleInstance(String tenantId, String userTenant,LookupClient lc, String maxComparedTime) throws Exception, IOException
	{
		Link lk = getSingleInstanceUrl(lc, "zdt/counts", "http");
		if (lk == null) {
			logger.warn("Get a null or empty link for one single instance!");
			return null;
		}
		logger.info("*****lookup link is {}", lk.getHref());
		String url = lk.getHref() + "?maxComparedDate="+URLEncoder.encode(maxComparedTime, "UTF-8");
		String response = new TenantSubscriptionUtil.RestClient().get(url, tenantId, userTenant);
		logger.info("Checking savedsearch OMC instance counts. Response is " + response);
		JsonUtil ju = JsonUtil.buildNormalMapper();
		CountsEntity ze = ju.fromJson(response, CountsEntity.class);
		if (ze == null) {
			logger.warn("Checking savedsearch OMC instance counts: null/empty entity retrieved.");
			return null;
		}
		// TODO: for the 1st step implementation, let's log in log files then
		logger.info(
				"Retrieved counts for Category OMC instance: savedsearch count - {}, Folders count - {}, Search count - {}",
				ze.getCountOfCategory(), ze.getCountOfFolders(), ze.getCountOfSearch());
		return ze;
	}

	/**
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ZDTException 
	 */
	private ZDTTableRowEntity retrieveRowsEntityFromJsonForSingleInstance(String response) throws IOException, ZDTException
	{
		JsonUtil ju = JsonUtil.buildNormalMapper();
		ZDTTableRowEntity tre = ju.fromJson(response, ZDTTableRowEntity.class);
		if (tre == null) {
			logger.warn("Checking SSF OMC instance table rows: null/empty entity retrieved.");
			throw new ZDTException(ZDTErrorConstants.NULL_LINK_ERROR_CODE, ZDTErrorConstants.NULL_LINK_ERROR_MESSAGE);
		}
		return tre;
	}

	/**
	 * @throws Exception
	 * @throws IOException
	 */
	private ZDTTableRowEntity retrieveRowsForSingleInstance(String tenantId, String userTenant,LookupClient lc, String comparisonType,
			String maxComparedDate, String tenant) throws Exception, IOException, ZDTException
	{
		Link lk = getSingleInstanceUrl(lc, "zdt/tablerows", "http");
		if (lk == null) {
			logger.warn("Get a null or empty link for one single instance!");
			throw new ZDTException(ZDTErrorConstants.NULL_TABLE_ROWS_ERROR_CODE, ZDTErrorConstants.NULL_TABLE_ROWS_ERROR_MESSAGE);
		}
		String url = null;
		if (tenant != null) {
			url = lk.getHref() + "?comparisonType="+comparisonType+"&maxComparedDate="+URLEncoder.encode(maxComparedDate, "UTF-8")+"&tenant="+tenant;
		} else {
			url = lk.getHref() + "?comparisonType="+comparisonType+"&maxComparedDate="+URLEncoder.encode(maxComparedDate, "UTF-8");
		}	
		logger.info("get table data url is "+url);
		String response = new TenantSubscriptionUtil.RestClient().get(url, tenantId,userTenant);
		logger.info("Checking SSF OMC instance table rows. Response is " + response);
		return retrieveRowsEntityFromJsonForSingleInstance(response);
	}
	
	public String retrieveComparatorStatusForOmcInstance(String tenantId, String userTenant) throws Exception {
		Link lk = getSingleInstanceUrl(client1, "zdt/compare/status", "http");
		if (lk == null) {
			logger.warn("Get a null or empty link for one single instance!");
			throw new ZDTException(ZDTErrorConstants.NULL_LINK_ERROR_CODE, ZDTErrorConstants.NULL_LINK_ERROR_MESSAGE);
		}
		String response =  new TenantSubscriptionUtil.RestClient().get(lk.getHref(), tenantId,userTenant);
		logger.info("checking comparator status is " + response);
		
		return response;
	}
	
	public String retrieveSyncStatusForOmcInstance(String tenantId, String userTenant) throws Exception {
		Link lk = getSingleInstanceUrl(client1, "zdt/sync/status", "http");
		if (lk == null) {
			logger.warn("Get a null or empty link for one single instance!");
			throw new ZDTException(ZDTErrorConstants.NULL_LINK_ERROR_CODE, ZDTErrorConstants.NULL_LINK_ERROR_MESSAGE);
		}
		String response =  new TenantSubscriptionUtil.RestClient().get(lk.getHref(), tenantId,userTenant);
		logger.info("checking sync status is " + response);
		
		return response;
	}
	
	public String retrieveTenants(String tenantId, String userTenant, LookupClient client) throws Exception {
		Link lk = getSingleInstanceUrl(client, "zdt/tenants", "http");
		if (lk == null) {
			logger.warn("get a null or empty link for omc instance!");
			throw new ZDTException(ZDTErrorConstants.NULL_LINK_ERROR_CODE, ZDTErrorConstants.NULL_LINK_ERROR_MESSAGE);
		}
		String response = new TenantSubscriptionUtil.RestClient().get(lk.getHref(), tenantId, userTenant);
		logger.info("checking tenants list " + response);
		return response;
	}
	
	public String saveComparatorStatus(String tenantId, String userTenant, LookupClient client, ZDTComparatorStatusRowEntity statusRowEntity) throws Exception{
		Link lk = getSingleInstanceUrl(client, "zdt/compare/result", "http");
		if (lk == null) {
			logger.warn("Get a null or empty link for one single instance!");
			throw new ZDTException(ZDTErrorConstants.NULL_LINK_ERROR_CODE, ZDTErrorConstants.NULL_LINK_ERROR_MESSAGE);
		}
		JsonUtil jsonUtil = JsonUtil.buildNonNullMapper();
		String entityStr = jsonUtil.toJson(statusRowEntity);
 		logger.info("print the put data {} !",entityStr);
 		String response = new TenantSubscriptionUtil.RestClient().put(lk.getHref(), entityStr, tenantId, userTenant);
 		logger.info("Checking saving comparator status. Response is " + response);
		return response;
	}

	
	public String syncForInstance(String tenantId, String userTenant, LookupClient client, String type, String syncDate) throws Exception {
		Link lk = getSingleInstanceUrl(client, "zdt/sync", "http");
		if (lk == null) {
			logger.warn("Get a null or empty link for one single instance!");
			return "Errors:Get a null or empty link for one single instance!";
		}
		String url = lk.getHref() + "?syncType=" + type + "&syncDate=" + URLEncoder.encode(syncDate, "UTF-8");
		logger.info("sync url is "+ url);
		String response = new TenantSubscriptionUtil.RestClient().get(url,  tenantId, userTenant);
 		logger.info("Checking sync reponse. Response is " + response);
		return response;
	} 
/*
	private String syncForInstance(String tenantId, String userTenant,InstanceData<ZDTTableRowEntity> instance) throws Exception
	{
		Link lk = getSingleInstanceUrl(instance.getClient(), "zdt/sync", "http");
		if (lk == null) {
			logger.warn("Get a null or empty link for one single instance!");
			return "Errors:Get a null or empty link for one single instance!";
		}
		ZDTTableRowEntity entity = instance.getData();
 		JsonUtil jsonUtil = JsonUtil.buildNonNullMapper();
 		jsonUtil.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
 		String entityStr = jsonUtil.toJson(entity);
 		logger.info("print the put data {} !",entityStr);
 		String response = new TenantSubscriptionUtil.RestClient().put(lk.getHref(), entityStr, tenantId, userTenant);
 		logger.info("Checking sync reponse. Response is " + response);
		return response;
	}
	*/

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public LookupClient getClient1() {
		return client1;
	}

	public void setClient1(LookupClient client1) {
		this.client1 = client1;
	}

	public LookupClient getClient2() {
		return client2;
	}

	public void setClient2(LookupClient client2) {
		this.client2 = client2;
	}
	
	
}
