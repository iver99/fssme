Rem 
Rem
Rem emaas_tenant_onboarding.sql
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_tenant_onboarding.sql
Rem
Rem    DESCRIPTION
Rem      Tenant onboarding file for Widgets Frameowrk - tenantid is passed by RM & we call seed data with 
Rem      that tenant id & data gets inserted for that tenant
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem 	sdhamdhe  8/26/2015     Made changes for LCM rep manager cut-over
Rem    vinjoshi   1/23/1015 	Created
     
WHENEVER SQLERROR EXIT ROLLBACK
SET FEEDBACK ON
SET SERVEROUTPUT ON
SET VERIFY OFF
SET ECHO OFF
DEFINE  TENANT_ID = '&1'
DEFINE EMSAAS_SQL_ROOT = '&2'

DECLARE	
@&EMSAAS_SQL_ROOT/1.19.0/emaas_pub_tenant_onboarding.sql -11 &EMSAAS_SQL_ROOT
/**
--PLEASE READ!!
--NO DDL IS ALLOWED in this file!! See EMCPDF-3333/EMCPSSF-465
**/

BEGIN
  DBMS_OUTPUT.PUT_LINE('Inserting OOB searches for &TENANT_ID is completed');
END;
/
