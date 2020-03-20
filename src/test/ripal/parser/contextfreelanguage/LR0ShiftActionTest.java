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

public class LR0ShiftActionTest
{
    @Test
    public void testEqualsReturnsTrueForShiftActionWithSameState()
    {
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ShiftAction action = new LR0ShiftAction(state);
        LR0ShiftAction otherAction = new LR0ShiftAction(state);

        assertTrue(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForShiftActionWithDifferentState()
    {
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ShiftAction action = new LR0ShiftAction(state);
        LR0ClosureRuleSetDFAState otherState = new LR0ClosureRuleSetDFAState("", false);
        LR0ShiftAction otherAction = new LR0ShiftAction(otherState);

        assertFalse(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForActionWithDifferentClass()
    {
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ShiftAction action = new LR0ShiftAction(state);
        LR0ReduceAction otherAction = new LR0ReduceAction(0);

        assertFalse(action.equals(otherAction));
    }
}
