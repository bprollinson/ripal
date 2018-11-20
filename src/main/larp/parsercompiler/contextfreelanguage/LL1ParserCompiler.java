/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLL1ParseTableException;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;

import java.util.List;
import java.util.Set;

public class LL1ParserCompiler
{
    public LL1ParseTable compile(ContextFreeGrammar grammar) throws AmbiguousLL1ParseTableException
    {
        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        List<ContextFreeGrammarParseTreeNode> productions = grammar.getProductions();

        FirstSetCalculator firstCalculator = new FirstSetCalculator(grammar);
        FollowSetCalculator followSetCalculator = new FollowSetCalculator(grammar);

        for (int firstRuleIndex = 0; firstRuleIndex < productions.size(); firstRuleIndex++)
        {
            Set<ContextFreeGrammarParseTreeNode> firsts = firstCalculator.getFirst(firstRuleIndex);
            NonTerminalNode nonTerminalNode = (NonTerminalNode)productions.get(firstRuleIndex).getChildNodes().get(0);

            for (ContextFreeGrammarParseTreeNode first: firsts)
            {
                if (first instanceof EpsilonNode)
                {
                    Set<ContextFreeGrammarParseTreeNode> follows = followSetCalculator.getFollow(nonTerminalNode);
                    for (ContextFreeGrammarParseTreeNode follow: follows)
                    {
                        parseTable.addCell(nonTerminalNode, follow, firstRuleIndex);
                    }

                    continue;
                }

                parseTable.addCell(nonTerminalNode, first, firstRuleIndex);
            }
        }

        return parseTable;
    }
}
