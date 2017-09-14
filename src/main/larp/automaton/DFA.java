package larp.automaton;

public class DFA
{
    private State startState;

    public DFA(State startState)
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
