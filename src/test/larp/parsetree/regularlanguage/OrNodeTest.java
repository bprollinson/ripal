/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsetree.regularlanguage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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

        assertEquals(expectedNode, node);
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

        assertNotEquals(expectedNode, node);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        OrNode node = new OrNode();

        assertNotEquals(new CharacterNode('a'), node);
    }
}
