/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsetree.regularlanguage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

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
        expectedNode.addChild(new CharacterNode('a'));

        assertFalse(node.equals(expectedNode));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        ConcatenationNode node = new ConcatenationNode();

        assertFalse(node.equals(new CharacterNode('a')));
    }
}
