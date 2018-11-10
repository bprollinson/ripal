/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ProductionNode;

public class ContextFreeLanguageConcatenationNodeTest
{
    @Test
    public void testEqualsReturnsTrueForConcatenationNodeWithNoChildren()
    {
        ConcatenationNode node = new ConcatenationNode();

        assertEquals(new ConcatenationNode(), node);
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

        assertEquals(expectedNode, node);
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

        assertNotEquals(expectedNode, node);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        ConcatenationNode node = new ConcatenationNode();

        assertNotEquals(new ProductionNode(), node);
    }
}
