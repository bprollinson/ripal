/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0GotoAction;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ReduceReduceConflictException;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parser.contextfreelanguage.LR0ShiftReduceConflictException;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.syntaxcompiler.contextfreelanguage.ContextFreeGrammarLR0SyntaxCompiler;

import java.util.HashSet;

public class ContextFreeGrammarLR0SyntaxCompilerTest
{
    @Test
    public void testCompileReturnsEmptyParseTableForEmptyCFG() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        LR0ParseTable expectedTable = new LR0ParseTable(grammar, null);

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileReturnsParseTableForSingleCharacterProductionCFG() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
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
    public void testCompileReturnsParseTableForSingleNonTerminalProductionCFG() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
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
    public void testCompileReturnsParseTableForSingleNonTerminalAndSingleTerminalProductionCFG() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
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
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
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
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state5 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state6 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
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
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("x"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("b"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state5 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state6 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
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
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("x"), new NonTerminalNode("X"));
        grammar.addProduction(new NonTerminalNode("X"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state5 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state6 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state7 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state8 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
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
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"), new TerminalNode("c"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state5 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
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
    public void testCompileReturnsTableForCFGWithDFAContainingCycle() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("S"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
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
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
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
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
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
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        compiler.compile(grammar);
    }

    @Test(expected = LR0ReduceReduceConflictException.class)
    public void testCompileThrowsExceptionForReduceReduceConflict() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        compiler.compile(grammar);
    }

    @Test(expected = LR0ShiftReduceConflictException.class)
    public void testCompilerThrowsExceptionForShiftReduceConflictInvolvingEpsilon() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        compiler.compile(grammar);
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testCompilerThrowsExceptionForReduceReduceConflictInvolvingEpsilon() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        compiler.compile(grammar);
    }
}
