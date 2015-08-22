/*
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.net.URI;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;

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
	public static final String NAME_CATEGORY_PROVIDER_NAME = "providerName";
	public static final String NAME_CATEGORY_PROVIDER_VERSIONE = "providerVersion";
	public static final String NAME_CATEGORY_PROVIDER_DISCOVERY = "providerDiscovery";
	public static final String NAME_CATEGORY_PROVIDER_ASSET_ROOT = "providerAssetRoot";
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
	public static final String NAME_SEARCH_IS_WIDGET = "isWidget";
	public static final String NAME_SEARCH_FOLDERPATH = "flattenedFolderPath";
	public static final String PATH_FOLDER = "folder/";
	public static final String PATH_SEARCH = "search/";

	public static final String PATH_CATEGORY = "category/";
	public static final String TYPE_SEARCH = "search";
	public static final String TYPE_FOLDER = "folder";

	public static final String NAME_WIDGET_GROUP_ID = "WIDGET_GROUP_ID";
	public static final String NAME_WIDGET_GROUP_NAME = "WIDGET_GROUP_NAME";
	public static final String NAME_WIDGET_GROUP_DESCRIPTION = "WIDGET_GROUP_DESCRIPTION";
	public static final String NAME_WIDGET_GROUP_PROVIDER_NAME = "PROVIDER_NAME";
	public static final String NAME_WIDGET_GROUP_PROVIDER_VERSION = "PROVIDER_VERSION";
	public static final String NAME_WIDGET_GROUP_PROVIDER_ASSET_ROOT = "PROVIDER_ASSET_ROOT";
	public static final String NAME_WIDGET_GROUP_PROVIDER_DISCOVERY = "PROVIDER_DISCOVERY";

	public static final String NAME_WIDGET_ID = "WIDGET_UNIQUE_ID";
	public static final String NAME_WIDGET_NAME = "WIDGET_NAME";
	public static final String NAME_WIDGET_DESCRIPTION = "WIDGET_DESCRIPTION";
	public static final String NAME_WIDGET_ICON = "WIDGET_ICON";
	public static final String NAME_WIDGET_VISUAL = "WIDGET_VISUAL";
	public static final String NAME_WIDGET_OWNER = "WIDGET_OWNER";
	public static final String NAME_WIDGET_CREATION_TIME = "WIDGET_CREATION_TIME";
	public static final String NAME_WIDGET_SOURCE = "WIDGET_SOURCE";
	public static final String NAME_WIDGET_KOC_NAME = "WIDGET_KOC_NAME";
	public static final String NAME_WIDGET_VIEWMODEL = "WIDGET_VIEWMODEL";
	public static final String NAME_WIDGET_TEMPLATE = "WIDGET_TEMPLATE";
	public static final String NAME_WIDGET_PROVIDER_NAME = "PROVIDER_NAME";
	public static final String NAME_WIDGET_PROVIDER_VERSION = "PROVIDER_VERSION";
	public static final String NAME_WIDGET_PROVIDER_ASSET_ROOT = "PROVIDER_ASSET_ROOT";

	/**
	 * Return full JSON string for category
	 * 
	 * @param baseUri
	 * @param category
	 * @return
	 * @throws JSONException
	 * @throws EMAnalyticsFwkJsonException
	 */
	public static JSONObject getFullCategoryJsonObj(URI baseUri, Category category) throws EMAnalyticsFwkException
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
	public static JSONObject getFullFolderJsonObj(URI baseUri, Folder folder) throws EMAnalyticsFwkException
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
	public static JSONObject getFullSearchJsonObj(URI baseUri, Search search) throws EMAnalyticsFwkException
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
	public static JSONObject getFullSearchJsonObj(URI baseUri, Search search, String[] folderPathArray)
			throws EMAnalyticsFwkException
	{
		return EntityJsonUtil.getSearchJsonObj(baseUri, search, new String[] { NAME_GUID, NAME_SEARCH_LOCKED,
				NAME_SEARCH_UIHIDDEN, NAME_SEARCH_IS_WIDGET }, folderPathArray, false);
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
	public static JSONObject getFullSearchJsonObj(URI baseUri, Search search, String[] folderPathArray, boolean bResult)
			throws JSONException, EMAnalyticsFwkException
	{
		JSONObject obj = null;
		String fields[] = new String[] { NAME_GUID, NAME_SEARCH_LOCKED, NAME_SEARCH_UIHIDDEN, NAME_SEARCH_IS_WIDGET };
		if (bResult) {
			obj = EntityJsonUtil.getSearchJsonObj(baseUri, search, fields, folderPathArray, false);
		}
		else {
			obj = EntityJsonUtil.getSearchJsonObj(baseUri, search, null, folderPathArray, false);
		}

		return obj;
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
			EMAnalyticsFwkException
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
	public static JSONObject getSimpleFolderJsonObj(URI baseUri, Folder folder) throws JSONException, EMAnalyticsFwkException
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
	public static JSONObject getSimpleFolderJsonObj(URI baseUri, Folder folder, boolean includeType)
			throws EMAnalyticsFwkException
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
	public static JSONObject getSimpleSearchJsonObj(URI baseUri, Search search) throws EMAnalyticsFwkException
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
	public static JSONObject getSimpleSearchJsonObj(URI baseUri, Search search, boolean includeType)
			throws EMAnalyticsFwkException
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
			throws EMAnalyticsFwkException
	{
		return EntityJsonUtil.getSearchJsonObj(baseUri, search, new String[] { NAME_GUID, NAME_OWNER, NAME_LASTMODIFIEDBY,
				NAME_SEARCH_QUERYSTR, NAME_PARAMETERS, NAME_LASTACCESSDATE, NAME_SEARCH_LOCKED, NAME_SEARCH_UIHIDDEN,
				NAME_SEARCH_IS_WIDGET }, folderPathArray, includeType);
	}

	/**
	 * Return simple JSON string for widget group
	 * 
	 * @param baseUri
	 * @param category
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getWidgetGroupJsonObj(URI baseUri, Category category) throws EMAnalyticsFwkException
	{
		JSONObject widgetGroupObj = new JSONObject();
		try {
			widgetGroupObj.put(NAME_WIDGET_GROUP_ID, category.getId());
			widgetGroupObj.put(NAME_WIDGET_GROUP_NAME, category.getName());
			widgetGroupObj.put(NAME_WIDGET_GROUP_DESCRIPTION, category.getDescription());
			widgetGroupObj.put(NAME_WIDGET_GROUP_PROVIDER_NAME, category.getProviderName());
			widgetGroupObj.put(NAME_WIDGET_GROUP_PROVIDER_VERSION, category.getProviderVersion());
			widgetGroupObj.put(NAME_WIDGET_GROUP_PROVIDER_DISCOVERY, category.getProviderDiscovery());
			widgetGroupObj.put(NAME_WIDGET_GROUP_PROVIDER_ASSET_ROOT, category.getProviderAssetRoot());
		}
		catch (JSONException e) {
			throw new EMAnalyticsFwkException("An error occurred while converting widget object to JSON string",
					EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION, null, e);
		}

		return widgetGroupObj;
	}

	/**
	 * Return simple JSON string for widget
	 * 
	 * @param baseUri
	 * @param search
	 * @param category
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getWidgetJsonObj(URI baseUri, Search search, Category category) throws EMAnalyticsFwkException
	{
		JSONObject widgetObj = new JSONObject();
		try {
			widgetObj.put(NAME_WIDGET_ID, search.getId());
			widgetObj.put(NAME_WIDGET_NAME, search.getName());
			widgetObj.put(NAME_WIDGET_DESCRIPTION, search.getDescription());
			widgetObj.put(NAME_WIDGET_OWNER, search.getOwner());
			String createdOn = null;
			if (search.getCreatedOn() != null) {
				createdOn = DateUtil.getDateFormatter().format(search.getCreatedOn());
			}
			widgetObj.put(NAME_WIDGET_CREATION_TIME, createdOn);
			widgetObj.put(NAME_WIDGET_SOURCE, 1);
			widgetObj.put(NAME_WIDGET_GROUP_NAME, category.getName());
			List<SearchParameter> paramList = search.getParameters();
			if (paramList != null && paramList.size() > 0) {
				for (SearchParameter param : paramList) {
					if (NAME_WIDGET_ICON.equals(param.getName())) {
						widgetObj.put(NAME_WIDGET_ICON, param.getValue());
					}
					else if (NAME_WIDGET_VISUAL.equals(param.getName())) {
						widgetObj.put(NAME_WIDGET_VISUAL, param.getValue());
					}
					else if (NAME_WIDGET_KOC_NAME.equals(param.getName())) {
						widgetObj.put(NAME_WIDGET_KOC_NAME, param.getValue());
					}
					else if (NAME_WIDGET_VIEWMODEL.equals(param.getName())) {
						widgetObj.put(NAME_WIDGET_VIEWMODEL, param.getValue());
					}
					else if (NAME_WIDGET_TEMPLATE.equals(param.getName())) {
						widgetObj.put(NAME_WIDGET_TEMPLATE, param.getValue());
					}
					else if (NAME_WIDGET_PROVIDER_NAME.equals(param.getName())) {
						widgetObj.put(NAME_WIDGET_PROVIDER_NAME, param.getValue());
					}
					else if (NAME_WIDGET_PROVIDER_VERSION.equals(param.getName())) {
						widgetObj.put(NAME_WIDGET_PROVIDER_VERSION, param.getValue());
					}
					else if (NAME_WIDGET_PROVIDER_ASSET_ROOT.equals(param.getName())) {
						widgetObj.put(NAME_WIDGET_PROVIDER_ASSET_ROOT, param.getValue());
					}
				}
			}
			//Get provider info from category if it doesn't exist in search parameters
			if (!widgetObj.has(NAME_WIDGET_PROVIDER_NAME)) {
				widgetObj.put(NAME_WIDGET_PROVIDER_NAME, category.getProviderName());
			}
			if (!widgetObj.has(NAME_WIDGET_PROVIDER_VERSION)) {
				widgetObj.put(NAME_WIDGET_PROVIDER_VERSION, category.getProviderVersion());
			}
			if (!widgetObj.has(NAME_WIDGET_PROVIDER_ASSET_ROOT)) {
				widgetObj.put(NAME_WIDGET_PROVIDER_ASSET_ROOT, category.getProviderAssetRoot());
			}

			//Check if it is a valid widget, if not then return null
			if (widgetObj.has(NAME_WIDGET_KOC_NAME) && widgetObj.has(NAME_WIDGET_VIEWMODEL)
					&& widgetObj.has(NAME_WIDGET_TEMPLATE) && widgetObj.has(NAME_WIDGET_PROVIDER_NAME)
					&& widgetObj.has(NAME_WIDGET_PROVIDER_VERSION) && widgetObj.has(NAME_WIDGET_PROVIDER_ASSET_ROOT)) {
				return widgetObj;
			}
		}
		catch (JSONException ex) {
			throw new EMAnalyticsFwkException("An error occurred while converting widget object to JSON string",
					EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION, null, ex);
		}
		return null;
	}

	private static JSONObject getCategoryJsonObj(URI baseUri, Category category, String[] excludedFields, boolean isSimple)
			throws EMAnalyticsFwkException
	{
		JSONObject categoryObj = null;
		try {
			categoryObj = JSONUtil.ObjectToJSONObject(category, excludedFields);
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
					categoryObj.put(NAME_HREF, baseUri + PATH_CATEGORY + folderId);
				}

			}
		}
		catch (JSONException ex) {
			throw new EMAnalyticsFwkException("An error occurred while converting category object to JSON string",
					EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION, null, ex);
		}
		return categoryObj;
	}

	private static JSONObject getFolderJsonObj(URI baseUri, Folder folder, String[] excludedFields, boolean isSimple,
			boolean includeType) throws EMAnalyticsFwkException
	{
		JSONObject folderObj = null;
		try {
			folderObj = JSONUtil.ObjectToJSONObject(folder, excludedFields);
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
		}
		catch (JSONException e) {
			throw new EMAnalyticsFwkException("An error occurred while converting folder object to JSON string",
					EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION, null, e);
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
			boolean includeType) throws EMAnalyticsFwkException
	{
		JSONObject searchObj = null;
		try {
			searchObj = JSONUtil.ObjectToJSONObject(search, excludedFields);
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
		}
		catch (JSONException ex) {
			throw new EMAnalyticsFwkException("An error occurred while converting search object to JSON string",
					EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION, null, ex);
		}
		return searchObj;
	}
}
