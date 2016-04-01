package oracle.sysman.emaas.platform.savedsearch.services;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.WidgetCacheManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationLifecycleEvent;
import weblogic.management.timer.Timer;

import javax.management.Notification;

import java.util.Date;

import static org.testng.Assert.*;

/**
 * Created by QIQIAN on 2016/3/28.
 */

@Test(groups = {"s1"})
public class WidgetCacheRefreshServiceManagerTest {
    WidgetCacheRefreshServiceManager widgetCacheRefreshServiceManager;

    @Test
    public void testGetName() throws Exception {
        widgetCacheRefreshServiceManager = new WidgetCacheRefreshServiceManager();
        Assert.assertEquals(widgetCacheRefreshServiceManager.getName(),"Saved Search Widget Cache Refresh Service");
    }

    @Test
    public void testHandleNotification() throws Exception {
        widgetCacheRefreshServiceManager = new WidgetCacheRefreshServiceManager();
        int i = 0;
        for(i=0;i<1801;++i)
            widgetCacheRefreshServiceManager.handleNotification(new Notification("type",new Object(),1234L),null);
    }

    @Test(groups = {"s2"})
    public void testHandleNotification_Mocked(@Mocked WidgetCacheManager widgetCacheManager) throws Exception {
        new Expectations(){
            {
                WidgetCacheManager.getInstance();
                result = new Exception();
            }
        };
        widgetCacheRefreshServiceManager = new WidgetCacheRefreshServiceManager();
        widgetCacheRefreshServiceManager.handleNotification(new Notification("type",new Object(),1234L),null);
    }


//    @Test
//    public void testPostStart(@Mocked final Timer timer) throws Exception {
//        new Expectations(){
//            {
//                withAny(timer).addNotificationListener((WidgetCacheRefreshServiceManager)any,null,null);
//                withAny(timer).addNotification("SavedSearchServiceWidgetCacheRefreshTimer", null, this, (Date)any,anyLong, 0);
//                withAny(timer).start();
//            }
//        };
//        widgetCacheRefreshServiceManager = new WidgetCacheRefreshServiceManager();
//        widgetCacheRefreshServiceManager.postStart(new ApplicationLifecycleEvent(null));
//    }

    @Test
    public void testPostStop() throws Exception {
        widgetCacheRefreshServiceManager = new WidgetCacheRefreshServiceManager();
        widgetCacheRefreshServiceManager.postStop(new ApplicationLifecycleEvent(null));
    }

    @Test
    public void testPreStart() throws Exception {
        widgetCacheRefreshServiceManager = new WidgetCacheRefreshServiceManager();
        widgetCacheRefreshServiceManager.preStart(new ApplicationLifecycleEvent(null));
    }

    @Test
    public void testPreStop() throws Exception {
        widgetCacheRefreshServiceManager = new WidgetCacheRefreshServiceManager();
        widgetCacheRefreshServiceManager.preStop(new ApplicationLifecycleEvent(null));
    }
}