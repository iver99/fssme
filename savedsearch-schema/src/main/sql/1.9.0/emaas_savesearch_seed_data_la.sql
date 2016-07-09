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
V_SEARCHID NUMBER(38,0);
V_SEARCH_STR CLOB;
V_TENANT NUMBER(38,0) := '&TENANT_ID';

BEGIN
V_SEARCHID := 2002;
V_SEARCH_STR := '''Target Type'' in (''Oracle Database Instance'', ''Automatic Storage Management'', ''Oracle Database Listener'', ''Oracle Clusterware'')';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';
V_SEARCHID := 2003;
V_SEARCH_STR := '''Target Type'' in (''Agent'', ''Harvester'', ''Gateway'', ''Oracle Management Service'', ''Lama'')';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';
V_SEARCHID := 2004;
V_SEARCH_STR := '''Target Type'' = ''Host (Linux)''';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';
V_SEARCHID := 2005;
V_SEARCH_STR := '''Target Type'' in (''Oracle Database Instance'', ''Automatic Storage Management'', ''Oracle Database Listener'', ''Oracle Clusterware'') and Severity in (''error'', ''severe'')  | stats count as ''Error Count'' by target | top limit=10 ''Error Count''';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';
V_SEARCHID := 2006;
V_SEARCH_STR := '''Target Type'' in (''WebLogic Server'', ''Oracle Internet Directory'', ''Oracle HTTP Server'', ''Oracle Access Management Server'', ''WebLogic Domain'')';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';  
V_SEARCHID := 2010;
V_SEARCH_STR :='''Target Type'' in (''WebLogic Server'', ''Oracle Internet Directory'',''Oracle HTTP Server'',''Oracle Access Management Server'',''WebLogic Domain'') and ''Error ID'' != ''bea-000000'' | stats count as ct by ''error id'' | top limit=10 ct';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';
--Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' in (''WebLogic Server'',''Oracle Internet Directory'',''Oracle HTTP Server'',''Oracle Access Management Server'', ''WebLogic Domain'') and ''Error ID'' != ''bea-000000'' | stats count as ''BEA-XXXXXX Count'' by ecid | top limit=10 ''BEA-XXXXXX Count''' where SEARCH_ID=2011 and TENANT_ID='&TENANT_ID';
V_SEARCHID := 2011;
V_SEARCH_STR :='''Target Type'' in (''WebLogic Server'',''Oracle Internet Directory'',''Oracle HTTP Server'',''Oracle Access Management Server'', ''WebLogic Domain'') and ''Error ID'' != ''bea-000000'' | stats count as ''BEA-XXXXXX Count'' by ecid | top limit=10 ''BEA-XXXXXX Count''';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';
--Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' in (''WebLogic Server'',''Oracle Internet Directory'',''Oracle HTTP Server'',''Oracle Access Management Server'', ''WebLogic Domain'') and Severity  in (''error'',''severe'') | stats count as ''Error Count'' by target | top limit=10 ''Error Count''' where SEARCH_ID=2012 and TENANT_ID='&TENANT_ID';
V_SEARCHID := 2012;
V_SEARCH_STR :='''Target Type'' in (''WebLogic Server'',''Oracle Internet Directory'',''Oracle HTTP Server'',''Oracle Access Management Server'', ''WebLogic Domain'') and Severity  in (''error'',''severe'') | stats count as ''Error Count'' by target | top limit=10 ''Error Count''';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';
--Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' in (''Oracle Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Oracle Clusterware'') and ''Message Group'' in (''ORA'', ''NZE'', ''EXP'', ''IMP'', ''SQL*Loader'', ''KUP'', ''UDE'', ''UDI'', ''DBV'', ''NID'', ''DGM'', ''DIA'', ''LCD'', ''OCI'', ''QSM'', ''RMAN'', ''LRM'', ''LFI'', ''PLS'', ''PLW'', ''AMD'', ''CLSR'', ''CLSS'', ''CRS'', ''EVM'', ''CLST'', ''CLSD'', ''PROC'', ''PROT'', ''TNS'', ''NNC'', ''NNO'', ''NPL'', ''NNF'', ''NMP'', ''NCR'', ''O2F'', ''O2I'', ''O2U'', ''PCB'', ''PCC'', ''SQL'', ''AUD'', ''IMG'', ''VID'', ''DRG'', ''LPX'', ''LSX'', ''PGA'', ''PGU'') or ''Message Group'' LIKE ''%NNL''' where SEARCH_ID=2013 and TENANT_ID='&TENANT_ID';
V_SEARCHID := 2013;
V_SEARCH_STR :='''Target Type'' in (''Oracle Database Instance'', ''Automatic Storage Management'', ''Oracle Database Listener'', ''Oracle Clusterware'') and ''Message Group'' in (''ORA'', ''NZE'', ''EXP'', ''IMP'', ''SQL*Loader'', ''KUP'', ''UDE'', ''UDI'', ''DBV'', ''NID'', ''DGM'', ''DIA'', ''LCD'', ''OCI'', ''QSM'', ''RMAN'', ''LRM'', ''LFI'', ''PLS'', ''PLW'', ''AMD'', ''CLSR'', ''CLSS'', ''CRS'', ''EVM'', ''CLST'', ''CLSD'', ''PROC'', ''PROT'', ''TNS'', ''NNC'', ''NNO'', ''NPL'', ''NNF'', ''NMP'', ''NCR'', ''O2F'', ''O2I'', ''O2U'', ''PCB'', ''PCC'', ''SQL'', ''AUD'', ''IMG'', ''VID'', ''DRG'', ''LPX'', ''LSX'', ''PGA'', ''PGU'') or ''Message Group'' LIKE ''%NNL''';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';
--Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' = ''Host (Linux)'' and (''failed password for'' or ''authentication failure'')' where SEARCH_ID=2014 and TENANT_ID='&TENANT_ID';
V_SEARCHID := 2014;
V_SEARCH_STR :='''Target Type'' = ''Host (Linux)'' and (''failed password for'' or ''authentication failure'')';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';  
--Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' in (''Oracle Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Oracle Clusterware'') and ''Message Group'' in ( ''ORA'',''NZE'',''EXP'', ''IMP'',''SQL*Loader'', ''KUP'',''UDE'', ''UDI'',''DBV'', ''NID'', ''DGM'',''DIA'',''LCD'',''OCI'', ''QSM'', ''RMAN'',''LRM'',''LFI'',''PLS'', ''PLW'',''AMD'',''CLSR'',''CLSS'',''CRS'' ,''EVM'', ''CLST'',''CLSD'',''PROC'', ''PROT'',''TNS'',''NNC'',''NNO'',''NPL'', ''NNF'', ''NMP'',''NCR'', ''O2F'', ''O2I'',''O2U'',''PCB'',''PCC'',''SQL'',''AUD'',''IMG'', ''VID'', ''DRG'',''LPX'',''LSX'',''PGA'', ''PGU'') or ''Message Group'' like ''%NNL''  | stats count as ''Errors'' by ''Message ID'' | top limit=10 ''Errors''' where SEARCH_ID=2015 and TENANT_ID='&TENANT_ID';
V_SEARCHID := 2015;
V_SEARCH_STR :='''Target Type'' in (''Oracle Database Instance'', ''Automatic Storage Management'', ''Oracle Database Listener'', ''Oracle Clusterware'') and ''Message Group'' in ( ''ORA'',''NZE'',''EXP'', ''IMP'',''SQL*Loader'', ''KUP'',''UDE'', ''UDI'',''DBV'', ''NID'', ''DGM'',''DIA'',''LCD'',''OCI'', ''QSM'', ''RMAN'',''LRM'',''LFI'',''PLS'', ''PLW'',''AMD'',''CLSR'',''CLSS'',''CRS'' ,''EVM'', ''CLST'',''CLSD'',''PROC'', ''PROT'',''TNS'',''NNC'',''NNO'',''NPL'', ''NNF'', ''NMP'',''NCR'', ''O2F'', ''O2I'',''O2U'',''PCB'',''PCC'',''SQL'',''AUD'',''IMG'', ''VID'', ''DRG'',''LPX'',''LSX'',''PGA'', ''PGU'') or ''Message Group'' like ''%NNL''  | stats count as ''Errors'' by ''Message ID'' | top limit=10 ''Errors''';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';   
--Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' in (''Oracle Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Oracle Clusterware'') and ''Error ID'' in ( ''ORA-00600'',''ORA-00603'',''ORA-07445'', ''ORA-04030'',''ORA-04031'',''ORA-00227'',''ORA-00239'', ''ORA-00240'',''ORA-00255'',''ORA-00353'', ''ORA-00355'',''ORA-00356'',''ORA-00445'',''ORA-00494'',''ORA-01578'', ''OCI-03106'',''ORA-03113'',''OCI-03113%'', ''OCI-03135'', ''ORA-03137'',''ORA-04036'',''ORA-24982'',''ORA-25319'', ''ORA-29740'',''ORA-29770'', ''ORA-29771'', ''ORA-32701'',''ORA-32703'',''ORA-32704'',''ORA-06512'', ''ORA-15173'' ) | stats count as ''Critical Errors'' by ''Target Type''' where SEARCH_ID=2016 and TENANT_ID='&TENANT_ID';
V_SEARCHID := 2016;
V_SEARCH_STR :='''Target Type'' in (''Oracle Database Instance'', ''Automatic Storage Management'', ''Oracle Database Listener'', ''Oracle Clusterware'') and ''Error ID'' in ( ''ORA-00600'',''ORA-00603'',''ORA-07445'', ''ORA-04030'',''ORA-04031'',''ORA-00227'',''ORA-00239'', ''ORA-00240'',''ORA-00255'',''ORA-00353'', ''ORA-00355'',''ORA-00356'',''ORA-00445'',''ORA-00494'',''ORA-01578'', ''OCI-03106'',''ORA-03113'',''OCI-03113%'', ''OCI-03135'', ''ORA-03137'',''ORA-04036'',''ORA-24982'',''ORA-25319'', ''ORA-29740'',''ORA-29770'', ''ORA-29771'', ''ORA-32701'',''ORA-32703'',''ORA-32704'',''ORA-06512'', ''ORA-15173'' ) | stats count as ''Critical Errors'' by ''Target Type''';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';   
--Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' = ''Host (Linux)'' | stats count as "Log Entries" by Target | top limit=10 "Log Entries"' where SEARCH_ID=2019 and TENANT_ID='&TENANT_ID';
V_SEARCHID := 2019;
V_SEARCH_STR :='''Target Type'' = ''Host (Linux)'' | stats count as "Log Entries" by Target | top limit=10 "Log Entries"';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID'; 
--Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' = ''Host (Linux)'' | stats count as "Log Entries" by ''Log Source'' | top limit = 10 "Log Entries"' where SEARCH_ID=2020 and TENANT_ID='&TENANT_ID';
V_SEARCHID := 2020;
V_SEARCH_STR :='''Target Type'' = ''Host (Linux)'' | stats count as "Log Entries" by ''Log Source'' | top limit = 10 "Log Entries"';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';
--Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' = ''Host (Linux)'' and (''invalid user'' or ''user unknown'')' where SEARCH_ID=2023 and TENANT_ID='&TENANT_ID';
V_SEARCHID := 2023;
V_SEARCH_STR :='''Target Type'' = ''Host (Linux)'' and (''invalid user'' or ''user unknown'')';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID';
--Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR='''Target Type'' = ''Host (Linux)'' | stats count as ''Log Entries'' BY service | top limit=10 ''Log Entries''' where SEARCH_ID=2026 and TENANT_ID='&TENANT_ID';
V_SEARCHID := 2026;
V_SEARCH_STR :='''Target Type'' = ''Host (Linux)'' | stats count as ''Log Entries'' BY service | top limit=10 ''Log Entries''';
Update EMS_ANALYTICS_SEARCH set SEARCH_DISPLAY_STR=V_SEARCH_STR WHERE SEARCH_ID=V_SEARCHID and V_TENANT='&TENANT_ID'; 
  
  commit;
  DBMS_OUTPUT.PUT_LINE('Query strings of LA OOB widgets have been updated to cut over to greenfield target model.');

EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('Failed to update Query string of LA OOB widgets for greenfield migration due to '||SQLERRM);   
  RAISE;  
END;
/