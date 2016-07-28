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

	public static String concatCategoryInsert(Long categoryId, String name, String description, String owner,
			String creationDate, String nameNlsid, String nameSubSystem, String descriptionNlsid, String descriptionSubSystem,
			String emPluginId, Long defaultFolderId, Long deleted, String providerName, String providerVersion,
			String providerDiscovery, String providerAssetroot, Long tenantId, String dashboardIneligible,
			String lastModificationDate)
	{
		String updateSql = "INSERT INTO EMS_ANALYTICS_CATEGORY VALUES(" + categoryId + ",'" + name + "','" + description + "','"
				+ owner + "','" + creationDate + "','" + nameNlsid + "'," + "'" + nameSubSystem + "','" + descriptionNlsid
				+ "','" + descriptionSubSystem + "','" + emPluginId + "'," + defaultFolderId + "," + deleted + ",'"
				+ providerName + "'," + "'" + providerVersion + "','" + providerDiscovery + "','" + providerAssetroot + "',"
				+ tenantId + ",'" + dashboardIneligible + "','" + lastModificationDate + "')";

		return updateSql;
	}

	public static String concatCategoryParamInsert(Long categoryId, String name, String paramValue, Long tenantId,
			String creationDate, String lastModificationDate)
	{
		String insertSql = "INSERT INTO EMS_ANALYTICS_CATEGORY_PARAMS VALUES(" + categoryId + ",'" + name + "','" + paramValue
				+ "'," + tenantId + "," + "'" + creationDate + "','" + lastModificationDate + "')";

		return insertSql;
	}

	public static String concatCategoryParamUpdate(Long categoryId, String name, String paramValue, Long tenantId,
			String creationDate, String lastModificationDate)
	{
		String insertSql = "UPDATE EMS_ANALYTICS_CATEGORY_PARAMS T SET T.PARAM_VALUE='" + paramValue + "',T.CREATION_DATE='"
				+ creationDate + "'," + "T.LAST_MODIFICATION_DATE='" + lastModificationDate + "' where T.CATEGORY_ID="
				+ categoryId + " and T.NAME='" + name + "' and T.TENANT_ID=" + tenantId;

		return insertSql;
	}

	public static String concatCategoryUpdate(Long categoryId, String name, String description, String owner,
			String creationDate, String nameNlsid, String nameSubSystem, String descriptionNlsid, String descriptionSubSystem,
			String emPluginId, Long defaultFolderId, Long deleted, String providerName, String providerVersion,
			String providerDiscovery, String providerAssetroot, Long tenantId, String dashboardIneligible,
			String lastModificationDate)
	{
		String updateSql = "UPDATE EMS_ANALYTICS_CATEGORY t set t.NAME='" + name + "',t.DESCRIPTION='" + description
				+ "',t.OWNER='" + owner + "'," + "t.CREATION_DATE='" + creationDate + "',t.NAME_NLSID='" + nameNlsid
				+ "',t.NAME_SUBSYSTEM='" + nameSubSystem + "'," + "t.DESCRIPTION_NLSID='" + descriptionNlsid
				+ "',t.DESCRIPTION_SUBSYSTEM='" + descriptionSubSystem + "',t.EM_PLUGIN_ID='" + emPluginId + "',"
				+ "t.DEFAULT_FOLDER_ID=" + defaultFolderId + ",t.DELETED=" + deleted + ",t.PROVIDER_NAME='" + providerName
				+ "',t.PROVIDER_VERSION='" + providerVersion + "'," + "t.PROVIDER_DISCOVERY='" + providerDiscovery
				+ "',t.PROVIDER_ASSET_ROOT='" + providerAssetroot + "' where t.CATEGORY_ID=" + categoryId + " and t.TENANT_ID="
				+ tenantId;

		return updateSql;
	}

	public static String concatFolderInsert(Long folderId, String name, Long parentId, String description, String creationDate,
			String owner, String lastModificationDate, String lastModifiedBy, String nameNlsid, String nameSubsystem,
			String descriptionNlsid, String descriptionSubsystem, Integer systemFolder, String emPluginId, Integer uiHidden,
			Long deleted, Long tenantId)
	{
		String insertSql = "INSERT INTO EMS_ANALYTICS_FOLDERS VALUES(" + folderId + ",'" + name + "'," + parentId + ",'"
				+ description + "','" + creationDate + "'," + "'" + owner + "','" + lastModificationDate + "','" + lastModifiedBy
				+ "','" + nameNlsid + "','" + nameSubsystem + "','" + descriptionNlsid + "','" + descriptionSubsystem + "',"
				+ systemFolder + ",'" + emPluginId + "'," + uiHidden + "," + deleted + "," + tenantId + ")";

		return insertSql;
	}

	public static String concatFolderUpdate(Long folderId, String name, Long parentId, String description, String creationDate,
			String owner, String lastModificationDate, String lastModifiedBy, String nameNlsid, String nameSubsystem,
			String descriptionNlsid, String descriptionSubsystem, Integer systemFolder, String emPluginId, Integer uiHidden,
			Long deleted, Long tenantId)
	{
		String updateSql = "UPDATE EMS_ANALYTICS_FOLDERS t SET t.NAME='" + name + "',t.PARENT_ID=" + parentId
				+ ",t.DESCRIPTION='" + description + "'," + "t.CREATION_DATE='" + creationDate + "',t.OWNER='" + owner
				+ "',t.LAST_MODIFICATION_DATE='" + lastModificationDate + "',t.LAST_MODIFIED_BY='" + lastModifiedBy + "',"
				+ "t.NAME_NLSID='" + nameNlsid + "',t.NAME_SUBSYSTEM='" + nameSubsystem + "',t.DESCRIPTION_NLSID='"
				+ descriptionNlsid + "',t.DESCRIPTION_SUBSYSTEM='" + descriptionSubsystem + "'," + "t.SYSTEM_FOLDER="
				+ systemFolder + ",t.EM_PLUGIN_ID='" + emPluginId + "',t.UI_HIDDEN=" + uiHidden + ",t.DELETED=" + deleted
				+ " where t.FOLDER_ID=" + folderId + " and t.TENANT_ID=" + tenantId + "";

		return updateSql;
	}

	public static String concatLastAccessInsert(Long objectId, String accessedBy, Long objectType, String accessDate,
			Long tenantId, String creationDate, String lastModificationDate)
	{
		String insertSql = "INSERT INTO EMS_ANALYTICS_LAST_ACCESS VALUES(" + objectId + ",'" + accessedBy + "'," + objectType
				+ ",'" + accessDate + "'," + tenantId + ",'" + creationDate + "','" + lastModificationDate + "')";

		return insertSql;
	}

	public static String concatLastAccessUpdate(Long objectId, String accessedBy, Long objectType, String accessDate,
			Long tenantId, String creationDate, String lastModificationDate)
	{
		String updateSql = "UPDATE EMS_ANALYTICS_LAST_ACCESS t SET t.ACCESS_DATE='" + accessDate + "',t.CREATION_DATE='"
				+ creationDate + "'," + "t.LAST_MODIFICATION_DATE='" + lastModificationDate + "' " + "where t.OBJECT_ID="
				+ objectId + " and t.ACCESSED_BY='" + accessedBy + "' and t.OBJECT_TYPE=" + objectType + " and t.TENANT_ID="
				+ tenantId + "";

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
		String insertSql = "INSERT INTO EMS_ANALYTICS_SEARCH VALUES(" + searchId + ",'" + searchGuid + "','" + name + "'.'"
				+ owner + "','" + creationDate + "','" + lastModificationDate + "','" + lastModifiedBy + "'," + "'" + description
				+ "'," + folderId + "," + categoryId + ",'" + nameNlsid + "','" + nameSubsystem + "','" + descriptionNlsid
				+ "','" + descriptionSubsystem + "'," + systemSearch + ",'" + emPluginId + "'," + isLocked + ",'" + metaDataClob
				+ "','" + searchDisplayStr + "'," + "" + uiHidden + "," + deleted + "," + isWidget + "," + tenantId + ",'"
				+ nameWidgetSource + "','" + widgetGroupName + "','" + widgetScreenshotHref + "',''" + widgetIcon + "','"
				+ widgetKocName + "','" + viewModel + "','" + widgetTemplate + "'," + "'" + widgetSupportTimeControl + "',"
				+ widgetLinkedDashboard + "," + widgetDefaultWidth + "," + widgetDefaultHeight + ",'" + providerName + "','"
				+ providerVersion + "','" + providerAssetRoot + "','" + dashboardIneligible + "')";//look out the order of 3 provider* columns and dashboard_ineligible

		return insertSql;
	}

	public static String concatSearchParamsInsert(BigInteger searchId, String name, String paramAttributes, Long paramType,
			String paramValueStr, String paramValueClob, Long tenantId, String creationDate, String lastModificationDate)
	{
		String insertSql = "INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS VALUES(" + searchId + ",'" + name + "','" + paramAttributes
				+ "'," + paramType + "," + "'" + paramValueStr + "','" + paramValueClob + "'," + tenantId + ",'" + creationDate
				+ "','" + lastModificationDate + "')";

		return insertSql;
	}

	public static String concatSearchParamsUpdate(BigInteger searchId, String name, String paramAttributes, Long paramType,
			String paramValueStr, String paramValueClob, Long tenantId, String creationDate, String lastModificationDate)
	{
		String updateSql = "UPDATE EMS_ANALYTICS_SEARCH_PARAMS t set t.PARAM_ATTRIBUTES='" + paramAttributes + "',t.PARAM_TYPE="
				+ paramType + "," + "t.PARAM_VALUE_STR='" + paramValueStr + "',t.PARAM_VALUE_CLOB='" + paramValueClob
				+ "',t.CREATION_DATE='" + creationDate + "'," + "t.LAST_MODIFICATION_DATE='" + lastModificationDate
				+ "' where t.SEARCH_ID=" + searchId + " and t.NAME='" + name + "' and t.TENANT_ID=" + tenantId + "";

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
		String updateSql = "UPDATE EMS_ANALYTICS_SEARCH_PARAMS t set t.SEARCH_GUID='" + searchGuid + "',t.NAME='" + name
				+ "',t.OWNER='" + owner + "'," + "t.CREATION_DATE='" + creationDate + "',t.LAST_MODIFICATION_DATE='"
				+ lastModificationDate + "',t.LAST_MODIFIED_BY='" + lastModifiedBy + "'," + "t.DESCRIPTION='" + description
				+ "',t.FOLDER_ID=" + folderId + ",t.CATEGORY_ID=" + categoryId + ",t.NAME_NLSID='" + nameNlsid
				+ "',t.NAME_SUBSYSTEM='" + nameSubsystem + "'," + "t.DESCRIPTION_NLSID='" + descriptionNlsid
				+ "',t.DESCRIPTION_SUBSSYSTEM='" + descriptionSubsystem + "',t.SYSTEM_SEARCH=" + systemSearch
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

		return updateSql;
	}

}
