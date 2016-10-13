/*
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.math.BigInteger;
import java.net.URI;
import java.util.List;
import java.util.Map;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessage;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Create JSON string for each entity (search/folder/category) and return to user
 *
 * @author miayu
 */
public class EntityJsonUtil
{
	private static final Logger LOGGER = LogManager.getLogger(EntityJsonUtil.class);

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

	public static final String NAME_SYSTEM_SEARCH = "systemSearch";

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
	public static final String NAME_WIDGET_SUPPORT_TIME_CONTROL = "WIDGET_SUPPORT_TIME_CONTROL";
	public static final String NAME_WIDGET_LINKED_DASHBOARD = "WIDGET_LINKED_DASHBOARD";
	public static final String NAME_WIDGET_SCREENSHOT_HREF = "WIDGET_SCREENSHOT_HREF";
	public static final String NAME_WIDGET_DEFAULT_WIDTH = "WIDGET_DEFAULT_WIDTH";
	public static final String NAME_WIDGET_DEFAULT_HEIGHT = "WIDGET_DEFAULT_HEIGHT";
	public static final String NAME_WIDGET_PROVIDER_NAME = "PROVIDER_NAME";
	public static final String NAME_WIDGET_PROVIDER_VERSION = "PROVIDER_VERSION";
	public static final String NAME_WIDGET_PROVIDER_ASSET_ROOT = "PROVIDER_ASSET_ROOT";
	public static final String NAME_WIDGET_SCREENSHOT = "screenShot";
	public static final String ERROR_MESSAGE = "message";
	public static final String ERROR_CODE = "errorCode";

	private static final Integer MAX_DASHBOARD_TILE_WIDTH = 8;

	private static final String DEFAULT_DB_VALUE = "0";

	private EntityJsonUtil() {
	}

	public static JSONObject getErrorJsonObject(BigInteger id, String message, long errorcode) throws EMAnalyticsFwkException
	{
		JSONObject errorObj = new JSONObject();
		try {
			errorObj.put(NAME_ID, id);
			errorObj.put(ERROR_MESSAGE, message);
			errorObj.put(ERROR_CODE, errorcode);

		}
		catch (JSONException e) {
			throw new EMAnalyticsFwkException("Error converting to JSON string",
					EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION, null, e);

		}
		return errorObj;

	}

	/**
	 * Return full JSON string for category
	 *
	 * @param baseUri
	 * @param category
	 * @return
	 * @throws JSONException
	 * @throws EMAnalyticsFwkJsonException
	 */
	public static ObjectNode getFullCategoryJsonObj(URI baseUri, Category category) throws EMAnalyticsFwkException
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
	public static ObjectNode getFullFolderJsonObj(URI baseUri, Folder folder) throws EMAnalyticsFwkException
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
	public static ObjectNode getFullSearchJsonObj(URI baseUri, Search search) throws EMAnalyticsFwkException
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
	public static ObjectNode getFullSearchJsonObj(URI baseUri, Search search, String[] folderPathArray)
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
	public static ObjectNode getFullSearchJsonObj(URI baseUri, Search search, String[] folderPathArray, boolean bResult)
			throws JSONException, EMAnalyticsFwkException
	{
		ObjectNode obj = null;
		String fields[] = new String[] { NAME_GUID, NAME_SEARCH_LOCKED, NAME_SEARCH_UIHIDDEN, NAME_SEARCH_IS_WIDGET };
		if (bResult) {
			obj = EntityJsonUtil.getSearchJsonObj(baseUri, search, fields, folderPathArray, false);
		}
		else {
			obj = EntityJsonUtil.getSearchJsonObj(baseUri, search, null, folderPathArray, false);
		}

		return obj;
	}

	public static String getJsonString(Map<String, Object> m, String screenshotUrl) throws EMAnalyticsFwkException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"WIDGET_UNIQUE_ID\":").append("\"" + m.get("SEARCH_ID") + "\"").append(",");
		sb.append("\"WIDGET_NAME\":").append("\"" + m.get("NAME") + "\"").append(",");
		if (m.get("DESCRIPTION") != null && !DEFAULT_DB_VALUE.equals(m.get("DESCRIPTION"))) {
			sb.append("\"WIDGET_DESCRIPTION\":").append("\"" + m.get("DESCRIPTION") + "\"").append(",");
		}
		sb.append("\"WIDGET_OWNER\":").append("\"" + m.get("OWNER") + "\"").append(",");

		String createdOn = null;
		if (m.get("CREATION_DATE") != null) {
			createdOn = DateUtil.getDateFormatter().format(m.get("CREATION_DATE"));
		}
		sb.append("\"WIDGET_CREATION_TIME\":").append("\"" + createdOn + "\"").append(",");
		sb.append("\"WIDGET_SOURCE\":").append(1).append(",");
		sb.append("\"WIDGET_GROUP_NAME\":").append("\"" + m.get("CATOGORY_NAME") + "\"").append(",");
		sb.append("\"WIDGET_SCREENSHOT_HREF\":").append("\"" + screenshotUrl + "\"").append(",");
		sb.append("\"WIDGET_SUPPORT_TIME_CONTROL\":").append("\"" + m.get("WIDGET_SUPPORT_TIME_CONTROL") + "\"").append(",");
		sb.append("\"WIDGET_KOC_NAME\":").append("\"" + m.get("WIDGET_KOC_NAME") + "\"").append(",");
		sb.append("\"WIDGET_TEMPLATE\":").append("\"" + m.get("WIDGET_TEMPLATE") + "\"").append(",");
		sb.append("\"WIDGET_VIEWMODEL\":").append("\"" + m.get("WIDGET_VIEWMODEL") + "\"").append(",");

		//if provider_name ,provider_version,provider_asset_root, did not exist in search table, find value in category table
		if (m.get("PROVIDER_NAME") != null && !DEFAULT_DB_VALUE.equals(m.get("PROVIDER_NAME"))) {
			sb.append("\"PROVIDER_NAME\":").append("\"" + m.get("PROVIDER_NAME") + "\"").append(",");
		}
		else {
			sb.append("\"PROVIDER_NAME\":").append("\"" + m.get("C_PROVIDER_NAME") + "\"").append(",");
		}
		if (m.get("PROVIDER_VERSION") != null && !DEFAULT_DB_VALUE.equals(m.get("PROVIDER_VERSION"))) {
			sb.append("\"PROVIDER_VERSION\":").append("\"" + m.get("PROVIDER_VERSION") + "\"").append(",");
		}
		else {
			sb.append("\"PROVIDER_VERSION\":").append("\"" + m.get("C_PROVIDER_VERSION") + "\"").append(",");
		}
		if (m.get("PROVIDER_ASSET_ROOT") != null && !DEFAULT_DB_VALUE.equals(m.get("PROVIDER_ASSET_ROOT"))) {
			sb.append("\"PROVIDER_ASSET_ROOT\":").append("\"" + m.get("PROVIDER_ASSET_ROOT") + "\"").append(",");
		}
		else {
			sb.append("\"PROVIDER_ASSET_ROOT\":").append("\"" + m.get("C_PROVIDER_ASSET_ROOT") + "\"").append(",");
		}

		if (m.get("WIDGET_DEFAULT_HEIGHT") != null && !DEFAULT_DB_VALUE.equals(m.get("WIDGET_DEFAULT_HEIGHT").toString())) {
			sb.append("\"WIDGET_DEFAULT_HEIGHT\":").append("\"" + m.get("WIDGET_DEFAULT_HEIGHT") + "\"").append(",");
		}
		if (m.get("DASHBOARD_INELIGIBLE") != null && !DEFAULT_DB_VALUE.equals(m.get("DASHBOARD_INELIGIBLE").toString())) {
			sb.append("\"DASHBOARD_INELIGIBLE\":").append("\"" + m.get("DASHBOARD_INELIGIBLE") + "\"").append(",");
		}
		if (m.get("WIDGET_LINKED_DASHBOARD") != null && !DEFAULT_DB_VALUE.equals(m.get("WIDGET_LINKED_DASHBOARD").toString())) {
			sb.append("\"WIDGET_LINKED_DASHBOARD\":").append("\"" + m.get("WIDGET_LINKED_DASHBOARD") + "\"").append(",");
		}
		if (m.get("WIDGET_DEFAULT_WIDTH") != null && !DEFAULT_DB_VALUE.equals(m.get("WIDGET_DEFAULT_WIDTH").toString())) {
			sb.append("\"WIDGET_DEFAULT_WIDTH\":").append("\"" + m.get("WIDGET_DEFAULT_WIDTH") + "\"");
		}
		
		if (m.get("WIDGET_EDITABLE") != null && !DEFAULT_DB_VALUE.equals(m.get("WIDGET_EDITABLE").toString())) {
			sb.append("\"WIDGET_EDITABLE\":").append("\"" + m.get("WIDGET_EDITABLE") + "\"");
		}

		if (sb.toString().endsWith(",")) {
			//remove the extra comma
			sb.deleteCharAt(sb.length() - 1);
		}

		sb.append("},");
		LOGGER.debug("Finished Conversion to JSON String:" + sb.toString());
		return sb.toString();
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
	public static ObjectNode getSimpleCategoryJsonObj(URI baseUri, Category category)
			throws EMAnalyticsFwkException
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
	public static ObjectNode getSimpleFolderJsonObj(URI baseUri, Folder folder) throws EMAnalyticsFwkException
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
	public static ObjectNode getSimpleFolderJsonObj(URI baseUri, Folder folder, boolean includeType)
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
	public static ObjectNode getSimpleSearchJsonObj(URI baseUri, Search search) throws EMAnalyticsFwkException
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
	public static ObjectNode getSimpleSearchJsonObj(URI baseUri, Search search, boolean includeType)
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
	public static ObjectNode getSimpleSearchJsonObj(URI baseUri, Search search, String[] folderPathArray, boolean includeType)
			throws EMAnalyticsFwkException
	{
		return EntityJsonUtil.getSearchJsonObj(baseUri, search, new String[] { NAME_GUID, NAME_OWNER, NAME_LASTMODIFIEDBY,
				NAME_SEARCH_QUERYSTR, NAME_PARAMETERS, NAME_LASTACCESSDATE, NAME_SEARCH_LOCKED, NAME_SEARCH_UIHIDDEN,
				NAME_SEARCH_IS_WIDGET }, folderPathArray, includeType);
	}

	/**
	 *
	 * @param baseUri
	 * @param search
	 * @return
	 * @throws EMAnalyticsFwkException
	 */

	public static ObjectNode getTargetCardJsonObj(URI baseUri, Search search)
			throws EMAnalyticsFwkException
	{
		return EntityJsonUtil.getTargetCardJsonObj(baseUri, search, new String[] { NAME_GUID, NAME_OWNER, NAME_SEARCH_QUERYSTR,
				NAME_LASTACCESSDATE, NAME_LASTMODIFIEDBY, NAME_SYSTEM_SEARCH, NAME_SEARCH_LOCKED, NAME_SEARCH_UIHIDDEN,
				NAME_SEARCH_IS_WIDGET });
	}
	/**
	 *
	 * @param baseUri
	 * @param search
	 * @param excludedFields
	 * @return
	 * @throws EMAnalyticsFwkException
	 */

	public static ObjectNode getTargetCardJsonObj(URI baseUri, Search search, String[] excludedFields) throws EMAnalyticsFwkException
	{
		ObjectNode searchObj = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			searchObj = JSONUtil.objectToJSONObject(search, excludedFields);
			if (searchObj.has(NAME_FOLDER_ID)) {
				BigInteger folderId = searchObj.get(NAME_FOLDER_ID).getBigIntegerValue();
				ObjectNode folderObj = mapper.createObjectNode();
				folderObj.put(NAME_ID, String.valueOf(folderId));
				searchObj.remove(NAME_FOLDER_ID);
				searchObj.put(NAME_FOLDER, folderObj);
			}
			if (searchObj.has(NAME_CATEGORY_ID)) {
				BigInteger categoryId = searchObj.get(NAME_CATEGORY_ID).getBigIntegerValue();
				ObjectNode categoryObj = mapper.createObjectNode();
				categoryObj.put(NAME_ID, String.valueOf(categoryId));
				searchObj.remove(NAME_CATEGORY_ID);
				searchObj.put(NAME_CATEGORY, categoryObj);
			}

		}
		catch (JSONException ex) {
			throw new EMAnalyticsFwkException("An error occurred while converting search object to JSON string",
					EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION, null, ex);
		}
		return searchObj;
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

	//	/**
	//	 * Return simple JSON string for widget screen shot
	//	 *
	//	 * @param widgetScreenshot
	//	 * @return
	//	 * @throws JSONException
	//	 */
	//	public static JSONObject getWidgetScreenshotJsonObj(String widgetScreenshot) throws EMAnalyticsFwkException
	//	{
	//		JSONObject widgetScreenshotObj = new JSONObject();
	//		try {
	//			widgetScreenshotObj.put(NAME_WIDGET_SCREENSHOT, widgetScreenshot);
	//		}
	//		catch (JSONException ex) {
	//			throw new EMAnalyticsFwkException("An error occurred while converting widget screen shot object to JSON string",
	//					EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION, null, ex);
	//		}
	//		return widgetScreenshotObj;
	//	}

	/**
	 * Return simple JSON string for widget
	 *
	 * @param baseUri
	 * @param search
	 * @param category
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getWidgetJsonObj(Search search, Category category, String screenshotUrl)
			throws EMAnalyticsFwkException
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
			widgetObj.put(NAME_WIDGET_SCREENSHOT_HREF, screenshotUrl);
			List<SearchParameter> paramList = search.getParameters();
			if (paramList != null && !paramList.isEmpty()) {
				for (SearchParameter param : paramList) {
					if (NAME_WIDGET_ICON.equals(param.getName())) {
						widgetObj.put(NAME_WIDGET_ICON, param.getValue());
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
					else if (NAME_WIDGET_SUPPORT_TIME_CONTROL.equals(param.getName())) {
						widgetObj.put(NAME_WIDGET_SUPPORT_TIME_CONTROL, param.getValue());
					}
					else if (NAME_WIDGET_LINKED_DASHBOARD.equals(param.getName())) {
						widgetObj.put(NAME_WIDGET_LINKED_DASHBOARD, param.getValue());
					}
					else if (NAME_WIDGET_DEFAULT_WIDTH.equals(param.getName())) {
						EntityJsonUtil.getWidgetJsonObjForDefaultWidth(search, widgetObj, param);
					}
					else if (NAME_WIDGET_DEFAULT_HEIGHT.equals(param.getName())) {
						EntityJsonUtil.getWidgetJsonObjForDefaultHeight(search, widgetObj, param);
					}
				}
			}
			//default value for NAME_WIDGET_SUPPORT_TIME_CTRONOL: default to support
			if (!widgetObj.has(NAME_WIDGET_SUPPORT_TIME_CONTROL)) {
				widgetObj.put(NAME_WIDGET_SUPPORT_TIME_CONTROL, "1");
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

	//	/**
	//	 * Return simple JSON string for widget screen shot
	//	 *
	//	 * @param widgetScreenshot
	//	 * @return
	//	 * @throws JSONException
	//	 */
	//	public static JSONObject getWidgetScreenshotJsonObj(String widgetScreenshot) throws EMAnalyticsFwkException
	//	{
	//		JSONObject widgetScreenshotObj = new JSONObject();
	//		try {
	//			widgetScreenshotObj.put(NAME_WIDGET_SCREENSHOT, widgetScreenshot);
	//		}
	//		catch (JSONException ex) {
	//			throw new EMAnalyticsFwkException("An error occurred while converting widget screen shot object to JSON string",
	//					EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION, null, ex);
	//		}
	//		return widgetScreenshotObj;
	//	}

	private static ObjectNode getCategoryJsonObj(URI baseUri, Category category, String[] excludedFields, boolean isSimple)
			throws EMAnalyticsFwkException
	{
		ObjectNode categoryObj = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			categoryObj = JSONUtil.objectToJSONObject(category, excludedFields);
			if (baseUri != null && categoryObj != null) {
				if (categoryObj.has(NAME_CATEGORY_DEFAULTFOLDERID)) {
					if (!isSimple) {
						BigInteger defaultFolderId = categoryObj.get(NAME_CATEGORY_DEFAULTFOLDERID).getBigIntegerValue();
						ObjectNode defaultFolderObj = mapper.createObjectNode();
						defaultFolderObj.put(NAME_ID, String.valueOf(defaultFolderId));
						defaultFolderObj.put(NAME_HREF, baseUri + PATH_FOLDER + defaultFolderId);
						categoryObj.put(NAME_CATEGORY_DEFAULTFOLDER, defaultFolderObj);
					}
					categoryObj.remove(NAME_CATEGORY_DEFAULTFOLDERID);
				}

				if (categoryObj.has(NAME_ID)) {
					BigInteger folderId = categoryObj.get(NAME_ID).getBigIntegerValue();
					categoryObj.put(NAME_ID, String.valueOf(folderId));
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

	private static ObjectNode getFolderJsonObj(URI baseUri, Folder folder, String[] excludedFields, boolean isSimple,
			boolean includeType) throws EMAnalyticsFwkException
	{
		ObjectNode folderObj = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			folderObj = JSONUtil.objectToJSONObject(folder, excludedFields);
			if (baseUri != null && folderObj != null) {
				if (folderObj.has(NAME_FOLDER_PARENTID)) {
					if (!isSimple) {
						BigInteger folderId = folderObj.get(NAME_FOLDER_PARENTID).getBigIntegerValue();
						ObjectNode parentFolderObj = mapper.createObjectNode();
						parentFolderObj.put(NAME_ID, String.valueOf(folderId));
						parentFolderObj.put(NAME_HREF, baseUri + PATH_FOLDER + folderId);
						folderObj.put(NAME_FOLDER_PARENTFOLDER, parentFolderObj);
					}
					folderObj.remove(NAME_FOLDER_PARENTID);
				}

				if (folderObj.has(NAME_ID)) {
					BigInteger folderId = folderObj.get(NAME_ID).getBigIntegerValue();
					folderObj.put(NAME_ID, String.valueOf(folderId));
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
	private static ObjectNode getSearchJsonObj(URI baseUri, Search search, String[] excludedFields, String[] folderPathArray,
			boolean includeType) throws EMAnalyticsFwkException
	{
		ObjectNode searchObj = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			searchObj = JSONUtil.objectToJSONObject(search, excludedFields);
			if (baseUri != null && searchObj != null) {
				if (searchObj.has(NAME_FOLDER_ID)) {
					BigInteger folderId = searchObj.get(NAME_FOLDER_ID).getBigIntegerValue();
					ObjectNode folderObj = mapper.createObjectNode();
					folderObj.put(NAME_ID, String.valueOf(folderId));
					folderObj.put(NAME_HREF, baseUri + PATH_FOLDER + folderId);
					searchObj.remove(NAME_FOLDER_ID);
					searchObj.put(NAME_FOLDER, folderObj);
				}
				if (searchObj.has(NAME_CATEGORY_ID)) {
					BigInteger categoryId = searchObj.get(NAME_CATEGORY_ID).getBigIntegerValue();
					ObjectNode categoryObj = mapper.createObjectNode();
					categoryObj.put(NAME_ID, String.valueOf(categoryId));
					categoryObj.put(NAME_HREF, baseUri + PATH_CATEGORY + categoryId);
					searchObj.remove(NAME_CATEGORY_ID);
					searchObj.put(NAME_CATEGORY, categoryObj);
				}
				if (folderPathArray != null && folderPathArray.length > 0) {
					ArrayNode jsonPathArray = mapper.createArrayNode();
					for (String p : folderPathArray) {
						jsonPathArray.add(p);
					}
					searchObj.put(NAME_SEARCH_FOLDERPATH, jsonPathArray);
				}
				if (searchObj.has(NAME_ID)) {
					BigInteger searchId = searchObj.get(NAME_ID).getBigIntegerValue();
					searchObj.put(NAME_ID, String.valueOf(searchId));
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

	private static void getWidgetJsonObjForDefaultHeight(Search search, JSONObject widgetObj, SearchParameter param)
			throws JSONException
	{
		try {
			int height = Integer.parseInt(param.getValue());
			if (height < 1) {
				LOGGER.warn("Widget (id={}) has the default height {}<1, use height=1", search.getId(), height);
				height = 1;
			}
			widgetObj.put(NAME_WIDGET_DEFAULT_HEIGHT, height);
		}
		catch (NumberFormatException e) {
			LOGGER.error(new MessageFormatMessage("Invalid widget default height \"{}\" for widget id={}", param.getValue(),
					search.getId()), e);
		}
	}

	private static void getWidgetJsonObjForDefaultWidth(Search search, JSONObject widgetObj, SearchParameter param)
			throws JSONException
	{
		try {
			int width = Integer.parseInt(param.getValue());
			if (width < 1) {
				LOGGER.warn("Widget (id={}) has the default width {}<1, use width=1", search.getId(), width);
				width = 1;
			}
			if (width > MAX_DASHBOARD_TILE_WIDTH) {
				LOGGER.warn("Widget (id={}) has the default width {}>{}, use width={}", search.getId(), width,
						MAX_DASHBOARD_TILE_WIDTH, MAX_DASHBOARD_TILE_WIDTH);
				width = MAX_DASHBOARD_TILE_WIDTH;
			}
			widgetObj.put(NAME_WIDGET_DEFAULT_WIDTH, width);
		}
		catch (NumberFormatException e) {
			LOGGER.error(new MessageFormatMessage("Invalid widget default width \"{}\" for widget id={}", param.getValue(),
					search.getId()), e);
		}
	}
}
