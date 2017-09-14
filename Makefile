full:
	make clean
	make program
	make jar
	make clean
program:
	javac -cp ./src/main ./*.java
test:
	make clean
	javac -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar ./src/test/*Test.java
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore StateMachineTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore RegularExpressionSyntaxTokenizerTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore RegularExpressionSyntaxTokenSequenceAssertionTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore RegularExpressionSyntaxParserTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore CharacterNodeTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore KleeneClosureNodeTest
	make clean
clean:
	find . -name "*.class" -exec rm {} \;
	find . -name "*.java~" -exec rm {} \;
jar:
	rm -f ./LARP.jar
	jar cvfm LARP.jar Manifest.txt LARP.java LARP.class -C ./src/main larp
run:
	java -jar LARP.jar
