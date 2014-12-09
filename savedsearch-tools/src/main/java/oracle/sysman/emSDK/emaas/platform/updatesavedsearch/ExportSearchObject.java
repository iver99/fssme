package oracle.sysman.emSDK.emaas.platform.updatesavedsearch;

import java.net.URL;

import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.exception.UpdateSearchException;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.response.Response;

public class ExportSearchObject
{

	public String exportSearch(long categoryId, String endpoint) throws Exception
	{
		String output = "";
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = endpoint;
		RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().enablePrettyPrinting(false));
		URL netUrl = new URL(endpoint);
		String host = netUrl.getHost();
		if (host.toLowerCase().startsWith(UpdateUtilConstants.WWW_STR)) {
			host = host.substring(UpdateUtilConstants.WWW_STR.length() + 1);
		}
		Response res1 = RestAssured.given().when()
				.get(UpdateUtilConstants.GET_SEARCH_BY_CAREGORY_STR + categoryId + UpdateUtilConstants.SEARCHES);

		if (res1.getStatusCode() == 200) {
			output = res1.getBody().asString();
		}
		else {
			throw new UpdateSearchException(res1.getBody().asString());
		}
		return output;
	}
}
