/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.contextfreelanguage;

import org.junit.Test;

public class GrammarFinalEscapingStatusValidAssertionTest
{
    @Test(expected = DanglingGrammarEscapeCharacterException.class)
    public void testValidateThrowsExceptionWhenEscaping() throws DanglingGrammarEscapeCharacterException
    {
        GrammarFinalEscapingStatusValidAssertion assertion = new GrammarFinalEscapingStatusValidAssertion(true);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenNotEscaping() throws DanglingGrammarEscapeCharacterException
    {
        GrammarFinalEscapingStatusValidAssertion assertion = new GrammarFinalEscapingStatusValidAssertion(false);

        assertion.validate();
    }
}
