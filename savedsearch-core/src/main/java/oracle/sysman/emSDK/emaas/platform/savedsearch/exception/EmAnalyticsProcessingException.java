/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.exception;

/**
 * @author vinjoshi
 */
public class EmAnalyticsProcessingException
{

	public static void processCategoryPersistantException(Exception ex, long defaultFolderId, String name)
			throws EMAnalyticsFwkException
	{

		EmAnalyticsProcessingException.processDataSourceException(ex);
		if (ex.getCause() != null && ex.getCause().getMessage().contains("ANALYTICS_SEARCH_FK1")) { // handles the scenario trying to delete category associated with search

			throw new EMAnalyticsFwkException("Error while deleting the category as it has associated searches",
					EMAnalyticsFwkException.ERR_DELETE_CATEGORY, null);
		}
		else if (ex.getCause() != null && ex.getCause().getMessage().contains("ANALYICS_CATEGORY_U01")) {
			throw new EMAnalyticsFwkException("Category name " + name + " already exist",
					EMAnalyticsFwkException.ERR_DUPLICATE_CATEGORY_NAME, new Object[] { name });
		}
		else if (ex.getCause() != null && ex.getCause().getMessage().contains("ANALYTICS_CATEGORY_FK1")) {
			throw new EMAnalyticsFwkException("Default folder with id " + defaultFolderId + " missing: " + name,
					EMAnalyticsFwkException.ERR_CATEGORY_INVALID_FOLDER, null);
		}
	}

	public static void processFolderPersistantException(Exception ex, long folderId, long parentId, String name)
			throws EMAnalyticsFwkException
	{
		EmAnalyticsProcessingException.processDataSourceException(ex);
		if (ex.getCause() != null && ex.getCause().getMessage().contains("ANALYTICS_SEARCH_FK2")) {
			throw new EMAnalyticsFwkException("folder with Id " + folderId + " has search child",
					EMAnalyticsFwkException.ERR_DELETE_FOLDER, null, ex);
		}
		else if (ex.getCause() != null && ex.getCause().getMessage().contains("ANALYTICS_SEARCH_FK1")) {
			throw new EMAnalyticsFwkException("folder with Id " + folderId + " has category child",
					EMAnalyticsFwkException.ERR_DELETE_FOLDER, null, ex);
		}
		else if (ex.getCause() != null && ex.getCause() != null && ex.getCause().getMessage().contains("ANALYTICS_FOLDERS_U01")) {
			throw new EMAnalyticsFwkException("Folder with name " + name + " already exist",
					EMAnalyticsFwkException.ERR_FOLDER_DUP_NAME, new Object[] { name });
		}

		else if (ex.getCause() != null && ex.getCause().getMessage().contains("ANALYTICS_FOLDERS_FK1")) {
			throw new EMAnalyticsFwkException("Parent folder with Id " + parentId + " does not exist: ",
					EMAnalyticsFwkException.ERR_FOLDER_INVALID_PARENT, null);
		}

	}

	public static void processSearchPersistantException(Exception ex, String name) throws EMAnalyticsFwkException
	{
		EmAnalyticsProcessingException.processDataSourceException(ex);
		if (ex.getCause() != null && ex.getCause().getMessage().contains("ANALYTICS_SEARCH_U01")) {
			throw new EMAnalyticsFwkException("Search name " + name + " already exist",
					EMAnalyticsFwkException.ERR_SEARCH_DUP_NAME, new Object[] { name });
		}
	}

	private static void processDataSourceException(Exception ex) throws EMAnalyticsFwkException
	{
		if (ex.getCause() != null && ex.getCause().getMessage().contains("Cannot acquire data source")) {

			throw new EMAnalyticsFwkException("Error while connecting to data source, please check the data source details: ",
					EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
		}
	}

}
