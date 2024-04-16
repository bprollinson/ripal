/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parserfactory.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.grammartokenizer.contextfreelanguage.IncorrectGrammarStatementPrefixException;
import ripal.grammartokenizer.contextfreelanguage.TokenizerException;
import ripal.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import ripal.parser.contextfreelanguage.AmbiguousParseTableException;
import ripal.parser.contextfreelanguage.LL1Parser;
import ripal.parser.contextfreelanguage.LL1ParseTable;
import ripal.parser.contextfreelanguage.LR0AcceptAction;
import ripal.parser.contextfreelanguage.LR0ClosureRuleSetDFAState;
import ripal.parser.contextfreelanguage.LR0GotoAction;
import ripal.parser.contextfreelanguage.LR0Parser;
import ripal.parser.contextfreelanguage.LR0ParseTable;
import ripal.parser.contextfreelanguage.LR0ReduceAction;
import ripal.parser.contextfreelanguage.LR0ReduceReduceConflictException;
import ripal.parser.contextfreelanguage.LR0ShiftAction;
import ripal.parser.contextfreelanguage.LR0ShiftReduceConflictException;
import ripal.parser.contextfreelanguage.LR1Parser;
import ripal.parser.contextfreelanguage.Parser;
import ripal.parser.contextfreelanguage.SLR1Parser;
import ripal.parsetree.contextfreelanguage.EndOfStringNode;
import ripal.parsetree.contextfreelanguage.EpsilonNode;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class ParserFactoryTest
{
    @Test
    public void testFactoryCreatesLL1ParserForLR0AndSLR1Grammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: \"a\"");

        Grammar expectedGrammar = new Grammar();
        expectedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        LL1ParseTable expectedTable = new LL1ParseTable(expectedGrammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser expectedParser = new LL1Parser(expectedTable);

        Parser parser = factory.factory(input);
        assertEquals(expectedParser, parser);
        assertEquals(LL1Parser.class, parser.getClass());
    }

    @Test
    public void testFactoryCreatesLL1ParserForLR0AndNotSLR1Grammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: A\"a\"A\"b\"");
        input.add("S: B\"b\"B\"a\"");
        input.add("A: \"\"");
        input.add("B: \"\"");

        Grammar expectedGrammar = new Grammar();
        expectedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("a"), new NonTerminalNode("A"), new TerminalNode("b"));
        expectedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("B"), new TerminalNode("b"), new NonTerminalNode("B"), new TerminalNode("a"));
        expectedGrammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        expectedGrammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());
        LL1ParseTable expectedTable = new LL1ParseTable(expectedGrammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 1);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 2);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("b"), 2);
        expectedTable.addCell(new NonTerminalNode("B"), new TerminalNode("a"), 3);
        expectedTable.addCell(new NonTerminalNode("B"), new TerminalNode("b"), 3);
        LL1Parser expectedParser = new LL1Parser(expectedTable);

        Parser parser = factory.factory(input);
        assertEquals(expectedParser, parser);
        assertEquals(LL1Parser.class, parser.getClass());
    }

    @Test
    public void testFactoryCreatesLR0ParserForLR0AndNotLL1Grammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: \"a\"\"a\"");
        input.add("S: \"a\"\"b\"");

        Grammar expectedGrammar = new Grammar();
        expectedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        expectedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("a"));
        expectedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state5 = new LR0ClosureRuleSetDFAState("", false);

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

        Parser parser = factory.factory(input);
        assertTrue(expectedParser.structureEquals(parser));
        assertEquals(LR0Parser.class, parser.getClass());
    }

    @Test
    public void testFactoryCreatesSLR1ParserForSLR1AndNotLL1Grammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: \"a\"\"a\"");
        input.add("S: \"a\"\"a\"B");
        input.add("B: \"b\"");

        Grammar expectedGrammar = new Grammar();
        expectedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        expectedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("a"));
        expectedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("a"), new NonTerminalNode("B"));
        expectedGrammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state5 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state6 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable expectedTable = new LR0ParseTable(expectedGrammar, state1);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state6));
        expectedTable.addCell(state2, new TerminalNode("a"), new LR0ShiftAction(state3));
        expectedTable.addCell(state3, new TerminalNode("b"), new LR0ShiftAction(state5));
        expectedTable.addCell(state3, new NonTerminalNode("B"), new LR0GotoAction(state4));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state5, new EndOfStringNode(), new LR0ReduceAction(3));
        expectedTable.addCell(state6, new EndOfStringNode(), new LR0AcceptAction());
        SLR1Parser expectedParser = new SLR1Parser(expectedTable);

        Parser parser = factory.factory(input);
        assertTrue(expectedParser.structureEquals(parser));
        assertEquals(SLR1Parser.class, parser.getClass());
    }

    @Test
    public void testFactoryCreatesLR1ParserForLR1AndNotLL1Grammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: A");
        input.add("S: \"b\"A\"c\"");
        input.add("S: \"d\"\"c\"");
        input.add("A: \"d\"");

        Grammar expectedGrammar = new Grammar();
        expectedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        expectedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        expectedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"), new NonTerminalNode("A"), new TerminalNode("c"));
        expectedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("d"), new TerminalNode("c"));
        expectedGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("d"));

        LR0ClosureRuleSetDFAState state1 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state2 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state3 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state4 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state5 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state6 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state7 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state8 = new LR0ClosureRuleSetDFAState("", false);
        LR0ClosureRuleSetDFAState state9 = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseTable expectedTable = new LR0ParseTable(expectedGrammar, state1);
        expectedTable.addCell(state1, new TerminalNode("b"), new LR0ShiftAction(state3));
        expectedTable.addCell(state1, new TerminalNode("d"), new LR0ShiftAction(state7));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state9));
        expectedTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        expectedTable.addCell(state3, new TerminalNode("d"), new LR0ShiftAction(state6));
        expectedTable.addCell(state3, new NonTerminalNode("A"), new LR0GotoAction(state4));
        expectedTable.addCell(state4, new TerminalNode("c"), new LR0ShiftAction(state5));
        expectedTable.addCell(state5, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state6, new TerminalNode("c"), new LR0ReduceAction(4));
        expectedTable.addCell(state7, new TerminalNode("c"), new LR0ShiftAction(state8));
        expectedTable.addCell(state7, new EndOfStringNode(), new LR0ReduceAction(4));
        expectedTable.addCell(state8, new EndOfStringNode(), new LR0ReduceAction(3));
        expectedTable.addCell(state9, new EndOfStringNode(), new LR0AcceptAction());
        LR1Parser expectedParser = new LR1Parser(expectedTable);

        Parser parser = factory.factory(input);
        assertTrue(expectedParser.structureEquals(parser));
        assertEquals(LR1Parser.class, parser.getClass());
    }

    @Test
    public void testFactoryCreatesLL1ParserForEmptyGrammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();

        Grammar expectedGrammar = new Grammar();
        LL1ParseTable expectedTable = new LL1ParseTable(expectedGrammar);
        LL1Parser expectedParser = new LL1Parser(expectedTable);

        Parser parser = factory.factory(input);
        assertEquals(expectedParser, parser);
        assertEquals(LL1Parser.class, parser.getClass());
    }

    @Test
    public void testFactoryThrowsLR0ShiftReduceExceptionForNonLL1NonLR1Grammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: S\"b\"S");
        input.add("S: \"a\"");

        assertThrows(
            LR0ShiftReduceConflictException.class,
            () -> {
                factory.factory(input);
            }
        );
    }

    @Test
    public void testFactoryThrowsLR0ReduceReduceExceptionForNonLL1NonLR1Grammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("S: \"s\"");
        input.add("S: \"s\"");

        assertThrows(
            LR0ReduceReduceConflictException.class,
            () -> {
                factory.factory(input);
            }
        );
    }

    @Test
    public void testFactoryThrowsSyntaxTokenizerExceptionForIncorrectGrammar() throws TokenizerException, AmbiguousParseTableException
    {
        ParserFactory factory = new ParserFactory();
        List<String> input = new ArrayList<String>();
        input.add("");

        assertThrows(
            IncorrectGrammarStatementPrefixException.class,
            () -> {
                factory.factory(input);
            }
        );
    }
}
