Rem
Rem emaas_savesearch_seed_data_la.sql
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data_la.sql 
Rem
Rem    DESCRIPTION
Rem      Seed data for Log Analytics
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    miayu          05/28/15 - add another 13 LA OOB widgets and set default time range of all LA OOB widgets to "Last 60 Minutes"
Rem    miayu          04/21/15 - move OOB folder for LA to emaas_savesearch_seed_data.sql. Only OOB SEARCHES are expected to be in this file
Rem    jerrusse       08/21/14 - Created for Log Analytics 
Rem		 sdhamdhe				03/25/15 - Removed OOB searches for all of LA as requested by Jerry


DEFINE TENANT_ID = '&1'

--OOB Searches
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2000,'Top Log Sources','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Top Log Sources based on number of log entries collected.',2,1,null,null,null,null,1,null,0,'* | STATS COUNT AS cnt BY ''Log Source'' | TOP LIMIT = 10 cnt',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2000
--,'VISUALIZATION_TYPE_KEY',null,1,'BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2000,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2000,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganBarChart.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2000,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2000,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
var SEARCH_ID                             NUMBER;
var	NAME								  VARCHAR2;
var OWNER                                 VARCHAR2;
var CREATION_DATE                         VARCHAR2;
var LAST_MODIFICATION_DATE                VARCHAR2;
var LAST_MODIFIED_BY                      VARCHAR2;
var DESCRIPTION                           VARCHAR2;
var FOLDER_ID                             NUMBER;
var CATEGORY_ID                           NUMBER;
var NAME_NLSID                            VARCHAR2;
var NAME_SUBSYSTEM                        VARCHAR2;
var DESCRIPTION_NLSID                     VARCHAR2;
var DESCRIPTION_SUBSYSTEM                 VARCHAR2;
var SYSTEM_SEARCH                         NUMBER;
var EM_PLUGIN_ID                          VARCHAR2;
var IS_LOCKED                             NUMBER;
var SEARCH_DISPLAY_STR                    CLOB;
var UI_HIDDEN                             NUMBER;
var DELETED                               NUMBER;
var IS_WIDGET                             NUMBER;
var TENANT_ID                             NUMBER;


exec :SEARCH_ID                          	:=2000;
exec :NAME                                  :='Top Log Sources';
exec :OWNER                 				:='ORACLE';
exec :CREATION_DATE         				:=SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:='ORACLE';
exec :DESCRIPTION           				:='Top Log Sources based on number of log entries collected.';
exec :FOLDER_ID             				:=2;
exec :CATEGORY_ID           				:=1;
exec :NAME_NLSID            				:=null;
exec :NAME_SUBSYSTEM        				:=null;
exec :DESCRIPTION_NLSID     				:=null;
exec :DESCRIPTION_SUBSYSTEM 				:=null;
exec :SYSTEM_SEARCH         				:=1;
exec :EM_PLUGIN_ID          				:=null;
exec :IS_LOCKED             				:=0;
exec :SEARCH_DISPLAY_STR    				:='* | STATS COUNT AS cnt BY ''Log Source'' | TOP LIMIT = 10 cnt';
exec :UI_HIDDEN             				:=0;
exec :DELETED               				:=0;
exec :IS_WIDGET             				:=1;
exec :TENANT_ID             				:='&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

var    SEARCH_ID               NUMBER;
var    NAME                    VARCHAR2;
var    PARAM_ATTRIBUTES        VARCHAR2;
var    PARAM_TYPE              NUMBER;
var    PARAM_VALUE_STR         VARCHAR2;
var    PARAM_VALUE_CLOB        NCLOB;
var    TENANT_ID               NUMBER;

exec :SEARCH_ID             :=2000;
exec :NAME                  :='WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=null;
exec :PARAM_TYPE            :=1;
exec :PARAM_VALUE_STR       :='LA_WIDGET_BAR';
exec :PARAM_VALUE_CLOB      :=null;
exec :TENANT_ID             :='&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             := 2000;
exec :NAME                  := 'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      := null;
exec :PARAM_TYPE            := 1;
exec :PARAM_VALUE_STR       := '/html/search/widgets/loganBarChart.html';
exec :PARAM_VALUE_CLOB      := null;
exec :TENANT_ID             := '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
exec :SEARCH_ID             :=  2000;
exec :NAME                  :=  'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2000;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative","duration":"60","timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2001,'All Logs Trend','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Time-based histogram of all log entries from all sources.',2,1,null,null,null,null,1,null,0,'*',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2001,'VISUALIZATION_TYPE_KEY',null,1,'HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2001,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2001,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganHistogram.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2001,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/results/loganSearchChartsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2001,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
exec :SEARCH_ID                          	:= 2001;
exec :NAME                                  := 'All Logs Trend';
exec :OWNER                 				:= 'ORACLE';
exec :CREATION_DATE         				:= SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:= SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:= 'ORACLE';
exec :DESCRIPTION           				:= 'Time-based histogram of all log entries from all sources.';
exec :FOLDER_ID             				:= 2;
exec :CATEGORY_ID           				:= 1;
exec :NAME_NLSID            				:= null;
exec :NAME_SUBSYSTEM        				:= null;
exec :DESCRIPTION_NLSID     				:= null;
exec :DESCRIPTION_SUBSYSTEM 				:= null;
exec :SYSTEM_SEARCH         				:= 1;
exec :EM_PLUGIN_ID          				:= null;
exec :IS_LOCKED             				:= 0;
exec :SEARCH_DISPLAY_STR    				:= '*';
exec :UI_HIDDEN             				:= 0;
exec :DELETED               				:= 0;
exec :IS_WIDGET             				:= 1;
exec :TENANT_ID             				:= '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);


exec :SEARCH_ID             :=   2001;
exec :NAME                  :=  'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  'HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=   2001;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2001;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganHistogram.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2001;
exec :NAME                  :=    'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '/js/viewmodel/search/results/loganSearchChartsViewModel.js';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=     2001;
exec :NAME                  :=     'time';
exec :PARAM_ATTRIBUTES      :=     null;
exec :PARAM_TYPE            :=     1;
exec :PARAM_VALUE_STR       :=     '{"type":"relative","duration":"60","timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=     null;
exec :TENANT_ID             :=     '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2002,'Database Log Trends','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Time-based histogram of all log entries from Database targets.',2,1,null,null,null,null,1,null,0,'''Target Type'' IN (''Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'')',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2002,'VISUALIZATION_TYPE_KEY',null,1,'HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2002,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2002,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganHistogram.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2002,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/results/loganSearchChartsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2002,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=  2002;
exec :NAME                                  :=  'Database Log Trends';
exec :OWNER                 				:=  'ORACLE';
exec :CREATION_DATE         				:=  SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=  SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=  'ORACLE';
exec :DESCRIPTION           				:=  'Time-based histogram of all log entries from Database targets.';
exec :FOLDER_ID             				:=  2;
exec :CATEGORY_ID           				:=  1;
exec :NAME_NLSID            				:=  null;
exec :NAME_SUBSYSTEM        				:=  null;
exec :DESCRIPTION_NLSID     				:=  null;
exec :DESCRIPTION_SUBSYSTEM 				:=  null;
exec :SYSTEM_SEARCH         				:=  1;
exec :EM_PLUGIN_ID          				:=  null;
exec :IS_LOCKED             				:=  0;
exec :SEARCH_DISPLAY_STR    				:=  '''Target Type'' IN (''Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'')';
exec :UI_HIDDEN             				:=  0;
exec :DELETED               				:=  0;
exec :IS_WIDGET             				:=  1;
exec :TENANT_ID             				:=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=    2002;
exec :NAME                  :=    'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
exec :SEARCH_ID             :=     2002;
exec :NAME                  :=     'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=     null;
exec :PARAM_TYPE            :=     1;
exec :PARAM_VALUE_STR       :=     'LA_WIDGET_HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=     null;
exec :TENANT_ID             :=     '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2002;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganHistogram.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
exec :SEARCH_ID             :=  2002;
exec :NAME                  :=  'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  '/js/viewmodel/search/results/loganSearchChartsViewModel.js';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2002;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2003,'Enterprise Manager Logs Trend','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Time-based histogram of all log entries from Enterprise Manager targets',2,1,null,null,null,null,1,null,0,'''Target Type'' IN (''Agent'', ''Harvester'', ''Gateway'', ''Oracle Management Service'', ''Lama'')',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2003,'VISUALIZATION_TYPE_KEY',null,1,'HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2003,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2003,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganHistogram.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2003,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/results/loganSearchChartsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2003,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');

exec :SEARCH_ID                          	:=   2003;
exec :NAME                                  :=   'Enterprise Manager Logs Trend';
exec :OWNER                 				:=   'ORACLE';
exec :CREATION_DATE         				:=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=   'ORACLE';
exec :DESCRIPTION           				:=   'Time-based histogram of all log entries from Enterprise Manager targets';
exec :FOLDER_ID             				:=   2;
exec :CATEGORY_ID           				:=   1;
exec :NAME_NLSID            				:=   null;
exec :NAME_SUBSYSTEM        				:=   null;
exec :DESCRIPTION_NLSID     				:=   null;
exec :DESCRIPTION_SUBSYSTEM 				:=   null;
exec :SYSTEM_SEARCH         				:=   1;
exec :EM_PLUGIN_ID          				:=   null;
exec :IS_LOCKED             				:=   0;
exec :SEARCH_DISPLAY_STR    				:=   '''Target Type'' IN (''Agent'', ''Harvester'', ''Gateway'', ''Oracle Management Service'', ''Lama'')';
exec :UI_HIDDEN             				:=    0;
exec :DELETED               				:=    0;
exec :IS_WIDGET             				:=    1;
exec :TENANT_ID             				:=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=    2003;
exec :NAME                  :=    'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2003;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2003;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganHistogram.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=   2003;
exec :NAME                  :=   'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/js/viewmodel/search/results/loganSearchChartsViewModel.js';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2003;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative","duration":"60","timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2004,'Host Logs Trend','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Time-based histogram of all log entries from Host targets',2,1,null,null,null,null,1,null,0,'''Target Type'' IN (''Host'')',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2004,'VISUALIZATION_TYPE_KEY',null,1,'HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2004,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2004,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganHistogram.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2004,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/results/loganSearchChartsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2004,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=    2004;
exec :NAME                                  :=    'Host Logs Trend';
exec :OWNER                 				:=    'ORACLE';
exec :CREATION_DATE         				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=    'ORACLE';
exec :DESCRIPTION           				:=    'Time-based histogram of all log entries from Host targets';
exec :FOLDER_ID             				:=    2;
exec :CATEGORY_ID           				:=    1;
exec :NAME_NLSID            				:=    null;
exec :NAME_SUBSYSTEM        				:=    null;
exec :DESCRIPTION_NLSID     				:=    null;
exec :DESCRIPTION_SUBSYSTEM 				:=    null;
exec :SYSTEM_SEARCH         				:=    1;
exec :EM_PLUGIN_ID          				:=    null;
exec :IS_LOCKED             				:=    0;
exec :SEARCH_DISPLAY_STR    				:=    '''Target Type'' IN (''Host'')';
exec :UI_HIDDEN             				:=    0;
exec :DELETED               				:=    0;
exec :IS_WIDGET             				:=    1;
exec :TENANT_ID             				:=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=    2004;
exec :NAME                  :=    'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2004;
exec :NAME                  :=    'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'LA_WIDGET_HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2004;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganHistogram.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2004;
exec :NAME                  :=    'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '/js/viewmodel/search/results/loganSearchChartsViewModel.js';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2004;
exec :NAME                  :=    'time';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '{"type":"relative","duration":"60","timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=     null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2005,'Top Database Targets with Log Errors','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Top 10 Database targets that have the most log entries with ERROR or SEVERE severity.',2,1,null,null,null,null,1,null,0,'''Target Type'' IN (''Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'') AND Severity IN (''error'', ''ERROR'', ''SEVERE'', ''severe'')  |stats count as ''Error Count'' by target |top limit=10 ''Error Count''',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2005,'VISUALIZATION_TYPE_KEY',null,1,'PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2005,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2005,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganPieChart.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2005,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2005,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=    2005;
exec :NAME                                  :=    'Top Database Targets with Log Errors';
exec :OWNER                 				:=    'ORACLE';
exec :CREATION_DATE         				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=    'ORACLE';
exec :DESCRIPTION           				:=    'Top 10 Database targets that have the most log entries with ERROR or SEVERE severity.';
exec :FOLDER_ID             				:=    2;
exec :CATEGORY_ID           				:=    1;
exec :NAME_NLSID            				:=    null;
exec :NAME_SUBSYSTEM        				:=    null;
exec :DESCRIPTION_NLSID     				:=    null;
exec :DESCRIPTION_SUBSYSTEM 				:=    null;
exec :SYSTEM_SEARCH         				:=    1;
exec :EM_PLUGIN_ID          				:=    null;
exec :IS_LOCKED             				:=    0;
exec :SEARCH_DISPLAY_STR    				:=    '''Target Type'' IN (''Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'') AND Severity IN (''error'', ''ERROR'', ''SEVERE'', ''severe'')  |stats count as ''Error Count'' by target |top limit=10 ''Error Count''';
exec :UI_HIDDEN             				:=    0;
exec :DELETED               				:=    0;
exec :IS_WIDGET             				:=    1;
exec :TENANT_ID             				:=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=     2005;
exec :NAME                  :=     'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=     null;
exec :PARAM_TYPE            :=     1;
exec :PARAM_VALUE_STR       :=     'PIE';
exec :PARAM_VALUE_CLOB      :=     null;
exec :TENANT_ID             :=     '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2005;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_PIE';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2005;
exec :NAME                  :=    'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '/html/search/widgets/loganPieChart.html';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2005;
exec :NAME                  :=    'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2005;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative","duration":"60","timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2006,'Middleware Logs Trend','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Time-based histogram of all log entries from Middleware targets',2,1,null,null,null,null,1,null,0,'''Target Type'' IN (''Oracle WebLogic Server'', ''Oracle Internet Directory'', ''Oracle HTTP Server'', ''Oracle Access Management Server'', ''Oracle WebLogic Domain'')',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2006,'VISUALIZATION_TYPE_KEY',null,1,'HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2006,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2006,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganHistogram.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2006,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/results/loganSearchChartsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2006,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=    2006;
exec :NAME                                  :=    'Middleware Logs Trend';
exec :OWNER                 				:=    'ORACLE';
exec :CREATION_DATE         				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=    'ORACLE';
exec :DESCRIPTION           				:=    'Time-based histogram of all log entries from Middleware targets';
exec :FOLDER_ID             				:=    2;
exec :CATEGORY_ID           				:=    1;
exec :NAME_NLSID            				:=    null;
exec :NAME_SUBSYSTEM        				:=    null;
exec :DESCRIPTION_NLSID     				:=    null;
exec :DESCRIPTION_SUBSYSTEM 				:=    null;
exec :SYSTEM_SEARCH         				:=    1;
exec :EM_PLUGIN_ID          				:=    null;
exec :IS_LOCKED             				:=    0;
exec :SEARCH_DISPLAY_STR    				:=    '''Target Type'' IN (''Oracle WebLogic Server'', ''Oracle Internet Directory'', ''Oracle HTTP Server'', ''Oracle Access Management Server'', ''Oracle WebLogic Domain'')';
exec :UI_HIDDEN             				:=    0;
exec :DELETED               				:=    0;
exec :IS_WIDGET             				:=    1;
exec :TENANT_ID             				:=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);


exec :SEARCH_ID             :=   2006;
exec :NAME                  :=   'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=  2006;
exec :NAME                  :=  'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  'LA_WIDGET_HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

 exec :SEARCH_ID            :=   2006;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganHistogram.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

 exec :SEARCH_ID            :=   2006;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

--
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2007,'Web Server Top Accessed Pages','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Top 10 recently accessed web pages in Middleware access logs.',2,1,null,null,null,null,1,null,0,'''log source'' in (''FMW WLS Server Access Log'',''FMW OHS Access Log'') |stats count as ''Request Count'' by URI |top limit=10 ''Request Count''',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2007,'VISUALIZATION_TYPE_KEY',null,1,'BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2007,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2007,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganBarChart.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2007,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2007,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=     2007;
exec :NAME                                  :=    'Web Server Top Accessed Pages';
exec :OWNER                 				:=    'ORACLE';
exec :CREATION_DATE         				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=    'ORACLE';
exec :DESCRIPTION           				:=    'Top 10 recently accessed web pages in Middleware access logs.';
exec :FOLDER_ID             				:=    2;
exec :CATEGORY_ID           				:=    1;
exec :NAME_NLSID            				:=    null;
exec :NAME_SUBSYSTEM        				:=    null;
exec :DESCRIPTION_NLSID     				:=    null;
exec :DESCRIPTION_SUBSYSTEM 				:=    null;
exec :SYSTEM_SEARCH         				:=    1;
exec :EM_PLUGIN_ID          				:=    null;
exec :IS_LOCKED             				:=    0;
exec :SEARCH_DISPLAY_STR    				:=    '''log source'' in (''FMW WLS Server Access Log'',''FMW OHS Access Log'') |stats count as ''Request Count'' by URI |top limit=10 ''Request Count''';
exec :UI_HIDDEN             				:=    0;
exec :DELETED               				:=    0;
exec :IS_WIDGET             				:=    1;
exec :TENANT_ID             				:=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

 exec :SEARCH_ID            :=  2007;
exec :NAME                  :=  'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  'BAR';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2007;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_BAR';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2007;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganBarChart.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=   2007;
exec :NAME                  :=   'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2007;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2008,'Web Server Failed HTTP Requests','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Time-based histogram of recent HTTP request failures.',2,1,null,null,null,null,1,null,0,'''log source'' IN (''FMW WLS Server Access Log'', ''FMW OHS Access Log'') AND Status IN (''400'', ''401'', ''402'', ''403'', ''404'', ''405'', ''406'', ''407'', ''408'', ''409'', ''410'', ''411'', ''412'', ''413'', ''414'', ''415'', ''416'', ''417'', ''500'', ''501'', ''502'', ''503'', ''504'', ''505'')',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2008,'VISUALIZATION_TYPE_KEY',null,1,'HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2008,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2008,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganHistogram.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2008,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/results/loganSearchChartsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2008,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=    2008;
exec :NAME                                  :=    'Web Server Failed HTTP Requests';
exec :OWNER                 				:=    'ORACLE';
exec :CREATION_DATE         				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=    'ORACLE';
exec :DESCRIPTION           				:=    'Time-based histogram of recent HTTP request failures.';
exec :FOLDER_ID             				:=    2;
exec :CATEGORY_ID           				:=    1;
exec :NAME_NLSID            				:=    null;
exec :NAME_SUBSYSTEM        				:=    null;
exec :DESCRIPTION_NLSID     				:=    null;
exec :DESCRIPTION_SUBSYSTEM 				:=    null;
exec :SYSTEM_SEARCH         				:=    1;
exec :EM_PLUGIN_ID          				:=    null;
exec :IS_LOCKED             				:=    0;
exec :SEARCH_DISPLAY_STR    				:=    '''log source'' IN (''FMW WLS Server Access Log'', ''FMW OHS Access Log'') AND Status IN (''400'', ''401'', ''402'', ''403'', ''404'', ''405'', ''406'', ''407'', ''408'', ''409'', ''410'', ''411'', ''412'', ''413'', ''414'', ''415'', ''416'', ''417'', ''500'', ''501'', ''502'', ''503'', ''504'', ''505'')';
exec :UI_HIDDEN             				:=    0;
exec :DELETED               				:=    0;
exec :IS_WIDGET             				:=    1;
exec :TENANT_ID             				:=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=   2008;
exec :NAME                  :=   'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=    2008;
exec :NAME                  :=    'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'LA_WIDGET_HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);



exec :SEARCH_ID             :=    2008;
exec :NAME                  :=    'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '/html/search/widgets/loganHistogram.html';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2008;
exec :NAME                  :=    'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '/js/viewmodel/search/results/loganSearchChartsViewModel.js';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

 exec :SEARCH_ID            :=    2008;
exec :NAME                  :=    'time';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2009,'Top Web Server Targets by Requests','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Top 10 web server targets with the most HTTP requests.',2,1,null,null,null,null,1,null,0,'''log source'' in (''FMW WLS Server Access Log'',''FMW OHS Access Log'') |stats count as ''Request Count'' by target |top limit=10 ''Request Count''',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2009,'VISUALIZATION_TYPE_KEY',null,1,'PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2009,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2009,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganPieChart.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2009,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2009,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=    2009;
exec :NAME                                  :=    'Top Web Server Targets by Requests';
exec :OWNER                 				:=    'ORACLE';
exec :CREATION_DATE         				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=    'ORACLE';
exec :DESCRIPTION           				:=    'Top 10 web server targets with the most HTTP requests.';
exec :FOLDER_ID             				:=    2;
exec :CATEGORY_ID           				:=    1;
exec :NAME_NLSID            				:=    null;
exec :NAME_SUBSYSTEM        				:=    null;
exec :DESCRIPTION_NLSID     				:=    null;
exec :DESCRIPTION_SUBSYSTEM 				:=    null;
exec :SYSTEM_SEARCH         				:=    1;
exec :EM_PLUGIN_ID          				:=    null;
exec :IS_LOCKED             				:=    0;
exec :SEARCH_DISPLAY_STR    				:=    '''log source'' in (''FMW WLS Server Access Log'',''FMW OHS Access Log'') |stats count as ''Request Count'' by target |top limit=10 ''Request Count''';
exec :UI_HIDDEN             				:=    0;
exec :DELETED               				:=    0;
exec :IS_WIDGET             				:=    1;
exec :TENANT_ID             				:=    '&TENANT_ID';
Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=    2009;
exec :NAME                  :=    'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'PIE';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';


Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2009;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_PIE';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=  2009;
exec :NAME                  :=  'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  '/html/search/widgets/loganPieChart.html';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=  2009;
exec :NAME                  :=  'time';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2010,'Top Middleware Error Codes','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Top 10 most common recent Middleware errors codes (such as BEA-XXXXXX).',2,1,null,null,null,null,1,null,0,'''target type'' in (''Oracle WebLogic Server'', ''Oracle Internet Directory'',''Oracle HTTP Server'',''Oracle Access Management Server'',''Oracle WebLogic Domain'') AND ''Error ID'' != ''bea-000000'' |stats count as cnt by ''error id'' |top limit=10 cnt',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2010,'VISUALIZATION_TYPE_KEY',null,1,'PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2010,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2010,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganPieChart.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2010,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2010,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=     2010;
exec :NAME                                  :=     'Top Middleware Error Codes';
exec :OWNER                 				:=     'ORACLE';
exec :CREATION_DATE         				:=     SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=     SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=     'ORACLE';
exec :DESCRIPTION           				:=     'Top 10 most common recent Middleware errors codes (such as BEA-XXXXXX).';
exec :FOLDER_ID             				:=     2;
exec :CATEGORY_ID           				:=     1;
exec :NAME_NLSID            				:=     null;
exec :NAME_SUBSYSTEM        				:=     null;
exec :DESCRIPTION_NLSID     				:=     null;
exec :DESCRIPTION_SUBSYSTEM 				:=     null;
exec :SYSTEM_SEARCH         				:=     1;
exec :EM_PLUGIN_ID          				:=     null;
exec :IS_LOCKED             				:=     0;
exec :SEARCH_DISPLAY_STR    				:=     '''target type'' in (''Oracle WebLogic Server'', ''Oracle Internet Directory'',''Oracle HTTP Server'',''Oracle Access Management Server'',''Oracle WebLogic Domain'') AND ''Error ID'' != ''bea-000000'' |stats count as cnt by ''error id'' |top limit=10 cnt';
exec :UI_HIDDEN             				:=     0;
exec :DELETED               				:=     0;
exec :IS_WIDGET             				:=     1;
exec :TENANT_ID             				:=     '&TENANT_ID';

 Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=   2010;
exec :NAME                  :=   'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'PIE';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=    2010;
exec :NAME                  :=    'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'LA_WIDGET_PIE';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2010;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganPieChart.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=   2010;
exec :NAME                  :=   'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2010;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID                          	:=      2011;
exec :NAME                                  :=      'Top ECIDs with BEA Errors';
exec :OWNER                 				:=      'ORACLE';
exec :CREATION_DATE         				:=      SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=      SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=      'ORACLE';
exec :DESCRIPTION           				:=      'Top 10 Execution IDs (ECIDs) based on number of log entries containing BEA error codes.';
exec :FOLDER_ID             				:=      2;
exec :CATEGORY_ID           				:=      1;
exec :NAME_NLSID            				:=      null;
exec :NAME_SUBSYSTEM        				:=      null;
exec :DESCRIPTION_NLSID     				:=      null;
exec :DESCRIPTION_SUBSYSTEM 				:=      null;
exec :SYSTEM_SEARCH         				:=      1;
exec :EM_PLUGIN_ID          				:=      null;
exec :IS_LOCKED             				:=      0;
exec :SEARCH_DISPLAY_STR    				:=      '''target type'' in (''Oracle WebLogic Server'',''Oracle Internet Directory'',''Oracle HTTP Server'',''Oracle Access Management Server'', ''Oracle WebLogic Domain'') AND ''Error ID'' != ''bea-000000'' |stats count as ''BEA-XXXXXX Count'' by ecid |top limit=10 ''BEA-XXXXXX Count''';
exec :UI_HIDDEN             				:=      0;
exec :DELETED               				:=      0;
exec :IS_WIDGET             				:=      1;
exec :TENANT_ID             				:=      '&TENANT_ID';


 Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);


exec :SEARCH_ID             :=   2011;
exec :NAME                  :=   'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'PIE';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=   2011;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_PIE';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2011;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganPieChart.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2011;
exec :NAME                  :=   'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2011;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

--
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2012,'Top Middleware Targets with Errors','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Top 10 Middleware targets that have the most log entries with ERROR or SEVERE severity.',2,1,null,null,null,null,1,null,0,'''target type'' in (''Oracle WebLogic Server'',''Oracle Internet Directory'',''Oracle HTTP Server'',''Oracle Access Management Server'', ''Oracle WebLogic Domain'') AND severity  in (''error'',''ERROR'',''SEVERE'',''severe'') |stats count as ''Error Count'' by target |top limit=10 ''Error Count''',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2012,'VISUALIZATION_TYPE_KEY',null,1,'PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2012,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2012,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganPieChart.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2012,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2012,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=   2012;
exec :NAME                                  :=   'Top Middleware Targets with Errors';
exec :OWNER                 				:=   'ORACLE';
exec :CREATION_DATE         				:=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=   'ORACLE';
exec :DESCRIPTION           				:=   'Top 10 Middleware targets that have the most log entries with ERROR or SEVERE severity.';
exec :FOLDER_ID             				:=   2;
exec :CATEGORY_ID           				:=   1;
exec :NAME_NLSID            				:=   null;
exec :NAME_SUBSYSTEM        				:=   null;
exec :DESCRIPTION_NLSID     				:=   null;
exec :DESCRIPTION_SUBSYSTEM 				:=   null;
exec :SYSTEM_SEARCH         				:=   1;
exec :EM_PLUGIN_ID          				:=   null;
exec :IS_LOCKED             				:=   0;
exec :SEARCH_DISPLAY_STR    				:=   '''target type'' in (''Oracle WebLogic Server'',''Oracle Internet Directory'',''Oracle HTTP Server'',''Oracle Access Management Server'', ''Oracle WebLogic Domain'') AND severity  in (''error'',''ERROR'',''SEVERE'',''severe'') |stats count as ''Error Count'' by target |top limit=10 ''Error Count''';
exec :UI_HIDDEN             				:=   0;
exec :DELETED               				:=   0;
exec :IS_WIDGET             				:=   1;
exec :TENANT_ID             				:=   '&TENANT_ID';



 Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=   2012;
exec :NAME                  :=   'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'PIE';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=  2012;
exec :NAME                  :=  'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  'LA_WIDGET_PIE';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=   2012;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganPieChart.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
exec :SEARCH_ID             :=   2012;
exec :NAME                  :=   'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=   2012;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
--
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2013,'Database Errors Trend','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Time-based histogram of all errors from Database targets.',2,1,null,null,null,null,1,null,0,'''Target Type'' IN (''Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'') AND ''Message Group'' IN (''ORA'', ''NZE'', ''EXP'', ''IMP'', ''SQL*Loader'', ''KUP'', ''UDE'', ''UDI'', ''DBV'', ''NID'', ''DGM'', ''DIA'', ''LCD'', ''OCI'', ''QSM'', ''RMAN'', ''LRM'', ''LFI'', ''PLS'', ''PLW'', ''AMD'', ''CLSR'', ''CLSS'', ''CRS'', ''EVM'', ''CLST'', ''CLSD'', ''PROC'', ''PROT'', ''TNS'', ''NNC'', ''NNO'', ''NPL'', ''NNF'', ''NMP'', ''NCR'', ''O2F'', ''O2I'', ''O2U'', ''PCB'', ''PCC'', ''SQL'', ''AUD'', ''IMG'', ''VID'', ''DRG'', ''LPX'', ''LSX'', ''PGA'', ''PGU'') OR ''Message Group'' LIKE ''%NNL''',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2013,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2013,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/results/loganSearchChartsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2013,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganHistogram.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2013,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2013,'VISUALIZATION_TYPE_KEY',null,1,'HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2013,'TARGET_FILTER',null,2,'',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=    2013;
exec :NAME                                  :=    'Database Errors Trend';
exec :OWNER                 				:=    'ORACLE';
exec :CREATION_DATE         				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=    'ORACLE';
exec :DESCRIPTION           				:=    'Time-based histogram of all errors from Database targets.';
exec :FOLDER_ID             				:=    2;
exec :CATEGORY_ID           				:=    1;
exec :NAME_NLSID            				:=    null;
exec :NAME_SUBSYSTEM        				:=    null;
exec :DESCRIPTION_NLSID     				:=    null;
exec :DESCRIPTION_SUBSYSTEM 				:=    null;
exec :SYSTEM_SEARCH         				:=    1;
exec :EM_PLUGIN_ID          				:=    null;
exec :IS_LOCKED             				:=    0;
exec :SEARCH_DISPLAY_STR    				:=    '''Target Type'' IN (''Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'') AND ''Message Group'' IN (''ORA'', ''NZE'', ''EXP'', ''IMP'', ''SQL*Loader'', ''KUP'', ''UDE'', ''UDI'', ''DBV'', ''NID'', ''DGM'', ''DIA'', ''LCD'', ''OCI'', ''QSM'', ''RMAN'',''LRM'', ''LFI'', ''PLS'', ''PLW'', ''AMD'', ''CLSR'', ''CLSS'', ''CRS'', ''EVM'', ''CLST'', ''CLSD'',''PROC'', ''PROT'', ''TNS'', ''NNC'', ''NNO'', ''NPL'',''NNF'', ''NMP'', ''NCR'', ''O2F'', ''O2I'', ''O2U'', ''PCB'', ''PCC'', ''SQL'', ''AUD'', ''IMG'', ''VID'', ''DRG'', ''LPX'', ''LSX'', ''PGA'', ''PGU'') OR ''Message Group'' LIKE ''%NNL''';
exec :UI_HIDDEN             				:=    0;
exec :DELETED               				:=    0;
exec :IS_WIDGET             				:=    1;
exec :TENANT_ID             				:=    '&TENANT_ID';

 Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=   2013;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2013;
exec :NAME                  :=   'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/js/viewmodel/search/results/loganSearchChartsViewModel.js';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2013;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganHistogram.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2013;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
exec :SEARCH_ID             :=     2013;
exec :NAME                  :=    'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2013;
exec :NAME                  :=    'TARGET_FILTER';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    2;
exec :PARAM_VALUE_STR       :=    '';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2014,'Failed Host Password Attempts','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Time-based histogram showing count of failed passwords while attempting to log into a user account',2,1,null,null,null,null,1,null,0,'''Target Type'' = ''Host'' AND (''failed password for'' OR ''authentication failure'')',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2014,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2014,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganHistogram.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2014,'TARGET_FILTER',null,2,'',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2014,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/results/loganSearchChartsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2014,'VISUALIZATION_TYPE_KEY',null,1,'HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2014,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=     2014;
exec :NAME                                  :=    'Failed Host Password Attempts';
exec :OWNER                 				:=    'ORACLE';
exec :CREATION_DATE         				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=    'ORACLE';
exec :DESCRIPTION           				:=    'Time-based histogram showing count of failed passwords while attempting to log into a user account';
exec :FOLDER_ID             				:=    2;
exec :CATEGORY_ID           				:=    1;
exec :NAME_NLSID            				:=    null;
exec :NAME_SUBSYSTEM        				:=    null;
exec :DESCRIPTION_NLSID     				:=    null;
exec :DESCRIPTION_SUBSYSTEM 				:=    null;
exec :SYSTEM_SEARCH         				:=    1;
exec :EM_PLUGIN_ID          				:=    null;
exec :IS_LOCKED             				:=    0;
exec :SEARCH_DISPLAY_STR    				:=    '''Target Type'' = ''Host'' AND (''failed password for'' OR ''authentication failure'')';
exec :UI_HIDDEN             				:=    0;
exec :DELETED               				:=    0;
exec :IS_WIDGET             				:=    1;
exec :TENANT_ID             				:=    '&TENANT_ID';


 Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=  2014;
exec :NAME                  :=  'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  'LA_WIDGET_HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2014;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganHistogram.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=  2014;
exec :NAME                  :=  'TARGET_FILTER';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  2;
exec :PARAM_VALUE_STR       :=  '';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=  2014;
exec :NAME                  :=  'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  '/js/viewmodel/search/results/loganSearchChartsViewModel.js';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=  2014;
exec :NAME                  :=  'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  'HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
exec :SEARCH_ID             :=   2014;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

--
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2015,'Database Top Errors','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Top 10 Database Errors that have recently occurred.',2,1,null,null,null,null,1,null,0,' ''Target Type'' IN (''Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'') AND ''Message Group'' in ( ''ORA'',''NZE'',''EXP'', ''IMP'',''SQL*Loader'', ''KUP'',''UDE'', ''UDI'',''DBV'', ''NID'', ''DGM'',''DIA'',''LCD'',''OCI'', ''QSM'', ''RMAN'',''LRM'',''LFI'',''PLS'', ''PLW'',''AMD'',''CLSR'',''CLSS'',''CRS'' ,''EVM'', ''CLST'',''CLSD'',''PROC'', ''PROT'',''TNS'',''NNC'',''NNO'',''NPL'', ''NNF'', ''NMP'',''NCR'', ''O2F'', ''O2I'',''O2U'',''PCB'',''PCC'',''SQL'',''AUD'',''IMG'', ''VID'', ''DRG'',''LPX'',''LSX'',''PGA'', ''PGU'') OR ''Message Group'' like ''%NNL''  |stats count as ''Errors'' by ''Message ID'' |top limit=10 ''Errors''',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2015,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2015,'TARGET_FILTER',null,2,'',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2015,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2015,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2015,'VISUALIZATION_TYPE_KEY',null,1,'BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2015,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganBarChart.html',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=     2015;
exec :NAME                                  :=     'Database Top Errors';
exec :OWNER                 				:=     'ORACLE';
exec :CREATION_DATE         				:=     SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=     SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=     'ORACLE';
exec :DESCRIPTION           				:=     'Top 10 Database Errors that have recently occurred.';
exec :FOLDER_ID             				:=     2;
exec :CATEGORY_ID           				:=     1;
exec :NAME_NLSID            				:=     null;
exec :NAME_SUBSYSTEM        				:=     null;
exec :DESCRIPTION_NLSID     				:=     null;
exec :DESCRIPTION_SUBSYSTEM 				:=     null;
exec :SYSTEM_SEARCH         				:=     1;
exec :EM_PLUGIN_ID          				:=     null;
exec :IS_LOCKED             				:=     0;
exec :SEARCH_DISPLAY_STR    				:=     ' ''Target Type'' IN (''Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'') AND ''Message Group'' in ( ''ORA'',''NZE'',''EXP'', ''IMP'',''SQL*Loader'', ''KUP'',''UDE'', ''UDI'',''DBV'', ''NID'', ''DGM'',''DIA'',''LCD'',''OCI'', ''QSM'', ''RMAN'',''LRM'',''LFI'',''PLS'', ''PLW'',''AMD'',''CLSR'',''CLSS'',''CRS'' ,''EVM'', ''CLST'',''CLSD'',''PROC'', ''PROT'',''TNS'',''NNC'',''NNO'',''NPL'', ''NNF'', ''NMP'',''NCR'', ''O2F'', ''O2I'',''O2U'',''PCB'',''PCC'',''SQL''''AUD'',''IMG'', ''VID'', ''DRG'',''LPX'',''LSX'',''PGA'', ''PGU'') OR ''Message Group'' like ''%NNL''  |stats count as ''Errors'' by ''Message ID'' |top limit=10 ''Errors''';
exec :UI_HIDDEN             				:=     0;
exec :DELETED               				:=     0;
exec :IS_WIDGET             				:=     1;
exec :TENANT_ID             				:=     '&TENANT_ID';


 Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);


exec :SEARCH_ID             :=   2015;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2015;
exec :NAME                  :=   'TARGET_FILTER';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   2;
exec :PARAM_VALUE_STR       :=   '';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2015;
exec :NAME                  :=   'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2015;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_BAR';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=    2015;
exec :NAME                  :=    'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'BAR';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2015;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganBarChart.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
 
--
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2016,'Database Critical Incidents by Target Type','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Database critical errors based on Error ID grouped by by target type',2,1,null,null,null,null,1,null,0,'''Target Type'' IN (''Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'') and ''Error ID'' IN ( ''ORA-00600'',''ORA-00603'',''ORA-07445'', ''ORA-04030'',''ORA-04031'',''ORA-00227'',''ORA-00239'', ''ORA-00240'',''ORA-00255'',''ORA-00353'', ''ORA-00355'',''ORA-00356'',''ORA-00445'',''ORA-00494'',''ORA-01578'', ''OCI-03106'',''ORA-03113'',''OCI-03113%'', ''OCI-03135'', ''ORA-03137'',''ORA-04036'',''ORA-24982'',''ORA-25319'', ''ORA-29740'',''ORA-29770'', ''ORA-29771'', ''ORA-32701'',''ORA-32703'',''ORA-32704'',''ORA-06512'', ''ORA-15173'' ) |stats count as ''Critical Errors'' by ''Target Type''',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2016,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganBarChart.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2016,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2016,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2016,'TARGET_FILTER',null,2,'',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2016,'VISUALIZATION_TYPE_KEY',null,1,'BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2016,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');

exec :SEARCH_ID                          	:=      2016;
exec :NAME                                  :=      'Database Critical Incidents by Target Type';
exec :OWNER                 				:=      'ORACLE';
exec :CREATION_DATE         				:=      SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=      SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=      'ORACLE';
exec :DESCRIPTION           				:=      'Database critical errors based on Error ID grouped by by target type';
exec :FOLDER_ID             				:=      2;
exec :CATEGORY_ID           				:=      1;
exec :NAME_NLSID            				:=      null;
exec :NAME_SUBSYSTEM        				:=      null;
exec :DESCRIPTION_NLSID     				:=      null;
exec :DESCRIPTION_SUBSYSTEM 				:=      null;
exec :SYSTEM_SEARCH         				:=      1;
exec :EM_PLUGIN_ID          				:=      null;
exec :IS_LOCKED             				:=      0;
exec :SEARCH_DISPLAY_STR    				:=      '''Target Type'' IN (''Database Instance'', ''Automatic Storage Management'', ''Listener'', ''Cluster'') and ''Error ID'' IN ( ''ORA-00600'',''ORA-00603'',''ORA-07445'', ''ORA-04030'',''ORA-04031'',''ORA-00227'',''ORA-00239'', ''ORA-00240'',''ORA-00255'',''ORA-00353'' ,''ORA-00355'',''ORA-00356'',''ORA-00445'',''ORA-00494'',''ORA-01578'', ''OCI-03106'',''ORA-03113'',''OCI-03113%'', ''OCI-03135'', ''ORA-03137'',''ORA-04036'',''ORA-24982'',''ORA-25319'', ''ORA-29740'',''ORA-29770'', ''ORA-29771'', ''ORA-32701'',''ORA-32703'',''ORA-32704'',''ORA-06512'', ''ORA-15173'' ) |stats count as ''Critical Errors'' by ''Target Type''';
exec :UI_HIDDEN             				:=      0;
exec :DELETED               				:=      0;
exec :IS_WIDGET             				:=      1;
exec :TENANT_ID             				:=      '&TENANT_ID';

 Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);


exec :SEARCH_ID             :=  2016;
exec :NAME                  :=  'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  '/html/search/widgets/loganBarChart.html';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2016;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_BAR';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2016;
exec :NAME                  :=    'time';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2016;
exec :NAME                  :=   'TARGET_FILTER';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   2;
exec :PARAM_VALUE_STR       :=   '';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);




exec :SEARCH_ID             :=    2016;
exec :NAME                  :=    'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'BAR';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=    2016;
exec :NAME                  :=    'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
--
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2017,'Access Log Error Status Codes','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Top 4xx and 5xx errors codes in HTTP Access Logs. ',2,1,null,null,null,null,1,null,0,'''Log Source'' LIKE "*Access Logs*" AND Status IN (''400'', ''401'', ''402'', ''403'', ''404'', ''405'', ''406'', ''407'', ''408'', ''409'', ''410'', ''411'', ''412'', ''413'', ''414'', ''415'', ''416'', ''417'', ''500'', ''501'', ''502'', ''503'', ''504'', ''505'') | stats count by ''log source''',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2017,'VISUALIZATION_TYPE_KEY',null,1,'PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2017,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2017,'TARGET_FILTER',null,2,'',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2017,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2017,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2017,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganPieChart.html',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=  2017;
exec :NAME                                  :=  'Access Log Error Status Codes';
exec :OWNER                 				:=  'ORACLE';
exec :CREATION_DATE         				:=  SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=  SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=  'ORACLE';
exec :DESCRIPTION           				:=  'Top 4xx and 5xx errors codes in HTTP Access Logs. ';
exec :FOLDER_ID             				:=  2;
exec :CATEGORY_ID           				:=  1;
exec :NAME_NLSID            				:=  null;
exec :NAME_SUBSYSTEM        				:=  null;
exec :DESCRIPTION_NLSID     				:=  null;
exec :DESCRIPTION_SUBSYSTEM 				:=  null;
exec :SYSTEM_SEARCH         				:=  1;
exec :EM_PLUGIN_ID          				:=  null;
exec :IS_LOCKED             				:=  0;
exec :SEARCH_DISPLAY_STR    				:=  '''Log Source'' LIKE "*Access Logs*" AND Status IN (''400'', ''401'', ''402'', ''403'', ''404'', ''405'', ''406'', ''407'', ''408'', ''409'', ''410'', ''411'', ''412'', ''413'', ''414'', ''415'', ''416'', ''417'',''500'', ''501'', ''502'', ''503'', ''504'', ''505'') | stats count by ''log source''';
exec :UI_HIDDEN             				:=  0;
exec :DELETED               				:=  0;
exec :IS_WIDGET             				:=  1;
exec :TENANT_ID             				:=  '&TENANT_ID';

 Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);


exec :SEARCH_ID             :=     2017;
exec :NAME                  :=    'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'PIE';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=    2017;
exec :NAME                  :=    'time';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2017;
exec :NAME                  :=    'TARGET_FILTER';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    2;
exec :PARAM_VALUE_STR       :=    '';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2017;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_PIE';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2017;
exec :NAME                  :=   'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2017;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganPieChart.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
--
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2018,'Web Server Top Accessed Pages (Excluding Assets)','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Graph of top 10 Top URIs for application pages excluding external assets such as images, javascript, css files. ',2,1,null,null,null,null,1,null,0,'''Log Source'' LIKE "*Access Logs*" and URI != null and "File Extension" not in ("gif","png","jpg","js","css","swf","ico") and URI not like "*/blank.html" | stats count as "Request Count" by URI |top limit=10 "Request Count"',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2018,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2018,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2018,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganBarChart.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2018,'VISUALIZATION_TYPE_KEY',null,1,'BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2018,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2018,'TARGET_FILTER',null,2,'',null,'&TENANT_ID');
exec :SEARCH_ID                          	:= 2018;
exec :NAME                                  := 'Web Server Top Accessed Pages (Excluding Assets)';
exec :OWNER                 				:= 'ORACLE';
exec :CREATION_DATE         				:= SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:= SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:= 'ORACLE';
exec :DESCRIPTION           				:= 'Graph of top 10 Top URIs for application pages excluding external assets such as images, javascript, css files. ';
exec :FOLDER_ID             				:= 2;
exec :CATEGORY_ID           				:= 1;
exec :NAME_NLSID            				:= null;
exec :NAME_SUBSYSTEM        				:= null;
exec :DESCRIPTION_NLSID     				:= null;
exec :DESCRIPTION_SUBSYSTEM 				:= null;
exec :SYSTEM_SEARCH         				:= 1;
exec :EM_PLUGIN_ID          				:= null;
exec :IS_LOCKED             				:= 0;
exec :SEARCH_DISPLAY_STR    				:= '''Log Source'' LIKE "*Access Logs*" and URI != null and "File Extension" not in ("gif","png","jpg","js","css","swf","ico") and URI not like "*/blank.html" | stats count as "Request Count" by URI |top limit=10 "Request Count"';
exec :UI_HIDDEN             				:= 0;
exec :DELETED               				:= 0;
exec :IS_WIDGET             				:= 1;
exec :TENANT_ID             				:= '&TENANT_ID';

 Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=   2018;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2018;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_BAR';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2018;
exec :NAME                  :=  'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  '/html/search/widgets/loganBarChart.html';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);



exec :SEARCH_ID             :=   2018;
exec :NAME                  :=   'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'BAR';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2018;
exec :NAME                  :=   'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
exec :SEARCH_ID             :=     2018;
exec :NAME                  :=    'TARGET_FILTER';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    2;
exec :PARAM_VALUE_STR       :=    '';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

--
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2019,'Top Hosts by Log Entries','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Top 10 hosts by number of log entries',2,1,null,null,null,null,1,null,0,'''Target Type''= ''Host'' | STATS COUNT AS "Log Entries" BY Target | TOP LIMIT=10 "Log Entries"',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2019,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2019,'VISUALIZATION_TYPE_KEY',null,1,'PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2019,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2019,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2019,'TARGET_FILTER',null,2,'',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2019,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganPieChart.html',null,'&TENANT_ID');
exec :SEARCH_ID                          	:= 2019;
exec :NAME                                  := 'Top Hosts by Log Entries';
exec :OWNER                 				:= 'ORACLE';
exec :CREATION_DATE         				:= SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:= SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:= 'ORACLE';
exec :DESCRIPTION           				:= 'Top 10 hosts by number of log entries';
exec :FOLDER_ID             				:= 2;
exec :CATEGORY_ID           				:= 1;
exec :NAME_NLSID            				:= null;
exec :NAME_SUBSYSTEM        				:= null;
exec :DESCRIPTION_NLSID     				:= null;
exec :DESCRIPTION_SUBSYSTEM 				:= null;
exec :SYSTEM_SEARCH         				:= 1;
exec :EM_PLUGIN_ID          				:= null;
exec :IS_LOCKED             				:= 0;
exec :SEARCH_DISPLAY_STR    				:= '''Target Type''= ''Host'' | STATS COUNT AS "Log Entries" BY Target | TOP LIMIT=10 "Log Entries"';
exec :UI_HIDDEN             				:= 0;
exec :DELETED               				:= 0;
exec :IS_WIDGET             				:= 1;
exec :TENANT_ID             				:= '&TENANT_ID';


 Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=   2019;
exec :NAME                  :=   'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=    2019;
exec :NAME                  :=    'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'PIE';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2019;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2019;
exec :NAME                  :=    'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'LA_WIDGET_PIE';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2019;
exec :NAME                  :=    'TARGET_FILTER';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    2;
exec :PARAM_VALUE_STR       :=    '';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=     2019;
exec :NAME                  :=    'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '/html/search/widgets/loganPieChart.html';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
--
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2020,'Top Host Log Sources','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Top 10 Log Sources for Host Target Type by number of log entries',2,1,null,null,null,null,1,null,0,'''Target Type'' = ''Host'' | stats count as "Log Entries" by ''Log Source'' | top limit = 10 "Log Entries"',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2020,'TARGET_FILTER',null,2,'',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2020,'VISUALIZATION_TYPE_KEY',null,1,'BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2020,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2020,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2020,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2020,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganBarChart.html',null,'&TENANT_ID');
exec :SEARCH_ID                          	:= 2020;
exec :NAME                                  := 'Top Host Log Sources';
exec :OWNER                 				:= 'ORACLE';
exec :CREATION_DATE         				:= SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:= SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:= 'ORACLE';
exec :DESCRIPTION           				:= 'Top 10 Log Sources for Host Target Type by number of log entries';
exec :FOLDER_ID             				:= 2;
exec :CATEGORY_ID           				:= 1;
exec :NAME_NLSID            				:= null;
exec :NAME_SUBSYSTEM        				:= null;
exec :DESCRIPTION_NLSID     				:= null;
exec :DESCRIPTION_SUBSYSTEM 				:= null;
exec :SYSTEM_SEARCH         				:= 1;
exec :EM_PLUGIN_ID          				:= null;
exec :IS_LOCKED             				:= 0;
exec :SEARCH_DISPLAY_STR    				:= '''Target Type'' = ''Host'' | stats count as "Log Entries" by ''Log Source'' | top limit = 10 "Log Entries"';
exec :UI_HIDDEN             				:= 0;
exec :DELETED               				:= 0;
exec :IS_WIDGET             				:= 1;
exec :TENANT_ID             				:= '&TENANT_ID';

 Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=    2020;
exec :NAME                  :=    'TARGET_FILTER';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    2;
exec :PARAM_VALUE_STR       :=    '';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=    2020;
exec :NAME                  :=    'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'BAR';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=  2020;
exec :NAME                  :=  'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2020;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_BAR';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2020;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2020;
exec :NAME                  :=    'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '/html/search/widgets/loganBarChart.html';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2021,'Top Commands Run with SUDO','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Top 10 most recent commands that are run using SUDO',2,1,null,null,null,null,1,null,0,'''log entity'' like ''/var/log/sudo.log*'' |stats count as ''Execution Count'' by command |top limit=10 ''Execution Count''',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2021,'TARGET_FILTER',null,2,'',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2021,'VISUALIZATION_TYPE_KEY',null,1,'HORIZONTAL_BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2021,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_HORIBAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2021,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganHoriBarChart.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2021,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2021,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=  2021;
exec :NAME                                  :=  'Top Commands Run with SUDO';
exec :OWNER                 				:=  'ORACLE';
exec :CREATION_DATE         				:=  SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=  SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=  'ORACLE';
exec :DESCRIPTION           				:=  'Top 10 most recent commands that are run using SUDO';
exec :FOLDER_ID             				:=  2;
exec :CATEGORY_ID           				:=  1;
exec :NAME_NLSID            				:=  null;
exec :NAME_SUBSYSTEM        				:=  null;
exec :DESCRIPTION_NLSID     				:=  null;
exec :DESCRIPTION_SUBSYSTEM 				:=  null;
exec :SYSTEM_SEARCH         				:=  1;
exec :EM_PLUGIN_ID          				:=  null;
exec :IS_LOCKED             				:=  0;
exec :SEARCH_DISPLAY_STR    				:=  '''log entity'' like ''/var/log/sudo.log*'' |stats count as ''Execution Count'' by command |top limit=10 ''Execution Count''';
exec :UI_HIDDEN             				:=  0;
exec :DELETED               				:=  0;
exec :IS_WIDGET             				:=  1;
exec :TENANT_ID             				:=  '&TENANT_ID';

 Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);


exec :SEARCH_ID             :=    2021;
exec :NAME                  :=    'TARGET_FILTER';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    2;
exec :PARAM_VALUE_STR       :=    '';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
exec :SEARCH_ID             :=   2021;
exec :NAME                  :=   'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'HORIZONTAL_BAR';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2021;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_HORIBAR';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2021;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganHoriBarChart.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2021;
exec :NAME                  :=    'time';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2021;
exec :NAME                  :=   'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
--
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2022,'Top SUDO Users','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Top 10 recent users initiating SUDO requests.',2,1,null,null,null,null,1,null,0,'''Log Entity'' like ''/var/log/sudo.log*''| stats count by ''User Name (Originating)''',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2022,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2022,'TARGET_FILTER',null,2,'',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2022,'VISUALIZATION_TYPE_KEY',null,1,'PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2022,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2022,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganPieChart.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2022,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=   2022;
exec :NAME                                  :=   'Top SUDO Users';
exec :OWNER                 				:=   'ORACLE';
exec :CREATION_DATE         				:=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=   'ORACLE';
exec :DESCRIPTION           				:=   'Top 10 recent users initiating SUDO requests.';
exec :FOLDER_ID             				:=   2;
exec :CATEGORY_ID           				:=   1;
exec :NAME_NLSID            				:=   null;
exec :NAME_SUBSYSTEM        				:=   null;
exec :DESCRIPTION_NLSID     				:=   null;
exec :DESCRIPTION_SUBSYSTEM 				:=   null;
exec :SYSTEM_SEARCH         				:=   1;
exec :EM_PLUGIN_ID          				:=   null;
exec :IS_LOCKED             				:=   0;
exec :SEARCH_DISPLAY_STR    				:=   '''Log Entity'' like ''/var/log/sudo.log*''| stats count by ''User Name (Originating)''';
exec :UI_HIDDEN             				:=   0;
exec :DELETED               				:=   0;
exec :IS_WIDGET             				:=   1;
exec :TENANT_ID             				:=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);


exec :SEARCH_ID             :=   2022;
exec :NAME                  :=   'time';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2022;
exec :NAME                  :=   'TARGET_FILTER';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   2;
exec :PARAM_VALUE_STR       :=   '';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2022;
exec :NAME                  :=   'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'PIE';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=    2022;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_PIE';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=   2022;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganPieChart.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=     2022;
exec :NAME                  :=    'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2023,'Invalid Host User Login Attempts','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Time-based histogram showing count of attempts to log into an invalid or unknown user account',2,1,null,null,null,null,1,null,0,'''Target Type'' = ''Host'' AND (''invalid user'' OR ''user unknown'')',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2023,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/results/loganSearchChartsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2023,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2023,'VISUALIZATION_TYPE_KEY',null,1,'HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2023,'TARGET_FILTER',null,2,'',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2023,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2023,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganHistogram.html',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=    2023;
exec :NAME                                  :=    'Invalid Host User Login Attempts';
exec :OWNER                 				:=    'ORACLE';
exec :CREATION_DATE         				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=    'ORACLE';
exec :DESCRIPTION           				:=    'Time-based histogram showing count of attempts to log into an invalid or unknown user account';
exec :FOLDER_ID             				:=    2;
exec :CATEGORY_ID           				:=    1;
exec :NAME_NLSID            				:=    null;
exec :NAME_SUBSYSTEM        				:=    null;
exec :DESCRIPTION_NLSID     				:=    null;
exec :DESCRIPTION_SUBSYSTEM 				:=    null;
exec :SYSTEM_SEARCH         				:=    1;
exec :EM_PLUGIN_ID          				:=    null;
exec :IS_LOCKED             				:=    0;
exec :SEARCH_DISPLAY_STR    				:=    '''Target Type'' = ''Host'' AND (''invalid user'' OR ''user unknown'')';
exec :UI_HIDDEN             				:=    0;
exec :DELETED               				:=    0;
exec :IS_WIDGET             				:=    1;
exec :TENANT_ID             				:=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=    2023;
exec :NAME                  :=    'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '/js/viewmodel/search/results/loganSearchChartsViewModel.js';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
exec :SEARCH_ID             :=    2023;
exec :NAME                  :=    'time';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
exec :SEARCH_ID             :=    2023;
exec :NAME                  :=    'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2023;
exec :NAME                  :=    'TARGET_FILTER';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    2;
exec :PARAM_VALUE_STR       :=    '';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2023;
exec :NAME                  :=    'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    'LA_WIDGET_HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2023;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganHistogram.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2024,'All Linux Package Lifecycle Activities','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Time-based histogram showing count of package changes (install,update,delete) for all packages',2,1,null,null,null,null,1,null,0,'''Log Entity'' LIKE ''/var/log/yum.log*''',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2024,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2024,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/results/loganSearchChartsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2024,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganHistogram.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2024,'VISUALIZATION_TYPE_KEY',null,1,'HISTOGRAM',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2024,'TARGET_FILTER',null,2,'',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2024,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=    2024;
exec :NAME                                  :=    'All Linux Package Lifecycle Activities';
exec :OWNER                 				:=    'ORACLE';
exec :CREATION_DATE         				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=    SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=    'ORACLE';
exec :DESCRIPTION           				:=    'Time-based histogram showing count of package changes (install,update,delete) for all packages';
exec :FOLDER_ID             				:=    2;
exec :CATEGORY_ID           				:=    1;
exec :NAME_NLSID            				:=    null;
exec :NAME_SUBSYSTEM        				:=    null;
exec :DESCRIPTION_NLSID     				:=    null;
exec :DESCRIPTION_SUBSYSTEM 				:=    null;
exec :SYSTEM_SEARCH         				:=    1;
exec :EM_PLUGIN_ID          				:=    null;
exec :IS_LOCKED             				:=    0;
exec :SEARCH_DISPLAY_STR    				:=    '''Log Entity'' LIKE ''/var/log/yum.log*''';
exec :UI_HIDDEN             				:=    0;
exec :DELETED               				:=    0;
exec :IS_WIDGET             				:=    1;
exec :TENANT_ID             				:=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=   2024;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=  2024;
exec :NAME                  :=  'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  '/js/viewmodel/search/results/loganSearchChartsViewModel.js';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);


exec :SEARCH_ID             :=   2024;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganHistogram.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=  2024;
exec :NAME                  :=  'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  'HISTOGRAM';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
exec :SEARCH_ID             :=   2024;
exec :NAME                  :=   'TARGET_FILTER';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   2;
exec :PARAM_VALUE_STR       :=   '';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2024;
exec :NAME                  :=    'time';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

--
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2025,'Web Server Top Users By Pages (Excluding Assets)','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Graph of top 10 Top Users for application pages excluding requests for external assets such as images, javascript, css files.',2,1,null,null,null,null,1,null,0,'''Log Source'' LIKE "*Access Logs*" and URI != null and "File Extension" not in ("gif","png","jpg","js","css","swf","ico") and URI not like "*/blank.html" | stats count as "Request Count" by "User Name" |top limit=10 "Request Count"',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2025,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2025,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_BAR',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2025,'TARGET_FILTER',null,2,'',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2025,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganBarChart.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2025,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2025,'VISUALIZATION_TYPE_KEY',null,1,'BAR',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=     2025;
exec :NAME                                  :=     'Web Server Top Users By Pages (Excluding Assets)';
exec :OWNER                 				:=     'ORACLE';
exec :CREATION_DATE         				:=     SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=     SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=     'ORACLE';
exec :DESCRIPTION           				:=     'Graph of top 10 Top Users for application pages excluding requests for external assets such as images, javascript, css files.';
exec :FOLDER_ID             				:=     2;
exec :CATEGORY_ID           				:=     1;
exec :NAME_NLSID            				:=     null;
exec :NAME_SUBSYSTEM        				:=     null;
exec :DESCRIPTION_NLSID     				:=     null;
exec :DESCRIPTION_SUBSYSTEM 				:=     null;
exec :SYSTEM_SEARCH         				:=     1;
exec :EM_PLUGIN_ID          				:=     null;
exec :IS_LOCKED             				:=     0;
exec :SEARCH_DISPLAY_STR    				:=     '''Log Source'' LIKE "*Access Logs*" and URI != null and "File Extension" not in ("gif","png","jpg","js","css","swf","ico") and URI not like "*/blank.html" | stats count as "Request Count" by "User Name" |top limit=10 "Request Count"';
exec :UI_HIDDEN             				:=     0;
exec :DELETED               				:=     0;
exec :IS_WIDGET             				:=     1;
exec :TENANT_ID             				:=     '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=  2025;
exec :NAME                  :=  'time';
exec :PARAM_ATTRIBUTES      :=  null;
exec :PARAM_TYPE            :=  1;
exec :PARAM_VALUE_STR       :=  '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=  null;
exec :TENANT_ID             :=  '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2025;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_BAR';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2025;
exec :NAME                  :=   'TARGET_FILTER';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   2;
exec :PARAM_VALUE_STR       :=   '';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2025;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganBarChart.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=    2025;
exec :NAME                  :=    'WIDGET_VIEWMODEL';
exec :PARAM_ATTRIBUTES      :=    null;
exec :PARAM_TYPE            :=    1;
exec :PARAM_VALUE_STR       :=    '/js/viewmodel/search/visualization/loganVisStatsViewModel.js';
exec :PARAM_VALUE_CLOB      :=    null;
exec :TENANT_ID             :=    '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=     2025;
exec :NAME                  :=     'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=     null;
exec :PARAM_TYPE            :=     1;
exec :PARAM_VALUE_STR       :=     'BAR';
exec :PARAM_VALUE_CLOB      :=     null;
exec :TENANT_ID             :=     '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
--Insert into EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,SEARCH_DISPLAY_STR,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID)
--values (2026,'Top Host Log Entries by Service','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE','Distribution of log entries across all monitored hosts by service',2,1,null,null,null,null,1,null,0, '''Target Type''= ''Host'' | STATS COUNT AS ''Log Entries'' BY SERVICE | TOP LIMIT=10 ''Log Entries''',0,0,1,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2026,'VISUALIZATION_TYPE_KEY',null,1,'PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2026,'WIDGET_KOC_NAME',null,1,'LA_WIDGET_PIE',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2026,'WIDGET_TEMPLATE',null,1,'/html/search/widgets/loganPieChart.html',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2026,'WIDGET_VIEWMODEL',null,1,'/js/viewmodel/search/visualization/loganVisStatsViewModel.js',null,'&TENANT_ID');
--Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (2026,'time',null,1,'{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}',null,'&TENANT_ID');
exec :SEARCH_ID                          	:=   2026;
exec :NAME                                  :=   'Top Host Log Entries by Service';
exec :OWNER                 				:=   'ORACLE';
exec :CREATION_DATE         				:=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFICATION_DATE				:=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
exec :LAST_MODIFIED_BY      				:=   'ORACLE';
exec :DESCRIPTION           				:=   'Distribution of log entries across all monitored hosts by service';
exec :FOLDER_ID             				:=   2;
exec :CATEGORY_ID           				:=   1;
exec :NAME_NLSID            				:=   null;
exec :NAME_SUBSYSTEM        				:=   null;
exec :DESCRIPTION_NLSID     				:=   null;
exec :DESCRIPTION_SUBSYSTEM 				:=   null;
exec :SYSTEM_SEARCH         				:=   1;
exec :EM_PLUGIN_ID          				:=   null;
exec :IS_LOCKED             				:=   0;
exec :SEARCH_DISPLAY_STR    				:=    '''Target Type''= ''Host'' | STATS COUNT AS ''Log Entries'' BY SERVICE | TOP LIMIT=10 ''Log Entries''';
exec :UI_HIDDEN             				:=   0;
exec :DELETED               				:=   0;
exec :IS_WIDGET             				:=   1;
exec :TENANT_ID             				:=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH (
SEARCH_ID,
NAME,
OWNER,
CREATION_DATE,
LAST_MODIFICATION_DATE,
LAST_MODIFIED_BY,
DESCRIPTION,
FOLDER_ID,
CATEGORY_ID,
NAME_NLSID,
NAME_SUBSYSTEM,
DESCRIPTION_NLSID,
DESCRIPTION_SUBSYSTEM,
SYSTEM_SEARCH,
EM_PLUGIN_ID,
IS_LOCKED,
SEARCH_DISPLAY_STR,
UI_HIDDEN,
DELETED,
IS_WIDGET,
TENANT_ID)
values (
:SEARCH_ID,
:NAME,
:OWNER,
:CREATION_DATE,
:LAST_MODIFICATION_DATE,
:LAST_MODIFIED_BY,
:DESCRIPTION,
:FOLDER_ID,
:CATEGORY_ID,
:NAME_NLSID,
:NAME_SUBSYSTEM,
:DESCRIPTION_NLSID,
:DESCRIPTION_SUBSYSTEM,
:SYSTEM_SEARCH,
:EM_PLUGIN_ID,
:IS_LOCKED,
:SEARCH_DISPLAY_STR,
:UI_HIDDEN,
:DELETED,
:IS_WIDGET,
:TENANT_ID);

exec :SEARCH_ID             :=     2026;
exec :NAME                  :=     'VISUALIZATION_TYPE_KEY';
exec :PARAM_ATTRIBUTES      :=     null;
exec :PARAM_TYPE            :=     1;
exec :PARAM_VALUE_STR       :=     'PIE';
exec :PARAM_VALUE_CLOB      :=     null;
exec :TENANT_ID             :=     '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2026;
exec :NAME                  :=   'WIDGET_KOC_NAME';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   'LA_WIDGET_PIE';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=   2026;
exec :NAME                  :=   'WIDGET_TEMPLATE';
exec :PARAM_ATTRIBUTES      :=   null;
exec :PARAM_TYPE            :=   1;
exec :PARAM_VALUE_STR       :=   '/html/search/widgets/loganPieChart.html';
exec :PARAM_VALUE_CLOB      :=   null;
exec :TENANT_ID             :=   '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);

exec :SEARCH_ID             :=     2026;
exec :NAME                  :=     'time';
exec :PARAM_ATTRIBUTES      :=     null;
exec :PARAM_TYPE            :=     1;
exec :PARAM_VALUE_STR       :=     '{"type":"relative", "duration":"60", "timeUnit":"MINUTE"}';
exec :PARAM_VALUE_CLOB      :=     null;
exec :TENANT_ID             :=     '&TENANT_ID';

Insert into EMS_ANALYTICS_SEARCH_PARAMS
(
SEARCH_ID,
NAME,
PARAM_ATTRIBUTES,
PARAM_TYPE,
PARAM_VALUE_STR,
PARAM_VALUE_CLOB,
TENANT_ID
) values (
:SEARCH_ID,
:NAME,
:PARAM_ATTRIBUTES,
:PARAM_TYPE,
:PARAM_VALUE_STR,
:PARAM_VALUE_CLOB,
:TENANT_ID
);
insert into EMS_ANALYTICS_LAST_ACCESS(OBJECT_ID,ACCESSED_BY,OBJECT_TYPE,ACCESS_DATE,TENANT_ID) 
select SEARCH_ID,'ORACLE',2,SYS_EXTRACT_UTC(SYSTIMESTAMP),'&TENANT_ID' from EMS_ANALYTICS_SEARCH where search_id>=2000 and search_id<=2999 and TENANT_ID ='&TENANT_ID';
