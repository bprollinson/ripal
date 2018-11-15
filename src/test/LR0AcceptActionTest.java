/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;

import java.util.HashSet;

public class LR0AcceptActionTest
{
    @Test
    public void testEqualsReturnsTrueForActionWithSameClass()
    {
        LR0AcceptAction action = new LR0AcceptAction();
        LR0AcceptAction otherAction = new LR0AcceptAction();

        assertEquals(otherAction, action);
    }

    @Test
    public void testEqualsReturnsFalseForActionWithDifferentClass()
    {
        LR0AcceptAction action = new LR0AcceptAction();
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ShiftAction otherAction = new LR0ShiftAction(state);

        assertNotEquals(otherAction, action);
    }
}
