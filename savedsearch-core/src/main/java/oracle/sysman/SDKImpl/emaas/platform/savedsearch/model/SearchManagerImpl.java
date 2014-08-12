package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsLastAccess;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsLastAccessPK;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearchParam;

import org.apache.log4j.Logger;

public class SearchManagerImpl extends SearchManager
{

	//  Logger
	private static final Logger _logger = Logger.getLogger(SearchManagerImpl.class);

	public static final SearchManagerImpl _instance = new SearchManagerImpl();
	private static final String FOLDER_ORDERBY = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsFolder.folderId = :folderId and e.deleted=0 ";
	private static final String FILTER_BY_CATEGORY = "and e.emAnalyticsCategory = :category ";
	private static final String SEARCH_ENT_PREFIX = "e.";
	private static final String LASTACCESS_ORDERBY = "SELECT e FROM EmAnalyticsSearch e  where e.deleted=0 order by e.lastAccess.accessDate DESC ";

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
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
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
			if (e.getCause() != null && e.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else {
				_logger.error("Error while getting the search object by ID: " + searchId, e);
				throw new EMAnalyticsFwkException("Error while deleting the search object by ID: " + searchId,
						EMAnalyticsFwkException.ERR_DELETE_SEARCH, new Object[] { searchId }, e);
			}
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
			EntityManagerFactory emf;
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			EmAnalyticsSearch searchEntity = EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit(search, em);
			if (searchEntity != null && searchEntity.getSystemSearch() != null && searchEntity.getSystemSearch().intValue() == 1) {
				throw new EMAnalyticsFwkException("Search with Id: " + searchEntity.getId()
						+ " is system search and NOT allowed to edit", EMAnalyticsFwkException.ERR_UPDATE_SEARCH, null);
			}
			em.getTransaction().begin();
			em.merge(searchEntity);
			em.getTransaction().commit();
			return createSearchObject(searchEntity, null);
		}
		catch (EMAnalyticsFwkException eme) {

			_logger.error("Search with name " + search.getName() + " was updated but could not bve retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			if (dmlce.getCause().getMessage().contains("ANALYTICS_SEARCH_U01")) {
				throw new EMAnalyticsFwkException("Search name " + search.getName() + " already exist",
						EMAnalyticsFwkException.ERR_SEARCH_DUP_NAME, new Object[] { search.getName() });
			}
			else if (dmlce.getCause().getMessage().contains("ANALYTICS_SEARCH_FK2")) {
				throw new EMAnalyticsFwkException("Parent folder with id " + search.getFolderId() + " missing: "
						+ search.getName(), EMAnalyticsFwkException.ERR_SEARCH_INVALID_FOLDER, null);
			}
			else if (dmlce.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + dmlce.getMessage(), dmlce);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else if (dmlce.getCause().getMessage().contains("ANALYTICS_SEARCH_FK1")) {
				throw new EMAnalyticsFwkException("Category with id " + search.getCategoryId() + " missing: " + search.getName(),
						EMAnalyticsFwkException.ERR_SEARCH_INVALID_CATEGORY, null);
			}
			else {
				_logger.error("Error while updating the search: " + search.getName(), dmlce);
				throw new EMAnalyticsFwkException("Error while updating the search: " + search.getName(),
						EMAnalyticsFwkException.ERR_UPDATE_SEARCH, null, dmlce);
			}
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
		_logger.info("Retrieving search with id: " + searchId);
		EntityManager em = null;
		Search search = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			EmAnalyticsSearch searchObj = EmAnalyticsObjectUtil.getSearchById(searchId, em);
			if (searchObj != null) {
				em.refresh(searchObj);
				search = createSearchObject(searchObj, null);
			}
		}
		catch (Exception e) {
			if (e.getCause() != null && e.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else {
				_logger.error("Error while getting the search object by ID: " + searchId, e);
				throw new EMAnalyticsFwkException("search object by ID: " + searchId + " does not exist",
						EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID, new Object[] { searchId }, e);
			}
		}
		if (search == null) {
			_logger.error("Search identified by ID: " + searchId + " does not exist");
			throw new EMAnalyticsFwkException("Search identified by ID: " + searchId + " does not exist",
					EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID, new Object[] { searchId });
		}
		return search;
	}

	@Override
	public Search getSearchByName(String name, long folderId) throws EMAnalyticsFwkException
	{
		_logger.info("Retrieving search with name " + name + " in folder id: " + folderId);
		EntityManager em = null;
		EmAnalyticsSearch searchEntity = null;
		try {

			EntityManagerFactory emf;
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			//EmAnalyticsFolder folder = EmAnalyticsObjectUtil.getFolderById(folderId, em);
			searchEntity = (EmAnalyticsSearch) em.createNamedQuery("Search.getSearchByName").setParameter("folderId", folderId)
					.setParameter("searchName", name).getSingleResult();

			return createSearchObject(searchEntity, null);
		}
		catch (NoResultException nre) {
			return null;
		}
		catch (Exception e) {
			if (e.getCause() != null && e.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else {
				_logger.error("Error while retrieving the list of searches for the parent folder: " + folderId, e);
				throw new EMAnalyticsFwkException("Error while retrieving the list of searches for the parent folder: "
						+ folderId, EMAnalyticsFwkException.ERR_GENERIC, null, e);
			}
		}

	}

	@Override
	public int getSearchCountByFolderId(long folderId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		int count;
		try {
			EntityManagerFactory emf;
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			count = ((Number) em.createNamedQuery("Search.getSearchCountByFolder").setParameter("folder", folderId)
					.getSingleResult()).intValue();
			return count;
		}
		catch (Exception e) {
			if (e.getCause() != null && e.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else {
				_logger.error("Error while retrieving the count of searches for the parent folder: " + folderId, e);
				throw new EMAnalyticsFwkException("Error while retrieving the count of searches for the parent folder: "
						+ folderId, EMAnalyticsFwkException.ERR_GENERIC, null, e);
			}
		}
		finally {
			if (em != null) {
				em.close();
			}
		}

	}

	@Override
	public List<Search> getSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		try {
			List<Search> rtnobj = new ArrayList<Search>();
			EntityManagerFactory emf;
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			//			EmAnalyticsCategory category = EmAnalyticsObjectUtil.getCategoryById(categoryId, em);
			List<EmAnalyticsSearch> searchList = em.createNamedQuery("Search.getSearchListByCategory")
					.setParameter("categoryId", categoryId).getResultList();
			for (EmAnalyticsSearch searchObj : searchList) {
				em.refresh(searchObj);
				rtnobj.add(createSearchObject(searchObj, null));
			}

			return rtnobj;
		}
		catch (Exception e) {
			if (e.getCause() != null && e.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else {
				_logger.error("Error while retrieving the list of searches for the categoryD : " + categoryId, e);
				throw new EMAnalyticsFwkException("Error while retrieving the list of searches for the categoryId : "
						+ categoryId, EMAnalyticsFwkException.ERR_GENERIC, null, e);
			}
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
			EntityManagerFactory emf;
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			//EmAnalyticsFolder folder = EmAnalyticsObjectUtil.getFolderById(folderId, em);
			List<EmAnalyticsSearch> searchList = em.createNamedQuery("Search.getSearchListByFolder")
					.setParameter("folderId", folderId).getResultList();
			for (EmAnalyticsSearch searchObj : searchList) {
				em.refresh(searchObj);
				rtnobj.add(createSearchObject(searchObj, null));
			}

			return rtnobj;
		}
		catch (Exception e) {
			if (e.getCause() != null && e.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else {
				_logger.error("Error while retrieving the list of searches for the parent folder: " + folderId, e);
				throw new EMAnalyticsFwkException("Error while retrieving the list of searches for the parent folder: "
						+ folderId, EMAnalyticsFwkException.ERR_GENERIC, null, e);
			}
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<Search> getSearchListByFolderIdCategoryFilter(long folderId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		try {
			List<Search> rtnobj = new ArrayList<Search>();
			EntityManagerFactory emf;
			List<EmAnalyticsSearch> searchList = null;
			StringBuilder query = new StringBuilder(FOLDER_ORDERBY);
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			//EmAnalyticsFolder folder = EmAnalyticsObjectUtil.getFolderById(folderId, em);
			searchList = em.createQuery(query.toString()).setParameter("folderId", folderId).getResultList();

			for (EmAnalyticsSearch searchObj : searchList) {
				em.refresh(searchObj);
				rtnobj.add(createSearchObject(searchObj, null));
			}

			return rtnobj;
		}
		catch (EMAnalyticsFwkException e) {
			throw e;
		}
		catch (Exception e) {
			if (e.getCause() != null && e.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else {
				_logger.error("Error while retrieving the list of searches for the parent folder: " + folderId, e);
				throw new EMAnalyticsFwkException("Error while retrieving the list of searches for the parent folder: "
						+ folderId, EMAnalyticsFwkException.ERR_GENERIC, null, e);
			}
		}
	}

	@Override
	public List<Search> getSearchListByLastAccessDate(int count) throws EMAnalyticsFwkException
	{
		EntityManagerFactory emf;
		EntityManager em = null;
		List<EmAnalyticsSearch> searchList = null;
		List<Search> rtnobj = new ArrayList<Search>();
		try {
			StringBuilder query = new StringBuilder(LASTACCESS_ORDERBY);
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			searchList = em.createQuery(query.toString()).setMaxResults(count).getResultList();

			for (EmAnalyticsSearch searchObj : searchList) {
				rtnobj.add(createSearchObject(searchObj, null));
				//			modifyLastAccessDate(searchObj.getId());
			}
		}
		catch (EMAnalyticsFwkException e) {
			throw e;
		}
		catch (Exception e) {
			if (e.getCause() != null && e.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else {
				_logger.error("Error while retrieving the list of searches ");
				throw new EMAnalyticsFwkException("Error while retrieving the list of searches ",
						EMAnalyticsFwkException.ERR_GENERIC, null, e);
			}
		}
		return rtnobj;
	}

	@Override
	public Date modifyLastAccessDate(long searchId) throws EMAnalyticsFwkException
	{
		EntityManagerFactory emf;
		EntityManager em = null;
		List<EmAnalyticsSearch> searchList = null;
		List<Search> rtnobj = new ArrayList<Search>();
		try {
			Date tmp = null;
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
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
			if (e.getCause() != null && e.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else {
				_logger.error("Error while retrieving the list of searches ");
				throw new EMAnalyticsFwkException("Error while retrieving the list of searches ",
						EMAnalyticsFwkException.ERR_GENERIC, null, e);
			}
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	public List<Search> saveMultipleSearch(List<ImportSearchImpl> searchList) throws Exception
	{
		
		EntityManagerFactory emf = null;
		EntityManager em = null;
		boolean bCommit = true;
		boolean bResult = false;
		List<Search> importedList = new ArrayList<Search>();
		try {
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			em.getTransaction().begin();
			for (ImportSearchImpl tmpImportSrImpl : searchList) {
				Search search = tmpImportSrImpl.getSearch();
				Object obj = tmpImportSrImpl.getFolderDetails();
				Object cateObj = tmpImportSrImpl.getCategoryDetails();
				try {
					if (search.getId() != null && search.getId() > 0) {
						EmAnalyticsSearch emSearch = EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit(search, em);
						em.merge(emSearch);
						tmpImportSrImpl.setId((int)emSearch.getId());
						importedList.add(createSearchObject(emSearch, null));						
					}
					else {
						EmAnalyticsSearch searchEntity = null;
						try {
							if (obj != null && obj instanceof Integer) {
								long id = ((Integer) obj).longValue();								
								searchEntity = (EmAnalyticsSearch) em.createNamedQuery("Search.getSearchByName")
										.setParameter("folderId", id).setParameter("searchName", search.getName())
										.getSingleResult();
								tmpImportSrImpl.setId((int)searchEntity.getId());
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
								EmAnalyticsCategory categoryObj = EmAnalyticsObjectUtil.getCategoryById(
										((Integer) cateObj).longValue(), em);
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
												.setParameter("categoryName", ((Category) cateObj).getName()).getSingleResult();
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
												.setParameter("folderId", search.getFolderId()).setParameter("searchName", search.getName())
												.getSingleResult();
									}
									catch (NoResultException e) {
												
									}
									if (searchEntity != null) {
										tmpImportSrImpl.setId((int)searchEntity.getId());
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
							tmpImportSrImpl.setId((int)emSearch.getId());
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
			EntityManagerFactory emf;
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			EmAnalyticsSearch searchEntity = EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(search, em);
			em.getTransaction().begin();
			em.persist(searchEntity);
			updateSearchLastAccess(searchEntity);
			em.getTransaction().commit();
			em.refresh(searchEntity);
			return createSearchObject(searchEntity, null);
		}
		catch (EMAnalyticsFwkException eme) {
			_logger.error("Search with name " + search.getName() + " was saved but could not bve retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			if (dmlce.getCause().getMessage().contains("ANALYTICS_SEARCH_U01")) {
				throw new EMAnalyticsFwkException("Search with this name already exist: " + search.getName(),
						EMAnalyticsFwkException.ERR_SEARCH_DUP_NAME, new Object[] { search.getName() });
			}
			else if (dmlce.getCause().getMessage().contains("ANALYTICS_SEARCH_FK2")) {
				throw new EMAnalyticsFwkException("Parent folder with id " + search.getFolderId() + " missing: "
						+ search.getName(), EMAnalyticsFwkException.ERR_SEARCH_INVALID_FOLDER, null);
			}
			else if (dmlce.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + dmlce.getMessage(), dmlce);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else if (dmlce.getCause().getMessage().contains("EM_ANALYTICS_SEARCH_FK1")) {
				throw new EMAnalyticsFwkException("Category with id " + search.getCategoryId() + " missing: " + search.getName(),
						EMAnalyticsFwkException.ERR_SEARCH_INVALID_CATEGORY, null);
			}
			else {
				_logger.error("Error while saving the search: " + search.getName(), dmlce);
				throw new EMAnalyticsFwkException("Error while saving the search: " + search.getName(),
						EMAnalyticsFwkException.ERR_CREATE_SEARCH, null, dmlce);
			}
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
			rtnObj.setSystemSearch(searchObj.getSystemSearch() != null && searchObj.getSystemSearch().intValue() == 1 ? true
					: false);
			rtnObj.setLastAccessDate(searchObj.getAccessDate());

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
					param.setName(paramVORow.getId().getName());
					param.setAttributes(paramVORow.getParamAttributes());
					param.setType(ParameterType.fromIntValue(paramVORow.getParamType().intValue()));

					if (ParameterType.CLOB.equals(param.getType())) {
						System.out.println("Clob value =" + paramVORow.getParamValueClob());
						if(paramVORow.getParamValueClob()!=null)
						{
						char[] charArr = new char[paramVORow.getParamValueClob().length()];
						Reader reader = new StringReader(new String(paramVORow.getParamValueClob()));
						reader.read(charArr);
						param.setValue(new String(charArr));
						}
						else
						{
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

	private EmAnalyticsCategory getEmAnalyticsCategoryBySearch(ImportSearchImpl search, EntityManager em)
	{
		EmAnalyticsCategory category = null;
		try {
			if (search.getSearch().getCategoryId() != null) {
				category = EmAnalyticsObjectUtil.getCategoryById(search.getSearch().getCategoryId(), em);
			}
			else {
				if (search.getCategoryDetails() != null) {
					category = EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd((Category) search.getCategoryDetails(), em);
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
								.setParameter("foldername", folder.getName()).getSingleResult();

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
	private void OrderBybuilder(StringBuilder query, String[] orderBy)
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

	}

	private void updateSearchLastAccess(EmAnalyticsSearch search)
	{
		if (search == null) {
			return;
		}
		//		String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
		//		if (search.getLastAccess() == null) {
		//			search.setLastAccess(new EmAnalyticsLastAccess(search.getId(), currentUser,
		//					EmAnalyticsLastAccess.LAST_ACCESS_TYPE_SEARCH));
		//		}
		search.setAccessDate(DateUtil.getCurrentUTCTime());
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

}
