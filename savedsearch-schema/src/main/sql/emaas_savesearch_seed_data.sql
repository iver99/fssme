Rem  drv: <migrate type="data_upgrade" version="13.1.0.0" />
Rem $Header: emcore/source/oracle/sysman/emdrep/sql/core/13.1.0.0/emanalytics/eman_data_upgrade.sql /st_emgc_pt-13.1mstr/2 2014/02/03 02:50:59 saurgarg Exp $
Rem
Rem eman_data_upgrade.sql
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      eman_data_upgrade.sql 
Rem
Rem    DESCRIPTION
Rem      EM Analytics framework data upgrade sql file.
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    kuabhina    12/13/13 - Created
Rem    miayu       06/06/14 - Reserve some permanent category/folder ID(s) for internal integrator
Rem

Rem SEED DATA FOR FOLDER
Insert into EM_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN) 
                          values (1,'All Searches',null,'Search Console Root Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','ALL_SEARCHES_NAME','EMANALYTICS','ALL_SEARCHES_DESC','EMANALYTICS',1,'oracle.sysman.core',0);
Insert into EM_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN) 
                          values (2,'Log Analytics Searches',1,'Log Analytics Root Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','ALL_LOG_ANALYTICS_SEARCHES_NAME','EMANALYTICS','ALL_LOG_ANALYTICS_SEARCHES_DESC','EMANALYTICS',1,'oracle.sysman.core',0);
Insert into EM_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN) 
                          values (3,'IT Analytics Searches',1,'IT Analytics Root Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','ALL_IT_ANALYTICS_SEARCHES_NAME','EMANALYTICS','ALL_IT_ANALYTICS_SEARCHES_DESC','EMANALYTICS',1,'oracle.sysman.core',0);
Insert into EM_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN) 
                          values (4,'Target Analytics Searches',1,'Target Analytics Root Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','ALL_TARGET_ANALYTICS_SEARCHES_NAME','EMANALYTICS','ALL_TARGET_ANALYTICS_SEARCHES_DESC','EMANALYTICS',1,'oracle.sysman.core',0);
Insert into EM_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN) 
                          values (5,'Demo Searches',1,'Demo Root Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','ALL_DEMO_SEARCHES_NAME','EMANALYTICS','ALL_DEMO_SEARCHES_DESC','EMANALYTICS',1,'oracle.sysman.core',0);

Rem SEED DATA FOR CATEGORY
Insert into EM_ANALYTICS_CATEGORY (CATEGORY_ID,NAME,DESCRIPTION,OWNER,CREATION_DATE,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,EM_PLUGIN_ID,DEFAULT_FOLDER_ID) 
                           values (1,'Log Analytics','Search Category for Log Analytics','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'LOG_ANALYTICS_CATEGORY_NAME','EMANALYTICS','LOG_ANALYTICS_CATEGORY_DESC','EMANALYTICS','oracle.sysman.core',2);
Insert into EM_ANALYTICS_CATEGORY (CATEGORY_ID,NAME,DESCRIPTION,OWNER,CREATION_DATE,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,EM_PLUGIN_ID,DEFAULT_FOLDER_ID) 
                           values (2,'Target Analytics','Search Category for Target Analytics','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'TARGET_ANALYTICS_CATEGORY_NAME','EMANALYTICS','TARGET_ANALYTICS_CATEGORY_DESC','EMANALYTICS','oracle.sysman.core',4);
Insert into EM_ANALYTICS_CATEGORY (CATEGORY_ID,NAME,DESCRIPTION,OWNER,CREATION_DATE,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,EM_PLUGIN_ID,DEFAULT_FOLDER_ID) 
                           values (3,'IT Analytics','Search Category for IT Analytics','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'IT_ANALYTICS_CATEGORY_NAME','EMANALYTICS','IT_ANALYTICS_CATEGORY_DESC','EMANALYTICS','oracle.sysman.core',3);
Insert into EM_ANALYTICS_CATEGORY (CATEGORY_ID,NAME,DESCRIPTION,OWNER,CREATION_DATE,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,EM_PLUGIN_ID,DEFAULT_FOLDER_ID) 
                           values (4,'Demo Analytics','Search Category for Demo','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'DEMO_ANALYTICS_CATEGORY_NAME','EMANALYTICS','DEMO_ANALYTICS_CATEGORY_DESC','EMANALYTICS','oracle.sysman.core',5);

Insert into EM_ANALYTICS_CATEGORY_PARAMS (CATEGORY_ID,NAME,PARAM_VALUE) 
                                  values (1,'CATEGORY_PARAM_VIEW_TASKFLOW','/WEB-INF/core/loganalytics/obssearch/plugins/dashboard-flow-definition.xml#dashboard-flow-definition');
Insert into EM_ANALYTICS_CATEGORY_PARAMS (CATEGORY_ID,NAME,PARAM_VALUE) 
                                  values (2,'CATEGORY_PARAM_VIEW_TASKFLOW','TBD');
Insert into EM_ANALYTICS_CATEGORY_PARAMS (CATEGORY_ID,NAME,PARAM_VALUE) 
                                  values (3,'CATEGORY_PARAM_VIEW_TASKFLOW','TBD');
Insert into EM_ANALYTICS_CATEGORY_PARAMS (CATEGORY_ID,NAME,PARAM_VALUE) 
                                  values (4,'CATEGORY_PARAM_VIEW_TASKFLOW','/WEB-INF/emlacore/dashboards/core-emanalytics-dumy-saved-search-generic-region.xml#core-emanalytics-dumy-region-task-flow');

Rem SEED DATA FOR SEARCH	
Insert into EM_ANALYTICS_SEARCH (NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN) 
                         values ('Demo Search 1','SYSMAN',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'SYSMAN',null,5,4,null,null,null,null,0,null,0,'*',0);
Insert into EM_ANALYTICS_SEARCH (NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN) 
                         values ('Demo Search 2','SYSMAN',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'SYSMAN',null,5,4,null,null,null,null,0,null,0,'*',0);
Insert into EM_ANALYTICS_SEARCH (NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN) 
                         values ('Log Search 1','SYSMAN',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'SYSMAN',null,2,1,null,null,null,null,0,null,0,'severity = ''WARN'' | stats count by severity',0);
Insert into EM_ANALYTICS_SEARCH (NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN) 
                         values ('Log Search 2','SYSMAN',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'SYSMAN',null,2,1,null,null,null,null,0,null,0,'* | stats count by severity',0);

Insert into EM_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR) values ((SELECT SEARCH_ID FROM EM_ANALYTICS_SEARCH WHERE NAME='Log Search 1'),'time_range',null,1,'ALL_TIME;');
Insert into EM_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR) values ((SELECT SEARCH_ID FROM EM_ANALYTICS_SEARCH WHERE NAME='Log Search 2'),'time_range',null,1,'ALL_TIME;');                         
