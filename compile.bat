@echo off

dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
del sources.txt

@rem Create JAR file for the client
jar cvfe jars\Client.jar Client -C out\client .

@rem Create JAR file for the server
jar cvfe jars\Server.jar Server -C out\server .
