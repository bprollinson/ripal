/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parser.contextfreelanguage;

import ripal.automaton.State;

public class LR0AcceptAction implements LR0ParseTableAction
{
    public boolean supportsTransition()
    {
        return false;
    }

    public State getNextState()
    {
        return null;
    }

    public boolean isShiftAction()
    {
        return false;
    }

    public boolean isReduceAction()
    {
        return false;
    }

    public boolean isGotoAction()
    {
        return false;
    }

    public boolean isAcceptAction()
    {
        return true;
    }

    public boolean equals(Object other)
    {
        return other instanceof LR0AcceptAction;
    }
}
