package oracle.sysman.emaas.savedsearch;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchParamTest extends BaseTest
{

	private static Integer folderId;
	private static Integer categoryId;
	private static Search searchObj;

	@AfterClass
	public static void testEndSearchparam() throws EMAnalyticsFwkException {
		Search tmpSearch = null;
		try {
			tmpSearch = SearchManagerImpl.getInstance().getSearch(searchObj.getId());
			List<SearchParameter> paraList = tmpSearch.getParameters();
			AssertJUnit.assertTrue(paraList.size() == 4);
			SearchParameter sp1 = new SearchParameter();
			sp1.setName("Height");
			sp1.setValue("Two");
			sp1.setAttributes("Length");
			sp1.setType(ParameterType.STRING);

			if (paraList.size() > 0) {
				paraList = new ArrayList<SearchParameter>();
			}
			paraList.add(sp1);
			tmpSearch.setParameters(paraList);
			tmpSearch = SearchManagerImpl.getInstance().editSearch(tmpSearch);
			tmpSearch = SearchManagerImpl.getInstance().getSearch(searchObj.getId());
			paraList = tmpSearch.getParameters();
			AssertJUnit.assertTrue(paraList.size() == 1);
		}
		finally {
			//now delete all the searchParams and search and folder

			AssertJUnit.assertTrue(tmpSearch != null);
			if (tmpSearch != null) {
				tmpSearch.setParameters(new ArrayList<SearchParameter>());
				tmpSearch = SearchManagerImpl.getInstance().editSearch(tmpSearch);
				//now delete the search
				SearchManagerImpl objSearch = SearchManagerImpl.getInstance();
				objSearch.deleteSearch(tmpSearch.getId(), true);
			}
			CategoryManagerImpl.getInstance().deleteCategory(categoryId, true);
			//now delete the folder
			FolderManagerImpl.getInstance().deleteFolder(folderId, true);
			TenantContext.clearContext();

		}
	}

	@BeforeClass
	public static void testInitSearchparam() throws EMAnalyticsFwkException {


			TenantContext.setContext(new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails()
					.get(QAToolUtil.TENANT_USER_NAME).toString()), TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails()
					.get(QAToolUtil.TENANT_NAME).toString())));

			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			Folder folder = new FolderImpl();
			folder.setName("SearchPramTest23UT");
			folder.setDescription("Test Parameter Description");
			folder.setUiHidden(false);
			folder = objFolder.saveFolder(folder);
			folderId = folder.getId();

			CategoryManager objCategory = CategoryManagerImpl.getInstance();
			Category cat = new CategoryImpl();
			cat.setName("CategoryTestOne");
			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
			cat.setOwner(currentUser);
			cat.setDefaultFolderId(folder.getParentId());
			cat.setProviderName("ProviderNameTest");
			cat.setProviderVersion("ProviderVersionTest");
			cat.setProviderDiscovery("ProviderDiscoveryTest");
			cat.setProviderAssetRoot("ProviderAssetRootTest");
			cat = objCategory.saveCategory(cat);
			categoryId = cat.getId();

			searchObj = new SearchImpl();
			searchObj.setName("Test Parameter1");
			searchObj.setDescription("analyze Parameter");
			searchObj.setFolderId(folderId);
			searchObj.setCategoryId(categoryId);
			searchObj.setLocked(false);
			searchObj.setUiHidden(false);
			SearchParameter sp1 = new SearchParameter();
			sp1.setName("ParamStringName");
			sp1.setValue("ParamStringValue");
			sp1.setAttributes("Regx");
			sp1.setType(ParameterType.STRING);

			SearchParameter sp2 = new SearchParameter();
			sp2.setName("ParamClobName");
			sp2.setValue("ParamClobValue");
			sp2.setAttributes("Image");
			sp2.setType(ParameterType.CLOB);

			ArrayList<SearchParameter> parameters = new ArrayList<SearchParameter>();
			parameters.add(sp1);
			parameters.add(sp2);

			searchObj.setParameters(parameters);

			searchObj = SearchManagerImpl.getInstance().saveSearch(searchObj);

			Search tmpSearch = SearchManagerImpl.getInstance().getSearch(searchObj.getId());
			List<SearchParameter> paraList = tmpSearch.getParameters();
			AssertJUnit.assertTrue(paraList.size() == 2);


	}

	 @Test 
	public void testSearchparamCRUD() throws EMAnalyticsFwkException {
			Search tmpSearch = SearchManagerImpl.getInstance().getSearch(searchObj.getId());
			List<SearchParameter> paraList = tmpSearch.getParameters();
			AssertJUnit.assertTrue(paraList.size() == 2);

			SearchParameter sp1 = new SearchParameter();
			sp1.setName("ParamName");
			sp1.setValue("ParamValue");
			sp1.setAttributes("Regular Expression");
			sp1.setType(ParameterType.STRING);

			SearchParameter sp2 = new SearchParameter();
			sp2.setName("ParamClob");
			//sp2.setValue("ParamClobTextValue");
			sp2.setAttributes("Text");
			sp2.setType(ParameterType.CLOB);

			tmpSearch.getParameters().add(sp2);
			tmpSearch.getParameters().add(sp1);
			SearchManagerImpl.getInstance().editSearch(tmpSearch);

			Search tmpEditSearch = SearchManagerImpl.getInstance().getSearch(searchObj.getId());
			List<SearchParameter> paraEditList = tmpSearch.getParameters();
			AssertJUnit.assertTrue(paraEditList.size() == 4);


	}
}
