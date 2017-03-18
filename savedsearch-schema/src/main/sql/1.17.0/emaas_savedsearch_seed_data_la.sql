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
Rem    MISANDOV    03/09/17 - update widget 2004
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE
  V_SEARCH_ID                           NUMBER                          ;
  V_SEARCH_DISPLAY_STR                  CLOB                            ;

  V_TENANT_ID                           NUMBER                          ;
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

     V_SEARCH_ID := 2004;
     V_SEARCH_DISPLAY_STR := '''Entity Type'' like ''Host%''';
     Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_DISPLAY_STR WHERE SEARCH_ID=V_SEARCH_ID and TENANT_ID=V_TENANT_ID;
 
  IF (V_TID<>-1) THEN
    EXIT;
  END IF;
  END LOOP;
  CLOSE TENANT_CURSOR;
  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Query string of LA widget (id: 2004 name: Host Logs Trend) has been updated to:  Entity Type like Host%');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to update Query string of LA widget (id: 2004 name: Host Logs Trend) due to '||SQLERRM);
    RAISE;
END;
/