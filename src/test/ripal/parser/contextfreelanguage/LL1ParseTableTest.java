/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parser.contextfreelanguage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;
import ripal.util.PairToValueMap;

public class LL1ParseTableTest
{
    @Test(expected = LL1ApplyApplyConflictException.class)
    public void testAddCellThrowsExceptionForCellThatAlreadyExists() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
    }

    @Test
    public void testAddCellDoesNotThrowExceptionForCellThatDoesNotAlreadyExist() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
    }

    @Test
    public void testCellsEqualReturnsTrue() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        PairToValueMap<NonTerminalNode, Node, Integer> cells = new PairToValueMap<NonTerminalNode, Node, Integer>();
        cells.put(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parseTable.cellsEqual(cells));
    }

    @Test
    public void testCellsEqualReturnsFalse() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        PairToValueMap<NonTerminalNode, Node, Integer> cells = new PairToValueMap<NonTerminalNode, Node, Integer>();
        cells.put(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 1);

        assertFalse(parseTable.cellsEqual(cells));
    }

    @Test
    public void testEqualsReturnsTrueForEmptyGrammarAndNoTableEntries()
    {
        LL1ParseTable parseTable = new LL1ParseTable(new Grammar());
        LL1ParseTable otherParseTable = new LL1ParseTable(new Grammar());

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueForNonEmptyGrammarAndTableEntries() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        Grammar otherGrammar = new Grammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherGrammar);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentGrammars() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        Grammar otherGrammar = new Grammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherGrammar);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentTableEntries() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        Grammar otherGrammar = new Grammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherGrammar);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueForSameTableEntriesInDifferentOrder() throws AmbiguousLL1ParseTableException
    {
        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);

        Grammar otherGrammar = new Grammar();
        otherGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherGrammar);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForParseTableWithDifferentClass()
    {
        Grammar grammar = new Grammar();

        LL1ParseTable parseTable = new LL1ParseTable(grammar);

        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);
        LR0ParseTable otherParseTable = new LR0ParseTable(grammar, state);

        assertFalse(parseTable.equals(otherParseTable));
    }
}
