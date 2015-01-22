package oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager
{
	/**
	 * An internal property to know whether test env is in use true: test env is in use null or non-true: test env is NOT in use
	 */
	public static final String TESTENV_PROP = "SSF.INTERNAL.TESTENV";
	/**
	 * For the whole JVM life cycle, IS_TEST_ENV can only be set once
	 */
	private static Boolean IS_TEST_ENV = null;

	private static final String PERSISTENCE_UNIT = "EmaasAnalyticsPublicModel";

	private static final String TEST_PERSISTENCE_UNIT = "EmaasAnalyticsPublicModelTest";
	private static final String CONNECTION_PROPS_FILE = "TestNG.properties";
	private static PersistenceManager singleton;
	private static final String TENANT_ID_STR = "ssftenant.id";
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

	private EntityManagerFactory emf;

	private PersistenceManager()
	{
		createEntityManagerFactory();
	}

	public synchronized void closeEntityManagerFactory()
	{
		if (emf != null) {
			emf.close();
			emf = null;
		}
	}

	/**
	 * If EntityManagerFactory is closed by closeEntityManagerFactory(), you have to create it explicitly by this method,
	 * otherwise getEntityManager
	 */
	public synchronized void createEntityManagerFactory()
	{
		if (IS_TEST_ENV == null) {
			String testEnvVal = System.getProperty(TESTENV_PROP, "false");
			IS_TEST_ENV = "true".equalsIgnoreCase(testEnvVal);
		}

		if (IS_TEST_ENV) {
			Properties props = loadProperties(CONNECTION_PROPS_FILE);
			createEntityManagerFactory(TEST_PERSISTENCE_UNIT, props);
		}
		else {
			createEntityManagerFactory(PERSISTENCE_UNIT, null);
		}
	}

	public EntityManager getEntityManager(String value)
	{
		Map<String, String> emProperties = new HashMap<String, String>();
		emProperties.put(TENANT_ID_STR, value);
		return emf.createEntityManager(emProperties);
	}

	public EntityManagerFactory getEntityManagerFactory()
	{
		if (emf == null || !emf.isOpen()) {
			throw new RuntimeException("Failed to get EntityManagerFactory, which is closed!");
		}
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

	protected void createEntityManagerFactory(String puName, Properties props)
	{

		if (emf == null || !emf.isOpen()) {
			emf = Persistence.createEntityManagerFactory(puName, props);
		}

	}

}
