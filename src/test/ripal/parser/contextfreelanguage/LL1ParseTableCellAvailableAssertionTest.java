/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parser.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;

public class LL1ParseTableCellAvailableAssertionTest
{
    @Test
    public void testValidateThrowsExceptionForCellThatAlreadyExists() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1ParseTableCellAvailableAssertion assertion = new LL1ParseTableCellAvailableAssertion(parseTable, new NonTerminalNode("S"), new TerminalNode("a"));

        assertThrows(
            LL1ApplyApplyConflictException.class,
            () -> {
                assertion.validate();
            }
        );
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
