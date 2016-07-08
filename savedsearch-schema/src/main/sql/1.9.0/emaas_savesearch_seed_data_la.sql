Rem
Rem emaas_savesearch_seed_data_la.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data_la.sql 
Rem
Rem    DESCRIPTION
Rem      Seed data for LA
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    miayu       11/28/15 - fix emcpssf-174
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE 

BEGIN

  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' IN (''Oracle Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'')' where SEARCH_ID=2002 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' IN (''Agent'', ''Harvester'', ''Gateway'', ''Oracle Management Service'', ''Lama'')' where SEARCH_ID=2003 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' IN (''Host (Linux)'')' where SEARCH_ID=2004 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' IN (''Oracle Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'') AND Severity IN (''error'', ''severe'')  |stats count as ''Error Count'' by target |top limit=10 ''Error Count''' where SEARCH_ID=2005 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' IN (''WebLogic Server'', ''Oracle Internet Directory'', ''Oracle HTTP Server'', ''Oracle Access Management Server'', ''WebLogic Domain'')' where SEARCH_ID=2006 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' in (''WebLogic Server'', ''Oracle Internet Directory'',''Oracle HTTP Server'',''Oracle Access Management Server'',''WebLogic Domain'') AND ''Error ID'' != ''bea-000000'' |stats count as cnt by ''error id'' |top limit=10 cnt' where SEARCH_ID=2010 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' in (''WebLogic Server'',''Oracle Internet Directory'',''Oracle HTTP Server'',''Oracle Access Management Server'', ''WebLogic Domain'') AND ''Error ID'' != ''bea-000000'' |stats count as ''BEA-XXXXXX Count'' by ecid |top limit=10 ''BEA-XXXXXX Count''' where SEARCH_ID=2011 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''target type'' in (''WebLogic Server'',''Oracle Internet Directory'',''Oracle HTTP Server'',''Oracle Access Management Server'', ''WebLogic Domain'') AND severity  in (''error'',''severe'') |stats count as ''Error Count'' by target |top limit=10 ''Error Count''' where SEARCH_ID=2012 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' IN (''Oracle Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'') AND ''Message Group'' IN (''ORA'', ''NZE'', ''EXP'', ''IMP'', ''SQL*Loader'', ''KUP'', ''UDE'', ''UDI'', ''DBV'', ''NID'', ''DGM'', ''DIA'', ''LCD'', ''OCI'', ''QSM'', ''RMAN'', ''LRM'', ''LFI'', ''PLS'', ''PLW'', ''AMD'', ''CLSR'', ''CLSS'', ''CRS'', ''EVM'', ''CLST'', ''CLSD'', ''PROC'', ''PROT'', ''TNS'', ''NNC'', ''NNO'', ''NPL'', ''NNF'', ''NMP'', ''NCR'', ''O2F'', ''O2I'', ''O2U'', ''PCB'', ''PCC'', ''SQL'', ''AUD'', ''IMG'', ''VID'', ''DRG'', ''LPX'', ''LSX'', ''PGA'', ''PGU'') OR ''Message Group'' LIKE ''%NNL''' where SEARCH_ID=2013 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' = ''Host (Linux)'' AND (''failed password for'' OR ''authentication failure'')' where SEARCH_ID=2014 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' IN (''Oracle Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'') AND ''Message Group'' in ( ''ORA'',''NZE'',''EXP'', ''IMP'',''SQL*Loader'', ''KUP'',''UDE'', ''UDI'',''DBV'', ''NID'', ''DGM'',''DIA'',''LCD'',''OCI'', ''QSM'', ''RMAN'',''LRM'',''LFI'',''PLS'', ''PLW'',''AMD'',''CLSR'',''CLSS'',''CRS'' ,''EVM'', ''CLST'',''CLSD'',''PROC'', ''PROT'',''TNS'',''NNC'',''NNO'',''NPL'', ''NNF'', ''NMP'',''NCR'', ''O2F'', ''O2I'',''O2U'',''PCB'',''PCC'',''SQL'',''AUD'',''IMG'', ''VID'', ''DRG'',''LPX'',''LSX'',''PGA'', ''PGU'') OR ''Message Group'' like ''%NNL''  |stats count as ''Errors'' by ''Message ID'' |top limit=10 ''Errors''' where SEARCH_ID=2015 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' IN (''Oracle Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'') and ''Error ID'' IN ( ''ORA-00600'',''ORA-00603'',''ORA-07445'', ''ORA-04030'',''ORA-04031'',''ORA-00227'',''ORA-00239'', ''ORA-00240'',''ORA-00255'',''ORA-00353'', ''ORA-00355'',''ORA-00356'',''ORA-00445'',''ORA-00494'',''ORA-01578'', ''OCI-03106'',''ORA-03113'',''OCI-03113%'', ''OCI-03135'', ''ORA-03137'',''ORA-04036'',''ORA-24982'',''ORA-25319'', ''ORA-29740'',''ORA-29770'', ''ORA-29771'', ''ORA-32701'',''ORA-32703'',''ORA-32704'',''ORA-06512'', ''ORA-15173'' ) |stats count as ''Critical Errors'' by ''Target Type''' where SEARCH_ID=2016 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' = ''Host (Linux)'' | STATS COUNT AS "Log Entries" BY Target | TOP LIMIT=10 "Log Entries"' where SEARCH_ID=2019 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' = ''Host (Linux)'' | stats count as "Log Entries" by ''Log Source'' | top limit = 10 "Log Entries"' where SEARCH_ID=2020 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' = ''Host (Linux)'' AND (''invalid user'' OR ''user unknown'')' where SEARCH_ID=2023 and TENANT_ID='&TENANT_ID';
  Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' = ''Host (Linux)'' | STATS COUNT AS ''Log Entries'' BY SERVICE | TOP LIMIT=10 ''Log Entries''' where SEARCH_ID=2026 and TENANT_ID='&TENANT_ID';

  commit;
  DBMS_OUTPUT.PUT_LINE('Query strings of LA OOB widgets have been updated to cut over to greenfield target model.');  
  
EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('Failed to update Query string of LA OOB widgets for greenfield migration due to '||SQLERRM);   
  RAISE;  
END;
/
