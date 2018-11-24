/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import larp.assertion.Assertion;
import larp.automaton.State;
import larp.parsetree.contextfreelanguage.Node;

public class LR0ParseTableCellAvailableAssertion implements Assertion
{
    private LR0ParseTable parseTable;
    private State state;
    private Node syntaxNode;
    private LR0ParseTableAction action;

    public LR0ParseTableCellAvailableAssertion(LR0ParseTable parseTable, State state, Node syntaxNode, LR0ParseTableAction action)
    {
        this.parseTable = parseTable;
        this.state = state;
        this.syntaxNode = syntaxNode;
        this.action = action;
    }

    public void validate() throws AmbiguousLR0ParseTableException
    {
        LR0ParseTableAction existingAction = this.parseTable.getCell(this.state, this.syntaxNode);
        if (existingAction == null)
        {
            return;
        }

        if (this.action.isShiftAction() && existingAction.isReduceAction())
        {
            throw new LR0ShiftReduceConflictException();
        }
        if (this.action.isReduceAction() && existingAction.isShiftAction())
        {
            throw new LR0ShiftReduceConflictException();
        }
        if (this.action.isReduceAction() && existingAction.isReduceAction())
        {
            throw new LR0ReduceReduceConflictException();
        }

        throw new LR0OtherConflictException();
    }
}
