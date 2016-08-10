package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;



import javax.servlet.http.HttpServletRequest;

import mockit.Mocked;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/24/2016.
 */
@Test(groups = { "s2" })
public class HeadersUtilTest
{
	@Mocked
	HttpServletRequest request;
	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetTenantInfo() throws Exception
	{
		HeadersUtil.getTenantInfo( request);
	}

}