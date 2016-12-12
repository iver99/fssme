package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearchParam;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by xidai on 6/29/2016.
 */
@Test(groups={"s2"})
public class SearchManagerImplForTargetCardTest {
	private BigInteger TEST_ID = new BigInteger("10000");
    private SearchManager searchManager;
    @Mocked
    TenantContext tenantContext;
    @Mocked
    TenantInfo tenantInfo;
    @Mocked
    PersistenceManager persistenceManager;
    @Mocked
    EntityManager entityManager;
    @Mocked
    EmAnalyticsSearch emAnalyticsSearch;
    @Mocked
    EntityTransaction entityTransaction;
    @Mocked
    EmAnalyticsObjectUtil emAnalyticsObjectUtil;
    @Mocked
    Search search;
    @Mocked
    Query query;
    @BeforeMethod
    public void setUp(){
        searchManager =  SearchManager.getInstance();
    }

    @Test
    public void testDeleteTargetCard() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getSearchById((BigInteger) any,withAny(entityManager));
                result = emAnalyticsSearch;
                EmAnalyticsObjectUtil.getSearchByIdForDelete((BigInteger) any, withAny(entityManager));
                result =emAnalyticsSearch;
                emAnalyticsSearch.setDeleted((BigInteger) any);
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                entityManager.remove(emAnalyticsSearch);
                entityManager.merge(emAnalyticsSearch);
                entityTransaction.commit();
                entityManager.close();
            }
        };
        searchManager.deleteTargetCard(TEST_ID,false);
        searchManager.deleteTargetCard(TEST_ID,true);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testDeleteTargetCardEMAnalyticsFwkException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getSearchById((BigInteger) any,withAny(entityManager));
                result = null;
            }
        };
        searchManager.deleteTargetCard(TEST_ID,false);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testDeleteTargetCardException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getSearchById((BigInteger) any,withAny(entityManager));
                result = emAnalyticsSearch;
                entityManager.getTransaction();
                result = new Exception();
            }
        };
        searchManager.deleteTargetCard(TEST_ID,false);
    }
    @Mocked
    EmAnalyticsCategory emAnalyticsCategory;
    @Mocked
    EmAnalyticsFolder emAnalyticsFolder;
    @Test
    public void testSaveTargetCard() throws EMAnalyticsFwkException {
        final Set<EmAnalyticsSearchParam> emAnalyticsSearchParamSet = new HashSet<>();
        EmAnalyticsSearchParam emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("WIDGET_VISUAL");
        emAnalyticsSearchParam.setParamValueClob("new_ParamValue");
        emAnalyticsSearchParam.setParamType(new BigDecimal(1));
        emAnalyticsSearchParamSet.add(emAnalyticsSearchParam);
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(search,entityManager);
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                entityManager.persist(emAnalyticsSearch);
                entityTransaction.commit();
                emAnalyticsSearch.getId();
                result = 1L;
                emAnalyticsSearch.getName();
                result ="name";
                emAnalyticsSearch.getDescription();
                result = "description";
                emAnalyticsSearch.getOwner();
                result = "Owner";
                emAnalyticsSearch.getCreationDate();
                result = new Date();
                emAnalyticsSearch.getLastModifiedBy();
                result = "lastmodifiedby";
                emAnalyticsSearch.getLastModificationDate();
                result = new Date();
                emAnalyticsSearch.getMetadataClob();
                result = "meta_clob";
                emAnalyticsSearch.getSearchDisplayStr();
                result = "search_display_str";
                emAnalyticsSearch.getIsLocked();
                result = new BigDecimal(0);
                emAnalyticsSearch.getEmAnalyticsCategory();
                result = emAnalyticsCategory;
                emAnalyticsCategory.getCategoryId();
                result = 1L;
                emAnalyticsSearch.getEmAnalyticsFolder();
                result = emAnalyticsFolder;
                emAnalyticsFolder.getFolderId();
                result = 1L;
                emAnalyticsSearch.getUiHidden();
                result = new BigDecimal(1);
                emAnalyticsSearch.getSystemSearch();
                result = new BigDecimal(0);
                emAnalyticsSearch.getIsWidget();
                result = 0L;
                emAnalyticsSearch.getEmAnalyticsSearchParams();
                result = emAnalyticsSearchParamSet;
                entityManager.close();
            }
        };
        searchManager.saveTargetCard(search);
    }
    @Mocked
    Throwable throwable;
    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testSaveTargetCardEMAnalyticsFwkException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = new EMAnalyticsFwkException(throwable);
            }
        };
        searchManager.saveTargetCard(search);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testSaveTargetCardPersistenceException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = new PersistenceException(throwable);
            }
        };
        searchManager.saveTargetCard(search);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testSaveTargetCardException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = new Exception(throwable);
            }
        };
        searchManager.saveTargetCard(search);
    }
    @Test
    public void testGetTargetCard() throws EMAnalyticsFwkException {
        final ArrayList<Search> searches = new ArrayList<>();
        final ArrayList<EmAnalyticsSearch> emAnalyticsSearches = new ArrayList<>();
        searches.add(search);
        emAnalyticsSearches.add(emAnalyticsSearch);
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyString);
                query.getResultList();
                result = emAnalyticsSearches;
                entityManager.close();
            }
        };
        searchManager.getTargetCard("name");
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetTargetCardException() throws EMAnalyticsFwkException {
        final ArrayList<Search> searches = new ArrayList<>();
        final ArrayList<EmAnalyticsSearch> emAnalyticsSearches = new ArrayList<>();
        searches.add(search);
        emAnalyticsSearches.add(emAnalyticsSearch);
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyString);
                query.getResultList();
                result =  new Exception();
            }
        };
        searchManager.getTargetCard("name");
    }
}