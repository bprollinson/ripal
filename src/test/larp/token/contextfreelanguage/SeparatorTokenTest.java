/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.token.contextfreelanguage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SeparatorTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        SeparatorToken token = new SeparatorToken();

        assertTrue(token.equals(new SeparatorToken()));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        SeparatorToken token = new SeparatorToken();

        assertFalse(token.equals(new NonTerminalToken("S")));
    }
}
