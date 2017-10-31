import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.automaton.contextfreelanguage.FirstSetCalculator;
import larp.grammar.contextfreelanguage.ConcatenationNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.ProductionNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.parsetable.ContextFreeGrammar;

import java.util.HashSet;

public class FirstSetCalculatorTest
{
    @Test
    public void testGetFirstReturnsSingleTerminalDirectlyFromProduction()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        FirstSetCalculator calculator = new FirstSetCalculator(grammar);
        HashSet<TerminalNode> expectedFirsts = new HashSet<TerminalNode>();
        expectedFirsts.add(new TerminalNode("a"));
        assertEquals(expectedFirsts, calculator.getFirst(0));
    }
}