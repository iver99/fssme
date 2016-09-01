package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.QueryParameterConstant;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.RequestContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.RequestContext.RequestType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.logging.Logger;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategoryParam;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategoryParamPK;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearchParam;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearchParamPK;
import org.apache.logging.log4j.LogManager;

class EmAnalyticsObjectUtil
{
	private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(EmAnalyticsObjectUtil.class);
	public static boolean canDeleteFolder(long folderId, EntityManager em) throws EMAnalyticsFwkException
	{
		EmAnalyticsFolder folder = EmAnalyticsObjectUtil.getFolderById(folderId, em);
		int count = ((Number) em.createNamedQuery("Search.getSearchCountByFolder").setParameter("folder", folder)
				.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getSingleResult())
				.intValue();

		if (count > 0) {
			throw new EMAnalyticsFwkException("The folder can not be deleted as folder is associated with searches",
					EMAnalyticsFwkException.ERR_DELETE_FOLDER, null);
		}

		if (!em.createNamedQuery("Category.getCategoryByFolder").setParameter("id", folder)
				.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getResultList()
				.isEmpty()) {
			throw new EMAnalyticsFwkException("The folder can not be deleted as folder is associated with categories",
					EMAnalyticsFwkException.ERR_DELETE_FOLDER, null);
		}

		try {
			EmAnalyticsFolder folderObj = folder;
			String parentFolder = "parentFolder";
			@SuppressWarnings("unchecked")
			List<EmAnalyticsFolder> folderList = em.createNamedQuery("Folder.getSubFolder").setParameter(parentFolder, folderObj)
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getResultList();

			if (!folderList.isEmpty()) {
				throw new EMAnalyticsFwkException("Sub folders founds", EMAnalyticsFwkException.ERR_DELETE_FOLDER, null);
			}
		}
		catch (NoResultException e) {

		}
		return true;
	}

	public static EmAnalyticsCategory getCategoryById(long id, EntityManager em)
	{

		EmAnalyticsCategory cateObj = null;
		try {

			cateObj = em.find(EmAnalyticsCategory.class, id);
			if (cateObj != null) {
				if (cateObj.getDeleted() == 0
						&& (RequestContext.getContext().equals(RequestType.INTERNAL_TENANT)
								|| "ORACLE".equals(cateObj.getOwner()) || cateObj.getOwner().equals(
								TenantContext.getContext().getUsername()))) {

					return cateObj;
				}
				else {
					cateObj = null;
				}

			}

		}
		catch (Exception nre) {
			// do nothing
		}
		return cateObj;
	}

	public static EmAnalyticsCategory getCategoryByIdForDelete(long id, EntityManager em)
	{

		EmAnalyticsCategory cateObj = null;
		try {

			cateObj = em.find(EmAnalyticsCategory.class, id);
			if (cateObj != null
					&& (RequestContext.getContext().equals(RequestType.INTERNAL_TENANT) || "ORACLE".equals(cateObj.getOwner()) || cateObj
							.getOwner().equals(TenantContext.getContext().getUsername()))) {

				return cateObj;
			}
			else {
				cateObj = null;
			}

		}
		catch (Exception nre) {
			// do nothing
		}
		return cateObj;
	}

	public static EmAnalyticsCategory getCategoryByName(String categoryName, EntityManager em) {
		try {
			if (RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())) {
				return (EmAnalyticsCategory) em.createNamedQuery("Category.getCategoryByNameForTenant")
						.setParameter("categoryName", categoryName).getSingleResult();
			}
			else {
				return (EmAnalyticsCategory) em.createNamedQuery("Category.getCategoryByName")
						.setParameter("categoryName", categoryName)
						.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
						.getSingleResult();
			}
		}
		catch (NoResultException e) {
			return null;

		}
	}

	public static EmAnalyticsSearch getSearchByNameForDelete(String searchName, EntityManager entityManager) {
		EmAnalyticsSearch result = null;
		try {
			if (RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())) {
				result = (EmAnalyticsSearch) entityManager.createNamedQuery("Search.getSearchByNameExcludeOOBAndNonDeletedFORTenant")
						.setParameter("searchName", searchName)
						.getSingleResult();
			} else {
				result = (EmAnalyticsSearch) entityManager.createNamedQuery("Search.getSearchByNameExcludeOOBAndNonDeleted")
						.setParameter("searchName", searchName)
						.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
						.getSingleResult();
			}
		} catch (NonUniqueResultException e) {
			LOGGER.error("The result is not unique");
		} catch (NoResultException e) {
			LOGGER.error("There is not result");
		}
		return result;
	}

	public static List getSearchListByNamePatternForDelete(String searchNamePattern, EntityManager entityManager) {
		List result = new ArrayList();
		searchNamePattern = searchNamePattern.replace("%", "\\%");
		try {
			if (RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())) {
				result = entityManager.createNamedQuery("Search.getSearchByNamePatternExcludeOOBAndNonDeletedFORTenant")
						.setParameter("searchName", "%" + searchNamePattern + "%")
						.getResultList();
			} else {
				result = entityManager.createNamedQuery("Search.getSearchByNamePatternExcludeOOBAndNonDeleted")
						.setParameter("searchName", "%" + searchNamePattern + "%")
						.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
						.getResultList();
			}
		} catch (NoResultException e) {
			LOGGER.error("There is not result");
		}
		return result;
	}

	public static EmAnalyticsCategory getEmAnalyticsCategoryForAdd(Category category, EntityManager em)
			throws EMAnalyticsFwkException
	{
		EmAnalyticsCategory categoryObj = new EmAnalyticsCategory();
		categoryObj.setName(category.getName());
		categoryObj.setDescription(category.getDescription());
		String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
		Date utcNow = DateUtil.getCurrentUTCTime();
		categoryObj.setOwner(currentUser);
		categoryObj.setCreationDate(utcNow);
		categoryObj.setDeleted(0);
		categoryObj.setProviderName(category.getProviderName());
		categoryObj.setProviderVersion(category.getProviderVersion());
		categoryObj.setProviderDiscovery(category.getProviderDiscovery());
		categoryObj.setProviderAssetRoot(category.getProviderAssetRoot());
		if (category.getDefaultFolderId() != null) {
			EmAnalyticsFolder defaultFolderObj = EmAnalyticsObjectUtil.getFolderById(category.getDefaultFolderId(), em);
			if (defaultFolderObj == null) {
				throw new EMAnalyticsFwkException("Folder with id " + category.getDefaultFolderId() + " " + "does not exist",
						EMAnalyticsFwkException.ERR_CATEGORY_INVALID_FOLDER, null);
			}
			categoryObj.setEmAnalyticsFolder(defaultFolderObj);
		}
		List<Parameter> params = category.getParameters();
		//Set<EmAnalyticsCategoryParam> paramSet = new HashSet<EmAnalyticsCategoryParam>();
		if (params != null && !params.isEmpty()) {
			Iterator<Parameter> paramIter = params.iterator();
			while (paramIter.hasNext()) {
				Parameter param = paramIter.next();
				//parameter DASHBOARD_INELIGIBLE has been move into Category table as a column
				if ("DASHBOARD_INELIGIBLE".equals(param.getName())) {
					categoryObj.setdashboardIneligible(param.getValue());
					paramIter.remove();
					continue;
				}
				EmAnalyticsCategoryParam categoryParamEntity = new EmAnalyticsCategoryParam();
				categoryParamEntity.setEmAnalyticsCategory(categoryObj);
				categoryParamEntity.setName(param.getName());
				categoryParamEntity.setValue(param.getValue());
				categoryObj.getEmAnalyticsCategoryParams().add(categoryParamEntity);

			}

		}

		return categoryObj;
	}

	public static EmAnalyticsCategory getEmAnalyticsCategoryForEdit(Category category, EntityManager em)
	{
		EmAnalyticsCategory categoryEntity = EmAnalyticsObjectUtil.getCategoryById(category.getId(), em);
		em.refresh(categoryEntity);
		categoryEntity.setName(category.getName());
		categoryEntity.setDescription(category.getDescription());
		String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
		categoryEntity.setOwner(currentUser);
		categoryEntity.setDeleted(0);
		categoryEntity.setProviderName(category.getProviderName());
		categoryEntity.setProviderVersion(category.getProviderVersion());
		categoryEntity.setProviderDiscovery(category.getProviderDiscovery());
		categoryEntity.setProviderAssetRoot(category.getProviderAssetRoot());
		if (category.getDefaultFolderId() != null) {
			categoryEntity.setEmAnalyticsFolder(EmAnalyticsObjectUtil.getFolderById(category.getDefaultFolderId(), em));
		}

		// param handling !!
		List<Parameter> params = category.getParameters();
		Long tenantId = TenantContext.getContext().getTenantInternalId();
		Map<EmAnalyticsCategoryParamPK, EmAnalyticsCategoryParam> newParams = new HashMap<EmAnalyticsCategoryParamPK, EmAnalyticsCategoryParam>();
		for (Parameter param : params) {
			EmAnalyticsCategoryParam newCatParam = new EmAnalyticsCategoryParam();
			EmAnalyticsCategoryParamPK newPK = new EmAnalyticsCategoryParamPK();
			newPK.setCategoryId(category.getId());
			newPK.setName(param.getName());
			newPK.setTenantId(tenantId);
			newCatParam.setCategoryId(category.getId());
			newCatParam.setName(param.getName());
			newCatParam.setEmAnalyticsCategory(categoryEntity);
			newCatParam.setValue(param.getValue());
			newCatParam.setEmAnalyticsCategory(categoryEntity);
			newParams.put(newPK, newCatParam);
		}

		Set<EmAnalyticsCategoryParam> existingParams = categoryEntity.getEmAnalyticsCategoryParams();
		Iterator<EmAnalyticsCategoryParam> it = existingParams.iterator();
		while (it.hasNext()) {
			EmAnalyticsCategoryParam catParam = it.next();
			if (!newParams.containsKey(catParam.getCategoryId()) || newParams.containsKey(catParam.getCategoryId())
					&& !newParams.containsValue(catParam)) {
				it.remove();
			}
		}

		existingParams.addAll(newParams.values()); // Set addition takes
		// care of duplicates !!
		Iterator<EmAnalyticsCategoryParam> paramIter = existingParams.iterator();
		while (paramIter.hasNext()) {
			EmAnalyticsCategoryParam paramObj = paramIter.next();
			if ("DASHBOARD_INELIGIBLE".equals(paramObj.getName())) {
				categoryEntity.setdashboardIneligible(paramObj.getValue());
				paramIter.remove();
				break;
			}
		}
		return categoryEntity;
	}

	public static EmAnalyticsFolder getEmAnalyticsFolderByFolderObject(Folder folder)
	{
		EntityManager em = null;
		EmAnalyticsFolder folderObj = null;
		try {

			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			if (folder.getParentId() == null) {
				folderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getRootFolderByName")
						.setParameter("foldername", folder.getName())
						.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
						.getSingleResult();

			}
			else {
				if (folder.getParentId() != null) {
					EmAnalyticsFolder parentFolderObj = null;
					if (folder.getParentId() == 1) {
						try {
							parentFolderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getRootFolders")
									.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
									.getSingleResult();
						}
						catch (NoResultException e) {
							parentFolderObj = null;
						}
					}
					else {
						parentFolderObj = EmAnalyticsObjectUtil.getFolderById(folder.getParentId().longValue(), em);
					}
					if (parentFolderObj == null) {
						return folderObj;
					}
					String parentFolder = "parentFolder";
					folderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getSubFolderByName")
							.setParameter(parentFolder, parentFolderObj).setParameter("foldername", folder.getName())
							.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
							.getSingleResult();
				}
			}

		}
		catch (NoResultException nre) {
			folderObj = null;
		}
		catch (Exception e) {
			folderObj = null;
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
		return folderObj;
	}

	public static EmAnalyticsFolder getEmAnalyticsFolderForAdd(Folder folder, EntityManager em) throws EMAnalyticsFwkException
	{
		EmAnalyticsFolder folderObj = new EmAnalyticsFolder();
		Date utcNow = DateUtil.getCurrentUTCTime();
		folderObj.setDescription(folder.getDescription());
		folderObj.setName(folder.getName());
		String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
		folderObj.setOwner(currentUser);
		folderObj.setCreationDate(utcNow);
		folderObj.setLastModifiedBy(currentUser);
		folderObj.setLastModificationDate(utcNow);
		folderObj.setUiHidden(new BigDecimal(0));
		boolean bResult = "ORACLE".equals(currentUser);
		folderObj.setSystemFolder(bResult ? new BigDecimal(1) : new BigDecimal(0));
		folderObj.setDeleted(0);
		if (folder.getParentId() != null) {

			EmAnalyticsFolder parentFolderObj = EmAnalyticsObjectUtil.getFolderById(folder.getParentId(), em);

			if (parentFolderObj == null) {
				throw new EMAnalyticsFwkException("Parent folder with Id " + folder.getParentId() + " does not exist",
						EMAnalyticsFwkException.ERR_FOLDER_INVALID_PARENT, null);
			}
			folderObj.setEmAnalyticsFolder(parentFolderObj);

		}
		return folderObj;
	}

	public static EmAnalyticsFolder getEmAnalyticsFolderForEdit(Folder folder, EntityManager em) throws EMAnalyticsFwkException
	{
		EmAnalyticsFolder folderObj = null;

		folderObj = EmAnalyticsObjectUtil.getFolderById(folder.getId(), em);
		if (folderObj != null) {
			folderObj.setName(folder.getName());
			folderObj.setDescription(folder.getDescription());
			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
			folderObj.setLastModifiedBy(currentUser);
			Date utcNow = DateUtil.getCurrentUTCTime();
			folderObj.setLastModificationDate(utcNow);
			folderObj.setSystemFolder(folder.isSystemFolder() == true ? new BigDecimal(1) : new BigDecimal(0));
			folderObj.setUiHidden(folder.isUiHidden() == true ? new BigDecimal(1) : new BigDecimal(0));
			folderObj.setDeleted(0);
			if (folder.getParentId() != null) {
				EmAnalyticsFolder parentFolderObj = null;

				if (folder.getParentId() == 1) { //get root for folder for tenant-id  vb
					try {
						parentFolderObj = EmAnalyticsObjectUtil.getFolderById(folder.getParentId(), em);
						/*parentFolderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getRootFolders")
								.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
								.getSingleResult();*/
					}
					catch (NoResultException e) {
						parentFolderObj = null;
					}
				}
				if (parentFolderObj == null) {
					parentFolderObj = EmAnalyticsObjectUtil.getFolderById(folder.getParentId(), em);
				}
				if (parentFolderObj == null) {
					throw new EMAnalyticsFwkException("Parent folder with Id " + folder.getParentId() + " does not exist",
							EMAnalyticsFwkException.ERR_FOLDER_INVALID_PARENT, null);
				}
				folderObj.setEmAnalyticsFolder(parentFolderObj);
			}

		}
		else {
			throw new EMAnalyticsFwkException("Can not find folder for update with id: " + folder.getId(),
					EMAnalyticsFwkException.ERR_GET_FOLDER_FOR_ID, null);
		}

		return folderObj;
	}

	public static EmAnalyticsSearch getEmAnalyticsSearchForAdd(Search search, EntityManager em) throws EMAnalyticsFwkException
	{
		EmAnalyticsSearch searchEntity = new EmAnalyticsSearch();
		// Copy all the data to entity !!

		searchEntity.setName(search.getName());
		searchEntity.setDescription(search.getDescription());
		EmAnalyticsFolder folderObj = EmAnalyticsObjectUtil.getFolderById(search.getFolderId(), em);
		if (folderObj == null) {
			throw new EMAnalyticsFwkException("Can not find folder with id: " + search.getFolderId(),
					EMAnalyticsFwkException.ERR_SEARCH_INVALID_FOLDER, null);
		}
		else {
			searchEntity.setEmAnalyticsFolder(folderObj);
		}

		EmAnalyticsCategory categoryObj = EmAnalyticsObjectUtil.getCategoryById(search.getCategoryId(), em);
		if (categoryObj == null) {
			throw new EMAnalyticsFwkException("Can not find category with id: " + search.getCategoryId(),
					EMAnalyticsFwkException.ERR_SEARCH_INVALID_CATEGORY, null);
		}
		else {
			searchEntity.setEmAnalyticsCategory(categoryObj);
		}

		Date utcDate = DateUtil.getCurrentUTCTime();
		String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
		searchEntity.setOwner(currentUser);
		searchEntity.setCreationDate(utcDate);
		searchEntity.setLastModifiedBy(currentUser);
		searchEntity.setLastModificationDate(utcDate);
		boolean isSystemSearch = "ORACLE".equals(currentUser);
		searchEntity.setSystemSearch(isSystemSearch ? new java.math.BigDecimal(1) : new java.math.BigDecimal(0));
		searchEntity.setIsLocked(search.isLocked() ? new java.math.BigDecimal(1) : new java.math.BigDecimal(0));
		searchEntity.setMetadataClob(search.getMetadata());
		searchEntity.setSearchDisplayStr(search.getQueryStr());
		searchEntity.setUiHidden(new java.math.BigDecimal(search.isUiHidden() ? 1 : 0));
		searchEntity.setIsWidget(search.getIsWidget() ? 1 : 0);
		searchEntity.setDeleted(0);
		List<SearchParameter> params = search.getParameters();
		//move values from search_params table to search table
		EmAnalyticsObjectUtil.moveParamsToSearchAdd(searchEntity, params);
		if (params != null && !params.isEmpty()) {
			for (SearchParameter param : params) {
				EmAnalyticsSearchParam searchParamEntity = new EmAnalyticsSearchParam();
				searchParamEntity.setEmAnalyticsSearch(searchEntity);
				searchParamEntity.setSearchId(searchEntity.getId());
				searchParamEntity.setName(param.getName());
				searchParamEntity.setParamAttributes(param.getAttributes());
				searchParamEntity.setParamType(new BigDecimal(param.getType().getIntValue()));
				if (param.getType().equals(ParameterType.CLOB)) {
					searchParamEntity.setParamValueClob(param.getValue());
				}
				else {
					searchParamEntity.setParamValueStr(param.getValue());
				}

				searchEntity.getEmAnalyticsSearchParams().add(searchParamEntity);
			}
		}
		return searchEntity;
	}

	public static EmAnalyticsSearch getEmAnalyticsSearchForEdit(Search search, EntityManager em) throws EMAnalyticsFwkException
	{
		EmAnalyticsSearch searchEntity = EmAnalyticsObjectUtil.getSearchById(search.getId(), em);
		em.refresh(searchEntity);
		// Copy all the data to entity !!
		searchEntity.setMetadataClob(search.getMetadata());
		searchEntity.setName(search.getName());

		searchEntity.setDescription(search.getDescription());
		EmAnalyticsFolder folderObj = EmAnalyticsObjectUtil.getFolderById(search.getFolderId(), em);

		if (folderObj == null) {
			throw new EMAnalyticsFwkException("Can not find folder with id: " + search.getFolderId(),
					EMAnalyticsFwkException.ERR_SEARCH_INVALID_FOLDER, null);
		}
		else {
			searchEntity.setEmAnalyticsFolder(folderObj);
		}

		EmAnalyticsCategory categoryObj = EmAnalyticsObjectUtil.getCategoryById(search.getCategoryId(), em);

		if (categoryObj == null) {
			throw new EMAnalyticsFwkException("Can not find category with id: " + search.getCategoryId(),
					EMAnalyticsFwkException.ERR_SEARCH_INVALID_CATEGORY, null);
		}
		else {
			searchEntity.setEmAnalyticsCategory(categoryObj);
		}

		Date utcNow = DateUtil.getCurrentUTCTime();
		if (!RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())) {
			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
			searchEntity.setOwner(currentUser);
			searchEntity.setLastModifiedBy(currentUser);
		}
		searchEntity.setLastModificationDate(utcNow);
		searchEntity.setSystemSearch(searchEntity.getSystemSearch() != null ? searchEntity.getSystemSearch()
				: new java.math.BigDecimal(0));
		searchEntity.setIsLocked(search != null && search.isLocked() ? new java.math.BigDecimal(1) : new java.math.BigDecimal(0));

		searchEntity.setSearchDisplayStr(search.getQueryStr());

		searchEntity.setUiHidden(new java.math.BigDecimal(search != null && search.isUiHidden() ? 1 : 0));
		searchEntity.setIsWidget(search.getIsWidget() ? 1 : 0);
		searchEntity.setDeleted(0);
		searchEntity.setName(search.getName());
		List<SearchParameter> params = search.getParameters();
		// Params handling !!
		Set<EmAnalyticsSearchParam> existingParams = Collections.synchronizedSet(searchEntity.getEmAnalyticsSearchParams());
		Map<EmAnalyticsSearchParamPK, EmAnalyticsSearchParam> newParams = new HashMap<EmAnalyticsSearchParamPK, EmAnalyticsSearchParam>();
		Long tenantId = TenantContext.getContext().getTenantInternalId();
		if (params != null) {
			for (SearchParameter param : params) {
				EmAnalyticsSearchParam newSearchParam = new EmAnalyticsSearchParam();
				EmAnalyticsSearchParamPK newPK = new EmAnalyticsSearchParamPK();
				newPK.setSearchId(search.getId());
				newPK.setName(param.getName());
				newPK.setTenantId(tenantId);
				newSearchParam.setSearchId(search.getId());
				newSearchParam.setName(param.getName());
				newSearchParam.setEmAnalyticsSearch(searchEntity);
				newSearchParam.setParamAttributes(param.getAttributes());
				newSearchParam.setParamType(new BigDecimal(param.getType().getIntValue()));
				if (param.getType() == ParameterType.STRING) {
					newSearchParam.setParamValueStr(param.getValue());
				}
				else {
					newSearchParam.setParamValueClob(param.getValue());
				}

				newParams.put(newPK, newSearchParam);
			}
		}

		// return error if the ODS entity id is missed
		// not in old -> false;
		// in old and in new -> false;
		// in old but not in new -> true;
		if (EmAnalyticsObjectUtil.containODSEntityId(existingParams)
				&& !EmAnalyticsObjectUtil.containODSEntityId(newParams.values())) {
			throw new EMAnalyticsFwkException("Can not delete ODS Entity without deleting Saved Search: " + search.getName(),
					EMAnalyticsFwkException.ERR_GENERIC, null);
		}

		Iterator<EmAnalyticsSearchParam> it = existingParams.iterator();
		while (it.hasNext()) {
			EmAnalyticsSearchParam searchParam = it.next();
			if (!newParams.containsKey(searchParam.getSearchId()) || newParams.containsKey(searchParam.getSearchId())
					&& !newParams.containsValue(searchParam)) {
				it.remove();
			}
		}
		existingParams.addAll(newParams.values());

		//move value from params to search table
		EmAnalyticsObjectUtil.moveParamsToSearchEdit(searchEntity, existingParams);

		searchEntity.setAccessDate(utcNow);
		return searchEntity;
	}

	public static EmAnalyticsFolder getFolderById(long id, EntityManager em)
	{

		EmAnalyticsFolder folderObj = null;

		try {

			folderObj = em.find(EmAnalyticsFolder.class, id);
			if (folderObj != null
					&& folderObj.getDeleted() == 0
					&& (RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())
							|| folderObj.getSystemFolder().intValue() == 1 || folderObj.getOwner().equals(
									TenantContext.getContext().getUsername()))) {

				return folderObj;
			}
			else {
				folderObj = null;
			}

		}
		catch (Exception nre) {
			//do nothing

		}
		return folderObj;
	}

	public static EmAnalyticsFolder getFolderByIdForDelete(long id, EntityManager em)
	{

		EmAnalyticsFolder folderObj = null;

		try {

			folderObj = em.find(EmAnalyticsFolder.class, id);
			if (folderObj != null
					&& (folderObj.getSystemFolder().intValue() == 1 || folderObj.getOwner().equals(
							TenantContext.getContext().getUsername()))) {

				return folderObj;
			}
			else {
				folderObj = null;
			}

		}
		catch (Exception nre) {
			//do nothing

		}
		return folderObj;
	}

	public static EmAnalyticsFolder getRootFolder()
	{
		final String rootfoldername = "All Searches";
		EmAnalyticsFolder folderObj = new EmAnalyticsFolder();
		Date utcNow = DateUtil.getCurrentUTCTime();
		folderObj.setDescription(rootfoldername);
		folderObj.setName(rootfoldername);
		String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
		folderObj.setOwner(currentUser);
		folderObj.setCreationDate(utcNow);
		folderObj.setLastModifiedBy(currentUser);
		folderObj.setLastModificationDate(utcNow);
		folderObj.setUiHidden(new BigDecimal(0));
		folderObj.setSystemFolder(new BigDecimal(1));
		folderObj.setDeleted(0);
		folderObj.setEmAnalyticsFolder(null);

		return folderObj;
	}

	public static EmAnalyticsSearch getSearchById(long id, EntityManager em)
	{

		EmAnalyticsSearch searchObj = null;
		try {

			searchObj = em.find(EmAnalyticsSearch.class, id);
			if (searchObj != null) {
				if (searchObj.getDeleted() == 0
						&& (RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())
								|| searchObj.getSystemSearch().intValue() == 1 || searchObj.getOwner().equals(
										TenantContext.getContext().getUsername()))) {

					return searchObj;
				}
				else {
					searchObj = null;
				}
			}
		}
		catch (Exception nre) {
			//don nothing
		}
		return searchObj;
	}

	public static EmAnalyticsSearch findEmSearchByIdWithoutOwner(long searchId, EntityManager em) {
		EmAnalyticsSearch searchObj = null;
		try {
			searchObj = em.find(EmAnalyticsSearch.class, searchId);
			// return null if the search has been deleted
			if(searchObj != null && searchObj.getDeleted() != 0) {
				searchObj = null;
			}
		} catch (Exception ex) {
			// do nothing
		}
		return searchObj;
	}

	public static EmAnalyticsSearch getSearchByIdForDelete(long id, EntityManager em)
	{

		EmAnalyticsSearch searchObj = null;
		try {

			searchObj = em.find(EmAnalyticsSearch.class, id);
			if (searchObj != null
					&& (RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())
							|| searchObj.getSystemSearch().intValue() == 1 || searchObj.getOwner().equals(
									TenantContext.getContext().getUsername()))) {
				return searchObj;
			}
			else {
				searchObj = null;
			}

		}
		catch (Exception nre) {
			//don nothing
		}
		return searchObj;
	}

	@SuppressWarnings("unchecked")
	public static String getSearchParamByName(long searchId, String paramName, EntityManager em)
	{
		List<EmAnalyticsSearchParam> paramList = em.createNamedQuery("SearchParam.getParamByName")
				.setParameter("searchId", searchId).setParameter("name", paramName).getResultList();
		if (!paramList.isEmpty()) {
			EmAnalyticsSearchParam param = paramList.get(0);
			return ParameterType.STRING.getIntValue() == param.getParamType().intValue() ? param.getParamValueStr() : param
					.getParamValueClob();
		}
		else {
			return null;
		}
	}

	private static boolean containODSEntityId(Collection<EmAnalyticsSearchParam> params)
	{
		for (EmAnalyticsSearchParam param : params) {
			if ("meId".equals(param.getName())) {
				return true;
			}
		}
		return false;
	}

	private static void moveParamsToSearchAdd(EmAnalyticsSearch searchEntity, List<SearchParameter> params)
	{
		if (params != null && !params.isEmpty()) {
			Iterator<SearchParameter> paramIter = params.iterator();
			while (paramIter.hasNext()) {
				SearchParameter param = paramIter.next();
				if ("WIDGET_SOURCE".equals(param.getName())) {
					searchEntity.setWIDGET_SOURCE(param.getValue());
					paramIter.remove();
					continue;
				}
				if ("WIDGET_GROUP_NAME".equals(param.getName())) {
					searchEntity.setWIDGETGROUPNAME(param.getValue());
					paramIter.remove();
					continue;
				}
				if ("WIDGET_SCREENSHOT_HREF".equals(param.getName())) {
					searchEntity.setWIDGETSCREENSHOTHREF(param.getValue());
					paramIter.remove();
					continue;
				}
				if ("WIDGET_ICON".equals(param.getName())) {
					searchEntity.setWIDGETICON(param.getValue());
					paramIter.remove();
					continue;
				}
				if ("WIDGET_KOC_NAME".equals(param.getName())) {
					searchEntity.setWIDGETKOCNAME(param.getValue());
					paramIter.remove();
					continue;
				}
				if ("WIDGET_VIEWMODEL".equals(param.getName())) {
					searchEntity.setWIDGETVIEWMODEL(param.getValue());
					paramIter.remove();
					continue;
				}
				if ("WIDGET_TEMPLATE".equals(param.getName())) {
					searchEntity.setWIDGETTEMPLATE(param.getValue());
					paramIter.remove();
					continue;
				}
				if ("WIDGET_SUPPORT_TIME_CONTROL".equals(param.getName())) {
					searchEntity.setWIDGETSUPPORTTIMECONTROL(param.getValue());
					paramIter.remove();
					continue;
				}
				if ("WIDGET_LINKED_DASHBOARD".equals(param.getName())) {
					searchEntity.setWIDGETLINKEDDASHBOARD(Long.valueOf(param.getValue()));
					paramIter.remove();
					continue;
				}
				if ("WIDGET_DEFAULT_WIDTH".equals(param.getName())) {
					searchEntity.setWIDGETDEFAULTWIDTH(Long.valueOf(param.getValue()));
					paramIter.remove();
					continue;
				}
				if ("WIDGET_DEFAULT_HEIGHT".equals(param.getName())) {
					searchEntity.setWIDGETDEFAULTHEIGHT(Long.valueOf(param.getValue()));
					paramIter.remove();
					continue;
				}
				if ("DASHBOARD_INELIGIBLE".equals(param.getName())) {
					searchEntity.setDASHBOARDINELIGIBLE(param.getValue());
					paramIter.remove();
					continue;
				}
				if ("PROVIDER_NAME".equals(param.getName())) {
					searchEntity.setPROVIDERNAME(param.getValue());
					paramIter.remove();
					continue;
				}
				if ("PROVIDER_VERSION".equals(param.getName())) {
					searchEntity.setPROVIDERVERSION(param.getValue());
					paramIter.remove();
					continue;
				}
				if ("PROVIDER_ASSET_ROOT".equals(param.getName())) {
					searchEntity.setPROVIDERASSETROOT(param.getValue());
					paramIter.remove();
					continue;
				}
			}
		}
	}

	/* DEAD CODE
	public static boolean isFolderExist(Folder folder)
	{
		EntityManager em = null;
		EmAnalyticsFolder folderObj = null;
		boolean bResult = false;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			if (folder.getParentId() == null || folder.getParentId() == 0) {
				folderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getRootFolderByName")
						.setParameter("foldername", folder.getName()).getSingleResult();

			}
			else {
				if (folder.getParentId() != null) {
					EmAnalyticsFolder parentFolderObj = EmAnalyticsObjectUtil.getFolderById(folder.getParentId().longValue(), em);
					String parentFolder = "parentFolder";
					folderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getSubFolderByName")
							.setParameter(parentFolder, parentFolderObj).setParameter("foldername", folder.getName())
							.getSingleResult();
				}
			}
			bResult = true;
		}
		catch (NoResultException nre) {
			bResult = false;
		}
		catch (Exception e) {
			bResult = false;
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
		return bResult;
	}
	 */

	private static void moveParamsToSearchEdit(EmAnalyticsSearch searchEntity, Set<EmAnalyticsSearchParam> existingParams)
	{
		Iterator<EmAnalyticsSearchParam> paramIter = existingParams.iterator();
		while (paramIter.hasNext()) {
			EmAnalyticsSearchParam param = paramIter.next();
			if ("WIDGET_SOURCE".equals(param.getName())) {
				searchEntity.setWIDGET_SOURCE(param.getParamValueStr());
				paramIter.remove();
				continue;
			}
			if ("WIDGET_GROUP_NAME".equals(param.getName())) {
				searchEntity.setWIDGETGROUPNAME(param.getParamValueStr());
				paramIter.remove();
				continue;
			}
			if ("WIDGET_SCREENSHOT_HREF".equals(param.getName())) {
				searchEntity.setWIDGETSCREENSHOTHREF(param.getParamValueStr());
				paramIter.remove();
				continue;
			}
			if ("WIDGET_ICON".equals(param.getName())) {
				searchEntity.setWIDGETICON(param.getParamValueStr());
				paramIter.remove();
				continue;
			}
			if ("WIDGET_KOC_NAME".equals(param.getName())) {
				searchEntity.setWIDGETKOCNAME(param.getParamValueStr());
				paramIter.remove();
				continue;
			}
			if ("WIDGET_VIEWMODEL".equals(param.getName())) {
				searchEntity.setWIDGETVIEWMODEL(param.getParamValueStr());
				paramIter.remove();
				continue;
			}
			if ("WIDGET_TEMPLATE".equals(param.getName())) {
				searchEntity.setWIDGETTEMPLATE(param.getParamValueStr());
				paramIter.remove();
				continue;
			}
			if ("WIDGET_SUPPORT_TIME_CONTROL".equals(param.getName())) {
				searchEntity.setWIDGETSUPPORTTIMECONTROL(param.getParamValueStr());
				paramIter.remove();
				continue;
			}
			if ("WIDGET_LINKED_DASHBOARD".equals(param.getName())) {
				searchEntity.setWIDGETLINKEDDASHBOARD(Long.valueOf(param.getParamValueStr()));
				paramIter.remove();
				continue;
			}
			if ("WIDGET_DEFAULT_WIDTH".equals(param.getName())) {
				searchEntity.setWIDGETDEFAULTWIDTH(Long.valueOf(param.getParamValueStr()));
				paramIter.remove();
				continue;
			}
			if ("WIDGET_DEFAULT_HEIGHT".equals(param.getName())) {
				searchEntity.setWIDGETDEFAULTHEIGHT(Long.valueOf(param.getParamValueStr()));
				paramIter.remove();
				continue;
			}
			if ("DASHBOARD_INELIGIBLE".equals(param.getName())) {
				searchEntity.setDASHBOARDINELIGIBLE(param.getParamValueStr());
				paramIter.remove();
				continue;
			}
			if ("PROVIDER_NAME".equals(param.getName())) {
				searchEntity.setPROVIDERNAME(param.getParamValueStr());
				paramIter.remove();
				continue;
			}
			if ("PROVIDER_VERSION".equals(param.getName())) {
				searchEntity.setPROVIDERVERSION(param.getParamValueStr());
				paramIter.remove();
				continue;
			}
			if ("PROVIDER_ASSET_ROOT".equals(param.getName())) {
				searchEntity.setPROVIDERASSETROOT(param.getParamValueStr());
				paramIter.remove();
				continue;
			}
		}
	}

	private EmAnalyticsObjectUtil()
	{
	}
}
