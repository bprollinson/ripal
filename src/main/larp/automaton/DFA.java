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

    public boolean structureEquals(Object other)
    {
        if (!(other instanceof FiniteAutomata))
        {
            return false;
        }

        return this.startState.structureEquals(((FiniteAutomata)other).getStartState());
    }
}
