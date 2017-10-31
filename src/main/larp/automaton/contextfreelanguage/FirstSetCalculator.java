package larp.automaton.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.parsetable.ContextFreeGrammar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class FirstSetCalculator
{
    private ContextFreeGrammar grammar;
    private HashMap<NonTerminalNode, HashSet<Integer>> nonTerminalRules;

    public FirstSetCalculator(ContextFreeGrammar grammar)
    {
        this.grammar = grammar;
        this.nonTerminalRules = new HashMap<NonTerminalNode, HashSet<Integer>>();

        Vector<ContextFreeGrammarSyntaxNode> productions = this.grammar.getProductions();
        for (int i = 0; i < productions.size(); i++)
        {
            ContextFreeGrammarSyntaxNode productionNode = productions.get(i);
            HashSet<Integer> existingSet = this.nonTerminalRules.get(productionNode.getChildNodes().get(0));
            if (existingSet == null)
            {
                existingSet = new HashSet<Integer>();
            }
            existingSet.add(i);
            this.nonTerminalRules.put((NonTerminalNode)productionNode.getChildNodes().get(0), existingSet);
        }
    }

    public HashSet<TerminalNode> getFirst(int ruleIndex)
    {
        HashSet<Integer> rulesUsed = new HashSet<Integer>();

        return this.getFirstRecursive(ruleIndex, rulesUsed);
    }

    private HashSet<TerminalNode> getFirstRecursive(int ruleIndex, HashSet<Integer> rulesUsed)
    {
        rulesUsed.add(ruleIndex);

        ContextFreeGrammarSyntaxNode concatenationNode = grammar.getProduction(ruleIndex).getChildNodes().get(1);
        if (concatenationNode.getChildNodes().get(0) instanceof TerminalNode)
        {
            HashSet<TerminalNode> results = new HashSet<TerminalNode>();
            results.add((TerminalNode)concatenationNode.getChildNodes().get(0));

            return results;
        }
        else
        {
            HashSet<TerminalNode> results = new HashSet<TerminalNode>();
            for (Integer childRuleIndex: nonTerminalRules.get(concatenationNode.getChildNodes().get(0)))
            {
                if (childRuleIndex != null && !rulesUsed.contains(childRuleIndex))
                {
                    results.addAll(this.getFirstRecursive(childRuleIndex, rulesUsed));
                }
            }

            return results;
        }
    }
}
