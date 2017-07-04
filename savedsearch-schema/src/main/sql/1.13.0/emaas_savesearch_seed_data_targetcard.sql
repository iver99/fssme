Rem
Rem emaas_savesearch_seed_data_targetcard.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data_targetcard.sql 
Rem
Rem    DESCRIPTION
Rem      Seed data for category and default folder
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    XIADAI     11/17/16 - Created
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON


DECLARE
    V_COUNT NUMBER;
    V_SEARCH_ID                         NUMBER(38,0)         ;
    V_NAME                              VARCHAR2(64 BYTE)    ;
    V_TENANT_ID                         NUMBER(38,0)         ;
    V_PARAM_TYPE                        EMS_ANALYTICS_SEARCH_PARAMS.PARAM_TYPE%TYPE;
    V_PARAM_VALUE_STR                   EMS_ANALYTICS_SEARCH_PARAMS.PARAM_VALUE_STR%TYPE;
    V_PARAM_VALUE_CLOB                  CLOB                           ;
    V_TID                               NUMBER(38,0) := '&TENANT_ID'   ;
    CURSOR TENANT_CURSOR IS
       SELECT DISTINCT TENANT_ID FROM EMS_ANALYTICS_FOLDERS ORDER BY TENANT_ID;

BEGIN

  LOOP
     IF (V_TID<>-1) THEN
        V_TENANT_ID:=V_TID;
     ELSE
       IF NOT TENANT_CURSOR%ISOPEN THEN
        OPEN TENANT_CURSOR;
       END IF;
       FETCH TENANT_CURSOR INTO V_TENANT_ID;
       EXIT WHEN TENANT_CURSOR%NOTFOUND;
     END IF;

    V_SEARCH_ID       := 4000;
    V_NAME            := 'CARD_DEF_omc_web_application_server';
    SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE
        SEARCH_ID = V_SEARCH_ID AND NAME = V_NAME AND TENANT_ID  = V_TENANT_ID;
    IF ( V_COUNT = 0) THEN
        UPDATE EMS_ANALYTICS_SEARCH SET NAME = V_NAME WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID = V_TENANT_ID;

        V_NAME            := 'entityType';
        V_PARAM_VALUE_STR := 'omc_web_application_server';
        UPDATE EMS_ANALYTICS_SEARCH_PARAMS SET PARAM_VALUE_STR = V_PARAM_VALUE_STR
            WHERE SEARCH_ID = V_SEARCH_ID AND NAME = V_NAME AND TENANT_ID  = V_TENANT_ID;

        V_NAME := 'cardef';
        V_PARAM_VALUE_CLOB := '{"id":null,"entityType":"omc_web_application_server","widgets":null,"widgetRefs":[{"id":3030,"parameters":null,"entityCapability":null,"accessControl":{"userPrivilege":"USE_TARGET_ANALYTICS"}}]}';
        UPDATE EMS_ANALYTICS_SEARCH_PARAMS SET PARAM_VALUE_CLOB = V_PARAM_VALUE_CLOB
                    WHERE SEARCH_ID = V_SEARCH_ID AND NAME = V_NAME AND TENANT_ID  = V_TENANT_ID;
        DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||V_SEARCH_ID||') has been updated');
    ELSE
        DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||V_SEARCH_ID||') has been updated before, no need to update again');
    END IF;  
  
    V_SEARCH_ID       := 4001;
    V_NAME            := 'CARD_DEF_omc_relational_database';
    SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE
        SEARCH_ID = V_SEARCH_ID AND NAME = V_NAME AND TENANT_ID  = V_TENANT_ID;
    IF (V_COUNT = 0) THEN
        UPDATE EMS_ANALYTICS_SEARCH SET NAME = V_NAME WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID = V_TENANT_ID;

        V_NAME            := 'entityType';
        V_PARAM_VALUE_STR := 'omc_relational_database';
        UPDATE EMS_ANALYTICS_SEARCH_PARAMS SET PARAM_VALUE_STR = V_PARAM_VALUE_STR
            WHERE SEARCH_ID = V_SEARCH_ID AND NAME = V_NAME AND TENANT_ID  = V_TENANT_ID;

        V_NAME := 'cardef';
        V_PARAM_VALUE_CLOB := '{"id":null,"entityType":"omc_relational_database","widgets":null,"widgetRefs":[{"id":3029,"parameters":null,"entityCapability":null,"accessControl":{"userPrivilege":"USE_TARGET_ANALYTICS"}}]}';
        UPDATE EMS_ANALYTICS_SEARCH_PARAMS SET PARAM_VALUE_CLOB = V_PARAM_VALUE_CLOB
                    WHERE SEARCH_ID = V_SEARCH_ID AND NAME = V_NAME AND TENANT_ID  = V_TENANT_ID;
        DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||V_SEARCH_ID||') has been updated');
    ELSE
        DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||V_SEARCH_ID||') has been updated before, no need to update again');
    END IF;
    
    IF (V_TID<>-1) THEN
        EXIT;
    END IF;
  END LOOP;
  IF TENANT_CURSOR%ISOPEN THEN
    CLOSE TENANT_CURSOR;
  END IF;

  COMMIT;
EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('FAILED TO  UPDATE  TARGET CARD DUE TO '||SQLERRM);
  RAISE;
END;
/