cd /d %~dp0
java -Dwebdriver.gecko.driver="chromedriver.exe" -jar selenium-server-standalone-3.141.59.jar -role node -nodeConfig node.json