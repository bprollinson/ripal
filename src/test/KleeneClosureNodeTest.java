/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.parsetree.regularlanguage.CharacterNode;
import larp.parsetree.regularlanguage.KleeneClosureNode;

public class KleeneClosureNodeTest
{
    @Test
    public void testEqualsReturnsTrueForKleeneClosureNodeWithNoChildren()
    {
        KleeneClosureNode node = new KleeneClosureNode();

        assertEquals(new KleeneClosureNode(), node);
    }

    @Test
    public void testEqualsReturnsTrueForKleeneClosureNodeWithSameSubtree()
    {
        KleeneClosureNode node = new KleeneClosureNode();
        node.addChild(new CharacterNode('a'));

        KleeneClosureNode otherNode = new KleeneClosureNode();
        otherNode.addChild(new CharacterNode('a'));

        assertEquals(otherNode, node);
    }

    @Test
    public void testEqualsReturnsFalseForKleeneClosureNodeWithDifferentSubtree()
    {
        KleeneClosureNode node = new KleeneClosureNode();
        node.addChild(new CharacterNode('a'));

        KleeneClosureNode otherNode = new KleeneClosureNode();
        otherNode.addChild(new CharacterNode('b'));

        assertNotEquals(otherNode, node);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        KleeneClosureNode node = new KleeneClosureNode();

        assertNotEquals(new CharacterNode('a'), node);
    }
}
