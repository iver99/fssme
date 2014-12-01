package oracle.sysman.emSDK.emaas.platform.updatesavedsearch;

import java.net.URL;

import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.exception.UpdateSearchException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class ImportSearchObject
{

	public String importSearches(String endpoint, String sData) throws Exception
	{

		String output = "";
		String jsonString1 = sData;
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = endpoint;
		RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().enablePrettyPrinting(false));
		URL netUrl = new URL(endpoint);
		String host = netUrl.getHost();
		if (host.toLowerCase().startsWith(UpdateUtilConstants.WWW_STR)) {
			host = host.substring(UpdateUtilConstants.WWW_STR.length() + 1);
		}
		Response res1 = RestAssured.given().contentType(ContentType.XML).headers(UpdateUtilConstants.DOMAIN_NAME, host)
				.body(jsonString1).when().post(UpdateUtilConstants.IMPORT_SEARCH_STR);
		output = res1.getBody().asString();
		if (res1.getStatusCode() == 200) {
			JSONArray arrfld = new JSONArray(output);
			output = "";
			for (int index = 0; index < arrfld.length(); index++) {
				JSONObject jsonObj = arrfld.getJSONObject(index);
				output = output + jsonObj.getInt(UpdateUtilConstants.ID) + "  " + jsonObj.getString(UpdateUtilConstants.NAME)
						+ System.getProperty("line.separator");
			}
		}
		else {
			throw new UpdateSearchException(res1.getBody().asString());
		}
		return output;
	}

}
