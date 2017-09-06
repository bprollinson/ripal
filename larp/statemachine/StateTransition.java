package larp.statemachine;

public class StateTransition
{
    private Character input;
    private State nextState;

    public StateTransition(Character input, State nextState)
    {
        this.input = input;
        this.nextState = nextState;
    }
}
