import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ProductionNode;

public class ConcatenationNodeTest
{
    @Test
    public void testEqualsReturnsTrueForConcatenationNodeWithNoChildren()
    {
        ConcatenationNode node = new ConcatenationNode();

        assertTrue(node.equals(new ConcatenationNode()));
    }

    @Test
    public void testEqualsReturnsTrueForConcatenationNodeWithSameSubtree()
    {
        ConcatenationNode node = new ConcatenationNode();
        node.addChild(new ConcatenationNode());
        node.addChild(new ConcatenationNode());

        ConcatenationNode expectedNode = new ConcatenationNode();
        expectedNode.addChild(new ConcatenationNode());
        expectedNode.addChild(new ConcatenationNode());

        assertTrue(node.equals(expectedNode));
    }

    @Test
    public void testEqualsReturnsFalseForConcatenationNodeWithDifferentSubtree()
    {
        ConcatenationNode node = new ConcatenationNode();
        node.addChild(new ConcatenationNode());
        node.addChild(new ConcatenationNode());

        ConcatenationNode expectedNode = new ConcatenationNode();
        expectedNode.addChild(new ConcatenationNode());
        expectedNode.addChild(new ProductionNode());

        assertFalse(node.equals(expectedNode));
    }

    @Test
    public void testEqualsReturnsFalseForNodeOfOtherType()
    {
        ConcatenationNode node = new ConcatenationNode();

        assertFalse(node.equals(new ProductionNode()));
    }
}
