package larp.parser.regularlanguage;

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

    public boolean inputEquals(Character otherInput)
    {
        if (this.input == null)
        {
            return otherInput == null;
        }

        return this.input.equals(otherInput);
    }

    public State getNextState()
    {
        return this.nextState;
    }
}
