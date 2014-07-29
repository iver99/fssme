/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.net.URI;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkJsonException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Create JSON string for each entity (search/folder/category) and return to user
 *
 * @author miayu
 */
public class EntityJsonUtil
{
	public static final String NAME_ID = "id";

	public static final String NAME_GUID = "guid";

	public static final String NAME_NAME = "name";

	public static final String NAME_DESCRIPTION = "description";

	public static final String NAME_OWNER = "owner";

	public static final String NAME_CREATEDON = "createdOn";

	public static final String NAME_LASTMODIFIEDBY = "lastModifiedBy";

	public static final String NAME_LASTMODIFIEDON = "lastModifiedOn";

	public static final String NAME_LASTACCESSDATE = "lastAccessDate";

	public static final String NAME_HREF = "href";

	public static final String NAME_PARAMETERS = "parameters";

	public static final String NAME_TYPE = "type";

	public static final String NAME_CATEGORY_ID = "categoryId";

	public static final String NAME_CATEGORY = "category";
	public static final String NAME_CATEGORY_DEFAULTFOLDERID = "defaultFolderId";
	public static final String NAME_CATEGORY_DEFAULTFOLDER = "defaultFolder";
	public static final String NAME_FOLDER_ID = "folderId";
	public static final String NAME_FOLDER_PARENTID = "parentId";

	public static final String NAME_FOLDER_PARENTFOLDER = "parentFolder";
	public static final String NAME_FOLDER_SYSTEMFOLDER = "systemFolder";

	public static final String NAME_FOLDER = "folder";
	public static final String NAME_FOLDER_UIHIDDEN = "uiHidden";

	public static final String NAME_SEARCH = "search";
	public static final String NAME_SEARCH_QUERYSTR = "queryStr";
	public static final String NAME_SEARCH_LOCKED = "locked";

	public static final String NAME_SEARCH_UIHIDDEN = "uiHidden";
	public static final String NAME_SEARCH_FOLDERPATH = "flattenedFolderPath";
	public static final String PATH_FOLDER = "folder/";
	public static final String PATH_SEARCH = "search/";

	public static final String PATH_CATEGORY = "category/";
	public static final String TYPE_SEARCH = "search";
	public static final String TYPE_FOLDER = "folder";

	/**
	 * Return full JSON string for category
	 *
	 * @param baseUri
	 * @param category
	 * @return
	 * @throws JSONException
	 * @throws EMAnalyticsFwkJsonException
	 */
	public static JSONObject getFullCategoryJsonObj(URI baseUri, Category category) throws JSONException,
	EMAnalyticsFwkJsonException
	{
		return EntityJsonUtil.getCategoryJsonObj(baseUri, category, null, false);
	}

	/**
	 * Return full JSON string for folder
	 *
	 * @param baseUri
	 * @param folder
	 * @return
	 * @throws JSONException
	 * @throws EMAnalyticsFwkJsonException
	 */
	public static JSONObject getFullFolderJsonObj(URI baseUri, Folder folder) throws JSONException, EMAnalyticsFwkJsonException
	{
		return EntityJsonUtil.getFolderJsonObj(baseUri, folder, new String[] { NAME_FOLDER_UIHIDDEN }, false, false);
	}

	/**
	 * Return full JSON string for search
	 *
	 * @param baseUri
	 * @param jsonObj
	 * @return
	 * @throws JSONException
	 * @throws EMAnalyticsFwkJsonException
	 */
	public static JSONObject getFullSearchJsonObj(URI baseUri, Search search) throws JSONException, EMAnalyticsFwkJsonException
	{
		return EntityJsonUtil.getFullSearchJsonObj(baseUri, search, null);
	}

	/**
	 * Return full JSON string for search
	 *
	 * @param baseUri
	 * @param jsonObj
	 * @param folderPathArray
	 *            full folder path in array
	 * @return
	 * @throws JSONException
	 * @throws EMAnalyticsFwkJsonException
	 */
	public static JSONObject getFullSearchJsonObj(URI baseUri, Search search, String[] folderPathArray) throws JSONException,
			EMAnalyticsFwkJsonException
	{
		return EntityJsonUtil.getSearchJsonObj(baseUri, search, new String[] { NAME_GUID, NAME_SEARCH_LOCKED,
				NAME_SEARCH_UIHIDDEN }, folderPathArray, false);
	}

	/**
	 * Return simple JSON string for category (without default folder and parameters)
	 *
	 * @param baseUri
	 * @param category
	 * @return
	 * @throws JSONException
	 * @throws EMAnalyticsFwkJsonException
	 */
	public static JSONObject getSimpleCategoryJsonObj(URI baseUri, Category category) throws JSONException,
			EMAnalyticsFwkJsonException
	{
		return EntityJsonUtil.getCategoryJsonObj(baseUri, category, new String[] { NAME_OWNER, NAME_CATEGORY_DEFAULTFOLDER,
				NAME_PARAMETERS }, true);
	}

	/**
	 * Return simple JSON string for folder without folder=type
	 *
	 * @param baseUri
	 * @param folder
	 * @return
	 * @throws JSONException
	 * @throws EMAnalyticsFwkJsonException
	 */
	public static JSONObject getSimpleFolderJsonObj(URI baseUri, Folder folder) throws JSONException, EMAnalyticsFwkJsonException
	{
		return EntityJsonUtil.getSimpleFolderJsonObj(baseUri, folder, false);
	}

	/**
	 * Return simple JSON string for folder with or without type=folder
	 *
	 * @param baseUri
	 * @param folder
	 * @param includeType
	 * @return
	 * @throws JSONException
	 * @throws EMAnalyticsFwkJsonException
	 */
	public static JSONObject getSimpleFolderJsonObj(URI baseUri, Folder folder, boolean includeType) throws JSONException,
	EMAnalyticsFwkJsonException
	{
		return EntityJsonUtil.getFolderJsonObj(baseUri, folder, new String[] { NAME_OWNER, NAME_LASTMODIFIEDBY,
				NAME_FOLDER_PARENTFOLDER, NAME_FOLDER_UIHIDDEN }, true, includeType);
	}

	/**
	 * Return JSON string for search without queryStr and parameters
	 *
	 * @param uri
	 * @param jsonObj
	 * @return
	 * @throws JSONException
	 * @throws EMAnalyticsFwkJsonException
	 */
	public static JSONObject getSimpleSearchJsonObj(URI baseUri, Search search) throws JSONException, EMAnalyticsFwkJsonException
	{
		return EntityJsonUtil.getSimpleSearchJsonObj(baseUri, search, false);
	}

	/**
	 * Return JSON string for search without queryStr and parameters
	 *
	 * @param uri
	 * @param jsonObj
	 * @param includeType
	 * @return
	 * @throws JSONException
	 * @throws EMAnalyticsFwkJsonException
	 */
	public static JSONObject getSimpleSearchJsonObj(URI baseUri, Search search, boolean includeType) throws JSONException,
			EMAnalyticsFwkJsonException
	{
		return EntityJsonUtil.getSimpleSearchJsonObj(baseUri, search, null, includeType);
	}

	/**
	 * Return JSON string for search without queryStr and parameters
	 *
	 * @param baseUri
	 * @param search
	 * @param folderPathArray
	 * @param includeType
	 * @return
	 * @throws JSONException
	 * @throws EMAnalyticsFwkJsonException
	 */
	public static JSONObject getSimpleSearchJsonObj(URI baseUri, Search search, String[] folderPathArray, boolean includeType)
			throws JSONException, EMAnalyticsFwkJsonException
	{
		return EntityJsonUtil.getSearchJsonObj(baseUri, search, new String[] { NAME_GUID, NAME_OWNER, NAME_LASTMODIFIEDBY,
				NAME_SEARCH_QUERYSTR, NAME_PARAMETERS, NAME_LASTACCESSDATE, NAME_SEARCH_LOCKED, NAME_SEARCH_UIHIDDEN },
				folderPathArray, includeType);
	}

	private static JSONObject getCategoryJsonObj(URI baseUri, Category category, String[] excludedFields, boolean isSimple)
			throws JSONException, EMAnalyticsFwkJsonException
	{
		JSONObject categoryObj = JSONUtil.ObjectToJSONObject(category, excludedFields);
		if (baseUri != null && categoryObj != null) {
			if (categoryObj.has(NAME_CATEGORY_DEFAULTFOLDERID)) {
				if (!isSimple) {
					int defaultFolderId = categoryObj.getInt(NAME_CATEGORY_DEFAULTFOLDERID);
					JSONObject defaultFolderObj = new JSONObject();
					defaultFolderObj.put(NAME_ID, defaultFolderId);
					defaultFolderObj.put(NAME_HREF, baseUri + PATH_FOLDER + defaultFolderId);
					categoryObj.put(NAME_CATEGORY_DEFAULTFOLDER, defaultFolderObj);
				}
				categoryObj.remove(NAME_CATEGORY_DEFAULTFOLDERID);
			}

			if (categoryObj.has(NAME_ID)) {
				int folderId = categoryObj.getInt(NAME_ID);
				categoryObj.put(NAME_HREF, baseUri + PATH_FOLDER + folderId);
			}

		}
		return categoryObj;
	}

	private static JSONObject getFolderJsonObj(URI baseUri, Folder folder, String[] excludedFields, boolean isSimple,
			boolean includeType) throws JSONException, EMAnalyticsFwkJsonException
	{
		JSONObject folderObj = JSONUtil.ObjectToJSONObject(folder, excludedFields);
		if (baseUri != null && folderObj != null) {
			if (folderObj.has(NAME_FOLDER_PARENTID)) {
				if (!isSimple) {
					int folderId = folderObj.getInt(NAME_FOLDER_PARENTID);
					JSONObject parentFolderObj = new JSONObject();
					parentFolderObj.put(NAME_ID, folderId);
					parentFolderObj.put(NAME_HREF, baseUri + PATH_FOLDER + folderId);
					folderObj.put(NAME_FOLDER_PARENTFOLDER, parentFolderObj);
				}
				folderObj.remove(NAME_FOLDER_PARENTID);
			}

			if (folderObj.has(NAME_ID)) {
				int folderId = folderObj.getInt(NAME_ID);
				folderObj.put(NAME_HREF, baseUri + PATH_FOLDER + folderId);
			}

			if (includeType) {
				folderObj.put(NAME_TYPE, TYPE_FOLDER);
			}

		}
		return folderObj;
	}

	/**
	 * Return JSON string of search according to customization
	 *
	 * @param uri
	 * @param jsonObj
	 * @param excludedFields
	 *            fields that will NOT be added to JSON string
	 * @param folderPathArray
	 *            full folder path in array
	 * @return
	 * @throws JSONException
	 * @throws EMAnalyticsFwkJsonException
	 */
	private static JSONObject getSearchJsonObj(URI baseUri, Search search, String[] excludedFields, String[] folderPathArray,
			boolean includeType) throws JSONException, EMAnalyticsFwkJsonException
	{
		JSONObject searchObj = JSONUtil.ObjectToJSONObject(search, excludedFields);
		if (baseUri != null && searchObj != null) {
			if (searchObj.has(NAME_FOLDER_ID)) {
				int folderId = searchObj.getInt(NAME_FOLDER_ID);
				JSONObject folderObj = new JSONObject();
				folderObj.put(NAME_ID, folderId);
				folderObj.put(NAME_HREF, baseUri + PATH_FOLDER + folderId);
				searchObj.remove(NAME_FOLDER_ID);
				searchObj.put(NAME_FOLDER, folderObj);
			}
			if (searchObj.has(NAME_CATEGORY_ID)) {
				int categoryId = searchObj.getInt(NAME_CATEGORY_ID);
				JSONObject folderObj = new JSONObject();
				folderObj.put(NAME_ID, categoryId);
				folderObj.put(NAME_HREF, baseUri + PATH_CATEGORY + categoryId);
				searchObj.remove(NAME_CATEGORY_ID);
				searchObj.put(NAME_CATEGORY, folderObj);
			}
			if (folderPathArray != null && folderPathArray.length > 0) {
				JSONArray jsonPathArray = new JSONArray();
				for (String p : folderPathArray) {
					jsonPathArray.put(p);
				}
				searchObj.put(NAME_SEARCH_FOLDERPATH, jsonPathArray);
			}

			if (searchObj.has(NAME_ID)) {
				int searchId = searchObj.getInt(NAME_ID);
				searchObj.put(NAME_HREF, baseUri + PATH_SEARCH + searchId);
			}

			if (includeType) {
				searchObj.put(NAME_TYPE, TYPE_SEARCH);
			}
		}
		return searchObj;
	}

}
