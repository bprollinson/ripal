/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parsetree.contextfreelanguage.EndOfStringNode;
import ripal.parsetree.contextfreelanguage.EpsilonNode;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;
import java.util.Set;

public class FirstSetCalculatorTest
{
    @Test
    public void testGetFirstReturnsEmptySetForNonExistentProduction()
    {
        Grammar grammar = new Grammar();

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsSingleTerminalDirectlyFromProduction()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsEndOfStringDirectlyFromProduction()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new EndOfStringNode());

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<Node> expectedFirsts = new HashSet<Node>();
        expectedFirsts.add(new EndOfStringNode());
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstIgnoresSubsequentCharacter()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsFirstCharacterFromMultipleCharacterTerminalToken()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsForCharacterFromMultipleCharacterTerminalTokenAfterEpsilon()
    {
        Grammar grammar = new Grammar();
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
        Grammar grammar = new Grammar();
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
        Grammar grammar = new Grammar();
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
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstDetectsCycle()
    {
        Grammar grammar = new Grammar();
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
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsEpsilonFromEpsilonTransition()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<Node> expectedFirsts = new HashSet<Node>();
        expectedFirsts.add(new EpsilonNode());
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsSubsequentTerminalFromAfterEpsilonNonTerminal()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("b"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsSubsequentEndOfStringFromAfterEpsilonNonTerminal()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new EndOfStringNode());
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<Node> expectedFirsts = new HashSet<Node>();
        expectedFirsts.add(new EndOfStringNode());
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsMultipleTerminalsWhenFirstNonTerminalGoesToTerminalOrEpsilon()
    {
        Grammar grammar = new Grammar();
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
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<Node> expectedFirsts = new HashSet<Node>();
        expectedFirsts.add(new EpsilonNode());
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsTerminalAfterSeriesOfEpsilonNonTerminals()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"), new TerminalNode("c"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<Node> expectedFirsts = new HashSet<Node>();
        expectedFirsts.add(new TerminalNode("c"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstReturnsNonterminalFirstAfterSeriesOfEpsilonNonTerminals()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<Node> expectedFirsts = new HashSet<Node>();
        expectedFirsts.add(new TerminalNode("c"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }

    @Test
    public void testGetFirstIgnoresSubsequentCharacterAfterFirstTerminalAfterEpsilon()
    {
        Grammar grammar = new Grammar();
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
        Grammar grammar = new Grammar();
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
    public void testGetFirstReturnsEmptySetForNonExistentNonTerminalNode()
    {
        Grammar grammar = new Grammar();

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        assertEquals(expectedFirsts, calculator.getFirst(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFirstReturnsMultipleTerminalNodesForNonTerminalNode()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        Set<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        expectedFirsts.add(new TerminalNode("b"));
        assertEquals(expectedFirsts, calculator.getFirst(new NonTerminalNode("S")));
    }
}
