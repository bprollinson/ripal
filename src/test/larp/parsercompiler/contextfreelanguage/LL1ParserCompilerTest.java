/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.Grammar;
import larp.parser.contextfreelanguage.AmbiguousLL1ParseTableException;
import larp.parser.contextfreelanguage.LL1ApplyApplyConflictException;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

public class LL1ParserCompilerTest
{
    @Test
    public void testCompileReturnsEmptyParseTableForEmptyGrammar() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        LL1ParseTable expectedTable = new LL1ParseTable(grammar);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableForSingleCharacterProductionGrammar() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompilerReturnsEmptyParseTableForSingleNonTerminalProductionGrammar() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableForSingleNonTerminalAndSingleTerminalProductionGrammar() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableWithNonTerminalConcatenation() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        expectedTable.addCell(new NonTerminalNode("B"), new TerminalNode("b"), 2);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableForProductionWithMultipleCharactersInFirstSetViaDirectProductions() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 1);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableForProductionWithMultipleCharactersInFirstSetViaIndirectProductions() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("b"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("b"), 2);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableWithNonTerminalChain() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("b"), 1);
        expectedTable.addCell(new NonTerminalNode("B"), new TerminalNode("b"), 2);
        expectedTable.addCell(new NonTerminalNode("C"), new TerminalNode("c"), 3);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test(expected = LL1ApplyApplyConflictException.class)
    public void testCompileThrowsExceptionForFirstAmbiguityBetweenTwoTerminals() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        compiler.compile(grammar);
    }

    @Test(expected = LL1ApplyApplyConflictException.class)
    public void testCompileThrowsExceptionForFirstAmbiguityBetweenTwoNonTerminals() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("a"));

        compiler.compile(grammar);
    }

    @Test(expected = LL1ApplyApplyConflictException.class)
    public void testCompileThrowsExceptionForFirstAmbiguityBetweenTerminalAndNonTerminal() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        compiler.compile(grammar);
    }

    @Test(expected = LL1ApplyApplyConflictException.class)
    public void testCompileThrowsExceptionForAmbiguousGrammarContainingCycle() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("S"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        compiler.compile(grammar);
    }

    @Test
    public void testCompileReturnsEmptyTableForDeadEndNonTerminalProduction() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableForGrammarContainingCycle() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("S"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 1);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileOnlyConsidersFirstCharacterFromMultiCharacterTerminalInFirstSet() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileHandlesTerminalFollowSets() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("b"), new NonTerminalNode("C"), new TerminalNode("c"), new NonTerminalNode("C"), new TerminalNode("d"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("C"), new EpsilonNode());

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("B"), new TerminalNode("b"), 1);
        expectedTable.addCell(new NonTerminalNode("C"), new TerminalNode("c"), 2);
        expectedTable.addCell(new NonTerminalNode("C"), new TerminalNode("d"), 2);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileHandlesEndOfStringFollowSets() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("S"), new EndOfStringNode(), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        expectedTable.addCell(new NonTerminalNode("A"), new EndOfStringNode(), 2);
        expectedTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 3);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileOnlyConsidersFirstCharacterFromMultiCharacterTerminalInFollowSet() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("ab"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test(expected = LL1ApplyApplyConflictException.class)
    public void testCompileThrowsExceptionForFirstFollowAmbiguity() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        compiler.compile(grammar);
    }

    @Test(expected = LL1ApplyApplyConflictException.class)
    public void testCompileThrowsExceptionForFollowFollowAmbiguity() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        compiler.compile(grammar);
    }

    @Test
    public void testCompileHandlesDanglingNonTerminalAfterNonTerminalThatGoesToEpsilon() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableForNonDanglingProductionPath() throws AmbiguousLL1ParseTableException
    {
        LL1ParserCompiler compiler = new LL1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("D"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("D"), new TerminalNode("d"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("d"), 1);
        expectedTable.addCell(new NonTerminalNode("D"), new TerminalNode("d"), 4);

        assertEquals(expectedTable, compiler.compile(grammar));
    }
}
