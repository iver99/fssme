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
    V_PARAM_ATTRIBUTES                  EMS_ANALYTICS_SEARCH_PARAMS.PARAM_ATTRIBUTES%TYPE;
    V_PARAM_TYPE                        EMS_ANALYTICS_SEARCH_PARAMS.PARAM_TYPE%TYPE;
    V_PARAM_VALUE_STR                   EMS_ANALYTICS_SEARCH_PARAMS.PARAM_VALUE_STR%TYPE;
    V_PARAM_VALUE_CLOB                  CLOB                           ;
    V_CLOB                              CLOB                           ;
    V_SEARCH_STR                        CLOB                           ;
    V_METADATA                          CLOB                           ;
    V_LAST_ACCESS_DATE                  TIMESTAMP(6)                   ;
    V_TID                               NUMBER(38,0) := '&TENANT_ID'   ;
    V_OBJECT_TYPE                       NUMBER(1,0)                    ;
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

    V_SEARCH_ID := 4010;
    SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID  = V_TENANT_ID;
    IF ( V_COUNT = 0) THEN
        V_NAME                     :='CARD_DEF_omc_hosted_target';
        V_OWNER                    :='ORACLE';
        V_CREATION_DATE            :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
        V_LAST_MODIFICATION_DATE   :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
        V_LAST_MODIFIED_BY         :='ORACLE';
        V_DESCRIPTION              :=null;
        V_FOLDER_ID                := 6;
        V_CATEGORY_ID              :=5;
        V_SYSTEM_SEARCH            :=1;
        V_IS_LOCKED                :=0;
        V_UI_HIDDEN                :=0;
        V_IS_WIDGET                :=0;
        INSERT INTO EMS_ANALYTICS_SEARCH
            (SEARCH_ID, NAME, OWNER, CREATION_DATE, LAST_MODIFICATION_DATE,
                LAST_MODIFIED_BY, DESCRIPTION, FOLDER_ID, CATEGORY_ID, SYSTEM_SEARCH,
                IS_LOCKED, UI_HIDDEN, TENANT_ID, IS_WIDGET)
        VALUES
            (V_SEARCH_ID, V_NAME, V_OWNER, V_CREATION_DATE, V_LAST_MODIFICATION_DATE,
                V_LAST_MODIFIED_BY, V_DESCRIPTION, V_FOLDER_ID, V_CATEGORY_ID, V_SYSTEM_SEARCH,
                V_IS_LOCKED, V_UI_HIDDEN, V_TENANT_ID, V_IS_WIDGET);

        V_NAME                  :='cardef';
        V_PARAM_ATTRIBUTES      :=null;
        V_PARAM_TYPE            :=2;
        V_PARAM_VALUE_STR       :=null;
        V_PARAM_VALUE_CLOB      :='{"id":null,"entityType":"omc_hosted_target","widgets":null,"widgetRefs":[{"id":3031,"parameters":null,"entityCapability":null,"accessControl":{"userPrivilege":"USE_TARGET_ANALYTICS"}}]}';
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS
            (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES
            (V_SEARCH_ID, V_NAME, V_PARAM_ATTRIBUTES, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

        V_NAME                  :='entityType';
        V_PARAM_ATTRIBUTES      :=null;
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='omc_hosted_target';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS
            (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES
            (V_SEARCH_ID, V_NAME, V_PARAM_ATTRIBUTES, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

        V_NAME                  :='isCardDef';
        V_PARAM_ATTRIBUTES      :=null;
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='true';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS
            (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES
            (V_SEARCH_ID, V_NAME, V_PARAM_ATTRIBUTES, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);

        V_NAME                  :='version';
        V_PARAM_ATTRIBUTES      :=null;
        V_PARAM_TYPE            :=1;
        V_PARAM_VALUE_STR       :='1001';
        V_PARAM_VALUE_CLOB      :=null;
        INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS
            (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID)
        VALUES
            (V_SEARCH_ID, V_NAME, V_PARAM_ATTRIBUTES, V_PARAM_TYPE, V_PARAM_VALUE_STR, V_PARAM_VALUE_CLOB, V_TENANT_ID);


        V_LAST_ACCESS_DATE      :=SYS_EXTRACT_UTC(SYSTIMESTAMP);
        V_OBJECT_TYPE           :=2;
        INSERT INTO EMS_ANALYTICS_LAST_ACCESS
            (OBJECT_ID, ACCESSED_BY, OBJECT_TYPE, ACCESS_DATE, TENANT_ID)
        VALUES
            (V_SEARCH_ID, V_OWNER, V_OBJECT_TYPE, V_LAST_ACCESS_DATE, V_TENANT_ID);

        DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||V_SEARCH_ID||') is added');
    ELSE
        DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||V_SEARCH_ID||') exists, no need to add again');
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
  CLOSE TENANT_CURSOR;
  COMMIT;
EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('FAILED TO  UPDATE  TARGET CARD DUE TO '||SQLERRM);
  RAISE;
END;
/