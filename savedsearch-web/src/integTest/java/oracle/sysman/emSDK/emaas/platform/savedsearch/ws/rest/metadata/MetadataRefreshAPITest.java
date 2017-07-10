package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.metadata;

import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.thread.OobRefreshRunnable;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.tool.InternalToolAPI;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataRetriever;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataStorer;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;

import org.testng.annotations.Test;

/**
 * Created by xiadai on 2017/5/5.
 */
@Test(groups = {"s2"})
public class MetadataRefreshAPITest {
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
    public void testRefreshOOB(){
        MetadataRefreshAPI metadataRefreshAPI = new MetadataRefreshAPI();
        metadataRefreshAPI.refreshOOB("ITA");
    }
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

    @Test
    public void testOobRefreshRunnable(@Mocked MetaDataStorer metaDataStorer, @Mocked final MetaDataRetriever metaDataRetriever) throws EMAnalyticsFwkException {

        OobRefreshRunnable oobRefreshRunnable = new OobRefreshRunnable();
        oobRefreshRunnable.run();
        new Expectations(){
            {
                DependencyStatus.getInstance();
                result = dependencyStatus;
                dependencyStatus.isDatabaseUp();
                result = true;
            }
        };
        oobRefreshRunnable.setServiceName("serviceName");
        oobRefreshRunnable.run();
    }
}