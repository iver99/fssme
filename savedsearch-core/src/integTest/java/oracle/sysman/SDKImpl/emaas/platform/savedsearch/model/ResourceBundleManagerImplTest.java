package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.Expectations;
import mockit.Mock;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ResourceBundleManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.entity.EmsResourceBundle;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2017/6/23.
 */
public class ResourceBundleManagerImplTest {
    @Mocked
    PersistenceManager persistenceManager;
    @Mocked
    EntityManager entityManager;
    @Mocked
    Query query;
    @Test
    public void testStoreResourceBundle() throws Exception {
        List<EmsResourceBundle> emsResourceBundles = new ArrayList<>();
        final EmsResourceBundle emsResourceBundle = new EmsResourceBundle();
        emsResourceBundles.add(emsResourceBundle);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo)any);
                result = entityManager;
                entityManager.getTransaction().isActive();
                result = true;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                query.executeUpdate();
                entityManager.getTransaction().commit();
                entityManager.persist(emsResourceBundle);
            }
        };
        ResourceBundleManager resourceBundleManager = new ResourceBundleManagerImpl();
        resourceBundleManager.storeResourceBundle("serviceName",emsResourceBundles);
    }

}