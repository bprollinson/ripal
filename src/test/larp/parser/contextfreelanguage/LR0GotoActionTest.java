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

public class LR0GotoActionTest
{
    @Test
    public void testEqualsReturnsTrueForGotoActionWithSameState()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<GrammarClosureRule>(), 0);
        LR0GotoAction action = new LR0GotoAction(state);
        LR0GotoAction otherAction = new LR0GotoAction(state);

        assertTrue(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForGotoActionWithDifferentState()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<GrammarClosureRule>(), 0);
        LR0GotoAction action = new LR0GotoAction(state);
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<GrammarClosureRule>(), 0);
        LR0GotoAction otherAction = new LR0GotoAction(otherState);

        assertFalse(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForActionWithDifferentClass()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<GrammarClosureRule>(), 0);
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<GrammarClosureRule>(), 0);
        LR0GotoAction action = new LR0GotoAction(state);
        LR0ShiftAction otherAction = new LR0ShiftAction(otherState);

        assertFalse(action.equals(otherAction));
    }
}
