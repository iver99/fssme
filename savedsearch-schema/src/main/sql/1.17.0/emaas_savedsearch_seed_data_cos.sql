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
Rem    XIADAI       02/09/17 -  update data for Orchestration OOB
Rem
SET FEEDBACK ON
SET SERVEROUTPUT ON
DEFINE TENANT_ID ='&1'
DECLARE
    V_COUNT             NUMBER;
    V_TENANT_ID         NUMBER(38,0);
    V_TID               NUMBER(38,0)        := '&TENANT_ID';
    V_SEARCH_ID_5020    NUMBER              := 5020;
    V_WIDGET_OLD_NAME   VARCHAR2(64 BYTE)   := 'Workflow Submission Details';
    V_WIDGET_NEW_NAME   VARCHAR2(64 BYTE)   := 'Execution Details';
    CURSOR TENANT_CURSOR IS
        SELECT DISTINCT TENANT_ID FROM EMS_ANALYTICS_SEARCH ORDER BY TENANT_ID;

BEGIN
  OPEN TENANT_CURSOR;
  LOOP
     IF (V_TID<>-1) THEN
        V_TENANT_ID:=V_TID;
     ELSE
       FETCH TENANT_CURSOR INTO V_TENANT_ID;
       EXIT WHEN TENANT_CURSOR%NOTFOUND;
     END IF;
        SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH
            WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5020 AND NAME = V_WIDGET_OLD_NAME;
        IF V_COUNT < 1 THEN
            DBMS_OUTPUT.PUT_LINE('WIDGET Workflow Submission Details NOT EXIST NO NEED TO UPDATE for tenant:'||V_TENANT_ID);
        ELSE
            UPDATE EMS_ANALYTICS_SEARCH SET NAME = V_WIDGET_NEW_NAME
                WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5020;
            DBMS_OUTPUT.PUT_LINE('WIDGET Workflow Submission Details UPDATED SUCCESSFULLY for tenant:'||V_TENANT_ID);
        END IF;
     IF (V_TID<>-1) THEN
        EXIT;
     END IF;
  END LOOP;
  CLOSE TENANT_CURSOR;
  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Upgrade is done');
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('Failed to update the sql due to '||SQLERRM);
      RAISE;
  END;
  /
