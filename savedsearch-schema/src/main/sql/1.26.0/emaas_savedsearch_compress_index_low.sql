Rem Copyright (c) 2013, 2014, 2015, 2016, 2017 Oracle and/or its affiliates.
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savedsearch_compress_index_low.sql
Rem
Rem    DESCRIPTION
Rem      compress the index with COMPRESS ADVANCED LOW
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    REX       10/28/17
Rem

DECLARE
  v_count_index        INTEGER;
  v_count_compression  INTEGER;
  v_table_name         VARCHAR2(64);
  v_index_name         VARCHAR2(64);
  const_compression    CONSTANT    VARCHAR2(16) := 'ADVANCED LOW';
BEGIN
  -- EMS_ANALYTICS_SCHEMA_VER_SSF
  v_table_name := 'EMS_ANALYTICS_SCHEMA_VER_SSF';
  v_index_name := 'EMS_ANALYTICS_SCHEMA_VER_PK';
  SELECT COUNT(1) INTO v_count_index from user_indexes where table_name = v_table_name and index_name = v_index_name;
  SELECT COUNT(1) INTO v_count_compression from user_indexes where table_name = v_table_name and index_name = v_index_name and compression = const_compression;
  IF v_count_index=0 THEN
    DBMS_OUTPUT.PUT_LINE('index EMS_ANALYTICS_SCHEMA_VER_PK does not exist');
  ELSIF v_count_compression=1 THEN
    DBMS_OUTPUT.PUT_LINE('EMS_ANALYTICS_SCHEMA_VER_PK has alredy been built with COMPRESS ADVANCED LOW');
  ELSE
    EXECUTE IMMEDIATE 'ALTER index EMS_ANALYTICS_SCHEMA_VER_PK rebuild COMPRESS ADVANCED LOW';
    DBMS_OUTPUT.PUT_LINE('EMS_ANALYTICS_SCHEMA_VER_PK is rebuilt with COMPRESS ADVANCED LOW successfully!');
  END IF;
  
  -- EMS_ANALYTICS_RESOURCE_BUNDLE
  v_table_name := 'EMS_ANALYTICS_RESOURCE_BUNDLE';
  v_index_name := 'EMS_ANALYTICS_RESOURCE_B_PK';
  SELECT COUNT(1) INTO v_count_index from user_indexes where table_name = v_table_name and index_name = v_index_name;
  SELECT COUNT(1) INTO v_count_compression from user_indexes where table_name = v_table_name and index_name = v_index_name and compression = const_compression;
  IF v_count_index=0 THEN
    DBMS_OUTPUT.PUT_LINE('index EMS_ANALYTICS_RESOURCE_B_PK does not exist');
  ELSIF v_count_compression=1 THEN
    DBMS_OUTPUT.PUT_LINE('EMS_ANALYTICS_RESOURCE_B_PK has alredy been built with COMPRESS ADVANCED LOW');
  ELSE
    EXECUTE IMMEDIATE 'ALTER index EMS_ANALYTICS_RESOURCE_B_PK rebuild COMPRESS ADVANCED LOW';
    DBMS_OUTPUT.PUT_LINE('EMS_ANALYTICS_RESOURCE_B_PK is rebuilt with COMPRESS ADVANCED LOW successfully!');
  END IF;
    
  DBMS_OUTPUT.PUT_LINE('compress index with ADVANCED LOW');
    
  EXCEPTION
  WHEN OTHERS THEN
  DBMS_OUTPUT.PUT_LINE('>>>SSF DDL ERROR<<<');
  DBMS_OUTPUT.PUT_LINE('Failed to compress index with ADVANCED LOW: ' || SQLERRM);
  RAISE;
END;
/



