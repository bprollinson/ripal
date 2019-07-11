/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.token.regularlanguage;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        OrToken token = new OrToken();

        assertTrue(token.equals(new OrToken()));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        OrToken token = new OrToken();

        assertFalse(token.equals(new CharacterToken('a')));
    }
}
