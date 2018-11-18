/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.regularlanguage;

import org.junit.Test;

public class RegularExpressionFinalNestingLevelValidAssertionTest
{
    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testValidateThrowsExceptionForPositiveNestingLevel() throws IncorrectRegularExpressionNestingException
    {
        RegularExpressionFinalNestingLevelValidAssertion assertion = new RegularExpressionFinalNestingLevelValidAssertion(1);

        assertion.validate();
    }

    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testValidateThrowsExceptionForNegativeNestingLevel() throws IncorrectRegularExpressionNestingException
    {
        RegularExpressionFinalNestingLevelValidAssertion assertion = new RegularExpressionFinalNestingLevelValidAssertion(-1);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForZeroNestingLevel() throws IncorrectRegularExpressionNestingException
    {
        RegularExpressionFinalNestingLevelValidAssertion assertion = new RegularExpressionFinalNestingLevelValidAssertion(0);

        assertion.validate();
    }
}
