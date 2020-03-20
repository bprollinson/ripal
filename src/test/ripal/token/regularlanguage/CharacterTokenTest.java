/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.token.regularlanguage;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CharacterTokenTest
{
    @Test
    public void testEqualsReturnsTrueForTokenWithSameClassAndCharacter()
    {
        CharacterToken token = new CharacterToken('a');

        assertTrue(token.equals(new CharacterToken('a')));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithSameClassAndDifferentCharacter()
    {
        CharacterToken token = new CharacterToken('a');

        assertFalse(token.equals(new CharacterToken('b')));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        CharacterToken token = new CharacterToken('a');

        assertFalse(token.equals(new OrToken()));
    }
}
