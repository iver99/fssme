Rem
Rem    DDL change during upgrade
Rem    MODIFIED   (MM/DD/YY)
Rem    GUOCHEN   09/26/2017 - created
Rem
Rem

SET FEEDBACK ON
SET SERVEROUTPUT ON
DECLARE
  v_count     INTEGER;
BEGIN
  --add new column 'EMS_ANALYTICS_SEARCH.FEDERATION_SUPPORTED'
  SELECT COUNT(*) INTO v_count FROM user_tab_columns WHERE table_name='EMS_ANALYTICS_SEARCH' AND column_name='FEDERATION_SUPPORTED';
  IF v_count=0 THEN
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_SEARCH ADD "FEDERATION_SUPPORTED" NUMBER(2,0) DEFAULT(0) NOT NULL';
  ELSE
    DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_SEARCH.FEDERATION_SUPPORTED exists already, no change is needed');
  END IF;

  EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to run the sql for federated dashboard support due to: '||SQLERRM);
    RAISE;
END;
/



