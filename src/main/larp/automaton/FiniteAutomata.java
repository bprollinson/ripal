package larp.automaton;

public class FiniteAutomata
{
    protected State startState;

    public FiniteAutomata(State startState)
    {
        this.startState = startState;
    }

    public State getStartState()
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
