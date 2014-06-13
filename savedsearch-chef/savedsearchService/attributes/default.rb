#
# Cookbook Name::datasource 
#

default["installer"]["download_dir"] = "/scratch/downloads"
default["base_dir"] = "/opt/ORCLemaas"

# basic directories
default["infra_dir"] = "#{node["base_dir"]}/InfrastructureSoftware"
default["platform_dir"] = "#{node["base_dir"]}/PlatformServices"
default["apps_dir"] = "#{node["base_dir"]}/Applications"

# java directories
default["jdk_dir"] = "#{node["infra_dir"]}/java"
default["java_home"] = "#{node["jdk_dir"]}/jdk1.7.0_51"

# wls and db attributes 
default["file"]["domain"] = "/tmp/createDomain.py"
default["mid_home"] = "#{node["infra_dir"]}/WLS12c"
default["oracle_home"] = "#{node["mid_home"]}/oracle_home"
default["wls_home"] = "#{node["oracle_home"]}/wlserver"
default["wlsuser"]="weblogic"
default["wlspassword"]="password1"
default["myApplicationName"]="emaas-applications-savedsearch-ear"
default["target"]="AdminServer"

default["dbhome"]="#{node["infra_dir"]}/rdbms/12.1.0" 
default["oracle_base"] = "/scratch/oracle"
default["SAAS_datasourcename"]="emaas_savesearch_ds"
default["SAAS_jndiname"]="jdbc/emaas_savesearch_ds"
default["weblogic_servername"]="AdminServer"
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

