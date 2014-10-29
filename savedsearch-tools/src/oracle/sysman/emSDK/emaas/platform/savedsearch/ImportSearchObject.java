package oracle.sysman.emSDK.emaas.platform.savedsearch;

import java.net.URL;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class ImportSearchObject {
	
	private static final String BASE_PATH_STR="/savedsearch/v1";
	private static final String IMPORT_SEARCH_STR="/importsearches";
	private static final String DOMAIN_NAME="X-USER-IDENTITY-DOMAIN-NAME";
	private static final String WWW_STR="www";
	private static final String ID="id";
	private static final String NAME="name";
	private static final String GET_FOLDER="/entities?folderId=1";
	
	public String importSearches(String endpoint, String sData)
			throws Exception {

		String output = "";
		String jsonString1 = sData;
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = endpoint;
		RestAssured.basePath = BASE_PATH_STR;
		RestAssured.config = RestAssured.config().logConfig(
				LogConfig.logConfig().enablePrettyPrinting(false));
		URL netUrl = new URL(endpoint);
		String host = netUrl.getHost();
		if (host.toLowerCase().startsWith(WWW_STR)) {
			host = host.substring(WWW_STR.length() + 1);
		}
		Response res1 = RestAssured.given().contentType(ContentType.XML)
				.headers(DOMAIN_NAME, host).body(jsonString1)
				.when().post(IMPORT_SEARCH_STR);
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for (int index = 0; index < arrfld.length(); index++) {
			JSONObject jsonObj = arrfld.getJSONObject(index);
			output = output + jsonObj.getInt(ID) + "  "
					+ jsonObj.getString(NAME)
					+ System.getProperty("line.separator");
		}
		return output;
	}

	public boolean isEndpointReachable(String endpoint) {
		try {
			RestAssured.useRelaxedHTTPSValidation();
			RestAssured.baseURI = endpoint;
			RestAssured.basePath = BASE_PATH_STR;
			Response res = RestAssured.given().when()
					.get(GET_FOLDER);

			if (res.getStatusCode() == 200)
				return true;
		} catch (Exception e) {
			System.out.println("Error:" + e.getLocalizedMessage());
		}
		return false;
	}

}
