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

public class LR1ParserCompilerTest
{
    @Test
    public void testCompileAppliesReduceActionOnlyToEndOfStringNodeInLookaheadSet() throws AmbiguousLR0ParseTableException
    {
        LR1ParserCompiler compiler = new LR1ParserCompiler();

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
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileAppliesReduceActionOnlyToSingleTerminalNodeInLookaheadSet() throws AmbiguousLR0ParseTableException
    {
        LR1ParserCompiler compiler = new LR1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state5 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state4));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state5));
        expectedTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));
        expectedTable.addCell(state2, new TerminalNode("b"), new LR0ShiftAction(state3));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state4, new TerminalNode("b"), new LR0ReduceAction(2));
        expectedTable.addCell(state5, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileAppliesReduceActionOnlyToMultipleTerminalNodesInLookaheadSet() throws AmbiguousLR0ParseTableException
    {
        LR1ParserCompiler compiler = new LR1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("c"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state5 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state6 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("c"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state5));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state6));
        expectedTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));
        expectedTable.addCell(state2, new TerminalNode("b"), new LR0ShiftAction(state3));
        expectedTable.addCell(state2, new TerminalNode("c"), new LR0ShiftAction(state4));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state5, new TerminalNode("b"), new LR0ReduceAction(3));
        expectedTable.addCell(state5, new TerminalNode("c"), new LR0ReduceAction(3));
        expectedTable.addCell(state6, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test(expected = LR0ShiftReduceConflictException.class)
    public void testCompileThrowsExceptionForShiftReduceConflict() throws AmbiguousLR0ParseTableException
    {
        LR1ParserCompiler compiler = new LR1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"), new TerminalNode("b"));

        compiler.compile(grammar);
    }

    @Test(expected = LR0ReduceReduceConflictException.class)
    public void testCompileThrowsExceptionForReduceReduceConflict() throws AmbiguousLR0ParseTableException
    {
        LR1ParserCompiler compiler = new LR1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        compiler.compile(grammar);
    }

    @Test(expected = LR0ShiftReduceConflictException.class)
    public void testCompilerThrowsExceptionForShiftReduceConflictInvolvingEpsilon() throws AmbiguousLR0ParseTableException
    {
        LR1ParserCompiler compiler = new LR1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        compiler.compile(grammar);
    }

    @Test(expected = LR0ReduceReduceConflictException.class)
    public void testCompilerThrowsExceptionForReduceReduceConflictInvolvingEpsilon() throws AmbiguousLR0ParseTableException
    {
        LR1ParserCompiler compiler = new LR1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        compiler.compile(grammar);
    }
}
