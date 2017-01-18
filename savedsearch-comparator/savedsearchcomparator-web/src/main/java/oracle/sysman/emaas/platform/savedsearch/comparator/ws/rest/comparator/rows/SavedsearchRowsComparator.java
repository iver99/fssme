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
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.JsonUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.TenantSubscriptionUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.AbstractComparator;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.RowEntityComparator.CompareListPair;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities.*;


public class SavedsearchRowsComparator extends AbstractComparator
{
	private static final Logger logger = LogManager.getLogger(SavedsearchRowsComparator.class);

	public InstancesComparedData<ZDTTableRowEntity> compare()
	{
		try {
			logger.info("Starts to compare the two ssf OMC instances: table by table and row by row");
			Entry<String, LookupClient>[] instances = getOMCInstances();
			if (instances == null) {
				logger.error("Failed to retrieve ZDT OMC instances: null retrieved");
				return null;
			}
			Entry<String, LookupClient> ins1 = instances[0];
			ZDTTableRowEntity tre1 = retrieveRowsForSingleInstance(ins1.getValue());
			if (tre1 == null) {
				logger.error("Failed to retrieve ZDT table rows entity for instance {}", ins1.getKey());
				logger.info("Completed to compare the two ssf OMC instances");
				return null;
			}

			Entry<String, LookupClient> ins2 = instances[1];
			ZDTTableRowEntity tre2 = retrieveRowsForSingleInstance(ins2.getValue());
			if (tre2 == null) {
				logger.error("Failed to retrieve ZDT table rows entity for instance {}", ins2.getKey());
				logger.info("Completed to compare the two SSF OMC instances");
				return null;
			}
			InstancesComparedData<ZDTTableRowEntity> cd = compareInstancesData(new InstanceData<ZDTTableRowEntity>(ins1, tre1),
					new InstanceData<ZDTTableRowEntity>(ins2, tre2));
			logger.info("Completed to compare the two SSF OMC instances");
			return cd;
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return null;
		}
	}

	public void sync(InstancesComparedData<ZDTTableRowEntity> instancesData) throws Exception
	{
		if (instancesData == null) {
			return;
		}
		// switch the data for the instances for sync
		InstanceData<ZDTTableRowEntity> instance1 = new InstanceData<ZDTTableRowEntity>(instancesData.getInstance1().getInstance(),
				instancesData.getInstance2().getData());
		InstanceData<ZDTTableRowEntity> instance2 = new InstanceData<ZDTTableRowEntity>(instancesData.getInstance2().getInstance(),
				instancesData.getInstance1().getData());
		InstancesComparedData<ZDTTableRowEntity> syncData = new InstancesComparedData<ZDTTableRowEntity>(instance1, instance2);
		syncForInstance(syncData.getInstance1());
		syncForInstance(syncData.getInstance2());
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
		InstanceData<ZDTTableRowEntity> outData1 = new InstanceData<ZDTTableRowEntity>(insData1.getInstance(), new ZDTTableRowEntity());
		InstanceData<ZDTTableRowEntity> outData2 = new InstanceData<ZDTTableRowEntity>(insData2.getInstance(), new ZDTTableRowEntity());
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
	 */
	private ZDTTableRowEntity retrieveRowsEntityFromJsonForSingleInstance(String response) throws IOException
	{
		JsonUtil ju = JsonUtil.buildNormalMapper();
		ZDTTableRowEntity tre = ju.fromJson(response, ZDTTableRowEntity.class);
		if (tre == null) {
			logger.warn("Checking SSF OMC instance table rows: null/empty entity retrieved.");
			return null;
		}
		return tre;
	}

	/**
	 * @throws Exception
	 * @throws IOException
	 */
	private ZDTTableRowEntity retrieveRowsForSingleInstance(LookupClient lc) throws Exception, IOException
	{
		Link lk = getSingleInstanceUrl(lc, "zdt/tablerows", "http");
		if (lk == null) {
			logger.warn("Get a null or empty link for one single instance!");
			return null;
		}
		String response = new TenantSubscriptionUtil.RestClient().get(lk.getHref(), null);
		logger.info("Checking SSF OMC instance table rows. Response is " + response);
		return retrieveRowsEntityFromJsonForSingleInstance(response);
	}

	private String syncForInstance(InstanceData<ZDTTableRowEntity> instance) throws Exception
	{
		Link lk = getSingleInstanceUrl(instance.getInstance().getValue(), "zdt/sync", "http");
		if (lk == null) {
			logger.warn("Get a null or empty link for one single instance!");
			return null;
		}
		String response = new TenantSubscriptionUtil.RestClient().put(lk.getHref(), instance.getData(), null);
		logger.info("Checking SSF OMC instance table rows. Response is " + response);
		return response;
	}
}
