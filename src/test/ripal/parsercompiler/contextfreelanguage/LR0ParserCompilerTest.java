/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.contextfreelanguage;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import ripal.parser.contextfreelanguage.LR0AcceptAction;
import ripal.parser.contextfreelanguage.LR0ClosureRuleSetDFAState;
import ripal.parser.contextfreelanguage.LR0GotoAction;
import ripal.parser.contextfreelanguage.LR0ParseTable;
import ripal.parser.contextfreelanguage.LR0ReduceAction;
import ripal.parser.contextfreelanguage.LR0ReduceReduceConflictException;
import ripal.parser.contextfreelanguage.LR0ShiftAction;
import ripal.parser.contextfreelanguage.LR0ShiftReduceConflictException;
import ripal.parsetree.contextfreelanguage.EndOfStringNode;
import ripal.parsetree.contextfreelanguage.EpsilonNode;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;

public class LR0ParserCompilerTest
{
    @Test
    public void testCompileReturnsEmptyParseTableForEmptyGrammar() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        LR0ParseTable expectedTable = new LR0ParseTable(grammar, null);

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileReturnsParseTableForSingleCharacterProductionGrammar() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state3));
        expectedTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(1));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileReturnsParseTableForSingleNonTerminalProductionGrammar() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state3));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileReturnsParseTableForSingleNonTerminalAndSingleTerminalProductionGrammar() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state3));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        expectedTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(2));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileHandlesMultipleTerminalProductionsWithinTheSameInitialState() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new TerminalNode("b"), new LR0ShiftAction(state3));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        expectedTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(1));
        expectedTable.addCell(state2, new TerminalNode("b"), new LR0ReduceAction(1));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(2));
        expectedTable.addCell(state3, new TerminalNode("b"), new LR0ReduceAction(2));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileHandlesMultipleNonTerminalProductionsWithinTheSameInitialState() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state5 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state6 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        augmentedGrammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        augmentedGrammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("B"), new LR0GotoAction(state3));
        expectedTable.addCell(state1, new NonTerminalNode("C"), new LR0GotoAction(state4));
        expectedTable.addCell(state1, new TerminalNode("c"), new LR0ShiftAction(state5));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state6));
        expectedTable.addCell(state2, new TerminalNode("c"), new LR0ReduceAction(1));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new TerminalNode("c"), new LR0ReduceAction(2));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state4, new TerminalNode("c"), new LR0ReduceAction(3));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0ReduceAction(3));
        expectedTable.addCell(state5, new TerminalNode("c"), new LR0ReduceAction(4));
        expectedTable.addCell(state5, new EndOfStringNode(), new LR0ReduceAction(4));
        expectedTable.addCell(state6, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileHandlesMultipleTerminalProductionsWithinTheSameSubsequentState() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("x"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("b"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state5 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state6 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("x"), new NonTerminalNode("A"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("b"));

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new TerminalNode("x"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state6));
        expectedTable.addCell(state2, new TerminalNode("a"), new LR0ShiftAction(state3));
        expectedTable.addCell(state2, new TerminalNode("b"), new LR0ShiftAction(state4));
        expectedTable.addCell(state2, new NonTerminalNode("A"), new LR0GotoAction(state5));
        expectedTable.addCell(state3, new TerminalNode("x"), new LR0ReduceAction(2));
        expectedTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(2));
        expectedTable.addCell(state3, new TerminalNode("b"), new LR0ReduceAction(2));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state4, new TerminalNode("x"), new LR0ReduceAction(3));
        expectedTable.addCell(state4, new TerminalNode("a"), new LR0ReduceAction(3));
        expectedTable.addCell(state4, new TerminalNode("b"), new LR0ReduceAction(3));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0ReduceAction(3));
        expectedTable.addCell(state5, new TerminalNode("x"), new LR0ReduceAction(1));
        expectedTable.addCell(state5, new TerminalNode("a"), new LR0ReduceAction(1));
        expectedTable.addCell(state5, new TerminalNode("b"), new LR0ReduceAction(1));
        expectedTable.addCell(state5, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state6, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileHandlesMultipleNonTerminalProductionsWithinTheSameSubsequentState() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("x"), new NonTerminalNode("X"));
        grammar.addProduction(new NonTerminalNode("X"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state5 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state6 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state7 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state8 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("x"), new NonTerminalNode("X"));
        augmentedGrammar.addProduction(new NonTerminalNode("X"), new NonTerminalNode("A"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        augmentedGrammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        augmentedGrammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new TerminalNode("x"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state8));
        expectedTable.addCell(state2, new NonTerminalNode("X"), new LR0GotoAction(state3));
        expectedTable.addCell(state2, new NonTerminalNode("A"), new LR0GotoAction(state4));
        expectedTable.addCell(state2, new NonTerminalNode("B"), new LR0GotoAction(state5));
        expectedTable.addCell(state2, new NonTerminalNode("C"), new LR0GotoAction(state6));
        expectedTable.addCell(state2, new TerminalNode("c"), new LR0ShiftAction(state7));
        expectedTable.addCell(state3, new TerminalNode("x"), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new TerminalNode("c"), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state4, new TerminalNode("x"), new LR0ReduceAction(2));
        expectedTable.addCell(state4, new TerminalNode("c"), new LR0ReduceAction(2));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state5, new TerminalNode("x"), new LR0ReduceAction(3));
        expectedTable.addCell(state5, new TerminalNode("c"), new LR0ReduceAction(3));
        expectedTable.addCell(state5, new EndOfStringNode(), new LR0ReduceAction(3));
        expectedTable.addCell(state6, new TerminalNode("x"), new LR0ReduceAction(4));
        expectedTable.addCell(state6, new TerminalNode("c"), new LR0ReduceAction(4));
        expectedTable.addCell(state6, new EndOfStringNode(), new LR0ReduceAction(4));
        expectedTable.addCell(state7, new TerminalNode("x"), new LR0ReduceAction(5));
        expectedTable.addCell(state7, new TerminalNode("c"), new LR0ReduceAction(5));
        expectedTable.addCell(state7, new EndOfStringNode(), new LR0ReduceAction(5));
        expectedTable.addCell(state8, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileReturnsParseTableWithTerminalChain() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"), new TerminalNode("c"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state5 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"), new TerminalNode("c"));

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state5));
        expectedTable.addCell(state2, new TerminalNode("b"), new LR0ShiftAction(state3));
        expectedTable.addCell(state3, new TerminalNode("c"), new LR0ShiftAction(state4));
        expectedTable.addCell(state4, new TerminalNode("a"), new LR0ReduceAction(1));
        expectedTable.addCell(state4, new TerminalNode("b"), new LR0ReduceAction(1));
        expectedTable.addCell(state4, new TerminalNode("c"), new LR0ReduceAction(1));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state5, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileReturnsTableForGrammarWithDFAContainingCycle() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("S"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("S"));

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        expectedTable.addCell(state2, new NonTerminalNode("A"), new LR0GotoAction(state2));
        expectedTable.addCell(state2, new NonTerminalNode("S"), new LR0GotoAction(state3));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileSplitsMultiCharacterTerminalIntoMultipleTerminals() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        expectedTable.addCell(state2, new TerminalNode("b"), new LR0ShiftAction(state3));
        expectedTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new TerminalNode("b"), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileAddsReduceActionForEpsilonProduction() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state2));
        expectedTable.addCell(state1, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test(expected = LR0ShiftReduceConflictException.class)
    public void testCompileThrowsExceptionForShiftReduceConflict() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        compiler.compile(grammar);
    }

    @Test(expected = LR0ReduceReduceConflictException.class)
    public void testCompileThrowsExceptionForReduceReduceConflict() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        compiler.compile(grammar);
    }

    @Test(expected = LR0ShiftReduceConflictException.class)
    public void testCompilerThrowsExceptionForShiftReduceConflictInvolvingEpsilon() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        compiler.compile(grammar);
    }

    @Test(expected = LR0ReduceReduceConflictException.class)
    public void testCompilerThrowsExceptionForReduceReduceConflictInvolvingEpsilon() throws AmbiguousLR0ParseTableException
    {
        LR0ParserCompiler compiler = new LR0ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        compiler.compile(grammar);
    }
}
