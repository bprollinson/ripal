/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import larp.parsetree.contextfreelanguage.Node;

import java.util.Set;

public class GrammarClosureRule
{
    private Node productionNode;
    private Set<Node> lookaheadSymbols;

    public GrammarClosureRule(Node productionNode, Set<Node> lookaheadSymbols)
    {
        this.productionNode = productionNode;
        this.lookaheadSymbols = lookaheadSymbols;
    }
}
