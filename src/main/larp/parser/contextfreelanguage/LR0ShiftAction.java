/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import larp.automaton.State;

public class LR0ShiftAction implements LR0ParseTableAction
{
    private State nextState;

    public LR0ShiftAction(State nextState)
    {
        this.nextState = nextState;
    }

    public boolean supportsTransition()
    {
        return true;
    }

    public State getNextState()
    {
        return this.nextState;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LR0ShiftAction))
        {
            return false;
        }

        return this.nextState.equals(((LR0ShiftAction)other).getNextState());
    }
}
