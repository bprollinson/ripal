/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parser.contextfreelanguage;

import org.junit.Test;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;

public class LR0ParseTableCellAvailableAssertionTest
{
    @Test(expected = LR0ShiftReduceConflictException.class)
    public void testValidateThrowsExceptionForShiftActionWhenCellContainsReduceAction() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("a"), new LR0ShiftAction(state));
        assertion.validate();
    }

    @Test(expected = LR0ShiftReduceConflictException.class)
    public void testValidateThrowsExceptionForReduceActionWhenCellContainsShiftAction() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("a"), new LR0ReduceAction(0));
        assertion.validate();
    }

    @Test(expected = LR0ReduceReduceConflictException.class)
    public void testValidateThrowsExceptionForReduceActionWhenCellContainsReduceAction() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("a"), new LR0ReduceAction(0));
        assertion.validate();
    }

    @Test(expected = LR0OtherConflictException.class)
    public void testValidateThrowsExceptionForOtherTypeOfConflict() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);
        parseTable.addCell(state, new NonTerminalNode("A"), new LR0GotoAction(state2));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new NonTerminalNode("A"), new LR0GotoAction(state2));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForCellThatDoesNotAlreadyExist() throws AmbiguousLR0ParseTableException
    {
        Grammar grammar = new Grammar();
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));
        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0ShiftAction(state));

        assertion.validate();
    }
}
