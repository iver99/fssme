package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.metadata;

import mockit.Expectations;
import mockit.Mock;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.tool.InternalToolAPI;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataRetriever;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataStorer;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2017/5/5.
 */
@Test(groups = {"s2"})
public class MetadataRefreshAPITest {
    private MetadataRefreshAPI metadataRefreshAPI;
    private InternalToolAPI internalToolAPI;
    @Mocked
    MetaDataRetriever metaDataRetriever;
    @Mocked
    MetaDataStorer metaDataStorer;
    @Mocked
    DependencyStatus dependencyStatus;

    @Mocked
    SearchManager searchManager;
    @Test
    public void testGetWidgetByName() throws EMAnalyticsFwkException {
        final String widgetName = "widgetName";
        final List<Search> searches = new ArrayList<>();
        searches.add(new SearchImpl());
        new Expectations(){
            {
                DependencyStatus.getInstance();
                result = dependencyStatus;
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getWidgetByName(widgetName);
                result = searches;
            }
        };
        internalToolAPI = new InternalToolAPI();
        internalToolAPI.getWidgetByName(widgetName);
    }
}