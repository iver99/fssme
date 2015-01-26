Rem
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      upgrade_058_100_dml.sql
Rem
Rem    DESCRIPTION
Rem      Upgrade file for upgrading from v0.5.8 to v1.0.0 
Rem
Rem    NOTES
Rem      This file will get called via generic upgrade.sql - upgrade.sql gets called as part of running SSF deployment recipe
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    aduan		01/20/15 - upgrade OOB Folder Searches from 0.5.8 to 1.0.0  
Rem

Rem delete exited data to support upgrade
delete from EMS_ANALYTICS_SEARCH_PARAMS;
delete from EMS_ANALYTICS_SEARCH;
delete from EMS_ANALYTICS_CATEGORY_PARAMS;
delete from EMS_ANALYTICS_CATEGORY;
delete from EMS_ANALYTICS_LAST_ACCESS;

commit;

Rem add columns to table EMS_ANALYTICS_CATEGORY
alter table EMS_ANALYTICS_CATEGORY add (PROVIDER_NAME VARCHAR2(64) NOT NULL);
alter table EMS_ANALYTICS_CATEGORY add (PROVIDER_VERSION VARCHAR2(64) NOT NULL);
alter table EMS_ANALYTICS_CATEGORY add (PROVIDER_DISCOVERY VARCHAR2(64));
alter table EMS_ANALYTICS_CATEGORY add (PROVIDER_ASSET_ROOT VARCHAR2(64) NOT NULL);

Rem add columns to table EMS_ANALYTICS_SEARCH
alter table EMS_ANALYTICS_SEARCH add (IS_WIDGET NUMBER(1,0) default 0 not null constraint EMS_ANALYTICS_SRCH_LCK2 check (IS_WIDGET between 0 and 1));

commit;

Rem insert seed data
@../emaas_savesearch_seed_data.sql
@../emaas_savesearch_seed_data_ta.sql
@../emaas_savesearch_seed_data_la.sql
@../emaas_savesearch_seed_data_apm.sql
