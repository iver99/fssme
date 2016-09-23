Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    ADUAN      07/05/2016 - created
Rem

--DEFINE TENANT_ID = '&1'

--update OOB Category, since OOB category can be referenced by both normal and OOB search, don't use delete from ...

--update OOB Folder, since OOB folder can be referenced by search and category, don't use delete from ...
-- -1 means we will update data of all existing tenants
@&EMSAAS_SQL_ROOT/1.9.1/emaas_savesearch_seed_data_la.sql  -1
@&EMSAAS_SQL_ROOT/1.9.1/emaas_savesearch_seed_data_remove_brownfield.sql -1
@&EMSAAS_SQL_ROOT/1.9.1/emaas_savesearch_seed_data_ocs.sql -1
@&EMSAAS_SQL_ROOT/1.9.1/emaas_savesearch_seed_data_ui_gallery.sql -1
COMMIT;

