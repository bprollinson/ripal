package larp.automaton.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.EpsilonNode;
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

    public HashSet<ContextFreeGrammarSyntaxNode> getFirst(int ruleIndex)
    {
        HashSet<Integer> rulesUsed = new HashSet<Integer>();

        return this.getFirstRecursive(ruleIndex, rulesUsed);
    }

    private HashSet<ContextFreeGrammarSyntaxNode> getFirstRecursive(int ruleIndex, HashSet<Integer> rulesUsed)
    {
        rulesUsed.add(ruleIndex);

        ContextFreeGrammarSyntaxNode concatenationNode = grammar.getProduction(ruleIndex).getChildNodes().get(1);
        if (concatenationNode.getChildNodes().get(0) instanceof TerminalNode)
        {
            HashSet<ContextFreeGrammarSyntaxNode> results = new HashSet<ContextFreeGrammarSyntaxNode>();
            results.add((TerminalNode)concatenationNode.getChildNodes().get(0));

            return results;
        }
        else if (concatenationNode.getChildNodes().get(0) instanceof NonTerminalNode)
        {
            HashSet<ContextFreeGrammarSyntaxNode> results = new HashSet<ContextFreeGrammarSyntaxNode>();

            int index = 0;
            boolean epsilonFound = true;
            while (index < concatenationNode.getChildNodes().size() && epsilonFound)
            {
                epsilonFound = false;

                if (concatenationNode.getChildNodes().get(index) instanceof TerminalNode)
                {
                    results.add(concatenationNode.getChildNodes().get(index));
                    break;
                }

                HashSet<Integer> childRuleIndices = this.nonTerminalRules.get(concatenationNode.getChildNodes().get(index));
                if (childRuleIndices != null)
                {
                    for (Integer childRuleIndex: childRuleIndices)
                    {
                        if (childRuleIndex != null && !rulesUsed.contains(childRuleIndex))
                        {
                            HashSet<ContextFreeGrammarSyntaxNode> childSet = this.getFirstRecursive(childRuleIndex, rulesUsed);
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

            if (results.size() == 0 && epsilonFound)
            {
                results.add(new EpsilonNode());
            }

            return results;
        }
        else if (concatenationNode.getChildNodes().get(0) instanceof EpsilonNode)
        {
            HashSet<ContextFreeGrammarSyntaxNode> results = new HashSet<ContextFreeGrammarSyntaxNode>();
            results.add(new EpsilonNode());

            return results;
        }

        return null;
    }
}
