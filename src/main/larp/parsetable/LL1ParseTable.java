package larp.parsetable;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.TerminalNode;

import java.util.Vector;

public class LL1ParseTable
{
    private ContextFreeGrammar contextFreeGrammar;
    private Vector<NonTerminalNode> nonTerminalNodes;
    private Vector<TerminalNode> terminalNodes;
    private Vector<Integer> contextFreeGrammarRuleIndexes;

    public LL1ParseTable(ContextFreeGrammar contextFreeGrammar)
    {
        this.contextFreeGrammar = contextFreeGrammar;
        this.nonTerminalNodes = new Vector<NonTerminalNode>();
        this.terminalNodes = new Vector<TerminalNode>();
        this.contextFreeGrammarRuleIndexes = new Vector<Integer>();
    }

    public boolean accepts(String inputString)
    {
        Vector<ContextFreeGrammarSyntaxNode> stack = new Vector<ContextFreeGrammarSyntaxNode>();
        stack.add(contextFreeGrammar.getStartSymbol());

        String remainingInput = inputString;

        while (true)
        {
            ContextFreeGrammarSyntaxNode topNode = stack.get(0);
            String nextCharacter = remainingInput.substring(0, 1);

            if (topNode instanceof NonTerminalNode)
            {
                int position = -1;
                for (int i = 0; i < this.nonTerminalNodes.size(); i++)
                {
                    if (this.nonTerminalNodes.get(i).equals(topNode) && this.terminalNodes.get(i).getValue().equals(nextCharacter))
                    {
                        position = i;
                        break;
                    }
                }

                stack.removeElement(0);

                stack.add(this.nonTerminalNodes.get(position).getChildNodes().get(1).getChildNodes().get(0));
            }
            else
            {
                return nextCharacter.equals(((TerminalNode)topNode).getValue());
            }
        }
    }

    public void addCell(NonTerminalNode nonTerminalNode, TerminalNode terminalNode, int contextFreeGrammarRuleIndex)
    {
        this.nonTerminalNodes.add(nonTerminalNode);
        this.terminalNodes.add(terminalNode);
        this.contextFreeGrammarRuleIndexes.add(contextFreeGrammarRuleIndex);
    }
}
