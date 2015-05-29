/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.savedsearch;

/**
 * @author vinjoshi
 */
public class TenantLeakData
{

	public static String getSql(long tenantid)
	{
		String Sql = "";

		Sql = Sql + " declare \n";
		Sql = Sql + " tenantid number(30) :=" + tenantid + ";";
		Sql = Sql + " begin \n";
		Sql = Sql
				+ " Insert into EMS_ANALYTICS_FOLDERS  (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,";
		Sql = Sql
				+ " LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,";
		Sql = Sql + " TENANT_ID)";
		Sql = Sql + "  values (1,'All Searches',null,'Search Console Root Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'TESTING',";
		Sql = Sql
				+ "SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','ALL_SEARCHES_NAME','EMANALYTICS','ALL_SEARCHES_DESC','EMANALYTICS',0,";
		Sql = Sql + "'oracle.sysman.core',0,tenantid);\n";

		Sql = Sql + " Insert into EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,";
		Sql = Sql + "LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,";
		Sql = Sql + " DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,TENANT_ID) ";
		Sql = Sql + " values (6666,'All Searches',1,'Search Console Root Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),";
		Sql = Sql + "'TESTING',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','ALL_SEARCHES_NAME','EMANALYTICS','ALL_SEARCHES_DESC',";
		Sql = Sql + "'EMANALYTICS',0,'oracle.sysman.core',0,tenantid);\n";

		Sql = Sql
				+ " Insert into EMS_ANALYTICS_CATEGORY  (CATEGORY_ID,NAME,DESCRIPTION,OWNER,CREATION_DATE,NAME_NLSID,NAME_SUBSYSTEM,";
		Sql = Sql + "DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,";
		Sql = Sql + "EM_PLUGIN_ID,DEFAULT_FOLDER_ID ,PROVIDER_NAME,PROVIDER_VERSION,PROVIDER_ASSET_ROOT,TENANT_ID)";
		Sql = Sql + "values (6666,'Log Analytics','Search Category for Log Analytics','TESTING',SYS_EXTRACT_UTC(SYSTIMESTAMP),"
				+ "'LOG_ANALYTICS_CATEGORY_NAME','EMANALYTICS',";
		Sql = Sql
				+ " 'LOG_ANALYTICS_CATEGORY_DESC','EMANALYTICS','oracle.sysman.core',6666,'LoganService','0.1','assetRoot',tenantid);\n";

		Sql = Sql
				+ "Insert into EMS_ANALYTICS_SEARCH(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,";
		Sql = Sql
				+ "NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID,IS_WIDGET)";
		Sql = Sql
				+ "values (6666,'WebLogic Servers with their Patch IDs','TESTING',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',";
		Sql = Sql
				+ "'Shows the status, Target Version and list of Patch IDs for WebLogic Servers',6666,6666,null,null,null,null,0,null,0,null,'',0,tenantid,1);\n";

		Sql = Sql
				+ "Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values ";
		Sql = Sql + "(6666,'WIDGET_TEMPLATE',null,1,'/widget/visualizationWidget/visualizationWidget.html',null,tenantid);\n";
		Sql = Sql
				+ "Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) ";
		Sql = Sql + "values (6666,'WIDGET_ICON',null,1,'/../images/func_horibargraph_24_ena.png',null,tenantid);\n";

		Sql = Sql
				+ "Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values";
		Sql = Sql + " (6666,'WIDGET_VIEWMODEL',null,1,'/widget/visualizationWidget/js/VisualizationWidget.js',null,tenantid);\n";
		Sql = Sql
				+ "Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values ";
		Sql = Sql + "(6666,'TIME_PERIOD_KEY',null,1,'24',null,tenantid);\n";

		Sql = Sql + " end;";

		return Sql;
	}

}
