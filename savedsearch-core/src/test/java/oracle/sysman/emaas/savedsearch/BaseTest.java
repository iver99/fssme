package oracle.sysman.emaas.savedsearch;

import org.testng.annotations.Test;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;

public class BaseTest
{
	protected static PersistenceManager pm;

	@Test
	public void getConnection()
	{
		PersistenceManager.IS_TEST_ENV = true;
	}
}
