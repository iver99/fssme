Rem
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      upgrade_056_057_ta_dml.sql
Rem
Rem    DESCRIPTION
Rem      Upgrade file for upgrading from v0.5.6 to v0.5.7 
Rem
Rem    NOTES
Rem      This file will get called via generic upgrade.sql - upgrade.sql gets called as part of running SSF deployment recipe
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    WKEICHER		09/30/14 - Upgrade TA OOB Searches  
Rem




delete from EMS_ANALYTICS_LAST_ACCESS where OBJECT_ID>=3000 and OBJECT_ID<=3999 and OBJECT_TYPE=2 ;
delete from EMS_ANALYTICS_SEARCH_PARAMS where SEARCH_ID >= 3000 and SEARCH_ID <= 3999 ;
delete from EMS_ANALYTICS_SEARCH where SEARCH_ID >= 3000 and SEARCH_ID <= 3999 ;
commit;
@../emaas_savesearch_seed_data_ta.sql
commit;
