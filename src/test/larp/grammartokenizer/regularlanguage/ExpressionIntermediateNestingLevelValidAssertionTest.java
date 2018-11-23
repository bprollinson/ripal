/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.regularlanguage;

import org.junit.Test;

public class ExpressionIntermediateNestingLevelValidAssertionTest
{
    @Test(expected = IncorrectExpressionNestingException.class)
    public void testValidateThrowsExceptionForNegativeNestingLevel() throws IncorrectExpressionNestingException
    {
        ExpressionIntermediateNestingLevelValidAssertion assertion = new ExpressionIntermediateNestingLevelValidAssertion(-1);

        assertion.validate();
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
