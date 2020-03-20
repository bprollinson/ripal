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

public class LR0GotoActionTest
{
    @Test
    public void testEqualsReturnsTrueForGotoActionWithSameState()
    {
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0GotoAction action = new LR0GotoAction(state);
        LR0GotoAction otherAction = new LR0GotoAction(state);

        assertTrue(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForGotoActionWithDifferentState()
    {
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0GotoAction action = new LR0GotoAction(state);
        LR0ClosureRuleSetDFAState otherState = new LR0ClosureRuleSetDFAState("", false);
        LR0GotoAction otherAction = new LR0GotoAction(otherState);

        assertFalse(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForActionWithDifferentClass()
    {
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState otherState = new LR0ClosureRuleSetDFAState("", false);
        LR0GotoAction action = new LR0GotoAction(state);
        LR0ShiftAction otherAction = new LR0ShiftAction(otherState);

        assertFalse(action.equals(otherAction));
    }
}
