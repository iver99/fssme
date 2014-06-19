package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class ImportSearchImpl  extends SearchImpl {
	private Object objFolderDetails;
	private Object objCategoryDetails;
	
	
	@XmlElements( value = {			
			@XmlElement(name="Folder", type=FolderImpl.class ),	           
			@XmlElement(name="folderId", type=Integer.class) 
	})	

	public Object getFolderDetails()
	{
		return objFolderDetails;
	}

	public void setFolderDetails(Object oFolder)
	{
		objFolderDetails=oFolder;
	}
	
	@XmlElements( value = {			
			@XmlElement(name="Category", type=CategoryImpl.class ),	           
			@XmlElement(name="categoryId", type=Integer.class) 
	})	

	public Object getCategoryDetails()
	{
		return objCategoryDetails;
	}

	public void setCategoryDetails(Object oCategory)
	{
		objCategoryDetails=oCategory;
	}
}
