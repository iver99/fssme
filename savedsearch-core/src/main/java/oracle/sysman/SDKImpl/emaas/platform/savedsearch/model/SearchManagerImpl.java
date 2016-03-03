package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.eclipse.persistence.sessions.DatabaseRecord;
import org.eclipse.persistence.sessions.Session;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.QueryParameterConstant;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot.ScreenshotData;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EmAnalyticsProcessingException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Widget;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsLastAccess;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsLastAccessPK;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearchParam;

public class SearchManagerImpl extends SearchManager
{

	//  LoggergetSearchListByCategoryId
	private static final Logger _logger = LogManager.getLogger(SearchManagerImpl.class);

	public static final SearchManagerImpl _instance = new SearchManagerImpl();
	private static final String FOLDER_ORDERBY = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsFolder.folderId = :folderId and e.deleted=0 ";
	private static final String FILTER_BY_CATEGORY = "and e.emAnalyticsCategory = :category ";
	private static final String SEARCH_ENT_PREFIX = "e.";
	private static final String LASTACCESS_ORDERBY = "SELECT e FROM EmAnalyticsSearch e  where e.deleted=0 and e.owner in ('ORACLE',:userName) order by e.lastAccess.accessDate DESC ";

	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_1 = "SELECT e FROM EmAnalyticsSearch e " + "where ";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_2 = "e.emAnalyticsCategory.categoryId=:widgetGroupId AND ";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_3 = "e.emAnalyticsCategory.categoryId in (select c.categoryId from EmAnalyticsCategory c where c.providerName in (";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_4 = "))  "
			+ "AND e.deleted=0 AND e.isWidget=1 AND e.tenant=:tenantId AND (e.owner=:userName OR e.systemSearch =1)";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_WO_INELIGIBLE_1 = "SELECT e FROM EmAnalyticsSearch e LEFT JOIN EmAnalyticsSearchParam pm "
			+ "ON pm.searchId=e.id AND pm.tenantId=:tenantId AND pm.name = 'DASHBOARD_INELIGIBLE' where e.tenant=:tenantId AND ";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_WO_INELIGIBLE_2 = "e.emAnalyticsCategory.categoryId=:widgetGroupId AND ";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_WO_INELIGIBLE_3 = "e.emAnalyticsCategory.categoryId in "
			+ "(select c.categoryId from EmAnalyticsCategory c LEFT JOIN EmAnalyticsCategoryParam cm "
			+ "on cm.categoryId=c.categoryId AND cm.name='DASHBOARD_INELIGIBLE' AND cm.tenantId=:tenantId "
			+ "where c.providerName in (";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_WO_INELIGIBLE_4 = ") AND (cm.value IS NULL OR cm.value<>'1')) "
			+ "AND e.deleted=0 AND e.isWidget=1 AND e.tenant=:tenantId AND (e.owner=:userName OR e.systemSearch =1) "
			+ "AND (pm.paramValueStr IS NULL OR pm.paramValueStr<>'1')";

	//+ " EmAnalyticsLastAccess t where e.searchId = t.objectId ";

	/**
	 * Get SearchManagerImpl singleton instance.
	 *
	 * @return Instance of SearchManagerImpl
	 */
	public static SearchManagerImpl getInstance()
	{
		return _instance;
	}

	/**
	 * Private Constructor
	 */
	private SearchManagerImpl()
	{

	}

	@Override
	public Search createNewSearch()
	{
		return new SearchImpl();
	}

	@Override
	public void deleteSearch(long searchId, boolean permanently) throws EMAnalyticsFwkException
	{
		_logger.info("Deleting search with id: " + searchId);
		EntityManager em = null;
		EmAnalyticsSearch searchObj = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			if (permanently) {
				searchObj = EmAnalyticsObjectUtil.getSearchByIdForDelete(searchId, em);
			}
			else {
				searchObj = EmAnalyticsObjectUtil.getSearchById(searchId, em);
			}
			if (searchObj == null) {
				throw new EMAnalyticsFwkException("Search with Id: " + searchId + " does not exist",
						EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID, null);
			}

			if (searchObj.getSystemSearch() != null && searchObj.getSystemSearch().intValue() == 1) {
				throw new EMAnalyticsFwkException("Search with Id: " + searchId + " is system search and NOT allowed to delete",
						EMAnalyticsFwkException.ERR_DELETE_SEARCH, null);
			}
			searchObj.setDeleted(searchId);
			em.getTransaction().begin();
			if (permanently) {
				em.remove(searchObj);
			}
			else {
				em.merge(searchObj);
			}
			em.getTransaction().commit();
		}
		catch (EMAnalyticsFwkException eme) {
			_logger.error("Search with Id: " + searchId + " does not exist", eme);
			throw eme;
		}
		catch (Exception e) {

			EmAnalyticsProcessingException.processSearchPersistantException(e, searchObj.getName());
			_logger.error("Error while getting the search object by ID: " + searchId, e);
			throw new EMAnalyticsFwkException("Error while deleting the search object by ID: " + searchId,
					EMAnalyticsFwkException.ERR_DELETE_SEARCH, new Object[] { searchId }, e);

		}
		finally {
			if (em != null) {
				em.close();
			}

		}
	}

	@Override
	public Search editSearch(Search search) throws EMAnalyticsFwkException
	{
		_logger.info("Editing search with id : " + search.getId());
		EntityManager em = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			EmAnalyticsSearch searchEntity = EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit(search, em);
			if (searchEntity != null && searchEntity.getSystemSearch() != null
					&& searchEntity.getSystemSearch().intValue() == 1) {
				throw new EMAnalyticsFwkException(
						"Search with Id: " + searchEntity.getId() + " is system search and NOT allowed to edit",
						EMAnalyticsFwkException.ERR_UPDATE_SEARCH, null);
			}
			em.getTransaction().begin();
			em.merge(searchEntity);
			em.getTransaction().commit();
			return createSearchObject(searchEntity, null);
		}
		catch (EMAnalyticsFwkException eme) {

			_logger.error("Search with name " + search.getName() + " was updated but could not be retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			processUniqueConstraints(search, em, dmlce);
			EmAnalyticsProcessingException.processSearchPersistantException(dmlce, null);
			_logger.error("Persistence Error while updating the search: " + search.getName(), dmlce);
			throw new EMAnalyticsFwkException("Error while updating the search: " + search.getName(),
					EMAnalyticsFwkException.ERR_UPDATE_SEARCH, null, dmlce);

		}
		catch (Exception e) {
			_logger.error("Error while updating the search: " + search.getName(), e);
			throw new EMAnalyticsFwkException("Error while updating the search: " + search.getName(),
					EMAnalyticsFwkException.ERR_UPDATE_SEARCH, null, e);
		}
		finally {
			if (em != null) {
				em.close();
			}

		}
	}

	@Override
	public Search getSearch(long searchId) throws EMAnalyticsFwkException
	{
		//Get full search data
		return getSearch(searchId, false);
	}

	@Override
	public Search getSearchByName(String name, long folderId) throws EMAnalyticsFwkException
	{
		_logger.info("Retrieving search with name " + name + " in folder id: " + folderId);
		EntityManager em = null;
		EmAnalyticsSearch searchEntity = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			//EmAnalyticsFolder folder = EmAnalyticsObjectUtil.getFolderById(folderId, em);
			searchEntity = (EmAnalyticsSearch) em.createNamedQuery("Search.getSearchByName").setParameter("folderId", folderId)
					.setParameter("searchName", name)
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getSingleResult();

			return createSearchObject(searchEntity, null);
		}
		catch (NoResultException nre) {
			return null;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			_logger.error("Error while retrieving the list of searches for the parent folder: " + folderId, e);
			throw new EMAnalyticsFwkException("Error while retrieving the list of searches for the parent folder: " + folderId,
					EMAnalyticsFwkException.ERR_GENERIC, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}

		}

	}

	@Override
	public int getSearchCountByFolderId(long folderId) throws EMAnalyticsFwkException
	{
		throw new RuntimeException("getSearchCountByFolderId is not ready to use");
		//Not used now, uncomment below when necessary
		//		EntityManager em = null;
		//		int count;
		//		try {
		//			EntityManagerFactory emf;
		//			emf = PersistenceManager.getInstance().getEntityManagerFactory();
		//			em = emf.createEntityManager();
		//			count = ((Number) em.createNamedQuery("Search.getSearchCountByFolder").setParameter("folder", folderId)
		//					.getSingleResult()).intValue();
		//			return count;
		//		}
		//		catch (Exception e) {
		//			if (e.getCause() != null && e.getCause().getMessage().contains("Cannot acquire data source")) {
		//				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
		//				throw new EMAnalyticsFwkException(
		//						"Error while connecting to data source, please check the data source details: ",
		//						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
		//			}
		//			else {
		//				_logger.error("Error while retrieving the count of searches for the parent folder: " + folderId, e);
		//				throw new EMAnalyticsFwkException("Error while retrieving the count of searches for the parent folder: "
		//						+ folderId, EMAnalyticsFwkException.ERR_GENERIC, null, e);
		//			}
		//		}
		//		finally {
		//			if (em != null) {
		//				em.close();
		//			}
		//		}

	}

	@Override
	public List<Search> getSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		try {
			List<Search> rtnobj = new ArrayList<Search>();
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			//			EmAnalyticsCategory category = EmAnalyticsObjectUtil.getCategoryById(categoryId, em);
			@SuppressWarnings("unchecked")
			List<EmAnalyticsSearch> searchList = em.createNamedQuery("Search.getSearchListByCategory")
					.setParameter("categoryId", categoryId)
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getResultList();
			for (EmAnalyticsSearch searchObj : searchList) {
				em.refresh(searchObj);
				rtnobj.add(createSearchObject(searchObj, null));
			}

			return rtnobj;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			_logger.error("Error while retrieving the list of searches for the categoryD : " + categoryId, e);
			throw new EMAnalyticsFwkException("Error while retrieving the list of searches for the categoryId : " + categoryId,
					EMAnalyticsFwkException.ERR_GENERIC, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}

		}

	}

	@Override
	public List<Search> getSearchListByFolderId(long folderId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		try {
			List<Search> rtnobj = new ArrayList<Search>();
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			//EmAnalyticsFolder folder = EmAnalyticsObjectUtil.getFolderById(folderId, em);
			@SuppressWarnings("unchecked")
			List<EmAnalyticsSearch> searchList = em.createNamedQuery("Search.getSearchListByFolder")
					.setParameter("folderId", folderId)
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getResultList();
			for (EmAnalyticsSearch searchObj : searchList) {
				em.refresh(searchObj);
				rtnobj.add(createSearchObject(searchObj, null));
			}

			return rtnobj;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			_logger.error("Error while retrieving the list of searches for the parent folder: " + folderId, e);
			throw new EMAnalyticsFwkException("Error while retrieving the list of searches for the parent folder: " + folderId,
					EMAnalyticsFwkException.ERR_GENERIC, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}

		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Search> getSearchListByLastAccessDate(int count) throws EMAnalyticsFwkException
	{

		EntityManager em = null;
		List<EmAnalyticsSearch> searchList = null;
		List<Search> rtnobj = new ArrayList<Search>();
		try {
			StringBuilder query = new StringBuilder(LASTACCESS_ORDERBY);
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			searchList = em.createQuery(query.toString())
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).setMaxResults(count)
					.getResultList();

			for (EmAnalyticsSearch searchObj : searchList) {
				rtnobj.add(createSearchObject(searchObj, null));
				//			modifyLastAccessDate(searchObj.getId());
			}
		}
		catch (EMAnalyticsFwkException e) {
			throw e;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			_logger.error("Error while retrieving the list of searches ");
			throw new EMAnalyticsFwkException("Error while retrieving the list of searches ", EMAnalyticsFwkException.ERR_GENERIC,
					null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}

		}
		return rtnobj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Search> getSystemSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		try {
			List<Search> rtnobj = new ArrayList<Search>();
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			//			EmAnalyticsCategory category = EmAnalyticsObjectUtil.getCategoryById(categoryId, em);
			List<EmAnalyticsSearch> searchList = em.createNamedQuery("Search.getSystemSearchListByCategory")
					.setParameter("categoryId", categoryId).getResultList();
			for (EmAnalyticsSearch searchObj : searchList) {
				em.refresh(searchObj);
				rtnobj.add(createSearchObject(searchObj, null));
			}

			return rtnobj;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);

			_logger.error("Error while retrieving the list of searches for the categoryD : " + categoryId, e);
			throw new EMAnalyticsFwkException("Error while retrieving the list of searches for the categoryId : " + categoryId,
					EMAnalyticsFwkException.ERR_GENERIC, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}

		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Search> getWidgetListByCategoryId(long categoryId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		try {
			List<Search> rtnobj = new ArrayList<Search>();
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			List<EmAnalyticsSearch> searchList = em.createNamedQuery("Search.getWidgetListByCategory")
					.setParameter("categoryId", categoryId)
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getResultList();
			for (EmAnalyticsSearch searchObj : searchList) {
				em.refresh(searchObj);
				rtnobj.add(createWidgetObject(searchObj, false));
			}

			return rtnobj;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			_logger.error("Error while retrieving the list of widgets for the categoryId : " + categoryId, e);
			throw new EMAnalyticsFwkException("Error while retrieving the list of widgets for the categoryId : " + categoryId,
					EMAnalyticsFwkException.ERR_GENERIC, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}
		}

	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager#getWidgetListByProviderNames(java.lang.String[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Widget> getWidgetListByProviderNames(boolean includeDashboardIneligible, List<String> providerNames,
			String widgetGroupId) throws EMAnalyticsFwkException
	{
		if (providerNames == null || providerNames.isEmpty()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		if (includeDashboardIneligible) {
			sb.append(JPL_WIDGET_LIST_BY_PROVIDERS_1);
			if (widgetGroupId != null) {
				sb.append(JPL_WIDGET_LIST_BY_PROVIDERS_2);
			}
			sb.append(JPL_WIDGET_LIST_BY_PROVIDERS_3);
		}
		else {
			sb.append(JPL_WIDGET_LIST_BY_PROVIDERS_WO_INELIGIBLE_1);
			if (widgetGroupId != null) {
				sb.append(JPL_WIDGET_LIST_BY_PROVIDERS_WO_INELIGIBLE_2);
			}
			sb.append(JPL_WIDGET_LIST_BY_PROVIDERS_WO_INELIGIBLE_3);
		}
		for (int i = 0; i < providerNames.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("'");
			sb.append(providerNames.get(i));
			sb.append("'");
		}
		if (includeDashboardIneligible) {
			sb.append(JPL_WIDGET_LIST_BY_PROVIDERS_4);
		}
		else {
			sb.append(JPL_WIDGET_LIST_BY_PROVIDERS_WO_INELIGIBLE_4);
		}
		EntityManager em = null;
		try {
			List<Widget> rtnobj = new ArrayList<Widget>();
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			em.getTransaction().begin();
			em.flush();
			em.getTransaction().commit();
			String jql = sb.toString();
			_logger.debug("The JQL to query all widget is {}", jql);
			long start = System.currentTimeMillis();
			Query query = em.createQuery(jql).setHint("eclipselink.left-join-fetch", "e.emAnalyticsSearchParams")
					.setHint("eclipselink.join-fetch", "e.emAnalyticsCategory")
					.setHint("eclipselink.left-join-fetch", "e.emAnalyticsCategory.emAnalyticsCategoryParams")
					.setHint("eclipselink.join-fetch", "e.emAnalyticsFolder")
					.setHint("eclipselink.left-join-fetch", "e.lastAccess")
					.setHint("eclipselink.left-join-fetch", "e.lastAccess.emAnalyticsSearch")
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
					.setParameter("tenantId", TenantContext.getContext().getTenantInternalId());
			if (widgetGroupId != null) {
				query.setParameter("widgetGroupId", Long.valueOf(widgetGroupId));
			}
			if (_logger.isDebugEnabled()) {
				try {
					Session session = em.unwrap(JpaEntityManager.class).getActiveSession();
					DatabaseQuery databaseQuery = ((EJBQueryImpl) query).getDatabaseQuery();
					databaseQuery.prepareCall(session, new DatabaseRecord());
					String sqlString = databaseQuery.getSQLString();
					//				String sqlString = databaseQuery.getTranslatedSQLString(session, new DatabaseRecord());
					_logger.debug("The SQL statement to retrieve all widget is: [{}]", sqlString);
				}
				catch (Exception e) {
					_logger.error("Error when printing debug sql: ", e);
				}
			}
			List<EmAnalyticsSearch> searchList = query.getResultList();
			_logger.debug("Querying to get all widgets takes {} ms, and retrieved {} widgets", System.currentTimeMillis() - start,
					searchList == null ? 0 : searchList.size());
			for (EmAnalyticsSearch searchObj : searchList) {
				//				em.refresh(searchObj);
				rtnobj.add(createWidgetObject(searchObj, false));
			}

			return rtnobj;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			_logger.error("Error while retrieving the list of widgets for providerNames : " + providerNames, e);
			throw new EMAnalyticsFwkException("Error while retrieving the list of widgets for providerNames : " + providerNames,
					EMAnalyticsFwkException.ERR_GENERIC, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public ScreenshotData getWidgetScreenshotById(long widgetId) throws EMAnalyticsFwkException
	{
		String screenshot = null;
		Search search = getSearch(widgetId, true);
		List<SearchParameter> paramList = search.getParameters();
		if (paramList != null && paramList.size() > 0) {
			for (SearchParameter param : paramList) {
				if (EntityJsonUtil.NAME_WIDGET_VISUAL.equals(param.getName())) {
					screenshot = param.getValue();
					break;
				}
			}
		}
		if (screenshot == null) {
			screenshot = SearchManager.BLANK_SCREENSHOT;
		}
		ScreenshotData ssd = new ScreenshotData(screenshot, search.getCreatedOn(), search.getLastModifiedOn());
		return ssd;
	}

	@Override
	public Date modifyLastAccessDate(long searchId) throws EMAnalyticsFwkException
	{

		EntityManager em = null;
		try {
			Date tmp = null;
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			EmAnalyticsSearch searchObj = EmAnalyticsObjectUtil.getSearchById(searchId, em);
			EmAnalyticsLastAccess accessObj = null;
			if (searchObj != null) {
				EmAnalyticsLastAccessPK pk = new EmAnalyticsLastAccessPK();
				pk.setAccessedBy(searchObj.getAccessedBy());
				pk.setObjectId(searchObj.getObjectId());
				pk.setObjectType(searchObj.getObjectType());
				accessObj = em.find(EmAnalyticsLastAccess.class, pk);
				if (accessObj != null) {
					tmp = DateUtil.getCurrentUTCTime();
					accessObj.setAccessDate(tmp);
					em.getTransaction().begin();
					em.persist(accessObj);
					em.getTransaction().commit();
				}
				return tmp;
			}
			else {
				throw new Exception("Invalid search id: " + searchId);
			}
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			_logger.error("Error while retrieving the list of searches ");
			throw new EMAnalyticsFwkException("Error while retrieving the list of searches ", EMAnalyticsFwkException.ERR_GENERIC,
					null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}

		}
	}

	public List<Search> saveMultipleSearch(List<ImportSearchImpl> searchList) throws Exception
	{
		return saveMultipleSearch(searchList, false);
	}

	public List<Search> saveMultipleSearch(List<ImportSearchImpl> searchList, boolean isOobSearch) throws Exception
	{

		EntityManager em = null;
		boolean bCommit = true;
		boolean bResult = false;
		List<Search> importedList = new ArrayList<Search>();
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			em.getTransaction().begin();
			for (ImportSearchImpl tmpImportSrImpl : searchList) {
				Search search = tmpImportSrImpl.getSearch();
				Object obj = tmpImportSrImpl.getFolderDetails();
				Object cateObj = tmpImportSrImpl.getCategoryDetails();
				try {
					if (search.getId() != null && search.getId() > 0) {

						EmAnalyticsSearch emSearch = EmAnalyticsObjectUtil.getSearchById(search.getId(), em);
						// checking that ,it is system search and method is not  calling from updateutility.
						if (!isOobSearch && emSearch != null && emSearch.getSystemSearch() != null
								&& emSearch.getSystemSearch().intValue() == 1) {
							importedList.add(createSearchObject(emSearch, null));
							continue;
						}

						// checking that ,it is system search and method is not  calling from updateutility.
						if (isOobSearch && emSearch != null && emSearch.getSystemSearch() != null
								&& emSearch.getSystemSearch().intValue() != 1) {
							importedList.add(createSearchObject(emSearch, null));
							continue;
						}

						if (obj != null) {
							if (obj instanceof Integer) {
								EmAnalyticsFolder tmpfld = EmAnalyticsObjectUtil.getFolderById(((Integer) obj).longValue(), em);
								if (tmpfld != null) {
									search.setFolderId((Integer) obj);
								}
								else {
									continue;
								}

							}
						}

						if (obj != null && obj instanceof FolderImpl) {
							Folder fld = (Folder) obj;
							if (fld.getParentId() == null || fld.getParentId() == 0) {
								fld.setParentId(1);
							}
							EmAnalyticsFolder objFolder = EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(fld);

							if (objFolder != null) {
								search.setFolderId((int) objFolder.getFolderId());
							}

						}

						if (obj instanceof FolderImpl) {
							if (search.getFolderId() == null) {
								EmAnalyticsFolder folderObj = getEmAnalyticsFolderBySearch(tmpImportSrImpl, em);
								em.persist(folderObj);
								search.setFolderId((int) folderObj.getFolderId());
							}
						}

						if (cateObj instanceof Integer) {
							EmAnalyticsSearch searchEntity = EmAnalyticsObjectUtil.getSearchById(search.getId(), em);

							if (searchEntity.getEmAnalyticsCategory().getCategoryId() == ((Integer) cateObj).longValue()) {
								search.setCategoryId((Integer) cateObj);
							}
							else {
								continue;
							}
						}

						if (cateObj instanceof CategoryImpl) {
							if (search.getCategoryId() == null) {
								EmAnalyticsCategory iCategory = getEmAnalyticsCategoryBySearch(tmpImportSrImpl, em);
								search.setCategoryId((int) iCategory.getCategoryId());
							}
						}

						emSearch = EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit(search, em);
						em.merge(emSearch);
						tmpImportSrImpl.setId((int) emSearch.getId());
						importedList.add(createSearchObject(emSearch, null));
					}
					else {
						EmAnalyticsSearch searchEntity = null;
						try {
							if (obj != null && obj instanceof Integer) {
								Integer id = null;
								EmAnalyticsFolder pFolderObj = null;
								searchEntity = (EmAnalyticsSearch) em.createNamedQuery("Search.getSearchByName")
										.setParameter("folderId", id).setParameter("searchName", search.getName())
										.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
										.getSingleResult();
								tmpImportSrImpl.setId((int) searchEntity.getId());
								importedList.add(createSearchObject(searchEntity, null));
							}
						}
						catch (NoResultException e) {
							searchEntity = null;
						}
						if (searchEntity == null) {
							EmAnalyticsFolder folder = null;
							Folder fld = null;

							if (obj == null) {

								continue;
							}
							if (obj != null) {
								if (obj instanceof Integer) {
									EmAnalyticsFolder tmpfld = EmAnalyticsObjectUtil.getFolderById(((Integer) obj).longValue(),
											em);
									if (tmpfld != null) {
										search.setFolderId((Integer) obj);
									}
									else {
										importedList.add(search);
										continue;
									}
								}
							}
							if (cateObj == null) {
								continue;
							}
							if (cateObj instanceof Integer) {
								EmAnalyticsCategory categoryObj = EmAnalyticsObjectUtil
										.getCategoryById(((Integer) cateObj).longValue(), em);
								if (categoryObj != null) {
									search.setCategoryId((Integer) cateObj);
								}
								else {

									importedList.add(search);
									continue;
								}
							}

							if (obj instanceof FolderImpl) {
								fld = (Folder) obj;
								if (fld.getParentId() == null || fld.getParentId() == 0) {
									fld.setParentId(1);
								}
								EmAnalyticsFolder objFolder = EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(fld);
								if (objFolder != null) {
									search.setFolderId((int) objFolder.getFolderId());
								}
							}
							EmAnalyticsCategory categoryObj = null;
							if (cateObj != null) {
								if (cateObj instanceof CategoryImpl) {
									try {
										categoryObj = (EmAnalyticsCategory) em.createNamedQuery("Category.getCategoryByName")
												.setParameter("categoryName", ((Category) cateObj).getName())
												.setParameter(QueryParameterConstant.USER_NAME,
														TenantContext.getContext().getUsername())
												.getSingleResult();
									}
									catch (NoResultException e) {

									}
									if (categoryObj != null) {

										search.setCategoryId((int) categoryObj.getCategoryId());
									}

								}

							}

							if (search != null) {
								if (search.getFolderId() != null) {
									try {

										searchEntity = (EmAnalyticsSearch) em.createNamedQuery("Search.getSearchByName")
												.setParameter("folderId", search.getFolderId())
												.setParameter("searchName", search.getName())
												.setParameter(QueryParameterConstant.USER_NAME,
														TenantContext.getContext().getUsername())
												.getSingleResult();
									}
									catch (NoResultException e) {

									}
									if (searchEntity != null) {
										tmpImportSrImpl.setId((int) searchEntity.getId());
										importedList.add(createSearchObject(searchEntity, null));
										continue;
									}
								}
							}

							if (obj instanceof FolderImpl) {
								if (search.getFolderId() == null) {
									folder = getEmAnalyticsFolderBySearch(tmpImportSrImpl, em);
									em.persist(folder);
									search.setFolderId((int) folder.getFolderId());
								}
							}

							if (cateObj instanceof CategoryImpl) {
								if (search.getCategoryId() == null) {
									EmAnalyticsCategory iCategory = getEmAnalyticsCategoryBySearch(tmpImportSrImpl, em);
									em.persist(iCategory);
									search.setCategoryId((int) iCategory.getCategoryId());
								}
							}

							EmAnalyticsSearch emSearch = EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(search, em);
							em.persist(emSearch);
							updateSearchLastAccess(emSearch, emSearch.getLastModificationDate());
							tmpImportSrImpl.setId((int) emSearch.getId());
							importedList.add(createSearchObject(emSearch, null));
						}
					}
				}
				catch (PersistenceException eme) {
					bCommit = false;
					_logger.error("Error while importing the search: " + search.getName(), eme);
					throw eme;
				}
			}
			if (bCommit) {
				em.getTransaction().commit();
			}
			else {
				em.getTransaction().rollback();
			}
		}
		catch (Exception e) {
			importedList.clear();
			_logger.error("Error in saveMultipleSearches", e);
			e.printStackTrace();
			throw e;
		}
		finally {
			if (em != null) {
				em.close();
			}

		}
		return importedList;
	}

	@Override
	public Search saveSearch(Search search) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			EmAnalyticsSearch searchEntity = EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(search, em);
			em.getTransaction().begin();
			em.persist(searchEntity);
			// when a search is created, creation date/modification date/last access date keeps the same
			updateSearchLastAccess(searchEntity, searchEntity.getLastModificationDate());
			em.getTransaction().commit();
			em.refresh(searchEntity);
			return createSearchObject(searchEntity, null);
		}
		catch (EMAnalyticsFwkException eme) {
			_logger.error("Search with name " + search.getName() + " was saved but could not bve retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			processUniqueConstraints(search, em, dmlce);
			EmAnalyticsProcessingException.processSearchPersistantException(dmlce, search.getName());
			_logger.error("Persistence error while saving the search: " + search.getName(), dmlce);
			throw new EMAnalyticsFwkException("Error while saving the search: " + search.getName(),
					EMAnalyticsFwkException.ERR_CREATE_SEARCH, null, dmlce);
		}
		catch (Exception e) {
			_logger.error("Error while saving the search: " + search.getName(), e);
			throw new EMAnalyticsFwkException("Error while saving the search: " + search.getName(),
					EMAnalyticsFwkException.ERR_CREATE_SEARCH, null, e);
		}
		finally {
			if (em != null) {
				em.close();
			}

		}
	}

	private Search createSearchObject(EmAnalyticsSearch searchObj, Search search) throws EMAnalyticsFwkException
	{
		SearchImpl rtnObj = null;
		try {
			if (search != null) {
				// populate the current object
				rtnObj = (SearchImpl) search;

			}
			else {
				rtnObj = (SearchImpl) createNewSearch();

			}

			rtnObj.setId((int) searchObj.getId());
			if (searchObj.getSearchGuid() != null) {
				rtnObj.setGuid(searchObj.getSearchGuid().toString());
			}

			// TODO : Abhinav Handle the internationalization via MGMT_MESSAGES
			// handling name here
			String nlsid = searchObj.getNameNlsid();
			String subsystem = searchObj.getNameSubsystem();
			if (nlsid == null || nlsid.trim().length() == 0 || subsystem == null || subsystem.trim().length() == 0) {
				rtnObj.setName(searchObj.getName());
			}
			else {
				// here the code should come !! get localized stuff from
				// MGMT_MESSAGES
				rtnObj.setName(searchObj.getName());
			}

			nlsid = searchObj.getDescriptionNlsid();
			subsystem = searchObj.getDescriptionSubsystem();
			if (nlsid == null || nlsid.trim().length() == 0 || subsystem == null || subsystem.trim().length() == 0) {
				rtnObj.setDescription(searchObj.getDescription());
			}
			else {
				// here the code should come !! get localized stuff from
				// MGMT_MESSAGES
				rtnObj.setDescription(searchObj.getDescription());
			}

			rtnObj.setOwner(searchObj.getOwner());
			rtnObj.setCreatedOn(searchObj.getCreationDate());
			rtnObj.setLastModifiedBy(searchObj.getLastModifiedBy());
			rtnObj.setLastModifiedOn(searchObj.getLastModificationDate());
			rtnObj.setLastAccessDate(searchObj.getAccessDate());
			if (searchObj.getMetadataClob() != null && searchObj.getMetadataClob().length() > 0) {
				char[] metadataCharArr = new char[searchObj.getMetadataClob().length()];
				Reader reader = new StringReader(searchObj.getMetadataClob());
				reader.read(metadataCharArr);
				rtnObj.setMetadata(new String(metadataCharArr));
			}

			rtnObj.setQueryStr(searchObj.getSearchDisplayStr());
			rtnObj.setLocked(searchObj.getIsLocked() != null && searchObj.getIsLocked().intValue() == 1 ? true : false);
			rtnObj.setCategoryId((int) searchObj.getEmAnalyticsCategory().getCategoryId());

			rtnObj.setFolderId((int) searchObj.getEmAnalyticsFolder().getFolderId());

			rtnObj.setUiHidden(searchObj.getUiHidden() != null && searchObj.getUiHidden().intValue() == 1 ? true : false);
			rtnObj.setSystemSearch(
					searchObj.getSystemSearch() != null && searchObj.getSystemSearch().intValue() == 1 ? true : false);
			rtnObj.setLastAccessDate(searchObj.getAccessDate());
			rtnObj.setIsWidget(searchObj.getIsWidget() == 1 ? true : false);

			{
				List<SearchParameter> searchParams = null;
				// get parameters
				Set<EmAnalyticsSearchParam> params = searchObj.getEmAnalyticsSearchParams();
				for (EmAnalyticsSearchParam paramObj : params) {
					EmAnalyticsSearchParam paramVORow = paramObj;
					if (searchParams == null) {
						searchParams = new ArrayList<SearchParameter>();
					}
					SearchParameter param = new SearchParameter();
					param.setName(paramVORow.getName());
					param.setAttributes(paramVORow.getParamAttributes());
					param.setType(ParameterType.fromIntValue(paramVORow.getParamType().intValue()));

					if (ParameterType.CLOB.equals(param.getType())) {
						System.out.println("Clob value =" + paramVORow.getParamValueClob());
						if (paramVORow.getParamValueClob() != null) {
							char[] charArr = new char[paramVORow.getParamValueClob().length()];
							Reader reader = new StringReader(new String(paramVORow.getParamValueClob()));
							reader.read(charArr);
							param.setValue(new String(charArr));
						}
						else {
							param.setValue(null);
						}
					}
					else if (ParameterType.STRING.equals(param.getType())) {
						param.setValue(paramVORow.getParamValueStr());
					}
					searchParams.add(param);
				}
				rtnObj.setParameters(searchParams);
			}

			return rtnObj;
		}
		catch (Exception e) {
			_logger.error("Error while getting the search object", e);
			throw new EMAnalyticsFwkException("Error while getting the search object", EMAnalyticsFwkException.ERR_GET_SEARCH,
					null, e);
		}
	}

	private Widget createWidgetObject(EmAnalyticsSearch searchObj, boolean loadScreenshot) throws EMAnalyticsFwkException
	{
		WidgetImpl rtnObj = null;
		try {
			rtnObj = new WidgetImpl();
			rtnObj.setId((int) searchObj.getId());

			// TODO : Handle the internationalization via MGMT_MESSAGES
			// handling name here
			String nlsid = searchObj.getNameNlsid();
			String subsystem = searchObj.getNameSubsystem();
			if (nlsid == null || nlsid.trim().length() == 0 || subsystem == null || subsystem.trim().length() == 0) {
				rtnObj.setName(searchObj.getName());
			}
			else {
				// here the code should come !! get localized stuff from
				// MGMT_MESSAGES
				rtnObj.setName(searchObj.getName());
			}

			nlsid = searchObj.getDescriptionNlsid();
			subsystem = searchObj.getDescriptionSubsystem();
			if (nlsid == null || nlsid.trim().length() == 0 || subsystem == null || subsystem.trim().length() == 0) {
				rtnObj.setDescription(searchObj.getDescription());
			}
			else {
				// here the code should come !! get localized stuff from
				// MGMT_MESSAGES
				rtnObj.setDescription(searchObj.getDescription());
			}

			rtnObj.setOwner(searchObj.getOwner());
			rtnObj.setCreatedOn(searchObj.getCreationDate());
			rtnObj.setLastModifiedBy(searchObj.getLastModifiedBy());
			rtnObj.setLastModifiedOn(searchObj.getLastModificationDate());
			rtnObj.setCategoryId((int) searchObj.getEmAnalyticsCategory().getCategoryId());
			rtnObj.setFolderId((int) searchObj.getEmAnalyticsFolder().getFolderId());
			rtnObj.setLastAccessDate(searchObj.getAccessDate());
			rtnObj.setIsWidget(searchObj.getIsWidget() == 1 ? true : false);

			List<SearchParameter> searchParams = null;
			// get parameters
			Set<EmAnalyticsSearchParam> params = searchObj.getEmAnalyticsSearchParams();
			for (EmAnalyticsSearchParam paramObj : params) {
				EmAnalyticsSearchParam paramVORow = paramObj;
				if (searchParams == null) {
					searchParams = new ArrayList<SearchParameter>();
				}
				SearchParameter param = new SearchParameter();
				param.setName(paramVORow.getName());
				param.setType(ParameterType.fromIntValue(paramVORow.getParamType().intValue()));

				if (ParameterType.STRING.equals(param.getType())) {
					param.setValue(paramVORow.getParamValueStr());
				}
				else if (loadScreenshot && EntityJsonUtil.NAME_WIDGET_VISUAL.equals(param.getName())
						&& ParameterType.CLOB.equals(param.getType())) {
					System.out.println("Clob value =" + paramVORow.getParamValueClob());
					if (paramVORow.getParamValueClob() != null) {
						char[] charArr = new char[paramVORow.getParamValueClob().length()];
						Reader reader = new StringReader(new String(paramVORow.getParamValueClob()));
						reader.read(charArr);
						param.setValue(new String(charArr));
					}
					else {
						param.setValue(null);
					}
				}
				searchParams.add(param);
			}
			rtnObj.setParameters(searchParams);

			Category category = CategoryManagerImpl.createCategoryObject(searchObj.getEmAnalyticsCategory(), null);
			rtnObj.setCategory(category);

			return rtnObj;
		}
		catch (Exception e) {
			_logger.error("Error while getting the widget object", e);
			throw new EMAnalyticsFwkException("Error while getting the widget object", EMAnalyticsFwkException.ERR_GET_SEARCH,
					null, e);
		}
	}

	/*
	 * private EmAnalyticsFolder
	 * getEmAnalyticsFolderByCategory(ImportCategoryImpl category ,
	 * EntityManager em) { EmAnalyticsFolder folder =null; try {
	 * if(category.getDefaultFolderId()!=null) folder
	 * =em.find(EmAnalyticsFolder.class , new
	 * Long(category.getDefaultFolderId())); else {
	 * if(category.getFolderDetails()!=null){
	 *
	 *
	 * try { folder = EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd(
	 * (Folder)category.getFolderDetails(),em); folder =(EmAnalyticsFolder)
	 * em.createNamedQuery("Folder.getRootFolderByName").
	 * setParameter("foldername", folder.getName()). getSingleResult();
	 *
	 * }catch(NoResultException e){ folder=null; } } } } catch
	 * (EMAnalyticsFwkException e) { folder =null; } return folder; }
	 */

	/*private void OrderBybuilder(StringBuilder query, String[] orderBy)
	{
		// incidentally the field names for EmAnalyticsSearch and SearchImpl is
		// same , hence order by will work here
		if (orderBy != null && orderBy.length != 0 && orderBy[0].length() != 0) {
			if (orderBy[0] != null) {
				query.append(" ORDER BY " + SEARCH_ENT_PREFIX + orderBy[0].replaceAll("-", " "));
			}
			for (int i = 1; i < orderBy.length; i++) {
				if (orderBy[i] != null) {
					query.append("," + SEARCH_ENT_PREFIX + orderBy[i].replaceAll("-", " "));
				}
			}
		}
	
	}*/

	private EmAnalyticsCategory getEmAnalyticsCategoryBySearch(ImportSearchImpl search, EntityManager em)
	{
		EmAnalyticsCategory category = null;
		try {
			if (search.getSearch().getCategoryId() != null) {
				category = EmAnalyticsObjectUtil.getCategoryById(search.getSearch().getCategoryId(), em);
			}
			else {
				if (search.getCategoryDetails() != null) {

					try {
						category = (EmAnalyticsCategory) em.createNamedQuery("Category.getCategoryByName")
								.setParameter("categoryName", ((Category) search.getCategoryDetails()).getName())
								.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
								.getSingleResult();
					}
					catch (NoResultException e) {

						category = EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd((Category) search.getCategoryDetails(), em);
						em.persist(category);
					}

					// Category.getCategoryByName
				}
			}
		}
		catch (Exception e) {
			category = null;
		}
		return category;
	}

	private EmAnalyticsFolder getEmAnalyticsFolderBySearch(ImportSearchImpl search, EntityManager em)
	{
		EmAnalyticsFolder folder = null;
		try {
			if (search.getSearch().getFolderId() != null) {
				folder = EmAnalyticsObjectUtil.getFolderById(search.getSearch().getFolderId(), em);
			}
			else {
				if (search.getFolderDetails() != null) {

					try {
						folder = EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd((Folder) search.getFolderDetails(), em);
						folder = (EmAnalyticsFolder) em.createNamedQuery("Folder.getRootFolderByName")
								.setParameter("foldername", folder.getName())
								.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
								.getSingleResult();

					}
					catch (NoResultException e) {

					}
				}
			}
		}
		catch (EMAnalyticsFwkException e) {
			folder = null;
		}
		return folder;
	}

	private Search getSearch(long searchId, boolean loadWidgetOnly) throws EMAnalyticsFwkException
	{
		_logger.info("Retrieving search with id: " + searchId);
		EntityManager em = null;
		Search search = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			EmAnalyticsSearch searchObj = EmAnalyticsObjectUtil.getSearchById(searchId, em);
			if (searchObj != null) {
				em.refresh(searchObj);
				if (loadWidgetOnly) {
					search = createWidgetObject(searchObj, true);
				}
				else {
					search = createSearchObject(searchObj, null);
				}
			}
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			String errMsg = "Error while getting the search object by ID: " + searchId;
			_logger.error(errMsg, e);
			throw new EMAnalyticsFwkException(errMsg, EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID, new Object[] { searchId },
					e);

		}
		finally {
			if (em != null) {
				em.close();
			}

		}
		if (search == null) {
			String errMsg = "Search identified by ID: " + searchId + " does not exist";
			_logger.error(errMsg);
			throw new EMAnalyticsFwkException(errMsg, EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID, new Object[] { searchId });
		}
		return search;
	}

	private void processUniqueConstraints(Search search, EntityManager em, PersistenceException dmlce)
			throws EMAnalyticsFwkException
	{

		if (dmlce.getCause() != null && dmlce.getCause().getMessage().contains("ANALYTICS_SEARCH_U01")) {
			EmAnalyticsSearch searchEntity = (EmAnalyticsSearch) em.createNamedQuery("Search.getSearchByName")
					.setParameter("folderId", search.getFolderId()).setParameter("searchName", search.getName())
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getSingleResult();
			String result = EntityJsonUtil.getErrorJsonObject(searchEntity.getId(),
					"Search name '" + search.getName() + "' already exist", EMAnalyticsFwkException.ERR_SEARCH_DUP_NAME)
					.toString();
			throw new EMAnalyticsFwkException(result, EMAnalyticsFwkException.ERR_SEARCH_DUP_NAME,
					new Object[] { search.getName() });
		}

	}

	private void updateSearchLastAccess(EmAnalyticsSearch search, Date lastAccessDate)
	{
		if (search == null) {
			return;
		}
		if (lastAccessDate == null) {
			search.setAccessDate(DateUtil.getCurrentUTCTime());
		}
		else {
			search.setAccessDate(lastAccessDate);
		}

	}

}
