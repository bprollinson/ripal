/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SLR1ParserCompilerTest
{
    @Test
    public void testCompileReturnsParseTableWithSingleNonTerminal()
    {
        assertTrue(false);
    }

    @Test
    public void testCompileAppliesReduceActionOnlyToTerminalsInFollowSet()
    {
        assertTrue(false);
    }

    @Test
    public void testCompileThrowsExceptionForShiftReduceConflict()
    {
        assertTrue(false);
    }

    @Test
    public void testCompileThrowsExceptionForReduceReduceConflict()
    {
        assertTrue(false);
    }

    @Test
    public void testCompilerThrowsExceptionForShiftReduceConflictInvolvingEpsilon()
    {
        assertTrue(false);
    }

    @Test
    public void testCompilerThrowsExceptionForReduceReduceConflictInvolvingEpsilon()
    {
        assertTrue(false);
    }
}
