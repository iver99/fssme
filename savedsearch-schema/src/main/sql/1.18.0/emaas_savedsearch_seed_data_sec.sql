Rem
Rem emaas_savedsearch_seed_data_sec.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, 2016, 2017 Oracle and/or its affiliates.
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savedsearch_seed_data_sec.sql
Rem
Rem    DESCRIPTION
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    XIADAI       03/28/17 -  seed data for Security Analytics OOB
Rem
SET FEEDBACK ON
SET SERVEROUTPUT ON
DEFINE TENANT_ID ='&1'
DECLARE
    V_SEARCH_ID                         NUMBER(38,0)         ;
    V_NAME                              VARCHAR2(64 BYTE)    ;
    V_OWNER                             VARCHAR2(64 BYTE)    ;
    V_CREATION_DATE                     TIMESTAMP(6)         ;
    V_LAST_MODIFICATION_DATE            TIMESTAMP(6)         ;
    V_LAST_MODIFIED_BY                  VARCHAR2(64 BYTE)    ;
    V_DESCRIPTION                       VARCHAR2(256 BYTE)   ;
    V_FOLDER_ID                         NUMBER(38,0)         ;
    V_CATEGORY_ID                       NUMBER(38,0)         ;
    V_SYSTEM_SEARCH                     NUMBER(1,0)          ;
    V_IS_LOCKED                         NUMBER(1,0)          ;
    V_UI_HIDDEN                         NUMBER(1,0)          ;
    V_DELETED                           NUMBER(38,0)         ;
    V_IS_WIDGET                         NUMBER(1,0)          ;
    V_TENANT_ID                         NUMBER(38,0)         ;
    V_WIDGET_SOURCE                     EMS_ANALYTICS_SEARCH.WIDGET_SOURCE%TYPE;
    V_WIDGET_GROUP_NAME                 EMS_ANALYTICS_SEARCH.WIDGET_GROUP_NAME%TYPE;
    V_WIDGET_ICON                       EMS_ANALYTICS_SEARCH.WIDGET_ICON%TYPE;
    V_WIDGET_KOC_NAME                   EMS_ANALYTICS_SEARCH.WIDGET_KOC_NAME%TYPE;
    V_WIDGET_VIEWMODEL                  EMS_ANALYTICS_SEARCH.WIDGET_VIEWMODEL%TYPE;
    V_WIDGET_TEMPLATE                   EMS_ANALYTICS_SEARCH.WIDGET_TEMPLATE%TYPE;
    V_WIDGET_SUPPORT_TIME_CONTROL       EMS_ANALYTICS_SEARCH.WIDGET_SUPPORT_TIME_CONTROL%TYPE;
    V_DASHBOARD_INELIGIBLE              EMS_ANALYTICS_SEARCH.DASHBOARD_INELIGIBLE%TYPE;
    V_PROVIDER_NAME                     EMS_ANALYTICS_SEARCH.PROVIDER_NAME%TYPE;
    V_PROVIDER_VERSION                  EMS_ANALYTICS_SEARCH.PROVIDER_VERSION%TYPE;
    V_PROVIDER_ASSET_ROOT               EMS_ANALYTICS_SEARCH.PROVIDER_ASSET_ROOT%TYPE;
    V_WIDGET_LINKED_DASHBOARD           EMS_ANALYTICS_SEARCH.WIDGET_LINKED_DASHBOARD%TYPE;
    V_WIDGET_DEFAULT_WIDTH              EMS_ANALYTICS_SEARCH.WIDGET_DEFAULT_WIDTH%TYPE;
    V_WIDGET_DEFAULT_HEIGHT             EMS_ANALYTICS_SEARCH.WIDGET_DEFAULT_HEIGHT%TYPE;
    V_PARAM_ATTRIBUTES                  EMS_ANALYTICS_SEARCH_PARAMS.PARAM_ATTRIBUTES%TYPE;
    V_PARAM_TYPE                        EMS_ANALYTICS_SEARCH_PARAMS.PARAM_TYPE%TYPE;
    V_PARAM_VALUE_STR                   EMS_ANALYTICS_SEARCH_PARAMS.PARAM_VALUE_STR%TYPE;
    V_PARAM_VALUE_CLOB                  CLOB                           ;
    V_CLOB                              CLOB                           ;
    V_SEARCH_STR                        CLOB                           ;
    V_METADATA                          CLOB                           ;

    v_count number;
    V_TID                   NUMBER(38,0) := '&TENANT_ID';
    CURSOR TENANT_CURSOR IS
      SELECT DISTINCT TENANT_ID FROM EMS_ANALYTICS_FOLDERS ORDER BY TENANT_ID;
BEGIN
  OPEN TENANT_CURSOR;
  LOOP
     IF (V_TID<>-1) THEN
        V_TENANT_ID:=V_TID;
     ELSE
       FETCH TENANT_CURSOR INTO V_TENANT_ID;
       EXIT WHEN TENANT_CURSOR%NOTFOUND;     
     END IF;
   ---3308 Top 10 Denied Sources
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3308 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Denied Sources already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3308;
       V_NAME                               :='Top 10 Denied Sources';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='Top 10 denied sources by number of denied connections';
       V_FOLDER_ID                          :=2; 
       V_CATEGORY_ID                        :=1; 
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Observer Endpoint Product'' = cisco_asa and ''SEF Category'' = network.connection.deny | stats count(''SEF Actor Endpoint Network Address'') as ''Denied Connections'' by ''SEF Actor Endpoint Network Address'', ''SEF Destination Endpoint Network Address'' | top ''Denied Connections''';
       V_WIDGET_SOURCE                      :='1';
       V_WIDGET_GROUP_NAME                  :=null;
       V_WIDGET_ICON                        :=null;
       V_WIDGET_KOC_NAME                    :='emcla-visualization';
       V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
       V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
       V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
       V_DASHBOARD_INELIGIBLE               :=0;
       V_PROVIDER_NAME                      :='LogAnalyticsUI';
       V_PROVIDER_VERSION                   :='1.0';
       V_PROVIDER_ASSET_ROOT                :='assetRoot';
       V_WIDGET_LINKED_DASHBOARD            :=null;
       V_WIDGET_DEFAULT_WIDTH               :=4;
       V_WIDGET_DEFAULT_HEIGHT              :=2;
       INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                         SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                         WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                         WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                         PROVIDER_ASSET_ROOT)
   	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
   	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
   	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
   	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);
        --Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE,DELETED)
        --values (238535640335261447215078772061278352345,'WIDGET_VISUAL',null,2,null,764924872,to_timestamp('09-MAR-17 12.24.34.959000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('09-MAR-17 12.24.34.959000000 AM','DD-MON-RR HH.MI.SSXFF AM'),0);
        --Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE,DELETED)
        --values (238535640335261447215078772061278352345,'VISUALIZATION_TYPE_KEY',null,1,'HORIZONTAL_BAR',764924872,to_timestamp('09-MAR-17 12.24.34.959000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('09-MAR-17 12.24.34.959000000 AM','DD-MON-RR HH.MI.SSXFF AM'),0);
        --Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE,DELETED)
        --values (238535640335261447215078772061278352345,'time',null,1,'{"type":"relative","duration":90,"timeUnit":"DAY"}',764924872,to_timestamp('09-MAR-17 12.24.34.959000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('09-MAR-17 12.24.34.959000000 AM','DD-MON-RR HH.MI.SSXFF AM'),0);
        --Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE,DELETED)
        --values (238535640335261447215078772061278352345,'APPLICATION_CONTEXT',null,2,null,764924872,to_timestamp('09-MAR-17 12.24.34.959000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('09-MAR-17 12.24.34.959000000 AM','DD-MON-RR HH.MI.SSXFF AM'),0);
        --Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,TENANT_ID,CREATION_DATE,LAST_MODIFICATION_DATE,DELETED)
        --values (238535640335261447215078772061278352345,'TARGET_FILTER',null,2,null,764924872,to_timestamp('09-MAR-17 12.24.34.959000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_timestamp('09-MAR-17 12.24.34.959000000 AM','DD-MON-RR HH.MI.SSXFF AM'),0);
        V_NAME                  :='WIDGET_VISUAL';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='data:image/png;base64,'||
        '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';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='VISUALIZATION_TYPE_KEY';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='time';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

   DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Widget Top 10 Denied Sources has been added for TENANT: '||V_TENANT_ID);
   END IF;

       ---3309 Denied Connections by Sources
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3309 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Denied Connections by Sources already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3309;
       V_NAME                               :='Denied Connections by Sources';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='The historical trend of denied connections by Source';
       V_FOLDER_ID                          :=2;
       V_CATEGORY_ID                        :=1;
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Observer Endpoint Product'' = cisco_asa and ''SEF Category'' = network.connection.deny | timestats count by ''SEF Actor Endpoint Network Address''';
       V_WIDGET_SOURCE                      :='1';
       V_WIDGET_GROUP_NAME                  :=null;
       V_WIDGET_ICON                        :=null;
       V_WIDGET_KOC_NAME                    :='emcla-visualization';
       V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
       V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
       V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
       V_DASHBOARD_INELIGIBLE               :=0;
       V_PROVIDER_NAME                      :='LogAnalyticsUI';
       V_PROVIDER_VERSION                   :='1.0';
       V_PROVIDER_ASSET_ROOT                :='assetRoot';
       V_WIDGET_LINKED_DASHBOARD            :=null;
       V_WIDGET_DEFAULT_WIDTH               :=6;
       V_WIDGET_DEFAULT_HEIGHT              :=2;
       INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                         SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                         WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                         WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                         PROVIDER_ASSET_ROOT)
   	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
   	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
   	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
   	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

        V_NAME                  :='SHOW_N_ROWS';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='25';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='SHOW_MESSAGE_FIELD';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='false';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='LA_CRITERIA';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[{"jsonConstructor":"laCriterionField","data":{"id":"mtgt","_value":null,"dataType":"string","displayName":"Entity","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"mtgttype","_value":null,"dataType":"string","displayName":"Entity Type","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"msrcid","_value":null,"dataType":"string","displayName":"Log Source","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"srvrhostname","_value":null,"dataType":"string","displayName":"Host Name (Server)","displayDetails":{"targetType":'||
        '"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"sevlvl","_value":null,"dataType":"string","displayName":"Severity","displayDetails":'||
        '{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"sefActorEPNwAddress","_value":null,"dataType":"string","displayName":"SEF Actor Endpoint Network Address","displayDetails":{"targetType":"charts","emSite":""},"role":"Group by"}}]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='WIDGET_VISUAL';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='data:image/png;base64,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';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='VISUALIZATION_TYPE_KEY';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='LIST_TABLE_WITH_HISTOGRAM';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='time';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
        END IF;

       ---3310 Top 10 Denied Destination Ports
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3310 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Denied Destination Ports already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3310;
       V_NAME                               :='Top 10 Denied Destination Ports';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='Top 10 destination ports by the number of denied connections';
       V_FOLDER_ID                          :=2;
       V_CATEGORY_ID                        :=1;
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Observer Endpoint Product'' = cisco_asa and ''SEF Category'' = network.connection.deny | stats count(''SEF Destination En'||
       'dpoint Network Address Port'') as ''Denied Connections'' by ''SEF Destination Endpoint Network Address Port'', ''SEF Actor Endpoint Network Address'' | top ''Denied Connections''';
       V_WIDGET_SOURCE                      :='1';
       V_WIDGET_GROUP_NAME                  :=null;
       V_WIDGET_ICON                        :=null;
       V_WIDGET_KOC_NAME                    :='emcla-visualization';
       V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
       V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
       V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
       V_DASHBOARD_INELIGIBLE               :=0;
       V_PROVIDER_NAME                      :='LogAnalyticsUI';
       V_PROVIDER_VERSION                   :='1.0';
       V_PROVIDER_ASSET_ROOT                :='assetRoot';
       V_WIDGET_LINKED_DASHBOARD            :=null;
       V_WIDGET_DEFAULT_WIDTH               :=6;
       V_WIDGET_DEFAULT_HEIGHT              :=2;
       INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                         SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                         WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                         WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                         PROVIDER_ASSET_ROOT)
   	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
   	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
   	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
   	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

        V_NAME                  :='WIDGET_VISUAL';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      := 'data:image/png;base64,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';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='VISUALIZATION_TYPE_KEY';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='time';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Denied Destination Ports has been added for TENANT: '||V_TENANT_ID);
   END IF;

       ---3311 Denied Connections by Destination Port
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3311 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Denied Connections by Destination Port already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3311;
       V_NAME                               :='Denied Connections by Destination Port';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='The historical trend of denied connections by destination Port';
       V_FOLDER_ID                          :=2;
       V_CATEGORY_ID                        :=1;
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Observer Endpoint Product'' = cisco_asa and ''SEF Category'' = network.connection.deny | timestats count by ''SEF Destination Endpoint Network Address Port''';
       V_WIDGET_SOURCE                      :='1';
       V_WIDGET_GROUP_NAME                  :=null;
       V_WIDGET_ICON                        :=null;
       V_WIDGET_KOC_NAME                    :='emcla-visualization';
       V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
       V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
       V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
       V_DASHBOARD_INELIGIBLE               :=0;
       V_PROVIDER_NAME                      :='LogAnalyticsUI';
       V_PROVIDER_VERSION                   :='1.0';
       V_PROVIDER_ASSET_ROOT                :='assetRoot';
       V_WIDGET_LINKED_DASHBOARD            :=null;
       V_WIDGET_DEFAULT_WIDTH               :=6;
       V_WIDGET_DEFAULT_HEIGHT              :=2;
       INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                         SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                         WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                         WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                         PROVIDER_ASSET_ROOT)
   	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
   	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
   	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
   	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

        V_NAME                  :='WIDGET_VISUAL';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='data:image/png;base64,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';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='VISUALIZATION_TYPE_KEY';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='LIST_TABLE_WITH_HISTOGRAM';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='SHOW_N_ROWS';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='25';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='SHOW_MESSAGE_FIELD';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='false';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='time';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='LA_CRITERIA';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[{"jsonConstructor":"laCriterionField","data":{"id":"mtgt","_value":null,"dataType":"string","displayName":"Entity","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"mtgttype","_value":null,"dataType":"string","displayName":"Entity Type","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"msrcid","_value":null,"dataType":"string","displayName":"Log Source","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"srvrhostname","_value":null,"dataType":"string","displayName":"Host Name (Server)","displayDetails":{"targetType":'||
        '"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"sevlvl","_value":null,"dataType":"string","displayName":"Severity","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"sefDestinationEPNwAddressPort","_value":null,"dataType":"string","displayName":"SEF Destination Endpoint Network Address Port","displayDetails":{"targetType":"charts","emSite":""},"role":"Group by"}}]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Denied Connections by Destination Port has been added for TENANT: '||V_TENANT_ID);
   END IF;

       ---3312 Top 10 Sources connected to Insecure Ports
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3312 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Sources connected to Insecure Ports already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3312;
       V_NAME                               :='Top 10 Sources connected to Insecure Ports';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='Top 10 Source IPs connected to insecure destination port (20, 21, 23, 69, 110, 143, 389, 1918, 512, 513)';
       V_FOLDER_ID                          :=2;
       V_CATEGORY_ID                        :=1;
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Observer Endpoint Product'' = cisco_asa and ''SEF Destination Endpoint Network Address Port'' in (20, 21, 23, 69, 110, 143, 389, 1918, 512, 513) a'||
       'nd ''SEF Category'' in (network.connection.up, network.connection.deny) | stats count as Connections by ''SEF Actor Endpoint Network Address'', ''SEF Destination Endpoint Network Address Port'' | rename ''SEF Actor Endpoint Network Address'' as Source | sort -Connections';
       V_WIDGET_SOURCE                      :='1';
       V_WIDGET_GROUP_NAME                  :=null;
       V_WIDGET_ICON                        :=null;
       V_WIDGET_KOC_NAME                    :='emcla-visualization';
       V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
       V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
       V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
       V_DASHBOARD_INELIGIBLE               :=0;
       V_PROVIDER_NAME                      :='LogAnalyticsUI';
       V_PROVIDER_VERSION                   :='1.0';
       V_PROVIDER_ASSET_ROOT                :='assetRoot';
       V_WIDGET_LINKED_DASHBOARD            :=null;
       V_WIDGET_DEFAULT_WIDTH               :=6;
       V_WIDGET_DEFAULT_HEIGHT              :=2;
       INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                         SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                         WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                         WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                         PROVIDER_ASSET_ROOT)
   	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
   	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
   	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
   	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

        V_NAME                  :='WIDGET_VISUAL';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      := 'data:image/png;base64,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';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='VISUALIZATION_TYPE_KEY';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='time';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Sources connected to Insecure Ports has been added for TENANT: '||V_TENANT_ID);
   END IF;


          ---3313 Top 10 Sources Connected to Unassigned Internal IP
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3313 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Sources Connected to Unassigned Internal IP already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3313;
          V_NAME                               :='Top 10 Sources Connected to Unassigned Internal IP';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='Top 10 Sources connected to unassigned Internal IP addresses like 10.10.2.%';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Observer Endpoint Product'' = cisco_asa and ''SEF Destination Endpoint Network Address'' like ''10.10.2.%'' | stats count as Connections by ''SEF Actor Endpoint Network Address'', ''SEF Destina'||
          'tion Endpoint Network Address'' | rename ''SEF Actor Endpoint Network Address'' as Source | sort -Connections';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      := 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAALoAAABcCAYAAADZE69uAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAABG6SURBVHhe7Z1tbFvXeccf0qJeTEmWZFmiKDmOE780duyosR27XgKnLezWSZYga7elHZwPhVMgGLABa4t8WjZsQ7Huw4phA/ZxGLJ1XVGgTVDXSOEmSBwnbuw4lmzLtmJLsmxSlCW/650i2fM7uldVY4m8tO5RGer87AveF+pe8vJ/n/s/z3m5gYxCLJYiJ+i8WixFjS9Cv3FtwJmzWPwhOZly5vwhq3Xp7OyUmzdvytatW+Xs2bNSWloqtbW1+rW/v19u3bqlt/30eJd890RKqkPOH5qATxmYmjWG6WO4Z9r0MRbgPI1lgvLlhlH5z+BfqhWVaprHQdNpSVeqv//igenIOzE5KaUlJc7S/Mkq9CtXrsipU6dk79690tbWJslkUgKBgNTV1UlFRYW0t7fLnj175MfHuuSlI+NSU2r6DFsKhdF0UPY2jcprwX1K+NXO2nsEoVcpiX/lvWmhJ5XQQwsldK90dF+RG2UrZMl0yPIZdf1MplJSsmTJ76Ki3+hjpNUx1Kk2cQy1/zSnWv0P6nhg5ouk1HdYwncwCL9FIFgitaG0bAh2qDXzvJWr85IJpkWWb5m+LxSk0Pv6EtLUFHGWLH9QMkowAbNCl4zyzwEVdA'||
           'xiTOjYlHA4LPF4XGpqavTrfffdJ+Xl5TIwMCDXrl3T/nzdunVy5swZmZiY0NseeughSbS/IZHOfxQpwatZspIaF2l+UmTH950V/jKpCnElJZ8tEc6G38eYvvSXLVumxX358mUZHR2VqqoqXQBF4NXV1TI4OCiVlZVy584dPd24cUOuXr069cdEkfSEmpJ28jIRES0Lii/WJXblkjTXL1U/oLPCAEnlC0N4dIMklb8NGfG3KhCo06w9uip4BUMVIuV1zjZ/sRF9dnwR+pVYTFqiK/TvaQryqiHjP6CJY6RlOFMuYfWb6TiuzvYSt8RlACv02ckqdNKH2JRt27ZJR0eHzpu3trbK9evXZWRkRJ3USXnkkUck0fZziZz/O+XRq5y/tEyTuS2vlP+z/ODpp3REz6QzRrMiVuizk/WMR6NRnS9H0JFIRJYuXarn0+r2y3JTU5PzTkWAMGVwMr1/JlPH4MXyB8Uf6xLvU9ZlhugNsDDWRXn0kjyiLacuM+kszIEqqE9ISEpVFE/pQruyLjai52RBrYtXYr1d0lynwlbG4A/oVhgZJK9jINqSsMjSBmdFbqzQvWNU6HjwI0eO6DQieXLy6diVUCikPTntXvDsO3bskHPnzun3P/300wvj0bn9z/uSzEE+xyAf3vIlkS/8wFmRGyt07xgVend3txb12NiYOlklugKJnDrryKsPDQ3piYojRM77NmzYMFVhdOH76hc0WGFUiEJv3iWy/Z+cFbmxQveOUaHfK1cSg9ISqXeWzDChptKpWWOYPoYVu'||
           'ncKUujxng6Jll9Tc+ZO8HSDK4NMpjLqGIR1D6RTyp83itSsc1bkxgrdO8aFzuKBAwfUj7FEdu3ape3M+Pi4pFRBjXQjTQR45X2JREK2b98+5dE7/96wdVECnP81mZ18jpEaE1n9nMjWv3VW5MYK3TvGhY6g6XBBIbSlpUULnVw6vpy3sj0Wi+kceldXlzz66KOSOH1QIpf+Xf2CYWcv/sOH9Bhr75mM+hfwehSEvnK3yOa/clbkxgrdO8aFfi9c6b8uLY1m2m64WI/uDSv02ZlV6NR8YleCwd/9ICyTiSEDQy8jtrnb4xdPSjRwQc2Z+/Ip9ZmWzPg8JsjrGCl1WTTtFKlscVbkxgrdOwsm9KNHj+rceV9fn+zbt08v0/6clCJt17ds2SKHDh2Sl156SXn015VHX2RtXSZui+z8V2VfvuysyI0VuncWROjkymlvTgRH9FQc0SGaCiOWgWW2k19PdBySSN9/q19wqd5mAj4mdxKT5HWM5LDy538t0rjNWZEbK3TvLIjQ8+Vy/w1Z2VjrLJnBenRvWKHPTk6hUwNKkwDSjdSSEsUBr+4S7/yNRMePqr2ZkwlNXIO5om0wKd3XtkmyqlbWtX7OWekd0w3HrNC9s+BCP3jwoG7fQt9Q3nr+/HltW0g/Xrp0Sfbv3y/97a9LYyG0Rw+OyOHub8tI5D75ygt7nZXesUL3RlEKHci4AAJ328G40R3B9507LE23Dqi9lev3mcBbRB+Xs/1fkvGaemn9o887K71jhe6NohV6LorFo1uhe+MzLXQi'||
           'NelEFoncRGwyEHhzPDoZF7atWLFCD3/BPOuZpiL6L1VEzTOiJ0dENr0sUnW/s2JuFubkWqF7oRCEjp1Go/X19XoenfJKjf3t27f1yBUzmRZ6b2+vHnKO6v2ysjJpaGjQgqYwumrVKhkeHtZiJ39OswC2cRD6kE63dcnXo4/fFNn9PyLLH3FWzI0VujcWi9Dff/99Legnn3xS3nnnHVm5cqWuwHzggQf0MIrodCa/Z13o9Ew2hYjOPNGaeXLnXARcNUwcgLbq+HOifl/nb6Rp7H21tzzNRWpUZO03RMLNzoq5sUL3RjEL/Ws/65Wvr6+Wb2yo0cs4ECox0SCOBF26g+B+ms+MR7dC90YxC736hx3y3e318urOBj14Fr3gcB5UbiJ4Kjqbm5v1NtbP5C6h43sYnYurhFaLWJfly5drq8Kt4f7779fNc7mCsDMPP/ywJDp+LZHEa+oXrHD24pHkkMi2V0WWrXVWzI0VujcWi3X56KOPdPOU3bt3y+HDh7WwEf6aNWvkjTfekBdffNF55xR3CZ0mujS/xaLQTBfvzuuFCxe0d8f7YGsYf5Emulr41qN7wgrdO7Me48AzImv+TGT974vYhaFY3ArNT5PVuuDNZ7ZgnAvdelE61d7y/PL0u1y5R6R8ubNibqzQvbEYhJ5RQr/U06MjOEGYh1JQnsSzY13oHESgZpBcF188+kK0R7dC98ZisS6nT5/Wgsa6fPDBB3qQXAqjjCpH5hBZP/jgg867Zwgdv4PnpjUiVwaRnNsA2RbSiAxvwZMuSON'||
           'gbXgPWZf169dP9TDq/Q/1C+bZw2jilshXfyYSyt3q0QrdG8Us9O5v/kiWPbdB6v681VnjnekzTuin4IlHZ/hoSrEUTLEvJOIRPKVbho/Go7ONi0ODBRmOqSme56T+xvgYFpZiIdwaldLoMj1P4OVxQ2RaKC8SxQnAdPUk2h87dky/z8UX6xLvOSPR8kGl2XwiiTosvXSiu9Tlljs62IjujcViXT755BNtXejAf+LECV2vQwDevHmzrkzCo2NrXHzy6P3Kozc6S2awQvdGMQt9T3u7vNDQIN+K5P8YoaxCJ6WIwaeaH0tDjRO5dYw/toVSL813e95qk+D/fiJSkfsEByYzEoxUyr+9/JD8S+NKZ21urNC9UcxCf60/IZuUxW4NV8rJkyd1OZEIzpNZKEdir8nCYGtIfc8k6xmnOQDeh4IpO6XGyX3Fw+PlNemMZEaTaprMOaXH1PsmJmVs/jcSyyLj63UR2VQxNXYQlZjoksBLUgRvjnVxhzX/NP5Yl+5eiZTWqGA1Y1fsdo49B1RESzRUyMoS74/tsxHdG8Uc0Zf9/1X5zoawvLop//GD/BF6X1xamqLOErBLfzsyW6F7o5iF/pPYuGysLpGNVUvkww8/1BmXxx57TBdGeTAF0ZzcOVaGGv1nn33W+cscQidNw87wO/geNyFPaRc/xI43bdok3YdOSvC/ziqPPvXB0kMTsvrH31Rz/ondCt0bxSz0/vO3pbK+TMLLy7R9ps0Vozm7NaG0x8Kzkw4nDc6jQl2ynnEayrADt9KICiR2hk'||
           '+nAwaTBpeCT1fXDK94dovFb1554Ygc/L9Leh5tImo0uXr1al3dTzssypVUbM4UOfhjXVSBtWVFRNIpFa6U6vUDqcJlUxt9wkZ0bxRzRD//0ZAsj5ZJfVNI3nvvPR25n3jiCZ03J9tCIRTr8uabb+rA/Pjjjzt/mSOieyapfr1URoIVITWV+i5yiwXWbzmjRN6v57HMa9eu1VkXnoxIFHchHZ61h9GnweSTXnzmmWd0dyVqnxgymua6rGe4Cwx/14HjUt07KfUv73D+0n9sRPdGsUZ0RDpy40+ktPxrEqr4C70OS+2OL0Q0p16HtDfrSTcy75L1jNP/zm3qiOfhqqFTBlcRvmjjxo162xT+ZlkslpmgrnDtj5TIX9DL7777rrz99tu6ISJWhQpNAi8dgmjncvz4cf0+F188eizRJ80R049ftBHdC8Xs0Yd/8UMJrd0upet36kaFZFbo9Y/YieIEYVrX0jiRiI5vd/FH6J09Urq0Rsqbq6UqYOZHtEL3RjELPbGvTCqffUUq//QfnDXeydujU5qlzTpDR9ME4KmnnpL4wRNyqP2qtH7vi7I5aKYgaoXujWIWeiadkkBAfbd7cMl5eXSGEqAgyrVBGmf61hAMqkJCSILzvjdYLHMTCN6byMEX65K42ieRBuvRc2Ejunf8PoanM07axoVqf4z/zOvj8p20jKRGnCWLpfDIKXTSNG+99ZYu5TJ+BvOkcPDoPO4F2m+KXB1J6HmLpRDJKXQGKKJzBW0KqG1invYuFExpawBhdYcJBU2PdWux3'||
           'Du+ePTribjURWY20/Uf69G9YT367Hg+4yTj3VdsDL6dhD2cvp2W60k9a7EUJJ6FTgsx2v2ST6cHdk9Pj37sC1wYSsvguApVFkuB4tm6UK1KGxeiOX31aKfOI1/Irb9++rJsXdMizeXm2rtY6+INa11mxxePPjjQJ/UrbB49F1bo3vH7GDnPuNt4xp1ntC6615FLpzENdHXckeT4vK8Xi8UYOYVOX1HGokbUtH2hUyqpRh7DyDYY7BuTyfGUnrdYCpGc1oXmjnSKpiEXDweg+xLQ4J12L4j+6NvnZcvOtRIqM3dLttbFG9a6zI4vHr2vv1+a7JB0ObFC947fx8h6xhlSgCfQAUMLMOA668i2MCqSuy0z2atfLZZCJavQKYS6XZLw5IzFyDr8Os+JYbhe7gfBwKh+j8VSqOS0Lu5m/LnbARV/Th6dZXLq8dgpiTZv0u8zhbUu3rDWZXZynnEEzgSImp7ViNxdhrSY9ecWy3y5K6ITselNjZjJtJBlofYTT07+nM6nZFtowcg2xteIxz+QaPQLzh7MYCO6N2xEn527zjhjoFPdT19RLAqNuCiIMh46VgXPTqGUtCIDOUJAbIsuS2HjS3oxFjsnzc2fc5bMYCO6N2xEnx1fznhGph6gZLEUKlmFzlO+Ojo6tGVx50ktuvn1ixcv6veVBG7oV4ulUMkqdAqkeHMKoTQFoD06Y7kgdjIubjZGGaCpV4ulQPHHo8cvSXN0lbNkBuvRvWE9+uz449EzdphoS2GTM6KfOnVKtz1nuGhsC+lGRgbg'||
           'qRd4d8aojsd+JfV1p6W04m+cv/IfG9G9YSP67GQ94+TL8eN4ca4H8uqMvUihlNw6+XbAqjuVpMZwa2FNEjA0QKpLQP0z/T2my00GCQQX4Bg+f495e/SxsXEZGIhJXW25lJY1qig/6WyZP3xZRgajcuo2NbQ1NbrlJMvz/NjTuBcxBe+h4RGpXFqhownLfh+DcQOHh0akqqpS3w39hGNwlw2FynTQcZMFfn4HEhJME+oOXxkO63m/zxPugX2OjI4qTdXqmnoqMefLvIX+8ccfS1g/ybdNnnvuj/UH9QsETQtJ7iSDAwNysatLnn/+ef0AVb+EwjHa2tpk1apV+rswusH+/fv1BeUXfH4Gp2fwp+PHj0ksFpd9+/b5egzEgc2k2QbH4vvs2LHDt9/DFSG14Xxunli4c+dOPQgtgvcD9xi0lGWf/PY8UaXRh74O8xY6bWBo6HXz5k3t4/3GTW1yZdeoiM4ro4T5CSeX08B3qa6u1sJk8hM+Nxcn34fRFPz+Dnx+6jp4WhtCJBgw+QlWleEIuaj4/Hwfv78HdyU+/8zfm4t3vviSXrTcDQX3uS587Bin3e+LyTI3VugG4O7GXYiBWOlvyx2PV7wmVolIRe0yEde1L2vWrNFPP7aYwQrdENQiY4O4vdOsGeFjkYjiDBuC6N3bMnaG15lPUbP4ixW6ZVFgPjltsRQAVuiWRYDIbwGrXekYIfPLdAAAAABJRU5ErkJggg==';
                      INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Sources Connected to Unassigned Internal IP has been added for TENANT: '||V_TENANT_ID);
      END IF;

  ---3314 Top 10 Connected Sources
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3314 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Connected Sources already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3314;
          V_NAME                               :='Top 10 Connected Sources';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='The Netowrk IP Addresses and count of connection for the top 10 Sources that connected to the Firewall';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Observer Endpoint Product'' = cisco_asa | stats count(''SEF Actor Endpoint Network Address'') as Connections by ''SEF Actor Endpoint Network Address'' | top Connections';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='data:image/png;base64,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';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Connected Sources has been added for TENANT: '||V_TENANT_ID);
      END IF;

  ---3315 Top 10 Sources by Bytes Transferred
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3315 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Sources by Bytes Transferred already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3315;
          V_NAME                               :='Top 10 Sources by Bytes Transferred';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='Top 10 Sources by the bytes transferred';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Observer Endpoint Product'' = cisco_asa and ''SEF Units Transferred'' != null and ''SEF Units Transferred'' != 0 and ''SEF Category'' = network'||
          '.connection.down | stats count(''SEF Units Transferred'') as ''Bytes Transferred'' by ''SEF Actor Endpoint Network Address'' | top ''Bytes Transferred''';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='data:image/png;base64,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';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Sources by Bytes Transferred has been added for TENANT: '||V_TENANT_ID);
      END IF;

 ---3316 Top 10 Destinations by Bytes Transferred
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3316 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Destinations by Bytes Transferred already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3316;
          V_NAME                               :='Top 10 Destinations by Bytes Transferred';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='Top 10 destinations by the bytes transferred';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Observer Endpoint Product'' = cisco_asa and ''SEF Units Transferred'' != null and ''SEF Units Transferred'' != 0 and ''SEF Category'' = network'||
          '.connection.down | stats count(''SEF Units Transferred'') as ''Bytes Transferred'' by ''SEF Destination Endpoint Network Address'' | top ''Bytes Transferred''';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='data:image/png;base64,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';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Destinations by Bytes Transferred has been added for TENANT: '||V_TENANT_ID);
      END IF;

 ---3317 Top 10 Sources Connect to External Resources
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3317 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Sources Connect to External Resources already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3317;
          V_NAME                               :='Top 10 Sources Connect to External Resources';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='Top 10 sources connecting to External Resources like 53 DNS, 123 NTP (time), 25 SMTP (mail), 20/21 FTP, 22 ssh, 110 imap (mail client), 143 pop3 (email client), 445, 135, 137, 138, 139 Windows netbios';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Observer Endpoint Product'' = cisco_asa and ''SEF Destination Endpoint Network Address Port'' in (53, 123, 25, 20, 21, 22, 110, 143, 445, 135, 137, 138, 139) and ''SEF Category'' in (network.connection.up, network.connection.deny) and ''SEF De'||
          'stination Endpoint Network Address'' not like in (''172.16.%'', ''10.%'', ''192.168.%'') | stats count as Connections by ''SEF Actor Endpoint Network Address'', ''SEF Destination Endpoint Network Address Port'' | sort -Connections';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='data:image/png;base64,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';

           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Sources Connect to External Resources has been added for TENANT: '||V_TENANT_ID);
      END IF;

 ---3318 Last Network Configuration Change
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3318 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Last Network Configuration Change already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3318;
          V_NAME                               :='Last Network Configuration Change';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='The Entity and time of latest Network configuration change';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Observer Endpoint Product'' = cisco_asa and ''SEF Category'' = network.configuration.begin | fields Entity, ''Host Name (Server)'' | head limit = 1 | timestats count';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      := 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAE4AAAAnCAYAAAChUX6PAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAO/SURBVGhD7ZrJbttGAIZ/kpIr11ItOZGXJiiaOm4dFGgQ9NICufiac4A8Qd+op176Bj01RXtvkCJtkNRwLNjWTnHTYlGiJVILxQ7JKSQGEuUwvhiZDyDEf0hwOJ84Q0pDziGA8c7w9JPxjjBxEWHiIsLERYSJi8jCu6oztnB4VsL9e/cwmUxo6fWE53k4pA3v//jAkWNx3trCK46LJXBrK+utuxW6eq/j4kr78aiKMf3u5+1z6WVG/QfxHPdTroYf9m+D8y+WK4GNcRFh4iLCxEUkIK7T1PD0tz9oYoQRENfWdWxlN2lihBEQl0mvwzAtmhhhBMQJKx9hZ/MGTYwwguLIk/FgNKaJEUZAHHgHeqdDAyOMoDjEwE/YFXcZAuIkuUZ+o8ZpYoQREJfJbJDHEf+HPSOcgLgb2S3s7e3SxAjjrTGOcVmYuIgwcRFh4iLCxEWEiYvIhyGO4690vsFl4WRNvpBHs27gu+8foN1uw7ZtuuV6kRB4/Hwi48nuNgQiL+rMlKspHo8jnU57eaE40+hAaffwxWef0hLGLAu76mpq3ZNWKBahiFVYM3PSZ4UiXfPRZAnWcHpFKpqKuiLCopOZRleHqtWhNVteHo8GqIgiXh'||
           '++8bKLTcoKlRpNPmVS9yyKpkE/12kCxGoJ5UoVhjn08oWhQxRlqA2/Hg/bgq5P/5ytN8i5kfNrd3teduwRVFWDpGhehjNGoVwlbSz5meDWI4kSesPpHyBLx7iWquA4d4hGo09LgGJ+elCXqlhEqaTSRERddKEpKlnaXh70u2i1DXS7fubIyUpKE6nEipddeNiQagpNPn/9+QyDmf5gmh38+svvNAFNRUJFVaGW/UaPrT5evvobpeL0CzjOvUblbHpuw76BktqEWpG9zAlxnJ78g6PjspfdzizXJMhVkWbiQJPxJpeDQtvjsnRCulQ4w53dPZqA89Y54jEeqfU0JuMR+iMbydUE3Tof7xUKMjrzdIR2q5xMHAhk/HHpk2GhNwKyG+tevizuuCsIAk1+Pd7rDpi4LyvQ0nm4TV50t3DIcf2e8v+x367HZak4OS/hxatncFZTODg4QFetoklkOaSrSYk09jez+PKrO3TvaBy/+BemYKFcUPH5/l18+83XdEs0lKM8np8e4eZaAnU7icePHtItV8dSccXTE2R3bsEy+4itrqEli0gmU7i5sw2zZ8J2OKQ/WaN7R2NAxqh4zIHWOAcXX8F29v3mPUbWEOagj4sLA4mPM9jIJOmWq2OpOMZ8lt4cGPNh4iIB/AdDxB562IXz7wAAAABJRU5ErkJggg==';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='TABLE_COLS_VIEW';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='SHOW_N_ROWS';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='25';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='SHOW_MESSAGE_FIELD';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='false';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='LA_CRITERIA';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[{"jsonConstructor":"laCriterionField","data":{"id":"mtgt","_value":null,"dataType":"string","displayName":"Entity","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"srvrhostname",'||
           '"_value":null,"dataType":"string","displayName":"Host Name (Server)","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}}]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

              DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Sources Connect to External Resources has been added for TENANT: '||V_TENANT_ID);
      END IF;
 ---3319 Top 10 Oracle DBs by Threats
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3319 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Oracle DBs by Threats already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3319;
          V_NAME                               :='Top 10 Oracle DBs by Threats';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='The top 10 Oracle databases under attack with the highest threat count';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Record Type'' = correlation and ''SEF Destination Type'' like ''*Oracle Database*'' and ''SEF Category'' != anomaly | stats count as Threats by ''SEF Destination Endpoint Name'', ''SEF Category'' | fields Threats, ''SEF Destination Endpoint Name'' as Database, ''SEF Category'' as ''Threat Category'' | top Threats';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFAAAAAoCAYAAABpYH0BAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAATjSURBVGhD7ZpNbxtFGMf/Xr/EdpomtV1CGtpCA01bIUIL4gScihBRBd8CceHEhU/BnR4RPcAFUIXKBQlVtHGqFCJiktiO3+K3rV+ythOv117vmllnELT1rkfx2nmxf9IoM5NnZtd/zzzzzIwtLQJGHBiO/h1xQEYC9ojpAsqyTHPGqAyeg8VGg9WuH5guoCAINGdMs9mkOX1YbDRY7fqB6QJyHFuXHGehOX1YbDQsFja7fmC6gFbGz2JjMGSx0WB9Zj8wPYz5ZjmOzx6KOG03/lTaqOn2aBabUl3Fd7dm8cn8FK0ZLKYL+P3jBL583MCkYzDDQpAUfP3Bi/hwbpLWDJanpnDgr0cIhxPg+SdIJVNIZ3mS55ErCghHQmhIIh488GOnUkU+m0KGzyEai9PWw8lTAk57PRB2ZaRiIfhXAqhWikjzGfy5ugGPx4PE9jZq1RrymSyyBR55vgz/kh9NVUaV1A8joyncI6YLeOdRHJ/7pcEJWFfw7eI53HrthCwigQSPiDwOm4F+2r+aqgIbZ4Xew1lsNBpKC29PO3B+0klrBovpAgrFAs54fbRkhEKSdT+rC4sN2copTXBWGy11pkJGqj8j4p0ZN6ac+31qOxib7b9'||
           '2NVGEy+1u51VZAWfv/mzTBSwUCvD5ugsok5e3/+/lO8Fio8Fi93tKxHu3g/j10yu4WI4ilIiR6KKCjxYX4b9/HxYbWU8tJDlPYVLOIJJzYtLjwiuzF3D9xlXay/MMjYB7MrBaVLHg5TBhB1qKjEaLw5ht30VoLkMUJdKPFUpLcx12KEoDY05Xu70epm/ljiqnWgLelX7ARGsHe1WJfHIrpHK5LZ4kVkldDW4XEY1I6bA7YCPTl7OPkWkuk6SSHZGCeqOBYq643yFlaEYg8ivATzeBj39Bsn4RP979GTNuB0Q4MX91BqFkFe+/dQV/rD6EXLPh7PlpJMIhvDo3D0EsIL4tYW5mChdeehmv37hGO+2HgDsV+DynaUmfhqLCYTWeAKYKSIJ9iDnAdZasSw5a2TvmC7h+D77t2+Ql91ezjjTI1Ll5B5Yx4+DXVAH7hPk+UPumJeIn6oJ+quVxiEd4pmIooCLXEYnGUSoLSKd5hLbCiEVC2CPxUkPaQzgSbduFghvYDP17qECUIQ4aHBkRRqm97h1/DAXM8hmEg+tYXl5CIr2LIl9AqbSLtbUAVlY3UCrmEI2sI5UvIfh3kLYaLg7sAyVJgtP5/PapELgLX/QrwDZBazqgTfFb9wCH8WJzon1gJ/HakHgJTRJnNWvGqaXSBscb81fhyBJ85d/IV2MQKshVqAtfEJNxWtGZ4zACzRewWILP2/1oqUGie4e2/zTgRE9hXVpsd7Qst58WxitSVrt+YPoITCaTcLvdXW/TZEWB3Wp8XMRio/Gsna'||
           'qq8Hq9sDK07RXTBRw2TB/7m8EwzemjyBIKpQot6ZNMRCAxeIRINEZzg8dcAdUd+FfTtKCPhWzmi4UsLelT2clDqncPd7a2EjQ3eEyfwooiE99jp6Xe0I7Yx1xudPuJzLNH84Nk5AN75EgIuPskjkhJQjKUwRtvXkJiK4pLly9jLbCJ2Wkvri1cN7zlO0yOxghUJZRrCtKxNF6Y9aAi7La3hHtiDWcm3Bj3noNnwvhu4rAYTeEeObwQ/oQwErAngH8AyL2fgvAHp4YAAAAASUVORK5CYII=';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Oracle DBs by Threats has been added for TENANT: '||V_TENANT_ID);
      END IF;

 ---3320 Threat Trend on Oracle DBs
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3320 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Threat Trend on Oracle DBs already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3320;
          V_NAME                               :='Threat Trend on Oracle DBs';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='Historical trend of threats on Oracle Databases';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Record Type'' = correlation and ''SEF Destination Type'' like ''*Oracle Database*'' and ''SEF Category'' != anomaly | timestats count as Threats by ''SEF Category''';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAE4AAAAoCAYAAABQB8xaAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAT+SURBVGhD7ZlbbxtFGIZfH2LIqY3apK1bWkooEBUQokhwgZoWIaH+DC655D9w28tecMlvQEIVEhGgiqK2ThPSJE7ixPFhvevzrr32ru317jAzHocY6kSx3CQ2+1xk5/12drJ6d76Zb9ceQoHLkfGKo8sRcY3rEde4HulqXK2qI6lkhHL5N103h4ZZwZ+hddy5/Smspg2c1j3E64entEWPAZDJ64DTFCf24QFG/H4h+sNw7Kqxn4DAJHB5XgRePcOxxtFZB49PiOPB3Rx6xDWuR1zjeqTDOC2fw8LCr0K5HESHcXpZxfjEGd6uGRVs7sR5eyCgJcdx0lGOqPkMMmoVc+/MQs1KSGQNfPTBu6e7jqP1mSf2kJYjEyDB24B9AnWcruawuLKFu3c+F5EBIfEzNw6Xju++O1LV5x3B9IVpoQaMoyaEtACEvuPNdCoJWckil82iUjWRkpLQ1DyicQWqpkKWFd5vPx3GEY8Ds2oINeTIvwHL93mzRM0JPX2CpRfrUIslGEYJz//aRCK6Td/X03j4yyM4Vh2mWef9GR2pWlYLePxkEffufSUiAwJL1RGaqsETSlVV03D+3HmhhpvHaQ0P1iTeTsRiiKyHEd7YhiQpiO7'||
           'GUCxqiGyFISWSSMj//UrUYVwgEMD4JH1y/wPSRh2rBZ23S1oBu3EZ4bVlVEwHizRtHa8XVqMBKRNHqaDRNDVQr3VJ1f3YDRORpIK5t2dF5BRzArvqgZ+VlIyC4MXgYNZxrHbzevDDpoKv565Q3d8K+UDjBoYDZtz36xK+ufmGUP2jY40baLo8/lf1JjY8xh0zrnE94hrXI8NjXJfFzNPn3bRN1111NxaFVjTx8a33afFn0mrklG6+vtfozf7Y2lWvfMEKUB5mdo34PHiwKuHbD6/R87RfH+lqnFbII182ceOtq2hYFpxmc2/j8vn8sPe+e3ng9XnpX0JjDn/CXlp1s2Edx+E9/H4fLwNtm9aDDNrHR/u0NbuGzwt6ZNcwzc6zYJPVkBQ2JqM9Jte0Hw3QKj/QihGLj2OLMVifgN8Ls25hdHSU9+kbzLiDiESjpJDNEEdoxm48Klot8rksSefyvG036ySeUki5XOaasROLk0wmK1SLZEoWLXpNwySm5ZCqrotIk8SSEr+ujVrMkUKpIhQhJTp+Pi0Tw7JbAcciqqqSnKpx2agbRM7mSWrf/+knh65xtknf6VYWIefb72kO1leSos1+OLeQprMzsZvgmj3lSqmCuBTjmlGvGnjy+yO0R8grEWxE0kKx7Kpi6flTPP7jhYiwMXQYpbLQLALoNNYmQcfP6iUkNlv3wjIi9OwZtrZbn/tHaFakt5cQWpO57jvCwK5sbYSJZf8z36xmk2jFolBOaybaFinrrdlgWzUipztnlyTJxKwZpNGwRI'||
           'QQfW92vQQ6nqykO2aLWS0Ts94UqkWlrHVkAnFsUqkYvMlmXK6gEcOoct1vDn3lCodWsSvtwDMxiTvz89haXYEdcFBXyqiMjeHadBBz770pencnsrgC2VDRqJnwjszgy7ufiDOHsxlaRiSWweSlAF4fu4jPbt0UZ06OQ42LhMOYmplBo17HxNlzSMV2cPbMBKaDl1HRq/D5A5g6My56d8fQTYyN+5FSsnSxdnBj9qo4czi1ag0qXQ70mo4LweuYmuzzQt8Dw/GSfwIcujm4vBzXuB5xjesR17ieAP4GgvRY/hi+AlgAAAAASUVORK5CYII=';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='COLS_TABLE_WITH_HISTOGRAM';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='SHOW_N_ROWS';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='25';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='SHOW_MESSAGE_FIELD';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='false';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='LA_CRITERIA';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[{"jsonConstructor":"laCriterionField","data":{"id":"sefCategory","_value":null,"dataType":"string","displayName":"SEF Category","displayDetails":{"targetType":"charts","emSite":""},"role":"Group by"}}]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Threat Trend on Oracle DBs has been added for TENANT: '||V_TENANT_ID);
      END IF;

 ---3321 Top 10 Oracle DBs by Activity
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3321 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Oracle DBs by Activity already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3321;
          V_NAME                               :='Top 10 Oracle DBs by Activity';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='Top 10 most active Oracle databases with the highest number of activity';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Record Type'' = base and ''SEF Destination Type'' like ''omc_oracle_db*'' | stats count as Activity by ''SEF Destination Endpoint Name'', ''SEF Category'' | rename ''SEF Category'' as ''Activit'||
          'y Category'' | rename ''SEF Destination Endpoint Name'' as Database | top Activity';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='data:image/png;base64,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';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
            DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Oracle DBs by Activity has been added for TENANT: '||V_TENANT_ID);
      END IF;
---3322 Activity Trend on Oracle DBs
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3322 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Activity Trend on Oracle DBs already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3322;
          V_NAME                               :='Activity Trend on Oracle DBs';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='Historical trend of activity on Oracle Databases';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Destination Type'' like ''omc_oracle_db*'' and ''SEF Record Type'' = base and ''SEF Category'' not in (system.shutdown.background, system.startup) | timestats count as Activity by ''SEF Category''';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='data:image/png;base64,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';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='COLS_TABLE_WITH_HISTOGRAM';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
            DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Oracle DBs by Activity has been added for TENANT: '||V_TENANT_ID);

           V_NAME                  :='SHOW_N_ROWS';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='25';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='SHOW_MESSAGE_FIELD';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='false';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='LA_CRITERIA';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[{"jsonConstructor":"laCriterionField","data":{"id":"mtgt","_value":null,"dataType":"string","displayName":"Entity","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"mtgttype","_value":null,"dataType":"string","displayName":"Entity Type","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"msrcid","_value":null,"dataType":"string","displayName":"Log Source","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"srvrhostname","_value":null,"dataType":"string","displayName":"Host Name (Server)","displayDetails":{"targetTyp'||
           'e":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"sevlvl","_value":null,"dataType":"string","displayName":"Severity","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"sefCategory","_value":null,"dataType":"string","displayName":"SEF Category","displayDetails":{"targetType":"charts","emSite":""},"role":"Group by"}}]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Activity Trend on Oracle DBs has been added for TENANT: '||V_TENANT_ID);
      END IF;
---3323 Top 10 Oracle DBs with Account Modifications on high privileges
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3323 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Oracle DBs with Account Modifications on high privileges already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3323;
          V_NAME                               :='Top 10 Oracle DBs with Account Modifications on high privileges';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='Top 10 Oracle databases with the highest number of account privilege increases like grant of DBA privileges, user account modification, or switching users. (DBA privilege, grant with Admin option, become User, User_history';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Record Type'' = base and ''SEF Destination Type'' like ''omc_oracle_db*'' and ''SEF Category'' = authorization.modify and ''SEF Command'' like in (''*DBA*'', ''*with admin option*'', ''*become user*'', ''user_history*'') | stats count as ''Acc'||
          'ount Modifications on High Privileges'' by ''SEF Destination Endpoint Name'' | rename ''SEF Destination Endpoint Name'' as Database | top ''Account Modifications on High Privileges''';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAD8AAAAoCAYAAABJjCL8AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAOWSURBVGhD7Zpda9NQGMf/Tdu0W+vW1tWxzenaDecb+k28ENyVl4IXIoKo30EQQRRE77zTu90JfgHvhIHzprAycC91xZbSrmvTJJ4nTcraZMnpevaS1R8UcnKePMlzznme/JM0oDOwD03TEAgEjJ8bdJgIG+KkfElmu4OiKKzDbLggyoY4bl8UPGGbeb2l4MdOE9FwEF0d+yD/GjtMYmcaxIY4Sl9sE3IwgMVUxOi3oNUtSZI9eNaF6KtVpGNhw5mfUVQdcwkZ3+/Pm3vauAQPTL/7hQtx+UwEPzsWxtd7c+aeNlbwRs7ncjmsr69ja3MTjb8Fo2MYcJz5ybds5tmy9zuKpuMSm/lvSxlzTxuXZa9i/kMOEyNnJ/jlu5fNPW0ODL7ZbEKWZbN1NunK+V6o0wtRNsRx+7JwDJ6nytO91AseG0Kkr36wBU+nCEgcJ+K5Ft7rFeirn0FyFDnBlz+9RQ512u4TPfDYEIJ8be+2oD6/6TkAlBoHVHv/ipztWgtbjxbZFl/wUqvVQj6fx9raGjY2NtAcIpEjhUIhZDIZZLNZzMzMQE5N9lUx/Yxjzkss570UnqA0NRDlq8CWvfpigJxXmMgJyxS4u4NTC4XDGbwtuWk'||
           'kdCYLPbHXSTs8NoRAX/Q8z4tjZeM5nOckvBci0lc/OAbPU+1F2RBCfQ0mclRk3udwfjRktv3DTl1F/uGVwUSO9Tzvt5JHIufP46tsizN4JnJ0EjnE6MgIe44PIfu5hAk2834Mvi+FFwwGsbCwYPym/4scDZFXq758jUUip/HsBpv3Q+Y8vbdfKSqIhIPmHmfosJP4yuJGU9VxOx3hFjm24NuvsXgUHh0mwoYQ50vX6XOb+22xk/NmuwuNQ+GJsiFE+uqeSnccg6dR8UKUDSHUF0eaWTgUPBVLy7+RiHqJHDrsdC37ckPFlzuznrXhwJynk6Te+FfklJ5cY1ucwauqqhcKBaOaRqNRpCIBXPxUGA6RQ0sklUoZv3PxOBCLGQMxDDiKnPhrf36rI5FTfXqdzfthc15T8HGlgljEfyJnV9Hw4Fby8AVvGL7VWdiCr9frxv9yqPj1dHWgka1Wq4izGuFmU6lUMDY21pk1Gu1eaF+pVEIikTDalj+ypxmyZolsksmk0XaC7Om6a7Waqx35KhaLmJqacsr5o4FWFJ2UoCfJRqPRCZAGkSiXyxgfHzf69vb2jIFLp9NG31FwbMGfPoB/O9NApjUtDwIAAAAASUVORK5CYII=';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Oracle DBs with Account Modifications on high privileges has been added for TENANT: '||V_TENANT_ID);
      END IF;
---3324 Top 10 Oracle DBs with Sensitive Object Accesses
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3324 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Oracle DBs with Sensitive Object Accesses  already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3324;
          V_NAME                               :='Top 10 Oracle DBs with Sensitive Object Accesses';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='Top 10 Oracle databases with the highest number of activities accessing sensitive objects';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Record Type'' = base and ''SEF Destination Type'' like ''omc_oracle_db*'' and ''SEF Category'' like ''access*'' and ''SEF Destination Resource Type'' like ''application.database*'' and ''SEF Destination Resource Name'' in (ALL_SOURCE, ''ALL_USER$'', ''APPROLE$'', ''AUDIT_TRAIL$'', DBA_ROLE_PRIVS, DBA_ROLES, DBMS_BACKUP_RESTORE, ''DEFROLE$'', ''FGA_LOG$'', ''LINK$'', ''OBJ$'', ''OBJAUTH$'', ''OBJPRIV$'', ''PROFILE$'', ''PROXY_ROLE_DATA$'', '||
          '''PROXY_ROLE_INFO$'', ROLE_ROLE_PRIVS, ''SOURCE$'', ''STATS$SQLTEXT'', ''STATS$SQLTEXT'', ''STATS$SQL_SUMMARY'', ''STREAMS$_PRIVILEGED_USER'', SYSTEM_PRIVILGE_MAP, TABLE_PRIVILEGE_MAP, ''TRIGGER$'', ''USER$'', ''USER_HISTORY$'', USER_TAB_PRIVS, SYSTEM_PRIVILEGE_MAP) | stats count as ''Sensitive Object Access'' by ''SEF Destination Endpoint Name'' | fields ''Sensitive Object Access'', ''SEF Destination Endpoint Name'' as Database | top ''Sensitive Object Access''';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAE4AAAAmCAYAAABqDa0qAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAALwSURBVGhD7ZnLbtNAFIZ/X5O0ddz0onIrgkqIi9iw5AVACIkVLwBrlrwBYgs73gcJhKgKC0BURG7UpEpc59akdZvYHjvY1ahFgIQ77lRBmW/jcxzpTPLH5zJjaRQDwYmR6VVwQjIJ57outfgRhiG1+MAaP5Nwg8GAWvwIOVcS1viZhJMkiVr84L0Ca3xR4xgRwjEihGNECMdIJuEU/r0Bmsp3Edb4mXYOXzdt7KgmFPAaGSSQKIQqK7HNYw0Jrh/g/opJ/fRkEu71WwsvvhAYKr3Bg2Tk4TjL2fsE/vPb1EtPJuHevNvAq/UIhnYGOcuJhhvAeXaTeukRzYGRX4SL0O724Ac+hoMDWJVNlMvlo8+cZvPQcuwtNDt7h/Ykc5SqFesHVlc/QS/omDFXUFBduN0+tJIJo5CPN/R7OLc4B7vVRK3q4emTRxOdqqlq3G5/F0WzSL1jRI37B38TbdIRzYGRzHPcy3iOm+GYqklkflNcPMfFqeqd9Rz3uWLDhgFV4vfTSBhBVfglxp5H8PhGiXrpySRcr9PG7PwC9XiRHG0nWy5esMXP9FcSnjlECTgvwhpfNAdGhHCMCOEYEcIxMvbCSTLfr8gaP9M4Yts2NE2jHh+'||
           'SN+2Kwm8cSRM/kUhVVZRKx/NeJuEmmUx58P7DGrX4MAo9bFRr1ONDp+2g7nSol54MwkWw6y1q80FSclCkiHp8aDZqkOWTlxuRqoyMRVft97p/nIAQfwj/t+3QTrsVb5EI9Y5JDgJ8z6Pe2TAWT1x5/RuqWw0snZ9HY3uIOSPudp4LYlyAHgzRbTfx4OE9VGo7qK1/xOyV69i2LBRNE1dWluFjGo61hn5UxLWLC9ALGr5bdUyrSceUcevOXRj50+3MYyGcYzcwkmToORWEKBiRA8hx+ydSDjoCDAZDXFpeRn2rinxhGkPvAK3OPq5eXoIXP5WFXA6qpqPX2Y5jTMWxJHg+gToKMJIVFGcXMZU/3Ze/osYxMhY17v8D+An3sk8fvDmd1wAAAABJRU5ErkJggg==';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
            DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Oracle DBs with Sensitive Object Accesses  has been added for TENANT: '||V_TENANT_ID);
      END IF;
---3325 Top 10 Oracle DBs by Startups / Shutdowns
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3325 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Oracle DBs by Startups / Shutdowns already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3325;
          V_NAME                               :='Top 10 Oracle DBs by Startups / Shutdowns';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='Top 10 Oracle databases with the highest number of Startup and Shutdown Operations';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Record Type'' = base and ''SEF Destination Type'' like ''omc_oracle_db*'' and ''SEF Category'' in (system.shutdown.admin, system.startup.admin) | stats count as ''Startup / Shutdown'' by ''SEF Destination Endpoint Name'', ''SEF Category'' | fields ''Startup / Shutdown'', ''SEF Destination Endpoint Name'' as Database | top ''Startup / Shutdown''';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='data:image/png;base64,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';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
            DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Oracle DBs by Startups / Shutdowns  has been added for TENANT: '||V_TENANT_ID);
      END IF;
---3326 Top 10 Oracle DBs with Account Modifications
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3326 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Oracle DBs with Account Modifications already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3326;
          V_NAME                               :='Top 10 Oracle DBs with Account Modifications';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='Top 10 Oracle databases with the highest number of account modification activity. e.g. user and role creation, deletion and Modification';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Record Type'' = base and ''SEF Destination Type'' like ''omc_oracle_db*'' and ''SEF Destination Resource Type'' like ''application.database*'' and ''SEF Category'' like ''authorization*'' | stats count as ''Account Modifications'' by ''SEF Destination Endpoint Name'', ''SEF Category'' | top ''Account Modifications''';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAD8AAAAqCAYAAAAERIP3AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAUMSURBVGhD5VnNayRFFP/150wy+ZjZZAxOiCHBSMSP9eLBg3jz5iJEUXFPIoiLCB78H3RRWUS8etKLB0FwWQ/iTV2EFWSzINFkgySbxXzMZpKdTE9Pt+9VepNMunpqJts1WZJfqEx1/aqm61f13uvXNUZIwAEEQQDDMERJAg85Tp6RxneY0ece6vU6DDStRwyK79TOM0JFJxavQmznG3UP6x7g2pZ0CfiWQRDCNOVLdL88g2icsSqkwIpa4gjCgHaOOmbyUUsz2IJNM7a3TYiJp2FwLs6imHPERLsNn+7a71r4u3iOplKIWhOwswa8cpV2ajBq2McRxQOlL27goT73eMSHBnodG1eHz7cn/qUfAacvathHO+IFOzc3h8XFRdxaXkZt/bZy0EmBUDk1NYXx8XE8XCohc2ZErNppgNTns58co8+T2fe7Nv4qvkxTkQezPbDZz/xGPj8QNezjSD4f+nVc+89D1pFHewYP0fYcFzc18KT7D9Vt0RQHPSko2huhDxQep8u4yCOJ9zwPrutQrdW+8xCdvBrsmK2ktR3wDoOfw62gn28j5qQQl6TilSummacOUUUv4gEv8PHlH2XkMsnZFTkcjWxhth3wjdDEsLODc5m'||
           'fqd0VbYnD/Srw2JuiqjLrI/k8DUP/Zze6Fu1rlMKe7V3HD/l3SXU8U9sHTbNK0f31P8WVJvHdzfBqgYUnejfw7eAH7Ymf+UVcpSHe9H0fCwsLmJ+fx9LSEryNU5Th2baNiYkJTE5OYnR0FG5hBA1aNTaIoAtl949B/+laWVJEos8Pd8nnPTL7p3NruJy/QLeOZ2pN4IxOp8/vJjm7Ufe4oEpgGJ2IbzQaos5S+ZMPbByHNlcmnl2h3S+WQTfPUPcJsbAZYq1cxsDd27AcF7VaDQMDA9jc3MT09PTJFc9JpFn9F/7dbVTsEkwjFBaQyWSwtbWFkZGR02H2SZAEvAZe+PomCj12YsDjAa2CYSd8nZKcR3vu4FLfx0TkRFvi+Nod4MVvRFWT+BDDn1OSk+Mk5xC1B55aEsdon6/Ra+tTlOF9N/g+zViR5HC0f/V3cZWGeJM7ra6uilKhQICdCjKWBdcyqJgJpRXHpQPeNGDzJE16jbYUhfukCLE0HAS4WBTo6BlALa127eRAavaFS2z26a5yEjzyeTb77/PvKcyeUF0FXrsmqpp83sdbl29hMCs/QhLeSkP4GEpmH53yPr3SPpLZwoe9XxHRI/rwjKSvtF4FeO4jUdUi/jQ96qQsD2wF3Tx1iCp6IRVPAbglTKlN7uN+edlprA7E7tLWbRVz186nBEnAC2BdvC6SHAo9u00x8OySOEb7vBfYeCa3ip8Kb9OtFa+0HO3Pz4mqyqdVPEMiPjrGaik+PXiU4e0dYynf50n8zK+imoZ4k990+A'||
           'iLf6xcPniMRZvDjyPdpQl8nVhEB9EtLZgWpbJ8hMU/VpaiYyxlND4hkPp8z6ezkdnLwevfnkfLcZDnY6yzuTVcyb9Dt1ZkeGz2b8yKqh6fb9Rx5WYVPa6VqICHxEz2ADrhA1qKPquOZ53rROym1InjGzUKSM+LqhbxD0KG1y3ExFerVXHAl81mxQ4cBq9muVxGPp+XxgbmNzY2UCgUmniOLQzeUX59LhaLezx/3rsX85VKRRwwyubAPJ/FbW9vY2hoKHEOKysrGBsbi1rkkD7qdIAnw+CJ8eKygHsWViqVlCaqA10T/+AB+B/OVWLHSE9hswAAAABJRU5ErkJggg==';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
            DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Oracle DBs with Account Modifications  has been added for TENANT: '||V_TENANT_ID);
      END IF;

---3327 Top 10 Oracle DBs with Schema Changes
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3327 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Oracle DBs with Schema Changes already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3327;
          V_NAME                               :='Top 10 Oracle DBs with Schema Changes';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='Top 10 Oracle databases with the highest number of schema change activity';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Record Type'' = base and ''SEF Destination Type'' like ''omc_oracle_db*'' and ''SEF Destination Resource Type'' like ''application.database*'' and ''SEF Category'' like ''application.admin*'' | stats count as ''Schema Changes'' by ''SEF Destin'||
          'ation Endpoint Name'', ''SEF Category'' | fields ''Schema Changes'', ''SEF Destination Endpoint Name'' as Database | top ''Schema Changes''';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='WIDGET_VISUAL';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHcAAAAoCAYAAADE4WWoAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAR/SURBVHhe7ZtbbxtFFMf/e/PaufnWOE0akraQtCVUQW14QH1AfADe+QTcJG7fgKc+8SV4QOIBBKoqgYQUWkFFoLekjULSBiXNpbbjxE5s79prz6yZdVZ9Aik70STOsD9r5BlLZ2d2/p6z54zHSouBEClR/fcQCQnFlRjh4vJ6/eOwO+o+RSNc3EKh4NeC0STErwWDui5arATFdVvtwgPvWEUjXFxV5etCURS/xsFhbDk41FgFEj5zJSYUV2JCcSUmFFdiQnElRri4EY0vkjR0za8FQ9c0ruhV09R24UHtzGAZwveWf5xfx/VHBD1GsBnwhsWXYni3c3SzXaxT3H53FFFD9z/pHISL+/3sGj7+vYZ4JNiq8OThGdhh7DyC2m7ZBOsfjHWkuMLdsrf4DOa3PPccpBj/8tlBymHseGy9e/NenUgYUEnMC7dcL2Vx8/YsJicnUNndQdOhyJwZQHZlBUa8D29MXsYvv85AJSquTF3C30sr6I53oWTVkejpweZGHq9PjmOnVIZdtlCxirh27S3ceLSOT5lbTphyfo/yFsHKe2MwjYMFgOvlJr68W8AnV/sxatbZ/DXasYWCOqyaid5uBc2mg+J2DZnTCVQtCyPDQ+1'||
           '5LZWKMFmgGemKo1G38NLQAHLbRbi0hd7eGHKb20j0J9CfTrf70r5geBVVY88MRcdgfy+WV3M4f3YUhDaZyA5eGb8AnTZQqZH2TcSiEVgskIhFFVSrDgYyp2DXHHTHIiBNCtrScHY4BSPKrlWo4KcNgqjema7rsFhNF59dTbMo/WBf3vltB+9/9wzvTKTQU93AjZvTcO0Kdm0b29kt1NwW7s0+hmFV8TS/i0a5jAiLV36enoYRMVCstrCTe4LyHkGd2Ngp1rD69D6e5yvIrmdx9+Ecpq5MtvsSHlD9MLeGz2fY6g5X7pETPnMl5kjE9VyDyxyEjGXf7Ql1ftwId8vfPnyGD+/U0BdwE4M7YT1iCjWK3Efj6Ir8DzcxqqUd9CT3ozdZoZRA8wLSDkO4W66zMJ0H3qMrhFKuM02Uuu3CA+fpHOGEAZXEhOJKTCiuxITiSkworsQIF5c30+I9C+ydk+ax9eyO5Yy1QITnueVyGY7jBJ4Ab1g8k8Zvt//Oo9N/9em6LtLpNMuBj2ffWbi4IceHcLf8zVdfg2dr4P6DGb8WjGJhE/NLq37r4JTyG7j3eNFvHZymY+GP32b9VmchXNyLr01wdTI2dolra7lU2mv/iB0UxYhiZCC4nffHs3i61291FqFblpgTLW7u+SZ0M4aoqSKXr2B4MAWXBTa7bPWmknFAM1Cv7MFqOKjZBGdOp0BbChRVg21ZiEVNVgcMswsRznPSncyJFndleQmLC08w9fab+PPWPJIpFzbtwoWMgYUsQTpuItnXjVOZJO'||
           '7ceoDRlzOY+2sNl8+lUbCjMMgWVDOJweERnB8Z8q8qDydaXO9P1grLTQkh0HX9RdvDdSnLW/dXY6NJEDF0UEr9tMTFwuIyXr04vp8D8eQ/J4DwmSsxwqPlkOMC+AcWeoEMfNF+9wAAAABJRU5ErkJggg==';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
            DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Oracle DBs with Schema Changes has been added for TENANT: '||V_TENANT_ID);
      END IF;

---3328 Top 10 Oracle DBs by Anomalies
          SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3328 AND TENANT_ID = V_TENANT_ID;
          IF v_count > 0 THEN
           DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 Oracle DBs by Anomalies already exists for '||V_TENANT_ID ||', no need to add');
          ELSE
          V_SEARCH_ID                          :=3328;
          V_NAME                               :='Top 10 Oracle DBs by Anomalies';
          V_OWNER                              :='ORACLE';
          V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
          V_LAST_MODIFIED_BY                   :='ORACLE';
          V_DESCRIPTION                        :='Top 10 Oracle databases with the highest number of Anomalies';
          V_FOLDER_ID                          :=2;
          V_CATEGORY_ID                        :=1;
          V_SYSTEM_SEARCH                      :=1;
          V_IS_LOCKED                          :=0;
          V_UI_HIDDEN                          :=0;
          V_DELETED                            :=0;
          V_IS_WIDGET                          :=1;
          V_METADATA                           :=null;
          V_SEARCH_STR                         :='''SEF Record Type'' = correlation and ''SEF Destination Type'' like ''*Oracle Database*'' and ''SEF Category'' = anomaly | stats count as Anomalies by ''SEF Destination Endpoint Name'' | fields Anomalies, ''SEF Destination Endpoint Name'' as Database | top Anomalies';
          V_WIDGET_SOURCE                      :='1';
          V_WIDGET_GROUP_NAME                  :=null;
          V_WIDGET_ICON                        :=null;
          V_WIDGET_KOC_NAME                    :='emcla-visualization';
          V_WIDGET_VIEWMODEL                   :='/js/viewmodel/search/widget/VisualizationWidget.js';
          V_WIDGET_TEMPLATE                    :='/html/search/widgets/visualizationWidget.html';
          V_WIDGET_SUPPORT_TIME_CONTROL        :=1;
          V_DASHBOARD_INELIGIBLE               :=0;
          V_PROVIDER_NAME                      :='LogAnalyticsUI';
          V_PROVIDER_VERSION                   :='1.0';
          V_PROVIDER_ASSET_ROOT                :='assetRoot';
          V_WIDGET_LINKED_DASHBOARD            :=null;
          V_WIDGET_DEFAULT_WIDTH               :=6;
          V_WIDGET_DEFAULT_HEIGHT              :=2;
          INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                            SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                            WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                            WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                            PROVIDER_ASSET_ROOT)
      	    VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
      	            V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
      	            V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
      	            V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);

           V_NAME                  :='VISUALIZATION_TYPE_KEY';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='HORIZONTAL_BAR';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='time';
           V_PARAM_TYPE            :=1;
           V_PARAM_VALUE_STR       :='{"type":"relative","duration":90,"timeUnit":"DAY"}';
           V_PARAM_VALUE_CLOB      :=null;
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='APPLICATION_CONTEXT';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":90}}';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

           V_NAME                  :='TARGET_FILTER';
           V_PARAM_TYPE            :=2;
           V_PARAM_VALUE_STR       :=null;
           V_PARAM_VALUE_CLOB      :='[]';
           INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
           VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
            DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 Oracle DBs by Anomalies has been added for TENANT: '||V_TENANT_ID);
      END IF;
-----
    IF (V_TID<>-1) THEN
        EXIT;
    END IF;
  END LOOP;
  CLOSE TENANT_CURSOR;
  COMMIT;
EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('FAILED TO  ADD  Security Analytics OOB WIDGET DUE TO '||SQLERRM);
  RAISE;  
END;
/
