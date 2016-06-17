package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.model.tenant.TenantIdProcessor;

import org.testng.annotations.Test;

/**
 * Created by xidai on 2/24/2016.
 */
@Test(groups = { "s2" })
public class HeadersUtilTest
{
	private final HeadersUtil headUtil = new HeadersUtil();

	@Test
	public void testGetTenantInfo(@Mocked final TenantIdProcessor anyTIP) throws Exception
	{
		ServletRequest request = new HttpServletRequest() {
			@Override
			public Object getAttribute(String s)
			{
				return null;
			}

			@Override
			public Enumeration getAttributeNames()
			{
				return null;
			}

			@Override
			public String getAuthType()
			{
				return null;
			}

			@Override
			public String getCharacterEncoding()
			{
				return null;
			}

			@Override
			public int getContentLength()
			{
				return 0;
			}

			@Override
			public String getContentType()
			{
				return null;
			}

			@Override
			public String getContextPath()
			{
				return null;
			}

			@Override
			public Cookie[] getCookies()
			{
				return new Cookie[0];
			}

			@Override
			public long getDateHeader(String s)
			{
				return 0;
			}

			@Override
			public String getHeader(String s)
			{
				if (s.equals("X-REMOTE-USER")) {
					s = "Origin.userName";
				}
				return s;
			}

			@Override
			public Enumeration getHeaderNames()
			{
				return null;
			}

			@Override
			public Enumeration getHeaders(String s)
			{
				return null;
			}

			@Override
			public ServletInputStream getInputStream() throws IOException
			{
				return null;
			}

			@Override
			public int getIntHeader(String s)
			{
				return 0;
			}

			@Override
			public String getLocalAddr()
			{
				return null;
			}

			@Override
			public Locale getLocale()
			{
				return null;
			}

			@Override
			public Enumeration getLocales()
			{
				return null;
			}

			@Override
			public String getLocalName()
			{
				return null;
			}

			@Override
			public int getLocalPort()
			{
				return 0;
			}

			@Override
			public String getMethod()
			{
				return "OPTIONS";
			}

			@Override
			public String getParameter(String s)
			{
				return null;
			}

			@Override
			public Map getParameterMap()
			{
				return null;
			}

			@Override
			public Enumeration getParameterNames()
			{
				return null;
			}

			@Override
			public String[] getParameterValues(String s)
			{
				return new String[0];
			}

			@Override
			public String getPathInfo()
			{
				return null;
			}

			@Override
			public String getPathTranslated()
			{
				return null;
			}

			@Override
			public String getProtocol()
			{
				return null;
			}

			@Override
			public String getQueryString()
			{
				return null;
			}

			@Override
			public BufferedReader getReader() throws IOException
			{
				return null;
			}

			@Override
			public String getRealPath(String s)
			{
				return null;
			}

			@Override
			public String getRemoteAddr()
			{
				return null;
			}

			@Override
			public String getRemoteHost()
			{
				return null;
			}

			@Override
			public int getRemotePort()
			{
				return 0;
			}

			@Override
			public String getRemoteUser()
			{
				return null;
			}

			@Override
			public RequestDispatcher getRequestDispatcher(String s)
			{
				return null;
			}

			@Override
			public String getRequestedSessionId()
			{
				return null;
			}

			@Override
			public String getRequestURI()
			{
				return null;
			}

			@Override
			public StringBuffer getRequestURL()
			{
				return null;
			}

			@Override
			public String getScheme()
			{
				return null;
			}

			@Override
			public String getServerName()
			{
				return null;
			}

			@Override
			public int getServerPort()
			{
				return 0;
			}

			@Override
			public String getServletPath()
			{
				return null;
			}

			@Override
			public HttpSession getSession()
			{
				return null;
			}

			@Override
			public HttpSession getSession(boolean b)
			{
				return null;
			}

			@Override
			public Principal getUserPrincipal()
			{
				return null;
			}

			@Override
			public boolean isRequestedSessionIdFromCookie()
			{
				return false;
			}

			@Override
			public boolean isRequestedSessionIdFromUrl()
			{
				return false;
			}

			@Override
			public boolean isRequestedSessionIdFromURL()
			{
				return false;
			}

			@Override
			public boolean isRequestedSessionIdValid()
			{
				return false;
			}

			@Override
			public boolean isSecure()
			{
				return false;
			}

			@Override
			public boolean isUserInRole(String s)
			{
				return false;
			}

			@Override
			public void removeAttribute(String s)
			{

			}

			@Override
			public void setAttribute(String s, Object o)
			{

			}

			@Override
			public void setCharacterEncoding(String s) throws UnsupportedEncodingException
			{

			}
		};
		HeadersUtil.getTenantInfo((HttpServletRequest) request);
	}

}