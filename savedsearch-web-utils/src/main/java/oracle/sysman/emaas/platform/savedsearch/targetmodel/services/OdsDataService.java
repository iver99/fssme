package oracle.sysman.emaas.platform.savedsearch.targetmodel.services;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

public interface OdsDataService {
	public final static String ENTITY_FLAG = "ODS_ENTITY";
	public final static String ENTITY_ID = "meId";
	public final static String ENTITY_CLASS = "meClass";
	public final static String ENTITY_TYPE_NAME = "usr_SSEntityType";//"omc_saved_search";
	public final static String ENTITY_TYPE_DIS_NAME = "Saved Search";
	public final static String FACT_TYPE_NAME = "ResultFact::DATA";
	public final static String FACT_DISPLAY_NAME = "ResultFact";
	
	public final static String HTTP_DELIMITER = "/";
	public final static String SERVICE_NAME = "targetmodel-service-shard#0";
	public final static String VERSION = "1.0+";
	public final static String REL_DATA_RESOURCE = "data";
	public final static String DATA_MES = "mes";
	public final static String REL_METADATA_RESOURCE = "metadata";
	public final static String METADATA_METYPES = "metypes";
	
	/**
	 * create an ODS entity
	 * @param searchName - will be used as entity name and display name
	 * @param tenantName - tenant.username
	 * @return ODS Entity ID
	 * @throws EMAnalyticsFwkException
	 */
	public String createOdsEntity(String searchId, String searchName, String tenantName) throws EMAnalyticsFwkException;
	
	/**
	 * delete the ODS entity by related Saved Search id
	 * @param id - saved search id
	 * @param tenantName - tenant.username
	 * @throws EMAnalyticsFwkException
	 */
	public void deleteOdsEntity(long searchId, String tenantName) throws EMAnalyticsFwkException;
	
	/**
	 * create an ODS entity type
	 * @param entityType - JSON format entity type
	 * @param tenantName - tenant.username
	 * @return ODS entity type
	 * @throws EMAnalyticsFwkException 
	 */
	public String createOdsEntityType(String entityType, String tenantName) throws EMAnalyticsFwkException;
}
