package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.util.SetMap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FollowSetCalculator
{
    private ContextFreeGrammar grammar;
    private SetMap<ContextFreeGrammarSyntaxNode, ContextFreeGrammarSyntaxNode> follows;
    private FirstSetCalculator firstSetCalculator;

    public FollowSetCalculator(ContextFreeGrammar grammar)
    {
        this.grammar = grammar;
        this.follows = new SetMap<ContextFreeGrammarSyntaxNode, ContextFreeGrammarSyntaxNode>();
        this.firstSetCalculator = new FirstSetCalculator(this.grammar);

        this.addStartSymbolDefaultNode();
        this.addFollowNodes();
        this.propagateFollowNodes();
    }

    public Set<ContextFreeGrammarSyntaxNode> getFollow(NonTerminalNode nonTerminal)
    {
        return this.follows.get(nonTerminal);
    }

    private void addStartSymbolDefaultNode()
    {
        this.follows.put(this.grammar.getStartSymbol(), new EndOfStringNode());
    }

    private void addFollowNodes()
    {
        Set<NonTerminalNode> nullableNonTerminals = new HashSet<NonTerminalNode>();
        List<ContextFreeGrammarSyntaxNode> productions = this.grammar.getProductions();
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
            this.addFollowNodesForProduction(production);
        }
    }

    private void addFollowNodesForProduction(ContextFreeGrammarSyntaxNode production)
    {
        ContextFreeGrammarSyntaxNode rightHandSide = production.getChildNodes().get(1);

        for (int i = 0; i < rightHandSide.getChildNodes().size() - 1; i++)
        {
            ContextFreeGrammarSyntaxNode previousNode = rightHandSide.getChildNodes().get(i);

            boolean done = false;
            int j = i + 1;
            while (j < rightHandSide.getChildNodes().size() && !done)
            {
                ContextFreeGrammarSyntaxNode node = rightHandSide.getChildNodes().get(j);
                done = this.addFollowNodesForProductionNode(previousNode, node);
                j++;
            }
        }
    }

    private boolean addFollowNodesForProductionNode(ContextFreeGrammarSyntaxNode previousNode, ContextFreeGrammarSyntaxNode node)
    {
        boolean done = true;

        if (previousNode instanceof NonTerminalNode && node instanceof TerminalNode)
        {
            this.follows.put(previousNode, new TerminalNode(((TerminalNode)node).getValue().substring(0, 1)));
        }
        if (previousNode instanceof NonTerminalNode && node instanceof NonTerminalNode)
        {
            Set<ContextFreeGrammarSyntaxNode> firstNodes = this.firstSetCalculator.getFirst((NonTerminalNode)node);
            if (firstNodes.contains(new EpsilonNode()))
            {
                done = false;
                firstNodes.remove(new EpsilonNode());
            }
            for (ContextFreeGrammarSyntaxNode firstNode: firstNodes)
            {
                this.follows.put(previousNode, firstNode);
            }
        }

        return done;
    }

    private void propagateFollowNodes()
    {
        List<ContextFreeGrammarSyntaxNode> productions = this.grammar.getProductions();
        boolean done = false;

        while (!done)
        {
            done = true;

            for (int i = 0; i < productions.size(); i++)
            {
                ContextFreeGrammarSyntaxNode production = productions.get(i);
                done = done & this.propagateFollowNodesForProduction(production);
            }
        }
    }

    private boolean propagateFollowNodesForProduction(ContextFreeGrammarSyntaxNode production)
    {
        boolean done = true;

        NonTerminalNode leftHandSide = (NonTerminalNode)production.getChildNodes().get(0);
        Set<ContextFreeGrammarSyntaxNode> leftHandFollow = this.follows.get(leftHandSide);

        if (leftHandFollow != null)
        {
            ContextFreeGrammarSyntaxNode rightHandSide = production.getChildNodes().get(1);

            boolean innerDone = false;
            int i = rightHandSide.getChildNodes().size() - 1;

            while (i >= 0 && !innerDone)
            {
                innerDone = true;

                ContextFreeGrammarSyntaxNode lastNode = rightHandSide.getChildNodes().get(i);
                if (lastNode instanceof NonTerminalNode)
                {
                    boolean followAdded = this.propagateAllParentFollows(lastNode, leftHandFollow);
                    done = done && !followAdded;

                    Set<ContextFreeGrammarSyntaxNode> firsts = this.firstSetCalculator.getFirst((NonTerminalNode)lastNode);
                    innerDone = innerDone && !firsts.contains(new EpsilonNode());
                }

                i--;
            }
        }

        return done;
    }

    private boolean propagateAllParentFollows(ContextFreeGrammarSyntaxNode lastNode, Set<ContextFreeGrammarSyntaxNode> leftHandFollow)
    {
        boolean followAdded = false;

        for (ContextFreeGrammarSyntaxNode parentFollow : leftHandFollow)
        {
            followAdded = followAdded | this.addParentFollow(lastNode, parentFollow);
        }

        return followAdded;
    }

    private boolean addParentFollow(ContextFreeGrammarSyntaxNode lastNode, ContextFreeGrammarSyntaxNode parentFollow)
    {
        int sizeBefore = 0;
        Set<ContextFreeGrammarSyntaxNode> currentFollows = this.follows.get(lastNode);
        if (currentFollows != null)
        {
             sizeBefore = currentFollows.size();
        }

        this.follows.put(lastNode, parentFollow);
        int sizeAfter = this.follows.get(lastNode).size();

        return sizeBefore != sizeAfter;
    }
}
