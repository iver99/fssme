package oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.SessionInfoUtil;
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

	/** 
	 * mapping to QAToolUtil
	 * Picking up db env when running test cases
	 */
	public static final String JDBC_PARAM_URL = "javax.persistence.jdbc.url";
	public static final String JDBC_PARAM_USER = "javax.persistence.jdbc.user";
	public static final String JDBC_PARAM_PASSWORD = "javax.persistence.jdbc.password";
	public static final String JDBC_PARAM_DRIVER = "javax.persistence.jdbc.driver";
	
	private static final String MODULE_NAME = "SavedSearchService"; // application service name
	private final String ACTION_NAME = this.getClass().getSimpleName();//current class name



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
			Properties props = new Properties();
			String sresult = System.getProperty(TESTENV_HUDSON_PROP, "false");

			boolean bResult = "true".equalsIgnoreCase(sresult);
			if (bResult) {
				props = loadProperties(CONNECTION_PROPS_FILE);
			}

			if (System.getenv("T_WORK") != null && !bResult) {
				props.put(PersistenceManager.JDBC_PARAM_URL, System.getProperty(PersistenceManager.JDBC_PARAM_URL));
				props.put(PersistenceManager.JDBC_PARAM_USER, System.getProperty(PersistenceManager.JDBC_PARAM_USER));
				props.put(PersistenceManager.JDBC_PARAM_PASSWORD, System.getProperty(PersistenceManager.JDBC_PARAM_PASSWORD));
				props.put(PersistenceManager.JDBC_PARAM_DRIVER, System.getProperty(PersistenceManager.JDBC_PARAM_DRIVER));
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
		EntityManager em = emf.createEntityManager(emProperties);
		try {
			SessionInfoUtil.setModuleAndAction(em, MODULE_NAME, ACTION_NAME);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
