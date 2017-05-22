package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.ws.test;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static oracle.sysman.emSDK.emaas.authz.listener.AuthorizationListener.OAM_REMOTE_USER_HEADER;

import org.testng.annotations.Test;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.SavedsearchComparatorCORSFilter;

@Test(groups={"s2"})
public class SavedSearchComparatorCORSFilterTest {
	private SavedsearchComparatorCORSFilter dashboardsComparatorCORSFilter = new SavedsearchComparatorCORSFilter();
    @Mocked
    HttpServletResponse httpServletResponse;
    @Mocked
    HttpServletRequest httpServletRequest;
    @Mocked
    FilterChain chain;
    @Mocked
    FilterConfig filterConfig;
    @Test
    public void testDestroy() {
        dashboardsComparatorCORSFilter.destroy();
    }
    
    @Test
    public void testDoFilter() throws Exception {
        new Expectations(){
            {
                httpServletRequest.getHeader("Origin");
                result = "origin";
                httpServletRequest.getHeader(OAM_REMOTE_USER_HEADER);
                result = "remote_user.user";
            }
        };
        dashboardsComparatorCORSFilter.doFilter(httpServletRequest, httpServletResponse, chain);
    }
    @Test
    public void testInit() throws ServletException {
        dashboardsComparatorCORSFilter.init(filterConfig);
    }
}
