/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.token.regularlanguage;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CharacterTokenTest
{
    @Test
    public void testEqualsReturnsTrueForTokenWithSameClassAndCharacter()
    {
        CharacterToken token = new CharacterToken('a');

        assertEquals(new CharacterToken('a'), token);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithSameClassAndDifferentCharacter()
    {
        CharacterToken token = new CharacterToken('a');

        assertNotEquals(new CharacterToken('b'), token);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        CharacterToken token = new CharacterToken('a');

        assertNotEquals(new OrToken(), token);
    }
}
