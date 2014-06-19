package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

/**
 * The class <code>FolderManager</code> provides CRUD and other management operations over the Folder entity in EM Analytics.
 * 
 * @see Folder
 * @version $Header:
 *          emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/emSDK/core/emanalytics/api/FolderManager.java
 *          /st_emgc_pt-13.1mstr/2 2014/02/03 02:51:01 saurgarg Exp $
 * @author kuabhina
 * @since release specific (what release of product did this appear in)
 */
public abstract class FolderManager
{
	/**
	 * Returns an instance of the manager.
	 * 
	 * @return an instance of the manager
	 */
	public static FolderManager getInstance()
	{
		return FolderManagerImpl.getInstance();
	}

	/**
	 * Creates a new folder object.
	 * 
	 * @return new folder
	 */
	public abstract Folder createNewFolder();

	/**
	 * Saves the folder into the system.
	 * 
	 * @param folder
	 *            folder to be saved
	 * @return re-loaded folder object with generated ids and dates
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Folder saveFolder(Folder folder) throws EMAnalyticsFwkException;

	/**
	 * Returns the folder object identified by the given identifier.
	 * 
	 * @param folderId
	 *            identifier for the folder entity
	 * @return folder
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Folder getFolder(long folderId) throws EMAnalyticsFwkException;

	/**
	 * Edits the folder into the system.
	 * 
	 * @param folder
	 *            folder to be saved
	 * @return re-loaded folder object with generated ids and dates
	 * @throws EMAnalyticsFwkException
	 */
	public abstract Folder updateFolder(Folder folder) throws EMAnalyticsFwkException;

	/**
	 * Returns the string representation of path of folder (from root, "/" separated).
	 * 
	 * @param folderId
	 *            folder for which path is required
	 * @return path of the folder from root
	 * @throws EMAnalyticsFwkException
	 */
	public abstract String[] getPathForFolderId(long folderId) throws EMAnalyticsFwkException;

	/**
	 * Returns a list of sub-folders, <code>null</code> if there are none.
	 * 
	 * @param folderId
	 *            parent folder id, if <code>null</code> then all the root level folders are returned
	 * @return
	 * @throws EMAnalyticsFwkException
	 */
	public abstract List<Folder> getSubFolders(long folderId) throws EMAnalyticsFwkException;

	/**
	 * Deletes the folder.
	 * 
	 * @param folderId
	 *            folder id
	 * @throws EMAnalyticsFwkException
	 */
	public abstract void deleteFolder(long folderId) throws EMAnalyticsFwkException;

	public abstract Folder savePath(Folder objFld) throws EMAnalyticsFwkException;  

}
