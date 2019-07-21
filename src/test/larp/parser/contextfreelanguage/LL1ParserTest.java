/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.Grammar;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class LL1ParserTest
{
    @Test
    public void testAcceptsReturnsTrueForCorrectCharacterInSingleCharacterGrammar() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertTrue(parser.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsFalseForIncorrectCharacterInSingleCharacterGrammar() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts("b"));
    }

    @Test
    public void testAcceptsReturnsTrueForMultiCharacterGrammarUnsingMultiCharacterTerminalNode() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertTrue(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsTrueForMultiCharacterGrammarUsingMultipleTerminalNodes() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertTrue(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsFalseForCharacterMismatchInMultiCharacterGrammarUsingMultiCharacterTerminalNode() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts("ac"));
    }

    @Test
    public void testAcceptsReturnsFalseForUnmatchedInputCharacter() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsFalseForUnmatchedGrammarCharacter() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsTrueForTerminalAndNonterminalProductionChainDependingOnCollectionPrefixing() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("ab"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        parseTable.addCell(new NonTerminalNode("C"), new TerminalNode("c"), 2);
        LL1Parser parser = new LL1Parser(parseTable);

        assertTrue(parser.accepts("abc"));
    }


    @Test
    public void testAcceptsReturnsTrueWhenEndOfStringNodeMatchesNonTerminal() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);
        LL1Parser parser = new LL1Parser(parseTable);

        assertTrue(parser.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsFalseWhenEndOfStringNonTerminalExistsWithoutEndOfStringInInput() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsTrueForEmptyStringMatchingSequenceOfEndOfStringProductions() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new EndOfStringNode(), 0);
        parseTable.addCell(new NonTerminalNode("A"), new EndOfStringNode(), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);
        LL1Parser parser = new LL1Parser(parseTable);

        assertTrue(parser.accepts(""));
    }

    @Test
    public void testAcceptsReturnsFalseForNonEmptyStringWithOnlyEndOfStringProduction() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new EndOfStringNode(), 0);
        parseTable.addCell(new NonTerminalNode("A"), new EndOfStringNode(), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsFalseForEmptyStringWhenGrammarIsEmpty()
    {
        Grammar grammar = new Grammar();

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts(""));
    }

    @Test
    public void testAcceptsReturnsFalseForNonEmptyStringWhenGrammarIsEmpty()
    {
        Grammar grammar = new Grammar();

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts("a"));
    }

    @Test
    public void testGetAppliedRulesReturnsEmptyListBeforeParseIsRun() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        LL1Parser parser = new LL1Parser(parseTable);

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesOnSuccessfulParse() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        LL1Parser parser = new LL1Parser(parseTable);
        parser.accepts("a");

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();
        expectedRuleIndexes.add(0);
        expectedRuleIndexes.add(1);

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesUntilTableLookupFailure() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        LL1Parser parser = new LL1Parser(parseTable);
        parser.accepts("ab");

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();
        expectedRuleIndexes.add(0);
        expectedRuleIndexes.add(1);

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesUntilEndStateFailure() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        parseTable.addCell(new NonTerminalNode("B"), new TerminalNode("b"), 2);
        LL1Parser parser = new LL1Parser(parseTable);
        parser.accepts("a");

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();
        expectedRuleIndexes.add(0);
        expectedRuleIndexes.add(1);

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesForSequenceOfEndOfStringProductions() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new EndOfStringNode(), 0);
        parseTable.addCell(new NonTerminalNode("A"), new EndOfStringNode(), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);
        LL1Parser parser = new LL1Parser(parseTable);
        parser.accepts("");

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();
        expectedRuleIndexes.add(0);
        expectedRuleIndexes.add(1);
        expectedRuleIndexes.add(2);

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testParseTableEqualsReturnsTrue() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        LL1ParseTable otherParseTable = new LL1ParseTable(grammar);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parser.parseTableEquals(otherParseTable));
    }

    @Test
    public void testParseTableEqualsReturnsFalse() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        LL1ParseTable otherParseTable = new LL1ParseTable(grammar);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);

        assertFalse(parser.parseTableEquals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueWhenParseTablesEqual() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        Grammar otherGrammar = new Grammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherGrammar);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser otherParser = new LL1Parser(otherParseTable);

        assertTrue(parser.equals(otherParser));
    }

    @Test
    public void testEqualsReturnsFalseWhenParseTablesNotEqual() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        Grammar otherGrammar = new Grammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherGrammar);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        LL1Parser otherParser = new LL1Parser(otherParseTable);

        assertFalse(parser.equals(otherParser));
    }

    @Test
    public void testEqualsReturnsFalseForParserWithDifferentClass()
    {
        Grammar grammar = new Grammar();

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        LL1Parser parser = new LL1Parser(parseTable);

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false);

        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state);
        LR0Parser otherParser = new LR0Parser(otherParseTable);

        assertFalse(parser.equals(otherParser));
    }
}
