package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest;


import mockit.*;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.RequestContext;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * Created by xidai on 2/19/2016.
 */
@Test(groups={"s2"})
public class SavedSearchCORSFilterTest {
    private SavedSearchCORSFilter savedSearchCORSFilter = new SavedSearchCORSFilter();
    @Mocked
    HttpServletRequest request;
    @Mocked
    HttpServletResponse response;
    @Mocked
    FilterChain chain;
    @Mocked
    RequestContext requestContext;
    @Mocked
    Principal principal;
    @Test(groups={"s1"})
    public void testDoFilter() throws Exception {
        new Expectations(){
            {
               request.getMethod();
                result = "OPTIONS";
            }
        };
        savedSearchCORSFilter.doFilter(request, response, chain);
    }
    @Test(groups={"s1"})
    public void testDoFilterNotOptions() throws Exception {
        new Expectations(){
            {
                request.getHeader(anyString);
                result = "head";
                requestContext.setContext((RequestContext.RequestType)any);
            }
        };
        savedSearchCORSFilter.doFilter(request, response, chain);
    }
}