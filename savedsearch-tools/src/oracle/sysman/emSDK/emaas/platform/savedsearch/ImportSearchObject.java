package oracle.sysman.emSDK.emaas.platform.savedsearch;

import java.net.URL;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class ImportSearchObject {

	public String importSearches(String endpoint, String sData)
			throws Exception {

		String output = "";
		String jsonString1 = sData;
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = endpoint;
		RestAssured.basePath = "/savedsearch/v1";
		RestAssured.config = RestAssured.config().logConfig(
				LogConfig.logConfig().enablePrettyPrinting(false));
		URL netUrl = new URL(endpoint);
		String host = netUrl.getHost();
		if (host.startsWith("www")) {
			host = host.substring("www".length() + 1);
		}
		Response res1 = RestAssured.given().contentType(ContentType.XML)
				.headers("X-USER-IDENTITY-DOMAIN-NAME", host).body(jsonString1)
				.when().post("/importsearches");
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for (int index = 0; index < arrfld.length(); index++) {
			JSONObject jsonObj = arrfld.getJSONObject(index);
			output = output + jsonObj.getInt("id") + "  "
					+ jsonObj.getString("name")
					+ System.getProperty("line.separator");
		}
		return output;
	}

	public boolean isEndpointReachable(String endpoint) {
		try {
			RestAssured.useRelaxedHTTPSValidation();
			RestAssured.baseURI = endpoint;
			RestAssured.basePath = "/savedsearch/v1";
			Response res = RestAssured.given().when()
					.get("/entities?folderId=1");

			if (res.getStatusCode() == 200)
				return true;
		} catch (Exception e) {
			System.out.println("Error:" + e.getLocalizedMessage());
		}
		return false;
	}

}
