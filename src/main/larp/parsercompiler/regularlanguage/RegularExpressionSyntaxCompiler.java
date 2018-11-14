/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.regularlanguage;

import larp.automaton.EpsilonNFA;
import larp.automaton.EpsilonNFAState;
import larp.automaton.State;
import larp.automaton.StateTransition;
import larp.parser.regularlanguage.StateGroup;
import larp.parsetree.regularlanguage.CharacterNode;
import larp.parsetree.regularlanguage.ConcatenationNode;
import larp.parsetree.regularlanguage.KleeneClosureNode;
import larp.parsetree.regularlanguage.OrNode;
import larp.parsetree.regularlanguage.RegularExpressionSyntaxNode;

import java.util.ArrayList;
import java.util.List;

public class RegularExpressionSyntaxCompiler
{
    public EpsilonNFA compile(RegularExpressionSyntaxNode rootNode)
    {
        StateGroup<EpsilonNFAState> stateGroup = this.compileStateGroup(rootNode);
        stateGroup.getEndState().setAccepting(true);

        return new EpsilonNFA(stateGroup.getStartState());
    }

    private StateGroup<EpsilonNFAState> compileStateGroup(RegularExpressionSyntaxNode node)
    {
        if (node instanceof CharacterNode)
        {
            return this.buildCharacterStateGroup(node);
        }
        if (node instanceof KleeneClosureNode)
        {
            return this.buildKleeneClosureStateGroup(node);
        }
        if (node instanceof ConcatenationNode)
        {
            return this.buildConcatenationStateGroup(node);
        }
        if (node instanceof OrNode)
        {
            return this.buildOrStateGroup(node);
        }

        return null;
    }

    private StateGroup<EpsilonNFAState> buildCharacterStateGroup(RegularExpressionSyntaxNode node)
    {
        EpsilonNFAState startState = new EpsilonNFAState("", false);
        EpsilonNFAState endState = new EpsilonNFAState("", false);
        startState.addTransition(new StateTransition<Character, EpsilonNFAState>(((CharacterNode)node).getCharacter(), endState));

        return new StateGroup<EpsilonNFAState>(startState, endState);
    }

    private StateGroup<EpsilonNFAState> buildKleeneClosureStateGroup(RegularExpressionSyntaxNode node)
    {
        StateGroup<EpsilonNFAState> childGroup = this.compileStateGroup(node.getChildNodes().get(0));
        EpsilonNFAState startState = childGroup.getStartState();
        EpsilonNFAState endState = childGroup.getEndState();
        startState.addTransition(new StateTransition<Character, EpsilonNFAState>(null, endState));
        endState.addTransition(new StateTransition<Character, EpsilonNFAState>(null, startState));

        return new StateGroup<EpsilonNFAState>(startState, endState);
    }

    private StateGroup<EpsilonNFAState> buildConcatenationStateGroup(RegularExpressionSyntaxNode node)
    {
        List<RegularExpressionSyntaxNode> childNodes = node.getChildNodes();

        if (childNodes.size() == 0)
        {
            EpsilonNFAState startState = new EpsilonNFAState("", false);
            EpsilonNFAState endState = new EpsilonNFAState("", false);
            startState.addTransition(new StateTransition<Character, EpsilonNFAState>(null, endState));

            return new StateGroup<EpsilonNFAState>(startState, endState);
        }

        List<StateGroup<EpsilonNFAState>> childGroups = new ArrayList<StateGroup<EpsilonNFAState>>(childNodes.size());

        for (int i = 0; i < childNodes.size(); i++)
        {
            StateGroup<EpsilonNFAState> currentGroup = this.compileStateGroup(childNodes.get(i));
            childGroups.add(i, currentGroup);
            if (i > 0)
            {
                childGroups.get(i - 1).getEndState().addTransition(new StateTransition<Character, EpsilonNFAState>(null, currentGroup.getStartState()));
            }
        }

        return new StateGroup<EpsilonNFAState>((EpsilonNFAState)childGroups.get(0).getStartState(), (EpsilonNFAState)childGroups.get(childGroups.size() - 1).getEndState());
    }

    private StateGroup<EpsilonNFAState> buildOrStateGroup(RegularExpressionSyntaxNode node)
    {
        EpsilonNFAState startState = new EpsilonNFAState("", false);
        EpsilonNFAState endState = new EpsilonNFAState("", false);

        List<RegularExpressionSyntaxNode> childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.size(); i++)
        {
            StateGroup<EpsilonNFAState> currentGroup = this.compileStateGroup(childNodes.get(i));
            startState.addTransition(new StateTransition<Character, EpsilonNFAState>(null, currentGroup.getStartState()));
            currentGroup.getEndState().addTransition(new StateTransition<Character, EpsilonNFAState>(null, endState));
        }

        return new StateGroup<EpsilonNFAState>(startState, endState);
    }
}
