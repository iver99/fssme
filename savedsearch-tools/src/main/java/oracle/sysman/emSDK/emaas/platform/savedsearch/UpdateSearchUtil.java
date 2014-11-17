package oracle.sysman.emSDK.emaas.platform.savedsearch;

import oracle.sysman.emSDK.emaas.platform.savedsearch.logging.UpdateSavedSearchLog;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.apache.log4j.Logger;
public class UpdateSearchUtil {

	private static Logger _logger = UpdateSavedSearchLog.getLogger(UpdateSearchUtil.class);
	
	public static boolean isEndpointReachable(String endpoint) {
		try {
			RestAssured.useRelaxedHTTPSValidation();
			RestAssured.baseURI = endpoint;			
			Response res = RestAssured.given().when()
					.get();

			if (res.getStatusCode() == 200)
				return true;
		} catch (Exception e) {
			 System.out.println("Error:" + e.getLocalizedMessage());
			_logger.error("Error:" + e.getLocalizedMessage());
		}
		return false;
	}
	
	
	
}
