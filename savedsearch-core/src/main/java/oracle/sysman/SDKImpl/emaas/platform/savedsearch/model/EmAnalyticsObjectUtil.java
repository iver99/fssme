package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategoryParam;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategoryParamPK;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearchParam;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearchParamPK;

class EmAnalyticsObjectUtil
{

	public static boolean canDeleteCategory(long categoryId, EntityManager em)
	{
		EmAnalyticsCategory cat = null;

		try {
			cat = (EmAnalyticsCategory) em.createNamedQuery("Category.getCategoryById").setParameter("id", categoryId)
					.getSingleResult();

			if (em.createNamedQuery("Search.getSearchListByCategory").setParameter("category", cat).getResultList().size() > 0) {
				throw new EMAnalyticsFwkException("The category can not be deleted as category is associated with searches",
						EMAnalyticsFwkException.ERR_DELETE_CATEGORY, null);
			}
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean canDeleteFolder(long folderId, EntityManager em) throws EMAnalyticsFwkException
	{
		EmAnalyticsFolder folder = EmAnalyticsObjectUtil.getFolderById(folderId, em);
		int count = ((Number) em.createNamedQuery("Search.getSearchCountByFolder").setParameter("folder", folder)
				.getSingleResult()).intValue();

		if (count > 0) {
			throw new EMAnalyticsFwkException("The folder can not be deleted as folder is associated with searches",
					EMAnalyticsFwkException.ERR_DELETE_FOLDER, null);
		}

		if (em.createNamedQuery("Category.getCategoryByFolder").setParameter("id", folder).getResultList().size() > 0) {
			throw new EMAnalyticsFwkException("The folder can not be deleted as folder is associated with categories",
					EMAnalyticsFwkException.ERR_DELETE_FOLDER, null);
		}

		try {
			EmAnalyticsFolder folderObj = folder;
			String parentFolder = "parentFolder";
			List<EmAnalyticsFolder> folderList = em.createNamedQuery("Folder.getSubFolder").setParameter(parentFolder, folderObj)
					.getResultList();

			if (folderList.size() > 0) {
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
				if (cateObj.getDeleted() == 0) {

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
			if (cateObj != null) {

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

	public static EmAnalyticsCategory getCategoryByName(String categoryName, EntityManager em)
	{
		try {
			return (EmAnalyticsCategory) em.createNamedQuery("Category.getCategoryByName")
					.setParameter("categoryName", categoryName).getSingleResult();
		}
		catch (NoResultException e) {
			return null;

		}
	}

	public static EmAnalyticsCategory getEmAnalyticsCategoryForAdd(Category category, EntityManager em)
			throws EMAnalyticsFwkException
	{
		EmAnalyticsCategory categoryObj = new EmAnalyticsCategory();
		categoryObj.setName(category.getName());
		categoryObj.setDescription(category.getDescription());
		categoryObj.setOwner("sysman"); // TO DO fix it
		categoryObj.setDeleted(0);
		if (category.getDefaultFolderId() != null) {
			EmAnalyticsFolder defaultFolderObj = EmAnalyticsObjectUtil.getFolderById(category.getDefaultFolderId(), em);
			if (defaultFolderObj == null) {
				throw new EMAnalyticsFwkException("Folder with id " + category.getDefaultFolderId() + " " + "does not exist",
						EMAnalyticsFwkException.ERR_CATEGORY_INVALID_FOLDER, null);
			}
			categoryObj.setEmAnalyticsFolder(defaultFolderObj);
		}
		List<Parameter> params = category.getParameters();
		Set<EmAnalyticsCategoryParam> paramSet = new HashSet<EmAnalyticsCategoryParam>();
		if (params != null && params.size() != 0) {
			for (Parameter param : params) {
				EmAnalyticsCategoryParam categoryParamEntity = new EmAnalyticsCategoryParam();
				categoryParamEntity.setEmAnalyticsCategory(categoryObj);
				EmAnalyticsCategoryParamPK id = new EmAnalyticsCategoryParamPK();
				id.setName(param.getName());

				categoryParamEntity.setId(id);
				// categoryParamEntity.setName(param.getName());
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
		categoryEntity.setOwner("sysman"); // TO DO fix it
		categoryEntity.setDeleted(0);
		if (category.getDefaultFolderId() != null) {
			categoryEntity.setEmAnalyticsFolder(EmAnalyticsObjectUtil.getFolderById(category.getDefaultFolderId(), em));
		}

		// param handling !!
		List<Parameter> params = category.getParameters();
		Map<EmAnalyticsCategoryParamPK, EmAnalyticsCategoryParam> newParams = new HashMap<EmAnalyticsCategoryParamPK, EmAnalyticsCategoryParam>();
		for (Parameter param : params) {
			EmAnalyticsCategoryParam newCatParam = new EmAnalyticsCategoryParam();
			EmAnalyticsCategoryParamPK newPK = new EmAnalyticsCategoryParamPK();
			newPK.setCategoryId(category.getId());
			newPK.setName(param.getName());
			newCatParam.setId(newPK);
			newCatParam.setEmAnalyticsCategory(categoryEntity);
			newCatParam.setValue(param.getValue());
			newCatParam.setEmAnalyticsCategory(categoryEntity);
			newParams.put(newPK, newCatParam);
		}

		Set<EmAnalyticsCategoryParam> existingParams = categoryEntity.getEmAnalyticsCategoryParams();
		Iterator<EmAnalyticsCategoryParam> it = existingParams.iterator();
		while (it.hasNext()) {
			EmAnalyticsCategoryParam catParam = it.next();
			if (!newParams.containsKey(catParam.getId()) || newParams.containsKey(catParam.getId())
					&& !newParams.containsValue(catParam)) {
				it.remove();
			}
		}

		existingParams.addAll(newParams.values()); // Set addition takes
													// care of duplicates !!
		return categoryEntity;
	}

	public static EmAnalyticsFolder getEmAnalyticsFolderByFolderObject(Folder folder)
	{
		EntityManager em = null;
		EmAnalyticsFolder folderObj = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			if (folder.getParentId() == null) {
				folderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getRootFolderByName")
						.setParameter("foldername", folder.getName()).getSingleResult();

			}
			else {
				if (folder.getParentId() != null) {
					EmAnalyticsFolder parentFolderObj = EmAnalyticsObjectUtil.getFolderById(folder.getParentId().longValue(), em);
					if (parentFolderObj == null) {
						return folderObj;
					}
					String parentFolder = "parentFolder";
					folderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getSubFolderByName")
							.setParameter(parentFolder, parentFolderObj).setParameter("foldername", folder.getName())
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
		folderObj.setDescription(folder.getDescription());
		folderObj.setName(folder.getName());
		folderObj.setUiHidden(new BigDecimal(0));
		folderObj.setSystemFolder(new BigDecimal(0));
		folderObj.setDeleted(0);
		if (folder.getParentId() != null) {
			EmAnalyticsFolder parentFolderObj = EmAnalyticsObjectUtil.getFolderById(folder.getParentId(), em);
			if (parentFolderObj == null) {
				throw new EMAnalyticsFwkException("Parent folder with id " + folder.getParentId() + " does not exist",
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
			//folderObj.setLastModifiedBy(EMSecurityContext.getSecurityContext().getEMUser()); // fix me to do
			folderObj.setSystemFolder(folder.isSystemFolder() == true ? new BigDecimal(1) : new BigDecimal(0));
			folderObj.setUiHidden(folder.isUiHidden() == true ? new BigDecimal(1) : new BigDecimal(0));
			folderObj.setDeleted(0);
			if (folder.getParentId() != null) {
				EmAnalyticsFolder parentFolderObj = EmAnalyticsObjectUtil.getFolderById(folder.getParentId(), em);
				if (parentFolderObj == null) {
					throw new EMAnalyticsFwkException("Parent folder with id " + folder.getParentId() + " does not exist",
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

		//TODO FIX this : Abhinav 
		//get logged in user ,      // EMSecurityContext.getSecurityContext().getEMUser() 
		searchEntity.setOwner("SYSMAN");
		searchEntity.setLastModifiedBy("SYSMAN");
		searchEntity.setSystemSearch(new java.math.BigDecimal(0));
		searchEntity.setIsLocked(search.isLocked() ? new java.math.BigDecimal(1) : new java.math.BigDecimal(0));
		searchEntity.setMetadataClob(search.getMetadata());
		searchEntity.setSearchDisplayStr(search.getQueryStr());
		searchEntity.setUiHidden(new java.math.BigDecimal(search.isUiHidden() ? 1 : 0));
		searchEntity.setDeleted(0);
		List<SearchParameter> params = search.getParameters();
		if (params != null && params.size() != 0) {
			for (SearchParameter param : params) {
				EmAnalyticsSearchParam searchParamEntity = new EmAnalyticsSearchParam();
				searchParamEntity.setEmAnalyticsSearch(searchEntity);

				EmAnalyticsSearchParamPK id = new EmAnalyticsSearchParamPK();
				id.setName(param.getName());
				id.setSearchId(searchEntity.getId());

				searchParamEntity.setId(id);
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

		searchEntity.setAccessDate(new Timestamp(new Date().getTime()));
		;
		searchEntity.setObjectId(searchEntity.getId());
		searchEntity.setAccessedBy("SYSMAN");
		searchEntity.setObjectType(2);
		return searchEntity;
	}

	public static EmAnalyticsSearch getEmAnalyticsSearchForEdit(Search search, EntityManager em) throws EMAnalyticsFwkException
	{
		EmAnalyticsSearch searchEntity = EmAnalyticsObjectUtil.getSearchById(search.getId(), em);
		em.refresh(searchEntity);
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

		//TODO FIX this : Abhinav 
		//get logged in user ,      // EMSecurityContext.getSecurityContext().getEMUser() 
		searchEntity.setOwner("SYSMAN");
		searchEntity.setLastModifiedBy("SYSMAN");
		searchEntity.setSystemSearch(new java.math.BigDecimal(0));
		searchEntity.setIsLocked(search.isLocked() ? new java.math.BigDecimal(1) : new java.math.BigDecimal(0));
		searchEntity.setMetadataClob(search.getMetadata());
		searchEntity.setSearchDisplayStr(search.getQueryStr());
		searchEntity.setUiHidden(new java.math.BigDecimal(search.isUiHidden() ? 1 : 0));
		searchEntity.setDeleted(0);
		List<SearchParameter> params = search.getParameters();
		// Params handling !!
		Set<EmAnalyticsSearchParam> existingParams = Collections.synchronizedSet(searchEntity.getEmAnalyticsSearchParams());
		Map<EmAnalyticsSearchParamPK, EmAnalyticsSearchParam> newParams = new HashMap<EmAnalyticsSearchParamPK, EmAnalyticsSearchParam>();
		if (params != null) {
			for (SearchParameter param : params) {
				EmAnalyticsSearchParam newSearchParam = new EmAnalyticsSearchParam();
				EmAnalyticsSearchParamPK newPK = new EmAnalyticsSearchParamPK();
				newPK.setSearchId(search.getId());
				newPK.setName(param.getName());

				newSearchParam.setId(newPK);
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

		Iterator<EmAnalyticsSearchParam> it = existingParams.iterator();
		while (it.hasNext()) {
			EmAnalyticsSearchParam searchParam = it.next();
			if (!newParams.containsKey(searchParam.getId()) || newParams.containsKey(searchParam.getId())
					&& !newParams.containsValue(searchParam)) {
				it.remove();
			}
		}
		existingParams.addAll(newParams.values());

		searchEntity.setAccessDate(new Timestamp(new Date().getTime()));
		;
		searchEntity.setObjectId(searchEntity.getId());
		searchEntity.setAccessedBy("SYSMAN");
		searchEntity.setObjectType(2);
		return searchEntity;
	}

	public static EmAnalyticsFolder getFolderById(long id, EntityManager em)
	{

		EmAnalyticsFolder folderObj = null;
		Object op = null;

		try {

			folderObj = em.find(EmAnalyticsFolder.class, id);
			if (folderObj != null && folderObj.getDeleted() == 0) {

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
		Object op = null;

		try {

			folderObj = em.find(EmAnalyticsFolder.class, id);
			if (folderObj != null) {

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

	public static EmAnalyticsSearch getSearchById(long id, EntityManager em)
	{

		EmAnalyticsSearch searchObj = null;
		try {

			searchObj = em.find(EmAnalyticsSearch.class, id);
			if (searchObj != null) {
				if (searchObj.getDeleted() == 0) {

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

	public static EmAnalyticsSearch getSearchByIdForDelete(long id, EntityManager em)
	{

		EmAnalyticsSearch searchObj = null;
		try {

			searchObj = em.find(EmAnalyticsSearch.class, id);
			if (searchObj != null) {
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

}
