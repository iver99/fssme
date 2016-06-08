Rem
Rem emaas_savesearch_seed_data_la.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data_la.sql 
Rem
Rem    DESCRIPTION
Rem      Seed data for LA
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    miayu       11/28/15 - fix emcpssf-174
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE 
v_SEARCH_DISPLAY_STR VARCHAR2(1024 BYTE);
v_SEARCH_ID NUMBER(38,0);
v_TENANT_ID NUMBER(38,0) := '&TENANT_ID';
BEGIN
  v_SEARCH_DISPLAY_STR := '''log source'' in (''FMW WLS Server Access Log'',''FMW OHS Access Log'') |stats count as ''Request Count'' by URI |top limit=10 ''Request Count''';
  v_SEARCH_ID :=2007;
--  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR= '''log source'' in (''FMW WLS Server Access Log'',''FMW OHS Access Log'') |stats count as ''Request Count'' by URI |top limit=10 ''Request Count'''where SEARCH_ID=2007 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR= v_SEARCH_DISPLAY_STR where SEARCH_ID=v_SEARCH_ID and TENANT_ID=v_TENANT_ID;
--  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''log source'' in (''FMW WLS Server Access Log'',''FMW OHS Access Log'') and URI != null and "File Extension" not in ("gif","png","jpg","js","css","swf","ico") and URI not like "*/blank.html" | stats count as "Request Count" by URI |top limit=10 "Request Count"' where SEARCH_ID=2018 and TENANT_ID='&TENANT_ID';
  v_SEARCH_DISPLAY_STR := '''log source'' in (''FMW WLS Server Access Log'',''FMW OHS Access Log'') and URI != null and "File Extension" not in ("gif","png","jpg","js","css","swf","ico") and URI not like "*/blank.html" | stats count as "Request Count" by URI |top limit=10 "Request Count"';
  v_SEARCH_ID :=2018;
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=v_SEARCH_DISPLAY_STR where SEARCH_ID=v_SEARCH_ID and TENANT_ID=v_TENANT_ID;
  commit;
  DBMS_OUTPUT.PUT_LINE('Query string of LA OOB widgets [Web Server Top Accessed Pages] and [Web Server Top Accessed Pages (Excluding Assets)] have been updated');  
  
EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('Failed to update Query string of LA OOB widgets [Web Server Top Accessed Pages] and [Web Server Top Accessed Pages (Excluding Assets)] due to '||SQLERRM);   
  RAISE;  
END;
/