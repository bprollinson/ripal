import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.automaton.contextfreelanguage.FollowSetCalculator;
import larp.grammar.contextfreelanguage.ConcatenationNode;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.EndOfStringNode;
import larp.grammar.contextfreelanguage.EpsilonNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.ProductionNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.parsetable.ContextFreeGrammar;

import java.util.HashSet;

public class FollowSetCalculatorTest
{
    @Test
    public void testGetFollowReturnsEndOfStringSymbolForStartNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new EndOfStringNode());
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeAndEndOfStringSymbolForStartNonTerminalFollowedByTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("S"));
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new EndOfStringNode());
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeForNonStartNonTerminalFollowedByTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsFirstCharacterFromMultipleCharacterTerminalToken()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("S"));
        concatenationNode.addChild(new TerminalNode("aa"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new EndOfStringNode());
        expectedFollows.add(new TerminalNode("a"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsFollowFromMultipleProductions()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        concatenationNode.addChild(new TerminalNode("c"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("b"));
        expectedFollows.add(new TerminalNode("c"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeAndEndOfStringSymbolForStartNonTerminalFollowedByNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("S"));
        concatenationNode.addChild(new NonTerminalNode("B"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("B"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new EndOfStringNode());
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeForNonStartNonTerminalFollowedByNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        concatenationNode.addChild(new NonTerminalNode("B"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("B"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsEndOfStringSymbolForNonTerminalProducedFromStartNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new EndOfStringNode());
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsParentFollowForNonTerminalProducedFromNonStartNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("B"));
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("B"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("C"));
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("C"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("c"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("C")));
    }

    @Test
    public void testGetFollowReturnsAncestorFollowForNonTerminalProducedFromNonStartNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("B"));
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("B"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("C"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("C"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("c"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("a"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("C")));
    }

    @Test
    public void testGetFollowChainsParentFollowInArbitraryOrder()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("B"));
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("D"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("d"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("C"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("D"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("B"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("C"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("a"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("D")));
    }

    @Test
    public void testGetFollowReturnsEndOfStringSymbolWhenNextNonTerminalGoesToEpsilon()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        concatenationNode.addChild(new NonTerminalNode("B"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("B"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new EpsilonNode());
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new EndOfStringNode());
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsEndOfStringSymbolWhenNextNonTerminalSometimesGoesToEpsilon()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        concatenationNode.addChild(new NonTerminalNode("B"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("B"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new EpsilonNode());
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("B"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("b"));
        expectedFollows.add(new EndOfStringNode());
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsSubsequentTerminalNodeWhenNextNonTerminalNodeGoesToEpsilon()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        concatenationNode.addChild(new NonTerminalNode("B"));
        concatenationNode.addChild(new TerminalNode("c"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("B"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new EpsilonNode());
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("c"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsSubsequentNonTerminalNodeWhenNextNonTerminalNodeGoesToEpsilon()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        concatenationNode.addChild(new NonTerminalNode("B"));
        concatenationNode.addChild(new NonTerminalNode("C"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("B"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new EpsilonNode());
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("C"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("c"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        HashSet<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("c"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }
}
