/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class GrammarFinalEscapingStatusValidAssertionTest
{
    @Test
    public void testValidateThrowsExceptionWhenEscaping() throws DanglingGrammarEscapeCharacterException
    {
        GrammarFinalEscapingStatusValidAssertion assertion = new GrammarFinalEscapingStatusValidAssertion(true);

        assertThrows(
            DanglingGrammarEscapeCharacterException.class,
            () -> {
                assertion.validate();
            }
        );
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenNotEscaping() throws DanglingGrammarEscapeCharacterException
    {
        GrammarFinalEscapingStatusValidAssertion assertion = new GrammarFinalEscapingStatusValidAssertion(false);

        assertion.validate();
    }
}
