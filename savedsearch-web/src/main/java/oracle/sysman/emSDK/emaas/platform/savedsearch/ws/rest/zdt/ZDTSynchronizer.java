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

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchFolderRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchParamRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.ZDTTableRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.DataManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.exception.SyncException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;

public class ZDTSynchronizer
{
	private static final Logger logger = LogManager.getLogger(ZDTSynchronizer.class);

	public String sync(ZDTTableRowEntity data) throws  SyncException
	{
		logger.info("begin to sync for table row entity");
		if (data == null) {
			logger.error("Failed to sync as input data is null");
			throw new SyncException("Errors occurred while sync for tables, input data is null...");
		}
		EntityManager em = null;
		try{
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			//now category and category params table will not be compared/synced
			//make sure sync in order that will not break DB constraints
//			syncCategoryTableRows(em, data.getSavedSearchCategory());
//			syncCategoryParamsTableRows(em, data.getSavedSearchCategoryParams());
			syncFoldersTableRows(em, data.getSavedSearchFoldersy());
			syncSearchTableRows(em, data.getSavedSearchSearch());
			syncSearchParamsTableRows(em, data.getSavedSearchSearchParams());
			em.getTransaction().commit();
			return "sync is successful";
		}catch (SyncException e){
			if(em!=null){
				em.getTransaction().rollback();
			}
			logger.error("SyncException occurred while sync for tables...");
			logger.error(e);
			throw e;
		}catch (Exception e) {
			if(em!=null){
				em.getTransaction().rollback();
			}
			logger.error("Exception occurred while sync for tables...");
			logger.error(e);
			logger.error(e.getCause());
			throw new SyncException("Errors occurred while sync for tables...");
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	private void syncFoldersTableRows(EntityManager em, List<SavedSearchFolderRowEntity> rows) throws SyncException
	{
		logger.info("Prepare to sync folder table");
		if (rows == null) {
			logger.info("List<SavedSearchFolderRowEntity> is null,no sync action is needed");
			return;
		}
		logger.info("Begin to sync table EMS_ANALYTICS_FOLDER table");
		for (SavedSearchFolderRowEntity e : rows) {
			
			DataManager.getInstance().syncFolderTable(em, e.getFolderId() != null? new BigInteger(e.getFolderId()) : null, e.getName(), e.getParentId()!=null?new BigInteger(e.getParentId()):null, e.getDescription(),
					e.getCreationDate(), e.getOwner(), e.getLastModificationDate(), e.getLastModifiedBy(), e.getNameNlsid(),
					e.getNameSubsystem(), e.getDescriptionNlsid(), e.getDescriptionSubsystem(), e.getSystemFolder(),
					e.getEmPluginId(), e.getUiHidden(), e.getDeleted()!=null? new BigInteger(e.getDeleted()): null, e.getTenantId());
		}
		logger.info("Finished to sync table EMS_ANALYTICS_FOLDERS table");
	}

	private void syncSearchParamsTableRows(EntityManager em, List<SavedSearchSearchParamRowEntity> rows)throws SyncException
	{
		logger.info("Prepare to sync search param table");
		if (rows == null) {
			logger.info("List<SavedSearchSearchParamRowEntity> is null,no sync action is needed");
			return;
		}
		logger.info("Begin to sync table EMS_ANALYTICS_SEARCH_PARAMS table");
		for (SavedSearchSearchParamRowEntity e : rows) {
			
			DataManager.getInstance().syncSearchParamsTable(em,e.getSearchId() == null? null:new BigInteger(e.getSearchId()), e.getName(), e.getParamAttributes(),
					e.getParamType(), e.getParamValueStr(), e.getParamValueClob(), e.getTenantId(), e.getCreationDate(),
					e.getLastModificationDate(),e.getDeleted());
		}
		logger.info("Finished to sync table EMS_ANALYTICS__SEARCH_PARAMS table");
	}

	private void syncSearchTableRows(EntityManager em, List<SavedSearchSearchRowEntity> rows) throws SyncException {
		logger.info("Prepare to sync search table");
		if (rows == null) {
			logger.info("List<SavedSearchSearchRowEntity> is null,no sync action is needed");
			return;
		}
		logger.info("Begin to sync table EMS_ANALYTICS_SEARCH table");
		int i = 0;
		for (SavedSearchSearchRowEntity e : rows) {
			DataManager.getInstance().syncSearchTable(em, e.getSearchId() == null ? null : new BigInteger(e.getSearchId())/*, e.getSearchGuid()*/, e.getName(), e.getOwner(),
					e.getCreationDate(), e.getLastModificationDate(), e.getLastModifiedBy(), e.getDescription(), e.getFolderId() == null ? null : new BigInteger(e.getFolderId()),
					e.getCategoryId() == null ? null : new BigInteger(e.getCategoryId()), e.getSystemSearch(), e.getIsLocked(), e.getMetadataClob(),
					e.getSearchDisplayStr(), e.getUiHidden(), e.getDeleted() == null ? null : new BigInteger(e.getDeleted()), e.getIsWidget(), e.getTenantId(),
					e.getNameWidgetSource(), e.getWidgetGroupName(), e.getWidgetScreenshotHref(), e.getWidgetIcon(),
					e.getWidgetKocName(), e.getWidgetViewModel(), e.getWidgetTemplate(), e.getWidgetSupportTimeControl(),
					e.getWidgetLinkedDashboard(), e.getWidgetDefaulWidth(), e.getWidgetDefaultHeight(),
					e.getDashboardIneligible(), e.getProviderName(), e.getProviderVersion(), e.getProviderAssetRoot());

		}
		logger.info("Finished to sync table EMS_ANALYTICS_SEARCH table");
	}
}
