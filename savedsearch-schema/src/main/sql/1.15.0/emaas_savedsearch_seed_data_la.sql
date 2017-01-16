Rem
Rem emaas_savedsearch_seed_data_la.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, 2016, 2017 Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savedsearch_seed_data_la.sql
Rem
Rem    DESCRIPTION
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    XIADAI       1/16/17 - update widgets 2000
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE
  V_SEARCH_ID                           NUMBER                          ;
  V_NAME                                VARCHAR2(64 BYTE)               ;
  V_PARAM_TYPE                          NUMBER(38,0)                    ;
  V_PARAM_VALUE_STR                     VARCHAR2(1024 BYTE)             ;
  V_PARAM_VALUE_CLOB                    NCLOB                           ;

  V_TENANT_ID                           NUMBER                          ;
  V_COUNT                               NUMBER                          ;
  V_TID                                 NUMBER(38,0) := '&TENANT_ID'    ;
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

  V_SEARCH_ID := 2000;
  SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID = V_TENANT_ID;
  IF (V_COUNT = 0) THEN
    DBMS_OUTPUT.PUT_LINE('There is no Top Log Sources widget, no need to do upgrading under tenant_id ='||V_TENANT_ID);
  ELSE
  --Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2000
  --,'VISUALIZATION_TYPE_KEY',null,1,'BAR',null,'&TENANT_ID');
    V_NAME                               :='VISUALIZATION_TYPE_KEY';
    V_PARAM_TYPE                         :=1;
    V_PARAM_VALUE_STR                    :='BAR';
    V_PARAM_VALUE_CLOB                   :=NULL;
    SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE SEARCH_ID = V_SEARCH_ID AND NAME = V_NAME AND TENANT_ID = V_TENANT_ID;
    IF(V_COUNT > 0 ) THEN
      DBMS_OUTPUT.PUT_LINE('Top Log Sources widget VISUALIZATION_TYPE_KEY param exists, no need to do upgrading under tenant_id ='||V_TENANT_ID);
    ELSE
      Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID, NAME, PARAM_TYPE, PARAM_VALUE_STR, PARAM_VALUE_CLOB, TENANT_ID )
      values (V_SEARCH_ID, V_NAME, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);
      DBMS_OUTPUT.PUT_LINE('Widget Top Log Sources widget have been upgraded successfully! tenant_id='||V_TENANT_ID);
    END IF;
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
    DBMS_OUTPUT.PUT_LINE('Failed to update widget 2000 due to error '||SQLERRM);
    RAISE;
END;
/
