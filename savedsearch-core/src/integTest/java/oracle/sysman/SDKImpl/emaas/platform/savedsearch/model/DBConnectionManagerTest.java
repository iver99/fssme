package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.*;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;

/**
 * Created by xidai on 3/2/2016.
 */
@Test(groups = {"s2"})
public class DBConnectionManagerTest {
    private DBConnectionManager dbConenctionManager = DBConnectionManager.getInstance();

    @BeforeMethod
    private void setUp() throws Exception
    {
        System.setProperty("SSF.INTERNAL.HUDSON.TESTENV","true");
        Deencapsulation.setField(PersistenceManager.class,"IS_TEST_ENV",true);
    }

    @Test
    public void testIsDatabaseConnectionAvailable(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManagerFactoryImpl entityManagerFactoryImpl, @Mocked final EntityManager entityManager) throws Exception {

        final Query query = new MockUp<Query>(){
            @Mock
            Object getSingleResult(){
              return new BigDecimal(1);
            };
        }.getMockInstance();

        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManagerFactory();
                result = entityManagerFactoryImpl;
                entityManagerFactoryImpl.createEntityManager();
                result = entityManager;
                entityManager.createNativeQuery(anyString);
                result = query;
            }
        };
        Assert.assertTrue(dbConenctionManager.isDatabaseConnectionAvailable());
    }
}