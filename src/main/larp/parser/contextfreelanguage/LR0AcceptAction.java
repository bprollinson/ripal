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
