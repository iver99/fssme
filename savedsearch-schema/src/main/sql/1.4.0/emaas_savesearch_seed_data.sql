Rem
Rem emaas_savesearch_seed_data.sql
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
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
Rem    miayu       10/28/15 - update category name from "Target Analytics" to "Data Explorer - Search"
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE 
  V_CATEGORY_NAME  EMS_ANALYTICS_CATEGORY.NAME%TYPE;
BEGIN

  SELECT NAME INTO V_CATEGORY_NAME FROM EMS_ANALYTICS_CATEGORY WHERE CATEGORY_ID=2 and TENANT_ID='&TENANT_ID';
  IF (V_CATEGORY_NAME='Target Analytics') THEN
    Update EMS_ANALYTICS_CATEGORY set NAME='Data Explorer - Search' where CATEGORY_ID=2 and TENANT_ID='&TENANT_ID';
    commit;
    DBMS_OUTPUT.PUT_LINE('Category name: [Target Analytics] has been changed to [Data Explorer - Search] successfully');  
  ELSE
    DBMS_OUTPUT.PUT_LINE('Category name: [Target Analytics] has been changed to [Data Explorer - Search]  before, no need to update again');  
  END IF;
  
EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('Failed to update Category name: [Target Analytics] to [Data Explorer - Search] due to '||SQLERRM);   
  RAISE;  
END;
/
