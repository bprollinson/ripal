/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parserfactory.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.Grammar;
import larp.grammartokenizer.contextfreelanguage.IncorrectGrammarStatementPrefixException;
import larp.grammartokenizer.contextfreelanguage.TokenizerException;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.AmbiguousParseTableException;
import larp.parser.contextfreelanguage.LL1Parser;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0GotoAction;
import larp.parser.contextfreelanguage.LR0Parser;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ReduceReduceConflictException;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ParserFactoryTest
{
    @Test
    public void testFactoryCreatesLL1ParserForLL1AndLR0ContextFreeGrammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: \"a\"");

        Grammar expectedGrammar = new Grammar();
        expectedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        LL1ParseTable expectedTable = new LL1ParseTable(expectedGrammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser expectedParser = new LL1Parser(expectedTable);

        assertEquals(expectedParser, factory.factory(input));
    }

    @Test
    public void testFactoryCreatesLL1ParserForLL1AndNotLR0ContextFreeGrammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: \"a\"");
        input.add("S: \"\"");

        Grammar expectedGrammar = new Grammar();
        expectedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        expectedGrammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());
        LL1ParseTable expectedTable = new LL1ParseTable(expectedGrammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("S"), new EndOfStringNode(), 1);
        LL1Parser expectedParser = new LL1Parser(expectedTable);

        assertEquals(expectedParser, factory.factory(input));
    }

    @Test
    public void testFactoryCreatesLR0ParserForLR0AndNotLL1ContextFreeGrammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: \"a\"\"a\"");
        input.add("S: \"a\"\"b\"");

        Grammar expectedGrammar = new Grammar();
        expectedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        expectedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("a"));
        expectedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<Node>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<Node>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<Node>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<Node>());
        LR0ProductionSetDFAState state5 = new LR0ProductionSetDFAState("", false, new HashSet<Node>());

        LR0ParseTable expectedTable = new LR0ParseTable(expectedGrammar, state1);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state5));
        expectedTable.addCell(state2, new TerminalNode("a"), new LR0ShiftAction(state3));
        expectedTable.addCell(state2, new TerminalNode("b"), new LR0ShiftAction(state4));
        expectedTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new TerminalNode("b"), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state4, new TerminalNode("a"), new LR0ReduceAction(2));
        expectedTable.addCell(state4, new TerminalNode("b"), new LR0ReduceAction(2));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state5, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser expectedParser = new LR0Parser(expectedTable);

        assertTrue(expectedParser.structureEquals(factory.factory(input)));
    }

    @Test
    public void testFactoryCreatesLL1ParserForEmptyContextFreeGrammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();

        Grammar expectedGrammar = new Grammar();
        LL1ParseTable expectedTable = new LL1ParseTable(expectedGrammar);
        LL1Parser expectedParser = new LL1Parser(expectedTable);

        assertEquals(expectedParser, factory.factory(input));
    }

    @Test(expected = LR0ReduceReduceConflictException.class)
    public void testFactoryThrowsAmbiguousLR0ParseTableExceptionForNonLL1NonLR0ContextFreeGrammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: \"s\"");
        input.add("S: \"s\"");

        factory.factory(input);
    }

    @Test(expected = IncorrectGrammarStatementPrefixException.class)
    public void testFactoryThrowsSyntaxTokenizerExceptionForIncorrectGrammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("");

        factory.factory(input);
    }
}
