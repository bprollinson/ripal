/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsetree.regularlanguage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class KleeneClosureNodeTest
{
    @Test
    public void testEqualsReturnsTrueForKleeneClosureNodeWithNoChildren()
    {
        KleeneClosureNode node = new KleeneClosureNode();

        assertTrue(node.equals(new KleeneClosureNode()));
    }

    @Test
    public void testEqualsReturnsTrueForKleeneClosureNodeWithSameSubtree()
    {
        KleeneClosureNode node = new KleeneClosureNode();
        node.addChild(new CharacterNode('a'));

        KleeneClosureNode otherNode = new KleeneClosureNode();
        otherNode.addChild(new CharacterNode('a'));

        assertTrue(node.equals(otherNode));
    }

    @Test
    public void testEqualsReturnsFalseForKleeneClosureNodeWithDifferentSubtree()
    {
        KleeneClosureNode node = new KleeneClosureNode();
        node.addChild(new CharacterNode('a'));

        KleeneClosureNode otherNode = new KleeneClosureNode();
        otherNode.addChild(new CharacterNode('b'));

        assertFalse(node.equals(otherNode));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        KleeneClosureNode node = new KleeneClosureNode();

        assertFalse(node.equals(new CharacterNode('a')));
    }
}
