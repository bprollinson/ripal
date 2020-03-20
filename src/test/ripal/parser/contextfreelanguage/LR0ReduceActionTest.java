/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parser.contextfreelanguage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

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
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ShiftAction otherAction = new LR0ShiftAction(state);

        assertFalse(action.equals(otherAction));
    }
}
