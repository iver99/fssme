Rem
Rem    DDL change during upgrade
Rem    MODIFIED   (MM/DD/YY)
Rem    MIAYU     02/11/2017

--/**
--Add deleted columns back, once for all, see EMCPDF-3333/EMCPSSF-365
--*/
@&EMSAAS_SQL_ROOT/1.10.0/emaas_insert_column.sql
@&EMSAAS_SQL_ROOT/1.12.0/emaas_savesearch_add_column.sql

