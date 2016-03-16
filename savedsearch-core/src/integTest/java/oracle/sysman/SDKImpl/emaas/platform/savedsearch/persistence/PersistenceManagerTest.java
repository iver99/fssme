package oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xidai on 3/2/2016.
 */
@Test(groups={""})
public class PersistenceManagerTest {

    @BeforeMethod
    private void setUp() throws Exception
    {
        System.setProperty("SSF.INTERNAL.HUDSON.TESTENV","true");
        Deencapsulation.setField(PersistenceManager.class,"IS_TEST_ENV",true);
    }

    @Test
    public void testPersistenceManager(@Mocked final Persistence anyPersistence)
    {
        new Expectations(){
            {
                Deencapsulation.invoke(anyPersistence,"createEntityManagerFactory",anyString,withAny(new HashMap()));
                result = new EntityManagerFactoryImpl(new String("EmaasAnalyticsPublicModelTest"),new HashMap(),new ArrayList());
            }
        };
        long testTenant = 3L;
        PersistenceManager pm = PersistenceManager.getInstance();
        Assert.assertNotNull(pm.getEntityManagerFactory());
        pm.closeEntityManagerFactory();
    }
}