import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLL1ParseTableException;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

public class LL1ParseTableTest
{
    @Test(expected = AmbiguousLL1ParseTableException.class)
    public void testAddCellThrowsExceptionForCellThatAlreadyExists() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
    }

    @Test
    public void testAddCellDoesNotThrowExceptionForCellThatDoesNotAlreadyExist() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
    }

    @Test
    public void testEqualsReturnsTrueForEmptyCFGAndNoTableEntries()
    {
        LL1ParseTable parseTable = new LL1ParseTable(new ContextFreeGrammar());
        LL1ParseTable otherParseTable = new LL1ParseTable(new ContextFreeGrammar());

        assertEquals(otherParseTable, parseTable);
    }

    @Test
    public void testEqualsReturnsTrueForNonEmptyCFGAndTableEntries() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        otherCfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherCfg);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertEquals(otherParseTable, parseTable);
    }

    @Test
    public void testEqualsReturnsFalseForDifferentCFGs() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        otherCfg.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherCfg);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertNotEquals(otherParseTable, parseTable);
    }

    @Test
    public void testEqualsReturnsFalseForDifferentTableEntries() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        otherCfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherCfg);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);

        assertNotEquals(otherParseTable, parseTable);
    }

    @Test
    public void testEqualsReturnsTrueForSameTableEntriesInDifferentOrder() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        otherCfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherCfg);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertEquals(otherParseTable, parseTable);
    }
}
