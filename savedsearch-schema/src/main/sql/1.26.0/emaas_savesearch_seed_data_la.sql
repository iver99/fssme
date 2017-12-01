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
Rem    agor    11/30/17 - update widgets 2007,2008,2009,2018 widgets (missing trailing s in query)
Rem
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE
  V_SEARCH_DISPLAY_STR CLOB;
  V_SEARCH_ID NUMBER;
  V_TENANT_ID NUMBER;
  
BEGIN
     V_TENANT_ID:= -11;
     
     V_SEARCH_ID := 2007;		
     V_SEARCH_DISPLAY_STR := '''log source'' like in (''FMW WLS Server Access Logs%'',''FMW OHS Access Logs%'') |stats count as ''Request Count'' by URI |top limit=10 ''Request Count''';
     Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_DISPLAY_STR WHERE SEARCH_ID=V_SEARCH_ID and TENANT_ID=V_TENANT_ID;

     V_SEARCH_ID := 2008;		
     V_SEARCH_DISPLAY_STR := '''log source'' like in (''FMW WLS Server Access Logs%'', ''FMW OHS Access Logs%'') AND Status IN (''400'', ''401'', ''402'', ''403'', ''404'', ''405'', ''406'', ''407'', ''408'', ''409'', ''410'', ''411'', ''412'', ''413'', ''414'', ''415'', ''416'', ''417'', ''500'', ''501'', ''502'', ''503'', ''504'', ''505'')';
     Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_DISPLAY_STR WHERE SEARCH_ID=V_SEARCH_ID and TENANT_ID=V_TENANT_ID;

     V_SEARCH_ID := 2009;
     V_SEARCH_DISPLAY_STR := '''log source'' like in (''FMW WLS Server Access Logs%'',''FMW OHS Access Logs%'') |stats count as ''Request Count'' by target |top limit=10 ''Request Count''';
     Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_DISPLAY_STR WHERE SEARCH_ID=V_SEARCH_ID and TENANT_ID=V_TENANT_ID;

     V_SEARCH_ID := 2018;
     V_SEARCH_DISPLAY_STR := '''log source'' like in (''FMW WLS Server Access Logs%'',''FMW OHS Access Logs%'') and URI != null and "File Extension" not in ("gif","png","jpg","js","css","swf","ico") and URI not like "*/blank.html" | stats count as "Request Count" by URI |top limit=10 "Request Count"';
     Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_DISPLAY_STR WHERE SEARCH_ID=V_SEARCH_ID and TENANT_ID=V_TENANT_ID;
	 
     COMMIT;
     DBMS_OUTPUT.PUT_LINE('Bug : 27085336 -> Query string of LA widgets ids: 2008 2008 2009 2018 have been updated to: FMW WLS Server Access Logs and FMW OHS Access Logs');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Bug : 27085336 -> Failed to update Query string of LA widgets ids: 2007 2008 2009 2018 due to '||SQLERRM);
    RAISE;
END;
/