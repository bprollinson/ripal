package larp.syntaxtokenizer.contextfreelanguage;

import larp.assertion.Assertion;

public class ContextFreeGrammarEscapeCharacterPositionCorrectException implements Assertion
{
    private boolean inTerminal;

    public ContextFreeGrammarEscapeCharacterPositionCorrectException(boolean inTerminal)
    {
        this.inTerminal = inTerminal;
    }

    public void validate() throws IncorrectContextFreeGrammarEscapeCharacterPositionException
    {
        if (!this.inTerminal)
        {
            throw new IncorrectContextFreeGrammarEscapeCharacterPositionException();
        }
    }
}
