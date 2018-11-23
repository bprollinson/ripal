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
	javac -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar -Xlint:unchecked @sources.txt
	rm sources.txt
	java -cp src/main:src/test:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore larp.automaton.FiniteAutomataTest larp.automaton.DFATest larp.grammartokenizer.regularlanguage.ExpressionIntermediateNestingLevelValidAssertionTest larp.grammartokenizer.regularlanguage.ExpressionFinalNestingLevelValidAssertionTest larp.grammartokenizer.regularlanguage.ExpressionFinalEscapingStatusValidAssertionTest larp.grammartokenizer.regularlanguage.TokenizerTest larp.token.regularlanguage.EpsilonTokenTest larp.token.regularlanguage.OrTokenTest larp.token.regularlanguage.CloseParenthesisTokenTest larp.token.regularlanguage.OpenParenthesisTokenTest larp.token.regularlanguage.KleeneClosureTokenTest larp.token.regularlanguage.CharacterTokenTest larp.grammarparser.regularlanguage.GrammarParserTest larp.parsetree.regularlanguage.CharacterNodeTest larp.parsetree.regularlanguage.KleeneClosureNodeTest larp.parsetree.regularlanguage.OrNodeTest larp.parsetree.regularlanguage.ConcatenationNodeTest larp.automaton.NFATest larp.automaton.EpsilonNFATest larp.automaton.StateTest larp.automaton.StateTransitionTest larp.automaton.DFAStateTest larp.parsercompiler.regularlanguage.ParserCompilerTest larp.parsercompiler.regularlanguage.EpsilonNFAToNFAConverterTest larp.parsercompiler.regularlanguage.NFAToDFAConverterTest larp.token.contextfreelanguage.SeparatorTokenTest larp.token.contextfreelanguage.NonTerminalTokenTest larp.token.contextfreelanguage.TerminalTokenTest larp.token.contextfreelanguage.EpsilonTokenTest larp.grammartokenizer.contextfreelanguage.TokenizerTest larp.grammartokenizer.contextfreelanguage.GrammarFinalQuoteNestingCorrectAssertionTest larp.grammartokenizer.contextfreelanguage.GrammarStartingTokensValidAssertionTest larp.grammartokenizer.contextfreelanguage.GrammarCorrectNumberOfSeparatorsAssertionTest larp.grammartokenizer.contextfreelanguage.GrammarEscapeCharacterPositionCorrectAssertionTest larp.parsetree.contextfreelanguage.ProductionNodeTest larp.parsetree.contextfreelanguage.EpsilonNodeTest larp.parsetree.contextfreelanguage.NonTerminalNodeTest larp.parsetree.contextfreelanguage.TerminalNodeTest larp.parsetree.contextfreelanguage.ConcatenationNodeTest larp.parsetree.contextfreelanguage.EndOfStringNodeTest larp.parsetree.contextfreelanguage.DotNodeTest larp.grammarparser.contextfreelanguage.GrammarParserTest larp.grammar.contextfreelanguage.GrammarTest larp.parser.contextfreelanguage.LL1ParseTableCellAvailableAssertionTest larp.parser.contextfreelanguage.LL1ParseTableTest larp.util.PairToValueMapTest larp.parser.contextfreelanguage.LR0ShiftActionTest larp.parser.contextfreelanguage.LR0ReduceActionTest larp.parser.contextfreelanguage.LR0GotoActionTest larp.parser.contextfreelanguage.LR0AcceptActionTest larp.parser.contextfreelanguage.LR0ParseTableCellAvailableAssertionTest larp.parser.contextfreelanguage.LR0ParseTableTest larp.parser.contextfreelanguage.LL1ParserTest larp.parser.contextfreelanguage.LR0ParseStackTest larp.parser.contextfreelanguage.LR0ParserTest larp.util.ValueToSetMapTest larp.parsercompiler.contextfreelanguage.FirstSetCalculatorTest larp.parsercompiler.contextfreelanguage.FollowSetCalculatorTest larp.parsercompiler.contextfreelanguage.LL1ParserCompilerTest larp.parser.contextfreelanguage.LR0ProductionSetDFATest larp.parser.contextfreelanguage.LR0ProductionSetDFAStateTest larp.parsercompiler.contextfreelanguage.LR0ParserCompilerTest larp.parsercompiler.contextfreelanguage.GrammarAugmentorTest larp.parsercompiler.contextfreelanguage.ProductionNodeDotRepositoryTest larp.parsercompiler.contextfreelanguage.GrammarClosureCalculatorTest larp.parsercompiler.contextfreelanguage.LR0ProductionSetDFACompilerTest larp.parserfactory.regularlanguage.ParserFactoryTest larp.parserfactory.contextfreelanguage.ParserFactoryTest
	make clean
clean:
	find . -name "*.class" -exec rm {} \;
	find . -name "*.java~" -exec rm {} \;
jar:
	rm -f ./LARP.jar
	jar cvfm LARP.jar Manifest.txt LARP.java LARP.class -C ./src/main larp
run:
	java -jar LARP.jar
