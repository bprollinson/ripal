/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.regularlanguage;

public class DFA extends FiniteAutomata<DFAState>
{
    public DFA(DFAState startState)
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
