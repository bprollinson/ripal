import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.syntaxcompiler.contextfreelanguage.ProductionNodeDotRepository;

public class ProductionNodeDotRepositoryTest
{
    @Test
    public void testFindProductionSymbolAfterDotFindsProductionSymbolWhenDotPresent()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new DotNode());
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);

        TerminalNode expectedNode = new TerminalNode("b");
        assertEquals(expectedNode, repository.findProductionSymbolAfterDot(productionNode));
    }

    @Test
    public void testFindProductionSymbolAfterDotReturnsNullWhenDotNotPresent()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testAddDotToProductionRightHandSideAddsDot()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testShiftDotInProductionShiftsDot()
    {
        assertEquals(0, 1);
    }
}
