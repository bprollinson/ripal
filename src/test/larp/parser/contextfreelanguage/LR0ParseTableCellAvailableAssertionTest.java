/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;

public class LR0ParseTableCellAvailableAssertionTest
{
    @Test(expected = LR0ShiftReduceConflictException.class)
    public void testValidateThrowsExceptionForShiftActionWhenCellContainsReduceAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarParseTreeNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("a"), new LR0ShiftAction(state));
        assertion.validate();
    }

    @Test(expected = LR0ShiftReduceConflictException.class)
    public void testValidateThrowsExceptionForReduceActionWhenCellContainsShiftAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarParseTreeNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("a"), new LR0ReduceAction(0));
        assertion.validate();
    }

    @Test(expected = LR0ReduceReduceConflictException.class)
    public void testValidateThrowsExceptionForReduceActionWhenCellContainsReduceAction() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarParseTreeNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("a"), new LR0ReduceAction(0));
        assertion.validate();
    }

    @Test(expected = LR0OtherConflictException.class)
    public void testValidateThrowsExceptionForOtherTypeOfConflict() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarParseTreeNode>());

        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);
        parseTable.addCell(state, new NonTerminalNode("A"), new LR0GotoAction(state2));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new NonTerminalNode("A"), new LR0GotoAction(state2));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForCellThatDoesNotAlreadyExist() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarParseTreeNode>());
        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));
        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0ShiftAction(state));

        assertion.validate();
    }
}
