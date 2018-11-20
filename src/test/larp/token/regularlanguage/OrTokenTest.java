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
