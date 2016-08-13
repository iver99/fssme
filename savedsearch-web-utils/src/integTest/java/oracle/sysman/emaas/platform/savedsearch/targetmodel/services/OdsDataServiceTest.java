package oracle.sysman.emaas.platform.savedsearch.targetmodel.services;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.emaas.platform.savedsearch.utils.RestRequestUtil;

import org.apache.http.HttpException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URISyntaxException;

@Test(groups = {"s2"})
public class OdsDataServiceTest {
	public static final String MOCKED_RESULT = "{\"meClass\":\"TARGET\", \"meId\":\"odsentityid\"}";
	OdsDataService odsService = OdsDataServiceImpl.getInstance();
	
	public void testCreateOdsEntity(@Mocked final RegistryLookupUtil registryLookupUtil, @Mocked final Link link,
			@Mocked final RestRequestUtil restRequestUtil, @Mocked final TenantContext context,
			@Mocked final TenantInfo info) throws Exception {
		new Expectations() {
			{
				TenantContext.getContext();
				result = info;
				info.gettenantName();
				result = "tenant";
				RegistryLookupUtil.getServiceInternalHttpLink(anyString, anyString, anyString, anyString);
				result = link;
				link.getHref();
				result = "http://xxx";
				RestRequestUtil.restGet(anyString);
				result = MOCKED_RESULT;
				RestRequestUtil.restPost(anyString, anyString);
				result = MOCKED_RESULT;
			}
		};
		
		Assert.assertEquals(odsService.createOdsEntity("999", "searchName"), "odsentityid");
	}
	
	public void testDeleteOdsEntity(@Mocked final SearchManager sman, @Mocked final RegistryLookupUtil registryLookupUtil, 
			@Mocked final Link link, @Mocked final RestRequestUtil restRequestUtil, @Mocked final TenantContext context,
			@Mocked final TenantInfo info) throws Exception {
		new Expectations() {
			{
				TenantContext.getContext();
				result = info;
				info.gettenantName();
				result = "tenant";
				RegistryLookupUtil.getServiceInternalHttpLink(anyString, anyString, anyString, anyString);
				result = link;
				link.getHref();
				result = "http://xxx";
				sman.getSearchParamByName(anyLong, anyString);
				result = "meid";
				RestRequestUtil.restDelete(anyString);
				result = MOCKED_RESULT;
			};
		};
		
		odsService.deleteOdsEntity(999);
	}
	
	public void testCreateOdsEntityType(@Mocked final RegistryLookupUtil registryLookupUtil, @Mocked final Link link,
			@Mocked final RestRequestUtil restRequestUtil, @Mocked final TenantContext context,
			@Mocked final TenantInfo info) throws Exception {
		new Expectations() {
			{
				TenantContext.getContext();
				result = info;
				info.gettenantName();
				result = "tenant";
				RegistryLookupUtil.getServiceInternalHttpLink(anyString, anyString, anyString, anyString);
				result = link;
				link.getHref();
				result = "http://xxx";
				RestRequestUtil.restGet(anyString);
				result = MOCKED_RESULT;
				result = new EMAnalyticsFwkException("testCreateOdsEntityType", 1, null);
				RestRequestUtil.restPost(anyString, anyString);
				result = MOCKED_RESULT;
			}
		};
		
		Assert.assertEquals(odsService.createOdsEntityType("entityType"), MOCKED_RESULT);	// success to get entity type
		Assert.assertEquals(odsService.createOdsEntityType("entityType"), MOCKED_RESULT);	// failed to get entity type but success to create one
	}
}
