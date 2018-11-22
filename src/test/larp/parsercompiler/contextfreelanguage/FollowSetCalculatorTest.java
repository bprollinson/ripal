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
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;
import java.util.Set;

public class FollowSetCalculatorTest
{
    @Test
    public void testGetFollowReturnsEmptySetForEmptyCFG()
    {
        Grammar grammar = new Grammar();

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsEmptySetForNonTerminalNodeNotAppearingInCFG()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsEmptySetForDanglingNonTerminal()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsEndOfStringSymbolForStartNonTerminal()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new EndOfStringNode());
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeAndEndOfStringSymbolForStartNonTerminalFollowedByTerminal()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new EndOfStringNode());
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeForNonStartNonTerminalFollowedByTerminal()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsFirstCharacterFromMultipleCharacterTerminalToken()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S"), new TerminalNode("aa"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new EndOfStringNode());
        expectedFollows.add(new TerminalNode("a"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsFollowFromMultipleProductions()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("A"), new TerminalNode("c"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new TerminalNode("b"));
        expectedFollows.add(new TerminalNode("c"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeAndEndOfStringSymbolForStartNonTerminalFollowedByNonTerminal()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new EndOfStringNode());
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeForNonStartNonTerminalFollowedByNonTerminal()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeFromASeriesOfProductions()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("d"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new TerminalNode("d"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsEndOfStringSymbolForNonTerminalProducedFromStartNonTerminal()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new EndOfStringNode());
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsParentFollowForNonTerminalProducedFromNonStartNonTerminal()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("B"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("C")));
    }

    @Test
    public void testGetFollowReturnsAncestorFollowForNonTerminalProducedFromNonStartNonTerminal()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("B"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new TerminalNode("a"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("C")));
    }

    @Test
    public void testGetFollowChainsParentFollowInArbitraryOrder()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("B"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("D"), new TerminalNode("d"));
        grammar.addProduction(new NonTerminalNode("C"), new NonTerminalNode("D"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new TerminalNode("a"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("D")));
    }

    @Test
    public void testGetFollowReturnsEndOfStringSymbolWhenNextNonTerminalGoesToEpsilon()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new EndOfStringNode());
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsEndOfStringSymbolWhenNextNonTerminalSometimesGoesToEpsilon()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new TerminalNode("b"));
        expectedFollows.add(new EndOfStringNode());
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsSubsequentTerminalNodeWhenNextNonTerminalNodeGoesToEpsilon()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"), new TerminalNode("c"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new TerminalNode("c"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeFromSubsequentNonTerminalNodeWhenNextNonTerminalNodeGoesToEpsilon()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        expectedFollows.add(new TerminalNode("c"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsFollowForNonDanglingProductionPath()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<Node> expectedFollows = new HashSet<Node>();
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }
}
