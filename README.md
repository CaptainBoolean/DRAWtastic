# DRAWtastic
## Git Setup Test

### Repository clonen
- Einladung zum Git Repository annehmen
- Code -> HTTPS-Link kopieren
- IntelliJ -> new Project -> from Version Controll -> Link einfügen
- Kontrollieren mit Befehlen:
```bash
git --version
```
oder
```bash
git status
```
Eventuell Einstellungen wie Name und Email konfigurieren, einfach Terminalbefehle folgen.  
Wenn git command nicht erkannt wird -> PC neu starten wegen PATH!

### Code zum laufen bringen
- Alle sollten das gleiche JDK verwenden. Projekt läuft mit JDK 22, weil JDK 24 Warnings schmeißt. Kann ez mit IntelliJ installiert werden.
- JavaFX sollte durch Maven bereits im Project vorhanden sein. Gegebenenfalls bei Project Structure -> Modules -> Dependencies -> alle Maven JavaFX Module anhaken.
- Language Level auf 22 stellen: File -> Project Structure -> Modules -> Sources -> Language Level
- Byte Code auf 22 stellen: File -> Settings -> Java Compliler -> Module -> Multimedia Gruppe -> Bytecode von 8 auf 22 stellen
- Code sollte laufen  

## Vor deiner Coding Session
### Neue Änderungen von der Main Branch pullen
```bash
git checkout DeineBranch # Wechselt auf deine Branch
git fetch origin      # Holt die neuesten Änderungen von GitHub auf deine lokale main
git merge origin main # Merged main in deine Branch
```  

## Nach deiner Coding Session
### Push die Änderungen DEINER BRANCH auf deine Git Branch mit den Befehlen:
```bash
git checkout DeineBranch # Wechselt auf deine Branch
git add . # Gibt deine Änderungen in eine "Mappe"
git commit -m "Was auch immer du implementiert hast" # Beschriftet die Mappe
git push # Gibt die Mappe ab
```
- Diesen Schritt kann man nicht oft genug machen, lieber einmal zuviel pushen als zuwenig
- **ACHTUNG! Nicht die Main checkouten bevor man nicht auf seine eigene Branch gepushed hat. DEIN CODE WIRD SONST RESETTED!**
  
### Merge deine Änderungen auf die Main 
- Als erstes sicher stellen das man die neueste main in der eigenen Branch hat, eventuell vorherigen Schritt wiederholen  
- Code umbedingt nochmal testen, Main muss clean bleiben!
```bash
git checkout main # Wechselt auf Main
git merge DeineBranch
git push origin main
```
-Bei Merge Konflikte -> weinen gehen bzw. manuell lösen
