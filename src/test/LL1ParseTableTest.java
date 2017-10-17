import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ConcatenationNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.grammar.contextfreelanguage.ProductionNode;
import larp.parsetable.ContextFreeGrammar;
import larp.parsetable.LL1ParseTable;

public class LL1ParseTableTest
{
    @Test
    public void testAcceptsReturnsTrueForCorrectCharacterInSingleCharacterCFG()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        contextFreeGrammar.addProduction(productionNode);
        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parseTable.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsTrueForIncorrectCharacterInSingleCharacterCFG()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        contextFreeGrammar.addProduction(productionNode);
        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertFalse(parseTable.accepts("b"));
    }

    @Test
    public void testAcceptsReturnsTrueForMultiCharacterCFG()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("ab"));
        productionNode.addChild(concatenationNode);
        contextFreeGrammar.addProduction(productionNode);
        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parseTable.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsTrueForMultiCharacterCFGUsingMultipleTerminalNodes()
    {
        assertTrue(false);
    }

    @Test
    public void testAcceptsReturnsFalseForCharacterMismatchInMultiCharacterCFG()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("ab"));
        productionNode.addChild(concatenationNode);
        contextFreeGrammar.addProduction(productionNode);
        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertFalse(parseTable.accepts("ac"));
    }

    @Test
    public void testAcceptsReturnsFalseForUnmatchedInputCharacter()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        contextFreeGrammar.addProduction(productionNode);
        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertFalse(parseTable.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsFalseForUnmatchedCFGCharacter()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("ab"));
        productionNode.addChild(concatenationNode);
        contextFreeGrammar.addProduction(productionNode);
        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertFalse(parseTable.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsTrueForNonterminalProductionChainDependingOnCollectionOrder()
    {
        assertTrue(false);
    }
}
