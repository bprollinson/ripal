package larp.automaton.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.parsetable.ContextFreeGrammar;

import java.util.HashMap;
import java.util.HashSet;

public class FirstSetCalculator
{
    public HashSet<TerminalNode> getFirst(HashMap<NonTerminalNode, HashSet<Integer>> nonTerminalRules, ContextFreeGrammar grammar, int ruleIndex, HashSet<Integer> rulesUsed)
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
                    results.addAll(this.getFirst(nonTerminalRules, grammar, childRuleIndex, rulesUsed));
                }
            }

            return results;
        }
    }
}
