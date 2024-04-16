/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class GrammarFinalQuoteNestingCorrectAssertionTest
{
    @Test
    public void testValidateThrowsExceptionWhenInTerminal() throws IncorrectGrammarQuoteNestingException
    {
        GrammarFinalQuoteNestingCorrectAssertion assertion = new GrammarFinalQuoteNestingCorrectAssertion(true);

        assertThrows(
            IncorrectGrammarQuoteNestingException.class,
            () -> {
                assertion.validate();
            }
        );
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenNotInTerminal() throws IncorrectGrammarQuoteNestingException
    {
        GrammarFinalQuoteNestingCorrectAssertion assertion = new GrammarFinalQuoteNestingCorrectAssertion(false);

        assertion.validate();
    }
}
