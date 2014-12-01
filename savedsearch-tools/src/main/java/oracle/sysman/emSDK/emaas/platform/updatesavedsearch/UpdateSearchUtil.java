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

	public static void exportSearches(long categoryId, String endpoint, String outputfile)
	{

		if (!UpdateSearchUtil.isEndpointReachable(endpoint)) {
			System.out.println("The endpoint was not reachable.");
			return;
		}

		try {
			ExportSearchObject objExport = new ExportSearchObject();
			String data = objExport.exportSearch(categoryId, endpoint);
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
			System.out.println("The process completed successfully.");
		}
		catch (IOException e) {
			System.out.println("an error occurred while writing data to file : " + outputfile);
			_logger.error("an error occurred while writing data to file : " + outputfile);
			return;
		}
		catch (Exception e) {
			System.out.println("an error occurred exporting searches  ");
			_logger.error("an error occurred exporting searches  ");
			return;
		}
	}

	public static void importSearches(String endpoint, String inputfile, String outputfile)
	{
		String data = "";
		String outputData = "";

		if (!UpdateSearchUtil.isEndpointReachable(endpoint)) {
			System.out.println("The endpoint was not reachable.");
			return;
		}

		try {
			data = FileUtils.readFile(inputfile);
		}
		catch (IOException e) {
			_logger.error("An error occurred while reading the input file : " + e.getMessage());
			System.out.println("An error occurred while reading the input file : " + inputfile);
			return;
		}
		catch (Exception ex) {
			_logger.error("An error occurred while updating searches" + ex.getMessage());
			System.out.println("An error occurred while updating searches");
			return;
		}
		ImportSearchObject objUpdate = new ImportSearchObject();
		try {
			outputData = objUpdate.importSearches(endpoint, data);
		}
		catch (Exception e1) {
			_logger.error("An error occurred while creating or updating search object" + e1.getMessage());
			System.out.println("An error occurred while creating or updating search object");
			return;
		}
		try {
			FileUtils.createOutputfile(outputfile, outputData);
		}
		catch (IOException e) {
			System.out.println("an error occurred while writing data to file : " + outputfile);
			_logger.error("an error occurred while writing data to file : " + outputfile);
			return;
		}
		System.out.println("The update process completed successfully.");
	}

	public static boolean isEndpointReachable(String endpoint)
	{
		try {
			RestAssured.useRelaxedHTTPSValidation();
			RestAssured.baseURI = endpoint;
			Response res = RestAssured.given().when().get();
			if (res.getStatusCode() == 200) {
				return true;
			}
		}
		catch (Exception e) {
			System.out.println("Error (isEndpointReachable):" + e.getLocalizedMessage());
			_logger.error("Error (isEndpointReachable):" + e.getLocalizedMessage());
		}
		return false;
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
