@echo off

dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
del sources.txt

@rem Create JAR file for the client
jar cfe jars\Client.jar client.Client -C out .

@rem Create JAR file for the server
jar cfe jars\Server.jar server.Server -C out .

@rem Remove the out directory
rmdir /s /q out
