package larp.syntaxtokenizer.contextfreelanguage;

import larp.assertion.Assertion;

public class ContextFreeGrammarEscapeCharacterPositionCorrectAssertion implements Assertion
{
    private boolean inTerminal;

    public ContextFreeGrammarEscapeCharacterPositionCorrectAssertion(boolean inTerminal)
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
