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

public class CloseParenthesisTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        CloseParenthesisToken token = new CloseParenthesisToken();

        assertEquals(new CloseParenthesisToken(), token);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        CloseParenthesisToken token = new CloseParenthesisToken();

        assertNotEquals(new CharacterToken('a'), token);
    }
}
