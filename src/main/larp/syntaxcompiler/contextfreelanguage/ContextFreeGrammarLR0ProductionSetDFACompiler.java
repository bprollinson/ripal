/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.LR0ProductionSetDFA;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.regularlanguage.StateTransition;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
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
    private Map<Set<ContextFreeGrammarSyntaxNode>, LR0ProductionSetDFAState> productionSetToStateMap;

    public ContextFreeGrammarLR0ProductionSetDFACompiler()
    {
        this.grammarAugmentor = new ContextFreeGrammarAugmentor();
        this.closureCalculator = new ContextFreeGrammarClosureCalculator();
        this.productionNodeDotRepository = new ProductionNodeDotRepository();
    }

    public LR0ProductionSetDFA compile(ContextFreeGrammar grammar)
    {
        this.productionSetToStateMap = new HashMap<Set<ContextFreeGrammarSyntaxNode>, LR0ProductionSetDFAState>();
        ContextFreeGrammar augmentedGrammar = this.grammarAugmentor.augment(grammar);

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        ContextFreeGrammarSyntaxNode firstProductionWithDot = this.productionNodeDotRepository.addDotToProductionRightHandSide(augmentedGrammar.getProduction(0));
        productionSet.add(firstProductionWithDot);

        LR0ProductionSetDFAState startState = this.compileState(augmentedGrammar, productionSet, false);

        return new LR0ProductionSetDFA(startState, augmentedGrammar);
    }

    private LR0ProductionSetDFAState compileState(ContextFreeGrammar augmentedGrammar, Set<ContextFreeGrammarSyntaxNode> productionSet, boolean accepting)
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
        ValueToSetMap<ContextFreeGrammarSyntaxNode, ContextFreeGrammarSyntaxNode> symbolToNextClosureMap = new ValueToSetMap<ContextFreeGrammarSyntaxNode, ContextFreeGrammarSyntaxNode>();

        Set<ContextFreeGrammarSyntaxNode> productionSet = state.getProductionSet();
        for (ContextFreeGrammarSyntaxNode productionNode: productionSet)
        {
            ContextFreeGrammarSyntaxNode nextSymbol = this.productionNodeDotRepository.findProductionSymbolAfterDot(productionNode);
            if (nextSymbol != null && !(nextSymbol instanceof EpsilonNode))
            {
                ContextFreeGrammarSyntaxNode productionWithDotShifted = this.productionNodeDotRepository.shiftDotInProduction(productionNode);
                symbolToNextClosureMap.put(nextSymbol, productionWithDotShifted);
            }
        }

        for (Map.Entry<ContextFreeGrammarSyntaxNode, Set<ContextFreeGrammarSyntaxNode>> mapEntry: symbolToNextClosureMap.entrySet())
        {
            ContextFreeGrammarSyntaxNode input = mapEntry.getKey();
            Set<ContextFreeGrammarSyntaxNode> nextStateProductionSet = mapEntry.getValue();

            boolean nextStateAccepting = input instanceof EndOfStringNode;
            LR0ProductionSetDFAState nextState = this.compileState(augmentedGrammar, nextStateProductionSet, nextStateAccepting);
            state.addTransition(new StateTransition<ContextFreeGrammarSyntaxNode, LR0ProductionSetDFAState>(input, nextState));
        }
    }
}
