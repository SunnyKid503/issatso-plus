@echo off
echo Starting ISSATSo++ Core Microservices...

start "Notification Service" cmd /c java -jar notificationManagement\target\notificationManagement-0.0.1-SNAPSHOT.jar
start "Forum Service" cmd /c java -jar forumManagement\target\forum-management-0.0.1-SNAPSHOT.jar
start "User Service" cmd /c java -jar userManagement\target\user-management-0.0.1-SNAPSHOT.jar

echo Waiting 40 seconds for services to start...
timeout /t 40 /nobreak >nul

echo Starting API Gateway...
start "API Gateway" cmd /c java -jar api-gate\target\api-gate-0.0.1-SNAPSHOT.jar

echo All services started successfully!
