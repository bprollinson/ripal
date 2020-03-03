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

        Set<Node> expectedLookaheadSymbols = new HashSet<Node>();
        expectedLookaheadSymbols.add(new EndOfStringNode());

        Set<GrammarClosureRule> closureRule0 = new HashSet<GrammarClosureRule>();
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

    @Test
    public void testCompileCreatesAndPropagatesSingleCharacterLookaheadFromStartState()
    {
        LR1ClosureRuleSetDFACompiler compiler = new LR1ClosureRuleSetDFACompiler();

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        Set<Node> expectedEndOfStringLookaheadSymbols = new HashSet<Node>();
        expectedEndOfStringLookaheadSymbols.add(new EndOfStringNode());
        Set<Node> expectedTerminalLookaheadSymbols = new HashSet<Node>();
        expectedTerminalLookaheadSymbols.add(new TerminalNode("b"));

        Set<GrammarClosureRule> closureRule0 = new HashSet<GrammarClosureRule>();
        closureRule0.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S'"), new DotNode(), new NonTerminalNode("S"), new EndOfStringNode()));
        closureRule0.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new TerminalNode("b")));
        closureRule0.add(this.buildClosureRule(expectedTerminalLookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new TerminalNode("a")));
        LR0ClosureRuleSetDFAState s0 = new LR0ClosureRuleSetDFAState("", false, closureRule0);

        Set<GrammarClosureRule> closureRule1 = new HashSet<GrammarClosureRule>();
        closureRule1.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new NonTerminalNode("A"), new DotNode(), new TerminalNode("b")));
        LR0ClosureRuleSetDFAState s1 = new LR0ClosureRuleSetDFAState("", false, closureRule1);

        Set<GrammarClosureRule> closureRule2 = new HashSet<GrammarClosureRule>();
        closureRule2.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"), new DotNode()));
        LR0ClosureRuleSetDFAState s2 = new LR0ClosureRuleSetDFAState("", false, closureRule2);

        Set<GrammarClosureRule> closureRule3 = new HashSet<GrammarClosureRule>();
        closureRule3.add(this.buildClosureRule(expectedTerminalLookaheadSymbols, new NonTerminalNode("A"), new TerminalNode("a"), new DotNode()));
        LR0ClosureRuleSetDFAState s3 = new LR0ClosureRuleSetDFAState("", false, closureRule3);

        Set<GrammarClosureRule> closureRule4 = new HashSet<GrammarClosureRule>();
        closureRule4.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S'"), new NonTerminalNode("S"), new DotNode(), new EndOfStringNode()));
        LR0ClosureRuleSetDFAState s4 = new LR0ClosureRuleSetDFAState("", false, closureRule4);

        Set<GrammarClosureRule> closureRule5 = new HashSet<GrammarClosureRule>();
        closureRule5.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode(), new DotNode()));
        LR0ClosureRuleSetDFAState s5 = new LR0ClosureRuleSetDFAState("", true, closureRule5);

        s0.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new NonTerminalNode("A"), s1));
        s0.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new TerminalNode("a"), s3));
        s0.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new NonTerminalNode("S"), s4));
        s1.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new TerminalNode("b"), s2));
        s4.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new EndOfStringNode(), s5));
        LR0ClosureRuleSetDFA expectedProductionSetDFA = new LR0ClosureRuleSetDFA(s0, augmentedGrammar);

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        assertTrue(expectedProductionSetDFA.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileCreatesAndPropagatesMultipleCharacterLookaheadFromStartState()
    {
        LR1ClosureRuleSetDFACompiler compiler = new LR1ClosureRuleSetDFACompiler();

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("c"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        Set<Node> expectedEndOfStringLookaheadSymbols = new HashSet<Node>();
        expectedEndOfStringLookaheadSymbols.add(new EndOfStringNode());
        Set<Node> expectedTerminalLookaheadSymbols = new HashSet<Node>();
        expectedTerminalLookaheadSymbols.add(new TerminalNode("b"));
        expectedTerminalLookaheadSymbols.add(new TerminalNode("c"));

        Set<GrammarClosureRule> closureRule0 = new HashSet<GrammarClosureRule>();
        closureRule0.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S'"), new DotNode(), new NonTerminalNode("S"), new EndOfStringNode()));
        closureRule0.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new TerminalNode("b")));
        closureRule0.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new NonTerminalNode("A"), new TerminalNode("c")));
        closureRule0.add(this.buildClosureRule(expectedTerminalLookaheadSymbols, new NonTerminalNode("A"), new DotNode(), new TerminalNode("a")));
        LR0ClosureRuleSetDFAState s0 = new LR0ClosureRuleSetDFAState("", false, closureRule0);

        Set<GrammarClosureRule> closureRule1 = new HashSet<GrammarClosureRule>();
        closureRule1.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new NonTerminalNode("A"), new DotNode(), new TerminalNode("b")));
        closureRule1.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new NonTerminalNode("A"), new DotNode(), new TerminalNode("c")));
        LR0ClosureRuleSetDFAState s1 = new LR0ClosureRuleSetDFAState("", false, closureRule1);

        Set<GrammarClosureRule> closureRule2 = new HashSet<GrammarClosureRule>();
        closureRule2.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"), new DotNode()));
        LR0ClosureRuleSetDFAState s2 = new LR0ClosureRuleSetDFAState("", false, closureRule2);

        Set<GrammarClosureRule> closureRule3 = new HashSet<GrammarClosureRule>();
        closureRule3.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("c"), new DotNode()));
        LR0ClosureRuleSetDFAState s3 = new LR0ClosureRuleSetDFAState("", false, closureRule3);

        Set<GrammarClosureRule> closureRule4 = new HashSet<GrammarClosureRule>();
        closureRule4.add(this.buildClosureRule(expectedTerminalLookaheadSymbols, new NonTerminalNode("A"), new TerminalNode("a"), new DotNode()));
        LR0ClosureRuleSetDFAState s4 = new LR0ClosureRuleSetDFAState("", false, closureRule4);

        Set<GrammarClosureRule> closureRule5 = new HashSet<GrammarClosureRule>();
        closureRule5.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S'"), new NonTerminalNode("S"), new DotNode(), new EndOfStringNode()));
        LR0ClosureRuleSetDFAState s5 = new LR0ClosureRuleSetDFAState("", false, closureRule5);

        Set<GrammarClosureRule> closureRule6 = new HashSet<GrammarClosureRule>();
        closureRule6.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode(), new DotNode()));
        LR0ClosureRuleSetDFAState s6 = new LR0ClosureRuleSetDFAState("", true, closureRule6);

        s0.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new NonTerminalNode("A"), s1));
        s1.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new TerminalNode("b"), s2));
        s1.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new TerminalNode("c"), s3));
        s0.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new TerminalNode("a"), s4));
        s0.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new NonTerminalNode("S"), s5));
        s5.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new EndOfStringNode(), s6));
        LR0ClosureRuleSetDFA expectedProductionSetDFA = new LR0ClosureRuleSetDFA(s0, augmentedGrammar);

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("c"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        assertTrue(expectedProductionSetDFA.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileCreatesAndPropagatesSingleCharacterLookaheadFromSubsequentState()
    {
        LR1ClosureRuleSetDFACompiler compiler = new LR1ClosureRuleSetDFACompiler();

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("c"));
        augmentedGrammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<Node> expectedEndOfStringLookaheadSymbols = new HashSet<Node>();
        expectedEndOfStringLookaheadSymbols.add(new EndOfStringNode());
        Set<Node> expectedTerminalLookaheadSymbols = new HashSet<Node>();
        expectedTerminalLookaheadSymbols.add(new TerminalNode("c"));

        Set<GrammarClosureRule> closureRule0 = new HashSet<GrammarClosureRule>();
        closureRule0.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S'"), new DotNode(), new NonTerminalNode("S"), new EndOfStringNode()));
        closureRule0.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("c")));
        LR0ClosureRuleSetDFAState s0 = new LR0ClosureRuleSetDFAState("", false, closureRule0);

        Set<GrammarClosureRule> closureRule1 = new HashSet<GrammarClosureRule>();
        closureRule1.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new TerminalNode("a"), new DotNode(), new NonTerminalNode("B"), new TerminalNode("c")));
        closureRule1.add(this.buildClosureRule(expectedTerminalLookaheadSymbols, new NonTerminalNode("B"), new DotNode(), new TerminalNode("b")));
        LR0ClosureRuleSetDFAState s1 = new LR0ClosureRuleSetDFAState("", false, closureRule1);

        Set<GrammarClosureRule> closureRule2 = new HashSet<GrammarClosureRule>();
        closureRule2.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new DotNode(), new TerminalNode("c")));
        LR0ClosureRuleSetDFAState s2 = new LR0ClosureRuleSetDFAState("", false, closureRule2);

        Set<GrammarClosureRule> closureRule3 = new HashSet<GrammarClosureRule>();
        closureRule3.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("c"), new DotNode()));
        LR0ClosureRuleSetDFAState s3 = new LR0ClosureRuleSetDFAState("", false, closureRule3);

        Set<GrammarClosureRule> closureRule4 = new HashSet<GrammarClosureRule>();
        closureRule4.add(this.buildClosureRule(expectedTerminalLookaheadSymbols, new NonTerminalNode("B"), new TerminalNode("b"), new DotNode()));
        LR0ClosureRuleSetDFAState s4 = new LR0ClosureRuleSetDFAState("", false, closureRule4);

        Set<GrammarClosureRule> closureRule5 = new HashSet<GrammarClosureRule>();
        closureRule5.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S'"), new NonTerminalNode("S"), new DotNode(), new EndOfStringNode()));
        LR0ClosureRuleSetDFAState s5 = new LR0ClosureRuleSetDFAState("", false, closureRule5);

        Set<GrammarClosureRule> closureRule6 = new HashSet<GrammarClosureRule>();
        closureRule6.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode(), new DotNode()));
        LR0ClosureRuleSetDFAState s6 = new LR0ClosureRuleSetDFAState("", true, closureRule6);

        s0.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new TerminalNode("a"), s1));
        s1.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new NonTerminalNode("B"), s2));
        s2.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new TerminalNode("c"), s3));
        s1.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new TerminalNode("b"), s4));
        s0.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new NonTerminalNode("S"), s5));
        s5.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new EndOfStringNode(), s6));
        LR0ClosureRuleSetDFA expectedProductionSetDFA = new LR0ClosureRuleSetDFA(s0, augmentedGrammar);

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("c"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        assertTrue(expectedProductionSetDFA.structureEquals(compiler.compile(grammar)));
    }

    @Test
    public void testCompileCreatesAndPropagatesMultipleCharacterLookaheadFromSubsequentState()
    {
        LR1ClosureRuleSetDFACompiler compiler = new LR1ClosureRuleSetDFACompiler();

        Grammar augmentedGrammar = new Grammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("c"));
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("d"));
        augmentedGrammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        Set<Node> expectedEndOfStringLookaheadSymbols = new HashSet<Node>();
        expectedEndOfStringLookaheadSymbols.add(new EndOfStringNode());
        Set<Node> expectedTerminalLookaheadSymbols = new HashSet<Node>();
        expectedTerminalLookaheadSymbols.add(new TerminalNode("c"));
        expectedTerminalLookaheadSymbols.add(new TerminalNode("d"));

        Set<GrammarClosureRule> closureRule0 = new HashSet<GrammarClosureRule>();
        closureRule0.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S'"), new DotNode(), new NonTerminalNode("S"), new EndOfStringNode()));
        closureRule0.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("c")));
        closureRule0.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new DotNode(), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("d")));
        LR0ClosureRuleSetDFAState s0 = new LR0ClosureRuleSetDFAState("", false, closureRule0);

        Set<GrammarClosureRule> closureRule1 = new HashSet<GrammarClosureRule>();
        closureRule1.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new TerminalNode("a"), new DotNode(), new NonTerminalNode("B"), new TerminalNode("c")));
        closureRule1.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new TerminalNode("a"), new DotNode(), new NonTerminalNode("B"), new TerminalNode("d")));
        closureRule1.add(this.buildClosureRule(expectedTerminalLookaheadSymbols, new NonTerminalNode("B"), new DotNode(), new TerminalNode("b")));
        LR0ClosureRuleSetDFAState s1 = new LR0ClosureRuleSetDFAState("", false, closureRule1);

        Set<GrammarClosureRule> closureRule2 = new HashSet<GrammarClosureRule>();
        closureRule2.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new DotNode(), new TerminalNode("c")));
        closureRule2.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new DotNode(), new TerminalNode("d")));
        LR0ClosureRuleSetDFAState s2 = new LR0ClosureRuleSetDFAState("", false, closureRule2);

        Set<GrammarClosureRule> closureRule3 = new HashSet<GrammarClosureRule>();
        closureRule3.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("c"), new DotNode()));
        LR0ClosureRuleSetDFAState s3 = new LR0ClosureRuleSetDFAState("", false, closureRule3);

        Set<GrammarClosureRule> closureRule4 = new HashSet<GrammarClosureRule>();
        closureRule4.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("d"), new DotNode()));
        LR0ClosureRuleSetDFAState s4 = new LR0ClosureRuleSetDFAState("", false, closureRule4);

        Set<GrammarClosureRule> closureRule5 = new HashSet<GrammarClosureRule>();
        closureRule5.add(this.buildClosureRule(expectedTerminalLookaheadSymbols, new NonTerminalNode("B"), new TerminalNode("b"), new DotNode()));
        LR0ClosureRuleSetDFAState s5 = new LR0ClosureRuleSetDFAState("", false, closureRule5);

        Set<GrammarClosureRule> closureRule6 = new HashSet<GrammarClosureRule>();
        closureRule6.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S'"), new NonTerminalNode("S"), new DotNode(), new EndOfStringNode()));
        LR0ClosureRuleSetDFAState s6 = new LR0ClosureRuleSetDFAState("", false, closureRule6);

        Set<GrammarClosureRule> closureRule7 = new HashSet<GrammarClosureRule>();
        closureRule7.add(this.buildClosureRule(expectedEndOfStringLookaheadSymbols, new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode(), new DotNode()));
        LR0ClosureRuleSetDFAState s7 = new LR0ClosureRuleSetDFAState("", true, closureRule7);

        s0.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new TerminalNode("a"), s1));
        s1.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new NonTerminalNode("B"), s2));
        s2.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new TerminalNode("c"), s3));
        s2.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new TerminalNode("d"), s4));
        s1.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new TerminalNode("b"), s5));
        s0.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new NonTerminalNode("S"), s6));
        s6.addTransition(new StateTransition<Node, LR0ClosureRuleSetDFAState>(new EndOfStringNode(), s7));
        LR0ClosureRuleSetDFA expectedProductionSetDFA = new LR0ClosureRuleSetDFA(s0, augmentedGrammar);

        Grammar grammar = new Grammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("c"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("d"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

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
