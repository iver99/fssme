package oracle.sysman.emaas.savedsearch;

import javax.persistence.EntityManagerFactory;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PersistenceManagerTest extends BaseTest
{
	@Test
	public void testAll()
	{
		PersistenceManager pm = PersistenceManager.getInstance();
		Assert.assertNotNull(pm, "Got NULL PersistenceManager instance");

		EntityManagerFactory emf = pm.getEntityManagerFactory();
		Assert.assertNotNull(emf, "Got NULL EntityManagerFactory instance");

		pm.closeEntityManagerFactory();

		try {
			emf = pm.getEntityManagerFactory();
			Assert.fail("EntityManagerFactory should NOT be got after it is closed by PersistenceManager.closeEntityManagerFactory()");
		}
		catch (Exception e) {
			//expected here
		}

		pm.createEntityManagerFactory();

		emf = pm.getEntityManagerFactory();
		Assert.assertNotNull(emf, "Got NULL EntityManagerFactory instance (2)");

	}
}
