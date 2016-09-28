package oracle.sysman.emaas.savedsearch;

import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManagerTest extends BaseTest
{
	@Test (groups = {"s2"},expectedExceptions = RuntimeException.class,expectedExceptionsMessageRegExp ="Failed to get EntityManagerFactory, which is closed!" )
	public void testAll(@Mocked final Persistence persistence,@Mocked EntityManagerFactory entityManagerFactory)
	{

		PersistenceManager pm = PersistenceManager.getInstance();
		Assert.assertNotNull(pm, "Got NULL PersistenceManager INSTANCE");

		EntityManagerFactory emf = pm.getEntityManagerFactory();
		Assert.assertNotNull(emf, "Got NULL EntityManagerFactory INSTANCE");

		pm.closeEntityManagerFactory();

		try {
			emf = pm.getEntityManagerFactory();
			Assert.fail("EntityManagerFactory should NOT be got after it is closed by PersistenceManager.closeEntityManagerFactory()");
		}catch (Exception e) {
			//expected here
		}

		pm.createEntityManagerFactory();

		emf = pm.getEntityManagerFactory();
		Assert.assertNotNull(emf, "Got NULL EntityManagerFactory INSTANCE (2)");

	}
}
