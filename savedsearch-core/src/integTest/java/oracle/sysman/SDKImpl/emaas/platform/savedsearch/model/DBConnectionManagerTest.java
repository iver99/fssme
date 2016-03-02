package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.MockUp;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl;
import org.eclipse.persistence.internal.sessions.DatabaseSessionImpl;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xidai on 3/2/2016.
 */
@Test(groups = {"s2"})
public class DBConnectionManagerTest {
    private DBConnectionManager dbConenctionManager = DBConnectionManager.getInstance();

    @BeforeTest
    private void setUp() throws Exception
    {
        System.setProperty("SSF.INTERNAL.HUDSON.TESTENV","true");
        Deencapsulation.setField(PersistenceManager.class,"IS_TEST_ENV",true);
    }

    @Test
    public void testIsDatabaseConnectionAvailable(@Mocked final Persistence anyPersistence) throws Exception {
        new Expectations(){
            {
                Deencapsulation.invoke(anyPersistence,"createEntityManagerFactory",anyString,withAny(new HashMap()));
                result = new EntityManagerFactoryImpl(new String("EmaasAnalyticsPublicModelTest"),new HashMap(),new ArrayList());
            }
        };
        PersistenceManager.getInstance();
        new MockUp<PersistenceManager>(){
            public EntityManagerFactory getEntityManagerFactory()
            {
                return new EntityManagerFactoryImpl(new DatabaseSessionImpl());
            }
        };
//        new MockUp<Persistence>(){
//            public EntityManagerFactory getEntityManagerFactory()
//            {
//                return new EntityManagerFactoryImpl(new DatabaseSessionImpl());
//            }
//        };
        dbConenctionManager.isDatabaseConnectionAvailable();
    }
}