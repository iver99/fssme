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
      V_SEARCH_ID  := 3318;
      V_SEARCH_STR := '''SEF Observer Endpoint Product'' = cisco_asa and ''SEF Category'' = network.configuration.write | fields Entity, ''Host Name (Server)'' | head limit = 1';
      SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID = V_TENANT_ID;
      IF V_COUNT < 1 THEN
           DBMS_OUTPUT.PUT_LINE('Last Network Configuration Change not exist for '||V_TENANT_ID);
      ELSE
           UPDATE EMS_ANALYTICS_SEARCH SET SEARCH_DISPLAY_STR = V_SEARCH_STR WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID = V_TENANT_ID;
           DBMS_OUTPUT.PUT_LINE('Last Network Configuration Change has been updated for '||V_TENANT_ID);
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
