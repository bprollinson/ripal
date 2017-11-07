package larp.automaton.contextfreelanguage;

import larp.grammar.contextfreelanguage.ConcatenationNode;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.EndOfStringNode;
import larp.grammar.contextfreelanguage.EpsilonNode;
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
    private FirstSetCalculator firstSetCalculator;

    public FollowSetCalculator(ContextFreeGrammar grammar)
    {
        this.grammar = grammar;
        this.follows = new HashMap<NonTerminalNode, HashSet<ContextFreeGrammarSyntaxNode>>();
        this.firstSetCalculator = new FirstSetCalculator(this.grammar);

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
        HashSet<NonTerminalNode> nullableNonTerminals = new HashSet<NonTerminalNode>();
        Vector<ContextFreeGrammarSyntaxNode> productions = this.grammar.getProductions();
        for (int i = 0; i < productions.size(); i++)
        {
            ContextFreeGrammarSyntaxNode production = productions.get(i);
            ContextFreeGrammarSyntaxNode rightHandSide = production.getChildNodes().get(1);
            if (rightHandSide.getChildNodes().size() == 1 && rightHandSide.getChildNodes().get(0) instanceof EpsilonNode)
            {
                nullableNonTerminals.add((NonTerminalNode)production.getChildNodes().get(0));
            }
        }

        for (int i = 0; i < productions.size(); i++)
        {
            ContextFreeGrammarSyntaxNode production = productions.get(i);
            ContextFreeGrammarSyntaxNode rightHandSide = production.getChildNodes().get(1);

            for (int j = 0; j < rightHandSide.getChildNodes().size() - 1; j++)
            {
                ContextFreeGrammarSyntaxNode previousNode = rightHandSide.getChildNodes().get(j);

                boolean done = false;
                int k = j + 1;
                while (k < rightHandSide.getChildNodes().size() && !done)
                {
                    done = true;

                    ContextFreeGrammarSyntaxNode node = rightHandSide.getChildNodes().get(k);

                    if (previousNode instanceof NonTerminalNode && node instanceof TerminalNode)
                    {
                        this.add((NonTerminalNode)previousNode, new TerminalNode(((TerminalNode)node).getValue().substring(0, 1)));
                    }
                    if (previousNode instanceof NonTerminalNode && node instanceof NonTerminalNode)
                    {
                        HashSet<ContextFreeGrammarSyntaxNode> firstNodes = this.firstSetCalculator.getFirst((NonTerminalNode)node);
                        if (firstNodes.contains(new EpsilonNode()))
                        {
                            done = false;
                            firstNodes.remove(new EpsilonNode());
                        }
                        for (ContextFreeGrammarSyntaxNode firstNode: firstNodes)
                        {
                            this.add((NonTerminalNode)previousNode, firstNode);
                        }
                    }

                    k++;
                }
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

                    boolean innerDone = false;
                    int j = rightHandSide.getChildNodes().size() - 1;

                    while (j >= 0 && !innerDone)
                    {
                        innerDone = true;

                        ContextFreeGrammarSyntaxNode lastNode = rightHandSide.getChildNodes().get(j);
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

                            HashSet<ContextFreeGrammarSyntaxNode> firsts = this.firstSetCalculator.getFirst((NonTerminalNode)lastNode);
                            if (firsts.contains(new EpsilonNode()))
                            {
                                innerDone = false;
                            }
                        }

                        j--;
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
