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

    public Node getProductionNode()
    {
        return this.productionNode;
    }

    public Set<Node> getLookaheadSymbols()
    {
        return this.lookaheadSymbols;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof GrammarClosureRule))
        {
            return false;
        }

        GrammarClosureRule otherRule = (GrammarClosureRule)other;

        return this.productionNode.equals(otherRule.getProductionNode()) && this.lookaheadSymbols.equals(otherRule.getLookaheadSymbols());
    }

    public int hashCode()
    {
        return this.productionNode.hashCode() + this.lookaheadSymbols.hashCode();
    }
}
