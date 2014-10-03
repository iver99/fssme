Rem
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      upgrade_056_057_dml.sql
Rem
Rem    DESCRIPTION
Rem      Upgrade file for upgrading from v0.5.6 to v0.5.7 
Rem
Rem    NOTES
Rem      This file will get called via generic upgrade.sql - upgrade.sql gets called as part of running SSF deployment recipe
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    miayu		09/30/14 - upgrade TA OOB Searches 
Rem    sdhamdhe		09/18/14 - placeholder  
Rem

Rem upgrade Target Analytics OOB Searches
@./upgrade_056_057_ta_dml.sql
