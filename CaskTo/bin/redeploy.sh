#!/bin/bash

PROJDIR=`pwd`

echo "Stopping everything"
echo "Stopping Procedures..."
curl -X POST "http://localhost:10000/v2/apps/caskto/procedures/url-manager/stop"
curl -X POST "http://localhost:10000/v2/apps/caskto/procedures/url-redirector/stop"
curl -X POST "http://localhost:10000/v2/apps/caskto/procedures/analytics/stop"

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

echo "Procedures Stopped"

echo "Stopping Webapp..."
curl -X POST "http://localhost:10000/v2/apps/caskto/webapp/stop"

sleep 2

echo -n "Webapp: "
curl "http://localhost:10000/v2/apps/caskto/webapp/status"
echo

echo "Webapp Stopped.  Done Stopping."

echo "Deleting App"

curl -X DELETE "http://localhost:10000/v2/apps/caskto"

sleep 2
echo "Deleted.  Continue?"
read

echo "Redeploying App"
jar=${PROJDIR}/target/cask-to-0.1-SNAPSHOT.jar
curl -H "X-Archive-Name: caskto" -X POST "http://localhost:10000/v2/apps" --data-binary @"$jar"
echo

echo "Done redeploying."

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
