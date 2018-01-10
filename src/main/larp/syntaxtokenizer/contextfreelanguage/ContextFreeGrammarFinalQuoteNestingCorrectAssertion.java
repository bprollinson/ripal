package larp.syntaxtokenizer.contextfreelanguage;

import larp.assertion.Assertion;

public class ContextFreeGrammarFinalQuoteNestingCorrectAssertion implements Assertion
{
    private boolean inTerminal;

    public ContextFreeGrammarFinalQuoteNestingCorrectAssertion(boolean inTerminal)
    {
        this.inTerminal = inTerminal;
    }

    public void validate() throws IncorrectContextFreeGrammarQuoteNestingException
    {
        if (this.inTerminal)
        {
            throw new IncorrectContextFreeGrammarQuoteNestingException();
        }
    }
}
