package larp.parser.regularlanguage;

public class StateGroup<S extends State>
{
    private S startState;
    private S endState;

    public StateGroup(S startState, S endState)
    {
        this.startState = startState;
        this.endState = endState;
    }

    public S getStartState()
    {
        return this.startState;
    }

    public S getEndState()
    {
        return this.endState;
    }
}
