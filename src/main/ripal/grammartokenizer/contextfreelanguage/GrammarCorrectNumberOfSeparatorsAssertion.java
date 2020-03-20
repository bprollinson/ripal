/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.contextfreelanguage;

import ripal.assertion.Assertion;

public class GrammarCorrectNumberOfSeparatorsAssertion implements Assertion
{
    private int numSeparators;

    public GrammarCorrectNumberOfSeparatorsAssertion(int numSeparators)
    {
        this.numSeparators = numSeparators;
    }

    public void validate() throws IncorrectGrammarSeparatorException
    {
        if (this.numSeparators != 1)
        {
            throw new IncorrectGrammarSeparatorException();
        }
    }
}
