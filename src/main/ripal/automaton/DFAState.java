/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.automaton;

public class DFAState extends State<Character, DFAState>
{
    public DFAState(String name, boolean accepting)
    {
        super(name, accepting);
    }

    public State getNextState(Character input)
    {
        for (int i = 0; i < this.transitions.size(); i++)
        {
            StateTransition transition = this.transitions.get(i);
            if (transition.inputEquals(input))
            {
                return transition.getNextState();
            }
        }

        return null;
    }
}
