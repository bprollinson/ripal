import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.automaton.contextfreelanguage.FollowSetCalculator;
import larp.grammar.contextfreelanguage.ConcatenationNode;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.EndOfStringNode;
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
}
