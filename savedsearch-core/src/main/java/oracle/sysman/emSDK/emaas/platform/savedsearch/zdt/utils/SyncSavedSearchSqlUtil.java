/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.utils;

import java.math.BigInteger;

/**
 * @author chehao
 */
public class SyncSavedSearchSqlUtil
{

	//	to_timestamp('" + lastModificationDate + "','yyyy-mm-dd hh24:mi:ss.ff')
	public static String concatCategoryInsert(Long categoryId, String name, String description, String owner,
			String creationDate, String nameNlsid, String nameSubSystem, String descriptionNlsid, String descriptionSubSystem,
			String emPluginId, Long defaultFolderId, Long deleted, String providerName, String providerVersion,
			String providerDiscovery, String providerAssetroot, Long tenantId, String dashboardIneligible,
			String lastModificationDate)
	{
		/*String updateSql = "INSERT INTO EMS_ANALYTICS_CATEGORY VALUES(" + categoryId + ",'" + name + "','" + description + "','"
				+ owner + "',to_timestamp('" + creationDate + "','yyyy-mm-dd hh24:mi:ss.ff'),'" + nameNlsid + "'," + "'"
				+ nameSubSystem + "','" + descriptionNlsid + "','" + descriptionSubSystem + "','" + emPluginId + "',"
				+ defaultFolderId + "," + deleted + ",'" + providerName + "'," + "'" + providerVersion + "','"
				+ providerDiscovery + "','" + providerAssetroot + "'," + tenantId + ",'" + dashboardIneligible
				+ "',to_timestamp('" + lastModificationDate + "','yyyy-mm-dd hh24:mi:ss.ff'))";*/
		String updateSql = "INSERT INTO EMS_ANALYTICS_CATEGORY (CATEGORY_ID,NAME,DESCRIPTION,OWNER,CREATION_DATE,"
				+ "NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,EM_PLUGIN_ID,"
				+ "DEFAULT_FOLDER_ID,DELETED,PROVIDER_NAME,PROVIDER_VERSION,PROVIDER_DISCOVERY,"
				+ "PROVIDER_ASSET_ROOT,TENANT_ID,DASHBOARD_INELIGIBLE,LAST_MODIFICATION_DATE) VALUES(?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
				+ "?,?,?,?,?,"
				+ "?,?,?,?,?,"
				+ "?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'))";

		return updateSql;
	}

	public static String concatCategoryParamInsert(Long categoryId, String name, String paramValue, Long tenantId,
			String creationDate, String lastModificationDate)
	{
		/*String insertSql = "INSERT INTO EMS_ANALYTICS_CATEGORY_PARAMS VALUES(" + categoryId + ",'" + name + "','" + paramValue
				+ "'," + tenantId + "," + "to_timestamp('" + creationDate + "','yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp('"
				+ lastModificationDate + "','yyyy-mm-dd hh24:mi:ss.ff'))";*/
		String insertSql = "INSERT INTO EMS_ANALYTICS_CATEGORY_PARAMS (CATEGORY_ID,NAME,PARAM_VALUE,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE) "
				+ "VALUES(?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'))";

		return insertSql;
	}

	public static String concatCategoryParamUpdate(Long categoryId, String name, String paramValue, Long tenantId,
			String creationDate, String lastModificationDate)
	{
		/*String insertSql = "UPDATE EMS_ANALYTICS_CATEGORY_PARAMS T SET T.PARAM_VALUE='" + paramValue
				+ "',T.CREATION_DATE=to_timestamp('" + creationDate + "','yyyy-mm-dd hh24:mi:ss.ff'),"
				+ "T.LAST_MODIFICATION_DATE=to_timestamp('" + lastModificationDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff') where T.CATEGORY_ID=" + categoryId + " and T.NAME='" + name
				+ "' and T.TENANT_ID=" + tenantId;*/
		String insertSql = "UPDATE EMS_ANALYTICS_CATEGORY_PARAMS T SET T.PARAM_VALUE=?,T.CREATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
				+ "T.LAST_MODIFICATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff') where T.CATEGORY_ID=? and T.NAME=? and T.TENANT_ID=?";

		return insertSql;
	}

	public static String concatCategoryUpdate(Long categoryId, String name, String description, String owner,
			String creationDate, String nameNlsid, String nameSubSystem, String descriptionNlsid, String descriptionSubSystem,
			String emPluginId, Long defaultFolderId, Long deleted, String providerName, String providerVersion,
			String providerDiscovery, String providerAssetroot, Long tenantId, String dashboardIneligible,
			String lastModificationDate)
	{
		/*String updateSql = "UPDATE EMS_ANALYTICS_CATEGORY t set t.NAME='" + name + "',t.DESCRIPTION='" + description
				+ "',t.OWNER='" + owner + "'," + "t.CREATION_DATE=to_timestamp('" + creationDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff'),t.NAME_NLSID='" + nameNlsid + "',t.NAME_SUBSYSTEM='" + nameSubSystem + "',"
				+ "t.DESCRIPTION_NLSID='" + descriptionNlsid + "',t.DESCRIPTION_SUBSYSTEM='" + descriptionSubSystem
				+ "',t.EM_PLUGIN_ID='" + emPluginId + "'," + "t.DEFAULT_FOLDER_ID=" + defaultFolderId + ",t.DELETED=" + deleted
				+ ",t.PROVIDER_NAME='" + providerName + "',t.PROVIDER_VERSION='" + providerVersion + "',"
				+ "t.PROVIDER_DISCOVERY='" + providerDiscovery + "',t.PROVIDER_ASSET_ROOT='" + providerAssetroot
				+ "',t.DASHBOARD_INELIGIBLE='" + dashboardIneligible + "',t.LAST_MODIFICATION_DATE=to_timestamp('"
				+ lastModificationDate + "','yyyy-mm-dd hh24:mi:ss.ff') where t.CATEGORY_ID=" + categoryId + " and t.TENANT_ID="
				+ tenantId;*/
		String updateSql = "UPDATE EMS_ANALYTICS_CATEGORY t set t.NAME=?,t.DESCRIPTION=?,t.OWNER=?,t.CREATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
				+ "t.NAME_NLSID=?,t.NAME_SUBSYSTEM=?,t.DESCRIPTION_NLSID=?,t.DESCRIPTION_SUBSYSTEM=?,t.EM_PLUGIN_ID=?,"
				+ "t.DEFAULT_FOLDER_ID=?,t.DELETED=?,t.PROVIDER_NAME=?,t.PROVIDER_VERSION=?,t.PROVIDER_DISCOVERY=?,"
				+ "t.PROVIDER_ASSET_ROOT=?,t.DASHBOARD_INELIGIBLE=?,t.LAST_MODIFICATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff') where t.CATEGORY_ID=? and t.TENANT_ID=?";

		return updateSql;
	}

	public static String concatFolderInsert(Long folderId, String name, Long parentId, String description, String creationDate,
			String owner, String lastModificationDate, String lastModifiedBy, String nameNlsid, String nameSubsystem,
			String descriptionNlsid, String descriptionSubsystem, Integer systemFolder, String emPluginId, Integer uiHidden,
			Long deleted, Long tenantId)
	{
		/*String insertSql = "INSERT INTO EMS_ANALYTICS_FOLDERS VALUES(" + folderId + ",'" + name + "'," + parentId + ",'"
				+ description + "',to_timestamp('" + creationDate + "','yyyy-mm-dd hh24:mi:ss.ff')," + "'" + owner
				+ "',to_timestamp('" + lastModificationDate + "','yyyy-mm-dd hh24:mi:ss.ff'),'" + lastModifiedBy + "','"
				+ nameNlsid + "','" + nameSubsystem + "','" + descriptionNlsid + "','" + descriptionSubsystem + "',"
				+ systemFolder + ",'" + emPluginId + "'," + uiHidden + "," + deleted + "," + tenantId + ")";*/
		String insertSql = "INSERT INTO EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,"
				+ "LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,"
				+ "DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,DELETED,TENANT_ID) VALUES(?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
				+ "?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),?,?,?,"
				+ "?,?,?,?,?,"
				+ "?,?)";

		return insertSql;
	}

	public static String concatFolderUpdate(Long folderId, String name, Long parentId, String description, String creationDate,
			String owner, String lastModificationDate, String lastModifiedBy, String nameNlsid, String nameSubsystem,
			String descriptionNlsid, String descriptionSubsystem, Integer systemFolder, String emPluginId, Integer uiHidden,
			Long deleted, Long tenantId)
	{
		/*String updateSql = "UPDATE EMS_ANALYTICS_FOLDERS t SET t.NAME='" + name + "',t.PARENT_ID=" + parentId
				+ ",t.DESCRIPTION='" + description + "'," + "t.CREATION_DATE=to_timestamp('" + creationDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff'),t.OWNER='" + owner + "',t.LAST_MODIFICATION_DATE=to_timestamp('"
				+ lastModificationDate + "','yyyy-mm-dd hh24:mi:ss.ff'),t.LAST_MODIFIED_BY='" + lastModifiedBy + "',"
				+ "t.NAME_NLSID='" + nameNlsid + "',t.NAME_SUBSYSTEM='" + nameSubsystem + "',t.DESCRIPTION_NLSID='"
				+ descriptionNlsid + "',t.DESCRIPTION_SUBSYSTEM='" + descriptionSubsystem + "'," + "t.SYSTEM_FOLDER="
				+ systemFolder + ",t.EM_PLUGIN_ID='" + emPluginId + "',t.UI_HIDDEN=" + uiHidden + ",t.DELETED=" + deleted
				+ " where t.FOLDER_ID=" + folderId + " and t.TENANT_ID=" + tenantId + "";*/
		String updateSql = "UPDATE EMS_ANALYTICS_FOLDERS t SET t.NAME=?,t.PARENT_ID=?,t.DESCRIPTION=?,t.CREATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
				+ "t.OWNER=?,t.LAST_MODIFICATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),t.LAST_MODIFIED_BY=?,t.NAME_NLSID=?,"
				+ "t.NAME_SUBSYSTEM=?,t.DESCRIPTION_NLSID=?,t.DESCRIPTION_SUBSYSTEM=?,t.SYSTEM_FOLDER=?,t.EM_PLUGIN_ID=?,"
				+ "t.UI_HIDDEN=?,t.DELETED=? where t.FOLDER_ID=? and t.TENANT_ID=?";

		return updateSql;
	}

	public static String concatLastAccessInsert(Long objectId, String accessedBy, Long objectType, String accessDate,
			Long tenantId, String creationDate, String lastModificationDate)
	{
		/*String insertSql = "INSERT INTO EMS_ANALYTICS_LAST_ACCESS VALUES(" + objectId + ",'" + accessedBy + "'," + objectType
				+ ",to_timestamp('" + accessDate + "','yyyy-mm-dd hh24:mi:ss.ff')," + tenantId + ",to_timestamp('" + creationDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp('" + lastModificationDate + "','yyyy-mm-dd hh24:mi:ss.ff'))";*/
		String insertSql = "INSERT INTO EMS_ANALYTICS_LAST_ACCESS (OBJECT_ID,ACCESSED_BY,OBJECT_TYPE,ACCESS_DATE,"
				+ "TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE) VALUES(?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'))";

		return insertSql;
	}

	public static String concatLastAccessUpdate(Long objectId, String accessedBy, Long objectType, String accessDate,
			Long tenantId, String creationDate, String lastModificationDate)
	{
		/*String updateSql = "UPDATE EMS_ANALYTICS_LAST_ACCESS t SET t.ACCESS_DATE=to_timestamp('" + accessDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff'),t.CREATION_DATE=to_timestamp('" + creationDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff')," + "t.LAST_MODIFICATION_DATE=to_timestamp('" + lastModificationDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff') " + "where t.OBJECT_ID=" + objectId + " and t.ACCESSED_BY='" + accessedBy
				+ "' and t.OBJECT_TYPE=" + objectType + " and t.TENANT_ID=" + tenantId + "";
*/
		String updateSql =	"UPDATE EMS_ANALYTICS_LAST_ACCESS t SET t.ACCESS_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),t.CREATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),t.LAST_MODIFICATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff') "
				+ "where t.OBJECT_ID=? and t.ACCESSED_BY=? and t.OBJECT_TYPE=? and t.TENANT_ID=?";
		return updateSql;
	}

	public static String concatSearchInsert(Long searchId, String searchGuid, String name, String owner, String creationDate,
			String lastModificationDate, String lastModifiedBy, String description, Long folderId, Long categoryId,
			String nameNlsid, String nameSubsystem, String descriptionNlsid, String descriptionSubsystem, Integer systemSearch,
			String emPluginId, Integer isLocked, String metaDataClob, String searchDisplayStr, Integer uiHidden, Long deleted,
			Integer isWidget, Long tenantId, String nameWidgetSource, String widgetGroupName, String widgetScreenshotHref,
			String widgetIcon, String widgetKocName, String viewModel, String widgetTemplate, String widgetSupportTimeControl,
			Long widgetLinkedDashboard, Long widgetDefaultWidth, Long widgetDefaultHeight, String dashboardIneligible,
			String providerName, String providerVersion, String providerAssetRoot)
	{
		/*String insertSql = "INSERT INTO EMS_ANALYTICS_SEARCH VALUES(" + searchId + ",'" + searchGuid + "','" + name + "','"
				+ owner + "',to_timestamp('" + creationDate + "','yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp('"
				+ lastModificationDate + "','yyyy-mm-dd hh24:mi:ss.ff'),'" + lastModifiedBy + "'," + "'" + description + "',"
				+ folderId + "," + categoryId + ",'" + nameNlsid + "','" + nameSubsystem + "','" + descriptionNlsid + "','"
				+ descriptionSubsystem + "'," + systemSearch + ",'" + emPluginId + "'," + isLocked + ",'" + metaDataClob + "','"
				+ searchDisplayStr + "'," + "" + uiHidden + "," + deleted + "," + isWidget + "," + tenantId + ",'"
				+ nameWidgetSource + "','" + widgetGroupName + "','" + widgetScreenshotHref + "','" + widgetIcon + "','"
				+ widgetKocName + "','" + viewModel + "','" + widgetTemplate + "'," + "'" + widgetSupportTimeControl + "',"
				+ widgetLinkedDashboard + "," + widgetDefaultWidth + "," + widgetDefaultHeight + ",'" + providerName + "','"
				+ providerVersion + "','" + providerAssetRoot + "','" + dashboardIneligible + "')";//look out the order of 3 provider* columns and dashboard_ineligible
		 */
		String insertSql = "INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,SEARCH_GUID,NAME,OWNER,CREATION_DATE,"
				+ "LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,"
				+ "NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,"
				+ "EM_PLUGIN_ID,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,"
				+ "DELETED,IS_WIDGET,TENANT_ID,NAME_WIDGET_SOURCE,WIDGET_GROUP_NAME,"
				+ "WIDGET_SCREENSHOT_HREF,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,"
				+ "WIDGET_SUPPORT_TIME_CONTROL,WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,PROVIDER_NAME,"
				+ "PROVIDER_VERSION,PROVIDER_ASSET_ROOT,DASHBOARD_INELIGIBLE) VALUES(?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff')," + "to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),?,?,?,?," + "?,?,?,?,?,"
				+ "?,?,?,?,?," + "?,?,?,?,?," + "?,?,?,?,?," + "?,?,?,?,?," + "?,?,?)";
		return insertSql;
	}

	public static String concatSearchParamsInsert(BigInteger searchId, String name, String paramAttributes, Long paramType,
			String paramValueStr, String paramValueClob, Long tenantId, String creationDate, String lastModificationDate)
	{
		/*String insertSql = "INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS VALUES(" + searchId + ",'" + name + "','" + paramAttributes
				+ "'," + paramType + "," + "'" + paramValueStr + "','" + paramValueClob + "'," + tenantId + ",to_timestamp('"
				+ creationDate + "','yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp('" + lastModificationDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff'))";*/
		String insertSql = "INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,"
				+ "PARAM_VALUE_CLOB,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE) VALUES(?,?,?,?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'))";

		return insertSql;
	}

	public static String concatSearchParamsUpdate(BigInteger searchId, String name, String paramAttributes, Long paramType,
			String paramValueStr, String paramValueClob, Long tenantId, String creationDate, String lastModificationDate)
	{
		/*String updateSql = "UPDATE EMS_ANALYTICS_SEARCH_PARAMS t set t.PARAM_ATTRIBUTES='" + paramAttributes + "',t.PARAM_TYPE="
				+ paramType + "," + "t.PARAM_VALUE_STR='" + paramValueStr + "',t.PARAM_VALUE_CLOB='" + paramValueClob
				+ "',t.CREATION_DATE=to_timestamp('" + creationDate + "','yyyy-mm-dd hh24:mi:ss.ff'),"
				+ "t.LAST_MODIFICATION_DATE=to_timestamp('" + lastModificationDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff') where t.SEARCH_ID=" + searchId + " and t.NAME='" + name + "' and t.TENANT_ID="
				+ tenantId + "";*/
		String updateSql = "UPDATE EMS_ANALYTICS_SEARCH_PARAMS t set t.PARAM_ATTRIBUTES=?,t.PARAM_TYPE=?,t.PARAM_VALUE_STR=?,"
				+ "t.PARAM_VALUE_CLOB=?,t.CREATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'), t.LAST_MODIFICATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff') where t.SEARCH_ID=? and t.NAME=? and t.TENANT_ID=?";

		return updateSql;
	}

	public static String concatSearchUpdate(Long searchId, String searchGuid, String name, String owner, String creationDate,
			String lastModificationDate, String lastModifiedBy, String description, Long folderId, Long categoryId,
			String nameNlsid, String nameSubsystem, String descriptionNlsid, String descriptionSubsystem, Integer systemSearch,
			String emPluginId, Integer isLocked, String metaDataClob, String searchDisplayStr, Integer uiHidden, Long deleted,
			Integer isWidget, Long tenantId, String nameWidgetSource, String widgetGroupName, String widgetScreenshotHref,
			String widgetIcon, String widgetKocName, String viewModel, String widgetTemplate, String widgetSupportTimeControl,
			Long widgetLinkedDashboard, Long widgetDefaultWidth, Long widgetDefaultHeight, String dashboardIneligible,
			String providerName, String providerVersion, String providerAssetRoot)
	{
		/*String updateSql = "UPDATE EMS_ANALYTICS_SEARCH t set t.SEARCH_GUID='" + searchGuid + "',t.NAME='" + name + "',t.OWNER='"
				+ owner + "'," + "t.CREATION_DATE=to_timestamp('" + creationDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff'),t.LAST_MODIFICATION_DATE=to_timestamp('" + lastModificationDate
				+ "','yyyy-mm-dd hh24:mi:ss.ff'),t.LAST_MODIFIED_BY='" + lastModifiedBy + "'," + "t.DESCRIPTION='" + description
				+ "',t.FOLDER_ID=" + folderId + ",t.CATEGORY_ID=" + categoryId + ",t.NAME_NLSID='" + nameNlsid
				+ "',t.NAME_SUBSYSTEM='" + nameSubsystem + "'," + "t.DESCRIPTION_NLSID='" + descriptionNlsid
				+ "',t.DESCRIPTION_SUBSYSTEM='" + descriptionSubsystem + "',t.SYSTEM_SEARCH=" + systemSearch
				+ ",t.EM_PLUGIN_ID='" + systemSearch + "'," + "t.IS_LOCKED=" + isLocked + ",t.METADATA_CLOB='" + metaDataClob
				+ "',t.SEARCH_DISPLAY_STR='" + searchDisplayStr + "',t.UI_HIDDEN=" + uiHidden + ",t.DELETED=" + deleted + ","
				+ "t.IS_WIDGET=" + isWidget + ",t.NAME_WIDGET_SOURCE='" + nameWidgetSource + "',t.WIDGET_GROUP_NAME='"
				+ widgetGroupName + "'," + "t.WIDGET_SCREENSHOT_HREF='" + widgetScreenshotHref + "',t.WIDGET_ICON='" + widgetIcon
				+ "',t.WIDGET_KOC_NAME='" + widgetKocName + "',t.WIDGET_VIEWMODEL='" + viewModel + "'," + "t.WIDGET_TEMPLATE='"
				+ widgetTemplate + "',t.WIDGET_SUPPORT_TIME_CONTROL='" + widgetSupportTimeControl
				+ "',t.WIDGET_LINKED_DASHBOARD=" + widgetLinkedDashboard + "," + "t.WIDGET_DEFAULT_WIDTH=" + widgetDefaultWidth
				+ ",t.WIDGET_DEFAULT_HEIGHT=" + widgetDefaultHeight + ",t.PROVIDER_NAME='" + providerName + "',"
				+ "t.PROVIDER_VERSION='" + providerVersion + "',t.PROVIDER_ASSET_ROOT='" + providerAssetRoot
				+ "',t.DASHBOARD_INELIGIBLE='" + dashboardIneligible + "' " + "where t.SEARCH_ID=" + searchId
				+ " and t.TENANT_ID=" + tenantId;//look out the order of 3 provider* columns and dashboard_ineligible
*/
		String updateSql="UPDATE EMS_ANALYTICS_SEARCH t set t.SEARCH_GUID=?,t.NAME=?,t.OWNER=?,t.CREATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),t.LAST_MODIFICATION_DATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff'),"
				+ "t.LAST_MODIFIED_BY=?,t.DESCRIPTION=?,t.FOLDER_ID=?,t.CATEGORY_ID=?,t.NAME_NLSID=?,t.NAME_SUBSYSTEM=?,t.DESCRIPTION_NLSID=?,"
				+ "t.DESCRIPTION_SUBSYSTEM=?,t.SYSTEM_SEARCH=?,t.EM_PLUGIN_ID=?,t.IS_LOCKED=?,t.METADATA_CLOB=?,t.SEARCH_DISPLAY_STR=?,t.UI_HIDDEN=?,"
				+ "t.DELETED=?,t.IS_WIDGET=?,t.NAME_WIDGET_SOURCE=?,t.WIDGET_GROUP_NAME=?,t.WIDGET_SCREENSHOT_HREF=?,t.WIDGET_ICON=?,"
				+ "t.WIDGET_KOC_NAME=?,t.WIDGET_VIEWMODEL=?,t.WIDGET_TEMPLATE=?,t.WIDGET_SUPPORT_TIME_CONTROL=?,t.WIDGET_LINKED_DASHBOARD=?,"
				+ "t.WIDGET_DEFAULT_WIDTH=?,t.WIDGET_DEFAULT_HEIGHT=?,t.PROVIDER_NAME=?,t.PROVIDER_VERSION=?,t.PROVIDER_ASSET_ROOT=?,"
				+ "t.DASHBOARD_INELIGIBLE=? where t.SEARCH_ID=? and t.TENANT_ID=?";
		return updateSql;
	}

}
