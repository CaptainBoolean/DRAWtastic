# DRAWtastic
## Git Setup Test
### Resourcen
JavaFX: https://gluonhq.com/products/javafx/  
Git: Download in InteliJ, beim Clonen

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
- JavaFX installieren und zu Liberies hinzufügen: https://www.youtube.com/watch?v=cyHeYdydbJQ
- Language Level auf 22 stellen: File -> Project Structure -> Modules -> Sources -> Language Level
- Byte Code auf 22 stellen: File -> Settings -> Java Compliler -> Module -> Multimedia Gruppe -> Bytecode von 8 auf 22 stellen
- Beispielprogramm sollte laufen

### Eigene Branch
- Erstelle deine eigene Branche im VSC Menü von Intellij mit **new Branch**
- Schreib deinen Namen in das print bei **stage.setTitle("Name Branch!");**
- Push deine Änderungen auf Git mit den Befehlen:
```bash
git add .
git commit -m "Meine Branch implementiert"
git push origin DeineBranchName
```
Upstream setten: Gegebenen Terminal Befehl ausführen

### Neue Änderungen von der Main Branch pullen (Am besten immer vor der Coding Session ausführen)
```bash
git checkout DeineBranch # Wechselt auf deine Branch
git fetch origin      # Holt die neuesten Änderungen von GitHub auf deine lokale main
git merge origin/main # Merged main in deine Branch
```
Bei Merge Konflikte -> weinen gehen bzw. manuell lösen und danach:
```bash
git add .   
git commit 
```

### Deine Änderungen auf Main pushen
- Als erstes sicher stellen das man die neueste main in der eigenen Branch hat, eventuell vorherigen Schritt wiederholen  
- Code umbedingt nochmal testen falls es eine neue main gibt  
Danach:
```bash
git checkout main 
git merge DeineBranch
git push origin main
```
Bei Merge Konflikten wieder manuell lösen

