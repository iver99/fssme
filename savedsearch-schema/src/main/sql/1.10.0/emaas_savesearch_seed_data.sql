Rem
Rem emaas_savesearch_seed_data.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, 2016, Oracle and/or its affiliates. 
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
Rem    ADUAN       07/05/16 - Add new category for Security Analytics
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE 
  V_PROVIDER_NAME  EMS_ANALYTICS_CATEGORY.PROVIDER_NAME%TYPE;
BEGIN

  SELECT PROVIDER_NAME INTO V_PROVIDER_NAME FROM EMS_ANALYTICS_CATEGORY WHERE CATEGORY_ID=1 AND TENANT_ID='&TENANT_ID';
  IF (V_PROVIDER_NAME='LoganService') THEN
    UPDATE EMS_ANALYTICS_CATEGORY SET PROVIDER_NAME='LogAnalyticsUI' WHERE CATEGORY_ID=1 AND TENANT_ID='&TENANT_ID';
    COMMIT;
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

DECLARE
  V_COUNT number;
BEGIN

SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE TENANT_ID='&TENANT_ID' AND NAME='PROVIDER_NAME' AND PARAM_VALUE_STR='LoganService';
IF (V_COUNT>0) THEN
  UPDATE EMS_ANALYTICS_SEARCH_PARAMS SET PARAM_VALUE_STR='LogAnalyticsUI' WHERE TENANT_ID='&TENANT_ID' AND NAME='PROVIDER_NAME' AND PARAM_VALUE_STR='LoganService';
  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Provider name of LogAnalytics widgets has been upgraded from [LoganService] to [LogAnalyticsUI] successfully for tenant: &TENANT_ID ! Upgraded records: '||V_COUNT);
ELSE
  DBMS_OUTPUT.PUT_LINE('Provider name of LogAnalytics widgets has been upgraded from [LoganService] to [LogAnalyticsUI] for tenant: &TENANT_ID before, no need to upgrade again');
END IF;

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to upgrade provider name of LogAnalytics widgets from [LoganService] to [LogAnalyticsUI] for tenant: &TENANT_ID due to '||SQLERRM);
    RAISE;
END;
/
