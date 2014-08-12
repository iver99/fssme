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

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class ImportTest {

	@Test
	public static void testImportFolderSet() throws Exception{
		try{

			FolderManagerImpl objFolder =  FolderManagerImpl.getInstance();
			FolderDetails folder = new FolderDetails();    		
			folder.setName("ImportFolderTest");
			folder.setDescription("ImportFolderDescription");
			folder.setUiHidden(false);

			FolderDetails folder1 = new FolderDetails();    		
			folder1.setName("ImportFolderTest1");
			folder1.setDescription("ImportFolderDescription1");
			folder1.setUiHidden(false);

			List<FolderDetails>  list =   new ArrayList<FolderDetails>();
			list.add(folder);
			list.add(folder1);
			List<FolderImpl>  listobj  = objFolder.saveMultipleFolders(list);		        
			AssertJUnit.assertTrue(listobj.size() == 2);

			for(Folder obj : listobj)
			{
				objFolder.deleteFolder(obj.getId(),true);
			}
				

		}catch(Exception e){
				e.printStackTrace();
			}
		}


		@Test
		public static void testImportCategorySet() throws Exception
		{
			try
			{
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


			List<ImportCategoryImpl>  listCat =   new ArrayList<ImportCategoryImpl>();		
			listCat.add((ImportCategoryImpl)category);
			listCat.add((ImportCategoryImpl)category1);

			

			List<ImportCategoryImpl>  listobj=  ((CategoryManagerImpl)catImpl).saveMultipleCategories(listCat);
			AssertJUnit.assertTrue(listobj.size() == 2);


			for(ImportCategoryImpl  obj : listobj)
			{
				catImpl.deleteCategory(obj.getId(),true);
			}
			}catch(Exception e){
				e.printStackTrace();
			}

		}


		@Test	
		public static void testImportSearchSet() throws Exception
		{
			try
			{
			FolderManagerImpl objFolder=FolderManagerImpl.getInstance();
			Folder folder=objFolder.createNewFolder();
			folder.setName("ImportFolderTest");
			folder.setDescription("testing purpose folder");              
			Integer folderId=objFolder.saveFolder(folder).getId();

			CategoryManager catImpl = CategoryManager.getInstance();
			Category category = catImpl.createNewCategory();
			category.setName("ImportCategoryName");
			category.setDescription("CategoryTest");				           	
			category = catImpl.saveCategory(category );

			
			SearchManagerImpl objSearch =  SearchManagerImpl.getInstance();  	
			ImportSearchImpl search= new ImportSearchImpl();
			search.setDescription("testing purpose");
			search.setName("Dummy Search");
			ObjectFactory objFactory = new ObjectFactory();
			JAXBElement<Integer> catId = objFactory.createCategoryId(category.getId());					
			search.setCategoryDet(catId);
			JAXBElement<Integer> fldId = objFactory.createFolderId(folderId);
			search.setFolderDet(fldId);			
			ImportSearchImpl.SearchParameters param= search.getSearchParameters();
			SearchParameterDetails tmpDetails = new SearchParameterDetails();
			tmpDetails.setName("Param1");
			tmpDetails.setType(ParameterType.STRING);
			tmpDetails.setValue("Value");
			param.getSearchParameter().add(tmpDetails);
			
				

			ImportSearchImpl search1=new ImportSearchImpl();
			search.setDescription("testing import purpose");
			search.setName("Dummy Search");    
			catId = objFactory.createCategoryId(category.getId());	
			search1.setCategoryDet(catId);
			fldId = objFactory.createFolderId(folderId);
			search1.setFolderDet(fldId);
			search1.getSearchParameters().getSearchParameter().add(tmpDetails);	

			List<ImportSearchImpl>  list =   new ArrayList<ImportSearchImpl>();
			list.add(search);
			list.add(search1);
			SearchManagerImpl  obj  =  SearchManagerImpl.getInstance();
			List<ImportSearchImpl>  listobj= obj.saveMultipleSearch(list);
			for(ImportSearchImpl  objSearch1 : listobj)
			{
				obj.deleteSearch(objSearch1.getId(),true);
			}
			catImpl.deleteCategory(category.getId(),true);
			objFolder.deleteFolder(folderId,true);
			}catch(Exception e){
					e.printStackTrace();
			}
			
		}
	}





