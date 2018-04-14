package larp.parser.regularlanguage;

public class StateTransition<I>
{
    private I input;
    private State nextState;

    public StateTransition(I input, State nextState)
    {
        this.input = input;
        this.nextState = nextState;
    }

    public I getInput()
    {
        return this.input;
    }

    public boolean inputEquals(Object otherInput)
    {
        if (this.input == null)
        {
            return otherInput == null;
        }

        return this.input.equals(otherInput);
    }

    public boolean inputEqualsOtherTransition(StateTransition otherTransition)
    {
        return otherTransition.inputEquals(this.input);
    }

    public State getNextState()
    {
        return this.nextState;
    }
}
