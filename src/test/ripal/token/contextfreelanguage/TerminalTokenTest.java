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

public class TerminalTokenTest
{
    @Test
    public void testEqualsReturnsTrueForTokenWithSameClassAndValue()
    {
        TerminalToken token = new TerminalToken("a");

        assertTrue(token.equals(new TerminalToken("a")));
    }

    @Test
    public void testEqualsReturnsFalseForTokenWithSameClassAndDifferentValue()
    {
        TerminalToken token = new TerminalToken("a");

        assertFalse(token.equals(new TerminalToken("b")));
    }

    @Test
    public void testEqualsReturnsFalseForTokenWithDifferentClass()
    {
        TerminalToken token = new TerminalToken("a");

        assertFalse(token.equals(new SeparatorToken()));
    }
}
