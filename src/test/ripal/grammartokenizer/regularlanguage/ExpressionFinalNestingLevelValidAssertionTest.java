/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.regularlanguage;

import org.junit.Test;

public class ExpressionFinalNestingLevelValidAssertionTest
{
    @Test(expected = IncorrectExpressionNestingException.class)
    public void testValidateThrowsExceptionForPositiveNestingLevel() throws IncorrectExpressionNestingException
    {
        ExpressionFinalNestingLevelValidAssertion assertion = new ExpressionFinalNestingLevelValidAssertion(1);

        assertion.validate();
    }

    @Test(expected = IncorrectExpressionNestingException.class)
    public void testValidateThrowsExceptionForNegativeNestingLevel() throws IncorrectExpressionNestingException
    {
        ExpressionFinalNestingLevelValidAssertion assertion = new ExpressionFinalNestingLevelValidAssertion(-1);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForZeroNestingLevel() throws IncorrectExpressionNestingException
    {
        ExpressionFinalNestingLevelValidAssertion assertion = new ExpressionFinalNestingLevelValidAssertion(0);

        assertion.validate();
    }
}
