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
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);
        contextFreeGrammar.addProduction(productionNode);
        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parseTable.accepts("ab"));
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
    public void testAcceptsReturnsTrueForTerminalAndNonterminalProductionChainDependingOnCollectionPrefixing()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        concatenationNode.addChild(new NonTerminalNode("C"));
        productionNode.addChild(concatenationNode);
        contextFreeGrammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        productionNode.addChild(concatenationNode);
        concatenationNode.addChild(new TerminalNode("ab"));
        contextFreeGrammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("C"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("c"));
        productionNode.addChild(concatenationNode);
        contextFreeGrammar.addProduction(productionNode);

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        parseTable.addCell(new NonTerminalNode("C"), new TerminalNode("c"), 2);

        assertTrue(parseTable.accepts("abc"));
    }

    @Test
    public void testEqualsReturnsTrueForEmptyCFGAndNoTableEntries()
    {
        LL1ParseTable parseTable = new LL1ParseTable(new ContextFreeGrammar());
        LL1ParseTable otherParseTable = new LL1ParseTable(new ContextFreeGrammar());

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueForNonEmptyCFGAndTableEntries()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        productionNode.addChild(new TerminalNode("a"));
        cfg.addProduction(productionNode);
        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        ProductionNode otherProductionNode = new ProductionNode();
        otherProductionNode.addChild(new NonTerminalNode("S"));
        otherProductionNode.addChild(new TerminalNode("a"));
        otherCfg.addProduction(otherProductionNode);
        LL1ParseTable otherParseTable = new LL1ParseTable(cfg);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentCFGs()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        productionNode.addChild(new TerminalNode("a"));
        cfg.addProduction(productionNode);
        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        ProductionNode otherProductionNode = new ProductionNode();
        otherProductionNode.addChild(new NonTerminalNode("S"));
        otherProductionNode.addChild(new TerminalNode("b"));
        otherCfg.addProduction(otherProductionNode);
        LL1ParseTable otherParseTable = new LL1ParseTable(cfg);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentTableEntries()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        productionNode.addChild(new TerminalNode("a"));
        cfg.addProduction(productionNode);
        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        ProductionNode otherProductionNode = new ProductionNode();
        otherProductionNode.addChild(new NonTerminalNode("S"));
        otherProductionNode.addChild(new TerminalNode("a"));
        otherCfg.addProduction(otherProductionNode);
        LL1ParseTable otherParseTable = new LL1ParseTable(cfg);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueForSameTableEntriesInDifferentOrder()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        productionNode.addChild(new TerminalNode("a"));
        cfg.addProduction(productionNode);
        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        ProductionNode otherProductionNode = new ProductionNode();
        otherProductionNode.addChild(new NonTerminalNode("S"));
        otherProductionNode.addChild(new TerminalNode("a"));
        otherCfg.addProduction(otherProductionNode);
        LL1ParseTable otherParseTable = new LL1ParseTable(cfg);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parseTable.equals(otherParseTable));
    }
}
