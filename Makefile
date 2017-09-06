full:
	make clean
	make program
	make jar
	make clean
program:
	javac ./*.java
clean:
	find . -name "*.class" -exec rm {} \;
	find . -name "*.java~" -exec rm {} \;
jar:
	rm -f ./LARP.jar
	jar cvfm LARP.jar Manifest.txt LARP.java LARP.class
run:
	java -jar LARP.jar
