/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.contextfreelanguage;

import org.junit.Test;

public class ContextFreeGrammarEscapeCharacterPositionCorrectAssertionTest
{
    @Test(expected = IncorrectGrammarEscapeCharacterPositionException.class)
    public void testValidateThrowsExceptionWhenNotInTerminal() throws IncorrectGrammarEscapeCharacterPositionException
    {
        ContextFreeGrammarEscapeCharacterPositionCorrectAssertion assertion = new ContextFreeGrammarEscapeCharacterPositionCorrectAssertion(false);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenInTerminal() throws IncorrectGrammarEscapeCharacterPositionException
    {
        ContextFreeGrammarEscapeCharacterPositionCorrectAssertion assertion = new ContextFreeGrammarEscapeCharacterPositionCorrectAssertion(true);

        assertion.validate();
    }
}
