full:
	make clean
	make program
	make jar
	make clean
program:
	javac ./LARP.java
test:
	make clean
	javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar ./*Test.java
	java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore StateMachineTest
	java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore RegularExpressionSyntaxTokenizerTest
	java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore RegularExpressionSyntaxTokenSequenceAssertionTest
	java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore RegularExpressionSyntaxParserTest
	make clean
clean:
	find . -name "*.class" -exec rm {} \;
	find . -name "*.java~" -exec rm {} \;
jar:
	rm -f ./LARP.jar
	jar cvfm LARP.jar Manifest.txt LARP.java LARP.class larp
run:
	java -jar LARP.jar
