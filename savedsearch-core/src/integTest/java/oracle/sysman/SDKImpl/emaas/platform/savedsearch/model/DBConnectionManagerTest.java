package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
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
    public void testIsDatabaseConnectionAvailable(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManagerFactoryImpl entityManagerFactoryImpl, @Mocked final EntityManager entityManager, @Mocked final EJBQueryImpl ejbQuery) throws Exception {

        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManagerFactory();
                result = entityManagerFactoryImpl;
                entityManagerFactoryImpl.createEntityManager();
                result = entityManager;
                entityManager.createNativeQuery(anyString);
                result = ejbQuery;
                ejbQuery.getSingleResult();
                result = new BigDecimal(1);
            }
        };
        Assert.assertTrue(dbConenctionManager.isDatabaseConnectionAvailable());
    }
}