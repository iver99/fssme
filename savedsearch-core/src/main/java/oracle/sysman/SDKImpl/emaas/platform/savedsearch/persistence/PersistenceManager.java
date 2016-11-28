package oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

public class PersistenceManager
{
	private static class PersistenceManagerHelper
	{
		private static final PersistenceManager SINGLETON = new PersistenceManager();

		private PersistenceManagerHelper() {
		}
	}

	/**
	 * An internal property to know whether test env is in use true: test env is in use null or non-true: test env is NOT in use
	 */
	public static final String TESTENV_PROP = "SSF.INTERNAL.TESTENV";

	public static final String TESTENV_HUDSON_PROP = "SSF.INTERNAL.HUDSON.TESTENV";

	/**
	 * For the whole JVM life cycle, IS_TEST_ENV can only be set once
	 */
	private static Boolean IS_TEST_ENV = null;

	private static final String PERSISTENCE_UNIT = "EmaasAnalyticsPublicModel";
	private static final String TEST_PERSISTENCE_UNIT = "EmaasAnalyticsPublicModelTest";
	private static final String CONNECTION_PROPS_FILE = "TestNG.properties";
	private static final String TENANT_ID_STR = "tenant";



	public static PersistenceManager getInstance()
	{

		return PersistenceManagerHelper.SINGLETON;
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
			Properties props = null;
			String sresult = System.getProperty(TESTENV_HUDSON_PROP, "false");

			boolean bResult = "true".equalsIgnoreCase(sresult);
			if (bResult) {
				props = loadProperties(CONNECTION_PROPS_FILE);
			}

			if (System.getenv("T_WORK") != null && !bResult) {
				props = QAToolUtil.getDbProperties();
			}
			createEntityManagerFactory(TEST_PERSISTENCE_UNIT, props);
		}
		else {
			createEntityManagerFactory(PERSISTENCE_UNIT, null);
		}
	}

	public EntityManager getEntityManager(TenantInfo value)
	{
		Map<String, String> emProperties = new HashMap<String, String>();
		emProperties.put(TENANT_ID_STR, String.valueOf(value.getTenantInternalId()));
		//return emf.createEntityManager(emProperties);
		EntityManager em = emf.createEntityManager(emProperties);
		//em.setProperty("tenant.id", value.getTenantInternalId());
		return em;
		
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
		finally {
			if (input != null) {
				try {
					input.close();
				}
				catch (Exception e) {
					//ignore exception
				}
			}
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
