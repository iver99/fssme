package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategoryParam;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategoryParamPK;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by xidai on 3/7/2016.
 */
@Test(groups={"s2"})

public class EmAnalyticsObjectUtilTest {
    private static final BigInteger TEST_ID = BigInteger.TEN;
	@Mocked
    PersistenceManager persistenceManager;
    @Mocked
    EntityManager entityManager;
    @Mocked
    Query query;
    @Mocked
    TenantContext tenantContext;
    @Mocked
    TenantInfo tenantInfo;
    @Mocked
    EmAnalyticsCategory emAnalyticsCategory;
    @Mocked
    Category category;
    @Mocked
    EmAnalyticsCategoryParamPK emAnalyticsCategoryParamPK;
    @Mocked
    EmAnalyticsCategoryParam emAnalyticsCategoryParam;
    @Mocked
    Parameter parameter;
    @Mocked
    Folder folder;
    @Mocked
    EmAnalyticsFolder emAnalyticsFolder;
    @Mocked
    Search search;
    @Mocked
    EmAnalyticsSearch emAnalyticsSearch;
    @Mocked
    SearchParameter searchParameter;


    @Test
    public void testCanDeleteFolder() throws Exception {
        new Expectations() {
            {
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.getSingleResult();
                result = 1;
                TenantContext.getContext();
                result = tenantInfo;
            }
        };
        try {
            EmAnalyticsObjectUtil.canDeleteFolder(TEST_ID, entityManager);

        }catch(EMAnalyticsFwkException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testCanDeleteFolder2nd() throws Exception {
        new Expectations() {
            {
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.getSingleResult();
                result = -1;
                TenantContext.getContext();
                result = tenantInfo;
            }
        };
        try {
            EmAnalyticsObjectUtil.canDeleteFolder(TEST_ID, entityManager);

        }catch(EMAnalyticsFwkException e){
            Assert.assertTrue(true);
        }
    }


    @Test
    public void testGetCategoryByName() throws Exception {
        new Expectations() {
            {
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.getSingleResult();
                result = emAnalyticsCategory;
                TenantContext.getContext();
                result = tenantInfo;
            }
        };
        EmAnalyticsObjectUtil.getCategoryByName("name",entityManager);
    }

    @Test
    public void testGetEmAnalyticsCategoryForEdit() throws Exception {
        final List<Parameter> newParams = new ArrayList<Parameter>();
        newParams.add(parameter);
        new Expectations() {
            {
                category.getId();
                result = BigInteger.ONE;
                category.getName();
                result = "name";
                entityManager.find(EmAnalyticsCategory.class,  (BigInteger) any);
                result = emAnalyticsCategory;
            }
        };
        try {
            EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(category, entityManager);
        }catch(Exception e){

        }
    }


    @Test
    public void testGetEmAnalyticsFolderByFolderObject() throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(folder);
    }
    @Test
    public void testGetEmAnalyticsFolderByFolderObject2nd() throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                folder.getParentId();
                result =null;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(folder);
    }

    @Test
    public void testGetEmAnalyticsFolderByFolderObject3th() throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                folder.getParentId();
                result =BigInteger.ONE;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(folder);
    }

    @Test
    public void testGetEmAnalyticsFolderByFolderObject4th() throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                folder.getParentId();
                result =new NoResultException();
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(folder);
    }

    @Test
    public void testGetEmAnalyticsFolderForEdit() throws Exception {
        new Expectations(){
            {
                entityManager.find(EmAnalyticsFolder.class, (BigInteger) any);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = BigInteger.ZERO;
                folder.getParentId();
                result = BigInteger.ONE;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder,entityManager);
    }

    @Test
    public void testGetEmAnalyticsSearchForEdit() throws Exception {
        final List<SearchParameter> searchParameters = new ArrayList<>();
        searchParameters.add(searchParameter);
        new Expectations(){
            {
                entityManager.find(EmAnalyticsSearch.class, (BigInteger) any);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getDeleted();
                result = BigInteger.ZERO;
                entityManager.find(EmAnalyticsFolder.class, (BigInteger) any);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = BigInteger.ZERO;
                entityManager.find(EmAnalyticsCategory.class,  (BigInteger) any);
                result = emAnalyticsCategory;
                emAnalyticsCategory.getDeleted();
                result = BigInteger.ZERO;
                search.getParameters();
                result = searchParameters;
                search.getId();
                result = BigInteger.ONE;
                searchParameter.getType();
                result = ParameterType.CLOB;
                emAnalyticsSearch.getEmAnalyticsSearchParams();
                result = new HashSet<>();

            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit(search,entityManager);

    }

    @Test
    public void testGetEmAnalyticsSearchForAdd() throws Exception {
        final List<SearchParameter> searchParameters = new ArrayList<>();
        searchParameters.add(searchParameter);
        new Expectations(){
            {
                entityManager.find(EmAnalyticsFolder.class, (BigInteger) any);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = BigInteger.ZERO;
                entityManager.find(EmAnalyticsCategory.class,  (BigInteger) any);
                result = emAnalyticsCategory;
                emAnalyticsCategory.getDeleted();
                result = BigInteger.ZERO;
                search.getParameters();
                result = searchParameters;
                emAnalyticsSearch.getId();
                result = BigInteger.ONE;
                searchParameter.getType();
                result = ParameterType.CLOB;
                emAnalyticsSearch.getEmAnalyticsSearchParams();
                result = new HashMap<>();
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(search,entityManager);

    }

    @Test
    public void testGetEmAnalyticsSearchForAdd2nd() throws Exception {
        final List<SearchParameter> searchParameters = new ArrayList<>();
        searchParameters.add(searchParameter);
        new Expectations(){
            {
                entityManager.find(EmAnalyticsFolder.class, (BigInteger) any);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = BigInteger.ZERO;
                entityManager.find(EmAnalyticsCategory.class,  (BigInteger) any);
                result = emAnalyticsCategory;
                emAnalyticsCategory.getDeleted();
                result = BigInteger.ZERO;
                search.getParameters();
                result = searchParameters;
                emAnalyticsSearch.getId();
                result = BigInteger.ONE;
                emAnalyticsSearch.getEmAnalyticsSearchParams();
                result = new HashMap<>();
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(search,entityManager);

    }


    @Test
    public void testGetRootFolder() throws Exception {

        EmAnalyticsObjectUtil.getRootFolder();
    }



    @Test
    public void testGetFolderById() throws Exception {
        new Expectations(){
            {
                entityManager.find(withAny(EmAnalyticsFolder.class), (BigInteger) any);
                result = emAnalyticsFolder;
            }
        };
        EmAnalyticsObjectUtil.getFolderById(TEST_ID,entityManager);
    }

    @Test
    public void testGetCategoryById2nd() throws Exception {
        new Expectations(){
            {
                entityManager.find(withAny(EmAnalyticsCategory.class), (BigInteger) any);
                result = emAnalyticsCategory;
                emAnalyticsCategory.getDeleted();
                result = BigInteger.ONE;
            }
        };
        EmAnalyticsObjectUtil.getCategoryById(TEST_ID,entityManager);
    }


    @Test
    public void testGetFolderById2nd() throws Exception {
        new Expectations(){
            {
                entityManager.find(withAny(EmAnalyticsFolder.class), (BigInteger) any);
                result = null;
            }
        };
        EmAnalyticsObjectUtil.getFolderById(TEST_ID,entityManager);
    }

    @Test
    public void testGetSearchById() throws Exception {
        new Expectations(){
            {
                entityManager.find(withAny(EmAnalyticsSearch.class), (BigInteger) any);
                result = emAnalyticsSearch;
            }
        };
        EmAnalyticsObjectUtil.getSearchById(TEST_ID,entityManager);

    }
    @Test
    public void testGetSearchById2nd() throws Exception {
        new Expectations(){
            {
                entityManager.find(withAny(EmAnalyticsSearch.class), (BigInteger) any);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getDeleted();
                result = BigInteger.ONE;
            }
        };
        EmAnalyticsObjectUtil.getSearchById(TEST_ID,entityManager);

    }

    @Test
    public void testGetCategoryByIdForDelete() throws Exception {
        new Expectations(){
            {
                entityManager.find(withAny(EmAnalyticsCategory.class), (BigInteger) any);
                result = emAnalyticsCategory;
                emAnalyticsCategory.getOwner();
                result = "ORACLE";
            }
        };
        EmAnalyticsObjectUtil.getCategoryByIdForDelete(TEST_ID,entityManager);

    }

    @Test
    public void testGetCategoryByIdForDelete2nd() throws Exception {
        new Expectations(){
            {
                entityManager.find(withAny(EmAnalyticsCategory.class), (BigInteger) any);
                result = null;
            }
        };
        EmAnalyticsObjectUtil.getCategoryByIdForDelete(TEST_ID,entityManager);

    }

    @Test
    public void testGetFolderByIdForDelete() throws Exception {
        new Expectations(){
            {
                entityManager.find(withAny(EmAnalyticsFolder.class), (BigInteger) any);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(1);
            }
        };
        EmAnalyticsObjectUtil.getFolderByIdForDelete(TEST_ID,entityManager);

    }
    @Test
    public void testGetFolderByIdForDelete2nd() throws Exception {
        new Expectations(){
            {
                entityManager.find(withAny(EmAnalyticsFolder.class), (BigInteger) any);
                result =  null;
            }
        };
        EmAnalyticsObjectUtil.getFolderByIdForDelete(TEST_ID,entityManager);

    }

    @Test
    public void testGetSearchByIdForDelete() throws Exception {
        new Expectations(){
            {
                entityManager.find(withAny(EmAnalyticsSearch.class), (BigInteger) any);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new BigDecimal(1);
            }
        };
        EmAnalyticsObjectUtil.getSearchByIdForDelete(TEST_ID,entityManager);

    }
    @Test
    public void testGetSearchByIdForDelete2nd() throws Exception {
        new Expectations(){
            {
                entityManager.find(withAny(EmAnalyticsSearch.class), (BigInteger) any);
                result =  null;
            }
        };
        EmAnalyticsObjectUtil.getSearchByIdForDelete(TEST_ID,entityManager);

    }

    @Test
    public void testGetEmAnalyticsCategoryForAdd(){
        final List<Parameter> parameters = new ArrayList<>();
        parameters.add(parameter);
        new Expectations(){
            {
                category.getParameters();
                result = parameters;
                emAnalyticsCategory.getEmAnalyticsCategoryParams();
                result = new HashSet<>();
            }
        };
        try {
            EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd(category,entityManager);
        } catch (EMAnalyticsFwkException e) {

        }
    }
}