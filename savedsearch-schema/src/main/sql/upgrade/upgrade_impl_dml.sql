Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    MIAYU       03/25/2015 - remove old OOB entities and then add new OOB entities
Rem    VINJOSHI    02/23/2015 - Update - call seed data with teant id passed from outside
Rem

DEFINE TENANT_ID = '&1'

delete from EMS_ANALYTICS_LAST_ACCESS where OBJECT_ID<=9999 and OBJECT_TYPE=2 AND TENANT_ID ='&TENANT_ID';
delete from EMS_ANALYTICS_SEARCH_PARAMS where SEARCH_ID <= 9999  AND TENANT_ID ='&TENANT_ID';
delete from EMS_ANALYTICS_SEARCH where SEARCH_ID <= 9999  AND TENANT_ID ='&TENANT_ID';
--update OOB Category, since OOB category can be referenced by both normal and OOB search, don't use delete from ...

--update OOB Folder, since OOB folder can be referenced by search and category, don't use delete from ...

commit;

@../emaas_savesearch_seed_data_la.sql '&TENANT_ID'
@../emaas_savesearch_seed_data_ta.sql '&TENANT_ID'
@../emaas_savesearch_seed_data_apm.sql '&TENANT_ID'
commit;
