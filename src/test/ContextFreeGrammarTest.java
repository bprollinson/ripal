import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.ProductionNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.parsetable.ContextFreeGrammar;

public class ContextFreeGrammarTest
{
    @Test
    public void testGetStartSymbolReturnsStartSymbol()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        productionNode.addChild(new TerminalNode("a"));
        contextFreeGrammar.addProduction(productionNode);
        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("T"));
        productionNode.addChild(new TerminalNode("b"));
        contextFreeGrammar.addProduction(productionNode);

        assertEquals(new NonTerminalNode("S"), contextFreeGrammar.getStartSymbol());
    }

    @Test
    public void testEqualsReturnsTrueForTwoEmptyCFGs()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsTrueForTwoCFGsWithTheSameProductions()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForTwoCFGSWithDifferentProductions()
    {
        assertEquals(0, 1);
    }
}
