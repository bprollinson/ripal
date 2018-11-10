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
import larp.token.regularlanguage.OpenParenthesisToken;

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
