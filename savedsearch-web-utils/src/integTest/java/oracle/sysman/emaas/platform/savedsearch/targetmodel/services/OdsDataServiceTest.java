package oracle.sysman.emaas.platform.savedsearch.targetmodel.services;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emaas.platform.savedsearch.utils.RegistryLookupUtil;
import oracle.sysman.emaas.platform.savedsearch.utils.RestRequestUtil;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"s2"})
public class OdsDataServiceTest {
	public static final String MOCKED_RESULT = "{\"meClass\":\"TARGET\", \"meId\":\"odsentityid\"}";
	OdsDataService odsService = OdsDataServiceImpl.getInstance();
	
	public void testCreateOdsEntity(@Mocked final RegistryLookupUtil registryLookupUtil, @Mocked final Link link,
			@Mocked final RestRequestUtil restRequestUtil) throws Exception {
		new Expectations() {
			{
				RegistryLookupUtil.getServiceInternalLink(anyString, anyString, anyString, anyString);
				result = link;
				link.getHref();
				result = "http://xxx";
				RestRequestUtil.restGet(anyString, anyString);
				result = MOCKED_RESULT;
				RestRequestUtil.restPost(anyString, anyString, anyString);
				result = MOCKED_RESULT;
			}
		};
		
		Assert.assertEquals(odsService.createOdsEntity("999", "searchName", "tenantName"), "odsentityid");
	}
	
	public void testDeleteOdsEntity(@Mocked final SearchManager sman, @Mocked final RegistryLookupUtil registryLookupUtil, 
			@Mocked final Link link, @Mocked final RestRequestUtil restRequestUtil) throws Exception {
		new Expectations() {
			{
				RegistryLookupUtil.getServiceInternalLink(anyString, anyString, anyString, anyString);
				result = link;
				link.getHref();
				result = "http://xxx";
				sman.getSearchParamByName(anyLong, anyString);
				result = "meid";
				RestRequestUtil.restDelete(anyString, anyString);
				result = MOCKED_RESULT;
			};
		};
		
		odsService.deleteOdsEntity(999, "tenantName");
	}
	
	public void testCreateOdsEntityType(@Mocked final RegistryLookupUtil registryLookupUtil, @Mocked final Link link,
			@Mocked final RestRequestUtil restRequestUtil) throws Exception {
		new Expectations() {
			{
				RegistryLookupUtil.getServiceInternalLink(anyString, anyString, anyString, anyString);
				result = link;
				link.getHref();
				result = "http://xxx";
				RestRequestUtil.restGet(anyString, anyString);
				result = MOCKED_RESULT;
				result = new EMAnalyticsFwkException("testCreateOdsEntityType", 1, null);
				RestRequestUtil.restPost(anyString, anyString, anyString);
				result = MOCKED_RESULT;
			}
		};
		
		Assert.assertEquals(odsService.createOdsEntityType("entityType", "tenantName"), MOCKED_RESULT);	// success to get entity type
		Assert.assertEquals(odsService.createOdsEntityType("entityType", "tenantName"), MOCKED_RESULT);	// failed to get entity type but success to create one
	}
}
