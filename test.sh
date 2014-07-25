#!/bin/sh

clear
echo "Changing the directory to savedsearch-ear"
cd savedsearch-ear
echo "Changed directory to: `pwd`"
echo "      "
echo "Cleaning the build directory"
echo "      "
gradle clean
echo "Building an ear and deploying it on env"
gradle clean build appServerDeploy
echo "Running Tests...."
echo "      "
cd ..; gradle --continue savedsearch-core:runDEVTestSuite savedsearch-web:runQATestSuite
echo "      "
echo "Undeploying ....."
gradle appServerUndeploy
echo "My Job Is Over"
