package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.util.Date;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot.ScreenshotData;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

/**
 * The class <code>SearchManager</code> provides CRUD and other management operations over the Search entity in EM Analytics.
 *
 * @see Search
 * @version $Header: emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle
 *          /sysman/emSDK/core/emanalytics/api/search/SearchManager.java /st_emgc_pt-13.1mstr/2 2014/02/03 02:51:01 saurgarg Exp $
 * @author saurgarg
 * @since 13.1.0.0
 */
public abstract class SearchManager
{
	public static final String SEARCH_PARAM_DASHBOARD_INELIGIBLE = "DASHBOARD_INELIGIBLE";
	public static final String BLANK_SCREENSHOT = "data:image/png;base64,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AEFIhjEBbGkpyX9EeoKlxVwvUjtiTYqnp98ivhBnAMDXwenpqfbp6a+1iNgHAHS/n/PlMyGNeHNmgKWVXejlAybZ4F/iL3HrBB73ywzvAAABnWlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iWE1QIENvcmUgNS40LjAiPgogICA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPgogICAgICA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIgogICAgICAgICAgICB4bWxuczpleGlmPSJodHRwOi8vbnMuYWRvYmUuY29tL2V4aWYvMS4wLyI+CiAgICAgICAgIDxleGlmOlBpeGVsWERpbWVuc2lvbj4zNDk8L2V4aWY6UGl4ZWxYRGltZW5zaW9uPgogICAgICAgICA8ZXhpZjpQaXhlbFlEaW1lbnNpb24+MTg3PC9leGlmOlBpeGVsWURpbWVuc2lvbj4KICAgICAgPC9yZGY6RGVzY3JpcHRpb24+CiAgIDwvcmRmOlJERj4KPC94OnhtcG1ldGE+CppjahgAAAX4SURBVHgB7dTBCQAgDARBtf+eo1jEviYNHAxh97xbjgABAgQSgZOsGCFAgACBLyC6HoEAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqGA6IbYpggQICC6foAAAQKhgOiG2KYIECAgun6AAAECoYDohtimCBAgILp+gAABAqHABWHCBXJKFjVxAAAAAElFTkSuQmCC";

	/**
	 * Returns an instance of the manager.
	 *
	 * @return instance of the manager
	 */
	public static SearchManager getInstance()
	{
		return SearchManagerImpl.getInstance();
	}

	/**
	 * Instantiates a new search object (empty).
	 *
	 * @return new search object
	 */
	public abstract Search createNewSearch();

	/**
	 * @param searchId
	 * @param permanently
	 * @throws EMAnalyticsFwkException
	 */
	public abstract void deleteSearch(long searchId, boolean permanently) throws EMAnalyticsFwkException;

	/**
	 * Edits an existing search entity in the analytics sub-system.
	 *
	 * @param search
	 *            search to be modified
	 * @return re-loaded search object with generated dates
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Search editSearch(Search search) throws EMAnalyticsFwkException;

	/**
	 * Returns the search object identified by the given identifier.
	 *
	 * @param searchId
	 *            identifier for the search entity
	 * @return search
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Search getSearch(long searchId) throws EMAnalyticsFwkException;

	/**
	 * @param name
	 * @param folderId
	 * @return
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Search getSearchByName(String name, long folderId) throws EMAnalyticsFwkException;

	/**
	 * Returns the count of (accessible) search entities in a folder.
	 *
	 * @param folderId
	 *            identifier of the folder
	 * @return count of seacrh entities
	 * @throws EMAnalyticsFwkException
	 */
	public abstract int getSearchCountByFolderId(long folderId) throws EMAnalyticsFwkException;

	/**
	 * Returns the list of search entities belonging to a category.
	 *
	 * @param categoryId
	 *            identifier of the category
	 * @return list of search entities belonging to a category
	 * @throws EMAnalyticsFwkException
	 */
	public abstract List<Search> getSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException;

	/**
	 * Returns the list of search entities contained (directly) in the specified folder.
	 *
	 * @param folderId
	 *            identifier of the folder
	 * @return list of search entities (<code>null</code> if none are contained)
	 * @throws EMAnalyticsFwkException
	 */
	public abstract List<Search> getSearchListByFolderId(long folderId) throws EMAnalyticsFwkException;

	public List<Search> getSearchListByLastAccessDate(int count) throws EMAnalyticsFwkException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public abstract List<Search> getSystemSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException;

	/**
	 * Returns the list of widgets belonging to a category.
	 *
	 * @param categoryId
	 *            identifier of the category
	 * @return list of widgets belonging to a category
	 * @throws EMAnalyticsFwkException
	 */
	public abstract List<Search> getWidgetListByCategoryId(long categoryId) throws EMAnalyticsFwkException;

	/**
	 * Returns the list of widgets belonging to the categories specified by provider names
	 *
	 * @param includeDashboardIneligible
	 *            specifies whether to return Dashboard Ineligible widgets or not.
	 * @param providerNames
	 *            provider names
	 * @return list of widgets
	 * @throws EMAnalyticsFwkException
	 */
	public abstract List<Widget> getWidgetListByProviderNames(boolean includeDashboardIneligible, List<String> providerNames,
			String widgetGroupId) throws EMAnalyticsFwkException;

	/**
	 * Returns the screenshot of the widget by given id.
	 *
	 * @param widgetId
	 *            identifier of the widget
	 * @return screenshot of widget
	 * @throws EMAnalyticsFwkException
	 */
	public abstract ScreenshotData getWidgetScreenshotById(long widgetId) throws EMAnalyticsFwkException;

	public abstract Date modifyLastAccessDate(long searchId) throws EMAnalyticsFwkException;

	/**
	 * Saves a completely specified search entity in the analytics sub-system.
	 *
	 * @param search
	 *            search to be saved
	 * @return re-loaded search object with generated ids and dates
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Search saveSearch(Search search) throws EMAnalyticsFwkException;

}
