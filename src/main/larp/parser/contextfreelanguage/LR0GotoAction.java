package larp.parser.contextfreelanguage;

import larp.parser.regularlanguage.State;

public class LR0GotoAction implements LR0ParseTableAction
{
    private State state;

    public LR0GotoAction(State state)
    {
        this.state = state;
    }

    public boolean isRowLevelAction()
    {
        return false;
    }

    public State getState()
    {
        return this.state;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LR0GotoAction))
        {
            return false;
        }

        return this.state.equals(((LR0GotoAction)other).getState());
    }
}
