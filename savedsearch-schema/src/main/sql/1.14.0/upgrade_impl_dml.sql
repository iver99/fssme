Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    REX      12/06/2016 - created
Rem

@&EMSAAS_SQL_ROOT/1.14.0/emaas_savesearch_seed_data.sql -1
@&EMSAAS_SQL_ROOT/1.14.0/emaas_savedsearch_seed_data_cos.sql -1
@&EMSAAS_SQL_ROOT/1.14.0/emaas_savedsearch_cos_widget_update.sql -1
COMMIT;

