package larp.automaton.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.parsetable.ContextFreeGrammar;
import larp.parsetable.LL1ParseTable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

public class ContextFreeGrammarLL1SyntaxCompiler
{
    public LL1ParseTable compile(ContextFreeGrammar grammar) throws AmbiguousLL1ParseTableException
    {
        LL1ParseTable parseTable = new LL1ParseTable(grammar);

        Vector<ContextFreeGrammarSyntaxNode> productions = grammar.getProductions();

        HashMap<NonTerminalNode, HashSet<Integer>> nonTerminalRules = new HashMap<NonTerminalNode, HashSet<Integer>>();

        for (int i = 0; i < productions.size(); i++)
        {
            ContextFreeGrammarSyntaxNode productionNode = productions.get(i);
            HashSet<Integer> existingSet = nonTerminalRules.get(productionNode.getChildNodes().get(0));
            if (existingSet == null)
            {
                existingSet = new HashSet<Integer>();
            }
            existingSet.add(i);
            nonTerminalRules.put((NonTerminalNode)productionNode.getChildNodes().get(0), existingSet);
        }

        for (Map.Entry<NonTerminalNode, HashSet<Integer>> entry : nonTerminalRules.entrySet()) {
            for (Integer firstRuleIndex: entry.getValue())
            {
                HashSet<Integer> rulesUsed = new HashSet<Integer>();
                TerminalNode first = this.getFirst(nonTerminalRules, grammar, firstRuleIndex, rulesUsed);

                if (first != null)
                {
                    if (parseTable.getCell(entry.getKey(), first) != null)
                    {
                        throw new AmbiguousLL1ParseTableException();
                    }

                    parseTable.addCell(entry.getKey(), first, firstRuleIndex);
                }
            }
        }

        return parseTable;
    }

    private TerminalNode getFirst(HashMap<NonTerminalNode, HashSet<Integer>> nonTerminalRules, ContextFreeGrammar grammar, int ruleIndex, HashSet<Integer> rulesUsed)
    {
        rulesUsed.add(ruleIndex);

        ContextFreeGrammarSyntaxNode concatenationNode = grammar.getProduction(ruleIndex).getChildNodes().get(1);
        if (concatenationNode.getChildNodes().get(0) instanceof TerminalNode)
        {
            return (TerminalNode)concatenationNode.getChildNodes().get(0);
        }
        else
        {
            for (Integer childRuleIndex: nonTerminalRules.get(concatenationNode.getChildNodes().get(0)))
            {
                if (childRuleIndex != null && !rulesUsed.contains(childRuleIndex))
                {
                    return this.getFirst(nonTerminalRules, grammar, childRuleIndex, rulesUsed);
                }
            }
        }

        return null;
    }
}
