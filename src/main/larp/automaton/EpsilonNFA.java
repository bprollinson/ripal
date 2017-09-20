package larp.automaton;

public class EpsilonNFA extends FiniteAutomata
{
    public EpsilonNFA(State startState)
    {
        super(startState);
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof FiniteAutomata))
        {
            return false;
        }

        return this.startState.equals(((FiniteAutomata)other).getStartState());
    }
}
