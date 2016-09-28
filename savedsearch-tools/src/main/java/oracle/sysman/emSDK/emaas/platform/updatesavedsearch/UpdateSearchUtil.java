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

	private static Logger LOGGER = UpdateSavedSearchLog.getLogger(UpdateSearchUtil.class);

	public static void exportSearches(long categoryId, String endpoint, String outputfile, String authToken, String tenantid)
	{

		if (!UpdateSearchUtil.isEndpointReachable(endpoint, authToken, tenantid)) {
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
		}
		catch (IOException e) {
			LOGGER.error("Error : an error occurred while writing data to file : ");
			return;
		}
		catch (Exception e) {
			LOGGER.error("Error : an error occurred exporting searches  " + e.getMessage());
			return;
		}
	}

	public static void importSearches(String endpoint, String inputfile, String outputfile, String authToken, String tenantid)
	{
		String data = "";
		String outputData = "";

		if (!UpdateSearchUtil.isEndpointReachable(endpoint, authToken, tenantid)) {
			return;
		}

		try {
			data = FileUtils.readFile(inputfile);
		}
		catch (IOException e) {
			LOGGER.error("Error : An error occurred while reading the input file : " + e.getMessage());
			return;
		}
		catch (Exception ex) {
			LOGGER.error("Error : An error occurred while updating searches" + ex.getMessage());
			return;
		}
		ImportSearchObject objUpdate = new ImportSearchObject();
		try {
			outputData = objUpdate.importSearches(endpoint, data, authToken, tenantid);
		}
		catch (Exception e1) {
			LOGGER.error("Error : An error occurred while creating or updating search object" + e1.getMessage());
			return;
		}
		try {
			FileUtils.createOutputfile(outputfile, outputData);
		}
		catch (IOException e) {
			LOGGER.error("an error occurred while writing data to file Please refer to the log file for more details.");
			return;
		}
	}

	public static boolean isEndpointReachable(String endpoint, String authToken, String tenantid)
	{
		try {
			TenantUtil objTenent = new TenantUtil(tenantid);
			RestAssured.useRelaxedHTTPSValidation();
			RestAssured.baseURI = endpoint;
			Response res = RestAssured.given().header(UpdateUtilConstants.SSF_AUTHORIZATION, authToken)
					.header(UpdateUtilConstants.OAM_REMOTE_USER, tenantid).when().get();
			if (res.getStatusCode() == 200) {
				return true;
			}
		}
		catch (Exception e) {
			LOGGER.error("Error (isEndpointReachable):" + e.getLocalizedMessage()
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
