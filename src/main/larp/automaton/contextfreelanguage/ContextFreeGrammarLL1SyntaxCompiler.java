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
    public LL1ParseTable compile(ContextFreeGrammar grammar)
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
            Integer firstRuleIndex = entry.getValue().iterator().next();
            TerminalNode first = this.getFirst(grammar, firstRuleIndex);

            if (first != null)
            {
                parseTable.addCell(entry.getKey(), first, firstRuleIndex);
            }
        }

        return parseTable;
    }

    private TerminalNode getFirst(ContextFreeGrammar grammar, int ruleIndex)
    {
        ContextFreeGrammarSyntaxNode concatenationNode = grammar.getProduction(ruleIndex).getChildNodes().get(1);
        if (concatenationNode.getChildNodes().get(0) instanceof TerminalNode)
        {
            return (TerminalNode)concatenationNode.getChildNodes().get(0);
        }

        return null;
    }
}
