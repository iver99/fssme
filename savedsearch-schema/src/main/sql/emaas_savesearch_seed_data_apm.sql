Rem  drv: <migrate type="data_upgrade" version="13.1.0.0" />
Rem
Rem emaas_savesearch_seed_data_apm.sql
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data_apm.sql 
Rem
Rem    DESCRIPTION
Rem      Seed data for Application Performance Monitoring Cloud Service
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    evazhang  10/16/14 - Add OOB sub-folders for APMCS
Rem    miayu       10/11/14 - Created for APMCS
Rem

DEFINE TENANT_ID ="&1"

-- folder id 5 is reserved as root folder for APMCS
Insert into EMS_ANALYTICS_FOLDERS 
(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
                          values (5,'APMCS Searches',1,'Application Performance Monitoring Cloud Service Root Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC
(SYSTIMESTAMP),'ORACLE','ALL_APMCS_SEARCHES_NAME','EMANALYTICS','ALL_APMCS_SEARCHES_DESC','EMANALYTICS',1,'oracle.sysman.core',0,'&TENANT_ID');
-- folder id between 501 - 599 are reserved for APMCS
Insert into EMS_ANALYTICS_FOLDERS 
(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
                         values (501,'Requests',5,'Request Types Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');
Insert into EMS_ANALYTICS_FOLDERS 
(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
                         values (502,'Pages',5,'Page Types Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');
Insert into EMS_ANALYTICS_FOLDERS 
(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
                         values (503,'Instances',5,'Instances Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');
Insert into EMS_ANALYTICS_FOLDERS 
(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
                         values (504,'Agents',5,'Agents Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');
Insert into EMS_ANALYTICS_FOLDERS 
(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
                         values (505,'App Servers',5,'App Servers Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');
Insert into EMS_ANALYTICS_FOLDERS 
(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
                         values (506,'Ajax Calls',5,'Ajax Calls Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');
Insert into EMS_ANALYTICS_FOLDERS 
(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) 
                         values (507,'JDBC Calls',5,'JDBC Calls Folder',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');

-- category id 4 is reserved  for APMCS
Insert into EMS_ANALYTICS_CATEGORY (CATEGORY_ID,NAME,DESCRIPTION,OWNER,CREATION_DATE,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,EM_PLUGIN_ID,DEFAULT_FOLDER_ID,TENANT_ID) 
                           values (4,'APMCS','Search Category for Application Performance Monitoring Cloud Service','ORACLE',SYS_EXTRACT_UTC
(SYSTIMESTAMP),'APMCS_CATEGORY_NAME','EMANALYTICS','APMCS_CATEGORY_DESC','EMANALYTICS','oracle.sysman.core',5,'&TENANT_ID');
Insert into EMS_ANALYTICS_CATEGORY_PARAMS (CATEGORY_ID,NAME,PARAM_VALUE,TENANT_ID) 
                                  values (4,'CATEGORY_PARAM_VIEW_TASKFLOW','TBD','&TENANT_ID');                                  


/*
-- folder id between 500 - 599 are reserved for APMCS, below is an example to creater OOB folder
--Insert into EMS_ANALYTICS_FOLDERS 
(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) values (301,'Databases',4,'Databases',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,'&TENANT_ID');


-- search id 5000 - 5999 are reserved for APMCS searches, below is an example to creater OOB search
--Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN)
--values (3000,'WebLogic Servers with a specific patch','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC
(SYSTIMESTAMP),'ORACLE',null,302,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":[{"jsonConstructor":"TargetStatusSearchCriterion","data":
{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":
{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle 
WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},{"jsonConstructor":"AssociationSearchCriterion","data":{"assoc":{"jsonConstructor":"Association","data":
{"sourceTargetType":"weblogic_j2eeserver","destinationTargetType":"oracle_home","associationType":"installed_at","assocDisplayName":"installed_at","inverseAssocType":"install_home_for","id":"installed_a
t(weblogic_j2eeserver,oracle_home)"}},"wrappedCriterion":{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_home_LlInvPatches_id","_value":
{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":{"type":"any","values":{"jsonConstructor":"CriterionExpression","data":
{"type":"eq","values":{"jsonConstructor":"CriterionValue","data":{"value":"999999"}}}}}}]}},"dataType":"string","displayName":"Patch ID","displayDetails":"Patches installed in Oracle 
Home","targetType":"oracle_home","mcName":"id","mgName":"LlInvPatches","mgDisplayName":"Patches installed in Oracle Home","isKey":1,"groupKeyColumns":["lang","upi","id"]}},"id":"installed_at
(weblogic_j2eeserver,oracle_home)_oracle_home_LlInvPatches_id","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":
{"type":"any","values":{"jsonConstructor":"CriterionExpression","data":{"type":"eq","values":{"jsonConstructor":"CriterionValue","data":
{"value":"999999"}}}}}}]}},"dataType":"string","displayName":"Patch ID","displayDetails":"installed_at (Oracle Home) > Patches installed in Oracle Home"}}]}',0,'&TENANT_ID');
*/

insert into EMS_ANALYTICS_LAST_ACCESS(OBJECT_ID,ACCESSED_BY,OBJECT_TYPE,ACCESS_DATE,TENANT_ID)
select SEARCH_ID,'SYSMAN',2,SYS_EXTRACT_UTC(SYSTIMESTAMP),'&TENANT_ID' from EMS_ANALYTICS_SEARCH where search_id>=5000 and search_id<=5999 and TENANT_ID ='&TENANT_ID';

