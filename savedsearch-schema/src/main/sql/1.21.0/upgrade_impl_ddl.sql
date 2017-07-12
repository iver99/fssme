Rem
Rem    DDL change during upgrade
Rem    MODIFIED   (MM/DD/YY)
Rem    XIADAI    06/01/2017 - created
Rem
Rem

@&EMSAAS_SQL_ROOT/1.21.0/emaas_remove_table_constraint.sql
@&EMSAAS_SQL_ROOT/1.21.0/create_table_resource_bundle.sql

@&EMSAAS_SQL_ROOT/1.21.0/create_ems_search_params_idx.sql
