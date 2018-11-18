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

import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;

import java.util.HashSet;

public class LR0ShiftActionTest
{
    @Test
    public void testEqualsReturnsTrueForShiftActionWithSameState()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ShiftAction action = new LR0ShiftAction(state);
        LR0ShiftAction otherAction = new LR0ShiftAction(state);

        assertEquals(otherAction, action);
    }

    @Test
    public void testEqualsReturnsFalseForShiftActionWithDifferentState()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ShiftAction action = new LR0ShiftAction(state);
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ShiftAction otherAction = new LR0ShiftAction(otherState);

        assertNotEquals(otherAction, action);
    }

    @Test
    public void testEqualsReturnsFalseForActionWithDifferentClass()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ShiftAction action = new LR0ShiftAction(state);
        LR0ReduceAction otherAction = new LR0ReduceAction(0);

        assertNotEquals(otherAction, action);
    }
}
