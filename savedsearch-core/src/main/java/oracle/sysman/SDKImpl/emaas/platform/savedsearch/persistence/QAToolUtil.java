/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence;

import java.util.Properties;

/**
 * @author vinjoshi
 */
public class QAToolUtil
{

	private static final String USER_NAME = "EMSAAS_SAVEDSRCH";
	private static final String PASSWORD = "welcome1";
	public static final String JDBC_URL = "jdbc:oracle:thin:@";

	public static final String ODS_HOSTNAME = "ODS_HOSTNAME";
	public static final String ODS_PORT = "ODS_PORT";
	public static final String ODS_SERVICE = "ODS_SERVICE";

	public static final String JDBC_PARAM_URL = "javax.persistence.jdbc.url";
	public static final String JDBC_PARAM_USER = "javax.persistence.jdbc.user";
	public static final String JDBC_PARAM_PASSWORD = "javax.persistence.jdbc.password";
	public static final String JDBC_PARAM_DRIVER = "javax.persistence.jdbc.driver";
	public static final String JDBC_PARAM_DRIVER_VALUE = "oracle.jdbc.OracleDriver";
	public static final String TENANT_USER_NAME = "TENANT_USER_NAME";
	public static final String TENANT_NAME = "TENANT_NAME";

	public static Properties getDbProperties()
	{
		Properties props = new Properties();
		String url = JDBC_URL + Utils.getProperty(ODS_HOSTNAME) + ":" + Utils.getProperty(ODS_PORT) + ":"
				+ Utils.getProperty(ODS_SERVICE);
		props.put(JDBC_PARAM_URL, url);
		props.put(JDBC_PARAM_USER, USER_NAME);
		props.put(JDBC_PARAM_PASSWORD, PASSWORD);
		props.put(JDBC_PARAM_DRIVER, JDBC_PARAM_DRIVER_VALUE);
		return props;
	}

	public static Properties getTenantDetails()
	{
		Properties props = new Properties();
		props.put(TENANT_USER_NAME, Utils.getProperty("TENANT_ID_INTERNAL") + "." + Utils.getProperty("SSO_USERNAME"));
		props.put(TENANT_NAME, Utils.getProperty("TENANT_ID_INTERNAL"));
		return props;
	}
}
