/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0GotoAction;
import larp.parser.contextfreelanguage.LR0OtherConflictException;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ParseTableAction;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ReduceReduceConflictException;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parser.contextfreelanguage.LR0ShiftReduceConflictException;
import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.util.PairToValueMap;

import java.util.HashSet;

public class LR0ParseTableTest
{
    @Test
    public void testSize() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0GotoAction(state1));
        parseTable.addCell(state2, new TerminalNode("b"), new LR0GotoAction(state1));

        assertEquals(3, parseTable.size());
    }

    @Test
    public void testGetStartState()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);

        assertEquals(state, parseTable.getStartState());
    }

    @Test(expected = LR0ShiftReduceConflictException.class)
    public void testAddCellThrowsExceptionForShiftActionWhenCellContainsReduceAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state2));
    }

    @Test(expected = LR0ShiftReduceConflictException.class)
    public void testAddCellThrowsExceptionForReduceActionWhenCellContainsShiftAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));
    }

    @Test(expected = LR0ReduceReduceConflictException.class)
    public void testAddCellThrowsExceptionForReduceActionWhenCellContainsReduceAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));
    }

    @Test(expected = LR0OtherConflictException.class)
    public void testAddCellThrowsExceptionForOtherTypeOfConflict() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0AcceptAction());
        parseTable.addCell(state, new TerminalNode("a"), new LR0AcceptAction());
    }

    @Test
    public void testAddCellDoesNotThrowExceptionForCellThatDoesNotAlreadyExist() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));
        parseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(state));
    }

    @Test
    public void testCellsEqualReturnsTrue() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> cells = new PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction>();
        cells.put(state, new TerminalNode("a"), new LR0ShiftAction(state));

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertTrue(parseTable.cellsEqual(cells));
    }

    @Test
    public void testCellsEqualReturnsFalse() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> cells = new PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction>();
        cells.put(state, new TerminalNode("a"), new LR0ShiftAction(state));

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(otherState));

        assertFalse(parseTable.cellsEqual(cells));
    }

    @Test
    public void testEqualsReturnsTrueForEmptyCFGAndNoTableEntries() throws AmbiguousLR0ParseTableException
    {
        LR0ParseTable parseTable = new LR0ParseTable(new ContextFreeGrammar(), null);
        LR0ParseTable otherParseTable = new LR0ParseTable(new ContextFreeGrammar(), null);

        assertEquals(otherParseTable, parseTable);
    }

    @Test
    public void testEqualsReturnsTrueForNonEmptyCFGAndTableEntries() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertEquals(otherParseTable, parseTable);
    }

    @Test
    public void testEqualsReturnsFalseForDifferentStartStates()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState);

        assertNotEquals(otherParseTable, parseTable);
    }

    @Test
    public void testEqualsReturnsFalseForDifferentCFGs() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        ContextFreeGrammar otherGrammar = new ContextFreeGrammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ParseTable otherParseTable = new LR0ParseTable(otherGrammar, state);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertNotEquals(otherParseTable, parseTable);
    }

    @Test
    public void testEqualsReturnsFalseForDifferentTableEntries() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(otherState));

        assertNotEquals(otherParseTable, parseTable);
    }

    @Test
    public void testEqualsReturnsTrueForSameTableEntriesInDifferentOrder() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));
        parseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(otherState));
        parseTable.addCell(otherState, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state);
        otherParseTable.addCell(otherState, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(otherState));
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertEquals(otherParseTable, parseTable);
    }

    @Test
    public void testEqualsReturnsFalseWhenOrphanedEntriesNotEqual() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(1));

        assertNotEquals(otherParseTable, parseTable);
    }

    @Test
    public void testEqualsReturnsFalseForTableWithDifferentClass()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);

        assertNotEquals(new Object(), parseTable);
    }

    @Test
    public void testStructureEqualsReturnsTrueForEmptyTableAndCFG()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);

        assertTrue(parseTable.structureEquals(new LR0ParseTable(grammar, null)));
    }

    @Test
    public void testStructureEqualsReturnsTrueForNonEmptyTableAndCFGContainingTerminalInSameSpot() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForNonEmptyTableAndCFGContainingNonTerminalInSameSpot() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForNonEmptyTableAndCFGContainingTerminalAndNonTerminalInSameSpots() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        otherParseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentCFGs() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        ContextFreeGrammar otherGrammar = new ContextFreeGrammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ParseTable otherParseTable = new LR0ParseTable(otherGrammar, state);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionWithoutTransitionPresentInOnlyFirstTable() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionWithoutTransitionPresentInOnlySecondTable() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionWithTransitionPresentInOnlyFirstTable() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionWithTransitionPresentInOnlySecondTable() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionInSameRowPresentinOnlyFirstTable() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(state1, new TerminalNode("b"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionInSameRowPresentinOnlySecondTable() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(state1, new TerminalNode("b"), new LR0ReduceAction(0));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForCycleMultipleTimesAsLongInFirstTable() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ShiftAction(state1));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForCycleMultipleTimesAsLongInSecondTable() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state1));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ShiftAction(state1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionWithDifferentInputBetweenTables() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("b"), new LR0ReduceAction(0));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentActionTypeBetweenTables() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentShiftActionInstanceBetweenTables() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentReduceActionInstanceBetweenTables() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentGotoActionInstanceBetweenTables() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForAnalogousShiftAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0ShiftAction(otherState2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForAnalogousReduceAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0ReduceAction(0));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForAnalogousGotoAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0GotoAction(otherState2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForAnalogousAcceptAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0AcceptAction());

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0AcceptAction());

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForTablesContainingCycle() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0GotoAction(state1));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0GotoAction(otherState2));
        otherParseTable.addCell(otherState2, new TerminalNode("a"), new LR0GotoAction(otherState1));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForUnmatchedCycleInFirstTable() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0GotoAction(state1));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0GotoAction(otherState2));
        otherParseTable.addCell(otherState2, new TerminalNode("a"), new LR0GotoAction(otherState3));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForUnmatchedCycleInSecondTable() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0GotoAction(state3));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0GotoAction(otherState2));
        otherParseTable.addCell(otherState2, new TerminalNode("a"), new LR0GotoAction(otherState1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenStartStatesOutOfSync() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState2);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0GotoAction(otherState2));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueWhenOrphanedEntriesNotEqual() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(otherState2, new TerminalNode("a"), new LR0ReduceAction(1));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentActionInstanceAfterShiftAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0ShiftAction(otherState2));
        otherParseTable.addCell(otherState2, new TerminalNode("a"), new LR0ReduceAction(1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentActionInstanceAfterGotoAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0GotoAction(otherState2));
        otherParseTable.addCell(otherState2, new TerminalNode("a"), new LR0ReduceAction(1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForObjectWithDifferentClass()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);

        assertFalse(parseTable.structureEquals(new Object()));
    }
}
