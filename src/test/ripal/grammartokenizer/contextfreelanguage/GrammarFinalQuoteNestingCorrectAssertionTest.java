/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.contextfreelanguage;

import org.junit.Test;

public class GrammarFinalQuoteNestingCorrectAssertionTest
{
    @Test(expected = IncorrectGrammarQuoteNestingException.class)
    public void testValidateThrowsExceptionWhenInTerminal() throws IncorrectGrammarQuoteNestingException
    {
        GrammarFinalQuoteNestingCorrectAssertion assertion = new GrammarFinalQuoteNestingCorrectAssertion(true);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenNotInTerminal() throws IncorrectGrammarQuoteNestingException
    {
        GrammarFinalQuoteNestingCorrectAssertion assertion = new GrammarFinalQuoteNestingCorrectAssertion(false);

        assertion.validate();
    }
}
