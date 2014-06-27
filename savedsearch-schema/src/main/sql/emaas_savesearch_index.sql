Rem
Rem $Header: emcore/source/oracle/sysman/emdrep/sql/core/13.1.0.0/emanalytics/eman_indexes.sql /st_emgc_pt-13.1mstr/1 2013/12/27 04:37:32 saurgarg Exp $
Rem
Rem eman_indexes.sql
Rem
Rem Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
Rem
Rem    NAME
Rem      eman_indexes.sql
Rem
Rem    DESCRIPTION
Rem      This file will create indexes on tables for EM Analytics Framework of EMCORE
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    kuabhina    12/13/13 - Created
Rem
Rem    BEGIN SQL_FILE_METADATA 
Rem    SQL_SOURCE_FILE: emcore/source/oracle/sysman/emdrep/sql/core/13.1.0.0/emanalytics/eman_indexes.sql 
Rem    SQL_SHIPPED_FILE: 
Rem    SQL_PHASE: 
Rem    SQL_STARTUP_MODE: NORMAL 
Rem    SQL_IGNORABLE_ERRORS: NONE 
Rem    SQL_CALLING_FILE: 
Rem    END SQL_FILE_METADATA

Rem ********************************************************************
Rem ** Indexes on folder and category  tables to help with tree creation
Rem ** query performance.
Rem ********************************************************************

CREATE INDEX EMS_ANALYTICS_FOLDERS_IDX1 ON EMS_ANALYTICS_FOLDERS(PARENT_ID) COMPRESS ;


Rem ********************************************************************
Rem ** Indexes on search tables to help with search query look up query
Rem ** performance.
Rem ********************************************************************

CREATE INDEX EMS_ANALYTICS_SEARCH_IDX1 ON EMS_ANALYTICS_SEARCH(SEARCH_DISPLAY_STR) ;


Rem ********************************************************************
Rem ** Indexes on search tables to help with categorization query
Rem ** performance.
Rem ********************************************************************

CREATE INDEX EMS_ANALYTICS_SEARCH_IDX2 ON EMS_ANALYTICS_SEARCH(CATEGORY_ID) COMPRESS ;


Rem ********************************************************************
Rem ** Indexes on search tables to help with folder organizational query
Rem ** performance.
Rem ********************************************************************

CREATE INDEX EMS_ANALYTICS_SEARCH_IDX3 ON EMS_ANALYTICS_SEARCH(FOLDER_ID) COMPRESS ;



