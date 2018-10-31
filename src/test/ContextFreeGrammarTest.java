import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

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
    public void testGetStartSymbolReturnsNullForEmptyCFG()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();

        assertNull(contextFreeGrammar.getStartSymbol());
    }

    @Test
    public void testEqualsReturnsTrueForTwoEmptyCFGs()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        ContextFreeGrammar expectedCfg = new ContextFreeGrammar();

        assertEquals(expectedCfg, cfg);
    }

    @Test
    public void testEqualsReturnsTrueForTwoCFGsWithTheSameProductions()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        ProductionNode node = new ProductionNode();
        node.addChild(new NonTerminalNode("S"));
        node.addChild(new TerminalNode("a"));
        cfg.addProduction(node);

        ContextFreeGrammar expectedCfg = new ContextFreeGrammar();
        ProductionNode expectedNode = new ProductionNode();
        expectedNode.addChild(new NonTerminalNode("S"));
        expectedNode.addChild(new TerminalNode("a"));
        expectedCfg.addProduction(expectedNode);

        assertEquals(expectedCfg, cfg);
    }

    @Test
    public void testEqualsReturnsFalseForTwoCFGSWithDifferentProductions()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        ProductionNode node = new ProductionNode();
        node.addChild(new NonTerminalNode("S"));
        node.addChild(new TerminalNode("a"));
        cfg.addProduction(node);
        node = new ProductionNode();
        node.addChild(new NonTerminalNode("S"));
        node.addChild(new TerminalNode("b"));
        cfg.addProduction(node);

        ContextFreeGrammar expectedCfg = new ContextFreeGrammar();
        ProductionNode expectedNode = new ProductionNode();
        expectedNode.addChild(new NonTerminalNode("S"));
        expectedNode.addChild(new TerminalNode("a"));
        expectedCfg.addProduction(expectedNode);
        expectedNode = new ProductionNode();
        expectedNode.addChild(new NonTerminalNode("S"));
        expectedNode.addChild(new TerminalNode("c"));
        expectedCfg.addProduction(expectedNode);

        assertNotEquals(expectedCfg, cfg);
    }
}
