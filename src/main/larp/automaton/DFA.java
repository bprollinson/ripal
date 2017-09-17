package larp.automaton;

public class DFA
{
    private DFAState startState;

    public DFA(DFAState startState)
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
            currentState = ((DFAState)currentState).getNextState(inputCharacter);

            characterPosition++;
        }

        return currentState != null && currentState.isAccepting();
    }
}
