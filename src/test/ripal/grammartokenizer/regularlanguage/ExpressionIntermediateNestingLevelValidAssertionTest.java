/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.regularlanguage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ExpressionIntermediateNestingLevelValidAssertionTest
{
    @Test
    public void testValidateThrowsExceptionForNegativeNestingLevel() throws IncorrectExpressionNestingException
    {
        ExpressionIntermediateNestingLevelValidAssertion assertion = new ExpressionIntermediateNestingLevelValidAssertion(-1);

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
        ExpressionIntermediateNestingLevelValidAssertion assertion = new ExpressionIntermediateNestingLevelValidAssertion(0);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForPositiveNestingLevel() throws IncorrectExpressionNestingException
    {
        ExpressionIntermediateNestingLevelValidAssertion assertion = new ExpressionIntermediateNestingLevelValidAssertion(1);

        assertion.validate();
    }
}
