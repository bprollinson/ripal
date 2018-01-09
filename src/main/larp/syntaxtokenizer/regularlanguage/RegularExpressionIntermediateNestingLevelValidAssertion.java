package larp.syntaxtokenizer.regularlanguage;

import larp.assertion.Assertion;

public class RegularExpressionIntermediateNestingLevelValidAssertion implements Assertion
{
    private int nestingLevel;

    public RegularExpressionIntermediateNestingLevelValidAssertion(int nestingLevel)
    {
        this.nestingLevel = nestingLevel;
    }

    public void validate() throws IncorrectRegularExpressionNestingException
    {
        if (this.nestingLevel < 0)
        {
            throw new IncorrectRegularExpressionNestingException();
        }
    }
}
