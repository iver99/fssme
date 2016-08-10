package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by xidai on 6/29/2016.
 */
@Test(groups={"s2"})
public class SearchManagerImplForTargetCardTest {
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
                EmAnalyticsObjectUtil.getSearchById(anyLong,withAny(entityManager));
                result = emAnalyticsSearch;
                EmAnalyticsObjectUtil.getSearchByIdForDelete(anyLong, withAny(entityManager));
                result =emAnalyticsSearch;
                emAnalyticsSearch.setDeleted(anyLong);
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                entityManager.remove(emAnalyticsSearch);
                entityManager.merge(emAnalyticsSearch);
                entityTransaction.commit();
                entityManager.close();
            }
        };
        searchManager.deleteTargetCard(10000,false);
        searchManager.deleteTargetCard(10000,true);
    }

    @Test
    public void testSaveTargetCard() throws EMAnalyticsFwkException {
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
                emAnalyticsSearch.getNameNlsid();
                result = "mls";
                emAnalyticsSearch.getNameSubsystem();
                result = "NameSystem";
                emAnalyticsSearch.getName();
                result ="name";
                emAnalyticsSearch.getDescriptionNlsid();
                result = "nls";
                emAnalyticsSearch.getNameSubsystem();
                result = "DesSystem";
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
                emAnalyticsSearch.getAccessDate();
                result = new Date();
                entityManager.close();
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
}