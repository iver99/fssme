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
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.SearchParameterDetails;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class ImportTest
{

	@Test
	public static void testImportCategorySet() throws Exception
	{
		try {
			CategoryManager catImpl = CategoryManager.getInstance();
			Category category = catImpl.createNewCategory();
			category.setName("ImportCategory");
			category.setDescription("CategoryTest");
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

			List<ImportCategoryImpl> listCat = new ArrayList<ImportCategoryImpl>();
			listCat.add((ImportCategoryImpl) category);
			listCat.add((ImportCategoryImpl) category1);

			List<Category> listobj = ((CategoryManagerImpl) catImpl).saveMultipleCategories(listCat);
			AssertJUnit.assertTrue(listobj.size() == 2);

			for (Category obj : listobj) {
				AssertJUnit.assertNotNull("Imported category has NULL creation date", obj.getCreatedOn());
				catImpl.deleteCategory(obj.getId(), true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}

	@Test
	public static void testImportSearchSet() throws Exception
	{
		try {
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			Folder folder = objFolder.createNewFolder();
			folder.setName("ImportFolderTest");
			folder.setDescription("testing purpose folder");
			Integer folderId = objFolder.saveFolder(folder).getId();

			CategoryManager catImpl = CategoryManager.getInstance();
			Category category = catImpl.createNewCategory();
			category.setName("ImportCategoryName");
			category.setDescription("CategoryTest");
			category = catImpl.saveCategory(category);

			SearchManagerImpl objSearch = SearchManagerImpl.getInstance();
			ImportSearchImpl search = new ImportSearchImpl();
			search.setDescription("testing purpose");
			search.setName("Dummy Search");
			ObjectFactory objFactory = new ObjectFactory();
			JAXBElement<Integer> catId = objFactory.createCategoryId(category.getId());
			search.setCategoryDet(catId);
			JAXBElement<Integer> fldId = objFactory.createFolderId(folderId);
			search.setFolderDet(fldId);
			ImportSearchImpl.SearchParameters param = search.getSearchParameters();
			SearchParameterDetails tmpDetails = new SearchParameterDetails();
			tmpDetails.setName("Param1");
			tmpDetails.setType(ParameterType.STRING);
			tmpDetails.setValue("Value");
			param.getSearchParameter().add(tmpDetails);

			ImportSearchImpl search1 = new ImportSearchImpl();
			search.setDescription("testing import purpose");
			search.setName("Import Dummy Search");
			catId = objFactory.createCategoryId(category.getId());
			search1.setCategoryDet(catId);
			fldId = objFactory.createFolderId(folderId);
			search1.setFolderDet(fldId);
			SearchParameterDetails tmpDetails2 = new SearchParameterDetails();
			tmpDetails2.setName("Param2");
			tmpDetails2.setType(ParameterType.CLOB);
			param.getSearchParameter().add(tmpDetails2);
			search1.getSearchParameters().getSearchParameter().add(tmpDetails2);

			List<ImportSearchImpl> list = new ArrayList<ImportSearchImpl>();
			list.add(search);
			list.add(search1);
			SearchManagerImpl obj = SearchManagerImpl.getInstance();
			List<Search> listobj = obj.saveMultipleSearch(list);
			for (Search objSearch1 : listobj) {
				AssertJUnit.assertNotNull(objSearch1);
				AssertJUnit.assertNotNull(objSearch1.getCreatedOn());
				AssertJUnit.assertNotNull(objSearch1.getLastModifiedOn());
				AssertJUnit.assertNotNull(objSearch1.getLastAccessDate());
				AssertJUnit.assertEquals(objSearch1.getCreatedOn(), objSearch1.getLastModifiedOn());
				AssertJUnit.assertEquals(objSearch1.getCreatedOn(), objSearch1.getLastAccessDate());
				if (objSearch1.getName().equals("Dummy Search")) {
					AssertJUnit.assertTrue(objSearch1.getParameters().get(0).getName().equals("Param1"));
					AssertJUnit.assertTrue(objSearch1.getParameters().get(0).getValue().equals("Value"));
					AssertJUnit.assertTrue(objSearch1.getParameters().get(0).getType().equals("STRING"));
				}
				if (objSearch1.getName().equals("Import Dummy Search")) {
					AssertJUnit.assertTrue(objSearch1.getParameters().get(0).getName().equals("Param2"));
					AssertJUnit.assertTrue(objSearch1.getParameters().get(0).getValue() == null);
					AssertJUnit.assertTrue(objSearch1.getParameters().get(0).getType().equals("CLOB"));
				}

				obj.deleteSearch(objSearch1.getId(), true);
			}
			catImpl.deleteCategory(category.getId(), true);
			objFolder.deleteFolder(folderId, true);
		}
		catch (Exception e) {
			AssertJUnit.fail(e.getMessage());
		}

	}
}
