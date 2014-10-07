#!/bin/bash

PROJDIR=`pwd`

echo "Deploying App"
jar=${PROJDIR}/target/cask-to-0.1-SNAPSHOT.jar
curl -H "X-Archive-Name: caskto" -X POST "http://localhost:10000/v2/apps" --data-binary @"$jar"
echo

echo "Done deploying."

echo "Starting Procedures..."
curl -X POST "http://localhost:10000/v2/apps/caskto/procedures/url-manager/start"
curl -X POST "http://localhost:10000/v2/apps/caskto/procedures/url-redirector/start"
curl -X POST "http://localhost:10000/v2/apps/caskto/procedures/analytics/start"

sleep 2

echo -n "URL Manager Procedure: "
curl "http://localhost:10000/v2/apps/caskto/procedures/url-manager/status"
echo
echo -n "URL Redirector Procedure: "
curl "http://localhost:10000/v2/apps/caskto/procedures/url-redirector/status"
echo
echo -n "Analytics Procedure: "
curl "http://localhost:10000/v2/apps/caskto/procedures/analytics/status"
echo

echo "Procedures Started."

echo "Starting Webapp..."
curl -X POST "http://localhost:10000/v2/apps/caskto/webapp/start"
echo

sleep 2

echo -n "Webapp: "
curl "http://localhost:10000/v2/apps/caskto/webapp/status"
echo

echo "Webapp Started.  Done."
