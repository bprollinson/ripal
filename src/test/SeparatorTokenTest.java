/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;

public class SeparatorTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        SeparatorToken token = new SeparatorToken();

        assertEquals(new SeparatorToken(), token);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        SeparatorToken token = new SeparatorToken();

        assertNotEquals(new NonTerminalToken("S"), token);
    }
}
