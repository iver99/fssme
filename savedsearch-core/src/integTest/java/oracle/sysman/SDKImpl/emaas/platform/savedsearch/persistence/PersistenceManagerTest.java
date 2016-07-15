package oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence;

import mockit.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import org.openqa.jetty.html.Input;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Created by xidai on 3/2/2016.
 */
@Test
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
    private void setUp() throws Exception
    {
        System.setProperty("SSF.INTERNAL.HUDSON.TESTENV","true");
        Deencapsulation.setField(PersistenceManager.class,"IS_TEST_ENV",true);
    }

    @Test
    public void testPersistenceManager() throws IOException {
        PersistenceManager pm = PersistenceManager.getInstance();
        Assert.assertNotNull(pm.getEntityManager(tenantInfo));
    }
}
