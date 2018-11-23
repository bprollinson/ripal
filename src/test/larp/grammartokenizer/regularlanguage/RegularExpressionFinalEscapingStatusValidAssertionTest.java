/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.regularlanguage;

import org.junit.Test;

public class RegularExpressionFinalEscapingStatusValidAssertionTest
{
    @Test(expected = DanglingExpressionEscapeCharacterException.class)
    public void testValidateThrowsExceptionWhenEscaping() throws DanglingExpressionEscapeCharacterException
    {
        RegularExpressionFinalEscapingStatusValidAssertion assertion = new RegularExpressionFinalEscapingStatusValidAssertion(true);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenNotEscaping() throws DanglingExpressionEscapeCharacterException
    {
        RegularExpressionFinalEscapingStatusValidAssertion assertion = new RegularExpressionFinalEscapingStatusValidAssertion(false);

        assertion.validate();
    }
}
