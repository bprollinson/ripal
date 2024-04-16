/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parser.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parsetree.contextfreelanguage.EndOfStringNode;
import ripal.parsetree.contextfreelanguage.EpsilonNode;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class LR0ParserTest
{
    @Test
    public void testAcceptsReturnsTrueForCorrectCharacterInSingleCharacterGrammar() throws AmbiguousLR0ParseTableException
    {
        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertTrue(parser.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsFalseForIncorrectCharacterInSingleCharacterGrammar() throws AmbiguousLR0ParseTableException
    {
        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts("b"));
    }

    @Test
    public void testAcceptsReturnsTrueForMultiCharacterGrammarUsingMultipleTerminalNodes() throws AmbiguousLR0ParseTableException
    {
        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        parseTable.addCell(state2, new TerminalNode("b"), new LR0ShiftAction(state3));
        parseTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state3, new TerminalNode("b"), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertTrue(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsDoesNotEquateCharactersWithMultiCharacterTerminalNode() throws AmbiguousLR0ParseTableException
    {
        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("ab"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("ab"), new LR0ReduceAction(1));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsFalseForUnmatchedInputCharacter() throws AmbiguousLR0ParseTableException
    {
        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsFalseForUnmatchedGrammarCharacter() throws AmbiguousLR0ParseTableException
    {
        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        parseTable.addCell(state2, new TerminalNode("b"), new LR0ShiftAction(state3));
        parseTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state3, new TerminalNode("b"), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsTrueForEmptyString() throws AmbiguousLR0ParseTableException
    {
        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state2));
        parseTable.addCell(state1, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertTrue(parser.accepts(""));
    }

    @Test
    public void testAcceptsReturnsFalseForEmptyStringWhenGrammarIsEmpty()
    {
        Grammar augmentedGrammar = new Grammar();

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, null);

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts(""));
    }

    @Test
    public void testAcceptsReturnsFalseForNonEmptyStringWhenGrammarIsEmpty()
    {
        Grammar augmentedGrammar = new Grammar();

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, null);

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsFalseWhenReduceActionRunsOutOfSymbols() throws AmbiguousLR0ParseTableException
    {
        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state2, new TerminalNode("b"), new LR0ReduceAction(1));

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsFalseWhenNextStateNotFoundInStack() throws AmbiguousLR0ParseTableException
    {
        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("A"), new LR0ReduceAction(1));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(2));

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts("a"));
    }

    @Test
    public void testGetAppliedRulesReturnsEmptyListBeforeParseIsRun() throws AmbiguousLR0ParseTableException
    {
        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("a"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        parseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(2));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(2));
        parseTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesOnSuccessfulParse() throws AmbiguousLR0ParseTableException
    {
        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("a"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        parseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(2));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(2));
        parseTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);
        parser.accepts("a");

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();
        expectedRuleIndexes.add(0);
        expectedRuleIndexes.add(1);

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesUntilTableLookupFailure() throws AmbiguousLR0ParseTableException
    {
        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state5 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state6 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("a"));
        augmentedGrammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("b"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state6));
        parseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(2));
        parseTable.addCell(state2, new TerminalNode("b"), new LR0ReduceAction(2));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(2));
        parseTable.addCell(state3, new TerminalNode("b"), new LR0ShiftAction(state5));
        parseTable.addCell(state3, new NonTerminalNode("B"), new LR0GotoAction(state4));
        parseTable.addCell(state4, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state4, new TerminalNode("b"), new LR0ReduceAction(1));
        parseTable.addCell(state4, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state5, new TerminalNode("a"), new LR0ReduceAction(3));
        parseTable.addCell(state5, new TerminalNode("b"), new LR0ReduceAction(3));
        parseTable.addCell(state5, new EndOfStringNode(), new LR0ReduceAction(3));
        parseTable.addCell(state6, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);
        parser.accepts("a");

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();
        expectedRuleIndexes.add(1);

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testStructureEqualsReturnsTrueWhenParseTablesHaveSameStructure() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ClosureRuleSetDFAState startState = new LR0ClosureRuleSetDFAState("", false);
        LR0ParseTable parseTable = new LR0ParseTable(grammar, startState);
        parseTable.addCell(startState, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(startState, new TerminalNode("b"), new LR0ReduceAction(1));
        LR0Parser parser = new LR0Parser(parseTable);

        LR0ClosureRuleSetDFAState otherStartState = new LR0ClosureRuleSetDFAState("", false);
        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherStartState);
        otherParseTable.addCell(otherStartState, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(otherStartState, new TerminalNode("b"), new LR0ReduceAction(1));
        LR0Parser otherParser = new LR0Parser(otherParseTable);

        assertTrue(parser.structureEquals(otherParser));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenParseTablesHaveDifferentStructure() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ClosureRuleSetDFAState startState = new LR0ClosureRuleSetDFAState("", false);
        LR0ParseTable parseTable = new LR0ParseTable(grammar, startState);
        parseTable.addCell(startState, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(startState, new TerminalNode("b"), new LR0ReduceAction(1));
        LR0Parser parser = new LR0Parser(parseTable);

        LR0ClosureRuleSetDFAState otherStartState = new LR0ClosureRuleSetDFAState("", false);
        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, otherStartState);
        otherParseTable.addCell(otherStartState, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(otherStartState, new TerminalNode("b"), new LR0ReduceAction(0));
        LR0Parser otherParser = new LR0Parser(otherParseTable);

        assertFalse(parser.structureEquals(otherParser));
    }

    @Test
    public void testStructureEqualsReturnsFalseForParserWithDifferentClass()
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState startState = new LR0ClosureRuleSetDFAState("", false);
        LR0ParseTable parseTable = new LR0ParseTable(grammar, startState);
        LR0Parser parser = new LR0Parser(parseTable);

        LL1ParseTable otherParseTable = new LL1ParseTable(grammar);
        LL1Parser otherParser = new LL1Parser(otherParseTable);

        assertFalse(parser.structureEquals(otherParser));
    }
}
