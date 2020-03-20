/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.regularlanguage;

import org.junit.Test;

public class ExpressionFinalEscapingStatusValidAssertionTest
{
    @Test(expected = DanglingExpressionEscapeCharacterException.class)
    public void testValidateThrowsExceptionWhenEscaping() throws DanglingExpressionEscapeCharacterException
    {
        ExpressionFinalEscapingStatusValidAssertion assertion = new ExpressionFinalEscapingStatusValidAssertion(true);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenNotEscaping() throws DanglingExpressionEscapeCharacterException
    {
        ExpressionFinalEscapingStatusValidAssertion assertion = new ExpressionFinalEscapingStatusValidAssertion(false);

        assertion.validate();
    }
}
