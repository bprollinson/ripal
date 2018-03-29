import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.regularlanguage.StateTransition;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;

import java.util.HashSet;
import java.util.Set;

public class LR0ProductionSetDFAStateTest
{
    @Test
    public void testStructureEqualsReturnsTrueForSameProductionSet()
    {
        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, productionSet);

        Set<ContextFreeGrammarSyntaxNode> otherProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertTrue(state.structureEquals(new LR0ProductionSetDFAState("S1", true, otherProductionSet)));
    }

    @Test
    public void testStructureEqualsReturnsTrueForSameSubsequentProductionSet()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarSyntaxNode>());
        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFAState nextState = new LR0ProductionSetDFAState("S0", true, productionSet);
        state.addTransition(new StateTransition('a', nextState));

        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarSyntaxNode>());
        Set<ContextFreeGrammarSyntaxNode> otherProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFAState otherNextState = new LR0ProductionSetDFAState("S0", true, productionSet);
        otherState.addTransition(new StateTransition('a', otherNextState));

        assertTrue(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentProductionSet()
    {
        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, productionSet);

        Set<ContextFreeGrammarSyntaxNode> otherProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("B")));
        assertFalse(state.structureEquals(new LR0ProductionSetDFAState("S1", true, otherProductionSet)));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentSubsequentProductionSet()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarSyntaxNode>());
        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFAState nextState = new LR0ProductionSetDFAState("S0", true, productionSet);
        state.addTransition(new StateTransition('a', nextState));

        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarSyntaxNode>());
        Set<ContextFreeGrammarSyntaxNode> otherProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("B")));
        LR0ProductionSetDFAState otherNextState = new LR0ProductionSetDFAState("S0", true, productionSet);
        otherState.addTransition(new StateTransition('a', otherNextState));

        assertFalse(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentNumberOfTransitions()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarSyntaxNode>());
        state.addTransition(new StateTransition('a', state));

        assertFalse(state.structureEquals(new LR0ProductionSetDFAState("S1", true, new HashSet<ContextFreeGrammarSyntaxNode>())));
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
