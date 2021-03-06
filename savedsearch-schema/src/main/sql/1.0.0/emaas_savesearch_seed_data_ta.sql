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
Rem    miayu       04/21/15 - Only OOB SEARCHES are expected to be in this file
Rem    WKEICHER    09/30/14 - Update TA OOB Searches
Rem    miayu       07/02/14 - Created for Target Analytics
Rem

DEFINE TENANT_ID ='&1'

DECLARE

screenshot_3024 CLOB:=
'data:image/png;base64,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'||
'+vnD596r...';

screenshot_3025 CLOB:=
'data:image/png;base64,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'||
'p1otkKeQ...';

screenshot_3026 CLOB:=
'data:image/png;base64,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'||
'+s1UEe5s...';

screenshot_3027 CLOB:=
'data:image/png;base64,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'||
'GIAu3vCD...';

screenshot_3028 CLOB:=
'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABAoAAAMkCAYAAAAxpcLkAAAgAElEQVR4XuydB3gcxdnH/+q9S7ZV3GTL4F4AG2xD6J1QA4ReQvoXQkIJCSQhhHyEEgIJNclHCqEEQkswvSRUg3uVm2S5SrJ67/qed8yJs3wn7+r29nZn//c8eZLIuzPz/t65ud3fzsxG9ff394MfEiABEiABEiABEiABEiABEiABEiABEgAQRVHAfkACJEACJEACJEACJEACJEACJEACJOAjQFHAvkACJEACJEACJEACJEACJEACJEACJDBAgKKAnYEESIAESIAESIAESIAESIAESIAESICigH2ABEiABEiABEiABEiABEiABEiABEhgfwKcUcBeQQIkQAIkQAIkQAIkQAIkQAIkQAIkwBkF7AMkQAIkQAIkQAIkQAIkQAIkQAIkQAKcUcA+QAIkQAIkQAIkQAIkQAIkQAIkQAIkMAQBLj1g9yABEiABEiABEiABEiABEiABEiABEhggQFHAzkACJEACJEACJEACJEACJEACJEACJEBRwD5AAiRAAiRAAiRAAiRAAiRAAiRAAiSwPwHOKGCvIAESIAESIAESIAESIAESIAESIAES4IwC9gESIAESIAESIAESIAESIAESIAESIAHOKGAfIAESIAESIAESIAESIAESIAESIAESGIJASEsPOjq7CJcESIAESMCjBBIT4gNGzt8Gj3YIhk0CLiDAccsFSWITI0Yg2PcjYg1ixRElEJIo2L67GuxQEc0fKycBEiCBiBAQGTA6f0TAuvnbEJGUsFISIIEDEOC4xS5CAsEJDPX9IDdvEghJFGzcuiPohaI3cTJqEiABEvAGAZEBk8YVBQyWvw3e6AOMkgTcRoDjltsyxvbaSWCo74ed7WBdziEQsigIdqHonBDZEhIgARIgAasJiAwYShTwt8Fq4iyPBEggVAIct0IlyPN1JjDU90PnuBlb'||
'cAIUBewdJEACJEACpgnwgts0Mp5AAiQQYQIctyKcAFbvaAIUBY5OT0QaZ6koaGlpwebNm1UghYWFyMvLU/+7vr4eFRUV6n9PnDgRcXFx2LJlC0aOHImcnJx9AveVIcelpqYG/behyogISVZKAiRAAh4iYOaCu6enB2VlZWhraxsglJCQgFGjRiEzMxNRUVEHJCfnyn9yc3OHPLazszPo78sBK+EBJEACWhMwM275QLS2tmL79u3o6OhAcnKyur5NSUlxLaeh4jE6zro2eADB7jN8v1PR0dEYO3asulfx/U3uRwoKCg4Ydrj5hfv3jaLggCn23AGWioJ169bh5z//uYJ4wQUX4JxzzlH/+/XXX8f//d//qf8t/z5ixAg888wzWLhwIWbOnLkPdF8ZctyUKVOC/ltGRgZ+9atf4dxzz8Wxxx7rucQxYBIgARKIJAEzF9zNzc2488470djYOHCxJRdho0ePxoknnojs7OwhQ5GLtQ8//BBSzumnnz7ksTt37uRvQyQ7BusmAQcTMDNuSRjd3d3473//i8WLF6Ovr0/dOH7pS1/CUUcdhZiYGAdHGrhpQ8XT399veJx1XeB+DQ52n+H7nZKb8e985zsYP368+s2R366pU6fioosusux3arj8wv37RlEw3Mzoe56louDTTz/FJZdcgqSkJJx88slKCshA+utf/xovv/yyukh84okncNBBB+HNN99UkqCkpAQNDQ3YtWsXxOLJl+Bb3/qWOm7u3LlB/01mInzta19TX+bzzjsPPkPa1dUFkQhifKXuHTt2qMFdLkh9ZlCMsPx/OdY3s0GOlTbI38QUy/lynM/epaWlKQsp/y3/JtZQDLN/fbGxsfr2FEZGAiRAAn4EzFxwy6yyr3/96xg3bhwuvvhiyAWpjPUvvPACzj77bJxyyilqvPb9FviPw4mJidiwYQN++9vfqtkEUo6M301NTQHHbBnT5bfhqquuwhFHHDEwC0FmL8hvTKA6ZK'||
'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'||
'nQ22+/Hb...';

ta_criteria_3024 CLOB:=
'{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"oracle_database","metadata":{"category":false},"displayValue":"Database Instance"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"string","displayName":"Target Status","displayDetails":null,"role":"column","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_database_InstanceEfficiency_cpuTimePct","_value":null,"dataType":"number","displayName":"Database CPU Time (%)","displayDetails":"Efficiency","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart'||
'"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":true},"targetType":"oracle_database","unitStr":null,"mcName":"cpuTimePct","mgName":"InstanceEfficiency","mgDisplayName":"Efficiency","isKey":0,"groupKeyColumns":[],"isConfig":0,"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_database_InstanceThroughput_iombsPs","_value":null,"dataType":"number","displayName":"I/O Megabytes (per second)","displayDetails":"Disk","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"oracle_database","unitStr":null,"mcName":"iombsPs","mgName":"InstanceThroughput","mgDisplayName":"Disk","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_database_MemoryUsage_totalMemory","_value":null,"dataType":"number","displayN'||
'ame":"Total Memory Usage (MB)","displayDetails":"Memory","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"oracle_database","unitStr":null,"mcName":"totalMemory","mgName":"MemoryUsage","mgDisplayName":"Memory","isKey":0,"groupKeyColumns":[],"isConfig":0}}]}';

ta_criteria_3025 CLOB:=
'{"version":0.5,"criteria":[{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"string","displayName":"Target Status","displayDetails":null,"role":"column","func":null,"UIProperties":{"useForSort":false,"ignoreCase":true}}},{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"host","metadata":{"category":false},"displayValue":"Host"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Load_cpuUtil","_value":null,"dataType":"number","displayName":"CPU Utilization (%)","displayDetails":"CPU","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"drop'||
'DownValue":"cigar","useForSort":true},"targetType":"host","unitStr":"%","mcName":"cpuUtil","mgName":"Load","mgDisplayName":"CPU","isKey":0,"groupKeyColumns":[],"isConfig":0,"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Load_memUsedPct","_value":null,"dataType":"number","displayName":"Memory Utilization (%)","displayDetails":"Memory","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"host","unitStr":"%","mcName":"memUsedPct","mgName":"Load","mgDisplayName":"Memory","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_DiskActivitySummary_totiosmade","_value":null,"dataType":"number","displayName":"Total Disk I/O made across all disks (per second)","displayDetails":"Disk Activity Summary","role":"column","func":null,"U'||
'IProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"host","unitStr":null,"mcName":"totiosmade","mgName":"DiskActivitySummary","mgDisplayName":"Disk Activity Summary","isKey":0,"groupKeyColumns":[],"isConfig":0}}]}';

ta_criteria_3026 CLOB:=
'{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"string","displayName":"Target Status","displayDetails":null,"role":"column","func":null,"UIProperties":{"useForSort":false,"ignoreCase":true}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_Jvm_cpuUsage.percentage","_value":null,"dataType":"number","displayName":"CPU Usage (%)","displayDetails":"Utilization ","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","'||
'label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":true},"targetType":"weblogic_j2eeserver","unitStr":"n/a","mcName":"cpuUsage.percentage","mgName":"Jvm","mgDisplayName":"Utilization ","isKey":0,"groupKeyColumns":[],"isConfig":0,"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_ServerModule_session.Active","_value":null,"dataType":"number","displayName":"Active Sessions","displayDetails":"Load ","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"weblogic_j2eeserver","unitStr":"n/a","mcName":"session.Active","mgName":"ServerModule","mgDisplayName":"Load ","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_Jvm_heapUsed.value","_value":null,"dataTyp'||
'e":"number","displayName":"Heap Usage (MB)","displayDetails":"Load ","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"weblogic_j2eeserver","unitStr":"n/a","mcName":"heapUsed.value","mgName":"Jvm","mgDisplayName":"Load ","isKey":0,"groupKeyColumns":[],"isConfig":0}}]}';

ta_criteria_3027 CLOB:=
'{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"j2ee_application","metadata":{"category":false},"displayValue":"Application Deployment"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"string","displayName":"Target Status","displayDetails":null,"role":"column","func":null,"UIProperties":{"useForSort":false,"ignoreCase":true}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"j2ee_application_DeploymentAppServletJsp_service.Throughput","_value":null,"dataType":"number","displayName":"Requests (per minute)","displayDetails":"Performance","role":"column","func":null,"UIProperties":{"dropDownOptions"'||
':[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":true},"targetType":"j2ee_application","unitStr":"n/a","mcName":"service.Throughput","mgName":"DeploymentAppServletJsp","mgDisplayName":"Performance","isKey":0,"groupKeyColumns":[],"isConfig":0,"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"j2ee_application_DeploymentAppServletJsp_service.Time","_value":null,"dataType":"number","displayName":"Request Processing Time (ms)","displayDetails":"Performance","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"j2ee_application","unitStr":"n/a","mcName":"service.Time","mgName":"DeploymentAppServletJsp","mgDisplayName":"Performance","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","dat'||
'a":{"id":"j2ee_application_DeploymentAppModule_session.Active","_value":null,"dataType":"number","displayName":"Active Sessions","displayDetails":"Performance","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"j2ee_application","unitStr":"n/a","mcName":"session.Active","mgName":"DeploymentAppModule","mgDisplayName":"Performance","isKey":0,"groupKeyColumns":[],"isConfig":0}}]}';
SEARCH_ID                             NUMBER         			;
NAME                                  VARCHAR2(64 BYTE)        ;
OWNER                                 VARCHAR2(64 BYTE)       ;
CREATION_DATE                         TIMESTAMP      ;
LAST_MODIFICATION_DATE                TIMESTAMP      ;
LAST_MODIFIED_BY                      VARCHAR2(64 BYTE)        ;
DESCRIPTION                           VARCHAR2(256 BYTE)       ;
FOLDER_ID                             NUMBER         ;
CATEGORY_ID                           NUMBER         ;
NAME_NLSID                            VARCHAR2(64 BYTE)        ;
NAME_SUBSYSTEM                        VARCHAR2(64 BYTE)        ;
DESCRIPTION_NLSID                     VARCHAR2(256 BYTE)        ;
DESCRIPTION_SUBSYSTEM                 VARCHAR2(64 BYTE)        ;
SYSTEM_SEARCH                         NUMBER         ;
EM_PLUGIN_ID                          VARCHAR2(64 BYTE)        ;
IS_LOCKED                             NUMBER         ;
METADATA_CLOB                         CLOB           ;
SEARCH_DISPLAY_STR                    CLOB           ;
UI_HIDDEN                             NUMBER         ;
TENANT_ID                             NUMBER         ;
IS_WIDGET                             NUMBER         ;

V_COUNT                               NUMBER         ;
DELETED                         NUMBER(38,0)                    ;
PARAM_ATTRIBUTES                VARCHAR2(1024 BYTE)             ;
PARAM_TYPE                      NUMBER(38,0)                    ;
PARAM_VALUE_STR                 VARCHAR2(1024 BYTE)             ;
PARAM_VALUE_CLOB                NCLOB                           ;
BEGIN
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3000;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data WebLogic Servers with their Patch IDs has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
-- base TA folder is 4
-- search id 3000 - 3999 are reserved for TA searches
SEARCH_ID                  :=              3000                                                                                        ;
NAME                       :=              'WebLogic Servers with their Patch IDs'                                                     ;
OWNER                      :=              'ORACLE'                                                                                    ;
CREATION_DATE              :=              SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                               ;
LAST_MODIFICATION_DATE     :=              SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                               ;
LAST_MODIFIED_BY           :=              'ORACLE'                                                                                    ;
DESCRIPTION                :=              'Shows the status, Target Version and list of Patch IDs for WebLogic Servers'               ;
FOLDER_ID                  :=              4                                                                                           ;
CATEGORY_ID                :=  			   2                                                                                           ;
NAME_NLSID                 :=              null                                                                                        ;
NAME_SUBSYSTEM             :=              null                                                                                        ;
DESCRIPTION_NLSID          :=              null                                                                                        ;
DESCRIPTION_SUBSYSTEM      :=              null                                                                                        ;
SYSTEM_SEARCH              :=              1                                                                                           ;
EM_PLUGIN_ID               :=              null                                                                                        ;
IS_LOCKED                  :=              0                                                                                           ;
METADATA_CLOB              :=              null                                                                                        ;
SEARCH_DISPLAY_STR         :=              ''                                                                                          ;
UI_HIDDEN                  :=              0                                                                                           ;
TENANT_ID                  :=              '&TENANT_ID'                                                                                ;
IS_WIDGET                  :=              1                                                                                           ;

Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
);


SEARCH_ID              := 3000                                                       ;
NAME                   := 'WIDGET_TEMPLATE'                                          ;
PARAM_ATTRIBUTES       := null                                                       ;
PARAM_TYPE             := 1                                                          ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/visualizationWidget.html'     ;
PARAM_VALUE_CLOB       := null                                                       ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3000                                                       ;
NAME                   := 'WIDGET_ICON'                                              ;
PARAM_ATTRIBUTES       := null                                                       ;
PARAM_TYPE             := 1                                                          ;
PARAM_VALUE_STR        := '/../images/func_horibargraph_24_ena.png'                  ;
PARAM_VALUE_CLOB       := null                                                       ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3000                                                       ;
NAME                   := 'WIDGET_VIEWMODEL'                                         ;
PARAM_ATTRIBUTES       := null                                                       ;
PARAM_TYPE             := 1                                                          ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/js/VisualizationWidget.js'    ;
PARAM_VALUE_CLOB       := null                                                       ;
TENANT_ID              := '&TENANT_ID'                                               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
                                                                                  
SEARCH_ID              :=3000                                                        ;
NAME                   :='TIME_PERIOD_KEY'                                           ;
PARAM_ATTRIBUTES       :=null                                                        ;
PARAM_TYPE             :=1                                                           ;
PARAM_VALUE_STR        :='24'                                                        ;
PARAM_VALUE_CLOB       :=null                                                        ;
TENANT_ID              :='&TENANT_ID'                                                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3000;
NAME                   := 'TA_CRITERIA';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 2;
PARAM_VALUE_STR        := null;
PARAM_VALUE_CLOB       := '{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"string","displayName":"Target Status","displayDetails":null,"role":"column","func":null,"UIProperties":{"useForSort":true,"ignoreCase":true},"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"PropertySearchCriterion","data":{"id":"property.orcl_gtp_target_version","_value":null,"dataType":"string","displayName":"Target Version","displayDetails":null,"role":"column","func":null,'||'"UIProperties":{"useForSort":false},"targetType":"weblogic_j2eeserver","propertyName":"orcl_gtp_target_version"}},{"jsonConstructor":"AssociationSearchCriterion","data":{"assoc":{"sourceTargetType":"weblogic_j2eeserver","destinationTargetType":"oracle_home","associationType":"installed_at","assocDisplayName":"installed_at","inverseAssocType":"install_home_for","id":"installed_at(weblogic_j2eeserver,oracle_home)"},"wrappedCriterion":{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_home_LlInvPatches_id","_value":null,"dataType":"string","displayName":"Patch ID","displayDetails":"Unclassified ","role":"column","func":null,"UIProperties":{},"targetType":"oracle_home","unitStr":"n/a","mcName":"id","mgName":"LlInvPatches","mgDisplayName":"Unclassified ","isKey":1,"groupKeyColumns":["id","lang","upi"],"isConfig":1}},"id":"installed_at(weblogic_j2eeserver,oracle_home)_oracle_home_LlInvPatches_id","_value":null,"dataType":"string","displayName":"Patch ID","displayDetails":"installed_at (Oracle Home) > Unclassified ","role":"column","func":null,"UIProperties":{"useForSort":false}}}]}';
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3000                     ;
NAME                   := 'PROVIDER_VERSION'       ;
PARAM_ATTRIBUTES       := null                     ;
PARAM_TYPE             := 1                        ;
PARAM_VALUE_STR        := '1.0'                    ;
PARAM_VALUE_CLOB       := null                     ;
TENANT_ID              := '&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3000                      ;
NAME            	   := 'WIDGET_KOC_NAME'         ;
PARAM_ATTRIBUTES       := null                      ;
PARAM_TYPE             := 1                         ;
PARAM_VALUE_STR        := 'emcta-visualization'     ;
PARAM_VALUE_CLOB       := null                      ;
TENANT_ID              := '&TENANT_ID'              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3000                      ;
NAME                   := 'VISUALIZATION_TYPE_KEY'  ;
PARAM_ATTRIBUTES       := null                      ;
PARAM_TYPE             := 1                         ;
PARAM_VALUE_STR        := 'TABLE'                   ;
PARAM_VALUE_CLOB       := null                      ;
TENANT_ID              := '&TENANT_ID'              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3000                       ;
NAME                   := 'PROVIDER_ASSET_ROOT'      ;
PARAM_ATTRIBUTES       := null                       ;
PARAM_TYPE             := 1                          ;
PARAM_VALUE_STR        := 'assetRoot'                ;
PARAM_VALUE_CLOB       := null                       ;
TENANT_ID              := '&TENANT_ID'               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3000                       ;
NAME                   := 'WIDGET_SOURCE'            ;
PARAM_ATTRIBUTES       := null                       ;
PARAM_TYPE             := 1                          ;
PARAM_VALUE_STR        := '1'                        ;
PARAM_VALUE_CLOB       := null                       ;
TENANT_ID              := '&TENANT_ID'               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3000                        ;
NAME                   := 'PROVIDER_NAME'             ;
PARAM_ATTRIBUTES       := null                        ;
PARAM_TYPE             := 1                           ;
PARAM_VALUE_STR        := 'TargetAnalytics'           ;
PARAM_VALUE_CLOB       := null                        ;
TENANT_ID              := '&TENANT_ID'                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3000                       ;
NAME                   := 'TOP_N_ROWS'               ;
PARAM_ATTRIBUTES       := null                       ;
PARAM_TYPE             := 1                          ;
PARAM_VALUE_STR        := '100'                      ;
PARAM_VALUE_CLOB       := null                       ;
TENANT_ID              := '&TENANT_ID'               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3000                        ;
NAME                   := 'TA_HORI_SORT_ORDER'        ;
PARAM_ATTRIBUTES       := null                        ;
PARAM_TYPE             := 1                           ;
PARAM_VALUE_STR        := 'true'                      ;
PARAM_VALUE_CLOB       := null                        ;
TENANT_ID              := '&TENANT_ID'                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3000                        ;
NAME                   := 'TA_HORI_SORT_COLUMN'       ;
PARAM_ATTRIBUTES       := null                        ;
PARAM_TYPE             := 1                           ;
PARAM_VALUE_STR        := '0'                         ;
PARAM_VALUE_CLOB       := null                        ;
TENANT_ID              := '&TENANT_ID'                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;

 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3001;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data WebLogic Servers with small Maximum Heap Size has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                  :=   3001                                                                                                                   ;
NAME                       :=   'WebLogic Servers with small Maximum Heap Size'                                                                        ;
OWNER                      :=   'ORACLE'                                                                                                               ;
CREATION_DATE              :=   SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                                          ;
LAST_MODIFICATION_DATE     :=   SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                                          ;
LAST_MODIFIED_BY           :=   'ORACLE'                                                                                                               ;
DESCRIPTION                :=   'Shows WebLogic Servers with Maximum Heap Size less than 1GB running on hosts with Memory Size greater than 8GB'       ;
FOLDER_ID                  :=   4                                                                                                                      ;
CATEGORY_ID                :=   2                                                                                                                      ;
NAME_NLSID                 :=   null                                                                                                                   ;
NAME_SUBSYSTEM             :=   null                                                                                                                   ;
DESCRIPTION_NLSID          :=   null                                                                                                                   ;
DESCRIPTION_SUBSYSTEM      :=   null                                                                                                                   ;
SYSTEM_SEARCH              :=   1                                                                                                                      ;
EM_PLUGIN_ID               :=   null                                                                                                                   ;
IS_LOCKED                  :=   0                                                                                                                      ;
METADATA_CLOB              :=   null                                                                                                                   ;
SEARCH_DISPLAY_STR         :=   ''                                                                                                                     ;
UI_HIDDEN                  :=   0                                                                                                                      ;
TENANT_ID                  :=   '&TENANT_ID'                                                                                                           ;
IS_WIDGET                  :=   1                                                                                                                      ;

Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
);

SEARCH_ID              := 3001                                                   ;
NAME                   := 'WIDGET_TEMPLATE'                                      ;
PARAM_ATTRIBUTES       := null                                                   ;
PARAM_TYPE             := 1                                                      ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/visualizationWidget.html' ;
PARAM_VALUE_CLOB       := null                                                   ;
TENANT_ID              := '&TENANT_ID'                                           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              :=3001                                             ;
NAME                   :='WIDGET_ICON'                                    ;
PARAM_ATTRIBUTES       :=null                                             ;
PARAM_TYPE             :=1                                                ;
PARAM_VALUE_STR        :='/../images/func_horibargraph_24_ena.png'        ;
PARAM_VALUE_CLOB       :=null                                             ;
TENANT_ID              :='&TENANT_ID'                                     ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3001                                                         ;
NAME                   :='WIDGET_VIEWMODEL'                                           ;
PARAM_ATTRIBUTES       :=null                                                         ;
PARAM_TYPE             :=1                                                            ;
PARAM_VALUE_STR        :='/widget/visualizationWidget/js/VisualizationWidget.js'      ;
PARAM_VALUE_CLOB       :=null                                                         ;
TENANT_ID              :='&TENANT_ID'                                                 ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS                                               
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3001                                                          ;
NAME                   :='TIME_PERIOD_KEY'                                             ;
PARAM_ATTRIBUTES       :=null                                                          ;
PARAM_TYPE             :=1                                                             ;
PARAM_VALUE_STR        :='24'                                                          ;
PARAM_VALUE_CLOB       :=null                                                          ;
TENANT_ID              :='&TENANT_ID'                                                  ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3001                        ;
NAME                   :='TA_CRITERIA'               ;
PARAM_ATTRIBUTES       :=null                        ;
PARAM_TYPE             :=2                           ;
PARAM_VALUE_STR        :=null                        ;
PARAM_VALUE_CLOB       :='{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_WeblogicResourceconfig_maxheap","_value":null,"dataType":"number","displayName":"Maximum Heap Size (MB)","displayDetails":"Resource Usage","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":true},"targetType":"weblogic_j2eeserver","unitStr":null,"mcName":"maxheap","mgName":"WeblogicResourceconfig","mgDisplayName":"Resource Usage","isKey":0,"groupKeyColumns":[],"isConfig":1,"direction":"DESC","inverseNullOrderBy":true}},'||
'{"jsonConstructor":"AssociationSearchCriterion","data":{"assoc":{"sourceTargetType":"weblogic_j2eeserver","destinationTargetType":"host","associationType":"hosted_by","assocDisplayName":"hosted_by","inverseAssocType":"host_for","id":"hosted_by(weblogic_j2eeserver,host)"},"wrappedCriterion":{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Hw_memorySizeInMb","_value":null,"dataType":"number","displayName":"Memory Size (MB)","displayDetails":"Hardware","role":"column","func":null,"UIProperties":{},"targetType":"host","unitStr":null,"mcName":"memorySizeInMb","mgName":"Hw","mgDisplayName":"Hardware","isKey":0,"groupKeyColumns":[],"isConfig":1}},"id":"hosted_by(weblogic_j2eeserver,host)_host_Hw_memorySizeInMb","_value":null,"dataType":"number","displayName":"Memory Size (MB)","displayDetails":"hosted_by (Host) > Hardware","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"}'||
',{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_WeblogicResourceconfig_maxheap","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":{"type":"lt","values":{"jsonConstructor":"CriterionValue","data":{"value":1024}}}}]}},"dataType":"number","displayName":"Maximum Heap Size (MB)","displayDetails":"Resource Usage","role":"filter","func":null,"UIProperties":{"useForSort":false},"targetType":"weblogic_j2eeserver","unitStr":null,"mcName":"maxheap","mgName":"WeblogicResourceconfig","mgDisplayName":"Resource Usage","isKey":0,"groupKeyColumns":[],"isConfig":1}},{"jsonConstructor":"AssociationSearchCriterion","data":{"assoc":{"sourceTargetType":"weblogic_j2eeserver","destinationTargetType":"host","associationType":"hosted_by","assocDisplayName":"hosted_by","inverseAssocType":"host_for","id":"hosted_by(weblogic_j2eeserver,host)"},'||
'"wrappedCriterion":{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Hw_memorySizeInMb","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":{"type":"gt","values":{"jsonConstructor":"CriterionValue","data":{"value":8192}}}}]}},"dataType":"number","displayName":"Memory Size (MB)","displayDetails":"Hardware","role":"filter","func":null,"UIProperties":{},"targetType":"host","unitStr":null,"mcName":"memorySizeInMb","mgName":"Hw","mgDisplayName":"Hardware","isKey":0,"groupKeyColumns":[],"isConfig":1}},"id":"hosted_by(weblogic_j2eeserver,host)_host_Hw_memorySizeInMb","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":{"type":"gt","values":{"jsonConstructor":"CriterionValue","data":{"value":8192}}}}]}},"dataType":"number","displayName":"Memory Size (MB)","displayDetails":"hosted_by (Host) > Hardware","role":"filter","func":null,"UIProperties":{"useForSort":false}}}]}';
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3001                        ;
NAME                   :='PROVIDER_VERSION'          ;
PARAM_ATTRIBUTES       :=null                        ;
PARAM_TYPE             :=1                           ;
PARAM_VALUE_STR        :='1.0'                       ;
PARAM_VALUE_CLOB       :=null                        ;
TENANT_ID              :='&TENANT_ID'                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3001                          ;
NAME                   :='WIDGET_KOC_NAME'             ;
PARAM_ATTRIBUTES       :=null                          ;
PARAM_TYPE             :=1                             ;
PARAM_VALUE_STR        :='emcta-visualization'         ;
PARAM_VALUE_CLOB       :=null                          ;
TENANT_ID              :='&TENANT_ID'                  ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3001                            ;
NAME                   :='VISUALIZATION_TYPE_KEY'        ;
PARAM_ATTRIBUTES       :=null                            ;
PARAM_TYPE             :=1                               ;
PARAM_VALUE_STR        :='TABLE'                         ;
PARAM_VALUE_CLOB       :=null                            ;
TENANT_ID              :='&TENANT_ID'                    ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3001                           ;
NAME                   :='PROVIDER_ASSET_ROOT'          ;
PARAM_ATTRIBUTES       :=null                           ;
PARAM_TYPE             :=1                              ;
PARAM_VALUE_STR        :='assetRoot'                    ;
PARAM_VALUE_CLOB       :=null                           ;
TENANT_ID              :='&TENANT_ID'                   ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3001                             ;
NAME                   :='WIDGET_SOURCE'                  ;
PARAM_ATTRIBUTES       :=null                             ;
PARAM_TYPE             :=1                                ;
PARAM_VALUE_STR        :='1'                              ;
PARAM_VALUE_CLOB       :=null                             ;
TENANT_ID              :='&TENANT_ID'                     ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3001                                 ;
NAME                   :='PROVIDER_NAME'                      ;
PARAM_ATTRIBUTES       :=null                                 ;
PARAM_TYPE             :=1                                    ;
PARAM_VALUE_STR        :='TargetAnalytics'                    ;
PARAM_VALUE_CLOB       :=null                                 ;
TENANT_ID              :='&TENANT_ID'                         ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3001                                  ;
NAME                   :='TOP_N_ROWS'                          ;
PARAM_ATTRIBUTES       :=null                                  ;
PARAM_TYPE             :=1                                     ;
PARAM_VALUE_STR        :='100'                                 ;
PARAM_VALUE_CLOB       :=null                                  ;
TENANT_ID              :='&TENANT_ID'                          ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);                                                              
SEARCH_ID              :=3001                                   ;
NAME                   :='TA_HORI_SORT_ORDER'                   ;
PARAM_ATTRIBUTES       :=null                                   ;
PARAM_TYPE             :=1                                      ;
PARAM_VALUE_STR        :='true'                                 ;
PARAM_VALUE_CLOB       :=null                                   ;
TENANT_ID              :='&TENANT_ID'                           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);                                                            
SEARCH_ID              :=3001                                 ;
NAME                   :='TA_HORI_SORT_COLUMN'                ;
PARAM_ATTRIBUTES       :=null                                 ;
PARAM_TYPE             :=1                                    ;
PARAM_VALUE_STR        :='0'                                  ;
PARAM_VALUE_CLOB       :=null                                 ;
TENANT_ID              :='&TENANT_ID'                         ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3002;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data WebLogic Servers and their Ports has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                  :=   3002;
NAME                       :=   'WebLogic Servers and their Ports';
OWNER                      :=   'ORACLE';
CREATION_DATE              :=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
LAST_MODIFICATION_DATE     :=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
LAST_MODIFIED_BY           :=   'ORACLE';
DESCRIPTION                :=   'Shows the Listen Port, SSL Listen Port and Administration Port for WebLogic Servers';
FOLDER_ID                  :=   4;
CATEGORY_ID                :=   2;
NAME_NLSID                 :=   null;
NAME_SUBSYSTEM             :=   null;
DESCRIPTION_NLSID          :=   null;
DESCRIPTION_SUBSYSTEM      :=   null;
SYSTEM_SEARCH              :=   1;
EM_PLUGIN_ID               :=   null;
IS_LOCKED                  :=   0;
METADATA_CLOB              :=   null;
SEARCH_DISPLAY_STR         :=   '';
UI_HIDDEN                  :=   0;
TENANT_ID                  :=   '&TENANT_ID';
IS_WIDGET                  :=   1;
Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
);

SEARCH_ID              := 3002                                                         ;
NAME                   := 'WIDGET_TEMPLATE'                                            ;
PARAM_ATTRIBUTES       := null                                                         ;
PARAM_TYPE             := 1                                                            ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/visualizationWidget.html'       ;
PARAM_VALUE_CLOB       := null                                                         ;
TENANT_ID              := '&TENANT_ID'                                                 ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3002                                             ;
NAME                   := 'WIDGET_ICON'                                    ;
PARAM_ATTRIBUTES       := null                                             ;
PARAM_TYPE             := 1                                                ;
PARAM_VALUE_STR        := '/../images/func_horibargraph_24_ena.png'        ;
PARAM_VALUE_CLOB       := null                                             ;
TENANT_ID              := '&TENANT_ID'                                     ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3002                                                        ;
NAME                   := 'WIDGET_VIEWMODEL'                                          ;
PARAM_ATTRIBUTES       := null                                                        ;
PARAM_TYPE             := 1                                                           ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/js/VisualizationWidget.js'     ;
PARAM_VALUE_CLOB       := null                                                        ;
TENANT_ID              := '&TENANT_ID'                                                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3002                                         ;
NAME                   := 'TIME_PERIOD_KEY'                            ;
PARAM_ATTRIBUTES       := null                                         ;
PARAM_TYPE             := 1                                            ;
PARAM_VALUE_STR        := '24'                                         ;
PARAM_VALUE_CLOB       := null                                         ;
TENANT_ID              := '&TENANT_ID'                                 ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3002;
NAME                   := 'TA_CRITERIA';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 2;
PARAM_VALUE_STR        := null;
PARAM_VALUE_CLOB       := '{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_WeblogicServer_listenport","_value":null,"dataType":"number","displayName":"Listen Port","displayDetails":"Server Information","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"string","useForSort":true},"targetType":"weblogic_j2eeserver","unitStr":null,"mcName":"listenport","mgName":"WeblogicServer","mgDisplayName":"Server Information","isKey":0,"groupKeyColumns":[],"isConfig":1,"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_WeblogicServer_ssllistenport","_value":null,"dataType":"number","displayName":"SSL Listen Port","displayDetails":"Server Information"'||',"role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"string","useForSort":false},"targetType":"weblogic_j2eeserver","unitStr":null,"mcName":"ssllistenport","mgName":"WeblogicServer","mgDisplayName":"Server Information","isKey":0,"groupKeyColumns":[],"isConfig":1}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_WeblogicServer_administrationport","_value":null,"dataType":"number","displayName":"Administration Port","displayDetails":"Server Information","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"string","useForSort":false},"targetType":"weblogic_j2eeserver","unitStr":null,"mcName":"administrationport","mgName":"WeblogicServer","mgDisplayName":"Server Information","isKey":0,"groupKeyColumns":[],"isConfig":1}}]}';
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3002                   ;
NAME                   := 'PROVIDER_VERSION'     ;
PARAM_ATTRIBUTES       := null                   ;
PARAM_TYPE             := 1                      ;
PARAM_VALUE_STR        := '1.0'                  ;
PARAM_VALUE_CLOB       := null                   ;
TENANT_ID              := '&TENANT_ID'           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3002                       ;
NAME                   := 'WIDGET_KOC_NAME'          ;
PARAM_ATTRIBUTES       := null                       ;
PARAM_TYPE             := 1                          ;
PARAM_VALUE_STR        := 'emcta-visualization'      ;
PARAM_VALUE_CLOB       := null                       ;
TENANT_ID              := '&TENANT_ID'               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3002                             ;
NAME                   := 'VISUALIZATION_TYPE_KEY'         ;
PARAM_ATTRIBUTES       := null                             ;
PARAM_TYPE             := 1                                ;
PARAM_VALUE_STR        := 'TABLE'                          ;
PARAM_VALUE_CLOB       := null                             ;
TENANT_ID              := '&TENANT_ID'                     ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3002                             ;
NAME                   := 'PROVIDER_ASSET_ROOT'            ;
PARAM_ATTRIBUTES       := null                             ;
PARAM_TYPE             := 1                                ;
PARAM_VALUE_STR        := 'assetRoot'                      ;
PARAM_VALUE_CLOB       := null                             ;
TENANT_ID              := '&TENANT_ID'                     ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3002                       ;
NAME                   := 'WIDGET_SOURCE'            ;
PARAM_ATTRIBUTES       := null                       ;
PARAM_TYPE             := 1                          ;
PARAM_VALUE_STR        := '1'                        ;
PARAM_VALUE_CLOB       := null                       ;
TENANT_ID              := '&TENANT_ID'               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3002                ;
NAME                   := 'PROVIDER_NAME'     ;
PARAM_ATTRIBUTES       := null                ;
PARAM_TYPE             := 1                   ;
PARAM_VALUE_STR        := 'TargetAnalytics'   ;
PARAM_VALUE_CLOB       := null                ;
TENANT_ID              := '&TENANT_ID'        ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3002                 ;
NAME                   := 'TOP_N_ROWS'         ;
PARAM_ATTRIBUTES       := null                 ;
PARAM_TYPE             := 1                    ;
PARAM_VALUE_STR        := '100'                ;
PARAM_VALUE_CLOB       := null                 ;
TENANT_ID              := '&TENANT_ID'         ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3002                     ;
NAME                   := 'TA_HORI_SORT_ORDER'     ;
PARAM_ATTRIBUTES       := null                     ;
PARAM_TYPE             := 1                        ;
PARAM_VALUE_STR        := 'true'                   ;
PARAM_VALUE_CLOB       := null                     ;
TENANT_ID              := '&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3002                     ;
NAME                   := 'TA_HORI_SORT_COLUMN'    ;
PARAM_ATTRIBUTES       := null                     ;
PARAM_TYPE             := 1                        ;
PARAM_VALUE_STR        := '0'                      ;
PARAM_VALUE_CLOB       := null                     ;
TENANT_ID              := '&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3003;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data Databases with Autoextend ON and size greater than 10GB has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                  :=    3003;
NAME                       :=    'Databases with Autoextend ON and size greater than 10GB';
OWNER                      :=    'ORACLE';
CREATION_DATE              :=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
LAST_MODIFICATION_DATE     :=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
LAST_MODIFIED_BY           :=    'ORACLE';
DESCRIPTION                :=    'Shows Databases for which there is a datafile with size greater than 10GB and Autoextensible is ON for that datafile';
FOLDER_ID                  :=    4;
CATEGORY_ID                :=    2;
NAME_NLSID                 :=    null;
NAME_SUBSYSTEM             :=    null;
DESCRIPTION_NLSID          :=    null;
DESCRIPTION_SUBSYSTEM      :=    null;
SYSTEM_SEARCH              :=    1;
EM_PLUGIN_ID               :=    null;
IS_LOCKED                  :=    0;
METADATA_CLOB              :=    null;
SEARCH_DISPLAY_STR         :=    '';
UI_HIDDEN                  :=    0;
TENANT_ID                  :=    '&TENANT_ID';
IS_WIDGET                  :=    1;
Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
);

SEARCH_ID              := 3003                                                      ;
NAME                   := 'WIDGET_TEMPLATE'                                         ;
PARAM_ATTRIBUTES       := null                                                      ;
PARAM_TYPE             := 1                                                         ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/visualizationWidget.html'    ;
PARAM_VALUE_CLOB       := null                                                      ;
TENANT_ID              := '&TENANT_ID'                                              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3003                                         ;
NAME                   := 'WIDGET_ICON'                                ;
PARAM_ATTRIBUTES       := null                                         ;
PARAM_TYPE             := 1                                            ;
PARAM_VALUE_STR        := '/../images/func_horibargraph_24_ena.png'    ;
PARAM_VALUE_CLOB       := null                                         ;
TENANT_ID              := '&TENANT_ID'                                 ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3003                                                     ;
NAME                   := 'WIDGET_VIEWMODEL'                                       ;
PARAM_ATTRIBUTES       := null                                                     ;
PARAM_TYPE             := 1                                                        ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/js/VisualizationWidget.js'  ;
PARAM_VALUE_CLOB       := null                                                     ;
TENANT_ID              := '&TENANT_ID'                                             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3003                   ;
NAME                   := 'TIME_PERIOD_KEY'      ;
PARAM_ATTRIBUTES       := null                   ;
PARAM_TYPE             := 1                      ;
PARAM_VALUE_STR        := '24'                   ;
PARAM_VALUE_CLOB       := null                   ;
TENANT_ID              := '&TENANT_ID'           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3003;
NAME                   := 'TA_CRITERIA';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 2;
PARAM_VALUE_STR        := null;
PARAM_VALUE_CLOB       := '{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"oracle_database","metadata":{"category":false},"displayValue":"Database Instance"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_database_DbDatafiles_fileSize","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":{"type":"any","values":{"jsonConstructor":"CriterionExpression","data":{"type":"gt","values":{"jsonConstructor":"CriterionValue","data":{"value":10737418240}}}}}}]}},"dataType":"number","displayName":"Size","displayDetails":"Datafiles","role":"filter","func":null,"UIProperties":{"useForSort":false},"targetType":"oracle_database","unitStr":null,"mcName":"fileSize","mgName":"DbDatafiles","mgDisplayName":"Datafiles","isKey":0,"groupKeyColumns":["fileName","tablespaceName"],"isConfig":1}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_database_DbDatafiles_autoextensible",'||
'"_value":{"jsonConstructor":"CriterionExpression","data":{"type":"or","values":[{"jsonConstructor":"CriterionExpression","data":{"type":"any","values":{"jsonConstructor":"CriterionExpression","data":{"type":"like","values":{"jsonConstructor":"CriterionValue","data":{"value":"%YES%"}}}}}}]}},"dataType":"string","displayName":"Autoextensible","displayDetails":"Datafiles","role":"filter","func":null,"UIProperties":{"useForSort":false},"targetType":"oracle_database","unitStr":null,"mcName":"autoextensible","mgName":"DbDatafiles","mgDisplayName":"Datafiles","isKey":0,"groupKeyColumns":["fileName","tablespaceName"],"isConfig":1}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_database_DbDatafiles_fileSize","_value":null,"dataType":"number","displayName":"Size","displayDetails":"Datafiles","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":true},"targetType":"oracle_database","unitStr":null,"mcName":"fileSize","mgName":"DbDatafiles","mgDisplayName":"Datafiles","isKey":0,"groupKeyColumns":["fileName","tablespaceName"],"isConfig":1,"direction":"DESC","inverseNullOrderBy":true}}]}';
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3003                         ;
NAME                   := 'PROVIDER_VERSION'           ;
PARAM_ATTRIBUTES       := null                         ;
PARAM_TYPE             := 1                            ;
PARAM_VALUE_STR        := '1.0'                        ;
PARAM_VALUE_CLOB       := null                         ;
TENANT_ID              := '&TENANT_ID'                 ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3003                      ;
NAME                   := 'WIDGET_KOC_NAME'         ;
PARAM_ATTRIBUTES       := null                      ;
PARAM_TYPE             := 1                         ;
PARAM_VALUE_STR        := 'emcta-visualization'     ;
PARAM_VALUE_CLOB       := null                      ;
TENANT_ID              := '&TENANT_ID'              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3003                          ;
NAME                   := 'VISUALIZATION_TYPE_KEY'      ;
PARAM_ATTRIBUTES       := null                          ;
PARAM_TYPE             := 1                             ;
PARAM_VALUE_STR        := 'TABLE'                       ;
PARAM_VALUE_CLOB       := null                          ;
TENANT_ID              := '&TENANT_ID'                  ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3003                      ;
NAME                   := 'PROVIDER_ASSET_ROOT'     ;
PARAM_ATTRIBUTES       := null                      ;
PARAM_TYPE             := 1                         ;
PARAM_VALUE_STR        := 'assetRoot'               ;
PARAM_VALUE_CLOB       := null                      ;
TENANT_ID              := '&TENANT_ID'              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3003                        ;
NAME                   := 'WIDGET_SOURCE'             ;
PARAM_ATTRIBUTES       := null                        ;
PARAM_TYPE             := 1                           ;
PARAM_VALUE_STR        := '1'                         ;
PARAM_VALUE_CLOB       := null                        ;
TENANT_ID              := '&TENANT_ID'                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3003                           ;
NAME                   := 'PROVIDER_NAME'                ;
PARAM_ATTRIBUTES       := null                           ;
PARAM_TYPE             := 1                              ;
PARAM_VALUE_STR        := 'TargetAnalytics'              ;
PARAM_VALUE_CLOB       := null                           ;
TENANT_ID              := '&TENANT_ID'                   ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3003              ;
NAME                   := 'TOP_N_ROWS'      ;
PARAM_ATTRIBUTES       := null              ;
PARAM_TYPE             := 1                 ;
PARAM_VALUE_STR        := '100'             ;
PARAM_VALUE_CLOB       := null              ;
TENANT_ID              := '&TENANT_ID'      ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3003                       ;
NAME                   := 'TA_HORI_SORT_ORDER'       ;
PARAM_ATTRIBUTES       := null                       ;
PARAM_TYPE             := 1                          ;
PARAM_VALUE_STR        := 'true'                     ;
PARAM_VALUE_CLOB       := null                       ;
TENANT_ID              := '&TENANT_ID'               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3003                        ;
NAME                   := 'TA_HORI_SORT_COLUMN'       ;
PARAM_ATTRIBUTES       := null                        ;
PARAM_TYPE             := 1                           ;
PARAM_VALUE_STR        := '0'                         ;
PARAM_VALUE_CLOB       := null                        ;
TENANT_ID              := '&TENANT_ID'                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3004;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data Top 10 Listeners by Load has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                  := 3004;
NAME                       := 'Top 10 Listeners by Load';
OWNER                      := 'ORACLE';
CREATION_DATE              := SYS_EXTRACT_UTC(SYSTIMESTAMP);
LAST_MODIFICATION_DATE     := SYS_EXTRACT_UTC(SYSTIMESTAMP);
LAST_MODIFIED_BY           := 'ORACLE';
DESCRIPTION                := 'Shows Response Time, Connections Established and Connections Refused for top 10 Listeners (by Load)';
FOLDER_ID                  := 4;
CATEGORY_ID                := 2;
NAME_NLSID                 := null;
NAME_SUBSYSTEM             := null;
DESCRIPTION_NLSID          := null;
DESCRIPTION_SUBSYSTEM      := null;
SYSTEM_SEARCH              := 1;
EM_PLUGIN_ID               := null;
IS_LOCKED                  := 0;
METADATA_CLOB              := null;
SEARCH_DISPLAY_STR         := '';
UI_HIDDEN                  := 0;
TENANT_ID                  := '&TENANT_ID';
IS_WIDGET                  := 1;
Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
);

SEARCH_ID              := 3004                                                          ;
NAME                   := 'WIDGET_TEMPLATE'                                             ;
PARAM_ATTRIBUTES       := null                                                          ;
PARAM_TYPE             := 1                                                             ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/visualizationWidget.html'        ;
PARAM_VALUE_CLOB       := null                                                          ;
TENANT_ID              := '&TENANT_ID'                                                  ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3004                                                    ;
NAME                   := 'WIDGET_ICON'                                           ;
PARAM_ATTRIBUTES       := null                                                    ;
PARAM_TYPE             := 1                                                       ;
PARAM_VALUE_STR        := '/../images/func_horibargraph_24_ena.png'               ;
PARAM_VALUE_CLOB       := null                                                    ;
TENANT_ID              := '&TENANT_ID'                                            ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3004                                                     ;
NAME                   := 'WIDGET_VIEWMODEL'                                       ;
PARAM_ATTRIBUTES       := null                                                     ;
PARAM_TYPE             := 1                                                        ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/js/VisualizationWidget.js'  ;
PARAM_VALUE_CLOB       := null                                                     ;
TENANT_ID              := '&TENANT_ID'                                             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3004                 ;
NAME                   := 'TIME_PERIOD_KEY'    ;
PARAM_ATTRIBUTES       := null                 ;
PARAM_TYPE             := 1                    ;
PARAM_VALUE_STR        := '24'                 ;
PARAM_VALUE_CLOB       := null                 ;
TENANT_ID              := '&TENANT_ID'         ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3004;
NAME                   := 'TA_CRITERIA';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 2;
PARAM_VALUE_STR        := null;
PARAM_VALUE_CLOB       := '{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"oracle_listener","metadata":{"category":false},"displayValue":"Listener"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","UIProperties":{"useForSort":false}}},{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"string","displayName":"Target Status","displayDetails":null,"role":"column","UIProperties":{"useForSort":false,"ignoreCase":true},"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_listener_Response_tnsPing","_value":null,"dataType":"number","displayName":"Response Time (msec)","displayDetails":"Response","role":"column","UIProperties":{"useForSort":false,"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar"},"targetType":"oracle_listener","mcName":"tnsPing","mgName":"Response","mgDisplayName":"Response","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_listener_Load_estConns","_value":null,"dataType":"number","displayName":"Connections Established","displayDetails":"Load","role":"column",'||'"UIProperties":{"useForSort":true,"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar"},"targetType":"oracle_listener","mcName":"estConns","mgName":"Load","mgDisplayName":"Load","isKey":0,"groupKeyColumns":[],"isConfig":0,"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_listener_Load_refConns","_value":null,"dataType":"number","displayName":"Connections Refused (per","displayDetails":"Load","role":"column","UIProperties":{"useForSort":false,"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar"},"targetType":"oracle_listener","mcName":"refConns","mgName":"Load","mgDisplayName":"Load","isKey":0,"groupKeyColumns":[],"isConfig":0}}]}';
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3004                     ;
NAME                   := 'PROVIDER_VERSION'       ;
PARAM_ATTRIBUTES       := null                     ;
PARAM_TYPE             := 1                        ;
PARAM_VALUE_STR        := '1.0'                    ;
PARAM_VALUE_CLOB       := null                     ;
TENANT_ID              := '&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3004                     ;
NAME                   := 'WIDGET_KOC_NAME'        ;
PARAM_ATTRIBUTES       := null                     ;
PARAM_TYPE             := 1                        ;
PARAM_VALUE_STR        := 'emcta-visualization'    ;
PARAM_VALUE_CLOB       := null                     ;
TENANT_ID              := '&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3004                         ;
NAME                   := 'VISUALIZATION_TYPE_KEY'     ;
PARAM_ATTRIBUTES       := null                         ;
PARAM_TYPE             := 1                            ;
PARAM_VALUE_STR        := 'TABLE'                      ;
PARAM_VALUE_CLOB       := null                         ;
TENANT_ID              := '&TENANT_ID'                 ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3004                     ;
NAME                   := 'PROVIDER_ASSET_ROOT'    ;
PARAM_ATTRIBUTES       := null                     ;
PARAM_TYPE             := 1                        ;
PARAM_VALUE_STR        := 'assetRoot'              ;
PARAM_VALUE_CLOB       := null                     ;
TENANT_ID              := '&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3004            ;
NAME                   := 'WIDGET_SOURCE' ;
PARAM_ATTRIBUTES       := null            ;
PARAM_TYPE             := 1               ;
PARAM_VALUE_STR        := '1'             ;
PARAM_VALUE_CLOB       := null            ;
TENANT_ID              := '&TENANT_ID'    ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3004               ;
NAME                   := 'PROVIDER_NAME'    ;
PARAM_ATTRIBUTES       := null               ;
PARAM_TYPE             := 1                  ;
PARAM_VALUE_STR        := 'TargetAnalytics'  ;
PARAM_VALUE_CLOB       := null               ;
TENANT_ID              := '&TENANT_ID'       ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3004                       ;
NAME                   := 'TOP_N_ROWS'               ;
PARAM_ATTRIBUTES       := null                       ;
PARAM_TYPE             := 1                          ;
PARAM_VALUE_STR        := '10'                       ;
PARAM_VALUE_CLOB       := null                       ;
TENANT_ID              := '&TENANT_ID'               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3004                         ;
NAME                   := 'TA_HORI_SORT_ORDER'         ;
PARAM_ATTRIBUTES       := null                         ;
PARAM_TYPE             := 1                            ;
PARAM_VALUE_STR        := 'true'                       ;
PARAM_VALUE_CLOB       := null                         ;
TENANT_ID              := '&TENANT_ID'                 ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3004                       ;
NAME                   := 'TA_HORI_SORT_COLUMN'      ;
PARAM_ATTRIBUTES       := null                       ;
PARAM_TYPE             := 1                          ;
PARAM_VALUE_STR        := '2'                        ;
PARAM_VALUE_CLOB       := null                       ;
TENANT_ID              := '&TENANT_ID'               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3018;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data Host Inventory By Platform has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                  := 3018;
NAME                       := 'Host Inventory By Platform';
OWNER                      := 'ORACLE';
CREATION_DATE              := SYS_EXTRACT_UTC(SYSTIMESTAMP);
LAST_MODIFICATION_DATE     := SYS_EXTRACT_UTC(SYSTIMESTAMP);
LAST_MODIFIED_BY           := 'ORACLE';
DESCRIPTION                := 'Shows CPU Implementation, Total CPU Cores/Sockets/Threads and Memory Size for Hosts grouped by Operating System';
FOLDER_ID                  := 4;
CATEGORY_ID                := 2;
NAME_NLSID                 := null;
NAME_SUBSYSTEM             := null;
DESCRIPTION_NLSID          := null;
DESCRIPTION_SUBSYSTEM      := null;
SYSTEM_SEARCH              := 1;
EM_PLUGIN_ID               := null;
IS_LOCKED                  := 0;
METADATA_CLOB              := null;
SEARCH_DISPLAY_STR         := '';
UI_HIDDEN                  := 0;
TENANT_ID                  := '&TENANT_ID';
IS_WIDGET                  := 1;
Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
);
SEARCH_ID              :=3018;
NAME                   :='WIDGET_TEMPLATE';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='/widget/visualizationWidget/visualizationWidget.html';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3018;
NAME                   :='WIDGET_ICON';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='/../images/func_horibargraph_24_ena.png';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3018;
NAME                   :='WIDGET_VIEWMODEL';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='/widget/visualizationWidget/js/VisualizationWidget.js';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3018;
NAME                   :='TIME_PERIOD_KEY';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='24';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3018;
NAME                   :='TA_CRITERIA';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=2;
PARAM_VALUE_STR        :=null;
PARAM_VALUE_CLOB       :='{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"host","metadata":{"category":false},"displayValue":"Host"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_HwCpu_impl","_value":null,"dataType":"string","displayName":"Implementation","displayDetails":"CPUs","role":"column","func":null,"UIProperties":{"useForSort":false},"targetType":"host","unitStr":null,"mcName":"impl","mgName":"HwCpu","mgDisplayName":"CPUs","isKey":1,"groupKeyColumns":["numCores","ecacheInMb","freqInMhz","isHyperthreadEnabled","impl","mask","revision","siblings","vendorName"],"isConfig":1}},'||
'{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Hw_totalCpuCores","_value":null,"dataType":"number","displayName":"Total CPU Cores","displayDetails":"Hardware","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"host","unitStr":null,"mcName":"totalCpuCores","mgName":"Hw","mgDisplayName":"Hardware","isKey":0,"groupKeyColumns":[],"isConfig":1}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Hw_physicalCpuCount","_value":null,"dataType":"number","displayName":"Total CPU Sockets","displayDetails":"Hardware","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"host","unitStr":null,"mcName":"physicalCpuCount","mgName":"Hw","mgDisplayName":"Hardware","isKey":0,"groupKeyColumns":[],"isConfig":1}}'||
',{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Hw_logicalCpuCount","_value":null,"dataType":"number","displayName":"Total CPU Threads","displayDetails":"Hardware","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"host","unitStr":null,"mcName":"logicalCpuCount","mgName":"Hw","mgDisplayName":"Hardware","isKey":0,"groupKeyColumns":[],"isConfig":1}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Hw_memorySizeInMb","_value":null,"dataType":"number","displayName":"Memory Size (MB)","displayDetails":"Hardware","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},'||
'{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"host","unitStr":null,"mcName":"memorySizeInMb","mgName":"Hw","mgDisplayName":"Hardware","isKey":0,"groupKeyColumns":[],"isConfig":1}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Os_name","_value":null,"dataType":"string","displayName":"Name","displayDetails":"Operating System","role":"group by","func":null,"UIProperties":{"useForSort":false},"targetType":"host","unitStr":null,"mcName":"name","mgName":"Os","mgDisplayName":"Operating System","isKey":0,"groupKeyColumns":[],"isConfig":1}}]}';
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3018                  ;
NAME                   :='PROVIDER_VERSION'    ;
PARAM_ATTRIBUTES       :=null                  ;
PARAM_TYPE             :=1                     ;
PARAM_VALUE_STR        :='1.0'                 ;
PARAM_VALUE_CLOB       :=null                  ;
TENANT_ID              :='&TENANT_ID'          ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3018                  ;
NAME                   :='WIDGET_KOC_NAME'     ;
PARAM_ATTRIBUTES       :=null                  ;
PARAM_TYPE             :=1                     ;
PARAM_VALUE_STR        :='emcta-visualization' ;
PARAM_VALUE_CLOB       :=null                  ;
TENANT_ID              :='&TENANT_ID'          ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3018                         ;
NAME                   :='VISUALIZATION_TYPE_KEY'     ;
PARAM_ATTRIBUTES       :=null                         ;
PARAM_TYPE             :=1                            ;
PARAM_VALUE_STR        :='TABLE'                      ;
PARAM_VALUE_CLOB       :=null                         ;
TENANT_ID              :='&TENANT_ID'                 ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3018                            ;
NAME                   :='PROVIDER_ASSET_ROOT'           ;
PARAM_ATTRIBUTES       :=null                            ;
PARAM_TYPE             :=1                               ;
PARAM_VALUE_STR        :='assetRoot'                     ;
PARAM_VALUE_CLOB       :=null                            ;
TENANT_ID              :='&TENANT_ID'                    ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3018                              ;
NAME                   :='WIDGET_SOURCE'                   ;
PARAM_ATTRIBUTES       :=null                              ;
PARAM_TYPE             :=1                                 ;
PARAM_VALUE_STR        :='1'                               ;
PARAM_VALUE_CLOB       :=null                              ;
TENANT_ID              :='&TENANT_ID'                      ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3018                               ;
NAME                   :='PROVIDER_NAME'                    ;
PARAM_ATTRIBUTES       :=null                               ;
PARAM_TYPE             :=1                                  ;
PARAM_VALUE_STR        :='TargetAnalytics'                  ;
PARAM_VALUE_CLOB       :=null                               ;
TENANT_ID              :='&TENANT_ID'                       ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3018                            ;
NAME                   :='TOP_N_ROWS'                    ;
PARAM_ATTRIBUTES       :=null                            ;
PARAM_TYPE             :=1                               ;
PARAM_VALUE_STR        :='100'                           ;
PARAM_VALUE_CLOB       :=null                            ;
TENANT_ID              :='&TENANT_ID'                    ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3018                        ;
NAME                   :='TA_HORI_SORT_ORDER'        ;
PARAM_ATTRIBUTES       :=null                        ;
PARAM_TYPE             :=1                           ;
PARAM_VALUE_STR        :='true'                      ;
PARAM_VALUE_CLOB       :=null                        ;
TENANT_ID              :='&TENANT_ID'                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3018                     ;
NAME                   :='TA_HORI_SORT_COLUMN'    ;
PARAM_ATTRIBUTES       :=null                     ;
PARAM_TYPE             :=1                        ;
PARAM_VALUE_STR        :='-1'                     ;
PARAM_VALUE_CLOB       :=null                     ;
TENANT_ID              :='&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3019;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data Top 25 Databases by Resource Consumption has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                  :=3019;
NAME                       :='Top 25 Databases by Resource Consumption';
OWNER                      :='ORACLE';
CREATION_DATE              :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
LAST_MODIFICATION_DATE     :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
LAST_MODIFIED_BY           :='ORACLE';
DESCRIPTION                :='Shows the status, Database CPU Time, CPU Utilization, Total Memory Usage and Number of Transactions per second for Top 25 Databases (by Resource Consumption)';
FOLDER_ID                  :=4;
CATEGORY_ID                :=2;
NAME_NLSID                 :=null;
NAME_SUBSYSTEM             :=null;
DESCRIPTION_NLSID          :=null;
DESCRIPTION_SUBSYSTEM      :=null;
SYSTEM_SEARCH              :=1;
EM_PLUGIN_ID               :=null;
IS_LOCKED                  :=0;
METADATA_CLOB              :=null;
SEARCH_DISPLAY_STR         :='';
UI_HIDDEN                  :=0;
TENANT_ID                  :='&TENANT_ID';
IS_WIDGET                  :=1;
Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
);

SEARCH_ID              :=3019                                                          ;
NAME                   :='WIDGET_TEMPLATE'                                             ;
PARAM_ATTRIBUTES       :=null                                                          ;
PARAM_TYPE             :=1                                                             ;
PARAM_VALUE_STR        :='/widget/visualizationWidget/visualizationWidget.html'        ;
PARAM_VALUE_CLOB       :=null                                                          ;
TENANT_ID              :='&TENANT_ID'                                                  ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3019                                           ;
NAME                   :='WIDGET_ICON'                                  ;
PARAM_ATTRIBUTES       :=null                                           ;
PARAM_TYPE             :=1                                              ;
PARAM_VALUE_STR        :='/../images/func_horibargraph_24_ena.png'      ;
PARAM_VALUE_CLOB       :=null                                           ;
TENANT_ID              :='&TENANT_ID'                                   ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3019                                                   ;
NAME                   :='WIDGET_VIEWMODEL'                                     ;
PARAM_ATTRIBUTES       :=null                                                   ;
PARAM_TYPE             :=1                                                      ;
PARAM_VALUE_STR        :='/widget/visualizationWidget/js/VisualizationWidget.js';
PARAM_VALUE_CLOB       :=null                                                   ;
TENANT_ID              :='&TENANT_ID'                                           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3019              ;
NAME                   :='TIME_PERIOD_KEY' ;
PARAM_ATTRIBUTES       :=null              ;
PARAM_TYPE             :=1                 ;
PARAM_VALUE_STR        :='24'              ;
PARAM_VALUE_CLOB       :=null              ;
TENANT_ID              :='&TENANT_ID'      ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3019;
NAME                   :='TA_CRITERIA';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=2;
PARAM_VALUE_STR        :=null;
PARAM_VALUE_CLOB       :='{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"oracle_database","metadata":{"category":false},"displayValue":"Database Instance"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"string","displayName":"Target Status","displayDetails":null,"role":"column","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_database_InstanceEfficiency_cpuTimePct","_value":null,"dataType":"number","displayName":"Database CPU Time (%)","displayDetails":"Efficiency","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},'||
'{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":true},"targetType":"oracle_database","unitStr":null,"mcName":"cpuTimePct","mgName":"InstanceEfficiency","mgDisplayName":"Efficiency","isKey":0,"groupKeyColumns":[],"isConfig":0,"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"AssociationSearchCriterion","data":{"assoc":{"sourceTargetType":"oracle_database","destinationTargetType":"host","associationType":"hosted_by","assocDisplayName":"hosted_by","inverseAssocType":"host_for","id":"hosted_by(oracle_database,host)"},"wrappedCriterion":{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Load_cpuUtil","_value":null,"dataType":"number","displayName":"CPU Utilization (%)","displayDetails":"Utilization ","role":"column","func":null,"UIProperties":{},"targetType":"host","unitStr":"%","mcName":"cpuUtil","mgName":"Load","mgDisplayName":"Utilization ","isKey":0,"groupKeyColumns":[],"isConfig":0}}'||
',"id":"hosted_by(oracle_database,host)_host_Load_cpuUtil","_value":null,"dataType":"number","displayName":"CPU Utilization (%)","displayDetails":"hosted_by (Host) > Utilization ","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_database_MemoryUsage_totalMemory","_value":null,"dataType":"number","displayName":"Total Memory Usage (MB)","displayDetails":"Memory Usage","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},'||
'{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"oracle_database","unitStr":null,"mcName":"totalMemory","mgName":"MemoryUsage","mgDisplayName":"Memory Usage","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_database_InstanceThroughput_transactionsPs","_value":null,"dataType":"number","displayName":"Number of Transactions (per second)","displayDetails":"Throughput","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"oracle_database","unitStr":null,"mcName":"transactionsPs","mgName":"InstanceThroughput","mgDisplayName":"Throughput","isKey":0,"groupKeyColumns":[],"isConfig":0}}]}';
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3019                   ;
NAME                   :='PROVIDER_VERSION'     ;
PARAM_ATTRIBUTES       :=null                   ;
PARAM_TYPE             :=1                      ;
PARAM_VALUE_STR        :='1.0'                  ;
PARAM_VALUE_CLOB       :=null                   ;
TENANT_ID              :='&TENANT_ID'           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3019                        ;
NAME                   :='WIDGET_KOC_NAME'           ;
PARAM_ATTRIBUTES       :=null                        ;
PARAM_TYPE             :=1                           ;
PARAM_VALUE_STR        :='emcta-visualization'       ;
PARAM_VALUE_CLOB       :=null                        ;
TENANT_ID              :='&TENANT_ID'                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3019                              ;
NAME                   :='VISUALIZATION_TYPE_KEY'          ;
PARAM_ATTRIBUTES       :=null                              ;
PARAM_TYPE             :=1                                 ;
PARAM_VALUE_STR        :='TABLE'                           ;
PARAM_VALUE_CLOB       :=null                              ;
TENANT_ID              :='&TENANT_ID'                      ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3019                       ;
NAME                   :='PROVIDER_ASSET_ROOT'      ;
PARAM_ATTRIBUTES       :=null                       ;
PARAM_TYPE             :=1                          ;
PARAM_VALUE_STR        :='assetRoot'                ;
PARAM_VALUE_CLOB       :=null                       ;
TENANT_ID              :='&TENANT_ID'               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3019                 ;
NAME                   :='WIDGET_SOURCE'      ;
PARAM_ATTRIBUTES       :=null                 ;
PARAM_TYPE             :=1                    ;
PARAM_VALUE_STR        :='1'                  ;
PARAM_VALUE_CLOB       :=null                 ;
TENANT_ID              :='&TENANT_ID'         ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3019               ;
NAME                   :='PROVIDER_NAME'    ;
PARAM_ATTRIBUTES       :=null               ;
PARAM_TYPE             :=1                  ;
PARAM_VALUE_STR        :='TargetAnalytics'  ;
PARAM_VALUE_CLOB       :=null               ;
TENANT_ID              :='&TENANT_ID'       ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3019              ;
NAME                   :='TOP_N_ROWS'      ;
PARAM_ATTRIBUTES       :=null              ;
PARAM_TYPE             :=1                 ;
PARAM_VALUE_STR        :='25'              ;
PARAM_VALUE_CLOB       :=null              ;
TENANT_ID              :='&TENANT_ID'      ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3019                      ;
NAME                   :='TA_HORI_SORT_ORDER'      ;
PARAM_ATTRIBUTES       :=null                      ;
PARAM_TYPE             :=1                         ;
PARAM_VALUE_STR        :='true'                    ;
PARAM_VALUE_CLOB       :=null                      ;
TENANT_ID              :='&TENANT_ID'              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3019                  ;
NAME                   :='TA_HORI_SORT_COLUMN' ;
PARAM_ATTRIBUTES       :=null                  ;
PARAM_TYPE             :=1                     ;
PARAM_VALUE_STR        :='1'                   ;
PARAM_VALUE_CLOB       :=null                  ;
TENANT_ID              :='&TENANT_ID'          ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3020;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data Database Configuration and Storage By Version has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                  := 3020                                                                                                      ;
NAME                       := 'Database Configuration and Storage By Version'                                                           ;
OWNER                      := 'ORACLE'                                                                                                  ;
CREATION_DATE              := SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                             ;
LAST_MODIFICATION_DATE     := SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                             ;
LAST_MODIFIED_BY           := 'ORACLE'                                                                                                  ;
DESCRIPTION                := 'Shows Total CPU Cores, Patch IDs, Tablespace Size and SGA Size for Databases grouped by their version'   ;
FOLDER_ID                  := 4                                                                                                         ;
CATEGORY_ID                := 2                                                                                                         ;
NAME_NLSID                 := null                                                                                                      ;
NAME_SUBSYSTEM             := null                                                                                                      ;
DESCRIPTION_NLSID          := null                                                                                                      ;
DESCRIPTION_SUBSYSTEM      := null                                                                                                      ;
SYSTEM_SEARCH              := 1                                                                                                         ;
EM_PLUGIN_ID               := null                                                                                                      ;
IS_LOCKED                  := 0                                                                                                         ;
METADATA_CLOB              := null                                                                                                      ;
SEARCH_DISPLAY_STR         := ''                                                                                                        ;
UI_HIDDEN                  := 0                                                                                                         ;
TENANT_ID                  := '&TENANT_ID'                                                                                              ;
IS_WIDGET                  := 1                                                                                                         ;
Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
);

SEARCH_ID              :=3020;
NAME                   :='WIDGET_TEMPLATE';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='/widget/visualizationWidget/visualizationWidget.html';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3020;
NAME                   :='WIDGET_ICON';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='/../images/func_horibargraph_24_ena.png';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3020;
NAME                   :='WIDGET_VIEWMODEL';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='/widget/visualizationWidget/js/VisualizationWidget.js';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3020;
NAME                   :='TIME_PERIOD_KEY';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='24';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3020;
NAME                   :='TA_CRITERIA';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=2;
PARAM_VALUE_STR        :=null;
PARAM_VALUE_CLOB       :='{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"oracle_database","metadata":{"category":false},"displayValue":"Database Instance"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"AssociationSearchCriterion","data":{"assoc":{"sourceTargetType":"oracle_database","destinationTargetType":"host","associationType":"hosted_by","assocDisplayName":"hosted_by","inverseAssocType":"host_for","id":"hosted_by(oracle_database,host)"},"wrappedCriterion":{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Hw_totalCpuCores","_value":null,"dataType":"number","displayName":"Total CPU Cores","displayDetails":"Hardware","role":"column","func":null,"UIProperties":{},'||
'"targetType":"host","unitStr":null,"mcName":"totalCpuCores","mgName":"Hw","mgDisplayName":"Hardware","isKey":0,"groupKeyColumns":[],"isConfig":1}},"id":"hosted_by(oracle_database,host)_host_Hw_totalCpuCores","_value":null,"dataType":"number","displayName":"Total CPU Cores","displayDetails":"hosted_by (Host) > Hardware","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":true},"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"AssociationSearchCriterion","data":{"assoc":{"sourceTargetType":"oracle_database","destinationTargetType":"oracle_home","associationType":"installed_at","assocDisplayName":"installed_at","inverseAssocType":"install_home_for","id":"installed_at(oracle_database,oracle_home)"},"wrappedCriterion":{"jsonConstructor":"MetricGroupSearchCriterion"'||
',"data":{"id":"oracle_home_LlInvPatches_id","_value":null,"dataType":"string","displayName":"Patch ID","displayDetails":"Unclassified ","role":"column","func":null,"UIProperties":{},"targetType":"oracle_home","unitStr":"n/a","mcName":"id","mgName":"LlInvPatches","mgDisplayName":"Unclassified ","isKey":1,"groupKeyColumns":["id","lang","upi"],"isConfig":1}},"id":"installed_at(oracle_database,oracle_home)_oracle_home_LlInvPatches_id","_value":null,"dataType":"string","displayName":"Patch ID","displayDetails":"installed_at (Oracle Home) > Unclassified ","role":"column","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"oracle_database_DbTablespaces_tablespaceSize","_value":null,"dataType":"number","displayName":"Size","displayDetails":"Tablespaces","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"oracle_database","unitStr":null,"mcName":"tablespaceSize","mgName":"DbTablespaces","mgDisplayName":"Tablespaces","isKey":0,"groupKeyColumns":["tablespaceName"],"isConfig":1}},{"jsonConstructor":"MetricGroupSearchCriterion","data":'||
'{"id":"oracle_database_DbSga_sgasize","_value":null,"dataType":"number","displayName":"Size","displayDetails":"System Global Area","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"oracle_database","unitStr":null,"mcName":"sgasize","mgName":"DbSga","mgDisplayName":"System Global Area","isKey":0,"groupKeyColumns":["sganame"],"isConfig":1}},{"jsonConstructor":"PropertySearchCriterion","data":{"id":"property.orcl_gtp_target_version","_value":null,"dataType":"string","displayName":"Target Version","displayDetails":null,"role":"group by","func":null,"UIProperties":{"useForSort":false},"targetType":"oracle_database","propertyName":"orcl_gtp_target_version"}}]}';
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3020;
NAME                   :='PROVIDER_VERSION';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='1.0';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3020;
NAME                   :='WIDGET_KOC_NAME';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='emcta-visualization';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3020;
NAME                   :='VISUALIZATION_TYPE_KEY';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='TABLE';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3020;
NAME                   :='PROVIDER_ASSET_ROOT';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='assetRoot';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3020;
NAME                   :='WIDGET_SOURCE';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='1';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3020;
NAME                   :='PROVIDER_NAME';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='TargetAnalytics';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3020;
NAME                   :='TOP_N_ROWS';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='100';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3020;
NAME                   :='TA_HORI_SORT_ORDER';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='true';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3020;
NAME                   :='TA_HORI_SORT_COLUMN';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='0';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3021;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data Top 25 WebLogic Servers by Heap Usage has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                  := 3021                                                                                                                                                        ;
NAME                       := 'Top 25 WebLogic Servers by Heap Usage'                                                                                                                     ;
OWNER                      := 'ORACLE'                                                                                                                                                    ;
CREATION_DATE              := SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                                                                               ;
LAST_MODIFICATION_DATE     := SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                                                                               ;
LAST_MODIFIED_BY           := 'ORACLE'                                                                                                                                                    ;
DESCRIPTION                := 'Shows the status, Heap Usage, Current Memory Pool Usage and Garbage Collection Invocation time and frequency for Top 25 WebLogic Servers (by Heap Usage)'  ;
FOLDER_ID                  := 4                                                                                                                                                           ;
CATEGORY_ID                := 2                                                                                                                                                           ;
NAME_NLSID                 := null                                                                                                                                                        ;
NAME_SUBSYSTEM             := null                                                                                                                                                        ;
DESCRIPTION_NLSID          := null                                                                                                                                                        ;
DESCRIPTION_SUBSYSTEM      := null                                                                                                                                                        ;
SYSTEM_SEARCH              := 1                                                                                                                                                           ;
EM_PLUGIN_ID               := null                                                                                                                                                        ;
IS_LOCKED                  := 0                                                                                                                                                           ;
METADATA_CLOB              := null                                                                                                                                                        ;
SEARCH_DISPLAY_STR         := ''                                                                                                                                                          ;
UI_HIDDEN                  := 0                                                                                                                                                           ;
TENANT_ID                  := '&TENANT_ID'                                                                                                                                                ;
IS_WIDGET                  := 1                                                                                                                                                           ;

Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
);

SEARCH_ID              := 3021                                                          ;
NAME                   := 'WIDGET_TEMPLATE'                                             ;
PARAM_ATTRIBUTES       := null                                                          ;
PARAM_TYPE             := 1                                                             ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/visualizationWidget.html'        ;
PARAM_VALUE_CLOB       := null                                                          ;
TENANT_ID              := '&TENANT_ID'                                                  ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3021                                                       ;
NAME                   := 'WIDGET_ICON'                                              ;
PARAM_ATTRIBUTES       := null                                                       ;
PARAM_TYPE             := 1                                                          ;
PARAM_VALUE_STR        := '/../images/func_horibargraph_24_ena.png'                  ;
PARAM_VALUE_CLOB       := null                                                       ;
TENANT_ID              := '&TENANT_ID'                                               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3021                                                     ;
NAME                   := 'WIDGET_VIEWMODEL'                                       ;
PARAM_ATTRIBUTES       := null                                                     ;
PARAM_TYPE             := 1                                                        ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/js/VisualizationWidget.js'  ;
PARAM_VALUE_CLOB       := null                                                     ;
TENANT_ID              := '&TENANT_ID'                                             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3021                    ;
NAME                   := 'TIME_PERIOD_KEY'       ;
PARAM_ATTRIBUTES       := null                    ;
PARAM_TYPE             := 1                       ;
PARAM_VALUE_STR        := '24'                    ;
PARAM_VALUE_CLOB       := null                    ;
TENANT_ID              := '&TENANT_ID'            ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3021;
NAME                   :='TA_CRITERIA';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=2;
PARAM_VALUE_STR        :=null;
PARAM_VALUE_CLOB       :='{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"string","displayName":"Target Status","displayDetails":null,"role":"column","func":null,"UIProperties":{"useForSort":false,"ignoreCase":true},"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_Jvm_heapUsed.value","_value":null,"dataType":"number","displayName":"Heap Usage (MB)","displayDetails":"Load ","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},'||
'{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":true},"targetType":"weblogic_j2eeserver","unitStr":"n/a","mcName":"heapUsed.value","mgName":"Jvm","mgDisplayName":"Load ","isKey":0,"groupKeyColumns":[],"isConfig":0,"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_JvmMemoryPools_usedNowInMBytes","_value":null,"dataType":"number","displayName":"Memory Pool - Current Usage (MB)","displayDetails":"Load ","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"}'||',{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"weblogic_j2eeserver","unitStr":"MB","mcName":"usedNowInMBytes","mgName":"JvmMemoryPools","mgDisplayName":"Load ","isKey":0,"groupKeyColumns":["name"],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_JvmGarbageCollectors_invocationsPerMin","_value":null,"dataType":"number","displayName":"Garbage Collector - Invocations (per min)","displayDetails":"Load ","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},'||
'{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"weblogic_j2eeserver","unitStr":"operations per minute","mcName":"invocationsPerMin","mgName":"JvmGarbageCollectors","mgDisplayName":"Load ","isKey":0,"groupKeyColumns":["name"],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_JvmGarbageCollectors_msecsPerInvocation","_value":null,"dataType":"number","displayName":"Garbage Collector - Invocation Time (ms)","displayDetails":"Response ","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"weblogic_j2eeserver","unitStr":"ms","mcName":"msecsPerInvocation","mgName":"JvmGarbageCollectors","mgDisplayName":"Response ","isKey":0,"groupKeyColumns":["name"],"isConfig":0}}]}';
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3021                      ;
NAME                   :='PROVIDER_VERSION'        ;
PARAM_ATTRIBUTES       :=null                      ;
PARAM_TYPE             :=1                         ;
PARAM_VALUE_STR        :='1.0'                     ;
PARAM_VALUE_CLOB       :=null                      ;
TENANT_ID              :='&TENANT_ID'              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3021                             ;
NAME                   :='WIDGET_KOC_NAME'                ;
PARAM_ATTRIBUTES       :=null                             ;
PARAM_TYPE             :=1                                ;
PARAM_VALUE_STR        :='emcta-visualization'            ;
PARAM_VALUE_CLOB       :=null                             ;
TENANT_ID              :='&TENANT_ID'                     ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3021                         ;
NAME                   :='VISUALIZATION_TYPE_KEY'     ;
PARAM_ATTRIBUTES       :=null                         ;
PARAM_TYPE             :=1                            ;
PARAM_VALUE_STR        :='TABLE'                      ;
PARAM_VALUE_CLOB       :=null                         ;
TENANT_ID              :='&TENANT_ID'                 ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3021                      ;
NAME                   :='PROVIDER_ASSET_ROOT'     ;
PARAM_ATTRIBUTES       :=null                      ;
PARAM_TYPE             :=1                         ;
PARAM_VALUE_STR        :='assetRoot'               ;
PARAM_VALUE_CLOB       :=null                      ;
TENANT_ID              :='&TENANT_ID'              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3021                ;
NAME                   :='WIDGET_SOURCE'     ;
PARAM_ATTRIBUTES       :=null                ;
PARAM_TYPE             :=1                   ;
PARAM_VALUE_STR        :='1'                 ;
PARAM_VALUE_CLOB       :=null                ;
TENANT_ID              :='&TENANT_ID'        ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3021                     ;
NAME                   :='PROVIDER_NAME'          ;
PARAM_ATTRIBUTES       :=null                     ;
PARAM_TYPE             :=1                        ;
PARAM_VALUE_STR        :='TargetAnalytics'        ;
PARAM_VALUE_CLOB       :=null                     ;
TENANT_ID              :='&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3021                      ;
NAME                   :='TOP_N_ROWS'              ;
PARAM_ATTRIBUTES       :=null                      ;
PARAM_TYPE             :=1                         ;
PARAM_VALUE_STR        :='25'                      ;
PARAM_VALUE_CLOB       :=null                      ;
TENANT_ID              :='&TENANT_ID'              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3021                        ;
NAME                   :='TA_HORI_SORT_ORDER'        ;
PARAM_ATTRIBUTES       :=null                        ;
PARAM_TYPE             :=1                           ;
PARAM_VALUE_STR        :='true'                      ;
PARAM_VALUE_CLOB       :=null                        ;
TENANT_ID              :='&TENANT_ID'                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3021                        ;
NAME                   :='TA_HORI_SORT_COLUMN'       ;
PARAM_ATTRIBUTES       :=null                        ;
PARAM_TYPE             :=1                           ;
PARAM_VALUE_STR        :='1'                         ;
PARAM_VALUE_CLOB       :=null                        ;
TENANT_ID              :='&TENANT_ID'                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3022;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data Top 25 WebLogic Servers by Load has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                  :=  3022                                                                                                                                                  ;
NAME                       :=  'Top 25 WebLogic Servers by Load'                                                                                                                     ;
OWNER                      :=  'ORACLE'                                                                                                                                              ;
CREATION_DATE              :=  SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                                                                         ;
LAST_MODIFICATION_DATE     :=  SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                                                                         ;
LAST_MODIFIED_BY           :=  'ORACLE'                                                                                                                                              ;
DESCRIPTION                :=  'Shows the status, CPU Usage, Active Sessions, Request Processing Time, Requests per minute and Heap Usage for Top 25 WebLogic Servers (by Load)'     ;
FOLDER_ID                  :=  4                                                                                                                                                     ;
CATEGORY_ID                :=  2                                                                                                                                                     ;
NAME_NLSID                 :=  null                                                                                                                                                  ;
NAME_SUBSYSTEM             :=  null                                                                                                                                                  ;
DESCRIPTION_NLSID          :=  null                                                                                                                                                  ;
DESCRIPTION_SUBSYSTEM      :=  null                                                                                                                                                  ;
SYSTEM_SEARCH              :=  1                                                                                                                                                     ;
EM_PLUGIN_ID               :=  null                                                                                                                                                  ;
IS_LOCKED                  :=  0                                                                                                                                                     ;
METADATA_CLOB              :=  null                                                                                                                                                  ;
SEARCH_DISPLAY_STR         :=  ''                                                                                                                                                    ;
UI_HIDDEN                  :=  0                                                                                                                                                     ;
TENANT_ID                  :=  '&TENANT_ID'                                                                                                                                          ;
IS_WIDGET                  :=  1                                                                                                                                                     ;
Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
);

SEARCH_ID              :=3022;
NAME                   :='WIDGET_TEMPLATE';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='/widget/visualizationWidget/visualizationWidget.html';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3022;
NAME                   :='WIDGET_ICON';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='/../images/func_horibargraph_24_ena.png';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3022                                                                ;
NAME                   :='WIDGET_VIEWMODEL'                                                  ;
PARAM_ATTRIBUTES       :=null                                                                ;
PARAM_TYPE             :=1                                                                   ;
PARAM_VALUE_STR        :='/widget/visualizationWidget/js/VisualizationWidget.js'             ;
PARAM_VALUE_CLOB       :=null                                                                ;
TENANT_ID              :='&TENANT_ID'                                                        ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3022;
NAME                   :='TIME_PERIOD_KEY'                                    ;
PARAM_ATTRIBUTES       :=null                                                 ;
PARAM_TYPE             :=1                                                    ;
PARAM_VALUE_STR        :='24'                                                 ;
PARAM_VALUE_CLOB       :=null                                                 ;
TENANT_ID              :='&TENANT_ID'                                         ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3022;
NAME                   :='TA_CRITERIA';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=2;
PARAM_VALUE_STR        :=null;
PARAM_VALUE_CLOB       :='{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"string","displayName":"Target Status","displayDetails":null,"role":"column","func":null,"UIProperties":{"useForSort":false,"ignoreCase":true},"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_Jvm_cpuUsage.percentage","_value":null,"dataType":"number","displayName":"CPU Usage (%)","displayDetails":"Utilization ","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},'||
'{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":true},"targetType":"weblogic_j2eeserver","unitStr":"n/a","mcName":"cpuUsage.percentage","mgName":"Jvm","mgDisplayName":"Utilization ","isKey":0,"groupKeyColumns":[],"isConfig":0,"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_ServerModule_session.Active","_value":null,"dataType":"number","displayName":"Active Sessions","displayDetails":"Load ","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false}'||
',"targetType":"weblogic_j2eeserver","unitStr":"n/a","mcName":"session.Active","mgName":"ServerModule","mgDisplayName":"Load ","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_ServerServletJsp_service.Time","_value":null,"dataType":"number","displayName":"Request Processing Time (ms)","displayDetails":"Response ","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"weblogic_j2eeserver","unitStr":"ms","mcName":"service.Time","mgName":"ServerServletJsp","mgDisplayName":"Response ","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_ServerServletJsp_service.Throughput","_value":null,"dataType":"number","displayName":"Requests (per minute)","displayDetails":"Load ",'||
'"role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"weblogic_j2eeserver","unitStr":"operations per minute","mcName":"service.Throughput","mgName":"ServerServletJsp","mgDisplayName":"Load ","isKey":0,"groupKeyColumns":[],"isConfig":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_Jvm_heapUsed.value","_value":null,"dataType":"number","displayName":"Heap Usage (MB)","displayDetails":"Load ","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"weblogic_j2eeserver","unitStr":"n/a","mcName":"heapUsed.value","mgName":"Jvm","mgDisplayName":"Load ","isKey":0,"groupKeyColumns":[],"isConfig":0}}]}';
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3022                      ;
NAME                   :='PROVIDER_VERSION'        ;
PARAM_ATTRIBUTES       :=null                      ;
PARAM_TYPE             :=1                         ;
PARAM_VALUE_STR        :='1.0'                     ;
PARAM_VALUE_CLOB       :=null                      ;
TENANT_ID              :='&TENANT_ID'              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3022                     ;
NAME                   :='WIDGET_KOC_NAME'        ;
PARAM_ATTRIBUTES       :=null                     ;
PARAM_TYPE             :=1                        ;
PARAM_VALUE_STR        :='emcta-visualization'    ;
PARAM_VALUE_CLOB       :=null                     ;
TENANT_ID              :='&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3022                         ;
NAME                   :='VISUALIZATION_TYPE_KEY'     ;
PARAM_ATTRIBUTES       :=null                         ;
PARAM_TYPE             :=1                            ;
PARAM_VALUE_STR        :='TABLE'                      ;
PARAM_VALUE_CLOB       :=null                         ;
TENANT_ID              :='&TENANT_ID'                 ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3022                       ;
NAME                   :='PROVIDER_ASSET_ROOT'      ;
PARAM_ATTRIBUTES       :=null                       ;
PARAM_TYPE             :=1                          ;
PARAM_VALUE_STR        :='assetRoot'                ;
PARAM_VALUE_CLOB       :=null                       ;
TENANT_ID              :='&TENANT_ID'               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3022                      ;
NAME                   :='WIDGET_SOURCE'           ;
PARAM_ATTRIBUTES       :=null                      ;
PARAM_TYPE             :=1                         ;
PARAM_VALUE_STR        :='1'                       ;
PARAM_VALUE_CLOB       :=null                      ;
TENANT_ID              :='&TENANT_ID'              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3022                       ;
NAME                   :='PROVIDER_NAME'            ;
PARAM_ATTRIBUTES       :=null                       ;
PARAM_TYPE             :=1                          ;
PARAM_VALUE_STR        :='TargetAnalytics'          ;
PARAM_VALUE_CLOB       :=null                       ;
TENANT_ID              :='&TENANT_ID'               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3022                   ;
NAME                   :='TOP_N_ROWS'           ;
PARAM_ATTRIBUTES       :=null                   ;
PARAM_TYPE             :=1                      ;
PARAM_VALUE_STR        :='25'                   ;
PARAM_VALUE_CLOB       :=null                   ;
TENANT_ID              :='&TENANT_ID'           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3022                     ;
NAME                   :='TA_HORI_SORT_ORDER'     ;
PARAM_ATTRIBUTES       :=null                     ;
PARAM_TYPE             :=1                        ;
PARAM_VALUE_STR        :='true'                   ;
PARAM_VALUE_CLOB       :=null                     ;
TENANT_ID              :='&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3022                     ;
NAME                   :='TA_HORI_SORT_COLUMN'    ;
PARAM_ATTRIBUTES       :=null                     ;
PARAM_TYPE             :=1                        ;
PARAM_VALUE_STR        :='1'                      ;
PARAM_VALUE_CLOB       :=null                     ;
TENANT_ID              :='&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3023;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data WebLogic Servers by JDK Version has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                  :=  3023                                      ;
NAME                       :=  'WebLogic Servers by JDK Version'         ;
OWNER                      :=  'ORACLE'                                  ;
CREATION_DATE              :=  SYS_EXTRACT_UTC(SYSTIMESTAMP)             ;
LAST_MODIFICATION_DATE     :=  SYS_EXTRACT_UTC(SYSTIMESTAMP)             ;
LAST_MODIFIED_BY           :=  'ORACLE'                                  ;
DESCRIPTION                :=  'Shows the Java VM Vendor, Target Version and Platform for WebLogic Servers grouped by Java version';
FOLDER_ID                  :=  4                                         ;
CATEGORY_ID                :=  2                                         ;
NAME_NLSID                 :=  null                                      ;
NAME_SUBSYSTEM             :=  null                                      ;
DESCRIPTION_NLSID          :=  null                                      ;
DESCRIPTION_SUBSYSTEM      :=  null                                      ;
SYSTEM_SEARCH              :=  1                                         ;
EM_PLUGIN_ID               :=  null                                      ;
IS_LOCKED                  :=  0                                         ;
METADATA_CLOB              :=  null                                      ;
SEARCH_DISPLAY_STR         :=  ''                                        ;
UI_HIDDEN                  :=  0                                         ;
TENANT_ID                  :=  '&TENANT_ID'                              ;
IS_WIDGET                  :=  1                                         ;
Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
TENANT_ID,
IS_WIDGET
);

SEARCH_ID              := 3023                                                       ;
NAME                   := 'WIDGET_TEMPLATE'                                          ;
PARAM_ATTRIBUTES       := null                                                       ;
PARAM_TYPE             := 1                                                          ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/visualizationWidget.html'     ;
PARAM_VALUE_CLOB       := null                                                       ;
TENANT_ID              := '&TENANT_ID'                                               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3023                                            ;
NAME                   := 'WIDGET_ICON'                                   ;
PARAM_ATTRIBUTES       := null                                            ;
PARAM_TYPE             := 1                                               ;
PARAM_VALUE_STR        := '/../images/func_horibargraph_24_ena.png'       ;
PARAM_VALUE_CLOB       := null                                            ;
TENANT_ID              := '&TENANT_ID'                                    ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3023                                                       ;
NAME                   := 'WIDGET_VIEWMODEL'                                         ;
PARAM_ATTRIBUTES       := null                                                       ;
PARAM_TYPE             := 1                                                          ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/js/VisualizationWidget.js'    ;
PARAM_VALUE_CLOB       := null                                                       ;
TENANT_ID              := '&TENANT_ID'                                               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3023                    ;
NAME                   := 'TIME_PERIOD_KEY'       ;
PARAM_ATTRIBUTES       := null                    ;
PARAM_TYPE             := 1                       ;
PARAM_VALUE_STR        := '24'                    ;
PARAM_VALUE_CLOB       := null                    ;
TENANT_ID              := '&TENANT_ID'            ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3023                               ;
NAME                   := 'TA_CRITERIA'                      ;
PARAM_ATTRIBUTES       := null                               ;
PARAM_TYPE             := 2                                  ;
PARAM_VALUE_STR        := null                               ;
PARAM_VALUE_CLOB       := '{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"weblogic_j2eeserver","metadata":{"category":false},"displayValue":"Oracle WebLogic Server"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_WeblogicServer_javavmvendor","_value":null,"dataType":"string","displayName":"Java VM Vendor","displayDetails":"Server Information","role":"column","func":null,"UIProperties":{"useForSort":false,"ignoreCase":true},"targetType":"weblogic_j2eeserver","unitStr":null,"mcName":"javavmvendor","mgName":"WeblogicServer","mgDisplayName":"Server Information","isKey":0,"groupKeyColumns":[],"isConfig":1,"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"PropertySearchCriterion","data":{"id":"property.orcl_gtp_target_version","_value":null,"dataType":"string","displayName":"Target Version","displayDetails":null,"role":"column","func":null,"UIProperties":{"useForSort":false},"targetType":"weblogic_j2eeserver","propertyName":"orcl_gtp_target_version"}},{"jsonConstructor":"PropertySearchCriterion","data":{"id":"property.orcl_gtp_platform","_value":null,"dataType":"string","displayName":"Platform","displayDetails":null,"role":"column","func":null,"UIProperties":{"useForSort":false},"targetType":"weblogic_j2eeserver","propertyName":"orcl_gtp_platform"}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"weblogic_j2eeserver_WeblogicServer_javaversion","_value":null,"dataType":"string","displayName":"Java Version","displayDetails":"Server Information","role":"group by","func":null,"UIProperties":{"useForSort":true,"ignoreCase":true},"targetType":"weblogic_j2eeserver","unitStr":null,"mcName":"javaversion","mgName":"WeblogicServer","mgDisplayName":"Server Information","isKey":0,"groupKeyColumns":[],"isConfig":1,"direction":"DESC","inverseNullOrderBy":true}}]}';
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3023              ;
NAME                   := 'PROVIDER_VERSION';
PARAM_ATTRIBUTES       := null              ;
PARAM_TYPE             := 1                 ;
PARAM_VALUE_STR        := '1.0'             ;
PARAM_VALUE_CLOB       := null              ;
TENANT_ID              := '&TENANT_ID'      ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3023                  ;
NAME                   := 'WIDGET_KOC_NAME'     ;
PARAM_ATTRIBUTES       := null                  ;
PARAM_TYPE             := 1                     ;
PARAM_VALUE_STR        := 'emcta-visualization' ;
PARAM_VALUE_CLOB       := null                  ;
TENANT_ID              := '&TENANT_ID'          ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3023                        ;
NAME                   := 'VISUALIZATION_TYPE_KEY'    ;
PARAM_ATTRIBUTES       := null                        ;
PARAM_TYPE             := 1                           ;
PARAM_VALUE_STR        := 'TABLE'                     ;
PARAM_VALUE_CLOB       := null                        ;
TENANT_ID              := '&TENANT_ID'                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3023                     ;
NAME                   := 'PROVIDER_ASSET_ROOT'    ;
PARAM_ATTRIBUTES       := null                     ;
PARAM_TYPE             := 1                        ;
PARAM_VALUE_STR        := 'assetRoot'              ;
PARAM_VALUE_CLOB       := null                     ;
TENANT_ID              := '&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3023                  ;
NAME                   := 'WIDGET_SOURCE'       ;
PARAM_ATTRIBUTES       := null                  ;
PARAM_TYPE             := 1                     ;
PARAM_VALUE_STR        := '1'                   ;
PARAM_VALUE_CLOB       := null                  ;
TENANT_ID              := '&TENANT_ID'          ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3023                    ;
NAME                   := 'PROVIDER_NAME'         ;
PARAM_ATTRIBUTES       := null                    ;
PARAM_TYPE             := 1                       ;
PARAM_VALUE_STR        := 'TargetAnalytics'       ;
PARAM_VALUE_CLOB       := null                    ;
TENANT_ID              := '&TENANT_ID'            ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3023                   ;
NAME                   := 'TOP_N_ROWS'           ;
PARAM_ATTRIBUTES       := null                   ;
PARAM_TYPE             := 1                      ;
PARAM_VALUE_STR        := '100'                  ;
PARAM_VALUE_CLOB       := null                   ;
TENANT_ID              := '&TENANT_ID'           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3023                     ;
NAME                   := 'TA_HORI_SORT_ORDER'     ;
PARAM_ATTRIBUTES       := null                     ;
PARAM_TYPE             := 1                        ;
PARAM_VALUE_STR        := 'true'                   ;
PARAM_VALUE_CLOB       := null                     ;
TENANT_ID              := '&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3023                   ;
NAME                   := 'TA_HORI_SORT_COLUMN'  ;
PARAM_ATTRIBUTES       := null                   ;
PARAM_TYPE             := 1                      ;
PARAM_VALUE_STR        := '-2'                   ;
PARAM_VALUE_CLOB       := null                   ;
TENANT_ID              := '&TENANT_ID'           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3024;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data Top 5 Databases has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID     			:= 3024                                                                                                       ;
NAME     				:= 'Top 5 Databases'                                                                                          ;
OWNER     				:= 'ORACLE'                                                                                                   ;
CREATION_DATE     		:= SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                              ;
LAST_MODIFICATION_DATE  := SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                              ;
LAST_MODIFIED_BY     	:= 'ORACLE'                                                                                                   ;
DESCRIPTION     		:= 'Shows the status, Database CPU Time, I/O Megabytes per second and Total Memory Usage for Top 5 Databases' ;
FOLDER_ID     			:= 4                                                                                                          ;
CATEGORY_ID     		:= 2                                                                                                          ;
NAME_NLSID     			:= null                                                                                                       ;
NAME_SUBSYSTEM     		:= null                                                                                                       ;
DESCRIPTION_NLSID     	:= null                                                                                                       ;
DESCRIPTION_SUBSYSTEM   := null                                                                                                       ;
SYSTEM_SEARCH     		:= 1                                                                                                          ;
EM_PLUGIN_ID     		:= null                                                                                                       ;
IS_LOCKED     			:= 0                                                                                                          ;
METADATA_CLOB     		:= null                                                                                                       ;
SEARCH_DISPLAY_STR     	:=  null                                                                                                      ;
UI_HIDDEN     			:= 0                                                                                                          ;
DELETED     			:= 0                                                                                                          ;
IS_WIDGET     			:= 1                                                                                                          ;
TENANT_ID				:='&TENANT_ID'																								  ;
Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID
);
SEARCH_ID              :=3024                ;
NAME                   :='WIDGET_VISUAL'     ;
PARAM_ATTRIBUTES       :=null                ;
PARAM_TYPE             :=2                   ;
PARAM_VALUE_STR        :=null                ;
PARAM_VALUE_CLOB       :=screenshot_3024     ;
TENANT_ID              :='&TENANT_ID'        ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3024                                       ;
NAME                   :='WIDGET_ICON'                              ;
PARAM_ATTRIBUTES       :=null                                       ;
PARAM_TYPE             :=1                                          ;
PARAM_VALUE_STR        :='/../images/func_horibargraph_24_ena.png'  ;
PARAM_VALUE_CLOB       :=null                                       ;
TENANT_ID              :='&TENANT_ID'                               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3024                                                  ;
NAME                   :='WIDGET_TEMPLATE'                                     ;
PARAM_ATTRIBUTES       :=null                                                  ;
PARAM_TYPE             :=1                                                     ;
PARAM_VALUE_STR        :='/widget/visualizationWidget/visualizationWidget.html';
PARAM_VALUE_CLOB       :=null                                                  ;
TENANT_ID              :='&TENANT_ID'                                          ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3024                ;
NAME                   :='PROVIDER_NAME'     ;
PARAM_ATTRIBUTES       :=null                ;
PARAM_TYPE             :=1                   ;
PARAM_VALUE_STR        :='TargetAnalytics'   ;
PARAM_VALUE_CLOB       :=null                ;
TENANT_ID              :='&TENANT_ID'        ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3024                    ;
NAME                   :='TA_HORI_SORT_ORDER'    ;
PARAM_ATTRIBUTES       :=null                    ;
PARAM_TYPE             :=1                       ;
PARAM_VALUE_STR        :='true'                  ;
PARAM_VALUE_CLOB       :=null                    ;
TENANT_ID              :='&TENANT_ID'            ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3024                  ;
NAME                   :='TOP_N_ROWS'          ;
PARAM_ATTRIBUTES       :=null                  ;
PARAM_TYPE             :=1                     ;
PARAM_VALUE_STR        :='5'                   ;
PARAM_VALUE_CLOB       :=null                  ;
TENANT_ID              :='&TENANT_ID'          ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3024                   ;
NAME                   :='WIDGET_SOURCE'        ;
PARAM_ATTRIBUTES       :=null                   ;
PARAM_TYPE             :=1                      ;
PARAM_VALUE_STR        :='1'                    ;
PARAM_VALUE_CLOB       :=null                   ;
TENANT_ID              :='&TENANT_ID'           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3024                       ;
NAME                   :='TA_HORI_SORT_COLUMN'      ;
PARAM_ATTRIBUTES       :=null                       ;
PARAM_TYPE             :=1                          ;
PARAM_VALUE_STR        :='1'                        ;
PARAM_VALUE_CLOB       :=null                       ;
TENANT_ID              :='&TENANT_ID'               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3024                        ;
NAME                   :='PROVIDER_ASSET_ROOT'       ;
PARAM_ATTRIBUTES       :=null                        ;
PARAM_TYPE             :=1                           ;
PARAM_VALUE_STR        :='assetRoot'                 ;
PARAM_VALUE_CLOB       :=null                        ;
TENANT_ID              :='&TENANT_ID'                ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3024                 ;
NAME                   :='TA_CRITERIA'        ;
PARAM_ATTRIBUTES       :=null                 ;
PARAM_TYPE             :=2                    ;
PARAM_VALUE_STR        :=null                 ;
PARAM_VALUE_CLOB       :=ta_criteria_3024     ;
TENANT_ID              :='&TENANT_ID'         ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3024                            ;
NAME                   :='VISUALIZATION_TYPE_KEY'        ;
PARAM_ATTRIBUTES       :=null                            ;
PARAM_TYPE             :=1                               ;
PARAM_VALUE_STR        :='TABLE'                         ;
PARAM_VALUE_CLOB       :=null                            ;
TENANT_ID              :='&TENANT_ID'                    ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3024                             ;
NAME                   :='PROVIDER_VERSION'               ;
PARAM_ATTRIBUTES       :=null                             ;
PARAM_TYPE             :=1                                ;
PARAM_VALUE_STR        :='1.0'                            ;
PARAM_VALUE_CLOB       :=null                             ;
TENANT_ID              :='&TENANT_ID'                     ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3024                                                      ;
NAME                   :='WIDGET_VIEWMODEL'                                        ;
PARAM_ATTRIBUTES       :=null                                                      ;
PARAM_TYPE             :=1                                                         ;
PARAM_VALUE_STR        :='/widget/visualizationWidget/js/VisualizationWidget.js'   ;
PARAM_VALUE_CLOB       :=null                                                      ;
TENANT_ID              :='&TENANT_ID'                                              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3024                     ;
NAME                   :='WIDGET_KOC_NAME'        ;
PARAM_ATTRIBUTES       :=null                     ;
PARAM_TYPE             :=1                        ;
PARAM_VALUE_STR        :='emcta-visualization'    ;
PARAM_VALUE_CLOB       :=null                     ;
TENANT_ID              :='&TENANT_ID'             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3025;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data Top 5 Hosts has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                           :=3025                                                                                                  ;
NAME                                :='Top 5 Hosts'                                                                                         ;
OWNER                               :='ORACLE'                                                                                              ;
CREATION_DATE                       :=SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                         ;
LAST_MODIFICATION_DATE              :=SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                         ;
LAST_MODIFIED_BY                    :='ORACLE'                                                                                              ;
DESCRIPTION                         :='Shows the status, CPU Utilization, Total Disk I/O per second and Memory Utilization for Top 5 Hosts' ;
FOLDER_ID                           :=4                                                                                                     ;
CATEGORY_ID                         :=2                                                                                                     ;
NAME_NLSID                          :=null                                                                                                  ;
NAME_SUBSYSTEM                      :=null                                                                                                  ;
DESCRIPTION_NLSID                   :=null                                                                                                  ;
DESCRIPTION_SUBSYSTEM               :=null                                                                                                  ;
SYSTEM_SEARCH                       :=1                                                                                                     ;
EM_PLUGIN_ID                        :=null                                                                                                  ;
IS_LOCKED                           :=0                                                                                                     ;
METADATA_CLOB                       :=null                                                                                                  ;
SEARCH_DISPLAY_STR                  := null                                                                                                 ;
UI_HIDDEN                           :=0                                                                                                     ;
DELETED                             :=0                                                                                                     ;
IS_WIDGET                           :=1                                                                                                     ;
TENANT_ID                           :='&TENANT_ID'                                                                                          ;
Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID
);
SEARCH_ID              :=3025;
NAME                   :='PROVIDER_VERSION';
PARAM_ATTRIBUTES       :=null;
PARAM_TYPE             :=1;
PARAM_VALUE_STR        :='1.0';
PARAM_VALUE_CLOB       :=null;
TENANT_ID              :='&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3025                    ;
NAME                   :='TA_HORI_SORT_COLUMN'   ;
PARAM_ATTRIBUTES       :=null                    ;
PARAM_TYPE             :=1                       ;
PARAM_VALUE_STR        :='1'                     ;
PARAM_VALUE_CLOB       :=null                    ;
TENANT_ID              :='&TENANT_ID'            ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3025                ;
NAME                   :='TOP_N_ROWS'        ;
PARAM_ATTRIBUTES       :=null                ;
PARAM_TYPE             :=1                   ;
PARAM_VALUE_STR        :='5'                 ;
PARAM_VALUE_CLOB       :=null                ;
TENANT_ID              :='&TENANT_ID'        ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3025                            ;
NAME                   :='VISUALIZATION_TYPE_KEY'        ;
PARAM_ATTRIBUTES       :=null                            ;
PARAM_TYPE             :=1                               ;
PARAM_VALUE_STR        :='TABLE'                         ;
PARAM_VALUE_CLOB       :=null                            ;
TENANT_ID              :='&TENANT_ID'                    ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3025                          ;
NAME                   :='WIDGET_KOC_NAME'             ;
PARAM_ATTRIBUTES       :=null                          ;
PARAM_TYPE             :=1                             ;
PARAM_VALUE_STR        :='emcta-visualization'         ;
PARAM_VALUE_CLOB       :=null                          ;
TENANT_ID              :='&TENANT_ID'                  ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3025                                                      ;
NAME                   :='WIDGET_VIEWMODEL'                                        ;
PARAM_ATTRIBUTES       :=null                                                      ;
PARAM_TYPE             :=1                                                         ;
PARAM_VALUE_STR        :='/widget/visualizationWidget/js/VisualizationWidget.js'   ;
PARAM_VALUE_CLOB       :=null                                                      ;
TENANT_ID              :='&TENANT_ID'                                              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3025             ;
NAME                   :='TA_CRITERIA'    ;
PARAM_ATTRIBUTES       :=null             ;
PARAM_TYPE             :=2                ;
PARAM_VALUE_STR        :=null             ;
PARAM_VALUE_CLOB       :=ta_criteria_3025 ;
TENANT_ID              :='&TENANT_ID'     ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3025                                                     ;
NAME                   :='WIDGET_TEMPLATE'                                        ;
PARAM_ATTRIBUTES       :=null                                                     ;
PARAM_TYPE             :=1                                                        ;
PARAM_VALUE_STR        :='/widget/visualizationWidget/visualizationWidget.html'   ;
PARAM_VALUE_CLOB       :=null                                                     ;
TENANT_ID              :='&TENANT_ID'                                             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3025                 ;
NAME                   :='PROVIDER_NAME'      ;
PARAM_ATTRIBUTES       :=null                 ;
PARAM_TYPE             :=1                    ;
PARAM_VALUE_STR        :='TargetAnalytics'    ;
PARAM_VALUE_CLOB       :=null                 ;
TENANT_ID              :='&TENANT_ID'         ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3025                   ;
NAME                   :='WIDGET_VISUAL'        ;
PARAM_ATTRIBUTES       :=null                   ;
PARAM_TYPE             :=2                      ;
PARAM_VALUE_STR        :=null                   ;
PARAM_VALUE_CLOB       :=screenshot_3025        ;
TENANT_ID              :='&TENANT_ID'           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3025                    ;
NAME                   :='TA_HORI_SORT_ORDER'    ;
PARAM_ATTRIBUTES       :=null                    ;
PARAM_TYPE             :=1                       ;
PARAM_VALUE_STR        :='true'                  ;
PARAM_VALUE_CLOB       :=null                    ;
TENANT_ID              :='&TENANT_ID'            ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3025                      ;
NAME                   :='PROVIDER_ASSET_ROOT'     ;
PARAM_ATTRIBUTES       :=null                      ;
PARAM_TYPE             :=1                         ;
PARAM_VALUE_STR        :='assetRoot'               ;
PARAM_VALUE_CLOB       :=null                      ;
TENANT_ID              :='&TENANT_ID'              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3025                                      ;
NAME                   :='WIDGET_ICON'                             ;
PARAM_ATTRIBUTES       :=null                                      ;
PARAM_TYPE             :=1                                         ;
PARAM_VALUE_STR        :='/../images/func_horibargraph_24_ena.png' ;
PARAM_VALUE_CLOB       :=null                                      ;
TENANT_ID              :='&TENANT_ID'                              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              :=3025                 ;
NAME                   :='WIDGET_SOURCE'      ;
PARAM_ATTRIBUTES       :=null                 ;
PARAM_TYPE             :=1                    ;
PARAM_VALUE_STR        :='1'                  ;
PARAM_VALUE_CLOB       :=null                 ;
TENANT_ID              :='&TENANT_ID'         ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3026;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data Top 5 WebLogic Servers has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                           :=3026                                                                                     ;
NAME                                :='Top 5 WebLogic Servers'                                                                 ;
OWNER                               :='ORACLE'                                                                                 ;
CREATION_DATE                       :=SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                            ;
LAST_MODIFICATION_DATE              :=SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                            ;
LAST_MODIFIED_BY                    :='ORACLE'                                                                                 ;
DESCRIPTION                         :='Shows the status, CPU Usage, Active Sessions and Heap Usage for Top 5 WebLogic Servers' ;
FOLDER_ID                           :=4                                                                                        ;
CATEGORY_ID                         :=2                                                                                        ;
NAME_NLSID                          :=null                                                                                     ;
NAME_SUBSYSTEM                      :=null                                                                                     ;
DESCRIPTION_NLSID                   :=null                                                                                     ;
DESCRIPTION_SUBSYSTEM               :=null                                                                                     ;
SYSTEM_SEARCH                       :=1                                                                                        ;
EM_PLUGIN_ID                        :=null                                                                                     ;
IS_LOCKED                           :=0                                                                                        ;
METADATA_CLOB                       :=null                                                                                     ;
SEARCH_DISPLAY_STR                  := null                                                                                    ;
UI_HIDDEN                           :=0                                                                                        ;
DELETED                             :=0                                                                                        ;
IS_WIDGET                           :=1                                                                                        ;
TENANT_ID                           :='&TENANT_ID'                                                                             ;
Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID
);

SEARCH_ID              := 3026                                                    ;
NAME                   := 'WIDGET_TEMPLATE'                                       ;
PARAM_ATTRIBUTES       := null                                                    ;
PARAM_TYPE             := 1                                                       ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/visualizationWidget.html'  ;
PARAM_VALUE_CLOB       := null                                                    ;
TENANT_ID              := '&TENANT_ID'                                            ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3026              ;
NAME                   := 'TOP_N_ROWS'      ;
PARAM_ATTRIBUTES       := null              ;
PARAM_TYPE             := 1                 ;
PARAM_VALUE_STR        := '5'               ;
PARAM_VALUE_CLOB       := null              ;
TENANT_ID              := '&TENANT_ID'      ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3026              ;
NAME                   := 'PROVIDER_VERSION';
PARAM_ATTRIBUTES       := null              ;
PARAM_TYPE             := 1                 ;
PARAM_VALUE_STR        := '1.0'             ;
PARAM_VALUE_CLOB       := null              ;
TENANT_ID              := '&TENANT_ID'      ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3026                       ;
NAME                   := 'PROVIDER_ASSET_ROOT'      ;
PARAM_ATTRIBUTES       := null                       ;
PARAM_TYPE             := 1                          ;
PARAM_VALUE_STR        := 'assetRoot'                ;
PARAM_VALUE_CLOB       := null                       ;
TENANT_ID              := '&TENANT_ID'               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3026                    ;
NAME                   := 'WIDGET_KOC_NAME'       ;
PARAM_ATTRIBUTES       := null                    ;
PARAM_TYPE             := 1                       ;
PARAM_VALUE_STR        := 'emcta-visualization'   ;
PARAM_VALUE_CLOB       := null                    ;
TENANT_ID              := '&TENANT_ID'            ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3026                                       ;
NAME                   := 'WIDGET_ICON'                              ;
PARAM_ATTRIBUTES       := null                                       ;
PARAM_TYPE             := 1                                          ;
PARAM_VALUE_STR        := '/../images/func_horibargraph_24_ena.png'  ;
PARAM_VALUE_CLOB       := null                                       ;
TENANT_ID              := '&TENANT_ID'                               ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3026                ;
NAME                   := 'WIDGET_SOURCE'     ;
PARAM_ATTRIBUTES       := null                ;
PARAM_TYPE             := 1                   ;
PARAM_VALUE_STR        := '1'                 ;
PARAM_VALUE_CLOB       := null                ;
TENANT_ID              := '&TENANT_ID'        ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3026                  ;
NAME                   := 'PROVIDER_NAME'       ;
PARAM_ATTRIBUTES       := null                  ;
PARAM_TYPE             := 1                     ;
PARAM_VALUE_STR        := 'TargetAnalytics'     ;
PARAM_VALUE_CLOB       := null                  ;
TENANT_ID              := '&TENANT_ID'          ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3026                   ;
NAME                   := 'TA_HORI_SORT_ORDER'   ;
PARAM_ATTRIBUTES       := null                   ;
PARAM_TYPE             := 1                      ;
PARAM_VALUE_STR        := 'true'                 ;
PARAM_VALUE_CLOB       := null                   ;
TENANT_ID              := '&TENANT_ID'           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3026                    ;
NAME                   := 'VISUALIZATION_TYPE_KEY';
PARAM_ATTRIBUTES       := null                    ;
PARAM_TYPE             := 1                       ;
PARAM_VALUE_STR        := 'TABLE'                 ;
PARAM_VALUE_CLOB       := null                    ;
TENANT_ID              := '&TENANT_ID'            ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3026                    ;
NAME                   := 'TA_CRITERIA'           ;
PARAM_ATTRIBUTES       := null                    ;
PARAM_TYPE             := 2                       ;
PARAM_VALUE_STR        := null                    ;
PARAM_VALUE_CLOB       := ta_criteria_3026        ;
TENANT_ID              := '&TENANT_ID'            ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3026                                                     ;
NAME                   := 'WIDGET_VIEWMODEL'                                       ;
PARAM_ATTRIBUTES       := null                                                     ;
PARAM_TYPE             := 1                                                        ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/js/VisualizationWidget.js'  ;
PARAM_VALUE_CLOB       := null                                                     ;
TENANT_ID              := '&TENANT_ID'                                             ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3026              ;
NAME                   := 'WIDGET_VISUAL'   ;
PARAM_ATTRIBUTES       := null              ;
PARAM_TYPE             := 2                 ;
PARAM_VALUE_STR        := null              ;
PARAM_VALUE_CLOB       := screenshot_3026   ;
TENANT_ID              := '&TENANT_ID'      ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3026                  ;
NAME                   := 'TA_HORI_SORT_COLUMN' ;
PARAM_ATTRIBUTES       := null                  ;
PARAM_TYPE             := 1                     ;
PARAM_VALUE_STR        := '1'                   ;
PARAM_VALUE_CLOB       := null                  ;
TENANT_ID              := '&TENANT_ID'          ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3027;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data Top 5 Application Deployments has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                           :=3027                                                                                                                   ;
NAME                                :='Top 5 Application Deployments '                                                                                       ;
OWNER                               :='ORACLE'                                                                                                               ;
CREATION_DATE                       :=SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                                          ;
LAST_MODIFICATION_DATE              :=SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                                          ;
LAST_MODIFIED_BY                    :='ORACLE'                                                                                                               ;
DESCRIPTION                         :='Shows the status, Requests per minute, Request Processing Time and Active Sessions for Top 5 Application Deployments' ;
FOLDER_ID                           :=4                                                                                                                      ;
CATEGORY_ID                         :=2                                                                                                                      ;
NAME_NLSID                          :=null                                                                                                                   ;
NAME_SUBSYSTEM                      :=null                                                                                                                   ;
DESCRIPTION_NLSID                   :=null                                                                                                                   ;
DESCRIPTION_SUBSYSTEM               :=null                                                                                                                   ;
SYSTEM_SEARCH                       :=1                                                                                                                      ;
EM_PLUGIN_ID                        :=null                                                                                                                   ;
IS_LOCKED                           :=0                                                                                                                      ;
METADATA_CLOB                       :=null                                                                                                                   ;
SEARCH_DISPLAY_STR                  := null                                                                                                                  ;
UI_HIDDEN                           :=0                                                                                                                      ;
DELETED                             :=0                                                                                                                      ;
IS_WIDGET                           :=1                                                                                                                      ;
TENANT_ID                           :='&TENANT_ID'                                                                                                           ;
Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID
);
SEARCH_ID              := 3027                             ;
NAME                   := 'WIDGET_VISUAL'                  ;
PARAM_ATTRIBUTES       := null                             ;
PARAM_TYPE             := 2                                ;
PARAM_VALUE_STR        := null                             ;
PARAM_VALUE_CLOB       := screenshot_3027                  ;
TENANT_ID              := '&TENANT_ID'                     ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3027                                                    ;
NAME                   := 'WIDGET_VIEWMODEL'                                      ;
PARAM_ATTRIBUTES       := null                                                    ;
PARAM_TYPE             := 1                                                       ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/js/VisualizationWidget.js' ;
PARAM_VALUE_CLOB       := null                                                    ;
TENANT_ID              := '&TENANT_ID'                                            ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3027;
NAME                   := 'WIDGET_SOURCE';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 1;
PARAM_VALUE_STR        := '1';
PARAM_VALUE_CLOB       := null;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3027                         ;
NAME                   := 'VISUALIZATION_TYPE_KEY'     ;
PARAM_ATTRIBUTES       := null                         ;
PARAM_TYPE             := 1                            ;
PARAM_VALUE_STR        := 'TABLE'                      ;
PARAM_VALUE_CLOB       := null                         ;
TENANT_ID              := '&TENANT_ID'                 ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3027                                                  ;
NAME                   := 'WIDGET_TEMPLATE'                                     ;
PARAM_ATTRIBUTES       := null                                                  ;
PARAM_TYPE             := 1                                                     ;
PARAM_VALUE_STR        := '/widget/visualizationWidget/visualizationWidget.html';
PARAM_VALUE_CLOB       := null                                                  ;
TENANT_ID              := '&TENANT_ID'                                          ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3027             ;
NAME                   := 'TOP_N_ROWS'     ;
PARAM_ATTRIBUTES       := null             ;
PARAM_TYPE             := 1                ;
PARAM_VALUE_STR        := '5'              ;
PARAM_VALUE_CLOB       := null             ;
TENANT_ID              := '&TENANT_ID'     ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3027                   ;
NAME                   := 'TA_HORI_SORT_ORDER'   ;
PARAM_ATTRIBUTES       := null                   ;
PARAM_TYPE             := 1                      ;
PARAM_VALUE_STR        := 'true'                 ;
PARAM_VALUE_CLOB       := null                   ;
TENANT_ID              := '&TENANT_ID'           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3027                 ;
NAME                   := 'TA_HORI_SORT_COLUMN';
PARAM_ATTRIBUTES       := null                 ;
PARAM_TYPE             := 1                    ;
PARAM_VALUE_STR        := '1'                  ;
PARAM_VALUE_CLOB       := null                 ;
TENANT_ID              := '&TENANT_ID'         ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3027                 ;
NAME                   := 'TA_CRITERIA'        ;
PARAM_ATTRIBUTES       := null                 ;
PARAM_TYPE             := 2                    ;
PARAM_VALUE_STR        := null                 ;
PARAM_VALUE_CLOB       := ta_criteria_3027     ;
TENANT_ID              := '&TENANT_ID'         ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3027                 ;
NAME                   := 'PROVIDER_VERSION'   ;
PARAM_ATTRIBUTES       := null                 ;
PARAM_TYPE             := 1                    ;
PARAM_VALUE_STR        := '1.0'                ;
PARAM_VALUE_CLOB       := null                 ;
TENANT_ID              := '&TENANT_ID'         ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3027                   ;
NAME                   := 'WIDGET_KOC_NAME'      ;
PARAM_ATTRIBUTES       := null                   ;
PARAM_TYPE             := 1                      ;
PARAM_VALUE_STR        := 'emcta-visualization'  ;
PARAM_VALUE_CLOB       := null                   ;
TENANT_ID              := '&TENANT_ID'           ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3027                  ;
NAME                   := 'PROVIDER_NAME'       ;
PARAM_ATTRIBUTES       := null                  ;
PARAM_TYPE             := 1                     ;
PARAM_VALUE_STR        := 'TargetAnalytics'     ;
PARAM_VALUE_CLOB       := null                  ;
TENANT_ID              := '&TENANT_ID'          ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3027                                      ;
NAME                   := 'WIDGET_ICON'                             ;
PARAM_ATTRIBUTES       := null                                      ;
PARAM_TYPE             := 1                                         ;
PARAM_VALUE_STR        := '/../images/func_horibargraph_24_ena.png' ;
PARAM_VALUE_CLOB       := null                                      ;
TENANT_ID              := '&TENANT_ID'                              ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
SEARCH_ID              := 3027                  ;
NAME                   := 'PROVIDER_ASSET_ROOT' ;
PARAM_ATTRIBUTES       := null                  ;
PARAM_TYPE             := 1                     ;
PARAM_VALUE_STR        := 'assetRoot'           ;
PARAM_VALUE_CLOB       := null                  ;
TENANT_ID              := '&TENANT_ID'          ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID = 3028;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('TA WIDGET meta data Targets Status has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
SEARCH_ID                           :=3028                                                                                              ;
NAME                                :='Targets Status'                                                                                  ;
OWNER                               :='ORACLE'                                                                                          ;
CREATION_DATE                       :=SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                     ;
LAST_MODIFICATION_DATE              :=SYS_EXTRACT_UTC(SYSTIMESTAMP)                                                                     ;
LAST_MODIFIED_BY                    :='ORACLE'                                                                                          ;
DESCRIPTION                         :='Shows the distribution of targets that are up grouped by target type Categories and target types.';
FOLDER_ID                           :=4                                                                                                 ;
CATEGORY_ID                         :=2                                                                                                 ;
NAME_NLSID                          :=null                                                                                              ;
NAME_SUBSYSTEM                      :=null                                                                                              ;
DESCRIPTION_NLSID                   :=null                                                                                              ;
DESCRIPTION_SUBSYSTEM               :=null                                                                                              ;
SYSTEM_SEARCH                       :=1                                                                                                 ;
EM_PLUGIN_ID                        :=null                                                                                              ;
IS_LOCKED                           :=0                                                                                                 ;
METADATA_CLOB                       :=null                                                                                              ;
SEARCH_DISPLAY_STR                  := null                                                                                             ;
UI_HIDDEN                           :=0                                                                                                 ;
DELETED                             :=0                                                                                                 ;
IS_WIDGET                           :=1                                                                                                 ;
TENANT_ID                           :='&TENANT_ID'                                                                                      ;
Insert into EMS_ANALYTICS_SEARCH
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
METADATA_CLOB,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID
);
SEARCH_ID              := 3028;
NAME                   := 'WIDGET_SOURCE';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 1;
PARAM_VALUE_STR        := '1';
PARAM_VALUE_CLOB       := null;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3028;
NAME                   := 'WIDGET_VISUAL';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 2;
PARAM_VALUE_STR        := null;
PARAM_VALUE_CLOB       := screenshot_3028;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3028;
NAME                   := 'PROVIDER_VERSION';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 1;
PARAM_VALUE_STR        := '1.0';
PARAM_VALUE_CLOB       := null;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3028;
NAME                   := 'WIDGET_ICON';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 1;
PARAM_VALUE_STR        := '/../images/func_horibargraph_24_ena.png';
PARAM_VALUE_CLOB       := null;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3028;
NAME                   := 'VISUALIZATION_TYPE_KEY';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 1;
PARAM_VALUE_STR        := 'TREEMAP';
PARAM_VALUE_CLOB       := null;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3028;
NAME                   := 'TA_HORI_SORT_COLUMN';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 1;
PARAM_VALUE_STR        := '0';
PARAM_VALUE_CLOB       := null;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3028;
NAME                   := 'PROVIDER_NAME';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 1;
PARAM_VALUE_STR        := 'TargetAnalytics';
PARAM_VALUE_CLOB       := null;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3028;
NAME                   := 'PROVIDER_ASSET_ROOT';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 1;
PARAM_VALUE_STR        := 'assetRoot';
PARAM_VALUE_CLOB       := null;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3028;
NAME                   := 'WIDGET_VIEWMODEL';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 1;
PARAM_VALUE_STR        := '/widget/visualizationWidget/js/VisualizationWidget.js';
PARAM_VALUE_CLOB       := null;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3028;
NAME                   := 'WIDGET_KOC_NAME';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 1;
PARAM_VALUE_STR        := 'emcta-visualization';
PARAM_VALUE_CLOB       := null;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3028;
NAME                   := 'TOP_N_ROWS';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 1;
PARAM_VALUE_STR        := '100';
PARAM_VALUE_CLOB       := null;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3028;
NAME                   := 'TA_HORI_SORT_ORDER';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 1;
PARAM_VALUE_STR        := 'true';
PARAM_VALUE_CLOB       := null;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3028;
NAME                   := 'WIDGET_TEMPLATE';
PARAM_ATTRIBUTES       := null;
PARAM_TYPE             := 1;
PARAM_VALUE_STR        := '/widget/visualizationWidget/visualizationWidget.html';
PARAM_VALUE_CLOB       := null;
TENANT_ID              := '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);

SEARCH_ID              := 3028              ;
NAME                   := 'TA_CRITERIA'     ;
PARAM_ATTRIBUTES       := null              ;
PARAM_TYPE             := 2                 ;
PARAM_VALUE_STR        := null              ;
PARAM_VALUE_CLOB       := '{"version":0.5,"criteria":[{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"string","displayName":"Target Status","displayDetails":null,"role":"color","func":null,"UIProperties":{"blockUIInteraction":true}}},{"jsonConstructor":"TargetCountSearchCriterion","data":{"id":"target_count","_value":null,"dataType":"string","displayName":"Target Count","displayDetails":null,"role":"size","func":null,"UIProperties":{"blockUIInteraction":true}}},{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":null,"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"group by","func":null,"UIProperties":{"blockUIInteraction":true}}}]}';
TENANT_ID              := '&TENANT_ID'      ;
Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
)
values
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
);
END IF;
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_LAST_ACCESS WHERE TENANT_ID = '&TENANT_ID' AND OBJECT_ID >= 3000 AND OBJECT_ID <= 3999;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('last access data has been added before, no need to add again');
 ELSE
insert into EMS_ANALYTICS_LAST_ACCESS(OBJECT_ID,ACCESSED_BY,OBJECT_TYPE,ACCESS_DATE,TENANT_ID) 
select SEARCH_ID,'ORACLE',2,SYS_EXTRACT_UTC(SYSTIMESTAMP),'&TENANT_ID' from EMS_ANALYTICS_SEARCH where search_id>=3000 and search_id<=3999 and TENANT_ID ='&TENANT_ID';
END IF;
COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('>>>SSF DML ERROR<<<');
    DBMS_OUTPUT.PUT_LINE('Failed to add TA WIDGET meta data due to error '||SQLERRM);
    RAISE;
END;
/
