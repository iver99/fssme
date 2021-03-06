Rem Copyright (c) 2013, 2014, 2015, 2016, 2017 Oracle and/or its affiliates.
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savedsearch_compress_index.sql
Rem
Rem    DESCRIPTION
Rem      compress the index of 
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    REX       08/18/17
Rem

BEGIN
  EXECUTE IMMEDIATE 'DROP TABLE EMS_ANALYTICS_RESOURCE_BUNDLE';
  DBMS_OUTPUT.PUT_LINE('EMS_ANALYTICS_RESOURCE_BUNDLE HAS BEEN DROPPED');
  EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -00942 THEN
         DBMS_OUTPUT.PUT_LINE('>>>SSF DDL ERROR<<<');
         RAISE;
      END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'CREATE TABLE EMS_ANALYTICS_RESOURCE_BUNDLE
  (
    LANGUAGE_CODE                VARCHAR(2) NOT NULL,
    COUNTRY_CODE                 VARCHAR(4) NOT NULL,
    SERVICE_NAME                 VARCHAR(255) NOT NULL,
    SERVICE_VERSION              VARCHAR(255),
    PROPERTIES_FILE              NCLOB,
    LAST_MODIFICATION_DATE       TIMESTAMP NOT NULL,
    CONSTRAINT EMS_ANALYTICS_RESOURCE_B_PK PRIMARY KEY (LANGUAGE_CODE, COUNTRY_CODE, SERVICE_NAME) USING INDEX COMPRESS
  )';
  DBMS_OUTPUT.PUT_LINE('EMS_ANALYTICS_RESOURCE_BUNDLE HAS BEEN CREATED');
  EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -00955 THEN
         DBMS_OUTPUT.PUT_LINE('>>>SSF DDL ERROR<<<');
         RAISE;
      END IF;
END;
/


