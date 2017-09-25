package larp.automaton;

public class DFA extends FiniteAutomata
{
    public DFA(State startState)
    {
        super(startState);
    }

    public boolean accepts(String inputString)
    {
        State currentState = this.startState;
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
