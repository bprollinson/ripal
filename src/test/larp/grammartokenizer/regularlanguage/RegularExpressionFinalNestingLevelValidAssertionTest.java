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
    @Test(expected = IncorrectExpressionNestingException.class)
    public void testValidateThrowsExceptionForPositiveNestingLevel() throws IncorrectExpressionNestingException
    {
        RegularExpressionFinalNestingLevelValidAssertion assertion = new RegularExpressionFinalNestingLevelValidAssertion(1);

        assertion.validate();
    }

    @Test(expected = IncorrectExpressionNestingException.class)
    public void testValidateThrowsExceptionForNegativeNestingLevel() throws IncorrectExpressionNestingException
    {
        RegularExpressionFinalNestingLevelValidAssertion assertion = new RegularExpressionFinalNestingLevelValidAssertion(-1);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForZeroNestingLevel() throws IncorrectExpressionNestingException
    {
        RegularExpressionFinalNestingLevelValidAssertion assertion = new RegularExpressionFinalNestingLevelValidAssertion(0);

        assertion.validate();
    }
}
