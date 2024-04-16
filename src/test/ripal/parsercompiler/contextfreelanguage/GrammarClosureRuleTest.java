/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ripal.parsetree.contextfreelanguage.ConcatenationNode;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.ProductionNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;

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

        assertTrue(rule.equals(otherRule));
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

        assertFalse(rule.equals(otherRule));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentLookaheads()
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

        assertFalse(rule.equals(otherRule));
    }

    @Test
    public void testEqualsReturnsFalseForObjectWithDifferentClass()
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);

        HashSet<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("b"));

        GrammarClosureRule rule = new GrammarClosureRule(productionNode, lookaheadSymbols);

        assertFalse(rule.equals(new ProductionNode()));
    }
}
