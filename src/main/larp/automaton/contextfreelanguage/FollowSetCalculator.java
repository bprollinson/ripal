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

        this.addStartSymbolDefaultNode();
        this.addFollowNodes();
        this.propagateFollowNodes();
    }

    public HashSet<ContextFreeGrammarSyntaxNode> getFollow(NonTerminalNode nonTerminal)
    {
        return this.follows.get(nonTerminal);
    }

    private void addStartSymbolDefaultNode()
    {
        this.add(this.grammar.getStartSymbol(), new EndOfStringNode());
    }

    private void addFollowNodes()
    {
        Vector<ContextFreeGrammarSyntaxNode> productions = this.grammar.getProductions();
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
                    this.add((NonTerminalNode)previousNode, new TerminalNode(((TerminalNode)node).getValue().substring(0, 1)));
                }

                previousNode = node;
            }
        }
    }

    private void propagateFollowNodes()
    {
        Vector<ContextFreeGrammarSyntaxNode> productions = this.grammar.getProductions();
        boolean done = false;

        while (!done)
        {
            done = true;
            for (int i = 0; i < productions.size(); i++)
            {
                ContextFreeGrammarSyntaxNode production = productions.get(i);
                NonTerminalNode leftHandSide = (NonTerminalNode)production.getChildNodes().get(0);
                HashSet<ContextFreeGrammarSyntaxNode> leftHandFollow = this.follows.get(leftHandSide);

                if (leftHandFollow != null)
                {
                    ContextFreeGrammarSyntaxNode rightHandSide = production.getChildNodes().get(1);
                    ContextFreeGrammarSyntaxNode lastNode = rightHandSide.getChildNodes().get(rightHandSide.getChildNodes().size() - 1);
                    if (lastNode instanceof NonTerminalNode)
                    {
                        for (ContextFreeGrammarSyntaxNode parentFollow : leftHandFollow)
                        {
                            int sizeBefore = 0;
                            HashSet<ContextFreeGrammarSyntaxNode> currentFollows = this.follows.get((NonTerminalNode)lastNode);
                            if (currentFollows != null)
                            {
                                 sizeBefore = currentFollows.size();
                            }

                            this.add((NonTerminalNode)lastNode, parentFollow);
                            int sizeAfter = this.follows.get((NonTerminalNode)lastNode).size();

                            if (sizeBefore != sizeAfter)
                            {
                                done = false;
                            }
                        }
                    }
                }
            }
        }
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
