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

public class CloseParenthesisTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        CloseParenthesisToken token = new CloseParenthesisToken();

        assertTrue(token.equals(new CloseParenthesisToken()));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        CloseParenthesisToken token = new CloseParenthesisToken();

        assertFalse(token.equals(new CharacterToken('a')));
    }
}
