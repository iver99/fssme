Rem  drv: <create type="triggers"/>
Rem $Header: emcore/source/oracle/sysman/emdrep/sql/core/latest/emanalytics/eman_triggers.sql /st_emgc_pt-13.1mstr/1 2013/12/27 04:37:32 saurgarg Exp $
Rem
Rem eman_triggers.sql
Rem
Rem Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
Rem
Rem    NAME
Rem      eman_triggers.sql - EM Analytics triggers
Rem
Rem    DESCRIPTION
Rem      This file will create triggers on tables for EM Analytics Framework of EMCORE
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    kuabhina    12/13/13 - Created
Rem

CREATE OR REPLACE TRIGGER  EMS_ANALYTICS_FOLDERS_TR BEFORE INSERT ON EMS_ANALYTICS_FOLDERS
FOR EACH ROW
BEGIN
  IF :new.FOLDER_ID IS NULL THEN
    SELECT EMS_ANALYTICS_FOLDERS_SEQ.NEXTVAL INTO :new.FOLDER_ID FROM DUAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER  EMS_ANALYTICS_CATEGORY_TR BEFORE INSERT ON EMS_ANALYTICS_CATEGORY
FOR EACH ROW
BEGIN
  IF :new.CATEGORY_ID IS NULL THEN
    SELECT EMS_ANALYTICS_CATEGORY_SEQ.NEXTVAL INTO :new.CATEGORY_ID FROM DUAL;
  END IF;
END;
/


CREATE OR REPLACE TRIGGER  EMS_ANALYTICS_SEARCH_TR BEFORE INSERT ON EMS_ANALYTICS_SEARCH
FOR EACH ROW
BEGIN
  IF :new.SEARCH_ID IS NULL THEN
    SELECT EMS_ANALYTICS_SEARCH_SEQ.NEXTVAL INTO :new.SEARCH_ID FROM DUAL;
  END IF;
END;
/

