/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsetree.regularlanguage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

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

        ConcatenationNode otherNode = new ConcatenationNode();
        otherNode.addChild(new ConcatenationNode());
        otherNode.addChild(new ConcatenationNode());

        assertTrue(node.equals(otherNode));
    }

    @Test
    public void testEqualsReturnsFalseForConcatenationNodeWithDifferentSubtree()
    {
        ConcatenationNode node = new ConcatenationNode();
        node.addChild(new ConcatenationNode());
        node.addChild(new ConcatenationNode());

        ConcatenationNode otherNode = new ConcatenationNode();
        otherNode.addChild(new ConcatenationNode());
        otherNode.addChild(new CharacterNode('a'));

        assertFalse(node.equals(otherNode));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        ConcatenationNode node = new ConcatenationNode();

        assertFalse(node.equals(new CharacterNode('a')));
    }
}
