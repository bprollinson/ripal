/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.Grammar;
import larp.parser.contextfreelanguage.LR0ParseTable;

public class LR1ParserCompilerTest
{
    @Test
    public void testCompileReturnsEmptyParseTableForEmptyGrammar()
    {
        LR1ParserCompiler compiler = new LR1ParserCompiler();

        Grammar grammar = new Grammar();
        LR0ParseTable expectedTable = new LR0ParseTable(grammar, null);

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }
}
