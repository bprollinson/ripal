/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.regularlanguage;

import ripal.automaton.State;

public class StateGroup<S extends State>
{
    private S startState;
    private S endState;

    public StateGroup(S startState, S endState)
    {
        this.startState = startState;
        this.endState = endState;
    }

    public S getStartState()
    {
        return this.startState;
    }

    public S getEndState()
    {
        return this.endState;
    }
}
