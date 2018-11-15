/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.util.ValueToSetMap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FirstSetCalculator
{
    private boolean initialized;
    private ContextFreeGrammar grammar;
    private ValueToSetMap<ContextFreeGrammarParseTreeNode, Integer> nonTerminalRules;

    public FirstSetCalculator(ContextFreeGrammar grammar)
    {
        this.initialized = false;
        this.grammar = grammar;
        this.nonTerminalRules = new ValueToSetMap<ContextFreeGrammarParseTreeNode, Integer>();
    }

    public Set<ContextFreeGrammarParseTreeNode> getFirst(int ruleIndex)
    {
        this.initialize();

        Set<Integer> rulesUsed = new HashSet<Integer>();

        return this.getFirstRecursive(ruleIndex, rulesUsed);
    }

    public Set<ContextFreeGrammarParseTreeNode> getFirst(NonTerminalNode node)
    {
        this.initialize();

        Set<ContextFreeGrammarParseTreeNode> results = new HashSet<ContextFreeGrammarParseTreeNode>();
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

        List<ContextFreeGrammarParseTreeNode> productions = this.grammar.getProductions();
        for (int i = 0; i < productions.size(); i++)
        {
            ContextFreeGrammarParseTreeNode productionNode = productions.get(i);
            this.nonTerminalRules.put(productionNode.getChildNodes().get(0), i);
        }

        this.initialized = true;
    }

    private Set<ContextFreeGrammarParseTreeNode> getFirstRecursive(int ruleIndex, Set<Integer> rulesUsed)
    {
        rulesUsed.add(ruleIndex);

        ContextFreeGrammarParseTreeNode concatenationNode = grammar.getProduction(ruleIndex).getChildNodes().get(1);
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

    private Set<ContextFreeGrammarParseTreeNode> getFirstFromTerminalNode(ContextFreeGrammarParseTreeNode terminalNode)
    {
        Set<ContextFreeGrammarParseTreeNode> results = new HashSet<ContextFreeGrammarParseTreeNode>();
        String value = ((TerminalNode)terminalNode).getValue();
        results.add(new TerminalNode(value.substring(0, 1)));

        return results;
    }

    private Set<ContextFreeGrammarParseTreeNode> getFirstFromNonterminalNode(ContextFreeGrammarParseTreeNode concatenationNode, Set<Integer> rulesUsed)
    {
        Set<ContextFreeGrammarParseTreeNode> results = new HashSet<ContextFreeGrammarParseTreeNode>();

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
                        Set<ContextFreeGrammarParseTreeNode> childSet = this.getFirstRecursive(childRuleIndex, rulesUsed);
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

    private Set<ContextFreeGrammarParseTreeNode> getFirstFromEpsilon()
    {
        Set<ContextFreeGrammarParseTreeNode> results = new HashSet<ContextFreeGrammarParseTreeNode>();
        results.add(new EpsilonNode());

        return results;
    }
}
