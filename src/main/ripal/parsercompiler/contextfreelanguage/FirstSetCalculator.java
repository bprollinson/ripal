/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.contextfreelanguage;

import ripal.grammar.contextfreelanguage.Grammar;
import ripal.parsetree.contextfreelanguage.EndOfStringNode;
import ripal.parsetree.contextfreelanguage.EpsilonNode;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;
import ripal.util.ValueToSetMap;

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
        Node childNode = concatenationNode.getChildNodes().get(0);

        if (childNode instanceof TerminalNode)
        {
            return this.getFirstFromTerminalNode(childNode);
        }
        else if (childNode instanceof NonTerminalNode)
        {
            return this.getFirstFromNonterminalNode(concatenationNode, rulesUsed);
        }
        else if (childNode instanceof EpsilonNode)
        {
            return this.getFirstFromEpsilon();
        }
        else if (childNode instanceof EndOfStringNode)
        {
            return this.getFirstFromEndOfString();
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

            Node childNode = concatenationNode.getChildNodes().get(index);
            if (childNode instanceof TerminalNode)
            {
                TerminalNode terminalChildNode = (TerminalNode)childNode;
                results.add(new TerminalNode(terminalChildNode.getValue().substring(0, 1)));
                break;
            }
            if (childNode instanceof EndOfStringNode)
            {
                results.add(new EndOfStringNode());
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
            }
            else
            {
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

    private Set<Node> getFirstFromEndOfString()
    {
        Set<Node> results = new HashSet<Node>();
        results.add(new EndOfStringNode());

        return results;
    }
}
