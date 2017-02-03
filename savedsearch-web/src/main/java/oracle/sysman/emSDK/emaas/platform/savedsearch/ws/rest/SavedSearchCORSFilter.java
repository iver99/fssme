package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest;

import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil.InteractionLogDirection;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.ZDTContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.RequestContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.RequestContext.RequestType;
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
	private static class OAMHttpRequestWrapper extends HttpServletRequestWrapper
	{
		private String oam_remote_user = null;
		private String tenant = null;
		private Vector<String> headerNames = null;

		@SuppressWarnings("unchecked")
		public OAMHttpRequestWrapper(HttpServletRequest request)
		{
			super(request);

			oam_remote_user = request.getHeader(OAM_REMOTE_USER_HEADER);
			LOGGER.debug(OAM_REMOTE_USER_HEADER + "=" + oam_remote_user);
			//oamRemoteUser could be null in dev mode. In dev mode, there is no OHS configured
			if (oam_remote_user != null) {
				int pos = oam_remote_user.indexOf(".");
				if (pos > 0) {
					tenant = oam_remote_user.substring(0, pos);
				}
				//on server side, to avoid too much code change, we still use X-REMOTE-USER & X-USER-IDENTITY-DOMAIN-NAME
				//to pass parameters. But these two headers are overridden by OAM_REMOTE_USER to ensure security.
				//Below codes is to add header X-REMOTE-USER & X-USER-IDENTITY-DOMAIN-NAME if they are not specified to avoid
				//unwanted server side header checking exception.
				//in short, below codes is to allow headers X-REMOTE-USER & X-USER-IDENTITY-DOMAIN-NAME not specified in client side

				String xRemoteUser = request.getHeader(X_REMOTE_USER_HEADER);
				if (xRemoteUser == null) {
					headerNames = new Vector<String>();
					headerNames.add(X_REMOTE_USER_HEADER);
				}
				String xDomainName = request.getHeader(X_USER_IDENTITY_DOMAIN_NAME_HEADER);
				if (xDomainName == null) {
					if (headerNames == null) {
						headerNames = new Vector<String>();
					}
					headerNames.add(X_USER_IDENTITY_DOMAIN_NAME_HEADER);
				}
				if (headerNames != null) {
					Enumeration<String> em = request.getHeaderNames();
					while (em.hasMoreElements()) {
						headerNames.add(em.nextElement());
					}
				}
			}

		}

		@Override
		public String getHeader(String name)
		{
			if (X_REMOTE_USER_HEADER.equals(name) && oam_remote_user != null) {
				return oam_remote_user;
			}
			else if (X_USER_IDENTITY_DOMAIN_NAME_HEADER.equals(name) && tenant != null) {
				return tenant;
			}
			else {
				return super.getHeader(name);
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		public Enumeration<String> getHeaderNames()
		{
			if (headerNames != null) {
				return headerNames.elements();
			}
			else {
				return super.getHeaderNames();
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		public Enumeration<String> getHeaders(String name)
		{
			if (X_REMOTE_USER_HEADER.equals(name) && oam_remote_user != null) {
				Vector<String> v = new Vector<String>();
				v.add(oam_remote_user);
				return v.elements();
			}
			else if (X_USER_IDENTITY_DOMAIN_NAME_HEADER.equals(name) && tenant != null) {
				Vector<String> v = new Vector<String>();
				v.add(tenant);
				return v.elements();
			}
			else {
				return super.getHeaders(name);
			}
		}
	}

	private static final String EMCS_GLOBAL_INTER_SERVICE_APPID = "EMCS_GLOBAL_INTER_SERVICE_APPID";
	private static final String OAM_REMOTE_USER_HEADER = "OAM_REMOTE_USER";
	private static final String X_REMOTE_USER_HEADER = "X-REMOTE-USER";

	private static final String X_USER_IDENTITY_DOMAIN_NAME_HEADER = "X-USER-IDENTITY-DOMAIN-NAME";

	private static final String PARAM_NAME = "updateLastAccessTime";
	private static final Logger LOGGER = LogManager.getLogger(SavedSearchCORSFilter.class);

	@Override
	public void destroy()
	{
		// Do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{
		HttpServletResponse hRes = (HttpServletResponse) response;
		HttpServletRequest hReq = (HttpServletRequest) request;
		HttpServletRequest oamRequest = new OAMHttpRequestWrapper(hReq);
		
		ZDTContext.clear();
		String requestIdHeader = hReq.getHeader("X-ORCL-OMC-APIGW-REQGUID");
		String requestTimeHeader = hReq.getHeader("X-ORCL-OMC-APIGW-REQTIME");
		if (requestIdHeader != null) {
			LOGGER.debug("X-ORCL-OMC-APIGW-REQGUID : " + requestIdHeader);
			UUID uuid = UUID.fromString(requestIdHeader);
			ZDTContext.setRequestId(uuid);
		}
		if (requestTimeHeader != null) {
			LOGGER.debug("X-ORCL-OMC-APIGW-REQTIME : " + requestTimeHeader);
			Long time = Long.valueOf(requestTimeHeader);
			ZDTContext.setRequestTime(time);
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
				chain.doFilter(oamRequest, response);
			}
			catch (Exception e) {
				LOGGER.error(e.getLocalizedMessage());
			}
		}
		else {
			try {
				String oamRemoteUserHeader = hReq.getHeader(OAM_REMOTE_USER_HEADER);
				String tenantIdHeader = hReq.getHeader(X_USER_IDENTITY_DOMAIN_NAME_HEADER);
				String xRemoteUserHeader = hReq.getHeader(X_REMOTE_USER_HEADER);
				// Presence of OAM_REMOTE_USER header indicates an external request
				if (oamRemoteUserHeader != null) {
					RequestContext.setContext(RequestType.EXTERNAL);
				}
				// If header not present do an additional check for user principal
				else {
					Principal p = hReq.getUserPrincipal();
					if (p == null || p.getName() == null
							|| !p.getName().toLowerCase().endsWith(EMCS_GLOBAL_INTER_SERVICE_APPID.toLowerCase())) {
						// no principal, erroneous request
						RequestContext.setContext(RequestType.ERRONEOUS);
						LOGGER.warn("Authorization failed: request has no principal with userIdentity " + tenantIdHeader
								+ ", remoteUser " + xRemoteUserHeader);
						hRes.sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization failed: No principal.");
						return;
					}
					// internal request
					else {
						if (tenantIdHeader != null && xRemoteUserHeader != null) {
							RequestContext.setContext(RequestType.INTERNAL_TENANT_USER);
						}
						else {
							RequestContext.setContext(RequestType.INTERNAL_TENANT);
						}
					}
				}
				TenantInfo info = HeadersUtil.getTenantInfo((HttpServletRequest) request);
				TenantContext.setContext(info);
				LogUtil.setInteractionLogThreadContext(info.gettenantName(), ((HttpServletRequest) request).getHeader("referer"),
						InteractionLogDirection.IN);
				if (isParameterPresent(hReq)) {
					HttpServletRequest newRequest = new RemoveHeader(hReq);
					oamRequest = new OAMHttpRequestWrapper(newRequest);
					chain.doFilter(oamRequest, response);
				}
				else {
					chain.doFilter(oamRequest, response);
				}
			}
			catch (Exception e) {
				hRes.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
				return;
			}
			finally {
				//always remove tenant-id from thradlocal when request completed or on error
				TenantContext.clearContext();
				RequestContext.clearContext();
			}
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException
	{
		// Do nothing because of X and Y.
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

/*Enumeration headerNames = hReq.getHeaderNames();
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
}*/