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
import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;

import java.util.HashSet;
import java.util.Set;

public class LR0ProductionSetDFATest
{
    @Test
    public void testGetStartStateReturnsLR0ProductionSetDFAState()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        LR0ProductionSetDFAState expectedStartState = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(expectedStartState, grammar);

        LR0ProductionSetDFAState startState = dfa.getStartState();
        assertEquals(expectedStartState, startState);
    }

    @Test
    public void testStructureEqualsReturnsTrue()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<ContextFreeGrammarParseTreeNode> productionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, productionSet), grammar);

        Set<ContextFreeGrammarParseTreeNode> otherProductionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertTrue(dfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", true, otherProductionSet), grammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenStatesNotEqual()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<ContextFreeGrammarParseTreeNode> productionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, productionSet), grammar);

        Set<ContextFreeGrammarParseTreeNode> otherProductionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertFalse(dfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", false, otherProductionSet), grammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenProductionSetsNotEqual()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<ContextFreeGrammarParseTreeNode> productionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, productionSet), grammar);

        Set<ContextFreeGrammarParseTreeNode> otherProductionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("B")));
        assertFalse(dfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", true, otherProductionSet), grammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenGrammarsNotEqual()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        Set<ContextFreeGrammarParseTreeNode> productionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, productionSet), grammar);

        ContextFreeGrammar otherGrammar = new ContextFreeGrammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("B"));
        Set<ContextFreeGrammarParseTreeNode> otherProductionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertFalse(dfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", true, otherProductionSet), otherGrammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseForAutomataWithDifferentClass()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        Set<ContextFreeGrammarParseTreeNode> productionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, productionSet), grammar);

        assertFalse(dfa.structureEquals(new DFA(new DFAState("S1", true))));
    }

    private ProductionNode buildProduction(NonTerminalNode nonTerminalNode, ContextFreeGrammarParseTreeNode... rightHandNodes)
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(nonTerminalNode);

        ConcatenationNode concatenationNode = new ConcatenationNode();
        for (ContextFreeGrammarParseTreeNode rightHandNode: rightHandNodes)
        {
            concatenationNode.addChild(rightHandNode);
        }
        productionNode.addChild(concatenationNode);

        return productionNode;
    }
}