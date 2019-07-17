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
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;
import java.util.Set;

public class GrammarClosureCalculatorTest
{
    @Test
    public void testCalculateDoesNotAddProductionWhenInitialProductionSetIsEmpty()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotAddProductionWhenGrammarIsEmpty()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateAddsSimpleTerminalProduction()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("A"), new DotNode(), new TerminalNode("a"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateAddsSimpleNonTerminalProduction()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")); 
        expectedProductionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotAddProductionWhenNoneAvailable()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")); 
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotAddProductionWhenNonReachable()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateAddsNonTerminalChain()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("d"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("C"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("C"), new DotNode(), new TerminalNode("d"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateAddsClosureFromNonFirstNonTerminalInProduction()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new TerminalNode("a"), new DotNode(), new NonTerminalNode("B"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new TerminalNode("a"), new DotNode(), new NonTerminalNode("B"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("B"), new DotNode(), new TerminalNode("b"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotAddProductionsForTerminal()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new TerminalNode("a"), new NonTerminalNode("B"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new TerminalNode("a"), new NonTerminalNode("B"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotAddProductionsForSubsequentNonTerminal()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new NonTerminalNode("B"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new NonTerminalNode("B"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotAddProductionsWhenDotNotFound()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateCalculatesClosureIgnoringImmediateInfiniteLoop()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("S"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("S"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new TerminalNode("a"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateCalculatesClosureIgnoringEventualInfiniteLoop()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("S"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("B"), new DotNode(), new TerminalNode("b"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("S"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateExpandsImmediateNonTerminalIntoParallelTerminals()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("b"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("A"), new DotNode(), new TerminalNode("a"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("A"), new DotNode(), new TerminalNode("b"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateExpandsEventualNonTerminalIntoParallelTerminals()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("c"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("B"), new DotNode(), new TerminalNode("b"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("B"), new DotNode(), new TerminalNode("c"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateExpandsImmediateNonTerminalIntoParallelNonTerminals()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("C"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("C"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateExpandsEventualNonTerminalIntoParallelNonTerminals()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("D"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("C"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("D"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateAddsNonTerminalChainInReverseOrder()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("d"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("C"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("C"), new DotNode(), new TerminalNode("d"));
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateAddsDotAfterEpsilon()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new EpsilonNode()); 
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new DotNode(), new EpsilonNode());
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));
        otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new EpsilonNode(), new DotNode());
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotExpandProductionSetWhenDotMissing()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new NonTerminalNode("A")); 
        expectedProductionSet.add(new GrammarClosureRule(otherProductionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotAddProductionWhenDotAtEndOfProduction()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));

        Set<GrammarClosureRule> productionSet = new HashSet<GrammarClosureRule>();
        Node productionNode = this.buildProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new DotNode());
        productionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        Set<GrammarClosureRule> expectedProductionSet = new HashSet<GrammarClosureRule>();
        Node otherProductionNode = this.buildProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new DotNode());
        expectedProductionSet.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    private ProductionNode buildProduction(NonTerminalNode nonTerminalNode, Node... rightHandNodes)
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(nonTerminalNode);

        ConcatenationNode concatenationNode = new ConcatenationNode();
        for (Node rightHandNode: rightHandNodes)
        {
            concatenationNode.addChild(rightHandNode);
        }
        productionNode.addChild(concatenationNode);

        return productionNode;
    }
}
