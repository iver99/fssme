package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;

import org.apache.log4j.Logger;

public class FolderManagerImpl extends FolderManager
{
	// Logger
	private static final Logger _logger = Logger.getLogger(FolderManagerImpl.class);

	private static final FolderManagerImpl _instance = new FolderManagerImpl();

	/**
	 * Get FolderManagerImpl singleton instance.
	 * 
	 * @return Instance of FolderManagerImpl
	 */
	public static FolderManagerImpl getInstance()
	{
		return _instance;

	}

	/**
	 * Private Constructor
	 */
	private FolderManagerImpl()
	{

	}

	@Override
	public Folder createNewFolder()
	{
		return new FolderImpl();
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager#createRootFolder(oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder)
	 */

	@Override
	public void deleteFolder(long folderId, boolean permanently) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		EmAnalyticsFolder folderObj = null;
		try {

			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			if (permanently) {
				folderObj = EmAnalyticsObjectUtil.getFolderByIdForDelete(folderId, em);
			}
			else {
				folderObj = EmAnalyticsObjectUtil.getFolderById(folderId, em);
			}
			if (folderObj == null) {
				_logger.error("Folder with id " + folderId + " does not exist");
				throw new EMAnalyticsFwkException("Folder with Id " + folderId + " does not exist",
						EMAnalyticsFwkException.ERR_GET_FOLDER_FOR_ID, null);
			}
			if (folderObj.getSystemFolder() != null && folderObj.getSystemFolder().intValue() == 1) {
				throw new EMAnalyticsFwkException("Folder with Id " + folderId + " is system folder and NOT allowed to delete",
						EMAnalyticsFwkException.ERR_DELETE_FOLDER, null);
			}

			EmAnalyticsObjectUtil.canDeleteFolder(folderId, em);

			folderObj.setDeleted(folderId);
			em.getTransaction().begin();
			if (permanently) {
				em.remove(folderObj);
			}
			else {
				em.merge(folderObj);
			}
			em.getTransaction().commit();
		}
		catch (EMAnalyticsFwkException eme) {
			_logger.error("Folder with Id: " + folderId + "is a system folder", eme);
			throw eme;
		}
		catch (Exception e) {
			if (e.getCause() != null && e.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null, e);
			}
			else if (e.getCause().getMessage().contains("ANALYTICS_SEARCH_FK2")) {
				throw new EMAnalyticsFwkException("folder with Id " + folderId + " has search child",
						EMAnalyticsFwkException.ERR_DELETE_FOLDER, null, e);
			}
			else if (e.getCause().getMessage().contains("ANALYTICS_SEARCH_FK1")) {
				throw new EMAnalyticsFwkException("folder with Id " + folderId + " has category child",
						EMAnalyticsFwkException.ERR_DELETE_FOLDER, null, e);
			}
			else {
				_logger.error("Error while deleting the folder with Id:" + folderId, e);

				throw new EMAnalyticsFwkException("Error occurred while deleting the folder",
						EMAnalyticsFwkException.ERR_DELETE_FOLDER, null, e);
			}
		}
		finally {
			if (em != null) {
				em.close();
			}
		}

	}

	@Override
	public Folder getFolder(long folderId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		Folder folder = new FolderImpl();
		try {

			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			EmAnalyticsFolder folderObj = EmAnalyticsObjectUtil.getFolderById(folderId, em);
			if (folderObj == null) {
				_logger.error("Folder with Id" + folderId + "does not exist");
				throw new EMAnalyticsFwkException("Folder with the Id " + folderId + " " + "does not exist",
						EMAnalyticsFwkException.ERR_GET_FOLDER_FOR_ID, null);
			}
			em.refresh(folderObj);
			folder = createFolderObject(folderObj, null);
		}
		catch (EMAnalyticsFwkException eme) {
			_logger.error("Folder with Id" + folderId + "does not exist", eme);
			throw eme;
		}
		catch (Exception e) {
			if (e.getCause() != null && e.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null, e);
			}
			else {
				_logger.error("Error while getting the folder with Id:" + folderId, e);
				throw new EMAnalyticsFwkException("Folder with the Id " + folderId + " " + "does not exist",
						EMAnalyticsFwkException.ERR_GET_FOLDER_FOR_ID, null, e);
			}
		}
		finally {
			if (em != null) {
				em.close();
			}

		}

		return folder;
	}

	@Override
	public String[] getPathForFolderId(long folderId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		String[] path = null;
		Folder folder = new FolderImpl();
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			EmAnalyticsFolder folderObj = EmAnalyticsObjectUtil.getFolderById(folderId, em);
			if (folderObj == null) {
				return path;
			}
			List<String> pathList = getPath(folderObj, em);
			path = pathList.toArray(new String[pathList.size()]);
			//folder= createFolderObject(folderObj,null);
		}
		catch (Exception e) {
			_logger.error("Error while getting the path for folder with Id:" + folderId, e);
			throw new EMAnalyticsFwkException("Error occurred while getting path for the folder",
					EMAnalyticsFwkException.ERR_DELETE_FOLDER, null, e);
		}
		finally {
			if (em != null) {
				em.close();
			}

		}
		return path;
	}

	public Folder getRootFolder() throws EMAnalyticsFwkException
	{
		EmAnalyticsFolder parentFolderObj = null;
		Folder fld = null;
		EntityManager em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
		try {
			parentFolderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getRootFolders").getSingleResult();
		}
		catch (NoResultException e) {
			parentFolderObj = null;
		}
		if (parentFolderObj != null) {
			fld = createFolderObject(parentFolderObj, null);
		}
		return fld;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Folder> getSubFolders(long folderId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		try {
			ArrayList<Folder> retList = new ArrayList<Folder>();
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			List<EmAnalyticsFolder> folderList;

			if (folderId <= 0) {
				folderList = em.createNamedQuery("Folder.getRootFolders").getResultList();
			}
			else {
				EmAnalyticsFolder folderObj = EmAnalyticsObjectUtil.getFolderById(folderId, em);
				String parentFolder = "parentFolder";
				folderList = em.createNamedQuery("Folder.getSubFolder").setParameter(parentFolder, folderObj).getResultList();
			}

			if (folderList != null) {
				for (EmAnalyticsFolder folder : folderList) {
					retList.add(createFolderObject(folder, null));
				}
			}
			return retList;
		}
		catch (Exception e) {
			if (e.getCause() != null && e.getCause().getMessage() != null
					&& e.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + e.getMessage(), e);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null, e);
			}
			else {
				_logger.error("Error while retrieving the list of sub-folders for the parent folder: " + folderId, e);
				throw new EMAnalyticsFwkException("Error while retrieving the list of sub-folders for the parent folder: "
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
	public Folder saveFolder(Folder folder) throws EMAnalyticsFwkException
	{

		EntityManager em = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			em.getTransaction().begin();
			EmAnalyticsFolder folderObj = EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd(folder, em);
			em.persist(folderObj);
			em.getTransaction().commit();
			em.refresh(folderObj);
			return createFolderObject(folderObj, folder);

		}
		catch (EMAnalyticsFwkException eme) {
			_logger.error("Folder with name " + folder.getName() + " was saved but could not bve retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			if (dmlce.getCause() != null && dmlce.getCause().getMessage().contains("ANALYTICS_FOLDERS_U01")) {
				throw new EMAnalyticsFwkException("Folder with name " + folder.getName() + " already exist",
						EMAnalyticsFwkException.ERR_FOLDER_DUP_NAME, new Object[] { folder.getName() });
			}
			//			else if (dmlce.getCause()!=null && dmlce.getCause().getMessage().contains("ANALYTICS_FOLDERS_FK1")) {
			//				throw new EMAnalyticsFwkException("Parent folder with id " + folder.getParentId() + " does not exist: ",
			//						EMAnalyticsFwkException.ERR_FOLDER_INVALID_PARENT, null);
			//			}
			else if (dmlce.getCause() != null && dmlce.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + dmlce.getMessage(), dmlce);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else {
				_logger.error("Error while saving the folder: " + folder.getName(), dmlce);
				throw new EMAnalyticsFwkException("Error while saving the folder: " + folder.getName(),
						EMAnalyticsFwkException.ERR_CREATE_FOLDER, null);
			}
		}
		catch (Exception e) {

			_logger.error("Error while saving the folder: " + folder.getName(), e);
			throw new EMAnalyticsFwkException("Error while saving the folder: " + folder.getName(),
					EMAnalyticsFwkException.ERR_CREATE_FOLDER, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}

		}

	}

	public List<FolderImpl> saveMultipleFolders(List<FolderDetails> folders) throws Exception
	{
		int iCount = 0;
		boolean bCommit = true;
		EmAnalyticsFolder folderObj = null;
		EntityManagerFactory emf = null;
		EntityManager em = null;
		Folder folder = null;
		List<FolderImpl> importedList = new ArrayList<FolderImpl>();
		try {

			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			em.getTransaction().begin();
			for (FolderDetails folderDet : folders) {
				try {
					folder = folderDet.getFolder();
					if (folder.getId() != null && folder.getId() > 0) {
						EmAnalyticsFolder emFolder = EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder, em);
						em.merge(emFolder);
						importedList.add((FolderImpl) createFolderObject(emFolder, folder));
						iCount++;
					}
					else {
						long id = folder.getParentId() == null || folder.getParentId() == 0 ? 1 : folder.getParentId()
								.longValue();
						folder.setParentId((int) id);
						folderObj = getFolderByName(folder.getName(), id, em);
						if (folderObj == null) {
							EmAnalyticsFolder emFolder = EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd(folder, em);
							em.persist(emFolder);
							importedList.add((FolderImpl) createFolderObject(emFolder, folder));
							iCount++;
						}
						else {
							importedList.add((FolderImpl) createFolderObject(folderObj, folder));
						}

					}
				}
				catch (EMAnalyticsFwkException eme) {
					bCommit = false;
					importedList.clear();
					_logger.error("Error while importing the folder: " + folder.getName(), eme);
					break;
				}
				catch (PersistenceException eme) {
					bCommit = false;
					importedList.clear();
					_logger.error("Error while importing the folder: " + folder.getName(), eme);
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
			_logger.error("Error in saveMultipleFolders :", e);
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
	public Folder savePath(Folder folder) throws EMAnalyticsFwkException
	{
		String[] folderNames = folder.getName().split("/");
		String description = folder.getDescription();

		EntityManager em = null;

		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			em.getTransaction().begin();
			EmAnalyticsFolder folderObj = null;
			for (int i = 0; i < folderNames.length - 1; i++) {
				folder.setName(folderNames[i]);
				folderObj = getFolderByName(folder.getName(), folder.getParentId(), em);
				if (folderObj == null) {
					folderObj = EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd(folder, em);
					em.persist(folderObj);
				}

				folder.setParentId((int) folderObj.getEmAnalyticsFolder().getFolderId());
			}
			folder.setName(folderNames[folderNames.length - 1]);
			folder.setDescription(description);
			folderObj = EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd(folder, em);
			em.persist(folderObj);
			em.getTransaction().commit();
			em.refresh(folderObj);
			return createFolderObject(folderObj, folder);
		}

		catch (EMAnalyticsFwkException eme) {
			_logger.error("Folder with name " + folder.getName() + " was saved but could not bve retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			if (dmlce.getCause().getMessage().contains("ANALYTICS_FOLDERS_U01")) {
				throw new EMAnalyticsFwkException("Folder with name " + folder.getName() + " already exist",
						EMAnalyticsFwkException.ERR_FOLDER_DUP_NAME, new Object[] { folder.getName() });
			}
			else if (dmlce.getCause().getMessage().contains("ANALYTICS_FOLDERS_FK1")) {
				throw new EMAnalyticsFwkException("Parent folder with Id " + folder.getParentId() + " does not exist: ",
						EMAnalyticsFwkException.ERR_FOLDER_INVALID_PARENT, null);
			}
			else if (dmlce.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + dmlce.getMessage(), dmlce);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else {
				_logger.error("Error while saving the folder: " + folder.getName(), dmlce);
				throw new EMAnalyticsFwkException("Error while saving the folder: " + folder.getName(),
						EMAnalyticsFwkException.ERR_CREATE_FOLDER, null);
			}
		}
		catch (Exception e) {

			_logger.error("Error while saving the folder: " + folder.getName(), e);
			throw new EMAnalyticsFwkException("Error while saving the folder: " + folder.getName(),
					EMAnalyticsFwkException.ERR_CREATE_FOLDER, null, e);

		}
		finally {
			if (em != null) {

				em.close();
			}

		}

	}

	@Override
	public Folder updateFolder(Folder folder) throws EMAnalyticsFwkException
	{

		EntityManager em = null;
		try {

			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			EmAnalyticsFolder folderObj = EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder, em);
			if (folderObj != null && folderObj.getSystemFolder() != null && folderObj.getSystemFolder().intValue() == 1) {
				throw new EMAnalyticsFwkException("Folder with Id " + folderObj.getFolderId()
						+ " is system folder and NOT allowed to edit", EMAnalyticsFwkException.ERR_UPDATE_FOLDER, null);
			}
			em.getTransaction().begin();
			em.persist(folderObj);
			em.getTransaction().commit();
			return createFolderObject(folderObj, null);
		}
		catch (EMAnalyticsFwkException eme) {
			_logger.error("Folder  was saved but could not bve retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			if (dmlce.getCause().getMessage().contains("ANALYTICS_FOLDERS_U01")) {
				throw new EMAnalyticsFwkException("Folder name " + folder.getName() + " already exist",
						EMAnalyticsFwkException.ERR_FOLDER_DUP_NAME, new Object[] { folder.getName() });
			}
			else if (dmlce.getCause().getMessage().contains("ANALYTICS_FOLDERS_FK1")) {
				throw new EMAnalyticsFwkException("Parent folder with Id " + folder.getParentId() + " missing: "
						+ folder.getName(), EMAnalyticsFwkException.ERR_FOLDER_INVALID_PARENT, null);
			}
			if (dmlce.getCause().getMessage().contains("Cannot acquire data source")) {
				_logger.error("Error while acquiring the data source" + dmlce.getMessage(), dmlce);
				throw new EMAnalyticsFwkException(
						"Error while connecting to data source, please check the data source details: ",
						EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS, null);
			}
			else {
				_logger.error("Error while saving the folder: " + folder.getName(), dmlce);
				throw new EMAnalyticsFwkException("Error while saving the folder: " + folder.getName(),
						EMAnalyticsFwkException.ERR_CREATE_FOLDER, null, dmlce);
			}
		}
		catch (Exception e) {

			_logger.error("Error while saving the folder: " + folder.getName(), e);
			throw new EMAnalyticsFwkException("Error while saving the folder: " + folder.getName(),
					EMAnalyticsFwkException.ERR_CREATE_FOLDER, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}
		}

	}

	private Folder createFolderObject(EmAnalyticsFolder folderObj, Folder folder) throws EMAnalyticsFwkException
	{
		FolderImpl rtnObj = null;
		try {
			if (folder != null) {
				//populate the current object
				rtnObj = (FolderImpl) folder;
			}
			else {
				rtnObj = (FolderImpl) createNewFolder();
			}

			rtnObj.setId((int) folderObj.getFolderId());
			EmAnalyticsFolder parentfolder = folderObj.getEmAnalyticsFolder();

			if (parentfolder != null && parentfolder.getFolderId() != 0) {
				rtnObj.setParentId((int) parentfolder.getFolderId());
			}

			// TODO : Abhinav Handle the internationalization via MGMT_MESSAGES
			// handling name here
			String nlsid = folderObj.getNameNlsid();
			String subsystem = folderObj.getNameSubsystem();
			if (nlsid == null || nlsid.trim().length() == 0 || subsystem == null || subsystem.trim().length() == 0) {
				rtnObj.setName(folderObj.getName());
			}
			else {
				// here the code should come !! get localized stuff from
				// MGMT_MESSAGES
				rtnObj.setName(folderObj.getName());
			}

			nlsid = folderObj.getDescriptionNlsid();
			subsystem = folderObj.getDescriptionSubsystem();
			if (nlsid == null || nlsid.trim().length() == 0 || subsystem == null || subsystem.trim().length() == 0) {
				rtnObj.setDescription(folderObj.getDescription());
			}
			else {
				// here the code should come !! get localized stuff from
				// MGMT_MESSAGES
				rtnObj.setDescription(folderObj.getDescription());
			}
			//
			rtnObj.setOwner(folderObj.getOwner());
			rtnObj.setCreatedOn(folderObj.getCreationDate());
			rtnObj.setLastModifiedBy(folderObj.getLastModifiedBy());
			rtnObj.setLastModifiedOn(folderObj.getLastModificationDate());
			rtnObj.setSystemFolder(folderObj.getSystemFolder().intValueExact() == 0 ? false : true);
			//rtnObj.setUiHidden(folderObj.getUiHidden().intValueExact()==0?false:true);

		}
		catch (Exception e) {
			_logger.error("Error while getting the folder object", e);
			throw new EMAnalyticsFwkException("Error while getting the folder object", EMAnalyticsFwkException.ERR_GET_FOLDER,
					null, e);
		}
		return rtnObj;
	}

	private EmAnalyticsFolder getFolderByName(String name, long parentId, EntityManager em) throws EMAnalyticsFwkException
	{
		//EntityManager em =null;
		EmAnalyticsFolder folderObj = null;
		try {
			//        	/EntityManagerFactory emf =PersistenceManager.getInstance().getEntityManagerFactory();
			//em = emf.createEntityManager();
			if (parentId == 0) {
				folderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getRootFolderByName")
						.setParameter("foldername", name).getSingleResult();
			}
			else {
				EmAnalyticsFolder parentFolderObj = EmAnalyticsObjectUtil.getFolderById(parentId, em);
				String parentFolder = "parentFolder";
				folderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getSubFolderByName")
						.setParameter(parentFolder, parentFolderObj).setParameter("foldername", name).getSingleResult();
			}

		}
		catch (NoResultException nre) {
			return null;
		}
		catch (Exception e) {
			_logger.error("Error while reading the folder with parent: " + parentId + " and name: " + name, e);
			throw new EMAnalyticsFwkException("Error occurred while  reading the folder", EMAnalyticsFwkException.ERR_GENERIC,
					null, e);
		}
		return folderObj;
	}

	private List<String> getPath(EmAnalyticsFolder folderObj, EntityManager em) throws EMAnalyticsFwkException
	{

		//        String fs=",";
		//        StringBuilder sb= new StringBuilder("");
		List<String> arrPath = new ArrayList<String>();
		arrPath.add(folderObj.getName());
		Long pId = null;
		EmAnalyticsFolder tmpFolder = folderObj.getEmAnalyticsFolder();
		while (tmpFolder != null) {
			pId = tmpFolder.getFolderId();
			arrPath.add(tmpFolder.getName());
			tmpFolder = EmAnalyticsObjectUtil.getFolderById(pId, em).getEmAnalyticsFolder();

		}
		return arrPath;
		//        sb.append("[");
		//        for(int i = (arrPath.size()-1); i>=0 ; i--)
		//        {
		//            sb.append("" +arrPath.get(i) + "");
		//            if(i!=0)
		//             sb.append(fs);
		//        }
		//
		//        sb.append("]");
		//        return sb.toString();

	}

}
