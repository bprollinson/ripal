/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import larp.token.regularlanguage.CharacterToken;
import larp.token.regularlanguage.OrToken;

public class CharacterTokenTest
{
    @Test
    public void testEqualsReturnsTrueForCharacterTokenWithSameCharacter()
    {
        CharacterToken token = new CharacterToken('a');

        assertEquals(new CharacterToken('a'), token);
    }

    @Test
    public void testEqualsReturnsFalseForCharacterNodeWithDifferentCharacter()
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
