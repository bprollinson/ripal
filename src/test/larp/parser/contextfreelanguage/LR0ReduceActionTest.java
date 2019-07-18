/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parsercompiler.contextfreelanguage.GrammarClosureRule;

import java.util.HashSet;

public class LR0ReduceActionTest
{
    @Test
    public void testEqualsReturnsTrueForReduceActionWithSameProductionIndex()
    {
        LR0ReduceAction action = new LR0ReduceAction(0);
        LR0ReduceAction otherAction = new LR0ReduceAction(0);

        assertTrue(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForReduceActionWithDifferentProductionIndex()
    {
        LR0ReduceAction action = new LR0ReduceAction(0);
        LR0ReduceAction otherAction = new LR0ReduceAction(1);

        assertFalse(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForActionWithDifferentClass()
    {
        LR0ReduceAction action = new LR0ReduceAction(0);
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<GrammarClosureRule>(), 0);
        LR0ShiftAction otherAction = new LR0ShiftAction(state);

        assertFalse(action.equals(otherAction));
    }
}
