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

    public Set<GrammarClosureRule> calculate(Grammar grammar, Set<GrammarClosureRule> productionSet)
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

    private boolean expandClosure(Grammar grammar, ValueToSetMap<Node, Integer> startingNonTerminalProductionsMap, Set<GrammarClosureRule> rulesClosure)
    {
        Set<GrammarClosureRule> closureRulesToAdd = new HashSet<GrammarClosureRule>();

        for (GrammarClosureRule closureRule: rulesClosure)
        {
            Node nextSymbol = this.productionNodeDotRepository.findProductionSymbolAfterDot(closureRule.getProductionNode());
            if (nextSymbol != null)
            {
                this.addClosureRulesForNonTerminal(grammar, startingNonTerminalProductionsMap.get(nextSymbol), closureRulesToAdd);
            }
            if (nextSymbol instanceof EpsilonNode)
            {
                closureRulesToAdd.add(this.buildEpsilonClosureRule(closureRule.getProductionNode().getChildNodes().get(0)));
            }
        }

        return rulesClosure.addAll(closureRulesToAdd);
    }

    private void addClosureRulesForNonTerminal(Grammar grammar, Set<Integer> productionIndices, Set<GrammarClosureRule> closureRulesToAdd)
    {
        for (int productionIndex: productionIndices)
        {
            Node production = grammar.getProductions().get(productionIndex);

            Node productionNode = this.productionNodeDotRepository.addDotToProductionRightHandSide(production);
            closureRulesToAdd.add(new GrammarClosureRule(productionNode));
        }
    }

    private GrammarClosureRule buildEpsilonClosureRule(Node leftHandNonTerminal)
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(leftHandNonTerminal);

        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new EpsilonNode());
        concatenationNode.addChild(new DotNode());
        productionNode.addChild(concatenationNode);

        return new GrammarClosureRule(productionNode);
    }
}
