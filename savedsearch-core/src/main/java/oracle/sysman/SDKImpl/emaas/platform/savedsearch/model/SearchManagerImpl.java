package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.IdGenerator;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.QueryParameterConstant;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.ZDTContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EmAnalyticsProcessingException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.RequestContext.RequestType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.nls.DatabaseResourceBundleUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.WidgetChangeNotification;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.ScreenshotData;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearchParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.eclipse.persistence.sessions.DatabaseRecord;
import org.eclipse.persistence.sessions.Session;

public class SearchManagerImpl extends SearchManager
{

	//  LoggergetSearchListByCategoryId
	private static final Logger LOGGER = LogManager.getLogger(SearchManagerImpl.class);
	public static final Long DEFAULT_TENANT_ID = -11L;
	public static final String DEFAULT_CURRENT_USER = "ORACLE";
	public static final SearchManagerImpl SEARCH_MANAGER = new SearchManagerImpl();
	private static final String LASTACCESS_ORDERBY = "SELECT e FROM EmAnalyticsSearch e  where e.deleted=0 and e.owner in ('ORACLE',:userName) order by e.lastModificationDate DESC ";
	private static final String LASTACCESS_ORDERBY_FOR_INTERNAL_TENANT = "SELECT e FROM EmAnalyticsSearch e  where e.deleted=0 order by e.lastModificationDate DESC ";

	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_1 = "SELECT e FROM EmAnalyticsSearch e " + "where ";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_2 = "e.emAnalyticsCategory.categoryId=:widgetGroupId AND ";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_3 = "e.emAnalyticsCategory.categoryId in (select c.categoryId from EmAnalyticsCategory c where c.providerName in (";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_4 = "))  "
			+ "AND e.deleted=0 AND e.isWidget=1 AND (e.tenantId=:tenantId OR e.tenantId ="+DEFAULT_TENANT_ID+") AND (e.owner=:userName OR e.systemSearch =1)";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_WO_INELIGIBLE_1 = "SELECT e FROM EmAnalyticsSearch e LEFT JOIN EmAnalyticsSearchParam pm "
			+ "ON pm.searchId=e.id AND (pm.tenantId=:tenantId OR pm.tenantId = "+DEFAULT_TENANT_ID+") AND pm.name = 'DASHBOARD_INELIGIBLE' where (e.tenantId=:tenantId or e.tenantId ="+DEFAULT_TENANT_ID+") AND ";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_WO_INELIGIBLE_2 = "e.emAnalyticsCategory.categoryId=:widgetGroupId AND ";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_WO_INELIGIBLE_3 = "e.emAnalyticsCategory.categoryId in "
			+ "(select c.categoryId from EmAnalyticsCategory c LEFT JOIN EmAnalyticsCategoryParam cm "
			+ "on cm.categoryId=c.categoryId AND cm.name='DASHBOARD_INELIGIBLE' AND (cm.tenantId=:tenantId OR cm.tenantId = "+DEFAULT_TENANT_ID+")"
			+ "where c.providerName in (";
	private static final String JPL_WIDGET_LIST_BY_PROVIDERS_WO_INELIGIBLE_4 = ") AND (cm.value IS NULL OR cm.value<>'1')) "
			+ "AND e.deleted=0 AND e.isWidget=1 AND (e.tenantId=:tenantId OR e.tenantId ="+DEFAULT_TENANT_ID+") AND (e.owner=:userName OR e.systemSearch =1) "
			+ "AND (pm.paramValueStr IS NULL OR pm.paramValueStr<>'1')";

	private static final String DEFAULT_DB_VALUE = "0";
	
	private static final String SQL_GET_ID_NAME_BY_UNIQUE_KEY = "SELECT SEARCH_ID, NAME FROM EMS_ANALYTICS_SEARCH "
			+ "WHERE NAME = ? AND CATEGORY_ID=? AND FOLDER_ID = ? AND DELETED=? AND OWNER=?";

	//+ " EmAnalyticsLastAccess t where e.searchId = t.objectId ";

	/**
	 * Get SearchManagerImpl singleton INSTANCE.
	 *
	 * @return Instance of SearchManagerImpl
	 */
	public static SearchManagerImpl getInstance()
	{
		return SEARCH_MANAGER;
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
	public EmAnalyticsSearch deleteSearch(BigInteger searchId, boolean permanently) throws EMAnalyticsFwkException
	{
		LOGGER.info("Deleting search with id: " + searchId);
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
			searchObj.setLastModificationDate(DateUtil.getGatewayTime());
			searchObj.setTenantId(TenantContext.getContext().getTenantInternalId());
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.setProperty("permanent", permanently);		
			if (permanently) {
				em.remove(searchObj);
			}
			else {
				em.merge(searchObj);
			}
			em.getTransaction().commit();
			return searchObj;
		}
		catch (EMAnalyticsFwkException eme) {
			LOGGER.error("Search with Id: " + searchId + " does not exist", eme);
			throw eme;
		}
		catch (Exception e) {

			EmAnalyticsProcessingException.processSearchPersistantException(e, searchObj.getName());
			LOGGER.error("Error while getting the search object by ID: " + searchId, e);
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
	public EmAnalyticsSearch deleteSearchWithEm(BigInteger searchId, EntityManager em, boolean permanently) throws EMAnalyticsFwkException
	{
		LOGGER.info("Deleting search with id: " + searchId);
		EmAnalyticsSearch searchObj = null;
		try {
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
			searchObj.setLastModificationDate(DateUtil.getGatewayTime());
			searchObj.setTenantId(TenantContext.getContext().getTenantInternalId());
			em.setProperty("permanent", permanently);
			if (permanently) {
				em.remove(searchObj);
			}
			else {
				em.merge(searchObj);
			}
			return searchObj;
		}
		catch (EMAnalyticsFwkException eme) {
			LOGGER.error("Search with Id: " + searchId + " does not exist", eme);
			throw eme;
		}
		catch (Exception e) {

			EmAnalyticsProcessingException.processSearchPersistantException(e, searchObj.getName());
			LOGGER.error("Error while getting the search object by ID: " + searchId, e);
			throw new EMAnalyticsFwkException("Error while deleting the search object by ID: " + searchId,
					EMAnalyticsFwkException.ERR_DELETE_SEARCH, new Object[] { searchId }, e);

		}
	}

	@Override
	public void deleteSearchByName(String searchName, boolean isExactly) throws EMAnalyticsFwkException
	{
		LOGGER.info("Deleting search with Name: {}, Exactly {}", searchName, isExactly);
		EntityManager entityManager = null;
		List<EmAnalyticsSearch> emAnalyticsSearchList = new ArrayList<>();
		try {
			entityManager = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			if (isExactly) {
				LOGGER.debug("DELETE {} SOFTLY EXACTLY", searchName);
				emAnalyticsSearchList.add(EmAnalyticsObjectUtil.getSearchByNameForDelete(searchName, entityManager));
			}
			else {
				LOGGER.debug("DELETE {} SOFTLY NONEXACTLY", searchName);
				emAnalyticsSearchList = EmAnalyticsObjectUtil.getSearchListByNamePatternForDelete(searchName, entityManager);
			}
			if (emAnalyticsSearchList.isEmpty() || emAnalyticsSearchList.get(0) == null) {
				throw new EMAnalyticsFwkException(
						"Search with Name : " + searchName
								+ " does not exist please check if it's a system search or you're not the owner",
						EMAnalyticsFwkException.ERR_GET_SEARCH_BY_NAME, null);
			}
			if (!entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().begin();
			}
			LOGGER.debug("START TRANSACTION DELETE {}", searchName);
			for (EmAnalyticsSearch temp : emAnalyticsSearchList) {
				LOGGER.debug("START DELETE {}", temp.getId());
				temp.setDeleted(temp.getId());
				temp.setTenantId(TenantContext.getContext().getTenantInternalId());
				entityManager.merge(temp);
				LOGGER.info("DELETED SEARCH WITH ID: {}", temp.getId());
			}
			LOGGER.debug("TRANSACTION COMMITTING");
			entityManager.getTransaction().commit();
		}
		catch (EMAnalyticsFwkException eme) {
			LOGGER.error("Search with Name: " + searchName + " does not exist", eme);
			throw eme;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, searchName);
			LOGGER.error("Error while getting the search object by Name: " + searchName, e);
			throw new EMAnalyticsFwkException("Error while deleting the search object by Name: " + searchName,
					EMAnalyticsFwkException.ERR_DELETE_SEARCH, new Object[] { searchName }, e);
		}
		finally {
			if (entityManager != null) {
				LOGGER.debug("TRANSACTION CLOSING");
				entityManager.close();
			}
		}
	}

	@Override
	public void deleteTargetCard(BigInteger targetCardId, boolean permanently) throws EMAnalyticsFwkException
	{
		LOGGER.info("Deleting target card with id: " + targetCardId);
		EntityManager em = null;
		EmAnalyticsSearch targetCardObj = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			if (permanently) {
				targetCardObj = EmAnalyticsObjectUtil.getSearchByIdForDelete(targetCardId, em);
			}
			else {
				targetCardObj = EmAnalyticsObjectUtil.getSearchById(targetCardId, em);
			}
			if (targetCardObj == null) {
				throw new EMAnalyticsFwkException("Target Card with Id: " + targetCardId + " does not exist",
						EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID, null);
			}
			/*
						if (targetCardObj.getSystemSearch() != null && targetCardObj.getSystemSearch().intValue() == 1) {
							throw new EMAnalyticsFwkException("Target Card with Id: " + targetCardId + " is system search and NOT allowed to delete",
									EMAnalyticsFwkException.ERR_DELETE_SEARCH, null);
						}*/
			targetCardObj.setDeleted(targetCardId);
			targetCardObj.setTenantId(TenantContext.getContext().getTenantInternalId());
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}			
			em.setProperty("permanent", permanently);			
			if (permanently) {
				em.remove(targetCardObj);
			}
			else {
				em.merge(targetCardObj);
			}
			em.getTransaction().commit();
		}
		catch (EMAnalyticsFwkException eme) {
			LOGGER.error("Target Card with Id: " + targetCardId + " does not exist", eme);
			throw eme;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, targetCardObj.getName());
			LOGGER.error("Error while getting the Target Card object by ID: " + targetCardId, e);
			throw new EMAnalyticsFwkException("Error while deleting the Target Card object by ID: " + targetCardId,
					EMAnalyticsFwkException.ERR_DELETE_SEARCH, new Object[] { targetCardId }, e);

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
		return editSearch(search, false);
	}

	@Override
	public Search editSearch(Search search, boolean canEditSysSearch) throws EMAnalyticsFwkException
	{
		LOGGER.info("Editing search with id : " + search.getId());
		EntityManager em = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			EmAnalyticsSearch searchEntity = EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit(search, em);
			if (searchEntity != null && searchEntity.getSystemSearch() != null && searchEntity.getSystemSearch().intValue() == 1
					&& !canEditSysSearch) {
				throw new EMAnalyticsFwkException(
						"Search with Id: " + searchEntity.getId() + " is system search and NOT allowed to edit",
						EMAnalyticsFwkException.ERR_UPDATE_SEARCH, null);
			}
			searchEntity.setTenantId(TenantContext.getContext().getTenantInternalId());
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.merge(searchEntity);
			em.getTransaction().commit();
			if (searchEntity.getIsWidget() == 1L) {
				new WidgetChangeNotification().notify(search, null);
			}
			return createSearchObject(searchEntity, null);
		}
		catch (EMAnalyticsFwkException eme) {

			LOGGER.error("Search with name " + search.getName() + " was updated but could not be retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			processUniqueConstraints(search, em, dmlce);
			EmAnalyticsProcessingException.processSearchPersistantException(dmlce, null);
			LOGGER.error("Persistence Error while updating the search: " + search.getName(), dmlce);
			throw new EMAnalyticsFwkException("Error while updating the search: " + search.getName(),
					EMAnalyticsFwkException.ERR_UPDATE_SEARCH, null, dmlce);

		}
		catch (Exception e) {
			LOGGER.error("Error while updating the search: " + search.getName(), e);
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
	public Search editSearchWithEm(Search search, EntityManager em, boolean canEditSysSearch) throws EMAnalyticsFwkException
	{
		LOGGER.info("Editing search with id : " + search.getId());
		try {
			EmAnalyticsSearch searchEntity = EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit(search, em);
			if (searchEntity != null && searchEntity.getSystemSearch() != null && searchEntity.getSystemSearch().intValue() == 1
					&& !canEditSysSearch) {
				throw new EMAnalyticsFwkException(
						"Search with Id: " + searchEntity.getId() + " is system search and NOT allowed to edit",
						EMAnalyticsFwkException.ERR_UPDATE_SEARCH, null);
			}
			searchEntity.setTenantId(TenantContext.getContext().getTenantInternalId());
			em.merge(searchEntity);
			if (searchEntity.getIsWidget() == 1L) {
				new WidgetChangeNotification().notify(search, null);
			}
			return createSearchObject(searchEntity, null);
		}
		catch (EMAnalyticsFwkException eme) {

			LOGGER.error("Search with name " + search.getName() + " was updated but could not be retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			processUniqueConstraints(search, em, dmlce);
			EmAnalyticsProcessingException.processSearchPersistantException(dmlce, null);
			LOGGER.error("Persistence Error while updating the search: " + search.getName(), dmlce);
			throw new EMAnalyticsFwkException("Error while updating the search: " + search.getName(),
					EMAnalyticsFwkException.ERR_UPDATE_SEARCH, null, dmlce);

		}
		catch (Exception e) {
			LOGGER.error("Error while updating the search: " + search.getName(), e);
			throw new EMAnalyticsFwkException("Error while updating the search: " + search.getName(),
					EMAnalyticsFwkException.ERR_UPDATE_SEARCH, null, e);
		}
	}

	@Override
	public Search editSearchWithEm(Search search,  boolean canEditSysSearch, EntityManager em) throws EMAnalyticsFwkException {
		LOGGER.info("Editing search with id : " + search.getId());
		try {
			EmAnalyticsSearch searchEntity = EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit(search, em);
			if (searchEntity != null && searchEntity.getSystemSearch() != null && searchEntity.getSystemSearch().intValue() == 1
					&& !canEditSysSearch) {
				throw new EMAnalyticsFwkException(
						"Search with Id: " + searchEntity.getId() + " is system search and NOT allowed to edit",
						EMAnalyticsFwkException.ERR_UPDATE_SEARCH, null);
			}
			searchEntity.setTenantId(TenantContext.getContext().getTenantInternalId());
			em.merge(searchEntity);
			if (searchEntity.getIsWidget() == 1L) {
				new WidgetChangeNotification().notify(search, null);
			}
			return createSearchObject(searchEntity, null);
		}
		catch (EMAnalyticsFwkException eme) {

			LOGGER.error("Search with name " + search.getName() + " was updated but could not be retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			processUniqueConstraints(search, em, dmlce);
			EmAnalyticsProcessingException.processSearchPersistantException(dmlce, null);
			LOGGER.error("Persistence Error while updating the search: " + search.getName(), dmlce);
			throw new EMAnalyticsFwkException("Error while updating the search: " + search.getName(),
					EMAnalyticsFwkException.ERR_UPDATE_SEARCH, null, dmlce);

		}
		catch (Exception e) {
			LOGGER.error("Error while updating the search: " + search.getName(), e);
			throw new EMAnalyticsFwkException("Error while updating the search: " + search.getName(),
					EMAnalyticsFwkException.ERR_UPDATE_SEARCH, null, e);
		}
	}

	@Override
	public Search getSearch(BigInteger searchId) throws EMAnalyticsFwkException
	{
		//Get full search data
		return getSearch(searchId, false);
	}

	@Override
	public Search getSearchByName(String name, BigInteger folderId) throws EMAnalyticsFwkException
	{
		LOGGER.info("Retrieving search with name " + name + " in folder id: " + folderId);
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
			LOGGER.error("no result");
			return null;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			LOGGER.error("Error while retrieving the list of searches for the parent folder: " + folderId, e);
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
	public List<Search> getWidgetByName(String name) throws EMAnalyticsFwkException {
		LOGGER.info("Calling SearchManager.getWidgetByName, with name {}", name);
		EntityManager entityManager = null;
		List<EmAnalyticsSearch> emAnalyticsSearchList;
		List<Search> result = new ArrayList<>();
		try{
			entityManager = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			emAnalyticsSearchList = entityManager.createNamedQuery("Search.getWidgetByName")
					.setParameter("widgetName", name)
					.setParameter(QueryParameterConstant.USER_NAME,TenantContext.getContext().getUsername())
					.getResultList();
			for(EmAnalyticsSearch emAnalyticsSearch : emAnalyticsSearchList){
				entityManager.refresh(emAnalyticsSearch);
				Search search = createSearchObject(emAnalyticsSearch, null);
				search.setDashboardIneligible(emAnalyticsSearch.getDASHBOARDINELIGIBLE() == null?"0":emAnalyticsSearch.getDASHBOARDINELIGIBLE());
				result.add(search);
			}
			return result;
		}catch(Exception e){
			LOGGER.error("Fall into error while retrieving widget with name: {}", name);
			throw new EMAnalyticsFwkException("Fall into error while retrieving widget with name: "+name, EMAnalyticsFwkException.ERR_GENERIC, null,e);
		}finally {
			if(entityManager != null)
				entityManager.close();
		}

	}


	@Override
	public int getSearchCountByFolderId(BigInteger folderId) throws EMAnalyticsFwkException
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Search> getSearchListByCategoryId(BigInteger categoryId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		try {
			List<Search> rtnobj = new ArrayList<Search>();
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			//			EmAnalyticsCategory category = EmAnalyticsObjectUtil.getCategoryById(categoryId, em);
			List<EmAnalyticsSearch> searchList = null;
			if (RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())) {
				searchList = em.createNamedQuery("Search.getSearchListByCategoryForTenant").setParameter("categoryId", categoryId)
						.getResultList();
			}
			else {
				searchList = em.createNamedQuery("Search.getSearchListByCategory").setParameter("categoryId", categoryId)
						.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getResultList();
			}
			for (EmAnalyticsSearch searchObj : searchList) {
				em.refresh(searchObj);
				rtnobj.add(createSearchObject(searchObj, null));
			}

			return rtnobj;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			LOGGER.error("Error while retrieving the list of searches for the categoryD : " + categoryId, e);
			throw new EMAnalyticsFwkException("Error while retrieving the list of searches for the categoryId : " + categoryId,
					EMAnalyticsFwkException.ERR_GENERIC, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Search> getSearchListByFolderId(BigInteger folderId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		try {
			List<Search> rtnobj = new ArrayList<Search>();
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			//EmAnalyticsFolder folder = EmAnalyticsObjectUtil.getFolderById(folderId, em);

			List<EmAnalyticsSearch> searchList = null;
			if (RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())) {
				searchList = em.createNamedQuery("Search.getSearchListByFolderForTenant").setParameter("folderId", folderId)
						.getResultList();
			}
			else {
				searchList = em.createNamedQuery("Search.getSearchListByFolder").setParameter("folderId", folderId)
						.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getResultList();
			}
			for (EmAnalyticsSearch searchObj : searchList) {
				em.refresh(searchObj);
				rtnobj.add(createSearchObject(searchObj, null));
			}

			return rtnobj;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			LOGGER.error("Error while retrieving the list of searches for the parent folder: " + folderId, e);
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
			StringBuilder query = null;
			if (RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())) {
				query = new StringBuilder(LASTACCESS_ORDERBY_FOR_INTERNAL_TENANT);
				em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
				searchList = em.createQuery(query.toString()).setMaxResults(count).getResultList();
			}
			else {
				query = new StringBuilder(LASTACCESS_ORDERBY);
				em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
				searchList = em.createQuery(query.toString())
						.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
						.setMaxResults(count).getResultList();
			}

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
			LOGGER.error("Error while retrieving the list of searches ");
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
	public String getSearchParamByName(BigInteger searchId, String paramName) throws EMAnalyticsFwkException
	{
		LOGGER.debug("get param value by searchId: " + searchId + ", param name: " + paramName);
		EntityManager em = null;
		String paramValue = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			paramValue = EmAnalyticsObjectUtil.getSearchParamByName(searchId, paramName, em);
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			String errorMessage = "Error while retrieving the value of search parameter for the searchId: " + searchId
					+ ", parameter name: " + paramName;
			LOGGER.error(errorMessage, e);
			throw new EMAnalyticsFwkException(errorMessage, EMAnalyticsFwkException.ERR_GENERIC, null, e);
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
		return paramValue;
	}

	@Override
	public Search getSearchWithoutOwner(BigInteger searchId) throws EMAnalyticsFwkException
	{
		return getSearchWithoutOwner(searchId, false);
	}

	@Override
	public Search getSearchWithoutOwnerByName(String searchName) throws EMAnalyticsFwkException {
	       LOGGER.info("getSearchWithoutOwnerByName with name: " + searchName);
	        EntityManager em = null;
	        EmAnalyticsSearch emSearch = null;
	        try {
	            em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
	            emSearch = EmAnalyticsObjectUtil.findEmSearchByNameWithoutOwner(searchName, em);
	        } catch (Exception e) {
	            EmAnalyticsProcessingException.processSearchPersistantException(e, null);
	            String errMsg = "Error while getting the search object by name: " + searchName;
	            LOGGER.error(errMsg, e);
	            throw new EMAnalyticsFwkException(errMsg, EMAnalyticsFwkException.ERR_GET_SEARCH_BY_NAME, new Object[] { searchName }, e);
	        } finally {
	            if (em != null) {
	                em.close();
	            }
	        }
	        
	        if (emSearch == null) {
	            String errMsg = "Search identified by name: " + searchName + " does not exist";
	            LOGGER.error(errMsg);
	            throw new EMAnalyticsFwkException(errMsg, EMAnalyticsFwkException.ERR_GET_SEARCH_BY_NAME, new Object[] { searchName });
	        }
	        
	        return getSearch(emSearch, false);
	}
	
    public List<Search> getSearchListWithoutOwnerByParam(String name, String value) throws EMAnalyticsFwkException {
        LOGGER.debug("getSearchListWithoutOwnerByParam with name={}, value={} ", name, value);
        EntityManager em = null;
        List<EmAnalyticsSearch> emSearchList = null;
        List<Search> searchList = new ArrayList<Search>();
        try {
            em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
            emSearchList = EmAnalyticsObjectUtil.findEmSearchListByParamWithoutOwner(name, value, em);
        } catch (Exception e) {
            EmAnalyticsProcessingException.processSearchPersistantException(e, null);
            String errMsg = "Error while getting the search object by parameter with name=: " + name + ", value=" + value;
            LOGGER.error(errMsg, e);
            throw new EMAnalyticsFwkException(errMsg, EMAnalyticsFwkException.ERR_GET_SEARCH_BY_NAME, new Object[] { name, value }, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        if (emSearchList != null && !emSearchList.isEmpty()) {
            for (EmAnalyticsSearch emSearch : emSearchList) {
                searchList.add(getSearch(emSearch, false));
            }
        }
        
        return searchList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Search> getSystemSearchListByCategoryId(BigInteger categoryId) throws EMAnalyticsFwkException
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

			LOGGER.error("Error while retrieving the list of searches for the categoryD : " + categoryId, e);
			throw new EMAnalyticsFwkException("Error while retrieving the list of searches for the categoryId : " + categoryId,
					EMAnalyticsFwkException.ERR_GENERIC, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}

		}

	}

	/**
	 * @param name
	 * @return
	 * @throws EMAnalyticsFwkException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Search> getTargetCard(String name) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		List<Search> targetCardResult = new ArrayList<Search>();
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			List<EmAnalyticsSearch> targetCardList = em.createNamedQuery("Search.getSearchListByTargetType")
					.setParameter("searchName", "link_" + name + "%")
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getResultList();
			for (EmAnalyticsSearch emTargetCard : targetCardList) {
				em.refresh(emTargetCard);
				targetCardResult.add(createSearchObject(emTargetCard, null));
			}
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			LOGGER.error("Error while retrieving the list of target card for the targetType : " + name, e);
			throw new EMAnalyticsFwkException("Error while retrieving the list of target card for the targetType : " + name,
					EMAnalyticsFwkException.ERR_GENERIC, null, e);
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
		return targetCardResult;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Search> getWidgetListByCategoryId(BigInteger categoryId) throws EMAnalyticsFwkException
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
			LOGGER.error("Error while retrieving the list of widgets for the categoryId : " + categoryId, e);
			throw new EMAnalyticsFwkException("Error while retrieving the list of widgets for the categoryId : " + categoryId,
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
	public List<Search> getSearchListByIds(List<BigInteger> ids) throws EMAnalyticsFwkException
	{
		List<Search> resultList = new ArrayList<Search>();
		if(ids == null || ids.isEmpty()) {
			return resultList;
		}
		
		EntityManager em = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			List<EmAnalyticsSearch> searchList = em.createNamedQuery("Search.getSearchListByIds")
					.setParameter("ids", ids).getResultList();
			for (EmAnalyticsSearch searchObj : searchList) {
				resultList.add(createWidgetObject(searchObj, false));
			}
		} catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			LOGGER.error("Error while retrieving the list of search by id list : " + ids, e);
			throw new EMAnalyticsFwkException("Error while retrieving the list of search by id list : " + ids,
					EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID, null, e);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return resultList;
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
			return Collections.emptyList();
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
			//			em.getTransaction().begin();
			//			em.flush();
			//			em.getTransaction().commit();
			String jql = sb.toString();
			LOGGER.debug("The JQL to query all widget is {}", jql);
			long start = System.currentTimeMillis();
			Query query = em.createQuery(jql).setHint("eclipselink.left-join-fetch", "e.emAnalyticsSearchParams")
					.setHint("eclipselink.join-fetch", "e.emAnalyticsCategory")
					.setHint("eclipselink.left-join-fetch", "e.emAnalyticsCategory.emAnalyticsCategoryParams")
					.setHint("eclipselink.join-fetch", "e.emAnalyticsFolder")
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
					.setParameter("tenantId", TenantContext.getContext().getTenantInternalId());
			if (widgetGroupId != null) {
				query.setParameter("widgetGroupId", new BigInteger(widgetGroupId));
			}
			if (LOGGER.isDebugEnabled()) {
				try {
					Session session = em.unwrap(JpaEntityManager.class).getActiveSession();
					DatabaseQuery databaseQuery = ((EJBQueryImpl) query).getDatabaseQuery();
					databaseQuery.prepareCall(session, new DatabaseRecord());
					String sqlString = databaseQuery.getSQLString();
					//				String sqlString = databaseQuery.getTranslatedSQLString(session, new DatabaseRecord());
					LOGGER.debug("The SQL statement to retrieve all widget is: [{}]", sqlString);
				}
				catch (Exception e) {
					LOGGER.error("Error when printing debug sql: ", e);
				}
			}
			List<EmAnalyticsSearch> searchList = query.getResultList();
			LOGGER.debug("Querying to get all widgets takes {} ms, and retrieved {} widgets", System.currentTimeMillis() - start,
					searchList == null ? 0 : searchList.size());
			for (EmAnalyticsSearch searchObj : searchList) {
				//				em.refresh(searchObj);
				rtnobj.add(createWidgetObject(searchObj, false));
			}

			return rtnobj;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			LOGGER.error("Error while retrieving the list of widgets for providerNames : " + providerNames, e);
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
	public ScreenshotData getWidgetScreenshotById(BigInteger widgetId) throws EMAnalyticsFwkException
	{
		String screenshot = null;
		Search search = getSearchWithoutOwner(widgetId, true);
		List<SearchParameter> paramList = search.getParameters();
		if (paramList != null && !paramList.isEmpty()) {
			for (SearchParameter param : paramList) {
				if (EntityJsonUtil.NAME_WIDGET_VISUAL.equals(param.getName())) {
					screenshot = param.getValue();
					break;
				}
			}
		}
		if (StringUtil.isEmpty(screenshot)) {
			LOGGER.debug("Screenshot for widget with id={} is null or empty. Use default widget screenshot instead", widgetId);
			screenshot = SearchManager.DEFAULT_WIDGET_SCREENSHOT;
		}
		ScreenshotData ssd = new ScreenshotData(screenshot, search.getCreatedOn(), search.getLastModifiedOn());
		return ssd;
	}

	public List<Search> saveMultipleSearch(List<ImportSearchImpl> searchList) throws Exception
	{
		return saveMultipleSearch(searchList, false);
	}

	public List<Search> saveMultipleSearch(List<ImportSearchImpl> searchList, boolean isOobSearch) throws Exception
	{

		EntityManager em = null;
		boolean bCommit = true;
		List<Search> importedList = new ArrayList<Search>();
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			for (ImportSearchImpl tmpImportSrImpl : searchList) {
				Search search = tmpImportSrImpl.getSearch();
				Object obj = tmpImportSrImpl.getFolderDetails();
				Object cateObj = tmpImportSrImpl.getCategoryDetails();
				if(search.getId() == null) {
					search.setId(IdGenerator.getIntUUID(ZDTContext.getRequestId()));
				}
					
				EmAnalyticsSearch emSearch = EmAnalyticsObjectUtil.getSearchById(search.getId(), em);
				try {
					if (emSearch != null) {
//						EmAnalyticsSearch emSearch = EmAnalyticsObjectUtil.getSearchById(search.getId(), em);
						// checking that ,it is system search and method is not  calling from updateutility.
						if (!isOobSearch && emSearch.getSystemSearch() != null
								&& emSearch.getSystemSearch().intValue() == 1) {
							importedList.add(createSearchObject(emSearch, null));
							continue;
						}

						// checking that ,it is system search and method is not  calling from updateutility.
						if (isOobSearch && emSearch.getSystemSearch() != null
								&& emSearch.getSystemSearch().intValue() != 1) {
							importedList.add(createSearchObject(emSearch, null));
							continue;
						}

						if (obj != null && obj instanceof BigInteger) {
							EmAnalyticsFolder tmpfld = EmAnalyticsObjectUtil.getFolderById((BigInteger) obj, em);
							if (tmpfld != null) {
								search.setFolderId((BigInteger) obj);
							}
							else {
								continue;
							}
						}

						if (obj != null && obj instanceof FolderImpl) {
							Folder fld = (Folder) obj;
							if (fld.getParentId() == null || BigInteger.ZERO.equals(fld.getParentId())) {
								fld.setParentId(BigInteger.ONE);
							}
							EmAnalyticsFolder objFolder = EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(fld);

							if (objFolder != null) {
								search.setFolderId(objFolder.getFolderId());
							}

						}

						if (obj instanceof FolderImpl && search.getFolderId() == null) {
							EmAnalyticsFolder folderObj = getEmAnalyticsFolderBySearch(tmpImportSrImpl, em);
							em.persist(folderObj);
							search.setFolderId(folderObj.getFolderId());
						}

						if (cateObj instanceof BigInteger) {
							EmAnalyticsSearch searchEntity = EmAnalyticsObjectUtil.getSearchById(search.getId(), em);

							if (searchEntity.getEmAnalyticsCategory().getCategoryId().equals((BigInteger) cateObj)) {
								search.setCategoryId((BigInteger) cateObj);
							}
							else {
								continue;
							}
						}

						if (cateObj instanceof CategoryImpl && search.getCategoryId() == null) {
							EmAnalyticsCategory iCategory = getEmAnalyticsCategoryBySearch(tmpImportSrImpl, em);
							search.setCategoryId(iCategory.getCategoryId());
						}

						emSearch = EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit(search, em);
						emSearch.setTenantId(TenantContext.getContext().getTenantInternalId());
						em.merge(emSearch);
						tmpImportSrImpl.setId(emSearch.getId());
						importedList.add(createSearchObject(emSearch, null));
					}
					else {
						EmAnalyticsSearch searchEntity = null;
						try {
							if (obj != null && obj instanceof BigInteger) {
								BigInteger id = (BigInteger) obj;
								searchEntity = (EmAnalyticsSearch) em.createNamedQuery("Search.getSearchByName")
										.setParameter("folderId", id).setParameter("searchName", search.getName())
										.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
										.getSingleResult();
								tmpImportSrImpl.setId(searchEntity.getId());
								importedList.add(createSearchObject(searchEntity, null));
							}
						}
						catch (NoResultException e) {
							searchEntity = null;
						}
						if (searchEntity == null) {
							if (obj == null) {
								continue;
							}
							if (obj != null && obj instanceof BigInteger) {
								EmAnalyticsFolder tmpfld = EmAnalyticsObjectUtil.getFolderById((BigInteger) obj, em);
								if (tmpfld != null) {
									search.setFolderId((BigInteger) obj);
								}
								else {
									importedList.add(search);
									continue;
								}
							}
							if (cateObj == null) {
								continue;
							}
							if (cateObj instanceof BigInteger) {
								EmAnalyticsCategory categoryObj = EmAnalyticsObjectUtil.getCategoryById((BigInteger) cateObj, em);
								if (categoryObj != null) {
									search.setCategoryId((BigInteger) cateObj);
								}
								else {
									importedList.add(search);
									continue;
								}
							}

							if (obj instanceof FolderImpl) {
							    Folder fld = (Folder) obj;
								if (fld.getParentId() == null || BigInteger.ZERO.equals(fld.getParentId())) {
									fld.setParentId(BigInteger.ONE);
								}
								EmAnalyticsFolder objFolder = EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(fld);
								if (objFolder != null) {
									search.setFolderId(objFolder.getFolderId());
								}
							}
							EmAnalyticsCategory categoryObj = null;
							if (cateObj != null && cateObj instanceof CategoryImpl) {
								try {
									categoryObj = (EmAnalyticsCategory) em.createNamedQuery("Category.getCategoryByName")
											.setParameter("categoryName", ((Category) cateObj).getName())
											.setParameter(QueryParameterConstant.USER_NAME,
													TenantContext.getContext().getUsername())
											.getSingleResult();
								}
								catch (NoResultException e) {
									LOGGER.error("no result");
								}
								if (categoryObj != null) {
									search.setCategoryId(categoryObj.getCategoryId());
								}
							}

							if (search != null  && search.getFolderId() != null) {
								try {

									searchEntity = (EmAnalyticsSearch) em.createNamedQuery("Search.getSearchByName")
											.setParameter("folderId", search.getFolderId())
											.setParameter("searchName", search.getName())
											.setParameter(QueryParameterConstant.USER_NAME,
													TenantContext.getContext().getUsername())
											.getSingleResult();
								}
								catch (NoResultException e) {
									LOGGER.error("no result");
								}
								if (searchEntity != null) {
									tmpImportSrImpl.setId(searchEntity.getId());
									importedList.add(createSearchObject(searchEntity, null));
									continue;
								}
							}

							if (obj instanceof FolderImpl && search.getFolderId() == null) {
							    EmAnalyticsFolder folder = getEmAnalyticsFolderBySearch(tmpImportSrImpl, em);
								folder.setTenantId(TenantContext.getContext().getTenantInternalId());
								em.persist(folder);
								search.setFolderId(folder.getFolderId());
							}

							if (cateObj instanceof CategoryImpl && search.getCategoryId() == null) {
								EmAnalyticsCategory iCategory = getEmAnalyticsCategoryBySearch(tmpImportSrImpl, em);
								iCategory.setTenantId(TenantContext.getContext().getTenantInternalId());
								em.persist(iCategory);
								search.setCategoryId(iCategory.getCategoryId());
							}

							EmAnalyticsSearch newSearch = EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(search, em);
							newSearch.setTenantId(TenantContext.getContext().getTenantInternalId());
							em.persist(newSearch);
							tmpImportSrImpl.setId(newSearch.getId());
							importedList.add(createSearchObject(newSearch, null));
						}
					}
				}
				catch (PersistenceException eme) {
					bCommit = false;
					LOGGER.error("Error while importing the search: " + search.getName(), eme);
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
			LOGGER.error("Error in saveMultipleSearches", e);
			LOGGER.error(e.getLocalizedMessage());
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
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			searchEntity.setTenantId(TenantContext.getContext().getTenantInternalId());
			em.persist(searchEntity);
			em.getTransaction().commit();
			em.refresh(searchEntity);
			return createSearchObject(searchEntity, null);
		}
		catch (EMAnalyticsFwkException eme) {
			LOGGER.error("Search with name " + search.getName() + " was saved but could not bve retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			processUniqueConstraints(search, em, dmlce);
			EmAnalyticsProcessingException.processSearchPersistantException(dmlce, search.getName());
			LOGGER.error("Persistence error while saving the search: " + search.getName(), dmlce);
			throw new EMAnalyticsFwkException("Error while saving the search: " + search.getName(),
					EMAnalyticsFwkException.ERR_CREATE_SEARCH, null, dmlce);
		}
		catch (Exception e) {
			LOGGER.error("Error while saving the search: " + search.getName(), e);
			throw new EMAnalyticsFwkException("Error while saving the search: " + search.getName(),
					EMAnalyticsFwkException.ERR_CREATE_SEARCH, null, e);
		}
		finally {
			if (em != null) {
				em.close();
			}

		}
	}

	/**
	 * This method will not open a tnx nor commit a txn.
	 * @param search
	 *            search to be saved
	 * @param em
	 * @return
	 * @throws EMAnalyticsFwkException
	 */
	@Override
	public Search saveSearchWithEm(Search search, EntityManager em) throws EMAnalyticsFwkException {
		try {
			EmAnalyticsSearch searchEntity = EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(search, em);
			searchEntity.setTenantId(TenantContext.getContext().getTenantInternalId());
			//Save into cache, not flushed into DB yet.
			em.persist(searchEntity);
			//flush to db, yet commit.
			em.flush();
			em.refresh(searchEntity);
			LOGGER.info("search id after flush and refresh is {}", searchEntity.getId());
			return createSearchObject(searchEntity, null);
		}
		catch (EMAnalyticsFwkException eme) {
			LOGGER.error("Search with name " + search.getName() + " was saved but could not be retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			processUniqueConstraints(search, em, dmlce);
			EmAnalyticsProcessingException.processSearchPersistantException(dmlce, search.getName());
			LOGGER.error("Persistence error while saving the search: " + search.getName(), dmlce);
			throw new EMAnalyticsFwkException("Error while saving the search: " + search.getName(),
					EMAnalyticsFwkException.ERR_CREATE_SEARCH, null, dmlce);
		}
		catch (Exception e) {
			LOGGER.error("Error while saving the search: " + search.getName(), e);
			throw new EMAnalyticsFwkException("Error while saving the search: " + search.getName(),
					EMAnalyticsFwkException.ERR_CREATE_SEARCH, null, e);
		}

	}

	@Override
	public List<BigInteger> getSearchIdsByCategory(BigInteger categoryId) throws EMAnalyticsFwkException {
		LOGGER.debug("Calling SearchManager.getSearchIdsByCategory");
		List<BigInteger> searchIds;
		EntityManager entityManager = null;
		try {
			entityManager = PersistenceManager.getInstance().getEntityManager(new TenantInfo(DEFAULT_CURRENT_USER, DEFAULT_TENANT_ID));
			searchIds = entityManager.createNamedQuery("Search.getSystemSearchIdsByCategoryId").setParameter("categoryId", categoryId).getResultList();
			return searchIds;
		}catch (NoResultException nre){
			LOGGER.warn("No Searches Found under category: {}", categoryId);
			return Collections.emptyList();
		}catch (Exception e){
			LOGGER.error("Fall into error while retrieving the list of searches for category " + categoryId, e);
			throw new EMAnalyticsFwkException("Fall into error while retrieving the list of searches for category " + categoryId,
					EMAnalyticsFwkException.ERR_GENERIC, null, e);
		}finally {
			if (entityManager != null)entityManager.close();
		}
	}
	@Override
	public void storeOobWidget(List<BigInteger> searchIds, List<SearchImpl> oobWidgetList) throws EMAnalyticsFwkException {
		LOGGER.info("Calling SearchManager.storeOobWidget");
		EntityManager entityManager = null;
		try{
			entityManager = PersistenceManager.getInstance().getEntityManager(new TenantInfo(DEFAULT_CURRENT_USER, DEFAULT_TENANT_ID));
			if (!entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().begin();
			}
			cleanSearchesPermanentlyById(searchIds, entityManager);
			for (SearchImpl search: oobWidgetList){
				saveOobSearch(search, entityManager);

			}
			LOGGER.info("commit the transction");
			entityManager.getTransaction().commit();

		}catch (Exception e){
			LOGGER.error("Fall into error while storing system searches", e);
			throw new EMAnalyticsFwkException("Fall into error while storing system searches",
					EMAnalyticsFwkException.ERR_GENERIC, null, e);
		}finally {
			if (entityManager != null)entityManager.close();
		}

	}

	private void cleanSearchesPermanentlyById(List<BigInteger> searchIds, EntityManager entityManager) throws EMAnalyticsFwkException {
		LOGGER.debug("Calling SearchManager.cleanSearchesPermanentlyById");
		if(searchIds.isEmpty() || searchIds == null ) return;
		try{
			LOGGER.debug("Cleaning oob search params");
			entityManager.createNamedQuery("SearchParam.deleteParamsBySearchIds")
					.setParameter("searchIds", searchIds).executeUpdate();
			LOGGER.debug("Cleaning oob searches");
			entityManager.createNamedQuery("Search.deleteSystemSearchByIds")
					.setParameter("ids", searchIds).executeUpdate();
		}catch (Exception e){
			LOGGER.error("Fall into error while cleaning system searches by ids", e);
			throw new EMAnalyticsFwkException("Fall into error while cleaning system searches by ids",
					EMAnalyticsFwkException.ERR_GENERIC, null, e);
		}
	}

	private void saveOobSearch(SearchImpl search, EntityManager entityManager) throws EMAnalyticsFwkException {
		LOGGER.debug("Calling SearchManager.saveOobSearch");
		 try{
			 EmAnalyticsSearch emAnalyticsSearch = EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(search, entityManager);
			 emAnalyticsSearch.setOwner(DEFAULT_CURRENT_USER);
			 emAnalyticsSearch.setTenantId(DEFAULT_TENANT_ID);
			 emAnalyticsSearch.setSystemSearch(new BigDecimal("1"));
			 emAnalyticsSearch.setLastModifiedBy(DEFAULT_CURRENT_USER);
			 emAnalyticsSearch.setDASHBOARDINELIGIBLE("TRUE".equalsIgnoreCase(search.getDashboardIneligible()) ? "1" : "0");
			 emAnalyticsSearch.setCreationDate(search.getCreationDate());
			 emAnalyticsSearch.setLastModificationDate(search.getLastModificationDate());
			 for(EmAnalyticsSearchParam emAnalyticsSearchParam: emAnalyticsSearch.getEmAnalyticsSearchParams())
				 emAnalyticsSearchParam.setTenantId(DEFAULT_TENANT_ID);
			 entityManager.persist(emAnalyticsSearch);
		 }catch(PersistenceException pe){
			 processUniqueConstraints(search, entityManager, pe);
			 EmAnalyticsProcessingException.processSearchPersistantException(pe, search.getName());
			 LOGGER.error("Persistence error while saving the search: " + search.getName(), pe);
			 throw new EMAnalyticsFwkException("Error while saving the search: " + search.getName(),
					 EMAnalyticsFwkException.ERR_CREATE_SEARCH, null, pe);
		 }catch (Exception e){
			 LOGGER.error(e);
		 }
	}




	@Override
	public Search saveTargetCard(Search targetCard) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		try {
			// use internal tenant id = 0 to create the EntityManager for target card
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			EmAnalyticsSearch targetCardEntity = EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(targetCard, em);
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			targetCardEntity.setTenantId(TenantContext.getContext().getTenantInternalId());
			em.persist(targetCardEntity);
			em.getTransaction().commit();
			return createSearchObject(targetCardEntity, null);
		}
		catch (EMAnalyticsFwkException eme) {
			LOGGER.error("Target card with name " + targetCard.getName() + " was saved but could not be retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			processUniqueConstraints(targetCard, em, dmlce);
			EmAnalyticsProcessingException.processSearchPersistantException(dmlce, targetCard.getName());
			LOGGER.error("Persistence error while saving the target card: " + targetCard.getName(), dmlce);
			throw new EMAnalyticsFwkException("Error while saving the target card: " + targetCard.getName(),
					EMAnalyticsFwkException.ERR_CREATE_SEARCH, null, dmlce);
		}
		catch (Exception e) {
			LOGGER.error("Error while saving the target card: " + targetCard.getName(), e);
			throw new EMAnalyticsFwkException("Error while saving the target card: " + targetCard.getName(),
					EMAnalyticsFwkException.ERR_CREATE_SEARCH, null, e);
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
	}

	private boolean checkIfIsOldParam(EmAnalyticsSearchParam param)
	{
		String name = param.getName();
		if ("WIDGET_SOURCE".equals(name) || "WIDGET_GROUP_NAME".equals(name) || "WIDGET_SCREENSHOT_HREF".equals(name)
				|| "WIDGET_ICON".equals(name) || "WIDGET_KOC_NAME".equals(name) || "WIDGET_VIEWMODEL".equals(name)
				|| "WIDGET_TEMPLATE".equals(name) || "WIDGET_SUPPORT_TIME_CONTROL".equals(name)
				|| "WIDGET_LINKED_DASHBOARD".equals(name) || "WIDGET_DEFAULT_WIDTH".equals(name)
				|| "DASHBOARD_INELIGIBLE".equals(name) || "WIDGET_DEFAULT_HEIGHT".equals(name) || "PROVIDER_NAME".equals(name)
				|| "PROVIDER_VERSION".equals(name) || "PROVIDER_ASSET_ROOT".equals(name)) {
			return true;
		}
		return false;

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

			rtnObj.setId(searchObj.getId());
			if (searchObj.getSearchGuid() != null) {
				rtnObj.setGuid(searchObj.getSearchGuid().toString());
			}
			boolean isSystemSearch = (searchObj.getSystemSearch()!= null) && (searchObj.getSystemSearch().intValue() == 1);
			String searchName = isSystemSearch ?
					DatabaseResourceBundleUtil.getTranslatedString(searchObj.getPROVIDERNAME(),searchObj.getName()) : searchObj.getName();
			rtnObj.setName(searchName);
			String searchDescription = isSystemSearch ?
					DatabaseResourceBundleUtil.getTranslatedString(searchObj.getPROVIDERNAME(),searchObj.getDescription()) : searchObj.getDescription();
			rtnObj.setDescription(searchDescription);
			rtnObj.setOwner(searchObj.getOwner());
			rtnObj.setCreationDate(searchObj.getCreationDate());
			rtnObj.setLastModifiedBy(searchObj.getLastModifiedBy());
			rtnObj.setLastModificationDate(searchObj.getLastModificationDate());
			rtnObj.setLastAccessDate(DateUtil.getCurrentUTCTime());
			if (searchObj.getMetadataClob() != null && searchObj.getMetadataClob().length() > 0) {
				char[] metadataCharArr = new char[searchObj.getMetadataClob().length()];
				Reader reader = new StringReader(searchObj.getMetadataClob());
				reader.read(metadataCharArr);
				rtnObj.setMetadata(new String(metadataCharArr));
			}

			rtnObj.setQueryStr(searchObj.getSearchDisplayStr());
			rtnObj.setLocked(searchObj.getIsLocked() != null && searchObj.getIsLocked().intValue() == 1 ? true : false);
			rtnObj.setCategoryId(searchObj.getEmAnalyticsCategory().getCategoryId());

			rtnObj.setFolderId(searchObj.getEmAnalyticsFolder().getFolderId());

			rtnObj.setUiHidden(searchObj.getUiHidden() != null && searchObj.getUiHidden().intValue() == 1 ? true : false);
			rtnObj.setSystemSearch(
					searchObj.getSystemSearch() != null && searchObj.getSystemSearch().intValue() == 1 ? true : false);
			rtnObj.setLastAccessDate(DateUtil.getCurrentUTCTime());
			rtnObj.setIsWidget(searchObj.getIsWidget() == 1 ? true : false);

			FederationSupportedType fst = FederationSupportedType.NON_FEDERATION_ONLY;
			try {
				fst = FederationSupportedType.fromValue(searchObj.getFederationSupported());
			} catch (IllegalArgumentException e) {
				LOGGER.error("Invalid federation support type value. Using default value instead", e);
			}
			rtnObj.setFederationSupported(fst.getJsonValue());

			if (TenantContext.getContext() != null && TenantContext.getContext().getUsername() != null) {
				rtnObj.setEditable(TenantContext.getContext().getUsername().equals(searchObj.getOwner()));
			}

			List<SearchParameter> searchParams = new ArrayList<SearchParameter>();
			// get parameters
			Set<EmAnalyticsSearchParam> params = searchObj.getEmAnalyticsSearchParams();
			for (EmAnalyticsSearchParam paramObj : params) {
				if (checkIfIsOldParam(paramObj)) {
					continue;//handle thos param later
				}
				EmAnalyticsSearchParam paramVORow = paramObj;
				SearchParameter param = new SearchParameter();
				param.setName(paramVORow.getName());
				param.setAttributes(paramVORow.getParamAttributes());
				param.setType(ParameterType.fromIntValue(paramVORow.getParamType().intValue()));

				if (ParameterType.CLOB.equals(param.getType())) {
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
			//handle params in search
			handleParamsInSearch(searchObj, searchParams);
			rtnObj.setParameters(searchParams);

			return rtnObj;
		}
		catch (Exception e) {
			LOGGER.error("Error while getting the search object", e);
			throw new EMAnalyticsFwkException("Error while getting the search object", EMAnalyticsFwkException.ERR_GET_SEARCH,
					null, e);
		}
	}

	private Widget createWidgetObject(EmAnalyticsSearch searchObj, boolean loadScreenshot) throws EMAnalyticsFwkException
	{
		WidgetImpl rtnObj = null;
		try {
			rtnObj = new WidgetImpl();
			rtnObj.setId(searchObj.getId());
			Boolean isSystemSearch = (searchObj.getSystemSearch() != null ) && (searchObj.getSystemSearch().intValue() == 1);
			String searchName =  isSystemSearch ?
					DatabaseResourceBundleUtil.getTranslatedString(searchObj.getPROVIDERNAME(), searchObj.getName()) : searchObj.getName();
			rtnObj.setName(searchName);
			String searchDescription = isSystemSearch ?
					DatabaseResourceBundleUtil.getTranslatedString(searchObj.getPROVIDERNAME(), searchObj.getDescription()) : searchObj.getDescription();
			rtnObj.setDescription(searchDescription);
			rtnObj.setOwner(searchObj.getOwner());
			rtnObj.setCreationDate(searchObj.getCreationDate());
			rtnObj.setLastModifiedBy(searchObj.getLastModifiedBy());
			rtnObj.setLastModificationDate(searchObj.getLastModificationDate());
			rtnObj.setCategoryId(searchObj.getEmAnalyticsCategory().getCategoryId());
			rtnObj.setFolderId(searchObj.getEmAnalyticsFolder().getFolderId());
			rtnObj.setLastAccessDate(DateUtil.getCurrentUTCTime());
			rtnObj.setIsWidget(searchObj.getIsWidget() == 1 ? true : false);
			FederationSupportedType fst = FederationSupportedType.NON_FEDERATION_ONLY;
			try {
				fst = FederationSupportedType.fromValue(searchObj.getFederationSupported());
			} catch (IllegalArgumentException e) {
				LOGGER.error("Invalid federation support type value. Using default value instead", e);
			}
			rtnObj.setFederationSupported(fst.getJsonValue());

			List<SearchParameter> searchParams = new ArrayList<SearchParameter>();
			// get parameters
			Set<EmAnalyticsSearchParam> params = searchObj.getEmAnalyticsSearchParams();
			for (EmAnalyticsSearchParam paramObj : params) {
				//check if the param is moved to search ,if is,handle it later
				if (checkIfIsOldParam(paramObj)) {
					continue;
				}
				EmAnalyticsSearchParam paramVORow = paramObj;
				SearchParameter param = new SearchParameter();
				param.setName(paramVORow.getName());
				param.setType(ParameterType.fromIntValue(paramVORow.getParamType().intValue()));

				if(!loadScreenshot && EntityJsonUtil.NAME_WIDGET_VISUAL.equals(param.getName())) continue;
				if (ParameterType.STRING.equals(param.getType())) {
					param.setValue(paramVORow.getParamValueStr());
				}
				else if (ParameterType.CLOB.equals(param.getType())) {
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
			//handle the param(those moved to search) in search
			handleParamsInSearch(searchObj, searchParams);
			rtnObj.setParameters(searchParams);

			Category category = CategoryManagerImpl.createCategoryObject(searchObj.getEmAnalyticsCategory(), null);
			rtnObj.setCategory(category);

			return rtnObj;
		}
		catch (Exception e) {
			LOGGER.error("Error while getting the widget object", e);
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
						LOGGER.error("no result");
						category = EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd((Category) search.getCategoryDetails(), em);
						em.persist(category);
					}

					// Category.getCategoryByName
				}
			}
		}
		catch (Exception e) {
			LOGGER.error("no result");
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
						LOGGER.error("no result");
					}
				}
			}
		}
		catch (EMAnalyticsFwkException e) {
			LOGGER.error(e.getLocalizedMessage());
			folder = null;
		}
		return folder;
	}

	private Search getSearch(EmAnalyticsSearch searchObj, boolean loadWidgetOnly) throws EMAnalyticsFwkException
	{
		Search search = null;
		if (searchObj != null) {
			if (loadWidgetOnly) {
				search = createWidgetObject(searchObj, true);
			}
			else {
				search = createSearchObject(searchObj, null);
			}
		}
		return search;
	}

	private Search getSearch(BigInteger searchId, boolean loadWidgetOnly) throws EMAnalyticsFwkException
	{
		LOGGER.info("Retrieving search with id: " + searchId);
		EntityManager em = null;
		EmAnalyticsSearch searchObj = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			searchObj = EmAnalyticsObjectUtil.getSearchById(searchId, em);
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			String errMsg = "Error while getting the search object by ID: " + searchId;
			LOGGER.error(errMsg, e);
			throw new EMAnalyticsFwkException(errMsg, EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID, new Object[] { searchId },
					e);
		}
		finally {
			if (em != null) {
				em.close();
			}
		}

		if (searchObj == null) {
			String errMsg = "Search identified by ID: " + searchId + " does not exist";
			LOGGER.error(errMsg);
			throw new EMAnalyticsFwkException(errMsg, EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID, new Object[] { searchId });
		}

		return getSearch(searchObj, loadWidgetOnly);
	}

	private Search getSearchWithoutOwner(BigInteger searchId, boolean loadWidgetOnly) throws EMAnalyticsFwkException
	{
		LOGGER.info("getSearchWithoutOwner with id: " + searchId);
		EntityManager em = null;
		EmAnalyticsSearch searchObj = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			searchObj = EmAnalyticsObjectUtil.findEmSearchByIdWithoutOwner(searchId, em);
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			String errMsg = "Error while getting the search object by ID: " + searchId;
			LOGGER.error(errMsg, e);
			throw new EMAnalyticsFwkException(errMsg, EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID, new Object[] { searchId },
					e);
		}
		finally {
			if (em != null) {
				em.close();
			}
		}

		if (searchObj == null || !BigInteger.ZERO.equals(searchObj.getDeleted())) {
			String errMsg = "Search identified by ID: " + searchId + " does not exist";
			LOGGER.error(errMsg);
			throw new EMAnalyticsFwkException(errMsg, EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID, new Object[] { searchId });
		}

		return getSearch(searchObj, loadWidgetOnly);
	}

	private void handleParamsInSearch(EmAnalyticsSearch searchObj, List<SearchParameter> searchParams)
	{
		if (searchObj.getWIDGET_SOURCE() != null && !DEFAULT_DB_VALUE.equals(searchObj.getWIDGET_SOURCE())) {
			SearchParameter param = new SearchParameter();
			param.setName("WIDGET_SOURCE");
			param.setType(ParameterType.STRING);
			param.setValue(searchObj.getWIDGET_SOURCE());
			searchParams.add(param);
		}
		if (searchObj.getWIDGETGROUPNAME() != null && !DEFAULT_DB_VALUE.equals(searchObj.getWIDGETGROUPNAME())) {
			SearchParameter param = new SearchParameter();
			param.setName("WIDGET_GROUP_NAME");
			param.setType(ParameterType.STRING);
			param.setValue(searchObj.getWIDGETGROUPNAME());
			searchParams.add(param);
		}
		if (searchObj.getWIDGETSCREENSHOTHREF() != null && !DEFAULT_DB_VALUE.equals(searchObj.getWIDGETSCREENSHOTHREF())) {
			SearchParameter param = new SearchParameter();
			param.setName("WIDGET_SCREENSHOT_HREF");
			param.setType(ParameterType.STRING);
			param.setValue(searchObj.getWIDGETSCREENSHOTHREF());
			searchParams.add(param);
		}
		if (searchObj.getWIDGETICON() != null && !DEFAULT_DB_VALUE.equals(searchObj.getWIDGETICON())) {
			SearchParameter param = new SearchParameter();
			param.setName("WIDGET_ICON");
			param.setType(ParameterType.STRING);
			param.setValue(searchObj.getWIDGETICON());
			searchParams.add(param);
		}
		if (searchObj.getWIDGETKOCNAME() != null && !DEFAULT_DB_VALUE.equals(searchObj.getWIDGETKOCNAME())) {
			SearchParameter param = new SearchParameter();
			param.setName("WIDGET_KOC_NAME");
			param.setType(ParameterType.STRING);
			param.setValue(searchObj.getWIDGETKOCNAME());
			searchParams.add(param);
		}
		if (searchObj.getWIDGETVIEWMODEL() != null && !DEFAULT_DB_VALUE.equals(searchObj.getWIDGETVIEWMODEL())) {
			SearchParameter param = new SearchParameter();
			param.setName("WIDGET_VIEWMODEL");
			param.setType(ParameterType.STRING);
			param.setValue(searchObj.getWIDGETVIEWMODEL());
			searchParams.add(param);
		}
		if (searchObj.getWIDGETTEMPLATE() != null && !DEFAULT_DB_VALUE.equals(searchObj.getWIDGETTEMPLATE())) {
			SearchParameter param = new SearchParameter();
			param.setName("WIDGET_TEMPLATE");
			param.setType(ParameterType.STRING);
			param.setValue(searchObj.getWIDGETTEMPLATE());
			searchParams.add(param);
		}
		if (searchObj.getWIDGETSUPPORTTIMECONTROL() != null
				&& !DEFAULT_DB_VALUE.equals(searchObj.getWIDGETSUPPORTTIMECONTROL())) {
			SearchParameter param = new SearchParameter();
			param.setName("WIDGET_SUPPORT_TIME_CONTROL");
			param.setType(ParameterType.STRING);
			param.setValue(searchObj.getWIDGETSUPPORTTIMECONTROL());
			searchParams.add(param);
		}
		if (searchObj.getWIDGETLINKEDDASHBOARD() != 0L
				&& !DEFAULT_DB_VALUE.equals(String.valueOf(searchObj.getWIDGETLINKEDDASHBOARD()))) {
			SearchParameter param = new SearchParameter();
			param.setName("WIDGET_LINKED_DASHBOARD");
			param.setType(ParameterType.STRING);
			param.setValue(String.valueOf(searchObj.getWIDGETLINKEDDASHBOARD()));
			searchParams.add(param);
		}
		if (searchObj.getWIDGETDEFAULTWIDTH() != 0L
				&& !DEFAULT_DB_VALUE.equals(String.valueOf(searchObj.getWIDGETDEFAULTWIDTH()))) {
			SearchParameter param = new SearchParameter();
			param.setName("WIDGET_DEFAULT_WIDTH");
			param.setType(ParameterType.STRING);
			param.setValue(String.valueOf(searchObj.getWIDGETDEFAULTWIDTH()));
			searchParams.add(param);
		}
		if (searchObj.getWIDGETDEFAULTHEIGHT() != 0L
				&& !DEFAULT_DB_VALUE.equals(String.valueOf(searchObj.getWIDGETDEFAULTHEIGHT()))) {
			SearchParameter param = new SearchParameter();
			param.setName("WIDGET_DEFAULT_HEIGHT");
			param.setType(ParameterType.STRING);
			param.setValue(String.valueOf(searchObj.getWIDGETDEFAULTHEIGHT()));
			searchParams.add(param);
		}
		if (searchObj.getDASHBOARDINELIGIBLE() != null && !DEFAULT_DB_VALUE.equals(searchObj.getDASHBOARDINELIGIBLE())) {
			SearchParameter param = new SearchParameter();
			param.setName("DASHBOARD_INELIGIBLE");
			param.setType(ParameterType.STRING);
			param.setValue(searchObj.getDASHBOARDINELIGIBLE());
			searchParams.add(param);
		}
		//handle 3 provider information
		if (searchObj.getPROVIDERVERSION() != null && !DEFAULT_DB_VALUE.equals(searchObj.getPROVIDERVERSION())) {
			SearchParameter param = new SearchParameter();
			param.setName("PROVIDER_VERSION");
			param.setType(ParameterType.STRING);
			param.setValue(searchObj.getPROVIDERVERSION());
			searchParams.add(param);
		}
		if (searchObj.getPROVIDERASSETROOT() != null && !DEFAULT_DB_VALUE.equals(searchObj.getPROVIDERASSETROOT())) {
			SearchParameter param = new SearchParameter();
			param.setName("PROVIDER_ASSET_ROOT");
			param.setType(ParameterType.STRING);
			param.setValue(searchObj.getPROVIDERASSETROOT());
			searchParams.add(param);
		}
		if (searchObj.getPROVIDERNAME() != null && !DEFAULT_DB_VALUE.equals(searchObj.getPROVIDERNAME())) {
			SearchParameter param = new SearchParameter();
			param.setName("PROVIDER_NAME");
			param.setType(ParameterType.STRING);
			param.setValue(searchObj.getPROVIDERNAME());
			searchParams.add(param);
		}
	}

	private void processUniqueConstraints(Search search, EntityManager em, PersistenceException dmlce)
			throws EMAnalyticsFwkException
	{

		if (dmlce.getCause() != null && dmlce.getCause().getMessage().contains("ANALYTICS_SEARCH_U01")) {
			EmAnalyticsSearch searchEntity = (EmAnalyticsSearch) em.createNamedQuery("Search.getSearchByName")
					.setParameter("folderId", search.getFolderId()).setParameter("searchName", search.getName())
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getSingleResult();
			String result = EntityJsonUtil.getErrorJsonObject(searchEntity.getId().toString(),
					"Search name '" + search.getName() + "' already exist", EMAnalyticsFwkException.ERR_SEARCH_DUP_NAME)
					.toString();
			throw new EMAnalyticsFwkException(result, EMAnalyticsFwkException.ERR_SEARCH_DUP_NAME,
					new Object[] { search.getName() });
		}

	}
	

	private String getQueryCondition(List<BigInteger> ids) {
		if (ids != null && !ids.isEmpty()) {
			StringBuilder parameters = new StringBuilder();
			int flag = 0;
			for (BigInteger id : ids) {
				if (flag++ > 0) {
					parameters.append(",");
				}
				parameters.append(id);
			}
			return parameters.toString();
		}
		return null;
	}

	/**
	 * this method will not close em. please be aware
	 * @param name
	 * @param folderId
	 * @param categoryId
	 * @param deleted
	 * @param owner
	 * @param em
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getSearchIdAndNameByUniqueKey(String name, BigInteger folderId, BigInteger categoryId, BigInteger deleted,String owner, EntityManager em) {
			Query q1 = em.createNativeQuery(SQL_GET_ID_NAME_BY_UNIQUE_KEY).setParameter(1, name).setParameter(2, categoryId)
				.setParameter(3, folderId).setParameter(4, deleted).setParameter(5, owner);
			q1.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> result = q1.getResultList();
		
		return result;
	}

}
