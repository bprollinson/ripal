/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import larp.parsetree.contextfreelanguage.Node;

import java.util.HashSet;
import java.util.Set;

public class GrammarClosureRule
{
    private Node productionNode;
    private Set<Node> lookaheadSymbols;

    public GrammarClosureRule(Node productionNode)
    {
        this(productionNode, new HashSet<Node>());
    }

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

    public void setLookaheadSymbols(Set<Node> lookaheadSymbols)
    {
        this.lookaheadSymbols = lookaheadSymbols;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof GrammarClosureRule))
        {
            return false;
        }

        GrammarClosureRule otherRule = (GrammarClosureRule)other;
        boolean lookaheadSymbolsEqual = this.lookaheadSymbols == null && otherRule.getLookaheadSymbols() == null || this.lookaheadSymbols.equals(otherRule.getLookaheadSymbols());

        return this.productionNode.equals(otherRule.getProductionNode()) && lookaheadSymbolsEqual;
    }

    public int hashCode()
    {
        int lookaheadSymbolsHashCode = this.lookaheadSymbols == null ? 0 : this.lookaheadSymbols.hashCode();

        return this.productionNode.hashCode() + lookaheadSymbolsHashCode;
    }
}
