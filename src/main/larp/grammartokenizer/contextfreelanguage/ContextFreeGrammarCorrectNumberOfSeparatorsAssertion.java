/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.contextfreelanguage;

import larp.assertion.Assertion;

public class ContextFreeGrammarCorrectNumberOfSeparatorsAssertion implements Assertion
{
    private int numSeparators;

    public ContextFreeGrammarCorrectNumberOfSeparatorsAssertion(int numSeparators)
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
