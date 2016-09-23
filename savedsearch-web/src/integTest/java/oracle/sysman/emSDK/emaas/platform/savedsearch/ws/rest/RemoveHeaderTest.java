package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest;

import mockit.Mocked;
import org.testng.Assert;
import org.testng.annotations.Test;


import javax.servlet.http.HttpServletRequest;


/**
 * Created by xidai on 2/19/2016.
 */
@Test(groups={"s1"})
public class RemoveHeaderTest {

    @Mocked
    HttpServletRequest httpServletRequest;


    @Test
    public void testGetHeader() throws Exception {
        RemoveHeader removeHeader = new RemoveHeader(httpServletRequest);
        Assert.assertEquals(null,removeHeader.getHeader("header"));

    }

    @Test
    public void testGetHeaderNames() throws Exception {
        RemoveHeader removeHeader = new RemoveHeader(httpServletRequest);
        Assert.assertNotNull(removeHeader.getHeaderNames());
    }
}