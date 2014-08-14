Rem
Rem $Header: emcore/source/oracle/sysman/emdrep/sql/core/13.1.0.0/emanalytics/eman_sequences.sql /st_emgc_pt-13.1mstr/1 2013/12/27 04:37:32 saurgarg Exp $
Rem
Rem eman_sequences.sql
Rem
Rem Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
Rem
Rem    NAME
Rem      eman_sequences.sql
Rem
Rem    DESCRIPTION
Rem      This file creates sequences for EM Analytics Framework of EMCORE
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    kuabhina    12/13/13 - Created
Rem
Rem    BEGIN SQL_FILE_METADATA 
Rem    SQL_SOURCE_FILE: emcore/source/oracle/sysman/emdrep/sql/core/13.1.0.0/emanalytics/eman_sequences.sql 
Rem    SQL_SHIPPED_FILE: 
Rem    SQL_PHASE: 
Rem    SQL_STARTUP_MODE: NORMAL 
Rem    SQL_IGNORABLE_ERRORS: NONE 	
Rem    SQL_CALLING_FILE: 
Rem    END SQL_FILE_METADATA

-- 1 -- 1000 is reserved
CREATE SEQUENCE EMS_ANALYTICS_FOLDERS_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1000 CACHE 20 ORDER  NOCYCLE;

-- 1 -- 1000 is reserved
CREATE SEQUENCE EMS_ANALYTICS_CATEGORY_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1000 CACHE 20 ORDER  NOCYCLE;

-- 1 -- 10000 is reserved
CREATE SEQUENCE EMS_ANALYTICS_SEARCH_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 10 START WITH 10000 CACHE 20 ORDER  NOCYCLE;

