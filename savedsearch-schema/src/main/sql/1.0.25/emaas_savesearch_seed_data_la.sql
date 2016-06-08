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
  v_PARAM_VALUE_STR VARCHAR2(1024 BYTE);
  v_SEARCH_ID NUMBER(38,0);
  v_CATEGORY_ID NUMBER(38,0);
  v_TENANT_ID NUMBER(38,0):= '&TENANT_ID';
  v_PARAM_TYPE NUMBER(38,0);
  v_NAME VARCHAR2(64 BYTE);
BEGIN

  SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE PARAM_VALUE_STR<>'emcla-visualization' AND SEARCH_ID in (select SEARCH_ID from EMS_ANALYTICS_SEARCH where CATEGORY_ID=1 and TENANT_ID='&TENANT_ID') and NAME='WIDGET_KOC_NAME' and PARAM_TYPE=1 and TENANT_ID='&TENANT_ID';
  IF (V_COUNT>0) THEN

   v_CATEGORY_ID :=1;
   v_PARAM_TYPE := 1;
--    Update EMS_ANALYTICS_SEARCH_PARAMS set PARAM_VALUE_STR='/html/search/widgets/visualizationWidget.html' where SEARCH_ID in (select SEARCH_ID from EMS_ANALYTICS_SEARCH where CATEGORY_ID=1 and TENANT_ID='&TENANT_ID') and NAME='WIDGET_TEMPLATE' and PARAM_TYPE=1 and TENANT_ID='&TENANT_ID';
--    Update EMS_ANALYTICS_SEARCH_PARAMS set PARAM_VALUE_STR='/js/viewmodel/search/widget/VisualizationWidget.js' where SEARCH_ID in (select SEARCH_ID from EMS_ANALYTICS_SEARCH where CATEGORY_ID=1 and TENANT_ID='&TENANT_ID') and NAME='WIDGET_VIEWMODEL' and PARAM_TYPE=1 and TENANT_ID='&TENANT_ID';
--    Update EMS_ANALYTICS_SEARCH_PARAMS set PARAM_VALUE_STR='emcla-visualization' where SEARCH_ID in (select SEARCH_ID from EMS_ANALYTICS_SEARCH where CATEGORY_ID=1 and TENANT_ID='&TENANT_ID') and NAME='WIDGET_KOC_NAME' and PARAM_TYPE=1 and TENANT_ID='&TENANT_ID';
   v_PARAM_VALUE_STR := '/html/search/widgets/visualizationWidget.html';
   v_NAME := 'WIDGET_TEMPLATE';
   Update EMS_ANALYTICS_SEARCH_PARAMS set PARAM_VALUE_STR=v_PARAM_VALUE_STR where SEARCH_ID in (select SEARCH_ID from EMS_ANALYTICS_SEARCH where CATEGORY_ID=v_CATEGORY_ID and TENANT_ID=v_TENANT_ID) and NAME=v_NAME and PARAM_TYPE=v_PARAM_TYPE and TENANT_ID=v_TENANT_ID;

   v_PARAM_VALUE_STR := '/js/viewmodel/search/widget/VisualizationWidget.js';
   v_NAME := 'WIDGET_VIEWMODEL';
   Update EMS_ANALYTICS_SEARCH_PARAMS set PARAM_VALUE_STR=v_PARAM_VALUE_STR where SEARCH_ID in (select SEARCH_ID from EMS_ANALYTICS_SEARCH where CATEGORY_ID=v_CATEGORY_ID and TENANT_ID=v_TENANT_ID) and NAME=v_NAME and PARAM_TYPE=v_PARAM_TYPE and TENANT_ID=v_TENANT_ID;

   v_PARAM_VALUE_STR := 'emcla-visualization';
   v_NAME := 'WIDGET_KOC_NAME';
   Update EMS_ANALYTICS_SEARCH_PARAMS set PARAM_VALUE_STR= v_PARAM_VALUE_STR where SEARCH_ID in (select SEARCH_ID from EMS_ANALYTICS_SEARCH where CATEGORY_ID=v_CATEGORY_ID and TENANT_ID=v_TENANT_ID) and NAME=v_NAME and PARAM_TYPE=v_PARAM_TYPE and TENANT_ID=v_TENANT_ID;
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
