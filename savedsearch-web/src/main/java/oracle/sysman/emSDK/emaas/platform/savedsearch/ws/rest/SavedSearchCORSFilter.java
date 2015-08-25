package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.HeadersUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Support across domain access CORS: Cross-Origin Resource Sharing Reference: http://enable-cors.org/ http://www.w3.org/TR/cors/
 * http://en.wikipedia.org/wiki/Cross-origin_resource_sharing
 * 
 * @author miayu
 */
public class SavedSearchCORSFilter implements Filter
{

	private static final String PARAM_NAME = "updateLastAccessTime";
	private static final Logger _logger = LogManager.getLogger(SavedSearchCORSFilter.class);

	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{
		HttpServletResponse hRes = (HttpServletResponse) response;
		HttpServletRequest hReq = (HttpServletRequest) request;
		Enumeration headerNames = hReq.getHeaderNames();
		if (headerNames.hasMoreElements()) {

			_logger.info("More elements");
		}
		else {
			_logger.info("There is no more element");
		}
		while (headerNames.hasMoreElements()) {
			Object elem = headerNames.nextElement();
			String paramName = (String) elem;
			_logger.info("Name=" + paramName);
		}

		// Only add CORS headers if the developer mode is enabled to add them
		if (new java.io.File("/var/opt/ORCLemaas/DEVELOPER_MODE-ENABLE_CORS_HEADERS").exists()) {

			hRes.addHeader("Access-Control-Allow-Origin", "*");
			if (hReq.getHeader("Origin") != null) {

				hRes.addHeader("Access-Control-Allow-Credentials", "true");
			}
			else {
				// non-specific origin, cannot support cookies
				//hRes.addHeader("Access-Control-Allow-Origin", "*");
			}
			hRes.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); //add more methods as necessary
			hRes.addHeader("Access-Control-Allow-Headers",
					"Origin, X-Requested-With, Content-Type, Accept,  OAM_REMOTE_USER,   Authorization, x-sso-client");
		}

		if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
			try {
				chain.doFilter(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				TenantInfo info = HeadersUtil.getTenantInfo((HttpServletRequest) request);
				TenantContext.setContext(info);
				if (isParameterPresent(hReq))

				{
					HttpServletRequest newRequest = new RemoveHeader(hReq);
					chain.doFilter(newRequest, response);
				}
				else {
					chain.doFilter(request, response);
				}
			}
			catch (Exception e) {
				hRes.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
				return;
			}
			finally {
				//always remove tenant-id from thradlocal when request completed or on error
				TenantContext.clearContext();
			}
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException
	{
	}

	private boolean isParameterPresent(HttpServletRequest hReq)
	{
		boolean bResult = false;
		if (hReq.getParameterMap() != null) {
			bResult = hReq.getParameterMap().containsKey(PARAM_NAME);
		}
		return bResult;

	}
}
