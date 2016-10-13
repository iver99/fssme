package oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence;

import mockit.*;

import java.util.Properties;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Created by xidai on 3/2/2016.
 */
@Test(groups={"s2"})
public class PersistenceManagerTest {
    private PersistenceManager persistenceManager;

    @Mocked
    System system;
    @Mocked
    Properties properties;
    @Mocked
    QAToolUtil qaToolUtil;
    @Mocked
    Persistence persistence;
    @Mocked
    EntityManagerFactory entityManagerFactory;
    @Mocked
    EntityManager entityManager;
    @Mocked
    TenantInfo tenantInfo;
    @BeforeMethod
    private void setUp()
    {
        System.setProperty("SSF.INTERNAL.HUDSON.TESTENV","true");
        Deencapsulation.setField(PersistenceManager.class,"IS_TEST_ENV",true);
    }

    @Test
    public void testPersistenceManager(){
        PersistenceManager pm = PersistenceManager.getInstance();
        Assert.assertNotNull(pm.getEntityManager(tenantInfo));
    }
}
