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

public class OrTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        OrToken token = new OrToken();

        assertEquals(new OrToken(), token);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        OrToken token = new OrToken();

        assertNotEquals(new CharacterToken('a'), token);
    }
}
