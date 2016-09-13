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
	private TenantLeakData() {
	}

	public static String getDeleteSql(long id)
	{

		String sql = "";
		sql = sql + " declare \n";
		sql = sql + " tenantid number(30) :=" + id + ";";
		sql = sql + " \nbegin \n";

		sql = sql + "delete from  EMS_ANALYTICS_LAST_ACCESS  where tenant_id = tenantid" + ";\n";
		sql = sql + "delete from EMS_ANALYTICS_SEARCH_PARAMS where tenant_id =tenantid" + ";\n";
		sql = sql + "delete from  EMS_ANALYTICS_SEARCH where tenant_id =tenantid" + ";\n";
		sql = sql + "delete from EMS_ANALYTICS_CATEGORY_PARAMS where tenant_id =tenantid" + ";\n";
		sql = sql + "delete from EMS_ANALYTICS_CATEGORY where tenant_id =tenantid" + ";\n";
		sql = sql + "delete from EMS_ANALYTICS_FOLDERS where tenant_id =tenantid" + ";\n";
		sql = sql + " end;";
		return sql;
	}

	public static String getSql(long tenantid)
	{
		String sql = "";

		sql = sql + " declare \n";
		sql = sql + " tenantid number(30) :=" + tenantid + ";";
		sql = sql + " begin \n";
		sql = sql
				+ " Insert into EMS_ANALYTICS_FOLDERS  (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,";
		sql = sql
				+ " LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,";
		sql = sql + " TENANT_ID)";
		sql = sql + "  values (1,'All Searches',null,'Search Console Root Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'TESTING',";
		sql = sql
				+ "SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','ALL_SEARCHES_NAME','EMANALYTICS','ALL_SEARCHES_DESC','EMANALYTICS',0,";
		sql = sql + "'oracle.sysman.core',0,tenantid);\n";

		sql = sql + " Insert into EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,";
		sql = sql + "LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,";
		sql = sql + " DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,TENANT_ID) ";
		sql = sql + " values (6666,'All Searches',1,'Search Console Root Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),";
		sql = sql + "'TESTING',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','ALL_SEARCHES_NAME','EMANALYTICS','ALL_SEARCHES_DESC',";
		sql = sql + "'EMANALYTICS',0,'oracle.sysman.core',0,tenantid);\n";

		sql = sql
				+ " Insert into EMS_ANALYTICS_CATEGORY  (CATEGORY_ID,NAME,DESCRIPTION,OWNER,CREATION_DATE,NAME_NLSID,NAME_SUBSYSTEM,";
		sql = sql + "DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,";
		sql = sql + "EM_PLUGIN_ID,DEFAULT_FOLDER_ID ,PROVIDER_NAME,PROVIDER_VERSION,PROVIDER_ASSET_ROOT,TENANT_ID)";
		sql = sql + "values (6666,'Log Analytics','Search Category for Log Analytics','TESTING',SYS_EXTRACT_UTC(SYSTIMESTAMP),"
				+ "'LOG_ANALYTICS_CATEGORY_NAME','EMANALYTICS',";
		sql = sql
				+ " 'LOG_ANALYTICS_CATEGORY_DESC','EMANALYTICS','oracle.sysman.core',6666,'LoganService','0.1','assetRoot',tenantid);\n";

		sql = sql
				+ "Insert into EMS_ANALYTICS_SEARCH(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,";
		sql = sql
				+ "SYSTEM_SEARCH,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID,IS_WIDGET)";
		sql = sql
				+ "values (6666,'WebLogic Servers with their Patch IDs','TESTING',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',";
		sql = sql
				+ "'Shows the status, Target Version and list of Patch IDs for WebLogic Servers',6666,6666,0,0,null,'',0,tenantid,1);\n";

		sql = sql
				+ "Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values ";
		sql = sql + "(6666,'WIDGET_TEMPLATE',null,1,'/widget/visualizationWidget/visualizationWidget.html',null,tenantid);\n";
		sql = sql
				+ "Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) ";
		sql = sql + "values (6666,'WIDGET_ICON',null,1,'/../images/func_horibargraph_24_ena.png',null,tenantid);\n";

		sql = sql
				+ "Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values";
		sql = sql + " (6666,'WIDGET_VIEWMODEL',null,1,'/widget/visualizationWidget/js/VisualizationWidget.js',null,tenantid);\n";
		sql = sql
				+ "Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values ";
		sql = sql + "(6666,'TIME_PERIOD_KEY',null,1,'24',null,tenantid);\n";

		sql = sql + " end;";

		return sql;
	}

}
