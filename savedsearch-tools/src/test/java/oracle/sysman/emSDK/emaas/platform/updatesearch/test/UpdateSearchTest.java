/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.updatesearch.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.ExportSearchObject;
import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.ImportSearchObject;
import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.TenantUtil;
import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.UpdateUtilConstants;
import oracle.sysman.emSDK.emaas.platform.updatesearch.test.common.BaseTest;
import oracle.sysman.emSDK.emaas.platform.updatesearch.test.common.CommonTest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

/**
 * @author vinjoshi
 */
public class UpdateSearchTest extends BaseTest
{

	static String HOSTNAME;
	static String portno;
	static String serveruri;
	static String authToken;
	private static final String tenantimport = "1";
	private static final String tenantimport1 = "2";
	public static final String TENANT_ID1 = "1.ORACLE";
	public static final String TENANT_ID2 = "2.ORACLE";
	private static final String SEARCH_XML = "oracle/sysman/emSDK/emaas/platform/updatesearch/test/Search.xml";
	private static final String CAT_NAME = "MTSearchMt";

	@BeforeClass
	public static void setUp()
	{
		CommonTest ct = new CommonTest();
		HOSTNAME = ct.getHOSTNAME();
		portno = ct.getPortno();
		serveruri = ct.getServeruri();
		authToken = ct.getAuthToken();
	}

	private static String getStringFromInputStream(InputStream is)
	{

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	/**
	 * Read Category Details by name:
	 */

	public Integer getCategoryDetailsbyName(String name)
	{
		TenantUtil obj = new TenantUtil(TENANT_ID1);
		Response res = RestAssured.given().log().everything().header(UpdateUtilConstants.SSF_AUTHORIZATION, authToken)
				.header(UpdateUtilConstants.SSF_HEADER, UpdateUtilConstants.SSF_HEADER)
				.header(UpdateUtilConstants.OAM_REMOTE_USER, TENANT_ID1).when().get("/category?name=" + name);
		JsonPath jp = res.jsonPath();
		return jp.get("id");

	}

	@Test
	/**
	 * Import Searches
	 */
	public void updateSearchesTest() throws Exception
	{

		InputStream stream = UpdateSearchTest.class.getClassLoader().getResourceAsStream(SEARCH_XML);
		ImportSearchObject objUpdate = new ImportSearchObject();
		String inputData = UpdateSearchTest.getStringFromInputStream(stream);
		String outputData = objUpdate.importSearches(serveruri, inputData, authToken, TENANT_ID1);
		List<Long> listID = new ArrayList<Long>();
		String[] tmpList = outputData.split("\n");

		for (String element : tmpList) {
			if (element != null) {
				listID.add(Long.parseLong(element.substring(0, element.indexOf(" "))));
			}

		}

		long id = getCategoryDetailsbyName(CAT_NAME);
		ExportSearchObject expObj = new ExportSearchObject();
		outputData = expObj.exportSearch(id, serveruri, authToken, TENANT_ID1);
		JSONArray arrfld = new JSONArray(outputData);
		TenantUtil obj = new TenantUtil(TENANT_ID2);
		for (int index = 0; index < arrfld.length(); index++) {
			JSONObject jsonObj = arrfld.getJSONObject(index);
			Response res = RestAssured.given().log().everything().header(UpdateUtilConstants.SSF_AUTHORIZATION, authToken)
					.header(UpdateUtilConstants.SSF_HEADER, UpdateUtilConstants.SSF_HEADER)
					.header(UpdateUtilConstants.OAM_REMOTE_USER, TENANT_ID1).when().get("/search/" + jsonObj.getInt("id"));
			JsonPath jp = res.jsonPath();
			Assert.assertTrue(res.getStatusCode() == 404);
		}

		TenantUtil objTenent = new TenantUtil(TENANT_ID2);
		for (int index = 0; index < arrfld.length(); index++) {
			JSONObject jsonObj = arrfld.getJSONObject(index);
			Response res = RestAssured.given().log().everything().header(UpdateUtilConstants.SSF_AUTHORIZATION, authToken)
					.header(UpdateUtilConstants.SSF_HEADER, UpdateUtilConstants.SSF_HEADER)
					.header(UpdateUtilConstants.OAM_REMOTE_USER, TENANT_ID2).when().get("/search/" + jsonObj.getInt("id"));
			JsonPath jp = res.jsonPath();
			Assert.assertTrue(listID.contains(jsonObj.getLong("id")));

		}

	}

	private boolean deleteSearch(int mySearchId)
	{
		Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
				.header(UpdateUtilConstants.SSF_HEADER, UpdateUtilConstants.SSF_HEADER)
				.header(UpdateUtilConstants.OAM_REMOTE_USER, TENANT_ID1).when().delete("/search/" + mySearchId);
		return res1.getStatusCode() == 204;

	}

}
