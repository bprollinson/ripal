package larp.parser.regularlanguage;

public class DFA extends FiniteAutomata
{
    public DFA(State startState)
    {
        super(startState);
    }

    public boolean accepts(String inputString)
    {
        State currentState = this.startState;
        if (!(currentState instanceof DFAState))
        {
            return false;
        }

        int characterPosition = 0;

        while (currentState != null && characterPosition < inputString.length())
        {
            Character inputCharacter = inputString.charAt(characterPosition);
            currentState = ((DFAState)currentState).getNextState(inputCharacter);

            if (!(currentState instanceof DFAState))
            {
                return false;
            }

            characterPosition++;
        }

        return currentState != null && currentState.isAccepting();
    }
}
