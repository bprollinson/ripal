/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.automaton;

import ripal.ComparableStructure;

public class FiniteAutomata<S extends State> implements ComparableStructure
{
    protected S startState;

    public FiniteAutomata(S startState)
    {
        this.startState = startState;
    }

    public S getStartState()
    {
        return this.startState;
    }

    public boolean structureEquals(Object other)
    {
        if (!(other instanceof FiniteAutomata))
        {
            return false;
        }

        return this.startState.structureEquals(((FiniteAutomata)other).getStartState());
    }
}
