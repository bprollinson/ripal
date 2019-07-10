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

public class CharacterNodeTest
{
    @Test
    public void testEqualsReturnsTrueForCharacterNodeWithSameCharacter()
    {
        CharacterNode node = new CharacterNode('a');

        assertTrue(node.equals(new CharacterNode('a')));
    }

    @Test
    public void testEqualsReturnsFalseForCharacterNodeWithDifferentCharacter()
    {
        CharacterNode node = new CharacterNode('a');

        assertFalse(node.equals(new CharacterNode('b')));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        CharacterNode node = new CharacterNode('a');

        assertFalse(node.equals(new ConcatenationNode()));
    }
}
