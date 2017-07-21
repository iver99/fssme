package oracle.sysman.emaas.platform.savedsearch.services;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataRetriever;
import org.testng.annotations.Test;
import weblogic.application.ApplicationLifecycleEvent;

import java.util.Collections;

import static org.testng.Assert.*;


@Test(groups = {"s2"})
public class MetaDataManagerTest {
    @Mocked
    ApplicationLifecycleEvent applicationLifecycleEvent;
    @Mocked
    MetaDataRetriever metaDataRetriever;
    @Mocked
    EMAnalyticsFwkException emAnalyticsFwkException;
    @Test
    public void testPostStart() throws Exception {
        MetaDataManager metaDataManager = new MetaDataManager();
        new Expectations(){
            {
                metaDataRetriever.getOobWidgetListByServiceName(anyString);
                result = Collections.EMPTY_LIST;
            }
        };
        metaDataManager.postStart(applicationLifecycleEvent);
    }
    @Test
    public void testPostStart_EMAnalyticsFwkException() throws Exception {
        MetaDataManager metaDataManager = new MetaDataManager();
        new Expectations(){
            {
                metaDataRetriever.getOobWidgetListByServiceName(anyString);
                result = emAnalyticsFwkException;
            }
        };
        metaDataManager.postStart(applicationLifecycleEvent);
    }
    @Test
    public void testPostStart_EMAnalyticsFwkException_second() throws Exception {
        MetaDataManager metaDataManager = new MetaDataManager();
        new Expectations(){
            {
                metaDataRetriever.getResourceBundleByService(anyString);
                result = emAnalyticsFwkException;
            }
        };
        metaDataManager.postStart(applicationLifecycleEvent);
    }
}