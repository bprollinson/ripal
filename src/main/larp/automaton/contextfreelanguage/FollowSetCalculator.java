package larp.automaton.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.EndOfStringNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.parsetable.ContextFreeGrammar;

import java.util.HashMap;
import java.util.HashSet;

public class FollowSetCalculator
{
    private ContextFreeGrammar grammar;
    private HashMap<NonTerminalNode, HashSet<ContextFreeGrammarSyntaxNode>> follows;

    public FollowSetCalculator(ContextFreeGrammar grammar)
    {
        this.grammar = grammar;
        this.follows = new HashMap<NonTerminalNode, HashSet<ContextFreeGrammarSyntaxNode>>();

        this.add(grammar.getStartSymbol(), new EndOfStringNode());
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
