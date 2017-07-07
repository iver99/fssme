package oracle.sysman.emaas.platform.savedsearch.metadata;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.JSONUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.nls.DatabaseResourceBundleUtil;
import oracle.sysman.emaas.platform.savedsearch.entity.EmsResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by xiadai on 2017/5/4.
 */
public class MetaDataRetriever {
    private static final Logger LOGGER = LogManager.getLogger(MetaDataRetriever.class);

    private enum CategoryName{
        LA(1), UDE(2), ITA(3), APM(4), SMA(6), OCS(7);

        private final int categoryId;
        CategoryName(int categoryId){
            this.categoryId = categoryId;
        }
    }

    private enum FolderName{
        LA(2), UDE(4), ITA(3), APM(5), SMA(7), OCS(8);

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
    //    public static final String SERVICENAME_MONITORING = "Monitoring";
    public static final String SERVICENAME_SECURITY_ANALYTICS = "SecurityAnalyticsUI";
    public static final String SERVICENAME_ORCHESTRATION = "CosUIService";
    //    public static final String SERVICENAME_COMPLIANCE = "Compliance";
    public static final String SERVICENAME_UDE = "TargetAnalytics";

    public static final String APM_STRING = "APM";
    public static final String ITA_SRING = "ITAnalytics";
    public static final String LA_STRING = "LogAnalytics";
    public static final String SECURITY_ANALYTICS_STRING = "SecurityAnalytics";
    public static final String ORCHESTRATION_STRING = "Orchestration";
    public static final String UDE_STRING = "UDE";


    static {
        CATEGORY_MAP.put(SERVICENAME_LA, CategoryName.LA.categoryId);
        CATEGORY_MAP.put(SERVICENAME_UDE, CategoryName.UDE.categoryId);
        CATEGORY_MAP.put(SERVICENAME_ITA, CategoryName.ITA.categoryId);
        CATEGORY_MAP.put(SERVICENAME_APM, CategoryName.APM.categoryId);
        CATEGORY_MAP.put(SERVICENAME_SECURITY_ANALYTICS, CategoryName.SMA.categoryId);
        CATEGORY_MAP.put(SERVICENAME_ORCHESTRATION, CategoryName.OCS.categoryId);

        FOLDER_MAP.put(SERVICENAME_LA, FolderName.LA.folderId);
        FOLDER_MAP.put(SERVICENAME_UDE, FolderName.UDE.folderId);
        FOLDER_MAP.put(SERVICENAME_ITA, FolderName.ITA.folderId);
        FOLDER_MAP.put(SERVICENAME_APM, FolderName.APM.folderId);
        FOLDER_MAP.put(SERVICENAME_SECURITY_ANALYTICS, FolderName.SMA.folderId);
        FOLDER_MAP.put(SERVICENAME_ORCHESTRATION, FolderName.OCS.folderId);

        APPLICATION_MAP.put(SERVICENAME_LA, LA_STRING);
        APPLICATION_MAP.put(SERVICENAME_UDE, UDE_STRING);
        APPLICATION_MAP.put(SERVICENAME_ITA, ITA_SRING);
        APPLICATION_MAP.put(SERVICENAME_APM, APM_STRING);
        APPLICATION_MAP.put(SERVICENAME_SECURITY_ANALYTICS, SECURITY_ANALYTICS_STRING);
        APPLICATION_MAP.put(SERVICENAME_ORCHESTRATION, ORCHESTRATION_STRING);
    }

    public List<SearchImpl> getOobWidgetListByServiceName(String serviceName) throws EMAnalyticsFwkException {
        LOGGER.debug("Calling MetaDataRetriever.getOobWidgetListByServiceName");
        List<SearchImpl> oobWidgetList = new ArrayList<>();
        VersionedLink versionedLink = RegistryLookupUtil.getServiceInternalHttpLink(serviceName, "1.0+", "oob/widgets", null);
        if(versionedLink == null || versionedLink.getHref() == null){
            LOGGER.warn("{} has not provide api {} for fetching oob widgets", serviceName, "oob/widgets");
            return Collections.emptyList();
        }
        RestClient restClient = new RestClient();
        String response = restClient.get(versionedLink.getHref(),"CloudServices",versionedLink.getAuthToken());
        try {
            oobWidgetList = JSONUtil.fromJsonToListUnencoded(response, SearchImpl.class);
        } catch (NullPointerException npe){
            throw new EMAnalyticsFwkException("Fail into errors when convert the OOB Widgets, may be some params were missing",
                    EMAnalyticsFwkException.ERR_GENERIC,null);
        }catch (IOException e) {
            LOGGER.warn("Fail into errors when convert the OOB Widgets");
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
        List<EmsResourceBundle> emsResourceBundles = new ArrayList<>();
        VersionedLink versionedLink = RegistryLookupUtil.getServiceInternalHttpLink(serviceName, "1.0+", "oob/resource_bundle", null);
        if(versionedLink == null || versionedLink.getHref() == null){
            LOGGER.warn("{} has not provide api {} for fetching oob resource_bundle", serviceName, "oob/resource_bundle");
        }
        RestClient restClient = new RestClient();
        String response = restClient.get(versionedLink.getHref(),"CloudServices",versionedLink.getAuthToken());
        try {
            emsResourceBundles = JSONUtil.fromJsonToListUnencoded(response, EmsResourceBundle.class);
        } catch (NullPointerException npe){
            LOGGER.warn("Fail into errors when convert the OOB Widgets, may be some params were missing");
            throw new EMAnalyticsFwkException("Fail into errors when convert the OOB Widgets, may be some params were missing",
                    EMAnalyticsFwkException.ERR_GENERIC,null);
        }catch (IOException e) {
            LOGGER.warn("Fail into errors when convert the OOB Widgets");
        }
        return setDefaultResourceBundleValue(emsResourceBundles, serviceName);
    }

    private List<EmsResourceBundle> setDefaultResourceBundleValue(List<EmsResourceBundle> emsResourceBundles, String serviceName) {
        if(emsResourceBundles != null || serviceName != null) {
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
