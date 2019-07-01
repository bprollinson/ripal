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
import larp.parser.contextfreelanguage.LR0ReduceReduceConflictException;
import larp.parser.contextfreelanguage.LR0ShiftReduceConflictException;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

public class SLR1ParserCompilerTest
{
    @Test
    public void testCompileReturnsParseTableWithSingleNonTerminal()
    {
        assertTrue(false);
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
