package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.util.List;

/**
 * TODO: all java-docs need to be defined once MRS gets finalized in subsequent txn
 * 
 * @version $Header: emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/emSDK/core/emanalytics/api/Category.java
 *          /st_emgc_pt-13.1mstr/2 2014/02/03 02:50:59 saurgarg Exp $
 * @author saurgarg
 * @since release specific (what release of product did this appear in)
 */
public interface Category
{
	/**
	 * Parameter to define the task-flow that is associated with editting of entities belonging to this category.
	 */
	public static final String CATEGORY_PARAM_EDIT_TASKFLOW = "CATEGORY_PARAM_EDIT_TASKFLOW";
	/**
	 * Parameter to define the task-flow that is associated with create-like of entities belonging to this category.
	 */
	public static final String CATEGORY_PARAM_CREATELIKE_TASKFLOW = "CATEGORY_PARAM_CREATELIKE_TASKFLOW";
	/**
	 * Parameter to define the task-flow that is associated with viewing of entities belonging to this category.
	 */
	public static final String CATEGORY_PARAM_VIEW_TASKFLOW = "CATEGORY_PARAM_VIEW_TASKFLOW";

	/**
	 * Returns the identifier of the category.
	 * 
	 * @return identifier
	 */
	public Integer getId();

	/**
	 * Returns the internal name of the category.
	 * 
	 * @return internal name
	 */
	public String getName();

	/**
	 * Returns the (localized based on current context locale) description of the category.
	 * 
	 * @return localized description
	 */
	public String getDescription();

	/**
	 * Returns the default folder id associated with the category (if any, <code>null</code> otherwise).
	 * 
	 * @return default folder id associated with the category
	 */
	public Integer getDefaultFolderId();

	/**
	 * Returns the list of parameters for the category.
	 * 
	 * @return category parameters
	 */
	public List<Parameter> getParameters();

	public void setParameters(List<Parameter> param);

	/**
	 * Sets the name for the category.
	 * 
	 * @param name
	 *            name
	 */
	public void setName(String name);

	/**
	 * Sets the description for the category.
	 * 
	 * @param description
	 *            description
	 */
	public void setDescription(String description);

	/**
	 * set the default folder id associated with the category (if any, <code>null</code> otherwise).
	 * 
	 * @return default folder id associated with the category
	 */
	public void setDefaultFolderId(Integer id);

}
