import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ProductionNode;

public class ProductionNodeTest
{
    @Test
    public void testEqualsReturnsTrueForProductionNodeWithNoChildren()
    {
        ProductionNode node = new ProductionNode();

        assertEquals(new ProductionNode(), node);
    }

    @Test
    public void testEqualsReturnsTrueForProductionNodeWithSameSubtree()
    {
        ProductionNode node = new ProductionNode();
        node.addChild(new ProductionNode());
        node.addChild(new ProductionNode());

        ProductionNode expectedNode = new ProductionNode();
        expectedNode.addChild(new ProductionNode());
        expectedNode.addChild(new ProductionNode());

        assertEquals(expectedNode, node);
    }

    @Test
    public void testEqualsReturnsFalseForProductionNodeWithDifferentSubtree()
    {
        ProductionNode node = new ProductionNode();
        node.addChild(new ProductionNode());
        node.addChild(new ProductionNode());

        ProductionNode expectedNode = new ProductionNode();
        expectedNode.addChild(new ProductionNode());
        expectedNode.addChild(new ConcatenationNode());

        assertNotEquals(expectedNode, node);
    }

    @Test
    public void testEqualsReturnsFalseForNodeOfOtherType()
    {
        ProductionNode node = new ProductionNode();

        assertNotEquals(new ConcatenationNode(), node);
    }
}
