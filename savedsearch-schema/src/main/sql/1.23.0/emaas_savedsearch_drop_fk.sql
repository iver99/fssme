Rem Copyright (c) 2013, 2014, 2015, 2016, 2017 Oracle and/or its affiliates.
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savedsearch_drop_fk.sql
Rem
Rem    DESCRIPTION
Rem      remove all fk in SSF
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    REX       08/18/17
Rem


BEGIN
  EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_SEARCH_PARAMS DROP CONSTRAINT EMS_ANALYTICS_SEARCH_PARAMS_FK';
  DBMS_OUTPUT.PUT_LINE('EMS_ANALYTICS_SEARCH_PARAMS_FK has been dropped');
  EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -02443 THEN
         DBMS_OUTPUT.PUT_LINE('>>>SSF DDL ERROR<<<');
         RAISE;
      END IF;
END;
/

