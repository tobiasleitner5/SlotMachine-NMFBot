# SlotMachine-NMFBot
Der NMF-Bot liegt im zeitlichen Ablauf der SlotMachine vor der AU-Bot und dient der Generierung der Slots und Flüge, welche der AU-Bot später als Input erhält. Details zur Implementierung und ein detailliertes Benutzerhandbuch befindet sich in der Bachelorarbeit "Implementierung zweier Bots für die Testdatengenerierung im Rahmen des Projekts SlotMachine".

## Installation und Start
Der AU-Bot wurde mit der Java Version 17 implementiert. Es wird deshalb die Verwendung einer JVMVersion 17 oder höher empfohlen. 
Für die Verwendung des AU-Bots ist es notwendig, zusätzlich das Projekt 
[SlotMachine-Objects](https://github.com/jku-win-dke/SlotMachine-Objects) zu installieren. 
Zudem kann der NMF-Bot nicht gestartet werden, wenn der [Controller](https://github.com/jku-win-dke/SlotMachine-Controller) nicht erreichbar ist. 
Dieser muss also auf einem anderen Host bereits laufen oder ebenfalls lokal gestartet werden. 

## Konfigurationsmöglichkeiten
- Port
- [config.json](https://github.com/tobiasleitner5/SlotMachine-NMFBot/blob/master/config/config.json)
System Properties: Über die System Properties wird spezifiziert, in welchem Verzeichnis sich die Konfigdatei befindet und auf welchem Host/Port der Controller gestartet wurde. Standardmäßig: -Dcontroller="http://localhost:8090" -Dconfig="config/config.json" 

## Funktionsweise
- Bot starten: Der Bot wird gestartet und es wird eine Flugliste (EnvelopeDTO), auf Basis der angegebenen Daten in der [Konfigurationsdatei](https://github.com/tobiasleitner5/SlotMachine-NMFBot/blob/master/config/config.json), erstellt.
- Abfrage der Flugliste vom Controller.
- Lösungsliste erhalten.
- Lösung akzeptieren.
