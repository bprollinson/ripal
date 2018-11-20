/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.token.contextfreelanguage;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TerminalTokenTest
{
    @Test
    public void testEqualsReturnsTrueForTokenWithSameClassAndValue()
    {
        TerminalToken token = new TerminalToken("a");

        assertEquals(new TerminalToken("a"), token);
    }

    @Test
    public void testEqualsReturnsFalseForTokenWithSameClassAndDifferentValue()
    {
        TerminalToken token = new TerminalToken("a");

        assertNotEquals(new TerminalToken("b"), token);
    }

    @Test
    public void testEqualsReturnsFalseForTokenWithDifferentClass()
    {
        TerminalToken token = new TerminalToken("a");

        assertNotEquals(new SeparatorToken(), token);
    }
}
