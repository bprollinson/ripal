/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.contextfreelanguage;

import org.junit.Test;

public class GrammarCorrectNumberOfSeparatorsAssertionTest
{
    @Test(expected = IncorrectGrammarSeparatorException.class)
    public void testValidateThrowsExceptionForLessThanOneSeparatorToken() throws IncorrectGrammarSeparatorException
    {
        GrammarCorrectNumberOfSeparatorsAssertion assertion = new GrammarCorrectNumberOfSeparatorsAssertion(0);

        assertion.validate();
    }

    @Test(expected = IncorrectGrammarSeparatorException.class)
    public void testValidateThrowsExceptionForMoreThanOneSeparatorToken() throws IncorrectGrammarSeparatorException
    {
        GrammarCorrectNumberOfSeparatorsAssertion assertion = new GrammarCorrectNumberOfSeparatorsAssertion(2);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForOneSeparatorToken() throws IncorrectGrammarSeparatorException
    {
        GrammarCorrectNumberOfSeparatorsAssertion assertion = new GrammarCorrectNumberOfSeparatorsAssertion(1);

        assertion.validate();
    }
}
