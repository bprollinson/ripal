/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.contextfreelanguage;

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
