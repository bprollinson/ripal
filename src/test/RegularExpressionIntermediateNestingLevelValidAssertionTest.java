/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import org.junit.Test;

import larp.grammartokenizer.regularlanguage.IncorrectRegularExpressionNestingException;
import larp.grammartokenizer.regularlanguage.RegularExpressionIntermediateNestingLevelValidAssertion;

public class RegularExpressionIntermediateNestingLevelValidAssertionTest
{
    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testValidateThrowsExceptionForNegativeNestingLevel() throws IncorrectRegularExpressionNestingException
    {
        RegularExpressionIntermediateNestingLevelValidAssertion assertion = new RegularExpressionIntermediateNestingLevelValidAssertion(-1);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForZeroNestingLevel() throws IncorrectRegularExpressionNestingException
    {
        RegularExpressionIntermediateNestingLevelValidAssertion assertion = new RegularExpressionIntermediateNestingLevelValidAssertion(0);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForPositiveNestingLevel() throws IncorrectRegularExpressionNestingException
    {
        RegularExpressionIntermediateNestingLevelValidAssertion assertion = new RegularExpressionIntermediateNestingLevelValidAssertion(1);

        assertion.validate();
    }
}
