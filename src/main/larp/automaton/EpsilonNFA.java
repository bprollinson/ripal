package larp.automaton;

public class EpsilonNFA extends FiniteAutomata
{
    public EpsilonNFA(State startState)
    {
        super(startState);
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
