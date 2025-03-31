# Multimeda-Paint-Project
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

### Eigene Branch
- Erstelle deine eigene Branche im VSC Menü von Intellij mit **new Branch**
- Schreib deinen Namen in das print bei **stage.setTitle("<name> Branch!");**
- Push deine Änderungen auf Git mit den Befehlen:
```bash
git add .
git commit -m "Meine Branch implementiert"
git push origin DeineBranchName
```
Upstream setten: Gegebenen Terminal Befehl ausführen

