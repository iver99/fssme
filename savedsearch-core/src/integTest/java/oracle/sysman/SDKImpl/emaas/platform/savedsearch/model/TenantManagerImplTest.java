package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantManager;

import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class TenantManagerImplTest {
    @Mocked
    PersistenceManager persistenceManager;
    @Mocked
    EntityManager entityManager;
    @Mocked
    EntityTransaction entityTransaction;
    
    @Test
    public void testCleanTenant() throws EMAnalyticsFwkException {
        TenantManager tm = TenantManager.getInstance();
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo)any);
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.isActive();
                result = false;
            }
        };
        tm.cleanTenant(1L);
    }
}
