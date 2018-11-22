/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.Node;

import java.util.HashSet;

public class LR0GotoActionTest
{
    @Test
    public void testEqualsReturnsTrueForGotoActionWithSameState()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<Node>());
        LR0GotoAction action = new LR0GotoAction(state);
        LR0GotoAction otherAction = new LR0GotoAction(state);

        assertEquals(otherAction, action);
    }

    @Test
    public void testEqualsReturnsFalseForGotoActionWithDifferentState()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<Node>());
        LR0GotoAction action = new LR0GotoAction(state);
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<Node>());
        LR0GotoAction otherAction = new LR0GotoAction(otherState);

        assertNotEquals(otherAction, action);
    }

    @Test
    public void testEqualsReturnsFalseForActionWithDifferentClass()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<Node>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<Node>());
        LR0GotoAction action = new LR0GotoAction(state);
        LR0ShiftAction otherAction = new LR0ShiftAction(otherState);

        assertNotEquals(otherAction, action);
    }
}
