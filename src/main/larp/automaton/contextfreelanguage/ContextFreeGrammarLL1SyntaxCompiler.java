package larp.automaton.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.parsetable.ContextFreeGrammar;
import larp.parsetable.LL1ParseTable;

import java.util.HashSet;
import java.util.Vector;

public class ContextFreeGrammarLL1SyntaxCompiler
{
    public LL1ParseTable compile(ContextFreeGrammar grammar) throws AmbiguousLL1ParseTableException
    {
        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        Vector<ContextFreeGrammarSyntaxNode> productions = grammar.getProductions();

        FirstSetCalculator firstCalculator = new FirstSetCalculator(grammar);

        for (int firstRuleIndex = 0; firstRuleIndex < productions.size(); firstRuleIndex++)
        {
            HashSet<ContextFreeGrammarSyntaxNode> firsts = firstCalculator.getFirst(firstRuleIndex);
            NonTerminalNode nonTerminalNode = (NonTerminalNode)productions.get(firstRuleIndex).getChildNodes().get(0);

            for (ContextFreeGrammarSyntaxNode first: firsts)
            {
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
