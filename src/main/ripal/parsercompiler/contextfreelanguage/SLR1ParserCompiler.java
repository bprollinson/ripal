/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.contextfreelanguage;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import ripal.parser.contextfreelanguage.LR0ParseTable;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;

import java.util.Set;

public class SLR1ParserCompiler extends LR0ParserCompiler
{
    private FollowSetCalculator followSetCalculator;

    public LR0ParseTable compile(Grammar grammar) throws AmbiguousLR0ParseTableException
    {
        this.followSetCalculator = new FollowSetCalculator(grammar);

        return super.compile(grammar);
    }

    protected boolean shouldReduceForProduction(Node nonTerminalNode, Node terminalNode, Set<Node> lookaheadSymbols)
    {
        Set<Node> follows = this.followSetCalculator.getFollow((NonTerminalNode)nonTerminalNode);

        return follows.contains(terminalNode);
    }
}
