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
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0ClosureRuleSetDFAState;
import larp.parser.contextfreelanguage.LR0GotoAction;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

public class LR1ParserCompilerTest
{
    @Test
    public void testCompileAppliesReduceActionOnlyToEndOfStringNodeInLookaheadSet() throws AmbiguousLR0ParseTableException
    {
        LR1ParserCompiler compiler = new LR1ParserCompiler();

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ParseTable expectedTable = new LR0ParseTable(augmentedGrammar, state1);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state3));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        assertTrue(expectedTable.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileAppliesReduceActionOnlyToSingleTerminalNodeInLookaheadSet()
    {
        assertTrue(false);
    }

    @Test
    public void testCompileAppliesReduceActionOnlyToMultipleTerminalNodesInLookaheadSet()
    {
        assertTrue(false);
    }
}
