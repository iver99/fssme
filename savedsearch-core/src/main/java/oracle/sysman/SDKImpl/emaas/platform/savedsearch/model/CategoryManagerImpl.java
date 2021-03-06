package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

/* $Header: emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/sdkImpl/core/emanalytics/impl/CategoryManagerImpl.java /st_emgc_pt-13.1mstr/1 2014/02/03 02:50:59 saurgarg Exp $ */

/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

/*
 DESCRIPTION
 <short description of component this file declares/defines>

 PRIVATE CLASSES
 <list of private classes defined - with one-line descriptions>

 NOTES
 <other useful comments, qualifications, etc.>

 MODIFIED    (MM/DD/YY)
 saurgarg    01/27/14 - Creation
 */

/**
 *  @version $Header: emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/sdkImpl/core/emanalytics/impl/CategoryManagerImpl.java /st_emgc_pt-13.1mstr/1 2014/02/03 02:50:59 saurgarg Exp $
 *  @author  saurgarg
 *  @since   release specific (what release of product did this appear in)
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.IdGenerator;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.QueryParameterConstant;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.ZDTContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EmAnalyticsProcessingException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.RequestContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.RequestContext.RequestType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategoryParam;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CategoryManagerImpl extends CategoryManager
{
	// Logger
	private static final Logger LOGGER = LogManager.getLogger(CategoryManagerImpl.class);

	private static final CategoryManagerImpl INSTANCE = new CategoryManagerImpl();

	private static final String DB_DEFAULT_VALUE = "0";

	public static Category createCategoryObject(EmAnalyticsCategory category, Category categoryObj) throws Exception
	{
		CategoryImpl rtnObj = null;
		try {

			if (categoryObj != null) {
				// populate the current object
				rtnObj = (CategoryImpl) categoryObj;
			}
			else {
				rtnObj = new CategoryImpl();
			}
			rtnObj.setId(category.getCategoryId());
			if (category.getEmAnalyticsFolder() != null) {
				BigInteger id = category.getEmAnalyticsFolder().getFolderId();
				rtnObj.setDefaultFolderId(BigInteger.ZERO.equals(id) ? null : id);
			}
			else {
				rtnObj.setDefaultFolderId(null);
			}
			// TODO : Abhinav Handle the internationalization via MGMT_MESSAGES
			// handling name here
			String nlsid = category.getNameNlsid();
			String subsystem = category.getNameSubsystem();
			if (nlsid == null || nlsid.trim().length() == 0 || subsystem == null || subsystem.trim().length() == 0) {
				rtnObj.setName(category.getName());
			}
			else {
				// here the code should come !! get localized stuff from
				// MGMT_MESSAGES
				rtnObj.setName(category.getName());
			}

			nlsid = category.getDescriptionNlsid();
			subsystem = category.getDescriptionSubsystem();
			if (nlsid == null || nlsid.trim().length() == 0 || subsystem == null || subsystem.trim().length() == 0) {
				rtnObj.setDescription(category.getDescription());
			}
			else {
				// here the code should come !! get localized stuff from
				// MGMT_MESSAGES
				rtnObj.setDescription(category.getDescription());
			}

			rtnObj.setOwner(category.getOwner());
			rtnObj.setCreatedOn(category.getCreationDate());
			rtnObj.setProviderName(category.getProviderName());
			rtnObj.setProviderVersion(category.getProviderVersion());
			rtnObj.setProviderDiscovery(category.getProviderDiscovery());
			rtnObj.setProviderAssetRoot(category.getProviderAssetRoot());

			// handle params

			Set<EmAnalyticsCategoryParam> params = category.getEmAnalyticsCategoryParams();
			List<Parameter> categoryParams = new ArrayList<Parameter>();
			if (params != null && !params.isEmpty()) {
				for (EmAnalyticsCategoryParam paramObj : params) {
					Parameter param = new Parameter();
					param.setName(paramObj.getName());
					param.setType(ParameterType.STRING);
					param.setValue(paramObj.getValue());
					categoryParams.add(param);
				}

			}
			if (!CategoryManagerImpl.containsDashboardIneligible(categoryParams)) {
				LOGGER.debug("Try to Get DASHBOARD_INELIGIBLE from category");
				if (category.getDASHBOARDINELIGIBLE() != null && !DB_DEFAULT_VALUE.equals(category.getDASHBOARDINELIGIBLE())) {
					LOGGER.debug("Getting DASHBOARD_INELIGIBLE from category");
					Parameter param = new Parameter();
					param.setName("DASHBOARD_INELIGIBLE");
					param.setType(ParameterType.STRING);
					param.setValue(category.getDASHBOARDINELIGIBLE());
					categoryParams.add(param);
				}
			}
			if (!categoryParams.isEmpty()) {
				LOGGER.debug("Category's parameter number is > 0!");
				rtnObj.setParameters(categoryParams);
			}

			return rtnObj;
		}
		catch (Exception e) {
			LOGGER.error("Error while creating the category object", e);
			throw e;
		}
	}

	public static CategoryManager getInstance()
	{
		return INSTANCE;
	}

	//check if param list contains DASHBOARD_INELIGIBLE
	private static boolean containsDashboardIneligible(List<Parameter> params)
	{
		for (Parameter param : params) {
			if ("DASHBOARD_INELIGIBLE".equals(param.getName())) {
				LOGGER.debug("List<Parameter> contains DASHBOARD_INELIGIBLE!");
				return true;
			}
		}
		LOGGER.debug("List<Parameter> did not contains DASHBOARD_INELIGIBLE!");
		return false;
	}

	/**
	 * Private Constructor
	 */
	private CategoryManagerImpl()
	{

	}

	@Override
	public Category createNewCategory()
	{

		return new CategoryImpl();
	}

	@Override
	public void deleteCategory(BigInteger categoryId, boolean permanently) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		EmAnalyticsCategory categoryObj = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			if (permanently) {
				categoryObj = EmAnalyticsObjectUtil.getCategoryByIdForDelete(categoryId, em);
			}
			else {
				categoryObj = EmAnalyticsObjectUtil.getCategoryById(categoryId, em);
			}
			if (categoryObj == null) {
				LOGGER.error("Category object by Id: " + categoryId + " " + "does not exist");
				throw new EMAnalyticsFwkException("Category object by Id: " + categoryId + " " + "does not exist",
						EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_ID_NOT_EXIST, null);
			}
			//boolean bResult = EmAnalyticsObjectUtil.canDeleteCategory(categoryId, em);
			categoryObj.setDeleted(categoryId);
			categoryObj.setLastModificationDate(DateUtil.getGatewayTime());
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.setProperty("permanent", permanently);
			if (permanently) {
				em.remove(categoryObj);
			}
			else {
				em.merge(categoryObj);
			}
			em.getTransaction().commit();
		}
		catch (EMAnalyticsFwkException eme) {
			LOGGER.error(eme.getMessage());
			throw eme;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processCategoryPersistantException(e, -1, null);
			LOGGER.error("Error while deleting the category with id:" + categoryId, e);
			throw new EMAnalyticsFwkException("Error occurred while deleting the category",
					EMAnalyticsFwkException.ERR_DELETE_CATEGORY, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}
		}

	}

	@Override
	public Category editCategory(Category category) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			EmAnalyticsCategory categoryEntity = EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(category, em);
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.merge(categoryEntity);
			em.getTransaction().commit();
			return CategoryManagerImpl.createCategoryObject(categoryEntity, null);
		}
		catch (EMAnalyticsFwkException eme) {
			LOGGER.error("Category with name " + category.getName() + " was saved but could not bve retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			EmAnalyticsProcessingException.processCategoryPersistantException(dmlce, category.getDefaultFolderId() == null ? -1
					: category.getDefaultFolderId().longValue(), category.getName());
			LOGGER.error("Error while updating the category: " + category.getName(), dmlce);
			throw new EMAnalyticsFwkException("Error while updating the category: " + category.getName(),
					EMAnalyticsFwkException.ERR_UPDATE_CATEGORY, null, dmlce);

		}
		catch (Exception e) {
			LOGGER.error("Error while updating the category name: ", e);
			throw new EMAnalyticsFwkException("Error while updating the category: " + category.getName(),
					EMAnalyticsFwkException.ERR_UPDATE_CATEGORY, null, e);
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<Category> getAllCategories() throws EMAnalyticsFwkException
	{
		List<Category> categories = null;
		EntityManager em = null;
		try {

			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			@SuppressWarnings("unchecked")
			List<EmAnalyticsCategory> emcategories = em.createNamedQuery("Category.getAllCategory")
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getResultList();
			if (categories == null) {
				categories = new ArrayList<Category>();
			}
			for (EmAnalyticsCategory categoriesObj : emcategories) {
				Category category = CategoryManagerImpl.createCategoryObject(categoriesObj, null);
				categories.add(category);
			}

		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processCategoryPersistantException(e, -1, null);
			LOGGER.error("Error while retrieving all the categories", e);
			throw new EMAnalyticsFwkException("Error while retrieving all the categories",
					EMAnalyticsFwkException.ERR_GET_CATEGORIES, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}
		}
		
		return categories;
	}

	@Override
	public Category getCategory(BigInteger categoryId) throws EMAnalyticsFwkException
	{
		Category category = null;
		EntityManager em = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			EmAnalyticsCategory categoryObj = EmAnalyticsObjectUtil.getCategoryById(categoryId, em);
			if (categoryObj != null) {
				em.refresh(categoryObj);
				category = CategoryManagerImpl.createCategoryObject(categoryObj, null);
			}
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processCategoryPersistantException(e, -1, null);
			LOGGER.error("Error while getting the category object by ID: " + categoryId, e);
			throw new EMAnalyticsFwkException("category object by ID: " + categoryId + " does not exist",
					EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_ID, new Object[] { categoryId }, e);

		}
		finally {
			if (em != null) {
				em.close();
			}
		}
		if (category == null) {
			LOGGER.error("Category identified by ID: " + categoryId + " does not exist");
			throw new EMAnalyticsFwkException("Category object by ID: " + categoryId + " does not exist",
					EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_ID_NOT_EXIST, new Object[] { categoryId });
		}

		return category;
	}

	@Override
	public Category getCategory(String categoryName) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		Category category = null;
		try {

			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());

			EmAnalyticsCategory categoryObj = null;
			// Internal request with tenant only
			if (RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())) {
				categoryObj = (EmAnalyticsCategory) em.createNamedQuery("Category.getCategoryByNameForTenant")
						.setParameter("categoryName", categoryName).getSingleResult();
			}
			// External or internal request with tenant and user
			else {
				categoryObj = (EmAnalyticsCategory) em.createNamedQuery("Category.getCategoryByName")
						.setParameter("categoryName", categoryName)
						.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
						.getSingleResult();
			}
			if (categoryObj != null) {
				category = CategoryManagerImpl.createCategoryObject(categoryObj, null);
			}
		}
		catch (NonUniqueResultException nure) {
			LOGGER.error("Error while getting the category object by Name ", nure);
			throw new EMAnalyticsFwkException("Multiple category objects with same name: \'" + categoryName + "\' exist.",
					EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_NAME, new Object[] { categoryName }, nure);
		}
		catch (Exception e) {
			LOGGER.error("Error while getting the category object by Name ", e);
			throw new EMAnalyticsFwkException("Category object by Name: " + categoryName + " does not exist",
					EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_NAME, new Object[] { categoryName }, e);
		}
		finally {
			if (em != null) {
				em.close();
			}

		}
		if (category == null) {
			LOGGER.error("Category object by name does not exist");
			throw new EMAnalyticsFwkException("Category object by name: " + categoryName + " does not exist",
					EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_NAME, new Object[] { categoryName });
		}
		return category;
	}

	@Override
	public Category saveCategory(Category category) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		if(category != null && category.getId() == null) {
			category.setId(IdGenerator.getIntUUID(ZDTContext.getRequestId()));
		}
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			EmAnalyticsCategory categoryObj = EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd(category, em);
			em.persist(categoryObj);
			em.getTransaction().commit();
			return CategoryManagerImpl.createCategoryObject(categoryObj, null);
		}
		catch (EMAnalyticsFwkException eme) {
			LOGGER.error("category with name " + category.getName() + " was saved but could not be retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			EmAnalyticsProcessingException.processCategoryPersistantException(dmlce, category.getDefaultFolderId() == null ? -1
					: category.getDefaultFolderId().longValue(), category.getName());

			LOGGER.error("Error while saving the category: " + category.getName(), dmlce);
			throw new EMAnalyticsFwkException("Error while saving the category: " + category.getName(),
					EMAnalyticsFwkException.ERR_CREATE_CATEGORY, null, dmlce);

		}
		catch (Exception e) {
			LOGGER.error("Error while saving the category: " + category.getName(), e);
			throw new EMAnalyticsFwkException("Error while saving the category: " + category.getName(),
					EMAnalyticsFwkException.ERR_CREATE_CATEGORY, null, e);
		}
		finally {
			if (em != null) {
				em.close();
			}

		}

	}

	public List<Category> saveMultipleCategories(List<ImportCategoryImpl> categories)
	{
		boolean bCommit = true;
		EntityManager em = null;
		Category category = null;
		List<Category> importedList = new ArrayList<Category>();
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());

			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			for (ImportCategoryImpl categorytmp : categories) {
				try {
					category = categorytmp.getCategoryDetails();
					EmAnalyticsCategory categoryEntity = EmAnalyticsObjectUtil.getCategoryById(category.getId(), em);
					if (categoryEntity != null) {
						EmAnalyticsCategory emCategory = EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(category, em);
						em.merge(emCategory);
						importedList.add(CategoryManagerImpl.createCategoryObject(emCategory, null));
					}
					else {
						EmAnalyticsCategory categoryObj = null;
						try {
							categoryObj = (EmAnalyticsCategory) em.createNamedQuery("Category.getCategoryByName")
									.setParameter("categoryName", category.getName())
									.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
									.getSingleResult();
							categorytmp.setId(categoryObj.getCategoryId());
							importedList.add(CategoryManagerImpl.createCategoryObject(categoryObj, null));
						}
						catch (NoResultException e) {
							categoryObj = null;
						}
						if (categoryObj == null) {
							if (categorytmp instanceof ImportCategoryImpl) {
								Object obj = categorytmp.getFolderDetails();
								if (obj != null) {

									if (obj instanceof Folder) {
										Folder folder = (Folder) obj;
										if (folder.getParentId() == null || BigInteger.ZERO.equals(folder.getParentId())) {
											folder.setParentId(BigInteger.ONE);
										}
										EmAnalyticsFolder tmpfld = EmAnalyticsObjectUtil.getFolderById(folder.getParentId(), em);

										if (tmpfld == null) {
											EmAnalyticsFolder fld = EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd(folder, em);
											em.persist(fld);
											category.setDefaultFolderId(fld.getFolderId());
										}
										else {
											category.setDefaultFolderId(tmpfld.getFolderId());
										}
									}
									else if (obj instanceof BigInteger) {
										EmAnalyticsFolder tmpfld = EmAnalyticsObjectUtil.getFolderById((BigInteger) obj, em);
										if (tmpfld != null) {
											category.setDefaultFolderId((BigInteger) obj);
										}
										else {
											importedList.add(category);
											continue;
										}
									}
								}
							}
							EmAnalyticsCategory emCategory = EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd(category, em);
							em.persist(emCategory);
							categorytmp.setId(emCategory.getCategoryId());
							importedList.add(CategoryManagerImpl.createCategoryObject(emCategory, null));
						}
					}
				}
				catch (PersistenceException eme) {
					LOGGER.error("Error while importing the category: " + category.getName(), eme);
					importedList.clear();
					bCommit = false;
					break;
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
			LOGGER.error("Error in saveMultipleCategory", e);
			// throw e;
		}
		finally {
			if (em != null) {
				em.close();
			}

		}
		return importedList;
	}
}
