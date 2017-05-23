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
Rem    XIADAI       05/03/17 -  Add widgets for Dashboard MySQL Database Security
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
   ---3401 Top 10 MySQL DBs by Threats
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3401 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
            DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 MySQL DBs by Threats already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3401;
       V_NAME                               :='Top 10 MySQL DBs by Threats';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='Top 10 MySQL Databases by Threats';
       V_FOLDER_ID                          :=7;
       V_CATEGORY_ID                        :=6;
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Record Type'' = correlation and ''SEF Destination Type'' in (''MySQL Database*'', ''omc_mysql_db*'') and ''SEF Category'' != anomaly | stats count as Threats by ''SEF Destination Endpoint Name'', ''SEF Category'' | fields Threats, ''SEF Destination Endpoint Name'' as Database, ''SEF Category'' as ''Threat Category'' | top Threats';
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
       V_WIDGET_DEFAULT_WIDTH               :=5;
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
        V_PARAM_VALUE_CLOB      :='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHUAAABqCAYAAACChB7yAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAMQSURBVHhe7d3NThNRAMXxM53S2gIibUKQRdENGxeoe6NPYNzIq+lKl5q4MK504zsQVhgWBEljpd+V0k5bWueOV1Z6mZKSgcP5sZjpLQlh/rnTJtO59SYhCJWU3f7TycmJ3ZO4xuOx3UuOM2oQBHZP4roKJz5nVLmeFJWQohJSVEKKSkhRCSkqIUUlpKiEFJWQohJSVEKKSkhRCSkqIUUlpKiEFJWQohJSVEKKSkhRCSkqIUUlpKiEFJWQohJSVELOqFnfs3sSl38Fjpnz/tSvuz+Qu30HmCR/e951MA6PZN4HHt+dtyPJcEZ9+Gob1UkOaU/3JccxOJ1gc+UWvmzdtyPJcEZ98mYHLS8fRrUD4hSEUR8UM/jwYt2OJENvlAgpKiFFJaSohBSVkKISUlRCikpIUQkpKiFFJaSohBSVkKIS0qW3GbqMS2/d42P8PDpCLpezI8BoNEImk8FwOIyWojVrDC8sLKBYLEbPn83UZrOJarWKcrmMg4ODaMwLfyQ573fb8FI+Ou02arUa2uHWxOz3++h0Ouh2u+j1etHWjP91NlMPDw/RarWwvLwcrci9sXEPT99+QwPmkw/R78o5Zj1Ttz59x7vnJUz7sSedfmdIn3yQS6'||
        'OohBSVkKISUlRCikpIUQkpKiFFJaSohBSVkKISUlRCikpIUQk5r6c+er2NWrQ8gB0QJ3M9dXMli88vr/DyAB93ypibX4IHLeQRh1nIY3HOw7P1RTuSDGfU3q82cotL9pHEYyZAsq9qzr/eH2mGTus0PAUnTW+UCCkqIUUlpKiEFJWQohJSVEKKSkhRCSkqIUUlpKiEFJWQohJSVEKKSkhRCSkqIUUlpKiEFJWQohJSVEKKSkhRCSkqIUUl5IyaSqn5tHzft3vJcd71tre3h7W1tWg5bzmfmQSNRgOFQuFCx8ykyOfzSKfTduRi/ht1MBigUqmgVCrZEYmjXq+frWWfFOdMvW7MdwCYfyebzUaPzbLxq6ur8LybdSs81YumiWnOMCbu/v5+9EUCQRDYZ28Oqpkqf+jtLR3gNylI+cc5YGhyAAAAAElFTkSuQmCC';
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
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":1,"timeUnit":"YEAR"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"YEAR","timeduration":1}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 MySQL DBs by Threats has been added for TENANT: '||V_TENANT_ID);
   END IF;
   ---3402 Top 10 MySQL DBs by Threats
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3402 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Threat Trend on MySQL DBs already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3402;
       V_NAME                               :='Threat Trend on MySQL DBs';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='Threat Trend on MySQL Databases';
       V_FOLDER_ID                          :=7;
       V_CATEGORY_ID                        :=6;
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Record Type'' = correlation and ''SEF Destination Type'' in (''MySQL Database*'', ''omc_mysql_db*'') and ''SEF Category'' != anomaly | timestats count as Threats by ''SEF Category''';
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
       V_WIDGET_DEFAULT_WIDTH               :=7;
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
        V_PARAM_VALUE_CLOB      := null;
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
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":60,"timeUnit":"MINUTE"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"MINUTE","timeduration":60}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='LA_CRITERIA';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[{"jsonConstructor":"laCriterionField","data":{"id":"sefCategory","_value":null,"dataType":"string","displayName":"SEF Category","displayDetails":{"targetType":"charts","emSite":""},"role":"Group by"}}]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='SHOW_MESSAGE_FIELD';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='false';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='SHOW_N_ROWS';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='25';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='SHOW_ORIGINAL_LOG_CONTENT';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='false';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Threat Trend on MySQL DBs has been added for TENANT: '||V_TENANT_ID);
   END IF;
   ---3403 Top 10 MySQL DBs by Activity
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3403 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 MySQL DBs by Activity already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3403;
       V_NAME                               :='Top 10 MySQL DBs by Activity';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='Top 10 MySQL Databases by Activity';
       V_FOLDER_ID                          :=7;
       V_CATEGORY_ID                        :=6;
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Record Type'' = base and ''SEF Collector Endpoint Type'' like ''omc_mysql_db*'' | stats count as Activity by ''SEF Destination Endpoint Name'', ''SEF Category'' | rename ''SEF Category'' as ''Activity Category'' | rename ''SEF Destination Endpoint Name'' as Database | top Activity';
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
       V_WIDGET_DEFAULT_WIDTH               :=5;
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
        V_PARAM_VALUE_CLOB      := null;
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
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":14,"timeUnit":"DAY"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"DAY","timeduration":14}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 MySQL DBs by Activity has been added for TENANT: '||V_TENANT_ID);
   END IF;
   ---3404 Activity Trend on MySQL DBs
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3404 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Activity Trend on MySQL DBs already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3404;
       V_NAME                               :='Activity Trend on MySQL DBs';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='Activity Trend on MySQL Databases.';
       V_FOLDER_ID                          :=7;
       V_CATEGORY_ID                        :=6;
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Collector Endpoint Type'' like ''omc_mysql_db*'' and ''SEF Record Type'' = base and ''SEF Category'' not in (system.shutdown.background, system.startup) | timestats count as Activity by ''SEF Category''';
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
       V_WIDGET_DEFAULT_WIDTH               :=7;
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
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":1,"timeUnit":"YEAR"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"YEAR","timeduration":1}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='LA_CRITERIA';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[{"jsonConstructor":"laCriterionField","data":{"id":"mtgt","_value":null,"dataType":"string","displayName":"Entity","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"mtgttype","_value":null,"dataType":"string","displayName":"Entity Type","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"msrcid","_value":null,"dataType":"string","displayName":"Log Source","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"srvrhostname","_value":null,"dataType":"string","displayName":"Host Name (Server)","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"sevlvl","_value":null,"dataType":"string","displayName":"Severity","displayDetails":{"targetType":"charts","emSite":""},"role":"Display Fields"}},{"jsonConstructor":"laCriterionField","data":{"id":"sefCategory","_value":null,"dataType":"string","displayName":"SEF Category","displayDetails":{"targetType":"charts","emSite":""},"role":"Group by"}}]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='SHOW_MESSAGE_FIELD';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='false';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='SHOW_N_ROWS';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='25';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='SHOW_ORIGINAL_LOG_CONTENT';
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='false';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Activity Trend on MySQL DBs has been added for TENANT: '||V_TENANT_ID);
   END IF;
   ---3405 Top 10 MySQL DBs with Account Modifications on high privileges
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3405 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 MySQL DBs with Account Modifications on high privileges already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3405;
       V_NAME                               :='Top 10 MySQL DBs with Account Modifications on high privileges';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='Top 10 MySQL Databases with Account Modifications on high privileges.';
       V_FOLDER_ID                          :=7;
       V_CATEGORY_ID                        :=6;
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Collector Endpoint Type'' like ''omc_mysql_db*'' and ''SEF Category'' = authorization.acquire and ''SEF Command'' in (create_user, grant, drop_user, update_user) | stats count as ''Account Modifications on High Privileges'' by ''SEF Destination Endpoint Name'' | rename ''SEF Destination Endpoint Name'' as Database | top ''Account Modifications on High Privileges''';
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
        V_NAME                  :='WIDGET_VISUAL';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAF4AAABTCAYAAAAWTLCwAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAANuSURBVHhe7Z3PThNRFIfPMC1QCoUmBCkmsCEEdWVc8gA+jAtduvMFfABMfA6IW9lj3NIQF4RoSQDb0pb+meKcyy0hCuO9pekPyu9LyMw9mTN35rt3bprhQIOLGCFDZ8xub6RWq9k9P7rdrt3zY/h5/nNO52m3j7mqedfneKL4Vqtl9/zo9yF6CHmaM4i8RPHkXwK7vSsUD4LiQVA8CIoHQfEgKB4ExYOgeBAUD4LiQVA8CIoHQfEgKB4ExYOgeBAUD4LiQVA8CIoHkSg+m+5vXMJwdPOCsThnAL/xTixo+vj1hwSZXNyPXzmDnjII/K/uIeRVW5F82HhiW+70an/GdOBiEsW/3Pwmv4OspALfOhK9Gd8cZdh5fmgPpXpHqu9eXAY88BK/8fm7VEIVbwOPHDVVqrfl55tnNuLO3+L9FzkyECgeBMWDoHgQFA+C4kFQPAiKB0HxICgeBMWDuBK/u7sr29vbsrOzI1tbWyLt2tV7BTJ4+JLMA74kGwEoHgTFg6B4EBQPguJBUDwIigdB8SAoHgTFg0h8V/PqU6+SzAaI/Kp3pPz2uW2541VJ9v5LUaKJXPxY3HrIjegpR7V2styMZPP1U9tyx0t8p1aWVHbWtkiP6K'||
        'IrYeC3Snu9nay2Lg/2JYoiu+fH8PP87+9CBfotADfiN2xkYFA8CIoHQfEgKB4ExYOgeBAUD4LiQVA8CIoHQfEgKB4ExYOgeBAUD4LiQVA8CIoHQfEgEsX3+8dno5yn/5Ms7CNP+7reX2J5x/7+viwuLl6VJvwPrVFpt9vmZ2pqytStuKIXdXZ2Jtls1jmv159+997E+LhXnn6dnt7X5OSkc55eY6VSMUUGs7mcsxfNK5fLkkqlZGFhwcRuFX9+fi5HR0eyvLxsI27oFzNWq1UzYD6odL2ppaUlG3Hj9PTUlHfMz8/biBsnJyex/GZ8nQUbcUP7K5VKsr6+biNuaN7x8bGsrq6aduKMR6AXNz09Lc1mU9LptJkAYRiaGaNy9QZ0ps3NzZlB7j1ZutWB0xlcr9fNMfr0aEzPpwObi2dpp9Mx59MYknsn/uDgwCwDuhyovEajISsrK1IsFiWTyZjB0JhudZmZmZkxbX1SDg8PpVAoyN7enpGuA6W3p/L1GBWu23w+L2tra7ZHDPdO/GOhv48D5I6I/AHMIZ4nxQUMRAAAAABJRU5ErkJggg==';
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
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":1,"timeUnit":"YEAR"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"YEAR","timeduration":1}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 MySQL DBs with Account Modifications on high privileges has been added for TENANT: '||V_TENANT_ID);
   END IF;
   ---3406 Top 10 MySQL DBs with Sensitive Object Accesses
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3406 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 MySQL DBs with Sensitive Object Accesses already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3406;
       V_NAME                               :='Top 10 MySQL DBs with Sensitive Object Accesses';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='Top 10 MySQL Databases with Sensitive Object Accesses.';
       V_FOLDER_ID                          :=7;
       V_CATEGORY_ID                        :=6;
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Record Type'' = base and ''SEF Collector Endpoint Type'' like ''omc_mysql_db*'' and ''SEF Category'' like ''access*'' and ''SEF Destination Resource Type'' like ''application.database*'' and ''SEF Command'' in (show_databases, show_tables) | stats count as ''Sensitive Object Access'' by ''SEF Destination Endpoint Name'' | rename ''Sensitive Object Access'' as ''Sensitive Object Accesses'' | fields ''Sensitive Object Access'', ''SEF Destination Endpoint Name'' as Database | top ''Sensitive Object Access''';
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
        V_NAME                  :='WIDGET_VISUAL';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAF4AAABTCAYAAAAWTLCwAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAANuSURBVHhe7Z3PThNRFIfPMC1QCoUmBCkmsCEEdWVc8gA+jAtduvMFfABMfA6IW9lj3NIQF4RoSQDb0pb+meKcyy0hCuO9pekPyu9LyMw9mTN35rt3bprhQIOLGCFDZ8xub6RWq9k9P7rdrt3zY/h5/nNO52m3j7mqedfneKL4Vqtl9/zo9yF6CHmaM4i8RPHkXwK7vSsUD4LiQVA8CIoHQfEgKB4ExYOgeBAUD4LiQVA8CIoHQfEgKB4ExYOgeBAUD4LiQVA8CIoHkSg+m+5vXMJwdPOCsThnAL/xTixo+vj1hwSZXNyPXzmDnjII/K/uIeRVW5F82HhiW+70an/GdOBiEsW/3Pwmv4OspALfOhK9Gd8cZdh5fmgPpXpHqu9eXAY88BK/8fm7VEIVbwOPHDVVqrfl55tnNuLO3+L9FzkyECgeBMWDoHgQFA+C4kFQPAiKB0HxICgeBMWDuBK/u7sr29vbsrOzI1tbWyLt2tV7BTJ4+JLMA74kGwEoHgTFg6B4EBQPguJBUDwIigdB8SAoHgTFg0h8V/PqU6+SzAaI/Kp3pPz2uW2541VJ9v5LUaKJXPxY3HrIjegpR7V2styMZPP1U9tyx0t8p1aWVHbWtkiP6K'||
        'IrYeC3Snu9nay2Lg/2JYoiu+fH8PP87+9CBfotADfiN2xkYFA8CIoHQfEgKB4ExYOgeBAUD4LiQVA8CIoHQfEgKB4ExYOgeBAUD4LiQVA8CIoHQfEgEsX3+8dno5yn/5Ms7CNP+7reX2J5x/7+viwuLl6VJvwPrVFpt9vmZ2pqytStuKIXdXZ2Jtls1jmv159+997E+LhXnn6dnt7X5OSkc55eY6VSMUUGs7mcsxfNK5fLkkqlZGFhwcRuFX9+fi5HR0eyvLxsI27oFzNWq1UzYD6odL2ppaUlG3Hj9PTUlHfMz8/biBsnJyex/GZ8nQUbcUP7K5VKsr6+biNuaN7x8bGsrq6aduKMR6AXNz09Lc1mU9LptJkAYRiaGaNy9QZ0ps3NzZlB7j1ZutWB0xlcr9fNMfr0aEzPpwObi2dpp9Mx59MYknsn/uDgwCwDuhyovEajISsrK1IsFiWTyZjB0JhudZmZmZkxbX1SDg8PpVAoyN7enpGuA6W3p/L1GBWu23w+L2tra7ZHDPdO/GOhv48D5I6I/AHMIZ4nxQUMRAAAAABJRU5ErkJggg==';
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
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":1,"timeUnit":"YEAR"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"YEAR","timeduration":1}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 MySQL DBs with Sensitive Object Accesses has been added for TENANT: '||V_TENANT_ID);
   END IF;
   ---3407 Top 10 MySQL DBs with Startups/Shutdowns
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3407 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 MySQL DBs with Startups/Shutdowns already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3407;
       V_NAME                               :='Top 10 MySQL DBs with Startups/Shutdowns';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='Top 10 MySQL Databases with Startups/Shutdowns.';
       V_FOLDER_ID                          :=7;
       V_CATEGORY_ID                        :=6;
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Record Type'' = base and ''SEF Collector Endpoint Type'' like ''omc_mysql_db*'' and ''SEF Category'' = system.admin.audit and Action in (audit, noaudit) | stats count as ''Startups / Shutdowns'' by ''SEF Destination Endpoint Name'', Action';
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
        V_NAME                  :='WIDGET_VISUAL';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEIAAABeCAYAAACXQMIIAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAMiSURBVHhe7dyxSxtRAMfxX5IjGCtGDRSSDA62/4CLHUo669Chc/+A/h3dS8GxXVw7FDoZ6FQcOga6VVARQVLUJKYpMUlzd817/SEV2iOG96LF3weO3Dv10G9eDslLkopHIEjz9q+63S733IuiiHvuTXLuxBD9fp977vmciJOcOzHEXaIQpBCkEKQQpBCkEKQQpBCkEKQQpBCkEKQQpBCkEKQQpBCkEKQQpBCkEKQQlBgineKOB6mUx5Pj+udODBF4LJFOe5yME5w7ce3z4WYN7fQsMim3q1LtMIc35Q94ntoC4lkedSTsIypVkH78mgfGkxhi7e0XdIN7yMBxiCiHV8UqnuHdKESORx2JRiHuP0J67SUPjGesOWQezy63K8zY5fb7pLwdn8cH6v9FIUghSCFIIUghSCFIIUghSCFIIUgh6DJErVZDtVrFzs4Otre3gWHX73MGt8zlX7q6uor19XVUKhVsbGwAwazXlwnfNnpokEKQQpBCkEKQQpBCkEKQQpBCkEKQQpBCUOIi8IPNGr77XA3H1mjkejW8h6j0xO1qeKPZQmFp0fmbVWOkEEchMvb1F9dfsE0WIwwjZIKA4/EkhmifnyO/sMCRW+a5Dl9P/IRRzM'||
        'jjS/xNIscz4U+uZ9kVseP3ht8lCkEKQQpBCkEKQQpBCkEKQQpBCkEKQQpBCkEKQQpBCkEKQQpBCkEKQQpBiSF8vuA0k8lwz71Jzv3PBR6zAHNwcIBisej8hacmcKPRQKFQ8HPuZhNLi8krdOZr8/PzHCWE6PV6aI5OWCqVeMStdruNfD7PkVv7+/tYWVnhaDyJS34+nJ6eIgxDzMzM2A8XNrdBENjpXK/X7Sy5uLiw35vNZjEcDpHL5TA3N2eP+TL1i6V5J7CZaWY7OzvD0dGRnR3m/jCz0Oyb4+ajqI+Pj+241Wrxp/2Z+oy4rW40hLlgmnveXODK5bI91ul0MBgM7L65Ndvy8rId+3Qj/0d8PPyBn2GM3d2v9iESRea+GG3fPo9+o6y9Vuzt7eHk5MReT6bCzIhpe/r+MO70Q45o0InjTy84mD5dIyzgF/EJJcacT0csAAAAAElFTkSuQmCC';
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
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":60,"timeUnit":"MINUTE"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"MINUTE","timeduration":60}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 MySQL DBs with Startups/Shutdowns has been added for TENANT: '||V_TENANT_ID);
   END IF;
   ---3408 Top 10 MySQL DBs with Account Modifications
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3408 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 MySQL DBs with Account Modifications already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3408;
       V_NAME                               :='Top 10 MySQL DBs with Account Modifications';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='Top 10 MySQL Databases with Account Modifications.';
       V_FOLDER_ID                          :=7;
       V_CATEGORY_ID                        :=6;
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Record Type'' = base and ''SEF Collector Endpoint Type'' like ''omc_mysql_db*'' and ''SEF Destination Resource Type'' like ''application.database*'' and ''SEF Category'' like ''authorization*'' | stats count as ''Account Modifications'' by ''SEF Destination Endpoint Name'', ''SEF Category'' | top ''Account Modifications''';
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
       V_WIDGET_DEFAULT_WIDTH               :=5;
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
        V_PARAM_VALUE_CLOB      :='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAF4AAABhCAYAAABf/hYWAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAPqSURBVHhe7Z1NTxpRFIYPH1oFAV34vbCamJSmjeum2/6bbttf0L/Q7vp72p1J04Xd4EKbAKLIhxgCw9A544UYC+PcUXnBvE9CZubC4Q7PnHuHOIcx1vcQMnHiZjmSVqtl1uxwXdes2TH5OPuc0zx1I+Sqxt3O8UDxnU7HrNkRdRDNQpzGPEZcoHjyPzGzfCgUD4LiQVA8CIoHQfEgKB4ExYOgeBAUD4LiQVA8CIoHQfEgKB4ExYOgeBAUD4LiQVA8CIoHQfEgKB4ExYMIFB+PRasiSUQsPpl8nH1gLB6xszsEiu+ZyqdBFVSYh9IzBVOjnh/3UCYfd7Mc9fzIh/dax+ss8QjyA4tWX309lKqkvMwY+5Ix6I7ZxijTH1e+dsT9/NZshWdQ3xmP3+R6oPj3339JI5GW5MMP8LNATZWvu1L8mDct4bkrnidXEBQPguJBUDwIigdB8SAoHgTFg6B4EBQPguJBUDwIigcxFH9xcSHFYlFOTk7k+PjYb4tFvBBC7mcovt1uS61Wk7m5OdPimCV5Cobit7e3JZ/Py+bmpuzt7XktSf+qC3kaOMeDoHgQFA+C4kFQPAiKB0HxICgeBMWDoHgQFA+C4kFQPIjAauHX37RMW6'||
        'uF+VfKAaVrR5xPb8xWeKzKtP/8rUh2ZeWmPtkCt+dKPGE/mCYf1/fi7C72dBxXXi7Pe2t2cVbim/WaZHLLZsuCvtdJzF7E5OO8j259la0vPVd/FWLXn1V9vBPhbtOK7lgUZiGuH7Gvu0RIE/IYUDwIigdB8SAoHgTFg6B4EBQPguJBUDwIigdB8SAoHgTFg6B4EBQPguJBUDwIigdB8SAoHgTFg6B4EIHiB8U3tjznuJgXY1vMpGhft/sLrCQrFAqysbExrIK6D733Qbfbla7jSGpx0eqX4bpTV1dXkk6nQ8dpf47Xl+7f/Py8VZz+B37X7cvCwovQcbqPjUZD30CymUxoL4O4RCIha2trfttY8Xpvg3K5LDs7O6YlHK1WS5rNpn/AbFDp9Xrd/2m/DZeXl7781dVV0xIOvWmGytdbCNig/ZVKJf/2AzZo3Pn5uezv7/vbgRk/TVSrVVn0RpGOKL3RhcrWg6xtmu1nZ2eSSqVkaWnJ/5C61MzOeJk5jcyM+KOjI0kmk5LNZuX09NTPVB3qlUpFcrmcP5R1mlJ0xOkI0ANwcHDgt00bMyP+uTFz4nXK0XOBZr7uuma9ztU6/ei0ouvarlORjgCdfqaRmRH/u9KWrdyCFA5/el8TErK+vu6fkHWK0XlepyGd61W4Hhz99qAHY3d317zDdDEz4r/8KMu7rZR8eDmdJ0tbOMdDEPkH83lImcSaBvgAAAAASUVORK5CYII=';
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
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":1,"timeUnit":"YEAR"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"YEAR","timeduration":1}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 MySQL DBs with Account Modifications has been added for TENANT: '||V_TENANT_ID);
   END IF;
   ---3409 Top 10 MySQL DBs with Schema Changes
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 3409 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB Widget Top 10 MySQL DBs with Schema Changes already exists for '||V_TENANT_ID ||', no need to add');
       ELSE
       V_SEARCH_ID                          :=3409;
       V_NAME                               :='Top 10 MySQL DBs with Schema Changes';
       V_OWNER                              :='ORACLE';
       V_CREATION_DATE                      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFICATION_DATE             :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='Top 10 MySQL Databases with Schema Changes.';
       V_FOLDER_ID                          :=7;
       V_CATEGORY_ID                        :=6;
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='''SEF Record Type'' = base and ''SEF Collector Endpoint Type'' like ''omc_mysql_db*'' and ''SEF Destination Resource Type'' like ''application.database*'' and ''SEF Category'' like ''application.admin*'' | stats count as ''Schema Changes'' by ''SEF Destination Endpoint Name'', ''SEF Category'' | fields ''Schema Changes'', ''SEF Destination Endpoint Name'' as Database | top ''Schema Changes''';
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
       V_WIDGET_DEFAULT_WIDTH               :=7;
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
        V_PARAM_VALUE_CLOB      :='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAF4AAABhCAYAAABf/hYWAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAUUSURBVHhe7Z1bTytVFMfXTFsKvVIoBYIJhAin8EaMMfGcD6OJb0d98nsYE33zM/gFND7o40k0xhc1KTeVtqcivXIZOjPOf3cXj0eYzgylC8j6wdCZPV1z+c2aPYSuGQzXg4SJY+rXa+n1enosHI7j6LFwTD4ufM4hT50IuYq4V3PcV7xlWXosHFFPoocQh5hxxPmKF/6PoV9vi4hnQsQzIeKZEPFMiHgmRDwTIp4JEc+EiGdCxDMh4pkQ8UyIeCZEPBMingkRz4SIZ0LEMyHimRDxTIh4JkQ8EyKeCV/xphGtiiQWsfhk0nFkRMg701sZhlviu2ZbVz4Nq6CCDMDWBVPXzb9pAJOMQ4zp2ohS376D98MLGQyoFtSlf9ct1w1YFuhbtFr+/Af6m1JeRgVb2L8gI8LGgMnF1fp5cp+8R9Rtjc78xCnVf/yETiszZMQd37U5PYtKHz+jzNM13TJgWN9pmoN1+Yp/+uVP1I6lKX77M+veUfXE1978kKjX9SzEdOsNxHvU+Pk5nR1MkRn3P8C2J774wTuUfvsN3TLgdfEROjlhHIh4JkQ8EyKeCRHPhIhnQsQzIeKZEPFMiHgmRDwTIp4JEc/Elfjj42OqVqt0eHhIu7'||
        'u7qs2I+EGIMJor8efn59RsNimRSOiWvn4V7oIr8SsrK7S1tUXLy8u0vr7utcTVJyrC3SB9PBMingkRz4SIZ0LEMyHimRDxTIh4JkQ8EyKeCRHPhIhnQsQz4VstvP0FyrRRLfz4/kqJamH7yfu6THtEtXCip8q0z3anvbf6P07XObVo4aNnlHl3VbcMCFWm/csfDcoVCqjA1y3BcGyHzFj4k2mScZdujFZjL72xAB/2GA7ZVp7cPuri8eUJvOlDIsclM5skMxnXDQNCie+0mpTNz+qpEOC2iSi3uUw4DneFhL2NB7KUeDUVnFD18f0IT5sG9gOJ82zokRB4MW6UuNeIkF7COBDxTIh4JkQ8EyKeCRHPhIhnQsQzIeKZEPFMiHgmRDwTIp4JEc+EiGdCxDMh4pkQ8UyIeCZEPBMingkRz4SIZ8JX/LD4JiyPOc7wYqLEIebVON9KskqlQktLS1dVUKPAsw8uLy/VHeFTU1OB7wxHXLfbVe/P5XKh1oc4wzApk04F/rf9ENDpdFRlYi6XDbw+xLVaLYonEpSamQm1f71ej+LxOM3Pzw/avOBro/Fsg3q9Tqur/y2+HAV2CDJwa34YTk5O1EMstre3dUsw8PyFRqNBGxsbuiUYSCokSblc1i3BQJxt27S5ualbgrG/v6+cDtfnm/H3DRxU7DQ2ORaLqUxNp9OqHaANWYkMw2sqlVLt95EHJR7ZXavVaMY7zfGYF3RL6NLwxBGcyhiAZVlUKpVCn62T5EGJf0xMVDy6hIuLCzWgG8A0shfdBbIZ'||
        'oIvIZDL6ommo7qNYLKp5ANcdZDj6y2Qyqd6Xz+dV5uMswPsRj/lYNpa7sLBA09PTKh5nRb/fV3Fow3wsH3HYruE2Yfva7TZls1l1Zo2biYj/vW3RhRsjt/Yb/Vmtqys75GHHsJPYuaOjI/UbFHZ8bm5OXWiHvxnt7OwQvXxBVHqLvv7mW5r1RCMOAjEf4+jv0cVguRA/PMC4aJe3ylTKF8k6OKHj2T4dVPbUdhUKBbUebM/i4iLt7e2pbcF1BLJxUNbW1tSBHTsQf9d89WvT/fRFQ09F5Lvnrnv2l54Ij1XruPXPvtdT/EgfzwLRPyTPLSUp7OlsAAAAAElFTkSuQmCC';
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
        V_PARAM_VALUE_STR       :='{"type":"relative","duration":1,"timeUnit":"YEAR"}';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        V_NAME                  :='APPLICATION_CONTEXT';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"data":{"meid":null,"composite":null,"entities":{},"starttime":null,"endtime":null,"timeunit":"YEAR","timeduration":1}}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);


        V_NAME                  :='TARGET_FILTER';
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='[]';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS(SEARCH_ID,NAME,  PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES(V_SEARCH_ID,V_NAME,V_PARAM_TYPE,V_PARAM_VALUE_STR,V_PARAM_VALUE_CLOB,V_TENANT_ID);

        UPDATE EMS_ANALYTICS_SEARCH SET FOLDER_ID = 7, CATEGORY_ID = 6 WHERE SEARCH_ID > 3299 AND SEARCH_ID < 3329 AND TENANT_ID = V_TENANT_ID;
        DBMS_OUTPUT.PUT_LINE('Security Analytics OOB WIDGET Top 10 MySQL DBs with Schema Changes has been added for TENANT: '||V_TENANT_ID);
   END IF;


   V_SEARCH_ID  := 3318;
   V_SEARCH_STR := '''SEF Observer Endpoint Product'' = cisco_asa and ''SEF Category'' = network.configuration.write | fields Entity, ''Host Name (Server)'' | head limit = 1';
   SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID = V_TENANT_ID;
   IF V_COUNT < 1 THEN
        DBMS_OUTPUT.PUT_LINE('Last Network Configuration Change not exist for '||V_TENANT_ID);
   ELSE
        UPDATE EMS_ANALYTICS_SEARCH SET SEARCH_DISPLAY_STR = V_SEARCH_STR WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID = V_TENANT_ID;
        DBMS_OUTPUT.PUT_LINE('Last Network Configuration Change has been updated for '||V_TENANT_ID);
   END IF;

   V_SEARCH_ID  := 3319;
   V_SEARCH_STR := '''SEF Record Type'' = correlation and ''SEF Destination Type'' in (''Oracle Database*'', ''omc_oracle_database*'') and ''SEF Category'' != anomaly | stats count as Threats by ''SEF Destination Endpoint Name'', ''SEF Category'' | fields Threats, ''SEF Destination Endpoint Name'' as Database, ''SEF Category'' as ''Threat Category'' | top Threats';
   SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID = V_TENANT_ID;
   IF V_COUNT < 1 THEN
        DBMS_OUTPUT.PUT_LINE('Top 10 Oracle DBs by Threats not exist for '||V_TENANT_ID);
   ELSE
        UPDATE EMS_ANALYTICS_SEARCH SET SEARCH_DISPLAY_STR = V_SEARCH_STR WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID = V_TENANT_ID;
        DBMS_OUTPUT.PUT_LINE('Top 10 Oracle DBs by Threats has been updated for '||V_TENANT_ID);
   END IF;

   V_SEARCH_ID  := 3320;
   V_SEARCH_STR := '''SEF Record Type'' = correlation and ''SEF Destination Type'' in (''Oracle Database*'', ''omc_oracle_database*'') and ''SEF Category'' != anomaly | timestats count as Threats by ''SEF Category''';
   SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID = V_TENANT_ID;
   IF V_COUNT < 1 THEN
        DBMS_OUTPUT.PUT_LINE('Threat Trend on Oracle DBs not exist for '||V_TENANT_ID);
   ELSE
        UPDATE EMS_ANALYTICS_SEARCH SET SEARCH_DISPLAY_STR = V_SEARCH_STR WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID = V_TENANT_ID;
        DBMS_OUTPUT.PUT_LINE('Threat Trend on Oracle DBs has been updated for '||V_TENANT_ID);
   END IF;
   --------

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
