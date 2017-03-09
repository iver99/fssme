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

import java.math.BigInteger;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchCategoryParamRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchCategoryRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchFolderRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchParamRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.ZDTTableRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.DataManager;

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
		syncSearchTableRows(data.getSavedSearchSearch());
		syncSearchParamsTableRows(data.getSavedSearchSearchParams());
	}

	private void syncCategoryParamsTableRows(List<SavedSearchCategoryParamRowEntity> rows)
	{
		// TODO: call DataManager implementation to insert or update data to database
		if (rows == null) {
			logger.debug("List<SavedSearchCategoryParamRowEntity> is null,no sync action is needed");
			return;
		}
		logger.debug("Begin to sync table EMS_ANALYTICS_CATEGORY_PARAMS table");
		for (SavedSearchCategoryParamRowEntity e : rows) {
			DataManager.getInstance().syncCategoryParamTable(e.getCategoryId()!=null?new BigInteger(e.getCategoryId()):null, e.getName(), e.getValue(), e.getTenantId(),
					e.getCreationDate(), e.getLastModificationDate(),e.getDeleted());
		}
		logger.debug("Finished to sync table EMS_ANALYTICS_CATEGORY_PARAMS table");
	}

	private void syncCategoryTableRows(List<SavedSearchCategoryRowEntity> rows)
	{
		// TODO: call DataManager implementation to insert or update data to database
		if (rows == null) {
			logger.debug("List<SavedSearchCategoryRowEntity> is null,no sync action is needed");
			return;
		}
		logger.debug("Begin to sync table EMS_ANALYTICS_CATEGORY table");
		for (SavedSearchCategoryRowEntity e : rows) {
			DataManager.getInstance().syncCategoryTable(e.getCategoryId() !=null? new BigInteger(e.getCategoryId()): null, e.getName(), e.getDescription(), e.getOwner(),
					e.getCreationDate(), e.getNameNlsid(), e.getNameSubsystem(), e.getDescriptionNlsid(),
					e.getDescriptionSubsystem(), e.getEmPluginId(), e.getDefaultFolderId() !=null? new BigInteger(e.getDefaultFolderId()):null, 
							e.getDeleted()!=null? new BigInteger(e.getDeleted()):null, e.getProviderName(),
					e.getProviderVersion(), e.getProviderDiscovery(), e.getProviderAssetRoot(), e.getTenantId(),
					e.getDashboardIneligible(), e.getLastModificationDate());
		}
		logger.debug("Finished to sync table EMS_ANALYTICS_CATEGORY table");

	}

	private void syncFoldersTableRows(List<SavedSearchFolderRowEntity> rows)
	{
		// TODO: call DataManager implementation to insert or update data to database
		if (rows == null) {
			logger.debug("List<SavedSearchFolderRowEntity> is null,no sync action is needed");
			return;
		}
		logger.debug("Begin to sync table EMS_ANALYTICS_CATEGORY table");
		for (SavedSearchFolderRowEntity e : rows) {
			DataManager.getInstance().syncFolderTable(e.getFolderId() != null? new BigInteger(e.getFolderId()) : null, e.getName(), e.getParentId()!=null?new BigInteger(e.getParentId()):null, e.getDescription(),
					e.getCreationDate(), e.getOwner(), e.getLastModificationDate(), e.getLastModifiedBy(), e.getNameNlsid(),
					e.getNameSubsystem(), e.getDescriptionNlsid(), e.getDescriptionSubsystem(), e.getSystemFolder(),
					e.getEmPluginId(), e.getUiHidden(), e.getDeleted()!=null? new BigInteger(e.getDeleted()): null, e.getTenantId());
		}
		logger.debug("Finished to sync table EMS_ANALYTICS_FOLDERS table");
	}

	private void syncSearchParamsTableRows(List<SavedSearchSearchParamRowEntity> rows)
	{
		// TODO: call DataManager implementation to insert or update data to database
		if (rows == null) {
			logger.debug("List<SavedSearchSearchParamRowEntity> is null,no sync action is needed");
			return;
		}
		logger.debug("Begin to sync table EMS_ANALYTICS_SEARCH_PARAMS table");
		for (SavedSearchSearchParamRowEntity e : rows) {
			DataManager.getInstance().syncSearchParamsTable(e.getSearchId() == null? null:new BigInteger(e.getSearchId()), e.getName(), e.getParamAttributes(),
					e.getParamType(), e.getParamValueStr(), e.getParamValueClob(), e.getTenantId(), e.getCreationDate(),
					e.getLastModificationDate(),e.getDeleted());
		}
		logger.debug("Finished to sync table EMS_ANALYTICS__SEARCH_PARAMS table");
	}

	private void syncSearchTableRows(List<SavedSearchSearchRowEntity> rows)
	{
		// TODO: call DataManager implementation to insert or update data to database
		if (rows == null) {
			logger.debug("List<SavedSearchSearchRowEntity> is null,no sync action is needed");
			return;
		}
		logger.debug("Begin to sync table EMS_ANALYTICS_SEARCH table");
		for (SavedSearchSearchRowEntity e : rows) {
			DataManager.getInstance().syncSearchTable(e.getSearchId() == null? null:new BigInteger(e.getSearchId())/*, e.getSearchGuid()*/, e.getName(), e.getOwner(),
					e.getCreationDate(), e.getLastModificationDate(), e.getLastModifiedBy(), e.getDescription(), e.getFolderId() == null? null:new BigInteger(e.getFolderId()),
					e.getCategoryId() == null? null:new BigInteger(e.getCategoryId()), e.getSystemSearch(), e.getIsLocked(), e.getMetadataClob(),
					e.getSearchDisplayStr(), e.getUiHidden(), e.getDeleted() == null? null:new BigInteger(e.getDeleted()), e.getIsWidget(), e.getTenantId(),
					e.getNameWidgetSource(), e.getWidgetGroupName(), e.getWidgetScreenshotHref(), e.getWidgetIcon(),
					e.getWidgetKocName(), e.getWidgetViewModel(), e.getWidgetTemplate(), e.getWidgetSupportTimeControl(),
					e.getWidgetLinkedDashboard(), e.getWidgetDefaulWidth(), e.getWidgetDefaultHeight(),
					e.getDashboardIneligible(), e.getProviderName(), e.getProviderVersion(), e.getProviderAssetRoot());

		}
		logger.debug("Finished to sync table EMS_ANALYTICS__SEARCH table");
	}
}
