package larp.automaton;

public class StateTransition
{
    private Character input;
    private State nextState;

    public StateTransition(Character input, State nextState)
    {
        this.input = input;
        this.nextState = nextState;
    }

    public Character getInput()
    {
        return this.input;
    }

    public State getNextState()
    {
        return this.nextState;
    }
}