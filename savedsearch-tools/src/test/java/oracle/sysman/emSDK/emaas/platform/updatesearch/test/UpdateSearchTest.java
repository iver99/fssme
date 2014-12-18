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

	private static final String SEARCH_XML = "oracle/sysman/emSDK/emaas/platform/updatesearch/test/Search.xml";
	private static final String CAT_NAME = "CatSearchUtil";

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

		Response res = RestAssured.given().log().everything().header("Authorization", authToken).when()
				.get("/category?name=" + name);
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
		String outputData = objUpdate.importSearches(serveruri, inputData);
		List<Long> listID = new ArrayList<Long>();

		String[] tmpList = outputData.split(System.getProperty("line.separator"));

		for (String element : tmpList) {
			if (element != null) {
				listID.add(Long.parseLong(element.substring(0, element.indexOf(" "))));
			}

		}

		long id = getCategoryDetailsbyName(CAT_NAME);
		ExportSearchObject expObj = new ExportSearchObject();
		outputData = expObj.exportSearch(id, serveruri);
		JSONArray arrfld = new JSONArray(outputData);
		for (int index = 0; index < arrfld.length(); index++) {
			JSONObject jsonObj = arrfld.getJSONObject(index);
			Response res = RestAssured.given().log().everything().header("Authorization", authToken).when()
					.get("/search/" + jsonObj.getInt("id"));
			JsonPath jp = res.jsonPath();
			System.out.println("deleteing searches::::" + res.getBody().asString());
			Assert.assertTrue(listID.contains(jsonObj.getLong("id")));
			Assert.assertTrue(deleteSearch(jsonObj.getInt("id")) == true);
			System.out.println("deleted  searches");
		}

	}

	private boolean deleteSearch(int mySearchId)
	{
		Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
				.when().delete("/search/" + mySearchId);
		System.out.println("											");
		return res1.getStatusCode() == 204;

	}

}
