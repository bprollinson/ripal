package larp.automaton.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.parsetable.ContextFreeGrammar;

import java.util.HashSet;

public class FollowSetCalculator
{
    private ContextFreeGrammar grammar;

    public FollowSetCalculator(ContextFreeGrammar grammar)
    {
        this.grammar = grammar;
    }

    public HashSet<ContextFreeGrammarSyntaxNode> getFollow(NonTerminalNode nonTerminal)
    {
        return null;
    }
}
