Rem
Rem    DDL change during upgrade
Rem    MODIFIED   (MM/DD/YY)
Rem    REX    08/18/2017 - created
Rem
Rem


@&EMSAAS_SQL_ROOT/1.23.0/emaas_savedsearch_drop_fk.sql
@&EMSAAS_SQL_ROOT/1.23.0/emaas_savedsearch_compress_index.sql

SET FEEDBACK ON
SET SERVEROUTPUT ON
DECLARE
  v_count     INTEGER;
BEGIN
  --add new column 'EMS_ANALYTICS_SEARCH.FEDERATION_SUPPORTED'
  SELECT COUNT(*) INTO v_count FROM user_tab_columns WHERE table_name='EMS_ANALYTICS_SEARCH' AND column_name='FEDERATION_SUPPORTED';
  IF v_count=0 THEN
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_SEARCH ADD "FEDERATION_SUPPORTED" NUMBER(1,0) DEFAULT(0) NOT NULL';
  ELSE
    DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_SEARCH.FEDERATION_SUPPORTED exists already, no change is needed');
  END IF;

  --add new column 'EMS_ANALYTICS_SEARCH.GREENFIELD_SUPPORTED'
  SELECT COUNT(*) INTO v_count FROM user_tab_columns WHERE table_name='EMS_ANALYTICS_SEARCH' AND column_name='GREENFIELD_SUPPORTED';
  IF v_count=0 THEN
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_SEARCH ADD "GREENFIELD_SUPPORTED" NUMBER(1,0) DEFAULT(1) NOT NULL';
  ELSE
    DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_SEARCH.GREENFIELD_SUPPORTED exists already, no change is needed');
  END IF;

  EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to run the sql for federated dashboard support due to: '||SQLERRM);
    RAISE;
END;
/



