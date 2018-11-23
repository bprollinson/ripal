/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import larp.automaton.StateTransition;
import larp.grammar.contextfreelanguage.Grammar;
import larp.parser.contextfreelanguage.LR0ProductionSetDFA;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.util.ValueToSetMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContextFreeGrammarLR0ProductionSetDFACompiler
{
    private ContextFreeGrammarAugmentor grammarAugmentor;
    private GrammarClosureCalculator closureCalculator;
    private ProductionNodeDotRepository productionNodeDotRepository;
    private Map<Set<Node>, LR0ProductionSetDFAState> productionSetToStateMap;

    public ContextFreeGrammarLR0ProductionSetDFACompiler()
    {
        this.grammarAugmentor = new ContextFreeGrammarAugmentor();
        this.closureCalculator = new GrammarClosureCalculator();
        this.productionNodeDotRepository = new ProductionNodeDotRepository();
    }

    public LR0ProductionSetDFA compile(Grammar grammar)
    {
        this.productionSetToStateMap = new HashMap<Set<Node>, LR0ProductionSetDFAState>();
        Grammar augmentedGrammar = this.grammarAugmentor.augment(grammar);

        Set<Node> productionSet = new HashSet<Node>();
        Node firstProductionWithDot = this.productionNodeDotRepository.addDotToProductionRightHandSide(augmentedGrammar.getProduction(0));
        productionSet.add(firstProductionWithDot);

        LR0ProductionSetDFAState startState = this.compileState(augmentedGrammar, productionSet, false);

        return new LR0ProductionSetDFA(startState, augmentedGrammar);
    }

    private LR0ProductionSetDFAState compileState(Grammar augmentedGrammar, Set<Node> productionSet, boolean accepting)
    {
        productionSet = this.closureCalculator.calculate(augmentedGrammar, productionSet);
        LR0ProductionSetDFAState startState = new LR0ProductionSetDFAState("", accepting, productionSet);

        LR0ProductionSetDFAState cachedStartState = this.productionSetToStateMap.get(productionSet);
        if (cachedStartState != null)
        {
            return cachedStartState;
        }

        this.productionSetToStateMap.put(productionSet, startState);
        this.compileAndAttachAdjacentStates(augmentedGrammar, startState);

        return startState;
    }

    private void compileAndAttachAdjacentStates(Grammar augmentedGrammar, LR0ProductionSetDFAState state)
    {
        ValueToSetMap<Node, Node> symbolToNextClosureMap = new ValueToSetMap<Node, Node>();

        Set<Node> productionSet = state.getProductionSet();
        for (Node productionNode: productionSet)
        {
            Node nextSymbol = this.productionNodeDotRepository.findProductionSymbolAfterDot(productionNode);
            if (nextSymbol != null && !(nextSymbol instanceof EpsilonNode))
            {
                Node productionWithDotShifted = this.productionNodeDotRepository.shiftDotInProduction(productionNode);
                symbolToNextClosureMap.put(nextSymbol, productionWithDotShifted);
            }
        }

        for (Map.Entry<Node, Set<Node>> mapEntry: symbolToNextClosureMap.entrySet())
        {
            Node input = mapEntry.getKey();
            Set<Node> nextStateProductionSet = mapEntry.getValue();

            boolean nextStateAccepting = input instanceof EndOfStringNode;
            LR0ProductionSetDFAState nextState = this.compileState(augmentedGrammar, nextStateProductionSet, nextStateAccepting);
            state.addTransition(new StateTransition<Node, LR0ProductionSetDFAState>(input, nextState));
        }
    }
}
