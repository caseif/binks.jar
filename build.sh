mkdir -p build
mkdir -p build/classes
javac -d build/classes net/caseif/binks/Binks.java
jar cfe build/binks.jar net.caseif.binks.Binks binks.wav LICENSE -C build/classes net/caseif/binks/Binks.class
