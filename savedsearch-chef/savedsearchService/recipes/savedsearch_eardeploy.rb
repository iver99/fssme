#
# Cookbook Name::dataService
# Recipe::dataservice_datasource
#
# This recipe creates the datasource and deploys the ear file in weblogic server
#

ruby_block "set_JAVA_HOME" do
  block do
    ENV['JAVA_HOME']=node["java_home"]
  end
  action :create
end

ruby_block "set_ENVs" do
  block do
    ENV['SAAS_REGISTRY_URLS']=node["serviceUrls"]
  end
  action :create
end

wls_home       =  node["wls_home"]
wls_usr        =  node["wlsuser"]
wls_pwd        =  node["wlspassword"]
wls_host       =  node["fqdn"]
wls_server     =  node["weblogic_servername"]
wls_datasource =  node["SAAS_datasourcename"]
wls_jndiname   =  node["SAAS_jndiname"]
db_driver      =  node["database_driver"]
db_password    = node["SAAS_schema_password"]
db_user        = node["SAAS_schema_user"]
is_lookup      = node["is_db_lookup"]

ruby_block "set_MW_HOME" do
  block do
    ENV['MW_HOME']=node["oracle_home"]
  end
  action :create
end

template "#{node["file"]["domain"]}" do
  source "createDomain.py.erb"
 only_if do
   ! File.exists?(node["file"]["domain"])
 end
end

# Create Central Inventory
bash "run_domain_script" do
  cwd "/tmp"
  code <<-EOF
  cd #{node["wls_home"]}/common/bin
  sh commEnv.sh
  #{node["wls_home"]}/common/bin/wlst.sh #{node["file"]["domain"]}
  EOF
  only_if do
      File.exists?(node["file"]["domain"]) && ! File.directory?("#{node["oracle_home"]}/user_projects/domains/base_domain")
  end
end

bash "start_server" do
  # user lazy {  %x( echo -n `env | grep ^HOME | cut -d '=' -f2 | cut -d '/' -f3` )  }
  cwd "#{node["oracle_home"]}/user_projects/domains/base_domain"
  code <<-EOF
  #{node["db_connectinfo"]} ="jdbc:oracle:thin:@#{node["db_host"]}:#{node["db_port"]}:#{node["db_sid"]}"
   # temporarily switch off -e option because following command returns 1 if server is down
    set +e
    status=`lwp-request -P http://#{node["hostname"]}:7001 | grep "Error 404--Not Found"`
    return_status=$?
    if  [ -n "$status" ]; then
        echo -e "\nWebLogic Server already running. But exiting with 0 status so that next step executes." >> /tmp/datasource.log
        exit 0
    else
        echo "\nWebLogic Server not running. Starting WebLogic server..." >> /tmp/datasource.log
    fi

    # set the environment variables
    # switch on -e again
    set -e
    echo "nohup #{node["oracle_home"]}/user_projects/domains/base_domain/startWebLogic.sh" >> /tmp/datasource.log 
    nohup #{node["oracle_home"]}/user_projects/domains/base_domain/startWebLogic.sh >> /tmp/startWebLogic.log 2>&1 &

    nohup_status=$?
    echo "nohup_status=$nohup_status"
    sleep 1m
  EOF
end

ruby_block "get database entity" do
  block do
    if is_lookup == "true"
    node.default["databaseInfos"] = `cat /tmp/savedSearchDatabaseLookup.out`
    databaseInfos =  node["databaseInfos"]
    databaseInfos = databaseInfos.tr("\n","").split(/;\s*/)
    for databaseInfo in databaseInfos
      index = databaseInfo.index(':')
      if index > 0 and index < databaseInfo.length-1
        infokey = databaseInfo[0,index]
        infovalue = databaseInfo[index+1, databaseInfo.length-index-1]
        puts "debugging with "+infokey+":"+infovalue
        if infokey == "db_host"
          node.default["db_host"] = infovalue
        elsif infokey == "db_port"
          node.default["db_port"] = infovalue
        elsif infokey == "db_sid"
          node.default["db_sid"] = infovalue
        elsif infokey == "db_service"
          node.default["db_service"] = infovalue
        end
      end
    end
    node.default["db_connectinfo"] = "jdbc:oracle:thin:@#{node["db_host"]}:#{node["db_port"]}:#{node["db_sid"]}"
  end
  end
  action :create
end

template "/tmp/wls_datasources.py" do
    source "wls_datasources.py.erb"
    action :create
    variables lazy {{
        :weblogic_username => wls_usr,
        :weblogic_password => wls_pwd,
        :weblogic_host => wls_host,
        :weblogic_port => "7001",
        :weblogic_servername => wls_server,
        :weblogic_datasource => wls_datasource,
        :weblogic_jndiname => wls_jndiname,
        :database_url => node["db_connectinfo"],
        :database_driver => db_driver,
        :database_username => db_user,
        :database_password => db_password
}}
end

bash "create_wls_datasource" do
  code <<-EOH
    echo "\n--------------------Create Data Source-------------------------">> /tmp/datasource.log
    set -e
    echo "Executing command:" >> /tmp/datasource.log 
    echo "#{wls_home}/common/bin/wlst.sh /tmp/wls_datasources.py" >> /tmp/datasource.log 
    #{wls_home}/common/bin/wlst.sh /tmp/wls_datasources.py >> /tmp/datasource.log 
  EOH
end

directory "#{node["oracle_home"]}/user_projects/domains/config" do
  action :create
  only_if do
    ! File.exists?("#{node["oracle_home"]}/user_projects/domains/config")
  end
end

bash "create_servicemanger_properties_file"  do
  code <<-EOH
		echo "Creating SM properties file in #{node["apps_dir"]}/#{node["SAAS_servicename"]}/init"
    mkdir -p #{node["apps_dir"]}/#{node["SAAS_servicename"]}/init
    cd #{node["apps_dir"]}/#{node["SAAS_servicename"]}/init
    echo "version=#{node["SAAS_version"]}" > servicemanager.properties
    echo "serviceName=SavedSearch" >> servicemanager.properties
    echo "registryUrls=$SAAS_REGISTRY_URLS" >> servicemanager.properties
    echo "serviceUrls=$SAAS_REGISTRY_URLS" >> servicemanager.properties
    EOH
end

bash "deploy_ear" do
  code <<-EOH

    echo "----------------------Deploying EAR-----------------------------">> /tmp/datasource.log
    echo "\n hostname= #{node["hostname"]}, fqdn=  #{node["fqdn"]}" >> /tmp/datasource.log
    set +e
    # Check if service is online
    curl -s -o out.html -w '%{http_code}' "http://#{node["hostname"]}:7001/emaas/savedsearch/v1" | grep 200
    if [ $? -eq 0 ]; then
    echo "Application is already deployed" >> /tmp/datasource.log
    exit 0
    else
    export JAVA_HOME=#{node["jdk_dir"]}/jdk1.7.0_51

    echo "#{node["jdk_dir"]}/jdk1.7.0_51/bin/java -cp #{node["wls_home"]}/server/lib/weblogic.jar weblogic.Deployer -username #{node["wlsuser"]} -password #{node["wlspassword"]} -url t3://#{node["hostname"]}:7001 -name #{node["myApplicationName"]} -deploy -targets #{node["target"]} -source #{node["apps_dir"]}/#{node["SAAS_servicename"]}/#{node["SAAS_version"]}/#{node["SAAS_earfile"]}"#{node["SAAS_version"]}.ea#{node["SAAS_version"]}.earr >> /tmp/datasource.log

    #{node["jdk_dir"]}/jdk1.7.0_51/bin/java -cp #{node["wls_home"]}/server/lib/weblogic.jar weblogic.Deployer -username #{node["wlsuser"]} -password #{node["wlspassword"]} -url t3://#{node["hostname"]}:7001 -name #{node["myApplicationName"]} -deploy -targets #{node["target"]} -source #{node["apps_dir"]}/#{node["SAAS_servicename"]}/#{node["SAAS_version"]}/#{node["SAAS_earfile"]}#{node["SAAS_version"]}.ear >> /tmp/datasource.log
    fi
  EOH
end
