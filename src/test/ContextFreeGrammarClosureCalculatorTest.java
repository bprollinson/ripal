/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsercompiler.contextfreelanguage.ContextFreeGrammarClosureCalculator;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;
import java.util.Set;

public class ContextFreeGrammarClosureCalculatorTest
{
    @Test
    public void testCalculatDoesNotAddProductionWhenInitialProductionSetIsEmpty()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotAddProductionWhenContextFreeGrammarIsEmpty()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateAddsSimpleTerminalProduction()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("A"), new DotNode(), new TerminalNode("a")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateAddsSimpleNonTerminalProduction()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotAddProductionWhenNoneAvailable()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotAddProductionWhenNonReachable()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateAddsNonTerminalChain()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("d"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("C")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("C"), new DotNode(), new TerminalNode("d")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateAddsClosureFromNonFirstNonTerminalInProduction()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new TerminalNode("a"), new DotNode(), new NonTerminalNode("B")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new TerminalNode("a"), new DotNode(), new NonTerminalNode("B")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("B"), new DotNode(), new TerminalNode("b")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotAddProductionsForTerminal()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new TerminalNode("a"), new NonTerminalNode("B")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new TerminalNode("a"), new NonTerminalNode("B")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotAddProductionsForSubsequentNonTerminal()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new NonTerminalNode("B")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new NonTerminalNode("B")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateCalculatesClosureIgnoringImmediateInfiniteLoop()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("S")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("S")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new TerminalNode("a")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateCalculatesClosureIgnoringEventualInfiniteLoop()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("S"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("B"), new DotNode(), new TerminalNode("b")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("S")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateExpandsImmediateNonTerminalIntoParallelTerminals()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("b"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("A"), new DotNode(), new TerminalNode("a")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("A"), new DotNode(), new TerminalNode("b")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateExpandsEventualNonTerminalIntoParallelTerminals()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("c"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("B"), new DotNode(), new TerminalNode("b")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("B"), new DotNode(), new TerminalNode("c")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateExpandsImmediateNonTerminalIntoParallelNonTerminals()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("C"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("C")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateExpandsEventualNonTerminalIntoParallelNonTerminals()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("D"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("C")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("D")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateAddsNonTerminalChainInReverseOrder()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("d"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("C")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("C"), new DotNode(), new TerminalNode("d")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateAddsDotAfterEpsilon()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new EpsilonNode()));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new EpsilonNode()));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new EpsilonNode(), new DotNode()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateDoesNotExpandProductionSetWhenDotMissing()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new NonTerminalNode("A")));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    @Test
    public void testCalculateClosureDoesNotAddProductionWhenDotAtEndOfProduction()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new DotNode()));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new DotNode()));

        assertEquals(expectedProductionSet, calculator.calculate(grammar, productionSet));
    }

    private ProductionNode buildProduction(NonTerminalNode nonTerminalNode, ContextFreeGrammarSyntaxNode... rightHandNodes)
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(nonTerminalNode);

        ConcatenationNode concatenationNode = new ConcatenationNode();
        for (ContextFreeGrammarSyntaxNode rightHandNode: rightHandNodes)
        {
            concatenationNode.addChild(rightHandNode);
        }
        productionNode.addChild(concatenationNode);

        return productionNode;
    }
}
