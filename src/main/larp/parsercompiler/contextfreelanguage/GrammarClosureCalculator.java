/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.Grammar;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.util.ValueToSetMap;

import java.util.HashSet;
import java.util.Set;

public class GrammarClosureCalculator
{
    private ProductionNodeDotRepository productionNodeDotRepository;

    public GrammarClosureCalculator()
    {
        this.productionNodeDotRepository = new ProductionNodeDotRepository();
    }

    public Set<GrammarClosureRule> calculateRules(Grammar grammar, Set<GrammarClosureRule> productionSet)
    {
        Set<GrammarClosureRule> productionsClosure = new HashSet<GrammarClosureRule>();
        productionsClosure.addAll(productionSet);
        ValueToSetMap<Node, Integer> startingNonTerminalProductionsMap = this.calculateStartingNonTerminalProductionsMap(grammar);

        boolean continueExpansion = true;
        while (continueExpansion)
        {
            continueExpansion = this.expandClosure(grammar, startingNonTerminalProductionsMap, productionsClosure);
        }

        return productionsClosure;
    }

    private ValueToSetMap<Node, Integer> calculateStartingNonTerminalProductionsMap(Grammar grammar)
    {
        ValueToSetMap<Node, Integer> startingNonTerminalProductions = new ValueToSetMap<Node, Integer>();
        for (int i = 0; i < grammar.getProductions().size(); i++)
        {
            startingNonTerminalProductions.put(grammar.getProduction(i).getChildNodes().get(0), i);
        }

        return startingNonTerminalProductions;
    }

    private boolean expandClosure(Grammar grammar, ValueToSetMap<Node, Integer> startingNonTerminalProductionsMap, Set<GrammarClosureRule> productionsClosure)
    {
        Set<GrammarClosureRule> productionsToAdd = new HashSet<GrammarClosureRule>();

        for (GrammarClosureRule productionNode: productionsClosure)
        {
            Node nextSymbol = this.productionNodeDotRepository.findProductionSymbolAfterDot(productionNode.getProductionNode());
            if (nextSymbol != null)
            {
                this.addProductionsForNonTerminal(grammar, startingNonTerminalProductionsMap.get(nextSymbol), productionsToAdd);
            }
            if (nextSymbol instanceof EpsilonNode)
            {
                productionsToAdd.add(this.buildEpsilonProduction(productionNode.getProductionNode().getChildNodes().get(0)));
            }
        }

        return productionsClosure.addAll(productionsToAdd);
    }

    private void addProductionsForNonTerminal(Grammar grammar, Set<Integer> productionIndices, Set<GrammarClosureRule> productionsToAdd)
    {
        for (int productionIndex: productionIndices)
        {
            Node production = grammar.getProductions().get(productionIndex);

            Node productionNode = this.productionNodeDotRepository.addDotToProductionRightHandSide(production);
            productionsToAdd.add(new GrammarClosureRule(productionNode, new HashSet<Node>()));
        }
    }

    private GrammarClosureRule buildEpsilonProduction(Node leftHandNonTerminal)
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(leftHandNonTerminal);

        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new EpsilonNode());
        concatenationNode.addChild(new DotNode());
        productionNode.addChild(concatenationNode);

        return new GrammarClosureRule(productionNode, new HashSet<Node>());
    }
}
