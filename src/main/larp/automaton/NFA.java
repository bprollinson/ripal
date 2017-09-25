package larp.automaton;

public class NFA extends FiniteAutomata
{
    public NFA(State startState)
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
