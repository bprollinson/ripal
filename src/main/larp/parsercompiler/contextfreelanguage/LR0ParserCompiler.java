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
import larp.parser.contextfreelanguage.LR0GotoAction;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ProductionSetDFA;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LR0ParserCompiler
{
    private ContextFreeGrammarLR0ProductionSetDFACompiler DFACompiler;

    public LR0ParserCompiler()
    {
        this.DFACompiler = new ContextFreeGrammarLR0ProductionSetDFACompiler();
    }

    public LR0ParseTable compile(Grammar grammar) throws AmbiguousLR0ParseTableException
    {
        if (grammar.getStartSymbol() == null)
        {
            return new LR0ParseTable(grammar, null);
        }

        LR0ProductionSetDFA DFA = this.DFACompiler.compile(grammar);
        Grammar augmentedGrammar = DFA.getGrammar();
        LR0ProductionSetDFAState startState = DFA.getStartState();
        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, startState);

        this.processState(parseTable, startState, this.calculateTerminalNodeList(augmentedGrammar), new ArrayList<LR0ProductionSetDFAState>());

        return parseTable;
    }

    private Set<ContextFreeGrammarParseTreeNode> calculateTerminalNodeList(Grammar grammar)
    {
        Set<ContextFreeGrammarParseTreeNode> terminalNodes = new HashSet<ContextFreeGrammarParseTreeNode>();

        List<ContextFreeGrammarParseTreeNode> productions = grammar.getProductions();
        for (ContextFreeGrammarParseTreeNode production: productions)
        {
            ContextFreeGrammarParseTreeNode concatenationNode = production.getChildNodes().get(1);
            for (ContextFreeGrammarParseTreeNode childNode: concatenationNode.getChildNodes())
            {
                if (childNode instanceof TerminalNode || childNode instanceof EndOfStringNode)
                {
                    terminalNodes.add(childNode);
                }
            }
        }

        return terminalNodes;
    }

    private void processState(LR0ParseTable parseTable, LR0ProductionSetDFAState state, Set<ContextFreeGrammarParseTreeNode> terminalNodes, List<LR0ProductionSetDFAState> coveredStates) throws AmbiguousLR0ParseTableException
    {
        coveredStates.add(state);

        if (!state.isAccepting())
        {
            this.processReduceActions(parseTable, state, terminalNodes);
        }

        List<StateTransition<ContextFreeGrammarParseTreeNode,LR0ProductionSetDFAState>> stateTransitions = state.getTransitions();
        for (StateTransition<ContextFreeGrammarParseTreeNode,LR0ProductionSetDFAState> stateTransition: stateTransitions)
        {
            ContextFreeGrammarParseTreeNode input = stateTransition.getInput();
            LR0ProductionSetDFAState nextState = stateTransition.getNextState();

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

    private void processReduceActions(LR0ParseTable parseTable, LR0ProductionSetDFAState state, Set<ContextFreeGrammarParseTreeNode> terminalNodes) throws AmbiguousLR0ParseTableException
    {
        Set<ContextFreeGrammarParseTreeNode> productions = state.getProductionSet();

        for (ContextFreeGrammarParseTreeNode production: productions)
        {
            ContextFreeGrammarParseTreeNode concatenationNode = production.getChildNodes().get(1);
            List<ContextFreeGrammarParseTreeNode> childNodes = concatenationNode.getChildNodes();
            int numChildNodes = childNodes.size();

            if (childNodes.get(numChildNodes - 1) instanceof DotNode)
            {
                List<Integer> productionPositions = this.findProductionPositions(production, parseTable.getGrammar().getProductions());

                for (int productionPosition: productionPositions)
                {
                    for (ContextFreeGrammarParseTreeNode terminalNode: terminalNodes)
                    {
                        parseTable.addCell(state, terminalNode, new LR0ReduceAction(productionPosition));
                    }
                }
            }
        }
    }

    private List<Integer> findProductionPositions(ContextFreeGrammarParseTreeNode needle, List<ContextFreeGrammarParseTreeNode> haystack)
    {
        ContextFreeGrammarParseTreeNode originalNeedle = this.removeDotFromProduction(needle);

        List<Integer> productionPositions = new ArrayList<Integer>();
        for (int i = 0; i < haystack.size(); i++)
        {
            ContextFreeGrammarParseTreeNode haystackProduction = haystack.get(i);
            if (haystackProduction.equals(originalNeedle))
            {
                productionPositions.add(i);
            }
        }

        return productionPositions;
    }

    private ContextFreeGrammarParseTreeNode removeDotFromProduction(ContextFreeGrammarParseTreeNode production)
    {
        ProductionNode newProduction = new ProductionNode();
        newProduction.addChild(production.getChildNodes().get(0));

        ConcatenationNode newConcatenationNode = new ConcatenationNode();
        ContextFreeGrammarParseTreeNode concatenationNode = production.getChildNodes().get(1);
        for (int i = 0; i < concatenationNode.getChildNodes().size() - 1; i++)
        {
            newConcatenationNode.addChild(concatenationNode.getChildNodes().get(i));
        }
        newProduction.addChild(newConcatenationNode);

        return newProduction;
    }
}
