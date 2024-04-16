/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.token.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class NonTerminalTokenTest
{
    @Test
    public void testEqualsReturnsTrueForTokenWithSameClassAndName()
    {
        NonTerminalToken token = new NonTerminalToken("S");

        assertTrue(token.equals(new NonTerminalToken("S")));
    }

    @Test
    public void testEqualsReturnsFalseForTokenWithSameClassAndDifferentName()
    {
        NonTerminalToken token = new NonTerminalToken("S");

        assertFalse(token.equals(new NonTerminalToken("T")));
    }

    @Test
    public void testEqualsReturnsFalseForTokenWithDifferentClass()
    {
        NonTerminalToken token = new NonTerminalToken("S");

        assertFalse(token.equals(new SeparatorToken()));
    }
}
