Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    GUOCHEN     04/26/2017 - created
Rem
@&EMSAAS_SQL_ROOT/1.19.0/emaas_savedsearch_seed_data_sec.sql -1
@&EMSAAS_SQL_ROOT/1.19.0/clear_up.sql
@&EMSAAS_SQL_ROOT/1.19.0/emaas_pub_tenant_onboarding.sql -11
COMMIT;


