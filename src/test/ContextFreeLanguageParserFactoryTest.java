import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.AmbiguousParseTableException;
import larp.parser.contextfreelanguage.LL1Parser;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0GotoAction;
import larp.parser.contextfreelanguage.LR0Parser;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parserfactory.contextfreelanguage.ContextFreeLanguageParserFactory;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarSyntaxTokenizerException;
import larp.syntaxtokenizer.contextfreelanguage.IncorrectContextFreeGrammarStatementPrefixException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ContextFreeLanguageParserFactoryTest
{
    @Test
    public void testFactoryCreatesLL1ParserForLL1AndLR0ContextFreeGrammar() throws ContextFreeGrammarSyntaxTokenizerException, AmbiguousParseTableException
    {
        ContextFreeLanguageParserFactory factory = new ContextFreeLanguageParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: \"a\"");

        ContextFreeGrammar expectedCFG = new ContextFreeGrammar();
        expectedCFG.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        LL1ParseTable expectedTable = new LL1ParseTable(expectedCFG);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser expectedParser = new LL1Parser(expectedTable);

        assertEquals(expectedParser, factory.factory(input));
    }

    @Test
    public void testFactoryCreatesLL1ParserForLL1AndNotLR0ContextFreeGrammar() throws ContextFreeGrammarSyntaxTokenizerException, AmbiguousParseTableException
    {
        ContextFreeLanguageParserFactory factory = new ContextFreeLanguageParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: \"a\"");
        input.add("S: \"\"");

        ContextFreeGrammar expectedCFG = new ContextFreeGrammar();
        expectedCFG.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        expectedCFG.addProduction(new NonTerminalNode("S"), new EpsilonNode());
        LL1ParseTable expectedTable = new LL1ParseTable(expectedCFG);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("S"), new EndOfStringNode(), 1);
        LL1Parser expectedParser = new LL1Parser(expectedTable);

        assertEquals(expectedParser, factory.factory(input));
    }

    @Test
    public void testFactoryCreatesLR0ParserForLR0AndNotLL1ContextFreeGrammar() throws ContextFreeGrammarSyntaxTokenizerException, AmbiguousParseTableException
    {
        ContextFreeLanguageParserFactory factory = new ContextFreeLanguageParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: \"a\"\"a\"");
        input.add("S: \"a\"\"b\"");

        ContextFreeGrammar expectedCFG = new ContextFreeGrammar();
        expectedCFG.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("a"));
        expectedCFG.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state5 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable expectedTable = new LR0ParseTable(expectedCFG, state1);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state5));
        expectedTable.addCell(state2, new TerminalNode("a"), new LR0ShiftAction(state3));
        expectedTable.addCell(state2, new TerminalNode("b"), new LR0ShiftAction(state4));
        expectedTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new TerminalNode("b"), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state4, new TerminalNode("a"), new LR0ReduceAction(2));
        expectedTable.addCell(state4, new TerminalNode("b"), new LR0ReduceAction(2));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state5, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser expectedParser = new LR0Parser(expectedTable);

        assertTrue(expectedParser.structureEquals(factory.factory(input)));
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testFactoryThrowsAmbiguousLR0ParseTableExceptionForNonLL1NonLR0ContextFreeGrammar() throws ContextFreeGrammarSyntaxTokenizerException, AmbiguousParseTableException
    {
        ContextFreeLanguageParserFactory factory = new ContextFreeLanguageParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: \"s\"");
        input.add("S: \"s\"");

        factory.factory(input);
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testFactoryThrowsSyntaxTokenizerExceptionForIncorrectContextFreeGrammar() throws ContextFreeGrammarSyntaxTokenizerException, AmbiguousParseTableException
    {
        ContextFreeLanguageParserFactory factory = new ContextFreeLanguageParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("");

        factory.factory(input);
    }
}
