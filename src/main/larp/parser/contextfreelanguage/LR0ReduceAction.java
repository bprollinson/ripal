/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import larp.automaton.State;

public class LR0ReduceAction implements LR0ParseTableAction
{
    private int productionIndex;

    public LR0ReduceAction(int productionIndex)
    {
        this.productionIndex = productionIndex;
    }

    public boolean supportsTransition()
    {
        return false;
    }

    public State getNextState()
    {
        return null;
    }

    public int getProductionIndex()
    {
        return this.productionIndex;
    }

    public boolean isShiftAction()
    {
        return false;
    }

    public boolean isReduceAction()
    {
        return true;
    }

    public boolean isGotoAction()
    {
        return false;
    }

    public boolean isAcceptAction()
    {
        return false;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LR0ReduceAction))
        {
            return false;
        }

        return this.productionIndex == ((LR0ReduceAction)other).getProductionIndex();
    }
}
