SHELL:=/bin/bash

full:
	make clean
	make program
	make jar
	make clean
program:
	javac -cp ./src/main -Xlint:unchecked ./*.java
test:
	make clean
	find src/test -name "*.java" > ./sources.txt
	javac -cp src/main:src/test:junit-platform-console-standalone-1.10.2.jar -Xlint:unchecked @sources.txt
	rm sources.txt
	java -jar junit-platform-console-standalone-1.10.2.jar execute --class-path src/main:src/test --select-package ripal
	make clean
clean:
	find . -name "*.class" -exec rm {} \;
	find . -name "*.java~" -exec rm {} \;
jar:
	rm -f ./RIPAL.jar
	jar cvfm RIPAL.jar Manifest.txt RIPAL.java RIPAL.class -C ./src/main ripal
run:
	java -jar RIPAL.jar
