/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.contextfreelanguage;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parser.contextfreelanguage.AmbiguousLL1ParseTableException;
import ripal.parser.contextfreelanguage.LL1ParseTable;
import ripal.parsetree.contextfreelanguage.EpsilonNode;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;

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
