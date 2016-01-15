Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    MIAYU       10/28/2015 - place holder
Rem    JNAN        01/15/2016 - Rename "Data Explorer - Search" to "Data Explorer" in category(EMCPDF-1221)

DEFINE TENANT_ID = '&1'

--update OOB Category, since OOB category can be referenced by both normal and OOB search, don't use delete from ...

--update OOB Folder, since OOB folder can be referenced by search and category, don't use delete from ...

@&EMSAAS_SQL_ROOT/1.5.0/emaas_savesearch_seed_data.sql '&TENANT_ID'


commit;

