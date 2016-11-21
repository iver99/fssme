Rem
Rem emaas_savesearch_seed_data.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, 2016, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data.sql 
Rem
Rem    DESCRIPTION
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    REX       11/14/16 - Add new widget
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE
  V_SEARCH_ID                           NUMBER                  ;
  V_NAME                                VARCHAR2(64 BYTE)       ;
  V_OWNER                               VARCHAR2(64 BYTE)       ;
  V_CREATION_DATE                       TIMESTAMP               ;
  V_LAST_MODIFICATION_DATE              TIMESTAMP               ;
  V_LAST_MODIFIED_BY                    VARCHAR2(64 BYTE)       ;
  V_DESCRIPTION                         VARCHAR2(256 BYTE)      ;
  V_FOLDER_ID                           NUMBER                  ;
  V_CATEGORY_ID                         NUMBER                  ;
  V_SYSTEM_SEARCH                       NUMBER                  ;
  V_IS_LOCKED                           NUMBER                  ;
  V_UI_HIDDEN                           NUMBER                  ;
  V_DELETED                             NUMBER                  ;
  V_IS_WIDGET                           NUMBER                  ;
  V_TENANT_ID                           NUMBER                  ;
  V_WIDGET_ICON                         VARCHAR2(1024 BYTE)     ;
  V_WIDGET_KOC_NAME                     VARCHAR2(256 BYTE)      ;
  V_WIDGET_VIEWMODEL                    VARCHAR2(1024 BYTE)     ;
  V_WIDGET_TEMPLATE                     VARCHAR2(1024 BYTE)     ;
  V_WIDGET_LINKED_DASHBOARD             NUMBER                  ;
  V_WIDGET_DEFAULT_WIDTH                NUMBER                  ;
  V_WIDGET_DEFAULT_HEIGHT               NUMBER                  ;
  V_PROVIDER_NAME                       VARCHAR2(64 BYTE)       ;
  V_PROVIDER_VERSION                    VARCHAR2(64 BYTE)       ;
  V_PROVIDER_ASSET_ROOT                 VARCHAR2(64 BYTE)       ;
  V_WIDGET_SOURCE                       VARCHAR2(64 BYTE)       ;
  
  V_PARAM_TYPE               NUMBER(38,0)                    ;
  V_PARAM_VALUE_STR          VARCHAR2(1024 BYTE)             ;
  V_PARAM_VALUE_CLOB         NCLOB                           ;
  
  V_COUNT                 NUMBER;
  V_TID                   NUMBER(38,0) := '&TENANT_ID';
  CURSOR TENANT_CURSOR IS
    SELECT DISTINCT TENANT_ID FROM EMS_ANALYTICS_CATEGORY ORDER BY TENANT_ID;
BEGIN
  OPEN TENANT_CURSOR;
  LOOP
     IF (V_TID<>-1) THEN
        V_TENANT_ID:=V_TID;
     ELSE
       FETCH TENANT_CURSOR INTO V_TENANT_ID;
       EXIT WHEN TENANT_CURSOR%NOTFOUND;
     END IF;
  SELECT COUNT(SEARCH_ID) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 5014 AND TENANT_ID=V_TENANT_ID;
  IF (V_COUNT<1) THEN
    V_SEARCH_ID                          :=5014;
    V_NAME                               :='Entity and Step Status';
    V_OWNER                              :='ORACLE';
    V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
    V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
    V_LAST_MODIFIED_BY                   :='ORACLE';
    V_DESCRIPTION                        :='Orchestration';
    V_FOLDER_ID                          :=8;
    V_CATEGORY_ID                        :=7;
    V_SYSTEM_SEARCH                      :=1;
    V_IS_LOCKED                          :=0;
    V_UI_HIDDEN                          :=0;
    V_DELETED                            :=0;
    V_IS_WIDGET                          :=1;
    V_WIDGET_ICON                        :='/../images/func_horibargraph_24_ena.png';
    V_WIDGET_KOC_NAME                    :='emcta-visualization';
    V_WIDGET_VIEWMODEL                   :='/widget/visualizationWidget/js/VisualizationWidget.js';
    V_WIDGET_TEMPLATE                    :='/widget/visualizationWidget/visualizationWidget.html';
    V_WIDGET_LINKED_DASHBOARD            :=0;
    V_WIDGET_DEFAULT_WIDTH               :=0;
    V_WIDGET_DEFAULT_HEIGHT              :=0;
    V_PROVIDER_NAME                      :='TargetAnalytics';
    V_PROVIDER_VERSION                   :='1.1';
    V_PROVIDER_ASSET_ROOT                :='assetRoot';
    V_WIDGET_SOURCE                      :='1';
    
    Insert into EMS_ANALYTICS_SEARCH (
    SEARCH_ID,
    NAME,
    OWNER,
    CREATION_DATE,
    LAST_MODIFICATION_DATE,
    LAST_MODIFIED_BY,
    DESCRIPTION,
    FOLDER_ID,
    CATEGORY_ID,
    SYSTEM_SEARCH,
    IS_LOCKED,
    UI_HIDDEN,
    DELETED,
    IS_WIDGET,
    TENANT_ID,
    WIDGET_ICON,
    WIDGET_KOC_NAME,
    WIDGET_VIEWMODEL,
    WIDGET_TEMPLATE,
    WIDGET_LINKED_DASHBOARD,
    WIDGET_DEFAULT_WIDTH,
    WIDGET_DEFAULT_HEIGHT,
    PROVIDER_NAME,
    PROVIDER_VERSION,
    PROVIDER_ASSET_ROOT,
    WIDGET_SOURCE
    )
    values (
    V_SEARCH_ID,
    V_NAME,
    V_OWNER,
    V_CREATION_DATE,
    V_LAST_MODIFICATION_DATE,
    V_LAST_MODIFIED_BY,
    V_DESCRIPTION,
    V_FOLDER_ID,
    V_CATEGORY_ID,
    V_SYSTEM_SEARCH,
    V_IS_LOCKED,
    V_UI_HIDDEN,
    V_DELETED,
    V_IS_WIDGET,
    V_TENANT_ID,
    V_WIDGET_ICON,
    V_WIDGET_KOC_NAME,
    V_WIDGET_VIEWMODEL,
    V_WIDGET_TEMPLATE,
    V_WIDGET_LINKED_DASHBOARD,
    V_WIDGET_DEFAULT_WIDTH,
    V_WIDGET_DEFAULT_HEIGHT,
    V_PROVIDER_NAME,
    V_PROVIDER_VERSION,
    V_PROVIDER_ASSET_ROOT,
    V_WIDGET_SOURCE
    );
    
    V_NAME                               :='SELECTED_TIME_RANGE';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='[1478645797907,1478732197907]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_CORRELATION_TIME_PERIOD_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='744';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_CRITERIA';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"_id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"omc_target","metadata":{"category":false},"displayValue":"Target"}}]}},"dataType":"string","_displayName":"Entity Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false},"measures":[{"name":"TARGETTYPEDISPLAYNAME","displayName":"Target Type","description":"Target type such as Oracle WebLogic Domain or Host","dimensionName":"OMC_TARGET","dataType":1,"disableGroupBy":false,"precision":256,"scale":0,"hide":0,"isAttr":true}]}},null,{"jsonConstructor":"MetricGroupSearchCriterion","data":{"_id":"omc_target_OmcWorkflowExecutionStatusSummary_submissionEntityName_","_value":null,"dataType":"string","_displayName":"Workflow Submission","displayDetails":"Workflow Execution Steps","role":"column","UIProperties":{},"targetType":"omc_target","unitStr":null,"mcName":"submissionEntityName","mgName":"OmcWorkflowExecutionStatusSummary","mgDisplayName":"Workflow Execution Steps","isKey":0,"groupKeyColumns":["groupEntityId","statusDescription","stepId","threadId","submissionEntityId"],"measures":[],"isConfig":0,"isCurated":true,"baselineable":0}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"_id":"omc_target_OmcWorkflowExecutionStatusSummary_statusDescription_","_value":null,"dataType":"string","_displayName":"Status","displayDetails":"Workflow Execution Steps","role":"column","UIProperties":{},"targetType":"omc_target","unitStr":null,"mcName":"statusDescription","mgName":"OmcWorkflowExecutionStatusSummary","mgDisplayName":"Workflow Execution Steps","isKey":1,"groupKeyColumns":["groupEntityId","statusDescription","stepId","threadId","submissionEntityId"],"measures":[],"isConfig":0,"isCurated":true,"baselineable":0}}]}';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_DONUT_WIDGET_ENABLE_RANKING';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_DONUT_WIDGET_TOP_N_CONFIG';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{}';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_FUNNEL_WIDGET_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='none';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_FUNNEL_WIDGET_KEY_METRICS';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{"keyMetricsNameList":[],"otherKeyMetricsNameList":[]}';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_FUNNEL_WIDGET_OTHER_METRICS_CRITERIA';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='[]';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_FUNNEL_WIDGET_SHOW_3D';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='off';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_FUNNEL_WIDGET_SHOW_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_FUNNEL_WIDGET_SHOW_KEY_NAME_SELECTION';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='["no"]';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_FUNNEL_WIDGET_SHOW_ORIENTATION';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='vertical';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_HISTOGRAM_INSIGHT_OPTION_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='insights';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_HORI_SORT_COLUMN';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='0';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_HORI_SORT_ORDER';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='true';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_LINECHART_WIDGET_ENABLE_RANKING';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_LINECHART_WIDGET_TOP_N_CONFIG';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{}';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_LINE_CHART_WIDGET_SHOW_STACKED';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='on';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_QDELINE_DISPLAY_OPTION_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='["insights"]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_QDELINE_TIME_PERIOD_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='744';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_QDELINE_TIME_SERIES_CALLOUT_OPTION_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='["no"]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_QDELINE_TIME_SERIES_ENABLE_CALLOUT_OPTION_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='["avg","min","max"]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_QDELINE_WIDGET_ENABLE_RANKING';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_QDELINE_WIDGET_TOP_N_CONFIG';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{}';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_STACKEDBAR_WIDGET_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='none';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_STACKEDBAR_WIDGET_ENABLE_RANKING';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_STACKEDBAR_WIDGET_KEY_METRICS';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{"keyMetricsNameList":[],"otherKeyMetricsNameList":[]}';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_STACKEDBAR_WIDGET_OTHER_METRICS_CRITERIA';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='[]';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_STACKEDBAR_WIDGET_SHARE_SAME_YAXIS';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_STACKEDBAR_WIDGET_SHOW_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_STACKEDBAR_WIDGET_SHOW_KEY_NAME_SELECTION';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='["no"]';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_STACKEDBAR_WIDGET_SHOW_STACKED';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='off';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_STACKEDBAR_WIDGET_TILE_SHOW_THRESHOLD';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_STACKEDBAR_WIDGET_TOP_N_CONFIG';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{}';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_TREEMAP_DISPLAY_OPTION_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='["insights"]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_TREEMAP_TOP_N_ROWS';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='5000';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_TREEMAP_WIDGET_ENABLE_RANKING';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_TREEMAP_WIDGET_TOP_N_CONFIG';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{}';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='none';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_GAUGE_COLOR_VALUE';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='#84bbe6';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_GAUGE_MAX_VALUE';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='100';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_GAUGE_SELECTED_TILE';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='emcta-tile-circular';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_GAUGE_SHOW_SPARK_CHART';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_GAUGE_THRESHOLD_CRITERION';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='[]';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_GAUGE_THRESHOLD_VALUE';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='100';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_LABEL_SELECTED_TILE';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='emcta-tile-label';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_LABEL_SHOW_SPARK_CHART';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='true';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_LINE_CHART_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='none';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_LINE_CHART_SELECTED_TILE';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='emcta-tile-line';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_LINE_CHART_SHOW_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_LINE_CHART_SHOW_STACKED';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='on';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_LINE_CHART_TILE_SHOW_THRESHOLD';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_METRIC_TABLE_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='none';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_METRIC_TABLE_SHOW_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_SELECTED_TILE';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='emcta-tile-label';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_SHOW_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_SHOW_PERCENT_CHANGE';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='true';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_SHOW_SPARK_CHART';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TA_WIDGET_TILE_SHOW_THRESHOLD';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TIME_SERIES_AREA_CALLOUT_OPTION_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='["avg","min","max"]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TIME_SERIES_AREA_DISPLAY_OPTION_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='["insights"]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TIME_SERIES_AREA_ENABLE_CALLOUT_OPTION_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='["no"]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TIME_SERIES_AREA_TIME_PERIOD_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='744';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TIME_SERIES_AREA_WIDGET_ENABLE_RANKING';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TIME_SERIES_AREA_WIDGET_TOP_N_CONFIG';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{}';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TIME_SERIES_CALLOUT_OPTION_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='["avg","min","max"]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TIME_SERIES_DISPLAY_OPTION_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='["insights"]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TIME_SERIES_ENABLE_CALLOUT_OPTION_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='["no"]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TIME_SERIES_REFERENCE_LINE_NAME_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='""';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TIME_SERIES_REFERENCE_LINE_OPTION_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='"nameValue"';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TIME_SERIES_REFERENCE_LINE_VALUE_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TIME_SERIES_TIME_PERIOD_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='744';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='TOP_N_ROWS';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='50';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='VISUALIZATION_TYPE_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='DATAGRID';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='WIDGET_VISUAL';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/2wBDAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/wAARCACMAO8DAREAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD+vj9pj9nf9rr4o+G7iz/Zz/bN1r9lrxY3jL+17TXh8B/gt8btCg8F3PgzSfD174NuPDHju3sL26v7TxNYah4+8OeK4/FNpLp+sa5e'
    ||'6L4h0bxb4WtdK0aw6pVIvapyu/8AK3p21RkovrG/zt+p5Xr/AOzv/wAFLr6LxB4m0P8AbU+G2j+K9d1b9mXVtC+HSfs3eEZvg58Oz4Sg8A6N+1LoQ1O+8Ral8WfiT4G+K9tbfE3xp4C0O98YeAvHvhHxRqngvw4nxc0/wvpWsTake0X/AD8V9PsOy2v0u+rWvXfQXLLt+KOqvPgB/wAFALn4u+FvFGlfti+CdC+Efhn4w+Nta8SfC/WP2cfCHjbUPix8D/FKfB7WPDngWfxlpvjD4bar8J/Hnwx1XQvjR4O8M+NdJHxE03xF4Q8d+GPEXjjw5rvivw2IAe0je/tNLvTke2ml7KzWuuvS6Hyv+XXTqv61/P7j6J+E3w4+M3g/w1HpXxe+J9r8ZfFUnivXby18WWXgTw/8M7e28MSRXUHhfQZPDmja3rdtc6tp2j29tJ4n8Rfb4bfxL4ru9c1nRfD/AIK8NXeieB/DdKrBJ3ndtae7JdV5Pt/WxLhJuNls7vVfyyXfuzd+J/w++KHirwhquk/Dbx/L8KfGE2m+II9D8YDwx4Z8c2enaxfeFde0nw9e6r4Y1+aCDWdL0DxPqOieLbvSbTVNBvNc/wCEdTQW1zTtP1W/lKdWHSdv+3W+np31HyS7fijxT4j/AAG/az8XfDuLRPBX7Wb/AAo+Jj+FHe98f+HvgT8MfEnh1fidB4U8MaFp+oaR8PvHWqeI5rH4NT+ILLxd428RfDPVPGevfE3UtW8RaHpOg/tDeE/Dfha70jxQOrC3x2ffldr+ltvK9/MOSXb8UeoyfC34u6h8PviF4W1j4ntJ4s8XeH7jSfDXjfQ/C2neG2+H+o3nw20Hwrc6vo2k2XiOW7vmTx5ZeIfiTp8d74hGoac3iKLwrFrD2OhWGpSHtYWfv7/3ZaaenfUOWXb8V/mb/wANPht4n8C+ENP8J6t4n8Q+PrrRZb22i8W+LLyzuvE+rac13LPo48QX0Uqp'
    ||'qmr6fpMtlpd/rAitTrFzZSao1lYtdm0hftadkua9k91K+7fbsxKEtdN33XZeZ5/+0B8Hf2g/iTpfw50/4IftA3v7N11oPxT0LxJ8Stc0z4Y/Dv4qav8AEP4T2+g+KNJ8TfC7RI/iG93ofgDXtX1PWfD/AIo0L4kroniqTw7q/hCytL/wh4o8PavreiXidWDtadtdfdbuu22nqPll2/FHlP7MP7N37aPwq8bfFHWv2kP24dR/at8E67fXq/B/wRL+zn8FvgddfDfRb3xLq+rLH428WfD6e6u/in4k0nQX8O+FNF1rTNM+GOhpa2HiPVtd8IeINY8QaRc+EEqkVe9S/b3Grfhr+AOL6K3zR9p/2Hqn/Pr/AOR7f/49Ve1p/wA34S/yDkl2/Ff5lW78N61cxJHDJdae63VlcGe0k0p5ZIrS9t7qeydb+G9gFrqcEMmnXrxwpex2V3cSadd6fqC2t/bHtaf834P/ACDkl2/Ff5nzVo3wb/a7a78HP4u/aU8F3NhbwfFKL4kW3gL9nvSfCN74gk8SaHomkfCi6+HF94u+LHxQg+Hk3wuv7HWvFGvJ4v074wW/xJ17XUgkg8IeGNKg8Nzr2sf511vaEu2lt7W3d738kHI+34r5/wBfmegal8LvjRceOdJ1PTPjBd6b8OkTU73xD4Pl8DeCL/xbfayPFvgrXfDtj4e8eu1vpmgeBbfw9ofjLwR4q0PVvAHi3xp4h0vxyur+H/iX4H8SeF9M1a4Pawv8enbld/vtt8m9dw5JdvxR23jjwR4z8SeDPFfh7wr4o1L4d+J9b8O6zpXh3x9o1h4W17VvBet3+n3FrpfinTtE8WWOteGNYvNBvZYNTg0vxBpOo6PfyWy2uoWc9rLKhbqws/et52ennsHJLt+K/wAz458Yfs2/8FANd+EvgHQfDH7d/hrwP8bvDfi3wrrfjT4m2X7I/wAPdd+HXxI8NeHdU8U3mq+C774Pa98TrzXvDFn4'
    ||'807XtG0jxfrfhv4x2muWsfgjw7e+BLvwReX3i9/E8+0jZfvNer5HZ/K2ifrfaz3u+V3+HTXS60+fkanxS/Z2/bp8W+GdGt/hr+23ofwl8bQ/GW/8YeItbi/Za+HnjnwfqXwZntlstP8AgvoPg3xJ8QE17QNVsI7LTtTk+KepfELxTqd74g1DxhcN4Zg8Pap4S8L+Ah1I9Kltf5Ht2/4N/kLkfVfil/me0+G/hN8ftO8ZeI9d8SfHI+JfCs3i3V9R8G+BYPhn4K0Gz0jwZrQ8Hznwt4w1+DUb3XvF+s+FrzTPG1v4H8WaBdfD6G20Lxbo1j498NfEbW/CMvibxK/aw19++unuvRdttfJ6edw5JdvxR6x4I0jxNoHhfRdH8Z65b+J/FunaTo1n4l8T2mjweHrXxJr1rounW+seILbw9a3+qW+g2+s6jHc6jDosOpahHpUdwtil7dJAJ5NFJSjFp3srN2a1u+/qTZpu/f8ARHWUwCgAoAKACgAoAKACgAoAKACgDxy4T9nu5+JWvT3GpXh+IWn/ABC8SafqmlN4g+IcUEnxHvf2e/AN7rcmmeGRqEfhzU9as/gAnhRtP1TQtLuxoNjq3iWHQ7vT/E3ibx1HqfnnQeTeG9a/YB0iTwpqWnat8INLsZvg5ZaR4Wv/ABVftZeEZfhpPqfxK0+60xLrxrLF4Tk8VTahN8WbHx1ZX0h+Jkiy+Nbbx1EbZ9VQgHB/sq63/wAEz/D/AMBNKi+AfiD4X6d8Ere00a20w/FbXfFC3ltpHibwN4ou/DentL+0heN47t/Deq/D/UviAvh7TLp00J9BufiRaaVbLCPGcCgHq3wctP2TbfSdf034S6LqWo6VqPwR+G2ueMfDWreHfi94hvPEXwZ1EfG/w/8ADqfVfCPj3T9R1bxVq2tjwx8TvDb2baVqfxC1/QND8OeHvEFvfeHrH4c2MYB7PceNPgR4ZtNZmf4g+C9A0+PxR4g1fWns/H8G'
    ||'lWOm+JdC8beEtK8cC6+w63BbaItr8QNa8NWfxF0vFlp0/ifxXdL40s5r/wAY62dWAOBn8';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    DBMS_OUTPUT.PUT_LINE('Widget-Entity and Step Status have been created successfully! tenant_id='||V_TENANT_ID);
  ELSE
    DBMS_OUTPUT.PUT_LINE('Widget-Entity and Step Status had been created before, no need to create again. tenant_id='||V_TENANT_ID);
  END IF;
  IF (V_TID<>-1) THEN
    EXIT;
  END IF;
  END LOOP;
  CLOSE TENANT_CURSOR;
  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to create widget Entity and Step Status due to error '||SQLERRM);
    RAISE;
END;
/
