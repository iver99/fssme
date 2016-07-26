Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    ADUAN      07/05/2016 - created
Rem

DEFINE TENANT_ID = '&1'

--update OOB Category, since OOB category can be referenced by both normal and OOB search, don't use delete from ...

--update OOB Folder, since OOB folder can be referenced by search and category, don't use delete from ...

@&EMSAAS_SQL_ROOT/1.9.0/emaas_savesearch_seed_data.sql '&TENANT_ID'
@&EMSAAS_SQL_ROOT/1.9.0/emaas_savesearch_seed_data_ocs.sql '&TENANT_ID'
@&EMSAAS_SQL_ROOT/1.9.0/emaas_savesearch_seed_data_la.sql '&TENANT_ID'
@&EMSAAS_SQL_ROOT/1.9.0/emaas_savesearch_ta_remove_brownfield.sql  '&TENANT_ID'
@&EMSAAS_SQL_ROOT/1.9.0/emaas_savesearch_ta_ui_gallery.sql  '&TENANT_ID'
@&EMSAAS_SQL_ROOT/1.9.0/emaas_savesearch_seed_data_targetcard.sql  '&TENANT_ID'
--below has to be the last one which updates existing data
@&EMSAAS_SQL_ROOT/1.9.0/emaas_savesearch_seed_data_params.sql '&TENANT_ID'
commit;

