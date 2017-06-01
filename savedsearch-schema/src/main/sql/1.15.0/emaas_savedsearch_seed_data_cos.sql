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
Rem    XIADAI       1/16/17 - update widgets 5009 ,5007, 5006
Rem

SET FEEDBACK ON
SET SERVEROUTPUT ON
DEFINE TENANT_ID ='&1'
DECLARE
    V_SEARCH_ID                         NUMBER(38,0)         ;
    V_TENANT_ID                         NUMBER(38,0)         ;
    V_COUNT                             NUMBER               ;
    V_TID                               NUMBER(38,0) := '&TENANT_ID';
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

       SELECT COUNT(*) INTO v_count FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID IN (5009, 5007, 5006);
       IF v_count > 0 THEN
        DELETE FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID IN (5009, 5007, 5006);
        DELETE FROM EMS_ANALYTICS_LAST_ACCESS WHERE TENANT_ID = V_TENANT_ID AND OBJECT_ID IN (5009, 5007, 5006);
        DELETE FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = V_TENANT_ID AND SEARCH_ID IN (5009, 5007, 5006);
        DBMS_OUTPUT.PUT_LINE('Searches with id : 5006, 5007, 5009 were deleted successfully for TENANT: '|| V_TENANT_ID);
       ELSE
        DBMS_OUTPUT.PUT_LINE('Searches with id : 5006, 5007, 5009 does not exist in TENANT: '|| V_TENANT_ID || ', no need to remove');
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
  DBMS_OUTPUT.PUT_LINE('FAILED TO  REMOVE  Orchestration WIDGET DUE TO '||SQLERRM);
  RAISE;
END;
/
