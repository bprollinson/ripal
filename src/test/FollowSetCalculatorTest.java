import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.syntaxcompiler.contextfreelanguage.FollowSetCalculator;

import java.util.HashSet;
import java.util.Set;

public class FollowSetCalculatorTest
{
    @Test
    public void testGetFollowReturnsEmptySetForEmptyCFG()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsEmptySetForNonTerminalNodeNotAppearingInCFG()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsEmptySetForDanglingNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsEndOfStringSymbolForStartNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new EndOfStringNode());
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeAndEndOfStringSymbolForStartNonTerminalFollowedByTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new EndOfStringNode());
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeForNonStartNonTerminalFollowedByTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsFirstCharacterFromMultipleCharacterTerminalToken()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S"), new TerminalNode("aa"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new EndOfStringNode());
        expectedFollows.add(new TerminalNode("a"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsFollowFromMultipleProductions()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("A"), new TerminalNode("c"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("b"));
        expectedFollows.add(new TerminalNode("c"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeAndEndOfStringSymbolForStartNonTerminalFollowedByNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new EndOfStringNode());
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("S")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeForNonStartNonTerminalFollowedByNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeFromASeriesOfProductions()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("d"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("d"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsEndOfStringSymbolForNonTerminalProducedFromStartNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new EndOfStringNode());
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsParentFollowForNonTerminalProducedFromNonStartNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("B"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("b"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("C")));
    }

    @Test
    public void testGetFollowReturnsAncestorFollowForNonTerminalProducedFromNonStartNonTerminal()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("B"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("a"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("C")));
    }

    @Test
    public void testGetFollowChainsParentFollowInArbitraryOrder()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("B"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("D"), new TerminalNode("d"));
        grammar.addProduction(new NonTerminalNode("C"), new NonTerminalNode("D"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("a"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("D")));
    }

    @Test
    public void testGetFollowReturnsEndOfStringSymbolWhenNextNonTerminalGoesToEpsilon()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new EndOfStringNode());
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsEndOfStringSymbolWhenNextNonTerminalSometimesGoesToEpsilon()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("b"));
        expectedFollows.add(new EndOfStringNode());
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsSubsequentTerminalNodeWhenNextNonTerminalNodeGoesToEpsilon()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"), new TerminalNode("c"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("c"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsTerminalNodeFromSubsequentNonTerminalNodeWhenNextNonTerminalNodeGoesToEpsilon()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        expectedFollows.add(new TerminalNode("c"));
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }

    @Test
    public void testGetFollowReturnsFollowForNonDanglingProductionPath()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        FollowSetCalculator calculator = new FollowSetCalculator(grammar);
        Set<ContextFreeGrammarSyntaxNode> expectedFollows = new HashSet<ContextFreeGrammarSyntaxNode>();
        assertEquals(expectedFollows, calculator.getFollow(new NonTerminalNode("A")));
    }
}
