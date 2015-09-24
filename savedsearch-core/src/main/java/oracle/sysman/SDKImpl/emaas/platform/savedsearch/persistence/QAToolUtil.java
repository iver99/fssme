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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oracle.sysman.qatool.uifwk.utils.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author vinjoshi
 */
public class QAToolUtil
{

	private static final String AUTHORIZATION = "Authorization";
	private static final String PATTERN = "((https?|www):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
	private static final String LIFE_CYCLE = "LifecycleInvManager";

	private static final String AUTH_STRING = "Basic d2VibG9naWM6d2VsY29tZTE=";//WEBLOGIC_USER + ":" + WEBLOGIC_USER;
	private static final String DEPLOY_URL = "/instances?servicename=LifecycleInventoryService";
	private static final String SSF_DEPLOY_URL = "/instances?servicename=SavedSearch";
	private static final String DEPLY_SCHEMA = "/schemaDeployments?softwareName=SavedSearchService";
	private static final String SERVICE_NAME = "SavedSearchService";
	private static final String PASSWORD = "welcome1";
	public static final String JDBC_URL = "jdbc:oracle:thin:@";
	private static final String SCHEMA_USER = "schemaUser";

	private static final String SERVICE_MANAGER_URL = "SERVICE_MANAGER_URL";

	public static final String ODS_HOSTNAME = "ODS_HOSTNAME";
	public static final String ODS_PORT = "ODS_PORT";
	public static final String ODS_SID = "ODS_SID";
	public static final String NODE_NAME1 = "EMCS_NODE1_HOSTNAME";
	public static final String NODE_NAME2 = "EMCS_NODE2_HOSTNAME";
	public static final String NODE_NAME3 = "EMCS_NODE3_HOSTNAME";

	public static final String JDBC_PARAM_URL = "javax.persistence.jdbc.url";
	public static final String JDBC_PARAM_USER = "javax.persistence.jdbc.user";
	public static final String JDBC_PARAM_PASSWORD = "javax.persistence.jdbc.password";
	public static final String JDBC_PARAM_DRIVER = "javax.persistence.jdbc.driver";
	public static final String JDBC_PARAM_DRIVER_VALUE = "oracle.jdbc.OracleDriver";
	public static final String TENANT_USER_NAME = "TENANT_USER_NAME";
	public static final String TENANT_NAME = "TENANT_NAME";

	private static Logger logger = LogManager.getLogger(QAToolUtil.class);

	public static Properties getDbProperties()
	{
		Properties props = new Properties();
		String url = JDBC_URL + Utils.getProperty(ODS_HOSTNAME) + ":" + Utils.getProperty(ODS_PORT) + ":"
				+ Utils.getProperty(ODS_SID);
		props.put(JDBC_PARAM_URL, url);
		String schemaName = QAToolUtil.getSchemaDeploymentDetails();
		props.put(JDBC_PARAM_USER, schemaName);
		props.put(JDBC_PARAM_PASSWORD, PASSWORD);
		props.put(JDBC_PARAM_DRIVER, JDBC_PARAM_DRIVER_VALUE);
		return props;
	}

	public static String getSavedSearchDeploymentDet()
	{
		String username = null;
		String data = QAToolUtil.getDetaildByUrl(Utils.getProperty(SERVICE_MANAGER_URL) + SSF_DEPLOY_URL);
		//String data = QAToolUtil.getDetaildByUrl("http://slc08twq.us.oracle.com:7001//registry/servicemanager/registry/v1"
		//	+ SSF_DEPLOY_URL);

		List<String> urlList = SchemaUtil.getSchemaUrls(data);
		if (urlList == null | urlList.isEmpty()) {
			return null;
		}
		return urlList.get(0);
	}

	public static Properties getTenantDetails()
	{
		Properties props = new Properties();
		props.put(TENANT_USER_NAME, Utils.getProperty("TENANT_ID_INTERNAL") + "." + Utils.getProperty("SSO_USERNAME"));
		props.put(TENANT_NAME, Utils.getProperty("TENANT_ID_INTERNAL"));
		return props;
	}

	/*public static void main(String h[])
	{
		System.out.println(QAToolUtil.getSchemaDeploymentDetails());
	}*/

	private static List<String> extractUrls(String value)
	{

		if (value == null) {
			return null;
		}
		List<String> result = new ArrayList<String>();

		Pattern p = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(value);
		while (m.find()) {
			result.add(value.substring(m.start(0), m.end(0)));
		}
		return result;
	}

	private static String getDetaildByUrl(String url)
	{
		BufferedReader in = null;
		InputStreamReader inReader = null;
		StringBuffer response = new StringBuffer();
		try {
			URL schema_dep_url = new URL(url);
			HttpURLConnection con = (HttpURLConnection) schema_dep_url.openConnection();
			con.setRequestProperty(AUTHORIZATION, AUTH_STRING);
			//int responseCode = con.getResponseCode();
			inReader = new InputStreamReader(con.getInputStream());
			in = new BufferedReader(inReader);
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		}
		catch (IOException e) {
			logger.error("an error occureed while getting details by url" + ":: " + url, e);
			System.out.println("an error occureed while getting details by url" + " ::" + url + "  " + e.toString());
			try {
				if (in != null) {
					in.close();
				}
				if (inReader != null) {
					inReader.close();
				}
			}
			catch (IOException ioEx) {
				//ignore
			}

		}
		return response.toString();

	}

	/**
	 * @return
	 */
	private static String getSchemaDeploymentDetails()
	{
		String username = null;
		String data = QAToolUtil.getDetaildByUrl(Utils.getProperty(SERVICE_MANAGER_URL) + DEPLOY_URL);
		//String data = QAToolUtil.getDetaildByUrl("http://slc04lec.us.oracle.com:7001//registry/servicemanager/registry/v1"
		//	+ DEPLOY_URL);
		List<String> urlList = QAToolUtil.extractUrls(data);
		for (String temp : urlList) {
			if (temp.endsWith(LIFE_CYCLE)) {
				data = QAToolUtil.getDetaildByUrl(temp + DEPLY_SCHEMA);
				if (data != null) {
					username = QAToolUtil.getUsername(data);
					if (username != null) {
						return username;
					}
				}
			}
		}

		return null;
	}

	private static String getUsername(String data)
	{
		String[] options = data.split(",");
		if (options.length > 0) {
			for (String tmp : options) {
				if (tmp.contains(SCHEMA_USER)) {
					String name = tmp.substring(tmp.indexOf(":"));
					if (name != null) {
						name = name.replace("\"", "").replace(":", "");
						return name;
					}
				}
			}
		}
		return null;
	}

}