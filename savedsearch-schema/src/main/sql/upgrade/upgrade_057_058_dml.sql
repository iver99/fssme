Rem
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      upgrade_057_058_dml.sql
Rem
Rem    DESCRIPTION
Rem      Upgrade file for upgrading from v0.5.7 to v0.5.8 
Rem
Rem    NOTES
Rem      This file will get called via generic upgrade.sql - upgrade.sql gets called as part of running SSF deployment recipe
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    miayu		10/11/14 - upgrade OOB Folder Searches from 0.5.7 to 0.5.8  
Rem

DEFINE TENANT_ID ='&1'

Rem upgrade APMCS OOB Folders & Searches
@./upgrade_057_058_apm_dml.sql &TENANT_ID
