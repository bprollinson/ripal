/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ripal.parsetree.contextfreelanguage.ConcatenationNode;
import ripal.parsetree.contextfreelanguage.DotNode;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.ProductionNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;
import java.util.Set;

public class ClosureLookaheadCombinerTest
{
    @Test
    public void testCombineReturnsEmptySetWhenClosureRulesSetIsEmpty()
    {
        ClosureLookaheadCombiner combiner = new ClosureLookaheadCombiner();

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();

        assertEquals(expectedClosureRules, combiner.combine(closureRules));
    }

    @Test
    public void testCombineReturnsInitialSetWhenClosureRulesContainNoCommonProductions()
    {
        ClosureLookaheadCombiner combiner = new ClosureLookaheadCombiner();

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("a"));
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("b"));
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("a"));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("b"));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));

        assertEquals(expectedClosureRules, combiner.combine(closureRules));
    }

    @Test
    public void testCombineCombinesLookaheadsForClosureRulesWithSameProduction()
    {
        ClosureLookaheadCombiner combiner = new ClosureLookaheadCombiner();

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("a"));
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("b"));
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("a"));
        expectedLookaheadSymbols.add(new TerminalNode("b"));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        assertEquals(expectedClosureRules, combiner.combine(closureRules));
    }

    @Test
    public void testCombineRemovesRedundantClosureRules()
    {
        ClosureLookaheadCombiner combiner = new ClosureLookaheadCombiner();

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("a"));
        lookaheadSymbols.add(new TerminalNode("b"));
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("b"));
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("a"));
        expectedLookaheadSymbols.add(new TerminalNode("b"));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));

        assertEquals(expectedClosureRules, combiner.combine(closureRules));
    }

    @Test
    public void testCombineCombinesOnlyClosureRulesWithSameProduction()
    {
        ClosureLookaheadCombiner combiner = new ClosureLookaheadCombiner();

        Set<GrammarClosureRule> closureRules = new HashSet<GrammarClosureRule>();
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("a"));
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("b"));
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new TerminalNode("c"));
        closureRules.add(this.buildClosureRule(lookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));

        Set<GrammarClosureRule> expectedClosureRules = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("a"));
        expectedLookaheadSymbols.add(new TerminalNode("b"));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A")));
        expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new TerminalNode("c"));
        expectedClosureRules.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new NonTerminalNode("B")));

        assertEquals(expectedClosureRules, combiner.combine(closureRules));
    }

    private GrammarClosureRule buildClosureRule(Set<Node> lookaheadSymbols, NonTerminalNode nonTerminalNode, Node... rightHandNodes)
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(nonTerminalNode);

        ConcatenationNode concatenationNode = new ConcatenationNode();
        for (Node rightHandNode: rightHandNodes)
        {
            concatenationNode.addChild(rightHandNode);
        }
        productionNode.addChild(concatenationNode);

        return new GrammarClosureRule(productionNode, lookaheadSymbols);
    }
}
