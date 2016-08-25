package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qianqi
 * @since 16-3-4.
 */
@Test(groups = {"s2"})
public class FolderManagerImplTest {

    FolderManagerImpl folderManager;
    @Mocked
    Throwable throwable;
    @BeforeMethod
    public void testGetInstance() {
        folderManager = FolderManagerImpl.getInstance();
    }

    @Test
    public void testCreateNewFolder() {
        Assert.assertTrue(folderManager.createNewFolder() instanceof FolderImpl);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testDeleteFolder_EMAnalyticsFwkException(@Mocked EMAnalyticsFwkException ee, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
            }
        };
        folderManager.deleteFolder(1234, true);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testDeleteFolderException(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = new Exception(throwable);
            }
        };

        folderManager.deleteFolder(1234, true);

    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testDeleteFolderEMAnalyticsFwkException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                                        @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
                                                        @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getFolderByIdForDelete(anyLong, entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(1);
            }
        };
        folderManager.deleteFolder(1234, true);
    }

    @Test
    public void testDeleteFolder(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                 @Mocked final PersistenceManager persistenceManager,
                                 @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext,
                                 @Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getFolderByIdForDelete(anyLong, entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(100);
            }
        };
        folderManager.deleteFolder(1234, true);
        folderManager.deleteFolder(1234, false);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetFolderException(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
                                       @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = new Exception(throwable);
            }
        };
        folderManager.getFolder(1234);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetFolderEMAnalyticsFwkException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                                     @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
                                                     @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getFolderById(anyLong, entityManager);
                result = null;
//                emAnalyticsFolder.getSystemFolder();
//                result = new BigDecimal(100);
            }
        };
        folderManager.getFolder(1234);
    }

    @Test
    public void testGetFolderNoException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                         @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
                                         @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getFolderById(anyLong, entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(100);
            }
        };
        folderManager.getFolder(1234);
    }

    @Test
    public void testGetPathForFolderId(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                       @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
                                       @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getFolderById(anyLong, entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getEmAnalyticsFolder();
                returns(emAnalyticsFolder, emAnalyticsFolder, emAnalyticsFolder, emAnalyticsFolder, null);
            }
        };
        folderManager.getPathForFolderId(1234);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetPathForFolderIdException(@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getFolderById(anyLong, entityManager);
                result = new Exception(throwable);
            }
        };
        folderManager.getPathForFolderId(1234);
    }

    @Test
    public void testGetRootFolder(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked final Query query,
                                  @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
                                  @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException {
        new Expectations() {
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
                query.setParameter(anyString, anyString);
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
    public void testGetRootFolderNoResultException(@Mocked final Query query, @Mocked final PersistenceManager persistenceManager,
                                                   @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext,
                                                   @Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException {
        new Expectations() {
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
                query.setParameter(anyString, anyString);
                result = query;
                query.getSingleResult();
                result = new NoResultException();
            }
        };
        folderManager.getRootFolder();
    }

    @Test
    public void testGetSubFoldersFolderIdST0(@Mocked final EmAnalyticsFolder emAnalyticsFolder,
                                             @Mocked final Query query, @Mocked final PersistenceManager persistenceManager,
                                             @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext,
                                             @Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException {
        new Expectations() {
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
                query.setParameter(anyString, anyString);
                result = query;
//                query.getSingleResult();
//                result = emAnalyticsFolder;
            }
        };
        folderManager.getSubFolders(-1);
    }

    @Test
    public void testGetSubFoldersFolderIdLT0(@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final EmAnalyticsFolder emAnalyticsFolder,
                                             @Mocked final Query query, @Mocked final PersistenceManager persistenceManager,
                                             @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext,
                                             @Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException {
        final List<EmAnalyticsFolder> folderList = new ArrayList<>();
        folderList.add(emAnalyticsFolder);
        folderList.add(emAnalyticsFolder);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                EmAnalyticsObjectUtil.getFolderById(anyLong, entityManager);
                result = emAnalyticsFolder;
                tenantInfo.getUsername();
                result = "userName";
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.getResultList();
                result = folderList;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(111);
            }
        };
        folderManager.getSubFolders(1);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetSubFoldersException(@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final EmAnalyticsFolder emAnalyticsFolder,
                                           @Mocked final Query query, @Mocked final PersistenceManager persistenceManager,
                                           @Mocked final EntityManager entityManager, @Mocked TenantContext tenantContext,
                                           @Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException {
        final List<EmAnalyticsFolder> folderList = new ArrayList<>();
        folderList.add(emAnalyticsFolder);
        folderList.add(emAnalyticsFolder);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                EmAnalyticsObjectUtil.getFolderById(anyLong, entityManager);
                result = emAnalyticsFolder;
                tenantInfo.getUsername();
                result = "userName";
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.getResultList();
                result = folderList;
                emAnalyticsFolder.getSystemFolder();
                result = new EMAnalyticsFwkException(throwable);
            }
        };
        folderManager.getSubFolders(1);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testSaveFolderEMAnalyticsFwkException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                                      @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd((Folder) any, entityManager);
                result = emAnalyticsFolder;
            }
        };
        folderManager.saveFolder(new FolderImpl());
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testSaveFolderPersistenceException(@Mocked final PersistenceManager persistenceManager) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = new PersistenceException(throwable);
            }
        };
        folderManager.saveFolder(new FolderImpl());
    }

    @Test
    public void testSaveFolderNoneException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                            @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd((Folder) any, entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(11);
            }
        };
        folderManager.saveFolder(new FolderImpl());
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testSaveFolderException(@Mocked final PersistenceManager persistenceManager) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = new Exception(throwable);
            }
        };
        folderManager.saveFolder(new FolderImpl());
    }


    @Test
    public void testSaveMultipleFoldersEMAnalyticsFwkException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                                               @Mocked final Folder folder, @Mocked final FolderDetails folderDetails,
                                                               @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                folderDetails.getFolder();
                result = folder;
                folder.getId();
                result = 111;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder, entityManager);
                result = emAnalyticsFolder;

            }
        };
        List<FolderDetails> folderDetailsList = new ArrayList<>();
        folderDetailsList.add(folderDetails);
        folderDetailsList.add(folderDetails);
        folderManager.saveMultipleFolders(folderDetailsList);
    }

    @Test
    public void testSaveMultipleFoldersHasfolderId(@Mocked final FolderImpl folder, @Mocked final EmAnalyticsFolder emAnalyticsFolder,
                                                   @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final FolderDetails folderDetails,
                                                   @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                folderDetails.getFolder();
                result = folder;
                folder.getId();
                result = 111;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder, entityManager);
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
    public void testSaveMultipleFoldersNofolderId(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                  @Mocked final Query query, @Mocked final FolderImpl folder,
                                                  @Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                                  @Mocked final FolderDetails folderDetails, @Mocked final PersistenceManager persistenceManager,
                                                  @Mocked final EntityManager entityManager) throws Exception {

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                folderDetails.getFolder();
                result = folder;
                folder.getId();
                result = -111;
//                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder,entityManager);
//                result = emAnalyticsFolder;
                withAny(emAnalyticsFolder).getSystemFolder();
                result = new BigDecimal(1123);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.setParameter(anyString, (EmAnalyticsFolder) any);
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
    public void testSaveMultipleFoldersNoResultException(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                         @Mocked final Query query, @Mocked final FolderImpl folder,
                                                         @Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                                         @Mocked final FolderDetails folderDetails, @Mocked final PersistenceManager persistenceManager,
                                                         @Mocked final EntityManager entityManager) throws Exception {

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                folderDetails.getFolder();
                result = folder;
                folder.getId();
                result = -111;
//                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder,entityManager);
//                result = emAnalyticsFolder;
                withAny(emAnalyticsFolder).getSystemFolder();
                result = new BigDecimal(1123);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.setParameter(anyString, (EmAnalyticsFolder) any);
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

    @Test(expectedExceptions = {Exception.class})
    public void testSaveMultipleFoldersException(@Mocked final PersistenceManager persistenceManager) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = new Exception(throwable);
            }
        };
        List<FolderDetails> folderDetailsList = new ArrayList<>();
        folderManager.saveMultipleFolders(folderDetailsList);
    }

    @Test
    public void testSaveMultipleFoldersPersistenceException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                                            @Mocked final Folder folder, @Mocked final FolderDetails folderDetails,
                                                            @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                folderDetails.getFolder();
                result = folder;
                folder.getId();
                result = 111;
                folder.getName();
                result = "namexx";
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder, entityManager);
                result = new PersistenceException();

            }
        };
        List<FolderDetails> folderDetailsList = new ArrayList<>();
        folderDetailsList.add(folderDetails);
        folderDetailsList.add(folderDetails);
        folderManager.saveMultipleFolders(folderDetailsList);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testUpdateFolderEMAnalyticsFwkException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                                        @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit((Folder) any, entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(1);
            }
        };
        FolderImpl folder = new FolderImpl();
        folderManager.updateFolder(null);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testUpdateFolderProcessUniqueConstraints(@Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                         @Mocked final Query query,
                                                         @Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                                         @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit((Folder) any, entityManager);
                result = new PersistenceException(throwable);  
            }
        };
        FolderImpl folder = new FolderImpl();
        folder.setParentId(1234);
        folderManager.updateFolder(folder);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testUpdateFolderPersistenceException(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                     @Mocked final Query query, @Mocked final PersistenceException pe,
                                                     @Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                                     @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit((Folder) any, entityManager);
                result = pe;
                pe.getCause();
                result = null;
            }
        };
        FolderImpl folder = new FolderImpl();
        folder.setParentId(1234);
        folderManager.updateFolder(folder);
    }

    @Test
    public void testUpdateFolder(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                 @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit((Folder) any, entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(222);
            }
        };
        FolderImpl folder = new FolderImpl();
        folderManager.updateFolder(null);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testUpdateFolderException(@Mocked final EmAnalyticsFolder emAnalyticsFolder, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                          @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit((Folder) any, entityManager);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new Exception(throwable);
            }
        };
        FolderImpl folder = new FolderImpl();
        folderManager.updateFolder(folder);
    }

}