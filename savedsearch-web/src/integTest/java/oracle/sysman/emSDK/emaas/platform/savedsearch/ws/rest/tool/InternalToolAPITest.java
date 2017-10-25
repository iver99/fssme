package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.tool;

import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantManager;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.BasicServiceMalfunctionException;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.model.tenant.TenantIdProcessor;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups={"s1"})
public class InternalToolAPITest {
    private InternalToolAPI api = new InternalToolAPI();
    @Mocked
    DependencyStatus dependencyStatus;
    @Mocked
    SearchManager searchManager;
    @Mocked
    TenantIdProcessor processor;
    @Mocked
    TenantManager tenantManager;
    
    @Test
    public void testGetWidgetByName() throws EMAnalyticsFwkException {
        Assert.assertEquals(api.getWidgetByName(null).getStatus(), 400);
        Assert.assertEquals(api.getWidgetByName("").getStatus(), 400);
        
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = false;
            }
        };
        Assert.assertEquals(api.getWidgetByName("widgetName").getStatus(), 505);
        
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getWidgetByName(anyString);
                result = new Exception();
            }
        };
        Assert.assertEquals(api.getWidgetByName("widgetName").getStatus(), 505);
        
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getWidgetByName(anyString);
                result = new ArrayList<Search>();
            }
        };
        Assert.assertEquals(api.getWidgetByName("widgetName").getStatus(), 200);
        
        final List<Search> list = new ArrayList<Search>();
        list.add(new SearchImpl());
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getWidgetByName(anyString);
                result = list;
            }
        };
        Assert.assertEquals(api.getWidgetByName("widgetName").getStatus(), 200);
    }
    
    @Test
    public void testDeleteTenant() throws BasicServiceMalfunctionException, EMAnalyticsFwkException {
        Assert.assertEquals(api.deleteTenant(null).getStatus(), 400);
        Assert.assertEquals(api.deleteTenant("").getStatus(), 400);
        
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = false;
            }
        };
        Assert.assertEquals(api.deleteTenant("tenant").getStatus(), 500);
        
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                TenantIdProcessor.getInternalTenantIdFromOpcTenantId(anyString);
                result = null;
            }
        };
        Assert.assertEquals(api.deleteTenant("tenant").getStatus(), 404);
        
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                TenantIdProcessor.getInternalTenantIdFromOpcTenantId(anyString);
                result = 1L;
                TenantManager.getInstance();
                result = tenantManager;
                tenantManager.cleanTenant(anyLong);
                result = new Exception("someerrors");
            }
        };
        Assert.assertEquals(api.deleteTenant("tenant").getStatus(), 500);
    
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                TenantIdProcessor.getInternalTenantIdFromOpcTenantId(anyString);
                result = 1L;
                TenantManager.getInstance();
                result = tenantManager;
                tenantManager.cleanTenant(anyLong);
            }
        };
        Assert.assertEquals(api.deleteTenant("tenant").getStatus(), 200);
    }
    
}
