package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;



import javax.servlet.http.HttpServletRequest;

import mockit.Expectations;
import mockit.Mocked;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.RequestContext;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.BasicServiceMalfunctionException;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.model.tenant.TenantIdProcessor;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/24/2016.
 */
@Test(groups = { "s2" })
public class HeadersUtilTest
{
	@Mocked
	HttpServletRequest request;
	@Mocked
	RequestContext requestContext;
	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetTenantInfoEMAnalyticsFwkException() throws EMAnalyticsFwkException {
		HeadersUtil.getTenantInfo( request);
	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetTenantInfoInternalTenant() throws EMAnalyticsFwkException {
		new Expectations(){
			{
				request.getHeader(anyString);
				result = null;
				RequestContext.getContext();
				result = RequestContext.RequestType.INTERNAL_TENANT;
			}
		};
		HeadersUtil.getTenantInfo( request);
	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetTenantInfoFormInvalid1() throws EMAnalyticsFwkException {
		new Expectations(){
			{
				request.getHeader(anyString);
				result = " .";
			}
		};
		HeadersUtil.getTenantInfo( request);
	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetTenantInfoFormInvalid2() throws EMAnalyticsFwkException {
		new Expectations(){
			{
				request.getHeader(anyString);
				result = "name.";
			}
		};
		HeadersUtil.getTenantInfo( request);
	}

	@Test
	public void testGetTenantInfo() throws EMAnalyticsFwkException {
		new Expectations(){
			{
				request.getHeader("OAM_REMOTE_USER");
				result = "1.emcsadmintest";
				request.getHeader("ssfheadertest");
				result = "ssfheadertest";
			}
		};
		HeadersUtil.getTenantInfo( request);
	}

	@Test
	public void testGetTenantInfoNumberFormatException() throws EMAnalyticsFwkException {
		new Expectations(){
			{
				request.getHeader("OAM_REMOTE_USER");
				result = "ssf1.emcsadmintest";
				request.getHeader("ssfheadertest");
				result = "ssfheadertest";
			}
		};
		HeadersUtil.getTenantInfo( request);
	}

	@Mocked
	TenantIdProcessor tenantIdProcessor;
	@Test
	public void testGetTenantInfoTestHeadNullInternalTenant() throws EMAnalyticsFwkException, BasicServiceMalfunctionException {
		new Expectations(){
			{
				request.getHeader("OAM_REMOTE_USER");
				result = "ssf1.emcsadmintest";
				request.getHeader("ssfheadertest");
				result = null;
				RequestContext.getContext();
				result = RequestContext.RequestType.INTERNAL_TENANT;
				request.getHeader("X-USER-IDENTITY-DOMAIN-NAME");
				result = "ssf1.emcsadmintest";
				TenantIdProcessor.getInternalTenantIdFromOpcTenantId(anyString);
				result = 1L;
			}
		};
		HeadersUtil.getTenantInfo(request);
	}

	@Test
	public void testGetTenantInfoTestHeadNull() throws EMAnalyticsFwkException, BasicServiceMalfunctionException {
		new Expectations(){
			{
				request.getHeader("OAM_REMOTE_USER");
				result = "ssf1.emcsadmintest";
				request.getHeader("ssfheadertest");
				result = null;
				RequestContext.getContext();
				result = RequestContext.RequestType.EXTERNAL;
				TenantIdProcessor.getInternalTenantIdFromOpcTenantId(anyString);
				result = 1L;
			}
		};
		HeadersUtil.getTenantInfo(request);
	}
 	@Mocked
 	Throwable throwable;
	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetTenantInfoTestHeadBasicServiceMalfunctionExceptionl() throws EMAnalyticsFwkException, BasicServiceMalfunctionException {
		new Expectations(){
			{
				request.getHeader("OAM_REMOTE_USER");
				result = "ssf1.emcsadmintest";
				request.getHeader("ssfheadertest");
				result = null;
				RequestContext.getContext();
				result = RequestContext.RequestType.EXTERNAL;
				TenantIdProcessor.getInternalTenantIdFromOpcTenantId(anyString);
				result = new BasicServiceMalfunctionException("","",1,throwable);
			}
		};
		HeadersUtil.getTenantInfo(request);
	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetTenantInfoTestHeadResultNull() throws EMAnalyticsFwkException, BasicServiceMalfunctionException {
		new Expectations(){
			{
				request.getHeader("OAM_REMOTE_USER");
				result = "ssf1.emcsadmintest";
				request.getHeader("ssfheadertest");
				result = null;
				RequestContext.getContext();
				result = RequestContext.RequestType.EXTERNAL;
				TenantIdProcessor.getInternalTenantIdFromOpcTenantId(anyString);
				result = null;
			}
		};
		HeadersUtil.getTenantInfo(request);
	}
}