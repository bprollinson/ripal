import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.syntaxcompiler.contextfreelanguage.ContextFreeGrammarClosureCalculator;

import java.util.HashSet;
import java.util.Set;

public class ContextFreeGrammarClosureCalculatorTest
{
    @Test
    public void testCalculateAddsSimpleTerminalProduction()
    {
        ContextFreeGrammarClosureCalculator calculator = new ContextFreeGrammarClosureCalculator();

        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        cfg.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<ContextFreeGrammarSyntaxNode> expectedProductionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedProductionSet.add(this.buildProduction(new NonTerminalNode("A"), new DotNode(), new TerminalNode("a")));

        assertEquals(expectedProductionSet, calculator.calculate(cfg, productionSet));
    }

    @Test
    public void testCalculateAddsSimpleNonTerminalProduction()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateDoesNotAddProductionWhenNoneAvailable()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateDoesNotAddProductionWhenNonReachable()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateAddsTerminalChain()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateAddsClosureFromNonFirstNonTerminalInProduction()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateDoesNotAddProductionsForNonTerminal()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateDoesNotAddProductionsForSubsequentNonTerminal()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateCalculatesClosureIgnoringImmediateInfiniteLoop()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateCalculatesClosureIgnoringEventualInfiniteLoop()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateExpandsImmediateNonTerminalIntoParallelTerminals()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateExpandsEventualNonTerminalIntoParallelTerminals()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateExpandsImmediateNonTerminalIntoParallelNonTerminals()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateExpandsEventualNonTerminalIntoParallelNonTerminals()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateAddsTerminalChainInReverseOrder()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateDoesNotExpandProductionSetWhenDotMissing()
    {
        assertEquals(0, 1);
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
