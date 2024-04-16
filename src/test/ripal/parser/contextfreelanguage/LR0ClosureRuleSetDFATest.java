/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parser.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ripal.automaton.DFA;
import ripal.automaton.DFAState;
import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parsercompiler.contextfreelanguage.GrammarClosureRule;
import ripal.parsetree.contextfreelanguage.ConcatenationNode;
import ripal.parsetree.contextfreelanguage.DotNode;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.ProductionNode;

import java.util.HashSet;
import java.util.Set;

public class LR0ClosureRuleSetDFATest
{
    @Test
    public void testGetStartStateReturnsLR0ClosureRuleSetDFAState()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        LR0ClosureRuleSetDFAState expectedStartState = new LR0ClosureRuleSetDFAState("S0", true);
        LR0ClosureRuleSetDFA dfa = new LR0ClosureRuleSetDFA(expectedStartState, grammar);

        LR0ClosureRuleSetDFAState startState = dfa.getStartState();
        assertEquals(expectedStartState, startState);
    }

    @Test
    public void testStructureEqualsReturnsTrue()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ClosureRuleSetDFA dfa = new LR0ClosureRuleSetDFA(new LR0ClosureRuleSetDFAState("S0", true, closureRules), grammar);

        Set<GrammarClosureRule> otherClosureRules = new HashSet<GrammarClosureRule>();
        otherClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertTrue(dfa.structureEquals(new LR0ClosureRuleSetDFA(new LR0ClosureRuleSetDFAState("S1", true, otherClosureRules), grammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenStatesNotEqual()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ClosureRuleSetDFA dfa = new LR0ClosureRuleSetDFA(new LR0ClosureRuleSetDFAState("S0", true, closureRules), grammar);

        Set<GrammarClosureRule> otherClosureRules = new HashSet<GrammarClosureRule>();
        otherClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertFalse(dfa.structureEquals(new LR0ClosureRuleSetDFA(new LR0ClosureRuleSetDFAState("S1", false, otherClosureRules), grammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenClosureRulesNotEqual()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ClosureRuleSetDFA dfa = new LR0ClosureRuleSetDFA(new LR0ClosureRuleSetDFAState("S0", true, closureRules), grammar);

        Set<GrammarClosureRule> otherClosureRules = new HashSet<GrammarClosureRule>();
        otherClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("B")));
        assertFalse(dfa.structureEquals(new LR0ClosureRuleSetDFA(new LR0ClosureRuleSetDFAState("S1", true, otherClosureRules), grammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenGrammarsNotEqual()
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ClosureRuleSetDFA dfa = new LR0ClosureRuleSetDFA(new LR0ClosureRuleSetDFAState("S0", true, closureRules), grammar);

        Grammar otherGrammar = new Grammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("B"));
        Set<GrammarClosureRule> otherClosureRules = new HashSet<GrammarClosureRule>();
        otherClosureRules.add(this.buildClosureRule(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertFalse(dfa.structureEquals(new LR0ClosureRuleSetDFA(new LR0ClosureRuleSetDFAState("S1", true, otherClosureRules), otherGrammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseForAutomataWithDifferentClass()
    {
        Grammar grammar = new Grammar();
        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        LR0ClosureRuleSetDFA dfa = new LR0ClosureRuleSetDFA(new LR0ClosureRuleSetDFAState("S0", true, closureRules), grammar);

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
