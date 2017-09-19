package larp.automaton;

import larp.grammar.CharacterNode;
import larp.grammar.ConcatenationNode;
import larp.grammar.KleeneClosureNode;
import larp.grammar.RegularExpressionSyntaxNode;

import java.util.Vector;

public class RegularExpressionSyntaxCompiler
{
    public EpsilonNFA compile(RegularExpressionSyntaxNode rootNode)
    {
        StateGroup stateGroup = this.compileStateGroup(rootNode);

        return new EpsilonNFA(stateGroup.getStartState());
    }

    private StateGroup compileStateGroup(RegularExpressionSyntaxNode node)
    {
        if (node instanceof CharacterNode)
        {
            State startState = new EpsilonNFAState("", false);
            State endState = new EpsilonNFAState("", false);
            startState.addTransition(new StateTransition(((CharacterNode)node).getCharacter(), endState));

            return new StateGroup(startState, endState);
        }
        if (node instanceof KleeneClosureNode)
        {
            StateGroup childGroup = this.compileStateGroup(node.getChildNodes().get(0));
            State startState = childGroup.getStartState();
            State endState = childGroup.getEndState();
            startState.addTransition(new StateTransition(null, endState));
            endState.addTransition(new StateTransition(null, startState));

            return new StateGroup(startState, endState);
        }
        if (node instanceof ConcatenationNode)
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

        return null;
    }
}
