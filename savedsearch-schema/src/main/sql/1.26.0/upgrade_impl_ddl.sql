Rem
Rem    DDL change during upgrade
Rem    MODIFIED   (MM/DD/YY)
Rem    REX   11/21/2017 - created
Rem
Rem

SET FEEDBACK ON
SET SERVEROUTPUT ON

@&EMSAAS_SQL_ROOT/1.26.0/emaas_savedsearch_rearrange_index.sql
@&EMSAAS_SQL_ROOT/1.26.0/emaas_savedsearch_compress_index_low.sql

