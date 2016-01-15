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
Rem    GUOCHEN     10/29/2015 - Introduce WIDGET_SUPPORT_TIME_CONTROL for widgets
Rem    miayu       10/28/2015 - update category name from "Target Analytics" to "Data Explorer - Search"
Rem    JNAN        01/15/2016 - update category name from "Data Explorer - Search" to "Data Explorer"
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE 
  V_CATEGORY_NAME  EMS_ANALYTICS_CATEGORY.NAME%TYPE;
BEGIN

  SELECT NAME INTO V_CATEGORY_NAME FROM EMS_ANALYTICS_CATEGORY WHERE CATEGORY_ID=2 and TENANT_ID='&TENANT_ID';
  IF (V_CATEGORY_NAME='Data Explorer - Search') THEN
    Update EMS_ANALYTICS_CATEGORY set NAME='Data Explorer' where CATEGORY_ID=2 and TENANT_ID='&TENANT_ID';
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
