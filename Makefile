full:
	make clean
	make program
	make jar
	make clean
program:
	javac ./*.java
test:
	make clean
	javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar ./SampleTest.java
	java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore SampleTest
	make clean
clean:
	find . -name "*.class" -exec rm {} \;
	find . -name "*.java~" -exec rm {} \;
jar:
	rm -f ./LARP.jar
	jar cvfm LARP.jar Manifest.txt LARP.java LARP.class larp
run:
	java -jar LARP.jar
