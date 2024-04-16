/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.regularlanguage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ExpressionFinalNestingLevelValidAssertionTest
{
    @Test
    public void testValidateThrowsExceptionForPositiveNestingLevel() throws IncorrectExpressionNestingException
    {
        ExpressionFinalNestingLevelValidAssertion assertion = new ExpressionFinalNestingLevelValidAssertion(1);

        assertThrows(
            IncorrectExpressionNestingException.class,
            () -> {
                assertion.validate();
            }
        );
    }

    @Test
    public void testValidateThrowsExceptionForNegativeNestingLevel() throws IncorrectExpressionNestingException
    {
        ExpressionFinalNestingLevelValidAssertion assertion = new ExpressionFinalNestingLevelValidAssertion(-1);

        assertThrows(
            IncorrectExpressionNestingException.class,
            () -> {
                assertion.validate();
            }
        );
    }

    @Test
    public void testValidateDoesNotThrowExceptionForZeroNestingLevel() throws IncorrectExpressionNestingException
    {
        ExpressionFinalNestingLevelValidAssertion assertion = new ExpressionFinalNestingLevelValidAssertion(0);

        assertion.validate();
    }
}
