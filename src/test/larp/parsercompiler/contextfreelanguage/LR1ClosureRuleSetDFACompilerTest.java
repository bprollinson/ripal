/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.automaton.StateTransition;
import larp.grammar.contextfreelanguage.Grammar;
import larp.parser.contextfreelanguage.LR0ClosureRuleSetDFA;
import larp.parser.contextfreelanguage.LR0ClosureRuleSetDFAState;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;
import java.util.Set;

public class LR1ClosureRuleSetDFACompilerTest
{
    @Test
    public void testCompileCreatesAndPropagatesEndOfStringLookaheadFromStartState()
    {
        LR1ClosureRuleSetDFACompiler compiler = new LR1ClosureRuleSetDFACompiler();

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        Set<GrammarClosureRule> closureRule0 = new HashSet<GrammarClosureRule>();
        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new EndOfStringNode());
        closureRule0.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S'"), new DotNode(), new NonTerminalNode("S"), new EndOfStringNode()));
        closureRule0.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new TerminalNode("a")));
        LR0ClosureRuleSetDFAState s0 = new LR0ClosureRuleSetDFAState("", false, closureRule0);

        Set<GrammarClosureRule> closureRule1 = new HashSet<GrammarClosureRule>();
        closureRule1.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S"), new TerminalNode("a"), new DotNode()));
        LR0ClosureRuleSetDFAState s1 = new LR0ClosureRuleSetDFAState("", false, closureRule1);

        Set<GrammarClosureRule> closureRule2 = new HashSet<GrammarClosureRule>();
        closureRule2.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S'"), new NonTerminalNode("S"), new DotNode(), new EndOfStringNode()));
        LR0ClosureRuleSetDFAState s2 = new LR0ClosureRuleSetDFAState("", false, closureRule2);

        Set<GrammarClosureRule> closureRule3 = new HashSet<GrammarClosureRule>();
        closureRule3.add(this.buildClosureRule(expectedLookaheadSymbols, new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode(), new DotNode()));
        LR0ClosureRuleSetDFAState s3 = new LR0ClosureRuleSetDFAState("", true, closureRule3);

        s0.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new TerminalNode("a"), s1));
        s0.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new NonTerminalNode("S"), s2));
        s2.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new EndOfStringNode(), s3));
        LR0ClosureRuleSetDFA expectedProductionSetDFA = new LR0ClosureRuleSetDFA(s0, augmentedGrammar);

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        assertTrue(expectedProductionSetDFA.structureEquals(compiler.compile(grammar)));
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
