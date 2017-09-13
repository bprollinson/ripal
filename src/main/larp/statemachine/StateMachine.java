package larp.statemachine;

public class StateMachine
{
    private State startState;

    public StateMachine(State startState)
    {
        this.startState = startState;
    }

    public boolean accepts(String inputString)
    {
        State currentState = startState;
        int characterPosition = 0;

        while (currentState != null && characterPosition < inputString.length())
        {
            Character inputCharacter = inputString.charAt(characterPosition);
            currentState = currentState.getNextState(inputCharacter);

            characterPosition++;
        }

        return currentState != null && currentState.isAccepting();
    }
}
