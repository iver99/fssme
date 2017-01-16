Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    REX      01/04/2017 - created
Rem

@&EMSAAS_SQL_ROOT/1.15.0/emaas_savesearch_seed_data_ta.sql -1
@&EMSAAS_SQL_ROOT/1.15.0/emaas_savedsearch_cos_widget_update.sql -1
@&EMSAAS_SQL_ROOT/1.15.0/emaas_savedsearch_seed_data_cos.sql -1
@&EMSAAS_SQL_ROOT/1.15.0/emaas_savedsearch_seed_data_la.sql -1
COMMIT;

