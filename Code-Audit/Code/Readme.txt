Kurzanleitung zum Ausführen des Prototypen

1. Server 
Vorrausetzungen zum Starten des Servers:
-Node.js v6.9.0
-Redis (Windows Version 3.2.100) 

Befehl zum Starten des Servers:
node server.js

2.Client
Vorraussetzungen zum Starten des Clients:
-Android Studio v.2.2.2 mit Google Play Services und Google Repository

-Android VD mit Neuxs 5 API 21 oder Genymotion mit Nexus 5 API 21

Getestet wurde die Client App mit Genymotion.

Für die Verwendung von AVD von Android Studio:
Den String BASE_URL in der Datei 

/app/java/de.schulzgette.thes_o_naise/utils/HttpClient

ändern von "http://10.0.3.2:3000/" (GENYMOTION)
	zu "http://10.0.2.2:3000/" (AVD)

_____________________________________________________________________



