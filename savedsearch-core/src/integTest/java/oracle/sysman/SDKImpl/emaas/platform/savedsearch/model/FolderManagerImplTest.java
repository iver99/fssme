package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-3-4.
 */
@Test(groups = {"s2"})
public class FolderManagerImplTest {

    private static final BigInteger TEST_ID = new BigInteger("1234");
	FolderManagerImpl folderManager;

    @BeforeMethod
    public void testGetInstance() throws Exception {
        folderManager = FolderManagerImpl.getInstance();
    }

    @Test
    public void testCreateNewFolder() throws Exception {
        Assert.assertTrue(folderManager.createNewFolder() instanceof FolderImpl);
    }

    @Test
    public void testDeleteFolder_EMAnalyticsFwkException(@Mocked EMAnalyticsFwkException ee, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
            }
        };
        try {
            folderManager.deleteFolder(TEST_ID, true);
        }catch (Exception e){

        }
    }

    @Test
    public void testDeleteFolder_Exception(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = new Exception();
            }
        };
        try {
            folderManager.deleteFolder(TEST_ID, true);
        }catch (Exception e){

        }
    }

    @Test
    public void testDeleteFolder_EMAnalyticsFwkException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getFolderByIdForDelete((BigInteger) any,entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(1);
            }
        };
        try {
            folderManager.deleteFolder(TEST_ID, true);
        }catch (Exception e){

        }
    }

    @Test
    public void testDeleteFolder(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getFolderByIdForDelete((BigInteger) any,entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(100);
            }
        };
            folderManager.deleteFolder(TEST_ID, true);
            folderManager.deleteFolder(TEST_ID, false);

    }

    @Test
    public void testGetFolder_Exception(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = new Exception();
            }
        };
        try {
            folderManager.getFolder(TEST_ID);
        }catch (Exception e){

        }
    }

    @Test
    public void testGetFolder_EMAnalyticsFwkException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getFolderById((BigInteger) any,entityManager);
                result = null;
//                emAnalyticsFolder.getSystemFolder();
//                result = new BigDecimal(100);
            }
        };
        try {
            folderManager.getFolder(TEST_ID);
        }catch (Exception e){

        }
    }

    @Test
    public void testGetFolder_noException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getFolderById((BigInteger) any,entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(100);
            }
        };
        try {
            folderManager.getFolder(TEST_ID);
        }catch (Exception e){

        }
    }

    @Test
    public void testGetPathForFolderId(@Mocked final EmAnalyticsFolder emAnalyticsFolder,@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getFolderById((BigInteger) any,entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getEmAnalyticsFolder();
                returns(emAnalyticsFolder,emAnalyticsFolder,emAnalyticsFolder,emAnalyticsFolder,null);
            }
        };
        folderManager.getPathForFolderId(TEST_ID);
    }

    @Test
    public void testGetPathForFolderId_Exception(@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getFolderById((BigInteger) any,entityManager);
                result = new Exception();
            }
        };
        try {
            folderManager.getPathForFolderId(TEST_ID);
        }catch (Exception e){

        }
    }

    @Test
    public void testGetRootFolder(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked final Query query, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyString);
                result = query;
                query.getSingleResult();
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(11);
            }
        };
        folderManager.getRootFolder();
    }


    @Test
    public void testGetRootFolder_NoResultException(@Mocked final Query query, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyString);
                result = query;
                query.getSingleResult();
                result = new NoResultException();
            }
        };
        try {
            folderManager.getRootFolder();
        }catch (Exception e){}
    }

    @Test
    public void testGetSubFolders_folderIdST0(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked final Query query, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyString);
                result = query;
//                query.getSingleResult();
//                result = emAnalyticsFolder;
            }
        };
        folderManager.getSubFolders(BigInteger.ONE.negate());
    }

    @Test
    public void testGetSubFolders_folderIdLT0(@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked final Query query, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        final List<EmAnalyticsFolder> folderList = new ArrayList<>();
        folderList.add(emAnalyticsFolder);
        folderList.add(emAnalyticsFolder);
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                EmAnalyticsObjectUtil.getFolderById((BigInteger) any,entityManager);
                result = emAnalyticsFolder;
                tenantInfo.getUsername();
                result = "userName";
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyString);
                result = query;
                query.getResultList();
                result = folderList;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(111);
            }
        };
        folderManager.getSubFolders(BigInteger.ONE);
    }

    @Test
    public void testGetSubFolders_Exception(@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked final Query query, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        final List<EmAnalyticsFolder> folderList = new ArrayList<>();
        folderList.add(emAnalyticsFolder);
        folderList.add(emAnalyticsFolder);
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                EmAnalyticsObjectUtil.getFolderById((BigInteger) any,entityManager);
                result = emAnalyticsFolder;
                tenantInfo.getUsername();
                result = "userName";
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyString);
                result = query;
                query.getResultList();
                result = folderList;
                emAnalyticsFolder.getSystemFolder();
                result = new EMAnalyticsFwkException(new Throwable());
            }
        };
        try {
            folderManager.getSubFolders(BigInteger.ONE);
        }catch (Exception e){}
    }

    @Test
    public void testSaveFolder_EMAnalyticsFwkException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd((Folder)any,entityManager);
                result = emAnalyticsFolder;
            }
        };
        try {
            folderManager.saveFolder(new FolderImpl());
        }catch (Exception e){}
    }

    @Test
    public void testSaveFolder_PersistenceException(@Mocked final PersistenceManager persistenceManager) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = new PersistenceException();
            }
        };
        try {
            folderManager.saveFolder(new FolderImpl());
        }catch (Exception e){}
    }

    @Test
    public void testSaveFolder_noneException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd((Folder)any,entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(11);
            }
        };
        try {
            folderManager.saveFolder(new FolderImpl());
        }catch (Exception e){}
    }

    @Test
    public void testSaveFolder_Exception(@Mocked final PersistenceManager persistenceManager) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = new Exception();
            }
        };
        try {
            folderManager.saveFolder(new FolderImpl());
        }catch (Exception e){}
    }


    @Test
    public void testSaveMultipleFolders_EMAnalyticsFwkException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final Folder folder, @Mocked final FolderDetails folderDetails, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                folderDetails.getFolder();
                result = folder;
                folder.getId();
                result = BigInteger.ONE;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder,entityManager);
                result = emAnalyticsFolder;

            }
        };
        List<FolderDetails> folderDetailsList = new ArrayList<>();
        folderDetailsList.add(folderDetails);
        folderDetailsList.add(folderDetails);
        folderManager.saveMultipleFolders(folderDetailsList);
    }

    @Test
    public void testSaveMultipleFolders_hasfolderId(@Mocked final FolderImpl folder, @Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final FolderDetails folderDetails, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {

        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                folderDetails.getFolder();
                result = folder;
                folder.getId();
                result = BigInteger.ONE;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder,entityManager);
                result = emAnalyticsFolder;
                withAny(emAnalyticsFolder).getSystemFolder();
                result = new BigDecimal(1123);
            }
        };
        List<FolderDetails> folderDetailsList = new ArrayList<>();
        folderDetailsList.add(folderDetails);
        folderDetailsList.add(folderDetails);
        folderManager.saveMultipleFolders(folderDetailsList);
    }

    @Test
    public void testSaveMultipleFolders_nofolderId(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo, @Mocked final Query query, @Mocked final FolderImpl folder, @Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final FolderDetails folderDetails, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {

        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                folderDetails.getFolder();
                result = folder;
                EmAnalyticsObjectUtil.getFolderById((BigInteger) any, entityManager);
                result = null;
                withAny(emAnalyticsFolder).getSystemFolder();
                result = new BigDecimal(1123);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyString);
                result = query;
                query.getSingleResult();
                result = emAnalyticsFolder;
            }
        };
        List<FolderDetails> folderDetailsList = new ArrayList<>();
        folderDetailsList.add(folderDetails);
        folderDetailsList.add(folderDetails);
        folderManager.saveMultipleFolders(folderDetailsList);
    }

    @Test
    public void testSaveMultipleFolders_NoResultException(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo, @Mocked final Query query, @Mocked final FolderImpl folder, @Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final FolderDetails folderDetails, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {

        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                folderDetails.getFolder();
                result = folder;
                EmAnalyticsObjectUtil.getFolderById((BigInteger) any, entityManager);
                result = null;
                withAny(emAnalyticsFolder).getSystemFolder();
                result = new BigDecimal(1123);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyString);
                result = query;
                query.getSingleResult();
                result = new NoResultException();
            }
        };
        List<FolderDetails> folderDetailsList = new ArrayList<>();
        folderDetailsList.add(folderDetails);
        folderDetailsList.add(folderDetails);
        folderManager.saveMultipleFolders(folderDetailsList);
    }

    @Test
    public void testSaveMultipleFolders_Exception(@Mocked final PersistenceManager persistenceManager) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = new Exception();
            }
        };
        List<FolderDetails> folderDetailsList = new ArrayList<>();
        try {
            folderManager.saveMultipleFolders(folderDetailsList);
        }catch (Exception e){}
    }

    @Test
    public void testSaveMultipleFolders_PersistenceException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final Folder folder, @Mocked final FolderDetails folderDetails, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                folderDetails.getFolder();
                result = folder;
                folder.getId();
                result = BigInteger.ONE;
                folder.getName();
                result = "namexx";
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder,entityManager);
                result = new PersistenceException();

            }
        };
        List<FolderDetails> folderDetailsList = new ArrayList<>();
        folderDetailsList.add(folderDetails);
        folderDetailsList.add(folderDetails);
        folderManager.saveMultipleFolders(folderDetailsList);
    }

    @Test
    public void testUpdateFolder_EMAnalyticsFwkException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit((Folder)any,entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(1);
            }
        };
        try {
            folderManager.updateFolder(null);
        }catch (Exception e){}
    }

    @Test
    public void testUpdateFolder_processUniqueConstraints(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo, @Mocked final Query query, @Mocked final PersistenceException pe, @Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit((Folder)any,entityManager);
                result = pe;;
                pe.getCause();
                result = "ANALYTICS_FOLDERS_U01";
                EmAnalyticsObjectUtil.getFolderById((BigInteger) any,entityManager);
                result = emAnalyticsFolder;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,emAnalyticsFolder);
                result = query;
                query.setParameter(anyString,anyString);
                result = query;
                query.getSingleResult();
                result = emAnalyticsFolder;
                emAnalyticsFolder.getFolderId();
                result = 1234L;
                emAnalyticsFolder.getName();
                result = "namexx";
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";

            }
        };
        FolderImpl folder = new FolderImpl();
        folder.setParentId(TEST_ID);
        try {
            folderManager.updateFolder(folder);
        }catch (Exception e){}
    }

    @Test
    public void testUpdateFolder_PersistenceException(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo, @Mocked final Query query, @Mocked final PersistenceException pe, @Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit((Folder)any,entityManager);
                result = pe;;
                pe.getCause();
                result = null;
            }
        };
        FolderImpl folder = new FolderImpl();
        folder.setParentId(TEST_ID);
        try {
            folderManager.updateFolder(folder);
        }catch (Exception e){}
    }

    @Test
    public void testUpdateFolder(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit((Folder)any,entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(222);
            }
        };
        folderManager.updateFolder(null);
    }

    @Test
    public void testUpdateFolder_Exception(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit((Folder)any,entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new Exception();
            }
        };
        FolderImpl folder = new FolderImpl();
        try {
            folderManager.updateFolder(folder);
        }catch (Exception e){}
    }

}