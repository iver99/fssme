package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

/**
 *  @version $Header: emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/emSDK/core/emanalytics/api/CategoryManager.java /st_emgc_pt-13.1mstr/1 2014/02/03 02:50:59 saurgarg Exp $
 *  @author  saurgarg
 *  @since   release specific (what release of product did this appear in)
 */

import java.math.BigInteger;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

public abstract class CategoryManager
{
	/**
	 * Returns an instance of the manager.
	 * 
	 * @return an instance of the manager
	 */
	public static CategoryManager getInstance()
	{
		return CategoryManagerImpl.getInstance();
	}

	/**
	 * Instantiates a new Category object (empty).
	 * 
	 * @return new Category object
	 */
	public abstract Category createNewCategory();

	/**
	 * @param categoryId
	 * @param permanently
	 * @throws EMAnalyticsFwkException
	 */
	public abstract void deleteCategory(BigInteger categoryId, boolean permanently) throws EMAnalyticsFwkException;

	/**
	 * Edits an existing Category entity in the analytics sub-system.
	 * 
	 * @param Category
	 *            to be modified
	 * @return re-loaded Category object with generated dates
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Category editCategory(Category category) throws EMAnalyticsFwkException;

	/**
	 * Returns a list of all the categories registered in the system.
	 * 
	 * @return list of all categories
	 * @throws EMAnalyticsFwkException
	 */
	public abstract List<Category> getAllCategories() throws EMAnalyticsFwkException;

	/**
	 * Returns the category identified by its unique identifier.
	 * 
	 * @param categoryId
	 *            unique identifier
	 * @return category
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Category getCategory(BigInteger categoryId) throws EMAnalyticsFwkException;

	/**
	 * Returns the category identified by its internal name.
	 * 
	 * @param categoryName
	 *            internal name of category
	 * @return category
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Category getCategory(String categoryName) throws EMAnalyticsFwkException;

	/**
	 * Saves a completely specified Category entity in the analytics sub-system.
	 * 
	 * @param Category
	 *            Category to be saved
	 * @return re-loaded Category object with generated ids and dates
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Category saveCategory(Category category) throws EMAnalyticsFwkException;

}
