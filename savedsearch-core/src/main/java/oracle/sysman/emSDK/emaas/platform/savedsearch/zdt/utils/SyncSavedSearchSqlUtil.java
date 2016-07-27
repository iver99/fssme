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
		String updateSql = "";

		return updateSql;
	}

	public static String concatCategoryParamInsert(Long categoryId, String name, String paramValue, Long tenantId,
			String creationDate, String lastModificationDate)
	{
		String insertSql = "";

		return insertSql;
	}

	public static String concatCategoryParamUpdate(Long categoryId, String name, String paramValue, Long tenantId,
			String creationDate, String lastModificationDate)
	{
		String insertSql = "";

		return insertSql;
	}

	public static String concatCategoryUpdate(Long categoryId, String name, String description, String owner,
			String creationDate, String nameNlsid, String nameSubSystem, String descriptionNlsid, String descriptionSubSystem,
			String emPluginId, Long defaultFolderId, Long deleted, String providerName, String providerVersion,
			String providerDiscovery, String providerAssetroot, Long tenantId, String dashboardIneligible,
			String lastModificationDate)
	{
		String updateSql = "";

		return updateSql;
	}

	public static String concatFolderInsert(Long folderId, String name, Long parentId, String description, String creationDate,
			String owner, String lastModificationDate, String lastModifiedBy, String nameNlsid, String nameSubsystem,
			String descriptionNlsid, String descriptionSubsystem, Integer systemFolder, String emPluginId, Integer uiHidden,
			Long deleted, Long tenantId)
	{
		String insertSql = "";

		return insertSql;
	}

	public static String concatFolderUpdate(Long folderId, String name, Long parentId, String description, String creationDate,
			String owner, String lastModificationDate, String lastModifiedBy, String nameNlsid, String nameSubsystem,
			String descriptionNlsid, String descriptionSubsystem, Integer systemFolder, String emPluginId, Integer uiHidden,
			Long deleted, Long tenantId)
	{
		String insertSql = "";

		return insertSql;
	}

	public static String concatLastAccessInsert(Long objectId, String accessedBy, Long objectType, String accessDate,
			Long tenantId, String creationDate, String lastModificationDate)
	{
		String insertSql = "";

		return insertSql;
	}

	public static String concatLastAccessUpdate(Long objectId, String accessedBy, Long objectType, String accessDate,
			Long tenantId, String creationDate, String lastModificationDate)
	{
		String insertSql = "";

		return insertSql;
	}

}
