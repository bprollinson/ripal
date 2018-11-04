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

public class NonTerminalTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClassAndName()
    {
        NonTerminalToken token = new NonTerminalToken("S");

        assertEquals(new NonTerminalToken("S"), token);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithSameClassAndDifferentName()
    {
        NonTerminalToken token = new NonTerminalToken("S");

        assertNotEquals(new NonTerminalToken("T"), token);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        NonTerminalToken token = new NonTerminalToken("S");

        assertNotEquals(new SeparatorToken(), token);
    }
}
