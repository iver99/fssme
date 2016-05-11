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
	public static final String DEFAULT_WIDGET_SCREENSHOT = "data:image/png;base64,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";

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
	
	/**
	 * get the parameter value of a saved search by parameter name
	 * @param searchId
	 * @param paramName
	 * @return
	 * @throws EMAnalyticsFwkException
	 */
	public abstract String getSearchParamByName(long searchId, String paramName) throws EMAnalyticsFwkException;
}
