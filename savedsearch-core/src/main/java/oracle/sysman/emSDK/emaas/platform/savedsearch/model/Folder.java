package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.util.Date;

/**
 * The interface <code>Folder</code> represents a folder in the EM Analytics entity organizational model.
 * 
 * @version $Header: emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/emSDK/core/emanalytics/api/Folder.java
 *          /st_emgc_pt-13.1mstr/2 2014/02/03 02:51:00 saurgarg Exp $
 * @author kuabhina
 * @since 13.1.0.0
 */
public interface Folder
{

	/**
	 * Returns the identifier of the folder.
	 * 
	 * @return identifier of the folder
	 */
	public Integer getId();

	/**
	 * Name/Internal-name of the folder.
	 * 
	 * @return internal name
	 */
	public String getName();

	/**
	 * Parent folder ID. <code>null</code> if its a root level folder.
	 * 
	 * @return parent folder id
	 */
	public Integer getParentId();

	/**
	 * Returns the localized description for the search (if localized description available, user specified description
	 * otherwise).
	 * 
	 * @return localized description
	 */
	public String getDescription();

	/**
	 * Returns the owner of search.
	 * 
	 * @return owner
	 */
	public String getOwner();

	/**
	 * Returns the creation date for search.
	 * 
	 * @return creation date
	 */
	public Date getCreationDate();

	/**
	 * Returns the user who last modified the search.
	 * 
	 * @return last modified by user
	 */
	public String getLastModifiedBy();

	/**
	 * Returns the last modification date.
	 * 
	 * @return last modification date
	 */
	public Date getLastModificationDate();

	/**
	 * Returns <code>true</code> if the folder is created by application (and not user created).
	 * 
	 * @return if the folder belongs to system
	 */
	public boolean isSystemFolder();

	/**
	 * sets the name of the folder.
	 * 
	 * @param name
	 *            name of folder
	 */
	public void setName(String name);

	/**
	 * Sets the parent folder Id.
	 * 
	 * @param parentId
	 *            parent folder id
	 */
	public void setParentId(Integer parentId);

	/**
	 * Sets the description of folder.
	 * 
	 * @param description
	 *            description of folder
	 */
	public void setDescription(String description);

	/**
	 * Sets the folder object to be marked as hidden from the UI.
	 * 
	 * @param uiHidden
	 *            hidden behavior of folder
	 */
	public void setUiHidden(boolean uiHidden);

	/**
	 * Returns <code>true</code> if the folder is marked to be hidden from UI.
	 * 
	 * @return if the folder is marked to be hidden from UI
	 */
	public boolean isUiHidden();
}
