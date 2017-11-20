package oracle.sysman.emaas.platform.savedsearch.metadata;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.JSONUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.nls.DatabaseResourceBundleUtil;
import oracle.sysman.emaas.platform.emcpdf.registry.RegistryLookupUtil;
import oracle.sysman.emaas.platform.savedsearch.entity.EmsResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

/**
 * Created by xiadai on 2017/5/4.
 */
public class MetaDataRetriever {
    private static final Logger LOGGER = LogManager.getLogger(MetaDataRetriever.class);

    private enum CategoryName{
        LA(1), UDE(2), ITA(3), APM(4), SMA(6), OCS(7), EVT(8), MON(9);

        private final int categoryId;
        CategoryName(int categoryId){
            this.categoryId = categoryId;
        }
    }

    private enum FolderName{
        LA(2), UDE(4), ITA(3), APM(5), SMA(7), OCS(8), EVT(9), MON(10);

        private final int folderId;
        FolderName(int folderId){
            this.folderId = folderId;
        }
    }
    private static final Map<String, Integer> CATEGORY_MAP = new HashMap<>();
    private static final Map<String, Integer> FOLDER_MAP = new HashMap<>();
    private static final Map<String, String> APPLICATION_MAP = new HashMap<>();
    // TODO fix the correct service name in SM
    public static final String SERVICENAME_APM = "ApmUI";
    public static final String SERVICENAME_ITA = "emcitas-ui-apps";
    public static final String SERVICENAME_LA = "LogAnalyticsUI";
    public static final String SERVICENAME_MONITORING = "MonitoringServiceUI";
    public static final String SERVICENAME_SECURITY_ANALYTICS = "SecurityAnalyticsUI";
    public static final String SERVICENAME_ORCHESTRATION = "CosUIService";
    //    public static final String SERVICENAME_COMPLIANCE = "Compliance";
    public static final String SERVICENAME_UDE = "TargetAnalytics";
    public static final String SERVICENAME_EVENT = "EventUI";

    public static final String APM_STRING = "APM";
    public static final String ITA_SRING = "ITAnalytics";
    public static final String LA_STRING = "LogAnalytics";
    public static final String MONITORING_STRING = "MonitoringService";
    public static final String SECURITY_ANALYTICS_STRING = "SecurityAnalytics";
    public static final String ORCHESTRATION_STRING = "Orchestration";
    public static final String UDE_STRING = "UDE";
    public static final String EVENT_STRING = "Alerts";


    static {
        CATEGORY_MAP.put(SERVICENAME_LA, CategoryName.LA.categoryId);
        CATEGORY_MAP.put(SERVICENAME_UDE, CategoryName.UDE.categoryId);
        CATEGORY_MAP.put(SERVICENAME_ITA, CategoryName.ITA.categoryId);
        CATEGORY_MAP.put(SERVICENAME_APM, CategoryName.APM.categoryId);
        CATEGORY_MAP.put(SERVICENAME_MONITORING, CategoryName.MON.categoryId);
        CATEGORY_MAP.put(SERVICENAME_SECURITY_ANALYTICS, CategoryName.SMA.categoryId);
        CATEGORY_MAP.put(SERVICENAME_ORCHESTRATION, CategoryName.OCS.categoryId);
        CATEGORY_MAP.put(SERVICENAME_EVENT, CategoryName.EVT.categoryId);

        FOLDER_MAP.put(SERVICENAME_LA, FolderName.LA.folderId);
        FOLDER_MAP.put(SERVICENAME_UDE, FolderName.UDE.folderId);
        FOLDER_MAP.put(SERVICENAME_ITA, FolderName.ITA.folderId);
        FOLDER_MAP.put(SERVICENAME_APM, FolderName.APM.folderId);
        FOLDER_MAP.put(SERVICENAME_MONITORING, FolderName.MON.folderId);
        FOLDER_MAP.put(SERVICENAME_SECURITY_ANALYTICS, FolderName.SMA.folderId);
        FOLDER_MAP.put(SERVICENAME_ORCHESTRATION, FolderName.OCS.folderId);
        FOLDER_MAP.put(SERVICENAME_EVENT, FolderName.EVT.folderId);

        APPLICATION_MAP.put(SERVICENAME_LA, LA_STRING);
        APPLICATION_MAP.put(SERVICENAME_UDE, UDE_STRING);
        APPLICATION_MAP.put(SERVICENAME_ITA, ITA_SRING);
        APPLICATION_MAP.put(SERVICENAME_APM, APM_STRING);
        APPLICATION_MAP.put(SERVICENAME_MONITORING, MONITORING_STRING);
        APPLICATION_MAP.put(SERVICENAME_SECURITY_ANALYTICS, SECURITY_ANALYTICS_STRING);
        APPLICATION_MAP.put(SERVICENAME_ORCHESTRATION, ORCHESTRATION_STRING);
        APPLICATION_MAP.put(SERVICENAME_EVENT, EVENT_STRING);
    }

    public List<SearchImpl> getOobWidgetListByServiceName(String serviceName) throws EMAnalyticsFwkException {
        LOGGER.debug("Calling MetaDataRetriever.getOobWidgetListByServiceName");
        List<SearchImpl> oobWidgetList = null;
        RegistryLookupUtil.VersionedLink versionedLink = RegistryLookupUtil.getServiceInternalLink(serviceName, "1.0+", "oob/widgets", null);
        if(versionedLink == null || versionedLink.getHref() == null){
            LOGGER.warn("{} has not provide api {} for fetching oob widgets", serviceName, "oob/widgets");
            return Collections.emptyList();
        }
        RestClient restClient = new RestClient();
        try {
            String response = restClient.get(versionedLink.getHref(), "CloudServices", versionedLink.getAuthToken());
            oobWidgetList = JSONUtil.fromJsonToListUnencoded(response, SearchImpl.class);
        } catch (UniformInterfaceException | ClientHandlerException responseException) {
            LOGGER.error("Fail to fecth OOB Widgets from {} - {}", serviceName, responseException.getMessage());
            throw new EMAnalyticsFwkException("Fail to fecth OOB Widgets from " + serviceName,
                    EMAnalyticsFwkException.ERR_GENERIC, null, responseException);
        } catch (NullPointerException | IOException convertException) {
            LOGGER.error("Fail into errors when convert the OOB Widgets from {} - {}", serviceName, convertException.getMessage());
            throw new EMAnalyticsFwkException("Fail into errors when convert the OOB Widgets from " + serviceName,
                    EMAnalyticsFwkException.JSON_JSON_TO_OBJECT, null, convertException);
        }
        if(oobWidgetList == null || oobWidgetList.isEmpty()){
            LOGGER.warn("No OOB Widgets data was fetched.");
            return Collections.emptyList();
        }
        return setWidgetDefaultValue(oobWidgetList, serviceName);
    }

    private List<SearchImpl> setWidgetDefaultValue(List<SearchImpl> oobWidgetList, String serviceName){
        LOGGER.debug("Calling MetaDataRetriever.setCategoryAndFolder");
        for(Search search : oobWidgetList){
            search.setIsWidget(Boolean.TRUE);
            if(search.getCategoryId() == null)
                search.setCategoryId(new BigInteger(CATEGORY_MAP.get(serviceName).toString()));
            if(search.getFolderId()== null)
                search.setFolderId(new BigInteger(FOLDER_MAP.get(serviceName).toString()));
        }
        return oobWidgetList;
    }
    
    public List<EmsResourceBundle> getResourceBundleByService(String serviceName) throws EMAnalyticsFwkException {
        LOGGER.debug("Calling MetaDataRetriever.getResourceBundleByServiceName");
        List<EmsResourceBundle> emsResourceBundles = null;
        RegistryLookupUtil.VersionedLink versionedLink = RegistryLookupUtil.getServiceInternalLink(serviceName, "1.0+", "oob/resource_bundle", null);
        if(versionedLink == null || versionedLink.getHref() == null){
            LOGGER.warn("{} has not provide api {} for fetching oob resource_bundle", serviceName, "oob/resource_bundle");
            return Collections.emptyList();
        }
        RestClient restClient = new RestClient();
        try {
            String response = restClient.get(versionedLink.getHref(), "CloudServices", versionedLink.getAuthToken());
            emsResourceBundles = JSONUtil.fromJsonToListUnencoded(response, EmsResourceBundle.class);
        } catch (UniformInterfaceException | ClientHandlerException responseException) {
            throw new EMAnalyticsFwkException("Fail to fecth resource bundle from " + serviceName,
                    EMAnalyticsFwkException.ERR_GENERIC, null, responseException);
        } catch (NullPointerException | IOException convertException) {
            throw new EMAnalyticsFwkException("Fail into errors when convert resource bundle from " + serviceName,
                    EMAnalyticsFwkException.JSON_JSON_TO_OBJECT, null, convertException);
        }
        return setDefaultResourceBundleValue(emsResourceBundles, serviceName);
    }

    private List<EmsResourceBundle> setDefaultResourceBundleValue(List<EmsResourceBundle> emsResourceBundles, String serviceName) {
        if(emsResourceBundles != null && serviceName != null) {
            for(EmsResourceBundle rb : emsResourceBundles) {
                rb.setServiceName(APPLICATION_MAP.get(serviceName));
                if(rb.getCountryCode() == null || rb.getCountryCode().isEmpty()) {
                    Locale locale = DatabaseResourceBundleUtil.generateLocale(rb.getLanguageCode());
                    if(rb.getLanguageCode().contains("-")) {
                        rb.setLanguageCode(locale.getLanguage());
                    }
                    rb.setCountryCode(locale.getCountry());
                }
            }
        }
        return emsResourceBundles;
    }
}
