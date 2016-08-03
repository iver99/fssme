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
		String sql = "select count(1) from EMS_ANALYTICS_CATEGORY_PARAMS t where t.CATEGORY_ID=? and t.TENANT_ID=? and t.NAME=?";//check if the data is existing.
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q1 = em.createNativeQuery(sql).setParameter(1, categoryId).setParameter(2, tenantId).setParameter(3, name);
		Integer count = Integer.valueOf(q1.getSingleResult().toString());
		try{
			if (count <= 0) {
				//execute insert action
				logger.info("Data not exist in table EMS_ANALYTICS_CATEGORY_PARAMS,execute insert action.");
				sql = SyncSavedSearchSqlUtil.concatCategoryParamInsert(categoryId, name, paramValue, tenantId, creationDate,
						lastModificationDate);
				logger.info("Sync data in EMS_ANALYTICS_CATEGORY_PARAMS,execute insert SQL:[" + sql + "]");
				/*"INSERT INTO EMS_ANALYTICS_CATEGORY_PARAMS (CATEGORY_ID,NAME,PARAM_VALUE,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE) "
				+ "VALUES(?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'))";*/
				em.createNativeQuery(sql)
				.setParameter(1, categoryId).setParameter(2, name).setParameter(3, paramValue)
				.setParameter(4, tenantId).setParameter(5, creationDate).setParameter(6, lastModificationDate)
				.executeUpdate();
			}
			else {
				//execute update action
				logger.info("Data exist in table EMS_ANALYTICS_CATEGORY_PARAMS,execute update action.");
				sql = SyncSavedSearchSqlUtil.concatCategoryParamUpdate(categoryId, name, paramValue, tenantId, creationDate,
						lastModificationDate);
				logger.info("Sync data in EMS_ANALYTICS_CATEGORY_PARAMS,execute update SQL:[" + sql + "]");
				/*"UPDATE EMS_ANALYTICS_CATEGORY_PARAMS T SET T.PARAM_VALUE=?,T.CREATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
				+ "T.LAST_MODIFICATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff') where T.CATEGORY_ID=? and T.NAME=? and T.TENANT_ID=?";*/
				em.createNativeQuery(sql)
				.setParameter(1, paramValue).setParameter(2, creationDate).setParameter(3, lastModificationDate)
				.setParameter(4, categoryId).setParameter(5, name).setParameter(6, tenantId)
				.executeUpdate();
			}
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}

	}

	public void syncCategoryTable(Long categoryId, String name, String description, String owner, String creationDate,
			String nameNlsid, String nameSubSystem, String descriptionNlsid, String descriptionSubSystem, String emPluginId,
			Long defaultFolderId, Long deleted, String providerName, String providerVersion, String providerDiscovery,
			String providerAssetroot, Long tenantId, String dashboardIneligible, String lastModificationDate)
	{
		String sql = "select count(1) from EMS_ANALYTICS_CATEGORY t where t.CATEGORY_ID=? and t.TENANT_ID=?";//check if the data is existing.
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q1 = em.createNativeQuery(sql).setParameter(1, categoryId).setParameter(2, tenantId);
		Integer count = Integer.valueOf(q1.getSingleResult().toString());

		try{
			if (count <= 0) {
				//execute insert action
				logger.info("Data not exist in table EMS_ANALYTICS_CATEGORY,execute insert action.");
				sql = SyncSavedSearchSqlUtil.concatCategoryInsert(categoryId, name, description, owner, creationDate, nameNlsid,
						nameSubSystem, descriptionNlsid, descriptionSubSystem, emPluginId, defaultFolderId, deleted, providerName,
						providerVersion, providerDiscovery, providerAssetroot, tenantId, dashboardIneligible, lastModificationDate);
				logger.info("Sync data in EMS_ANALYTICS_CATEGORY,execute insert SQL:[" + sql + "]");
				/*"INSERT INTO EMS_ANALYTICS_CATEGORY (CATEGORY_ID,NAME,DESCRIPTION,OWNER,CREATION_DATE,"
				+ "NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,EM_PLUGIN_ID,"
				+ "DEFAULT_FOLDER_ID,DELETED,PROVIDER_NAME,PROVIDER_VERSION,PROVIDER_DISCOVERY,"
				+ "PROVIDER_ASSET_ROOT,TENANT_ID,DASHBOARD_INELIGIBLE,LAST_MODIFICATION_DATE) VALUES(?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
				+ "?,?,?,?,?,"
				+ "?,?,?,?,?,"
				+ "?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'))"*/
				em.createNativeQuery(sql)
				.setParameter(1, categoryId).setParameter(2, name).setParameter(3, description).setParameter(4, owner).setParameter(5, creationDate)
				.setParameter(6, nameNlsid).setParameter(7, nameSubSystem).setParameter(8, descriptionNlsid).setParameter(9, descriptionSubSystem).setParameter(10, emPluginId)
				.setParameter(11, defaultFolderId).setParameter(12, deleted).setParameter(13, providerName).setParameter(14, providerVersion).setParameter(15, providerDiscovery)
				.setParameter(16, providerAssetroot).setParameter(17, tenantId).setParameter(18, dashboardIneligible).setParameter(19, lastModificationDate)
				.executeUpdate();
			}
			else {
				//execute update action
				logger.info("Data exist in table EMS_ANALYTICS_CATEGORY,execute update action.");
				sql = SyncSavedSearchSqlUtil.concatCategoryUpdate(categoryId, name, description, owner, creationDate, nameNlsid,
						nameSubSystem, descriptionNlsid, descriptionSubSystem, emPluginId, defaultFolderId, deleted, providerName,
						providerVersion, providerDiscovery, providerAssetroot, tenantId, dashboardIneligible, lastModificationDate);
				logger.info("Sync data in EMS_ANALYTICS_CATEGORY,execute update SQL:[" + sql + "]");
				/*"UPDATE EMS_ANALYTICS_CATEGORY t set t.NAME=?,t.DESCRIPTION=?,t.OWNER=?,t.CREATION_DATE=?,"
				+ "t.NAME_NLSID=?,t.NAME_SUBSYSTEM=?,t.DESCRIPTION_NLSID=?,t.DESCRIPTION_SUBSYSTEM==?,t.EM_PLUGIN_ID=?,"
				+ "t.DEFAULT_FOLDER_ID=?,t.DELETED=?,t.PROVIDER_NAME=?,t.PROVIDER_VERSION=?,t.PROVIDER_DISCOVERY=?,"
				+ "t.PROVIDER_ASSET_ROOT=?,t.DASHBOARD_INELIGIBLE=?,t.LAST_MODIFICATION_DATE=? where t.CATEGORY_ID=? and t.TENANT_ID=?";*/
				em.createNativeQuery(sql)
				.setParameter(1, name).setParameter(2, description).setParameter(3, owner).setParameter(4, creationDate).setParameter(5, nameNlsid)
				.setParameter(6, nameSubSystem).setParameter(7, descriptionNlsid).setParameter(8, descriptionSubSystem).setParameter(9, emPluginId).setParameter(10, defaultFolderId)
				.setParameter(11, deleted).setParameter(12, providerName).setParameter(13, providerVersion).setParameter(14, providerDiscovery).setParameter(15, providerAssetroot)
				.setParameter(16, dashboardIneligible).setParameter(17, lastModificationDate).setParameter(18, categoryId).setParameter(19, tenantId)
				.executeUpdate();
			}
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
	}

	public void syncFolderTable(Long folderId, String name, Long parentId, String description, String creationDate, String owner,
			String lastModificationDate, String lastModifiedBy, String nameNlsid, String nameSubsystem, String descriptionNlsid,
			String descriptionSubsystem, Integer systemFolder, String emPluginId, Integer uiHidden, Long deleted, Long tenantId)
	{
		String sql = "select count(1) from EMS_ANALYTICS_FOLDERS t where t.FOLDER_ID=? and t.TENANT_ID=?";//check if the data is existing.
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q1 = em.createNativeQuery(sql).setParameter(1, folderId).setParameter(2, tenantId);
		Integer count = Integer.valueOf(q1.getSingleResult().toString());

		try{
			if (count <= 0) {
				//execute insert action
				logger.info("Data not exist in table EMS_ANALYTICS_FOLDERS,execute insert action.");
				sql = SyncSavedSearchSqlUtil.concatFolderInsert(folderId, name, parentId, description, creationDate, owner,
						lastModificationDate, lastModifiedBy, nameNlsid, nameSubsystem, descriptionNlsid, descriptionSubsystem,
						systemFolder, emPluginId, uiHidden, deleted, tenantId);
				logger.info("Sync data in EMS_ANALYTICS_FOLDERS,execute insert SQL:[" + sql + "]");
				/*String insertSql = "INSERT INTO EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,"
						+ "LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,"
						+ "DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,DELETED,TENANT_ID) VALUES(?,?,?,?,?,"
						+ "?,?,?,?,?,"
						+ "?,?,?,?,?,"
						+ "?,?)";*/
				em.createNativeQuery(sql)
				.setParameter(1, folderId).setParameter(2, name).setParameter(3, parentId)
				.setParameter(4, description).setParameter(5, creationDate).setParameter(6, owner)
				.setParameter(7, lastModificationDate).setParameter(8, lastModifiedBy).setParameter(9, nameNlsid)
				.setParameter(10, nameSubsystem).setParameter(11, descriptionNlsid).setParameter(12, descriptionSubsystem)
				.setParameter(13, systemFolder).setParameter(14, emPluginId).setParameter(15, uiHidden)
				.setParameter(16, deleted).setParameter(17, tenantId)
				.executeUpdate();
			}
			else {
				//execute update action
				logger.info("Data exist in table EMS_ANALYTICS_FOLDERS,execute update action.");
				sql = SyncSavedSearchSqlUtil.concatFolderUpdate(folderId, name, parentId, description, creationDate, owner,
						lastModificationDate, lastModifiedBy, nameNlsid, nameSubsystem, descriptionNlsid, descriptionSubsystem,
						systemFolder, emPluginId, uiHidden, deleted, tenantId);
				logger.info("Sync data in EMS_ANALYTICS_FOLDERS,execute update SQL:[" + sql + "]");
				/*"UPDATE EMS_ANALYTICS_FOLDERS t SET t.NAME=?,t.PARENT_ID=?,t.DESCRIPTION=?,t.CREATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
				+ "t.OWNER=?,t.LAST_MODIFICATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),t.LAST_MODIFIED_BY=?,t.NAME_NLSID=?,"
				+ "t.NAME_SUBSYSTEM=?,t.DESCRIPTION_NLSID=?,t.DESCRIPTION_SUBSYSTEM=?,t.SYSTEM_FOLDER=?,t.EM_PLUGIN_ID=?,"
				+ "t.UI_HIDDEN=?,t.DELETED=? where t.FOLDER_ID=? and t.TENANT_ID=?";*/
				em.createNativeQuery(sql)
				.setParameter(1, name).setParameter(2, parentId).setParameter(3, description)
				.setParameter(4, creationDate).setParameter(5, owner).setParameter(6, lastModificationDate)
				.setParameter(7, lastModifiedBy).setParameter(8, nameNlsid).setParameter(9, nameSubsystem)
				.setParameter(10, descriptionNlsid).setParameter(11, descriptionSubsystem).setParameter(12, systemFolder)
				.setParameter(13, emPluginId).setParameter(14, uiHidden).setParameter(15, deleted)
				.setParameter(16, folderId).setParameter(17, tenantId)
				.executeUpdate();
			}
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}

	}

	public void syncLastAccessTable(Long objectId, String accessedBy, Long objectType, String accessDate, Long tenantId,
			String creationDate, String lastModificationDate)
	{
		String sql = "select count(1) from EMS_ANALYTICS_LAST_ACCESS t where t.OBJECT_ID=? and t.ACCESSED_BY=? and t.OBJECT_TYPE=? and t.TENANT_ID=?";//check if the data is existing.
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q1 = em.createNativeQuery(sql).setParameter(1, objectId).setParameter(2, accessedBy).setParameter(3, objectType)
				.setParameter(4, tenantId);
		Integer count = Integer.valueOf(q1.getSingleResult().toString());

		try{
			if (count <= 0) {
				//execute insert action
				logger.info("Data not exist in table EMS_ANALYTICS_LAST_ACCESS,execute insert action.");
				sql = SyncSavedSearchSqlUtil.concatLastAccessInsert(objectId, accessedBy, objectType, accessDate, tenantId,
						creationDate, lastModificationDate);
				/*String insertSql = "INSERT INTO EMS_ANALYTICS_LAST_ACCESS (OBJECT_ID,ACCESSED_BY,OBJECT_TYPE,ACCESS_DATE,"
						+ "TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE) VALUES(?,?,?,?,?,?,?)";*/
				logger.info("Sync data in EMS_ANALYTICS_LAST_ACCESS,execute insert SQL:[" + sql + "]");
				em.createNativeQuery(sql)
				.setParameter(1, objectId)
				.setParameter(2, accessedBy)
				.setParameter(3, objectType)
				.setParameter(4, accessDate)
				.setParameter(5, tenantId)
				.setParameter(6, creationDate)
				.setParameter(7, lastModificationDate)
				.executeUpdate();
			}
			else {
				//execute update action
				logger.info("Data exist in table EMS_ANALYTICS_LAST_ACCESS,execute update action.");
				sql = SyncSavedSearchSqlUtil.concatLastAccessUpdate(objectId, accessedBy, objectType, accessDate, tenantId,
						creationDate, lastModificationDate);
				logger.info("Sync data in EMS_ANALYTICS_LAST_ACCESS,execute update SQL:[" + sql + "]");
				/*String updateSql = "UPDATE EMS_ANALYTICS_LAST_ACCESS t SET t.ACCESS_DATE=to_timestamp('" + accessDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff'),t.CREATION_DATE=to_timestamp('" + creationDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff')," + "t.LAST_MODIFICATION_DATE=to_timestamp('" + lastModificationDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff') " + "where t.OBJECT_ID=" + objectId + " and t.ACCESSED_BY='" + accessedBy
				+ "' and t.OBJECT_TYPE=" + objectType + " and t.TENANT_ID=" + tenantId + "";
	*/
				em.createNativeQuery(sql)
				.setParameter(1, accessDate)
				.setParameter(2, creationDate)
				.setParameter(3, lastModificationDate)
				.setParameter(4, objectId)
				.setParameter(5, accessedBy)
				.setParameter(6, objectType)
				.setParameter(7, tenantId)
				.executeUpdate();
			}
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		
	}

	public void syncSearchParamsTable(BigInteger searchId, String name, String paramAttributes, Long paramType,
			String paramValueStr, String paramValueClob, Long tenantId, String creationDate, String lastModificationDate)
	{
		String sql = "select count(1) from EMS_ANALYTICS_SEARCH_PARAMS t where t.SEARCH_ID=? and t.NAME=? and t.TENANT_ID=?";//check if the data is existing.
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q1 = em.createNativeQuery(sql).setParameter(1, searchId).setParameter(2, name).setParameter(3, tenantId);
		Integer count = Integer.valueOf(q1.getSingleResult().toString());

		try{
			if (count <= 0) {
				//execute insert action
				logger.info("Data not exist in table EMS_ANALYTICS_SEARCH_PARAMS,execute insert action.");
				sql = SyncSavedSearchSqlUtil.concatSearchParamsInsert(searchId, name, paramAttributes, paramType, paramValueStr,
						paramValueClob, tenantId, creationDate, lastModificationDate);
				logger.info("Sync data in EMS_ANALYTICS_SEARCH_PARAMS,execute insert SQL:[" + sql + "]");
				/*String insertSql = "INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,"
						+ "PARAM_VALUE_CLOB,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE) VALUES(?,?,?,?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'))";*/
				em.createNativeQuery(sql)
				.setParameter(1, searchId).setParameter(2, name).setParameter(3, paramAttributes)
				.setParameter(4, paramType).setParameter(5, paramValueStr).setParameter(6, paramValueClob)
				.setParameter(7, tenantId).setParameter(8, creationDate).setParameter(9, lastModificationDate)
				.executeUpdate();
			}
			else {
				//execute update action
				logger.info("Data exist in table EMS_ANALYTICS_SEARCH_PARAMS,execute update action.");
				sql = SyncSavedSearchSqlUtil.concatSearchParamsUpdate(searchId, name, paramAttributes, paramType, paramValueStr,
						paramValueClob, tenantId, creationDate, lastModificationDate);
				logger.info("Sync data in EMS_ANALYTICS_SEARCH_PARAMS,execute update SQL:[" + sql + "]");
				/*String updateSql = "UPDATE EMS_ANALYTICS_SEARCH_PARAMS t set t.PARAM_ATTRIBUTES=?,t.PARAM_TYPE=?,t.PARAM_VALUE_STR=?,"
						+ "t.PARAM_VALUE_CLOB=?,t.CREATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'), t.LAST_MODIFICATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff') where t.SEARCH_ID=? and t.NAME=? and t.TENANT_ID=?";*/
			
				em.createNativeQuery(sql)
				.setParameter(1, paramAttributes).setParameter(2, paramType).setParameter(3, paramValueStr)
				.setParameter(4, paramValueClob).setParameter(5, creationDate).setParameter(6, lastModificationDate)
				.setParameter(7, searchId).setParameter(8, name).setParameter(9, tenantId)
				.executeUpdate();
			}
			//sync Data
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			em.close();
		}
		

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
		String sql = "select count(1) from EMS_ANALYTICS_SEARCH t where t.SEARCH_ID=? and t.NAME=? and t.TENANT_ID=?";//check if the data is existing.
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q1 = em.createNativeQuery(sql).setParameter(1, searchId).setParameter(2, name).setParameter(3, tenantId);
		Integer count = Integer.valueOf(q1.getSingleResult().toString());
		try{
			if (count <= 0) {
				//execute insert action
				logger.info("Data not exist in table EMS_ANALYTICS_SEARCH,execute insert action.");
				sql = SyncSavedSearchSqlUtil.concatSearchInsert(searchId, searchGuid, name, owner, creationDate,
						lastModificationDate, lastModifiedBy, description, folderId, categoryId, nameNlsid, nameSubsystem,
						descriptionNlsid, descriptionSubsystem, systemSearch, emPluginId, isLocked, metaDataClob, searchDisplayStr,
						uiHidden, deleted, isWidget, tenantId, nameWidgetSource, widgetGroupName, widgetScreenshotHref, widgetIcon,
						widgetKocName, viewModel, widgetTemplate, widgetSupportTimeControl, widgetLinkedDashboard,
						widgetDefaultWidth, widgetDefaultHeight, dashboardIneligible, providerName, providerVersion,
						providerAssetRoot);
				logger.info("Sync data in EMS_ANALYTICS_SEARCH,execute insert SQL:[" + sql + "]");
				em.createNativeQuery(sql).setParameter(1, searchId).setParameter(2, searchGuid).setParameter(3, name)
				.setParameter(4, owner).setParameter(5, creationDate).setParameter(6, lastModificationDate)
						.setParameter(7, lastModifiedBy).setParameter(8, description).setParameter(9, folderId)
						.setParameter(10, categoryId).setParameter(11, nameNlsid).setParameter(12, nameSubsystem)
						.setParameter(13, descriptionNlsid).setParameter(14, descriptionSubsystem).setParameter(15, systemSearch)
				.setParameter(16, emPluginId).setParameter(17, isLocked).setParameter(18, metaDataClob)
						.setParameter(19, searchDisplayStr).setParameter(20, uiHidden).setParameter(21, deleted)
						.setParameter(22, isWidget).setParameter(23, tenantId).setParameter(24, nameWidgetSource)
						.setParameter(25, widgetGroupName).setParameter(26, widgetScreenshotHref).setParameter(27, widgetIcon)
				.setParameter(28, widgetKocName).setParameter(29, viewModel).setParameter(30, widgetTemplate)
						.setParameter(31, widgetSupportTimeControl).setParameter(32, widgetLinkedDashboard)
						.setParameter(33, widgetDefaultWidth).setParameter(34, widgetDefaultHeight).setParameter(35, providerName)
				.setParameter(36, providerVersion).setParameter(37, providerAssetRoot).setParameter(38, dashboardIneligible)
						.executeUpdate();
			}
			else {
				//execute update action
				logger.info("Data exist in table EMS_ANALYTICS_SEARCH,execute update action.");
				sql = SyncSavedSearchSqlUtil.concatSearchUpdate(searchId, searchGuid, name, owner, creationDate,
						lastModificationDate, lastModifiedBy, description, folderId, categoryId, nameNlsid, nameSubsystem,
						descriptionNlsid, descriptionSubsystem, systemSearch, emPluginId, isLocked, metaDataClob, searchDisplayStr,
						uiHidden, deleted, isWidget, tenantId, nameWidgetSource, widgetGroupName, widgetScreenshotHref, widgetIcon,
						widgetKocName, viewModel, widgetTemplate, widgetSupportTimeControl, widgetLinkedDashboard,
						widgetDefaultWidth, widgetDefaultHeight, dashboardIneligible, providerName, providerVersion,
						providerAssetRoot);
				logger.info("Sync data in EMS_ANALYTICS_SEARCH,execute update SQL:[" + sql + "]");
				em.createNativeQuery(sql).setParameter(1, searchGuid).setParameter(2, name)
				.setParameter(3, owner).setParameter(4, creationDate).setParameter(5, lastModificationDate)
						.setParameter(6, lastModifiedBy).setParameter(7, description).setParameter(8, folderId)
						.setParameter(9, categoryId).setParameter(10, nameNlsid).setParameter(11, nameSubsystem)
						.setParameter(12, descriptionNlsid).setParameter(13, descriptionSubsystem).setParameter(14, systemSearch)
				.setParameter(15, emPluginId).setParameter(16, isLocked).setParameter(17, metaDataClob)
						.setParameter(18, searchDisplayStr).setParameter(19, uiHidden).setParameter(20, deleted)
						.setParameter(21, isWidget).setParameter(22, nameWidgetSource)
						.setParameter(23, widgetGroupName).setParameter(24, widgetScreenshotHref).setParameter(25, widgetIcon)
				.setParameter(26, widgetKocName).setParameter(27, viewModel).setParameter(28, widgetTemplate)
						.setParameter(29, widgetSupportTimeControl).setParameter(30, widgetLinkedDashboard)
						.setParameter(31, widgetDefaultWidth).setParameter(32, widgetDefaultHeight).setParameter(33, providerName)
				.setParameter(34, providerVersion).setParameter(35, providerAssetRoot).setParameter(36, dashboardIneligible)
				.setParameter(37, searchId).setParameter(38, tenantId)
						.executeUpdate();
			}
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			em.close();
		}
		

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


}
