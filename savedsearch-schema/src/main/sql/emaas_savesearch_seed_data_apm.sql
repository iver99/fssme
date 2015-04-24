Rem
Rem emaas_savesearch_seed_data_apm.sql
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data_apm.sql 
Rem
Rem    DESCRIPTION
Rem      Seed data for Application Performance Monitoring Cloud Service
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    miayu       04/21/15 - move OOB folder for APMCS to emaas_savesearch_seed_data.sql. Only OOB SEARCHES are expected to be in this file
Rem    evazhang    10/16/14 - Add OOB sub-folders for APMCS
Rem    miayu       10/11/14 - Created for APMCS
Rem

--place holder now
DEFINE TENANT_ID ='&1'

insert into EMS_ANALYTICS_LAST_ACCESS(OBJECT_ID,ACCESSED_BY,OBJECT_TYPE,ACCESS_DATE,TENANT_ID)
select SEARCH_ID,'SYSMAN',2,SYS_EXTRACT_UTC(SYSTIMESTAMP),'&TENANT_ID' from EMS_ANALYTICS_SEARCH where search_id>=5000 and search_id<=5999 and TENANT_ID ='&TENANT_ID';

