Rem  drv: <migrate type="data_upgrade" version="13.1.0.0" />
Rem
Rem upgrade_058_059_ta_dml.sql
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      upgrade_058_059_ta_dml.sql 
Rem
Rem    DESCRIPTION
Rem      Target Analytics upgrade
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    WKEICHER    02/18/15 - Update TA OOB Searches
Rem    VINJOSHI    02/23/2015 - Update - call seed data with teant id passed from outside
Rem

DEFINE TENANT_ID = '&1'

delete from EMS_ANALYTICS_LAST_ACCESS where OBJECT_ID>=3000 and OBJECT_ID<=3999 and OBJECT_TYPE=2 AND TENANT_ID ='&TENANT_ID';
delete from EMS_ANALYTICS_SEARCH_PARAMS where SEARCH_ID >= 3000 and SEARCH_ID <= 3999  AND TENANT_ID ='&TENANT_ID';
delete from EMS_ANALYTICS_SEARCH where SEARCH_ID >= 3000 and SEARCH_ID <= 3999  AND TENANT_ID ='&TENANT_ID';
commit;
@../emaas_savesearch_seed_data_ta.sql  '&TENANT_ID'
commit;
