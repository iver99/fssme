package oracle.sysman.emSDK.emaas.platform.updatesavedsearch;

import java.io.IOException;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.entity.SearchEntity;
import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.logging.UpdateSavedSearchLog;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class UpdateSearchUtil
{

	private static Logger _logger = UpdateSavedSearchLog.getLogger(UpdateSearchUtil.class);

	public static void exportSearches(long categoryId, String endpoint, String outputfile, String authToken, String tenantid)
	{

		if (!UpdateSearchUtil.isEndpointReachable(endpoint, authToken, tenantid)) {
			System.out.println("The endpoint was not reachable.");
			return;
		}

		try {
			ExportSearchObject objExport = new ExportSearchObject();
			String data = objExport.exportSearch(categoryId, endpoint, authToken, tenantid);
			List<SearchEntity> list = UpdateSearchUtil.JSONToSearchList(data);
			ExportSearchSet exportList = new ExportSearchSet();
			exportList.setSearchSet(list);
			if (list != null && list.size() > 0) {
				data = XMLUtil.ObjectToXML(exportList);
			}
			if (FileUtils.fileExist(outputfile)) {
				FileUtils.deleteFile(outputfile);
			}
			FileUtils.createOutputfile(outputfile, data);
			System.out.println("The export process completed.");
		}
		catch (IOException e) {
			System.out.println("Error : an error occurred while writing data to file : " + outputfile
					+ " Please refer to the log file for more details.");
			_logger.error("Error : an error occurred while writing data to file : ");
			return;
		}
		catch (Exception e) {
			System.out.println("Error : an error occurred exporting searches  "
					+ " Please refer to the log file for more details.");
			_logger.error("Error : an error occurred exporting searches  " + e.getMessage());
			return;
		}
	}

	public static void importSearches(String endpoint, String inputfile, String outputfile, String authToken, String tenantid)
	{
		String data = "";
		String outputData = "";

		if (!UpdateSearchUtil.isEndpointReachable(endpoint, authToken, tenantid)) {
			System.out.println("The endpoint was not reachable.");
			return;
		}

		try {
			data = FileUtils.readFile(inputfile);
		}
		catch (IOException e) {
			_logger.error("Error : An error occurred while reading the input file : " + e.getMessage());
			System.out.println("Error : An error occurred while reading the input file : " + inputfile
					+ " Please refer to the log file for more details.");
			return;
		}
		catch (Exception ex) {
			_logger.error("Error : An error occurred while updating searches" + ex.getMessage());
			System.out.println("Error : An error occurred while updating searche Please refer to the log file for more details.");
			return;
		}
		ImportSearchObject objUpdate = new ImportSearchObject();
		try {
			outputData = objUpdate.importSearches(endpoint, data, authToken, tenantid);
		}
		catch (Exception e1) {
			_logger.error("Error : An error occurred while creating or updating search object" + e1.getMessage());
			System.out.println("Error : An error occurred while creating or updating search object "
					+ " Please refer to the log file for more details.");
			return;
		}
		try {
			FileUtils.createOutputfile(outputfile, outputData);
		}
		catch (IOException e) {
			System.out.println("an error occurred while writing data to file : " + outputfile
					+ " Please refer to the log file for more details.");
			_logger.error("an error occurred while writing data to file Please refer to the log file for more details.");
			return;
		}
		System.out.println("The import process completed.");
	}

	public static boolean isEndpointReachable(String endpoint, String authToken, String tenantid)
	{
		try {
			TenantUtil objTenent = new TenantUtil(tenantid);
			RestAssured.useRelaxedHTTPSValidation();
			RestAssured.baseURI = endpoint;
			Response res = RestAssured.given().header(UpdateUtilConstants.SSF_AUTHORIZATION, authToken)
					.header(UpdateUtilConstants.DOMAIN_NAME, objTenent.getTenantId())
					.header(UpdateUtilConstants.SSF_REMOTE_USER, objTenent.getUserName()).when().get();
			if (res.getStatusCode() == 200) {
				return true;
			}
		}
		catch (Exception e) {
			System.out.println("Error (isEndpointReachable):" + e.getLocalizedMessage());
			_logger.error("Error (isEndpointReachable):" + e.getLocalizedMessage()
					+ " Please refer to the log file for more details.");
		}
		return false;
	}

	public static boolean isTestEnv()
	{
		String stesting = System.getProperty(UpdateUtilConstants.SSF_TEST_ENV, "false");
		stesting = stesting == null ? "false" : stesting;
		return stesting.equalsIgnoreCase("true");
	}

	public static List<SearchEntity> JSONToSearchList(String jsonStr) throws JsonParseException, JsonMappingException,
			IOException
	{
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<SearchEntity> list = mapper.readValue(jsonStr,
				TypeFactory.defaultInstance().constructCollectionType(List.class, SearchEntity.class));
		return list;
	}

}
