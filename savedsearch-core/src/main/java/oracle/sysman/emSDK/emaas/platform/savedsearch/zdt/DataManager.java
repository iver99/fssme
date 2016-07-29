/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.zdt;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.utils.SyncSavedSearchSqlUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;

/**
 * @author pingwu
 */
public class DataManager
{
	private static final Logger logger = LogManager.getLogger(DataManager.class);
	private static DataManager instance = new DataManager();

	private static final String SQL_ALL_CATEGORY_COUNT = "SELECT COUNT(*) FROM EMS_ANALYTICS_CATEGORY WHERE DELETED <> 1";
	private static final String SQL_ALL_FOLDER_COUNT = "SELECT COUNT(*) FROM EMS_ANALYTICS_FOLDERS WHERE DELETED <> 1";
	private static final String SQL_ALL_SEARCH_COUNT = "SELECT COUNT(*) FROM EMS_ANALYTICS_FOLDERS WHERE DELETED <> 1";

	private static final String SQL_ALL_CATEGORY_ROWS = "SELECT * FROM EMS_ANALYTICS_CATEGORY";
	private static final String SQL_ALL_FOLDER_ROWS = "SELECT * FROM EMS_ANALYTICS_FOLDERS";
	private static final String SQL_ALL_SEARCH_ROWS = "SELECT * FROM EMS_ANALYTICS_SEARCH";
	private static final String SQL_ALL_CATEGORY_PARAMS_ROWS = "SELECT * FROM EMS_ANALYTICS_CATEGORY_PARAMS";
	private static final String SQL_ALL_LAST_ACCESS_ROWS = "SELECT * FROM EMS_ANALYTICS_LAST_ACCESS";
	private static final String SQL_ALL_SCHEMA_VER_ROWS = "SELECT * FROM EMS_ANALYTICS_SCHEMA_VER_SSF";
	private static final String SQL_ALL_SEARCH_PARAMS_ROWS = "SELECT * FROM EMS_ANALYTICS_SEARCH_PARAMS";

	/**
	 * Return the singleton for data manager
	 *
	 * @return
	 */
	public static DataManager getInstance()
	{
		return instance;

	}

	/**
	 * Get the number of category rows
	 *
	 * @return
	 */

	public long getAllCategoryCount()
	{
		EntityManager em = null;
		em = getEntityManager();
		Query query = em.createNativeQuery(SQL_ALL_CATEGORY_COUNT);
		long count = ((Number) query.getSingleResult()).longValue();
		return count;

	}

	/**
	 * Get the number of folder rows
	 *
	 * @return
	 */

	public long getAllFolderCount()
	{
		EntityManager em = null;
		em = getEntityManager();
		Query query = em.createNativeQuery(SQL_ALL_FOLDER_COUNT);
		long count = ((Number) query.getSingleResult()).longValue();
		return count;
	}

	/**
	 * Get the number of search rows
	 *
	 * @return
	 */

	public long getAllSearchCount()
	{
		EntityManager em = null;
		em = getEntityManager();
		Query query = em.createNativeQuery(SQL_ALL_SEARCH_COUNT);
		long count = ((Number) query.getSingleResult()).longValue();
		return count;

	}

	/**
	 * Get all rows in category param table
	 *
	 * @return
	 */

	public List<Map<String, Object>> getCategoryParamTableData()
	{
		return getDatabaseTableData(SQL_ALL_CATEGORY_PARAMS_ROWS);
	}

	/**
	 * Get all rows in category table
	 *
	 * @return
	 */

	public List<Map<String, Object>> getCategoryTableData()
	{
		return getDatabaseTableData(SQL_ALL_CATEGORY_ROWS);
	}

	/**
	 * Get all rows in folder table
	 *
	 * @return
	 */
	public List<Map<String, Object>> getFolderTableData()
	{
		return getDatabaseTableData(SQL_ALL_FOLDER_ROWS);
	}

	/**
	 * Get all rows in last access table
	 *
	 * @return
	 */

	public List<Map<String, Object>> getLastAccessTableData()
	{
		return getDatabaseTableData(SQL_ALL_LAST_ACCESS_ROWS);
	}

	/**
	 * Get all rows in search version table
	 *
	 * @return
	 */

	public List<Map<String, Object>> getSchemaVerTableData()
	{
		return getDatabaseTableData(SQL_ALL_SCHEMA_VER_ROWS);
	}

	/**
	 * get all rows in search param table
	 *
	 * @return
	 */

	public List<Map<String, Object>> getSearchParamTableData()
	{
		return getDatabaseTableData(SQL_ALL_SEARCH_PARAMS_ROWS);
	}

	/**
	 * Get all rows in search table
	 *
	 * @return
	 */

	public List<Map<String, Object>> getSearchTableData()
	{
		return getDatabaseTableData(SQL_ALL_SEARCH_ROWS);
	}

	public void syncCategoryParamTable(Long categoryId, String name, String paramValue, Long tenantId, String creationDate,
			String lastModificationDate)
	{
		EntityManager em = null;
		em = getEntityManager();
		String sql = "select count(1) from EMS_ANALYTICS_CATEGORY_PARAMS t where t.CATEGORY_ID=? and t.TENANT_ID=? and t.NAME=?";//check if the data is existing.
		Query q1 = em.createNativeQuery(sql);
		q1.setParameter(0, categoryId);
		q1.setParameter(1, tenantId);
		q1.setParameter(2, name);
		Integer count = Integer.valueOf(q1.getSingleResult().toString());

		if (count <= 0) {
			//execute insert action
			logger.debug("Data not exist in table EMS_ANALYTICS_CATEGORY_PARAMS,execute insert action.");
			sql = SyncSavedSearchSqlUtil.concatCategoryParamInsert(categoryId, name, paramValue, tenantId, creationDate,
					lastModificationDate);
			logger.debug("Sync data in EMS_ANALYTICS_CATEGORY_PARAMS,execute insert SQL:[" + sql + "]");
		}
		else {
			//execute update action
			logger.debug("Data exist in table EMS_ANALYTICS_CATEGORY_PARAMS,execute update action.");
			sql = SyncSavedSearchSqlUtil.concatCategoryParamUpdate(categoryId, name, paramValue, tenantId, creationDate,
					lastModificationDate);
			logger.debug("Sync data in EMS_ANALYTICS_CATEGORY_PARAMS,execute update SQL:[" + sql + "]");
		}
		//sync Data
		syncDatabaseTableData(sql);

	}

	public void syncCategoryTable(Long categoryId, String name, String description, String owner, String creationDate,
			String nameNlsid, String nameSubSystem, String descriptionNlsid, String descriptionSubSystem, String emPluginId,
			Long defaultFolderId, Long deleted, String providerName, String providerVersion, String providerDiscovery,
			String providerAssetroot, Long tenantId, String dashboardIneligible, String lastModificationDate)
	{
		EntityManager em = null;
		em = getEntityManager();
		String sql = "select count(1) from EMS_ANALYTICS_CATEGORY t where t.CATEGORY_ID=? and t.TENANT_ID=?";//check if the data is existing.
		Query q1 = em.createNativeQuery(sql);
		q1.setParameter(0, categoryId);
		q1.setParameter(1, tenantId);
		Integer count = Integer.valueOf(q1.getSingleResult().toString());

		if (count <= 0) {
			//execute insert action
			logger.debug("Data not exist in table EMS_ANALYTICS_CATEGORY,execute insert action.");
			sql = SyncSavedSearchSqlUtil.concatCategoryInsert(categoryId, name, description, owner, creationDate, nameNlsid,
					nameSubSystem, descriptionNlsid, descriptionSubSystem, emPluginId, defaultFolderId, deleted, providerName,
					providerVersion, providerDiscovery, providerAssetroot, tenantId, dashboardIneligible, lastModificationDate);
			logger.debug("Sync data in EMS_ANALYTICS_CATEGORY,execute insert SQL:[" + sql + "]");
		}
		else {
			//execute update action
			logger.debug("Data exist in table EMS_ANALYTICS_CATEGORY,execute update action.");
			sql = SyncSavedSearchSqlUtil.concatCategoryUpdate(categoryId, name, description, owner, creationDate, nameNlsid,
					nameSubSystem, descriptionNlsid, descriptionSubSystem, emPluginId, defaultFolderId, deleted, providerName,
					providerVersion, providerDiscovery, providerAssetroot, tenantId, dashboardIneligible, lastModificationDate);
			logger.debug("Sync data in EMS_ANALYTICS_CATEGORY,execute update SQL:[" + sql + "]");
		}
		//sync Data
		syncDatabaseTableData(sql);

	}

	public void syncFolderTable(Long folderId, String name, Long parentId, String description, String creationDate, String owner,
			String lastModificationDate, String lastModifiedBy, String nameNlsid, String nameSubsystem, String descriptionNlsid,
			String descriptionSubsystem, Integer systemFolder, String emPluginId, Integer uiHidden, Long deleted, Long tenantId)
	{
		EntityManager em = null;
		em = getEntityManager();
		String sql = "select count(1) from EMS_ANALYTICS_FOLDERS t where t.FOLDER_ID=? and t.TENANT_ID=?";//check if the data is existing.
		Query q1 = em.createNativeQuery(sql);
		q1.setParameter(0, folderId);
		q1.setParameter(1, tenantId);
		Integer count = Integer.valueOf(q1.getSingleResult().toString());

		if (count <= 0) {
			//execute insert action
			logger.debug("Data not exist in table EMS_ANALYTICS_FOLDERS,execute insert action.");
			sql = SyncSavedSearchSqlUtil.concatFolderInsert(folderId, name, parentId, description, creationDate, owner,
					lastModificationDate, lastModifiedBy, nameNlsid, nameSubsystem, descriptionNlsid, descriptionSubsystem,
					systemFolder, emPluginId, uiHidden, deleted, tenantId);
			logger.debug("Sync data in EMS_ANALYTICS_FOLDERS,execute insert SQL:[" + sql + "]");
		}
		else {
			//execute update action
			logger.debug("Data exist in table EMS_ANALYTICS_FOLDERS,execute update action.");
			sql = SyncSavedSearchSqlUtil.concatFolderUpdate(folderId, name, parentId, description, creationDate, owner,
					lastModificationDate, lastModifiedBy, nameNlsid, nameSubsystem, descriptionNlsid, descriptionSubsystem,
					systemFolder, emPluginId, uiHidden, deleted, tenantId);
			logger.debug("Sync data in EMS_ANALYTICS_FOLDERS,execute update SQL:[" + sql + "]");
		}
		//sync Data
		syncDatabaseTableData(sql);

	}

	public void syncLastAccessTable(Long objectId, String accessedBy, Long objectType, String accessDate, Long tenantId,
			String creationDate, String lastModificationDate)
	{
		EntityManager em = null;
		em = getEntityManager();
		String sql = "select count(1) from EMS_ANALYTICS_LAST_ACCESS t where t.OBJECT_ID=? and t.ACCESSED_BY=? and t.OBJECT_TYPE=? and t.TENANT_ID=?";//check if the data is existing.
		Query q1 = em.createNativeQuery(sql);
		q1.setParameter(0, objectId);
		q1.setParameter(1, accessedBy);
		q1.setParameter(2, objectType);
		q1.setParameter(3, tenantId);
		Integer count = Integer.valueOf(q1.getSingleResult().toString());

		if (count <= 0) {
			//execute insert action
			logger.debug("Data not exist in table EMS_ANALYTICS_LAST_ACCESS,execute insert action.");
			sql = SyncSavedSearchSqlUtil.concatLastAccessInsert(objectId, accessedBy, objectType, accessDate, tenantId,
					creationDate, lastModificationDate);
			logger.debug("Sync data in EMS_ANALYTICS_LAST_ACCESS,execute insert SQL:[" + sql + "]");
		}
		else {
			//execute update action
			logger.debug("Data exist in table EMS_ANALYTICS_LAST_ACCESS,execute update action.");
			sql = SyncSavedSearchSqlUtil.concatLastAccessUpdate(objectId, accessedBy, objectType, accessDate, tenantId,
					creationDate, lastModificationDate);
			logger.debug("Sync data in EMS_ANALYTICS_LAST_ACCESS,execute update SQL:[" + sql + "]");
		}
		//sync Data
		syncDatabaseTableData(sql);

	}

	public void syncSearchParamsTable(BigInteger searchId, String name, String paramAttributes, Long paramType,
			String paramValueStr, String paramValueClob, Long tenantId, String creationDate, String lastModificationDate)
	{
		EntityManager em = null;
		em = getEntityManager();
		String sql = "select count(1) from EMS_ANALYTICS_SEARCH_PARAMS t where t.SEARCH_ID=? and t.NAME=? and t.TENANT_ID=?";//check if the data is existing.
		Query q1 = em.createNativeQuery(sql);
		q1.setParameter(0, searchId);
		q1.setParameter(1, name);
		q1.setParameter(2, tenantId);
		Integer count = Integer.valueOf(q1.getSingleResult().toString());

		if (count <= 0) {
			//execute insert action
			logger.debug("Data not exist in table EMS_ANALYTICS_SEARCH_PARAMS,execute insert action.");
			sql = SyncSavedSearchSqlUtil.concatSearchParamsInsert(searchId, name, paramAttributes, paramType, paramValueStr,
					paramValueClob, tenantId, creationDate, lastModificationDate);
			logger.debug("Sync data in EMS_ANALYTICS_SEARCH_PARAMS,execute insert SQL:[" + sql + "]");
		}
		else {
			//execute update action
			logger.debug("Data exist in table EMS_ANALYTICS_SEARCH_PARAMS,execute update action.");
			sql = SyncSavedSearchSqlUtil.concatSearchParamsUpdate(searchId, name, paramAttributes, paramType, paramValueStr,
					paramValueClob, tenantId, creationDate, lastModificationDate);
			logger.debug("Sync data in EMS_ANALYTICS_SEARCH_PARAMS,execute update SQL:[" + sql + "]");
		}
		//sync Data
		syncDatabaseTableData(sql);

	}

	public void syncSearchTable(Long searchId, String searchGuid, String name, String owner, String creationDate,
			String lastModificationDate, String lastModifiedBy, String description, Long folderId, Long categoryId,
			String nameNlsid, String nameSubsystem, String descriptionNlsid, String descriptionSubsystem, Integer systemSearch,
			String emPluginId, Integer isLocked, String metaDataClob, String searchDisplayStr, Integer uiHidden, Long deleted,
			Integer isWidget, Long tenantId, String nameWidgetSource, String widgetGroupName, String widgetScreenshotHref,
			String widgetIcon, String widgetKocName, String viewModel, String widgetTemplate, String widgetSupportTimeControl,
			Long widgetLinkedDashboard, Long widgetDefaultWidth, Long widgetDefaultHeight, String dashboardIneligible,
			String providerName, String providerVersion, String providerAssetRoot)
	{
		EntityManager em = null;
		em = getEntityManager();
		String sql = "select count(1) from EMS_ANALYTICS_SEARCH t where t.SEARCH_ID=? and t.NAME=? and t.TENANT_ID=?";//check if the data is existing.
		Query q1 = em.createNativeQuery(sql);
		q1.setParameter(0, searchId);
		q1.setParameter(1, name);
		q1.setParameter(2, tenantId);
		Integer count = Integer.valueOf(q1.getSingleResult().toString());

		if (count <= 0) {
			//execute insert action
			logger.debug("Data not exist in table EMS_ANALYTICS_SEARCH,execute insert action.");
			sql = SyncSavedSearchSqlUtil.concatSearchInsert(searchId, searchGuid, name, owner, creationDate,
					lastModificationDate, lastModifiedBy, description, folderId, categoryId, nameNlsid, nameSubsystem,
					descriptionNlsid, descriptionSubsystem, systemSearch, emPluginId, isLocked, metaDataClob, searchDisplayStr,
					uiHidden, deleted, isWidget, tenantId, nameWidgetSource, widgetGroupName, widgetScreenshotHref, widgetIcon,
					widgetKocName, viewModel, widgetTemplate, widgetSupportTimeControl, widgetLinkedDashboard,
					widgetDefaultWidth, widgetDefaultHeight, dashboardIneligible, providerName, providerVersion,
					providerAssetRoot);
			logger.debug("Sync data in EMS_ANALYTICS_SEARCH,execute insert SQL:[" + sql + "]");
		}
		else {
			//execute update action
			logger.debug("Data exist in table EMS_ANALYTICS_SEARCH,execute update action.");
			sql = SyncSavedSearchSqlUtil.concatSearchUpdate(searchId, searchGuid, name, owner, creationDate,
					lastModificationDate, lastModifiedBy, description, folderId, categoryId, nameNlsid, nameSubsystem,
					descriptionNlsid, descriptionSubsystem, systemSearch, emPluginId, isLocked, metaDataClob, searchDisplayStr,
					uiHidden, deleted, isWidget, tenantId, nameWidgetSource, widgetGroupName, widgetScreenshotHref, widgetIcon,
					widgetKocName, viewModel, widgetTemplate, widgetSupportTimeControl, widgetLinkedDashboard,
					widgetDefaultWidth, widgetDefaultHeight, dashboardIneligible, providerName, providerVersion,
					providerAssetRoot);
			logger.debug("Sync data in EMS_ANALYTICS_SEARCH,execute update SQL:[" + sql + "]");
		}
		//sync Data
		syncDatabaseTableData(sql);

	}

	/**
	 * Retrieves database data rows for specific native SQL for all tenant
	 *
	 * @return
	 */
	private List<Map<String, Object>> getDatabaseTableData(String nativeSql)
	{
		if (StringUtil.isEmpty(nativeSql)) {
			logger.error("Can't query database table with null or empty SQL statement!");
			return null;
		}
		EntityManager em = null;
		em = getEntityManager();
		Query query = em.createNativeQuery(nativeSql);
		query.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}

	private EntityManager getEntityManager()
	{
		EntityManager em;
		em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
		if (em == null) {
			logger.error("Can't get persistence entity manager");
		}
		return em;
	}

	private void syncDatabaseTableData(String syncSql)
	{
		if (StringUtil.isEmpty(syncSql)) {
			logger.error("Can't sync database table with null or empty SQL statement!");
			return;
		}
		EntityManager em = null;
		em = getEntityManager();
		em.createNativeQuery(syncSql).executeUpdate();
	}

}
