/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammar.contextfreelanguage;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;

import java.util.ArrayList;
import java.util.List;

public class Grammar
{
    private List<Node> productions;

    public Grammar()
    {
        this.productions = new ArrayList<Node>();
    }

    public void addProduction(Node productionNode)
    {
        this.productions.add(productionNode);
    }

    public void addProduction(NonTerminalNode nonTerminalNode, Node... rightHandNodes)
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(nonTerminalNode);

        ConcatenationNode concatenationNode = new ConcatenationNode();
        for (Node rightHandNode: rightHandNodes)
        {
            concatenationNode.addChild(rightHandNode);
        }
        productionNode.addChild(concatenationNode);

        this.addProduction(productionNode);
    }

    public Node getProduction(int index)
    {
        return this.productions.get(index);
    }

    public List<Node> getProductions()
    {
        return this.productions;
    }

    public NonTerminalNode getStartSymbol()
    {
        if (this.productions.size() == 0)
        {
            return null;
        }

        return (NonTerminalNode)this.productions.get(0).getChildNodes().get(0);
    }

    public List<Integer> findProductionPositions(Node expectedProduction)
    {
        List<Integer> productionPositions = new ArrayList<Integer>();

        for (int i = 0; i < this.productions.size(); i++)
        {
            Node production = this.productions.get(i);
            if (production.equals(expectedProduction))
            {
                productionPositions.add(i);
            }
        }

        return productionPositions;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof Grammar))
        {
            return false;
        }

        return this.productions.equals(((Grammar)other).getProductions());
    }
}
