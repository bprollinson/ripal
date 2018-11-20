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
