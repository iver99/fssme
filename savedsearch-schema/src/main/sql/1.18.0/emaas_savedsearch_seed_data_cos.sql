Rem
Rem emaas_savedsearch_seed_data_sec.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, 2016, 2017 Oracle and/or its affiliates.
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savedsearch_seed_data_cos.sql
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
    V_COUNT               NUMBER;
    V_TENANT_ID           NUMBER(38,0);
    V_TID                 NUMBER(38,0)        := '&TENANT_ID';

    V_SEARCH_ID_5005      NUMBER                := 5005;
    V_SEARCH_ID_5008      NUMBER                := 5008;
    V_SEARCH_ID_5010      NUMBER                := 5010;
    V_SEARCH_ID_5011      NUMBER                := 5011;
    V_SEARCH_ID_5012      NUMBER                := 5012;
    V_WIDGET_OLD_NAME   VARCHAR2(64 BYTE)     := 'Summary';
    V_WIDGET_NEW_NAME   VARCHAR2(64 BYTE)     := 'Overview';
    CURSOR TENANT_CURSOR IS
        SELECT DISTINCT TENANT_ID FROM EMS_ANALYTICS_SEARCH ORDER BY TENANT_ID;

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
        ---EMCPSSF-481
        SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH
            WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5008;
        IF V_COUNT < 1 THEN
            DBMS_OUTPUT.PUT_LINE('WIDGET Histogram of Submissions by Average Failed Steps NOT EXIST NO NEED TO UPDATE for tenant:'||V_TENANT_ID);
        ELSE
            DELETE FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5008;
            DELETE FROM EMS_ANALYTICS_LAST_ACCESS WHERE TENANT_ID = V_TENANT_ID AND OBJECT_ID = V_SEARCH_ID_5008;
            DELETE FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5008;
            DBMS_OUTPUT.PUT_LINE('WIDGET Histogram of Submissions by Average Failed Steps DELETED SUCCESSFULLY for tenant:'||V_TENANT_ID);
        END IF;

        ---EMCPSSF-482
        SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH
            WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5005 AND NAME = V_WIDGET_OLD_NAME;
        IF V_COUNT < 1 THEN
            DBMS_OUTPUT.PUT_LINE('WIDGET Summary NOT EXIST NO NEED TO UPDATE for tenant:'||V_TENANT_ID);
        ELSE
            UPDATE EMS_ANALYTICS_SEARCH SET NAME = V_WIDGET_NEW_NAME
                WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5005;
            DBMS_OUTPUT.PUT_LINE('WIDGET Summary UPDATED SUCCESSFULLY for tenant:'||V_TENANT_ID);
        END IF;

       ---EMCPSSF-483
       SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH
           WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5010;
       IF V_COUNT < 1 THEN
           DBMS_OUTPUT.PUT_LINE('Submissions by Type NOT EXIST NO NEED TO UPDATE for tenant:'||V_TENANT_ID);
       ELSE
           DELETE FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5010;
           DELETE FROM EMS_ANALYTICS_LAST_ACCESS WHERE TENANT_ID = V_TENANT_ID AND OBJECT_ID = V_SEARCH_ID_5010;
           DELETE FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5010;
           DBMS_OUTPUT.PUT_LINE('Submissions by Type DELETED SUCCESSFULLY for tenant:'||V_TENANT_ID);
       END IF;

       SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH
           WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5011;
       IF V_COUNT < 1 THEN
           DBMS_OUTPUT.PUT_LINE('Submissions by User NOT EXIST NO NEED TO UPDATE for tenant:'||V_TENANT_ID);
       ELSE
           DELETE FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5011;
           DELETE FROM EMS_ANALYTICS_LAST_ACCESS WHERE TENANT_ID = V_TENANT_ID AND OBJECT_ID = V_SEARCH_ID_5011;
           DELETE FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5011;
           DBMS_OUTPUT.PUT_LINE('Submissions by User DELETED SUCCESSFULLY for tenant:'||V_TENANT_ID);
       END IF;

       SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH
           WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5012;
       IF V_COUNT < 1 THEN
           DBMS_OUTPUT.PUT_LINE('Workflow Submission Alerts NOT EXIST NO NEED TO UPDATE for tenant:'||V_TENANT_ID);
       ELSE
           DELETE FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5012;
           DELETE FROM EMS_ANALYTICS_LAST_ACCESS WHERE TENANT_ID = V_TENANT_ID AND OBJECT_ID = V_SEARCH_ID_5012;
           DELETE FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID = V_SEARCH_ID_5012;
           DBMS_OUTPUT.PUT_LINE('Workflow Submission Alerts DELETED SUCCESSFULLY for tenant:'||V_TENANT_ID);
       END IF;

     IF (V_TID<>-1) THEN
        EXIT;
     END IF;
  END LOOP;
  IF TENANT_CURSOR%ISOPEN THEN
    CLOSE TENANT_CURSOR;
  END IF;

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Upgrade is done');
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('Failed to update the sql due to '||SQLERRM);
      RAISE;
  END;
  /
