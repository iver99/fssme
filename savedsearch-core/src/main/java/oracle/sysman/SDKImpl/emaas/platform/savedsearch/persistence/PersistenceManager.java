package oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager
{
	/**
	 * For the whole JVM life cycle, IS_TEST_ENV can only be set once
	 */
	private static Boolean IS_TEST_ENV = null;

	private static final String PERSISTENCE_UNIT = "EmaasAnalyticsPublicModel";

	private static final String TEST_PERSISTENCE_UNIT = "EmaasAnalyticsPublicModelTest";
	private static final String CONNECTION_PROPS_FILE = "TestNG.properties";
	private static PersistenceManager singleton;
	private static Object lock = new Object();

	public static PersistenceManager getInstance()
	{
		if (singleton == null) {
			synchronized (lock) {
				if (singleton == null) {
					singleton = new PersistenceManager();
				}
			}
		}
		return singleton;
	}

	public static void setTestEnv(boolean value)
	{
		synchronized (lock) {
			if (IS_TEST_ENV == null) {
				IS_TEST_ENV = true;
			}
		}
	}

	private EntityManagerFactory emf;

	private PersistenceManager()
	{
		if (IS_TEST_ENV == null) {
			IS_TEST_ENV = false;
		}

		if (IS_TEST_ENV) {
			Properties props = loadProperties(CONNECTION_PROPS_FILE);
			createEntityManagerFactory(TEST_PERSISTENCE_UNIT, props);
		}
		else {
			createEntityManagerFactory(PERSISTENCE_UNIT, null);
		}

	}

	public synchronized void closeEntityManagerFactory()
	{
		if (emf != null) {
			emf.close();
			emf = null;
		}
	}

	public EntityManagerFactory getEntityManagerFactory()
	{
		return emf;
	}

	private Properties loadProperties(String testPropsFile)
	{
		Properties connectionProps = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(testPropsFile);
			connectionProps.load(input);
			return connectionProps;
		}
		catch (Exception ex) {
			ex.printStackTrace();

		}
		return connectionProps;

	}

	protected synchronized void createEntityManagerFactory(String puName, Properties props)
	{
		emf = Persistence.createEntityManagerFactory(puName, props);
	}

}
