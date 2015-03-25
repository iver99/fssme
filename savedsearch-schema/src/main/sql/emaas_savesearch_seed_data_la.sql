Rem  drv: <migrate type="data_upgrade" version="13.1.0.0" />
Rem
Rem emaas_savesearch_seed_data_la.sql
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data_la.sql 
Rem
Rem    DESCRIPTION
Rem      Seed data for Log Analytics
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    jerrusse       08/21/14 - Created for Log Analytics 
Rem		 sdhamdhe				03/25/15 - Removed OOB searches for all of LA as requested by Jerry


DEFINE TENANT_ID = '&1'

-- *********************** FOLDERS ********************************
-- folder id between 200 - 299 are reserved for Log Analytics

-- DATABASE 
Insert into EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
values (201,'Database',2,'Databases',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');

Insert into EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
values (206,'Alerts',201,'Searches based on database alert logs',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');


-- MIDDLEWARE
Insert into EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
values (202,'Middleware',2,'Middleware',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');

Insert into EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
values (207,'Access',202,'Searches based on HTTP request access logs',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');

Insert into EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
values (208,'Server',202,'Searches based on operational middleware server logs',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');



-- HOST
Insert into EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
values (203,'Servers, Storage and Network',2,'Servers, Storage and Network',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');

Insert into EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
values (209,'Security',203,'Searches based on logs that capture host-level security',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',
SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');

Insert into EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
values (210,'Lifecycle',203,'Searches based on logs that capture host lifecycle, such as package managers',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',
SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');

-- ENTERPRISE MANAGER
Insert into EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
values (205,'Enterprise Manager',2,'Entprise Manager',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');

Insert into EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
values (211,'Servers',205,'Searches based on Enterprise Manager server logs',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');

Insert into EMS_ANALYTICS_FOLDERS (FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
values (212,'Agents',205,'Searches based on Enterprise Manager agent logs',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');

