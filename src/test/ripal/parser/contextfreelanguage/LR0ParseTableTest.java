/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parser.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ripal.automaton.State;
import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;
import ripal.util.PairToValueMap;

public class LR0ParseTableTest
{
    @Test
    public void testSize() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0GotoAction(state1));
        parseTable.addCell(state2, new TerminalNode("b"), new LR0GotoAction(state1));

        assertEquals(3, parseTable.size());
    }

    @Test
    public void testGetStartState()
    {
        Grammar grammar = new Grammar();
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);

        assertEquals(state, parseTable.getStartState());
    }

    @Test
    public void testAddCellThrowsExceptionForShiftActionWhenCellContainsReduceAction() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        assertThrows(
            LR0ShiftReduceConflictException.class,
            () -> {
                parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state2));
            }
        );
    }

    @Test
    public void testAddCellThrowsExceptionForReduceActionWhenCellContainsShiftAction() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state2));

        assertThrows(
            LR0ShiftReduceConflictException.class,
            () -> {
                parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));
            }
        );
    }

    @Test
    public void testAddCellThrowsExceptionForReduceActionWhenCellContainsReduceAction() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        assertThrows(
            LR0ReduceReduceConflictException.class,
            () -> {
                parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));
            }
        );
    }

    @Test
    public void testAddCellThrowsExceptionForOtherTypeOfConflict() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0AcceptAction());

        assertThrows(
            LR0OtherConflictException.class,
            () -> {
                parseTable.addCell(state, new TerminalNode("a"), new LR0AcceptAction());
            }
        );
    }

    @Test
    public void testAddCellDoesNotThrowExceptionForCellThatDoesNotAlreadyExist() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));
        parseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(state));
    }

    @Test
    public void testCellsEqualReturnsTrue() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        PairToValueMap<State, Node, LR0ParseTableAction> cells = new PairToValueMap<State, Node, LR0ParseTableAction>();
        cells.put(state, new TerminalNode("a"), new LR0ShiftAction(state));

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertTrue(parseTable.cellsEqual(cells));
    }

    @Test
    public void testCellsEqualReturnsFalse() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState = new LR0ClosureRuleSetDFAState("", false);

        PairToValueMap<State, Node, LR0ParseTableAction> cells = new PairToValueMap<State, Node, LR0ParseTableAction>();
        cells.put(state, new TerminalNode("a"), new LR0ShiftAction(state));

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(otherState));

        assertFalse(parseTable.cellsEqual(cells));
    }

    @Test
    public void testEqualsReturnsTrueForEmptyGrammarAndNoTableEntries() throws AmbiguousLR0ParseTableException
    {
        LR0ParseTable parseTable = new LR0ParseTable(new Grammar(), null);
        LR0ParseTable otherParseTable = new LR0ParseTable(new Grammar(), null);

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueForNonEmptyGrammarAndTableEntries() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentStartStates()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState);

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentGrammars() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        Grammar otherGrammar = new Grammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ParseTable otherParseTable = new LR0ParseTable(otherGrammar, state);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentTableEntries() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(otherState));

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueForSameTableEntriesInDifferentOrder() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));
        parseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(otherState));
        parseTable.addCell(otherState, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state);
        otherParseTable.addCell(otherState, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(state, new TerminalNode("b"), new LR0ShiftAction(otherState));
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseWhenOrphanedEntriesNotEqual() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(1));

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForTableWithDifferentClass()
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);

        assertFalse(parseTable.equals(new Object()));
    }

    @Test
    public void testStructureEqualsReturnsTrueForEmptyTableAndGrammar()
    {
        Grammar grammar = new Grammar();

        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);

        assertTrue(parseTable.structureEquals(new LR0ParseTable(grammar, null)));
    }

    @Test
    public void testStructureEqualsReturnsTrueForNonEmptyTableAndGrammarContainingTerminalInSameSpot() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForNonEmptyTableAndGrammarContainingNonTerminalInSameSpot() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForNonEmptyTableAndGrammarContainingTerminalAndNonTerminalInSameSpots() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        otherParseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentGrammars() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        Grammar otherGrammar = new Grammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ParseTable otherParseTable = new LR0ParseTable(otherGrammar, state);
        otherParseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionWithoutTransitionPresentInOnlyFirstTable() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionWithoutTransitionPresentInOnlySecondTable() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionWithTransitionPresentInOnlyFirstTable() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionWithTransitionPresentInOnlySecondTable() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForActionInSameRowPresentinOnlyFirstTable() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);

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
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);

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
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

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
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

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
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("b"), new LR0ReduceAction(0));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentActionTypeBetweenTables() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentShiftActionInstanceBetweenTables() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentReduceActionInstanceBetweenTables() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentGotoActionInstanceBetweenTables() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state1);
        otherParseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForAnalogousShiftAction() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ClosureRuleSetDFAState otherState1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0ShiftAction(otherState2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForAnalogousReduceAction() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState1 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0ReduceAction(0));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForAnalogousGotoAction() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ClosureRuleSetDFAState otherState1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0GotoAction(otherState2));

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForAnalogousAcceptAction() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState1 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0AcceptAction());

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0AcceptAction());

        assertTrue(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueForTablesContainingCycle() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ClosureRuleSetDFAState otherState1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState2 = new LR0ClosureRuleSetDFAState("", false);

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
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ClosureRuleSetDFAState otherState1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState3 = new LR0ClosureRuleSetDFAState("", false);

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
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);

        LR0ClosureRuleSetDFAState otherState1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState2 = new LR0ClosureRuleSetDFAState("", false);

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
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ClosureRuleSetDFAState otherState1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState2);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0GotoAction(otherState2));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsTrueWhenOrphanedEntriesNotEqual() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ClosureRuleSetDFAState otherState1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState2 = new LR0ClosureRuleSetDFAState("", false);

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
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ClosureRuleSetDFAState otherState1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState2 = new LR0ClosureRuleSetDFAState("", false);

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
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ClosureRuleSetDFAState otherState1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0GotoAction(state2));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherState1);
        otherParseTable.addCell(otherState1, new TerminalNode("a"), new LR0GotoAction(otherState2));
        otherParseTable.addCell(otherState2, new TerminalNode("a"), new LR0ReduceAction(1));

        assertFalse(parseTable.structureEquals(otherParseTable));
    }

    @Test
    public void testStructureEqualsReturnsFalseForParseTableWithDifferentClass()
    {
        Grammar grammar = new Grammar();
        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);

        assertFalse(parseTable.structureEquals(new LL1ParseTable(grammar)));
    }
}
