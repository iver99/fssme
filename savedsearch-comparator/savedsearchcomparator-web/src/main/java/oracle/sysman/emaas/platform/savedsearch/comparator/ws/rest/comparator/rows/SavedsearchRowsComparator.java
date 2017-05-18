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
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emaas.platform.savedsearch.comparator.exception.ZDTErrorConstants;
import oracle.sysman.emaas.platform.savedsearch.comparator.exception.ZDTException;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.JsonUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.TenantSubscriptionUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.AbstractComparator;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.RowEntityComparator.CompareListPair;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities.*;


public class SavedsearchRowsComparator extends AbstractComparator
{
	private static final Logger logger = LogManager.getLogger(SavedsearchRowsComparator.class);

	public InstancesComparedData<ZDTTableRowEntity> compare(String tenantId, String userTenant) throws ZDTException
	{
		try {
			logger.info("Starts to compare the two ssf OMC instances: table by table and row by row");
			HashMap<String, LookupClient> instances = getOMCInstances();
			if (instances == null) {
				logger.error("Failed to retrieve ZDT OMC instances: null retrieved");
				throw new ZDTException(ZDTErrorConstants.NULL_RETRIEVED_ERROR_CODE,ZDTErrorConstants.NULL_RETRIEVED_ERROR_MESSAGE);
			}
			 
			String key1 = null;
 			String key2 = null;
 			LookupClient client1 = null;
 			LookupClient client2 = null;
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
 			
 			ZDTTableRowEntity tre1 = retrieveRowsForSingleInstance(tenantId, userTenant,client1);
 			int rowNum1 = countForComparedRows(tre1);
			if (tre1 == null) {
				logger.error("Failed to retrieve ZDT table rows entity for instance {}", key1);
				logger.info("Completed to compare the two ssf OMC instances");
				return null;
			}

			ZDTTableRowEntity tre2 = retrieveRowsForSingleInstance(tenantId, userTenant,client2);
			int rowNum2 = countForComparedRows(tre2);
			if (tre2 == null) {
				logger.error("Failed to retrieve ZDT table rows entity for instance {}", key2);
				logger.info("Completed to compare the two SSF OMC instances");
				return null;
			}
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

	public String sync(InstancesComparedData<ZDTTableRowEntity> instancesData,String tenantId, String userTenant) throws Exception
	{
		if (instancesData == null) {
			return "Errors: Failed to retrieve ZDT OMC instances: null retrieved!";
		}
		// switch the data for the instances for sync
		InstanceData<ZDTTableRowEntity> instance1 = new InstanceData<ZDTTableRowEntity>(instancesData.getInstance1().getKey(),
				instancesData.getInstance1().getClient(),
				instancesData.getInstance2().getData(),
				0);
		InstanceData<ZDTTableRowEntity> instance2 = new InstanceData<ZDTTableRowEntity>(instancesData.getInstance2().getKey(),
				instancesData.getInstance2().getClient(),
				instancesData.getInstance1().getData(),
				0);
		InstancesComparedData<ZDTTableRowEntity> syncData = new InstancesComparedData<ZDTTableRowEntity>(instance1, instance2);
		
		String message1 = null;
		String message2 = null;
		if (hasSyncData(syncData.getInstance1().getData())) {
			logger.info("instance1 has data {}",syncData.getInstance1().getData().toString());
			message1 = syncForInstance( tenantId, userTenant,syncData.getInstance1());
		}
		if (hasSyncData(syncData.getInstance2().getData())) {
			logger.info("instance2 has data {}",syncData.getInstance2().getData().toString());
			message2 = syncForInstance(tenantId, userTenant,syncData.getInstance2());
		}
		
 		return syncData.getInstance1().getKey() + ":{"+ (message1==null?"sync is successful":message1) + "}" 
 		+ "____"+syncData.getInstance2().getKey()+":{" + (message2==null?"sync is successful":message2)+"}";
	
	}
	
	private boolean hasSyncData(ZDTTableRowEntity entity) {
		if (entity != null) {
			if (entity.getSavedSearchCategory() != null && !entity.getSavedSearchCategory().isEmpty()) return true;
			if (entity.getSavedSearchFoldersy() != null && !entity.getSavedSearchFoldersy().isEmpty()) return true;
			if (entity.getSavedSearchCategoryParams() != null && !entity.getSavedSearchCategoryParams().isEmpty()) return true;
			if (entity.getSavedSearchSearch() != null && !entity.getSavedSearchSearch().isEmpty()) return true;
			if (entity.getSavedSearchSearchParams() != null && !entity.getSavedSearchSearchParams().isEmpty()) return true;
			
		}
		return false;
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
	private ZDTTableRowEntity retrieveRowsForSingleInstance(String tenantId, String userTenant,LookupClient lc) throws Exception, IOException, ZDTException
	{
		Link lk = getSingleInstanceUrl(lc, "zdt/tablerows", "http");
		if (lk == null) {
			logger.warn("Get a null or empty link for one single instance!");
			throw new ZDTException(ZDTErrorConstants.NULL_TABLE_ROWS_ERROR_CODE, ZDTErrorConstants.NULL_TABLE_ROWS_ERROR_MESSAGE);
		}
		String response = new TenantSubscriptionUtil.RestClient().get(lk.getHref(), tenantId,userTenant);
		logger.info("Checking SSF OMC instance table rows. Response is " + response);
		return retrieveRowsEntityFromJsonForSingleInstance(response);
	}

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
}
