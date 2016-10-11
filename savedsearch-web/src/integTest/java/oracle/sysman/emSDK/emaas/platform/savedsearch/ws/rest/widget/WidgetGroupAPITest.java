package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.widget;

import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;

/**
 * Created by xidai on 2/26/2016.
 */
@Test(groups = { "s1" })
public class WidgetGroupAPITest
{
	private static final BigInteger TEST_ID_10 = BigInteger.TEN;
	private final WidgetGroupAPI widgetAPI = new WidgetGroupAPI();

	@BeforeMethod
	public void setUp() throws Exception
	{
		UriInfo uri = new UriInfo() {
			@Override
			public URI getAbsolutePath()
			{
				return null;
			}

			@Override
			public UriBuilder getAbsolutePathBuilder()
			{
				return null;
			}

			@Override
			public URI getBaseUri()
			{
				return null;
			}

			@Override
			public UriBuilder getBaseUriBuilder()
			{
				return null;
			}

			@Override
			public List<Object> getMatchedResources()
			{
				return null;
			}

			@Override
			public List<String> getMatchedURIs()
			{
				return null;
			}

			@Override
			public List<String> getMatchedURIs(boolean b)
			{
				return null;
			}

			@Override
			public String getPath()
			{
				return null;
			}

			@Override
			public String getPath(boolean b)
			{
				return null;
			}

			@Override
			public MultivaluedMap<String, String> getPathParameters()
			{
				return null;
			}

			@Override
			public MultivaluedMap<String, String> getPathParameters(boolean b)
			{
				return null;
			}

			@Override
			public List<PathSegment> getPathSegments()
			{
				return null;
			}

			@Override
			public List<PathSegment> getPathSegments(boolean b)
			{
				return null;
			}

			@Override
			public MultivaluedMap<String, String> getQueryParameters()
			{
				return null;
			}

			@Override
			public MultivaluedMap<String, String> getQueryParameters(boolean b)
			{
				return null;
			}

			@Override
			public URI getRequestUri()
			{

				URI uril = null;
				try {
					uril = new URI("");
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				return uril;
			}

			@Override
			public UriBuilder getRequestUriBuilder()
			{
				return null;
			}
		};
		Deencapsulation.setField(widgetAPI, "uri", uri);
	}

	@Test
	public void testGetAllWidgetGroups(@Mocked final TenantSubscriptionUtil anyTSU) throws Exception
	{
		String userTenant = "OAM_REMOTE_USER.userName";
		String widgetGroupId = "10";
		boolean includeDashboardIneligible = true;
		final List<Category> list = new ArrayList<Category>();
		for (int i = 0; i < 3; i++) {
			CategoryImpl category = new CategoryImpl();
			category.setId(TEST_ID_10);
			list.add(category);
		}
		final List<SearchParameter> parameters = new ArrayList<SearchParameter>();
		SearchParameter searchParameter = new SearchParameter();
		searchParameter.setName("DASHBOARD_INELIGIBLE");
		searchParameter.setValue("1");
		for (int i = 0; i < 3; i++) {
			parameters.add(searchParameter);
		}
		final List<Search> searches = new ArrayList<Search>();
		for (int i = 0; i < 3; i++) {
			SearchImpl search = new SearchImpl();
			search.setParameters(parameters);
			searches.add(search);
		}

		new Expectations() {
			{
				TenantSubscriptionUtil.getTenantSubscribedCategories(anyString, anyBoolean);
				result = list;
			}
		};

		new MockUp<CategoryManagerImpl>() {
			@Mock
			public List<Category> getAllCategories() throws EMAnalyticsFwkException
			{
				return list;
			}
		};
		new MockUp<SearchManagerImpl>() {
			@Mock
			public List<Search> getWidgetListByCategoryId(BigInteger categoryId) throws EMAnalyticsFwkException
			{
				return searches;
			}
		};
		new MockUp<URI>() {
			@Mock
			public String getQuery()
			{
				return "includeDashboardIneligible=true";
			}
		};
		widgetAPI.getAllWidgetGroups(userTenant, includeDashboardIneligible);

	}

	@Test
	public void testGetAllWidgetGroups2nd() throws Exception
	{
		String userTenant = "OAM_REMOTE_USER.userName";
		String widgetGroupId = "10";
		boolean includeDashboardIneligible = true;
		final List<Category> list = new ArrayList<Category>();
		for (int i = 0; i < 3; i++) {
			CategoryImpl category = new CategoryImpl();
			category.setId(TEST_ID_10);
			list.add(category);
		}
		final List<SearchParameter> parameters = new ArrayList<SearchParameter>();
		SearchParameter searchParameter = new SearchParameter();
		searchParameter.setName("DASHBOARD_INELIGIBLE");
		searchParameter.setValue("1");
		for (int i = 0; i < 3; i++) {
			parameters.add(searchParameter);
		}
		final List<Search> searches = new ArrayList<Search>();
		for (int i = 0; i < 3; i++) {
			SearchImpl search = new SearchImpl();
			search.setParameters(parameters);
			searches.add(search);
		}

		new MockUp<CategoryManagerImpl>() {
			@Mock
			public List<Category> getAllCategories() throws EMAnalyticsFwkException
			{
				return list;
			}
		};
		new MockUp<SearchManagerImpl>() {
			@Mock
			public List<Search> getWidgetListByCategoryId(BigInteger categoryId) throws EMAnalyticsFwkException
			{
				return searches;
			}
		};
		new MockUp<URI>() {
			@Mock
			public String getQuery()
			{
				return "includeDashboardIneligible=";
			}
		};
		widgetAPI.getAllWidgetGroups(userTenant, includeDashboardIneligible);

	}

	@Test
	public void testGetAllWidgetGroups3th(@Mocked final TenantSubscriptionUtil anyTSU) throws Exception
	{
		String userTenant = "OAM_REMOTE_USER.userName";
		String widgetGroupId = "10";
		boolean includeDashboardIneligible = true;
		final List<Category> list = new ArrayList<Category>();
		for (int i = 0; i < 3; i++) {
			CategoryImpl category = new CategoryImpl();
			category.setId(TEST_ID_10);
			list.add(category);
		}
		final List<SearchParameter> parameters = new ArrayList<SearchParameter>();
		SearchParameter searchParameter = new SearchParameter();
		searchParameter.setName("DASHBOARD_INELIGIBLE");
		searchParameter.setValue("1");
		for (int i = 0; i < 3; i++) {
			parameters.add(searchParameter);
		}
		final List<Search> searches = new ArrayList<Search>();
		for (int i = 0; i < 3; i++) {
			SearchImpl search = new SearchImpl();
			search.setParameters(parameters);
			searches.add(search);
		}

		new Expectations() {
			{
				TenantSubscriptionUtil.getTenantSubscribedCategories(anyString, anyBoolean);
				result = new EMAnalyticsFwkException(new Throwable());
			}
		};

		new MockUp<CategoryManagerImpl>() {
			@Mock
			public List<Category> getAllCategories() throws EMAnalyticsFwkException
			{
				return list;
			}
		};
		new MockUp<SearchManagerImpl>() {
			@Mock
			public List<Search> getWidgetListByCategoryId(BigInteger categoryId) throws EMAnalyticsFwkException
			{
				return searches;
			}
		};
		new MockUp<URI>() {
			@Mock
			public String getQuery()
			{
				return "includeDashboardIneligible=true";
			}
		};
		widgetAPI.getAllWidgetGroups(userTenant, includeDashboardIneligible);

	}

	@Test
	public void testGetAllWidgetGroups4th(@Mocked final TenantSubscriptionUtil anyTSU) throws Exception
	{
		String userTenant = "OAM_REMOTE_USER.userName";
		String widgetGroupId = "10";
		boolean includeDashboardIneligible = true;
		final List<Category> list = new ArrayList<Category>();
		for (int i = 0; i < 3; i++) {
			CategoryImpl category = new CategoryImpl();
			category.setId(TEST_ID_10);
			list.add(category);
		}
		final List<SearchParameter> parameters = new ArrayList<SearchParameter>();
		SearchParameter searchParameter = new SearchParameter();
		searchParameter.setName("DASHBOARD_INELIGIBLE");
		searchParameter.setValue("1");
		for (int i = 0; i < 3; i++) {
			parameters.add(searchParameter);
		}
		final List<Search> searches = new ArrayList<Search>();
		for (int i = 0; i < 3; i++) {
			SearchImpl search = new SearchImpl();
			search.setParameters(parameters);
			searches.add(search);
		}

		new Expectations() {
			{
				TenantSubscriptionUtil.getTenantSubscribedCategories(anyString, anyBoolean);
				result = new Exception();
			}
		};

		new MockUp<CategoryManagerImpl>() {
			@Mock
			public List<Category> getAllCategories() throws EMAnalyticsFwkException
			{
				return list;
			}
		};
		new MockUp<SearchManagerImpl>() {
			@Mock
			public List<Search> getWidgetListByCategoryId(BigInteger categoryId) throws EMAnalyticsFwkException
			{
				return searches;
			}
		};
		new MockUp<URI>() {
			@Mock
			public String getQuery()
			{
				return "includeDashboardIneligible=true";
			}
		};
		widgetAPI.getAllWidgetGroups(userTenant, includeDashboardIneligible);

	}
}
