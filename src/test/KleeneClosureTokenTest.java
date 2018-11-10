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
import larp.token.regularlanguage.KleeneClosureToken;

public class KleeneClosureTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        KleeneClosureToken token = new KleeneClosureToken();

        assertEquals(new KleeneClosureToken(), token);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        KleeneClosureToken token = new KleeneClosureToken();

        assertNotEquals(new CharacterToken('a'), token);
    }
}
