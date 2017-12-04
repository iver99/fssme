Rem
Rem    DML change during upgrade
Rem    MODIFIED   (MM/DD/YY)
Rem    MIAYU   12/01/2017 - created
Rem
Rem

SET FEEDBACK ON
SET SERVEROUTPUT ON
@&EMSAAS_SQL_ROOT/1.26.0/emaas_savedsearch_seed_data_dashboard.sql
@&EMSAAS_SQL_ROOT/1.26.0/emaas_savesearch_seed_data_la.sql
COMMIT;

