/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import org.junit.Test;

import larp.grammar.contextfreelanguage.Grammar;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

public class LL1ParseTableCellAvailableAssertionTest
{
    @Test(expected = LL1ApplyApplyConflictException.class)
    public void testValidateThrowsExceptionForCellThatAlreadyExists() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1ParseTableCellAvailableAssertion assertion = new LL1ParseTableCellAvailableAssertion(parseTable, new NonTerminalNode("S"), new TerminalNode("a"));

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForCellThatDoesNotAlreadyExist() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1ParseTableCellAvailableAssertion assertion = new LL1ParseTableCellAvailableAssertion(parseTable, new NonTerminalNode("S"), new TerminalNode("b"));

        assertion.validate();
    }
}
