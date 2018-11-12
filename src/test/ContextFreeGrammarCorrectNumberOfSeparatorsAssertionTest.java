/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import org.junit.Test;

import larp.grammartokenizer.contextfreelanguage.ContextFreeGrammarCorrectNumberOfSeparatorsAssertion;
import larp.grammartokenizer.contextfreelanguage.IncorrectContextFreeGrammarSeparatorException;

public class ContextFreeGrammarCorrectNumberOfSeparatorsAssertionTest
{
    @Test(expected = IncorrectContextFreeGrammarSeparatorException.class)
    public void testValidateThrowsExceptionForLessThanOneSeparatorToken() throws IncorrectContextFreeGrammarSeparatorException
    {
        ContextFreeGrammarCorrectNumberOfSeparatorsAssertion assertion = new ContextFreeGrammarCorrectNumberOfSeparatorsAssertion(0);

        assertion.validate();
    }

    @Test(expected = IncorrectContextFreeGrammarSeparatorException.class)
    public void testValidateThrowsExceptionForMoreThanOneSeparatorToken() throws IncorrectContextFreeGrammarSeparatorException
    {
        ContextFreeGrammarCorrectNumberOfSeparatorsAssertion assertion = new ContextFreeGrammarCorrectNumberOfSeparatorsAssertion(2);

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForOneSeparatorToken() throws IncorrectContextFreeGrammarSeparatorException
    {
        ContextFreeGrammarCorrectNumberOfSeparatorsAssertion assertion = new ContextFreeGrammarCorrectNumberOfSeparatorsAssertion(1);

        assertion.validate();
    }
}
