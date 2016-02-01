package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.util.Date;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
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
	public abstract String getWidgetScreenshotById(long widgetId) throws EMAnalyticsFwkException;

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
