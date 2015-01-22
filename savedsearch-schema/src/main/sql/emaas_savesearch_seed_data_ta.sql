Rem  drv: <migrate type="data_upgrade" version="13.1.0.0" />
Rem
Rem emaas_savesearch_seed_data_ta.sql
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data_ta.sql 
Rem
Rem    DESCRIPTION
Rem      Seed data for Target Analytics
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    WKEICHER    09/30/14 - Update TA OOB Searches
Rem    miayu       07/02/14 - Created for Target Analytics
Rem



Insert into EMS_ANALYTICS_FOLDERS 
(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) values (301,'Databases',4,'Databases',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,l_tenantid);
Insert into EMS_ANALYTICS_FOLDERS 
(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) values (302,'Middleware',4,'Middleware',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,l_tenantid);
Insert into EMS_ANALYTICS_FOLDERS 
(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) values (300,'Servers, Storage and Network',4,'Servers, Storage and Network',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC
(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,l_tenantid);
Insert into EMS_ANALYTICS_FOLDERS 
(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,
TENANT_ID) values (303,'Applications',4,'Applications',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,null,null,null,1,null,0,l_tenantid);


-- search id 3000 - 3999 are reserved for TA searches
Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3000,'WebLogic Servers with a specific patch','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC
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
{"value":"999999"}}}}}}]}},"dataType":"string","displayName":"Patch ID","displayDetails":"installed_at (Oracle Home) > Patches installed in Oracle Home"}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3001,'WebLogic Servers with non-standard Maximum Heap Size','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC
(SYSTIMESTAMP),'ORACLE',null,302,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":[{"jsonConstructor":"TargetStatusSearchCriterion","data":
{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":
{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle 
WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"weblogic_j2eeserver_WeblogicResourceconfig_maxheap","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":
{"type":"lt","values":{"jsonConstructor":"CriterionValue","data":{"value":1024}}}}]}},"dataType":"number","displayName":"Maximum Heap Size (MB)","displayDetails":"Resource 
Usage","targetType":"weblogic_j2eeserver","mcName":"maxheap","mgName":"WeblogicResourceconfig","mgDisplayName":"Resource Usage","isKey":0,"groupKeyColumns":[]}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3002,'WebLogic Servers with non-standard Starting Heap Size','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC
(SYSTIMESTAMP),'ORACLE',null,302,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":[{"jsonConstructor":"TargetStatusSearchCriterion","data":
{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":
{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle 
WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"weblogic_j2eeserver_WeblogicResourceconfig_startheap","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":
{"type":"lt","values":{"jsonConstructor":"CriterionValue","data":{"value":256}}}}]}},"dataType":"number","displayName":"Starting Heap Size (MB)","displayDetails":"Resource 
Usage","targetType":"weblogic_j2eeserver","mcName":"startheap","mgName":"WeblogicResourceconfig","mgDisplayName":"Resource Usage","isKey":0,"groupKeyColumns":[]}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3003,'WebLogic Servers with non-standard Admin Port','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC
(SYSTIMESTAMP),'ORACLE',null,302,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":[{"jsonConstructor":"TargetStatusSearchCriterion","data":
{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":
{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle 
WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"weblogic_j2eeserver_WeblogicServer_administrationport","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":
{"type":"lt","values":{"jsonConstructor":"CriterionValue","data":{"value":9002}}}},{"jsonConstructor":"CriterionExpression","data":{"type":"gt","values":{"jsonConstructor":"CriterionValue","data":
{"value":9002}}}}]}},"dataType":"number","displayName":"Administration Port","displayDetails":"Server 
Information","targetType":"weblogic_j2eeserver","mcName":"administrationport","mgName":"WeblogicServer","mgDisplayName":"Server Information","isKey":0,"groupKeyColumns":[]}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3004,'WebLogic Servers with non-standard SSL Listen Port','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC
(SYSTIMESTAMP),'ORACLE',null,302,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":[{"jsonConstructor":"TargetStatusSearchCriterion","data":
{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":
{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle 
WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"weblogic_j2eeserver_WeblogicServer_ssllistenport","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":
{"type":"lt","values":{"jsonConstructor":"CriterionValue","data":{"value":7000}}}},{"jsonConstructor":"CriterionExpression","data":{"type":"gt","values":{"jsonConstructor":"CriterionValue","data":
{"value":10000}}}}]}},"dataType":"number","displayName":"SSL Listen Port","displayDetails":"Server 
Information","targetType":"weblogic_j2eeserver","mcName":"ssllistenport","mgName":"WeblogicServer","mgDisplayName":"Server Information","isKey":0,"groupKeyColumns":[]}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3005,'WebLogic Servers with non-standard Listen Port','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC
(SYSTIMESTAMP),'ORACLE',null,302,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":[{"jsonConstructor":"TargetStatusSearchCriterion","data":
{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":
{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle 
WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"weblogic_j2eeserver_WeblogicServer_listenport","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":
{"type":"lt","values":{"jsonConstructor":"CriterionValue","data":{"value":7000}}}},{"jsonConstructor":"CriterionExpression","data":{"type":"gt","values":{"jsonConstructor":"CriterionValue","data":
{"value":10000}}}}]}},"dataType":"number","displayName":"Listen Port","displayDetails":"Server 
Information","targetType":"weblogic_j2eeserver","mcName":"listenport","mgName":"WeblogicServer","mgDisplayName":"Server Information","isKey":0,"groupKeyColumns":[]}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3006,'Oracle HTTP Servers with a specific patch','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC
(SYSTIMESTAMP),'ORACLE',null,302,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":[{"jsonConstructor":"TargetStatusSearchCriterion","data":
{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":
{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"oracle_apache","metadata":{"category":false},"displayValue":"Oracle HTTP 
Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},{"jsonConstructor":"AssociationSearchCriterion","data":{"assoc":{"jsonConstructor":"Association","data":
{"sourceTargetType":"oracle_apache","destinationTargetType":"oracle_home","associationType":"installed_at","assocDisplayName":"installed_at","inverseAssocType":"install_home_for","id":"installed_at
(oracle_apache,oracle_home)"}},"wrappedCriterion":{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_home_LlInvPatches_id","_value":{"jsonConstructor":"CriterionExpression","data":
{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":{"type":"any","values":{"jsonConstructor":"CriterionExpression","data":{"type":"eq","values":
{"jsonConstructor":"CriterionValue","data":{"value":"999999"}}}}}}]}},"dataType":"string","displayName":"Patch ID","displayDetails":"Patches installed in Oracle 
Home","targetType":"oracle_home","mcName":"id","mgName":"LlInvPatches","mgDisplayName":"Patches installed in Oracle Home","isKey":1,"groupKeyColumns":["lang","upi","id"]}},"id":"installed_at
(oracle_apache,oracle_home)_oracle_home_LlInvPatches_id","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":
{"type":"any","values":{"jsonConstructor":"CriterionExpression","data":{"type":"eq","values":{"jsonConstructor":"CriterionValue","data":
{"value":"999999"}}}}}}]}},"dataType":"string","displayName":"Patch ID","displayDetails":"installed_at (Oracle Home) > Patches installed in Oracle Home"}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3007,'Databases with a specific patch','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,301,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":
[{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},
{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":
{"value":"oracle_database","metadata":{"category":false},"displayValue":"Database Instance"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},
{"jsonConstructor":"AssociationSearchCriterion","data":{"assoc":{"jsonConstructor":"Association","data":
{"sourceTargetType":"oracle_database","destinationTargetType":"oracle_home","associationType":"installed_at","assocDisplayName":"installed_at","inverseAssocType":"install_home_for","id":"installed_at
(oracle_database,oracle_home)"}},"wrappedCriterion":{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_home_LlInvPatches_id","_value":{"jsonConstructor":"CriterionExpression","data":
{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":{"type":"any","values":{"jsonConstructor":"CriterionExpression","data":{"type":"eq","values":
{"jsonConstructor":"CriterionValue","data":{"value":"999999"}}}}}}]}},"dataType":"string","displayName":"Patch ID","displayDetails":"Patches installed in Oracle 
Home","targetType":"oracle_home","mcName":"id","mgName":"LlInvPatches","mgDisplayName":"Patches installed in Oracle Home","isKey":1,"groupKeyColumns":["lang","upi","id"]}},"id":"installed_at
(oracle_database,oracle_home)_oracle_home_LlInvPatches_id","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":
{"type":"any","values":{"jsonConstructor":"CriterionExpression","data":{"type":"eq","values":{"jsonConstructor":"CriterionValue","data":
{"value":"999999"}}}}}}]}},"dataType":"string","displayName":"Patch ID","displayDetails":"installed_at (Oracle Home) > Patches installed in Oracle Home"}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3008,'Databases with Autoextend ON and size greater than 30GB','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC
(SYSTIMESTAMP),'ORACLE',null,301,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":[{"jsonConstructor":"TargetStatusSearchCriterion","data":
{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":
{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"oracle_database","metadata":{"category":false},"displayValue":"Database 
Instance"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"oracle_database_DbDatafiles_autoextensible","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":
{"type":"any","values":{"jsonConstructor":"CriterionExpression","data":{"type":"eq","values":{"jsonConstructor":"CriterionValue","data":
{"value":"YES"}}}}}}]}},"dataType":"string","displayName":"Autoextensible","displayDetails":"Datafiles","targetType":"oracle_database","mcName":"autoextensible","mgName":"DbDatafiles","mgDisplayName":"D
atafiles","isKey":0,"groupKeyColumns":["fileName","tablespaceName","fileName","tablespaceName","fileName","tablespaceName"]}},{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"oracle_database_DbDatafiles_fileSize","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":{"type":"any","values":
{"jsonConstructor":"CriterionExpression","data":{"type":"gt","values":{"jsonConstructor":"CriterionValue","data":
{"value":32212254720}}}}}}]}},"dataType":"number","displayName":"Size","displayDetails":"Datafiles","targetType":"oracle_database","mcName":"fileSize","mgName":"DbDatafiles","mgDisplayName":"Datafiles",
"isKey":0,"groupKeyColumns":["fileName","tablespaceName","fileName","tablespaceName","fileName","tablespaceName"]}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3009,'Fusion Instances missing a given patch','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC
(SYSTIMESTAMP),'ORACLE',null,303,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":
{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"fusion_apps_instance","metadata":{"category":false},"displayValue":"Fusion 
Instance"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},{"jsonConstructor":"TargetStatusSearchCriterion","data":
{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},{"jsonConstructor":"AssociationSearchCriterion","data":{"assoc":
{"jsonConstructor":"Association","data":
{"sourceTargetType":"fusion_apps_instance","destinationTargetType":"oracle_home","associationType":"installed_at","assocDisplayName":"installed_at","inverseAssocType":"install_home_for","id":"installed_
at(fusion_apps_instance,oracle_home)"}},"wrappedCriterion":{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_home_LlInvPatches_id","_value":
{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":{"type":"all","values":{"jsonConstructor":"CriterionExpression","data":
{"type":"not","values":{"jsonConstructor":"CriterionExpression","data":{"type":"eq","values":{"jsonConstructor":"CriterionValue","data":
{"value":"999999"}}}}}}}}]}},"dataType":"string","displayName":"Patch ID","displayDetails":"Patches installed in Oracle 
Home","targetType":"oracle_home","mcName":"id","mgName":"LlInvPatches","mgDisplayName":"Patches installed in Oracle Home","isKey":1,"groupKeyColumns":["lang","upi","id"]}},"id":"installed_at
(fusion_apps_instance,oracle_home)_oracle_home_LlInvPatches_id","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":
{"type":"all","values":{"jsonConstructor":"CriterionExpression","data":{"type":"not","values":{"jsonConstructor":"CriterionExpression","data":{"type":"eq","values":
{"jsonConstructor":"CriterionValue","data":{"value":"999999"}}}}}}}}]}},"dataType":"string","displayName":"Patch ID","displayDetails":"installed_at (Oracle Home) > Patches installed in Oracle 
Home"}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3010,'Database Instances Overview','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,301,2,null,null,null,null,1,null,0,null, '{"version":0.5,"criteria":
[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":
{"value":"oracle_database","metadata":{"category":false},"displayValue":"Database Instance"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},
{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}}'||
',{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"oracle_database_ServerAdaptiveThresholdMetric_instanceThroughputAvgActiveSessions","_value":null,"dataType":"number","displayName":"Average Active Sessions","displayDetails":"Server Adaptive 
Threshold Metric","targetType":"oracle_database","mcName":"instanceThroughputAvgActiveSessions","mgName":"ServerAdaptiveThresholdMetric","mgDisplayName":"Server Adaptive Threshold 
Metric","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"oracle_database_InstanceThroughput_avgActiveSessions","_value":null,"dataType":"number","displayName":"Average Active 
Sessions","displayDetails":"Throughput","targetType":"oracle_database","mcName":"avgActiveSessions","mgName":"InstanceThroughput","mgDisplayName":"Throughput","isKey":0,"groupKeyColumns":
[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_database_WaitBottlenecks_userWaitTimePct","_value":null,"dataType":"number","displayName":"Wait Time 
(%)","displayDetails":"Wait Bottlenecks","targetType":"oracle_database","mcName":"userWaitTimePct","mgName":"WaitBottlenecks","mgDisplayName":"Wait Bottlenecks","isKey":0,"groupKeyColumns":
[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_database_WaitBottlenecks_userCpuTimeCnt","_value":null,"dataType":"number","displayName":"Active Sessions Using 
CPU","displayDetails":"Wait Bottlenecks","targetType":"oracle_database","mcName":"userCpuTimeCnt","mgName":"WaitBottlenecks","mgDisplayName":"Wait Bottlenecks","isKey":0,"groupKeyColumns":
[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_database_WaitBottlenecks_userioWaitCnt","_value":null,"dataType":"number","displayName":"Active Sessions Waiting: 
I/O","displayDetails":"Wait Bottlenecks","targetType":"oracle_database","mcName":"userioWaitCnt","mgName":"WaitBottlenecks","mgDisplayName":"Wait Bottlenecks","isKey":0,"groupKeyColumns":
[],"isConfig":0}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3011,'Listeners Overview','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,301,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":
[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":
{"value":"oracle_listener","metadata":{"category":false},"displayValue":"Listener"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},
{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_listener_Response_tnsPing","_value":null,"dataType":"number","displayName":"Response Time 
(msec)","displayDetails":"Response","targetType":"oracle_listener","mcName":"tnsPing","mgName":"Response","mgDisplayName":"Response","isKey":0,"groupKeyColumns":[],"isConfig":0}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_listener_Load_estConns","_value":null,"dataType":"number","displayName":"Connections 
Established","displayDetails":"Load","targetType":"oracle_listener","mcName":"estConns","mgName":"Load","mgDisplayName":"Load","isKey":0,"groupKeyColumns":[],"isConfig":0}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_listener_Load_refConns","_value":null,"dataType":"number","displayName":"Connections Refused 
(per","displayDetails":"Load","targetType":"oracle_listener","mcName":"refConns","mgName":"Load","mgDisplayName":"Load","isKey":0,"groupKeyColumns":[],"isConfig":0}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3012,'OAM Servers Overview','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,302,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":
[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":
{"value":"oracle_oam","metadata":{"category":false},"displayValue":"Oracle Access Management Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},
{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}}' ||
',{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_oam_Authentications_authnRequests.persec","_value":null,"dataType":"number","displayName":"Authentication Requests per 
second","displayDetails":"Authentication","targetType":"oracle_oam","mcName":"authnRequests.persec","mgName":"Authentications","mgDisplayName":"Authentication","isKey":0,"groupKeyColumns":
[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_oam_Authentications_authnSuccessFailure.rate","_value":null,"dataType":"number","displayName":"Authentication 
Success Rate 
(%)","displayDetails":"Authentication","targetType":"oracle_oam","mcName":"authnSuccessFailure.rate","mgName":"Authentications","mgDisplayName":"Authentication","isKey":0,"groupKeyColumns":
[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_oam_Authentications_authnLatency","_value":null,"dataType":"number","displayName":"Average Authentication Latency 
(ms)","displayDetails":"Authentication","targetType":"oracle_oam","mcName":"authnLatency","mgName":"Authentications","mgDisplayName":"Authentication","isKey":0,"groupKeyColumns":[],"isConfig":0}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_oam_Authorizations_authzRequests.persec","_value":null,"dataType":"number","displayName":"Authorization Requests per 
second","displayDetails":"Authorizations","targetType":"oracle_oam","mcName":"authzRequests.persec","mgName":"Authorizations","mgDisplayName":"Authorizations","isKey":0,"groupKeyColumns":
[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_oam_Authorizations_authzSuccessFailure.rate","_value":null,"dataType":"number","displayName":"Authorization Success 
Rate (%)","displayDetails":"Authorizations","targetType":"oracle_oam","mcName":"authzSuccessFailure.rate","mgName":"Authorizations","mgDisplayName":"Authorizations","isKey":0,"groupKeyColumns":
[],"isConfig":0}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3013,'OIM Overview','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,302,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":
[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":
{"value":"oracle_oim","metadata":{"category":false},"displayValue":"Oracle Identity Manager"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},
{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_oim_Adapters_completedExec","_value":null,"dataType":"number","displayName":"Completed Adapter 
Executions","displayDetails":"Adapters","targetType":"oracle_oim","mcName":"completedExec","mgName":"Adapters","mgDisplayName":"Adapters","isKey":0,"groupKeyColumns":["adapterName"],"isConfig":0}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_oim_Adapters_avgProcess","_value":null,"dataType":"number","displayName":"Average Adapter Execution Time 
(ms)","displayDetails":"Adapters","targetType":"oracle_oim","mcName":"avgProcess","mgName":"Adapters","mgDisplayName":"Adapters","isKey":0,"groupKeyColumns":["adapterName"],"isConfig":0}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_oim_EventsHandler_completedExec","_value":null,"dataType":"number","displayName":"Completed Events 
Executions","displayDetails":"Events Handler","targetType":"oracle_oim","mcName":"completedExec","mgName":"EventsHandler","mgDisplayName":"Events Handler","isKey":0,"groupKeyColumns":
["eventName"],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_oim_EventsHandler_avgProcess","_value":null,"dataType":"number","displayName":"Average Events Execution 
Time (ms)","displayDetails":"Events Handler","targetType":"oracle_oim","mcName":"avgProcess","mgName":"EventsHandler","mgDisplayName":"Events Handler","isKey":0,"groupKeyColumns":
["eventName"],"isConfig":0}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3014,'OIM Cluster Overview','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,302,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":
[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":
{"value":"oracle_oim_cluster","metadata":{"category":false},"displayValue":"Oracle Identity Manager Cluster"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},
{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_oim_cluster_SelfSvcRequests_noOfCompSelfSvcReq","_value":null,"dataType":"number","displayName":"Completed Self Service Registration 
Requests","displayDetails":"Self Service Registration Requests","targetType":"oracle_oim_cluster","mcName":"noOfCompSelfSvcReq","mgName":"SelfSvcRequests","mgDisplayName":"Self Service Registration 
Requests","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"oracle_oim_cluster_SelfSvcRequests_noOfFailedSelfSvcReq","_value":null,"dataType":"number","displayName":"Failed Self Service Registration Requests","displayDetails":"Self Service Registration 
Requests","targetType":"oracle_oim_cluster","mcName":"noOfFailedSelfSvcReq","mgName":"SelfSvcRequests","mgDisplayName":"Self Service Registration Requests","isKey":0,"groupKeyColumns":
[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_oim_cluster_SelfSvcRequests_noOfPendingSelfSvcReq","_value":null,"dataType":"number","displayName":"Pending Self 
Service Registration Requests","displayDetails":"Self Service Registration Requests","targetType":"oracle_oim_cluster","mcName":"noOfPendingSelfSvcReq","mgName":"SelfSvcRequests","mgDisplayName":"Self 
Service Registration Requests","isKey":0,"groupKeyColumns":[],"isConfig":0}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3015,'WebLogic Server Overview','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,302,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":
[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":
{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},
{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}}' ||
',{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_ServerWorkManager_wmStuck.active","_value":null,"dataType":"number","displayName":"Work Manager Stuck 
Threads","displayDetails":"Server Work Manager Metrics","targetType":"weblogic_j2eeserver","mcName":"wmStuck.active","mgName":"ServerWorkManager","mgDisplayName":"Server Work Manager 
Metrics","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"weblogic_j2eeserver_ServerServletJsp_service.Throughput","_value":null,"dataType":"number","displayName":"Requests (per minute)","displayDetails":"Server Servlet/JSP 
Metrics","targetType":"weblogic_j2eeserver","mcName":"service.Throughput","mgName":"ServerServletJsp","mgDisplayName":"Server Servlet/JSP Metrics","isKey":0,"groupKeyColumns":[],"isConfig":0}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_Jvm_heapUsedPercentage.value","_value":null,"dataType":"number","displayName":"Heap Usage (%)","displayDetails":"JVM 
Metrics","targetType":"weblogic_j2eeserver","mcName":"heapUsedPercentage.value","mgName":"Jvm","mgDisplayName":"JVM Metrics","isKey":0,"groupKeyColumns":[],"isConfig":0}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_Jvm_cpuUsage.percentage","_value":null,"dataType":"number","displayName":"CPU Usage (%)","displayDetails":"JVM 
Metrics","targetType":"weblogic_j2eeserver","mcName":"cpuUsage.percentage","mgName":"Jvm","mgDisplayName":"JVM Metrics","isKey":0,"groupKeyColumns":[],"isConfig":0}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_JvmGarbageCollectors_oldHeapPercentFreeAfterGc","_value":null,"dataType":"number","displayName":"Garbage Collector - Old 
Heap Percent Free after GC (%)","displayDetails":"JVM Garbage Collectors","targetType":"weblogic_j2eeserver","mcName":"oldHeapPercentFreeAfterGc","mgName":"JvmGarbageCollectors","mgDisplayName":"JVM 
Garbage Collectors","isKey":0,"groupKeyColumns":["name"],"isConfig":0}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3016,'OID Overview','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,302,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":
[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":
{"value":"oracle_ldap","metadata":{"category":false},"displayValue":"Oracle Internet Directory"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},
{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}}' ||
',{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_ldap_LDAPserverOpLatencyCC_bindOpLatency","_value":null,"dataType":"number","displayName":"Bind Operation Response 
Time","displayDetails":"LDAP Operation Response Time","targetType":"oracle_ldap","mcName":"bindOpLatency","mgName":"LDAPserverOpLatencyCC","mgDisplayName":"LDAP Operation Response 
Time","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"oracle_ldap_LDAPserverOpLatencyCC_compareOpLatency","_value":null,"dataType":"number","displayName":"Compare Operation Response Time","displayDetails":"LDAP Operation Response 
Time","targetType":"oracle_ldap","mcName":"compareOpLatency","mgName":"LDAPserverOpLatencyCC","mgDisplayName":"LDAP Operation Response Time","isKey":0,"groupKeyColumns":[],"isConfig":0}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_ldap_LDAPserverOpLatencyCC_mesgSrchOpLatency","_value":null,"dataType":"number","displayName":"Messaging Search Operation Response 
Time","displayDetails":"LDAP Operation Response Time","targetType":"oracle_ldap","mcName":"mesgSrchOpLatency","mgName":"LDAPserverOpLatencyCC","mgDisplayName":"LDAP Operation Response 
Time","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"oracle_ldap_LDAPserverResponseCC_lDAPserverResponse","_value":null,"dataType":"number","displayName":"Server Response (ms)","displayDetails":"LDAP 
Response","targetType":"oracle_ldap","mcName":"lDAPserverResponse","mgName":"LDAPserverResponseCC","mgDisplayName":"LDAP Response","isKey":0,"groupKeyColumns":[],"isConfig":0}}]}',0,l_tenantid);

Insert into EMS_ANALYTICS_SEARCH 
(SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID
,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID) 
values (3017,'Host Overview','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,300,2,null,null,null,null,1,null,0,null,'{"version":0.5,"criteria":
[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":
{"value":"host","metadata":{"category":false},"displayValue":"Host"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null}},{"jsonConstructor":"TargetStatusSearchCriterion","data":
{"id":"target_status","_value":null,"dataType":"number","displayName":"Target Status","displayDetails":null}},{"jsonConstructor":"MetricGroupSearchCriterion","data":
{"id":"host_Load_cpuLoad","_value":null,"dataType":"number","displayName":"Run Queue Length (5 minute average,per 
core)","displayDetails":"Load","targetType":"host","mcName":"cpuLoad","mgName":"Load","mgDisplayName":"Load","isKey":0,"groupKeyColumns":[],"isConfig":0}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Load_noOfProcs","_value":null,"dataType":"number","displayName":"Total 
Processes","displayDetails":"Load","targetType":"host","mcName":"noOfProcs","mgName":"Load","mgDisplayName":"Load","isKey":0,"groupKeyColumns":[],"isConfig":0}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Load_logicMemfreePct","_value":null,"dataType":"number","displayName":"Logical Free Memory 
(%)","displayDetails":"Load","targetType":"host","mcName":"logicMemfreePct","mgName":"Load","mgDisplayName":"Load","isKey":0,"groupKeyColumns":[],"isConfig":0}},
{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_TotalDiskUsage_totused","_value":null,"dataType":"number","displayName":"Total Disk Space Utilized (across all local filesystems in 
MB)","displayDetails":"Total Disk Usage","targetType":"host","mcName":"totused","mgName":"TotalDiskUsage","mgDisplayName":"Total Disk Usage","isKey":0,"groupKeyColumns":
[],"isConfig":0}}]}',0,l_tenantid);

insert into EMS_ANALYTICS_LAST_ACCESS(OBJECT_ID,ACCESSED_BY,OBJECT_TYPE,ACCESS_DATE,TENANT_ID) 
select SEARCH_ID,'SYSMAN',2,SYS_EXTRACT_UTC(SYSTIMESTAMP),l_tenantid from EMS_ANALYTICS_SEARCH where search_id>=3000 and search_id<=3999 and TENANT_ID =l_tenantid;
