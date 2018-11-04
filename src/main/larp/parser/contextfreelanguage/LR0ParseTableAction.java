/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import larp.parser.regularlanguage.State;

public interface LR0ParseTableAction
{
    public boolean supportsTransition();
    public State getNextState();
}
