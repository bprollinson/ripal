package larp.automaton.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.EpsilonNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.parsetable.ContextFreeGrammar;
import larp.parsetable.LL1ParseTable;

import java.util.HashSet;
import java.util.List;

public class ContextFreeGrammarLL1SyntaxCompiler
{
    public LL1ParseTable compile(ContextFreeGrammar grammar) throws AmbiguousLL1ParseTableException
    {
        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        List<ContextFreeGrammarSyntaxNode> productions = grammar.getProductions();

        FirstSetCalculator firstCalculator = new FirstSetCalculator(grammar);
        FollowSetCalculator followSetCalculator = new FollowSetCalculator(grammar);

        for (int firstRuleIndex = 0; firstRuleIndex < productions.size(); firstRuleIndex++)
        {
            HashSet<ContextFreeGrammarSyntaxNode> firsts = firstCalculator.getFirst(firstRuleIndex);
            NonTerminalNode nonTerminalNode = (NonTerminalNode)productions.get(firstRuleIndex).getChildNodes().get(0);

            for (ContextFreeGrammarSyntaxNode first: firsts)
            {
                if (first instanceof EpsilonNode)
                {
                    HashSet<ContextFreeGrammarSyntaxNode> follows = followSetCalculator.getFollow(nonTerminalNode);
                    for (ContextFreeGrammarSyntaxNode follow: follows)
                    {
                        parseTable.addCell(nonTerminalNode, follow, firstRuleIndex);
                    }

                    continue;
                }

                if (parseTable.getCell(nonTerminalNode, first) != null)
                {
                    throw new AmbiguousLL1ParseTableException();
                }

                parseTable.addCell(nonTerminalNode, first, firstRuleIndex);
            }
        }

        return parseTable;
    }
}
