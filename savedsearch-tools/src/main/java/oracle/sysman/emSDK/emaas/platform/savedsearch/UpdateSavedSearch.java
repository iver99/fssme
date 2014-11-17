package oracle.sysman.emSDK.emaas.platform.savedsearch;

import java.io.IOException;

import org.apache.log4j.Logger;

import oracle.sysman.emSDK.emaas.platform.savedsearch.FileUtils;
import oracle.sysman.emSDK.emaas.platform.savedsearch.logging.UpdateSavedSearchLog;
import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.model.restfulClient.discover.IServiceDefinition;
import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.model.restfulClient.discover.SavedSearchServiceDefFactory;
import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.model.restfulClient.discover.ServiceDiscoverer;

public class UpdateSavedSearch {

	private int m_intCommandNo;
	private String m_strEndPoint;
	private String m_strSmUrl;
	private String m_strFilePath;
	private String m_strOutputPath;
	private Long m_lngCategoryId;	
	private static final String HELP="-help";
	private static final String URL="-ssfurl";
	private static final String SM_URL="-servicemanagerurl";
	private static final String INPUT_FILE_PATH="-inputfilepath";
	private static final String OUTPUT_FILE_PATH="-outputfilepath";
	private static final String CATEGORY_ID="-categoryId";
	private static final String EXPORT="-export";
	private static final String IMPORT="-import";
	private static final int OPT_DISPLAY_HELP=1;
	private static final int OPT_UPDATE_SEARCH=2;
	private static final int OPT_GET_SEARCH=3;
	private static final int OPT_INVALID=0;
	private static Logger _logger = UpdateSavedSearchLog.getLogger(UpdateSavedSearch.class);
	
	
	public static void main(String args[]) {
		
		
		
		UpdateSavedSearch obj = new UpdateSavedSearch();
		ImportSearchObject objUpdate = new ImportSearchObject();
		obj.configureFromArgs(args);
		
		if(obj.getOption() == 0 )
			System.out.println("Please specify valid command");
		
		if (obj.getOption() == OPT_DISPLAY_HELP) {
			obj.printHelp();
		}

		if (obj.getOption() == OPT_UPDATE_SEARCH) {
			if (!UpdateSearchUtil.isEndpointReachable(obj.getEndPoint())) {
				System.out.println("The endpoint was not reachable.");
				return;
			}
			obj.importSearches(obj.getEndPoint(), obj.getFilePath(),
					obj.getOutputPath());
		}
		if (obj.getOption() == OPT_GET_SEARCH) {
			if (!UpdateSearchUtil.isEndpointReachable(obj.getEndPoint())) {
				System.out.println("The endpoint was not reachable.");
				return;
			}
			
			try {
				ExportSearchObject objExport = new ExportSearchObject();
				String data = objExport.exportSearch(obj.getCategoryId(),obj.getEndPoint());
				if(FileUtils.fileExist(obj.getOutputPath()))
					FileUtils.deleteFile(obj.getOutputPath());
				FileUtils.createOutputfile(obj.getOutputPath() , data);
				System.out.println("The process completed successfully.");

			} catch (IOException e) {
				System.out
						.println("an error occurred while writing data to file : "
								+ obj.getOutputPath() );
				_logger.error("an error occurred while writing data to file : "
						+ obj.getOutputPath() );
				return;
			}catch (Exception e) {
				System.out
				.println("an error occurred exporting searches  ");
				_logger.error("an error occurred exporting searches  ");
		return;
	}
			
		}
	}

	public int getOption() {
		return m_intCommandNo;
	}

	public void setOption(int iOption) {
		this.m_intCommandNo = iOption;
	}

	private void printHelp() {
		System.out.println("UpdateSavedSearch -url -inputfilepath  -outfilepath");
	}

	private void configureFromArgs(String[] args) {
		boolean foundHelp=false , foundEndPoint=false, 
		foundCategory=false, foundInputPath=false, foundSmUrl=false,
		foundOutputPath=false, foundExport=false,foundImport=false , foundUrl =false;
		
				
		
		try
		{			
     		for (int index = 0; index < args.length; index = index + 2) {

			if (args[index].equalsIgnoreCase(HELP)) {
				foundHelp = true;
			}else if (args[index].equalsIgnoreCase(EXPORT)) {
				foundExport=true;
			}else if (args[index].equalsIgnoreCase(IMPORT)) {
				foundImport=true;
			}
     		else if (args[index].equalsIgnoreCase(SM_URL)) {
     			if (index + 1 >= args.length) {
					throw new IllegalArgumentException("Service manager url is required"); 
				}
     			m_strSmUrl=args[index + 1];
     			if(m_strSmUrl.length()==0)
					throw new IllegalArgumentException("Please specify valid service manager url");
				foundSmUrl=true;
			}
			else if (args[index].equalsIgnoreCase(URL)) {
				if (index + 1 >= args.length) {
					throw new IllegalArgumentException("End point is required"); 
				}
				m_strEndPoint = args[index + 1];
				if(m_strEndPoint.length()==0)
					throw new IllegalArgumentException("Please specify valid end point");
				foundEndPoint =true;
			} else if (args[index].equalsIgnoreCase(INPUT_FILE_PATH)) {				
				if (index + 1 >= args.length) {
					throw new IllegalArgumentException("Input path is required"); 
				}
				m_strFilePath = args[index + 1];
				if(m_strFilePath.length()==0)
					throw new IllegalArgumentException("Please specify valid input path");
				foundInputPath=true;
			} else if(args[index].equalsIgnoreCase(CATEGORY_ID)) {				
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
				
			} else if (args[index].equalsIgnoreCase(OUTPUT_FILE_PATH)) {
				if (index + 1 >= args.length) {
					throw new IllegalArgumentException("Output path is required"); 
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
			m_intCommandNo=OPT_INVALID;			
		}
		
		if(foundEndPoint && foundSmUrl)
		{
			 m_intCommandNo =OPT_INVALID;
			 System.out.println("Please specify valid command");
		}
		
		if( (foundInputPath && foundOutputPath && foundImport ))
		{
			 if (foundHelp || foundCategory || foundExport)
			 {
				 m_intCommandNo =OPT_INVALID;
				 System.out.println("Please specify valid command");
			 }
			 else
			 m_intCommandNo = OPT_UPDATE_SEARCH;
		}
			
		if( (foundCategory && foundOutputPath && foundExport))
		{
			if((foundHelp || foundInputPath || foundImport))
			{
				m_intCommandNo =OPT_INVALID;
				System.out.println("Please specify valid command");
			}
			else
			m_intCommandNo = OPT_GET_SEARCH;
		}
		
		
		if(foundHelp)
		{
			if( ((foundCategory || foundOutputPath || foundEndPoint || foundInputPath )))
			{
				m_intCommandNo =OPT_INVALID;
				System.out.println("Please specify valid command");
			}else
			{
				m_intCommandNo = OPT_DISPLAY_HELP;			
			}
		}
		
	}

	public String getEndPoint() {
		return m_strEndPoint;
	}

	public void setEndPoint(String url) {
		this.m_strEndPoint = url;
	}

	public String getFilePath() {
		return m_strFilePath;
	}

	public void setFilePath(String path) {
		this.m_strFilePath = path;
	}

	public String getOutputPath() {
		return m_strOutputPath;
	}

	public void setOutputPath(String path) {
		this.m_strOutputPath = path;
	}
	
	
	
	public Long getCategoryId() {
		return m_lngCategoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.m_lngCategoryId = categoryId;
	}


	private boolean validateInput() {

		
		if (m_strEndPoint != null && m_strFilePath != null
				&& m_strOutputPath != null) {
			if (m_strEndPoint.equalsIgnoreCase("")) {
				System.out.println("Please enter valid endpoint.");
				return true;
			}

			if (m_strFilePath.equalsIgnoreCase("")) {
				System.out.println("Please enter valid file name.");
				return true;
			}

			if (m_strOutputPath.equalsIgnoreCase("")) {
				System.out.println("Please enter valid output file name.");
				return true;
			}
			m_intCommandNo = 1;
		}

		return false;
	}

	private void importSearches(String endpoint, String inputfile,
			String outputfile) {

		String data = "";
		String outputData = "";
		try {
			data = FileUtils.readFile(inputfile);

		} catch (IOException e) {
			_logger .error("An error occurred while reading the input file : " + e.getMessage());
			System.out
					.println("An error occurred while reading the input file : "
							+ inputfile);
			return;
		} catch (Exception ex) {
			_logger .error("An error occurred while updating searches" + ex.getMessage());
			System.out.println("An error occurred while updating searches");
			return;
		}

		ImportSearchObject objUpdate = new ImportSearchObject();

		try {

			outputData = objUpdate.importSearches(endpoint, data);

		} catch (Exception e1) {

			_logger .error("An error occurred while creating or updating search object" +e1.getMessage());
			System.out
					.println("An error occurred while creating or updating search object");
		}

		try {
			FileUtils.createOutputfile(outputfile, outputData);

		} catch (IOException e) {
			System.out
					.println("an error occurred while writing data to file : "
							+ outputfile);
			_logger .error("an error occurred while writing data to file : "
					+ outputfile);
			return;
		}
		
		System.out.println("The update process completed successfully.");
	}

}
