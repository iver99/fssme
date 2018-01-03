package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.ScreenshotData;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;

import javax.persistence.EntityManager;

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
	 * @return 
	 * @throws EMAnalyticsFwkException
	 */
	public abstract EmAnalyticsSearch deleteSearch(BigInteger searchId, boolean permanently) throws EMAnalyticsFwkException;

	public abstract EmAnalyticsSearch deleteSearchWithEm(BigInteger searchId, EntityManager em, boolean permanently) throws EMAnalyticsFwkException;

	/**
	 *
	 * @param searchId
	 * @param permanently
	 * @throws EMAnalyticsFwkException
	 */
	public abstract void deleteTargetCard(BigInteger searchId, boolean permanently) throws EMAnalyticsFwkException;
	/**
	 *
	 * @param searchName
	 * @param isExactly
	 * @throws EMAnalyticsFwkException
	 */
	public abstract void deleteSearchByName(String searchName, boolean isExactly)throws EMAnalyticsFwkException;


    /**
     *
     * @param categoryId
     */
    public abstract List<BigInteger> getSearchIdsByCategory(BigInteger categoryId) throws EMAnalyticsFwkException;

	/**
	 *
	 * @param searchIds
	 * @param oobWidgetList
	 * @throws EMAnalyticsFwkException
     */
	public abstract void storeOobWidget(List<BigInteger> searchIds, List<SearchImpl> oobWidgetList)throws EMAnalyticsFwkException;
	/**
	 * Resturns the search by its name dxy
	 * @param searchName
	 * @return search
	 * @throw EMAnalyticsFwkException	 *
	 */
	public abstract List<Search> getTargetCard(String searchName)throws EMAnalyticsFwkException;

	/**
	 * Edits an existing search entity in the analytics sub-system.
	 * Don't allow edit system search by default.
	 *
	 * @param search
	 *            search to be modified
	 * @return re-loaded search object with generated dates
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Search editSearch(Search search) throws EMAnalyticsFwkException;

	/**
	 * Edits an existing search entity in the analytics sub-system.
	 * Don't allow edit system search by default.
	 *
	 * NOTE: in this method ,it will not open a txn nor commit, and will NOT close em.
	 *
	 * @param search
	 *            search to be modified
	 * @return re-loaded search object with generated dates
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Search editSearchWithEm(Search search, boolean canEditSysSearch, EntityManager em) throws EMAnalyticsFwkException;
	
	/**
	 * 
	 * @param search
	 * @param canEditSysSearch
	 * @return
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Search editSearch(Search search, boolean canEditSysSearch) throws EMAnalyticsFwkException;


	/**
	 * Returns the search object identified by the given identifier.
	 *
	 * @param searchId
	 *            identifier for the search entity
	 * @return search
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Search getSearch(BigInteger searchId) throws EMAnalyticsFwkException;

	/**
	 * Return the search in tenant scope. Don't care the owner of this search.
	 * @param searchId
	 * @return
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Search getSearchWithoutOwner(BigInteger searchId) throws EMAnalyticsFwkException;
	
	/**
	 * 
	 * @param searchName
	 * @return
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Search getSearchWithoutOwnerByName(String searchName) throws EMAnalyticsFwkException;
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return
	 * @throws EMAnalyticsFwkException
	 */
	public abstract List<Search> getSearchListWithoutOwnerByParam(String name, String value) throws EMAnalyticsFwkException;

	/**
	 * @param name
	 * @param folderId
	 * @return
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Search getSearchByName(String name, BigInteger folderId) throws EMAnalyticsFwkException;


	public abstract List<Search> getWidgetByName(String name) throws EMAnalyticsFwkException;

	/**
	 * Returns the count of (accessible) search entities in a folder.
	 *
	 * @param folderId
	 *            identifier of the folder
	 * @return count of seacrh entities
	 * @throws EMAnalyticsFwkException
	 */
	public abstract int getSearchCountByFolderId(BigInteger folderId) throws EMAnalyticsFwkException;

	/**
	 * Returns the list of search entities belonging to a category.
	 *
	 * @param categoryId
	 *            identifier of the category
	 * @return list of search entities belonging to a category
	 * @throws EMAnalyticsFwkException
	 */
	public abstract List<Search> getSearchListByCategoryId(BigInteger categoryId) throws EMAnalyticsFwkException;

	/**
	 * Returns the list of search entities contained (directly) in the specified folder.
	 *
	 * @param folderId
	 *            identifier of the folder
	 * @return list of search entities (<code>null</code> if none are contained)
	 * @throws EMAnalyticsFwkException
	 */
	public abstract List<Search> getSearchListByFolderId(BigInteger folderId) throws EMAnalyticsFwkException;

	public List<Search> getSearchListByLastAccessDate(int count) throws EMAnalyticsFwkException
	{
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	public abstract List<Search> getSystemSearchListByCategoryId(BigInteger categoryId) throws EMAnalyticsFwkException;

	/**
	 * Returns the list of widgets belonging to a category.
	 *
	 * @param categoryId
	 *            identifier of the category
	 * @return list of widgets belonging to a category
	 * @throws EMAnalyticsFwkException
	 */
	public abstract List<Search> getWidgetListByCategoryId(BigInteger categoryId) throws EMAnalyticsFwkException;

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
	public abstract ScreenshotData getWidgetScreenshotById(BigInteger widgetId) throws EMAnalyticsFwkException;

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
	 * Saves a completely specified search entity in the analytics sub-system with EM,
	 * NOTE: #1.will not close em in side this method.
	 * 		 #2. transaction already open outside this method
	 *
	 * @param search
	 *            search to be saved
	 * @return re-loaded search object with generated ids and dates
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Search saveSearchWithEm(Search search, EntityManager em) throws EMAnalyticsFwkException;

	/**
	 *
	 * @param search
	 * @return
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Search saveTargetCard(Search search) throws EMAnalyticsFwkException;
	
	/**
	 * get the parameter value of a saved search by parameter name
	 * @param searchId
	 * @param paramName
	 * @return
	 * @throws EMAnalyticsFwkException
	 */
	public abstract String getSearchParamByName(BigInteger searchId, String paramName) throws EMAnalyticsFwkException;
	
	/**
	 * get search list by a list of id
	 * @return
	 * @throws EMAnalyticsFwkException
	 */
	public abstract List<Search> getSearchListByIds(List<BigInteger> ids) throws EMAnalyticsFwkException;


	/**
	 * This method need to pass a reference of EntityManager, and will not close it inside this method.
	 * NOTE: #1.will not close em in side this method.
	 * 		 #2. transaction already open outside this method
	 * @param name
	 * @param folderId
	 * @param categoryId
	 * @param deleted
	 * @param owner
	 * @param em
	 * @return
	 */
	public abstract List<Map<String, Object>> getSearchIdAndNameByUniqueKey(String name, BigInteger folderId, BigInteger categoryId, BigInteger deleted, String owner, EntityManager em);
	
}
