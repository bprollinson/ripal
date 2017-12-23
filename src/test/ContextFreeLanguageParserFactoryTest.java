import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLL1ParseTableException;
import larp.parser.contextfreelanguage.LL1Parser;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parserfactory.contextfreelanguage.ContextFreeLanguageParserFactory;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarSyntaxTokenizerException;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeLanguageParserFactoryTest
{
    @Test
    public void testFactoryCreatesDFAForRegularExpression() throws ContextFreeGrammarSyntaxTokenizerException, AmbiguousLL1ParseTableException
    {
        ContextFreeLanguageParserFactory factory = new ContextFreeLanguageParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: AB");
        input.add("A: \"a\"");
        input.add("B: \"b\"");
        LL1Parser parser = factory.factory(input);

        ContextFreeGrammar expectedCFG = new ContextFreeGrammar();
        expectedCFG.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        expectedCFG.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        expectedCFG.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));
        LL1ParseTable expectedTable = new LL1ParseTable(expectedCFG);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("B"), new TerminalNode("b"), 1);
        LL1Parser expectedParser = new LL1Parser(expectedTable);
        assertEquals(expectedParser, parser);
    }
}
