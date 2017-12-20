package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetable.AmbiguousLL1ParseTableException;
import larp.parsetable.LL1ParseTable;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;

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

                parseTable.addCell(nonTerminalNode, first, firstRuleIndex);
            }
        }

        return parseTable;
    }

    private void addCell(LL1ParseTable parseTable, NonTerminalNode nonTerminalNode, ContextFreeGrammarSyntaxNode terminalNode, int ruleIndex) throws AmbiguousLL1ParseTableException
    {
        if (parseTable.getCell(nonTerminalNode, terminalNode) != null)
        {
            throw new AmbiguousLL1ParseTableException();
        }

        parseTable.addCell(nonTerminalNode, terminalNode, ruleIndex);
    }
}
