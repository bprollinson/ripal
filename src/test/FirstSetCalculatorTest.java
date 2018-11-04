/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.syntaxcompiler.contextfreelanguage.FirstSetCalculator;

import java.util.HashSet;
import java.util.Set;

public class FirstSetCalculatorTest
{
    @Test
    public void testGetFirstReturnsSingleTerminalDirectlyFromProduction()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstIgnoresSubsequentCharacter()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsFirstCharacterFromMultipleCharacterTerminalToken()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsForCharacterFromMultipleCharacterTerminalTokenAfterEpsilon()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("ab"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsSingleTerminalWithIntermediateNonTerminalProduction()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsMultipleTerminalsWithIntermediateNonTerminalProductions()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("b"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        expectedFirsts.add(new TerminalNode("b"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsEmptySetForDeadEndNonTerminalProduction()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstDetectsCycle()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstIgnoresNonTerminalWithoutProductions()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsEpsilonForEpsilonTransition()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFirsts = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFirsts.add(new EpsilonNode());
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsSubsequentTerminalFromAfterEpsilonNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("b"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsMultipleTerminalsWhenFirstNonTerminalGoesToTerminalOrEpsilon()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        expectedFirsts.add(new TerminalNode("b"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsEpsilonWhenAllNonTerminalsGoToEpsilon()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFirsts = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFirsts.add(new EpsilonNode());
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsTerminalAfterSeriesOfEpsilonNonTerminals()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"), new TerminalNode("c"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFirsts = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFirsts.add(new TerminalNode("c"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsNonterminalFirstAfterSeriesOfEpsilonNonTerminals()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFirsts = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFirsts.add(new TerminalNode("c"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstIgnoresSubsequentCharacterAfterFirstTerminalAfterEpsilon()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"), new TerminalNode("c"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("b"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstIgnoresSubsequentCharacterAfterFirstNonEpsilonNoTerminalAfterEpsilon()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("b"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsMultipleTerminalNodesForNonTerminalNode()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        expectedFirsts.add(new TerminalNode("b"));
        assertEquals(expectedFirsts, calculator.getFirst(new NonTerminalNode("S")));
    }
}
