Rem
Rem emaas_savesearch_seed_data.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data.sql 
Rem
Rem    DESCRIPTION
Rem      Seed data for category and default folder
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    ADUAN      3/15/2016 - Created
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE 
  V_PROVIDER_NAME  EMS_ANALYTICS_CATEGORY.PROVIDER_NAME%TYPE;
BEGIN

  SELECT PROVIDER_NAME INTO V_PROVIDER_NAME FROM EMS_ANALYTICS_CATEGORY WHERE CATEGORY_ID=1 and TENANT_ID='&TENANT_ID';
  IF (V_PROVIDER_NAME='LoganService') THEN
    Update EMS_ANALYTICS_CATEGORY set PROVIDER_NAME='LogAnalyticsUI' where CATEGORY_ID=1 and TENANT_ID='&TENANT_ID';
    commit;
    DBMS_OUTPUT.PUT_LINE('Category provider name: [LoganService] has been changed to [LogAnalyticsUI] successfully');  
  ELSE
    DBMS_OUTPUT.PUT_LINE('Category provider name: [LoganService] has been changed to [LogAnalyticsUI] before, no need to update again');  
  END IF;
  
EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('Failed to update Category provider name: [LoganService] to [LogAnalyticsUI] due to '||SQLERRM);   
  RAISE;  
END;
/

BEGIN

  UPDATE EMS_ANALYTICS_SEARCH_PARAMS SET PARAM_VALUE_STR='LogAnalyticsUI' WHERE TENANT_ID='&TENANT_ID' and NAME='PROVIDER_NAME' and SEARCH_ID in (SELECT SEARCH_ID FROM EMS_ANALYTICS_SEARCH WHERE CATEGORY_ID = 1);
  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Provider name of Log Analytics widgets has been updated from [LoganService] to [LogAnalyticsUI] successfully for tenant: &TENANT_ID');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to update provider name of Log Analytics widgets from [LoganService] to [LogAnalyticsUI] for tenant: &TENANT_ID due to '||SQLERRM);
    RAISE;
END;
/
