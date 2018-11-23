/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.regularlanguage;

import larp.assertion.Assertion;

public class ExpressionIntermediateNestingLevelValidAssertion implements Assertion
{
    private int nestingLevel;

    public ExpressionIntermediateNestingLevelValidAssertion(int nestingLevel)
    {
        this.nestingLevel = nestingLevel;
    }

    public void validate() throws IncorrectExpressionNestingException
    {
        if (this.nestingLevel < 0)
        {
            throw new IncorrectExpressionNestingException();
        }
    }
}
