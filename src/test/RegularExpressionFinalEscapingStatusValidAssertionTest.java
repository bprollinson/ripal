/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import org.junit.Test;

import larp.grammartokenizer.regularlanguage.DanglingRegularExpressionEscapeCharacterException;
import larp.grammartokenizer.regularlanguage.RegularExpressionFinalEscapingStatusValidAssertion;

public class RegularExpressionFinalEscapingStatusValidAssertionTest
{
    @Test(expected = DanglingRegularExpressionEscapeCharacterException.class)
    public void testValidateThrowsExceptionWhenEscaping() throws DanglingRegularExpressionEscapeCharacterException
    {
        RegularExpressionFinalEscapingStatusValidAssertion assertion = new RegularExpressionFinalEscapingStatusValidAssertion(true);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenNotEscaping() throws DanglingRegularExpressionEscapeCharacterException
    {
        RegularExpressionFinalEscapingStatusValidAssertion assertion = new RegularExpressionFinalEscapingStatusValidAssertion(false);

        assertion.validate();
    }
}
