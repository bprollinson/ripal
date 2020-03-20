/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parser.contextfreelanguage;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.util.PairToValueMap;

public class LL1ParseTable
{
    private Grammar grammar;
    private PairToValueMap<NonTerminalNode, Node, Integer> cells;

    public LL1ParseTable(Grammar grammar)
    {
        this.grammar = grammar;
        this.cells = new PairToValueMap<NonTerminalNode, Node, Integer>();
    }

    public void addCell(NonTerminalNode nonTerminalNode, Node syntaxNode, int ruleIndex) throws AmbiguousLL1ParseTableException
    {
        new LL1ParseTableCellAvailableAssertion(this, nonTerminalNode, syntaxNode).validate();

        this.cells.put(nonTerminalNode, syntaxNode, ruleIndex);
    }

    public Integer getCell(NonTerminalNode nonTerminalNode, Node syntaxNode)
    {
        return this.cells.get(nonTerminalNode, syntaxNode);
    }

    public Grammar getGrammar()
    {
        return this.grammar;
    }

    public boolean cellsEqual(PairToValueMap<NonTerminalNode, Node, Integer> otherCells)
    {
        return this.cells.equals(otherCells);
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LL1ParseTable))
        {
            return false;
        }

        if (!this.grammar.equals(((LL1ParseTable)other).getGrammar()))
        {
            return false;
        }

        return ((LL1ParseTable)other).cellsEqual(this.cells);
    }
}
