package larp.syntaxtokenizer.regularlanguage;

import larp.assertion.Assertion;

public class RegularExpressionNestingLevelValidAssertion implements Assertion
{
    private int nestingLevel;

    public RegularExpressionNestingLevelValidAssertion(int nestingLevel)
    {
        this.nestingLevel = nestingLevel;
    }

    public void validate() throws IncorrectRegularExpressionNestingException
    {
        if (this.nestingLevel != 0)
        {
            throw new IncorrectRegularExpressionNestingException();
        }
    }
}
