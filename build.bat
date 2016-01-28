@echo off
if not exist "build/classes" mkdir "build/classes"
javac -d build/classes net/caseif/binks/Binks.java
jar cfe build/binks.jar net.caseif.binks.Binks binks.wav LICENSE -C build/classes net/caseif/binks/Binks.class
