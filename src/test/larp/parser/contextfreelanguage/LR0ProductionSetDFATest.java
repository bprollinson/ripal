/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.automaton.DFA;
import larp.automaton.DFAState;
import larp.grammar.contextfreelanguage.Grammar;
import larp.parsercompiler.contextfreelanguage.GrammarClosureRule;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;

import java.util.HashSet;
import java.util.Set;

public class LR0ProductionSetDFATest
{
    @Test
    public void testGetStartStateReturnsLR0ProductionSetDFAState()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        LR0ProductionSetDFAState expectedStartState = new LR0ProductionSetDFAState("S0", true);
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(expectedStartState, grammar);

        LR0ProductionSetDFAState startState = dfa.getStartState();
        assertEquals(expectedStartState, startState);
    }

    @Test
    public void testStructureEqualsReturnsTrue()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, closureRules), grammar);

        Set<GrammarClosureRule> otherClosureRules = new HashSet<GrammarClosureRule>();
        otherClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertTrue(dfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", true, otherClosureRules), grammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenStatesNotEqual()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, closureRules), grammar);

        Set<GrammarClosureRule> otherClosureRules = new HashSet<GrammarClosureRule>();
        otherClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertFalse(dfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", false, otherClosureRules), grammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenClosureRulesNotEqual()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, closureRules), grammar);

        Set<GrammarClosureRule> otherClosureRules = new HashSet<GrammarClosureRule>();
        otherClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("B")));
        assertFalse(dfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", true, otherClosureRules), grammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenGrammarsNotEqual()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, closureRules), grammar);

        Grammar otherGrammar = new Grammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("B"));
        Set<GrammarClosureRule> otherClosureRules = new HashSet<GrammarClosureRule>();
        otherClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertFalse(dfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", true, otherClosureRules), otherGrammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseForAutomataWithDifferentClass()
    {
        Grammar grammar = new Grammar();
        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, closureRules), grammar);

        assertFalse(dfa.structureEquals(new DFA(new DFAState("S1", true))));
    }

    private GrammarClosureRule buildClosureRule(NonTerminalNode nonTerminalNode, Node... rightHandNodes)
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(nonTerminalNode);

        ConcatenationNode concatenationNode = new ConcatenationNode();
        for (Node rightHandNode: rightHandNodes)
        {
            concatenationNode.addChild(rightHandNode);
        }
        productionNode.addChild(concatenationNode);

        return new GrammarClosureRule(productionNode);
    }
}
