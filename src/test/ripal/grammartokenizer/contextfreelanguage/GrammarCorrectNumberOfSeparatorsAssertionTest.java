/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class GrammarCorrectNumberOfSeparatorsAssertionTest
{
    @Test
    public void testValidateThrowsExceptionForLessThanOneSeparatorToken() throws IncorrectGrammarSeparatorException
    {
        GrammarCorrectNumberOfSeparatorsAssertion assertion = new GrammarCorrectNumberOfSeparatorsAssertion(0);

        assertThrows(
            IncorrectGrammarSeparatorException.class,
            () -> {
                assertion.validate();
            }
        );
    }

    @Test
    public void testValidateThrowsExceptionForMoreThanOneSeparatorToken() throws IncorrectGrammarSeparatorException
    {
        GrammarCorrectNumberOfSeparatorsAssertion assertion = new GrammarCorrectNumberOfSeparatorsAssertion(2);

        assertThrows(
            IncorrectGrammarSeparatorException.class,
            () -> {
                assertion.validate();
            }
        );
    }

    @Test
    public void testValidateDoesNotThrowExceptionForOneSeparatorToken() throws IncorrectGrammarSeparatorException
    {
        GrammarCorrectNumberOfSeparatorsAssertion assertion = new GrammarCorrectNumberOfSeparatorsAssertion(1);

        assertion.validate();
    }
}
