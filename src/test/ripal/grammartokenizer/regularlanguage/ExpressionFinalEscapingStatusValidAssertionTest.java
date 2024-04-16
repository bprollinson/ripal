/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.regularlanguage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ExpressionFinalEscapingStatusValidAssertionTest
{
    @Test
    public void testValidateThrowsExceptionWhenEscaping() throws DanglingExpressionEscapeCharacterException
    {
        ExpressionFinalEscapingStatusValidAssertion assertion = new ExpressionFinalEscapingStatusValidAssertion(true);

        assertThrows(
            DanglingExpressionEscapeCharacterException.class,
            () -> {
                assertion.validate();
            }
        );
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenNotEscaping() throws DanglingExpressionEscapeCharacterException
    {
        ExpressionFinalEscapingStatusValidAssertion assertion = new ExpressionFinalEscapingStatusValidAssertion(false);

        assertion.validate();
    }
}
