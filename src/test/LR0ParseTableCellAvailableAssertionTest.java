/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ParseTableCellAvailableAssertion;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;

public class LR0ParseTableCellAvailableAssertionTest
{
    @Test
    public void testValidateThrowsExceptionForShiftActionWhenCellContainsReduceAction()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateThrowsExceptionForReduceActionWhenCellContainsShiftAction()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateThrowsExceptionForReduceActionWhenCellContainsReduceAction()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateThrowsExceptionForOtherTypeOfConflict()
    {
        throw new RuntimeException();
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForCellThatAlreadyExists() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));
        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("a"), new LR0ShiftAction(state));

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForCellThatDoesNotAlreadyExist() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ParseTable parseTable = new LR0ParseTable(grammar, null);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(state));
        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0ShiftAction(state));

        assertion.validate();
    }
}
