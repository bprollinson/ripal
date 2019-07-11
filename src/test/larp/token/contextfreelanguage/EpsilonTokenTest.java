/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.token.contextfreelanguage;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EpsilonTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        EpsilonToken token = new EpsilonToken();

        assertTrue(token.equals(new EpsilonToken()));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        EpsilonToken token = new EpsilonToken();

        assertFalse(token.equals(new TerminalToken("a")));
    }
}
