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

		if (obj.getOption() == UpdateUtilConstants.OPT_DISPLAY_HELP || obj.getOption() == UpdateUtilConstants.OPT_INVALID) {
			UpdateSavedSearch.printHelp();
		}
		if (obj.getOption() == UpdateUtilConstants.OPT_UPDATE_SEARCH) {

			UpdateSearchUtil.importSearches(obj.getEndPoint(), obj.getFilePath(), obj.getOutputPath(), obj.getauthToken(),
					obj.getTenantId());
		}
		if (obj.getOption() == UpdateUtilConstants.OPT_GET_SEARCH) {

			UpdateSearchUtil.exportSearches(obj.getCategoryId(), obj.getEndPoint(), obj.getOutputPath(), obj.getauthToken(),
					obj.getTenantId());
		}
	}

	private static void printHelp() throws Exception
	{
		FileUtils.readFile(ClassLoader.getSystemResourceAsStream(UpdateUtilConstants.HELP_FILE));
	}

	private int m_intCommandNo;
	private String m_strEndPoint;
	private String m_strSmUrl;
	private String m_strFilePath;
	private String m_strOutputPath;
	private String m_tenantid;

	private String m_strSsfVersion;

	private String m_authToken;

	private Long m_lngCategoryId;
	private static Logger LOGGER = UpdateSavedSearchLog.getLogger(UpdateSavedSearch.class);

	public String getauthToken()
	{
		return m_authToken;
	}

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

	public String getSsfVersion()
	{
		return m_strSsfVersion;
	}

	public String getTenantId()
	{
		return m_tenantid;
	}

	public void getTenantId(String value)
	{
		m_tenantid = value;
	}

	public void setauthToken(String value)
	{
		m_authToken = value;
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

	public void setSsfVersion(String sVersion)
	{
		m_strSsfVersion = sVersion;
	}

	private void configureFromArgs(String[] args)
	{
		boolean foundHelp = false, foundEndPoint = false, foundCategory = false, foundInputPath = false, foundSmUrl = false, foundOutputPath = false, foundExport = false, foundImport = false;
		boolean foundSearchVersion = false, foundToken = false, foundTenant = false;
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
				else if (args[index].equalsIgnoreCase(UpdateUtilConstants.TENANT_TOKEN)) {
					if (index + 1 >= args.length) {
						throw new IllegalArgumentException("Tenant Id  is required");
					}
					m_tenantid = args[index + 1];
					if (m_tenantid.length() == 0) {
						throw new IllegalArgumentException("Please specify valid Tenant Id");
					}
					m_tenantid = m_tenantid + UpdateUtilConstants.SSF_ORACLE;
					foundTenant = true;
				}
				else if (args[index].equalsIgnoreCase(UpdateUtilConstants.AUTH_TOKEN)) {
					if (index + 1 >= args.length) {
						throw new IllegalArgumentException("Authntication toekn is required");
					}
					m_authToken = args[index + 1];
					if (m_authToken.length() == 0) {
						throw new IllegalArgumentException("Please specify valid Authntication toekn");
					}
					foundToken = true;
				}
				else if (args[index].equalsIgnoreCase(UpdateUtilConstants.SSS_VERSION_STR)) {
					if (index + 1 >= args.length) {
						throw new IllegalArgumentException("SSF version is required");
					}
					m_strSsfVersion = args[index + 1];
					if (m_strSsfVersion.length() == 0) {
						throw new IllegalArgumentException("Please specify valid SSF version");
					}
					foundSearchVersion = true;
				}
				else {
					System.exit(0);
				}
			}

		}
		catch (IllegalArgumentException e) {
			LOGGER.error("Error: " + e.getMessage());
			m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
		}

		if (foundImport && foundExport) {
			m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
					+ UpdateUtilConstants.EXPORT);
			System.exit(0);
		}

		if (!foundHelp && foundImport && (!foundInputPath || !foundOutputPath || !foundToken || !foundTenant)) {
			System.exit(0);
		}

		if (!foundHelp && foundExport && (!foundCategory || !foundOutputPath || !foundToken || !foundTenant)) {
			System.exit(0);
		}

		if (!foundHelp && foundInputPath && foundOutputPath && foundImport && foundToken && foundTenant) {
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
			if (foundEndPoint && foundSmUrl || foundEndPoint && foundSearchVersion || foundSmUrl && !foundSearchVersion
					|| !foundSmUrl && foundSearchVersion || !foundEndPoint && !foundSmUrl) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
			}
			if (m_intCommandNo == UpdateUtilConstants.OPT_INVALID) {
				if (temp.length() != 0) {
				}
			}

		}

		if (!foundHelp && foundCategory && foundOutputPath && foundExport && foundToken && foundTenant) {
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
			if (foundEndPoint && foundSmUrl || foundEndPoint && foundSearchVersion || foundSmUrl && !foundSearchVersion
					|| !foundSmUrl && foundSearchVersion || !foundEndPoint && !foundSmUrl) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
			}
			if (m_intCommandNo == UpdateUtilConstants.OPT_INVALID) {
				if (temp.length() != 0) {
				}
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
			if (foundSmUrl) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.SM_URL + "  ";
			}
			if (foundSearchVersion) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.SSS_VERSION_STR + "  ";
			}
			if (foundToken) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.AUTH_TOKEN + "  ";
			}

			if (foundTenant) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
				temp = temp + UpdateUtilConstants.TENANT_TOKEN + "  ";
			}
			if (m_intCommandNo == UpdateUtilConstants.OPT_INVALID) {
			}
		}

		if (foundSmUrl && foundSearchVersion) {
			try {
				if (m_intCommandNo != UpdateUtilConstants.OPT_INVALID) {
					m_strEndPoint = ServiceDiscoveryUtil.getSsfUrlBySmUrl(m_strSmUrl, m_strSsfVersion);
					LOGGER.info("URL:" + m_strEndPoint);
				}
			}
			catch (Exception e) {
				m_intCommandNo = UpdateUtilConstants.OPT_INVALID;
			}
		}

	}

}
