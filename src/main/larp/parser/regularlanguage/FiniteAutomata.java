package larp.parser.regularlanguage;

import larp.ComparableStructure;

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
