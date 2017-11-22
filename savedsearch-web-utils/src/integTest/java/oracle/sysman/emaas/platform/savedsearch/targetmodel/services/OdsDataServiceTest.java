package oracle.sysman.emaas.platform.savedsearch.targetmodel.services;

import java.math.BigInteger;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.emcpdf.registry.RegistryLookupUtil;
import oracle.sysman.emaas.platform.savedsearch.utils.RestRequestUtil;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"s2"})
public class OdsDataServiceTest {
	public static final String MOCKED_RESULT = "{\"meClass\":\"TARGET\", \"meId\":\"odsentityid\"}";
	public static final String MOCKED_AUTH = "authToken";
	OdsDataService odsService = OdsDataServiceImpl.getInstance();
	
	public void testCreateOdsEntity(@Mocked final RegistryLookupUtil registryLookupUtil,
			@Mocked final RestRequestUtil restRequestUtil, @Mocked final TenantContext context,
			@Mocked final TenantInfo info, @Mocked final RegistryLookupUtil.VersionedLink linkInfo) throws Exception {
		new Expectations() {
			{
				TenantContext.getContext();
				result = info;
				info.gettenantName();
				result = "tenant";
				RegistryLookupUtil.getServiceInternalLink(anyString, anyString, anyString, anyString);
				result = linkInfo;
				linkInfo.getHref();
				result = "http://xxx";
				linkInfo.getAuthToken();
				result = MOCKED_AUTH;
				RestRequestUtil.restGet(anyString, anyString);
				result = MOCKED_RESULT;
				RestRequestUtil.restPost(anyString, anyString, anyString);
				result = MOCKED_RESULT;
			}
		};
		
		Assert.assertEquals(odsService.createOdsEntity("999", "searchName"), "odsentityid");
	}
	
	public void testDeleteOdsEntity(@Mocked final SearchManager sman, @Mocked final RegistryLookupUtil registryLookupUtil, 
			@Mocked final RestRequestUtil restRequestUtil, @Mocked final TenantContext context,
			@Mocked final TenantInfo info, @Mocked final RegistryLookupUtil.VersionedLink linkInfo) throws Exception {
		new Expectations() {
			{
				TenantContext.getContext();
				result = info;
				info.gettenantName();
				result = "tenant";
				RegistryLookupUtil.getServiceInternalLink(anyString, anyString, anyString, anyString);
				result = linkInfo;
				linkInfo.getHref();
				result = "http://xxx";
				linkInfo.getAuthToken();
				result = MOCKED_AUTH;
				sman.getSearchParamByName((BigInteger) any, anyString);
				result = "meid";
				RestRequestUtil.restDelete(anyString, anyString);
				result = MOCKED_RESULT;
			};
		};
		
		odsService.deleteOdsEntity(new BigInteger("999"));
	}
	
	public void testCreateOdsEntityType(@Mocked final RegistryLookupUtil registryLookupUtil, 
			@Mocked final RestRequestUtil restRequestUtil, @Mocked final TenantContext context,
			@Mocked final TenantInfo info, @Mocked final RegistryLookupUtil.VersionedLink linkInfo) throws Exception {
		new Expectations() {
			{
				TenantContext.getContext();
				result = info;
				info.gettenantName();
				result = "tenant";
				RegistryLookupUtil.getServiceInternalLink(anyString, anyString, anyString, anyString);
				result = linkInfo;
				linkInfo.getHref();
				result = "http://xxx";
				linkInfo.getAuthToken();
				result = MOCKED_AUTH;
				RestRequestUtil.restGet(anyString, anyString);
				result = MOCKED_RESULT;
				result = new EMAnalyticsFwkException("testCreateOdsEntityType", 1, null);
				RestRequestUtil.restPost(anyString, anyString, anyString);
				result = MOCKED_RESULT;
			}
		};
		
		Assert.assertEquals(odsService.createOdsEntityType("entityType"), MOCKED_RESULT);	// success to get entity type
		Assert.assertEquals(odsService.createOdsEntityType("entityType"), MOCKED_RESULT);	// failed to get entity type but success to create one
	}
}
