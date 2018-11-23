/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.contextfreelanguage;

import org.junit.Test;

public class ContextFreeGrammarCorrectNumberOfSeparatorsAssertionTest
{
    @Test(expected = IncorrectGrammarSeparatorException.class)
    public void testValidateThrowsExceptionForLessThanOneSeparatorToken() throws IncorrectGrammarSeparatorException
    {
        ContextFreeGrammarCorrectNumberOfSeparatorsAssertion assertion = new ContextFreeGrammarCorrectNumberOfSeparatorsAssertion(0);

        assertion.validate();
    }

    @Test(expected = IncorrectGrammarSeparatorException.class)
    public void testValidateThrowsExceptionForMoreThanOneSeparatorToken() throws IncorrectGrammarSeparatorException
    {
        ContextFreeGrammarCorrectNumberOfSeparatorsAssertion assertion = new ContextFreeGrammarCorrectNumberOfSeparatorsAssertion(2);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForOneSeparatorToken() throws IncorrectGrammarSeparatorException
    {
        ContextFreeGrammarCorrectNumberOfSeparatorsAssertion assertion = new ContextFreeGrammarCorrectNumberOfSeparatorsAssertion(1);

        assertion.validate();
    }
}
