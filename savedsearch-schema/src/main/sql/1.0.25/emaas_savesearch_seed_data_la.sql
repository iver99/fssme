Rem
Rem emaas_savesearch_seed_data_la.sql
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data_la.sql 
Rem
Rem    DESCRIPTION
Rem      Seed data for Log Analytics
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    aduan       09/14/15 - Upgrade OOB saved widgets for LA
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE 
  V_COUNT NUMBER;
BEGIN

  SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE PARAM_VALUE_STR<>'emcla-visualization' AND SEARCH_ID in (select SEARCH_ID from EMS_ANALYTICS_SEARCH where CATEGORY_ID=1 and TENANT_ID='&TENANT_ID') and NAME='WIDGET_KOC_NAME' and PARAM_TYPE=1 and TENANT_ID='&TENANT_ID';
  IF (V_COUNT>0) THEN
    Update EMS_ANALYTICS_SEARCH_PARAMS set PARAM_VALUE_STR='/html/search/widgets/visualizationWidget.html' where SEARCH_ID in (select SEARCH_ID from EMS_ANALYTICS_SEARCH where CATEGORY_ID=1 and TENANT_ID='&TENANT_ID') and NAME='WIDGET_TEMPLATE' and PARAM_TYPE=1 and TENANT_ID='&TENANT_ID';
    Update EMS_ANALYTICS_SEARCH_PARAMS set PARAM_VALUE_STR='/js/viewmodel/search/widget/VisualizationWidget.js' where SEARCH_ID in (select SEARCH_ID from EMS_ANALYTICS_SEARCH where CATEGORY_ID=1 and TENANT_ID='&TENANT_ID') and NAME='WIDGET_VIEWMODEL' and PARAM_TYPE=1 and TENANT_ID='&TENANT_ID';
    Update EMS_ANALYTICS_SEARCH_PARAMS set PARAM_VALUE_STR='emcla-visualization' where SEARCH_ID in (select SEARCH_ID from EMS_ANALYTICS_SEARCH where CATEGORY_ID=1 and TENANT_ID='&TENANT_ID') and NAME='WIDGET_KOC_NAME' and PARAM_TYPE=1 and TENANT_ID='&TENANT_ID';
    commit;
    DBMS_OUTPUT.PUT_LINE('LA WIDGET meta data has been updated to meet up-to-date LA side change successfully');  
  ELSE
    DBMS_OUTPUT.PUT_LINE('LA WIDGET meta data has been updated before, no need to update again');  
  END IF;
  
EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('Failed to update LA WIDGET meta data according to up-to-date LA side change due to '||SQLERRM);   
  RAISE;  
END;
/
