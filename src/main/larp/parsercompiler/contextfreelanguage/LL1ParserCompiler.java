/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.Grammar;
import larp.parser.contextfreelanguage.AmbiguousLL1ParseTableException;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;

import java.util.List;
import java.util.Set;

public class LL1ParserCompiler
{
    public LL1ParseTable compile(Grammar grammar) throws AmbiguousLL1ParseTableException
    {
        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        List<Node> productions = grammar.getProductions();

        FirstSetCalculator firstSetCalculator = new FirstSetCalculator(grammar);
        FollowSetCalculator followSetCalculator = new FollowSetCalculator(grammar);

        for (int ruleIndex = 0; ruleIndex < productions.size(); ruleIndex++)
        {
            Set<Node> firsts = firstSetCalculator.getFirst(ruleIndex);
            NonTerminalNode nonTerminalNode = (NonTerminalNode)productions.get(ruleIndex).getChildNodes().get(0);

            for (Node first: firsts)
            {
                if (first instanceof EpsilonNode)
                {
                    this.addCellsForEpsilonNode(parseTable, followSetCalculator, nonTerminalNode, ruleIndex);
                    continue;
                }

                this.addCellForNonEpsilonNode(parseTable, nonTerminalNode, first, ruleIndex);
            }
        }

        return parseTable;
    }

    private void addCellsForEpsilonNode(LL1ParseTable parseTable, FollowSetCalculator followSetCalculator, NonTerminalNode nonTerminalNode, int ruleIndex) throws AmbiguousLL1ParseTableException
    {
        Set<Node> follows = followSetCalculator.getFollow(nonTerminalNode);
        for (Node follow: follows)
        {
            parseTable.addCell(nonTerminalNode, follow, ruleIndex);
        }
    }

    private void addCellForNonEpsilonNode(LL1ParseTable parseTable, NonTerminalNode nonTerminalNode, Node first, int ruleIndex) throws AmbiguousLL1ParseTableException
    {
        parseTable.addCell(nonTerminalNode, first, ruleIndex);
    }
}
