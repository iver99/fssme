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
Rem    GUOCHEN     12/22/15 - Created
Rem    JNAN        01/15/2016 - update category name from "Data Explorer - Search" to "Data Explorer"
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE
    V_COUNT             NUMBER;
    DBWIDGET_SEARCH_ID    VARCHAR2(5);
    WLSWIDGET_SEARCH_ID    VARCHAR2(5);
    DBDASHBOARD_LINK_ID   VARCHAR2(3);
    WLSDASHBOARD_LINK_ID   VARCHAR2(3);
    
    V_TENANT_ID NUMBER;
    V_NUMBER_1 NUMBER;
    V_WIDGET_NAME VARCHAR2(64 BYTE);
BEGIN
  DBWIDGET_SEARCH_ID := '3024';
  WLSWIDGET_SEARCH_ID := '3026';
  DBDASHBOARD_LINK_ID := '8';
  WLSDASHBOARD_LINK_ID := '10';
  
  V_TENANT_ID := '&TENANT_ID';
  V_NUMBER_1 := 1;
  V_WIDGET_NAME := 'WIDGET_LINKED_DASHBOARD';

  SELECT COUNT(1) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE TENANT_ID=V_TENANT_ID AND SEARCH_ID=DBWIDGET_SEARCH_ID AND PARAM_TYPE=V_NUMBER_1 AND NAME = V_WIDGET_NAME AND PARAM_VALUE_STR =DBDASHBOARD_LINK_ID;
  IF (V_COUNT>0) THEN
    DBMS_OUTPUT.PUT_LINE('WIDGET_LINKED_DASHBOARD is already updated for SEARCH_ID: 3024 and 3026, no need to update again');
  ELSE

    INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) VALUES (DBWIDGET_SEARCH_ID, V_WIDGET_NAME, null, V_NUMBER_1, DBDASHBOARD_LINK_ID, null, V_TENANT_ID);
    INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) VALUES (WLSWIDGET_SEARCH_ID, V_WIDGET_NAME, null, V_NUMBER_1, WLSDASHBOARD_LINK_ID, null, V_TENANT_ID);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Update WIDGET_LINKED_DASHBOARD for SEARCH_ID: 3024 and 3026 in Schema object: EMS_ANALYTICS_SEARCH_PARAMS successfully for tenant: &TENANT_ID');
  END IF;

EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('Failed to update WIDGET_LINKED_DASHBOARD on SEARCH_ID: 3024 and 3026 due to '||SQLERRM);
  RAISE;
END;
/


DECLARE 
  V_CATEGORY_NAME  EMS_ANALYTICS_CATEGORY.NAME%TYPE;
  V_NUMBER_2 NUMBER;
  V_TENANT_ID NUMBER;
  V_NEW_NAME VARCHAR2(64 BYTE);
BEGIN
  V_NUMBER_2 := 2;
  V_TENANT_ID := '&TENANT_ID';
  V_NEW_NAME := 'Data Explorer';
  
  SELECT NAME INTO V_CATEGORY_NAME FROM EMS_ANALYTICS_CATEGORY WHERE CATEGORY_ID=V_NUMBER_2 and TENANT_ID=V_TENANT_ID;
  IF (V_CATEGORY_NAME='Data Explorer - Search') THEN
    Update EMS_ANALYTICS_CATEGORY set NAME=V_NEW_NAME where CATEGORY_ID=V_NUMBER_2 and TENANT_ID=V_TENANT_ID;
    commit;
    DBMS_OUTPUT.PUT_LINE('Category name: [Data Explorer - Search] has been changed to [Data Explorer] successfully');  
  ELSE
    DBMS_OUTPUT.PUT_LINE('Category name: [Data Explorer - Search] has been changed to [Data Explorer]  before, no need to update again');  
  END IF;
  
EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('Failed to update Category name: [Data Explorer - Search] to [Data Explorer] due to '||SQLERRM);   
  RAISE;  
END;
/

DECLARE
  V_COUNT number;
  V_TENANT_ID NUMBER;
  V_OLD_VERSION VARCHAR2(64 BYTE);
  V_NEW_VERSION VARCHAR2(64 BYTE);
BEGIN
  V_TENANT_ID := '&TENANT_ID';
  V_OLD_VERSION := '0.1';
  V_NEW_VERSION := '1.0';
  
SELECT COUNT(1) INTO V_COUNT from EMS_ANALYTICS_CATEGORY where TENANT_ID=V_TENANT_ID AND PROVIDER_VERSION=V_OLD_VERSION;
IF (V_COUNT>0) THEN
  UPDATE EMS_ANALYTICS_CATEGORY SET PROVIDER_VERSION=V_NEW_VERSION WHERE TENANT_ID=V_TENANT_ID;--set all to 1.0 including ta version from 1.0.5 to 1.0
  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Provider version of category has been upgraded from 0.1 to 1.0 for tenant: &TENANT_ID successfully! Upgraded records: '||V_COUNT);
ELSE
  DBMS_OUTPUT.PUT_LINE('Provider version of category  has been upgraded from 0.1 to 1.0 for tenant: &TENANT_ID before, no need to upgrade again');
END IF;

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to upgrade version from 0.1 to 1.0 for tenant: &TENANT_ID due to '||SQLERRM);
    RAISE;
END;
/

DECLARE
  V_COUNT number;
  V_TENANT_ID NUMBER;
  V_VERSION VARCHAR2(64 BYTE);
  V_PARAMS_NAME VARCHAR2(64 BYTE);
BEGIN
  V_TENANT_ID := '&TENANT_ID';
  V_VERSION := '1.0';
  V_PARAMS_NAME := 'PROVIDER_VERSION';

  UPDATE EMS_ANALYTICS_SEARCH_PARAMS SET PARAM_VALUE_STR=V_VERSION WHERE TENANT_ID=V_TENANT_ID and NAME=V_PARAMS_NAME;--set all to 1.0 including ta version from 1.0.5 to 1.0
  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Provider version of widget  has been upgraded from 0.1 to 1.0 for tenant: &TENANT_ID successfully! Upgraded records: '||V_COUNT);

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to upgrade version of widget from 0.1 to 1.0 for tenant: &TENANT_ID due to '||SQLERRM);
    RAISE;
END;
/
