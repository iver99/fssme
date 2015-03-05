Rem
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      upgrade_057_058_apm_dml.sql
Rem
Rem    DESCRIPTION
Rem      Upgrade file for upgrading from v0.5.7 to v0.5.8
Rem
Rem    NOTES
Rem      This file will get called via generic upgrade.sql - upgrade.sql gets called as part of running SSF deployment recipe
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    MIAYU		10/11/14 - Introduce APMCS OOB Folders and Searches  
Rem



delete from EMS_ANALYTICS_LAST_ACCESS where OBJECT_ID>=5000 and OBJECT_ID<=5999 and OBJECT_TYPE=2 ;
delete from EMS_ANALYTICS_SEARCH_PARAMS where SEARCH_ID>=5000 and SEARCH_ID<=5999; 
delete from EMS_ANALYTICS_SEARCH where SEARCH_ID>=5000 and SEARCH_ID<=5999;
delete from EMS_ANALYTICS_CATEGORY_PARAMS where CATEGORY_ID=4 ;
delete from EMS_ANALYTICS_CATEGORY where CATEGORY_ID=4 ;
delete from EMS_ANALYTICS_FOLDERS where (FOLDER_ID=5 or (FOLDER_ID>=500 and FOLDER_ID<=599));
commit;

@../emaas_savesearch_seed_data_apm.sql
commit;
