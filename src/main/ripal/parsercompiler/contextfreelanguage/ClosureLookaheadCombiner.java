/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.contextfreelanguage;

import ripal.parsetree.contextfreelanguage.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClosureLookaheadCombiner
{
    public Set<GrammarClosureRule> combine(Set<GrammarClosureRule> closureRules)
    {
        List<GrammarClosureRule> combinedClosureRules = new ArrayList<GrammarClosureRule>();
        Map<Node, Integer> nodeToProductionIndexMap = new HashMap<Node, Integer>();

        for (GrammarClosureRule closureRule: closureRules)
        {
            this.combineRule(combinedClosureRules, nodeToProductionIndexMap, closureRule);
        }

        return new HashSet<GrammarClosureRule>(combinedClosureRules);
    }

    private void combineRule(List<GrammarClosureRule> combinedClosureRules, Map<Node, Integer> nodeToProductionIndexMap, GrammarClosureRule closureRule)
    {
        Integer existingRuleIndex = nodeToProductionIndexMap.get(closureRule.getProductionNode());
        if (existingRuleIndex == null)
        {
            this.addNewRule(combinedClosureRules, nodeToProductionIndexMap, closureRule);
        }
        else
        {
            this.updateExistingRule(combinedClosureRules, existingRuleIndex, closureRule);
        }
    }

    private void addNewRule(List<GrammarClosureRule> combinedClosureRules, Map<Node, Integer> nodeToProductionIndexMap, GrammarClosureRule closureRule)
    {
        combinedClosureRules.add(closureRule);
        nodeToProductionIndexMap.put(closureRule.getProductionNode(), combinedClosureRules.size() - 1);
    }

    private void updateExistingRule(List<GrammarClosureRule> combinedClosureRules, int existingRuleIndex, GrammarClosureRule closureRule)
    {
        GrammarClosureRule existingRule = combinedClosureRules.get(existingRuleIndex);
        existingRule.getLookaheadSymbols().addAll(closureRule.getLookaheadSymbols());
    }
}
