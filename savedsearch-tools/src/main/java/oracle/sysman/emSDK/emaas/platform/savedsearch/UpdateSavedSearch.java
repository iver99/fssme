package oracle.sysman.emSDK.emaas.platform.savedsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import oracle.sysman.emSDK.emaas.platform.savedsearch.logging.UpdateSavedSearchLog;
import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.model.restfulClient.discover.ServiceDiscoveryUtil;



public class UpdateSavedSearch {

	private int m_intCommandNo;
	private String m_strEndPoint;
	private String m_strSmUrl;
	private String m_strFilePath;
	private String m_strOutputPath;
	private Long m_lngCategoryId;	
	
	private static Logger _logger = UpdateSavedSearchLog.getLogger(UpdateSavedSearch.class);
	
    

	public int getOption()
	{
		return m_intCommandNo;
	}

	public void setOption(int iOption) 
	{
		this.m_intCommandNo = iOption;
	}

	private void printHelp() throws Exception
	{			
		FileUtils.readFile(ClassLoader
		.getSystemResourceAsStream(UpdateUtilConstants.HELP_FILE));
	}

	public String getEndPoint()
	{
		return m_strEndPoint;
	}

	public void setEndPoint(String url)
	{
		this.m_strEndPoint = url;
	}

	public String getFilePath()
	{
		return m_strFilePath;
	}

	public void setFilePath(String path) 
	{
		this.m_strFilePath = path;
	}

	public String getOutputPath()
	{
		return m_strOutputPath;
	}

	public void setOutputPath(String path)
	{
		this.m_strOutputPath = path;
	}
	
	public Long getCategoryId() 
	{
		return m_lngCategoryId;
	}

	public void setCategoryId(Long categoryId)
	{
		this.m_lngCategoryId = categoryId;
	}

	public String getSMUrl() 
	{
		return m_strSmUrl;
	}

	public void setSMUrl(String url) 
	{
		this.m_strSmUrl = url;
	}

	
	private void configureFromArgs(String[] args) 
	{
		boolean foundHelp=false , foundEndPoint=false, 
		foundCategory=false, foundInputPath=false, foundSmUrl=false,
		foundOutputPath=false, foundExport=false,foundImport=false ;
		try
		{			
     		for (int index = 0; index < args.length; index = index + 2) {

			if (args[index].equalsIgnoreCase(UpdateUtilConstants.HELP)) {
				foundHelp = true;
			}else if (args[index].equalsIgnoreCase(UpdateUtilConstants.EXPORT)) {
				index =index-1;
				foundExport=true;
			}else if (args[index].equalsIgnoreCase(UpdateUtilConstants.IMPORT)) {
				index =index-1;
				foundImport=true;
			}
     		else if (args[index].equalsIgnoreCase(UpdateUtilConstants.SM_URL)) {
     			if (index + 1 >= args.length) {
					throw new IllegalArgumentException("Service manager url is required"); 
				}
     			m_strSmUrl=args[index + 1];
     			if(m_strSmUrl.trim().length()==0)
					throw new IllegalArgumentException("Please specify valid service manager url");
				foundSmUrl=true;
			}
			else if (args[index].equalsIgnoreCase(UpdateUtilConstants.URL)) {
				if (index + 1 >= args.length) {
					throw new IllegalArgumentException("End point is required"); 
				}
				m_strEndPoint = args[index + 1];
				if(m_strEndPoint.trim().length()==0)
					throw new IllegalArgumentException("Please specify valid end point");
				if(!(m_strEndPoint.endsWith("/")) )
					m_strEndPoint = m_strEndPoint + "/";
				foundEndPoint =true;
			} else if (args[index].equalsIgnoreCase(UpdateUtilConstants.INPUT_FILE_PATH)) {				
				if (index + 1 >= args.length) {
					throw new IllegalArgumentException("Input file path is required"); 
				}
				m_strFilePath = args[index + 1];
				if(m_strFilePath.length()==0)
					throw new IllegalArgumentException("Please specify valid input path");
				foundInputPath=true;
			} else if(args[index].equalsIgnoreCase(UpdateUtilConstants.CATEGORY_ID)) {				
				if (index + 1 >= args.length) {
					throw new IllegalArgumentException("Category Id is required"); 
				}
				try
				{
					m_lngCategoryId =  Long.parseLong(args[index + 1]);
					foundCategory=true;
				}catch(NumberFormatException e)						
				{
					throw new IllegalArgumentException("Please specify valid Category Id"); 
				}				
				
			} else if (args[index].equalsIgnoreCase(UpdateUtilConstants.OUTPUT_FILE_PATH)) {
				if (index + 1 >= args.length) {
					throw new IllegalArgumentException("Output file path is required"); 
				}
				m_strOutputPath = args[index + 1];
				if(m_strOutputPath.length()==0)
					throw new IllegalArgumentException("Please specify valid Output Path ");
				foundOutputPath =true;
			}
		}
     		
		}
		catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			_logger .error("Error: " + e.getMessage());
			m_intCommandNo=UpdateUtilConstants.OPT_INVALID;			
		}
		
		if(foundEndPoint && foundSmUrl)
		{
			 m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
			 System.out.println("Please specify valid command");
		}
		
		if( (foundInputPath && foundOutputPath && foundImport ))
		{
			 if (foundHelp || foundCategory || foundExport)
			 {
				 m_intCommandNo =UpdateUtilConstants.OPT_INVALID;
				 System.out.println("Please specify valid command");
			 }
			 else
			 {
				 m_intCommandNo = UpdateUtilConstants.OPT_UPDATE_SEARCH;
			 }
		}
			
		if( (foundCategory && foundOutputPath && foundExport))
		{
			if((foundHelp || foundInputPath || foundImport))
			{
				m_intCommandNo =UpdateUtilConstants.OPT_INVALID;
				System.out.println("Please specify valid command");
			}
			else
			m_intCommandNo = UpdateUtilConstants.OPT_GET_SEARCH;
		}
		
		
		if(foundHelp)
		{
			if( ((foundCategory || foundOutputPath || foundEndPoint || foundInputPath  || foundSmUrl )))
			{
				m_intCommandNo =UpdateUtilConstants.OPT_INVALID;
				System.out.println("Please specify valid command");
			}else
			{
				m_intCommandNo = UpdateUtilConstants.OPT_DISPLAY_HELP;			
			}
		}
		
	}

	public static void main(String args[]) throws Exception 
	{
		
		UpdateSavedSearch obj = new UpdateSavedSearch();		
		obj.configureFromArgs(args);
		
		if(obj.getSMUrl()!=null && obj.getSMUrl().length()!=0)
		{
			
			String tmp = ServiceDiscoveryUtil.getSsfUrlBySmUrl(obj.getSMUrl());
			obj.setEndPoint(tmp);	        
		}
				
		if(obj.getOption() == 0 )
			System.out.println("Please specify valid command");
		
		if (obj.getOption() == UpdateUtilConstants.OPT_DISPLAY_HELP)
		{
			obj.printHelp();
		}
		if (obj.getOption() == UpdateUtilConstants.OPT_UPDATE_SEARCH) 
		{			
			UpdateSearchUtil.importSearches(obj.getEndPoint(), obj.getFilePath(),obj.getOutputPath());
		}
		if (obj.getOption() == UpdateUtilConstants.OPT_GET_SEARCH)
		{	
			
			UpdateSearchUtil.exportSearches(obj.getCategoryId(), obj.getEndPoint(), obj.getOutputPath());
		}
	}

		
}
