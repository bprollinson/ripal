package larp.automaton.contextfreelanguage;

import larp.grammar.contextfreelanguage.ConcatenationNode;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.EndOfStringNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.parsetable.ContextFreeGrammar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class FollowSetCalculator
{
    private ContextFreeGrammar grammar;
    private HashMap<NonTerminalNode, HashSet<ContextFreeGrammarSyntaxNode>> follows;

    public FollowSetCalculator(ContextFreeGrammar grammar)
    {
        this.grammar = grammar;
        this.follows = new HashMap<NonTerminalNode, HashSet<ContextFreeGrammarSyntaxNode>>();

        this.add(grammar.getStartSymbol(), new EndOfStringNode());
        Vector<ContextFreeGrammarSyntaxNode> productions = grammar.getProductions();
        for (int i = 0; i < productions.size(); i++)
        {
            ContextFreeGrammarSyntaxNode production = productions.get(i);
            ContextFreeGrammarSyntaxNode rightHandSide = production.getChildNodes().get(1);
            ContextFreeGrammarSyntaxNode previousNode = null;
            for (int j = 0; j < rightHandSide.getChildNodes().size(); j++)
            {
                ContextFreeGrammarSyntaxNode node = rightHandSide.getChildNodes().get(j);

                if (previousNode instanceof NonTerminalNode && node instanceof TerminalNode)
                {
                    this.add((NonTerminalNode)previousNode, node);
                }

                previousNode = node;
            }
        }
    }

    public HashSet<ContextFreeGrammarSyntaxNode> getFollow(NonTerminalNode nonTerminal)
    {
        return follows.get(nonTerminal);
    }

    private void add(NonTerminalNode nonTerminal, ContextFreeGrammarSyntaxNode first)
    {
        HashSet<ContextFreeGrammarSyntaxNode> firstSet = this.follows.get(nonTerminal);
        if (firstSet == null)
        {
            firstSet = new HashSet<ContextFreeGrammarSyntaxNode>();
            this.follows.put(nonTerminal, firstSet);
        }

        firstSet.add(first);
    }
}
