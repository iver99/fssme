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
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_SEARCH ADD "FEDERATION_SUPPORTED" NUMBER(1,0) DEFAULT(0) NOT NULL';
  ELSE
    DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_SEARCH.FEDERATION_SUPPORTED exists already, no change is needed');
  END IF;

  EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('>>>SSF DDL ERROR<<<');
    DBMS_OUTPUT.PUT_LINE('Failed to run the sql for federated dashboard support due to: '||SQLERRM);
    RAISE;
END;
/

DECLARE
  v_count_index        INTEGER;
  v_count_position     INTEGER;
BEGIN
  --OMCSQ048: Indexes with a tenant_id key column must list it first
  SELECT COUNT(1) INTO v_count_index FROM user_constraints WHERE constraint_name = 'EMS_ANALYTICS_LAST_ACCESS_PK' AND table_name = 'EMS_ANALYTICS_LAST_ACCESS';
  SELECT COUNT(1) INTO v_count_position FROM all_ind_columns WHERE INDEX_NAME = 'EMS_ANALYTICS_LAST_ACCESS_PK' AND COLUMN_NAME = 'TENANT_ID' AND COLUMN_POSITION = 1;
  IF v_count_index=0 THEN
    DBMS_OUTPUT.PUT_LINE('constraint EMS_ANALYTICS_LAST_ACCESS_PK does not exist');
  ELSIF  v_count_position=1 THEN
    DBMS_OUTPUT.PUT_LINE('TENANT_ID has been already listed first in EMS_ANALYTICS_LAST_ACCESS_PK');
  ELSE
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_LAST_ACCESS DROP CONSTRAINT EMS_ANALYTICS_LAST_ACCESS_PK';
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_LAST_ACCESS ADD CONSTRAINT EMS_ANALYTICS_LAST_ACCESS_PK PRIMARY KEY(TENANT_ID,OBJECT_ID,ACCESSED_BY,OBJECT_TYPE)';
    DBMS_OUTPUT.PUT_LINE('TENANT_ID is listed first in EMS_ANALYTICS_LAST_ACCESS_PK successfully!');
  END IF;
  EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('>>>SSF DDL ERROR<<<');
    DBMS_OUTPUT.PUT_LINE('Failed to rebuild index due to error '||SQLERRM);
    RAISE;
END;
/

