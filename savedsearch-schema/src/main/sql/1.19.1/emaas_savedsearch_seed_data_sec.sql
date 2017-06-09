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
Rem    MIAYU       06/03/17 -  fix EMCPSSF-524 again
Rem
SET FEEDBACK ON
SET SERVEROUTPUT ON
DEFINE TENANT_ID ='&1'
DECLARE
    V_SEARCH_ID                         NUMBER(38,0)         ;
    V_TENANT_ID                         NUMBER(38,0)         ;
    V_SEARCH_STR                        CLOB                           ;

    v_count number;
    V_TID                   NUMBER(38,0) := '&TENANT_ID';
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

   V_SEARCH_ID  := 3328;
   V_SEARCH_STR := '''SEF Record Type'' = correlation and ''SEF Destination Type'' in (''Oracle Database*'', ''omc_oracle_db*'') and ''SEF Category'' = anomaly | stats count as Anomalies by ''SEF Destination Endpoint Name'' | fields Anomalies, ''SEF Destination Endpoint Name'' as Database | top Anomalies';
   SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID = V_TENANT_ID;
   IF V_COUNT < 1 THEN
        DBMS_OUTPUT.PUT_LINE('Top 10 Oracle DBs by Anomalies not exist for '||V_TENANT_ID);
   ELSE
        UPDATE EMS_ANALYTICS_SEARCH SET SEARCH_DISPLAY_STR = V_SEARCH_STR WHERE SEARCH_ID = V_SEARCH_ID AND TENANT_ID = V_TENANT_ID;
        DBMS_OUTPUT.PUT_LINE('Top 10 Oracle DBs by Anomalies has been corrected for '||V_TENANT_ID);
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
  DBMS_OUTPUT.PUT_LINE('FAILED TO  update  Security Analytics OOB WIDGET:Top 10 Oracle DBs by Anomalies DUE TO '||SQLERRM);
  RAISE;
END;
/
