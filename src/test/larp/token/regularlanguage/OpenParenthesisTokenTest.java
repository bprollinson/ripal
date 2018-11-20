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

public class OpenParenthesisTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        OpenParenthesisToken token = new OpenParenthesisToken();

        assertEquals(new OpenParenthesisToken(), token);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        OpenParenthesisToken token = new OpenParenthesisToken();

        assertNotEquals(new CharacterToken('a'), token);
    }
}
