/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.contextfreelanguage;

import larp.assertion.Assertion;

public class GrammarFinalEscapingStatusValidAssertion implements Assertion
{
    private boolean escaping;

    public GrammarFinalEscapingStatusValidAssertion(boolean escaping)
    {
        this.escaping = escaping;
    }

    public void validate() throws DanglingGrammarEscapeCharacterException
    {
        if (this.escaping)
        {
            throw new DanglingGrammarEscapeCharacterException();
        }
    }
}
