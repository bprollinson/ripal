/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;

public class GrammarClosureRuleTest
{
    @Test
    public void testEqualsReturnsTrueForRuleWithSameSymbolsAndLookaheads()
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);

        HashSet<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("b"));

        GrammarClosureRule rule = new GrammarClosureRule(productionNode, lookaheadSymbols);

        ProductionNode otherProductionNode = new ProductionNode();
        otherProductionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode otherConcatenationNode = new ConcatenationNode();
        otherConcatenationNode.addChild(new TerminalNode("a"));
        otherProductionNode.addChild(otherConcatenationNode);

        HashSet<Node> otherLookaheadSymbols = new HashSet<Node>();
        otherLookaheadSymbols.add(new TerminalNode("b"));

        GrammarClosureRule otherRule = new GrammarClosureRule(otherProductionNode, otherLookaheadSymbols);

        assertEquals(otherRule, rule);
    }

    @Test
    public void testEqualsReturnsFalseForDifferentSymbols()
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);

        HashSet<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("b"));

        GrammarClosureRule rule = new GrammarClosureRule(productionNode, lookaheadSymbols);

        ProductionNode otherProductionNode = new ProductionNode();
        otherProductionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode otherConcatenationNode = new ConcatenationNode();
        otherConcatenationNode.addChild(new TerminalNode("b"));
        otherProductionNode.addChild(otherConcatenationNode);

        HashSet<Node> otherLookaheadSymbols = new HashSet<Node>();
        otherLookaheadSymbols.add(new TerminalNode("b"));

        GrammarClosureRule otherRule = new GrammarClosureRule(otherProductionNode, otherLookaheadSymbols);

        assertNotEquals(otherRule, rule);
    }

    @Test
    public void testNotEqualsReturnsFalseForDIfferentLookaheads()
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);

        HashSet<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("b"));

        GrammarClosureRule rule = new GrammarClosureRule(productionNode, lookaheadSymbols);

        ProductionNode otherProductionNode = new ProductionNode();
        otherProductionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode otherConcatenationNode = new ConcatenationNode();
        otherConcatenationNode.addChild(new TerminalNode("a"));
        otherProductionNode.addChild(otherConcatenationNode);

        HashSet<Node> otherLookaheadSymbols = new HashSet<Node>();
        otherLookaheadSymbols.add(new TerminalNode("c"));

        GrammarClosureRule otherRule = new GrammarClosureRule(otherProductionNode, otherLookaheadSymbols);

        assertNotEquals(otherRule, rule);
    }
}
