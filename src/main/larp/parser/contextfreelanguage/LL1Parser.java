/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import larp.grammar.contextfreelanguage.Grammar;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class LL1Parser implements Parser
{
    private LL1ParseTable parseTable;
    private Grammar grammar;
    private List<Integer> appliedRules;

    public LL1Parser(LL1ParseTable parseTable)
    {
        this.parseTable = parseTable;
        this.grammar = parseTable.getGrammar();
        this.appliedRules = new ArrayList<Integer>();
    }

    public boolean accepts(String inputString)
    {
        this.appliedRules = new ArrayList<Integer>();

        List<Node> stack = new ArrayList<Node>();
        NonTerminalNode startSymbol = this.grammar.getStartSymbol();
        if (startSymbol == null)
        {
            return false;
        }
        stack.add(startSymbol);

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

    private boolean processInputCharacter(List<Node> stack, StringBuffer remainingInput)
    {
        Node topNode = stack.get(0);
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

    private boolean processInputCharacterForNonTerminalNode(List<Node> stack, NonTerminalNode topNode, String nextCharacter)
    {
        Integer position = this.parseTable.getCell(topNode, new TerminalNode(nextCharacter));

        stack.remove(0);

        if (position == null)
        {
            return false;
        }

        this.appliedRules.add(position);

        List<Node> childNodes = this.grammar.getProduction(position).getChildNodes().get(1).getChildNodes();
        for (int i = childNodes.size() - 1; i >= 0; i--)
        {
            stack.add(0, childNodes.get(i));
        }

        return true;
    }

    private boolean processInputCharacterForTerminalNode(List<Node> stack, TerminalNode topNode, String nextCharacter, StringBuffer remainingInput)
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

    private void processNonTerminalsAtEndOfString(List<Node> stack, StringBuffer remainingInput)
    {
        while (remainingInput.length() == 0 && stack.size() > 0)
        {
            Node topNode = stack.get(0);
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
            List<Node> childNodes = this.grammar.getProduction(position).getChildNodes().get(1).getChildNodes();
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

    public boolean parseTableEquals(LL1ParseTable otherParseTable)
    {
        return this.parseTable.equals(otherParseTable);
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LL1Parser))
        {
            return false;
        }

        return ((LL1Parser)other).parseTableEquals(this.parseTable);
    }
}
