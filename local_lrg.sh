#!/bin/csh
set buildID = "rex_local_lrg_428"
set uploadPath = "/net/slc00bqs/scratch/download/may2/ssf_20_lrgs_428"
set workPath = "temp"
set lrgs=("emcpssf_l0_test" "emcpssf_l1_test" "emcpssf_test_3n")
set start = 1
set end = 2
git pull
gradle clean build artifactoryPublish -PBuildID=$buildID -PlrgMetadata=1 -x test
echo "[gradle clean build artifactoryPublish] success"
setenv SKIP_OMCSETUP true
echo "SKIP_OMCSETUP $SKIP_OMCSETUP" 
setenv SKIP_OMCTEARDOWN 1
echo "SKIP_OMCSETUP $SKIP_OMCTEARDOWN" 

set j = $start
while ( $j <= $end )
  foreach lrg ($lrgs)
    rm -f ${workPath}/${lrg}_run${j}.log
    touch ${workPath}/${lrg}_run${j}.log
    echo "Run lrg: $lrg in $j times"
    echo "Run lrg: $lrg in $j times" >> ${workPath}/${lrg}_run${j}.log
    rm -rf $GRADLE_USER_HOME/* 
    rm -rf oracle/work/*
    cp /net/den00zyr/scratch/emaas/setuplogs/emaas.properties.log oracle/work
    echo "Workspace is cleaned."
    #sqlplus 'SYSEMS_T_3/welcome1@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=den00zys.us.oracle.com)(Port=1521))(CONNECT_DATA=(SID=orcl12c)))'  @df_clean_lrg.sql
    #echo "DF Database is cleaned."
    #sqlplus 'SYSEMS_T_9/welcome1@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=den00zys.us.oracle.com)(Port=1521))(CONNECT_DATA=(SID=orcl12c)))' @ssf_clean_lrg.sql
    #echo "SSF Database is cleaned."
	echo "database den00zys cleaning ..."
	gradle cleanUpDB4LocalLRG -PcleanDB=den00zys
	echo "database cleaned"
    gradle do_lrg -Plrgs=$lrg -PREPO_BID=$buildID
    ls oracle/work/*.dif >> ${workPath}/${lrg}_run${j}.log
    cp -r oracle ${workPath}/${lrg}_run${j}
    zip -r ${workPath}/${lrg}_run${j}.zip  ${workPath}/${lrg}_run${j}
    cp ${workPath}/${lrg}_run${j}.zip ${uploadPath}/${lrg}_run${j}.zip
    chmod 777 ${uploadPath}/${lrg}_run${j}.zip
    echo "Success upload ${uploadPath}/${lrg}_run${j}.zip"
    echo "Success upload ${uploadPath}/${lrg}_run${j}.zip" >> ${workPath}/${lrg}_run${j}.log
  end
@ j++
end


