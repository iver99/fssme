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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategoryParam;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;

import org.apache.log4j.Logger;

public class CategoryManagerImpl extends CategoryManager
{
	// Logger
	private static final Logger _logger = Logger.getLogger(CategoryManagerImpl.class);

	private static final CategoryManagerImpl _instance = new CategoryManagerImpl();

	public static CategoryManager getInstance()
	{
		return _instance;
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
	public void deleteCategory(long categoryId, boolean permanently) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		EmAnalyticsCategory categoryObj = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			if (permanently) {
				categoryObj = EmAnalyticsObjectUtil.getCategoryByIdForDelete(categoryId, em);
			}
			else {
				categoryObj = EmAnalyticsObjectUtil.getCategoryById(categoryId, em);
			}
			if (categoryObj == null) {
				_logger.error("Category object by Id: " + categoryId + " " + "does not exist");
				throw new EMAnalyticsFwkException("Category object by Id: " + categoryId + " " + "does not exist",
						EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_ID_NOT_EXIST, null);
			}
			boolean bResult = EmAnalyticsObjectUtil.canDeleteCategory(categoryId, em);
			categoryObj.setDeleted(categoryId);
			em.getTransaction().begin();
			if (permanently) {
				em.remove(categoryObj);
			}
			else {
				em.merge(categoryObj);
			}
			em.getTransaction().commit();
		}
		catch (EMAnalyticsFwkException eme) {
			_logger.error("Category object by Id: " + categoryId + " " + "does not exist");
			throw eme;
		}
		catch (Exception e) {
			if (e.getCause() != null && e.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else if (e.getCause() != null && e.getCause().getMessage().contains("ANALYTICS_SEARCH_FK1")) {
				_logger.error("Error while deleting the category" + e.getMessage(), e);
				throw new EMAnalyticsFwkException("Error while deleting the category as it has associated searches",
						EMAnalyticsFwkException.ERR_DELETE_CATEGORY, null);
			}
			else {
				_logger.error("Error while deleting the category with id:" + categoryId, e);

				throw new EMAnalyticsFwkException("Error occurred while deleting the category",
						EMAnalyticsFwkException.ERR_DELETE_CATEGORY, null, e);
			}
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
			EntityManagerFactory emf;
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			EmAnalyticsCategory categoryEntity = EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(category, em);
			em.getTransaction().begin();
			em.merge(categoryEntity);
			em.getTransaction().commit();
			return createCategoryObject(categoryEntity, null);
		}
		catch (EMAnalyticsFwkException eme) {
			_logger.error("Category with name " + category.getName() + " was saved but could not bve retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			if (dmlce.getCause().getMessage().contains("ANALYICS_CATEGORY_U01")) {
				throw new EMAnalyticsFwkException("Duplicate category name " + category.getName(),
						EMAnalyticsFwkException.ERR_DUPLICATE_CATEGORY_NAME, new Object[] { category.getName() });
			}
			else if (dmlce.getCause().getMessage().contains("ANALYTICS_CATEGORY_FK1")) {
				throw new EMAnalyticsFwkException("Parent folder with id " + category.getDefaultFolderId() + " missing: "
						+ category.getName(), EMAnalyticsFwkException.ERR_CATEGORY_INVALID_FOLDER, null);
			}
			else if (dmlce.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + dmlce.getMessage(), dmlce);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else {
				_logger.error("Error while updating the category: " + category.getName(), dmlce);
				throw new EMAnalyticsFwkException("Error while updating the category: " + category.getName(),
						EMAnalyticsFwkException.ERR_UPDATE_CATEGORY, null, dmlce);
			}
		}
		catch (Exception e) {
			_logger.error("Error while updating the category: " + category.getName(), e);
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
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			EntityManager em = emf.createEntityManager();

			List<EmAnalyticsCategory> emcategories = em.createNamedQuery("Category.getAllCategory").getResultList();
			for (EmAnalyticsCategory categoriesObj : emcategories) {
				if (categories == null) {
					categories = new ArrayList<Category>();
				}
				Category category = createCategoryObject(categoriesObj, null);
				categories.add(category);
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
				_logger.error("Error while retrieving all the categories", e);
				throw new EMAnalyticsFwkException("Error while retrieving all the categories",
						EMAnalyticsFwkException.ERR_GET_CATEGORIES, null, e);
			}
		}
		return categories;
	}

	@Override
	public Category getCategory(long categoryId) throws EMAnalyticsFwkException
	{
		Category category = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			EntityManager em = emf.createEntityManager();
			EmAnalyticsCategory categoryObj = EmAnalyticsObjectUtil.getCategoryById(categoryId, em);
			if (categoryObj != null) {
				em.refresh(categoryObj);
				category = createCategoryObject(categoryObj, null);
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
				_logger.error("Error while getting the category object by ID: " + categoryId, e);
				throw new EMAnalyticsFwkException("category object by ID: " + categoryId + " does not exist",
						EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_ID, new Object[] { categoryId }, e);
			}
		}
		if (category == null) {
			_logger.error("Category identified by ID: " + categoryId + " does not exist");
			throw new EMAnalyticsFwkException("Category object by ID: " + categoryId + " does not exist",
					EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_ID_NOT_EXIST, new Object[] { categoryId });
		}
		return category;
	}

	@Override
	public Category getCategory(String categoryName) throws EMAnalyticsFwkException
	{

		Category category = null;
		try {
			EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			EntityManager em = emf.createEntityManager();

			EmAnalyticsCategory categoryObj = (EmAnalyticsCategory) em.createNamedQuery("Category.getCategoryByName")
					.setParameter("categoryName", categoryName).getSingleResult();

			if (categoryObj != null) {
				category = createCategoryObject(categoryObj, null);
			}
		}
		catch (Exception e) {
			_logger.error("Error while getting the category object by Name: " + categoryName, e);
			e.printStackTrace();
			throw new EMAnalyticsFwkException("Category object by Name: " + categoryName + " does not exist",
					EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_NAME, new Object[] { categoryName }, e);
		}
		if (category == null) {
			_logger.error("Category object by name: " + categoryName + " does not exist");
			throw new EMAnalyticsFwkException("Category object by name: " + categoryName + " does not exist",
					EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_NAME, new Object[] { categoryName });
		}
		return category;
	}

	@Override
	public Category saveCategory(Category category) throws EMAnalyticsFwkException
	{
		EntityManager em = null;

		try {
			EntityManagerFactory emf;
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			em.getTransaction().begin();
			EmAnalyticsCategory categoryObj = EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd(category, em);
			em.persist(categoryObj);
			em.getTransaction().commit();
			return createCategoryObject(categoryObj, null);
		}
		catch (EMAnalyticsFwkException eme) {
			_logger.error("category with name " + category.getName() + " was saved but could not be retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			if (dmlce.getCause().getMessage().contains("ANALYICS_CATEGORY_U01")) {
				throw new EMAnalyticsFwkException("Category name " + category.getName() + " already exist",
						EMAnalyticsFwkException.ERR_DUPLICATE_CATEGORY_NAME, new Object[] { category.getName() });
			}
			else if (dmlce.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + dmlce.getMessage(), dmlce);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else if (dmlce.getCause().getMessage().contains("ANALYTICS_CATEGORY_FK1")) {
				throw new EMAnalyticsFwkException("Default folder with id " + category.getDefaultFolderId() + " missing: "
						+ category.getName(), EMAnalyticsFwkException.ERR_CATEGORY_INVALID_FOLDER, null);
			}
			else {
				_logger.error("Error while saving the category: " + category.getName(), dmlce);
				throw new EMAnalyticsFwkException("Error while saving the category: " + category.getName(),
						EMAnalyticsFwkException.ERR_CREATE_CATEGORY, null, dmlce);
			}
		}
		catch (Exception e) {
			_logger.error("Error while saving the category: " + category.getName(), e);
			throw new EMAnalyticsFwkException("Error while saving the category: " + category.getName(),
					EMAnalyticsFwkException.ERR_CREATE_CATEGORY, null, e);
		}
		finally {
			if (em != null) {
				em.close();
			}
		}

	}

	public List<ImportCategoryImpl> saveMultipleCategories(List<ImportCategoryImpl> categories)
	{
		int iCount = 0;
		boolean bCommit = true;
		boolean bResult = false;
		EntityManagerFactory emf = null;
		EntityManager em = null;
		List<ImportCategoryImpl> importedList = new ArrayList<ImportCategoryImpl>();
		try {
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();

			em.getTransaction().begin();
			for (Category category : categories) {
				try {
					if (category.getId() != null && category.getId() > 0) {
						EmAnalyticsCategory emCategory = EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(category, em);
						em.merge(emCategory);
						importedList.add((ImportCategoryImpl) createCategoryObject(emCategory, category));
						iCount++;
					}
					else {
						EmAnalyticsCategory categoryObj = null;
						try {
							categoryObj = (EmAnalyticsCategory) em.createNamedQuery("Category.getCategoryByName")
									.setParameter("categoryName", category.getName()).getSingleResult();
							importedList.add((ImportCategoryImpl) createCategoryObject(categoryObj, category));
						}
						catch (NoResultException e) {
							categoryObj = null;
						}
						if (categoryObj == null) {
							if (category instanceof ImportCategoryImpl) {
								Object obj = ((ImportCategoryImpl) category).getFolder();
								if (obj != null) {

									if (obj instanceof Folder) {
										Folder folder = (Folder) obj;
										if (folder.getParentId() == null || folder.getParentId() == 0) {
											folder.setParentId(1);
										}
										EmAnalyticsFolder tmpfld = EmAnalyticsObjectUtil.getFolderById(folder.getParentId()
												.longValue(), em);

										if (tmpfld == null) {
											EmAnalyticsFolder fld = EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd(folder, em);
											em.persist(fld);
											category.setDefaultFolderId((int) fld.getFolderId());
										}
										else {
											category.setDefaultFolderId((int) tmpfld.getFolderId());
										}
									}
									else if (obj instanceof Integer) {
										EmAnalyticsFolder tmpfld = EmAnalyticsObjectUtil.getFolderById(
												((Integer) obj).longValue(), em);
										if (tmpfld != null) {
											category.setDefaultFolderId((Integer) obj);
										}
										else {
											importedList.add((ImportCategoryImpl) category);
											continue;
										}
									}
								}
							}
							EmAnalyticsCategory emCategory = EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd(category, em);
							em.persist(emCategory);
							importedList.add((ImportCategoryImpl) createCategoryObject(emCategory, category));
							iCount++;
						}
					}
				}
				catch (PersistenceException eme) {
					_logger.error("Error while importing the category: " + category.getName(), eme);
					importedList.clear();
					bCommit = false;
					iCount = 0;
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
			e.printStackTrace();
			_logger.error("Error in saveMultipleCategory", e);
			// throw e;
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
		return importedList;
	}

	private Category createCategoryObject(EmAnalyticsCategory category, Category categoryObj) throws Exception
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
			rtnObj.setId((int) category.getCategoryId());
			if (category.getEmAnalyticsFolder() != null) {
				Long id = category.getEmAnalyticsFolder().getFolderId();
				rtnObj.setDefaultFolderId(id == 0 ? null : (int) id.intValue());
			}
			else {
				rtnObj.setDefaultFolderId(0);
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

			// handle params

			Set<EmAnalyticsCategoryParam> params = category.getEmAnalyticsCategoryParams();
			if (params != null && params.size() > 0) {
				List<Parameter> categoryParams = new ArrayList<Parameter>();
				for (EmAnalyticsCategoryParam paramObj : params) {
					Parameter param = new Parameter();
					param.setName(paramObj.getId().getName());
					param.setType(ParameterType.STRING);
					param.setValue(paramObj.getValue());
					categoryParams.add(param);
				}
				rtnObj.setParameters(categoryParams);
			}

			return rtnObj;
		}
		catch (Exception e) {
			_logger.error("Error while creating the category object", e);
			throw e;
		}
	}

}
