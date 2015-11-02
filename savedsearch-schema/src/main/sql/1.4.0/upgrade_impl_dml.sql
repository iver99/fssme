Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    GUOCHEN    10/29/2015 - Update - call seed data with teant id passed from outside
Rem

DEFINE TENANT_ID = '&1'
@&EMSAAS_SQL_ROOT/1.4.0/emaas_savesearch_seed_data.sql '&TENANT_ID'
commit;

