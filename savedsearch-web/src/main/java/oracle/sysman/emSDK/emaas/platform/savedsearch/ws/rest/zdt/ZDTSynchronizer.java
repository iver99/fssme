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

import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchCategoryParamRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchCategoryRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchFolderRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchLastAccessRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSchemaVerRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchParamRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.ZDTTableRowEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZDTSynchronizer
{
	private static final Logger logger = LogManager.getLogger(ZDTSynchronizer.class);

	public void sync(ZDTTableRowEntity data)
	{
		if (data == null) {
			logger.error("Failed to sync as input data is null");
			return;
		}
		syncCategoryTableRows(data.getSavedSearchCategory());
		syncCategoryParamsTableRows(data.getSavedSearchCategoryParams());
		syncFoldersTableRows(data.getSavedSearchFoldersy());
		syncLastAccessTableRows(data.getSavedSearchLastAccess());
		syncSchemaVerTableRows(data.getSavedSearchSchemaVer());
		syncSearchParamsTableRows(data.getSavedSearchSearchParams());
		syncSearchTableRows(data.getSavedSearchSearch());
	}

	private void syncCategoryParamsTableRows(List<SavedSearchCategoryParamRowEntity> rows)
	{
		if (rows == null) {
			return;
		}
		// TODO: call DataManager implementation to insert or update data to database
	}

	private void syncCategoryTableRows(List<SavedSearchCategoryRowEntity> rows)
	{
		if (rows == null) {
			return;
		}
		// TODO: call DataManager implementation to insert or update data to database
	}

	private void syncFoldersTableRows(List<SavedSearchFolderRowEntity> rows)
	{
		if (rows == null) {
			return;
		}
		// TODO: call DataManager implementation to insert or update data to database
	}

	private void syncLastAccessTableRows(List<SavedSearchLastAccessRowEntity> dashboardRows)
	{
		if (dashboardRows == null) {
			return;
		}
		// TODO: call DataManager implementation to insert or update data to database
	}

	private void syncSchemaVerTableRows(List<SavedSearchSchemaVerRowEntity> rows)
	{
		if (rows == null) {
			return;
		}
		// TODO: call DataManager implementation to insert or update data to database
	}

	private void syncSearchParamsTableRows(List<SavedSearchSearchParamRowEntity> rows)
	{
		if (rows == null) {
			return;
		}
		// TODO: call DataManager implementation to insert or update data to database
	}

	private void syncSearchTableRows(List<SavedSearchSearchRowEntity> rows)
	{
		if (rows == null) {
			return;
		}
		// TODO: call DataManager implementation to insert or update data to database
	}

}
