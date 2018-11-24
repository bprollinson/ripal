/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.contextfreelanguage;

import org.junit.Test;

public class GrammarFinalEscapingStatusValidAssertionTest
{
    @Test
    public void testValidateThrowsExceptionWhenEscaping()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateDoesNotThrowExceptionWhenNotEscaping()
    {
        throw new RuntimeException();
    }
}
