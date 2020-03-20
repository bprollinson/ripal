/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.automaton;

public class StateTransition<I, S extends State>
{
    private I input;
    private S nextState;

    public StateTransition(I input, S nextState)
    {
        this.input = input;
        this.nextState = nextState;
    }

    public I getInput()
    {
        return this.input;
    }

    public boolean inputEquals(Object otherInput)
    {
        if (this.input == null)
        {
            return otherInput == null;
        }

        return this.input.equals(otherInput);
    }

    public boolean inputEqualsOtherTransition(StateTransition otherTransition)
    {
        return otherTransition.inputEquals(this.input);
    }

    public S getNextState()
    {
        return this.nextState;
    }
}
