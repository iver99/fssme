package oracle.sysman.emaas.platform.savedsearch.metadata;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.JSONUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by xiadai on 2017/5/4.
 */
public class MetaDataRetriever {
    private static final Logger LOGGER = LogManager.getLogger(RegistryLookupUtil.class);

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
    private static final String SERVICENAME_APM = "ApmDataServer";
    private static final String SERVICENAME_ITA = "TargetAnalytics";
    private static final String SERVICENAME_LA = "LogAnalyticsProcessor";
    private static final String SERVICENAME_SECURITY_ANALYTICS = "SecurityAnalytics";
    private static final String SERVICENAME_ORCHESTRATION = "CosFacadeService";
    private static final String SERVICENAME_UDE = "UDE";

    //JUST FOR TEST TO BE DELETED
    private static final String SERVICENAME_SAVEDSEARCH = "SavedSearch";

    static {
        CATEGORY_MAP.put(SERVICENAME_LA, CategoryName.LA.categoryId);
        CATEGORY_MAP.put(SERVICENAME_UDE, CategoryName.UDE.categoryId);
        CATEGORY_MAP.put(SERVICENAME_ITA, CategoryName.ITA.categoryId);
        CATEGORY_MAP.put(SERVICENAME_APM, CategoryName.APM.categoryId);
        CATEGORY_MAP.put(SERVICENAME_SECURITY_ANALYTICS, CategoryName.SMA.categoryId);
        CATEGORY_MAP.put(SERVICENAME_ORCHESTRATION, CategoryName.OCS.categoryId);
        //JUST FOR TEST TO BE DELETED
        CATEGORY_MAP.put(SERVICENAME_SAVEDSEARCH, CategoryName.UDE.categoryId);

        FOLDER_MAP.put(SERVICENAME_LA, FolderName.LA.folderId);
        FOLDER_MAP.put(SERVICENAME_UDE, FolderName.UDE.folderId);
        FOLDER_MAP.put(SERVICENAME_ITA, FolderName.ITA.folderId);
        FOLDER_MAP.put(SERVICENAME_APM, FolderName.APM.folderId);
        FOLDER_MAP.put(SERVICENAME_SECURITY_ANALYTICS, FolderName.SMA.folderId);
        FOLDER_MAP.put(SERVICENAME_ORCHESTRATION, FolderName.OCS.folderId);
        //JUST FOR TEST TO BE DELETED
        FOLDER_MAP.put(SERVICENAME_SAVEDSEARCH, FolderName.UDE.folderId);
    }

    public List<SearchImpl> getOobWidgetListByServiceName(String serviceName) throws EMAnalyticsFwkException {
        LOGGER.debug("Calling MetaDataRetriever.getOobWidgetListByServiceName");
        List<SearchImpl> oobWidgetList = new ArrayList<>();
        VersionedLink versionedLink = RegistryLookupUtil.getServiceInternalLink(serviceName, "1.0+", "oob/widgets", null);
        if(versionedLink == null){
            LOGGER.error("{} has not provide api {} for fetching oob widgets", serviceName, "oob/widgets");
            throw new EMAnalyticsFwkException("{} has not provide api {} for fetching oob widgets",
                    EMAnalyticsFwkException.ERR_GENERIC,null);
        }
        RestClient restClient = new RestClient();
        String response = restClient.get(versionedLink.getHref(),"CloudServices",versionedLink.getAuthToken());
        LOGGER.error("GET response for fetching oob: {}",response);
        try {
            oobWidgetList = JSONUtil.fromJsonToListUnencoded(response, SearchImpl.class);
        } catch (NullPointerException npe){
            LOGGER.error("Fail into errors when convert the OOB Widgets, may be some params were missing");
            throw new EMAnalyticsFwkException("Fail into errors when convert the OOB Widgets, may be some params were missing",
                    EMAnalyticsFwkException.ERR_GENERIC,null);
        }catch (IOException e) {
            LOGGER.error("Fail into errors when convert the OOB Widgets");
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
            search.setCategoryId(new BigInteger(CATEGORY_MAP.get(serviceName).toString()));
            search.setFolderId(new BigInteger(FOLDER_MAP.get(serviceName).toString()));
        }
        return oobWidgetList;
    }
}
