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

public class CharacterNodeTest
{
    @Test
    public void testEqualsReturnsTrueForCharacterNodeWithSameCharacter()
    {
        CharacterNode node = new CharacterNode('a');

        assertEquals(new CharacterNode('a'), node);
    }

    @Test
    public void testEqualsReturnsFalseForCharacterNodeWithDifferentCharacter()
    {
        CharacterNode node = new CharacterNode('a');

        assertNotEquals(new CharacterNode('b'), node);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        CharacterNode node = new CharacterNode('a');

        assertNotEquals(new ConcatenationNode(), node);
    }
}
