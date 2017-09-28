package larp.automaton.regularlanguage;

import larp.automaton.EpsilonNFA;
import larp.automaton.EpsilonNFAState;
import larp.automaton.State;
import larp.automaton.StateGroup;
import larp.automaton.StateTransition;
import larp.grammar.regularlanguage.CharacterNode;
import larp.grammar.regularlanguage.ConcatenationNode;
import larp.grammar.regularlanguage.KleeneClosureNode;
import larp.grammar.regularlanguage.OrNode;
import larp.grammar.regularlanguage.RegularExpressionSyntaxNode;

import java.util.Vector;

public class RegularExpressionSyntaxCompiler
{
    public EpsilonNFA compile(RegularExpressionSyntaxNode rootNode)
    {
        StateGroup stateGroup = this.compileStateGroup(rootNode);
        stateGroup.getEndState().setAccepting(true);

        return new EpsilonNFA(stateGroup.getStartState());
    }

    private StateGroup compileStateGroup(RegularExpressionSyntaxNode node)
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

    private StateGroup buildCharacterStateGroup(RegularExpressionSyntaxNode node)
    {
        State startState = new EpsilonNFAState("", false);
        State endState = new EpsilonNFAState("", false);
        startState.addTransition(new StateTransition(((CharacterNode)node).getCharacter(), endState));

        return new StateGroup(startState, endState);
    }

    private StateGroup buildKleeneClosureStateGroup(RegularExpressionSyntaxNode node)
    {
        StateGroup childGroup = this.compileStateGroup(node.getChildNodes().get(0));
        State startState = childGroup.getStartState();
        State endState = childGroup.getEndState();
        startState.addTransition(new StateTransition(null, endState));
        endState.addTransition(new StateTransition(null, startState));

        return new StateGroup(startState, endState);
    }

    private StateGroup buildConcatenationStateGroup(RegularExpressionSyntaxNode node)
    {
        Vector<RegularExpressionSyntaxNode> childNodes = node.getChildNodes();
        StateGroup[] childGroups = new StateGroup[childNodes.size()];

        for (int i = 0; i < childNodes.size(); i++)
        {
            StateGroup currentGroup = this.compileStateGroup(childNodes.get(i));
            childGroups[i] = currentGroup;
            if (i > 0)
            {
                childGroups[i - 1].getEndState().addTransition(new StateTransition(null, currentGroup.getStartState()));
            }
        }

        return new StateGroup(childGroups[0].getStartState(), childGroups[childGroups.length - 1].getEndState());
    }

    private StateGroup buildOrStateGroup(RegularExpressionSyntaxNode node)
    {
        State startState = new EpsilonNFAState("", false);
        State endState = new EpsilonNFAState("", false);

        Vector<RegularExpressionSyntaxNode> childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.size(); i++)
        {
            StateGroup currentGroup = this.compileStateGroup(childNodes.get(i));
            startState.addTransition(new StateTransition(null, currentGroup.getStartState()));
            currentGroup.getEndState().addTransition(new StateTransition(null, endState));
        }

        return new StateGroup(startState, endState);
    }
}
