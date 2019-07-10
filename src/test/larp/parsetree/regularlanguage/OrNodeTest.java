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

public class OrNodeTest
{
    @Test
    public void testEqualsReturnsTrueForOrNodeWithSameSubtree()
    {
        OrNode node = new OrNode();
        node.addChild(new CharacterNode('a'));
        node.addChild(new CharacterNode('b'));

        OrNode expectedNode = new OrNode();
        expectedNode.addChild(new CharacterNode('a'));
        expectedNode.addChild(new CharacterNode('b'));

        assertTrue(node.equals(expectedNode));
    }

    @Test
    public void testEqualsReturnsFalseForOrNodeWithDifferentSubtree()
    {
        OrNode node = new OrNode();
        node.addChild(new CharacterNode('a'));
        node.addChild(new CharacterNode('b'));

        OrNode expectedNode = new OrNode();
        expectedNode.addChild(new CharacterNode('a'));
        expectedNode.addChild(new CharacterNode('c'));

        assertFalse(node.equals(expectedNode));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        OrNode node = new OrNode();

        assertFalse(node.equals(new CharacterNode('a')));
    }
}
