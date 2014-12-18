/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.updatesearch.test.common;

/**
 * @author vinjoshi
 *
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;

public class CommonTest
{

	private final String HOSTNAME;
	private final String portno;
	private final String serveruri;

	/**
	 * Sets up RESTAssured defaults before executing test cases Enables logging Reading the inputs from the testenv.properties
	 * file
	 */

	public CommonTest()
	{
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("testenv.properties");
			prop.load(input);
			System.out.println("---------------------------------------------------------------------");
			System.out.println("The property values - Hostname: " + prop.getProperty("hostname") + " and Port: "
					+ prop.getProperty("port"));
			System.out.println("---------------------------------------------------------------------");
			System.out.println("											");
		}
		catch (IOException e) {

			e.printStackTrace();
		}

		HOSTNAME = prop.getProperty("hostname");
		portno = prop.getProperty("port");
		serveruri = "http://" + HOSTNAME + ":" + portno;
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = serveruri;
		RestAssured.basePath = "/savedsearch/v1";
		RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().enablePrettyPrinting(false));

	}

	public String getHOSTNAME()
	{
		return HOSTNAME;
	}

	public String getPortno()
	{
		return portno;
	}

	public String getServeruri()
	{
		return serveruri;
	}

}
