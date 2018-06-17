package larp.parser.contextfreelanguage;

import larp.parser.regularlanguage.State;

public class LR0ShiftAction implements LR0ParseTableAction
{
    private State state;

    public LR0ShiftAction(State state)
    {
        this.state = state;
    }

    public State getState()
    {
        return this.state;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LR0ShiftAction))
        {
            return false;
        }

        return this.state.equals(((LR0ShiftAction)other).getState());
    }
}
