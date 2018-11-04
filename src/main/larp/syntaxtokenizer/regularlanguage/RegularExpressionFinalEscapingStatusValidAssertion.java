/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.syntaxtokenizer.regularlanguage;

import larp.assertion.Assertion;

public class RegularExpressionFinalEscapingStatusValidAssertion implements Assertion
{
    private boolean escaping;

    public RegularExpressionFinalEscapingStatusValidAssertion(boolean escaping)
    {
        this.escaping = escaping;
    }

    public void validate() throws DanglingRegularExpressionEscapeCharacterException
    {
        if (this.escaping)
        {
            throw new DanglingRegularExpressionEscapeCharacterException();
        }
    }
}
