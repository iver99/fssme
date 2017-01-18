Rem
Rem emaas_savesearch_seed_data_ta.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, 2016, 2017 Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data_ta.sql 
Rem
Rem    DESCRIPTION
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    REX       1/4/17 - update widgets 3029, 3030, 3031
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
     
  -- delete the existing widgets with id 3029, 3030, 3031
  SELECT COUNT(SEARCH_ID) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID in (3029, 3030, 3031) AND TENANT_ID=V_TENANT_ID;
  IF (V_COUNT > 0) THEN
    DELETE FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE SEARCH_ID in (3029, 3030, 3031) AND TENANT_ID=V_TENANT_ID;
    DELETE FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID in (3029, 3030, 3031) AND TENANT_ID=V_TENANT_ID;
    DBMS_OUTPUT.PUT_LINE('Widget 3029,3030,3031 have been deleted successfully under tenant_id='||V_TENANT_ID);
  ELSE
    DBMS_OUTPUT.PUT_LINE('There is no widget with id 3029,3030,3031 under tenant_id='||V_TENANT_ID);
  END IF;
    DBMS_OUTPUT.PUT_LINE('Inserting 3029,3030,3031 under tenant_id='||V_TENANT_ID);
    -----------------------------------3029----------------------------------------------------------
    V_SEARCH_ID                          :=3029;
    V_NAME                               :='OMC Oracle Database - Transactions Rate';
    V_OWNER                              :='ORACLE';
    V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
    V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
    V_LAST_MODIFIED_BY                   :='ORACLE';
    V_DESCRIPTION                        :='OMC Oracle Database - Avg Transactions Rate by Hour';
    V_FOLDER_ID                          :=4;
    V_CATEGORY_ID                        :=2;
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
    
    V_NAME                               :='DATA_GRID_COLUMN_WIDTH_MAP_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='[]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='SELECTED_TIME_PERIOD';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='Last 1 day';
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
    V_PARAM_VALUE_CLOB                   :='{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"UIProperties":{},"_displayName":"Entity Type","_id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"displayValue":"OMC Oracle Database","metadata":{"category":false},"value":"omc_oracle_db"}}]}},"dataType":"string","displayDetails":null,"func":null,"measures":[{"name":"TARGETTYPEDISPLAYNAME","displayName":"Target Type","description":"Target type such as Oracle WebLogic Domain or Host","dataType":1,"disableGroupBy":false,"precision":256,"scale":0,"hide":0,"isAttr":true}],"role":"filter"}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"UIProperties":{},"_displayName":"Transactions Rate","_id":"omc_relational_database_RDB_Load_transactionsPerSecond_","_value":null,"dataType":"number","displayDetails":"Load","func":"mean","groupKeyColumns":[],"isConfig":0,"isCurat'
    ||'ed":true,"isKey":0,"mcName":"transactionsPerSecond","measures":[{"subject":"OMC_RELATIONAL_DATABASE","cube":"OMC_RELATIONAL_DATABASE-RDB_LOAD","name":"TRANSACTIONSPERSECOND","metricSourceType":1,"displayName":"Transactions Rate","cubeDisplayName":"Load","mgName":"RDB_Load","mcName":"transactionsPerSecond"}],"mgDisplayName":"Load","mgName":"RDB_Load","role":"y-axis ","targetType":"omc_relational_database","unitStr":"OMC_SYS_STANDARD_RATE_TRANPS"}}]}';
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

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='none';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_ENABLE_RANKING';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_KEY_METRICS';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{"keyMetricsNameList":[],"otherKeyMetricsNameList":[]}';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_OTHER_METRICS_CRITERIA';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='[]';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_SHARE_SAME_YAXIS';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_SHOW_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_SHOW_KEY_NAME_SELECTION';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='["no"]';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_SHOW_STACKED';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='off';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_TILE_SHOW_THRESHOLD';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_TOP_N_CONFIG';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{}';
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
    V_PARAM_VALUE_STR                    :='true';
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
    V_PARAM_VALUE_STR                    :='LINE';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='WIDGET_VISUAL';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='data:image/jpeg;base64,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';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    -----------------------------------3030----------------------------------------------------------
    V_SEARCH_ID                          :=3030;
    V_NAME                               :='OMC WebLogic Server - Web Request Rate';
    V_OWNER                              :='ORACLE';
    V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
    V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
    V_LAST_MODIFIED_BY                   :='ORACLE';
    V_DESCRIPTION                        :='OMC WebLogic Server - Avg Web Request Rate by Hour';
    V_FOLDER_ID                          :=4;
    V_CATEGORY_ID                        :=2;
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
    
    V_NAME                               :='DATA_GRID_COLUMN_WIDTH_MAP_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='[]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='SELECTED_TIME_PERIOD';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='Last 1 day';
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
    V_PARAM_VALUE_CLOB                   :='{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"UIProperties":{"useForSort":false},"_displayName":"Entity Type","_id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"displayValue":"OMC WebLogic Server","metadata":{"category":false},"value":"omc_weblogic_j2eeserver"}}]}},"dataType":"string","displayDetails":null,"func":null,"measures":[{"name":"TARGETTYPEDISPLAYNAME","displayName":"Target Type","description":"Target type such as Oracle WebLogic Domain or Host","dimensionName":"OMC_WEBLOGIC_J2EESERVER","dataType":1,"disableGroupBy":false,"precision":256,"scale":0,"hide":0,"isAttr":true}],"role":"filter"}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"UIProperties":{},"_displayName":"Web Request Rate","_id":"omc_web_application_server_WebRequests_serviceThroughput_","_value":null,"dataType":"number","displayD'
    ||'etails":"Web Requests","func":"mean","groupKeyColumns":[],"isConfig":0,"isCurated":true,"isKey":0,"mcName":"serviceThroughput","measures":[{"subject":"OMC_WEB_APPLICATION_SERVER","cube":"OMC_WEB_APPLICATION_SERVER-WEBREQUESTS","name":"SERVICETHROUGHPUT","metricSourceType":1,"displayName":"Web Request Rate","cubeDisplayName":"Web Requests","mgName":"WebRequests","mcName":"serviceThroughput"}],"mgDisplayName":"Web Requests","mgName":"WebRequests","role":"y-axis ","targetType":"omc_web_application_server","unitStr":"OMC_SYS_STANDARD_RATE_OPERATIONMINUTE"}}]}';
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

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='none';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_ENABLE_RANKING';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_KEY_METRICS';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{"keyMetricsNameList":[],"otherKeyMetricsNameList":[]}';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_OTHER_METRICS_CRITERIA';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='[]';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_SHARE_SAME_YAXIS';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_SHOW_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_SHOW_KEY_NAME_SELECTION';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='["no"]';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_SHOW_STACKED';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='off';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_TILE_SHOW_THRESHOLD';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_TOP_N_CONFIG';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{}';
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
    V_PARAM_VALUE_STR                    :='true';
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
    V_PARAM_VALUE_STR                    :='LINE';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='WIDGET_VISUAL';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='data:image/jpeg;base64,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';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    -----------------------------------3031----------------------------------------------------------
    V_SEARCH_ID                          :=3031;
    V_NAME                               :='OMC Host CPU Utilization';
    V_OWNER                              :='ORACLE';
    V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
    V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
    V_LAST_MODIFIED_BY                   :='ORACLE';
    V_DESCRIPTION                        :='OMC Host - Avg CPU Utilization by Hour';
    V_FOLDER_ID                          :=4;
    V_CATEGORY_ID                        :=2;
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
    
    V_NAME                               :='DATA_GRID_COLUMN_WIDTH_MAP_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='[]';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='SELECTED_TIME_PERIOD';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='Last 1 day';
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
    V_PARAM_VALUE_CLOB                   :='{"version":0.5,"criteria":[{"jsonConstructor":"TargetTypeSearchCriterion","data":{"UIProperties":{"useForSort":false},"_displayName":"Entity Type","_id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"displayValue":"OMC Host","metadata":{"category":false},"value":"omc_host"}}]}},"dataType":"string","displayDetails":null,"func":null,"measures":[{"name":"TARGETTYPEDISPLAYNAME","displayName":"Target Type","description":"Target type such as Oracle WebLogic Domain or Host","dataType":1,"disableGroupBy":false,"precision":256,"scale":0,"hide":0,"isAttr":true}],"role":"filter"}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"UIProperties":{},"_displayName":"CPU Utilization","_id":"omc_host_HOST_CPU_cpuUtilizationPercent_","_value":null,"dataType":"number","displayDetails":"CPU","func":"mean","groupKeyColumns":[],"isConfig":0,"isCurated":true,"isKey"'
    ||':0,"mcName":"cpuUtilizationPercent","measures":[{"subject":"OMC_HOST","cube":"OMC_HOST-HOST_CPU","name":"CPUUTILIZATIONPERCENT","metricSourceType":1,"displayName":"CPU Utilization","cubeDisplayName":"CPU","mgName":"HOST_CPU","mcName":"cpuUtilizationPercent"}],"mgDisplayName":"CPU","mgName":"HOST_CPU","role":"y-axis ","targetType":"omc_host","unitStr":"OMC_SYS_STANDARD_PERCENT_PERCENT"}}]}';
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

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='none';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_ENABLE_RANKING';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_KEY_METRICS';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{"keyMetricsNameList":[],"otherKeyMetricsNameList":[]}';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_OTHER_METRICS_CRITERIA';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='[]';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_SHARE_SAME_YAXIS';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_SHOW_CALLOUT';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_SHOW_KEY_NAME_SELECTION';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='["no"]';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_SHOW_STACKED';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='off';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_TILE_SHOW_THRESHOLD';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='false';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    V_NAME                               :='TA_HORISTACKEDBAR_WIDGET_TOP_N_CONFIG';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='{}';
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
    V_PARAM_VALUE_STR                    :='true';
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
    V_PARAM_VALUE_STR                    :='LINE';
    V_PARAM_VALUE_CLOB                   :=NULL;
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
    
    V_NAME                               :='WIDGET_VISUAL';
    V_PARAM_TYPE                         :=2;
    V_PARAM_VALUE_STR                    :=NULL;
    V_PARAM_VALUE_CLOB                   :='data:image/jpeg;base64,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';
    Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID ) 
    values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

    DBMS_OUTPUT.PUT_LINE('Widget 3029,3030,3031 have been created successfully! tenant_id='||V_TENANT_ID);
  IF (V_TID<>-1) THEN
    EXIT;
  END IF;
  END LOOP;
  CLOSE TENANT_CURSOR;
  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to update widget 3029,3030,3031 due to error '||SQLERRM);
    RAISE;
END;
/
