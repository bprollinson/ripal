/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.regularlanguage;

import larp.assertion.Assertion;

public class ExpressionFinalEscapingStatusValidAssertion implements Assertion
{
    private boolean escaping;

    public ExpressionFinalEscapingStatusValidAssertion(boolean escaping)
    {
        this.escaping = escaping;
    }

    public void validate() throws DanglingExpressionEscapeCharacterException
    {
        if (this.escaping)
        {
            throw new DanglingExpressionEscapeCharacterException();
        }
    }
}
