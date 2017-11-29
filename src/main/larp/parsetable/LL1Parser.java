package larp.parsetable;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.EndOfStringNode;
import larp.grammar.contextfreelanguage.EpsilonNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.TerminalNode;

import java.util.List;
import java.util.Vector;

public class LL1Parser
{
    private LL1ParseTable parseTable;
    private ContextFreeGrammar contextFreeGrammar;
    private Vector<Integer> appliedRules;

    public LL1Parser(LL1ParseTable parseTable)
    {
        this.parseTable = parseTable;
        this.contextFreeGrammar = parseTable.getContextFreeGrammar();
        this.appliedRules = new Vector<Integer>();
    }

    public boolean accepts(String inputString)
    {
        this.appliedRules = new Vector<Integer>();

        Vector<ContextFreeGrammarSyntaxNode> stack = new Vector<ContextFreeGrammarSyntaxNode>();
        stack.add(contextFreeGrammar.getStartSymbol());

        StringBuffer remainingInput = new StringBuffer(inputString);

        while (remainingInput.length() > 0 && stack.size() > 0)
        {
            boolean continueProcessing = this.processInputCharacter(stack, remainingInput);
            if (!continueProcessing)
            {
                return false;
            }
        }

        this.processNonTerminalsAtEndOfString(stack, remainingInput);

        return remainingInput.length() == 0 && stack.size() == 0;
    }

    private boolean processInputCharacter(Vector<ContextFreeGrammarSyntaxNode> stack, StringBuffer remainingInput)
    {
        ContextFreeGrammarSyntaxNode topNode = stack.get(0);
        String nextCharacter = remainingInput.substring(0, 1);

        if (topNode instanceof NonTerminalNode)
        {
            boolean continueProcessing = this.processInputCharacterForNonTerminalNode(stack, (NonTerminalNode)topNode, nextCharacter);
            if (!continueProcessing)
            {
                return false;
            }
        }
        else
        {
            boolean continueProcessing = this.processInputCharacterForTerminalNode(stack, (TerminalNode)topNode, nextCharacter, remainingInput);
            if (!continueProcessing)
            {
                return false;
            }
        }

        return true;
    }

    private boolean processInputCharacterForNonTerminalNode(Vector<ContextFreeGrammarSyntaxNode> stack, NonTerminalNode topNode, String nextCharacter)
    {
        Integer position = this.parseTable.getCell(topNode, new TerminalNode(nextCharacter));

        stack.remove(0);

        if (position == null)
        {
            return false;
        }

        this.appliedRules.add(position);

        List<ContextFreeGrammarSyntaxNode> childNodes = this.contextFreeGrammar.getProduction(position).getChildNodes().get(1).getChildNodes();
        for (int i = childNodes.size() - 1; i >= 0; i--)
        {
            stack.add(0, childNodes.get(i));
        }

        return true;
    }

    private boolean processInputCharacterForTerminalNode(Vector<ContextFreeGrammarSyntaxNode> stack, TerminalNode topNode, String nextCharacter, StringBuffer remainingInput)
    {
        if (!topNode.getValue().substring(0, 1).equals(nextCharacter))
        {
            return false;
        }

        stack.remove(0);

        String remainingTerminalContent = topNode.getValue().substring(1);
        if (remainingTerminalContent.length() > 0)
        {
            stack.add(0, new TerminalNode(remainingTerminalContent));
        }

        remainingInput.deleteCharAt(0);

        return true;
    }

    private void processNonTerminalsAtEndOfString(Vector<ContextFreeGrammarSyntaxNode> stack, StringBuffer remainingInput)
    {
        while (remainingInput.length() == 0 && stack.size() > 0)
        {
            ContextFreeGrammarSyntaxNode topNode = stack.get(0);
            if (!(topNode instanceof NonTerminalNode))
            {
                break;
            }

            Integer position = this.parseTable.getCell((NonTerminalNode)topNode, new EndOfStringNode());
            if (position == null)
            {
                break;
            }

            this.appliedRules.add(position);

            stack.remove(0);
            List<ContextFreeGrammarSyntaxNode> childNodes = this.contextFreeGrammar.getProduction(position).getChildNodes().get(1).getChildNodes();
            for (int i = childNodes.size() - 1; i >= 0; i--)
            {
                if (!(childNodes.get(i) instanceof EpsilonNode))
                {
                    stack.add(0, childNodes.get(i));
                }
            }
        }
    }

    public List<Integer> getAppliedRules()
    {
        return this.appliedRules;
    }
}
