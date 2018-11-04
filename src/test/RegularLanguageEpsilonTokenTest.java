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
import larp.token.regularlanguage.EpsilonToken;

public class RegularLanguageEpsilonTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        EpsilonToken token = new EpsilonToken();

        assertEquals(new EpsilonToken(), token);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        EpsilonToken token = new EpsilonToken();

        assertNotEquals(new CharacterToken('a'), token);
    }
}
