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
    private ClosureLookaheadCombiner lookaheadCombiner;

    public GrammarClosureCalculator()
    {
        this.productionNodeDotRepository = new ProductionNodeDotRepository();
        this.lookaheadCombiner = new ClosureLookaheadCombiner();
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

        return this.lookaheadCombiner.combine(closureRules);
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

            if (nextSymbols == null || nextSymbols.size() == 0)
            {
                continue;
            }

            Node nextSymbol = nextSymbols.get(0);
            List<Node> followingSymbols = nextSymbols.subList(1, nextSymbols.size());
            Set<Integer> productionIndices = startingNonTerminalProductionsMap.get(nextSymbol);

            Set<GrammarClosureRule> nonTerminalClosureRules = this.addClosureRulesForNonTerminal(grammar, closureRule, productionIndices, followingSymbols);
            closureRulesToAdd.addAll(nonTerminalClosureRules);

            if (nextSymbol instanceof EpsilonNode)
            {
                closureRulesToAdd.add(this.buildEpsilonClosureRule(closureRule.getProductionNode().getChildNodes().get(0), closureRule.getLookaheadSymbols()));
            }
        }

        return rulesClosure.addAll(closureRulesToAdd);
    }

    private Set<GrammarClosureRule> addClosureRulesForNonTerminal(Grammar grammar, GrammarClosureRule closureRule, Set<Integer> productionIndices, List<Node> followingSymbols)
    {
        Set<GrammarClosureRule> closureRulesToAdd = new HashSet<GrammarClosureRule>();

        for (int productionIndex: productionIndices)
        {
            Node production = grammar.getProductions().get(productionIndex);

            Node productionNode = this.productionNodeDotRepository.addDotToProduction(production);
            boolean addLookaheadSymbols = !closureRule.getLookaheadSymbols().isEmpty();

            if (addLookaheadSymbols)
            {
                for (Node parentLookaheadSymbol: closureRule.getLookaheadSymbols())
                {
                    List<Node> followingSymbolsForSymbol = new ArrayList<Node>();
                    followingSymbolsForSymbol.addAll(followingSymbols);
                    followingSymbolsForSymbol.add(parentLookaheadSymbol);
                    Set<Node> lookaheadSymbols = this.calculateLookaheadSymbols(grammar, followingSymbolsForSymbol);
                    closureRulesToAdd.add(new GrammarClosureRule(productionNode, lookaheadSymbols));
                }
            }
            else
            {
                closureRulesToAdd.add(new GrammarClosureRule(productionNode));
            }
        }

        return closureRulesToAdd;
    }

    private Set<Node> calculateLookaheadSymbols(Grammar grammar, List<Node> followingSymbols)
    {
        Grammar lookaheadGrammar = new Grammar();

        Node productionNode = new ProductionNode();
        productionNode.getChildNodes().add(new NonTerminalNode("A"));
        Node concatenationNode = new ConcatenationNode();
        for (Node followingSymbol: followingSymbols)
        {
            concatenationNode.getChildNodes().add(followingSymbol);
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
