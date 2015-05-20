package oracle.sysman.emaas.savedsearch;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportCategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportSearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ObjectFactory;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ParameterDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.SearchParameterDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.QAToolUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ImportTest extends BaseTest
{

	private static final String TENANT_ID_OPC1 = TestUtils.TENANT_ID_OPC1;

	@Test
	public static void testImportCategorySet() throws Exception
	{
		try {
			CategoryManager catImpl = CategoryManager.getInstance();
			Category category = catImpl.createNewCategory();
			category.setName("ImportCategory");
			category.setDescription("CategoryTest");
			category.setProviderName("ProviderNameImportUT");
			category.setProviderVersion("ProviderVersionImportUT");
			category.setProviderDiscovery("ProviderDiscoveryImportUT");
			category.setProviderAssetRoot("ProviderAssetRootImportUT");
			//set the parameter for the category
			Parameter sp1 = new Parameter();
			sp1.setName("Param1");
			sp1.setValue("ParamValue1");

			Parameter sp2 = new Parameter();
			sp2.setName("Param2");
			sp2.setValue("ParamValue2");

			List<Parameter> list = new ArrayList<Parameter>();
			list.add(sp1);
			list.add(sp2);
			category.setParameters(list);

			Category category1 = catImpl.createNewCategory();
			category1.setName("ImportCategory1");
			category1.setDescription("CategoryTest");
			category1.setProviderName("ProviderNameImportUT1");
			category1.setProviderVersion("ProviderVersionImportUT1");
			category1.setProviderDiscovery("ProviderDiscoveryImportUT1");
			category1.setProviderAssetRoot("ProviderAssetRootImportUT1");

			List<ImportCategoryImpl> listCat = new ArrayList<ImportCategoryImpl>();

			listCat.add(ImportTest.createImportCategoryImpl(category));
			listCat.add(ImportTest.createImportCategoryImpl(category1));

			List<Category> listobj = ((CategoryManagerImpl) catImpl).saveMultipleCategories(listCat);
			AssertJUnit.assertTrue(listobj.size() == 2);

			for (Category obj : listobj) {
				AssertJUnit.assertNotNull("Imported category has NULL creation date", obj.getCreatedOn());
				catImpl.deleteCategory(obj.getId(), true);
			}
		}
		catch (Exception e) {
			AssertJUnit.fail(e.getLocalizedMessage());
		}

	}

	@Test
	public static void testImportFolderSet() throws Exception
	{
		try {

			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			FolderDetails folder = new FolderDetails();
			folder.setName("ImportFolderTest");
			folder.setDescription("ImportFolderDescription");
			folder.setUiHidden(false);

			FolderDetails folder1 = new FolderDetails();
			folder1.setName("ImportFolderTest1");
			folder1.setDescription("ImportFolderDescription1");
			folder1.setUiHidden(false);

			List<FolderDetails> list = new ArrayList<FolderDetails>();
			list.add(folder);
			list.add(folder1);
			List<FolderImpl> listobj = objFolder.saveMultipleFolders(list);
			AssertJUnit.assertTrue(listobj.size() == 2);

			for (Folder obj : listobj) {
				AssertJUnit.assertNotNull(obj.getLastModifiedOn());
				AssertJUnit.assertNotNull(obj.getCreatedOn());
				AssertJUnit.assertEquals(obj.getCreatedOn(), obj.getLastModifiedOn());

				objFolder.deleteFolder(obj.getId(), true);
			}

		}
		catch (Exception e) {
			AssertJUnit.fail(e.getLocalizedMessage());
		}
	}

	@Test
	public static void testImportSearchSet() throws Exception
	{
		Integer folderId = 0;
		Integer categoryId = 0;
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		CategoryManager catImpl = CategoryManager.getInstance();
		SearchManagerImpl obj = SearchManagerImpl.getInstance();
		List<Search> listobj = new ArrayList<Search>();
		try {

			System.out.println("Start to create folder for search importing");

			Folder folder = objFolder.createNewFolder();
			folder.setName("ImportFolderTest");
			folder.setDescription("testing purpose folder");
			folderId = objFolder.saveFolder(folder).getId();
			System.out.println("The folder ID is " + folderId);
			System.out.println("create folder for search importing finished");

			System.out.println("Start to create category for search importing");

			Category category = catImpl.createNewCategory();
			category.setName("ImportCategoryName");
			category.setDescription("CategoryTest");
			category.setProviderName("ProviderNameTest");
			category.setProviderVersion("ProviderVersionTest");
			category.setProviderDiscovery("ProviderDiscoveryTest");
			category.setProviderAssetRoot("ProviderAssetRootTest");
			//set the parameter for the category
			Parameter sp1 = new Parameter();
			sp1.setName("Param1");
			sp1.setValue("ParamValue1");

			Parameter sp2 = new Parameter();
			sp2.setName("Param2");
			sp2.setValue("ParamValue2");

			List<Parameter> catParams = new ArrayList<Parameter>();
			catParams.add(sp1);
			catParams.add(sp2);
			category.setParameters(catParams);
			category = catImpl.saveCategory(category);
			categoryId = category.getId();
			System.out.println("The category ID is " + categoryId);
			System.out.println("create category for search importing finished");

			//SearchManagerImpl objSearch = SearchManagerImpl.getInstance();
			System.out.println("Start to create the first search for search importing");

			ImportSearchImpl search = new ImportSearchImpl();
			search.setDescription("testing purpose");
			search.setName("Dummy Search");
			search.setIsWidget(false);
			ObjectFactory objFactory = new ObjectFactory();
			JAXBElement<Integer> catId = objFactory.createCategoryId(category.getId());
			System.out.println("The Category ID is " + catId.toString());
			search.setCategoryDet(catId);
			JAXBElement<Integer> fldId = objFactory.createFolderId(folderId);
			search.setFolderDet(fldId);
			System.out.println("The folder ID is " + fldId.toString());

			ImportSearchImpl.SearchParameters param = search.getSearchParameters();
			if (param == null) {
				param = new ImportSearchImpl.SearchParameters();
				search.setSearchParameters(param);
			}
			SearchParameterDetails tmpDetails = new SearchParameterDetails();
			tmpDetails.setName("Param1");
			tmpDetails.setType(ParameterType.STRING);
			tmpDetails.setValue("Value");
			System.out.println("Set the parameters of Search");
			param.getSearchParameter().add(tmpDetails);

			System.out.println("Start to create second search for search importing");
			ImportSearchImpl search1 = new ImportSearchImpl();
			search1.setDescription("testing import purpose");
			search1.setName("Import Dummy Search");
			catId = objFactory.createCategoryId(category.getId());
			search1.setCategoryDet(catId);
			fldId = objFactory.createFolderId(folderId);
			search1.setFolderDet(fldId);
			ImportSearchImpl.SearchParameters param1 = search1.getSearchParameters();
			if (param1 == null) {
				param1 = new ImportSearchImpl.SearchParameters();
				search1.setSearchParameters(param1);
			}
			SearchParameterDetails tmpDetails2 = new SearchParameterDetails();
			tmpDetails2.setName("Param2");
			tmpDetails2.setType(ParameterType.CLOB);
			//tmpDetails2.setValue("Value");
			param1.getSearchParameter().add(tmpDetails2);
			search1.getSearchParameters().getSearchParameter().add(tmpDetails2);

			List<ImportSearchImpl> list = new ArrayList<ImportSearchImpl>();
			list.add(search);
			list.add(search1);

			System.out.println("create search for search importing finished");

			System.out.println("Start to importing search");
			listobj = obj.saveMultipleSearch(list);
			System.out.println("Verify the importing");
			for (Search objSearch1 : listobj) {
				AssertJUnit.assertNotNull(objSearch1);
				AssertJUnit.assertNotNull(objSearch1.getCreatedOn());
				AssertJUnit.assertNotNull(objSearch1.getLastModifiedOn());
				AssertJUnit.assertNotNull(objSearch1.getLastAccessDate());
				AssertJUnit.assertEquals(objSearch1.getCreatedOn(), objSearch1.getLastModifiedOn());
				AssertJUnit.assertEquals(objSearch1.getCreatedOn(), objSearch1.getLastAccessDate());
				System.out.println("Verify data passed!");
				if (objSearch1.getName().equals("Dummy Search")) {
					System.out.println("Start verify search parameter--String");
					AssertJUnit.assertTrue(objSearch1.getParameters().get(0).getName().equals("Param1"));
					AssertJUnit.assertTrue(objSearch1.getParameters().get(0).getValue().equals("Value"));
					System.out.println("Parameter Type is " + objSearch1.getParameters().get(0).getType());
					AssertJUnit.assertTrue(objSearch1.getParameters().get(0).getType().toString().equals("STRING"));
					System.out.println("Verify search parameter passed");
				}
				if (objSearch1.getName().equals("Import Dummy Search")) {
					System.out.println("Start verify search parameter--clob");
					AssertJUnit.assertTrue(objSearch1.getParameters().get(0).getName().equals("Param2"));
					AssertJUnit.assertTrue(objSearch1.getParameters().get(0).getValue() == null);
					AssertJUnit.assertTrue(objSearch1.getParameters().get(0).getType().toString().equals("CLOB"));
					System.out.println("Verify search parameter passed");
				}
			}
			System.out.println("search importing finished");

			//catImpl.deleteCategory(category.getId(), true);

			//objFolder.deleteFolder(folderId, true);
		}
		catch (Exception e) {

			AssertJUnit.fail(e.getMessage());
		}
		finally {

			for (Search objSearch : listobj) {
				if (objSearch.getId() != null) {
					System.out.println("Delete the search");
					obj.deleteSearch(objSearch.getId(), true);
					System.out.println("Delete the search passed");
				}
			}
			if (categoryId != 0) {
				System.out.println("delete category ");
				catImpl.deleteCategory(categoryId, true);
			}
			if (folderId != 0) {
				System.out.println("delete folder");
				objFolder.deleteFolder(folderId, true);
			}

		}
	}

	private static ImportCategoryImpl createImportCategoryImpl(Category cat)
	{
		if (cat == null) {
			return null;
		}
		ImportCategoryImpl impCat = new ImportCategoryImpl();
		impCat.setId(cat.getId());
		impCat.setName(cat.getName());
		impCat.setDescription(cat.getDescription());
		impCat.setProviderName(cat.getProviderName());
		impCat.setProviderVersion(cat.getProviderVersion());
		impCat.setProviderDiscovery(cat.getProviderDiscovery());
		impCat.setProviderAssetRoot(cat.getProviderAssetRoot());
		//TODO add parameters
		ImportCategoryImpl.Parameters param = impCat.getParameters();
		if (param == null) {
			param = new ImportCategoryImpl.Parameters();
			impCat.setParameters(param);
		}
		ParameterDetails tmpDetails = new ParameterDetails();
		tmpDetails.setName("Param");
		tmpDetails.setValue("ParamValue");
		System.out.println("Set the parameters of category");
		param.getParameter().add(tmpDetails);
		return impCat;
	}

	@BeforeClass
	public void initTenantDetails()
	{
		TenantContext.setContext(new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails()
				.get(QAToolUtil.TENANT_USER_NAME).toString()), TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails()
				.get(QAToolUtil.TENANT_NAME).toString())));
	}

	@AfterClass
	public void removeTenantDetails()
	{
		TenantContext.clearContext();
	}

}