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

public class OpenParenthesisTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        OpenParenthesisToken token = new OpenParenthesisToken();

        assertTrue(token.equals(new OpenParenthesisToken()));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        OpenParenthesisToken token = new OpenParenthesisToken();

        assertFalse(token.equals(new CharacterToken('a')));
    }
}
