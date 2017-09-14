package larp.automaton;

public class StateGroup
{
    private State startState;
    private State endState;

    public StateGroup(State startState, State endState)
    {
        this.startState = startState;
        this.endState = endState;
    }

    public State getStartState()
    {
        return this.startState;
    }

    public State getEndState()
    {
        return this.getEndState();
    }
}
