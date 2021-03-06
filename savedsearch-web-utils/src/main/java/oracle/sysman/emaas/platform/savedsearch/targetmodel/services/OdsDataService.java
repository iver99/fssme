package oracle.sysman.emaas.platform.savedsearch.targetmodel.services;

import java.math.BigInteger;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

public interface OdsDataService {
	public final static String ENTITY_FLAG = "ODS_ENTITY";
	public final static String ENTITY_ID = "meId";
	public final static String ENTITY_CLASS = "TARGET";
	public final static String ENTITY_TYPE_NAME = "omc_saved_search";//"usr_SSEntityType"
//	public final static String ENTITY_TYPE_DIS_NAME = "Saved Search";
	
	public final static String HTTP_DELIMITER = "/";
	public final static String SERVICE_NAME = "TargetModelManagement";
	public final static String VERSION = "1.1";
	public final static String REL_DATA_RESOURCE = "serviceapi/tm-data";
	public final static String DATA_MES = "mes";
	public final static String REL_METADATA_RESOURCE = "serviceapi/tm-metadata";
	public final static String METADATA_METYPES = "metypes";
	
	/**
	 * create an ODS entity
	 * @param searchName - will be used as entity name and display name
	 * @return ODS Entity ID
	 * @throws EMAnalyticsFwkException
	 */
	public String createOdsEntity(String searchId, String searchName) throws EMAnalyticsFwkException;
	
	/**
	 * delete the ODS entity by related Saved Search id
	 * @param id - saved search id
	 * @throws EMAnalyticsFwkException
	 */
	public void deleteOdsEntity(BigInteger searchId) throws EMAnalyticsFwkException;
	
	/**
	 * create an ODS entity type
	 * @param entityType - JSON format entity type
	 * @return ODS entity type
	 * @throws EMAnalyticsFwkException 
	 */
	public String createOdsEntityType(String entityType) throws EMAnalyticsFwkException;
}
