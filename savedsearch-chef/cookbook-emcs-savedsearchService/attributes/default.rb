#
# Cookbook Name::datasource 
#

default["run_dir"] = "/var/opt/ORCLemaas"
default["installer"]["download_dir"] = "#{node["run_dir"]}/downloads"
default["oracle_base"] = "#{node["run_dir"]}/oracle"
default["base_dir"] = "/opt/ORCLemaas"
default["log_dir"] = "#{node["run_dir"]}/logs"

# basic directories
default["infra_dir"] = "#{node["base_dir"]}/InfrastructureSoftware"
default["platform_dir"] = "#{node["base_dir"]}/PlatformServices"
default["apps_dir"] = "#{node["base_dir"]}/Applications"

# java directories
default["jdk_dir"] = "#{node["infra_dir"]}/java"
default["java_home"] = "#{node["jdk_dir"]}/jdk1.7.0_51"

default["SAAS_API_version"] = "0.1"

default["file_domain_create"] = "#{node["log_dir"]}/savedSearchCreateDomain.py"

default["myApplicationName"]="emaas-applications-savedsearch-ear"
default["target"]="#{node["wls_adminserver_name"]}"

default["dbhome"]="#{node["infra_dir"]}/rdbms/12.1.0" 
default["oracle_base"] = "#{node["run_dir"]}/oracle"
default["SAAS_datasourcename"]="emaas_savesearch_ds"
default["SAAS_jndiname"]="jdbc/emaas_savesearch_ds"
default["sql_bundle"]="emaas-applications-savedsearch-schema-"
default["sql_dir"]="emaas-applications-savedsearch-schema"

#if db connection parameters are to be obtained by db entity lookup, set is_db_lookup to true
default["is_db_lookup"]="true"

#Default values for DB connection. If is_db_lookup is set to true, these values are overriden in the recipe
default["db_sid"]="orcl11g"
default["db_port"]="1521"
default["db_connectinfo"]="jdbc:oracle:thin:@#{node["db_host"]}:1521:#{node["db_sid"]}"
default["SAAS_schema_user"]="EMSAAS_SAVEDSRCH"
default["SAAS_schema_password"]="welcome1"
default["sys_user"]="sys"
default["sys_password"]="welcome1"
default["database_driver"] ="oracle.jdbc.OracleDriver"
default["db_host"]="hostname.us.oracle.com"
default["db_service"]="orcl11g.us.oracle.com"

default["SAAS_entityNamingDomain"]="DatabaseTenantMapping"
default["SAAS_entityNamingKey"]="tenantid"

default["SAAS_earSelfCheck"]="emaas"
default["SAAS_earfile"]="emaas-applications-savedsearch-ear-"
default["SAAS_deploymentUuid"]="0"


#To make SQL available to the LCMRepManager

default["SAAS_Service_parent_dir"]="#{node["apps_dir"]}"
default["SAAS_schema_archive_filename"]="#{node["sql_bundle"]}#{node["SAAS_version"]}.tgz"
default["SAAS_schema_sql_root_dir"]="#{node["SAAS_Service_parent_dir"]}/#{node["SAAS_servicename"]}/#{node["SAAS_version"]}/sql/"

default["SAAS_current_version"]="0.0.0"

