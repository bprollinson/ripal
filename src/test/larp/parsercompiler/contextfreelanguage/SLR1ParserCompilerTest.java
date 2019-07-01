/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.Grammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0GotoAction;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ReduceReduceConflictException;
import larp.parser.contextfreelanguage.LR0ShiftReduceConflictException;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;

public class SLR1ParserCompilerTest
{
    @Test
    public void testCompileReturnsParseTableWithSingleNonTerminal() throws AmbiguousLR0ParseTableException
    {
        SLR1ParserCompiler compiler = new SLR1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<Node>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<Node>());

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state2));
        expectedTable.addCell(state1, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileAppliesReduceActionOnlyToTerminalsInFollowSet()
    {
        assertTrue(false);
    }

    @Test(expected = LR0ShiftReduceConflictException.class)
    public void testCompileThrowsExceptionForShiftReduceConflict() throws AmbiguousLR0ParseTableException
    {
        SLR1ParserCompiler compiler = new SLR1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        compiler.compile(grammar);
    }

    @Test(expected = LR0ReduceReduceConflictException.class)
    public void testCompileThrowsExceptionForReduceReduceConflict() throws AmbiguousLR0ParseTableException
    {
        SLR1ParserCompiler compiler = new SLR1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        compiler.compile(grammar);
    }

    @Test(expected = LR0ShiftReduceConflictException.class)
    public void testCompilerThrowsExceptionForShiftReduceConflictInvolvingEpsilon() throws AmbiguousLR0ParseTableException
    {
        SLR1ParserCompiler compiler = new SLR1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        compiler.compile(grammar);
    }

    @Test(expected = LR0ReduceReduceConflictException.class)
    public void testCompilerThrowsExceptionForReduceReduceConflictInvolvingEpsilon() throws AmbiguousLR0ParseTableException
    {
        SLR1ParserCompiler compiler = new SLR1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        compiler.compile(grammar);
    }
}
