package oracle.sysman.emaas.savedsearch;

import java.util.Properties;
import java.util.TimeZone;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;

/**
 * Each test needs to inherit from this
 *
 * @author miao
 */
public abstract class BaseTest
{
	protected static PersistenceManager pm;
	static {
		System.setProperty(PersistenceManager.TESTENV_PROP, "true");
		DateUtil.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		// set db env to system property for running test cases
		Properties props = QAToolUtil.getDbProperties();
//		if (props == null) {
//			System.setProperty(PersistenceManager.JDBC_PARAM_URL, props.getProperty(QAToolUtil.JDBC_PARAM_URL));
//			System.setProperty(PersistenceManager.JDBC_PARAM_USER, props.getProperty(QAToolUtil.JDBC_PARAM_USER));
//			System.setProperty(PersistenceManager.JDBC_PARAM_PASSWORD, props.getProperty(QAToolUtil.JDBC_PARAM_PASSWORD));
//			System.setProperty(PersistenceManager.JDBC_PARAM_DRIVER, props.getProperty(QAToolUtil.JDBC_PARAM_DRIVER));


			System.setProperty(PersistenceManager.JDBC_PARAM_URL, "jdbc:oracle:thin:@slc10uam.us.oracle.com:1521:orcl12c");
			System.setProperty(PersistenceManager.JDBC_PARAM_USER, "SYSEMS_T_1010");
			System.setProperty(PersistenceManager.JDBC_PARAM_PASSWORD, "welcome1");
			System.setProperty(PersistenceManager.JDBC_PARAM_DRIVER, "oracle.jdbc.OracleDriver");
//		}
	}
}
