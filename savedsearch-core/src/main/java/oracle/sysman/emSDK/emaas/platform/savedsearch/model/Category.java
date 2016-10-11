package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.math.BigInteger;
import java.util.Date;
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
	 * Get creation date of this category
	 *
	 * @return
	 */
	public Date getCreatedOn();

	/**
	 * Returns the default folder id associated with the category (if any, <code>null</code> otherwise).
	 *
	 * @return default folder id associated with the category
	 */
	public BigInteger getDefaultFolderId();

	/**
	 * Returns the (localized based on current context locale) description of the category.
	 *
	 * @return localized description
	 */
	public String getDescription();

	/**
	 * Returns the identifier of the category.
	 *
	 * @return identifier
	 */
	public BigInteger getId();

	/**
	 * Returns the internal name of the category.
	 *
	 * @return internal name
	 */
	public String getName();

	/**
	 * Return owner of this category
	 */
	public String getOwner();

	/**
	 * Returns the list of parameters for the category.
	 *
	 * @return category parameters
	 */
	public List<Parameter> getParameters();

	/**
	 * Return provider's asset root reference name of this category
	 */
	public String getProviderAssetRoot();

	/**
	 * Return provider's widget discovery reference name of this category
	 */
	public String getProviderDiscovery();

	/**
	 * Return provider name of this category
	 */
	public String getProviderName();

	/**
	 * Return provider version of this category
	 */
	public String getProviderVersion();

	/**
	 * Set creation date of this category
	 *
	 * @param date
	 */
	public void setCreatedOn(Date date);

	/**
	 * set the default folder id associated with the category (if any, <code>null</code> otherwise).
	 *
	 * @return default folder id associated with the category
	 */
	public void setDefaultFolderId(BigInteger id);

	/**
	 * Sets the description for the category.
	 *
	 * @param description
	 *            description
	 */
	public void setDescription(String description);

	/**
	 * Sets the name for the category.
	 *
	 * @param name
	 *            name
	 */
	public void setName(String name);

	/**
	 * Set owner of this category
	 *
	 * @param owner
	 */
	public void setOwner(String owner);

	/**
	 * Set parameters of category
	 *
	 * @param param
	 *            List of parameters
	 */
	public void setParameters(List<Parameter> param);

	/**
	 * Set provider's asset root reference name of this category
	 *
	 * @param providerAssetRoot
	 *            Provider's asset root reference name
	 */
	public void setProviderAssetRoot(String providerAssetroot);

	/**
	 * Set provider's widget discovery reference name of this category
	 *
	 * @param providerDiscovery
	 *            Provider's widget discovery reference name
	 */
	public void setProviderDiscovery(String providerDiscovery);

	/**
	 * Set provider name of this category
	 *
	 * @param providerName
	 *            Name of the provider for this category
	 */
	public void setProviderName(String providerName);

	/**
	 * Set provider version of this category
	 *
	 * @param providerVersion
	 *            Version of the provider for this category
	 */
	public void setProviderVersion(String providerVersion);
	
	public void setId(BigInteger id);

}
