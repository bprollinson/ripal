package larp.automaton.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.parsetable.ContextFreeGrammar;
import larp.parsetable.LL1ParseTable;

import java.util.Vector;

public class ContextFreeGrammarLL1SyntaxCompiler
{
    public LL1ParseTable compile(ContextFreeGrammar grammar)
    {
        LL1ParseTable parseTable = new LL1ParseTable(grammar);

        Vector<ContextFreeGrammarSyntaxNode> productions = grammar.getProductions();

        for (int i = 0; i < productions.size(); i++)
        {
            ContextFreeGrammarSyntaxNode productionNode = productions.get(i);
            TerminalNode first = this.getFirst(productionNode);

            if (first != null)
            {
                parseTable.addCell((NonTerminalNode)productionNode.getChildNodes().get(0), first, i);
            }
        }

        return parseTable;
    }

    private TerminalNode getFirst(ContextFreeGrammarSyntaxNode productionNode)
    {
        ContextFreeGrammarSyntaxNode concatenationNode = productionNode.getChildNodes().get(1);
        if (concatenationNode.getChildNodes().get(0) instanceof TerminalNode)
        {
            return (TerminalNode)concatenationNode.getChildNodes().get(0);
        }

        return null;
    }
}
