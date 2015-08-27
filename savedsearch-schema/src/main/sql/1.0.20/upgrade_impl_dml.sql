Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    MIAYU       03/25/2015 - remove old OOB entities and then add new OOB entities
Rem    VINJOSHI    02/23/2015 - Update - call seed data with teant id passed from outside
Rem		 SDHAMDHE		8/26/2015		- Updated for EMCPSSF-184, LCM Rep manager cutover
Rem

DEFINE TENANT_ID = '&1'

--update OOB Category, since OOB category can be referenced by both normal and OOB search, don't use delete from ...

--update OOB Folder, since OOB folder can be referenced by search and category, don't use delete from ...

@&EMSAAS_SQL_ROOT/1.0.20/emaas_savesearch_seed_data_ta.sql '&TENANT_ID'
commit;

