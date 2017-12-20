Rem Copyright (c) 2013, 2014, 2015, 2016, 2017 Oracle and/or its affiliates.
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_dashboard_tile_title_length.sql
Rem
Rem    DESCRIPTION
Rem      increase the max lengh of search name from 64 to 256
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    Chehao       12/04/17
Rem
DECLARE
  v_length        INTEGER;
  
BEGIN

  SELECT data_length INTO v_length FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'EMS_ANALYTICS_SEARCH'  AND COLUMN_NAME='NAME';
  --original max length
  IF v_length = 64 THEN
    EXECUTE IMMEDIATE 'Alter table EMS_ANALYTICS_SEARCH modify NAME VARCHAR2(256)';
    DBMS_OUTPUT.PUT_LINE('Extend EMS_ANALYTICS_SEARCH search name column max length from 64 to 256 successfully!');
  ELSE
    DBMS_OUTPUT.PUT_LINE('Extend EMS_ANALYTICS_SEARCH search name column max length already be 256, , no further action will be taken.');
  END IF;

  EXCEPTION
  WHEN OTHERS THEN
  DBMS_OUTPUT.PUT_LINE('>>>SSF DDL ERROR<<<');
  DBMS_OUTPUT.PUT_LINE('Failed to extend EMS_ANALYTICS_SEARCH search name column max length from 64 to 256: ' || SQLERRM);
  RAISE;
END;
/



