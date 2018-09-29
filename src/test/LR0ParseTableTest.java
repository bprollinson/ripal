import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0GotoAction;
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
    @Test
    public void testSize() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0GotoAction(state1));
        parseTable.addCell(state2, new TerminalNode("b"), new LR0GotoAction(state1));

        assertEquals(3, parseTable.size());
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testAddCellThrowsExceptionForCellThatAlreadyExists() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));
    }

    @Test
    public void testAddCellDoesNotThrowExceptionForCellThatDoesNotAlreadyExist() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));
        parseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(state));
    }

    @Test
    public void testEqualsReturnsTrueForEmptyCFGAndNoTableEntries() throws AmbiguousLR0ParseTableException
    {
        LR0ParseTable parseTable = new LR0ParseTable(new ContextFreeGrammar(), null);
        LR0ParseTable otherParseTable = new LR0ParseTable(new ContextFreeGrammar(), null);

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueForNonEmptyCFGAndTableEntries() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, state);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentStartStates()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state);
        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, otherState);

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentCFGs() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        otherCfg.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ParseTable otherParseTable = new LR0ParseTable(otherCfg, state);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentTableEntries() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, state);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(otherState));

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueForSameTableEntriesInDifferentOrder() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));
        parseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(otherState));
        parseTable.addCell(otherState, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, state);
        otherParseTable.addCell(otherState, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(otherState));
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseWhenOrphanedEntriesNotEqual() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(1));

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForEmptyTableAndCFG()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ParseTable parseTable = new LR0ParseTable(cfg, null);

        assertTrue(parseTable.structureEquals(new LR0ParseTable(cfg, null)));
    }

    @Test
    public void testStructureEqualsReturnsTrueForNonEmptyTableAndCFGContainingTerminalInSameSpot() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForNonEmptyTableAndCFGContainingNonTerminalInSameSpot() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, state1);
        otherParseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForNonEmptyTableAndCFGContainingTerminalAndNonTerminalInSameSpots() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        otherParseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentCFGs() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        otherCfg.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ParseTable otherParseTable = new LR0ParseTable(otherCfg, state);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionPresentInOnlyFirstTable() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, state1);

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionPresentInOnlySecondTable() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentActionTypeBetweenTables() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentActionInstanceBetweenTables() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForAnalogousShiftAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state1));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0ShiftAction(otherState1));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForAnalogousReduceAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0ReduceAction(0));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForAnalogousGotoAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0GotoAction(otherState2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForAnalogousAcceptAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0AcceptAction());

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0AcceptAction());

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForTablesContainingCycle() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0GotoAction(state1));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0GotoAction(otherState2));
        otherParseTable.addCell(otherState2, new TerminalNode("a"), new LR0GotoAction(otherState1));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenStartStatesOutOfSync() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, otherState2);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0GotoAction(otherState2));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueWhenOrphanedEntriesNotEqual() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(otherState2, new TerminalNode("a"), new LR0ReduceAction(1));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentActionInstanceAfterShiftAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0ShiftAction(otherState2));
        otherParseTable.addCell(otherState2, new TerminalNode("a"), new LR0ReduceAction(1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentActionInstanceAfterGotoAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0GotoAction(otherState2));
        otherParseTable.addCell(otherState2, new TerminalNode("a"), new LR0ReduceAction(1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForObjectOfOtherType()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        LR0ParseTable parseTable = new LR0ParseTable(cfg, null);

        assertFalse(parseTable.structureEquals(new Object()));
    }
}
