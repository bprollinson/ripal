/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.Grammar;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.util.ValueToSetMap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FirstSetCalculator
{
    private boolean initialized;
    private Grammar grammar;
    private ValueToSetMap<Node, Integer> nonTerminalRules;

    public FirstSetCalculator(Grammar grammar)
    {
        this.initialized = false;
        this.grammar = grammar;
        this.nonTerminalRules = new ValueToSetMap<Node, Integer>();
    }

    public Set<Node> getFirst(int ruleIndex)
    {
        if (ruleIndex >= this.grammar.getProductions().size())
        {
            return new HashSet<Node>();
        }

        this.initialize();

        Set<Integer> rulesUsed = new HashSet<Integer>();

        return this.getFirstRecursive(ruleIndex, rulesUsed);
    }

    public Set<Node> getFirst(NonTerminalNode node)
    {
        this.initialize();

        Set<Node> results = new HashSet<Node>();
        Set<Integer> existingSet = this.nonTerminalRules.get(node);
        if (existingSet != null)
        {
            for (int index: existingSet)
            {
                results.addAll(this.getFirst(index));
            }
        }

        return results;
    }

    private void initialize()
    {
        if (this.initialized)
        {
            return;
        }

        List<Node> productions = this.grammar.getProductions();
        for (int i = 0; i < productions.size(); i++)
        {
            Node productionNode = productions.get(i);
            this.nonTerminalRules.put(productionNode.getChildNodes().get(0), i);
        }

        this.initialized = true;
    }

    private Set<Node> getFirstRecursive(int ruleIndex, Set<Integer> rulesUsed)
    {
        rulesUsed.add(ruleIndex);

        Node concatenationNode = grammar.getProduction(ruleIndex).getChildNodes().get(1);
        if (concatenationNode.getChildNodes().get(0) instanceof TerminalNode)
        {
            return this.getFirstFromTerminalNode(concatenationNode.getChildNodes().get(0));
        }
        else if (concatenationNode.getChildNodes().get(0) instanceof NonTerminalNode)
        {
            return this.getFirstFromNonterminalNode(concatenationNode, rulesUsed);
        }
        else if (concatenationNode.getChildNodes().get(0) instanceof EpsilonNode)
        {
            return this.getFirstFromEpsilon();
        }

        return null;
    }

    private Set<Node> getFirstFromTerminalNode(Node terminalNode)
    {
        Set<Node> results = new HashSet<Node>();
        String value = ((TerminalNode)terminalNode).getValue();
        results.add(new TerminalNode(value.substring(0, 1)));

        return results;
    }

    private Set<Node> getFirstFromNonterminalNode(Node concatenationNode, Set<Integer> rulesUsed)
    {
        Set<Node> results = new HashSet<Node>();

        int index = 0;
        boolean epsilonFound = true;
        while (index < concatenationNode.getChildNodes().size() && epsilonFound)
        {
            epsilonFound = false;

            if (concatenationNode.getChildNodes().get(index) instanceof TerminalNode)
            {
                TerminalNode childNode = (TerminalNode)concatenationNode.getChildNodes().get(index);
                results.add(new TerminalNode(childNode.getValue().substring(0, 1)));
                break;
            }

            Set<Integer> childRuleIndices = this.nonTerminalRules.get(concatenationNode.getChildNodes().get(index));
            if (childRuleIndices != null)
            {
                for (Integer childRuleIndex: childRuleIndices)
                {
                    if (childRuleIndex != null && !rulesUsed.contains(childRuleIndex))
                    {
                        Set<Node> childSet = this.getFirstRecursive(childRuleIndex, rulesUsed);
                        epsilonFound = childSet.contains(new EpsilonNode());
                        childSet.remove(new EpsilonNode());
                        results.addAll(childSet);
                    }
                }
            } else {
                epsilonFound = false;
            }

            index++;
        }

        if (epsilonFound)
        {
            results.add(new EpsilonNode());
        }

        return results;
    }

    private Set<Node> getFirstFromEpsilon()
    {
        Set<Node> results = new HashSet<Node>();
        results.add(new EpsilonNode());

        return results;
    }
}
