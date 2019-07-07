/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class GrammarClosureRuleTest
{
    @Test
    public void testEqualsReturnsTrueForRuleWithSameSymbolsAndLookaheads()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForDifferentSymbols()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testNotEqualsReturnsFalseForDIfferentLookaheads()
    {
        assertEquals(0, 1);
    }
}
