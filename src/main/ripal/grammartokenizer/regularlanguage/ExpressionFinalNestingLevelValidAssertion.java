/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.regularlanguage;

import ripal.assertion.Assertion;

public class ExpressionFinalNestingLevelValidAssertion implements Assertion
{
    private int nestingLevel;

    public ExpressionFinalNestingLevelValidAssertion(int nestingLevel)
    {
        this.nestingLevel = nestingLevel;
    }

    public void validate() throws IncorrectExpressionNestingException
    {
        if (this.nestingLevel != 0)
        {
            throw new IncorrectExpressionNestingException();
        }
    }
}
