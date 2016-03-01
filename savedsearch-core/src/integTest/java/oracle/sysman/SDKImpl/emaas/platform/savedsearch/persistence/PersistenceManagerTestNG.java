package oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.Persistence;
import java.util.Properties;

/**
 * @author jishshi
 * @since 2016/2/29.
 */
@Test(groups = {"s2"})
public class PersistenceManagerTestNG {
    private static final String TESTENV_HUDSON_PROP = "SSF.INTERNAL.HUDSON.TESTENV";
    PersistenceManager persistenceManager;

    @Mocked
    Persistence persistence;

    @BeforeMethod
    public void setUp() throws Exception {
        persistenceManager = PersistenceManager.getInstance();
    }

    @Test
    public void testGetInstance() throws Exception {
        Assert.assertNotNull(persistenceManager);
    }

    @Test
    public void testCloseEntityManagerFactory() throws Exception {
        Assert.assertNotNull(Deencapsulation.getField(persistenceManager, "emf"));
        persistenceManager.closeEntityManagerFactory();
        Assert.assertNull(Deencapsulation.getField(persistenceManager, "emf"));
    }

    @Test
    public void testCreateEntityManagerFactory(@Mocked final System system) throws Exception {
        new Expectations() {
            {
                System.getProperty(anyString,anyString);
                result = "true";
            }
        };

        persistenceManager.createEntityManagerFactory();
        Deencapsulation.setField(persistenceManager, "IS_TEST_ENV", true);
        persistenceManager.createEntityManagerFactory();

    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testGetEntityManagerFactoryException() {
        Deencapsulation.setField(persistenceManager, "emf", null);
        persistenceManager.getEntityManagerFactory();

    }

}