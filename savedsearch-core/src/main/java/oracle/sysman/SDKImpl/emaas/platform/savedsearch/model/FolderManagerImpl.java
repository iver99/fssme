package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.IdGenerator;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.QueryParameterConstant;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.ZDTContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EmAnalyticsProcessingException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FolderManagerImpl extends FolderManager
{
	// Logger
	private static final Logger LOGGER = LogManager.getLogger(FolderManagerImpl.class);

	private static final FolderManagerImpl INSTANCE = new FolderManagerImpl();

	/**
	 * Get FolderManagerImpl singleton instance.
	 * 
	 * @return Instance of FolderManagerImpl
	 */
	public static FolderManagerImpl getInstance()
	{
		return INSTANCE;

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
	public void deleteFolder(BigInteger folderId, boolean permanently) throws EMAnalyticsFwkException
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
				LOGGER.error("Folder with id " + folderId + " does not exist");
				throw new EMAnalyticsFwkException("Folder with Id " + folderId + " does not exist",
						EMAnalyticsFwkException.ERR_GET_FOLDER_FOR_ID, null);
			}
			if (folderObj.getSystemFolder() != null && folderObj.getSystemFolder().intValue() == 1) {
				throw new EMAnalyticsFwkException("Folder with Id " + folderId + " is system folder and NOT allowed to delete",
						EMAnalyticsFwkException.ERR_DELETE_FOLDER, null);
			}

			EmAnalyticsObjectUtil.canDeleteFolder(folderId, em);

			folderObj.setDeleted(folderId);
			folderObj.setLastModificationDate(DateUtil.getGatewayTime());
			em.getTransaction().begin();
			//em.setProperty("soft.deletion.permanent", permanently);			
			if (permanently) {
				em.remove(folderObj);
			}
			else {
				em.merge(folderObj);
			}
			em.getTransaction().commit();
		}
		catch (EMAnalyticsFwkException eme) {
			LOGGER.error("Folder with Id: " + folderId + "is a system folder", eme);
			throw eme;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processFolderPersistantException(e, folderId.longValue(), -1, null);
			LOGGER.error("Error while deleting the folder with Id:" + folderId, e);
			throw new EMAnalyticsFwkException("Error occurred while deleting the folder",
					EMAnalyticsFwkException.ERR_DELETE_FOLDER, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}
		}

	}

	@Override
	public Folder getFolder(BigInteger folderId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		Folder folder = new FolderImpl();
		try {

			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			EmAnalyticsFolder folderObj = EmAnalyticsObjectUtil.getFolderById(folderId, em);
			if (folderObj == null) {
				LOGGER.error("Folder with Id" + folderId + "does not exist");
				throw new EMAnalyticsFwkException("Folder with the Id " + folderId + " " + "does not exist",
						EMAnalyticsFwkException.ERR_GET_FOLDER_FOR_ID, null);
			}
			em.refresh(folderObj);
			folder = createFolderObject(folderObj, null);
		}
		catch (EMAnalyticsFwkException eme) {
			LOGGER.error("Folder with Id" + folderId + "does not exist", eme);
			throw eme;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processFolderPersistantException(e, folderId.longValue(), -1, null);
			LOGGER.error("Error while getting the folder with Id:" + folderId, e);
			throw new EMAnalyticsFwkException("Folder with the Id " + folderId + " " + "does not exist",
					EMAnalyticsFwkException.ERR_GET_FOLDER_FOR_ID, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}

		}

		return folder;
	}

	@Override
	public String[] getPathForFolderId(BigInteger folderId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		String[] path = null;
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
			LOGGER.error("Error while getting the path for folder with Id:" + folderId, e);
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
			parentFolderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getRootFolders")
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getSingleResult();
		}
		catch (NoResultException e) {
			LOGGER.error(e.getLocalizedMessage());
			parentFolderObj = null;
		}
		if (parentFolderObj != null) {
			fld = createFolderObject(parentFolderObj, null);
		}
		return fld;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Folder> getSubFolders(BigInteger folderId) throws EMAnalyticsFwkException
	{
		EntityManager em = null;
		try {
			List<Folder> retList = new ArrayList<Folder>();
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			List<EmAnalyticsFolder> folderList;

			if (BigInteger.ZERO.compareTo(folderId) >= 0) {
				folderList = em.createNamedQuery("Folder.getRootFolders")
						.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getResultList();
			}
			else {
				EmAnalyticsFolder folderObj = EmAnalyticsObjectUtil.getFolderById(folderId, em);
				String parentFolder = "parentFolder";
				folderList = em.createNamedQuery("Folder.getSubFolder").setParameter(parentFolder, folderObj)
						.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getResultList();
			}

			if (folderList != null) {
				for (EmAnalyticsFolder folder : folderList) {
					retList.add(createFolderObject(folder, null));
				}
			}
			return retList;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processFolderPersistantException(e, -1, -1, null);
			LOGGER.error("Error while retrieving the list of sub-folders for the parent folder: " + folderId, e);
			throw new EMAnalyticsFwkException(
					"Error while retrieving the list of sub-folders for the parent folder: " + folderId,
					EMAnalyticsFwkException.ERR_GENERIC, null, e);

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
		if(folder != null && folder.getId() == null) {
			folder.setId(IdGenerator.getIntUUID(ZDTContext.getRequestId()));
		}

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
			LOGGER.error("Folder with name " + folder.getName() + " was saved but could not bve retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			processUniqueConstraints(folder, em, dmlce);
			EmAnalyticsProcessingException.processFolderPersistantException(dmlce, -1, -1, folder.getName());
			LOGGER.error("Error while saving the folder: " + folder.getName(), dmlce);
			throw new EMAnalyticsFwkException("Error while saving the folder: " + folder.getName(),
					EMAnalyticsFwkException.ERR_CREATE_FOLDER, null);

		}
		catch (Exception e) {

			LOGGER.error("Error while saving the folder: " + folder.getName(), e);
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
		boolean bCommit = true;
		EmAnalyticsFolder folderObj = null;
		EntityManager em = null;
		Folder folder = null;
		List<FolderImpl> importedList = new ArrayList<FolderImpl>();
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			em.getTransaction().begin();
			for (FolderDetails folderDet : folders) {
				try {
					folder = folderDet.getFolder();
					EmAnalyticsFolder existFolder = EmAnalyticsObjectUtil.getFolderById(folder.getId(), em);
					if (existFolder != null) {
						EmAnalyticsFolder emFolder = EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder, em);
						em.merge(emFolder);
						importedList.add((FolderImpl) createFolderObject(emFolder, folder));
					}
					else {
						BigInteger id = folder.getParentId() == null
								|| BigInteger.ZERO.equals(folder.getParentId()) ? BigInteger.ONE
								: folder.getParentId();
						folder.setParentId(id);
						folderObj = getFolderByName(folder.getName(), id, em);
						if (folderObj == null) {
							EmAnalyticsFolder emFolder = EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd(folder, em);
							em.persist(emFolder);
							importedList.add((FolderImpl) createFolderObject(emFolder, folder));
						}
						else {
							importedList.add((FolderImpl) createFolderObject(folderObj, folder));
						}

					}
				}
				catch (EMAnalyticsFwkException eme) {
					bCommit = false;
					importedList.clear();
					LOGGER.error("Error while importing the folder: " + folder.getName(), eme);
					break;
				}
				catch (PersistenceException eme) {
					bCommit = false;
					importedList.clear();
					LOGGER.error("Error while importing the folder: " + folder.getName(), eme);
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
			LOGGER.error("Error in saveMultipleFolders :", e);
			throw e;

		}
		finally {
			if (em != null) {

				em.close();
			}
		}
		return importedList;
	}

	/*@Override
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
			EmAnalyticsProcessingException.processFolderPersistantException(dmlce, -1, folder.getParentId(), folder.getName());
			_logger.error("Error while saving the folder: " + folder.getName(), dmlce);
			throw new EMAnalyticsFwkException("Error while saving the folder: " + folder.getName(),
					EMAnalyticsFwkException.ERR_CREATE_FOLDER, null);

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

	}*/

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
			LOGGER.error("Folder  was saved but could not bve retrieved back", eme);
			throw eme;
		}
		catch (PersistenceException dmlce) {
			LOGGER.error("Error while saving the folder: " + folder.getName(), dmlce);
			processUniqueConstraints(folder, em, dmlce);
			EmAnalyticsProcessingException.processFolderPersistantException(dmlce, -1, folder.getParentId().longValue(), folder.getName());
			throw new EMAnalyticsFwkException("Error while saving the folder: " + folder.getName(),
					EMAnalyticsFwkException.ERR_CREATE_FOLDER, null, dmlce);

		}
		catch (Exception e) {

			LOGGER.error("Error while saving the folder: " + folder.getName(), e);
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

			rtnObj.setId(folderObj.getFolderId());
			EmAnalyticsFolder parentfolder = folderObj.getEmAnalyticsFolder();

			if (parentfolder != null && !BigInteger.ZERO.equals(parentfolder.getFolderId())) {
				rtnObj.setParentId(parentfolder.getFolderId());
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
			rtnObj.setCreationDate(folderObj.getCreationDate());
			rtnObj.setLastModifiedBy(folderObj.getLastModifiedBy());
			rtnObj.setLastModificationDate(folderObj.getLastModificationDate());
			rtnObj.setSystemFolder(folderObj.getSystemFolder().intValueExact() == 0 ? false : true);
			//rtnObj.setUiHidden(folderObj.getUiHidden().intValueExact()==0?false:true);

		}
		catch (Exception e) {
			LOGGER.error("Error while getting the folder object", e);
			throw new EMAnalyticsFwkException("Error while getting the folder object", EMAnalyticsFwkException.ERR_GET_FOLDER,
					null, e);
		}
		return rtnObj;
	}

	private EmAnalyticsFolder getFolderByName(String name, BigInteger parentId, EntityManager em) throws EMAnalyticsFwkException
	{
		EmAnalyticsFolder folderObj = null;
		try {
			if (BigInteger.ZERO.equals(parentId)) {
				folderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getRootFolderByName")
						.setParameter("foldername", name)
						.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
						.getSingleResult();
			}
			else {
				EmAnalyticsFolder parentFolderObj = EmAnalyticsObjectUtil.getFolderById(parentId, em);
				String parentFolder = "parentFolder";
				folderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getSubFolderByName")
						.setParameter(parentFolder, parentFolderObj).setParameter("foldername", name)
						.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername())
						.getSingleResult();
			}

		}
		catch (NoResultException nre) {
			LOGGER.error(nre.getLocalizedMessage());
			return null;
		}
		catch (Exception e) {
			LOGGER.error("Error while reading the folder with parent: " + parentId + " and name: " + name, e);
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
		BigInteger pId = null;
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

	private void processUniqueConstraints(Folder folder, EntityManager em, PersistenceException dmlce)
			throws EMAnalyticsFwkException
	{

		if (dmlce.getCause() != null && dmlce.getCause().getMessage().contains("ANALYTICS_FOLDERS_U01")) {
			EmAnalyticsFolder parentFolderObj = EmAnalyticsObjectUtil.getFolderById(folder.getParentId(), em);
			String parentFolder = "parentFolder";
			EmAnalyticsFolder folderObj = (EmAnalyticsFolder) em.createNamedQuery("Folder.getSubFolderByName")
					.setParameter(parentFolder, parentFolderObj).setParameter("foldername", folder.getName())
					.setParameter(QueryParameterConstant.USER_NAME, TenantContext.getContext().getUsername()).getSingleResult();

			String result = EntityJsonUtil.getErrorJsonObject(folderObj.getFolderId(),
					"The folder name '" + folderObj.getName() + "' already exist", EMAnalyticsFwkException.ERR_FOLDER_DUP_NAME)
					.toString();

			throw new EMAnalyticsFwkException(result, EMAnalyticsFwkException.ERR_FOLDER_DUP_NAME,
					new Object[] { folderObj.getName() });
		}

	}

}
