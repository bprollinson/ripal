/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import larp.parser.regularlanguage.State;

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

    public boolean equals(Object other)
    {
        return other instanceof LR0AcceptAction;
    }
}
