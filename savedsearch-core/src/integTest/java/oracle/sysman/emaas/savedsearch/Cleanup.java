package oracle.sysman.emaas.savedsearch;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;

import org.testng.annotations.Test;

public class Cleanup extends BaseTest
{
	@Test
	public void closeConnection()
	{
		PersistenceManager pm = PersistenceManager.getInstance();
		pm.closeEntityManagerFactory();
	}

}
