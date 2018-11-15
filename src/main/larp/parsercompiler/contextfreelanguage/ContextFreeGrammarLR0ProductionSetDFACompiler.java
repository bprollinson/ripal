/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import larp.automaton.StateTransition;
import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.LR0ProductionSetDFA;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.util.ValueToSetMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContextFreeGrammarLR0ProductionSetDFACompiler
{
    private ContextFreeGrammarAugmentor grammarAugmentor;
    private ContextFreeGrammarClosureCalculator closureCalculator;
    private ProductionNodeDotRepository productionNodeDotRepository;
    private Map<Set<ContextFreeGrammarParseTreeNode>, LR0ProductionSetDFAState> productionSetToStateMap;

    public ContextFreeGrammarLR0ProductionSetDFACompiler()
    {
        this.grammarAugmentor = new ContextFreeGrammarAugmentor();
        this.closureCalculator = new ContextFreeGrammarClosureCalculator();
        this.productionNodeDotRepository = new ProductionNodeDotRepository();
    }

    public LR0ProductionSetDFA compile(ContextFreeGrammar grammar)
    {
        this.productionSetToStateMap = new HashMap<Set<ContextFreeGrammarParseTreeNode>, LR0ProductionSetDFAState>();
        ContextFreeGrammar augmentedGrammar = this.grammarAugmentor.augment(grammar);

        Set<ContextFreeGrammarParseTreeNode> productionSet = new HashSet<ContextFreeGrammarParseTreeNode>();
        ContextFreeGrammarParseTreeNode firstProductionWithDot = this.productionNodeDotRepository.addDotToProductionRightHandSide(augmentedGrammar.getProduction(0));
        productionSet.add(firstProductionWithDot);

        LR0ProductionSetDFAState startState = this.compileState(augmentedGrammar, productionSet, false);

        return new LR0ProductionSetDFA(startState, augmentedGrammar);
    }

    private LR0ProductionSetDFAState compileState(ContextFreeGrammar augmentedGrammar, Set<ContextFreeGrammarParseTreeNode> productionSet, boolean accepting)
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

    private void compileAndAttachAdjacentStates(ContextFreeGrammar augmentedGrammar, LR0ProductionSetDFAState state)
    {
        ValueToSetMap<ContextFreeGrammarParseTreeNode, ContextFreeGrammarParseTreeNode> symbolToNextClosureMap = new ValueToSetMap<ContextFreeGrammarParseTreeNode, ContextFreeGrammarParseTreeNode>();

        Set<ContextFreeGrammarParseTreeNode> productionSet = state.getProductionSet();
        for (ContextFreeGrammarParseTreeNode productionNode: productionSet)
        {
            ContextFreeGrammarParseTreeNode nextSymbol = this.productionNodeDotRepository.findProductionSymbolAfterDot(productionNode);
            if (nextSymbol != null && !(nextSymbol instanceof EpsilonNode))
            {
                ContextFreeGrammarParseTreeNode productionWithDotShifted = this.productionNodeDotRepository.shiftDotInProduction(productionNode);
                symbolToNextClosureMap.put(nextSymbol, productionWithDotShifted);
            }
        }

        for (Map.Entry<ContextFreeGrammarParseTreeNode, Set<ContextFreeGrammarParseTreeNode>> mapEntry: symbolToNextClosureMap.entrySet())
        {
            ContextFreeGrammarParseTreeNode input = mapEntry.getKey();
            Set<ContextFreeGrammarParseTreeNode> nextStateProductionSet = mapEntry.getValue();

            boolean nextStateAccepting = input instanceof EndOfStringNode;
            LR0ProductionSetDFAState nextState = this.compileState(augmentedGrammar, nextStateProductionSet, nextStateAccepting);
            state.addTransition(new StateTransition<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>(input, nextState));
        }
    }
}
