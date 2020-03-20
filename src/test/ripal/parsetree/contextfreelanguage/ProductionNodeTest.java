/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsetree.contextfreelanguage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

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

        ProductionNode otherNode = new ProductionNode();
        otherNode.addChild(new ProductionNode());
        otherNode.addChild(new ProductionNode());

        assertTrue(node.equals(otherNode));
    }

    @Test
    public void testEqualsReturnsFalseForProductionNodeWithDifferentSubtree()
    {
        ProductionNode node = new ProductionNode();
        node.addChild(new ProductionNode());
        node.addChild(new ProductionNode());

        ProductionNode otherNode = new ProductionNode();
        otherNode.addChild(new ProductionNode());
        otherNode.addChild(new ConcatenationNode());

        assertFalse(node.equals(otherNode));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        ProductionNode node = new ProductionNode();

        assertFalse(node.equals(new ConcatenationNode()));
    }
}
