/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.automaton.State;
import larp.automaton.StateTransition;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;
import java.util.Set;

public class LR0ProductionSetDFAStateTest
{
    @Test
    public void testStructureEqualsReturnsTrueForSameAcceptsValueOnSingleState()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());

        assertTrue(state.structureEquals(new LR0ProductionSetDFAState("S1", true, new HashSet<ContextFreeGrammarParseTreeNode>())));
    }

    @Test
    public void testStructureEqualsReturnsTrueForSameProductionSet()
    {
        Set<ContextFreeGrammarParseTreeNode> productionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, productionSet);

        Set<ContextFreeGrammarParseTreeNode> otherProductionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertTrue(state.structureEquals(new LR0ProductionSetDFAState("S1", true, otherProductionSet)));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentAcceptsValueOnSingleState()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());

        assertFalse(state.structureEquals(new LR0ProductionSetDFAState("S1", false, new HashSet<ContextFreeGrammarParseTreeNode>())));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentProductionSet()
    {
        Set<ContextFreeGrammarParseTreeNode> productionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, productionSet);

        Set<ContextFreeGrammarParseTreeNode> otherProductionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("B")));
        assertFalse(state.structureEquals(new LR0ProductionSetDFAState("S1", true, otherProductionSet)));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentNumberOfTransitions()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        state.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), state));

        assertFalse(state.structureEquals(new LR0ProductionSetDFAState("S1", true, new HashSet<ContextFreeGrammarParseTreeNode>())));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentTransitionTerminalNodes()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        state.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), state));
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("S1", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        otherState.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("b"), otherState));

        assertFalse(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsTrueForSameTransitionTerminalNodes()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        state.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), new LR0ProductionSetDFAState("S1", true, new HashSet<ContextFreeGrammarParseTreeNode>())));
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("S2", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        otherState.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), new LR0ProductionSetDFAState("S3", true, new HashSet<ContextFreeGrammarParseTreeNode>())));

        assertTrue(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentTransitionNonTerminalNodes()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        state.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new NonTerminalNode("A"), state));
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("S1", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        otherState.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new NonTerminalNode("B"), otherState));

        assertFalse(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsTrueForSameTransitionNonTerminalNodes()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        state.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new NonTerminalNode("A"), new LR0ProductionSetDFAState("S1", true, new HashSet<ContextFreeGrammarParseTreeNode>())));
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("S2", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        otherState.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new NonTerminalNode("A"), new LR0ProductionSetDFAState("S3", true, new HashSet<ContextFreeGrammarParseTreeNode>())));

        assertTrue(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsTrueForSameSubsequentProductionSet()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        Set<ContextFreeGrammarParseTreeNode> productionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFAState nextState = new LR0ProductionSetDFAState("S0", true, productionSet);
        state.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), nextState));

        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        Set<ContextFreeGrammarParseTreeNode> otherProductionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFAState otherNextState = new LR0ProductionSetDFAState("S0", true, otherProductionSet);
        otherState.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), otherNextState));

        assertTrue(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsTrueForSameTransitionCharactersInDifferentOrder()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        state.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), new LR0ProductionSetDFAState("S1", true, new HashSet<ContextFreeGrammarParseTreeNode>())));
        state.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("b"), new LR0ProductionSetDFAState("S2", true, new HashSet<ContextFreeGrammarParseTreeNode>())));
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("S3", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        otherState.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("b"), new LR0ProductionSetDFAState("S4", true, new HashSet<ContextFreeGrammarParseTreeNode>())));
        otherState.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), new LR0ProductionSetDFAState("S5", true, new HashSet<ContextFreeGrammarParseTreeNode>())));

        assertTrue(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsFalseForSubsequentStateInequality()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        state.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), new LR0ProductionSetDFAState("S1", true, new HashSet<ContextFreeGrammarParseTreeNode>())));
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("S2", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        otherState.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), new LR0ProductionSetDFAState("S3", false, new HashSet<ContextFreeGrammarParseTreeNode>())));

        assertFalse(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentSubsequentProductionSet()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        Set<ContextFreeGrammarParseTreeNode> productionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFAState nextState = new LR0ProductionSetDFAState("S1", true, productionSet);
        state.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), nextState));

        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("S2", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        Set<ContextFreeGrammarParseTreeNode> otherProductionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("B")));
        LR0ProductionSetDFAState otherNextState = new LR0ProductionSetDFAState("S3", true, otherProductionSet);
        otherState.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), otherNextState));

        assertFalse(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsTrueForStateGraphContainingCycle()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        state.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), state));
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        otherState.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), otherState));

        assertTrue(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsFalseForStateGraphContainingDifferentCycle()
    {
        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("S1", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("S1", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        state1.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), state2));
        state2.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), state3));
        state3.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), state1));

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("S1", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ProductionSetDFAState otherState3 = new LR0ProductionSetDFAState("S1", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        otherState1.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), otherState2));
        otherState2.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), otherState3));
        otherState3.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), otherState2));

        assertFalse(state1.structureEquals(otherState1));
    }

    @Test
    public void testStructureEqualsReturnsFalseForUnmatchedCycleFoundInParallelRecursion()
    {
        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("S0", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("S1", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("S2", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("S3", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        state1.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), state2));
        state1.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("b"), state3));
        state2.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), state4));
        state3.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), state4));

        LR0ProductionSetDFAState otherState1 = new LR0ProductionSetDFAState("S0", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ProductionSetDFAState otherState2 = new LR0ProductionSetDFAState("S1", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ProductionSetDFAState otherState3 = new LR0ProductionSetDFAState("S2", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ProductionSetDFAState otherState4 = new LR0ProductionSetDFAState("S4", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ProductionSetDFAState otherState5 = new LR0ProductionSetDFAState("S5", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        otherState1.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), otherState2));
        otherState1.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("b"), otherState3));
        otherState2.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), otherState4));
        otherState3.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(new TerminalNode("a"), otherState5));

        assertFalse(state1.structureEquals(otherState1));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentStateClass()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarParseTreeNode>());
        TestState otherState = new TestState("S1", true);

        assertFalse(state.structureEquals(otherState));
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

    private class TestState extends State<Character, TestState>
    {
        public TestState(String name, boolean accepting)
        {
            super(name, accepting);
        }
    }
}
