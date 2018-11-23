/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.contextfreelanguage;

import org.junit.Test;

public class GrammarEscapeCharacterPositionCorrectAssertionTest
{
    @Test(expected = IncorrectGrammarEscapeCharacterPositionException.class)
    public void testValidateThrowsExceptionWhenNotInTerminal() throws IncorrectGrammarEscapeCharacterPositionException
    {
        GrammarEscapeCharacterPositionCorrectAssertion assertion = new GrammarEscapeCharacterPositionCorrectAssertion(false);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenInTerminal() throws IncorrectGrammarEscapeCharacterPositionException
    {
        GrammarEscapeCharacterPositionCorrectAssertion assertion = new GrammarEscapeCharacterPositionCorrectAssertion(true);

        assertion.validate();
    }
}
