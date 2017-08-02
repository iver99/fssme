Rem
Rem emaas_savedsearch_seed_data_sec.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, 2016, 2017 Oracle and/or its affiliates.
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savedsearch_seed_data_sec.sql
Rem
Rem    DESCRIPTION
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    MIAYU       07/26/17 -  change the category of Security Analytics OOB widgets 
Rem
SET FEEDBACK ON
SET SERVEROUTPUT ON
DECLARE
    
BEGIN
  UPDATE EMS_ANALYTICS_SEARCH SET FOLDER_ID = 7, CATEGORY_ID = 6 WHERE SEARCH_ID >= 3300 AND SEARCH_ID <= 3328;
  COMMIT;
EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('FAILED TO change the category of Security Analytics OOB WIDGET: '||SQLERRM);
  RAISE;
END;
/
