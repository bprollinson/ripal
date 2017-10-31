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
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore DFATest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore RegularExpressionSyntaxTokenizerTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore RegularExpressionSyntaxTokenSequenceAssertionTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore RegularExpressionSyntaxParserTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore CharacterNodeTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore KleeneClosureNodeTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore EpsilonNFATest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore StateTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore RegularExpressionSyntaxCompilerTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore EpsilonNFAToNFAConverterTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore NFAToDFAConverterTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore SeparatorTokenTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore NonTerminalTokenTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore TerminalTokenTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore EpsilonTokenTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore ContextFreeGrammarSyntaxTokenizerTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore ContextFreeGrammarStartingTokenAssertionTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore ProductionNodeTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore NonTerminalNodeTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore TerminalNodeTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore ConcatenationNodeTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore ContextFreeGrammarSyntaxParserTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore ContextFreeGrammarTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore LL1ParseTableTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore FirstSetCalculatorTest
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore ContextFreeGrammarLL1SyntaxCompilerTest
	make clean
clean:
	find . -name "*.class" -exec rm {} \;
	find . -name "*.java~" -exec rm {} \;
jar:
	rm -f ./LARP.jar
	jar cvfm LARP.jar Manifest.txt LARP.java LARP.class -C ./src/main larp
run:
	java -jar LARP.jar
