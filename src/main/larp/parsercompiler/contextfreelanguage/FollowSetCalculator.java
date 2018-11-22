/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.Grammar;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.util.ValueToSetMap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FollowSetCalculator
{
    private boolean initialized;
    private Grammar grammar;
    private ValueToSetMap<Node, Node> follows;
    private FirstSetCalculator firstSetCalculator;

    public FollowSetCalculator(Grammar grammar)
    {
        this.initialized = false;
        this.grammar = grammar;
        this.follows = new ValueToSetMap<Node, Node>();
        this.firstSetCalculator = new FirstSetCalculator(this.grammar);
    }

    public Set<Node> getFollow(NonTerminalNode nonTerminal)
    {
        this.initialize();

        return this.follows.get(nonTerminal);
    }

    private void initialize()
    {
        if (this.initialized)
        {
            return;
        }

        this.addStartSymbolDefaultNode();
        this.addFollowNodes();
        this.propagateFollowNodes();

        this.initialized = true;
    }

    private void addStartSymbolDefaultNode()
    {
        this.follows.put(this.grammar.getStartSymbol(), new EndOfStringNode());
    }

    private void addFollowNodes()
    {
        Set<NonTerminalNode> nullableNonTerminals = new HashSet<NonTerminalNode>();
        List<Node> productions = this.grammar.getProductions();
        for (int i = 0; i < productions.size(); i++)
        {
            Node production = productions.get(i);
            Node rightHandSide = production.getChildNodes().get(1);
            if (rightHandSide.getChildNodes().size() == 1 && rightHandSide.getChildNodes().get(0) instanceof EpsilonNode)
            {
                nullableNonTerminals.add((NonTerminalNode)production.getChildNodes().get(0));
            }
        }

        for (int i = 0; i < productions.size(); i++)
        {
            Node production = productions.get(i);
            this.addFollowNodesForProduction(production);
        }
    }

    private void addFollowNodesForProduction(Node production)
    {
        Node rightHandSide = production.getChildNodes().get(1);

        for (int i = 0; i < rightHandSide.getChildNodes().size() - 1; i++)
        {
            Node previousNode = rightHandSide.getChildNodes().get(i);

            boolean done = false;
            int j = i + 1;
            while (j < rightHandSide.getChildNodes().size() && !done)
            {
                Node node = rightHandSide.getChildNodes().get(j);
                done = this.addFollowNodesForProductionNode(previousNode, node);
                j++;
            }
        }
    }

    private boolean addFollowNodesForProductionNode(Node previousNode, Node node)
    {
        boolean done = true;

        if (previousNode instanceof NonTerminalNode && node instanceof TerminalNode)
        {
            this.follows.put(previousNode, new TerminalNode(((TerminalNode)node).getValue().substring(0, 1)));
        }
        if (previousNode instanceof NonTerminalNode && node instanceof NonTerminalNode)
        {
            Set<Node> firstNodes = this.firstSetCalculator.getFirst((NonTerminalNode)node);
            if (firstNodes.contains(new EpsilonNode()))
            {
                done = false;
                firstNodes.remove(new EpsilonNode());
            }
            for (Node firstNode: firstNodes)
            {
                this.follows.put(previousNode, firstNode);
            }
        }

        return done;
    }

    private void propagateFollowNodes()
    {
        List<Node> productions = this.grammar.getProductions();
        boolean done = false;

        while (!done)
        {
            done = true;

            for (int i = 0; i < productions.size(); i++)
            {
                Node production = productions.get(i);
                done = done & this.propagateFollowNodesForProduction(production);
            }
        }
    }

    private boolean propagateFollowNodesForProduction(Node production)
    {
        boolean done = true;

        NonTerminalNode leftHandSide = (NonTerminalNode)production.getChildNodes().get(0);
        Set<Node> leftHandFollow = this.follows.get(leftHandSide);

        if (leftHandFollow != null)
        {
            Node rightHandSide = production.getChildNodes().get(1);

            boolean innerDone = false;
            int i = rightHandSide.getChildNodes().size() - 1;

            while (i >= 0 && !innerDone)
            {
                innerDone = true;

                Node lastNode = rightHandSide.getChildNodes().get(i);
                if (lastNode instanceof NonTerminalNode)
                {
                    boolean followAdded = this.propagateAllParentFollows(lastNode, leftHandFollow);
                    done = done && !followAdded;

                    Set<Node> firsts = this.firstSetCalculator.getFirst((NonTerminalNode)lastNode);
                    innerDone = innerDone && !firsts.contains(new EpsilonNode());
                }

                i--;
            }
        }

        return done;
    }

    private boolean propagateAllParentFollows(Node lastNode, Set<Node> leftHandFollow)
    {
        boolean followAdded = false;

        for (Node parentFollow : leftHandFollow)
        {
            followAdded = followAdded | this.addParentFollow(lastNode, parentFollow);
        }

        return followAdded;
    }

    private boolean addParentFollow(Node lastNode, Node parentFollow)
    {
        int sizeBefore = 0;
        Set<Node> currentFollows = this.follows.get(lastNode);
        if (currentFollows != null)
        {
             sizeBefore = currentFollows.size();
        }

        this.follows.put(lastNode, parentFollow);
        int sizeAfter = this.follows.get(lastNode).size();

        return sizeBefore != sizeAfter;
    }
}
