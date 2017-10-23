package larp.parsetable;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.TerminalNode;

import java.util.HashMap;
import java.util.Vector;

public class LL1ParseTable
{
    private ContextFreeGrammar contextFreeGrammar;
    private HashMap<NonTerminalNode, HashMap<TerminalNode, Integer>> table;

    public LL1ParseTable(ContextFreeGrammar contextFreeGrammar)
    {
        this.contextFreeGrammar = contextFreeGrammar;
        this.table = new HashMap<NonTerminalNode, HashMap<TerminalNode, Integer>>();
    }

    public boolean accepts(String inputString)
    {
        Vector<ContextFreeGrammarSyntaxNode> stack = new Vector<ContextFreeGrammarSyntaxNode>();
        stack.add(contextFreeGrammar.getStartSymbol());

        String remainingInput = inputString;

        while (remainingInput.length() > 0 && stack.size() > 0)
        {
            ContextFreeGrammarSyntaxNode topNode = stack.get(0);
            String nextCharacter = remainingInput.substring(0, 1);

            if (topNode instanceof NonTerminalNode)
            {
                Integer position = null;
                HashMap<TerminalNode, Integer> entry = this.table.get(topNode);
                if (entry != null)
                {
                    position = entry.get(new TerminalNode(nextCharacter));
                }

                stack.remove(0);

                if (position == null)
                {
                    return false;
                }

                Vector<ContextFreeGrammarSyntaxNode> childNodes = this.contextFreeGrammar.getProduction(position).getChildNodes().get(1).getChildNodes();
                for (int i = childNodes.size() - 1; i >= 0; i--)
                {
                    stack.add(0, childNodes.get(i));
                }
            }
            else
            {
                if (!(((TerminalNode)topNode).getValue().substring(0, 1).equals(nextCharacter)))
                {
                    return false;
                }

                stack.remove(0);

                String remainingTerminalContent = ((TerminalNode)topNode).getValue().substring(1);
                if (remainingTerminalContent.length() > 0)
                {
                    stack.add(0, new TerminalNode(remainingTerminalContent));
                }

                remainingInput = remainingInput.substring(1);
            }
        }

        return remainingInput.length() == 0 && stack.size() == 0;
    }

    public void addCell(NonTerminalNode nonTerminalNode, TerminalNode terminalNode, int contextFreeGrammarRuleIndex)
    {
        HashMap<TerminalNode, Integer> entry = this.table.get(nonTerminalNode);
        if (entry == null)
        {
            entry = new HashMap<TerminalNode, Integer>();
        }

        entry.put(terminalNode, contextFreeGrammarRuleIndex);
        this.table.put(nonTerminalNode, entry);
    }

    public ContextFreeGrammar getContextFreeGrammar()
    {
        return this.contextFreeGrammar;
    }

    public HashMap<NonTerminalNode, HashMap<TerminalNode, Integer>> getTable()
    {
        return this.table;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LL1ParseTable))
        {
            return false;
        }

        if (!this.contextFreeGrammar.equals(((LL1ParseTable)other).getContextFreeGrammar()))
        {
            return false;
        }

        return this.table.equals(((LL1ParseTable)other).getTable());
    }
}
