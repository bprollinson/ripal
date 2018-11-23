/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.contextfreelanguage;

import larp.assertion.Assertion;

public class GrammarEscapeCharacterPositionCorrectAssertion implements Assertion
{
    private boolean inTerminal;

    public GrammarEscapeCharacterPositionCorrectAssertion(boolean inTerminal)
    {
        this.inTerminal = inTerminal;
    }

    public void validate() throws IncorrectGrammarEscapeCharacterPositionException
    {
        if (!this.inTerminal)
        {
            throw new IncorrectGrammarEscapeCharacterPositionException();
        }
    }
}
