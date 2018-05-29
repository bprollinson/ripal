import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;

public class LR0ParseTableTest
{
    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testAddCellThrowsExceptionForShiftActionWhenTableContainsShiftForTheSameStateAndSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
    }

    @Test
    public void testAddCellDoesNotThrowExceptionForShiftActionWhenTableContainsShiftForTheSameStateAndDifferentSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
        parseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(0));
    }

    @Test
    public void testAddCellDoesNotThrowExceptionForShiftActionWhenTableContainsShiftForDifferentStateAndTheSameSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
        parseTable.addCell(otherState, new TerminalNode("a"), new LR0ShiftAction(0));
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testAddCellThrowsExceptionForReduceActionWhenTableContainsReduceForTheSameState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(state, new TerminalNode("b"), new LR0ReduceAction(1));
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testAddCellThrowsExceptionForShiftActionWhenTableContainsReduceActionWithTheSameState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(0));
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testAddCellThrowsExceptionForReduceActionWhenTableContainsShiftActionWithTheSameState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
        parseTable.addCell(state, new TerminalNode("b"), new LR0ReduceAction(0));
    }

    @Test
    public void testAddDoesNotThrowExceptionForShiftActionWhenTableContainsReduceActionWithDifferentState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(otherState, new TerminalNode("a"), new LR0ShiftAction(0));
    }

    @Test
    public void testAddCellDoesNotThrowExceptionForReduceActionWhenTableContainsShiftActionWithDifferentState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
        parseTable.addCell(otherState, new TerminalNode("a"), new LR0ReduceAction(0));
    }

    @Test
    public void testEqualsReturnsTrueForEmptyCFGAndNoTableEntries() throws AmbiguousLR0ParseTableException
    {
        LR0ParseTable parseTable = new LR0ParseTable(new ContextFreeGrammar());
        LR0ParseTable otherParseTable = new LR0ParseTable(new ContextFreeGrammar());

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueForNonEmptyCFGAndTableEntries() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
        parseTable.addCell(otherState, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
        otherParseTable.addCell(otherState, new TerminalNode("a"), new LR0ReduceAction(0));

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentCFGs() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        otherCfg.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ParseTable otherParseTable = new LR0ParseTable(otherCfg);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentShiftTableStates() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg);
        otherParseTable.addCell(otherState, new TerminalNode("a"), new LR0ShiftAction(0));

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentShiftTableSymbols() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg);
        otherParseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(0));

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentReduceTableStates() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg);
        otherParseTable.addCell(otherState, new TerminalNode("a"), new LR0ReduceAction(0));

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueForSameTableEntriesInDifferentOrder() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
        parseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(1));
        parseTable.addCell(otherState, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg);
        otherParseTable.addCell(otherState, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(1));
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testCellsEqualOtherTableReturnsTrueWhenCellsMatchExpectedCells()
    {
        assertTrue(false);
    }

    @Test
    public void testCellsEqualOtherTableReturnsFalseWhenCellsDoNotMatchExpectedCells()
    {
        assertTrue(false);
    }

    @Test
    public void testRowsEqualOtherTableReturnsTrueWhenRowsMatchExpectedRows()
    {
        assertTrue(false);
    }

    @Test
    public void testRowsEqualOtherTableReturnsFalseWhenRowsDoNotMatchExpectedRows()
    {
        assertTrue(false);
    }
}
