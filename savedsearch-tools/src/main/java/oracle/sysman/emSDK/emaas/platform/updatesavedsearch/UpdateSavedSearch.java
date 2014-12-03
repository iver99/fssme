package oracle.sysman.emSDK.emaas.platform.updatesavedsearch;

import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.logging.UpdateSavedSearchLog;
import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.model.restfulClient.discover.ServiceDiscoveryUtil;

import org.apache.log4j.Logger;

public class UpdateSavedSearch
{

	public static void main(String args[]) throws Exception
	{

		UpdateSavedSearch obj = new UpdateSavedSearch();
		obj.configureFromArgs(args);

		if (obj.getSMUrl() != null && obj.getSMUrl().length() != 0) {

			String tmp = ServiceDiscoveryUtil.getSsfUrlBySmUrl(obj.getSMUrl());
			obj.setEndPoint(tmp);
		}

		if (obj.getOption() == UpdateUtilConstants.OPT_DISPLAY_HELP || obj.getOption() == UpdateUtilConstants.OPT_INVALID) {
			obj.printHelp();
		}
		if (obj.getOption() == UpdateUtilConstants.OPT_UPDATE_SEARCH) {
			UpdateSearchUtil.importSearches(obj.getEndPoint(), obj.getFilePath(), obj.getOutputPath());
		}
		if (obj.getOption() == UpdateUtilConstants.OPT_GET_SEARCH) {
			UpdateSearchUtil.exportSearches(obj.getCategoryId(), obj.getEndPoint(), obj.getOutputPath());
		}
	}

	private int m_intCommandNo;
	private String m_strEndPoint;
	private String m_strSmUrl;
	private String m_strFilePath;
	private String m_strOutputPath;

	private Long m_lngCategoryId;

	private static Logger _logger = UpdateSavedSearchLog.getLogger(UpdateSavedSearch.class);

	public Long getCategoryId()
	{
		return m_lngCategoryId;
	}

	public String getEndPoint()
	{
		return m_strEndPoint;
	}

	public String getFilePath()
	{
		return m_strFilePath;
	}

	public int getOption()
	{
		return m_intCommandNo;
	}

	public String getOutputPath()
	{
		return m_strOutputPath;
	}

	public String getSMUrl()
	{
		return m_strSmUrl;
	}

	public void setCategoryId(Long categoryId)
	{
		m_lngCategoryId = categoryId;
	}

	public void setEndPoint(String url)
	{
		m_strEndPoint = url;
	}

	public void setFilePath(String path)
	{
		m_strFilePath = path;
	}

	public void setOption(int iOption)
	{
		m_intCommandNo = iOption;
	}

	public void setOutputPath(String path)
	{
		m_strOutputPath = path;
	}

	public void setSMUrl(String url)
	{
		m_strSmUrl = url;
	}

	private void configureFromArgs(String[] args)
	{
		boolean foundHelp = false, foundEndPoint = false, foundCategory = false, foundInputPath = false, foundSmUrl = false, foundOutputPath = false, foundExport = false, foundImport = false;
		boolean isInvalidArg = false;
		try {
			for (int index = 0; index < args.length; index = index + 2) {

				if (args[index].equalsIgnoreCase(UpdateUtilConstants.HELP)) {
					index = index - 1;
					foundHelp = true;
				}
				else if (args[index].equalsIgnoreCase(UpdateUtilConstants.EXPORT)) {
					index = index - 1;
					foundExport = true;
				}
				else if (args[index].equalsIgnoreCase(UpdateUtilConstants.IMPORT)) {
					index = index - 1;
					foundImport = true;
				}
				else if (args[index].equalsIgnoreCase(UpdateUtilConstants.SM_URL)) {
					if (index + 1 >= args.length) {
						throw new IllegalArgumentException("Service manager url is required");
					}
					m_strSmUrl = args[index + 1];
					if (m_strSmUrl.trim().length() == 0) {
						throw new IllegalArgumentException("Please specify valid service manager url");
					}
					foundSmUrl = true;
				}
				else if (args[index].equalsIgnoreCase(UpdateUtilConstants.URL)) {
					if (index + 1 >= args.length) {
						throw new IllegalArgumentException("SSF URL is required");
					}
					m_strEndPoint = args[index + 1];
					if (m_strEndPoint.trim().length() == 0) {
						throw new IllegalArgumentException("Please specify valid SSF URL");
					}
					if (!m_strEndPoint.endsWith("/")) {
						m_strEndPoint = m_strEndPoint + "/";
					}
					foundEndPoint = true;
				}
				else if (args[index].equalsIgnoreCase(UpdateUtilConstants.INPUT_FILE_PATH)) {
					if (index + 1 >= args.length) {
						throw new IllegalArgumentException("Input file path is required");
					}
					m_strFilePath = args[index + 1];
					if (m_strFilePath.length() == 0) {
						throw new IllegalArgumentException("Please specify valid input path");
					}
					foundInputPath = true;
				}
				else if (args[index].equalsIgnoreCase(UpdateUtilConstants.CATEGORY_ID)) {
					if (index + 1 >= args.length) {
						throw new IllegalArgumentException("Category Id is required");
					}
					try {
						m_lngCategoryId = Long.parseLong(args[index + 1]);
						foundCategory = true;
					}
					catch (NumberFormatException e) {
						throw new IllegalArgumentException("Please specify valid Category Id");
					}

				}
				else if (args[index].equalsIgnoreCase(UpdateUtilConstants.OUTPUT_FILE_PATH)) {
					if (index + 1 >= args.length) {
						throw new IllegalArgumentException("Output file path is required");
					}
					m_strOutputPath = args[index + 1];
					if (m_strOutputPath.length() == 0) {
						throw new IllegalArgumentException("Please specify valid Output Path ");
					}
					foundOutputPath = true;
				}
				else {
					System.out.println("Error :Please specify valid command ");
					System.exit(0);
				}
			}

		}
		catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			_logger.error("Error: " + e.getMessage());
			m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
		}

		if (foundEndPoint && foundSmUrl) {
			m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
			System.out.println("Please specify valid command - specify either service manager URL or SSF URL");
			System.exit(0);
		}

		if (foundImport && foundExport) {
			m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
			System.out.println("Error : argument " + UpdateUtilConstants.IMPORT + "  not allowed with argument "
					+ UpdateUtilConstants.EXPORT);
			System.exit(0);
		}

		if (foundImport && (!foundInputPath || !foundOutputPath)) {
			System.out.println("Error: you must specify both " + UpdateUtilConstants.INPUT_FILE_PATH + " and  "
					+ UpdateUtilConstants.OUTPUT_FILE_PATH + " options.");
			System.exit(0);
		}

		if (foundExport && (!foundCategory || !foundOutputPath)) {
			System.out.println("Error: you must specify both " + UpdateUtilConstants.CATEGORY_ID + " and  "
					+ UpdateUtilConstants.OUTPUT_FILE_PATH + " options.");
			System.exit(0);
		}

		if (foundInputPath && foundOutputPath && foundImport) {
			String temp = "";
			m_intCommandNo = UpdateUtilConstants.OPT_UPDATE_SEARCH;
			if (foundHelp) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.HELP + "  ";
			}
			if (foundCategory) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.CATEGORY_ID + "  ";
			}
			if (foundExport) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.EXPORT + "  ";
			}
			if (m_intCommandNo == UpdateUtilConstants.OPT_INVALID) {
				System.out.println("Error : argument " + temp + "  not allowed with argument " + UpdateUtilConstants.IMPORT);
			}

		}

		if (foundCategory && foundOutputPath && foundExport) {
			String temp = "";
			m_intCommandNo = UpdateUtilConstants.OPT_GET_SEARCH;
			if (foundHelp) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.HELP + "  ";
			}
			if (foundInputPath) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.INPUT_FILE_PATH + "  ";
			}
			if (foundImport) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.IMPORT + "  ";
			}
			if (m_intCommandNo == UpdateUtilConstants.OPT_INVALID) {
				System.out.println("Error : argument " + temp + "  not allowed with argument " + UpdateUtilConstants.EXPORT);
			}
		}

		if (foundHelp) {
			m_intCommandNo = UpdateUtilConstants.OPT_DISPLAY_HELP;
			String temp = "";
			if (foundCategory) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.CATEGORY_ID + "  ";
			}

			if (foundOutputPath) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.OUTPUT_FILE_PATH + "  ";
			}
			if (foundEndPoint) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.URL + "  ";
			}
			if (foundInputPath) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				System.out.println("Please specify valid command");
			}
			if (foundSmUrl) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.INPUT_FILE_PATH + "  ";
			}
			if (foundExport) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.EXPORT + "  ";
			}
			if (foundImport) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.IMPORT + "  ";
			}
			if (m_intCommandNo == UpdateUtilConstants.OPT_INVALID) {
				System.out.println("Error : argument " + temp + "  not allowed with argument " + UpdateUtilConstants.HELP);
			}
		}

	}

	private void printHelp() throws Exception
	{
		FileUtils.readFile(ClassLoader.getSystemResourceAsStream(UpdateUtilConstants.HELP_FILE));
	}

}
