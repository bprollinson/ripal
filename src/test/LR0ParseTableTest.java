import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ParseTableAction;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.util.PairToValueMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class LR0ParseTableTest
{
    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testAddCellThrowsExceptionForCellThatAlreadyExists() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
    }

    @Test
    public void testAddCellDoesNotThrowExceptionForCellThatDoesNotAlreadyExist() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
        parseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(0));
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
    public void testRowsEqualReturnsTrueWhenRowsMatchExpectedRows() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        Map<State, LR0ParseTableAction> otherRows = new HashMap<State, LR0ParseTableAction>();
        otherRows.put(state, new LR0ReduceAction(0));

        assertTrue(parseTable.rowsEqual(otherRows));
    }

    @Test
    public void testRowsEqualIgnoresCells() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(otherState, new TerminalNode("a"), new LR0ShiftAction(0));

        Map<State, LR0ParseTableAction> otherRows = new HashMap<State, LR0ParseTableAction>();
        otherRows.put(state, new LR0ReduceAction(0));

        assertTrue(parseTable.rowsEqual(otherRows));
    }

    @Test
    public void testRowsEqualReturnsFalseWhenRowsDoNotMatchExpectedRows() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        Map<State, LR0ParseTableAction> otherRows = new HashMap<State, LR0ParseTableAction>();
        otherRows.put(state, new LR0ReduceAction(1));

        assertFalse(parseTable.rowsEqual(otherRows));
    }

    @Test
    public void testRowsEqualOtherTableReturnsTrueWhenRowsMatchExpectedRows() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        assertTrue(parseTable.rowsEqualOtherTable(otherParseTable));
    }

    @Test
    public void testRowsEqualOtherTableIgnoresCells() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(otherState, new TerminalNode("a"), new LR0ShiftAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(otherState, new TerminalNode("a"), new LR0ShiftAction(1));

        assertTrue(parseTable.rowsEqualOtherTable(otherParseTable));
    }

    @Test
    public void testRowsEqualOtherTableReturnsFalseWhenRowsDoNotMatchExpectedRows() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(1));

        assertFalse(parseTable.rowsEqualOtherTable(otherParseTable));
    }

    @Test
    public void testCellsEqualReturnsTrueWhenCellsMatchExpectedCells() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> otherCells = new PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction>();
        otherCells.put(state, new TerminalNode("a"), new LR0ShiftAction(0));

        assertTrue(parseTable.cellsEqual(otherCells));
    }

    @Test
    public void testCellsEqualIgnoresRows() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
        parseTable.addCell(otherState, new TerminalNode("b"), new LR0ReduceAction(0));

        PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> otherCells = new PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction>();
        otherCells.put(state, new TerminalNode("a"), new LR0ShiftAction(0));

        assertTrue(parseTable.cellsEqual(otherCells));
    }

    @Test
    public void testCellsEqualReturnsFalseWhenCellsDoNotMatchExpectedCells() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> otherCells = new PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction>();
        otherCells.put(state, new TerminalNode("a"), new LR0ShiftAction(1));

        assertFalse(parseTable.cellsEqual(otherCells));
    }

    @Test
    public void testCellsEqualOtherTableReturnsTrueWhenCellsMatchExpectedCells() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        assertTrue(parseTable.cellsEqualOtherTable(otherParseTable));
    }

    @Test
    public void testCellsEqualOtherTableIgnoresRows() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
        parseTable.addCell(otherState, new TerminalNode("b"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));
        otherParseTable.addCell(otherState, new TerminalNode("b"), new LR0ReduceAction(1));

        assertTrue(parseTable.cellsEqualOtherTable(otherParseTable));
    }

    @Test
    public void testCellsEqualOtherTableReturnsFalseWhenCellsDoNotMatchExpectedCells() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(1));

        assertFalse(parseTable.cellsEqualOtherTable(otherParseTable));
    }
}
