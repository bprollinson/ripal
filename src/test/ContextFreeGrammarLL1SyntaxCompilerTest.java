import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.automaton.contextfreelanguage.ContextFreeGrammarLL1SyntaxCompiler;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.ProductionNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.parsetable.ContextFreeGrammar;
import larp.parsetable.LL1ParseTable;

public class ContextFreeGrammarLL1SyntaxCompilerTest
{
    @Test
    public void testCompileReturnsParseTableForSingleCharacterProductionCFG()
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        productionNode.addChild(new TerminalNode("a"));
        grammar.addProduction(productionNode);
        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertEquals(expectedTable, compiler.compile(grammar));
    }
}
