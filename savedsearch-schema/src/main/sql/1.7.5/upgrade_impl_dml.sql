Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    ADUAN      3/15/2016 - Created
Rem

DEFINE TENANT_ID = '&1'

--update OOB Category, since OOB category can be referenced by both normal and OOB search, don't use delete from ...

--update OOB Folder, since OOB folder can be referenced by search and category, don't use delete from ...

@&EMSAAS_SQL_ROOT/1.7.5/emaas_savesearch_seed_data.sql '&TENANT_ID'

commit;

