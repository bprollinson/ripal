/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import larp.automaton.StateTransition;
import larp.grammar.contextfreelanguage.Grammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0ClosureRuleSetDFA;
import larp.parser.contextfreelanguage.LR0ClosureRuleSetDFAState;
import larp.parser.contextfreelanguage.LR0GotoAction;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LR0ParserCompiler
{
    protected LR0ClosureRuleSetDFACompiler DFACompiler;
    private ProductionNodeDotRepository productionNodeDotRepository;

    public LR0ParserCompiler()
    {
        this.DFACompiler = new LR0ClosureRuleSetDFACompiler();
        this.productionNodeDotRepository = new ProductionNodeDotRepository();
    }

    public LR0ParseTable compile(Grammar grammar) throws AmbiguousLR0ParseTableException
    {
        if (grammar.getStartSymbol() == null)
        {
            return new LR0ParseTable(grammar, null);
        }

        LR0ClosureRuleSetDFA DFA = this.DFACompiler.compile(grammar);
        Grammar augmentedGrammar = DFA.getGrammar();
        LR0ClosureRuleSetDFAState startState = DFA.getStartState();
        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, startState);

        this.processState(parseTable, startState, this.calculateTerminalNodeList(augmentedGrammar), new ArrayList<LR0ClosureRuleSetDFAState>());

        return parseTable;
    }

    protected boolean shouldReduceForProduction(Node nonTerminalNode, Node terminalNode, Set<Node> lookaheadSymbols)
    {
        return true;
    }

    private Set<Node> calculateTerminalNodeList(Grammar grammar)
    {
        Set<Node> terminalNodes = new HashSet<Node>();

        List<Node> productions = grammar.getProductions();
        for (Node production: productions)
        {
            Node concatenationNode = production.getChildNodes().get(1);
            for (Node childNode: concatenationNode.getChildNodes())
            {
                if (childNode instanceof TerminalNode || childNode instanceof EndOfStringNode)
                {
                    terminalNodes.add(childNode);
                }
            }
        }

        return terminalNodes;
    }

    private void processState(LR0ParseTable parseTable, LR0ClosureRuleSetDFAState state, Set<Node> terminalNodes, List<LR0ClosureRuleSetDFAState> coveredStates) throws AmbiguousLR0ParseTableException
    {
        coveredStates.add(state);

        if (!state.isAccepting())
        {
            this.processReduceActions(parseTable, state, terminalNodes);
        }

        List<StateTransition<Node,LR0ClosureRuleSetDFAState>> stateTransitions = state.getTransitions();
        for (StateTransition<Node,LR0ClosureRuleSetDFAState> stateTransition: stateTransitions)
        {
            Node input = stateTransition.getInput();
            LR0ClosureRuleSetDFAState nextState = stateTransition.getNextState();

            if (input instanceof TerminalNode)
            {
                parseTable.addCell(state, input, new LR0ShiftAction(nextState));
            }
            if (input instanceof NonTerminalNode)
            {
                parseTable.addCell(state, input, new LR0GotoAction(nextState));
            }
            if (nextState.isAccepting())
            {
                parseTable.addCell(state, input, new LR0AcceptAction());
            }

            if (!coveredStates.contains(nextState))
            {
                this.processState(parseTable, nextState, terminalNodes, coveredStates);
            }
        }
    }

    private void processReduceActions(LR0ParseTable parseTable, LR0ClosureRuleSetDFAState state, Set<Node> terminalNodes) throws AmbiguousLR0ParseTableException
    {
        Set<GrammarClosureRule> closureRules = state.getClosureRules();

        for (GrammarClosureRule closureRule: closureRules)
        {
            Node production = closureRule.getProductionNode();
            Node nonTerminalNode = production.getChildNodes().get(0);
            Node concatenationNode = production.getChildNodes().get(1);
            List<Node> childNodes = concatenationNode.getChildNodes();
            int numChildNodes = childNodes.size();

            if (childNodes.get(numChildNodes - 1) instanceof DotNode)
            {
                List<Integer> productionPositions = this.findProductionPositions(production, parseTable.getGrammar());

                for (int productionPosition: productionPositions)
                {
                    for (Node terminalNode: terminalNodes)
                    {
                        if (this.shouldReduceForProduction(nonTerminalNode, terminalNode, closureRule.getLookaheadSymbols()))
                        {
                            parseTable.addCell(state, terminalNode, new LR0ReduceAction(productionPosition));
                        }
                    }
                }
            }
        }
    }

    private List<Integer> findProductionPositions(Node needle, Grammar grammar)
    {
        Node originalProduction = this.productionNodeDotRepository.removeDotFromProduction(needle);

        return grammar.findProductionPositions(originalProduction);
    }
}
