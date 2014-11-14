package oracle.sysman.emSDK.emaas.platform.savedsearch;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class UpdateSearchUtil {

	
	public static boolean isEndpointReachable(String endpoint) {
		try {
			RestAssured.useRelaxedHTTPSValidation();
			RestAssured.baseURI = endpoint;
			//RestAssured.basePath = UpdateUtilConstants.BASE_PATH_STR;
			Response res = RestAssured.given().when()
					.get();

			if (res.getStatusCode() == 200)
				return true;
		} catch (Exception e) {
			System.out.println("Error:" + e.getLocalizedMessage());
		}
		return false;
	}
	
	
	
}
