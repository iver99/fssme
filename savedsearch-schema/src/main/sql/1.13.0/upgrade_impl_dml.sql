Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    REX      11/14/2016 - created
Rem

@&EMSAAS_SQL_ROOT/1.13.0/emaas_savesearch_seed_data.sql -1
@&EMSAAS_SQL_ROOT/1.13.0/emaas_savesearch_seed_data_targetcard.sql -1
@&EMSAAS_SQL_ROOT/1.13.0/emaas_savesearch_cos_widget_update.sql -1
COMMIT;

