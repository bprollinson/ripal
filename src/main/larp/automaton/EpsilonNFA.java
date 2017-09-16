package larp.automaton;

public class EpsilonNFA
{
    private State startState;

    public EpsilonNFA(State startState)
    {
        this.startState = startState;
    }

    public State getStartState()
    {
        return this.startState;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof EpsilonNFA))
        {
            return false;
        }

        return this.startState.equals(((EpsilonNFA)other).getStartState());
    }
}
