/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.util.ValueToSetMap;

import java.util.HashSet;
import java.util.Set;

public class ContextFreeGrammarClosureCalculator
{
    private ProductionNodeDotRepository productionNodeDotRepository;

    public ContextFreeGrammarClosureCalculator()
    {
        this.productionNodeDotRepository = new ProductionNodeDotRepository();
    }

    public Set<ContextFreeGrammarParseTreeNode> calculate(ContextFreeGrammar grammar, Set<ContextFreeGrammarParseTreeNode> productionSet)
    {
        Set<ContextFreeGrammarParseTreeNode> productionsClosure = new HashSet<ContextFreeGrammarParseTreeNode>();
        productionsClosure.addAll(productionSet);
        ValueToSetMap<ContextFreeGrammarParseTreeNode, Integer> startingNonTerminalProductionsMap = this.calculateStartingNonTerminalProductionsMap(grammar);

        boolean continueExpansion = true;
        while (continueExpansion)
        {
            continueExpansion = this.expandClosure(grammar, startingNonTerminalProductionsMap, productionsClosure);
        }

        return productionsClosure;
    }

    private ValueToSetMap<ContextFreeGrammarParseTreeNode, Integer> calculateStartingNonTerminalProductionsMap(ContextFreeGrammar grammar)
    {
        ValueToSetMap<ContextFreeGrammarParseTreeNode, Integer> startingNonTerminalProductions = new ValueToSetMap<ContextFreeGrammarParseTreeNode, Integer>();
        for (int i = 0; i < grammar.getProductions().size(); i++)
        {
            startingNonTerminalProductions.put(grammar.getProduction(i).getChildNodes().get(0), i);
        }

        return startingNonTerminalProductions;
    }

    private boolean expandClosure(ContextFreeGrammar grammar, ValueToSetMap<ContextFreeGrammarParseTreeNode, Integer> startingNonTerminalProductionsMap, Set<ContextFreeGrammarParseTreeNode> productionsClosure)
    {
        Set<ContextFreeGrammarParseTreeNode> productionsToAdd = new HashSet<ContextFreeGrammarParseTreeNode>();

        for (ContextFreeGrammarParseTreeNode productionNode: productionsClosure)
        {
            ContextFreeGrammarParseTreeNode nextSymbol = this.productionNodeDotRepository.findProductionSymbolAfterDot(productionNode);
            if (nextSymbol != null)
            {
                this.addProductionsForNonTerminal(grammar, startingNonTerminalProductionsMap.get(nextSymbol), productionsToAdd);
            }
            if (nextSymbol instanceof EpsilonNode)
            {
                productionsToAdd.add(this.buildEpsilonProduction(productionNode.getChildNodes().get(0)));
            }
        }

        return productionsClosure.addAll(productionsToAdd);
    }

    private void addProductionsForNonTerminal(ContextFreeGrammar grammar, Set<Integer> productionIndices, Set<ContextFreeGrammarParseTreeNode> productionsToAdd)
    {
        for (int productionIndex: productionIndices)
        {
            ContextFreeGrammarParseTreeNode production = grammar.getProductions().get(productionIndex);

            ContextFreeGrammarParseTreeNode productionNode = this.productionNodeDotRepository.addDotToProductionRightHandSide(production);
            productionsToAdd.add(productionNode);
        }
    }

    private ProductionNode buildEpsilonProduction(ContextFreeGrammarParseTreeNode leftHandNonTerminal)
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(leftHandNonTerminal);

        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new EpsilonNode());
        concatenationNode.addChild(new DotNode());
        productionNode.addChild(concatenationNode);

        return productionNode;
    }
}
