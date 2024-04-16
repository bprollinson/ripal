/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class GrammarEscapeCharacterPositionCorrectAssertionTest
{
    @Test
    public void testValidateThrowsExceptionWhenNotInTerminal() throws IncorrectGrammarEscapeCharacterPositionException
    {
        GrammarEscapeCharacterPositionCorrectAssertion assertion = new GrammarEscapeCharacterPositionCorrectAssertion(false);

        assertThrows(
            IncorrectGrammarEscapeCharacterPositionException.class,
            () -> {
                assertion.validate();
            }
        );
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenInTerminal() throws IncorrectGrammarEscapeCharacterPositionException
    {
        GrammarEscapeCharacterPositionCorrectAssertion assertion = new GrammarEscapeCharacterPositionCorrectAssertion(true);

        assertion.validate();
    }
}
