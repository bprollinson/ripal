/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import org.junit.Test;

import larp.grammartokenizer.contextfreelanguage.ContextFreeGrammarEscapeCharacterPositionCorrectAssertion;
import larp.grammartokenizer.contextfreelanguage.IncorrectContextFreeGrammarEscapeCharacterPositionException;

public class ContextFreeGrammarEscapeCharacterPositionCorrectAssertionTest
{
    @Test(expected = IncorrectContextFreeGrammarEscapeCharacterPositionException.class)
    public void testValidateThrowsExceptionWhenNotInTerminal() throws IncorrectContextFreeGrammarEscapeCharacterPositionException
    {
        ContextFreeGrammarEscapeCharacterPositionCorrectAssertion assertion = new ContextFreeGrammarEscapeCharacterPositionCorrectAssertion(false);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenInTerminal() throws IncorrectContextFreeGrammarEscapeCharacterPositionException
    {
        ContextFreeGrammarEscapeCharacterPositionCorrectAssertion assertion = new ContextFreeGrammarEscapeCharacterPositionCorrectAssertion(true);

        assertion.validate();
    }
}
