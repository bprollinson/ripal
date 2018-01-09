package larp.syntaxtokenizer.contextfreelanguage;

import larp.assertion.Assertion;

public class ContextFreeGrammarCorrectNumberOfSeparatorsAssertion implements Assertion
{
    private int numSeparators;

    public ContextFreeGrammarCorrectNumberOfSeparatorsAssertion(int numSeparators)
    {
        this.numSeparators = numSeparators;
    }

    public void validate() throws IncorrectContextFreeGrammarSeparatorException
    {
        if (this.numSeparators != 1)
        {
            throw new IncorrectContextFreeGrammarSeparatorException();
        }
    }
}
