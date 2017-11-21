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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.exception.HalfSyncException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.exception.NoComparedResultException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.exception.SyncException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;

/**
 * @author pingwu
 */
@SuppressWarnings("unchecked")
public class DataManager
{
	private static final Logger logger = LogManager.getLogger(DataManager.class);
	private static DataManager instance = new DataManager();

	private static final String SQL_ALL_SEARCH_COUNT = "SELECT COUNT(*) FROM EMS_ANALYTICS_SEARCH WHERE LAST_MODIFICATION_DATE < to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')"
			+ " and system_search <> 1";
	private static final String SQL_ALL_SEARCH__PARAM_COUNT = "SELECT COUNT(*) FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE LAST_MODIFICATION_DATE < to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')"
			+ " and search_Id in (select search_id  from ems_analytics_search  where system_search <> 1)";
    private static final String SQL_ALL_FOLDER_COUNT = "SELECT COUNT(*) FROM EMS_ANALYTICS_FOLDERS WHERE LAST_MODIFICATION_DATE < to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')";

	private static final String SQL_ALL_SEARCH_ROWS = "SELECT TO_CHAR(SEARCH_ID) AS SEARCH_ID,NAME,OWNER,CREATION_DATE,"
			+ "LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,TO_CHAR(FOLDER_ID) AS FOLDER_ID, TO_CHAR(CATEGORY_ID) AS CATEGORY_ID,"
			+ "SYSTEM_SEARCH,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,"
			+ "DELETED,IS_WIDGET,TENANT_ID,WIDGET_SOURCE,WIDGET_GROUP_NAME,"
			+ "WIDGET_SCREENSHOT_HREF,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,"
			+ "WIDGET_SUPPORT_TIME_CONTROL,WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,PROVIDER_NAME,"
			+ "PROVIDER_VERSION,PROVIDER_ASSET_ROOT,DASHBOARD_INELIGIBLE,FEDERATION_SUPPORTED FROM EMS_ANALYTICS_SEARCH where LAST_MODIFICATION_DATE < to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')"
			+ " and system_search <> 1";

	private static final String SQL_ALL_SEARCH_ROWS_BY_TENANT = "SELECT TO_CHAR(SEARCH_ID) AS SEARCH_ID,NAME,OWNER,CREATION_DATE,"
			+ "LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,TO_CHAR(FOLDER_ID) AS FOLDER_ID, TO_CHAR(CATEGORY_ID) AS CATEGORY_ID,"
			+ "SYSTEM_SEARCH,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,"
			+ "DELETED,IS_WIDGET,TENANT_ID,WIDGET_SOURCE,WIDGET_GROUP_NAME,"
			+ "WIDGET_SCREENSHOT_HREF,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,"
			+ "WIDGET_SUPPORT_TIME_CONTROL,WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,PROVIDER_NAME,"
			+ "PROVIDER_VERSION,PROVIDER_ASSET_ROOT,DASHBOARD_INELIGIBLE,FEDERATION_SUPPORTED FROM EMS_ANALYTICS_SEARCH where LAST_MODIFICATION_DATE < to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')"
			+ " and system_search <> 1 and tenant_id = ?";

    private static final String SQL_ALL_FOLDER_ROWS_BY_DATE = "SELECT TO_CHAR(FOLDER_ID) AS FOLDER_ID,NAME, TO_CHAR(PARENT_ID) AS PARENT_ID, DESCRIPTION,CREATION_DATE,OWNER,"
        + "LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,"
        + "DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,DELETED,TENANT_ID FROM EMS_ANALYTICS_FOLDERS WHERE LAST_MODIFICATION_DATE > to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff') and LAST_MODIFICATION_DATE < to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')";

    private static final String SQL_ALL_FOLDER_ROWS = "SELECT TO_CHAR(FOLDER_ID) AS FOLDER_ID,NAME, TO_CHAR(PARENT_ID) AS PARENT_ID, DESCRIPTION,CREATION_DATE,OWNER,"
            + "LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,"
            + "DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,DELETED,TENANT_ID FROM EMS_ANALYTICS_FOLDERS where LAST_MODIFICATION_DATE < to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')";

    private static final String SQL_ALL_FOLDER_ROWS_BY_TENANT = "SELECT TO_CHAR(FOLDER_ID) AS FOLDER_ID,NAME, TO_CHAR(PARENT_ID) AS PARENT_ID, DESCRIPTION,CREATION_DATE,OWNER,"
            + "LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,"
            + "DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,DELETED,TENANT_ID FROM EMS_ANALYTICS_FOLDERS where LAST_MODIFICATION_DATE < to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff') and tenant_id = ?";

	private static final String SQL_ALL_SEARCH_PARAMS_ROWS = "SELECT TO_CHAR(SEARCH_ID) AS SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,"
			+ "PARAM_VALUE_CLOB,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE,DELETED FROM EMS_ANALYTICS_SEARCH_PARAMS where LAST_MODIFICATION_DATE < to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')"
			+ " and search_Id in (select search_Id  from ems_analytics_search  where system_search <> 1)";

	private static final String SQL_ALL_SEARCH_PARAMS_ROWS_BY_TENANT = "SELECT TO_CHAR(SEARCH_ID) AS SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,"
			+ "PARAM_VALUE_CLOB,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE,DELETED FROM EMS_ANALYTICS_SEARCH_PARAMS where LAST_MODIFICATION_DATE < to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')"
			+ " and search_Id in (select search_Id  from ems_analytics_search  where system_search <> 1) and tenant_id = ?";

	private static final String SQL_ALL_SEARCH_ROWS_BY_DATE = "SELECT TO_CHAR(SEARCH_ID) AS SEARCH_ID,NAME,OWNER,CREATION_DATE,"
			+ "LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,TO_CHAR(FOLDER_ID) AS FOLDER_ID, TO_CHAR(CATEGORY_ID) AS CATEGORY_ID,"
			+ "SYSTEM_SEARCH,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,"
			+ "DELETED,IS_WIDGET,TENANT_ID,WIDGET_SOURCE,WIDGET_GROUP_NAME,"
			+ "WIDGET_SCREENSHOT_HREF,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,"
			+ "WIDGET_SUPPORT_TIME_CONTROL,WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,PROVIDER_NAME,"
			+ "PROVIDER_VERSION,PROVIDER_ASSET_ROOT,DASHBOARD_INELIGIBLE,FEDERATION_SUPPORTED FROM EMS_ANALYTICS_SEARCH WHERE LAST_MODIFICATION_DATE > to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff') and LAST_MODIFICATION_DATE < to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')"
			+ " and system_search <> 1";
/*
	private static final String SQL_ALL_CATEGORY_PARAMS_ROWS_BY_DATE = "SELECT TO_CHAR(CATEGORY_ID) AS CATEGORY_ID,NAME,PARAM_VALUE,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE, DELETED FROM EMS_ANALYTICS_CATEGORY_PARAMS "
			+ "WHERE LAST_MODIFICATION_DATE > to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff') and LAST_MODIFICATION_DATE < to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')";
*/
	private static final String SQL_ALL_SEARCH_PARAMS_ROWS_BY_DATE = "SELECT TO_CHAR(SEARCH_ID) AS SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,"
			+ "PARAM_VALUE_CLOB,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE,DELETED FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE LAST_MODIFICATION_DATE > to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff') and LAST_MODIFICATION_DATE < to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')"
			+ " and search_Id in (select search_id  from ems_analytics_search  where system_search <> 1)";

	private static final String SQL_INSERT_FOLDER = "INSERT INTO EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,"
			+ "LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,"
			+ "DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,DELETED,TENANT_ID) VALUES(?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
			+ "?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),?,?,?," + "?,?,?,?,?," + "?,?)";

	private static final String SQL_UPDATE_FOLDER = "UPDATE EMS_ANALYTICS_FOLDERS t SET t.NAME=?,t.PARENT_ID=?,t.DESCRIPTION=?,t.CREATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
			+ "t.OWNER=?,t.LAST_MODIFICATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),t.LAST_MODIFIED_BY=?,t.NAME_NLSID=?,"
			+ "t.NAME_SUBSYSTEM=?,t.DESCRIPTION_NLSID=?,t.DESCRIPTION_SUBSYSTEM=?,t.SYSTEM_FOLDER=?,t.EM_PLUGIN_ID=?,"
			+ "t.UI_HIDDEN=?,t.DELETED=? where t.FOLDER_ID=? and t.TENANT_ID=?";

	private static final String SQL_INSERT_SEARCH = "INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,SEARCH_GUID,NAME,OWNER,CREATION_DATE,"
			+ "LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,"
			+ "SYSTEM_SEARCH,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,"
			+ "DELETED,IS_WIDGET,TENANT_ID,WIDGET_SOURCE,WIDGET_GROUP_NAME,"
			+ "WIDGET_SCREENSHOT_HREF,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,"
			+ "WIDGET_SUPPORT_TIME_CONTROL,WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,PROVIDER_NAME,"
			+ "PROVIDER_VERSION,PROVIDER_ASSET_ROOT,DASHBOARD_INELIGIBLE,FEDERATION_SUPPORTED) VALUES(?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
			+ "to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),?,?,?,?,"
			+ "?,?,?,?,?,"
			+ "?,?,?,?,?,"
			+ "?,?,?,?,?,"
			+ "?,?,?,?,?," + "?,?,?,?)";

	private static final String SQL_UPDATE_SEARCH = "UPDATE EMS_ANALYTICS_SEARCH t set t.NAME=?,t.OWNER=?,t.CREATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
			+ "t.LAST_MODIFICATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
			+ "t.LAST_MODIFIED_BY=?,t.DESCRIPTION=?,t.FOLDER_ID=?,t.CATEGORY_ID=?,"
			+ "t.SYSTEM_SEARCH=?,t.IS_LOCKED=?,t.METADATA_CLOB=?,t.SEARCH_DISPLAY_STR=?,t.UI_HIDDEN=?,"
			+ "t.DELETED=?,t.IS_WIDGET=?,t.WIDGET_SOURCE=?,t.WIDGET_GROUP_NAME=?,t.WIDGET_SCREENSHOT_HREF=?,t.WIDGET_ICON=?,"
			+ "t.WIDGET_KOC_NAME=?,t.WIDGET_VIEWMODEL=?,t.WIDGET_TEMPLATE=?,t.WIDGET_SUPPORT_TIME_CONTROL=?,t.WIDGET_LINKED_DASHBOARD=?,"
			+ "t.WIDGET_DEFAULT_WIDTH=?,t.WIDGET_DEFAULT_HEIGHT=?,t.PROVIDER_NAME=?,t.PROVIDER_VERSION=?,t.PROVIDER_ASSET_ROOT=?,"
			+ "t.DASHBOARD_INELIGIBLE=?,t.FEDERATION_SUPPORTED=? where t.SEARCH_ID=? and t.TENANT_ID=?";

	private static final String SQL_INSERT_SEARCH_PARAM = "INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,"
			+ "PARAM_VALUE_CLOB,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE,DELETED) VALUES(?,?,?,?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),?)";

	private static final String SQL_UPDATE_SEARCH_PARAM = "UPDATE EMS_ANALYTICS_SEARCH_PARAMS t set t.PARAM_ATTRIBUTES=?,t.PARAM_TYPE=?,t.PARAM_VALUE_STR=?,"
			+ "t.PARAM_VALUE_CLOB=?,t.CREATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'), t.LAST_MODIFICATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'), t.deleted=? where t.SEARCH_ID=? and t.NAME=? and t.TENANT_ID=?";

	private static final String SQL_INSERT_TO_ZDT_COMPARISON_TABLE = "insert into ems_analytics_zdt_comparator (comparison_date,next_schedule_comparison_date, comparison_type, comparison_result, divergence_percentage) "
			+ "values (to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'), to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'), ?, ?, ?)";

	private static final String SQL_GET_COMPARISON_STATUS = "select * from (SELECT to_char(COMPARISON_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as COMPARISON_DATE, to_char(NEXT_SCHEDULE_COMPARISON_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as NEXT_SCHEDULE_COMPARISON_DATE,COMPARISON_TYPE, divergence_percentage "
			+ "from ems_analytics_zdt_comparator order by comparison_date desc) where rownum = 1";

	private static final String SQL_INSERT_TO_ZDT_SYNC_TABLE = "insert into ems_analytics_zdt_sync (sync_date,next_schedule_sync_date, sync_type, sync_result, divergence_percentage, LAST_COMPARISON_DATE) "
			+ "values (to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'), to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'), ?, ?, ?, to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'))";

	private static final String SQL_GET_LAST_COMPARISON_DATE_FOR_SYNC = "SELECT * FROM (SELECT to_char(LAST_COMPARISON_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as LAST_COMPARISON_DATE FROM EMS_ANALYTICS_ZDT_SYNC WHERE SYNC_RESULT = 'SUCCESSFUL' ORDER BY SYNC_DATE DESC) WHERE ROWNUM = 1";

	private static final String SQL_GET_HALF_SYNC_RECORD = "SELECT * FROM EMS_ANALYTICS_ZDT_SYNC WHERE SYNC_TYPE = 'half'";

	private static final String SQL_GET_LATEST_COMPARISON_DATE = "SELECT * FROM (SELECT to_char(COMPARISON_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as COMPARISON_DATE FROM EMS_ANALYTICS_ZDT_COMPARATOR WHERE COMPARISON_RESULT IS NOT NULL AND COMPARISON_DATE IS NOT NULL ORDER BY COMPARISON_DATE DESC) WHERE ROWNUM = 1";

	private static final String SQL_GET_SYNC_STATUS = "select * from (SELECT to_char(SYNC_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SYNC_DATE, to_char(NEXT_SCHEDULE_SYNC_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as NEXT_SCHEDULE_SYNC_DATE,SYNC_TYPE, divergence_percentage from ems_analytics_zdt_sync order by sync_date desc) where rownum = 1";

	private static final String SQL_GET_COMPARED_DATA_TO_SYNC_BY_DATE = "SELECT to_char(COMPARISON_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as COMPARISON_DATE, comparison_result from EMS_ANALYTICS_ZDT_COMPARATOR where comparison_date > to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff') and comparison_result is not null";

	private static final String SQL_GET_COMPARED_DATA_TO_SYNC = "SELECT to_char(COMPARISON_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as COMPARISON_DATE, comparison_result from EMS_ANALYTICS_ZDT_COMPARATOR where comparison_result is not null";

	private static final String SQL_GET_HALF_SYNC_COMPARED_DATA_TO_SYNC = "SELECT comparison_result from EMS_ANALYTICS_ZDT_COMPARATOR where comparison_date = to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')";

	private static final String SQL_GET_ALL_TENANTS = "select distinct tenant_id from ems_analytics_search where system_search <>1";
	/**
	 * Return the singleton for data manager
	 *
	 * @return
	 */
	public static DataManager getInstance()
	{
		return instance;

	}

	public int saveToComparatorTable(EntityManager em, String comparisonDate, String nextCompareDate, String comparisonType,
			String comparisonResult, double divergencePercentage) {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		try {
			em.createNativeQuery(SQL_INSERT_TO_ZDT_COMPARISON_TABLE).setParameter(1, comparisonDate).setParameter(2, nextCompareDate).setParameter(3, comparisonType)
			.setParameter(4, comparisonResult).setParameter(5, divergencePercentage).executeUpdate();
			em.getTransaction().commit();
			return 0;
		} catch (Exception e) {
			logger.error("errors occurs in saveToComparatorTalbe, "+e.getLocalizedMessage());
			return -1;
		}
	}

	public int saveToSyncTable(String syncDate, String nextSyncDate, String SyncType,
			String syncResult, double divergencePercentage, String lastComparisonDate) {
		EntityManager em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		try {
			em.createNativeQuery(SQL_INSERT_TO_ZDT_SYNC_TABLE).setParameter(1, syncDate).setParameter(2, nextSyncDate).setParameter(3, SyncType)
			.setParameter(4, syncResult).setParameter(5, divergencePercentage).setParameter(6, lastComparisonDate).executeUpdate();
			em.getTransaction().commit();
			return 0;
		} catch (Exception e) {
			logger.error("errors occurs in saveToComparatorTalbe, ", e.getLocalizedMessage());
			return -1;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Map<String, Object>> getComparedDataForSync(EntityManager em, String lastComparisonDateForSync) {
		List<Map<String, Object>> result = null;
		try {
			if (lastComparisonDateForSync != null) {
				result = getDatabaseTableData(em,SQL_GET_COMPARED_DATA_TO_SYNC_BY_DATE, lastComparisonDateForSync,null, null);
			} else {
				result = getDatabaseTableData(em,SQL_GET_COMPARED_DATA_TO_SYNC,null, null, null);
			}

		}catch(Exception e) {
			logger.error("exception happens in getComparedDataForSync " + e);
		}
		return result;
	}

	public Map<String, Object> getHalfSyncedComparedData(EntityManager em, String lastComparisonDateForSync)throws HalfSyncException, NoComparedResultException {
		logger.info("Get half synced compared data for last compared date is {}", lastComparisonDateForSync);
		if(lastComparisonDateForSync == null){
			logger.error("lastComparisonDateForSync can not be null for getting half synced compared data");
			throw new HalfSyncException("lastComparisonDateForSync can not be null for getting half synced compared data");
		}

		List<Map<String, Object>> result = getDatabaseTableData(em,SQL_GET_HALF_SYNC_COMPARED_DATA_TO_SYNC, lastComparisonDateForSync,null, null);

		if(result == null){
			logger.error("No compared result found for last compared date {}", lastComparisonDateForSync);
			throw new NoComparedResultException("No compared result found");
		}
		return result.get(0);
	}

	public String getLastComparisonDateForSync(EntityManager em) {
		List<Object> result = getSingleTableData(em,SQL_GET_LAST_COMPARISON_DATE_FOR_SYNC, null);
		if (result != null && result.size() == 1) {
			return (String)result.get(0);
		}
		logger.info("No successful sync record found in sync table!");
		return null;
	}

	public Map<String, Object> checkHalfSyncRecord(EntityManager em) throws  HalfSyncException{
		List<Object> result = getSingleTableData(em,SQL_GET_HALF_SYNC_RECORD, null);
		if(result == null || result.size() == 0){
			logger.info("No half sync record found in sync table!");
			return null;
		}
		if(result.size() > 1){
			logger.error("'SYNC_TYPE = half' record is more than 1, which is unexpected. ");
			throw new HalfSyncException("'SYNC_TYPE = half' record is more than 1, which is unexpected. ");
		}
		//return the only 1 half record
		return (Map<String, Object>)(result.get(0));
	}

	public List<Object> getAllTenants(EntityManager em) {
		List<Object> result =  getSingleTableData(em, SQL_GET_ALL_TENANTS, null);
		return result;
	}

	public String getLatestComparisonDateForCompare(EntityManager em) {
		List<Object> result = getSingleTableData(em,SQL_GET_LATEST_COMPARISON_DATE, null);
		if (result != null && result.size() == 1) {
			return (String)result.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> getSyncStatus(EntityManager em) {
		try {
			List<Map<String, Object>> result = getDatabaseTableData(em, SQL_GET_SYNC_STATUS, null, null, null);
			return result;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public List<Map<String, Object>> getComparatorStatus(EntityManager em) {
		try {
			List<Map<String, Object>> result = getDatabaseTableData(em, SQL_GET_COMPARISON_STATUS, null, null, null);
			return result;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	/**
	 * Get the number of folder rows
	 *
	 * @return
	 */

	public long getAllFolderCount(EntityManager em, String maxComparedDate)
	{
        long count = 0l;
        try {
            Query query = em.createNativeQuery(SQL_ALL_FOLDER_COUNT).setParameter(1, maxComparedDate);
            List<Object> result = query.getResultList();
            if (result != null && result.size() == 1) {
                count =  ((Number)result.get(0)).longValue();
            }
        }
        catch (Exception e) {
            logger.error("Error occured when get all folder count!",e.getLocalizedMessage());
        }
        return count;
	}

	/**
	 * Get the number of search rows
	 *
	 * @return
	 */

	public long getAllSearchCount(EntityManager em, String maxComparedDate)
	{
		long count = 0l;
		try {
			Query query = em.createNativeQuery(SQL_ALL_SEARCH_COUNT).setParameter(1, maxComparedDate);
			List<Object> result = query.getResultList();
			if (result != null && result.size() == 1) {
				count =  ((Number)result.get(0)).longValue();
			}
		}
		catch (Exception e) {
			logger.error("Error occured when get all search count!",e.getLocalizedMessage());
		}
		return count;

	}

	public long getAllSearchParamsCount(EntityManager em, String maxComparedDate){
		long count = 0l;
		try {
			Query query = em.createNativeQuery(SQL_ALL_SEARCH__PARAM_COUNT).setParameter(1, maxComparedDate);
			List<Object> result = query.getResultList();
			if (result != null && result.size() == 1) {
				count =  ((Number)result.get(0)).longValue();
			}
		}
		catch (Exception e) {
			logger.error("Error occured when get all search params count!",e.getLocalizedMessage());
		}
		return count;
	}

	/**
	 * Get all rows in folder table
	 *
	 * @return
	 */
	public List<Map<String, Object>> getFolderTableData(EntityManager em, String type, String date, String maxComparedDate, String tenant)
	{
        if (tenant != null) {
            return getDatabaseTableData(em,SQL_ALL_FOLDER_ROWS_BY_TENANT,null, maxComparedDate, tenant);
        } else {
            if ("incremental".equals(type) && date != null) {
                return getDatabaseTableData(em,SQL_ALL_FOLDER_ROWS_BY_DATE,date, maxComparedDate, null);
            }else {
                return getDatabaseTableData(em,SQL_ALL_FOLDER_ROWS,null, maxComparedDate, null);
            }
        }
	}

	/**
	 * get all rows in search param table
	 *
	 * @return
	 */

	public List<Map<String, Object>> getSearchParamTableData(EntityManager em, String type, String date, String maxComparedDate, String tenant)
	{
		if (tenant != null) {
			return getDatabaseTableData(em,SQL_ALL_SEARCH_PARAMS_ROWS_BY_TENANT,null, maxComparedDate, tenant);
		} else {
			if ("incremental".equals(type) && date != null) {
				return getDatabaseTableData(em,SQL_ALL_SEARCH_PARAMS_ROWS_BY_DATE,date, maxComparedDate, null);
			}else {
				return getDatabaseTableData(em,SQL_ALL_SEARCH_PARAMS_ROWS,null, maxComparedDate, null);
			}
		}
	}

	/**
	 * Get all rows in search table
	 *
	 * @return
	 */

	public List<Map<String, Object>> getSearchTableData(EntityManager em, String type, String date, String maxComparedDate, String tenant)
	{
		// it means it is the first time comparison
		if (tenant != null) {
			return getDatabaseTableData(em,SQL_ALL_SEARCH_ROWS_BY_TENANT,null, maxComparedDate, tenant);
		} else {
			if ("incremental".equals(type) && date != null) {
				return getDatabaseTableData(em,SQL_ALL_SEARCH_ROWS_BY_DATE,date, maxComparedDate, null);
			} else {
				// avoid to fetch all rows one time
				return getDatabaseTableData(em,SQL_ALL_SEARCH_ROWS,null, maxComparedDate, null);
			}
 		}
	}


	public void syncFolderTable(EntityManager em, BigInteger folderId, String name, BigInteger parentId, String description, String creationDate, String owner,
			String lastModificationDate, String lastModifiedBy, String nameNlsid, String nameSubsystem, String descriptionNlsid,
			String descriptionSubsystem, Integer systemFolder, String emPluginId, Integer uiHidden, BigInteger deleted, Long tenantId) throws SyncException
	{
		//check db constraint
		if (checkDbFolderConstraint(folderId, name, creationDate, systemFolder, uiHidden, deleted, tenantId)){
			return;
		}

		String sql = "select to_char(CREATION_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CREATION_DATE,to_char(t.LAST_MODIFICATION_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as LAST_MODIFICATION_DATE from EMS_ANALYTICS_FOLDERS t "
				+ "where (t.FOLDER_ID=? and t.TENANT_ID=?) or (t.name=? and t.parent_id=? and t.deleted=? and t.tenant_id =? and t.owner=?)";//check if the data is existing.
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		Query q1 = em.createNativeQuery(sql).setParameter(1, folderId).setParameter(2, tenantId)
				.setParameter(3, name).setParameter(4, parentId).setParameter(5, deleted)
				.setParameter(6, tenantId).setParameter(7,owner);
		q1.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> result = q1.getResultList();
		boolean flag = true;//true=>insert,false=>update
		if (result != null && result.size() > 0) {
			flag = false;
		}

		try {
			if (flag) {
				//execute insert action
				logger.info("Data not exist in table EMS_ANALYTICS_FOLDERS,execute insert action.Folder id is {}", folderId);
				int insertResult = em.createNativeQuery(SQL_INSERT_FOLDER).setParameter(1, folderId).setParameter(2, name).setParameter(3, parentId)
						.setParameter(4, description).setParameter(5, creationDate).setParameter(6, owner)
						.setParameter(7, lastModificationDate).setParameter(8, lastModifiedBy).setParameter(9, nameNlsid)
						.setParameter(10, nameSubsystem).setParameter(11, descriptionNlsid)
						.setParameter(12, descriptionSubsystem).setParameter(13, systemFolder).setParameter(14, emPluginId)
						.setParameter(15, uiHidden).setParameter(16, deleted).setParameter(17, tenantId).executeUpdate();
				logger.info("InsertResult is {}",insertResult);
			}else {
				Map<String, Object> dateMap = result.get(0);
		    	String creationD  = dateMap.get("CREATION_DATE").toString();
		    	if (creationD == null) {
		    		return;
		    	}
		    	Object lastModifiedObj = dateMap.get("LAST_MODIFICATION_DATE");
		    	boolean check = false;
		    	if (lastModifiedObj == null || lastModificationDate == null) {
		    		check = isAfter(creationD,creationDate);
		    	} else {
		    		check = isAfter((String)lastModifiedObj, lastModificationDate);
		    	}

				if (check) {
					logger.info("Data's Last modification date is earlier, no update action is needed in table EMS_ANALYTICS_FOLDERS.");
					//do nothing
				}
				else {
					//execute update action
					logger.info("Data exist in table EMS_ANALYTICS_FOLDERS,execute update action. Folder id is {}",folderId);
					int updateResult = em.createNativeQuery(SQL_UPDATE_FOLDER).setParameter(1, name).setParameter(2, parentId)
							.setParameter(3, description).setParameter(4, creationDate).setParameter(5, owner)
							.setParameter(6, lastModificationDate).setParameter(7, lastModifiedBy).setParameter(8, nameNlsid)
							.setParameter(9, nameSubsystem).setParameter(10, descriptionNlsid)
							.setParameter(11, descriptionSubsystem).setParameter(12, systemFolder).setParameter(13, emPluginId)
							.setParameter(14, uiHidden).setParameter(15, deleted).setParameter(16, folderId)
							.setParameter(17, tenantId).executeUpdate();
					logger.info("UpdateResult is {}",updateResult);
				}

			}
		}catch (Exception e) {
			logger.error("Error occured when sync folders table data! {}",e);
			throw new SyncException("Error occured when sync folders table data!");
		}
	}

	private boolean checkDbFolderConstraint(BigInteger folderId, String name, String creationDate, Integer systemFolder, Integer uiHidden, BigInteger deleted, Long tenantId) {
		if(folderId == null){
			logger.warn("folder Id can not be null");
			return true;
		}
		if(name == null){
			logger.warn("name can not be null");
			return true;
		}
		if(creationDate == null){
			logger.warn("creationDate can not be null");
			return true;
		}
		if(systemFolder == null){
			logger.warn("systemFolder can not be null");
			return true;
		}
		if(uiHidden == null){
			logger.warn("uiHidden can not be null");
			return true;
		}
		if(deleted == null){
			logger.warn("deleted can not be null");
			return true;
		}
		if(tenantId == null){
			logger.warn("tenantId can not be null");
			return true;
		}
		return false;
	}

	public void syncSearchParamsTable(EntityManager em, BigInteger searchId, String name, String paramAttributes, Long paramType,
			String paramValueStr, String paramValueClob, Long tenantId, String creationDate, String lastModificationDate, Integer deleted) throws SyncException {
		//check db constraint
		if (checkDbSearchParamsConstraint(searchId, name, paramType, tenantId, deleted)){
			return;
		}
		String sql = "select to_char(CREATION_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CREATION_DATE,to_char(t.LAST_MODIFICATION_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as LAST_MODIFICATION_DATE from EMS_ANALYTICS_SEARCH_PARAMS t "
				+ "where t.SEARCH_ID=? and t.NAME=? and t.TENANT_ID=?";//check if the data is existing.
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		Query q1 = em.createNativeQuery(sql).setParameter(1, searchId).setParameter(2, name).setParameter(3, tenantId);
		q1.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> result = q1.getResultList();
		boolean flag = true;//true=>insert,false=>update
		if (result != null && result.size() > 0) {
			flag = false;
		}

		try {
			if (flag) {
				//execute insert action
				logger.info("Data not exist in table EMS_ANALYTICS_SEARCH_PARAMS,execute insert action. search id is {}",searchId);
				int insertResult = em.createNativeQuery(SQL_INSERT_SEARCH_PARAM).setParameter(1, searchId).setParameter(2, name)
						.setParameter(3, paramAttributes).setParameter(4, paramType).setParameter(5, paramValueStr)
						.setParameter(6, paramValueClob).setParameter(7, tenantId).setParameter(8, creationDate)
						.setParameter(9, lastModificationDate).setParameter(10, deleted).executeUpdate();
				logger.info("InsertResult is {}",insertResult);
			}
			else {
				Map<String, Object> dateMap = result.get(0);

		    	String creationD  = dateMap.get("CREATION_DATE").toString();
		    	if (creationD == null) {
		    		return;
		    	}
		    	Object lastModifiedObj = dateMap.get("LAST_MODIFICATION_DATE");
		    	boolean check = false;
		    	if (lastModifiedObj == null || lastModificationDate == null) {
		    		check = isAfter(creationD,creationDate);
		    	} else {
		    		check = isAfter((String)lastModifiedObj, lastModificationDate);
		    	}
		    	if (check) {
					logger.info("Data's Last modification date is earlier, no update action is needed in table EMS_ANALYTICS_SEARCH_PARAMS.");
					//do nothing
				}
				else {
					//execute update action
					logger.info("Data exist in table EMS_ANALYTICS_SEARCH_PARAMS,execute update action.Search id is {}", searchId);
				    int updateResult = em.createNativeQuery(SQL_UPDATE_SEARCH_PARAM).setParameter(1, paramAttributes).setParameter(2, paramType)
							.setParameter(3, paramValueStr).setParameter(4, paramValueClob).setParameter(5, creationDate)
							.setParameter(6, lastModificationDate).setParameter(7, deleted).setParameter(8, searchId).setParameter(9, name)
							.setParameter(10, tenantId).executeUpdate();
					logger.info("UpdateResult is {}",updateResult);
				}

			}

		}
		catch (Exception e) {
			logger.error("Error occurred when sync search param table data! {}", e);
			throw  new SyncException("Error occurred when sync search param table data!");
		}
	}

	private boolean checkDbSearchParamsConstraint(BigInteger searchId, String name, Long paramType, Long tenantId, Integer deleted) {
		if(searchId == null){
			logger.warn("searchId can not be null");
			return true;
		}
		if(name == null){
			logger.warn("name can not be null");
			return true;
		}
		if(paramType == null){
			logger.warn("paramType can not be null");
			return true;
		}
		if(tenantId == null){
			logger.warn("tenantId can not be null");
			return true;
		}
		if(deleted == null){
			logger.warn("deleted can not be null");
			return true;
		}
		return false;
	}

	public void syncSearchTable(EntityManager em,BigInteger searchId, String name, String owner, String creationDate,
			String lastModificationDate, String lastModifiedBy, String description, BigInteger folderId, BigInteger categoryId,
			Integer systemSearch,Integer isLocked, String metaDataClob, String searchDisplayStr, Integer uiHidden, BigInteger deleted,
			Integer isWidget, Long tenantId, String nameWidgetSource, String widgetGroupName, String widgetScreenshotHref,
			String widgetIcon, String widgetKocName, String viewModel, String widgetTemplate, String widgetSupportTimeControl,
			Long widgetLinkedDashboard, Long widgetDefaultWidth, Long widgetDefaultHeight, String dashboardIneligible,
			String providerName, String providerVersion, String providerAssetRoot, Integer federationSupported) throws SyncException {
		//check db constraint
		if (checkDbSearchConstraint(searchId, name, owner, creationDate, folderId, categoryId, systemSearch, isLocked, uiHidden, deleted, isWidget, tenantId, federationSupported)){
			return;
		}
		String sql = "select to_char(CREATION_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CREATION_DATE,to_char(t.LAST_MODIFICATION_DATE,'yyyy-mm-dd hh24:mi:ss.ff3') as  LAST_MODIFICATION_DATE "
				+ "from EMS_ANALYTICS_SEARCH t where (t.SEARCH_ID=? and t.TENANT_ID=?) or "
				+ "(t.name = ? and t.folder_id = ? and t.category_id=? and t.deleted = ? "
				+ "and t.tenant_id = ? and t.owner = ?)";//check if the data is existing.
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		Query q1 = em.createNativeQuery(sql).setParameter(1, searchId).setParameter(2, tenantId)
				.setParameter(3, name)
				.setParameter(4, folderId)
				.setParameter(5, categoryId)
				.setParameter(6, deleted)
				.setParameter(7,tenantId)
				.setParameter(8,owner);
		q1.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> result = q1.getResultList();
		boolean flag = true;//true=>insert,false=>update
		if (result != null && result.size() > 0) {
			flag = false;
		}

		try {
			if (flag) {
				//execute insert action
				logger.info("Data not exist in table EMS_ANALYTICS_SEARCH,execute insert action. Search id is {}", searchId);
				int insertResult = em.createNativeQuery(SQL_INSERT_SEARCH).setParameter(1, searchId).setParameter(2, null).setParameter(3, name)
						.setParameter(4, owner).setParameter(5, creationDate).setParameter(6, lastModificationDate)
						.setParameter(7, lastModifiedBy).setParameter(8, description).setParameter(9, folderId)
						.setParameter(10, categoryId).setParameter(11, systemSearch).setParameter(12, isLocked).setParameter(13, metaDataClob)
						.setParameter(14, searchDisplayStr).setParameter(15, uiHidden).setParameter(16, deleted)
						.setParameter(17, isWidget).setParameter(18, tenantId).setParameter(19, nameWidgetSource)
						.setParameter(20, widgetGroupName).setParameter(21, widgetScreenshotHref).setParameter(22, widgetIcon)
						.setParameter(23, widgetKocName).setParameter(24, viewModel).setParameter(25, widgetTemplate)
						.setParameter(26, widgetSupportTimeControl).setParameter(27, widgetLinkedDashboard)
						.setParameter(28, widgetDefaultWidth).setParameter(29, widgetDefaultHeight)
						.setParameter(30, providerName).setParameter(31, providerVersion).setParameter(32, providerAssetRoot)
						.setParameter(33, dashboardIneligible).setParameter(34, federationSupported).executeUpdate();
				logger.info("InsertResult is {}",insertResult);
			}
			else {
				Map<String, Object> dateMap = result.get(0);
		    	String creationD  = dateMap.get("CREATION_DATE").toString();
		    	if (creationD == null) {
		    		return;
		    	}
		    	Object lastModifiedObj = dateMap.get("LAST_MODIFICATION_DATE");
		    	boolean check = false;
		    	if (lastModifiedObj == null || lastModificationDate == null) {
		    		check = isAfter(creationD,creationDate);
		    	} else {
		    		check = isAfter((String)lastModifiedObj, lastModificationDate);
		    	}

				if (check) {
					logger.info("Data's Last modification date is earlier, no update action is needed in table EMS_ANALYTICS_SEARCH.");
					//do nothing
				}
				else {
					//execute update action
					logger.info("Data exist in table EMS_ANALYTICS_SEARCH,execute update action. Search id is {}", searchId);
					int updateResult = em.createNativeQuery(SQL_UPDATE_SEARCH).setParameter(1, name).setParameter(2, owner)
							.setParameter(3, creationDate).setParameter(4, lastModificationDate).setParameter(5, lastModifiedBy)
							.setParameter(6, description).setParameter(7, folderId).setParameter(8, categoryId)
							.setParameter(9, systemSearch).setParameter(10, isLocked).setParameter(11, metaDataClob).setParameter(12, searchDisplayStr)
							.setParameter(13, uiHidden).setParameter(14, deleted).setParameter(15, isWidget)
							.setParameter(16, nameWidgetSource).setParameter(17, widgetGroupName)
							.setParameter(18, widgetScreenshotHref).setParameter(19, widgetIcon).setParameter(20, widgetKocName)
							.setParameter(21, viewModel).setParameter(22, widgetTemplate)
							.setParameter(23, widgetSupportTimeControl).setParameter(24, widgetLinkedDashboard)
							.setParameter(25, widgetDefaultWidth).setParameter(26, widgetDefaultHeight)
							.setParameter(27, providerName).setParameter(28, providerVersion).setParameter(29, providerAssetRoot)
							.setParameter(30, dashboardIneligible).setParameter(31, federationSupported).setParameter(32, searchId).setParameter(33, tenantId)
							.executeUpdate();
					logger.info("UpdateResult is {}",updateResult);
				}

			}

		}
		catch (Exception e) {
			logger.error("Error occured when sync search table data!{} "+e);
			throw new SyncException("Error occured when sync search table data! ");
		}
	}

	private boolean checkDbSearchConstraint(BigInteger searchId, String name, String owner, String creationDate, BigInteger folderId, BigInteger categoryId, Integer systemSearch, Integer isLocked, Integer uiHidden, BigInteger deleted, Integer isWidget, Long tenantId, Integer federationSupported) {
		if(searchId == null){
			logger.warn("searchId can not be null!");
			return true;
		}
		if(name == null){
			logger.warn("name can not be null!");
			return true;
		}
		if(owner == null){
			logger.warn("owner can not be null!");
			return true;
		}
		if(creationDate == null){
			logger.warn("creationDate can not be null!");
			return true;
		}
		if(folderId == null){
			logger.warn("folderId can not be null!");
			return true;
		}
		if(categoryId == null){
			logger.warn("categoryId can not be null!");
			return true;
		}
		if(systemSearch == null){
			logger.warn("systemSearch can not be null!");
			return true;
		}
		if(isLocked == null){
			logger.warn("isLocked can not be null!");
			return true;
		}
		if(uiHidden == null){
			logger.warn("uiHidden can not be null!");
			return true;
		}
		if(deleted == null){
			logger.warn("deleted can not be null!");
			return true;
		}
		if(isWidget == null){
			logger.warn("isWidget can not be null!");
			return true;
		}
		if(tenantId == null){
			logger.warn("tenantId can not be null!");
			return true;
		}
		if(federationSupported == null){
			logger.warn("federationSupported can not be null!");
			return true;
		}
		return false;
	}

	private boolean isAfter(String thisDate, String comparedDate){
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         try{
                 return simpleDateFormat.parse(thisDate).after(simpleDateFormat.parse(comparedDate));
         }catch (Exception e){
                 logger.error(e);
                 return false;
         }
 }

	/**
	 * Retrieves database data rows for specific native SQL for all tenant
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getDatabaseTableData(EntityManager em,String nativeSql, String date, String maxComparedDate, String tenant)
	{
		if (StringUtil.isEmpty(nativeSql)) {
			logger.error("Can't query database table with null or empty SQL statement!");
			return null;
		}
		List<Map<String, Object>> list = null;
		try {
			Query query = null;
			if (date != null) {
				if (maxComparedDate != null) {
					query = em.createNativeQuery(nativeSql).setParameter(1, date).setParameter(2, maxComparedDate);
				} else {
					query = em.createNativeQuery(nativeSql).setParameter(1, date);
				}

			} else {
				if (maxComparedDate != null) {
					if (tenant != null) {
						query = em.createNativeQuery(nativeSql).setParameter(1, maxComparedDate).setParameter(2, tenant);
					} else {
						query = em.createNativeQuery(nativeSql).setParameter(1, maxComparedDate);
					}
				} else {
					// impossible case
					query = em.createNativeQuery(nativeSql);
				}

			}
			query.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
			list = query.getResultList();
		}
		catch (Exception e) {
			logger.error("exception happens in getDatabaseTableData :"+e.getLocalizedMessage());
			logger.error("Error occured when execute SQL:[" + nativeSql + "]");
		}
		if (list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		return list;
	}

	private List<Object> getSingleTableData(EntityManager em, String nativeSql, String maxComparedDate) {
		if (StringUtil.isEmpty(nativeSql)) {
			logger.error("can not query database with empty sql statement!");
			return null;
		}
		List<Object>  result = null;
		try {
			Query query = null;
			if (maxComparedDate != null) {
				query = em.createNativeQuery(nativeSql).setParameter(1, maxComparedDate);
			} else {
				query = em.createNativeQuery(nativeSql);
			}

			result = query.getResultList();
		}catch(Exception e) {
			logger.error("exception happens in getSingleTableData :"+e.getLocalizedMessage());
			logger.error("error occurs while executing sql:" + nativeSql);
		}
		return result;
	}

	public int updateHalfSyncStatus(String syncResult, String type){

		logger.info("[Sync report]: Half Sync finished with result [{}] and type [{}]", syncResult, type);
		logger.info("Prepare to update half sync status.");
		EntityManager em = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			//check half-sync record number first
			checkHalfSyncRecord(em);
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			String sql = "update EMS_ANALYTICS_ZDT_SYNC set SYNC_RESULT= ? where SYNC_TYPE = 'half'";
			String sql2 = "update EMS_ANALYTICS_ZDT_SYNC set SYNC_RESULT= ?, SYNC_TYPE=? where SYNC_TYPE = 'half'";
			if(type !=null){
				em.createNativeQuery(sql).setParameter(1, syncResult).setParameter(2, type).executeUpdate();
			}else{
				em.createNativeQuery(sql).setParameter(1, syncResult).executeUpdate();
			}
			em.createNativeQuery(sql).setParameter(1, syncResult).executeUpdate();
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			logger.error("errors occurs in updateHalfSyncStatus,{}", e);
			return -1;
//			throw new HalfSyncException("Errors occurs in updateHalfSyncStatus");
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return 0;
	}


}
