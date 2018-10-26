import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.AmbiguousParseTableException;
import larp.parser.contextfreelanguage.LL1Parser;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parserfactory.contextfreelanguage.ContextFreeLanguageParserFactory;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarSyntaxTokenizerException;
import larp.syntaxtokenizer.contextfreelanguage.IncorrectContextFreeGrammarStatementPrefixException;

import java.util.ArrayList;
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
    public void testFactoryCreatesLR0ParserForLR0AndNotLL1ContextFreeGrammar()
    {
        throw new RuntimeException();
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
