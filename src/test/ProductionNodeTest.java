import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ConcatenationNode;
import larp.grammar.contextfreelanguage.ProductionNode;

public class ProductionNodeTest
{
    @Test
    public void testEqualsReturnsTrueForProductionNodeWithNoChildren()
    {
        ProductionNode node = new ProductionNode();

        assertTrue(node.equals(new ProductionNode()));
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

        assertTrue(node.equals(expectedNode));
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

        assertFalse(node.equals(expectedNode));
    }

    @Test
    public void testEqualsReturnsFalseForNodeOfOtherType()
    {
        ProductionNode node = new ProductionNode();

        assertFalse(node.equals(new ConcatenationNode()));
    }
}
