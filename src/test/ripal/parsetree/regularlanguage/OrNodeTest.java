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

public class OrNodeTest
{
    @Test
    public void testEqualsReturnsTrueForOrNodeWithSameSubtree()
    {
        OrNode node = new OrNode();
        node.addChild(new CharacterNode('a'));
        node.addChild(new CharacterNode('b'));

        OrNode otherNode = new OrNode();
        otherNode.addChild(new CharacterNode('a'));
        otherNode.addChild(new CharacterNode('b'));

        assertTrue(node.equals(otherNode));
    }

    @Test
    public void testEqualsReturnsFalseForOrNodeWithDifferentSubtree()
    {
        OrNode node = new OrNode();
        node.addChild(new CharacterNode('a'));
        node.addChild(new CharacterNode('b'));

        OrNode otherNode = new OrNode();
        otherNode.addChild(new CharacterNode('a'));
        otherNode.addChild(new CharacterNode('c'));

        assertFalse(node.equals(otherNode));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        OrNode node = new OrNode();

        assertFalse(node.equals(new CharacterNode('a')));
    }
}
