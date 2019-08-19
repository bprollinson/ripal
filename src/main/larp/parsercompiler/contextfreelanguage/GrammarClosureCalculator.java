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
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.util.ValueToSetMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GrammarClosureCalculator
{
    private ProductionNodeDotRepository productionNodeDotRepository;

    public GrammarClosureCalculator()
    {
        this.productionNodeDotRepository = new ProductionNodeDotRepository();
    }

    public Set<GrammarClosureRule> calculate(Grammar grammar, Set<GrammarClosureRule> initialClosureRules)
    {
        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        closureRules.addAll(initialClosureRules);
        ValueToSetMap<Node, Integer> startingNonTerminalProductionsMap = this.calculateStartingNonTerminalProductionsMap(grammar);

        boolean continueExpansion = true;
        while (continueExpansion)
        {
            continueExpansion = this.expandClosure(grammar, startingNonTerminalProductionsMap, closureRules);
        }

        return closureRules;
    }

    private ValueToSetMap<Node, Integer> calculateStartingNonTerminalProductionsMap(Grammar grammar)
    {
        ValueToSetMap<Node, Integer> startingNonTerminalProductions = new ValueToSetMap<Node, Integer>();
        for (int i = 0; i < grammar.getProductions().size(); i++)
        {
            Node leftHandSide = grammar.getProduction(i).getChildNodes().get(0);
            startingNonTerminalProductions.put(leftHandSide, i);
        }

        return startingNonTerminalProductions;
    }

    private boolean expandClosure(Grammar grammar, ValueToSetMap<Node, Integer> startingNonTerminalProductionsMap, Set<GrammarClosureRule> rulesClosure)
    {
        Set<GrammarClosureRule> closureRulesToAdd = new HashSet<GrammarClosureRule>();

        for (GrammarClosureRule closureRule: rulesClosure)
        {
            List<Node> nextSymbols = this.productionNodeDotRepository.findProductionSymbolsAfterDot(closureRule.getProductionNode());

            if (nextSymbols.size() > 0)
            {
                boolean addLookaheadSymbols = closureRule.getLookaheadSymbols() == null || !closureRule.getLookaheadSymbols().isEmpty();
                this.addClosureRulesForNonTerminal(grammar, closureRule, startingNonTerminalProductionsMap.get(nextSymbols.get(0)), closureRulesToAdd, addLookaheadSymbols, nextSymbols.subList(1, nextSymbols.size()));
            }
            if (nextSymbols.size() > 0 && nextSymbols.get(0) instanceof EpsilonNode)
            {
                closureRulesToAdd.add(this.buildEpsilonClosureRule(closureRule.getProductionNode().getChildNodes().get(0), closureRule.getLookaheadSymbols()));
            }
        }

        return rulesClosure.addAll(closureRulesToAdd);
    }

    private void addClosureRulesForNonTerminal(Grammar grammar, GrammarClosureRule closureRule, Set<Integer> productionIndices, Set<GrammarClosureRule> closureRulesToAdd, boolean addLookaheadSymbols, List<Node> nextSymbols)
    {
        for (int productionIndex: productionIndices)
        {
            Node production = grammar.getProductions().get(productionIndex);

            Node productionNode = this.productionNodeDotRepository.addDotToProductionRightHandSide(production);
            if (addLookaheadSymbols)
            {
                for (Node parentLookaheadSymbol: closureRule.getLookaheadSymbols())
                {
                    List<Node> nextSymbolsForSymbol = new ArrayList<Node>();
                    nextSymbolsForSymbol.addAll(nextSymbols);
                    nextSymbolsForSymbol.add(parentLookaheadSymbol);
                    Set<Node> lookaheadSymbols = this.calculateLookaheadSymbols(grammar, nextSymbolsForSymbol);
                    closureRulesToAdd.add(new GrammarClosureRule(productionNode, lookaheadSymbols));
                }
            }
            else
            {
                closureRulesToAdd.add(new GrammarClosureRule(productionNode));
            }
        }
    }

    private Set<Node> calculateLookaheadSymbols(Grammar grammar, List<Node> nextSymbols)
    {
        Grammar lookaheadGrammar = new Grammar();

        Node productionNode = new ProductionNode();
        productionNode.getChildNodes().add(new NonTerminalNode("A"));
        Node concatenationNode = new ConcatenationNode();
        for (Node nextSymbol: nextSymbols)
        {
            concatenationNode.getChildNodes().add(nextSymbol);
        }
        productionNode.getChildNodes().add(concatenationNode);
        lookaheadGrammar.addProduction(productionNode);

        for (Node grammarProduction: grammar.getProductions())
        {
            lookaheadGrammar.addProduction(grammarProduction);
        }

        FirstSetCalculator firstSetCalculator = new FirstSetCalculator(lookaheadGrammar);

        return firstSetCalculator.getFirst(0);
    }

    private GrammarClosureRule buildEpsilonClosureRule(Node leftHandNonTerminal, Set<Node> lookaheadSymbols)
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(leftHandNonTerminal);

        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new EpsilonNode());
        concatenationNode.addChild(new DotNode());
        productionNode.addChild(concatenationNode);

        return new GrammarClosureRule(productionNode, lookaheadSymbols);
    }
}
