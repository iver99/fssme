package oracle.sysman.emaas.platform.savedsearch.services;

import java.util.Collections;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.OOBWidgetExpiredNotification;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataRetriever;

import org.testng.Assert;
import org.testng.annotations.Test;

import weblogic.application.ApplicationLifecycleEvent;


@Test(groups = {"s2"})
public class MetaDataManagerTest {
    @Mocked
    ApplicationLifecycleEvent applicationLifecycleEvent;
    @Mocked
    MetaDataRetriever metaDataRetriever;
    @Mocked
    EMAnalyticsFwkException emAnalyticsFwkException;
    @Mocked
    OOBWidgetExpiredNotification oobNotification;
    
    @Test
    public void testGetName() throws Exception {
        MetaDataManager metaDataManager = new MetaDataManager();
        Assert.assertEquals(metaDataManager.getName(), "MetaData Services");
    }
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
    public void testPostStartEMAnalyticsFwkException() throws Exception {
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
    public void testPostStartEMAnalyticsFwkException2() throws Exception {
        MetaDataManager metaDataManager = new MetaDataManager();
        new Expectations(){
            {
                metaDataRetriever.getResourceBundleByService(anyString);
                result = emAnalyticsFwkException;
            }
        };
        metaDataManager.postStart(applicationLifecycleEvent);
    }
    @Test
    public void testPostStartException() throws Exception {
        MetaDataManager metaDataManager = new MetaDataManager();
        new Expectations(){
            {
                oobNotification.notify(anyString);
                result = new Exception("Test Exception thrown by OOBWidgetExpiredNotification.notify(string) ");
            }
        };
        metaDataManager.postStart(applicationLifecycleEvent);
    }
}