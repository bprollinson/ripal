import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.LR0ProductionSetDFA;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
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

        LR0ProductionSetDFAState expectedStartState = new LR0ProductionSetDFAState("S0", true, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(expectedStartState, grammar);

        LR0ProductionSetDFAState startState = dfa.getStartState();
        assertEquals(expectedStartState, startState);
    }

    @Test
    public void testStructureEqualsReturnsTrue()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, productionSet), grammar);

        Set<ContextFreeGrammarSyntaxNode> otherProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertTrue(dfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", true, otherProductionSet), grammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenStatesNotEqual()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, productionSet), grammar);

        Set<ContextFreeGrammarSyntaxNode> otherProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertFalse(dfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", false, otherProductionSet), grammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenProductionSetsNotEqual()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, productionSet), grammar);

        Set<ContextFreeGrammarSyntaxNode> otherProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("B")));
        assertFalse(dfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", true, otherProductionSet), grammar)));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenGrammarsNotEqual()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        LR0ProductionSetDFA dfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true, productionSet), grammar);

        ContextFreeGrammar otherGrammar = new ContextFreeGrammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("B"));
        Set<ContextFreeGrammarSyntaxNode> otherProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        otherProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        assertFalse(dfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", true, otherProductionSet), otherGrammar)));
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
