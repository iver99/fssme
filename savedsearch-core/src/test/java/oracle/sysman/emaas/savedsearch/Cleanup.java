package oracle.sysman.emaas.savedsearch;

import org.testng.annotations.Test;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;

public class Cleanup
{
	@Test
	public void closeConnection()
	{
		PersistenceManager pm = PersistenceManager.getInstance();
		pm.closeEntityManagerFactory();
	}

}
