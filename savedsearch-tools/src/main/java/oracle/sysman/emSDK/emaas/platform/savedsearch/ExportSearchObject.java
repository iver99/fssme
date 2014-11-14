package oracle.sysman.emSDK.emaas.platform.savedsearch;

import java.net.URL;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.response.Response;

public class ExportSearchObject {

	
	public String  exportSearch(long categoryId ,String endpoint) throws Exception
	{
		String output = "";		
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI =  endpoint;		
		RestAssured.config = RestAssured.config().logConfig(
				LogConfig.logConfig().enablePrettyPrinting(false));
		URL netUrl = new URL(endpoint);
		String host = netUrl.getHost();
		if (host.toLowerCase().startsWith(UpdateUtilConstants.WWW_STR)) {
			host = host.substring(UpdateUtilConstants.WWW_STR.length() + 1);
		}
		Response res1 = RestAssured.given().when().
				get(UpdateUtilConstants.GET_SEARCH_BY_CAREGORY_STR + categoryId +
						UpdateUtilConstants.SEARCHES );
		
		
		output= res1.getBody().asString();
		return output;
	}
	
	
	
	
}
