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
import ripal.parsetree.contextfreelanguage.ConcatenationNode;
import ripal.parsetree.contextfreelanguage.DotNode;
import ripal.parsetree.contextfreelanguage.EndOfStringNode;
import ripal.parsetree.contextfreelanguage.EpsilonNode;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.ProductionNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;
import java.util.Set;

public class GrammarClosureCalculatorTest
{
    @Test
    public void testCalculateDoesNotAddClosureRuleWhenInitialClosureRuleSetIsEmpty()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateDoesNotAddClosureruleWhenGrammarIsEmpty()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateAddsSimpleTerminalClosureRule()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("A"), new DotNode(), new TerminalNode("a")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateAddsSimpleNonTerminalClosureRule()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateDoesNotAddClosureRuleWhenNoneAvailable()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateDoesNotAddClosureRuleWhenNonReachable()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
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

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("C")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("C"), new DotNode(), new TerminalNode("d")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateAddsClosureRuleFromNonFirstNonTerminalInRule()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new TerminalNode("a"), new DotNode(), new NonTerminalNode("B")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new TerminalNode("a"), new DotNode(), new NonTerminalNode("B")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("B"), new DotNode(), new TerminalNode("b")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateDoesNotAddClosureRuleForTerminal()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new TerminalNode("a"), new NonTerminalNode("B")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new TerminalNode("a"), new NonTerminalNode("B")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateDoesNotAddClosureRuleForSubsequentNonTerminal()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new NonTerminalNode("B")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new NonTerminalNode("B")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateDoesNotAddClosureRuleWhenDotNotFound()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new NonTerminalNode("A")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateCalculatesClosureIgnoringImmediateInfiniteLoop()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("S")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("S")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new TerminalNode("a")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
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

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("B"), new DotNode(), new TerminalNode("b")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("S")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateExpandsImmediateNonTerminalIntoParallelTerminals()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("b"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("A"), new DotNode(), new TerminalNode("a")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("A"), new DotNode(), new TerminalNode("b")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
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

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("B"), new DotNode(), new TerminalNode("b")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("B"), new DotNode(), new TerminalNode("c")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateExpandsImmediateNonTerminalIntoParallelNonTerminals()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("C"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("C")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
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

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("C")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("D")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
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

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("B"), new DotNode(), new NonTerminalNode("C")));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("C"), new DotNode(), new TerminalNode("d")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateAddsDotAfterEpsilon()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new EpsilonNode()));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new EpsilonNode()));
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new EpsilonNode(), new DotNode()));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateDoesNotExpandClosureRulesWhenDotMissing()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new NonTerminalNode("A")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateDoesNotAddClosureRuleWhenDotAtEndOfRule()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new NonTerminalNode("A"), new DotNode()));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        expectedClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new NonTerminalNode("A"), new DotNode()));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculatePreservesInitialLookaheadSymbolSetWhenNoRulesAdded()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new EndOfStringNode());
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new EndOfStringNode());
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateAddsSimpleClosureRuleWithSingleLookaheadSymbol()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new EndOfStringNode());
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new TerminalNode("b")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new EndOfStringNode());
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new TerminalNode("b")));
        expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("b"));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new TerminalNode("a")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateAddsNonTerminalChainWithLookaheadSymbols()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("c"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new EndOfStringNode());
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new TerminalNode("a")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new EndOfStringNode());
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new TerminalNode("a")));
        expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("a"));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B"), new TerminalNode("b")));
        expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("b"));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("B"), new DotNode(), new TerminalNode("c")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateAddsParentEndOfStringLookaheadWhenFollowingSymbolsNullable()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new EndOfStringNode());
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new EndOfStringNode());
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new TerminalNode("a")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateAddsSingleParentTerminalNodeWhenFollowingSymbolsNullable()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("a"));
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("a"));
        expectedClosureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedClosureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new EpsilonNode()));
        expectedClosureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("A"), new EpsilonNode(), new DotNode()));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateAddsMultipleParentTerminalNodesWhenFollowingSymbolsNullable()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("a"));
        lookaheadSymbols.add(new TerminalNode("b"));
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("a"));
        expectedLookaheadSymbols.add(new TerminalNode("b"));
        expectedClosureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedClosureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new EpsilonNode()));
        expectedClosureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("A"), new EpsilonNode(), new DotNode()));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateAddsSingleParentTerminalNodeInChainWhenFollowingSymbolsNullable()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("a"));
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("a"));
        expectedClosureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedClosureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));
        expectedClosureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("B"), new DotNode(), new EpsilonNode()));
        expectedClosureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("B"), new EpsilonNode(), new DotNode()));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateAddsEndOfStringNodeAndOtherLookaheadWhenFollowingSymbolsNullable()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new EndOfStringNode());
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new NonTerminalNode("B")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new EndOfStringNode());
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new NonTerminalNode("B")));
        expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("b"));
        expectedLookaheadSymbols.add(new EndOfStringNode());
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new TerminalNode("a")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateDoesNotIncludeLookaheadSymbolsFromUnreachableProductions()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("c"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("A"), new TerminalNode("b"));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new EndOfStringNode());
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new TerminalNode("c")));
        expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("c"));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new TerminalNode("a"), new NonTerminalNode("B")));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new EndOfStringNode());
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new TerminalNode("c")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculateIncludesAndCombinesLookaheadSymbolsFromMultipleSubsequentProductions()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("A"), new TerminalNode("c"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new EndOfStringNode());
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new TerminalNode("b")));
        expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("b"));
        expectedLookaheadSymbols.add(new TerminalNode("c"));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("A"), new TerminalNode("c")));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new TerminalNode("a")));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new EndOfStringNode());
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new TerminalNode("b")));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    @Test
    public void testCalculatePreservesLookaheadSymbolsWhenAddingDotAfterEpsilon()
    {
        GrammarClosureCalculator calculator = new GrammarClosureCalculator();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("a"));
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new EpsilonNode()));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("a"));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new EpsilonNode()));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new EpsilonNode(), new DotNode()));

        assertEquals(expectedClosureRules, calculator.calculate(grammar, closureRules));
    }

    private GrammarClosureRule buildClosureRule(NonTerminalNode nonTerminalNode, Node... rightHandNodes)
    {
        return this.buildClosureRule(new HashSet<Node>(), nonTerminalNode, rightHandNodes);
    }

    private GrammarClosureRule buildClosureRule(Set<Node> lookaheadSymbols, NonTerminalNode nonTerminalNode, Node... rightHandNodes)
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(nonTerminalNode);

        ConcatenationNode concatenationNode = new ConcatenationNode();
        for (Node rightHandNode: rightHandNodes)
        {
            concatenationNode.addChild(rightHandNode);
        }
        productionNode.addChild(concatenationNode);

        return new GrammarClosureRule(productionNode, lookaheadSymbols);
    }
}
