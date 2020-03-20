/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.contextfreelanguage;

import ripal.assertion.Assertion;

public class GrammarFinalQuoteNestingCorrectAssertion implements Assertion
{
    private boolean inTerminal;

    public GrammarFinalQuoteNestingCorrectAssertion(boolean inTerminal)
    {
        this.inTerminal = inTerminal;
    }

    public void validate() throws IncorrectGrammarQuoteNestingException
    {
        if (this.inTerminal)
        {
            throw new IncorrectGrammarQuoteNestingException();
        }
    }
}
