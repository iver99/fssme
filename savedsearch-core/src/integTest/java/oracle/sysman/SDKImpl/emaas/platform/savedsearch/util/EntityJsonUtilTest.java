package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;

import org.codehaus.jettison.json.JSONException;
import org.testng.annotations.Test;

/**
 * Created by xiadai on 2016/8/23.
 */
@Test(groups = { "s2" })
public class EntityJsonUtilTest
{
	@Mocked
	URI baseUri;

	@Mocked
	Throwable throwable;

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
	public void getCategoryJsonObjJSONException(@Mocked JSONUtil jsonUtil) throws EMAnalyticsFwkException, JSONException
	{
		new Expectations() {
			{
				JSONUtil.objectToJSONObject((Search) any, (String[]) any);
				result = new JSONException(throwable);
			}
		};
		EntityJsonUtil.getFullCategoryJsonObj(baseUri, new CategoryImpl());
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
	public void getFolderJsonObjJSONException(@Mocked JSONUtil jsonUtil) throws EMAnalyticsFwkException, JSONException
	{
		new Expectations() {
			{
				JSONUtil.objectToJSONObject((Search) any, (String[]) any);
				result = new JSONException(throwable);
			}
		};
		EntityJsonUtil.getFullFolderJsonObj(baseUri, new FolderImpl());
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
	public void getSearchJsonObjJSONException(@Mocked JSONUtil jsonUtil) throws EMAnalyticsFwkException, JSONException
	{
		new Expectations() {
			{
				JSONUtil.objectToJSONObject((Search) any, (String[]) any);
				result = new JSONException(throwable);
			}
		};
		EntityJsonUtil.getFullSearchJsonObj(baseUri, new SearchImpl(), new String[0]);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
	public void getTargetCardJsonObjJSONException(@Mocked JSONUtil jsonUtil) throws EMAnalyticsFwkException, JSONException
	{
		new Expectations() {
			{
				JSONUtil.objectToJSONObject((Search) any, (String[]) any);
				result = new JSONException(throwable);
			}
		};
		EntityJsonUtil.getTargetCardJsonObj(baseUri, new SearchImpl());
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
	public void getWidgetGroupJsonObjJSONException(@Mocked JSONUtil jsonUtil, @Mocked final Category category)
			throws EMAnalyticsFwkException, JSONException
	{
		new Expectations() {
			{
				category.getId();
				result = new JSONException(throwable);
			}
		};
		EntityJsonUtil.getWidgetGroupJsonObj(baseUri, category);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
	public void getWidgetJsonObjJSONException(@Mocked JSONUtil jsonUtil, @Mocked final Search search)
			throws EMAnalyticsFwkException, JSONException
	{
		new Expectations() {
			{
				search.getId();
				result = new JSONException(throwable);
			}
		};
		EntityJsonUtil.getWidgetJsonObj(search, new CategoryImpl(), "");
	}

	@Test
	public void testGetErrorJsonObject() throws EMAnalyticsFwkException
	{
		EntityJsonUtil.getErrorJsonObject("1", "err_message", 1000L);
	}

	@Test
	public void testGetFullCategoryJsonObj() throws EMAnalyticsFwkException
	{
		CategoryImpl category = new CategoryImpl();
		Date date = new Date();
		category.setCreatedOn(date);
		category.setDefaultFolderId(BigInteger.ONE);
		category.setDescription("des");
		category.setName("name");
		category.setOwner("Oracle");
		category.setId(BigInteger.ONE);
		List<Parameter> parameters = new ArrayList<>();
		category.setParameters(parameters);
		category.setProviderAssetRoot("pro_assetroot");
		category.setProviderDiscovery("pro_discovery");
		category.setProviderName("pro_name");
		category.setProviderVersion("pro_version");
		EntityJsonUtil.getFullCategoryJsonObj(baseUri, category);
	}

	@Test
	public void testGetFullFolderJsonObj() throws EMAnalyticsFwkException
	{
		FolderImpl folder = new FolderImpl();
		Date date = new Date();
		folder.setCreationDate(date);
		folder.setDescription("des");
		folder.setId(BigInteger.ONE);
		folder.setLastModifiedBy("Oracle");
		folder.setLastModificationDate(date);
		folder.setOwner("Oracle");
		folder.setParentId(BigInteger.ONE);
		folder.setSystemFolder(false);
		folder.setUiHidden(false);
		EntityJsonUtil.getFullFolderJsonObj(baseUri, folder);
	}

	@Test
	public void testGetFullSearchJsonObj() throws EMAnalyticsFwkException
	{
		SearchImpl search = new SearchImpl();
		search.setIsWidget(false);
		search.setMetadata("eata_data");
		List<SearchParameter> searchParameterList = new ArrayList<>();
		search.setParameters(searchParameterList);
		search.setQueryStr("query_str");
		search.setLocked(false);
		search.setUiHidden(false);
		search.setFolderId(BigInteger.ONE);
		search.setCategoryId(BigInteger.ONE);
		search.setId(BigInteger.ONE);
		EntityJsonUtil.getFullSearchJsonObj(baseUri, search);
	}

	@Test
	public void testGetFullSearchJsonObj1() throws EMAnalyticsFwkException, JSONException
	{
		SearchImpl search = new SearchImpl();
		search.setIsWidget(false);
		search.setMetadata("eata_data");
		List<SearchParameter> searchParameterList = new ArrayList<>();
		search.setParameters(searchParameterList);
		search.setQueryStr("query_str");
		search.setLocked(false);
		search.setUiHidden(false);
		search.setFolderId(BigInteger.ONE);
		search.setCategoryId(BigInteger.ONE);
		search.setId(BigInteger.ONE);
		String[] folderPathArray = { "path1", "path2" };
		EntityJsonUtil.getFullSearchJsonObj(baseUri, search, folderPathArray, true);
		EntityJsonUtil.getFullSearchJsonObj(baseUri, search, folderPathArray, false);

	}

	@Test
	public void testGetSimpleCategoryJsonObj() throws EMAnalyticsFwkException, JSONException
	{
		CategoryImpl category = new CategoryImpl();
		Date date = new Date();
		category.setCreatedOn(date);
		category.setDefaultFolderId(BigInteger.ONE);
		category.setDescription("des");
		category.setName("name");
		category.setOwner("Oracle");
		category.setId(BigInteger.ONE);
		List<Parameter> parameters = new ArrayList<>();
		category.setParameters(parameters);
		category.setProviderAssetRoot("pro_assetroot");
		category.setProviderDiscovery("pro_discovery");
		category.setProviderName("pro_name");
		category.setProviderVersion("pro_version");
		EntityJsonUtil.getSimpleCategoryJsonObj(baseUri, category);
	}

	@Test
	public void testGetSimpleFolderJsonObj() throws EMAnalyticsFwkException, JSONException
	{
		FolderImpl folder = new FolderImpl();
		Date date = new Date();
		folder.setCreationDate(date);
		folder.setDescription("des");
		folder.setId(BigInteger.ONE);
		folder.setLastModifiedBy("Oracle");
		folder.setLastModificationDate(date);
		folder.setOwner("Oracle");
		folder.setParentId(BigInteger.ONE);
		folder.setSystemFolder(false);
		folder.setUiHidden(false);
		EntityJsonUtil.getSimpleFolderJsonObj(baseUri, folder);

	}

	@Test
	public void testGetSimpleSearchJsonObj() throws EMAnalyticsFwkException
	{
		SearchImpl search = new SearchImpl();
		search.setIsWidget(false);
		search.setMetadata("eata_data");
		List<SearchParameter> searchParameterList = new ArrayList<>();
		search.setParameters(searchParameterList);
		search.setQueryStr("query_str");
		search.setLocked(false);
		search.setUiHidden(false);
		search.setFolderId(BigInteger.ONE);
		search.setCategoryId(BigInteger.ONE);
		search.setId(BigInteger.ONE);
		EntityJsonUtil.getSimpleSearchJsonObj(baseUri, search);
	}

	@Test
	public void testGetSimpleSearchJsonObj1() throws EMAnalyticsFwkException
	{
		SearchImpl search = new SearchImpl();
		search.setIsWidget(false);
		search.setMetadata("eata_data");
		List<SearchParameter> searchParameterList = new ArrayList<>();
		search.setParameters(searchParameterList);
		search.setQueryStr("query_str");
		search.setLocked(false);
		search.setUiHidden(false);
		search.setFolderId(BigInteger.ONE);
		search.setCategoryId(BigInteger.ONE);
		search.setId(BigInteger.ONE);
		EntityJsonUtil.getSimpleSearchJsonObj(baseUri, search, true);
		EntityJsonUtil.getSimpleSearchJsonObj(baseUri, search, false);

	}

	@Test
	public void testGetTargetCardJsonObj() throws EMAnalyticsFwkException
	{
		SearchImpl search = new SearchImpl();
		search.setIsWidget(false);
		search.setMetadata("eata_data");
		List<SearchParameter> searchParameterList = new ArrayList<>();
		search.setParameters(searchParameterList);
		search.setQueryStr("query_str");
		search.setLocked(false);
		search.setUiHidden(false);
		search.setFolderId(BigInteger.ONE);
		search.setCategoryId(BigInteger.ONE);
		search.setId(BigInteger.ONE);
		EntityJsonUtil.getTargetCardJsonObj(baseUri, search);
	}

	@Test
	public void testGetWidgetGroupJsonObj() throws EMAnalyticsFwkException
	{
		CategoryImpl category = new CategoryImpl();
		Date date = new Date();
		category.setCreatedOn(date);
		category.setDefaultFolderId(BigInteger.ONE);
		category.setDescription("des");
		category.setName("name");
		category.setOwner("Oracle");
		category.setId(BigInteger.ONE);
		List<Parameter> parameters = new ArrayList<>();
		category.setParameters(parameters);
		category.setProviderAssetRoot("pro_assetroot");
		category.setProviderDiscovery("pro_discovery");
		category.setProviderName("pro_name");
		category.setProviderVersion("pro_version");
		EntityJsonUtil.getWidgetGroupJsonObj(baseUri, category);
	}

	@Test
	public void testGetWidgetJsonObj() throws EMAnalyticsFwkException
	{
		final List<SearchParameter> params = new ArrayList<>();
		for (int i = 0; i <= 12; i++) {
			params.add(new SearchParameter());
		}
		params.get(0).setName("WIDGET_SOURCE");
		params.get(0).setType(ParameterType.STRING);
		params.get(1).setName("WIDGET_GROUP_NAME");
		params.get(1).setType(ParameterType.STRING);
		params.get(2).setName("WIDGET_SCREENSHOT_HREF");
		params.get(2).setType(ParameterType.STRING);
		params.get(3).setName("WIDGET_ICON");
		params.get(3).setType(ParameterType.STRING);
		params.get(4).setName("WIDGET_KOC_NAME");
		params.get(4).setType(ParameterType.STRING);
		params.get(5).setName("WIDGET_VIEWMODEL");
		params.get(5).setType(ParameterType.STRING);
		params.get(6).setName("WIDGET_TEMPLATE");
		params.get(6).setType(ParameterType.STRING);
		params.get(7).setName("WIDGET_SUPPORT_TIME_CONTROL");
		params.get(7).setType(ParameterType.STRING);
		params.get(8).setName("WIDGET_LINKED_DASHBOARD");
		params.get(8).setType(ParameterType.STRING);
		params.get(8).setValue("1");
		params.get(9).setName("WIDGET_DEFAULT_WIDTH");
		params.get(9).setValue("3");
		params.get(9).setType(ParameterType.STRING);
		params.get(10).setName("WIDGET_DEFAULT_HEIGHT");
		params.get(10).setValue("3");
		params.get(10).setType(ParameterType.STRING);
		params.get(11).setName("DASHBOARD_INELIGIBLE");
		params.get(11).setValue("1");
		params.get(11).setType(ParameterType.STRING);
		SearchImpl search = new SearchImpl();
		search.setParameters(params);
		CategoryImpl category = new CategoryImpl();
		category.setProviderName("provider_name");
		category.setProviderVersion("provider_version");
		category.setProviderAssetRoot("assetroot");
		EntityJsonUtil.getWidgetJsonObj(search, category, "url");
	}

	@Test
	public void testGetWidgetJsonObjLessThanOne() throws EMAnalyticsFwkException
	{
		final List<SearchParameter> params = new ArrayList<>();
		for (int i = 0; i <= 12; i++) {
			params.add(new SearchParameter());
		}
		params.get(0).setName("WIDGET_SOURCE");
		params.get(0).setType(ParameterType.STRING);
		params.get(1).setName("WIDGET_GROUP_NAME");
		params.get(1).setType(ParameterType.STRING);
		params.get(2).setName("WIDGET_SCREENSHOT_HREF");
		params.get(2).setType(ParameterType.STRING);
		params.get(3).setName("WIDGET_ICON");
		params.get(3).setType(ParameterType.STRING);
		params.get(4).setName("WIDGET_KOC_NAME");
		params.get(4).setType(ParameterType.STRING);
		params.get(5).setName("WIDGET_VIEWMODEL");
		params.get(5).setType(ParameterType.STRING);
		params.get(6).setName("WIDGET_TEMPLATE");
		params.get(6).setType(ParameterType.STRING);
		params.get(7).setName("WIDGET_SUPPORT_TIME_CONTROL");
		params.get(7).setType(ParameterType.STRING);
		params.get(8).setName("WIDGET_LINKED_DASHBOARD");
		params.get(8).setType(ParameterType.STRING);
		params.get(8).setValue("1");
		params.get(9).setName("WIDGET_DEFAULT_WIDTH");
		params.get(9).setValue("0");
		params.get(9).setType(ParameterType.STRING);
		params.get(10).setName("WIDGET_DEFAULT_HEIGHT");
		params.get(10).setValue("0");
		params.get(10).setType(ParameterType.STRING);
		params.get(11).setName("DASHBOARD_INELIGIBLE");
		params.get(11).setValue("1");
		params.get(11).setType(ParameterType.STRING);
		SearchImpl search = new SearchImpl();
		search.setParameters(params);
		CategoryImpl category = new CategoryImpl();
		category.setProviderName("provider_name");
		category.setProviderVersion("provider_version");
		category.setProviderAssetRoot("assetroot");
		EntityJsonUtil.getWidgetJsonObj(search, category, "url");
	}

	@Test
	public void testGetWidgetJsonObjNumberException() throws EMAnalyticsFwkException
	{
		final List<SearchParameter> params = new ArrayList<>();
		for (int i = 0; i <= 12; i++) {
			params.add(new SearchParameter());
		}
		params.get(0).setName("WIDGET_SOURCE");
		params.get(0).setType(ParameterType.STRING);
		params.get(1).setName("WIDGET_GROUP_NAME");
		params.get(1).setType(ParameterType.STRING);
		params.get(2).setName("WIDGET_SCREENSHOT_HREF");
		params.get(2).setType(ParameterType.STRING);
		params.get(3).setName("WIDGET_ICON");
		params.get(3).setType(ParameterType.STRING);
		params.get(4).setName("WIDGET_KOC_NAME");
		params.get(4).setType(ParameterType.STRING);
		params.get(5).setName("WIDGET_VIEWMODEL");
		params.get(5).setType(ParameterType.STRING);
		params.get(6).setName("WIDGET_TEMPLATE");
		params.get(6).setType(ParameterType.STRING);
		params.get(7).setName("WIDGET_SUPPORT_TIME_CONTROL");
		params.get(7).setType(ParameterType.STRING);
		params.get(8).setName("WIDGET_LINKED_DASHBOARD");
		params.get(8).setType(ParameterType.STRING);
		params.get(8).setValue("1");
		params.get(9).setName("WIDGET_DEFAULT_WIDTH");
		params.get(9).setValue("a");
		params.get(9).setType(ParameterType.STRING);
		params.get(10).setName("WIDGET_DEFAULT_HEIGHT");
		params.get(10).setValue("a");
		params.get(10).setType(ParameterType.STRING);
		params.get(11).setName("DASHBOARD_INELIGIBLE");
		params.get(11).setValue("1");
		params.get(11).setType(ParameterType.STRING);
		SearchImpl search = new SearchImpl();
		search.setParameters(params);
		CategoryImpl category = new CategoryImpl();
		category.setProviderName("provider_name");
		category.setProviderVersion("provider_version");
		category.setProviderAssetRoot("assetroot");
		EntityJsonUtil.getWidgetJsonObj(search, category, "url");
	}

	@Test
	public void testGetWidgetJsonObjOverMax() throws EMAnalyticsFwkException
	{
		final List<SearchParameter> params = new ArrayList<>();
		for (int i = 0; i <= 12; i++) {
			params.add(new SearchParameter());
		}
		params.get(0).setName("WIDGET_SOURCE");
		params.get(0).setType(ParameterType.STRING);
		params.get(1).setName("WIDGET_GROUP_NAME");
		params.get(1).setType(ParameterType.STRING);
		params.get(2).setName("WIDGET_SCREENSHOT_HREF");
		params.get(2).setType(ParameterType.STRING);
		params.get(3).setName("WIDGET_ICON");
		params.get(3).setType(ParameterType.STRING);
		params.get(4).setName("WIDGET_KOC_NAME");
		params.get(4).setType(ParameterType.STRING);
		params.get(5).setName("WIDGET_VIEWMODEL");
		params.get(5).setType(ParameterType.STRING);
		params.get(6).setName("WIDGET_TEMPLATE");
		params.get(6).setType(ParameterType.STRING);
		params.get(7).setName("WIDGET_SUPPORT_TIME_CONTROL");
		params.get(7).setType(ParameterType.STRING);
		params.get(8).setName("WIDGET_LINKED_DASHBOARD");
		params.get(8).setType(ParameterType.STRING);
		params.get(8).setValue("1");
		params.get(9).setName("WIDGET_DEFAULT_WIDTH");
		params.get(9).setValue("10");
		params.get(9).setType(ParameterType.STRING);
		params.get(10).setName("WIDGET_DEFAULT_HEIGHT");
		params.get(10).setValue("10");
		params.get(10).setType(ParameterType.STRING);
		params.get(11).setName("DASHBOARD_INELIGIBLE");
		params.get(11).setValue("1");
		params.get(11).setType(ParameterType.STRING);
		SearchImpl search = new SearchImpl();
		search.setParameters(params);
		CategoryImpl category = new CategoryImpl();
		category.setProviderName("provider_name");
		category.setProviderVersion("provider_version");
		category.setProviderAssetRoot("assetroot");
		EntityJsonUtil.getWidgetJsonObj(search, category, "url");
	}
}
