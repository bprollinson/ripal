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
}
