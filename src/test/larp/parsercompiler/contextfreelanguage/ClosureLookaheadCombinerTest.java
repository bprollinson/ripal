/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ClosureLookaheadCombinerTest
{
    @Test
    public void testCombineReturnsEmptySetWhenClosureRulesSetIsEmpty()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCombineReturnsInitialSetWheClosureRulesContainNoCommonProductions()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCombineCombinesLookaheadsForClosureRulesWithSameProduction()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCombineRemovesRedundantClosureRules()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCombineCombinesOnlyClosureRulesWithSameProduction()
    {
        assertEquals(0, 1);
    }
}
