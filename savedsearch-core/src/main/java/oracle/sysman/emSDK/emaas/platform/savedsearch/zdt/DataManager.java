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

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

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
		em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
		if (em == null) {
			logger.error("Can't get persistence entity manager");
		}
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
		em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
		if (em == null) {
			logger.error("Can't get persistence entity manager");
		}
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
		em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
		if (em == null) {
			logger.error("Can't get persistence entity manager");
		}
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
		em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
		if (em == null) {
			logger.error("Can't get persistence entity manager");
		}
		Query query = em.createNativeQuery(nativeSql);
		query.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
}
