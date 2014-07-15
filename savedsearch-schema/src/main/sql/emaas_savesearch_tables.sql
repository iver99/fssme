Rem
Rem $Header: emcore/source/oracle/sysman/emdrep/sql/core/13.1.0.0/emanalytics/eman_tables.sql /st_emgc_pt-13.1mstr/2 2014/02/03 02:50:58 saurgarg Exp $
Rem
Rem eman_tables.sql
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      eman_tables.sql - EM Analytics Framework tables
Rem
Rem    DESCRIPTION
Rem      This file will create all the tables for EM Analytics Framework of EMCORE
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    saurgarg    01/27/14 - adding category params table and other changes
Rem    kuabhina    12/13/13 - Created
Rem
Rem    BEGIN SQL_FILE_METADATA 
Rem    SQL_SOURCE_FILE: emcore/source/oracle/sysman/emdrep/sql/core/13.1.0.0/emanalytics/eman_tables.sql 
Rem    SQL_SHIPPED_FILE: 
Rem    SQL_PHASE: 
Rem    SQL_STARTUP_MODE: NORMAL 
Rem    SQL_IGNORABLE_ERRORS: NONE 
Rem    SQL_CALLING_FILE: 
Rem    END SQL_FILE_METADATA

Rem EMS_ANALYTICS_FOLDERS
Rem
Rem PURPOSE
Rem
Rem This table stores folder structure information of Em Analytics search
Rem
Rem KEYS
Rem
Rem The primary key is FOLDER_ID
Rem The foreign key is PARENT_ID
Rem
Rem COLUMNS
Rem
Rem FOLDER_ID - Id of Folder
Rem NAME - name of Folder
Rem PARENT_ID - Id of parent folder
Rem DESCRIPTION - description of the folder
Rem CREATION_DATE - date on which the folder was created
Rem OWNER - owner username of the folder
Rem LAST_MODIFICATION_DATE - modification date of the folder.
Rem LAST_MODIFIED_BY - latest user to modify the search
Rem NAME_NLSID  -  name Nlsid to map to messages
Rem NAME_SUBSYSTEM - Subsystem to map to messages
Rem DESCRIPTION_NLSID - description Nlsid to map to messages
Rem DESCRIPTION_SUBSYSTEM - Subsystem to map to messages
Rem SYSTEM_FOLDER - checks whether the folder is ORACLE owned folder or not.
Rem EM_PLUGIN_ID - Id of the plugin to which folder belongs
Rem UI_HIDDEN - Attribute to mark the folder hidden from being displayed in UI
Rem

CREATE TABLE EMS_ANALYTICS_FOLDERS
  (
    FOLDER_ID 		   NUMBER(*,0) NOT NULL,
    NAME 		   VARCHAR2(64) NOT NULL,
    PARENT_ID 		   NUMBER(*,0),
    DESCRIPTION 	   VARCHAR2(256),
    CREATION_DATE  	   DATE DEFAULT SYS_EXTRACT_UTC(SYSTIMESTAMP),
    OWNER 		   VARCHAR2(64),
    LAST_MODIFICATION_DATE DATE DEFAULT SYS_EXTRACT_UTC(SYSTIMESTAMP),
    LAST_MODIFIED_BY 	   VARCHAR2(64),
    NAME_NLSID             VARCHAR2(256),
    NAME_SUBSYSTEM         VARCHAR2(64),
    DESCRIPTION_NLSID      VARCHAR2(256),
    DESCRIPTION_SUBSYSTEM  VARCHAR2(64),
    SYSTEM_FOLDER          NUMBER(1,0) default 0 not null constraint EMS_ANALYTICS_FLD_SYSF1 check (SYSTEM_FOLDER between 0 and 1) ,
    EM_PLUGIN_ID           VARCHAR2(64),
    UI_HIDDEN              NUMBER(1,0) default 0 not null constraint EMS_ANALYTICS_FLD_UIHID1 check (UI_HIDDEN between 0 and 1),
    DELETED              NUMBER(1,0) default 0 not null constraint EMS_ANALYTICS_FLD_DEL1 check (DELETED between 0 and 1), 	
    CONSTRAINT EMS_ANALYTICS_FOLDERS_PK PRIMARY KEY (FOLDER_ID) USING INDEX,
    CONSTRAINT EMS_ANALYTICS_FOLDERS_FK1 FOREIGN KEY (PARENT_ID) REFERENCES EMS_ANALYTICS_FOLDERS (FOLDER_ID) 
  )  ;
  

Rem This table stores category information of Em Analytics search
Rem
Rem KEYS
Rem
Rem The primary key is CATEGORY_ID
Rem
Rem COLUMNS
Rem
Rem CATEGORY_ID - Id of Category
Rem NAME - name of Category
Rem DESCRIPTION - description of the category
Rem OWNER - user who created the category
Rem CREATION_DATE - creation date of the category
Rem NAME_NLSID  -  name Nlsid to map to messages
Rem NAME_SUBSYSTEM - Subsystem to map to messages
Rem DESCRIPTION_NLSID - description Nlsid to map to messages
Rem DESCRIPTION_SUBSYSTEM - Subsystem to map to messages
Rem EM_PLUGIN_ID - Id of the plugin to which category belongs
Rem DEFAULT_FOLDER_ID - Default folder ID for the category entities
Rem

CREATE TABLE EMS_ANALYTICS_CATEGORY
  (
    CATEGORY_ID 	   NUMBER(*,0) NOT NULL,
    NAME 		   VARCHAR2(64) NOT NULL,
    DESCRIPTION 	   VARCHAR2(256),
    OWNER 		   VARCHAR2(64),
    CREATION_DATE 	   DATE DEFAULT SYS_EXTRACT_UTC(SYSTIMESTAMP),
    NAME_NLSID             VARCHAR2(256),
    NAME_SUBSYSTEM         VARCHAR2(64),
    DESCRIPTION_NLSID      VARCHAR2(256),
    DESCRIPTION_SUBSYSTEM  VARCHAR2(64),
    EM_PLUGIN_ID           VARCHAR2(64),
    DEFAULT_FOLDER_ID      NUMBER(*,0),
    DELETED                NUMBER(1,0) default 0 not null constraint EMS_ANALYTICS_CAT_DEL1 check (DELETED between 0 and 1),
    CONSTRAINT EMS_ANALYTICS_CATEGORY_PK PRIMARY KEY (CATEGORY_ID) USING INDEX,
    CONSTRAINT EMS_ANALYTICS_CATEGORY_FK1 FOREIGN KEY (DEFAULT_FOLDER_ID) REFERENCES EMS_ANALYTICS_FOLDERS (FOLDER_ID)
   )  ;

Rem This table stores data of EM Analytics search param
Rem
Rem KEYS
Rem
Rem The primary key is CATEGORY_ID, NAME
Rem
Rem COLUMNS
Rem
Rem CATEGORY_ID - Id of category to which the param belongs
Rem NAME - parameter name
Rem PARAM_VALUE - parameter value
Rem

CREATE TABLE EMS_ANALYTICS_CATEGORY_PARAMS
  (
    CATEGORY_ID              NUMBER(*,0) NOT NULL,
    NAME                     VARCHAR2(64) NOT NULL,
    PARAM_VALUE                    VARCHAR2(1024),
    CONSTRAINT EMS_ANALYTICS_CAT_PARAMS_PK PRIMARY KEY (CATEGORY_ID,NAME) USING INDEX,
    CONSTRAINT EMS_ANALYTICS_CAT_PARAMS_FK1 FOREIGN KEY (CATEGORY_ID) REFERENCES EMS_ANALYTICS_CATEGORY (CATEGORY_ID)
   );


Rem This table stores information of Em Analytics search
Rem
Rem KEYS
Rem
Rem The primary key is SEARCH_ID
Rem The foreign key is FOLDER_ID
Rem The foreign key is CATEGORY_ID
Rem
Rem COLUMNS
Rem
Rem SEARCH_ID - Id of Search
Rem SEARCH_GUID - Guid of the search (required to support EM privilege model)
Rem NAME - name of Search
Rem OWNER - owner username of the folder
Rem CREATION_DATE - creation date of the search
Rem LAST_MODIFICATION_DATE - modification date of the folder.
Rem LAST_MODIFIED_BY - latest user to modify the search
Rem DESCRIPTION - description of the search.
Rem FOLDER_ID - The Id of the folder in the EMS_ANALYTICS_FOLDERS table to which the search belongs
Rem CATEGORY_ID - The Category Id of the category to which the search belongs
Rem NAME_NLSID  -  name Nlsid to map to messages
Rem NAME_SUBSYSTEM - Subsystem to map to messages
Rem DESCRIPTION_NLSID - description Nlsid to map to messages
Rem DESCRIPTION_SUBSYSTEM - Subsystem to map to messages
Rem SYSTEM_SEARCH - checks whether the search is system owned or not
Rem EM_PLUGIN_ID - Id of the plugin to which folder belongs
Rem IS_LOCKED -  whether the search is locked or not for update to its metadata
Rem METADATA_CLOB  - clob to store search metadata.
Rem SEARCH_DISPLAY_STR - display string for search content.
Rem UI_HIDDEN - Attribute to mark the folder hidden from being displayed in UI
Rem

CREATE TABLE EMS_ANALYTICS_SEARCH
  (
    SEARCH_ID 		   NUMBER(*,0) NOT NULL,
    SEARCH_GUID 	   RAW(16) DEFAULT SYS_GUID(),
    NAME 		   VARCHAR2(64) NOT NULL ,
    OWNER 		   VARCHAR2(64) NOT NULL ,
    CREATION_DATE 	   DATE DEFAULT SYS_EXTRACT_UTC(SYSTIMESTAMP),
    LAST_MODIFICATION_DATE DATE DEFAULT SYS_EXTRACT_UTC(SYSTIMESTAMP),
    LAST_MODIFIED_BY 	   VARCHAR2(64),
    DESCRIPTION 	   VARCHAR2(256),
    FOLDER_ID 		   NUMBER(*,0) NOT NULL,
    CATEGORY_ID    	   NUMBER(*,0) NOT NULL,
    NAME_NLSID             VARCHAR2(256),
    NAME_SUBSYSTEM         VARCHAR2(64),
    DESCRIPTION_NLSID      VARCHAR2(256),
    DESCRIPTION_SUBSYSTEM  VARCHAR2(64),
    SYSTEM_SEARCH          NUMBER(1,0) default 0 not null constraint EMS_ANALYTICS_SRCH_SSRCH1 check (SYSTEM_SEARCH between 0 and 1),
    EM_PLUGIN_ID           VARCHAR2(64),
    IS_LOCKED          	   NUMBER(1,0) default 0 not null constraint EMS_ANALYTICS_SRCH_LCK1 check (IS_LOCKED between 0 and 1),
    METADATA_CLOB      	   NCLOB,
    SEARCH_DISPLAY_STR     VARCHAR2(4000),
    UI_HIDDEN		   NUMBER(1,0) default 0 not null constraint EMS_ANALYTICS_SRCH_UIHID1 check (UI_HIDDEN between 0 and 1),
    DELETED              NUMBER(1,0) default 0 not null constraint EMS_ANALYTICS_SEARCH_DEL1 check (DELETED between 0 and 1),
    CONSTRAINT EMS_ANALYTICS_SEARCH_PK PRIMARY KEY (SEARCH_ID) USING INDEX ,
    CONSTRAINT EMS_ANALYTICS_SEARCH_FK1 FOREIGN KEY (CATEGORY_ID) REFERENCES EMS_ANALYTICS_CATEGORY (CATEGORY_ID),
    CONSTRAINT EMS_ANALYTICS_SEARCH_FK2 FOREIGN KEY (FOLDER_ID) REFERENCES EMS_ANALYTICS_FOLDERS (FOLDER_ID) 
  ) ;
  

Rem This table stores data of EM Analytics search param
Rem
Rem KEYS
Rem
Rem The primary key is PARAM_ID
Rem
Rem COLUMNS
Rem
Rem SEARCH_ID - Id of search to whom the param belongs
Rem NAME - name of param
Rem PARAM_ATTRIBUTES - parameter attributes. These will be qualifying rules for the param value
Rem     	       For example if the attribute suggest regex, the param values will be regular expressions.
Rem		       This will be handled at java layer.
Rem PARAM_TYPE - type of param whether its Clob or a String or other types stored as String or clob, this will be handled by Java layer.
Rem	         The row entries should always specify the param type hence not null.
Rem PARAM_VALUE_STR - param string value
Rem PARAM_VALUE_CLOB - Param value clob
Rem 


CREATE TABLE EMS_ANALYTICS_SEARCH_PARAMS
  (
    SEARCH_ID 		   NUMBER(*,0) NOT NULL,
    NAME 		   VARCHAR2(64) NOT NULL,
    PARAM_ATTRIBUTES	   VARCHAR2(1024),
    PARAM_TYPE		   NUMBER(*,0) NOT NULL,
    PARAM_VALUE_STR        VARCHAR2(1024),
    PARAM_VALUE_CLOB       NCLOB,
    CONSTRAINT EMS_ANALYTICS_SEARCH_PROPS_PK PRIMARY KEY (SEARCH_ID,NAME) USING INDEX,
    CONSTRAINT EMS_ANALYTICS_SEARCH_PARAMS_FK FOREIGN KEY (SEARCH_ID) REFERENCES EMS_ANALYTICS_SEARCH (SEARCH_ID) 
   );


Rem This table stores data for last access of a search

CREATE TABLE EMS_ANALYTICS_LAST_ACCESS 
  (
    OBJECT_ID NUMBER(38,0) NOT NULL, 
    ACCESSED_BY VARCHAR2(64) NOT NULL, 
    OBJECT_TYPE NUMBER(38,0) NOT NULL, 
    ACCESS_DATE TIMESTAMP,
    CONSTRAINT EMS_ANALYTICS_LAST_ACCESS_PK PRIMARY KEY (OBJECT_ID,ACCESSED_BY,OBJECT_TYPE) USING INDEX
  );
