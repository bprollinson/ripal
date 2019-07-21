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

public class LR0ShiftActionTest
{
    @Test
    public void testEqualsReturnsTrueForShiftActionWithSameState()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<GrammarClosureRule>());
        LR0ShiftAction action = new LR0ShiftAction(state);
        LR0ShiftAction otherAction = new LR0ShiftAction(state);

        assertTrue(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForShiftActionWithDifferentState()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<GrammarClosureRule>());
        LR0ShiftAction action = new LR0ShiftAction(state);
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<GrammarClosureRule>());
        LR0ShiftAction otherAction = new LR0ShiftAction(otherState);

        assertFalse(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForActionWithDifferentClass()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<GrammarClosureRule>());
        LR0ShiftAction action = new LR0ShiftAction(state);
        LR0ReduceAction otherAction = new LR0ReduceAction(0);

        assertFalse(action.equals(otherAction));
    }
}
