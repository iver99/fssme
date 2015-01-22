package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.HeadersUtil;

/**
 * Support across domain access CORS: Cross-Origin Resource Sharing Reference: http://enable-cors.org/ http://www.w3.org/TR/cors/
 * http://en.wikipedia.org/wiki/Cross-origin_resource_sharing
 * 
 * @author miayu
 */
public class SavedSearchCORSFilter implements Filter
{
	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{
		HttpServletResponse hRes = (HttpServletResponse) response;
		hRes.addHeader("Access-Control-Allow-Origin", "*");
		hRes.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); //add more methods as necessary
		hRes.addHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, X-USER-IDENTITY-DOMAIN-NAME, Authorization, x-sso-client");

		String tenantId = HeadersUtil.getTenantId((HttpServletRequest) request);
		if (tenantId == null || tenantId.trim().length() == 0) {
			hRes.sendError(HttpServletResponse.SC_BAD_REQUEST, "Please specify tenantid");
			return;
		}
		TenantContext.setContext(tenantId);
		try {
			chain.doFilter(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			//always remove tenant-id from thradlocal when request completed or on error 
			TenantContext.clearContext();
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException
	{
	}

}
